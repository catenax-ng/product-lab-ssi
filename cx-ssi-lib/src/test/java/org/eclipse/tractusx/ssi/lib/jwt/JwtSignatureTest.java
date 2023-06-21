/********************************************************************************
 * Copyright (c) 2021,2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/

package org.eclipse.tractusx.ssi.lib.jwt;

import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.lib.crypt.ed25519.Ed25519Key;
import org.eclipse.tractusx.ssi.lib.resolver.OctetKeyPairFactory;
import org.eclipse.tractusx.ssi.lib.serialization.jwt.SerializedVerifiablePresentation;
import org.eclipse.tractusx.ssi.lib.util.identity.TestDidDocumentResolver;
import org.eclipse.tractusx.ssi.lib.util.identity.TestIdentity;
import org.eclipse.tractusx.ssi.lib.util.identity.TestIdentityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtSignatureTest {

  private SignedJwtFactory factory;
  private SignedJwtVerifier verifier;
  private SignedJwtValidator validator;

  private TestIdentity credentialIssuer;
  private TestIdentity credentialHolder;

  @BeforeEach
  @SneakyThrows
  public void setup() {}

  @Test
  @SneakyThrows
  public void testJwtVerify() {

    // setup
    final TestDidDocumentResolver didDocumentResolver = new TestDidDocumentResolver();

    credentialIssuer = TestIdentityFactory.newIdentityWithED25519Keys();
    didDocumentResolver.register(credentialIssuer);
    credentialHolder = TestIdentityFactory.newIdentityWithED25519Keys();
    didDocumentResolver.register(credentialHolder);

    factory = new SignedJwtFactory(new OctetKeyPairFactory());
    verifier = new SignedJwtVerifier(didDocumentResolver.withRegistry());
    validator = new SignedJwtValidator();
    SerializedVerifiablePresentation serializedVerifiablePresentation =
        new SerializedVerifiablePresentation("{ \"foo\": \"bar\" }");

    // prepare
    var signedJwt =
        factory.create(
            credentialIssuer.getDid(),
            credentialHolder.getDid().toString(),
            serializedVerifiablePresentation,
            new Ed25519Key(credentialIssuer.getPrivateKey()));

    // assert
    Assertions.assertDoesNotThrow(() -> verifier.verify(signedJwt));
  }
}
