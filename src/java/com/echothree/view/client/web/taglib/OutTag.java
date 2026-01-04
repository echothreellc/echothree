// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.util.common.string.StringUtils;
import com.echothree.view.client.web.taglib.util.AbsoluteUrlFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;

public class OutTag
        extends BaseTag {
    
    Log log = LogFactory.getLog(this.getClass());

    protected String value;
    protected String mimeTypeName;
    protected String absoluteUrls;
    protected String var;
    protected int scope;

    private void init() {
        value = null;
        mimeTypeName = null;
        absoluteUrls = null;
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }

    /** Creates a new instance of OutTag */
    public OutTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void setMimeTypeName(String mimeTypeName) {
        this.mimeTypeName = mimeTypeName;
    }
    
    public void setAbsoluteUrls(String absoluteUrls) {
        this.absoluteUrls = absoluteUrls;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }

    protected QName[] toQNames(final String[] tags) {
        final var qnames = new QName[tags.length];
        
        for(var i = 0; i < tags.length; ++i) {
            qnames[i] = new QName(null, tags[i], null, null);
        }

        return qnames;
    }

    protected String absolutifyUrls(final String html)
            throws JspException {
        XMLParserConfiguration parser = new HTMLConfiguration();
        var stringWriter = new StringWriter();
        var writer = new org.cyberneko.html.filters.Writer(stringWriter, "UTF-8");

        XMLDocumentFilter[] filters = {
            new AbsoluteUrlFilter(pageContext),
            writer
        };

        parser.setProperty("http://cyberneko.org/html/properties/filters", filters);
        parser.setProperty("http://cyberneko.org/html/properties/balance-tags/fragment-context-stack", toQNames(new String[] { "html", "body" }));

        parser.setFeature("http://xml.org/sax/features/namespaces", false);
        parser.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment", true);

        try {
            parser.parse(new XMLInputSource(null, null, null, new StringReader(html), null));
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }

        return stringWriter.toString();
    }

    /**
     * @return <CODE>SKIP_BODY</CODE>
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doStartTag()
            throws JspException {

        if(value == null) {
            log.error("value == null");
        } else if(mimeTypeName == null) {
            log.error("mimeTypeName == null");
        } else {
            var html = StringUtils.getInstance().convertToHtml(value, mimeTypeName);

            if(absoluteUrls != null) {
                if(Boolean.parseBoolean(absoluteUrls)) {
                    html = absolutifyUrls(html);
                }
            }

            if(var == null) {
                TagUtils.getInstance().write(pageContext, html);
            } else {
                pageContext.setAttribute(var, html, scope);
            }
        }
        
        return SKIP_BODY;
    }

}
