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

package com.echothree.ui.cli.dataloader.util.data.handler.subscription;

import com.echothree.control.user.subscription.common.SubscriptionUtil;
import com.echothree.control.user.subscription.common.SubscriptionService;
import com.echothree.control.user.subscription.common.form.SubscriptionFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SubscriptionTypeHandler
        extends BaseHandler {
    SubscriptionService subscriptionService;
    String subscriptionKindName;
    String subscriptionTypeName;
    
    /** Creates a new instance of SubscriptionTypeHandler */
    public SubscriptionTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String subscriptionKindName, String subscriptionTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            subscriptionService = SubscriptionUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.subscriptionKindName = subscriptionKindName;
        this.subscriptionTypeName = subscriptionTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("subscriptionTypeDescription")) {
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = SubscriptionFormFactory.getCreateSubscriptionTypeDescriptionForm();
                
                commandForm.setSubscriptionKindName(subscriptionKindName);
                commandForm.setSubscriptionTypeName(subscriptionTypeName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                subscriptionService.createSubscriptionTypeDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("subscriptionTypeChain")) {
            String chainTypeName = null;
            String chainName = null;
            String unitOfMeasureTypeName = null;
            String remainingTime = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("chainTypeName"))
                    chainTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("chainName"))
                    chainName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unitOfMeasureTypeName"))
                    unitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("remainingTime"))
                    remainingTime = attrs.getValue(i);
            }
            
            try {
                var commandForm = SubscriptionFormFactory.getCreateSubscriptionTypeChainForm();
                
                commandForm.setSubscriptionKindName(subscriptionKindName);
                commandForm.setSubscriptionTypeName(subscriptionTypeName);
                commandForm.setChainTypeName(chainTypeName);
                commandForm.setChainName(chainName);
                commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                commandForm.setRemainingTime(remainingTime);
                
                subscriptionService.createSubscriptionTypeChain(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("subscriptionType")) {
            initialDataParser.popHandler();
        }
    }
    
}
