// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.edit.SelectorTypeEdit;
import com.echothree.control.user.selector.common.form.EditSelectorTypeForm;
import com.echothree.control.user.selector.common.result.EditSelectorTypeResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditSelectorTypeCommand
        extends BaseAbstractEditCommand<SelectorTypeSpec, SelectorTypeEdit, EditSelectorTypeResult, SelectorType, SelectorType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SelectorType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSelectorTypeCommand */
    public EditSelectorTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSelectorTypeResult getResult() {
        return SelectorResultFactory.getEditSelectorTypeResult();
    }

    @Override
    public SelectorTypeEdit getEdit() {
        return SelectorEditFactory.getSelectorTypeEdit();
    }

    SelectorKind selectorKind;

    @Override
    public SelectorType getEntity(EditSelectorTypeResult result) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        SelectorType selectorType = null;
        var selectorKindName = spec.getSelectorKindName();

        selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind != null) {
            var selectorTypeName = spec.getSelectorTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            } else { // EditMode.UPDATE
                selectorType = selectorControl.getSelectorTypeByNameForUpdate(selectorKind, selectorTypeName);
            }

            if(selectorType == null) {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorKindName, selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorType;
    }

    @Override
    public SelectorType getLockEntity(SelectorType selectorType) {
        return selectorType;
    }

    @Override
    public void fillInResult(EditSelectorTypeResult result, SelectorType selectorType) {
        var selectorControl = Session.getModelController(SelectorControl.class);

        result.setSelectorType(selectorControl.getSelectorTypeTransfer(getUserVisit(), selectorType));
    }

    @Override
    public void doLock(SelectorTypeEdit edit, SelectorType selectorType) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorTypeDescription = selectorControl.getSelectorTypeDescription(selectorType, getPreferredLanguage());
        var selectorTypeDetail = selectorType.getLastDetail();

        edit.setSelectorTypeName(selectorTypeDetail.getSelectorTypeName());
        edit.setIsDefault(selectorTypeDetail.getIsDefault().toString());
        edit.setSortOrder(selectorTypeDetail.getSortOrder().toString());

        if(selectorTypeDescription != null) {
            edit.setDescription(selectorTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SelectorType selectorType) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKindDetail = selectorKind.getLastDetail();
        var selectorTypeName = edit.getSelectorTypeName();
        var duplicateSelectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

        if(duplicateSelectorType != null && !selectorType.equals(duplicateSelectorType)) {
            addExecutionError(ExecutionErrors.DuplicateSelectorTypeName.name(), selectorKindDetail.getSelectorKindName(), selectorTypeName);
        }
    }

    @Override
    public void doUpdate(SelectorType selectorType) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var partyPK = getPartyPK();
        var selectorTypeDetailValue = selectorControl.getSelectorTypeDetailValueForUpdate(selectorType);
        var selectorTypeDescription = selectorControl.getSelectorTypeDescriptionForUpdate(selectorType, getPreferredLanguage());
        var description = edit.getDescription();

        selectorTypeDetailValue.setSelectorTypeName(edit.getSelectorTypeName());
        selectorTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        selectorTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        selectorControl.updateSelectorTypeFromValue(selectorTypeDetailValue, partyPK);

        if(selectorTypeDescription == null && description != null) {
            selectorControl.createSelectorTypeDescription(selectorType, getPreferredLanguage(), description, partyPK);
        } else if(selectorTypeDescription != null && description == null) {
            selectorControl.deleteSelectorTypeDescription(selectorTypeDescription, partyPK);
        } else if(selectorTypeDescription != null && description != null) {
            var selectorTypeDescriptionValue = selectorControl.getSelectorTypeDescriptionValue(selectorTypeDescription);

            selectorTypeDescriptionValue.setDescription(description);
            selectorControl.updateSelectorTypeDescriptionFromValue(selectorTypeDescriptionValue, partyPK);
        }
    }

}
