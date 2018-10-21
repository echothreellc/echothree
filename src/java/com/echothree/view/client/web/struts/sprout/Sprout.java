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

import com.echothree.view.client.web.struts.sprout.support.SproutUtils;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.support.AbstractBeanFactory;

/**
 * <p>Sprout at its core is a Spring-aware <code>MappingDispatchAction</code>.
 * Sprouts are appropriate when one is working with a legacy Struts
 * application and becomes fed up with service lookups and repetitive writing
 * of mappings. One goal is to make web application development in Java more
 * fun. Sprouts require Java 5 (1.5) due to their use of annotations.  They
 * also require a servlet engine conforming to the Servlet 2.4 and JSP 2.0
 * specs (Tomcat 5.x, for example) due to the use of filters, listeners, and
 * tag files.</p>
 * 
 * <p>In addition to using on Spring for auto-wiring, Sprouts obviate the need
 * to write repetitive bean declarations for each URL as they register
 * aliases in the Spring context on startup.</p>
 * 
 * <p>Sprouts additionally obviate the need to write Struts action-mappings as
 * they use information gleaned from the initial Spring bean definition, class
 * properties (name and package), as well as annotations to appropriately
 * self-register on URLs defined by convention or specification with
 * properties obtained the same way.</p>
 * 
 * <p>The path is determined by the package name where the components to the
 * right of "action" are converted into directories.  Thus,
 * <code>whatever.action.*</code> will correspond to <code>/*</code> and
 * <code>whatever.action.help.*</code> to <code>/help/*</code></p>
 * 
 * <p>The <em>file</em> (or <em>action</em>) is determined by the method name.
 * Thus, <code>index()</code> will correspond to <code>index.do</code> and
 * <code>submitChange()</code> to <code>submit_change</code>
 * (CamelCased method names are converted).</p>
 * 
 * <p>(<strong>NOTE:</strong> <code>publick()</code> maps to public.do as
 * <em>public</em> is a Java keyword.)</p>
 * 
 * <p>Form names are created from the class name appended with <em>Form</em>,
 * so <code>TestAction</code> would default to <code>TestActionForm</code>.
 * This behavior can be overridden using the
 * <code>@FormName("AlternateForm")</code> annotation.</p>
 * 
 * <p>Input, validate, and scope properties can be overridden
 * with <code>@Input</code>, <code>@Validate</code>, and <code>@Scope</code>
 * respectively.</p>
 * 
 * <p><code>f(key)</code>, <code>F(key)</code>, and <code>s(key,value)</code>
 * are helper methods that manipulate DynaActionForms (if used) and obviate
 * the need to cast excessively.  <code>f()</code> is the equivalent of
 * calling <code>getString()</code>, <code>F()</code> <code>get()</code>, and
 * <code>s()</code> <code>set()</code>.</p>
 * 
 * <p><code>getMessages()</code>, <code>getErrors()</code>,
 * <code>saveMessages()</code>, and <code>saveErrors()</code> have been
 * modified to store state in the user's session allowing them to be used more
 * simply and effectively.  Rather than using this:
 * <pre>
 *   ActionMessages errors = new ActionMessages();
 *   ...
 *   saveErrors(request, errors);
 * </pre>
 * You should use getErrors() to initialize the errors ActionMessages object:
 * <pre>
 *   ActionMessages errors = getErrors( request );
 *   ...
 * </pre>
 * This way, messages and errors can be stacked up (while being kept separate)
 * until they are displayed using the sprout:notifications taglib (see
 * WEB-INF/tags/sprout/notifications.tag).</p>
 * 
 * <p>
 * TODO add additional reserved words<br />
 * TODO add a default ActionForm with just an "id" field (as a String) as "SproutForm"<br />
 * TODO add a GlobalForward annotation with "redirect" property to add to the<br />
 *      list of global-forwards.<br />
 * TODO add some measure of SiteMesh integration for AJAX partials<br />
 * TODO add some form of ActionForwardBuilder</p>
 * 
 * @author Seth Fitzsimmons
 */
