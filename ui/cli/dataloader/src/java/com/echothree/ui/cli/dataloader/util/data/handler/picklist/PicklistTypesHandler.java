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

package com.echothree.ui.cli.dataloader.util.data.handler.picklist;

import com.echothree.control.user.picklist.common.PicklistUtil;
import com.echothree.control.user.picklist.common.PicklistService;
import com.echothree.control.user.picklist.common.form.PicklistFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PicklistTypesHandler
        extends BaseHandler {

    PicklistService picklistService;
    
    /** Creates a new instance of PicklistTypesHandler */
    public PicklistTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            picklistService = PicklistUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("picklistType")) {
            var commandForm = PicklistFormFactory.getCreatePicklistTypeForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(picklistService.createPicklistType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new PicklistTypeHandler(initialDataParser, this, commandForm.getPicklistTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("picklistTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
