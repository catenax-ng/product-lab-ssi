package org.eclipse.tractusx.ssi.lib.util.vc;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.lib.model.proof.Proof;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredentialBuilder;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredentialSubject;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredentialType;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentation;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentationBuilder;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentationType;
import org.eclipse.tractusx.ssi.lib.util.identity.TestIdentity;

public class TestVerifiableFactory {

  @SneakyThrows
  public static VerifiableCredential createVerifiableCredential(TestIdentity issuer, Proof proof) {
    final VerifiableCredentialBuilder verifiableCredentialBuilder =
        new VerifiableCredentialBuilder();

    final VerifiableCredentialSubject verifiableCredentialSubject =
        new VerifiableCredentialSubject(Map.of("foo", "bar"));

    return verifiableCredentialBuilder
        .id(URI.create("did:test:id"))
        .type(List.of(VerifiableCredentialType.VERIFIABLE_CREDENTIAL))
        .issuer(issuer.getDid().toUri())
        .expirationDate(Instant.parse("2025-02-15T17:21:42Z").plusSeconds(3600))
        .issuanceDate(Instant.parse("2023-02-15T17:21:42Z"))
        .proof(proof)
        .credentialSubject(verifiableCredentialSubject)
        .build();
  }

  @SneakyThrows
  public static VerifiablePresentation createVerifiablePresentation(
      TestIdentity issuer, List<VerifiableCredential> vcs, Proof proof) {
    final VerifiablePresentationBuilder verifiableCredentialBuilder =
        new VerifiablePresentationBuilder();

    return verifiableCredentialBuilder
        .id(URI.create("did:test:id"))
        .type(List.of(VerifiablePresentationType.VERIFIABLE_PRESENTATION))
        .verifiableCredentials(vcs)
        .proof(proof)
        .build();
  }

  public static VerifiableCredential attachProof(
      VerifiableCredential verifiableCredential, Proof proof) {
    final VerifiableCredentialBuilder verifiableCredentialBuilder =
        new VerifiableCredentialBuilder();

    return verifiableCredentialBuilder
        .id(verifiableCredential.getId())
        .type(verifiableCredential.getTypes())
        .issuer(verifiableCredential.getIssuer())
        .expirationDate(verifiableCredential.getExpirationDate())
        .issuanceDate(verifiableCredential.getIssuanceDate())
        .proof(proof)
        .credentialSubject(verifiableCredential.getCredentialSubject())
        .build();
  }

  public static VerifiablePresentation attachProof(
      VerifiablePresentation verifiablePresentation, Proof proof) {

    final VerifiablePresentationBuilder verifiablePresentationBuilder =
        new VerifiablePresentationBuilder();

    return verifiablePresentationBuilder
        .id(URI.create("did:test:id"))
        .type(List.of(VerifiablePresentationType.VERIFIABLE_PRESENTATION))
        .verifiableCredentials(verifiablePresentation.getVerifiableCredentials())
        .proof(proof)
        .build();
  }
}
