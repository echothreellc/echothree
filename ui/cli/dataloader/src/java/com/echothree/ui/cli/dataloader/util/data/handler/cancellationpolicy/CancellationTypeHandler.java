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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CancellationTypeHandler
        extends BaseHandler {

    CancellationPolicyService cancellationPolicyService;
    String cancellationKindName;
    String cancellationTypeName;
    
    /** Creates a new instance of CancellationTypeHandler */
    public CancellationTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String cancellationKindName, String cancellationTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        cancellationPolicyService = lookupService(CancellationPolicyService.class, CancellationPolicyUtil::getHome);
        
        this.cancellationKindName = cancellationKindName;
        this.cancellationTypeName = cancellationTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("cancellationTypeDescription")) {
            var commandForm = CancellationPolicyFormFactory.getCreateCancellationTypeDescriptionForm();

            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setCancellationTypeName(cancellationTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm,
                    cancellationPolicyService.createCancellationTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("cancellationReasonType")) {
            var commandForm = CancellationPolicyFormFactory.getCreateCancellationReasonTypeForm();

            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setCancellationTypeName(cancellationTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm,
                    cancellationPolicyService.createCancellationReasonType(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("cancellationType")) {
            initialDataParser.popHandler();
        }
    }
    
}
