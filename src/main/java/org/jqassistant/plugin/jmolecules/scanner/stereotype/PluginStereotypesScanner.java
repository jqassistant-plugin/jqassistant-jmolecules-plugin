package org.jqassistant.plugin.jmolecules.scanner.stereotype;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.api.model.URIDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.AbstractVirtualFileResource;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.xo.api.Query;

import static java.util.Collections.list;

public class PluginStereotypesScanner extends AbstractScannerPlugin<URI, Descriptor> {

    public static final String JMOLECULES_STEREOTYPES_JSON = "META-INF/jmolecules-stereotypes.json";
    public static final String JMOLECULES_SCHEME = "jmolecules";
    public static final String JMOLECULES_STEREOTYPES = "stereotypes";

    @Override
    public boolean accepts(URI uri, String path, Scope scope) {
        return JMOLECULES_SCHEME.equalsIgnoreCase(uri.getScheme()) && JMOLECULES_STEREOTYPES.equalsIgnoreCase(uri.getSchemeSpecificPart());
    }

    @Override
    public Descriptor scan(URI item, String path, Scope scope, Scanner scanner) throws IOException {
        List<URL> urls = list(Thread.currentThread()
                .getContextClassLoader()
                .getResources(JMOLECULES_STEREOTYPES_JSON));
        for (URL url : urls) {
            scan(getUri(url), scope, scanner);
        }
        return null;
    }

    // TODO check if this can be part of jQA core (common-plugin)
    private static void scan(URI uri, Scope scope, Scanner scanner) {
        Store store = scanner.getContext()
                .getStore();
        String path = getPath(uri);
        Query.Result<Query.Result.CompositeRowObject> result = store.executeQuery("MATCH (uri:URI{uri:$uri}) RETURN uri", Map.of("uri", uri.toString()));
        if (!result.hasResult()) {
            FileResource fileResource = new AbstractVirtualFileResource() {
                @Override
                public InputStream createStream() throws IOException {
                    return uri.toURL()
                            .openStream();
                }

                @Override
                protected String getRelativePath() {
                    return path;
                }
            };
            Descriptor descriptor = scanner.scan(fileResource, path, scope);
            if (descriptor != null) {
                URIDescriptor uriDescriptor = store.addDescriptorType(descriptor, URIDescriptor.class);
                uriDescriptor.setUri(uri.toString());
            }
        }
    }

    private static URI getUri(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String getPath(URI uri) {
        String scheme = uri.getScheme();
        if ("jar".equals(scheme)) {
            try {
                return getPath(new URI(uri.getSchemeSpecificPart()));
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        } else if ("file".equals(scheme)) {
            return uri.getPath();
        }
        throw new IllegalArgumentException("Unsupported scheme: " + scheme);
    }
}
