package org.eclipse.tractusx.ssi.lib.model.verifiable.credential;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.eclipse.tractusx.ssi.lib.model.Proof;

@NoArgsConstructor
public class VerifiableCredentialBuilder {

  private List<String> context;
  private URI id;
  private List<String> types;
  private URI issuer;
  private Instant issuanceDate;
  private Instant expirationDate;
  private VerifiableCredentialSubject credentialSubject;
  private Proof proof;

  public VerifiableCredentialBuilder context(List<String> context) {
    this.context = context;
    return this;
  }

  public VerifiableCredentialBuilder id(URI id) {
    this.id = id;
    return this;
  }

  public VerifiableCredentialBuilder type(List<String> types) {
    this.types = types;
    return this;
  }

  public VerifiableCredentialBuilder issuer(URI issuer) {
    this.issuer = issuer;
    return this;
  }

  public VerifiableCredentialBuilder issuanceDate(Instant issuanceDate) {
    this.issuanceDate = issuanceDate;
    return this;
  }

  public VerifiableCredentialBuilder expirationDate(Instant expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  public VerifiableCredentialBuilder credentialSubject(
      VerifiableCredentialSubject credentialSubject) {
    this.credentialSubject = credentialSubject;
    return this;
  }

  public VerifiableCredentialBuilder proof(Proof proof) {
    this.proof = proof;
    return this;
  }

  public VerifiableCredential build() {
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(VerifiableCredential.TIME_FORMAT).withZone(ZoneOffset.UTC);

    // Map.of does not work, as proof can be null
    Map<String, Object> map = new HashMap<>();
    map.put(VerifiableCredential.CONTEXT, context);
    map.put(VerifiableCredential.ID, id.toString());
    map.put(VerifiableCredential.TYPE, types);
    map.put(VerifiableCredential.ISSUER, issuer.toString());
    map.put(VerifiableCredential.ISSUANCE_DATE, formatter.format(issuanceDate));
    map.put(VerifiableCredential.EXPIRATION_DATE, formatter.format(expirationDate));
    map.put(VerifiableCredential.CREDENTIAL_SUBJECT, credentialSubject);
    map.put(VerifiableCredential.PROOF, proof);

    return new VerifiableCredential(map);
  }
}