import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.kotlin.mpp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser() }
    js { browser() }

    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)

                // Ktor
                implementation(libs.bundles.ktor)

                // Coil (Image Loading)
                implementation(libs.bundles.coil)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
            }
        }

        val jsWasmMain by creating {
            dependsOn(commonMain.get())
        }

        jsMain {
            dependsOn(jsWasmMain)
        }

        wasmJsMain {
            dependsOn(jsWasmMain)
        }
    }
}

buildConfig {
    useKotlinOutput { topLevelConstants = true }

    buildConfigField(
        "LAST_MODIFIED_DATE_TIME",
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(Date()),
    )
}

val wasmJsBrowserTest by tasks.existing(KotlinJsTest::class) {
    reports.junitXml.required.set(true)
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED)
    }
}
