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

package com.echothree.ui.cli.dataloader.util.data.handler.vendor;

import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.term.common.TermService;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.form.TermFormFactory;
import com.echothree.control.user.vendor.common.VendorService;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.VendorFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.purchase.PurchaseInvoicesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class VendorHandler
        extends BaseHandler {

    PartyService partyService = PartyUtil.getHome();
    TermService termService = TermUtil.getHome();
    VendorService vendorService = VendorUtil.getHome();

    String partyName;
    String vendorName;
    String entityRef;
    
    /** Creates a new instance of VendorHandler */
    public VendorHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String vendorName,
            String entityRef)
            throws NamingException {
        super(initialDataParser, parentHandler);

        this.partyName = partyName;
        this.vendorName = vendorName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("partyAlias")) {
            var commandForm = PartyFormFactory.getCreatePartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));

            partyService.createPartyAlias(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("partyCreditLimit")) {
            var form = TermFormFactory.getCreatePartyCreditLimitForm();
            
            form.setPartyName(partyName);
            form.set(getAttrsMap(attrs));
            
            termService.createPartyCreditLimit(initialDataParser.getUserVisit(), form);
        } else if(localName.equals("vendorItem")) {
            var form = VendorFormFactory.getCreateVendorItemForm();
            
            form.setVendorName(vendorName);
            form.set(getAttrsMap(attrs));
            
            vendorService.createVendorItem(initialDataParser.getUserVisit(), form);
            
            initialDataParser.pushHandler(new VendorItemHandler(initialDataParser, this, vendorName, form.getVendorItemName()));
        } else if(localName.equals("purchaseInvoices")) {
            String companyName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("companyName"))
                    companyName = attrs.getValue(i);
            }
            
            initialDataParser.pushHandler(new PurchaseInvoicesHandler(initialDataParser, this, vendorName, companyName));
        } else if(localName.equals("contactMechanisms")) {
            initialDataParser.pushHandler(new ContactMechanismsHandler(initialDataParser, this, partyName));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("comments")) {
            initialDataParser.pushHandler(new CommentsHandler(initialDataParser, this, entityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("vendor")) {
            initialDataParser.popHandler();
        }
    }
    
}
