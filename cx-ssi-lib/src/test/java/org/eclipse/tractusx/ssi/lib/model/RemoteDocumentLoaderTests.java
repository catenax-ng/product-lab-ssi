package org.eclipse.tractusx.ssi.lib.model;

import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.loader.DocumentLoaderOptions;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemoteDocumentLoaderTests {

  public static final URI VC_CONTEXT = URI.create("https://www.w3.org/2018/credentials/v1");

  private final Map<URI, Document> cache = new HashMap<>();
  private final RemoteDocumentLoader remoteDocumentLoader = new RemoteDocumentLoader(cache);

  @BeforeEach
  public void setup() {
    cache.clear();
    remoteDocumentLoader.setEnableHttps(true);
  }

  @Test
  @SneakyThrows
  public void testCachingEnabledByDefault() {
    remoteDocumentLoader.loadDocument(VC_CONTEXT, new DocumentLoaderOptions());

    Assertions.assertEquals(1, cache.size());
  }

  @Test
  @SneakyThrows
  public void testUsingCacheBeforeLoading() {
    final JsonStructure subStructure = () -> JsonValue.ValueType.STRING;
    final JsonDocument stub = JsonDocument.of(subStructure);

    cache.put(VC_CONTEXT, stub);

    final Document document =
        remoteDocumentLoader.loadDocument(VC_CONTEXT, new DocumentLoaderOptions());

    Assertions.assertEquals(stub, document);
  }
}
