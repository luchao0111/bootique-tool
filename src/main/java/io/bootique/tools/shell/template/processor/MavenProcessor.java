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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import io.bootique.tools.shell.template.Properties;
import io.bootique.tools.shell.template.TemplateException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class MavenProcessor extends XMLTemplateProcessor {

    @Override
    protected Document processDocument(Document document, Properties properties) {
        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            Node artifactId = (Node)xpath.evaluate("/project/artifactId", document, XPathConstants.NODE);
            artifactId.setTextContent(properties.get("project.name"));

            Node groupId = (Node)xpath.evaluate("/project/groupId", document, XPathConstants.NODE);
            String groupIdText = properties.get("java.package");
            if(groupIdText == null || groupIdText.trim().isEmpty()) {
                groupId.setTextContent("example");
            } else {
                groupId.setTextContent(groupIdText);
            }

            Node version = (Node)xpath.evaluate("/project/version", document, XPathConstants.NODE);
            version.setTextContent(properties.get("project.version"));

            Node mainClass = (Node)xpath.evaluate("/project/properties/main.class", document, XPathConstants.NODE);
            String javaPackage = properties.get("java.package");
            if(javaPackage != null && !javaPackage.isEmpty()) {
                javaPackage += '.';
            }
            mainClass.setTextContent(javaPackage + "Application");

            Node bqVersion = (Node)xpath.evaluate("/project/properties/bootique.version", document, XPathConstants.NODE);
            bqVersion.setTextContent(properties.get("bq.version"));

            Node source = (Node)xpath.evaluate("/project/properties/maven.compiler.source", document, XPathConstants.NODE);
            source.setTextContent(properties.get("java.version"));

            Node target = (Node)xpath.evaluate("/project/properties/maven.compiler.target", document, XPathConstants.NODE);
            target.setTextContent(properties.get("java.version"));
        } catch (XPathExpressionException ex) {
            throw new TemplateException("Unable to modify xml, is template a proper maven xml?", ex);
        }

        return document;
    }
}
