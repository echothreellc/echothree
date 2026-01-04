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

package com.echothree.ui.cli.mailtransfer.util.blogentry;

import java.util.Locale;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.filters.DefaultFilter;

public class BodyContentsOnlyFilter
        extends DefaultFilter {

    boolean insideBody;

    final void init() {
        insideBody = false;
    }

    BodyContentsOnlyFilter() {
        super();
        init();
    }

    @Override
    public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
            throws XNIException {
        var eName = element.rawname.toLowerCase(Locale.getDefault());

        if(eName.equals("body")) {
            insideBody = true;
        } else {
            if(insideBody) {
                super.startElement(element, attributes, augs);
            }
        }
    }

    @Override
    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
            throws XNIException {
        if(insideBody) {
            super.emptyElement(element, attributes, augs);
        }
    }

    @Override
    public void characters(XMLString text, Augmentations augs)
        throws XNIException {
        if(insideBody) {
            super.characters(text, augs);
        }
    }

    @Override
    public void ignorableWhitespace(XMLString text, Augmentations augs)
        throws XNIException {
        if(insideBody) {
            super.ignorableWhitespace(text, augs);
        }
    }

    @Override
    public void startGeneralEntity(String name, XMLResourceIdentifier id, String encoding, Augmentations augs)
        throws XNIException {
        if(insideBody) {
            super.startGeneralEntity(name, id, encoding, augs);
        }
    }

    @Override
    public void textDecl(String version, String encoding, Augmentations augs)
        throws XNIException {
        if(insideBody) {
            super.textDecl(version, encoding, augs);
        }
    }

    @Override
    public void endGeneralEntity(String name, Augmentations augs)
        throws XNIException {
        if(insideBody) {
            super.endGeneralEntity(name, augs);
        }
    }

    @Override
    public void startCDATA(Augmentations augs)
            throws XNIException {
        if(insideBody) {
            super.startCDATA(augs);
        }
    }

    @Override
    public void endCDATA(Augmentations augs)
            throws XNIException {
        if(insideBody) {
            super.endCDATA(augs);
        }
    }

    @Override
    public void endElement(QName element, Augmentations augs)
            throws XNIException {
        var eName = element.rawname.toLowerCase(Locale.getDefault());

        if(eName.equals("body")) {
            insideBody = false;
        } else {
            if(insideBody) {
                super.endElement(element, augs);
            }
        }
    }

}
