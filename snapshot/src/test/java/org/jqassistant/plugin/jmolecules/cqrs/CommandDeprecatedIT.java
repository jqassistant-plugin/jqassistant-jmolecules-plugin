package org.jqassistant.plugin.jmolecules.cqrs;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.cqrs.deprecated.Command1;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandDeprecatedIT extends AbstractJMoleculesPluginIT {

    @Test
    public void command() throws RuleException {
        scanClassesAndPackages(Command1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-cqrs:Command").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (t:JMolecules:CQRS:Command) RETURN t.name AS name, t.commandName AS commandName, t.commandNamespace AS commandNamespace ORDER BY t.fqn").getRows();
        assertThat(rows.size()).isEqualTo(2);
        assertThat(rows.get(0).get("name")).isEqualTo("Command1");
        assertThat(rows.get(0).get("commandName")).isEqualTo("Command1");
        assertThat(rows.get(0).get("commandNamespace")).isEqualTo("org.jqassistant.plugin.jmolecules.set.cqrs.deprecated");
        assertThat(rows.get(1).get("name")).isEqualTo("Command2");
        assertThat(rows.get(1).get("commandName")).isEqualTo("Command2");
        assertThat(rows.get(1).get("commandNamespace")).isEqualTo("test-namespace");
        store.commitTransaction();
    }

}
