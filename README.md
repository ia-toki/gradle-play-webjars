# gradle-play-webjars

[![GitHub Release](https://img.shields.io/github/release/ia-toki/gradle-play-webjars.svg)](https://github.com/ia-toki/gradle-play-webjars)

Gradle plugin for extracting WebJars in Play framework projects.

## Usage

1. Apply the [Play framework plugin](https://docs.gradle.org/current/userguide/play_plugin.html).
1. Apply this plugin by adding the following to your `build.gradle`:

   ```
   buildscript {
       repositories {
           jcenter()
       }
 
       dependencies {
           classpath 'org.iatoki:gradle-play-webjars:0.1.0'
       }
   }
 
   apply plugin: 'org.iatoki.play-webjars'
   ```
   
## Features

This plugin will add a new `:extractPlayBinaryWebJars` task, which extracts all WebJars found in the classpath to the `$buildDir/src/play/binary/extractPlayBinaryWebJars` directory. All WebJars contents will then be included in the resulting Play assets jar (produced by the `:createPlayBinaryAssetsJar` task), under the `lib/` directory, similar to what is described in the [Play framework public assets documentation](https://www.playframework.com/documentation/2.5.x/AssetsOverview#WebJars).
