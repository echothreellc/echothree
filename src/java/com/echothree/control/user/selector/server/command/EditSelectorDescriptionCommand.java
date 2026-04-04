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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.edit.SelectorDescriptionEdit;
import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.result.EditSelectorDescriptionResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSelectorDescriptionCommand
        extends BaseAbstractEditCommand<SelectorDescriptionSpec, SelectorDescriptionEdit, EditSelectorDescriptionResult, SelectorDescription, Selector> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Selector.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditSelectorDescriptionCommand */
    public EditSelectorDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    PartyControl partyControl;

    @Inject
    SelectorControl selectorControl;

    @Override
    public EditSelectorDescriptionResult getResult() {
        return SelectorResultFactory.getEditSelectorDescriptionResult();
    }

    @Override
    public SelectorDescriptionEdit getEdit() {
        return SelectorEditFactory.getSelectorDescriptionEdit();
    }

    @Override
    public SelectorDescription getEntity(EditSelectorDescriptionResult result) {
        SelectorDescription selectorDescription = null;
        var selectorKindName = spec.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind != null) {
            var selectorTypeName = spec.getSelectorTypeName();
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

            if(selectorType != null) {
                var selectorName = spec.getSelectorName();
                var selector = selectorControl.getSelectorByName(selectorType, selectorName);

                if(selector != null) {
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);

                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            selectorDescription = selectorControl.getSelectorDescription(selector, language);
                        } else { // EditMode.UPDATE
                            selectorDescription = selectorControl.getSelectorDescriptionForUpdate(selector, language);
                        }

                        if(selectorDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownSelectorDescription.name(), selectorKindName, selectorTypeName, selectorName, languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorDescription;
    }

    @Override
    public Selector getLockEntity(SelectorDescription selectorDescription) {
        return selectorDescription.getSelector();
    }

    @Override
    public void fillInResult(EditSelectorDescriptionResult result, SelectorDescription selectorDescription) {
        result.setSelectorDescription(selectorControl.getSelectorDescriptionTransfer(getUserVisit(), selectorDescription));
    }

    @Override
    public void doLock(SelectorDescriptionEdit edit, SelectorDescription selectorDescription) {
        edit.setDescription(selectorDescription.getDescription());
    }

    @Override
    public void doUpdate(SelectorDescription selectorDescription) {
        var selectorDescriptionValue = selectorControl.getSelectorDescriptionValue(selectorDescription);

        selectorDescriptionValue.setDescription(edit.getDescription());

        selectorControl.updateSelectorDescriptionFromValue(selectorDescriptionValue, getPartyPK());
    }

}
