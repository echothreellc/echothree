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
package com.echothree.view.client.web.struts.sslext.util;

import com.echothree.view.client.web.struts.sslext.action.SecurePlugInInterface;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;

/**
 * Define some additional utility methods utilized by sslext.
 */
public class SecureRequestUtils {
    
    /**
     * The message resources.
     */
    protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
    
    private static Log sLog = LogFactory.getLog(SecureRequestUtils.class);
    
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String STD_HTTP_PORT = "80";
    private static final String STD_HTTPS_PORT = "443";
    
    private static final String STOWED_REQUEST_ATTRIBS = "ssl.redirect.attrib.stowed";
    
    /**
     * Compute a hyperlink URL based on the <code>forward</code>,
     * <code>href</code>, or <code>page</code> parameter that is not null.
     * The returned URL will have already been passed to
     * <code>response.encodeURL()</code> for adding a session identifier.
     *
     * @param pageContext PageContext for the tag making this call
     *
     * @param forward Logical forward name for which to look up
     *  the context-relative URI (if specified)
     * @param href URL to be utilized unmodified (if specified)
     * @param page Context-relative page for which a URL should
     *  be created (if specified)
     * @param action a Struts action name
     *
     * @param params Map of parameters to be dynamically included (if any)
     * @param anchor Anchor to be dynamically included (if any)
     *
     * @param redirect Is this URL for a <code>response.sendRedirect()</code>?
     *
     * @exception MalformedURLException if a URL cannot be created
     *  for the specified parameters
     */
    public static String computeURL(PageContext pageContext, String forward, String href, String page, String action, Map params, String anchor, boolean redirect)
    throws MalformedURLException {
        var url = new StringBuilder(TagUtils.getInstance().computeURL(pageContext, forward, href, page, action, null, params, anchor, redirect));
        var request = (HttpServletRequest)pageContext.getRequest();
        
        // Get the action servlet's context, we'll need it later
        var servletContext = pageContext.getServletContext();
        var contextPath = request.getContextPath();
        var securePlugin = (SecurePlugInInterface)servletContext.getAttribute(SecurePlugInInterface.SECURE_PLUGIN);
        if(securePlugin.getSslExtEnable() && url.toString().startsWith(contextPath)) {
            
            // Initialize the scheme and ports we are using
            var usingScheme = request.getScheme();
            var usingPort = String.valueOf(request.getServerPort());
            
            // Get the servlet context relative link URL
            var linkString = url.toString().substring(contextPath.length());
            
            // See if link references an action somewhere in our app
            var secureConfig = getActionConfig(pageContext, linkString);
            
            // If link is an action, find the desired port and scheme
            if(secureConfig != null && !SecureActionMapping.ANY.equalsIgnoreCase(secureConfig.getSecure())) {

                var desiredScheme = Boolean.valueOf(secureConfig.getSecure()) ? HTTPS : HTTP;
                var desiredPort = Boolean.valueOf(secureConfig.getSecure()) ? securePlugin.getHttpsPort() : securePlugin.getHttpPort();
                
                // If scheme and port we are using do not match the ones we want
                if((!desiredScheme.equals(usingScheme) || !desiredPort.equals(usingPort))) {
                    url.insert(0, startNewUrlString(request, desiredScheme, desiredPort));
                    
                    // This is a hack to help us overcome the problem that some
                    // older browsers do not share sessions between http & https
                    // If this feature is diabled, session ID could still be added
                    // the previous call to the RequestUtils.computeURL() method,
                    // but only if needed due to cookies disabled, etc.
                    if(securePlugin.getSslExtAddSession() && url.toString().indexOf(";jsessionid=") < 0) {
                        // Add the session identifier
                        url = new StringBuilder(toEncoded(url.toString(),
                        request.getSession().getId()));
                    }
                }
            }
        }
        
        return url.toString();
    }
    
