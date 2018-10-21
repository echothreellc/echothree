// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.item.remote.form.GetItemDescriptionForm;
import com.echothree.control.user.item.remote.result.GetItemDescriptionResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemDescriptionCommand
        extends BaseSimpleCommand<GetItemDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Referrer", FieldType.URL, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemDescriptionCommand */
    public GetItemDescriptionCommand(UserVisitPK userVisitPK, GetItemDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetItemDescriptionResult result = ItemResultFactory.getGetItemDescriptionResult();
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            String itemDescriptionTypeName = form.getItemDescriptionTypeName();
            ItemDescriptionType itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);
            
            if(itemDescriptionType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = languageIsoName == null ? getPreferredLanguage() : partyControl.getLanguageByIsoName(languageIsoName);
                
                if(languageIsoName == null || language != null) {
                    ItemDescription itemDescription = itemControl.getItemDescription(itemDescriptionType, item, language);

                    if(itemDescription == null) {
                        itemDescription = ItemDescriptionLogic.getInstance().searchForItemDescription(itemDescriptionType, item, language, getPartyPK());
                    }

                    if(itemDescription != null) {
                        if(itemDescriptionType.getLastDetail().getCheckContentWebAddress()) {
                            ContentLogic.getInstance().checkReferrer(this, form.getReferrer());
                        }

                        if(!hasExecutionErrors()) {
                            result.setItemDescription(itemControl.getItemDescriptionTransfer(getUserVisit(), itemDescription));
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemDescription.name(), itemDescriptionTypeName, itemName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return result;
    }
    
}
