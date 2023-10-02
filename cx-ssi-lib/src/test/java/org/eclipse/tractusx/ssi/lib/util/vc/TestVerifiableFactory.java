package org.eclipse.tractusx.ssi.lib.util.vc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.lib.model.proof.Proof;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.*;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredentialBuilder;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredentialSubject;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredentialType;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentation;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentationBuilder;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentationType;
import org.eclipse.tractusx.ssi.lib.util.identity.TestIdentity;

public class TestVerifiableFactory {
  static List<URI> contextList =
        List.of(
            URI.create("https://www.w3.org/2018/credentials/v1"),
            URI.create(
                "https://catenax-ng.github.io/product-core-schemas/businessPartnerData.json"),
            URI.create("https://w3id.org/security/suites/jws-2020/v1"),
            URI.create("https://w3id.org/security/suites/ed25519-2020/v1"));

  @SneakyThrows
  public static VerifiableCredential createVerifiableCredential(TestIdentity issuer, Proof proof) {
    final VerifiableCredentialBuilder verifiableCredentialBuilder =
        new VerifiableCredentialBuilder();

  

    
    VerifiableCredentialSubject verifiableCredentialSubject =
        new VerifiableCredentialSubject(Map.of("MembershipCredential",Map.of("holderIdentifier", "BPNSWVKGWCP7PDQR")));

    // add VC status
    String validStatus =
        "{\n"
            + "    \"id\": \"https://example.com/credentials/status/3#94567\",\n"
            + "    \"type\": \"StatusList2021Entry\",\n"
            + "    \"statusPurpose\": \"revocation\",\n"
            + "    \"statusListIndex\": \"94567\",\n"
            + "    \"statusListCredential\": \"https://example.com/credentials/status/3\"\n"
            + "  }";

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> statusMap = objectMapper.readValue(validStatus, Map.class);
    
    // VerifiableCredentialStatusList2021Entry verifiableCredentialStatusList2021Entry =
    //     new VerifiableCredentialStatusList2021Entry(null);
     
    return verifiableCredentialBuilder
        .id(URI.create("did:test:id"))
        .context(contextList)
        .type(List.of(VerifiableCredentialType.VERIFIABLE_CREDENTIAL))
        .issuer(issuer.getDid().toUri())
        .expirationDate(Instant.parse("2025-02-15T17:21:42Z").plusSeconds(3600))
        .issuanceDate(Instant.parse("2023-02-15T17:21:42Z"))
        .proof(proof)
        .credentialSubject(verifiableCredentialSubject)
        .verifiableCredentialStatus(null)
        .build();
  }

  @SneakyThrows
  public static VerifiablePresentation createVerifiablePresentation(
      TestIdentity issuer, List<VerifiableCredential> vcs, Proof proof) {
    final VerifiablePresentationBuilder verifiableCredentialBuilder =
        new VerifiablePresentationBuilder();

    return verifiableCredentialBuilder
        .id(URI.create("did:test:id"))
        .context(contextList)
        .type(List.of(VerifiablePresentationType.VERIFIABLE_PRESENTATION))
        .verifiableCredentials(vcs)
        .proof(proof)
        .build();
  }

  public static VerifiableCredential attachProof(
      VerifiableCredential verifiableCredential, Proof proof) {
    VerifiableCredentialBuilder verifiableCredentialBuilder = new VerifiableCredentialBuilder();

    return verifiableCredentialBuilder
        .id(verifiableCredential.getId())
        .context(verifiableCredential.getContext())
        .type(verifiableCredential.getTypes())
        .issuer(verifiableCredential.getIssuer())
        .expirationDate(verifiableCredential.getExpirationDate())
        .issuanceDate(verifiableCredential.getIssuanceDate())
        .proof(proof)
        .credentialSubject(verifiableCredential.getCredentialSubject())
        .verifiableCredentialStatus(null)
        .build();
  }

  public static VerifiablePresentation attachProof(
      VerifiablePresentation verifiablePresentation, Proof proof) {

    final VerifiablePresentationBuilder verifiablePresentationBuilder =
        new VerifiablePresentationBuilder();

    return verifiablePresentationBuilder
        .id(URI.create("did:test:id"))
        .context(verifiablePresentation.getContext())
        .type(List.of(VerifiablePresentationType.VERIFIABLE_PRESENTATION))
        .verifiableCredentials(verifiablePresentation.getVerifiableCredentials())
        .proof(proof)
        .build();
  }
}
