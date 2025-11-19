package org.jqassistant.plugin.jmolecules.descriptor.stereotype;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.jmolecules.descriptor.JMoleculesDescriptor;

@Label("StereotypeGroup")
public interface StereotypeGroupDescriptor extends JMoleculesDescriptor {

    String getDisplayName();

    void setDisplayName(String displayName);

    String getType();

    void setType(String type);

    @Relation("CONTAINS")
    List<StereotypeDescriptor> getContainsStereotypes();

}