    /**
     * Finds the configuration definition for the specified action link
     * @param pageContext the current page context.
     * @param linkString The action we are searching for, specified as a link. (i.e. may include "..")
     * @return The SecureActionMapping object entry for this action, or null if not found
     */
    private static SecureActionMapping getActionConfig(PageContext pageContext, String linkString) {
        var moduleConfig = SecureRequestUtils.selectModule(linkString, pageContext);
        
        // Strip off the subapp path, if any
        linkString = linkString.substring(moduleConfig.getPrefix().length());
        
        // Get all the servlet mappings for the ActionServlet, loop thru to find
        // the correct action being specified
        var servletContext = pageContext.getServletContext();
        var spi = (SecurePlugInInterface)servletContext.getAttribute(SecurePlugInInterface.SECURE_PLUGIN);
        var mappingItr = spi.getServletMappings().iterator();
        
        while(mappingItr.hasNext()) {
            var servletMapping = (String) mappingItr.next();

            var starIndex = servletMapping != null ? servletMapping.indexOf('*') : -1;
            if(starIndex == -1) {
                continue;
            }// No servlet mapping or no usable pattern defined, short circuit

            var prefix = servletMapping.substring(0, starIndex);
            var suffix = servletMapping.substring(starIndex + 1);
            
            // Strip off the jsessionid, if any
            var jsession = linkString.indexOf(";jsessionid=");
            if(jsession >= 0) {
                linkString = linkString.substring(0, jsession);
            }
            
            // Strip off the anchor, if any
            var anchor = linkString.indexOf('#');
            if(anchor >= 0) {
                linkString = linkString.substring(0, anchor);
            }
            
            // Strip off the query string, if any
            var question = linkString.indexOf('?');
            if(question >= 0) {
                linkString = linkString.substring(0, question);
            }
            
            // Unable to establish this link as an action, short circuit
            if(!(linkString.startsWith(prefix) && linkString.endsWith(suffix))) {
                continue;
            }
            
            // Chop off prefix and suffix
            linkString = linkString.substring(prefix.length());
            linkString = linkString.substring(0, linkString.length() - suffix.length());
            if(!linkString.startsWith("/")) {
                linkString = "/" + linkString;
            }

            var secureConfig = (SecureActionMapping) moduleConfig.findActionConfig(linkString);
            
            return secureConfig;
        }
        
        return null;
    }
    
    /**
     * Builds the protocol, server name, and port portion of the new URL
     * @param request The current request
     * @param desiredScheme  The scheme (http or https) to be used in the new URL
     * @param desiredPort The port number to be used in th enew URL
     * @return The new URL as a StringBuilder
     */
    private static StringBuilder startNewUrlString(HttpServletRequest request, String desiredScheme, String desiredPort) {
        var url = new StringBuilder();
        var serverName = request.getServerName();
        
        url.append(desiredScheme).append("://").append(serverName);
        
        if((HTTP.equals(desiredScheme) && !STD_HTTP_PORT.equals(desiredPort)) || (HTTPS.equals(desiredScheme) && !STD_HTTPS_PORT.equals(desiredPort))) {
            url.append(":").append(desiredPort);
        }
        
        return url;
    }
    
    
    /**
     * Creates query String from request body parameters
     * @param aRequest The current request
     * @return The created query string (with no leading "?")
     */
    public static String getRequestParameters(HttpServletRequest aRequest) {
        var m = getParameterMap(aRequest);
        
        return createQueryStringFromMap(m, "&").toString();
    }
    
    /**
     * Builds a query string from a given map of parameters
     * @param m A map of parameters
     * @param ampersand String to use for ampersands (e.g. "&" or "&amp;" )
     * @return query string (with no leading "?")
     */
    public static StringBuilder createQueryStringFromMap(Map m, String ampersand) {
        var aReturn = new StringBuilder("");
        var aEntryS = m.entrySet();

        for(final var entry : aEntryS) {
            var aEntry = (Map.Entry)entry;
            var value = aEntry.getValue();
            var aValues = new String[1];
            if(value == null) {
                aValues[0] = "";
            } else if(value instanceof List aList) { // Work around for Weblogic 6.1sp1
                aValues = (String[])aList.toArray(new String[aList.size()]);
            } else if(value instanceof String aString) {  // Single value from Struts tags
                aValues[0] = aString;
            } else { // String array, the standard returned from request.getParameterMap()
                aValues = (String[])value;  // This is the standard
            }
            for(var aValue : aValues) {
                append(aEntry.getKey(), aValue, aReturn, ampersand);
            }
        }
        
        return aReturn;
    }
    
