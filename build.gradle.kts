// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  id("com.google.dagger.hilt.android") version "2.51.1" apply false
  id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
  id("com.diffplug.spotless") version "7.2.1"
  id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

subprojects {
  apply(plugin = "com.diffplug.spotless")
  configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      targetExclude("${rootProject.layout.buildDirectory}/**/*.kt")

      ktlint()
        .editorConfigOverride(
          mapOf(
            "ktlint_standard_filename" to "disabled", // cho phép tên file tự do hơn
            "ij_kotlin_imports_layout" to "*", // optimize imports
            "ij_kotlin_allow_trailing_comma" to "true",
            "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
          )
        )
      licenseHeaderFile(rootProject.file("spotless/license-header.kt"))
    }

    kotlinGradle {
      target("*.gradle.kts")
      ktlint()
    }
  }

  // Tự động chạy spotlessApply trước khi build
  afterEvaluate { tasks.named("preBuild") { dependsOn("spotlessApply") } }
}

spotless {
  kotlinGradle {
    target("*.gradle.kts") // chỉ root gradle scripts
    ktfmt().googleStyle()
  }
  yaml {
    target("*.yml", ".github/**/*.yml")
    jackson()
  }
}
