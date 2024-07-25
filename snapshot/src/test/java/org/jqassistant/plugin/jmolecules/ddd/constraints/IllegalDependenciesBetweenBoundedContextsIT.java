package org.jqassistant.plugin.jmolecules.ddd.constraints;

import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.assertj.core.api.HamcrestCondition;
import org.jqassistant.plugin.jmolecules.set.bc.App;
import org.jqassistant.plugin.jmolecules.set.bc.bc1.Product;
import org.jqassistant.plugin.jmolecules.set.bc.bc2.OrderService;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

public class IllegalDependenciesBetweenBoundedContextsIT extends AbstractJavaPluginIT {

    @Test
    public void testIllegalDependenciesBetweenBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(App.class));
        Result<Constraint> result = validateConstraint("jmolecules-ddd:IllegalDependenciesBetweenBoundedContexts");
        List<Row> rows = result.getRows();
        store.beginTransaction();
        assertThat(rows.size()).isEqualTo(1);
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("SourceType").getValue()).is(new HamcrestCondition<>(typeDescriptor(OrderService.class)));
        assertThat((TypeDescriptor) rows.get(0).getColumns().get("TargetType").getValue()).is(new HamcrestCondition<>(typeDescriptor(Product.class)));
        store.commitTransaction();
    }

    @Test
    public void testNoIllegalDependenciesBetweenBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(App.class));
        applyConcept("jmolecules-ddd:BoundedContextPackage");
        query("MATCH (b1:BoundedContext{name: 'order'}), (b2:BoundedContext{name: 'catalog'}) MERGE (b1)-[:DEFINES_DEPENDENCY]->(b2)");
        Result<Constraint> result = validateConstraint("jmolecules-ddd:IllegalDependenciesBetweenBoundedContexts");
        assertThat(result.getStatus()).isEqualTo(Result.Status.SUCCESS);
    }

}
