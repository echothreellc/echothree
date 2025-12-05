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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.PartyTrainingClassEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditPartyTrainingClassForm;
import com.echothree.control.user.training.common.result.EditPartyTrainingClassResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.PartyTrainingClassSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
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
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditPartyTrainingClassCommand
        extends BaseAbstractEditCommand<PartyTrainingClassSpec, PartyTrainingClassEdit, EditPartyTrainingClassResult, PartyTrainingClass, PartyTrainingClass> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClass.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompletedTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ValidUntilTime", FieldType.DATE_TIME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTrainingClassCommand */
    public EditPartyTrainingClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyTrainingClassResult getResult() {
        return TrainingResultFactory.getEditPartyTrainingClassResult();
    }

    @Override
    public PartyTrainingClassEdit getEdit() {
        return TrainingEditFactory.getPartyTrainingClassEdit();
    }

    @Override
    public PartyTrainingClass getEntity(EditPartyTrainingClassResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClass partyTrainingClass;
        var partyTrainingClassName = spec.getPartyTrainingClassName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            partyTrainingClass = trainingControl.getPartyTrainingClassByName(partyTrainingClassName);
        } else { // EditMode.UPDATE
            partyTrainingClass = trainingControl.getPartyTrainingClassByNameForUpdate(partyTrainingClassName);
        }

        if(partyTrainingClass != null) {
            result.setPartyTrainingClass(trainingControl.getPartyTrainingClassTransfer(getUserVisit(), partyTrainingClass));
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTrainingClassName.name(), partyTrainingClassName);
        }

        return partyTrainingClass;
    }

    @Override
    public PartyTrainingClass getLockEntity(PartyTrainingClass partyTrainingClass) {
        return partyTrainingClass;
    }

    @Override
    public void fillInResult(EditPartyTrainingClassResult result, PartyTrainingClass partyTrainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setPartyTrainingClass(trainingControl.getPartyTrainingClassTransfer(getUserVisit(), partyTrainingClass));
    }

    @Override
    public void doLock(PartyTrainingClassEdit edit, PartyTrainingClass partyTrainingClass) {
        var dateUtils = DateUtils.getInstance();
        var partyTrainingClassDetail = partyTrainingClass.getLastDetail();
        var userVisit = getUserVisit();
        var preferredDateTimeFormat = getPreferredDateTimeFormat();
        
        edit.setCompletedTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, partyTrainingClassDetail.getCompletedTime()));
        edit.setValidUntilTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, partyTrainingClassDetail.getValidUntilTime()));
    }

    Long completedTime;
    Long validUntilTime;
    
    @Override
    public void canUpdate(PartyTrainingClass partyTrainingClass) {
        var strCompletedTime = edit.getCompletedTime();
        
        completedTime = strCompletedTime == null ? null : Long.valueOf(strCompletedTime);

        if(completedTime == null || completedTime < session.getStartTime()) {
            var strValidUntilTime = edit.getValidUntilTime();
            
            validUntilTime = strValidUntilTime == null ? null : Long.valueOf(strValidUntilTime);

            if(validUntilTime != null && validUntilTime <= session.getStartTime()) {
                addExecutionError(ExecutionErrors.InvalidValidUntilTime.name());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidCompletedTime.name());
        }
    }

    @Override
    public void doUpdate(PartyTrainingClass partyTrainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var partyTrainingClassDetailValue = trainingControl.getPartyTrainingClassDetailValueForUpdate(partyTrainingClass);

        partyTrainingClassDetailValue.setCompletedTime(completedTime);
        partyTrainingClassDetailValue.setValidUntilTime(validUntilTime);

        PartyTrainingClassLogic.getInstance().updatePartyTrainingClassFromValue(partyTrainingClassDetailValue, partyPK);
    }

}
