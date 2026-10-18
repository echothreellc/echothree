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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.edit.PeriodEditFactory;
import com.echothree.control.user.period.common.edit.PeriodKindDescriptionEdit;
import com.echothree.control.user.period.common.result.EditPeriodKindDescriptionResult;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodKindDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDescription;
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
public class EditPeriodKindDescriptionCommand
        extends BaseAbstractEditCommand<PeriodKindDescriptionSpec, PeriodKindDescriptionEdit, EditPeriodKindDescriptionResult, PeriodKindDescription, PeriodKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodKind.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PeriodControl periodControl;

    @Inject
    PeriodKindLogic periodKindLogic;

    /** Creates a new instance of EditPeriodKindDescriptionCommand */
    public EditPeriodKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPeriodKindDescriptionResult getResult() {
        return PeriodResultFactory.getEditPeriodKindDescriptionResult();
    }

    @Override
    public PeriodKindDescriptionEdit getEdit() {
        return PeriodEditFactory.getPeriodKindDescriptionEdit();
    }

    @Override
    public PeriodKindDescription getEntity(EditPeriodKindDescriptionResult result) {
        return periodKindLogic.getPeriodKindDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public PeriodKind getLockEntity(PeriodKindDescription periodKindDescription) {
        return periodKindDescription.getPeriodKind();
    }

    @Override
    public void fillInResult(EditPeriodKindDescriptionResult result, PeriodKindDescription periodKindDescription) {
        result.setPeriodKindDescription(periodControl.getPeriodKindDescriptionTransfer(getUserVisit(), periodKindDescription));
    }

    @Override
    public void doLock(PeriodKindDescriptionEdit edit, PeriodKindDescription periodKindDescription) {
        edit.setDescription(periodKindDescription.getDescription());
    }

    @Override
    public void doUpdate(PeriodKindDescription periodKindDescription) {
        var periodKindDescriptionValue = periodControl.getPeriodKindDescriptionValue(periodKindDescription);

        periodKindDescriptionValue.setDescription(edit.getDescription());

        periodControl.updatePeriodKindDescriptionFromValue(periodKindDescriptionValue, getPartyPK());
    }

}
