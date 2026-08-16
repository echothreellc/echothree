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

package com.echothree.ui.cli.database.util.javagen;

import com.echothree.ui.cli.database.util.definition.Component;
import com.echothree.ui.cli.database.util.definition.ColumnDataType;
import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.definition.Table;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class PrimaryKeyJavaGenerator extends JavaGenerator {

    public PrimaryKeyJavaGenerator(boolean verbose, Database database) {
        super(verbose, database);
    }

    public void writePKImports(PrintWriter pw, Table theTable) {
        pw.println("import " + theTable.getConstantsImport() + ";");
        pw.println("");
        pw.println("import com.echothree.util.common.persistence.BasePK;");
        pw.println("");
    }
    
    public void writePKConstructors(PrintWriter pw, Table theTable) {
        var pkClass = theTable.getPKClass();
        
        pw.println("    /** Creates a new instance of " + pkClass + " */");
        pw.println("    public " + pkClass + "(Long entityId) {");
        pw.println("        super(" + theTable.getConstantsClass() + ".COMPONENT_VENDOR_NAME, " + theTable.getConstantsClass() + ".ENTITY_TYPE_NAME, entityId);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    /** Creates a new instance of " + pkClass + " */");
        pw.println("    public " + pkClass + "(String entityId) {");
        pw.println("        super(" + theTable.getConstantsClass() + ".COMPONENT_VENDOR_NAME, " + theTable.getConstantsClass() + ".ENTITY_TYPE_NAME, Long.valueOf(entityId));");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writePKClass(PrintWriter pw, Table theTable) {
        pw.println("public class " + theTable.getPKClass());
        pw.println("        extends BasePK {");
        pw.println("    ");
        
        writePKConstructors(pw, theTable);
        
        pw.println("}");
    }
    
    public void export(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createPKDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                if(theTable.hasEID()) {
                    var classFileName = theTable.getPKClass() + ".java";
                    var f = new File(componentDirectory + File.separatorChar + classFileName);
                    
                    try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                        var pw = new PrintWriter(bw);
                        
                        writeCopyright(pw);
                        writeVersion(pw, classFileName);
                        writePackage(pw, theComponent.getPKPackage());
                        writePKImports(pw, theTable);
                        writePKClass(pw, theTable);
                    }
                }
            }
        }
    }
    
}
