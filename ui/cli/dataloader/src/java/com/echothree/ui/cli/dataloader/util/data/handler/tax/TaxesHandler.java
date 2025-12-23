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

public class TaxesHandler
        extends BaseHandler {
    TaxService taxService;
    
    /** Creates a new instance of TaxesHandler */
    public TaxesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            taxService = TaxUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("tax")) {
            String taxName = null;
            String contactMechanismPurposeName = null;
            String glAccountName = null;
            String includeShippingCharge = null;
            String includeProcessingCharge = null;
            String includeInsuranceCharge = null;
            String percent = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("taxName"))
                    taxName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contactMechanismPurposeName"))
                    contactMechanismPurposeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("glAccountName"))
                    glAccountName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("includeShippingCharge"))
                    includeShippingCharge = attrs.getValue(i);
                else if(attrs.getQName(i).equals("includeProcessingCharge"))
                    includeProcessingCharge = attrs.getValue(i);
                else if(attrs.getQName(i).equals("includeInsuranceCharge"))
                    includeInsuranceCharge = attrs.getValue(i);
                else if(attrs.getQName(i).equals("percent"))
                    percent = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var createTaxForm = TaxFormFactory.getCreateTaxForm();
                
                createTaxForm.setTaxName(taxName);
                createTaxForm.setContactMechanismPurposeName(contactMechanismPurposeName);
                createTaxForm.setGlAccountName(glAccountName);
                createTaxForm.setIncludeShippingCharge(includeShippingCharge);
                createTaxForm.setIncludeProcessingCharge(includeProcessingCharge);
                createTaxForm.setIncludeInsuranceCharge(includeInsuranceCharge);
                createTaxForm.setPercent(percent);
                createTaxForm.setIsDefault(isDefault);
                createTaxForm.setSortOrder(sortOrder);
                
                taxService.createTax(initialDataParser.getUserVisit(), createTaxForm);
                
                initialDataParser.pushHandler(new TaxHandler(initialDataParser, this, taxName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("taxes")) {
            initialDataParser.popHandler();
        }
    }
    
}
