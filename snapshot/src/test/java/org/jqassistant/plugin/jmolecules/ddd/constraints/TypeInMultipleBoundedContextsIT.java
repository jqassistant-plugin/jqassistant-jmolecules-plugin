package org.jqassistant.plugin.jmolecules.ddd.constraints;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.plugin.jmolecules.set.violation.bc1.bc3.MultipleBoundedContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeInMultipleBoundedContextsIT extends AbstractJavaPluginIT {

    @Test
    public void testTypeInMultipleBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(MultipleBoundedContext.class));
        assertThat(applyConcept("jmolecules-ddd:BoundedContextPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        Result<Constraint> result = validateConstraint("jmolecules-ddd:TypeInMultipleBoundedContexts");
        assertEquals(2, result.getRows().size());
        List<String> types = result.getRows().stream().map(r -> (String) r.getColumns().get("Type").getValue()).collect(Collectors.toList());
        assertTrue(types.contains("org.jqassistant.plugin.jmolecules.set.violation.bc1.bc3.MultipleBoundedContext"));
        assertTrue(types.contains("org.jqassistant.plugin.jmolecules.set.violation.bc1.bc3.package-info"));
    }
}
