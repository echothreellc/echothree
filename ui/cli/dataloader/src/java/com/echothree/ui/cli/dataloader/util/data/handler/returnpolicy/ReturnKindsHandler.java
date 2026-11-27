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

public class ReturnKindsHandler
        extends BaseHandler {
    ReturnPolicyService returnPolicyService;
    
    /** Creates a new instance of ReturnKindsHandler */
    public ReturnKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            returnPolicyService = ReturnPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("returnKind")) {
            var commandForm = ReturnPolicyFormFactory.getCreateReturnKindForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(returnPolicyService.createReturnKind(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ReturnKindHandler(initialDataParser, this, commandForm.getReturnKindName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("returnKinds")) {
            initialDataParser.popHandler();
        }
    }
    
}
