package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import java.util.List;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("Instance")
public interface StereotypeInstanceDescriptor extends StereotypeDescriptor, NamedDescriptor {

    @Relation("IMPLEMENTED_BY")
    List<TypeDescriptor> getImplementedByTypes();

}
