package org.jqassistant.plugin.jmolecules.ddd.constraints;

import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.assertj.core.api.HamcrestCondition;
import org.jqassistant.plugin.jmolecules.set.entity.Entity1;
import org.jqassistant.plugin.jmolecules.set.valueobject.ValueObject1;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

public class ValueObjectReferencingEntityOrAggregateRootIT extends AbstractJavaPluginIT {

    @Test
    public void testValueObjectReferencingEntity() throws RuleException {
        scanClasses(ValueObject1.class, Entity1.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:ValueObjectReferencingEntityOrAggregateRoot");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject1.class)));
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("Identifiable").getValue()).is(new HamcrestCondition<>(typeDescriptor(Entity1.class)));
        store.commitTransaction();
    }

}
