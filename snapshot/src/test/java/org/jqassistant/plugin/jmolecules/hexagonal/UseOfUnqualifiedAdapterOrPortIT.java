package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.useOfUnqualifiedAdapterOrPort.Adapter;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.useOfUnqualifiedAdapterOrPort.Port;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class UseOfUnqualifiedAdapterOrPortIT extends AbstractJMoleculesPluginIT {

    @Test
    public void useOfUnqualifiedAdapterOrPort() throws RuleException {
        scanClassesAndPackages(Adapter.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:UseOfUnqualifiedAdapterOrPort");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        store.beginTransaction();
        assertThat(constraintResult.getRows().size()).isEqualTo(2);
        List<TypeDescriptor> types = constraintResult.getRows().stream().map(Row::getColumns).map(r -> (TypeDescriptor) r.get("ImplementingType").getValue()).collect(Collectors.toList());
        assertThat(types).is(matching(containsInAnyOrder(
                typeDescriptor(Adapter.class),
                typeDescriptor(Port.class))));
        store.commitTransaction();
    }

}
