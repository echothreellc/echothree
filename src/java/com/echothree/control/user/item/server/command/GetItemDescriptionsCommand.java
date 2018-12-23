// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemDescriptionsForm;
import com.echothree.control.user.item.common.result.GetItemDescriptionsResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUse;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.factory.ItemDescriptionFactory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemDescriptionsCommand
        extends BaseSimpleCommand<GetItemDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescription.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemDescriptionsCommand */
    public GetItemDescriptionsCommand(UserVisitPK userVisitPK, GetItemDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetItemDescriptionsResult result = ItemResultFactory.getGetItemDescriptionsResult();
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            String itemDescriptionTypeUseTypeName = form.getItemDescriptionTypeUseTypeName();
            ItemDescriptionTypeUseType itemDescriptionTypeUseType = itemDescriptionTypeUseTypeName == null ? null : itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

            if(itemDescriptionTypeUseTypeName == null || itemDescriptionTypeUseType != null) {
                UserVisit userVisit = getUserVisit();
                List<ItemDescriptionTransfer> itemDescriptions = null;

                result.setItem(itemControl.getItemTransfer(userVisit, item));

                if(itemDescriptionTypeUseType == null) {
                    if(form.getLanguageIsoName() == null) {
                        if(session.hasLimit(ItemDescriptionFactory.class)) {
                            result.setItemDescriptionCount(itemControl.countItemDescriptionsByItem(item));
                        }

                        itemDescriptions = itemControl.getItemDescriptionTransfersByItem(userVisit, item);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidParameterCombination.name());
                    }
                } else {
                    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    String languageIsoName = form.getLanguageIsoName();
                    Language language = languageIsoName == null ? getPreferredLanguage() : partyControl.getLanguageByIsoName(languageIsoName);

                    if(languageIsoName == null || language != null) {
                        List<ItemDescriptionTypeUse> itemDescriptionTypeUses = itemControl.getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);

                        itemDescriptions = new ArrayList<>();

                        for(ItemDescriptionTypeUse itemDescriptionTypeUse: itemDescriptionTypeUses) {
                            ItemDescription itemDescription = itemControl.getBestItemDescription(itemDescriptionTypeUse.getItemDescriptionType(), item, language);

                            if(itemDescription != null) {
                                itemDescriptions.add(itemControl.getItemDescriptionTransfer(userVisit, itemDescription));
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                }

                if(!hasExecutionErrors()) {
                    result.setItemDescriptions(itemDescriptions);

                    sendEventUsingNames(item.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return result;
    }
    
}
