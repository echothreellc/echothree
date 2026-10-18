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
import com.echothree.control.user.period.common.edit.PeriodTypeDescriptionEdit;
import com.echothree.control.user.period.common.result.EditPeriodTypeDescriptionResult;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
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
public class EditPeriodTypeDescriptionCommand
        extends BaseAbstractEditCommand<PeriodTypeDescriptionSpec, PeriodTypeDescriptionEdit, EditPeriodTypeDescriptionResult, PeriodTypeDescription, PeriodType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PeriodControl periodControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PeriodKindLogic periodKindLogic;

    /** Creates a new instance of EditPeriodTypeDescriptionCommand */
    public EditPeriodTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPeriodTypeDescriptionResult getResult() {
        return PeriodResultFactory.getEditPeriodTypeDescriptionResult();
    }

    @Override
    public PeriodTypeDescriptionEdit getEdit() {
        return PeriodEditFactory.getPeriodTypeDescriptionEdit();
    }

    @Override
    public PeriodTypeDescription getEntity(EditPeriodTypeDescriptionResult result) {
        PeriodTypeDescription periodTypeDescription = null;
        var periodKindName = spec.getPeriodKindName();
        var periodKind = periodKindLogic.getPeriodKindByName(this, periodKindName);

        if(!hasExecutionErrors()) {
            var periodTypeName = spec.getPeriodTypeName();
            var periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);

            if(periodType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, language, editModeToEntityPermission(editMode));

                    if(periodTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownPeriodTypeDescription.name(), periodKindName, periodTypeName, languageIsoName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), periodKindName, periodTypeName);
            }
        }

        return periodTypeDescription;
    }

    @Override
    public PeriodType getLockEntity(PeriodTypeDescription periodTypeDescription) {
        return periodTypeDescription.getPeriodType();
    }

    @Override
    public void fillInResult(EditPeriodTypeDescriptionResult result, PeriodTypeDescription periodTypeDescription) {
        result.setPeriodTypeDescription(periodControl.getPeriodTypeDescriptionTransfer(getUserVisit(), periodTypeDescription));
    }

    @Override
    public void doLock(PeriodTypeDescriptionEdit edit, PeriodTypeDescription periodTypeDescription) {
        edit.setDescription(periodTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PeriodTypeDescription periodTypeDescription) {
        var periodTypeDescriptionValue = periodControl.getPeriodTypeDescriptionValue(periodTypeDescription);

        periodTypeDescriptionValue.setDescription(edit.getDescription());

        periodControl.updatePeriodTypeDescriptionFromValue(periodTypeDescriptionValue, getPartyPK());
    }

}
