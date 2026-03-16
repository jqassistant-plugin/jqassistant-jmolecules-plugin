package org.jqassistant.plugin.jmolecules.stereotype;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.report.api.model.Row;
import com.buschmais.jqassistant.core.rule.api.model.Concept;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.api.model.URIDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;

import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeDefinitionDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeGroupDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeMetadataFileDescriptor;
import org.jqassistant.plugin.jmolecules.set.stereotype.ATestStereotype;
import org.jqassistant.plugin.jmolecules.set.stereotype.ITestStereotype;
import org.jqassistant.plugin.jmolecules.set.stereotype.named.NamedPackageType;
import org.jqassistant.plugin.jmolecules.set.stereotype.type.AssignableFromType;
import org.jqassistant.plugin.jmolecules.set.stereotype.type.NamedAnnotatedByType;
import org.jqassistant.plugin.jmolecules.set.stereotype.type.UnnamedAnnotatedByType;
import org.jqassistant.plugin.jmolecules.set.stereotype.unnamed.UnnamedPackageType;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.assertj.TypeDescriptorCondition.typeDescriptor;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor.Type.ANNOTATED_BY;
import static org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor.Type.ASSIGNABLE_FROM;

public class StereotypeIT extends AbstractJMoleculesPluginIT {

    public static final String TEST_STEREOTYPE = "test.Stereotype";

