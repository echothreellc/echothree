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

public class SubscriptionKindHandler
        extends BaseHandler {
    SubscriptionService subscriptionService;
    String subscriptionKindName;
    
    /** Creates a new instance of SubscriptionKindHandler */
    public SubscriptionKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String subscriptionKindName) {
        super(initialDataParser, parentHandler);
        
        try {
            subscriptionService = SubscriptionUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.subscriptionKindName = subscriptionKindName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("subscriptionKindDescription")) {
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
                var commandForm = SubscriptionFormFactory.getCreateSubscriptionKindDescriptionForm();
                
                commandForm.setSubscriptionKindName(subscriptionKindName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                subscriptionService.createSubscriptionKindDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("subscriptionType")) {
            String subscriptionTypeName = null;
            String subscriptionSequenceName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("subscriptionTypeName"))
                    subscriptionTypeName = attrs.getValue(i);
                if(attrs.getQName(i).equals("subscriptionSequenceName"))
                    subscriptionSequenceName = attrs.getValue(i);
                if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = SubscriptionFormFactory.getCreateSubscriptionTypeForm();
                
                commandForm.setSubscriptionKindName(subscriptionKindName);
                commandForm.setSubscriptionTypeName(subscriptionTypeName);
                commandForm.setSubscriptionSequenceName(subscriptionSequenceName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                subscriptionService.createSubscriptionType(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new SubscriptionTypeHandler(initialDataParser, this, subscriptionKindName, subscriptionTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("subscriptionKind")) {
            initialDataParser.popHandler();
        }
    }
    
}
