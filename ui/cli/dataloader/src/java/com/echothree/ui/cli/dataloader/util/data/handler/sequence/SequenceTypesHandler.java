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

package com.echothree.ui.cli.dataloader.util.data.handler.sequence;

import com.echothree.control.user.sequence.common.SequenceService;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.SequenceFormFactory;
import com.echothree.control.user.sequence.common.spec.SequenceSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SequenceTypesHandler
        extends BaseHandler {

    SequenceService sequenceService;

    /** Creates a new instance of SequenceTypesHandler */
    public SequenceTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            sequenceService = SequenceUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("sequenceType")) {
            var attrsMap = getAttrsMap(attrs);
            // The XML loader clears an omitted prefix.
            attrsMap.putIfAbsent("Prefix", null);

            var outcome = editOrCreate(attrsMap, SequenceSpecFactory.getSequenceTypeSpec(),
                    SequenceFormFactory.getEditSequenceTypeForm(), sequenceService::editSequenceType,
                    SequenceFormFactory.getCreateSequenceTypeForm(), sequenceService::createSequenceType,
                    ExecutionErrors.UnknownSequenceTypeName);

            if(outcome.successful()) {
                var sequenceTypeName = (String)attrsMap.get("SequenceTypeName");
                initialDataParser.pushHandler(new SequenceTypeHandler(initialDataParser, this, sequenceTypeName));
            } else {
                skipCurrentElement();
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("sequenceTypes")) {
            initialDataParser.popHandler();
        }
    }
}
