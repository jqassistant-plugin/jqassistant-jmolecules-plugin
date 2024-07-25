package org.jqassistant.plugin.jmolecules.onion.classical;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.jqassistant.plugin.jmolecules.set.violation.ring1.ClassicalRing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeInMultipleRingsIT extends AbstractJavaPluginIT {

    @Test
    public void testTypeInMultipleBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(ClassicalRing.class));
        Result<Constraint> result = validateConstraint("jmolecules-onion-classical:TypeInMultipleRings");
        assertEquals(1, result.getRows().size());
        Row row = result.getRows().get(0);
        assertEquals("org.jqassistant.plugin.jmolecules.set.violation.ring1.ClassicalRing", row.getColumns().get("Type").getValue());
    }
}
