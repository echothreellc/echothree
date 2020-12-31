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

package com.echothree.ui.cli.dataloader.data.handler.cancellationpolicy;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyService;
import com.echothree.control.user.cancellationpolicy.common.form.CancellationPolicyFormFactory;
import com.echothree.control.user.cancellationpolicy.common.form.CreateCancellationPolicyReasonForm;
import com.echothree.control.user.cancellationpolicy.common.form.CreateCancellationReasonDescriptionForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CancellationReasonHandler
        extends BaseHandler {
    CancellationPolicyService cancellationPolicyService;
    String cancellationKindName;
    String cancellationReasonName;
    
    /** Creates a new instance of CancellationReasonHandler */
    public CancellationReasonHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String cancellationKindName, String cancellationReasonName) {
        super(initialDataParser, parentHandler);
        
        try {
            cancellationPolicyService = CancellationPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.cancellationKindName = cancellationKindName;
        this.cancellationReasonName = cancellationReasonName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("cancellationReasonDescription")) {
            String languageIsoName = null;
            String description = null;
            
            int attrCount = attrs.getLength();
            for(int i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateCancellationReasonDescriptionForm commandForm = CancellationPolicyFormFactory.getCreateCancellationReasonDescriptionForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setCancellationReasonName(cancellationReasonName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                checkCommandResult(cancellationPolicyService.createCancellationReasonDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("cancellationPolicyReason")) {
            String cancellationPolicyName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int attrCount = attrs.getLength();
            for(int i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("cancellationPolicyName"))
                    cancellationPolicyName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateCancellationPolicyReasonForm commandForm = CancellationPolicyFormFactory.getCreateCancellationPolicyReasonForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setCancellationPolicyName(cancellationPolicyName);
                commandForm.setCancellationReasonName(cancellationReasonName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                checkCommandResult(cancellationPolicyService.createCancellationPolicyReason(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("cancellationReason")) {
            initialDataParser.popHandler();
        }
    }
    
}
