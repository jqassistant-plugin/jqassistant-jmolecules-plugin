package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Assignment")
public interface StereotypeAssignmentDescriptor extends StereotypeDescriptor {

    String getValue();

    void setValue(String value);

    Type getType();

    void setType(Type type);

    enum Type {
        ANNOTATED_BY,
        ASSIGNABLE_FROM
    }

}