public abstract class Sprout
        extends MappingDispatchAction implements BeanFactoryAware, BeanNameAware {
    private final static Logger log = Logger.getLogger( Sprout.class );
    static final String PACKAGE_DELIMITER = ".action";
    static final String DEFAULT_SCOPE = "request";
    static final String DEFAULT_FORM_SUFFIX = "Form";
    static final String DEFAULT_VIEW_EXTENSION = ".jsp";
    public static final String SPROUT_DEFAULT_ACTION_FORM_NAME = "form"; 

    /** Default forward key. */
    public static final String FWD_SUCCESS = "success";
    
    private String beanName;
    private static final ThreadLocal<DynaActionForm> formHolder = new ThreadLocal<>();

    @Override
    public final void setBeanFactory(final BeanFactory factory) throws BeansException {
        if ( !factory.isSingleton( beanName ) ) {
            log.warn( getClass().getName() + " must be defined with singleton=\"true\" in order to self-register.");
            return;
        }
        
        final String pkgName = getClass().getPackage().getName();
        final String path = pkgName.substring( pkgName.indexOf( PACKAGE_DELIMITER ) + PACKAGE_DELIMITER.length() ).replace('.', '/') + "/";
        
        if ( factory instanceof AbstractBeanFactory ) {
            final AbstractBeanFactory dlbf = (AbstractBeanFactory) factory;
            
            final Collection<Method> methods = SproutUtils.getDeclaredMethods( getClass(), Sprout.class );
            
            // register beans for each url
            log.debug("Registering paths...");
            for ( final Iterator<Method> i = methods.iterator(); i.hasNext(); ) {
                final Method method = i.next();
                String name = method.getName();
                if ( Modifier.isPublic( method.getModifiers() ) && method.getReturnType().equals( ActionForward.class ) ) {
                    if ( name.equals("publick") )
                        name = "public";
                    final String url = path + name.replaceAll("([A-Z])", "_$1" ).toLowerCase();
                    log.debug( url );
                    if ( !ArrayUtils.contains( dlbf.getAliases( beanName ), url ) )
                        dlbf.registerAlias( beanName, url );
                }
            }
        } else {
            log.warn("Unable to self-register; factory bean was of an unsupported type.");
            throw new BeanNotOfRequiredTypeException( beanName, AbstractBeanFactory.class, factory.getClass() );
        }
    }

    /**
     * Sets the name of this bean, as registered in the Spring context.
     */
    @Override
    public final void setBeanName(final String name) {
        this.beanName = name;
    }
    
    public final void init(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        if ( form instanceof DynaActionForm )
            formHolder.set( (DynaActionForm) form );
        else
            // clean up
            formHolder.remove();
        
        onInit( mapping, form, request, response );
    }
    
    /**
     * Callback for subclass-specific initialization.
     */
    protected void onInit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {}
    
    /**
     * Shortcut for ((DynaActionForm) form).getString(key).
     */
    protected String f(final String key) {
        if ( null == formHolder.get() )
            throw new UnsupportedOperationException("Active form is not a DynaActionForm.");
        
        return formHolder.get().getString( key );
    }
    
    /**
     * Shortcut for ((DynaActionForm) form).get(key).
     */
    protected Object F(final String key) {
        if ( null == formHolder.get() )
            throw new UnsupportedOperationException("Active form is not a DynaActionForm.");
        
        return formHolder.get().get( key );
    }
    
    /**
     * Shortcut for ((DynaActionForm) form).set(key, value).
     */
    protected void s(final String key, final Object value) {
        if ( null == formHolder.get() )
            throw new UnsupportedOperationException("Active form is not a DynaActionForm.");
        
        formHolder.get().set( key, value );
    }
    
    /**
     * Add errors to the session.
     */
    @Override
    protected void addErrors(final HttpServletRequest request, final ActionMessages msgs) {
        saveErrors( request, msgs );
    }
    
    /**
     * Gets undisplayed errors from both the request and the session.
     */
    @Override
    protected ActionMessages getErrors(final HttpServletRequest request) {
        final ActionMessages errors = super.getErrors( request );
        errors.add( (ActionMessages) request.getSession().getAttribute( Globals.ERROR_KEY ) );
        return errors;
    }
    
    /**
     * Saves errors to the session scope so that they may be picked up by the
     * next action that accesses errors.
     */
    @Override
    protected void saveErrors(final HttpServletRequest request, final ActionMessages msgs) {
        saveErrors( request.getSession(), msgs );
    }
    
    /**
     * Add messages to the session.
     */
    @Override
    protected void addMessages(final HttpServletRequest request, final ActionMessages msgs) {
        saveMessages( request, msgs );
    }
    
    /**
     * Gets undisplayed messages from both the request and the session.
     */
    @Override
    protected ActionMessages getMessages(final HttpServletRequest request) {
        final ActionMessages msgs = super.getMessages( request );
        msgs.add( (ActionMessages) request.getSession().getAttribute( Globals.MESSAGE_KEY ) );
        return msgs;
    }
    
    /**
     * Saves messages to the session scope so that they may be picked up by the
     * next action that accesses messages.
     */
    @Override
    protected void saveMessages(final HttpServletRequest request, final ActionMessages msgs) {
        saveMessages( request.getSession(), msgs );
    }
    
    /**
     * Helper method to display index.jsp in response to a request for
     * /index.do
     */
    public ActionForward index(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        return mapping.findForward( FWD_SUCCESS );
    }
    
    /**
     * Override the default form name for this action.  Equivalent to setting
     * <em>name</em> property in an <em>action</em> mapping in
     * <code>struts-config.xml</code>.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface FormName {
        /**
         * Form name. Corresponds to a <em>form-bean</em> mapping in
         * <code>struts-config.xml</code>.
         */
        String value();
    }
    
    /**
     * <p>Specifies a local forward.  Equivalent to adding a
     * <em>forward</em> mapping within an <em>action</em> mapping in
     * <code>struts-config.xml</code>.</p>
     * 
     * <p>It is possible to define multiple forwards by providing parameters
     * as arrays.</p>
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Forward {
        /**
         * Forward name.  Corresponds to <em>name</em> property.
         */
        String[] name();
        /**
         * Whether this forward is a redirect.  Corresponds to
         * <em>redirect</em> property.
         */
        boolean[] redirect() default {};
        /**
         * Forward path.  Corresponds to <em>path</em> property.
         */
        String[] path();
    }
    
    /**
     * Specifies the "input" property for this action.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Input {
        /**
         * Path to source JSP.
         */
        String value();
    }

    /**
     * Specifies the "scope" property for this action.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Scope {
        /**
         * <em>request</em> (default) or <em>session</em>
         */
        String value();
    }
    
    /**
     * Instruct Struts to validate the form provided to this method. Equivalent
     * to setting <em>validate</em> property to <em>true</em>.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Validate {
        /**
         * Unnecessary to specify this, as it defaults to <em>true</em> if
         * present, <em>false</em> otherwise.
         */
        boolean value() default true;
    }
}
