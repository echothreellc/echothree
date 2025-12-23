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

package com.echothree.ui.cli.dataloader.util.data.handler.associate;

import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.common.AssociateService;
import com.echothree.control.user.associate.common.form.AssociateFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class AssociateHandler
        extends BaseHandler {
    AssociateService associateService;
    String associateProgramName;
    String associateName;
    
    /** Creates a new instance of AssociateHandler */
    public AssociateHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String associateProgramName,
            String associateName) {
        super(initialDataParser, parentHandler);
        
        try {
            associateService = AssociateUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.associateProgramName = associateProgramName;
        this.associateName = associateName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("associatePartyContactMechanism")) {
            var commandForm = AssociateFormFactory.getCreateAssociatePartyContactMechanismForm();
            String associatePartyContactMechanismName = null;
            String contactMechanismName = null;
            String contactMechanismAliasTypeName = null;
            String alias = null;
            String isDefault = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("associatePartyContactMechanismName"))
                    associatePartyContactMechanismName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contactMechanismName"))
                    contactMechanismName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contactMechanismAliasTypeName"))
                    contactMechanismAliasTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("alias"))
                    alias = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);
            commandForm.setContactMechanismName(contactMechanismName);
            commandForm.setContactMechanismAliasTypeName(contactMechanismAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            
            checkCommandResult(associateService.createAssociatePartyContactMechanism(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("associate")) {
            initialDataParser.popHandler();
        }
    }
    
}
