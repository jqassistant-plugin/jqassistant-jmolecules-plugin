package org.jqassistant.plugin.jmolecules.ddd;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.plugin.jmolecules.set.repository.Repository1;
import org.jqassistant.plugin.jmolecules.set.repository.Repository2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryIT extends AbstractJavaPluginIT {

    @Test
    public void repositoryType() throws RuleException {
        scanClasses(Repository1.class);
        assertThat(applyConcept("jmolecules-ddd:RepositoryType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:JMolecules:DDD:Repository) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Repository1");
        store.commitTransaction();
    }

    @Test
    public void repositoryExtend() throws RuleException {
        scanClasses(Repository2.class);
        assertThat(applyConcept("jmolecules-ddd:RepositoryExtend").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:JMolecules:DDD:Repository) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Repository2");
        store.commitTransaction();
    }

}
