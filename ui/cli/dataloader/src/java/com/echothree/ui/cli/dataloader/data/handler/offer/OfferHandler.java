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

package com.echothree.ui.cli.dataloader.data.handler.offer;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.OfferService;
import com.echothree.control.user.offer.common.form.CreateOfferChainTypeForm;
import com.echothree.control.user.offer.common.form.CreateOfferCustomerTypeForm;
import com.echothree.control.user.offer.common.form.CreateOfferDescriptionForm;
import com.echothree.control.user.offer.common.form.CreateOfferItemForm;
import com.echothree.control.user.offer.common.form.CreateOfferUseForm;
import com.echothree.control.user.offer.common.form.OfferFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OfferHandler
        extends BaseHandler {
    OfferService offerService;
    String offerName;
    
    /** Creates a new instance of OfferHandler */
    public OfferHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String offerName) {
        super(initialDataParser, parentHandler);
        
        try {
            offerService = OfferUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.offerName = offerName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("offerDescription")) {
            CreateOfferDescriptionForm commandForm = OfferFormFactory.getCreateOfferDescriptionForm();
            
            commandForm.setOfferName(offerName);
            commandForm.set(getAttrsMap(attrs));
            
            offerService.createOfferDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("offerUse")) {
            CreateOfferUseForm commandForm = OfferFormFactory.getCreateOfferUseForm();
            
            commandForm.setOfferName(offerName);
            commandForm.set(getAttrsMap(attrs));
            
            offerService.createOfferUse(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new OfferUseHandler(initialDataParser, this, offerName, commandForm.getUseName()));
        } else if(localName.equals("offerItem")) {
            CreateOfferItemForm commandForm = OfferFormFactory.getCreateOfferItemForm();
            
            commandForm.setOfferName(offerName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(offerService.createOfferItem(initialDataParser.getUserVisit(), commandForm));
            
            initialDataParser.pushHandler(new OfferItemHandler(initialDataParser, this, offerName, commandForm.getItemName()));
        } else if(localName.equals("offerCustomerType")) {
            CreateOfferCustomerTypeForm commandForm = OfferFormFactory.getCreateOfferCustomerTypeForm();
            
            commandForm.setOfferName(offerName);
            commandForm.set(getAttrsMap(attrs));
            
            offerService.createOfferCustomerType(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("offerChainType")) {
            CreateOfferChainTypeForm commandForm = OfferFormFactory.getCreateOfferChainTypeForm();
            
            commandForm.setOfferName(offerName);
            commandForm.set(getAttrsMap(attrs));
            
            offerService.createOfferChainType(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("offer")) {
            initialDataParser.popHandler();
        }
    }
    
}
