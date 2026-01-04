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

public class ReturnKindHandler
        extends BaseHandler {
    ReturnPolicyService returnPolicyService;
    String returnKindName;
    
    /** Creates a new instance of ReturnKindHandler */
    public ReturnKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String returnKindName) {
        super(initialDataParser, parentHandler);
        
        try {
            returnPolicyService = ReturnPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.returnKindName = returnKindName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("returnKindDescription")) {
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
                var commandForm = ReturnPolicyFormFactory.getCreateReturnKindDescriptionForm();
                
                commandForm.setReturnKindName(returnKindName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                checkCommandResult(returnPolicyService.createReturnKindDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }else if(localName.equals("returnPolicy")) {
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
                var commandForm = ReturnPolicyFormFactory.getCreateReturnPolicyForm();
                
                commandForm.setReturnKindName(returnKindName);
                commandForm.setReturnPolicyName(returnPolicyName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                checkCommandResult(returnPolicyService.createReturnPolicy(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new ReturnPolicyHandler(initialDataParser, this, returnKindName, returnPolicyName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("returnReason")) {
            String returnReasonName = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("returnReasonName"))
                    returnReasonName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = ReturnPolicyFormFactory.getCreateReturnReasonForm();
                
                commandForm.setReturnKindName(returnKindName);
                commandForm.setReturnReasonName(returnReasonName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                commandForm.setDescription(description);
                
                checkCommandResult(returnPolicyService.createReturnReason(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new ReturnReasonHandler(initialDataParser, this, returnKindName, returnReasonName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("returnType")) {
            String returnTypeName = null;
            String returnSequenceName = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("returnTypeName"))
                    returnTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("returnSequenceName"))
                    returnSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = ReturnPolicyFormFactory.getCreateReturnTypeForm();
                
                commandForm.setReturnKindName(returnKindName);
                commandForm.setReturnTypeName(returnTypeName);
                commandForm.setReturnSequenceName(returnSequenceName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                commandForm.setDescription(description);
                
                checkCommandResult(returnPolicyService.createReturnType(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new ReturnTypeHandler(initialDataParser, this, returnKindName, returnTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("returnKind")) {
            initialDataParser.popHandler();
        }
    }
    
}
