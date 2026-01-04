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

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.SequenceService;
import com.echothree.control.user.sequence.common.form.SequenceFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SequenceChecksumTypeHandler
        extends BaseHandler {

    SequenceService sequenceService;
    String sequenceChecksumTypeName;

    /** Creates a new instance of SequenceChecksumTypeHandler */
    public SequenceChecksumTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String sequenceChecksumTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            sequenceService = SequenceUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.sequenceChecksumTypeName = sequenceChecksumTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("sequenceChecksumTypeDescription")) {
            var commandForm = SequenceFormFactory.getCreateSequenceChecksumTypeDescriptionForm();

            commandForm.setSequenceChecksumTypeName(sequenceChecksumTypeName);
            commandForm.set(getAttrsMap(attrs));

            sequenceService.createSequenceChecksumTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("sequenceChecksumType")) {
            initialDataParser.popHandler();
        }
    }
}