package org.jqassistant.plugin.jmolecules.stereotype;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.test.plugin.AbstractPluginIT;

import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypesFileDescriptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StereotypeIT extends AbstractPluginIT {

    @Test
    void stereotypes() throws IOException {
        List<URL> urls = Collections.list(StereotypeIT.class.getClassLoader()
                .getResources("META-INF/jmolecules-stereotypes.json"));
        for (URL url : urls) {
            StereotypesFileDescriptor stereotypesFileDescriptor = getScanner().scan(url, url.getPath(), DefaultScope.NONE);
            assertThat(stereotypesFileDescriptor).isNotNull();
        }
    }
}
