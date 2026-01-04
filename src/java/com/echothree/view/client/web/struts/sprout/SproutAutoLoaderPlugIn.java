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
import io.github.classgraph.ClassGraph;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionFormBean;
import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;

/**
 * <p>Finds Sprouts present in the classpath and registers them with
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
        final var annotations = bean.getAnnotations();

        for(var j = 0; j < annotations.length; j++) {
            final var a = annotations[j];
            final Class type = a.annotationType();

            if(type.equals(SproutForm.class)) {
                final var form = (SproutForm) a;
                final var actionFormName = form.name();
                final var actionFormType = bean.getName();

                if(log.isDebugEnabled()) {
                    log.debug("ActionForm " + actionFormName + " -> " + actionFormType);
                }

                getModuleConfig().addFormBeanConfig(new ActionFormBean(actionFormName, actionFormType));
            }
        }
    }
    
    private void loadAction(final Class bean) {
        final var annotations = bean.getAnnotations();

        for(var i = 0; i < annotations.length; i++) {
            final var a = annotations[i];
            final Class type = a.annotationType();

            if(type.equals(SproutAction.class)) {
                final var form = (SproutAction) a;
                final var path = form.path();
                final Class<ActionConfig> mappingClass = form.mappingClass();
                final var scope = form.scope();
                final var name = form.name();
                final var parameter = form.parameter();
                final var validate = form.validate();
                final var input = form.input();
                final var properties = form.properties();
                final var forwards = form.forwards();
                ActionConfig actionConfig = null;
                
                try {
                    var constructor = mappingClass.getDeclaredConstructor();
                    
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

                        for(var j = 0; j < properties.length; j++) {
                            beanMap.put(properties[j].property(), properties[j].value());
                        }
                    }

                    if(forwards != null && forwards.length > 0) {
                        for(var j = 0; j < forwards.length; j++) {
                            var fcModule = forwards[j].module();

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
        try(var scanResult = new ClassGraph()
                .enableAnnotationInfo()
                .scan()) {
            final var actionClasses = scanResult
                    .getClassesWithAnnotation(SproutAction.class.getName());
            final var formClasses = scanResult
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
     * Extends SproutContextLoaderPlugIn's initialization callback to add
     * Struts registration of Sprouts.
     */
    @Override
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
        final var actionForward = new ActionForward();

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
        final var methods = clazz.getMethods();

        for(var i = 0; i < methods.length; i++) {
            var methodName = methods[i].getName();

            if(methodName.equals("publick"))
                methodName = "public";

            if(methodName.equalsIgnoreCase(name.replaceAll("_([a-z])", "$1")))
                return methods[i];
        }

        throw new NoSuchMethodException(name);
    }

}
