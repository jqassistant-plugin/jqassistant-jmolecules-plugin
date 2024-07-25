package org.jqassistant.plugin.jmolecules.layered.constraints;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.jqassistant.plugin.jmolecules.set.violation.layer1.Layer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeInMultipleLayersIT extends AbstractJavaPluginIT {

    @Test
    public void testTypeInMultipleBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Layer.class));
        Result<Constraint> result = validateConstraint("jmolecules-layered:TypeInMultipleLayers");
        assertEquals(1, result.getRows().size());
        Row row = result.getRows().get(0);
        assertEquals("org.jqassistant.plugin.jmolecules.set.violation.layer1.Layer", row.getColumns().get("Type").getValue());
    }
}
