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

public class CommonJavaGenerator extends JavaGenerator {

    public CommonJavaGenerator(boolean verbose, Database database) {
        super(verbose, database);
    }

    public void writeConstantsInstanceVariables(PrintWriter pw, Table theTable) {
        pw.println("    String COMPONENT_VENDOR_NAME = \"ECHO_THREE\";");
        pw.println("    String ENTITY_TYPE_NAME = \"" + theTable.getNameSingular() + "\";");
        pw.println("    ");
    }
    
    public void writeConstantsClass(PrintWriter pw, Table theTable) {
        pw.println("public interface " + theTable.getConstantsClass() + " {");
        pw.println("    ");
        
        writeConstantsInstanceVariables(pw, theTable);
        
        pw.println("}");
    }
    
    private void exportCommons(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createCommonDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                var classFileName = theTable.getConstantsClass() + ".java";
                var f = new File(componentDirectory + File.separatorChar + classFileName);
                
                try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                    var pw = new PrintWriter(bw);
                    
                    writeCopyright(pw);
                    writeVersion(pw, classFileName);
                    writePackage(pw, theComponent.getCommonPackage());
                    writeConstantsClass(pw, theTable);
                }
            }
        }
    }
    
    private void exportEntityTypesEnum(String baseDirectory)
    throws Exception {
        var directory = createCommonCoreControlDirectory(baseDirectory);
        var classFileName = "EntityTypes.java";
        var f = new File(directory + File.separatorChar + classFileName);
                
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);

            writeCopyright(pw);
            writePackage(pw, MODEL_CONTROL_CORE_COMMON_PACKAGE);

            pw.println("public enum EntityTypes {");
            pw.println("    ");

            var notFirst = false;
            for(var theComponent: myComponents) {
                for(var theTable: theComponent.getTables()) {
                    if(notFirst) {
                        pw.println(",");
                    }
                    pw.print("    " + theTable.getNameSingular());
                    notFirst = true;
                }
            }
            pw.println("");
            
            pw.println("    ");
            pw.println("}");
        }
    }

    public void export(String baseDirectory) throws Exception {
        exportCommons(baseDirectory);
        exportEntityTypesEnum(baseDirectory);
    }
    
}
