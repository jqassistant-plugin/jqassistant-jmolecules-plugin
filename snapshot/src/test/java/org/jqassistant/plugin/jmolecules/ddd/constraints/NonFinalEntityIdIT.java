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
import org.jqassistant.plugin.jmolecules.set.entity.AbstractEntity;
import org.jqassistant.plugin.jmolecules.set.entity.Entity1;
import org.jqassistant.plugin.jmolecules.set.entity.Entity3;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.FieldDescriptorMatcher.fieldDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

public class NonFinalEntityIdIT extends AbstractJavaPluginIT {

    @Test
    public void testNonFinalIdInEntity() throws RuleException, NoSuchFieldException {
        scanClasses(Entity1.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:NonFinalEntityId");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("Identifiable").getValue()).is(new HamcrestCondition<>(typeDescriptor(Entity1.class)));
        assertThat((FieldDescriptor) rows.get(0).getColumns().get("MutableIdentity").getValue()).is(new HamcrestCondition<>(fieldDescriptor(Entity1.class, "id")));
        store.commitTransaction();
    }

    @Test
    public void testMutableIdInAbstractEntity() throws RuleException, NoSuchFieldException {
        scanClasses(AbstractEntity.class, Entity3.class);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:MutableEntityId");
        store.beginTransaction();
        List<Row> rows = result.getRows();
        assertThat(rows.size()).isEqualTo(1);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("Identifiable").getValue()).is(new HamcrestCondition<>(typeDescriptor(Entity3.class)));
        assertThat((FieldDescriptor) rows.get(0).getColumns().get("MutableIdentity").getValue()).is(new HamcrestCondition<>(fieldDescriptor(AbstractEntity.class, "mutableId")));
        store.commitTransaction();
    }

}
