package org.jqassistant.plugin.jmolecules.ddd.constraints;

import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.FieldDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.MethodDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.assertj.core.api.HamcrestCondition;
import org.jqassistant.plugin.jmolecules.set.valueobject.AbstractValueObject;
import org.jqassistant.plugin.jmolecules.set.valueobject.ValueObject1;
import org.jqassistant.plugin.jmolecules.set.valueobject.ValueObject3;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.FieldDescriptorMatcher.fieldDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.MethodDescriptorMatcher.methodDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

public class MutableValueObjectIT extends AbstractJavaPluginIT {

    @Test
    public void testMutableFieldsInValueObjects() throws RuleException, NoSuchFieldException, NoSuchMethodException {
        scanClasses(ValueObject1.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:MutableValueObject");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject1.class)));
        assertThat((FieldDescriptor) rows.get(0).getColumns().get("MutableField").getValue()).is(new HamcrestCondition<>(fieldDescriptor(ValueObject1.class, "mutableValue")));
        assertThat((MethodDescriptor) rows.get(0).getColumns().get("Method").getValue()).is(new HamcrestCondition<>(methodDescriptor(ValueObject1.class, "setMutableValue", String.class)));
        store.commitTransaction();
    }

    @Test
    public void testMutableFieldsInAbstractValueObjects() throws RuleException, NoSuchFieldException, NoSuchMethodException {
        scanClasses(AbstractValueObject.class, ValueObject3.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:MutableValueObject");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("ValueObject").getValue()).is(new HamcrestCondition<>(typeDescriptor(ValueObject3.class)));
        assertThat((FieldDescriptor) rows.get(0).getColumns().get("MutableField").getValue()).is(new HamcrestCondition<>(fieldDescriptor(AbstractValueObject.class, "field")));
        assertThat((MethodDescriptor) rows.get(0).getColumns().get("Method").getValue()).is(new HamcrestCondition<>(methodDescriptor(AbstractValueObject.class, "setField", String.class)));
        store.commitTransaction();
    }

}
