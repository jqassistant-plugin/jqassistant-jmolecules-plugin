package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.portMustBePartOfApplicationCore.PortOutsideApplicationCore;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.contains;

public class PortMustBePartOfApplicationCoreIT extends AbstractJMoleculesPluginIT {

    @Test
    public void portMustBePartOfApplicationCore() throws RuleException {
        scanClassesAndPackages(PortOutsideApplicationCore.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:PortMustBePartOfApplicationCore");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        store.beginTransaction();
        assertThat(constraintResult.getRows().size()).isEqualTo(1);
        List<TypeDescriptor> types = constraintResult.getRows().stream().map(Row::getColumns).map(r -> (TypeDescriptor) r.get("PortType").getValue()).collect(Collectors.toList());
        assertThat(types).is(matching(contains(
                typeDescriptor(PortOutsideApplicationCore.class))));
        store.commitTransaction();
    }

}
