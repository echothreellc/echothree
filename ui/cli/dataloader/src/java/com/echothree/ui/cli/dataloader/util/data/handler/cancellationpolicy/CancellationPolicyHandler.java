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

package com.echothree.ui.cli.dataloader.util.data.handler.cancellationpolicy;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyService;
import com.echothree.control.user.cancellationpolicy.common.form.CancellationPolicyFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CancellationPolicyHandler
        extends BaseHandler {
    CancellationPolicyService cancellationPolicyService;
    String cancellationKindName;
    String cancellationPolicyName;
    
    /** Creates a new instance of CancellationPolicyHandler */
    public CancellationPolicyHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String cancellationKindName, String cancellationPolicyName) {
        super(initialDataParser, parentHandler);
        
        try {
            cancellationPolicyService = CancellationPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.cancellationKindName = cancellationKindName;
        this.cancellationPolicyName = cancellationPolicyName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("cancellationPolicyTranslation")) {
            var commandForm = CancellationPolicyFormFactory.getCreateCancellationPolicyTranslationForm();

            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.set(getAttrsMap(attrs));

            cancellationPolicyService.createCancellationPolicyTranslation(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("cancellationPolicy")) {
            initialDataParser.popHandler();
        }
    }
    
}
