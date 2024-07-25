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

public class CommandHandlerDeprecatedIT extends AbstractJMoleculesPluginIT {

    @Test
    public void commandHandler() throws RuleException {
        scanClassesAndPackages(Command1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-cqrs:CommandHandler").getStatus());
        store.beginTransaction();
        List<MethodDescriptor> handlers = query("MATCH (h:JMolecules:CQRS:CommandHandler) RETURN h ORDER BY h.name").getColumn("h");
        assertThat(handlers.size()).isEqualTo(3);
        assertThat(handlers.get(0).getName()).isEqualTo("handles1");
        assertThat(handlers.get(1).getName()).isEqualTo("handles2");
        assertThat(handlers.get(2).getName()).isEqualTo("handles3");
        store.commitTransaction();
    }

    @Test
    public void commandHandlerHandlesByAnnotation() throws RuleException {
        scanClassesAndPackages(Command1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-cqrs:CommandHandlerHandlesByAnnotation").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (h:JMolecules:CQRS:CommandHandler)-[:HANDLES]->(c:JMolecules:CQRS:Command) RETURN h.name AS handler, c.name AS command ORDER BY handler, command").getRows();
        assertThat(rows.size()).isEqualTo(3);
        assertThat(rows.get(0).get("handler")).isEqualTo("handles1");
        assertThat(rows.get(0).get("command")).isEqualTo("Command2");
        assertThat(rows.get(1).get("handler")).isEqualTo("handles2");
        assertThat(rows.get(1).get("command")).isEqualTo("Command1");
        assertThat(rows.get(2).get("handler")).isEqualTo("handles2");
        assertThat(rows.get(2).get("command")).isEqualTo("Command2");
        store.commitTransaction();
    }

    @Test
    public void commandHandlerHandlesByParameter() throws RuleException {
        scanClassesAndPackages(Command1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-cqrs:CommandHandlerHandlesByParameter").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (h:JMolecules:CQRS:CommandHandler)-[:HANDLES]->(c:JMolecules:CQRS:Command) RETURN h.name AS handler, c.name AS command ORDER BY handler, command").getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat(rows.get(0).get("handler")).isEqualTo("handles3");
        assertThat(rows.get(0).get("command")).isEqualTo("Command2");
        store.commitTransaction();
    }

}
