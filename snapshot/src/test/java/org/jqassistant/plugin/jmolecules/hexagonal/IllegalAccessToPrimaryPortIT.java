package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.illegalAccessToPrimaryPort.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class IllegalAccessToPrimaryPortIT extends AbstractJMoleculesPluginIT {

    @Test
    public void illegalAccessToPrimaryPort() throws RuleException {
        scanClassesAndPackages(PrimaryPort.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:IllegalAccessToPrimaryPort");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        store.beginTransaction();
        assertThat(constraintResult.getRows().size()).isEqualTo(3);
        List<TypeDescriptor> types = constraintResult.getRows().stream().map(Row::getColumns).map(r -> (TypeDescriptor) r.get("IllegalDependent").getValue()).collect(Collectors.toList());
        assertThat(types).is(matching(containsInAnyOrder(
                typeDescriptor(SecondaryAdapter.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(UnknownType.class))));
        store.commitTransaction();
    }

}
