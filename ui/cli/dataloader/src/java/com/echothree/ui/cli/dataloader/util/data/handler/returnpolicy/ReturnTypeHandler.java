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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ReturnTypeHandler
        extends BaseHandler {
    ReturnPolicyService returnPolicyService;
    String returnKindName;
    String returnTypeName;
    
    /** Creates a new instance of ReturnTypeHandler */
    public ReturnTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String returnKindName, String returnTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            returnPolicyService = ReturnPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.returnKindName = returnKindName;
        this.returnTypeName = returnTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("returnTypeDescription")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnTypeDescriptionForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.setReturnTypeName(returnTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(returnPolicyService.createReturnTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("returnReasonType")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnReasonTypeForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.setReturnTypeName(returnTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(returnPolicyService.createReturnReasonType(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("returnTypeShippingMethod")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnTypeShippingMethodForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.setReturnTypeName(returnTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(returnPolicyService.createReturnTypeShippingMethod(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("returnType")) {
            initialDataParser.popHandler();
        }
    }
    
}
