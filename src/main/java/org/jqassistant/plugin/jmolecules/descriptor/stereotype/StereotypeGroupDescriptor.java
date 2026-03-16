package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import java.util.List;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("Group")
public interface StereotypeGroupDescriptor extends StereotypeDescriptor, PriorityTemplate, NamedDescriptor {

    String getDisplayName();

    void setDisplayName(String displayName);

    String getType();

    void setType(String type);

    @Relation("CONTAINS")
    List<StereotypeDefinitionDescriptor> getContainsStereotypes();

}