    /**
     * Appends new key and value pair to query string
     * @param key parameter name
     * @param value value of parameter
     * @param queryString existing query string
     * @param ampersand string to use for ampersand (e.g. "&" or "&amp;")
     * @return query string (with no leading "?")
     */
    private static StringBuilder append(Object key, Object value, StringBuilder queryString, String ampersand) {
        var tagUtils = TagUtils.getInstance();
        
        if(queryString.length() > 0) {
            queryString.append(ampersand);
        }
        queryString.append(tagUtils.encodeURL(key.toString()));
        queryString.append("=");
        queryString.append(tagUtils.encodeURL(value.toString()));
        
        return queryString;
    }
    
    /**
     * Stores request attributes in session
     * @param aRequest The current request
     * @return true, if the attributes were stowed in the session,
     * false otherwise
     */
    public static boolean stowRequestAttributes(HttpServletRequest aRequest) {
        if(aRequest.getSession().getAttribute(STOWED_REQUEST_ATTRIBS) != null) {
            return false;
        }
        
        Enumeration enumeration = aRequest.getAttributeNames();
        Map map = new HashMap();
        while(enumeration.hasMoreElements()) {
            var name = (String) enumeration.nextElement();
            map.put(name, aRequest.getAttribute(name));
        }
        aRequest.getSession().setAttribute(STOWED_REQUEST_ATTRIBS, map);
        
        return true;
    }
    
    
    /**
     * Reclaims request attributes from session to request
     * @param aRequest The current request
     * @param doRemove True, if the attributes should be removed after being reclaimed,
     * false otherwise
     */
    public static void reclaimRequestAttributes(HttpServletRequest aRequest,
    boolean doRemove) {
        var map = (Map) aRequest.getSession().getAttribute(STOWED_REQUEST_ATTRIBS);
        
        if(map == null) {
            return;
        }

        var itr = map.keySet().iterator();
        while(itr.hasNext()) {
            var name = (String) itr.next();
            
            aRequest.setAttribute(name, map.get(name));
        }
        
        if(doRemove) {
            aRequest.getSession().removeAttribute(STOWED_REQUEST_ATTRIBS);
        }
    }
    
    
    /**
     * Creates a redirect URL string if the current request should be redirected
     * @param request current servlet request
     * @param application the currecnt ServletContext
     * @param isSecure "true" if the current request should be transmitted via SSL
     * "false" if not, "any" if we just don't care if it's SSL or not
     * @return the URL to redirect to
     */
    static public String getRedirectString(HttpServletRequest request, ServletContext application, String isSecure) {
        String urlString = null;
        var securePlugin = (SecurePlugInInterface)application.getAttribute(SecurePlugInInterface.SECURE_PLUGIN);
        var httpPort = securePlugin.getHttpPort();
        var httpsPort = securePlugin.getHttpsPort();
        
        // If sslext disabled, or we don't have a protocol preference,
        // just return the null value we have so far
        if(!securePlugin.getSslExtEnable() || SecureActionMapping.ANY.equalsIgnoreCase(isSecure)) {
            return urlString;
        }
        
        // get the scheme we want to use for this page and
        // get the scheme used in this request
        var desiredScheme = Boolean.valueOf(isSecure) ? HTTPS : HTTP;
        var usingScheme = request.getScheme();
        
        // Determine the port number we want to use
        // and the port number we used in this request
        var desiredPort = Boolean.valueOf(isSecure) ? httpsPort : httpPort;
        var usingPort = String.valueOf(request.getServerPort());
        
        // Must also check ports, because of IE multiple redirect problem
        if(!desiredScheme.equals(usingScheme) || !desiredPort.equals(usingPort)) {
            
            urlString = buildNewUrlString(request,
            desiredScheme,
            desiredPort,
            securePlugin.getSslExtAddSession());
            
            // Temporarily store attributes in session
            if(!SecureRequestUtils.stowRequestAttributes(request)) {
                // If request attributes already stored in session, reclaim them
                // This is a hack for the IE multiple redirect problem
                SecureRequestUtils.reclaimRequestAttributes(request, false);
            }
        } else {
            // Retrieve attributes from session
            SecureRequestUtils.reclaimRequestAttributes(request, true);
        }
        
        return urlString;
    }
    
    
    /**
     * Builds the URL that we will redirect to
     * @param request The current request
     * @param desiredScheme The protocol (http or https) we wish to use in new URL
     * @param desiredPort The port number we wish to use in new URL
     * @return the URL we will redirect to, as a String
     */
    private static String buildNewUrlString(HttpServletRequest request, String desiredScheme, String desiredPort,
    boolean addSessionID) {
        var url = startNewUrlString(request, desiredScheme, desiredPort);
        
        url.append(request.getRequestURI());

        var returnUrl = addQueryString(request, url);
        
        // If the add session ID feature is enabled, add the session ID when creating a new URL
        // Could still be added by the calling checkSsl() method if needed due to disabled cookies, etc.
        if(addSessionID){
            returnUrl = toEncoded( returnUrl, request.getSession().getId());
        }
        
        return returnUrl;
    }
    
