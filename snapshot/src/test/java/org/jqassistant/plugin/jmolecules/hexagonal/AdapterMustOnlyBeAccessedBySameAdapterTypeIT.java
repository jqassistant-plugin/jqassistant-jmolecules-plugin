package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.adapterMustOnlyBeAccessedBySameAdapterType.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.*;

public class AdapterMustOnlyBeAccessedBySameAdapterTypeIT extends AbstractJMoleculesPluginIT {

    @Test
    public void adapterMustOnlyBeAccessedBySameAdapterType() throws RuleException {
        scanClassesAndPackages(Adapter.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:AdapterMustOnlyBeAccessedBySameAdapterType");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        store.beginTransaction();
        assertThat(constraintResult.getRows().size()).isEqualTo(17);
        Map<String, List<TypeDescriptor>> types = constraintResult.getRows().stream().map(Row::getColumns).collect(Collectors.groupingBy(r -> ((TypeDescriptor) r.get("Adapter").getValue()).getName(), Collectors.mapping(r -> (TypeDescriptor) r.get("IllegalDependent").getValue(), Collectors.toList())));
        assertThat(types.size()).isEqualTo(3);

        assertThat(types.get("Adapter")).is(matching(containsInAnyOrder(
                typeDescriptor(ApplicationCoreType.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(UnknownType.class))));

        assertThat(types.get("PrimaryAdapter")).is(matching(containsInAnyOrder(
                typeDescriptor(ApplicationCoreType.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(SecondaryAdapter.class),
                typeDescriptor(UnknownType.class))));

        assertThat(types.get("SecondaryAdapter")).is(matching(containsInAnyOrder(
                typeDescriptor(ApplicationCoreType.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(PrimaryAdapter.class),
                typeDescriptor(UnknownType.class))));
        store.commitTransaction();
    }

    @Test
    public void adapterMustOnlyBeAccessedBySameAdapter() throws RuleException {
        scanClassesAndPackages(Adapter.class);
        Result<Constraint> constraintResult = validateConstraint("jmolecules-hexagonal:AdapterMustOnlyBeAccessedBySameAdapter");
        assertThat(constraintResult.getStatus()).isEqualTo(Result.Status.FAILURE);
        store.beginTransaction();
        assertThat(constraintResult.getRows().size()).isEqualTo(21);
        Map<String, List<TypeDescriptor>> types = constraintResult.getRows().stream().map(Row::getColumns).collect(Collectors.groupingBy(r -> ((TypeDescriptor) r.get("Adapter").getValue()).getName(), Collectors.mapping(r -> (TypeDescriptor) r.get("IllegalDependent").getValue(), Collectors.toList())));
        assertThat(types.size()).isEqualTo(3);

        assertThat(types.get("Adapter")).is(matching(containsInAnyOrder(
                typeDescriptor(ApplicationCoreType.class),
                typeDescriptor(PrimaryAdapter.class),
                typeDescriptor(SecondaryAdapter.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(UnknownType.class))));

        assertThat(types.get("PrimaryAdapter")).is(matching(containsInAnyOrder(
                typeDescriptor(ApplicationCoreType.class),
                typeDescriptor(Adapter.class),
                typeDescriptor(SecondaryAdapter.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(UnknownType.class))));

        assertThat(types.get("SecondaryAdapter")).is(matching(containsInAnyOrder(
                typeDescriptor(ApplicationCoreType.class),
                typeDescriptor(Adapter.class),
                typeDescriptor(PrimaryAdapter.class),
                typeDescriptor(Port.class),
                typeDescriptor(PrimaryPort.class),
                typeDescriptor(SecondaryPort.class),
                typeDescriptor(UnknownType.class))));
        store.commitTransaction();
    }

}