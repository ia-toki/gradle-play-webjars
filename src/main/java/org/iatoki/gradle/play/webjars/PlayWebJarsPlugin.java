package org.iatoki.gradle.play.webjars;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.model.ModelMap;
import org.gradle.model.Mutate;
import org.gradle.model.Path;
import org.gradle.model.RuleSource;
import org.gradle.play.internal.PlayApplicationBinarySpecInternal;

public class PlayWebJarsPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {}

    static class Rules extends RuleSource {
        @Mutate
        void createWebJarsExtractTask(
                @Path("binaries") ModelMap<PlayApplicationBinarySpecInternal> binarySpecs,
                @Path("buildDir") File buildDir) {

            binarySpecs.all(binarySpec -> {
                String taskName = binarySpec.getTasks().taskName("extract", "webJars");
                binarySpec.getTasks().create(taskName, WebJarsExtract.class, webJarsExtract -> {
                    webJarsExtract.setDescription("Extract WebJars for " + binarySpec.getDisplayName() + ".");
                    webJarsExtract.setClasspath(binarySpec.getClasspath());

                    File generatedSourceDir = binarySpec.getNamingScheme().getOutputDirectory(buildDir, "src");
                    File outputDir = new File(generatedSourceDir, taskName);
                    File libDir = new File(outputDir, "lib");
                    webJarsExtract.setOutputDirectory(libDir);
                    binarySpec.getAssets().addAssetDir(outputDir);
                    binarySpec.getAssets().builtBy(webJarsExtract);
                });
            });
        }
    }
}
