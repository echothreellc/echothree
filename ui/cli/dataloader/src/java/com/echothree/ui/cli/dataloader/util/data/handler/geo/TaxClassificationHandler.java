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

package com.echothree.ui.cli.dataloader.util.data.handler.geo;

import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.TaxService;
import com.echothree.control.user.tax.common.form.TaxFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TaxClassificationHandler
        extends BaseHandler {

    TaxService taxService;
    String countryGeoCodeName;
    String taxClassificationName;

    /** Creates a new instance of TaxClassificationHandler */
    public TaxClassificationHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String countryGeoCodeName,
            String taxClassificationName)
            throws SAXException {

        super(initialDataParser, parentHandler);

        try {
            taxService = TaxUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.countryGeoCodeName = countryGeoCodeName;
        this.taxClassificationName = taxClassificationName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("taxClassificationTranslation")) {
            var commandForm = TaxFormFactory.getCreateTaxClassificationTranslationForm();

            commandForm.setCountryName(countryGeoCodeName);
            commandForm.setTaxClassificationName(taxClassificationName);
            commandForm.set(getAttrsMap(attrs));

            taxService.createTaxClassificationTranslation(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("taxClassification")) {
            initialDataParser.popHandler();
        }
    }

}
