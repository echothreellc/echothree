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

package com.echothree.ui.cli.database.util;

import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.javagen.CommonJavaGenerator;
import com.echothree.ui.cli.database.util.javagen.EntityJavaGenerator;
import com.echothree.ui.cli.database.util.javagen.FactoryJavaGenerator;
import com.echothree.ui.cli.database.util.javagen.PrimaryKeyJavaGenerator;
import com.echothree.ui.cli.database.util.javagen.ValueJavaGenerator;

public class DatabaseUtilitiesForJava {

    private final PrimaryKeyJavaGenerator primaryKeyGenerator;
    private final ValueJavaGenerator valueGenerator;
    private final EntityJavaGenerator entityGenerator;
    private final FactoryJavaGenerator factoryGenerator;
    private final CommonJavaGenerator commonGenerator;

    /** Creates a new instance of DatabaseUtilitiesForJava */
    public DatabaseUtilitiesForJava(boolean verbose, Database database) {
        primaryKeyGenerator = new PrimaryKeyJavaGenerator(verbose, database);
        valueGenerator = new ValueJavaGenerator(verbose, database);
        entityGenerator = new EntityJavaGenerator(verbose, database);
        factoryGenerator = new FactoryJavaGenerator(verbose, database);
        commonGenerator = new CommonJavaGenerator(verbose, database);
    }

    public void exportJava(String baseDirectory) throws Exception {
        primaryKeyGenerator.export(baseDirectory);
        valueGenerator.export(baseDirectory);
        entityGenerator.export(baseDirectory);
        factoryGenerator.export(baseDirectory);
        commonGenerator.export(baseDirectory);
    }

}
