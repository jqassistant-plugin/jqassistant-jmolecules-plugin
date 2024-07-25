package org.jqassistant.plugin.jmolecules.events;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent1;
import org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainEventIT extends AbstractJMoleculesPluginIT {

    @Test
    public void domainEventType() throws RuleException {
        scanClassesAndPackages(DomainEvent1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventType").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (t:JMolecules:Event:DomainEvent) RETURN t.name AS name, t.eventName AS eventName, t.eventNamespace AS eventNamespace ORDER BY t.fqn").getRows();
        assertThat(rows.size()).isEqualTo(2);
        assertThat(rows.get(0).get("name")).isEqualTo("DomainEvent1");
        assertThat(rows.get(0).get("eventName")).isEqualTo("DomainEvent1");
        assertThat(rows.get(0).get("eventNamespace")).isEqualTo("org.jqassistant.plugin.jmolecules.set.domainevent");
        assertThat(rows.get(1).get("name")).isEqualTo("DomainEvent3");
        assertThat(rows.get(1).get("eventName")).isEqualTo("Event3");
        assertThat(rows.get(1).get("eventNamespace")).isEqualTo("test-namespace");
        store.commitTransaction();
    }

    @Test
    public void domainEventExtend() throws RuleException {
        scanClassesAndPackages(DomainEvent2.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventExtend").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:JMolecules:Event:DomainEvent) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("DomainEvent2");
        store.commitTransaction();
    }

    @Test
    public void externalizedType() throws RuleException {
        scanClassesAndPackages(DomainEvent1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:ExternalizedType").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (t:JMolecules:Event:Externalized:DomainEvent) RETURN t.name AS name ORDER BY t.fqn").getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat(rows.get(0).get("name")).isEqualTo("DomainEvent1");
        store.commitTransaction();
    }

    @Test
    public void externalizedExtend() throws RuleException {
        scanClassesAndPackages(DomainEvent2.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:ExternalizedExtend").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:JMolecules:Event:Externalized:DomainEvent) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("DomainEvent2");
        store.commitTransaction();
    }

}
