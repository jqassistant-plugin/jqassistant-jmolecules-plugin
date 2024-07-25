package org.jqassistant.plugin.jmolecules.events;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.MethodDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent1;
import org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainEventPublisherIT extends AbstractJMoleculesPluginIT {

    @Test
    public void domainEventPublisher() throws RuleException {
        scanClassesAndPackages(DomainEvent1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventPublisher").getStatus());
        store.beginTransaction();
        List<MethodDescriptor> handlers = query("MATCH (p:JMolecules:Event:DomainEventPublisher) RETURN p ORDER BY p.name").getColumn("p");
        assertThat(handlers.size()).isEqualTo(3);
        assertThat(handlers.get(0).getName()).isEqualTo("publishes");
        assertThat(handlers.get(1).getName()).isEqualTo("publishes2");
        assertThat(handlers.get(2).getName()).isEqualTo("publishes3");
        store.commitTransaction();
    }

    @Test
    public void domainEventPublisherPublishes() throws RuleException {
        scanClassesAndPackages(DomainEvent2.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-event:DomainEventPublisherPublishes").getStatus());
        store.beginTransaction();
        List<Map<String, Object>> rows = query("MATCH (p:JMolecules:Event:DomainEventPublisher)-[:PUBLISHES]->(e:JMolecules:Event:DomainEvent) RETURN p.name AS publisher, e.name AS event ORDER BY publisher, event").getRows();
        assertThat(rows.size()).isEqualTo(2);
        assertThat(rows.get(0).get("publisher")).isEqualTo("publishes2");
        assertThat(rows.get(0).get("event")).isEqualTo("DomainEvent2");
        assertThat(rows.get(1).get("publisher")).isEqualTo("publishes3");
        assertThat(rows.get(1).get("event")).isEqualTo("DomainEvent3");
        store.commitTransaction();
    }

}
