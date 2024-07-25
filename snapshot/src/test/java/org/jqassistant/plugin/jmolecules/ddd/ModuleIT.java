package org.jqassistant.plugin.jmolecules.ddd;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.module.App;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleIT extends AbstractJMoleculesPluginIT {

    @Test
    public void modulePackage() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-ddd:ModulePackage").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (m:JMolecules:DDD:Module) RETURN m").getColumn("m").size()).isEqualTo(2);
        assertThat(query("MATCH (m:JMolecules:DDD:Module{identifier: 'org.jqassistant.plugin.jmolecules.set.module.module2', name: 'order'}) RETURN m").getColumn("m").size()).isEqualTo(1);
        assertThat(query("MATCH (m:JMolecules:DDD:Module{identifier: 'module1', name: 'catalog', description: 'catalog module'}) RETURN m").getColumn("m").size()).isEqualTo(1);
        List<TypeDescriptor> types = query("MATCH (:JMolecules:DDD:Module{name: 'order'})-[:CONTAINS]->(t:Type) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("OrderRepository");
        assertThat(types.get(1).getName()).isEqualTo("OrderService");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

    @Test
    public void boundedContextDependency() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-ddd:ModuleDependency").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (:JMolecules:DDD:Module)-[d:DEPENDS_ON]->(:JMolecules:DDD:Module) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:JMolecules:DDD:Module{name: 'order'})-[d:DEPENDS_ON]->(:JMolecules:DDD:Module{name: 'catalog'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        store.commitTransaction();
    }

}
