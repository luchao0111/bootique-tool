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

package io.bootique.tools.shell;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import io.bootique.Bootique;

public class Application {

    public static void main(String[] args) {
        // TODO: can we detect this in native mode?
        if(System.getProperty("sun.arch.data.model") == null) {
            System.setProperty("sun.arch.data.model", "64");
        }

        // turn JLine logging off
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.OFF);

        Bootique
                .app(args)
                .autoLoadModules()
                .exec()
                .exit();
    }
}