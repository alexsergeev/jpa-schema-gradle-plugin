/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

plugins {
  id("java-library")
  id("com.gradle.plugin-publish") version "0.10.1"
  id("dev.gradleplugins.kotlin-gradle-plugin") version "0.0.21"
  kotlin("jvm") version embeddedKotlinVersion
}

repositories {
  mavenCentral()
}

dependencies {
  // gradle
  api(gradleApi())
  implementation(localGroovy())
  testImplementation(gradleTestKit())
  // kotlin
  implementation(kotlin("stdlib-jdk8"))
  // jaxb (removed from java 9+)
  api("javax.xml.bind:jaxb-api:2.3.0")
  // jpa
  api("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
  functionalTestApi("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
  // test
  testImplementation("junit:junit:4.12")
  testImplementation("org.hamcrest:hamcrest-all:1.3")
  functionalTestImplementation("org.hamcrest:hamcrest-all:1.3")
  // extra dependencies for test
  functionalTestRuntimeOnly("com.h2database:h2:1.4.200")
  functionalTestRuntimeOnly(fileTree("dir" to "lib", "include" to listOf("*.jar")))
  // lombok
  functionalTestCompileOnly("org.projectlombok:lombok:1.18.12")
}

tasks.test {
  testLogging.showStandardStreams = true
}

gradlePlugin {
  plugins {
    create("generateSchema") {
      id = "io.github.divinespear.jpa-schema-generate"
      implementationClass = "io.github.divinespear.plugin.JpaSchemaGenerationPlugin"
    }
  }
}

pluginBundle {
  website = "https://divinespear.github.io/jpa-schema-gradle-plugin"
  vcsUrl = "https://github.com/divinespear/jpa-schema-gradle-plugin"
  description = "Gradle plugin for generate database schema or DDL scripts from JPA entities"

  (plugins) {
    "generateSchema" {
      displayName = "JPA 2.1 Schema Generation Plugin for Gradle"
      tags = listOf("jpa", "schema", "schemagen", "hibernate", "eclipselink", "generate")
    }
  }
}
