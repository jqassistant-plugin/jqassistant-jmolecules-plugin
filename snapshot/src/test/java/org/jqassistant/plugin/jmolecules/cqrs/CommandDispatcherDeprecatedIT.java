package org.jqassistant.plugin.jmolecules.cqrs;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.MethodDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.cqrs.deprecated.Command1;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandDispatcherDeprecatedIT extends AbstractJMoleculesPluginIT {

    @Test
    public void commandDispatcher() throws RuleException {
        scanClassesAndPackages(Command1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-cqrs:CommandDispatcher").getStatus());
        store.beginTransaction();
        List<MethodDescriptor> dispatchers = query("MATCH (d:JMolecules:CQRS:CommandDispatcher) RETURN d ORDER BY d.name").getColumn("d");
        assertThat(dispatchers.size()).isEqualTo(3);
        assertThat(dispatchers.get(0).getName()).isEqualTo("dispatches");
        assertThat(dispatchers.get(1).getName()).isEqualTo("dispatches2");
        assertThat(dispatchers.get(2).getName()).isEqualTo("dispatches3");
        store.commitTransaction();
    }

    @Test
    public void commandDispatcherDispatches() throws RuleException {
        scanClassesAndPackages(Command1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-cqrs:CommandDispatcherDispatches").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (d:JMolecules:CQRS:CommandDispatcher)-[:DISPATCHES]->(c:JMolecules:CQRS:Command) RETURN d.name AS dispatcher, c.name AS command ORDER BY dispatcher, command").getRows();
        assertThat(rows.size()).isEqualTo(2);
        assertThat(rows.get(0).get("dispatcher")).isEqualTo("dispatches2");
        assertThat(rows.get(0).get("command")).isEqualTo("Command1");
        assertThat(rows.get(1).get("dispatcher")).isEqualTo("dispatches3");
        assertThat(rows.get(1).get("command")).isEqualTo("Command2");
        store.commitTransaction();
    }

}
