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

package com.echothree.ui.cli.dataloader.util.data.handler.tax;

import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.TaxService;
import com.echothree.control.user.tax.common.form.TaxFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TaxHandler
        extends BaseHandler {
    TaxService taxService;
    String taxName;
    
    /** Creates a new instance of TaxHandler */
    public TaxHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String taxName) {
        super(initialDataParser, parentHandler);
        
        try {
            taxService = TaxUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.taxName = taxName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("taxDescription")) {
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
                var createTaxDescriptionForm = TaxFormFactory.getCreateTaxDescriptionForm();
                
                createTaxDescriptionForm.setTaxName(taxName);
                createTaxDescriptionForm.setLanguageIsoName(languageIsoName);
                createTaxDescriptionForm.setDescription(description);
                
                taxService.createTaxDescription(initialDataParser.getUserVisit(), createTaxDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("tax")) {
            initialDataParser.popHandler();
        }
    }
    
}
