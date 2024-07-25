package org.jqassistant.plugin.jmolecules.report;

import com.buschmais.jqassistant.core.report.api.SourceProvider;
import com.buschmais.jqassistant.core.report.api.model.Language;
import com.buschmais.jqassistant.core.report.api.model.LanguageElement;
import org.jqassistant.plugin.jmolecules.descriptor.ddd.BoundedContextDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.ddd.ModuleDescriptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the language elements for "DDD"
 */
@Language
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DDDLanguage {

    DDDLanguageElement value();

    enum DDDLanguageElement implements LanguageElement {
        BoundedContext {
            @Override
            public SourceProvider<BoundedContextDescriptor> getSourceProvider() {
                return descriptor -> descriptor.getName();
            }
        },
        Module {
            @Override
            public SourceProvider<ModuleDescriptor> getSourceProvider() {
                return descriptor -> descriptor.getName();
            }
        };

        @Override
        public String getLanguage() {
            return "DDD";
        }

    }
}
