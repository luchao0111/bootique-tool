/*
 *   Licensed to ObjectStyle LLC under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ObjectStyle LLC licenses
 *   this file to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 */

package io.bootique.tools.shell.template.processor;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.bootique.tools.shell.template.Properties;
import io.bootique.tools.shell.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JavaPackageProcessorTest {

    private JavaPackageProcessor processor;

    private Properties properties;

    @Before
    public void prepareProcessor() {
        processor = new JavaPackageProcessor();
        properties = Properties.builder()
                .with("java.package", "io.bootique.test")
                .build();
    }

    @Test
    public void processTemplate() {
        Template template = new Template(Paths.get("example", "MyClass.java"), "package example;");
        Template result = processor.process(template, properties);

        assertEquals("package io.bootique.test;", result.getContent());
        assertEquals(Paths.get("/io", "bootique", "test", "MyClass.java"), result.getPath());
    }

    @Test
    public void processContent() {
        String content = "package example.service;\n" +
                "import example.service.io.MyClass;" +
                "import example.service.MyClass;" +
                "public class JavaPackageProcessorTest {\n" +
                "    private JavaPackageProcessor processor;" +
                "}";

        String expected = "package io.bootique.test.service;\n" +
                "import io.bootique.test.service.io.MyClass;" +
                "import io.bootique.test.service.MyClass;" +
                "public class JavaPackageProcessorTest {\n" +
                "    private JavaPackageProcessor processor;" +
                "}";

        String processed = processor.processContent(new Template(Paths.get(""), content), properties);
        assertEquals(expected, processed);
    }

    @Test
    public void outputPathSimple() {
        Path path = Paths.get("tpl", "example", "MyClass.java");
        Path out = processor.outputPath(new Template(path, ""), properties);
        assertEquals(Paths.get("tpl", "io", "bootique", "test", "MyClass.java"), out);
    }

    @Test
    public void outputPathWithPackage() {
        Path path = Paths.get("tpl", "example", "service", "MyClass.java");
        Path out = processor.outputPath(new Template(path, ""), properties);
        assertEquals(Paths.get("tpl", "io", "bootique", "test", "service", "MyClass.java"), out);
    }

    @Test
    public void packageToPath() {
        assertEquals(Paths.get("io"), processor.packageToPath("io", "/"));
        assertEquals(Paths.get("io", "bootique"), processor.packageToPath("io.bootique", "/"));
        assertEquals(Paths.get("io", "bootique", "test"), processor.packageToPath("io.bootique.test", "/"));
    }
}