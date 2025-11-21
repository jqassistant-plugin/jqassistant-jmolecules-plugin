package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import java.util.List;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.jmolecules.descriptor.JMoleculesDescriptor;

@Label("Stereotype")
public interface StereotypeDescriptor extends JMoleculesDescriptor, PriorityTemplate, NamedDescriptor {

    @Relation("HAS_ASSIGNMENT")
    List<StereotypeAssignmentDescriptor> getStereotypeAssignments();

}
