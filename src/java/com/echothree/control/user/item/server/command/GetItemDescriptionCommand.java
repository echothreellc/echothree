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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemDescriptionForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.item.server.logic.ItemDescriptionTypeLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetItemDescriptionCommand
        extends BaseSingleEntityCommand<ItemDescription, GetItemDescriptionForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("Referrer", FieldType.URL, false, null, null)
        );
    }
    
    /** Creates a new instance of GetItemDescriptionCommand */
    public GetItemDescriptionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    private void checkReferrer(final ItemDescriptionType itemDescriptionType) {
        if(itemDescriptionType.getLastDetail().getCheckContentWebAddress()) {
            ContentLogic.getInstance().checkReferrer(this, form.getReferrer());
        }
    }

    @Override
    protected ItemDescription getEntity() {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescription itemDescription = null;
        var itemName = form.getItemName();
        var itemDescriptionTypeName = form.getItemDescriptionTypeName();
        var traditionalParameterCount = ParameterUtils.getInstance().countNonNullParameters(itemName, itemDescriptionTypeName);

        if(traditionalParameterCount == 0 || traditionalParameterCount == 2) {
            var possibleEntitySpecsCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

            // checkReferrer(...) is called separately in the two paths since the first one can be short circuited if
            // the referrer check fails.
            if(traditionalParameterCount == 2 && possibleEntitySpecsCount == 0) {
                var item = ItemLogic.getInstance().getItemByName(this, itemName);
                var itemDescriptionType = ItemDescriptionTypeLogic.getInstance().getItemDescriptionTypeByName(this, itemDescriptionTypeName);

                if(!hasExecutionErrors()) {
                    checkReferrer(itemDescriptionType);

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
            } else if(traditionalParameterCount == 0 && possibleEntitySpecsCount == 1) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemDescription.name());

                if(!hasExecutionErrors()) {
                    itemDescription = itemControl.getItemDescriptionByEntityInstance(entityInstance);

                    checkReferrer(itemDescription.getLastDetail().getItemDescriptionType());

                    if(hasExecutionErrors()) {
                        itemDescription = null; // pretend that didn't happen.
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return itemDescription;
    }

    @Override
    protected BaseResult getResult(ItemDescription itemDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var result = ItemResultFactory.getGetItemDescriptionResult();

        if(itemDescription != null) {
            result.setItemDescription(itemControl.getItemDescriptionTransfer(getUserVisit(), itemDescription));
        }

        return result;
    }


}
