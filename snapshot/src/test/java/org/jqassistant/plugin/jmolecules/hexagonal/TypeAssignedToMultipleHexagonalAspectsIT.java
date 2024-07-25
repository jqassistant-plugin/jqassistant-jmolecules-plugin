package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.typeAssignedToMultipleHexagonalAspects.AdapterAndPort;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.typeAssignedToMultipleHexagonalAspects.PrimaryAndSecondaryPort;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.typeAssignedToMultipleHexagonalAspects.adapter1.Adapter2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class TypeAssignedToMultipleHexagonalAspectsIT extends AbstractJMoleculesPluginIT {

    @Test
    public void typeAssignedToMultipleHexagonalAspects() throws RuleException {
        scanClassesAndPackages(AdapterAndPort.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:TypeAssignedToMultipleHexagonalAspects");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        assertThat(constraintResult.getRows().size()).isEqualTo(3);
        store.beginTransaction();
        List<TypeDescriptor> types = constraintResult.getRows().stream().map(Row::getColumns).map(r -> (TypeDescriptor) r.get("Type").getValue()).collect(Collectors.toList());
        assertThat(types).is(matching(containsInAnyOrder(
                typeDescriptor(AdapterAndPort.class),
                typeDescriptor(Adapter2.class),
                typeDescriptor(PrimaryAndSecondaryPort.class))));
        store.commitTransaction();
    }

}
