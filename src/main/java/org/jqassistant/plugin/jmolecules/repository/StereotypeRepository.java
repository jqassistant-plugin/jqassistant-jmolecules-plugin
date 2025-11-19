package org.jqassistant.plugin.jmolecules.repository;

import com.buschmais.xo.api.annotation.Repository;
import com.buschmais.xo.api.annotation.ResultOf;
import com.buschmais.xo.neo4j.api.annotation.Cypher;

import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeGroupDescriptor;

@Repository
public interface StereotypeRepository {

    @ResultOf
    @Cypher("MERGE (g:JMolecules:StereotypeGroup{name:$name}) RETURN g")
    StereotypeGroupDescriptor mergeGroup(String name);

    @ResultOf
    @Cypher("MERGE (g:JMolecules:Stereotype{name:$name}) RETURN g")
    StereotypeDescriptor mergeStereotype(String name);
}
