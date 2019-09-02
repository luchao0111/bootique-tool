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

package io.bootique.tools.shell.template;

import java.nio.file.Path;

/**
 * @since 4.2
 */
public class BinaryTemplate extends Template {

    private final byte[] content;

    public BinaryTemplate(Path path, byte[] content) {
        super(path, "");
        this.content = content;
    }

    public BinaryTemplate withContent(byte[] newContent) {
        return new BinaryTemplate(getPath(), newContent);
    }

    public byte[] getBinaryContent() {
        return content;
    }
}
