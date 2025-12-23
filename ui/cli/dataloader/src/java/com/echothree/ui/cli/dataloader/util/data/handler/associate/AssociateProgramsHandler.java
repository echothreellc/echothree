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

public class AssociateProgramsHandler
        extends BaseHandler {
    AssociateService associateService;
    
    /** Creates a new instance of AssociateProgramsHandler */
    public AssociateProgramsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            associateService = AssociateUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("associateProgram")) {
            var commandForm = AssociateFormFactory.getCreateAssociateProgramForm();
            
            String associateProgramName = null;
            String associateSequenceName = null;
            String associatePartyContactMechanismSequenceName = null;
            String associateReferralSequenceName = null;
            String itemIndirectSalePercent = null;
            String itemDirectSalePercent = null;
            String isDefault = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("associateProgramName"))
                    associateProgramName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("associateSequenceName"))
                    associateSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("associatePartyContactMechanismSequenceName"))
                    associatePartyContactMechanismSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("associateReferralSequenceName"))
                    associateReferralSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("itemIndirectSalePercent"))
                    itemIndirectSalePercent = attrs.getValue(i);
                else if(attrs.getQName(i).equals("itemDirectSalePercent"))
                    itemDirectSalePercent = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateSequenceName(associateSequenceName);
            commandForm.setAssociatePartyContactMechanismSequenceName(associatePartyContactMechanismSequenceName);
            commandForm.setAssociateReferralSequenceName(associateReferralSequenceName);
            commandForm.setAssociateReferralSequenceName(associateReferralSequenceName);
            commandForm.setItemIndirectSalePercent(itemIndirectSalePercent);
            commandForm.setItemDirectSalePercent(itemDirectSalePercent);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            
            checkCommandResult(associateService.createAssociateProgram(initialDataParser.getUserVisit(), commandForm));
            
            initialDataParser.pushHandler(new AssociateProgramHandler(initialDataParser, this, associateProgramName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("associatePrograms")) {
            initialDataParser.popHandler();
        }
    }
    
}
