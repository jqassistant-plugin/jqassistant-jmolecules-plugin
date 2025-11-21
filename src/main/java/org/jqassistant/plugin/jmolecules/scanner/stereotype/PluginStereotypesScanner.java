package org.jqassistant.plugin.jmolecules.scanner.stereotype;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.api.model.URIDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.AbstractVirtualFileResource;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypesFileDescriptor;

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
        Store store = scanner.getContext()
                .getStore();
        List<URL> urls = list(Thread.currentThread()
                .getContextClassLoader()
                .getResources(JMOLECULES_STEREOTYPES_JSON));
        for (URL url : urls) {
            FileResource fileResource = new AbstractVirtualFileResource() {

                @Override
                public InputStream createStream() throws IOException {
                    return url.openStream();
                }

                @Override
                protected String getRelativePath() {
                    return JMOLECULES_STEREOTYPES_JSON;
                }
            };
            StereotypesFileDescriptor descriptor = scanner.scan(fileResource, getPath(url), scope);
            if (descriptor != null) {
                URIDescriptor uriDescriptor = store.addDescriptorType(descriptor, URIDescriptor.class);
                try {
                    uriDescriptor.setUri(url.toURI()
                            .toString());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private static String getPath(URL url) throws MalformedURLException {
        String protocol = url.getProtocol();
        if ("jar".equals(protocol)) {
            return getPath(new URL(url.getPath()));
        } else if ("file".equals(protocol)) {
            return url.getPath();
        }
        throw new IllegalArgumentException("Unsupported protocol: " + protocol);
    }
}
