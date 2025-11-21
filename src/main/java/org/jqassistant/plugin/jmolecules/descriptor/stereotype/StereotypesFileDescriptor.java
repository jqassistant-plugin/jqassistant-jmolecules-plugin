package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import java.util.List;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.jmolecules.descriptor.JMoleculesDescriptor;

@Label("Stereotypes")
public interface StereotypesFileDescriptor extends JMoleculesDescriptor, FileDescriptor {

    @Relation("DECLARES_STEREOTYPE")
    List<StereotypeDescriptor> getDeclaresStereotypes();

    @Relation("DECLARES_STEREOTYPE_GROUP")
    List<StereotypeGroupDescriptor> getDeclaresStereotypeGroups();
}
