package org.jqassistant.plugin.jmolecules.descriptor.architecture.onion;

import com.buschmais.xo.neo4j.api.annotation.Label;
import org.jqassistant.plugin.jmolecules.descriptor.architecture.ArchitectureDescriptor;
import org.jqassistant.plugin.jmolecules.report.ArchitectureLanguage;

import static org.jqassistant.plugin.jmolecules.report.ArchitectureLanguage.ArchitectureLanguageElement.Onion;

@ArchitectureLanguage(Onion)
@Label("Onion")
public interface OnionDescriptor extends ArchitectureDescriptor {

    String getName();

}
