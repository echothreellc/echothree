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

package com.echothree.ui.cli.dataloader.util.data.handler.returnpolicy;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyService;
import com.echothree.control.user.returnpolicy.common.form.ReturnPolicyFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ReturnReasonHandler
        extends BaseHandler {
    ReturnPolicyService returnPolicyService;
    String returnKindName;
    String returnReasonName;
    
    /** Creates a new instance of ReturnReasonHandler */
    public ReturnReasonHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String returnKindName, String returnReasonName) {
        super(initialDataParser, parentHandler);
        
        try {
            returnPolicyService = ReturnPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.returnKindName = returnKindName;
        this.returnReasonName = returnReasonName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("returnReasonDescription")) {
            String languageIsoName = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = ReturnPolicyFormFactory.getCreateReturnReasonDescriptionForm();
                
                commandForm.setReturnKindName(returnKindName);
                commandForm.setReturnReasonName(returnReasonName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                checkCommandResult(returnPolicyService.createReturnReasonDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("returnPolicyReason")) {
            String returnPolicyName = null;
            String isDefault = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("returnPolicyName"))
                    returnPolicyName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = ReturnPolicyFormFactory.getCreateReturnPolicyReasonForm();
                
                commandForm.setReturnKindName(returnKindName);
                commandForm.setReturnPolicyName(returnPolicyName);
                commandForm.setReturnReasonName(returnReasonName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                checkCommandResult(returnPolicyService.createReturnPolicyReason(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("returnReason")) {
            initialDataParser.popHandler();
        }
    }
    
}
