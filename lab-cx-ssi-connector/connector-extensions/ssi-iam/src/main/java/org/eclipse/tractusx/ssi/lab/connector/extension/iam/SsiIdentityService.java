package org.eclipse.tractusx.ssi.lab.connector.extension.iam;

import com.nimbusds.jwt.SignedJWT;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.eclipse.edc.spi.iam.ClaimToken;
import org.eclipse.edc.spi.iam.IdentityService;
import org.eclipse.edc.spi.iam.TokenParameters;
import org.eclipse.edc.spi.iam.TokenRepresentation;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.tractusx.ssi.lab.connector.extension.spi.agent.SsiAgent;
import org.eclipse.tractusx.ssi.lab.connector.extension.spi.agent.SsiAgentRegistry;
import org.eclipse.tractusx.ssi.lab.connector.extension.spi.verifiable.VerifiableCredential;
import org.eclipse.tractusx.ssi.lab.connector.extension.spi.verifiable.VerifiableCredentialType;
import org.eclipse.tractusx.ssi.lab.connector.extension.spi.verifiable.VerifiablePresentation;

@RequiredArgsConstructor
public class SsiIdentityService implements IdentityService {
  private final SsiAgentRegistry agentRegistry;
  private final URI membershipIssuer;
  private final Monitor monitor;

  /**
   * This function is called to get the JWT token, that is sent to another connector via IDS
   * protocol.
   *
   * @param tokenParameters token parameters
   * @return token
   */
  @Override
  public Result<TokenRepresentation> obtainClientCredentials(TokenParameters tokenParameters) {
    final String audience = tokenParameters.getAudience(); // IDS URL of another connector
    final SsiAgent agent = agentRegistry.getConfiguredAgent();
    final VerifiableCredential membershipCredential =
        agent.getByType(VerifiableCredentialType.MEMBERSHIP_CREDENTIAL);

    final SignedJWT membershipPresentation =
        agent.createVerifiablePresentationAsJwt(List.of(membershipCredential), audience);

    final String jwt = membershipPresentation.serialize(); // null

    monitor.info("Created JWT: " + jwt); // TODO Improve Log message
    final TokenRepresentation tokenRepresentation =
        TokenRepresentation.Builder.newInstance().token(jwt).build();

    return Result.success(tokenRepresentation);
  }

  @Override
  public Result<ClaimToken> verifyJwtToken(
      TokenRepresentation tokenRepresentation, String audience) {

    ClaimToken.Builder claimTokenBuilder = ClaimToken.Builder.newInstance();

    String token = tokenRepresentation.getToken();
    monitor.info("Received JWT: " + token);

    SignedJWT jwt;
    try {
      jwt = SignedJWT.parse(token);
    } catch (ParseException e) {
      throw new RuntimeException(e); // TODO
    }

    final SsiAgent agent = agentRegistry.getConfiguredAgent();
    final VerifiablePresentation verifiablePresentation = agent.check(jwt, audience);

    // TODO check that JWT issuer is the one the membership credential was issued to

    // TODO Parse Information from Verifiable Credentials and add to ClaimToken (e.g.
    // BusinessPartnerNumber)
    // TODO Check whether credentials issues by dataspace operator
    final VerifiableCredential membershipCredential =
        verifiablePresentation.getVerifiableCredentials().stream()
            .filter(
                c ->
                    c.getTypes().stream()
                        .anyMatch(VerifiableCredentialType.MEMBERSHIP_CREDENTIAL::equalsIgnoreCase))
            .findFirst()
            .orElse(null);

    if (membershipCredential == null) {
      return Result.failure("No membership credential provided");
    }

    final URI credentialIssuer = membershipCredential.getIssuer();
    if (!credentialIssuer.equals(membershipIssuer)) {
      return Result.failure(
          String.format(
              "Identity validation failed. Membership credential not issued by dataspace operator. Issuer: %s, Operator: %s",
              credentialIssuer, membershipIssuer));
    }

    final Map<String, Object> businessPartnerSubject =
        membershipCredential.getCredentialSubject().get(0);
    final String businessPartnerNumber =
        (String) businessPartnerSubject.get("businessPartnerNumber");
    final String did = (String) businessPartnerSubject.get("id");
    // TODO BPN is not optional!
    if (businessPartnerNumber != null) {
      // TODO Magic String

      claimTokenBuilder.claim("businessPartnerNumber", businessPartnerNumber);
    }
    claimTokenBuilder.claim("did", did);

    // TODO TBD
    // invalidate presentation e.g. by agent.invalidatePresentation(jwt);

    return Result.success(claimTokenBuilder.build());
  }
}
