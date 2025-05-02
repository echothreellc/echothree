// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.form.util;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FormUtils {
    
    private FormUtils() {
        super();
    }
    
    private static class FormUtilsHolder {
        static FormUtils instance = new FormUtils();
    }
    
    public static FormUtils getInstance() {
        return FormUtilsHolder.instance;
    }

    private static class ComponentInformation {
        String name;
        String packageName;
        List<String> editInterfaces;
        List<String> formInterfaces;
        List<String> specInterfaces;
        List<String> resultInterfaces;

        ComponentInformation(String name, String packageName, List<String> editInterfaces, List<String> formInterfaces, List<String> specInterfaces,
                List<String> resultInterfaces) {
            this.name = name;
            this.packageName = packageName;
            this.editInterfaces = editInterfaces;
            this.formInterfaces = formInterfaces;
            this.specInterfaces = specInterfaces;
            this.resultInterfaces = resultInterfaces;
        }

        boolean hasInterfaces() {
            return !editInterfaces.isEmpty() || !formInterfaces.isEmpty() || !specInterfaces.isEmpty() || !resultInterfaces.isEmpty();
        }
    }

    private List<String> getInterfaces(String commonBase, String packageName, String interfaceSuffix)
            throws Exception {
        List<String> interfaces = new ArrayList<>();
        var editBase = commonBase + "/" + packageName;
        var editDirectory = new File(editBase);
        
        if(editDirectory.exists()) {
            var editFiles = editDirectory.listFiles();
            var fileSuffix = interfaceSuffix + ".java";

            if(editFiles != null) {
                for(var editFile : editFiles) {
                    var editFileName = editFile.getName();

                    if(editFile.isFile() && editFileName.endsWith(fileSuffix)) {
                        interfaces.add(editFileName.substring(0, editFileName.length() - 5));
                    }
                }
            } else {
                throw new Exception(editDirectory + " is not a directory");
            }
        }

        return interfaces;
    }

    private List<ComponentInformation> getComponents(String sourceDirectory)
            throws Exception {
        List<ComponentInformation> components = new ArrayList<>();
        var componentBase = sourceDirectory + "/java/com/echothree/control/user";
        var componentsDirectory = new File(componentBase);

        if(componentsDirectory.exists()) {
            var componentDirectories = componentsDirectory.listFiles();

            if(componentDirectories != null) {
                for(var componentDirectory : componentDirectories) {
                    if(componentDirectory.isDirectory()) {
                        var packageName = componentDirectory.getName();
                        var beanBase = componentBase + '/' + packageName + "/server";
                        var beanDirectory = new File(beanBase);

                        if(beanDirectory.exists()) {
                            var beanFiles = beanDirectory.listFiles();

                            if(beanFiles != null) {
                                for(var beanFile : beanFiles) {
                                    var beanFileName = beanFile.getName();

                                    if(beanFile.isFile() && beanFileName.endsWith("Bean.java")) {
                                        var commonBase = componentBase + '/' + componentDirectory.getName() + "/common";
                                        var name = beanFileName.substring(0, beanFileName.length() - 9);
                                        var editInterfaces = getInterfaces(commonBase, "edit", "Edit");
                                        var formInterfaces = getInterfaces(commonBase, "form", "Form");
                                        var specInterfaces = getInterfaces(commonBase, "spec", "Spec");
                                        var resultInterfaces = getInterfaces(commonBase, "result", "Result");

                                        components.add(new ComponentInformation(name, packageName, editInterfaces, formInterfaces, specInterfaces, resultInterfaces));
                                    }
                                }
                            } else {
                                throw new Exception(beanDirectory + " is not a directory");
                            }
                        }
                    }
                }
            } else {
                throw new Exception(componentsDirectory + " is not a directory");
            }
        } else {
            System.err.println("sourceDirectory doesn't exist: " + sourceDirectory);
        }

        return components;
    }

    public String createDirectoryForClassPackage(String classPackage, String generatedDirectory)
            throws Exception {
        var directory = new StringBuilder(generatedDirectory);
        var currentIndex = 0;
        int nextDot;

        do {
            nextDot = classPackage.indexOf('.', currentIndex);
            if(nextDot == -1)
                directory.append(File.separatorChar).append(classPackage.substring(currentIndex));
            else
                directory.append(File.separatorChar).append(classPackage, currentIndex, nextDot);
            currentIndex = nextDot + 1;
        } while (nextDot != -1);

        var theDirectory = new File(directory.toString());
        if(!theDirectory.exists()) {
            if(!theDirectory.mkdirs()) {
                throw new Exception(theDirectory + " could not be created");
            }
        }

        return directory.toString();
    }

    public void writeCopyright(PrintWriter pw) {
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("// Copyright 2002-2025 Echo Three, LLC");
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

    private void exportForms(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        var interfacePackage = "com.echothree.control.user." + componentInformation.packageName + ".common";
        var interfaceDirectory = createDirectoryForClassPackage(interfacePackage, generatedDirectory);

        var className = componentInformation.name + "Forms";
        var classFileName = className + ".java";

        var f = new File(interfaceDirectory + "/" + classFileName);
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, interfacePackage);
            
            if(!componentInformation.editInterfaces.isEmpty()) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.edit.*;");
            }
            if(!componentInformation.formInterfaces.isEmpty()) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.form.*;");
            }
            if(!componentInformation.specInterfaces.isEmpty()) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.spec.*;");
            }
            pw.println("");
            
            pw.println("public interface " + className + " {");
            pw.println("");
            componentInformation.editInterfaces
                    .forEach((interfaceName) -> pw.println("    " + interfaceName + " get" + interfaceName + "();"));
            componentInformation.formInterfaces
                    .forEach((interfaceName) -> pw.println("    " + interfaceName + " get" + interfaceName + "();"));
            componentInformation.specInterfaces
                    .forEach((interfaceName) -> pw.println("    " + interfaceName + " get" + interfaceName + "();"));
            pw.println("");
            pw.println("}");
        }
    }

    private void exportFormsImpl(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        var interfacePackage = "com.echothree.control.user." + componentInformation.packageName + ".server";
        var interfaceDirectory = createDirectoryForClassPackage(interfacePackage, generatedDirectory);

        var className = componentInformation.name + "FormsImpl";
        var classFileName = className + ".java";

        var f = new File(interfaceDirectory + "/" + classFileName);
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, interfacePackage);
            
            if(!componentInformation.editInterfaces.isEmpty()) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.edit.*;");
            }
            if(!componentInformation.formInterfaces.isEmpty()) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.form.*;");
            }
            if(!componentInformation.specInterfaces.isEmpty()) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.spec.*;");
            }
            if(!componentInformation.editInterfaces.isEmpty() || !componentInformation.formInterfaces.isEmpty() || !componentInformation.specInterfaces.isEmpty()) {
                pw.println("");
            }
            
            pw.println("public class " + className + " {");
            pw.println("    ");
            componentInformation.editInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return " + componentInformation.name + "EditFactory.get" + interfaceName + "();"))
                    .peek((_item) -> pw.println("    }"))
                    .forEach((_item) -> pw.println("    "));
            componentInformation.formInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return " + componentInformation.name + "FormFactory.get" + interfaceName + "();"))
                    .peek((_item) -> pw.println("    }")).
                    forEach((_item) -> pw.println("    "));
            componentInformation.specInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return " + componentInformation.name + "SpecFactory.get" + interfaceName + "();"))
                    .peek((_item) -> pw.println("    }"))
                    .forEach((_item) -> pw.println("    "));
            pw.println("}");
        }
    }

    private void exportEditFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        var classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.edit";
        var classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        var className = componentInformation.name + "EditFactory";
        var classFileName = className + ".java";

        var f = new File(classDirectory + "/" + classFileName);
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.form.BaseFormFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseFormFactory {");
            pw.println("    ");
            componentInformation.editInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    static public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return createForm(" + interfaceName + ".class);"))
                    .peek((_item) -> pw.println("    }"))
                    .forEach((_item) -> pw.println("    "));
            pw.println("}");
        }
    }

    private void exportFormFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        var classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.form";
        var classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        var className = componentInformation.name + "FormFactory";
        var classFileName = className + ".java";

        var f = new File(classDirectory + "/" + classFileName);
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.form.BaseFormFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseFormFactory {");
            pw.println("    ");
            componentInformation.formInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    static public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return createForm(" + interfaceName + ".class);"))
                    .peek((_item) -> pw.println("    }"))
                    .forEach((_item) -> pw.println("    "));
            pw.println("}");
        }
    }

    private void exportSpecFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        var classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.spec";
        var classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        var className = componentInformation.name + "SpecFactory";
        var classFileName = className + ".java";

        var f = new File(classDirectory + "/" + classFileName);
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.form.BaseFormFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseFormFactory {");
            pw.println("    ");
            componentInformation.specInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    static public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return createForm(" + interfaceName + ".class);"))
                    .peek((_item) -> pw.println("    }"))
                    .forEach((_item) -> pw.println("    "));
            pw.println("}");
        }
    }

    private void exportResultFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        var classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.result";
        var classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        var className = componentInformation.name + "ResultFactory";
        var classFileName = className + ".java";

        var f = new File(classDirectory + "/" + classFileName);
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.command.BaseResultFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseResultFactory {");
            pw.println("    ");
            componentInformation.resultInterfaces.stream()
                    .peek((interfaceName) -> pw.println("    static public " + interfaceName + " get" + interfaceName + "() {"))
                    .peek((interfaceName) -> pw.println("        return createResult(" + interfaceName + ".class);"))
                    .peek((_item) -> pw.println("    }"))
                    .forEach((_item) -> pw.println("    "));
            pw.println("}");
        }
    }

    private void exportJava(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        if(componentInformation.hasInterfaces()) {
            exportForms(generatedDirectory, componentInformation);
            exportFormsImpl(generatedDirectory, componentInformation);
        }

        if(!componentInformation.editInterfaces.isEmpty()) {
            exportEditFactory(generatedDirectory, componentInformation);
        }

        if(!componentInformation.formInterfaces.isEmpty()) {
            exportFormFactory(generatedDirectory, componentInformation);
        }

        if(!componentInformation.specInterfaces.isEmpty()) {
            exportSpecFactory(generatedDirectory, componentInformation);
        }

        if(!componentInformation.resultInterfaces.isEmpty()) {
            exportResultFactory(generatedDirectory, componentInformation);
        }
    }

    public void generateClasses(String generatedDirectory, String sourceDirectory)
            throws Exception {
        var components = getComponents(sourceDirectory);
        
        for(var component: components) {
            exportJava(generatedDirectory, component);
        }
    }
    
}