    /**
     * Adds the query string, if any, to the given URL.  The query string
     * is either taken from the existing query string or
     * generated from the posting request body parameters.
     * @param request The current request
     * @param url The existing URL we will add the query string to
     * @return The URL with query string
     */
    private static String addQueryString(HttpServletRequest request, StringBuilder url) {
        // add query string, if any
        var queryString = request.getQueryString();
        
        if(queryString != null && queryString.length() != 0) {
            url.append("?").append(queryString);
        } else {
            queryString = SecureRequestUtils.getRequestParameters(request);
            if(queryString != null && queryString.length() != 0) {
                url.append("?").append(queryString);
            }
        }
        
        return url.toString();
    }
    
    
    /**
     * Select the sub-application to which the specified request belongs, and
     * add corresponding request attributes to this request.
     *
     * @param urlPath The requested URL
     * @param pageContext The ServletContext for this web application
     * @return The ModuleConfig for the given URL path
     */
    public static ModuleConfig selectModule(String urlPath, PageContext pageContext) {
        // Get the ServletContext
        var servletContext = pageContext.getServletContext();
        
        // Match against the list of sub-application prefixes
        var prefix = ModuleUtils.getInstance().getModuleName(urlPath, servletContext);
        
        // Expose the resources for this sub-application
        var config = (ModuleConfig) servletContext.getAttribute(Globals.MODULE_KEY + prefix);
        
        return config;
    }
    
    /**
     *  Creates a map of request parameters where the key is the parameter name and the
     *  value is the String array of parameter values.
     *  @param request The current request
     *  @return The map of parameters and their values
     */
    private static Map getParameterMap(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration enumeration = request.getParameterNames();
        
        while(enumeration.hasMoreElements()) {
            var name = (String) enumeration.nextElement();
            var values = request.getParameterValues(name);
            map.put(name, values);
        }
        
        return map;
    }
    
    /** Checks to see if SSL should be toggled for this
     *  action
     *  @param aMapping The mapping object for this Action
     *  @param aContext The current ServletContext
     *  @param aRequest The current request object
     *  @param aResponse The current response object
     *  @return true, if being redirected, false otherwise
     */
    public static boolean checkSsl(SecureActionMapping aMapping, ServletContext aContext, HttpServletRequest aRequest, HttpServletResponse aResponse) {
        // Build a redirect string if needed
        var redirectString = SecureRequestUtils.getRedirectString(aRequest, aContext, aMapping.getSecure());
        
        // If a redirect string was generated, perform the redirect and return true
        if(redirectString != null) {
            try {
                // Redirect the page to the desired URL
                aResponse.sendRedirect(aResponse.encodeRedirectURL(redirectString));
                return true;
            } catch (Exception ioe) {
                sLog.error("IOException in redirect" + ioe.getMessage());
            }
        }
        
        // No redirect performed, return false
        return false;
    }
    
    
    /**
     * Return the specified URL with the specified session identifier
     * suitably encoded.
     *
     * @param url URL to be encoded with the session id
     * @param sessionId Session id to be included in the encoded URL
     */
    private static String toEncoded(String url, String sessionId) {
        if((url == null) || (sessionId == null))
            return (url);

        var path = url;
        var query = "";
        var anchor = "";

        var question = url.indexOf('?');
        if(question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }

        var pound = path.indexOf('#');
        if(pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }

        var sb = new StringBuilder(path);
        if(sb.length() > 0) { // jsessionid can't be first.
            sb.append(";jsessionid=");
            sb.append(sessionId);
        }
        sb.append(anchor);
        sb.append(query);
        
        return sb.toString();
    }
    
}
