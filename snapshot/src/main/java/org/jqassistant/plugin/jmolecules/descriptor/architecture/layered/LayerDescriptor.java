package org.jqassistant.plugin.jmolecules.descriptor.architecture.layered;

import com.buschmais.xo.neo4j.api.annotation.Label;
import org.jqassistant.plugin.jmolecules.report.ArchitectureLanguage;

@ArchitectureLanguage(ArchitectureLanguage.ArchitectureLanguageElement.Layer)
@Label("Layer")
public interface LayerDescriptor extends LayeredDescriptor {

    String getName();

}
