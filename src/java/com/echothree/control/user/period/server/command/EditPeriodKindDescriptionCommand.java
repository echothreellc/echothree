// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.period.common.form.EditPeriodKindDescriptionForm;
import com.echothree.control.user.period.common.result.EditPeriodKindDescriptionResult;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodKindDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDescription;
import com.echothree.model.data.period.server.value.PeriodKindDescriptionValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPeriodKindDescriptionCommand
        extends BaseEditCommand<PeriodKindDescriptionSpec, PeriodKindDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodKind.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPeriodKindDescriptionCommand */
    public EditPeriodKindDescriptionCommand(UserVisitPK userVisitPK, EditPeriodKindDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        EditPeriodKindDescriptionResult result = PeriodResultFactory.getEditPeriodKindDescriptionResult();
        String periodKindName = spec.getPeriodKindName();
        PeriodKind periodKind = periodControl.getPeriodKindByName(periodKindName);

        if(periodKind != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    PeriodKindDescription periodKindDescription = periodControl.getPeriodKindDescription(periodKind, language);

                    if(periodKindDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setPeriodKindDescription(periodControl.getPeriodKindDescriptionTransfer(getUserVisit(), periodKindDescription));

                            if(lockEntity(periodKind)) {
                                PeriodKindDescriptionEdit edit = PeriodEditFactory.getPeriodKindDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(periodKindDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(periodKind));
                        } else { // EditMode.ABANDON
                            unlockEntity(periodKind);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPeriodKindDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    PeriodKindDescription periodKindDescription = periodControl.getPeriodKindDescriptionForUpdate(periodKind, language);
                    PeriodKindDescriptionValue periodKindDescriptionValue = periodControl.getPeriodKindDescriptionValue(periodKindDescription);

                    if(periodKindDescriptionValue != null) {
                        if(lockEntityForUpdate(periodKind)) {
                            try {
                                String description = edit.getDescription();

                                periodKindDescriptionValue.setDescription(description);

                                periodControl.updatePeriodKindDescriptionFromValue(periodKindDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(periodKind);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPeriodKindDescription.name());
                    }

                    if(hasExecutionErrors()) {
                        result.setPeriodKindDescription(periodControl.getPeriodKindDescriptionTransfer(getUserVisit(), periodKindDescription));
                        result.setEntityLock(getEntityLockTransfer(periodKind));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPeriodKindName.name(), periodKindName);
        }

        return result;
    }

}
