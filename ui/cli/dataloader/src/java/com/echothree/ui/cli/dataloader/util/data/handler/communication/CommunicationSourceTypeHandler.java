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

package com.echothree.ui.cli.dataloader.util.data.handler.communication;

import com.echothree.control.user.communication.common.CommunicationUtil;
import com.echothree.control.user.communication.common.CommunicationService;
import com.echothree.control.user.communication.common.form.CommunicationFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CommunicationSourceTypeHandler
        extends BaseHandler {

    CommunicationService communicationService;
    String communicationSourceTypeName;
    
    /** Creates a new instance of CommunicationSourceTypeHandler */
    public CommunicationSourceTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String communicationSourceTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        communicationService = lookupService(CommunicationService.class, CommunicationUtil::getHome);
        
        this.communicationSourceTypeName = communicationSourceTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("communicationSourceTypeDescription")) {
            var form = CommunicationFormFactory.getCreateCommunicationSourceTypeDescriptionForm();

            form.setCommunicationSourceTypeName(communicationSourceTypeName);
            form.set(getAttrsMap(attrs));

            checkCommandResult(form, communicationService.createCommunicationSourceTypeDescription(initialDataParser.getUserVisit(), form));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("communicationSourceType")) {
            initialDataParser.popHandler();
        }
    }
    
}