    @Test
    void metadataFile() {
        File file = new File(getClassesDirectory(StereotypeIT.class), "/stereotypes/jmolecules-stereotypes.json");

        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), DefaultScope.NONE);

        store.requireTransaction(() -> {
            assertThat(descriptor).isNotNull();
            assertThat(descriptor).isInstanceOf(StereotypeMetadataFileDescriptor.class);
            StereotypeMetadataFileDescriptor stereotypeMetadataFileDescriptor = (StereotypeMetadataFileDescriptor) descriptor;

            List<StereotypeDefinitionDescriptor> stereotypes = stereotypeMetadataFileDescriptor.getDeclaresStereotypes();
            assertThat(stereotypes.size()).isEqualTo(1);
            StereotypeDefinitionDescriptor stereotypeDefinitionDescriptor = stereotypes.get(0);
            assertThat(stereotypeDefinitionDescriptor.getName()).isEqualTo(TEST_STEREOTYPE);
            assertThat(stereotypeDefinitionDescriptor.getPriority()).isEqualTo(30);
            List<StereotypeAssignmentDescriptor> stereotypeAssignments = stereotypeDefinitionDescriptor.getStereotypeAssignments();
            assertThat(stereotypeAssignments.size()).isEqualTo(2);
            assertThat(stereotypeAssignments).satisfiesExactlyInAnyOrder(assignableFromAssignment -> {
                assertThat(assignableFromAssignment.getValue()).isEqualTo(ITestStereotype.class.getName());
                assertThat(assignableFromAssignment.getType()).isEqualTo(ASSIGNABLE_FROM);
            }, annotatedByAssignment -> {
                assertThat(annotatedByAssignment.getValue()).isEqualTo(ATestStereotype.class.getName());
                assertThat(annotatedByAssignment.getType()).isEqualTo(ANNOTATED_BY);
            });

            List<StereotypeGroupDescriptor> groups = stereotypeMetadataFileDescriptor.getDeclaresStereotypeGroups();
            assertThat(groups.size()).isEqualTo(1);
            StereotypeGroupDescriptor stereotypeGroupDescriptor = groups.get(0);
            assertThat(stereotypeGroupDescriptor.getName()).isEqualTo("test");
            assertThat(stereotypeGroupDescriptor.getDisplayName()).isEqualTo("Test");
            assertThat(stereotypeGroupDescriptor.getPriority()).isEqualTo(100);
            assertThat(stereotypeGroupDescriptor.getType()).isEqualTo("business");
            assertThat(stereotypeGroupDescriptor.getContainsStereotypes()).containsExactly(stereotypeDefinitionDescriptor);
        });
    }

    @Test
    void pluginMetadataFiles() throws URISyntaxException {
        URI uri = new URI("jmolecules:stereotypes");

        getScanner().scan(uri, uri.toString(), DefaultScope.NONE);

        store.requireTransaction(() -> {
            List<StereotypeMetadataFileDescriptor> stereotypesFiles = query(
                    "MATCH (metadata:Json:JMolecules:Stereotype:Metadata{valid:true}) RETURN metadata").getColumn("metadata");
            assertThat(stereotypesFiles).allSatisfy(stereotypesFile -> {
                assertThat(stereotypesFile.getFileName()).startsWith("/")
                        .endsWith("META-INF/jmolecules-stereotypes.json");
                assertThat(stereotypesFile).isInstanceOf(URIDescriptor.class)
                        .satisfies(uriDescriptor -> {
                            assertThat(((URIDescriptor) uriDescriptor).getUri()).startsWith("jar:file:/")
                                    .endsWith("META-INF/jmolecules-stereotypes.json");
                        });
            });
            List<StereotypeGroupDescriptor> groups = query(
                    "MATCH (:Json:JMolecules:Stereotype:Metadata)-[:DEFINES_STEREOTYPE_GROUP]->(group:JMolecules:Stereotype:Group) RETURN group").getColumn(
                    "group");
            assertThat(groups).anySatisfy(ddd -> {
                assertThat(ddd.getName()).isEqualTo("ddd");
                assertThat(ddd.getDisplayName()).isEqualTo("Domain-Driven Design");
            });
        });
    }

    @Test
    void stereotypeInstance() throws RuleException {
        scanClassesAndPackages(ATestStereotype.class);
        File file = new File(getClassesDirectory(StereotypeIT.class), "/stereotypes/jmolecules-stereotypes.json");
        getScanner().scan(file, file.getAbsolutePath(), DefaultScope.NONE);

        Result<Concept> result = applyConcept("jmolecules-stereotype:StereotypeInstance");

        store.requireTransaction(() -> {
            Map<String, List<Row>> rowsByInstance = result.getRows()
                    .stream()
                    .collect(groupingBy(row -> row.getColumns()
                            .get("StereotypeInstance")
                            .getLabel()));

            verifyInstance(rowsByInstance, AssignableFromType.class.getName(), AssignableFromType.class, AssignableFromType.Inner.class);
            verifyInstance(rowsByInstance, UnnamedAnnotatedByType.class.getName(), UnnamedAnnotatedByType.class, UnnamedAnnotatedByType.Inner.class);
            verifyInstance(rowsByInstance, "NamedAnnotatedByType", NamedAnnotatedByType.class, NamedAnnotatedByType.Inner.class);
            verifyInstance(rowsByInstance, UnnamedPackageType.class.getPackageName(), UnnamedPackageType.class, UnnamedPackageType.Inner.class);
            verifyInstance(rowsByInstance, "NamedPackage", NamedPackageType.class, NamedPackageType.Inner.class);

        });
    }

    private static void verifyInstance(Map<String, List<Row>> rowsByInstance, String instanceName, Class<?>... expectedTypes) {
        List<Row> rows = rowsByInstance.get(instanceName);
        assertThat(rows).describedAs("Rows for instance: " + instanceName)
                .isNotNull();
        assertThat(rows).allSatisfy(row -> assertThat(row.getColumns()
                .get("StereotypeDefinition")
                .getLabel()).isEqualTo(TEST_STEREOTYPE));
        List<TypeDescriptor> types = rows.stream()
                .map(row -> (TypeDescriptor) row.getColumns()
                        .get("Type")
                        .getValue())
                .collect(toList());
        assertThat(types).hasSize(expectedTypes.length);
        for (Class<?> expectedType : expectedTypes) {
            assertThat(types).haveExactly(1, typeDescriptor(expectedType));
        }
    }
}
