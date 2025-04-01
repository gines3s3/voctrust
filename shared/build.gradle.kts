import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import java.text.SimpleDateFormat
import java.util.*

plugins {
    alias(libs.plugins.kotlin.mpp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildconfig)
    kotlin("plugin.serialization") version "2.1.10"
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
                implementation("io.ktor:ktor-client-core:3.1.0")
                implementation("io.ktor:ktor-client-cio:3.1.0")
                implementation("io.ktor:ktor-client-content-negotiation:3.1.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.0")

                // Coil (Image Loading)
                implementation("io.coil-kt.coil3:coil-compose:3.1.0")
                implementation("io.coil-kt.coil3:coil-network-ktor3:3.1.0")

                // Cloudinary URL generator
                implementation("com.cloudinary:kotlin-url-gen:1.7.0")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // Ensure this line is present

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
