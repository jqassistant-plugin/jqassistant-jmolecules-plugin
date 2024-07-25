package org.jqassistant.plugin.jmolecules.layered.constraints;

import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.assertj.core.api.HamcrestCondition;
import org.jqassistant.plugin.jmolecules.set.layer.application.Application1;
import org.jqassistant.plugin.jmolecules.set.layer.application.Application2;
import org.jqassistant.plugin.jmolecules.set.layer.domain.Domain1;
import org.jqassistant.plugin.jmolecules.set.layer.infrastructure.Infrastructure1;
import org.jqassistant.plugin.jmolecules.set.layer.interfaces.Interface1;
import org.jqassistant.plugin.jmolecules.set.layer.interfaces.Interface2;
import org.jqassistant.plugin.jmolecules.set.violation.layer1.Layer;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IllegalLayerDependencyIT extends AbstractJavaPluginIT {

    @Test
    public void testIllegalLayerDependencies() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Layer.class));
        Result<Constraint> result = validateConstraint("jmolecules-layered:IllegalLayerDependency");
        List<Row> rows = result.getRows();
        store.beginTransaction();
        assertEquals(6, rows.size());
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Application2.class)));
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Interface2.class)));

        assertThat((TypeDescriptor) rows.get(1).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Domain1.class)));
        assertThat((TypeDescriptor) rows.get(1).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Application1.class)));
        assertThat((TypeDescriptor) rows.get(2).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Domain1.class)));
        assertThat((TypeDescriptor) rows.get(2).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Interface2.class)));

        assertThat((TypeDescriptor) rows.get(3).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Infrastructure1.class)));
        assertThat((TypeDescriptor) rows.get(3).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Application2.class)));
        assertThat((TypeDescriptor) rows.get(4).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Infrastructure1.class)));
        assertThat((TypeDescriptor) rows.get(4).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Domain1.class)));
        assertThat((TypeDescriptor) rows.get(5).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Infrastructure1.class)));
        assertThat((TypeDescriptor) rows.get(5).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Interface1.class)));
        store.commitTransaction();
    }

}
