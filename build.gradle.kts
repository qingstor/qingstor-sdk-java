plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.diffplug.spotless") version "5.1.1"
}

group = "com.yunify"
version = "2.4.0"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks {
    jar {
        manifest {
            attributes(
                    mapOf("Implementation-Title" to project.name,
                            "Implementation-Version" to project.version)
            )
        }
    }
    javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as CoreJavadocOptions).addBooleanOption("-no-module-directories", true)
            (options as CoreJavadocOptions).addBooleanOption("html5", true)
        }
    }
    val fatJar = register<Jar>("fatJar") {
        archiveClassifier.set("all-deps")

        from(sourceSets.main.get().output)

        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
    build {
        dependsOn(fatJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "qingstor.sdk.java"  // this should be replaced near future.

            pom {
                name.set(project.name)
                description.set("The official QingStor SDK for the Java programming language.")
                url.set("https://www.qingcloud.com/products/qingstor/")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("yunify")
                        name.set("yunify")
                        email.set("sdk_group@yunify.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:yunify/qingstor-sdk-java.git")
                    developerConnection.set("scm:git:git@github.com:yunify/qingstor-sdk-java.git")
                    url.set("https://github.com/qingstor/qingstor-sdk-java")
                }
            }
        }
    }
    repositories {
        // local repo for test usage, redirect output to this for verification before upload.
        // or use ./gradlew publishToMavenLocal
        // maven {
        //     name = "myRepo"
        //     url = uri("file://${buildDir}/repo")
        // }
        maven {
            name = "mavencentral"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("SONATYPE_NEXUS_USERNAME")
                password = System.getenv("SONATYPE_NEXUS_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}

val cucumberRuntime: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

// In this section you declare the dependencies for your production and test code
dependencies {
    api("com.squareup.okhttp3:okhttp:3.12.12")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.10")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.10.5")
    implementation("org.json:json:20200518")
    // it's library's consumer's responsibility to choose a log implementation(and set log_level.)
    implementation("org.slf4j:slf4j-api:1.7.30")
    testImplementation("io.cucumber:cucumber-java:6.2.2")
    testImplementation("io.cucumber:cucumber-junit:6.2.2")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.register("cucumber") {
    dependsOn(tasks.assemble, tasks.compileTestJava)
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath(cucumberRuntime, sourceSets.main.get().output, sourceSets.test.get().output)
            args = listOf("--plugin", "pretty", "--glue", "integration.cucumber", "tests/features")
        }
    }
}

spotless {
    format("misc") {
        // define the files to apply `misc` to
        target("*.gradle*", "*.md", ".gitignore")
        // define the steps to apply to those files
        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()
    }
    java {
        // switch to AOSP style
        googleJavaFormat("1.7").aosp()
        // Add license to every java file.
        licenseHeaderFile(rootProject.file("spotless.license.java"))
    }
}
