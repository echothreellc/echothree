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

public class CancellationKindsHandler
        extends BaseHandler {
    CancellationPolicyService cancellationPolicyService;
    
    /** Creates a new instance of CancellationKindsHandler */
    public CancellationKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            cancellationPolicyService = CancellationPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("cancellationKind")) {
            String cancellationKindName = null;
            String cancellationSequenceTypeName = null;
            String isDefault = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("cancellationKindName"))
                    cancellationKindName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("cancellationSequenceTypeName"))
                    cancellationSequenceTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = CancellationPolicyFormFactory.getCreateCancellationKindForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setCancellationSequenceTypeName(cancellationSequenceTypeName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                checkCommandResult(cancellationPolicyService.createCancellationKind(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new CancellationKindHandler(initialDataParser, this, cancellationKindName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("cancellationKinds")) {
            initialDataParser.popHandler();
        }
    }
    
}
