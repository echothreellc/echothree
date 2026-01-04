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

package com.echothree.view.client.web.struts.sslext.taglib;

import com.echothree.view.client.web.struts.sslext.util.SecureRequestUtils;
import java.net.MalformedURLException;
import java.util.HashMap;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.LinkTag;
import org.apache.struts.taglib.logic.IterateTag;

/**
 * Defines the link tag of the sslext tag library
 */
public class SecureLinkTag
        extends LinkTag {
    
    /**
     * Return the complete URL to which this hyperlink will direct the user.
     *
     * @exception JspException if an exception is thrown calculating the value
     */
    @Override
    protected String calculateURL() throws JspException {
        var tagUtils = TagUtils.getInstance();
        
        // Identify the parameters we will add to the completed URL
        var params = tagUtils.computeParameters(pageContext, paramId, paramName, paramProperty, paramScope, name, property, scope, transaction);
        
        // if "indexed=true", add "index=x" parameter to query string
        if(indexed) {
            // look for outer iterate tag
            var iterateTag = (IterateTag) findAncestorWithClass(this, IterateTag.class);
            
            if(iterateTag == null) {
                // This tag should only be nested in an iterate tag
                // If it's not, throw exception
                var e = new JspException(messages.getMessage("indexed.noEnclosingIterate"));
                tagUtils.saveException(pageContext, e);
                throw e;
            }
            
            //calculate index, and add as a parameter
            if(params == null) {
                // create new HashMap if no other params
                params = new HashMap();
            }
            
            if(indexId != null) {
                params.put(indexId, Integer.toString(iterateTag.getIndex()));
            } else {
                params.put("index", Integer.toString(iterateTag.getIndex()));
            }
        }
        
        String url;
        try {
            url = SecureRequestUtils.computeURL(pageContext, forward, href, page, action, params, anchor, false);
        } catch (MalformedURLException e) {
            tagUtils.saveException(pageContext, e);
            throw new JspException(messages.getMessage("rewrite.url", e.toString()));
        }
        
        return url;
    }
    
}
