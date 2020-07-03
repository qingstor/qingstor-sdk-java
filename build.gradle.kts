plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.diffplug.gradle.spotless") version "3.27.2"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("io.freefair.lombok") version "5.0.0"
}

group = "com.yunify"
version = "2.3.4"

repositories {
    mavenCentral()
}

java {
    tasks.jar {
        manifest {
            attributes(
                    mapOf("Implementation-Title" to project.name,
                            "Implementation-Version" to project.version)
            )
        }
    }
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        val version = this.project.version.toString() + "-all-deps"
        archiveVersion.set(version)
    }
    javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as CoreJavadocOptions).addBooleanOption("-no-module-directories", true)
            (options as CoreJavadocOptions).addBooleanOption("html5", true)
        }
    }
    build {
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        // create<MavenPublication>("shadow") {
        //     project.shadow.component(this)
        //     artifactId = "qingstor.sdk.java"
        //     version = project.version.toString() + "-all-deps"
        // }

        create<MavenPublication>("maven") {
            artifactId = "qingstor.sdk.java"  // this should be replaced near future.

            from(components["java"])

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
        // local repo. For test usage.
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
    // sign(publishing.publications["shadow"])
}

val cucumberRuntime: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

// In this section you declare the dependencies for your production and test code
dependencies {
    compileOnly("org.projectlombok:lombok:1.18.12")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.10")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.10.4")

    implementation("org.json:json:20190722")
    implementation("com.squareup.okhttp3:okhttp:3.12.10")
    // it's library's consumer's responsibility to choose a log implementation(and set log_level.)
    implementation("org.slf4j:slf4j-api:1.7.30")
    testImplementation("io.cucumber:cucumber-java:5.5.0")
    testImplementation("io.cucumber:cucumber-junit:5.5.0")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")
}

task("cucumber") {
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
    java {
        // switch to AOSP style
        googleJavaFormat("1.7").aosp()
        // Add license to every java file.
        licenseHeaderFile(rootProject.file("spotless.license.java"))
    }
}
