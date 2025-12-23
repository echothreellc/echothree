// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.cli.database;

import com.echothree.ui.cli.database.util.DatabaseDefinitionParser;
import java.io.IOException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class CustomEntityResolver implements EntityResolver {

    private static final String DATABASE_DEFINITION_DTD = "/DatabaseDefinition.dtd";

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws IOException {
        // If we don't handle this special case there, the parser will attempt to retrieve the
        // DTD from a file instead of a resource.
        if (systemId.contains(DATABASE_DEFINITION_DTD)) {
            return new InputSource(DatabaseDefinitionParser.class.getResource(DATABASE_DEFINITION_DTD).openStream());
        } else {
            return null;
        }
    }

}