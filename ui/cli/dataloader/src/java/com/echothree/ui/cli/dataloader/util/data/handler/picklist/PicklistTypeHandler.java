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

public class PicklistTypeHandler
        extends BaseHandler {
    PicklistService picklistService;
    String picklistTypeName;
    
    /** Creates a new instance of PicklistTypeHandler */
    public PicklistTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String picklistTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            picklistService = PicklistUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.picklistTypeName = picklistTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("picklistTypeDescription")) {
            String attrLanguageIsoName = null;
            String attrDescription = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    attrLanguageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            try {
                var createPicklistTypeDescriptionForm = PicklistFormFactory.getCreatePicklistTypeDescriptionForm();
                
                createPicklistTypeDescriptionForm.setPicklistTypeName(picklistTypeName);
                createPicklistTypeDescriptionForm.setLanguageIsoName(attrLanguageIsoName);
                createPicklistTypeDescriptionForm.setDescription(attrDescription);
                
                checkCommandResult(picklistService.createPicklistTypeDescription(initialDataParser.getUserVisit(), createPicklistTypeDescriptionForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("picklistTimeType")) {
            var commandForm = PicklistFormFactory.getCreatePicklistTimeTypeForm();

            commandForm.setPicklistTypeName(picklistTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(picklistService.createPicklistTimeType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new PicklistTimeTypeHandler(initialDataParser, this, picklistTypeName, commandForm.getPicklistTimeTypeName()));
        } else if(localName.equals("picklistAliasType")) {
            String picklistAliasTypeName = null;
            String isDefault = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("picklistAliasTypeName"))
                    picklistAliasTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var createPicklistAliasTypeForm = PicklistFormFactory.getCreatePicklistAliasTypeForm();
                
                createPicklistAliasTypeForm.setPicklistTypeName(picklistTypeName);
                createPicklistAliasTypeForm.setPicklistAliasTypeName(picklistAliasTypeName);
                createPicklistAliasTypeForm.setIsDefault(isDefault);
                createPicklistAliasTypeForm.setSortOrder(sortOrder);
                
                checkCommandResult(picklistService.createPicklistAliasType(initialDataParser.getUserVisit(), createPicklistAliasTypeForm));
                
                initialDataParser.pushHandler(new PicklistAliasTypeHandler(initialDataParser, this, picklistTypeName, picklistAliasTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("picklistType")) {
            initialDataParser.popHandler();
        }
    }
    
}
