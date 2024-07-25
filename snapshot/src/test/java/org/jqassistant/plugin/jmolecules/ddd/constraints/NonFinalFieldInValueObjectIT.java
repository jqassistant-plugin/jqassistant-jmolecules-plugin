package org.jqassistant.plugin.jmolecules.ddd.constraints;

import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.FieldDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.assertj.core.api.HamcrestCondition;
import org.jqassistant.plugin.jmolecules.set.valueobject.AbstractValueObject;
import org.jqassistant.plugin.jmolecules.set.valueobject.ValueObject1;
import org.jqassistant.plugin.jmolecules.set.valueobject.ValueObject3;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.FieldDescriptorMatcher.fieldDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

public class NonFinalFieldInValueObjectIT extends AbstractJavaPluginIT {

    @Test
    public void testNonFinalFieldsInValueObjects() throws RuleException, NoSuchFieldException {
        scanClasses(ValueObject1.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:NonFinalFieldInValueObject");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(2);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject1.class)));
        assertThat((FieldDescriptor) rows.get(0).getColumns().get("MutableField").getValue()).is(new HamcrestCondition<>(fieldDescriptor(ValueObject1.class, "effectivelImmutableValue")));
        assertThat((TypeDescriptor) rows.get(1).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject1.class)));
        assertThat((FieldDescriptor) rows.get(1).getColumns().get("MutableField").getValue()).is(new HamcrestCondition<>(fieldDescriptor(ValueObject1.class, "mutableValue")));
        store.commitTransaction();
    }

    @Test
    public void testNonFinalFieldsInAbstractValueObjects() throws RuleException, NoSuchFieldException {
        scanClasses(AbstractValueObject.class, ValueObject3.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:NonFinalFieldInValueObject");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(2);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject3.class)));
        assertThat((FieldDescriptor) rows.get(0).getColumns().get("MutableField").getValue()).is(new HamcrestCondition<>(fieldDescriptor(AbstractValueObject.class, "field")));
        assertThat((TypeDescriptor) rows.get(1).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject3.class)));
        assertThat((FieldDescriptor) rows.get(1).getColumns().get("MutableField").getValue()).is(new HamcrestCondition<>(fieldDescriptor(AbstractValueObject.class, "effectivelyFinalField")));
        store.commitTransaction();
    }

}
