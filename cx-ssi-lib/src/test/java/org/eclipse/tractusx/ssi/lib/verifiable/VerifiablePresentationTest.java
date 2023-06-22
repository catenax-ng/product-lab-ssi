package org.eclipse.tractusx.ssi.lib.verifiable;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Map;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.lib.model.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.lib.model.verifiable.presentation.VerifiablePresentation;
import org.eclipse.tractusx.ssi.lib.serialization.jsonLd.DanubeTechMapper;
import org.eclipse.tractusx.ssi.lib.util.TestResourceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifiablePresentationTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  @SneakyThrows
  public void canSerializeVP() {
    final Map<String, Object> vpFromMap = TestResourceUtil.getAlumniVerifiablePresentation();
    var vp = new VerifiablePresentation(vpFromMap);
    var json = vp.toJson();
    var mapFromJson = MAPPER.readValue(json, Map.class);
    Assertions.assertEquals(
        mapFromJson.get(VerifiablePresentation.VERIFIABLE_CREDENTIAL),
        vp.get(VerifiablePresentation.VERIFIABLE_CREDENTIAL));
  }

  @Test
  public void shouldNotRemoveContext() {
    var vpFromMap = TestResourceUtil.getBPNVerifiableCredential();
    var vc = new VerifiableCredential(vpFromMap);
    var vpMapped = DanubeTechMapper.map(vc);

    var ctx =
        URI.create(
            "https://raw.githubusercontent.com/catenax-ng/product-core-schemas/main/businessPartnerData");

    Assertions.assertTrue(vpMapped.getContexts().contains(ctx));
  }

  @Test
  public void shouldLoadCachedContext() {
    var vpFromMap = TestResourceUtil.getBPNVerifiableCredential();
    var vc = com.danubetech.verifiablecredentials.VerifiableCredential.fromMap(vpFromMap);
    Assertions.assertDoesNotThrow(
        () -> {
          vc.normalize("urdna2015");
        });
  }
}
