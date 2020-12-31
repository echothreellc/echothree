// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
package com.echothree.view.client.web.struts.sprout.support;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.ContextLoaderPlugIn;
import org.springframework.web.util.WebUtils;

/**
 * Convenience class for Spring-aware JSP Taglibs.
 *
 * <p>Provides a reference to the current Spring application context, e.g.
 * for bean lookup or resource loading. Auto-detects a ContextLoaderPlugIn
 * context, falling back to the root WebApplicationContext. For typical
 * usage, i.e. accessing middle tier beans, use a root WebApplicationContext.</p>
 *
 * @author Juergen Hoeller
 * @author Seth Fitzsimmons
 * @see ContextLoaderPlugIn#SERVLET_CONTEXT_PREFIX
 * @see WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
 * @see org.springframework.web.context.ContextLoaderListener
 */
public abstract class SpringBodyTagSupport
        extends BodyTagSupport {
	private WebApplicationContext webApplicationContext;

	private MessageSourceAccessor messageSourceAccessor;
	
	private static final Logger log = Logger.getLogger( SpringBodyTagSupport.class );

	/**
	 * Initialize the WebApplicationContext for this Action.
	 * Invokes onInit after successful initialization of the context.
	 * @see #initWebApplicationContext
	 * @see #onInit
	 */
        @Override
	public void setPageContext(final PageContext pageContext) {
	    super.setPageContext( pageContext );
	    if ( null != pageContext ) {
		    this.webApplicationContext = initWebApplicationContext( pageContext );
		    this.messageSourceAccessor = new MessageSourceAccessor(this.webApplicationContext);
		    onInit();
	    } else {
	        log.warn("Setting a NULL PageContext.");
	    }
	}

	/**
	 * Fetch ContextLoaderPlugIn's WebApplicationContext from the ServletContext,
	 * falling back to the root WebApplicationContext (the usual case).
	 * @return the WebApplicationContext
	 * @throws IllegalStateException if no WebApplicationContext could be found
	 * @see ContextLoaderPlugIn#SERVLET_CONTEXT_PREFIX
	 * @see WebApplicationContextUtils#getWebApplicationContext
	 */
	protected WebApplicationContext initWebApplicationContext(final PageContext pageContext)
            throws IllegalStateException {
	    final ServletContext sc = pageContext.getServletContext();
		WebApplicationContext wac = (WebApplicationContext)
				sc.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		if ( null == wac ) {
			wac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		}
		return wac;
	}

	/**
	 * Return the current Spring WebApplicationContext.
	 */
	protected final WebApplicationContext getWebApplicationContext() {
		return this.webApplicationContext;
	}

	/**
	 * Return a MessageSourceAccessor for the application context
	 * used by this object, for easy message access.
	 */
	protected final MessageSourceAccessor getMessageSourceAccessor() {
		return this.messageSourceAccessor;
	}

	/**
	 * Return the current ServletContext.
	 */
	protected final ServletContext getServletContext() {
		return this.webApplicationContext.getServletContext();
	}

	/**
	 * Return the temporary directory for the current web application,
	 * as provided by the servlet container.
	 * @return the File representing the temporary directory
	 */
	protected final File getTempDir() {
		return WebUtils.getTempDir(getServletContext());
	}

	/**
	 * Callback for custom initialization after the context has been set up.
	 * @see #setPageContext
	 */
	protected void onInit() {}
}
