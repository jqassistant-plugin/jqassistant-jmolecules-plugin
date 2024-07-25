package org.jqassistant.plugin.jmolecules.ddd.constraints;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.module1.module3.MultipleModule;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeInMultipleModulesIT extends AbstractJavaPluginIT {

    @Test
    public void testTypeInMultipleBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(MultipleModule.class));
        assertThat(applyConcept("jmolecules-ddd:ModulePackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:TypeInMultipleModules");
        assertEquals(2, result.getRows().size());
        List<String> types = result.getRows().stream().map(r -> (String) r.getColumns().get("Type").getValue()).collect(Collectors.toList());
        assertTrue(types.contains("org.jqassistant.plugin.jmolecules.set.violation.module1.module3.MultipleModule"));
        assertTrue(types.contains("org.jqassistant.plugin.jmolecules.set.violation.module1.module3.package-info"));
    }
}
