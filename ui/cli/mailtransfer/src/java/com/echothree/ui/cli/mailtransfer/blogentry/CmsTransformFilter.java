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

package com.echothree.ui.cli.mailtransfer.blogentry;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.filters.DefaultFilter;

public class CmsTransformFilter
        extends DefaultFilter {

    String cmsBaseUrl;

    CmsTransformFilter(String cmsBaseUrl) {
        super();

        this.cmsBaseUrl = cmsBaseUrl;
    }

    @Override
    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
            throws XNIException {
        String eName = element.rawname.toLowerCase();

        if(eName.equals("img")) {
            int attributeCount = attributes.getLength();

            for(int i = 0; i < attributeCount; i++) {
                String aname = attributes.getQName(i).toLowerCase();

                if(aname.toLowerCase().equals("src")) {
                    String imgSrc = attributes.getValue(i);

                    if(imgSrc.startsWith("/cms/")) {
                        attributes.setValue(i, cmsBaseUrl + imgSrc);
                    }

                    // attributes.setValue(i, transformedURL);
                }
            }
        }

        super.emptyElement(element, attributes, augs);
    }

}
