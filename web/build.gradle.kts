import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.mpp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildconfig)
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "vocwebapp.js"
            }
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            target.set("es2015")
        }
        binaries.executable()
    }

    sourceSets {
        jsMain {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.bundles.voyager)
            }
        }
    }
}

val copyDistributions by tasks.registering {
    doLast {
        copy {
            val destinationDir = File("$rootDir/public")
            if (!destinationDir.exists()) {
                destinationDir.mkdir()
            }
            val distributions =
                File("${layout.buildDirectory.asFile.get().absoluteFile}/dist/js/productionExecutable/")
            from(distributions)
            into(destinationDir)
        }
    }
}

val jsBrowserDistribution by tasks.existing {
    finalizedBy(copyDistributions)
}
