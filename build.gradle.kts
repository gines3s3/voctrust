import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    alias(libs.plugins.kotlin.mpp) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.buildconfig) apply false

    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
}

tasks.withType<DependencyUpdatesTask> {
    fun isUnstable(version: String): Boolean {
        val upperCaseVersion = version.uppercase()
        // A version is considered unstable if it contains any of these keywords.
        return listOf("ALPHA", "BETA", "RC", "SNAPSHOT", "PREVIEW", "MILESTONE", "M", "EAP").any {
            upperCaseVersion.contains(it)
        }
    }

    rejectVersionIf {
        // Reject the new version if it's considered unstable.
        isUnstable(candidate.version)
    }
}

val wasmJsBrowserTestReport by tasks.registering(TestReport::class) {
    destinationDirectory.set(layout.buildDirectory.file("reports/wasmJsBrowserTest").get().asFile)
    subprojects.forEach {
        val wasmJsBrowserTest: KotlinJsTest = it.tasks
            .findByName("wasmJsBrowserTest") as? KotlinJsTest
            ?: return@forEach

        testResults.from(wasmJsBrowserTest.binaryResultsDirectory)
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs the Lint check whole project at once."

    basePath = rootProject.projectDir.absolutePath
    parallel = true
    autoCorrect = true
    setSource(files(rootProject.projectDir))
    config.setFrom(listOf(rootProject.files("config/detekt.yml")))

    include("**/*.kt", "**/**.kts")
    exclude("**/resources/**", "**/composeResources/**", "**/build/**")

    reports {
        html {
            required.set(true)
            outputLocation.set(rootProject.file("lint-reports/kotlin/detekt.html"))
        }

        sarif {
            required.set(true)
            outputLocation.set(rootProject.file("lint-reports/kotlin/detekt.sarif"))
        }

        txt {
            required.set(false)
        }
    }
}
