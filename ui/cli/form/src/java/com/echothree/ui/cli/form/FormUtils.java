// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.form;

import static com.google.common.base.Charsets.UTF_8;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
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

    private List<String> getInterfaces(String remoteBase, String packageName, String interfaceSuffix) {
        List<String> interfaces = new ArrayList<>();
        String editBase = new StringBuilder(remoteBase).append("/").append(packageName).toString();
        File editDirectory = new File(editBase);
        
        if(editDirectory.exists()) {
            File[] editFiles = editDirectory.listFiles();
            String fileSuffix = new StringBuilder(interfaceSuffix).append(".java").toString();

            for(int k = 0; k < editFiles.length; k++) {
                File editFile = editFiles[k];
                String editFileName = editFile.getName();

                if(editFile.isFile() && editFileName.endsWith(fileSuffix)) {
                    interfaces.add(editFileName.substring(0, editFileName.length() - 5));
                }
            }
        }

        return interfaces;
    }

    private List<ComponentInformation> getComponents(String sourceDirectory) {
        List<ComponentInformation> components = new ArrayList<>();
        String componentBase = new StringBuilder(sourceDirectory).append("/java/com/echothree/control/user").toString();
        File componentsDirectory = new File(componentBase);

        if(componentsDirectory.exists()) {
            File[] componentDirectories = componentsDirectory.listFiles();

            for(int i = 0; i < componentDirectories.length; i++) {
                File componentDirectory = componentDirectories[i];

                if(componentDirectory.isDirectory()) {
                    String packageName = componentDirectory.getName();
                    String beanBase = new StringBuilder(componentBase).append('/').append(packageName).append("/server").toString();
                    File beanDirectory = new File(beanBase);

                    if(beanDirectory.exists()) {
                        File[] beanFiles = beanDirectory.listFiles();

                        for(int j = 0; j < beanFiles.length; j++) {
                            File beanFile = beanFiles[j];
                            String beanFileName = beanFile.getName();

                            if(beanFile.isFile() && beanFileName.endsWith("Bean.java")) {
                                String remoteBase = new StringBuilder(componentBase).append('/').append(componentDirectory.getName()).append("/common").toString();
                                String name = beanFileName.substring(0, beanFileName.length() - 9);
                                List<String> editInterfaces = getInterfaces(remoteBase, "edit", "Edit");
                                List<String> formInterfaces = getInterfaces(remoteBase, "form", "Form");
                                List<String> specInterfaces = getInterfaces(remoteBase, "spec", "Spec");
                                List<String> resultInterfaces = getInterfaces(remoteBase, "result", "Result");

                                components.add(new ComponentInformation(name, packageName, editInterfaces, formInterfaces, specInterfaces, resultInterfaces));
                            }
                        }
                    }
                }
            }
        } else {
            System.err.println("sourceDirectory doesn't exist: " + sourceDirectory);
        }

        return components;
    }

    public String createDirectoryForClassPackage(String classPackage, String generatedDirectory) {
        String directory = generatedDirectory;
        int currentIndex = 0;
        int nextDot;
        do {
            nextDot = classPackage.indexOf('.', currentIndex);
            if(nextDot == -1)
                directory = directory + File.separatorChar + classPackage.substring(currentIndex);
            else
                directory = directory + File.separatorChar + classPackage.substring(currentIndex, nextDot);
            currentIndex = nextDot + 1;
        } while (nextDot != -1);

        File theDirectory = new File(directory);
        if(theDirectory.exists() == false) {
            theDirectory.mkdirs();
        }

        return directory;
    }

    public void writeCopyright(PrintWriter pw) {
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("// Copyright 2002-2018 Echo Three, LLC");
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
        String interfacePackage = "com.echothree.control.user." + componentInformation.packageName + ".common";
        String interfaceDirectory = createDirectoryForClassPackage(interfacePackage, generatedDirectory);

        String className = componentInformation.name + "Forms";
        String classFileName = className + ".java";

        File f = new File(interfaceDirectory + "/" + classFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            PrintWriter pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, interfacePackage);
            
            if(componentInformation.editInterfaces.size() > 0) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.edit.*;");
            }
            if(componentInformation.formInterfaces.size() > 0) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.form.*;");
            }
            if(componentInformation.specInterfaces.size() > 0) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.spec.*;");
            }
            pw.println("");
            
            pw.println("public interface " + className + " {");
            pw.println("");
            componentInformation.editInterfaces.stream().forEach((interfaceName) -> {
                pw.println("    " + interfaceName + " get" + interfaceName + "();");
            });
            componentInformation.formInterfaces.stream().forEach((interfaceName) -> {
                pw.println("    " + interfaceName + " get" + interfaceName + "();");
            });
            componentInformation.specInterfaces.stream().forEach((interfaceName) -> {
                pw.println("    " + interfaceName + " get" + interfaceName + "();");
            });
            pw.println("");
            pw.println("}");
        }
    }

    private void exportFormsImpl(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        String interfacePackage = "com.echothree.control.user." + componentInformation.packageName + ".server";
        String interfaceDirectory = createDirectoryForClassPackage(interfacePackage, generatedDirectory);

        String className = componentInformation.name + "FormsImpl";
        String classFileName = className + ".java";

        File f = new File(interfaceDirectory + "/" + classFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            PrintWriter pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, interfacePackage);
            
            if(componentInformation.editInterfaces.size() > 0) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.edit.*;");
            }
            if(componentInformation.formInterfaces.size() > 0) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.form.*;");
            }
            if(componentInformation.specInterfaces.size() > 0) {
                pw.println("import com.echothree.control.user." + componentInformation.packageName + ".common.spec.*;");
            }
            if(componentInformation.editInterfaces.size() > 0 || componentInformation.formInterfaces.size() > 0 || componentInformation.specInterfaces.size() > 0) {
                pw.println("");
            }
            
            pw.println("public class " + className + " {");
            pw.println("    ");
            componentInformation.editInterfaces.stream().map((interfaceName) -> {
                pw.println("    public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return " + componentInformation.name + "EditFactory.get" + interfaceName + "();");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
            componentInformation.formInterfaces.stream().map((interfaceName) -> {
                pw.println("    public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return " + componentInformation.name + "FormFactory.get" + interfaceName + "();");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
            componentInformation.specInterfaces.stream().map((interfaceName) -> {
                pw.println("    public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return " + componentInformation.name + "SpecFactory.get" + interfaceName + "();");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
            pw.println("}");
        }
    }

    private void exportEditFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        String classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.edit";
        String classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        String className = componentInformation.name + "EditFactory";
        String classFileName = className + ".java";

        File f = new File(classDirectory + "/" + classFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            PrintWriter pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.form.BaseFormFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseFormFactory {");
            pw.println("    ");
            componentInformation.editInterfaces.stream().map((interfaceName) -> {
                pw.println("    static public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return (" + interfaceName + ")createForm(" + interfaceName + ".class);");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
            pw.println("}");
        }
    }

    private void exportFormFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        String classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.form";
        String classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        String className = componentInformation.name + "FormFactory";
        String classFileName = className + ".java";

        File f = new File(classDirectory + "/" + classFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            PrintWriter pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.form.BaseFormFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseFormFactory {");
            pw.println("    ");
            componentInformation.formInterfaces.stream().map((interfaceName) -> {
                pw.println("    static public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return (" + interfaceName + ")createForm(" + interfaceName + ".class);");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
            pw.println("}");
        }
    }

    private void exportSpecFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        String classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.spec";
        String classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        String className = componentInformation.name + "SpecFactory";
        String classFileName = className + ".java";

        File f = new File(classDirectory + "/" + classFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            PrintWriter pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.form.BaseFormFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseFormFactory {");
            pw.println("    ");
            componentInformation.specInterfaces.stream().map((interfaceName) -> {
                pw.println("    static public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return (" + interfaceName + ")createForm(" + interfaceName + ".class);");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
            pw.println("}");
        }
    }

    private void exportResultFactory(String generatedDirectory, ComponentInformation componentInformation)
            throws Exception {
        String classPackage = "com.echothree.control.user." + componentInformation.packageName + ".common.result";
        String classDirectory = createDirectoryForClassPackage(classPackage, generatedDirectory);

        String className = componentInformation.name + "ResultFactory";
        String classFileName = className + ".java";

        File f = new File(classDirectory + "/" + classFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            PrintWriter pw = new PrintWriter(bw);
            
            writeCopyright(pw);
            writeVersion(pw, classFileName);
            writePackage(pw, classPackage);
            
            pw.println("import com.echothree.util.common.command.BaseResultFactory;");
            pw.println("");
            
            pw.println("public class " + className);
            pw.println("        extends BaseResultFactory {");
            pw.println("    ");
            componentInformation.resultInterfaces.stream().map((interfaceName) -> {
                pw.println("    static public " + interfaceName + " get" + interfaceName + "() {");
                return interfaceName;
            }).map((interfaceName) -> {
                pw.println("        return (" + interfaceName + ")createResult(" + interfaceName + ".class);");
                return interfaceName;
            }).map((_item) -> {
                pw.println("    }");
                return _item;
            }).forEach((_item) -> {
                pw.println("    ");
            });
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
        List<ComponentInformation> components = getComponents(sourceDirectory);
        
        for(ComponentInformation component: components) {
            exportJava(generatedDirectory, component);
        }
    }
    
}
