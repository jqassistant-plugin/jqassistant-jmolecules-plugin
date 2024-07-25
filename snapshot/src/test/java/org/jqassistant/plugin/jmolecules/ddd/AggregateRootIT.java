package org.jqassistant.plugin.jmolecules.ddd;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.plugin.jmolecules.set.aggregate.Aggregate1;
import org.jqassistant.plugin.jmolecules.set.aggregate.Aggregate2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregateRootIT extends AbstractJavaPluginIT {

    @Test
    public void aggregateType() throws RuleException {
        scanClasses(Aggregate1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-ddd:AggregateRootType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:JMolecules:DDD:AggregateRoot) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Aggregate1");
        store.commitTransaction();
    }


    @Test
    public void aggregateTypeExtends() throws RuleException {
        scanClasses(Aggregate2.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("jmolecules-ddd:AggregateRootExtend").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:JMolecules:DDD:AggregateRoot) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Aggregate2");
        store.commitTransaction();
    }
}
