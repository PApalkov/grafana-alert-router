import ru.art.gradle.constants.DependencyConfiguration.EMBEDDED
import ru.art.gradle.constants.DependencyConfiguration.PROVIDED

val artVersion: String by project

plugins {
    `maven-publish`
    id("io.github.art.project") version "1.0.113"
}

apply(plugin = "io.github.art.project")
apply(plugin = "maven-publish")

repositories {
    mavenCentral()
    jcenter()
}

afterConfiguring {
    run {
        configurations.filter { configuration -> configuration.name == EMBEDDED.configuration || configuration.name == PROVIDED.configuration }
                .stream()
                .flatMap { configuration -> configuration.allDependencies.withType(ProjectDependency::class.java).stream() }
                .forEach { dependency ->
                    tasks.withType(JavaCompile::class.java).forEach { task -> task.dependsOn(":${dependency.name}:build") }
                }
    }
}

art {

    projectVersion {
        fromBranch()
    }


    mainClass("ru.grafana.alert.router.module.GrafanaAlertRouter")

    embeddedModules {
        configurationManagement()
        metrics()
        protocols {
            httpServer()
            httpCommunication()
        }
        db {
            sql()
        }
    }
    generator {
        packageName = "ru.grafana.alert.router"
    }

    idea()
    lombok()
    tests()
    dependencyRefreshing {
        refreshingRateInSeconds = 1
    }
    embeddedModules {
        useVersion(artVersion)
        exclude("application-protobuf-generated")
    }
    providedModules {
        useVersion(artVersion)
        exclude("application-protobuf-generated")
    }
    testModules {
        useVersion(artVersion)
        exclude("application-protobuf-generated")
    }
}

dependencies {
    embedded(module("org.apache.httpcomponents", "httpmime", "4.+"))
}