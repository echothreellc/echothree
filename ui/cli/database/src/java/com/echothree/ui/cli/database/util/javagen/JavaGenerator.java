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

public abstract class JavaGenerator {
    
    protected static final String MODEL_CONTROL_CORE_COMMON_PACKAGE = "com.echothree.model.control.core.common";
    
    protected final boolean verbose;
    protected final Database myDatabase;
    protected final List<Component> myComponents;
    
    /** Creates a new Java generator. */
    protected JavaGenerator(boolean verbose, Database theDatabase) {
        this.verbose = verbose;
        myDatabase = theDatabase;
        myComponents = theDatabase.getComponents();
    }
    
    public String createDirectoryForClassPackage(String classPackage, String baseDirectory) {
        var directory = baseDirectory;
        var currentIndex = 0;
        int nextDot;
        do {
            nextDot = classPackage.indexOf('.', currentIndex);
            if(nextDot == -1)
                directory = directory + File.separatorChar + classPackage.substring(currentIndex);
            else
                directory = directory + File.separatorChar + classPackage.substring(currentIndex, nextDot);
            currentIndex = nextDot + 1;
        } while (nextDot != -1);

        var theDirectory = new File(directory);
        if(!theDirectory.exists()) {
            theDirectory.mkdirs();
        }
        
        return directory;
    }
    
    public String createPKDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getPKPackage(), baseDirectory);
    }
    
    public String createValueDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getValuePackage(), baseDirectory);
    }
    
    public String createEntityDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getEntityPackage(), baseDirectory);
    }
    
    public String createFactoryDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getFactoryPackage(), baseDirectory);
    }
    
    public String createCommonDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getCommonPackage(), baseDirectory);
    }
    
    public String createCommonCoreControlDirectory(String baseDirectory) {
        return createDirectoryForClassPackage(MODEL_CONTROL_CORE_COMMON_PACKAGE, baseDirectory);
    }
    
    public void writeCopyright(PrintWriter pw) {
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("// Copyright 2002-2026 Echo Three, LLC");
        pw.println("//");
        pw.println("// Licensed under the Apache License, Version 2.0 (the \"License\");");
        pw.println("// you may not use this file except in compliance with the License.");
        pw.println("// You may obtain a copy of the License at");
        pw.println("//");
        pw.println("//     http://www.apache.org/licenses/LICENSE-2.0");
        pw.println("//");
        pw.println("// Unless required by applicable law or agreed to in writing, software");
        pw.println("// distributed under the License is distributed on an \"AS IS\" BASIS,");
        pw.println("// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.");
        pw.println("// See the License for the specific language governing permissions and");
        pw.println("// limitations under the License.");
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("// Generated File -- DO NOT EDIT BY HAND");
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("");
    }
    
    public void writeVersion(PrintWriter pw, String classFileName) {
        pw.println("/**");
        pw.println(" * " + classFileName);
        pw.println(" */");
        pw.println("");
    }
    
    public void writePackage(PrintWriter pw, String classPackage) {
        pw.println("package " + classPackage + ";");
        pw.println("");
    }
    
}
