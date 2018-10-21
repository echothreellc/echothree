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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.remote.edit.SelectorEditFactory;
import com.echothree.control.user.selector.remote.edit.SelectorKindEdit;
import com.echothree.control.user.selector.remote.form.EditSelectorKindForm;
import com.echothree.control.user.selector.remote.result.EditSelectorKindResult;
import com.echothree.control.user.selector.remote.result.SelectorResultFactory;
import com.echothree.control.user.selector.remote.spec.SelectorKindSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorKindDescription;
import com.echothree.model.data.selector.server.entity.SelectorKindDetail;
import com.echothree.model.data.selector.server.value.SelectorKindDescriptionValue;
import com.echothree.model.data.selector.server.value.SelectorKindDetailValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditSelectorKindCommand
        extends BaseAbstractEditCommand<SelectorKindSpec, SelectorKindEdit, EditSelectorKindResult, SelectorKind, SelectorKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SelectorKind.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditSelectorKindCommand */
    public EditSelectorKindCommand(UserVisitPK userVisitPK, EditSelectorKindForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSelectorKindResult getResult() {
        return SelectorResultFactory.getEditSelectorKindResult();
    }

    @Override
    public SelectorKindEdit getEdit() {
        return SelectorEditFactory.getSelectorKindEdit();
    }

    @Override
    public SelectorKind getEntity(EditSelectorKindResult result) {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        SelectorKind selectorKind = null;
        String selectorKindName = spec.getSelectorKindName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        } else { // EditMode.UPDATE
            selectorKind = selectorControl.getSelectorKindByNameForUpdate(selectorKindName);
        }

        if(selectorKind == null) {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorKind;
    }

    @Override
    public SelectorKind getLockEntity(SelectorKind selectorKind) {
        return selectorKind;
    }

    @Override
    public void fillInResult(EditSelectorKindResult result, SelectorKind selectorKind) {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);

        result.setSelectorKind(selectorControl.getSelectorKindTransfer(getUserVisit(), selectorKind));
    }

    @Override
    public void doLock(SelectorKindEdit edit, SelectorKind selectorKind) {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        SelectorKindDescription selectorKindDescription = selectorControl.getSelectorKindDescription(selectorKind, getPreferredLanguage());
        SelectorKindDetail selectorKindDetail = selectorKind.getLastDetail();

        edit.setSelectorKindName(selectorKindDetail.getSelectorKindName());
        edit.setIsDefault(selectorKindDetail.getIsDefault().toString());
        edit.setSortOrder(selectorKindDetail.getSortOrder().toString());

        if(selectorKindDescription != null) {
            edit.setDescription(selectorKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SelectorKind selectorKind) {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        String selectorKindName = edit.getSelectorKindName();
        SelectorKind duplicateSelectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(duplicateSelectorKind != null && !selectorKind.equals(duplicateSelectorKind)) {
            addExecutionError(ExecutionErrors.DuplicateSelectorKindName.name(), selectorKindName);
        }
    }

    @Override
    public void doUpdate(SelectorKind selectorKind) {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        PartyPK partyPK = getPartyPK();
        SelectorKindDetailValue selectorKindDetailValue = selectorControl.getSelectorKindDetailValueForUpdate(selectorKind);
        SelectorKindDescription selectorKindDescription = selectorControl.getSelectorKindDescriptionForUpdate(selectorKind, getPreferredLanguage());
        String description = edit.getDescription();

        selectorKindDetailValue.setSelectorKindName(edit.getSelectorKindName());
        selectorKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        selectorKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        selectorControl.updateSelectorKindFromValue(selectorKindDetailValue, partyPK);

        if(selectorKindDescription == null && description != null) {
            selectorControl.createSelectorKindDescription(selectorKind, getPreferredLanguage(), description, partyPK);
        } else if(selectorKindDescription != null && description == null) {
            selectorControl.deleteSelectorKindDescription(selectorKindDescription, partyPK);
        } else if(selectorKindDescription != null && description != null) {
            SelectorKindDescriptionValue selectorKindDescriptionValue = selectorControl.getSelectorKindDescriptionValue(selectorKindDescription);

            selectorKindDescriptionValue.setDescription(description);
            selectorControl.updateSelectorKindDescriptionFromValue(selectorKindDescriptionValue, partyPK);
        }
    }

}
