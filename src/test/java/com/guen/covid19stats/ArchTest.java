package com.guen.covid19stats;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.guen.covid19stats");

        noClasses()
            .that()
                .resideInAnyPackage("com.guen.covid19stats.service..")
            .or()
                .resideInAnyPackage("com.guen.covid19stats.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.guen.covid19stats.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
