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

package com.echothree.ui.cli.dataloader.util.data.handler.license;

import com.echothree.control.user.license.common.LicenseUtil;
import com.echothree.control.user.license.common.LicenseService;
import com.echothree.control.user.license.common.form.LicenseFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LicenseTypeHandler
        extends BaseHandler {
    
    LicenseService licenseService;
    String licenseTypeName;
    
    /** Creates a new instance of LicenseTypeHandler */
    public LicenseTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String licenseTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            licenseService = LicenseUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.licenseTypeName = licenseTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("licenseTypeDescription")) {
            var commandForm = LicenseFormFactory.getCreateLicenseTypeDescriptionForm();

            commandForm.setLicenseTypeName(licenseTypeName);
            commandForm.set(getAttrsMap(attrs));

            licenseService.createLicenseTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("licenseType")) {
            initialDataParser.popHandler();
        }
    }
    
}
