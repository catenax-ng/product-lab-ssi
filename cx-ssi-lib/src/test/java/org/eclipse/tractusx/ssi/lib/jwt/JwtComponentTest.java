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
import org.eclipse.tractusx.ssi.lib.crypt.octet.OctetKeyPairFactory;
import org.eclipse.tractusx.ssi.lib.did.resolver.DidDocumentResolverRegistry;
import org.eclipse.tractusx.ssi.lib.serialization.jsonLd.JsonLdSerializer;
import org.eclipse.tractusx.ssi.lib.serialization.jsonLd.JsonLdSerializerImpl;
import org.eclipse.tractusx.ssi.lib.util.TestResourceUtil;
import org.eclipse.tractusx.ssi.lib.util.identity.TestDidDocumentResolver;
import org.eclipse.tractusx.ssi.lib.util.identity.TestIdentity;
import org.eclipse.tractusx.ssi.lib.util.identity.TestIdentityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtComponentTest {

  private TestIdentity jwtIssuer;

  private SignedJwtFactory factory;
  private SignedJwtVerifier verifier;
  private JsonLdSerializer jsonLdSerializer;
  private TestDidDocumentResolver testDidDocumentResolver;

  @BeforeEach
  @SneakyThrows
  public void setup() {
    jwtIssuer = TestIdentityFactory.newIdentityWithED25519Keys();

    testDidDocumentResolver = new TestDidDocumentResolver();
    testDidDocumentResolver.register(jwtIssuer);
    final DidDocumentResolverRegistry didDocumentResolverRegistry =
        testDidDocumentResolver.withRegistry();

    factory = new SignedJwtFactory(new OctetKeyPairFactory());
    verifier = new SignedJwtVerifier(didDocumentResolverRegistry);
    jsonLdSerializer = new JsonLdSerializerImpl();
  }

  @Test
  @SneakyThrows
  public void testSignatureSuccess() {
    var presentation = TestResourceUtil.getAlumniVerifiablePresentation();
    var serializedPresentation = jsonLdSerializer.serializePresentation(presentation);
    var jwt =
        factory.create(
            jwtIssuer.getDid(), "audience", serializedPresentation, jwtIssuer.getPrivateKey());

    var isValid = verifier.verify(jwt);
    Assertions.assertTrue(isValid);
  }

  @Test
  @SneakyThrows
  public void testSignatureFailure() {
    var presentation = TestResourceUtil.getAlumniVerifiablePresentation();
    var serializedPresentation = jsonLdSerializer.serializePresentation(presentation);

    final TestIdentity notTheIssuer = TestIdentityFactory.newIdentityWithED25519Keys();
    testDidDocumentResolver.register(notTheIssuer);

    var jwt =
        factory.create(
            notTheIssuer.getDid(), "audience", serializedPresentation, jwtIssuer.getPrivateKey());

    var isValid = verifier.verify(jwt);
    Assertions.assertFalse(isValid);
  }
}
