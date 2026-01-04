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

package com.echothree.view.client.web.taglib.util;

import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.filters.DefaultFilter;

public class AbsoluteUrlFilter
        extends DefaultFilter {

    String base;

    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;

    /** Creates a new instance of AbsoluteUrlFilter */
    public AbsoluteUrlFilter(PageContext pageContest) {
        super();

        buildBase(pageContest.getRequest());
    }

    private void buildBase(ServletRequest servletRequest) {
        var url = new StringBuilder();
        var scheme = servletRequest.getScheme();
        var serverName = servletRequest.getServerName();
        var localPort = servletRequest.getLocalPort();

        url.append(scheme).append("://").append(serverName);

        if(("http".equals(scheme) && !(HTTP_PORT == localPort)) || ("https".equals(scheme) && !(HTTPS_PORT == localPort))) {
            url.append(":").append(localPort);
        }

        base = url.toString();
    }

    private String absoluteifyUrl(String url) {
        return url == null ? null : url.length() > 0 && url.toCharArray()[0] == '/' ? base + url : url;
    }

    private static Map<String, Set<String>> elementsAndAttributes;

    static {
        elementsAndAttributes = new HashMap<>();
        elementsAndAttributes.put("a", Sets.newHashSet("href"));
        elementsAndAttributes.put("img", Sets.newHashSet("src"));
    }

    private void filterElementAndAttributes(QName element, XMLAttributes attributes) {
        var attributesSet = elementsAndAttributes.get(element.rawname.toLowerCase(Locale.getDefault()));

        if(attributesSet != null) {
            var attributeCount = attributes.getLength();

            for(var i = 0; i < attributeCount; i++) {
                if(attributesSet.contains(attributes.getQName(i).toLowerCase(Locale.getDefault()))) {
                     attributes.setValue(i, absoluteifyUrl(attributes.getValue(i)));
                 }
            }
        }
    }

    @Override
    public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
            throws XNIException {
        filterElementAndAttributes(element, attributes);

        super.startElement(element, attributes, augs);
    }

    @Override
    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
            throws XNIException {
        filterElementAndAttributes(element, attributes);

        super.emptyElement(element, attributes, augs);
    }

}
