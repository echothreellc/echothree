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

import com.echothree.control.user.selector.common.edit.SelectorEdit;
import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.result.EditSelectorResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSelectorCommand
        extends BaseAbstractEditCommand<SelectorSpec, SelectorEdit, EditSelectorResult, Selector, Selector> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Selector.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditSelectorCommand */
    public EditSelectorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    SelectorControl selectorControl;

    @Override
    public EditSelectorResult getResult() {
        return SelectorResultFactory.getEditSelectorResult();
    }

    @Override
    public SelectorEdit getEdit() {
        return SelectorEditFactory.getSelectorEdit();
    }

    @Override
    public Selector getEntity(EditSelectorResult result) {
        Selector selector = null;
        var selectorKindName = spec.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind != null) {
            var selectorTypeName = spec.getSelectorTypeName();
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

            if(selectorType != null) {
                var selectorName = spec.getSelectorName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    selector = selectorControl.getSelectorByName(selectorType, selectorName);
                } else { // EditMode.UPDATE
                    selector = selectorControl.getSelectorByNameForUpdate(selectorType, selectorName);
                }

                if(selector == null) {
                    addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selector;
    }

    @Override
    public Selector getLockEntity(Selector selector) {
        return selector;
    }

    @Override
    public void fillInResult(EditSelectorResult result, Selector selector) {
        result.setSelector(selectorControl.getSelectorTransfer(getUserVisit(), selector));
    }

    @Override
    public void doLock(SelectorEdit edit, Selector selector) {
        var selectorDescription = selectorControl.getSelectorDescription(selector, getPreferredLanguage());
        var selectorDetail = selector.getLastDetail();

        edit.setSelectorName(selectorDetail.getSelectorName());
        edit.setIsDefault(selectorDetail.getIsDefault().toString());
        edit.setSortOrder(selectorDetail.getSortOrder().toString());

        if(selectorDescription != null) {
            edit.setDescription(selectorDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Selector selector) {
        var selectorType = selector.getLastDetail().getSelectorType();
        var selectorName = edit.getSelectorName();
        var duplicateSelector = selectorControl.getSelectorByName(selectorType, selectorName);

        if(duplicateSelector != null && !selector.equals(duplicateSelector)) {
            addExecutionError(ExecutionErrors.DuplicateSelectorName.name(), selectorName);
        }
    }

    @Override
    public void doUpdate(Selector selector) {
        var partyPK = getPartyPK();
        var selectorDetailValue = selectorControl.getSelectorDetailValueForUpdate(selector);
        var selectorDescription = selectorControl.getSelectorDescriptionForUpdate(selector, getPreferredLanguage());
        var description = edit.getDescription();

        selectorDetailValue.setSelectorName(edit.getSelectorName());
        selectorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        selectorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        selectorControl.updateSelectorFromValue(selectorDetailValue, partyPK);

        if(selectorDescription == null && description != null) {
            selectorControl.createSelectorDescription(selector, getPreferredLanguage(), description, partyPK);
        } else if(selectorDescription != null && description == null) {
            selectorControl.deleteSelectorDescription(selectorDescription, partyPK);
        } else if(selectorDescription != null && description != null) {
            var selectorDescriptionValue = selectorControl.getSelectorDescriptionValue(selectorDescription);

            selectorDescriptionValue.setDescription(description);
            selectorControl.updateSelectorDescriptionFromValue(selectorDescriptionValue, partyPK);
        }
    }
    
}
