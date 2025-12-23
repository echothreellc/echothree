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
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Struts", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.echothree.view.client.web.struts.sslext.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.MissingResourceException;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

/**
 * Implements sslext plugin functionality
 */
public class SecurePlugIn implements SecurePlugInInterface {
    
    protected String addSession = DEFAULT_ADD_SESSION;
    protected String httpPort = DEFAULT_HTTP_PORT;
    protected String httpsPort = DEFAULT_HTTPS_PORT;
    protected String enable = DEFAULT_ENABLE;
    
    private Log sLog = LogFactory.getLog(SecurePlugIn.class);
    
    /**
     * The {@link ActionServlet} owning this application.
     */
    private ActionServlet servlet = null;
    
    /**
     * The servlet name under which we are registered in our web application
     * deployment descriptor.
     */
    private String servletName = null;
    
    /**
     * The servlet mappings for the Struts Action Servlet
     */
    private Collection servletMappings = new ArrayList();
    
    /**
     * The set of public identifiers, and corresponding resource names, for
     * the versions of the configuration file DTDs that we know about.  There
     * <strong>MUST</strong> be an even number of Strings in this list!
     */
    protected String registrations[] = {
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN", "/org/apache/struts/resources/web-app_2_2.dtd",
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", "/org/apache/struts/resources/web-app_2_3.dtd"
    };
    
    /**
     * The resources object for our internal resources.
     */
    protected MessageResources resources = null;
    
    
    /**
     * The Java base name of our internal resources.
     * @since Struts 1.1
     */
    protected String resourceName = "org.apache.struts.action.SecureResources";
    
    /**
     * Initialize some instance variables and
     * the ServletContext (application) to make this PlugIn's
     * properties accessible from the whole app.
     *
     * @param servlet The Struts ActionServlet instance for the whole application
     * @param config The ApplicationConfig for our owning sub-application
     * @exception ServletException if we cannot configure ourselves correctly
     */
    @Override
    public void init(ActionServlet servlet, ModuleConfig config)
    throws ServletException {
        this.servlet = servlet;
        
        initMappings();
        servlet.getServletContext().setAttribute(SECURE_PLUGIN, this);
    }
    
    /**
     * Remove stuff from the ServletContext (application).
     */
    @Override
    public void destroy() {
        servlet.getServletContext().removeAttribute(SECURE_PLUGIN);
    }
    
    public void setHttpsPort(String s) {
        this.httpsPort = s;
    }
    
    public void setHttpPort(String s) {
        this.httpPort = s;
    }
    
    @Override
    public String getHttpsPort() {
        return this.httpsPort;
    }
    
    @Override
    public String getHttpPort() {
        return this.httpPort;
    }
    
    @Override
    public Collection getServletMappings() {
        return this.servletMappings;
    }
    
    @Override
    public String getEnable() {
        return enable;
    }
    
    public void setEnable(String s) {
        enable = s;
    }
    
    public String getAddSession() {
        return addSession;
    }
    
    public void setAddSession(String addSession) {
        this.addSession = addSession;
    }
    
    @Override
    public boolean getSslExtAddSession() {
        return Boolean.valueOf(getAddSession());
    }
    
    @Override
    public boolean getSslExtEnable() {
        return Boolean.valueOf(getEnable());
    }
    
    /**
     * Initialize the servlet mappings under which the Struts ActionServlet
     * is accessed.  This will be used when searching for action mappings in the
     * Struts configuration files when creating links, etc.
     */
    protected void initMappings() throws ServletException {
        // Get the action servlet name
        this.servletName = servlet.getServletConfig().getServletName();
        
        // Prepare a Digester to scan the web application deployment descriptor
        var digester = new Digester();
        digester.push(this);
        digester.setNamespaceAware(true);
        digester.setValidating(false);
        
        // Register our local copy of the DTDs that we can find
        for(var i = 0; i < registrations.length; i += 2) {
            var url = this.getClass().getResource(registrations[i + 1]);
            if(url != null) {
                digester.register(registrations[i], url.toString());
            }
        }
        
        // Configure the processing rules that we need
        digester.addCallMethod("web-app/servlet-mapping", "addServletMapping", 2);
        digester.addCallParam("web-app/servlet-mapping/servlet-name", 0);
        digester.addCallParam("web-app/servlet-mapping/url-pattern", 1);
        
        // Process the web application deployment descriptor
        if(sLog.isDebugEnabled()) {
            sLog.debug("Scanning web.xml for ActionServlet URL mappings");
        }
        
        InputStream input = null;
        try {
            input = servlet.getServletContext().getResourceAsStream("/WEB-INF/web.xml");
            digester.parse(input);
        } catch (Throwable e) {
            sLog.error(resources.getMessage("configWebXml"), e);
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Nothing
                }
            }
        }
    }
    
    /**
     * Initialize our internal MessageResources bundle.
     *
     * @exception ServletException if we cannot initialize these resources
     */
    protected void initResources()
    throws ServletException {
        try {
            resources = MessageResources.getMessageResources(resourceName);
        } catch (MissingResourceException e) {
            sLog.error("Cannot load internal resources from '" + resourceName + "'", e);
            throw new UnavailableException("Cannot load internal resources from '" + resourceName + "'");
        }
    }
    
    /**
     * Remember all servlet mapping from our web application deployment
     * descriptor, if it is for the Struts ActionServlet.
     *
     * @param servletName The name of the servlet being mapped
     * @param urlPattern The URL pattern to which this servlet is mapped
     */
    public void addServletMapping(String servletName, String urlPattern) {
        if(sLog.isDebugEnabled()) {
            sLog.debug("Process servletName=" + servletName + ", urlPattern=" + urlPattern);
        }
        
        if(servletName == null) {
            return;
        }
        
        if(servletName.equals(this.servletName)) {
            this.servletMappings.add(urlPattern);
        }
    }
}
