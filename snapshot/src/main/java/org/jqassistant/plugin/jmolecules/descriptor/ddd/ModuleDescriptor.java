package org.jqassistant.plugin.jmolecules.descriptor.ddd;

import com.buschmais.xo.neo4j.api.annotation.Label;
import org.jqassistant.plugin.jmolecules.report.DDDLanguage;

import static org.jqassistant.plugin.jmolecules.report.DDDLanguage.DDDLanguageElement.Module;

@DDDLanguage(Module)
@Label(value = "Module")
public interface ModuleDescriptor extends DDDDescriptor {

    String getName();

}
