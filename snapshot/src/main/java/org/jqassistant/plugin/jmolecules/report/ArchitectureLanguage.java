package org.jqassistant.plugin.jmolecules.report;

import com.buschmais.jqassistant.core.report.api.SourceProvider;
import com.buschmais.jqassistant.core.report.api.model.Language;
import com.buschmais.jqassistant.core.report.api.model.LanguageElement;

import org.jqassistant.plugin.jmolecules.descriptor.architecture.layered.LayerDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.architecture.onion.OnionDescriptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the language elements for "Architecture"
 */
@Language
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ArchitectureLanguage {

    ArchitectureLanguageElement value();

    enum ArchitectureLanguageElement implements LanguageElement {

        Layer {
            @Override
            public SourceProvider<LayerDescriptor> getSourceProvider() {
                return descriptor -> descriptor.getName();
            }
        },
        Onion {
            @Override
            public SourceProvider<OnionDescriptor> getSourceProvider() {
                return descriptor -> descriptor.getName();
            }
        };

        @Override
        public String getLanguage() {
            return "Architecture";
        }

    }
}
