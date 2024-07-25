package org.jqassistant.plugin.jmolecules.events;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.MethodDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent1;
import org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainEventHandlerIT extends AbstractJMoleculesPluginIT {

    @Test
    public void domainEventHandler() throws RuleException {
        scanClassesAndPackages(DomainEvent1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventHandler").getStatus());
        store.beginTransaction();
        List<MethodDescriptor> handlers = query("MATCH (h:JMolecules:Event:DomainEventHandler) RETURN h ORDER BY h.name").getColumn("h");
        assertThat(handlers.size()).isEqualTo(3);
        assertThat(handlers.get(0).getName()).isEqualTo("handles1");
        assertThat(handlers.get(1).getName()).isEqualTo("handles2");
        assertThat(handlers.get(2).getName()).isEqualTo("handles3");
        store.commitTransaction();
    }

    @Test
    public void domainEventHandlerHandlesByAnnotation() throws RuleException {
        scanClassesAndPackages(DomainEvent2.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventHandlerHandlesByAnnotation").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (h:JMolecules:Event:DomainEventHandler)-[:HANDLES]->(e:JMolecules:Event:DomainEvent) RETURN h.name AS handler, e.name AS event ORDER BY handler, event").getRows();
        assertThat(rows.size()).isEqualTo(4);
        assertThat(rows.get(0).get("handler")).isEqualTo("handles1");
        assertThat(rows.get(0).get("event")).isEqualTo("DomainEvent3");
        assertThat(rows.get(1).get("handler")).isEqualTo("handles2");
        assertThat(rows.get(1).get("event")).isEqualTo("DomainEvent1");
        assertThat(rows.get(2).get("handler")).isEqualTo("handles2");
        assertThat(rows.get(2).get("event")).isEqualTo("DomainEvent2");
        assertThat(rows.get(3).get("handler")).isEqualTo("handles2");
        assertThat(rows.get(3).get("event")).isEqualTo("DomainEvent3");
        store.commitTransaction();
    }

    @Test
    public void domainEventHandlerHandlesByParameter() throws RuleException {
        scanClassesAndPackages(DomainEvent2.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventHandlerHandlesByParameter").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (h:JMolecules:Event:DomainEventHandler)-[:HANDLES]->(e:JMolecules:Event:DomainEvent) RETURN h.name AS handler, e.name AS event ORDER BY handler, event").getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat(rows.get(0).get("handler")).isEqualTo("handles3");
        assertThat(rows.get(0).get("event")).isEqualTo("DomainEvent2");
        store.commitTransaction();
    }

}
