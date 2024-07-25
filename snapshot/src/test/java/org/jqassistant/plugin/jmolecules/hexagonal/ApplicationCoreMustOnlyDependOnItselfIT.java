package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.applicationCoreMustOnlyDependOnItself.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ApplicationCoreMustOnlyDependOnItselfIT extends AbstractJMoleculesPluginIT {

    @Test
    public void applicationCoreMustOnlyDependOnItself() throws RuleException {
        scanClassesAndPackages(ApplicationCoreType.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:ApplicationCoreMustOnlyDependOnItself");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        store.beginTransaction();
        assertThat(constraintResult.getRows().size()).isEqualTo(7);
        List<TypeDescriptor> types = constraintResult.getRows().stream().map(Row::getColumns).map(r -> (TypeDescriptor) r.get("Dependency").getValue()).collect(Collectors.toList());
        assertThat(types).is(matching(containsInAnyOrder(
                typeDescriptor(Adapter.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryAdapter.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryAdapter.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(UnknownType.class))));
        store.commitTransaction();
    }

}
