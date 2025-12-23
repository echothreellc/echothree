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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.filters.ElementRemover;

public class FilterHtmlTag
        extends BaseTag {
    
    protected ElementRemover htmlFilter;
    protected String value;
    protected String var;
    protected int scope;
    
    private void init() {
        htmlFilter = null;
        value = null;
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }
    
    /** Creates a new instance of FilterHtmlTag */
    public FilterHtmlTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setHtmlFilter(ElementRemover htmlFilter) {
        this.htmlFilter = htmlFilter;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    private QName[] toQNames(final String[] tags) {
        final var qnames = new QName[tags.length];
        for(var i = 0; i < tags.length; ++i) {
            qnames[i] = new QName(null, tags[i], null, null);
        }

        return qnames;
    }

    private String replaceImageURLs(XMLInputSource source)
            throws IOException {
        XMLParserConfiguration parser = new HTMLConfiguration();
        var stringWriter = new StringWriter();
        var writer = new org.cyberneko.html.filters.Writer(stringWriter, "UTF-8");

        XMLDocumentFilter[] filters = {
            htmlFilter,
            writer
        };

        parser.setProperty("http://cyberneko.org/html/properties/filters", filters);
        parser.setProperty("http://cyberneko.org/html/properties/balance-tags/fragment-context-stack", toQNames(new String[] { "html", "body" }));

        parser.setFeature("http://xml.org/sax/features/namespaces", false);
        parser.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment", true);

        parser.parse(source);

        return stringWriter.toString();
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        try {
        pageContext.setAttribute(var, replaceImageURLs(new XMLInputSource(null, null, null, new StringReader(value), null)), scope);
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }

        return SKIP_BODY;
    }

}
