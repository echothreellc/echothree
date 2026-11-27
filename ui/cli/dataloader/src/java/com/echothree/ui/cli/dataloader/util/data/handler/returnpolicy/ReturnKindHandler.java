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

package com.echothree.ui.cli.dataloader.util.data.handler.returnpolicy;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyService;
import com.echothree.control.user.returnpolicy.common.form.ReturnPolicyFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ReturnKindHandler
        extends BaseHandler {

    ReturnPolicyService returnPolicyService;
    String returnKindName;
    
    /** Creates a new instance of ReturnKindHandler */
    public ReturnKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String returnKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        returnPolicyService = lookupService(ReturnPolicyService.class, ReturnPolicyUtil::getHome);
        
        this.returnKindName = returnKindName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("returnKindDescription")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnKindDescriptionForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, returnPolicyService.createReturnKindDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("returnPolicy")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnPolicyForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, returnPolicyService.createReturnPolicy(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ReturnPolicyHandler(initialDataParser, this, returnKindName, commandForm.getReturnPolicyName()));
        } else if(localName.equals("returnReason")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnReasonForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, returnPolicyService.createReturnReason(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ReturnReasonHandler(initialDataParser, this, returnKindName, commandForm.getReturnReasonName()));
        } else if(localName.equals("returnType")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnTypeForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, returnPolicyService.createReturnType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ReturnTypeHandler(initialDataParser, this, returnKindName, commandForm.getReturnTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("returnKind")) {
            initialDataParser.popHandler();
        }
    }
    
}
