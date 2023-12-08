package pl.amazingcode.threadscollider.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Architecture_Scenarios {

  private static final String ROOT_PACKAGE = "pl.amazingcode.threadscollider";
  private static final String SINGLE_PACKAGE = "pl.amazingcode.threadscollider.single";
  private static final String MULTI_PACKAGE = "pl.amazingcode.threadscollider.multi";

  private final JavaClasses classes =
      new ClassFileImporter()
          .withImportOption(new ImportOption.DoNotIncludeTests())
          .importPackages(ROOT_PACKAGE);

  @Test
  void Independent_single_package() {

    noClasses()
        .that()
        .resideInAPackage(SINGLE_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(MULTI_PACKAGE)
        .check(classes);
  }

  @Test
  void Independent_multi_package() {

    noClasses()
        .that()
        .resideInAPackage(MULTI_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(SINGLE_PACKAGE)
        .check(classes);
  }
}
