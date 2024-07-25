package org.jqassistant.plugin.jmolecules.ddd;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.bc.App;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundedContextIT extends AbstractJMoleculesPluginIT {

    @Test
    public void boundedContextPackage() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-ddd:BoundedContextPackage").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (bC:JMolecules:DDD:BoundedContext) RETURN bC").getColumn("bC").size()).isEqualTo(2);
        assertThat(query("MATCH (bC:JMolecules:DDD:BoundedContext{identifier: 'org.jqassistant.plugin.jmolecules.set.bc.bc2', name: 'order'}) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        assertThat(query("MATCH (bC:JMolecules:DDD:BoundedContext{identifier: 'bc1', name: 'catalog', description: 'catalog bounded context'}) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        List<TypeDescriptor> types = query("MATCH (:JMolecules:DDD:BoundedContext{name: 'order'})-[:CONTAINS]->(t:Type) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("OrderRepository");
        assertThat(types.get(1).getName()).isEqualTo("OrderService");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

    @Test
    public void boundedContextDependency() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-ddd:BoundedContextDependency").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (:JMolecules:DDD:BoundedContext)-[d:DEPENDS_ON]->(:JMolecules:DDD:BoundedContext) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:JMolecules:DDD:BoundedContext{name: 'order'})-[d:DEPENDS_ON]->(:JMolecules:DDD:BoundedContext{name: 'catalog'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        store.commitTransaction();
    }

}
