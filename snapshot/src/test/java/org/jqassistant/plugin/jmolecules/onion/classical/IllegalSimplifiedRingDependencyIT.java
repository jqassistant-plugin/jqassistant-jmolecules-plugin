package org.jqassistant.plugin.jmolecules.onion.classical;

import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.assertj.core.api.HamcrestCondition;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.SimplifiedRingApp;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.application.Application1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.domain.Domain1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.infrastructure.Infrastructure1;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IllegalSimplifiedRingDependencyIT extends AbstractJavaPluginIT {

    @Test
    public void testIllegalRingDependencies() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(SimplifiedRingApp.class));
        Result<Constraint> result = validateConstraint("jmolecules-onion-simplified:IllegalRingDependency");
        List<Row> rows = result.getRows();
        store.beginTransaction();
        assertEquals(3, rows.size());
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Application1.class)));
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Infrastructure1.class)));

        assertThat((TypeDescriptor) rows.get(1).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Domain1.class)));
        assertThat((TypeDescriptor) rows.get(1).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Application1.class)));
        assertThat((TypeDescriptor) rows.get(2).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Domain1.class)));
        assertThat((TypeDescriptor) rows.get(2).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Infrastructure1.class)));
        store.commitTransaction();
    }

}
