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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.edit.PeriodEditFactory;
import com.echothree.control.user.period.common.edit.PeriodTypeDescriptionEdit;
import com.echothree.control.user.period.common.form.EditPeriodTypeDescriptionForm;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditPeriodTypeDescriptionCommand
        extends BaseEditCommand<PeriodTypeDescriptionSpec, PeriodTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPeriodTypeDescriptionCommand */
    public EditPeriodTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var periodControl = Session.getModelController(PeriodControl.class);
        var result = PeriodResultFactory.getEditPeriodTypeDescriptionResult();
        var periodKindName = spec.getPeriodKindName();
        var periodKind = periodControl.getPeriodKindByName(periodKindName);

        if(periodKind != null) {
            var periodTypeName = spec.getPeriodTypeName();
            var periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);

            if(periodType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        var periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, language);

                        if(periodTypeDescription != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                result.setPeriodTypeDescription(periodControl.getPeriodTypeDescriptionTransfer(getUserVisit(), periodTypeDescription));

                                if(lockEntity(periodType)) {
                                    var edit = PeriodEditFactory.getPeriodTypeDescriptionEdit();

                                    result.setEdit(edit);
                                    edit.setDescription(periodTypeDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }

                                result.setEntityLock(getEntityLockTransfer(periodType));
                            } else { // EditMode.ABANDON
                                unlockEntity(periodType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPeriodTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var periodTypeDescription = periodControl.getPeriodTypeDescriptionForUpdate(periodType, language);
                        var periodTypeDescriptionValue = periodControl.getPeriodTypeDescriptionValue(periodTypeDescription);

                        if(periodTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(periodType)) {
                                try {
                                    var description = edit.getDescription();

                                    periodTypeDescriptionValue.setDescription(description);

                                    periodControl.updatePeriodTypeDescriptionFromValue(periodTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(periodType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPeriodTypeDescription.name());
                        }

                        if(hasExecutionErrors()) {
                            result.setPeriodTypeDescription(periodControl.getPeriodTypeDescriptionTransfer(getUserVisit(), periodTypeDescription));
                            result.setEntityLock(getEntityLockTransfer(periodType));
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), periodTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPeriodKindName.name(), periodKindName);
        }

        return result;
    }

}
