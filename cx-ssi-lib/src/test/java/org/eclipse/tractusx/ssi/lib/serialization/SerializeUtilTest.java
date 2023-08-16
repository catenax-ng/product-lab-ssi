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

package org.eclipse.tractusx.ssi.lib.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import org.eclipse.tractusx.ssi.lib.model.did.DidDocument;
import org.eclipse.tractusx.ssi.lib.model.did.Ed25519VerificationMethod;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentation;
import org.eclipse.tractusx.ssi.lib.util.TestResourceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SerializeUtilTest {

  @Test
  public void shouldSerializeVcContextFirst() {
    VerifiableCredential vc = TestResourceUtil.getAlumniVerifiableCredential();
    String serializedVc = SerializeUtil.toJson(vc);

    Assertions.assertTrue(
        serializedVc.startsWith("{\"@context\":["), "Serialized VC should start with @context");
  }

  @Test
  public void shouldSerializeVpContextFirst() {
    VerifiablePresentation vp = TestResourceUtil.getAlumniVerifiablePresentation();
    String serializedVc = SerializeUtil.toJson(vp);

    Assertions.assertTrue(
        serializedVc.startsWith("{\"@context\":["), "Serialized VP should start with @context");
  }

  @Test
  @DisplayName("Test property order in json string")
  void testJsonPropertyOrder() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    // test VC json
    VerifiableCredential vc = TestResourceUtil.getBPNVerifiableCredential();
    Set<String> set = objectMapper.readValue(vc.toJson(), LinkedHashMap.class).keySet();
    List<String> keyOrder = SerializeUtil.ORDER_MAP_LIST.get(VerifiableCredential.class);
    String[] valuesToCheck = set.toArray(new String[set.size()]);

    List<String> presentKeys = new ArrayList<>();
    for (String s : keyOrder) {
      if (set.contains(s)) {
        presentKeys.add(s);
      }
    }
    Assertions.assertEquals(presentKeys.size(), set.size());
    for (String key : presentKeys) {
      int keyIndex = presentKeys.indexOf(key);
      String value = valuesToCheck[keyIndex];
      Assertions.assertEquals(key, value);
    }

    // test VP json
    VerifiablePresentation vp = TestResourceUtil.getAlumniVerifiablePresentation();
    set = objectMapper.readValue(vp.toJson(), LinkedHashMap.class).keySet();
    keyOrder = SerializeUtil.ORDER_MAP_LIST.get(VerifiablePresentation.class);
    valuesToCheck = set.toArray(new String[set.size()]);
    presentKeys = new ArrayList<>();
    for (String s : keyOrder) {
      if (set.contains(s)) {
        presentKeys.add(s);
      }
    }
    Assertions.assertEquals(presentKeys.size(), set.size());
    for (String key : presentKeys) {
      int keyIndex = presentKeys.indexOf(key);
      String value = valuesToCheck[keyIndex];
      Assertions.assertEquals(key, value);
    }

    // test did document json
    DidDocument document =
        new DidDocument(TestResourceUtil.getDidDocument(Ed25519VerificationMethod.DEFAULT_TYPE));
    set = objectMapper.readValue(document.toJson(), LinkedHashMap.class).keySet();
    keyOrder = SerializeUtil.ORDER_MAP_LIST.get(DidDocument.class);
    valuesToCheck = set.toArray(new String[set.size()]);
    presentKeys = new ArrayList<>();
    for (String s : keyOrder) {
      if (set.contains(s)) {
        presentKeys.add(s);
      }
    }
    Assertions.assertEquals(presentKeys.size(), set.size());
    for (String key : presentKeys) {
      int keyIndex = presentKeys.indexOf(key);
      String value = valuesToCheck[keyIndex];
      Assertions.assertEquals(key, value);
    }
  }
}
