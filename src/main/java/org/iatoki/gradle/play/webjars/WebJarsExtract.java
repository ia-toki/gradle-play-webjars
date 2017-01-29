package org.iatoki.gradle.play.webjars;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GFileUtils;
import org.webjars.WebJarExtractor;

public class WebJarsExtract extends DefaultTask {
    private File outputDirectory;
    private FileCollection classpath;

    @OutputDirectory
    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @InputFiles
    public FileCollection getClasspath() {
        return classpath;
    }

    public void setClasspath(FileCollection classpath) {
        this.classpath = classpath;
    }

    @TaskAction
    public void doExtract() throws IOException {
        GFileUtils.forceDelete(outputDirectory);

        List<URL> urls = new ArrayList<>();
        classpath.getFiles().forEach(file -> {
            try {
                urls.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
        WebJarExtractor extractor = new WebJarExtractor(classLoader);

        extractor.extractAllWebJarsTo(outputDirectory);
        extractor.extractAllNodeModulesTo(outputDirectory);

        setDidWork(true);
    }
}
