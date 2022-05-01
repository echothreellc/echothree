// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.item.common.form.GetItemDescriptionForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.item.server.logic.ItemDescriptionTypeLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetItemDescriptionCommand
        extends BaseSingleEntityCommand<ItemDescription, GetItemDescriptionForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Referrer", FieldType.URL, false, null, null)
        );
    }
    
    /** Creates a new instance of GetItemDescriptionCommand */
    public GetItemDescriptionCommand(UserVisitPK userVisitPK, GetItemDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected ItemDescription getEntity() {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescription itemDescription = null;
        var itemName = form.getItemName();
        var item = ItemLogic.getInstance().getItemByName(this, itemName);
        var itemDescriptionTypeName = form.getItemDescriptionTypeName();
        var itemDescriptionType = ItemDescriptionTypeLogic.getInstance().getItemDescriptionTypeByName(this, itemDescriptionTypeName);

        if(!hasExecutionErrors()) {
            if(itemDescriptionType.getLastDetail().getCheckContentWebAddress()) {
                ContentLogic.getInstance().checkReferrer(this, form.getReferrer());
            }

            if(!hasExecutionErrors()) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = form.getLanguageIsoName();
                var language = languageIsoName == null ? getPreferredLanguage() : partyControl.getLanguageByIsoName(languageIsoName);

                if(languageIsoName == null || language != null) {
                    itemDescription = itemControl.getItemDescription(itemDescriptionType, item, language);

                    if(itemDescription == null) {
                        itemDescription = ItemDescriptionLogic.getInstance().searchForItemDescription(itemDescriptionType,
                                item, language, getPartyPK());
                    }

                    if(itemDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownItemDescription.name(), itemDescriptionTypeName,
                                itemName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            }
        }

        return itemDescription;
    }

    @Override
    protected BaseResult getTransfer(ItemDescription itemDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var result = ItemResultFactory.getGetItemDescriptionResult();

        if(itemDescription != null) {
            result.setItemDescription(itemControl.getItemDescriptionTransfer(getUserVisit(), itemDescription));
        }

        return result;
    }


}
