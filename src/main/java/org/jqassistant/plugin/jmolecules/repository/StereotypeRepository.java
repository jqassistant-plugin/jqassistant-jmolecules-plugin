package org.jqassistant.plugin.jmolecules.repository;

import com.buschmais.xo.api.annotation.Repository;
import com.buschmais.xo.api.annotation.ResultOf;
import com.buschmais.xo.neo4j.api.annotation.Cypher;

import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeDefinitionDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeGroupDescriptor;

@Repository
public interface StereotypeRepository {

    @ResultOf
    @Cypher("MERGE (g:JMolecules:Stereotype:Group{name:$name}) RETURN g")
    StereotypeGroupDescriptor mergeGroup(String name);

    @ResultOf
    @Cypher("MERGE (g:JMolecules:Stereotype:Definition{name:$name}) RETURN g")
    StereotypeDefinitionDescriptor mergeStereotype(String name);
}
