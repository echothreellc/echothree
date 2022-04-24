// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

/*
Copyright 2005-2006 Seth Fitzsimmons <seth@note.amherst.edu>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.echothree.view.client.web.struts.sprout;

import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionFormBean;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;

/**
 * <p>Finds Sprouts registered in a Spring context and registers them with
 * Struts, using cues from annotations present to set specific properties.</p> 
 * 
 * <p>This needs to be configured as a plug-in in
 * <code>struts-config.xml</code>.</p>
 *
 * <p>TODO create GlobalForward annotation and create global forwards based on that</p>
 * 
 * @author Seth Fitzsimmons
 */
public class SproutAutoLoaderPlugIn
        extends SproutContextLoaderPlugIn {

    private final static Logger log = Logger.getLogger(SproutAutoLoaderPlugIn.class);

    private void loadForm(final Class bean) {
        final Annotation[] annotations = bean.getAnnotations();

        for(int j = 0; j < annotations.length; j++) {
            final Annotation a = annotations[j];
            final Class type = a.annotationType();

            if(type.equals(SproutForm.class)) {
                final SproutForm form = (SproutForm) a;
                final String actionFormName = form.name();
                final String actionFormType = bean.getName();

                if(log.isDebugEnabled()) {
                    log.debug("ActionForm " + actionFormName + " -> " + actionFormType);
                }

                getModuleConfig().addFormBeanConfig(new ActionFormBean(actionFormName, actionFormType));
            }
        }
    }
    
    private void loadAction(final Class bean) {
        final Annotation[] annotations = bean.getAnnotations();

        for(int i = 0; i < annotations.length; i++) {
            final Annotation a = annotations[i];
            final Class type = a.annotationType();

            if(type.equals(SproutAction.class)) {
                final SproutAction form = (SproutAction) a;
                final String path = form.path();
                final Class<ActionConfig> mappingClass = form.mappingClass();
                final String scope = form.scope();
                final String name = form.name();
                final String parameter = form.parameter();
                final boolean validate = form.validate();
                final String input = form.input();
                final SproutProperty[] properties = form.properties();
                final SproutForward[] forwards = form.forwards();
                ActionConfig actionConfig = null;
                
                try {
                    Constructor<ActionConfig> constructor = mappingClass.getDeclaredConstructor();
                    
                    actionConfig = constructor.newInstance();
                } catch (NoSuchMethodException nsme) {
                    log.error("Failed to create a new instance of " + mappingClass + ", " + nsme.getMessage());
                } catch (InstantiationException ie) {
                    log.error("Failed to create a new instance of " + mappingClass + ", " + ie.getMessage());
                } catch (IllegalAccessException iae) {
                    log.error("Failed to create a new instance of " + mappingClass + ", " + iae.getMessage());
                } catch (InvocationTargetException ite) {
                    log.error("Failed to create a new instance of " + mappingClass + ", " + ite.getMessage());
                }

                if(actionConfig != null) {
                    actionConfig.setPath(path);
                    actionConfig.setType(bean.getName());
                    actionConfig.setScope(scope);
                    actionConfig.setValidate(validate);

                    if(name.length() > 0) {
                        actionConfig.setName(name);
                    }

                    if(parameter.length() > 0) {
                        actionConfig.setParameter(parameter);
                    }

                    if(input.length() > 0) {
                        actionConfig.setInput(input);
                    }

                    if(properties != null && properties.length > 0) {
                        var beanMap = new BeanMap(actionConfig);

                        for(int j = 0; j < properties.length; j++) {
                            beanMap.put(properties[j].property(), properties[j].value());
                        }
                    }

                    if(forwards != null && forwards.length > 0) {
                        for(int j = 0; j < forwards.length; j++) {
                            String fcModule = forwards[j].module();

                            actionConfig.addForwardConfig(makeForward(forwards[j].name(), forwards[j].path(), forwards[j].redirect(),
                                    fcModule.length() == 0? null: fcModule));
                        }
                    }
                }
                
                if(log.isDebugEnabled()) {
                    log.debug("Action " + path + " -> " + bean.getName());
                }

                getModuleConfig().addActionConfig(actionConfig);
            }
        }
    }
    
    private void loadAnnotatedActionsAndForms() {
        try(ScanResult scanResult= new ClassGraph()
                .enableAnnotationInfo()
                .scan()) {
            final ClassInfoList actionClasses = scanResult
                    .getClassesWithAnnotation(SproutAction.class.getName());
            final ClassInfoList formClasses = scanResult
                    .getClassesWithAnnotation(SproutForm.class.getName());

            for(var actionClass : actionClasses) {
                Class clazz = actionClass.loadClass();

                loadAction(clazz);
            }

            for(var formClass : formClasses) {
                Class clazz = formClass.loadClass();

                loadForm(clazz);
            }
        }
    }

    /**
     * Extends Spring's ContextLoaderPlugIn initialization callback to add
     * Struts registration of Sprouts.
     */
    public void onInit() throws ServletException {
        loadAnnotatedActionsAndForms();
    }
    
    /**
     * Helper method for creating ActionForwards.
     * 
     * @param name Forward name.
     * @param path Registered path.
     * @return ForwardConfig.
     */
    private ForwardConfig makeForward(final String name, final String path) {
        return makeForward(name, path, false, null);
    }

    /**
     * Helper method for creating ActionForwards.
     * 
     * @param name Forward name.
     * @param path Registered path.
     * @param redirect Whether this should be an HTTP redirect.
     * @return ActionForward.
     */
    private ActionForward makeForward(final String name, final String path, final boolean redirect, final String module) {
        final ActionForward actionForward = new ActionForward();

        actionForward.setName(name);
        actionForward.setPath(path);
        actionForward.setRedirect(redirect);
        actionForward.setModule(module);

        return actionForward;
    }
    
    /**
     * Finds the method in the target class which corresponds to a registered
     * pathname.
     * 
     * @param name Action portion of pathname.
     * @param clazz Target class.
     * @return Corresponding method.
     * @throws NoSuchMethodException when corresponding method cannot be found.
     */
    private Method findMethod(final String name, final Class clazz) throws NoSuchMethodException {
        final Method[] methods = clazz.getMethods();

        for(int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();

            if(methodName.equals("publick"))
                methodName = "public";

            if(methodName.equalsIgnoreCase(name.replaceAll("_([a-z])", "$1")))
                return methods[i];
        }

        throw new NoSuchMethodException(name);
    }

}
