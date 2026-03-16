package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import java.util.List;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("Metadata")
public interface StereotypeMetadataFileDescriptor extends StereotypeDescriptor, FileDescriptor {

    @Relation("DEFINES_STEREOTYPE")
    List<StereotypeDefinitionDescriptor> getDeclaresStereotypes();

    @Relation("DEFINES_STEREOTYPE_GROUP")
    List<StereotypeGroupDescriptor> getDeclaresStereotypeGroups();
}
