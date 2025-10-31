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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.EmploymentEdit;
import com.echothree.control.user.employee.common.form.EditEmploymentForm;
import com.echothree.control.user.employee.common.result.EditEmploymentResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.EmploymentSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.employee.server.entity.Employment;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEmploymentCommand
        extends BaseAbstractEditCommand<EmploymentSpec, EmploymentEdit, EditEmploymentResult, Employment, Employment> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EmploymentName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("EndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("TerminationTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TerminationReasonName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditEmploymentCommand */
    public EditEmploymentCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEmploymentResult getResult() {
        return EmployeeResultFactory.getEditEmploymentResult();
    }

    @Override
    public EmploymentEdit getEdit() {
        return EmployeeEditFactory.getEmploymentEdit();
    }

    ItemDescriptionType itemDescriptionType;
    Item item;

    @Override
    public Employment getEntity(EditEmploymentResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        Employment employment;
        var employmentName = spec.getEmploymentName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            employment = employeeControl.getEmploymentByName(employmentName);
        } else { // EditMode.UPDATE
            employment = employeeControl.getEmploymentByNameForUpdate(employmentName);
        }

        if(employment == null) {
            addExecutionError(ExecutionErrors.UnknownEmployment.name(), employmentName);
        }

        return employment;
    }

    @Override
    public Employment getLockEntity(Employment employment) {
        return employment;
    }

    @Override
    public void fillInResult(EditEmploymentResult result, Employment employment) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setEmployment(employeeControl.getEmploymentTransfer(getUserVisit(), employment));
    }

    TerminationType terminationType;
    TerminationReason terminationReason;

    @Override
    public void doLock(EmploymentEdit edit, Employment employment) {
        var partyControl = Session.getModelController(PartyControl.class);
        var employmentDetail = employment.getLastDetail();
        var endTime = employmentDetail.getEndTime();

        terminationType = employmentDetail.getTerminationType();
        terminationReason = employmentDetail.getTerminationReason();

        edit.setCompanyName(partyControl.getPartyCompany(employmentDetail.getCompanyParty()).getPartyCompanyName());
        edit.setStartTime(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), employmentDetail.getStartTime()));
        edit.setEndTime(endTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), endTime));
        edit.setTerminationTypeName(terminationType == null ? null : terminationType.getLastDetail().getTerminationTypeName());
        edit.setTerminationReasonName(terminationReason == null ? null : terminationReason.getLastDetail().getTerminationReasonName());
    }

    PartyCompany partyCompany;
    
    @Override
    public void canUpdate(Employment employment) {
        var partyControl = Session.getModelController(PartyControl.class);
        var companyName = edit.getCompanyName();

        partyCompany = partyControl.getPartyCompanyByName(companyName);

        if(partyCompany != null) {
            var employeeControl = Session.getModelController(EmployeeControl.class);
            var terminationTypeName = edit.getTerminationTypeName();

            terminationType = employeeControl.getTerminationTypeByName(terminationTypeName);

            if(terminationTypeName == null || terminationType != null) {
                var terminationReasonName = edit.getTerminationReasonName();

                terminationReason = employeeControl.getTerminationReasonByName(terminationReasonName);

                if(terminationReasonName != null && terminationReason == null) {
                    addExecutionError(ExecutionErrors.UnknownTerminationReasonName.name(), terminationReasonName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTerminationTypeName.name(), terminationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
        }
    }

    @Override
    public void doUpdate(Employment employment) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var employmentDetailValue = employeeControl.getEmploymentDetailValueForUpdate(employment);
        var strEndTime = edit.getEndTime();

        employmentDetailValue.setCompanyPartyPK(partyCompany.getPartyPK());
        employmentDetailValue.setStartTime(Long.valueOf(edit.getStartTime()));
        employmentDetailValue.setEndTime(strEndTime == null ? null : Long.valueOf(strEndTime));
        employmentDetailValue.setTerminationTypePK(terminationType == null ? null : terminationType.getPrimaryKey());
        employmentDetailValue.setTerminationReasonPK(terminationReason == null ? null : terminationReason.getPrimaryKey());
        
        employeeControl.updateEmploymentFromValue(employmentDetailValue, getPartyPK());
    }
    
}
