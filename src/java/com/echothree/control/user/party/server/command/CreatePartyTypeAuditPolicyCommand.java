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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyTypeAuditPolicyForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePartyTypeAuditPolicyCommand
        extends BaseSimpleCommand<CreatePartyTypeAuditPolicyForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AuditCommands", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RetainUserVisitsTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("RetainUserVisitsTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyTypeAuditPolicyCommand */
    public CreatePartyTypeAuditPolicyCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var rawRetainUserVisitsTime = form.getRetainUserVisitsTime();
        var lockoutInactiveTimeUnitOfMeasureTypeName = form.getRetainUserVisitsTimeUnitOfMeasureTypeName();
        var lockoutInactiveTimeParameterCount = (rawRetainUserVisitsTime == null ? 0 : 1) + (lockoutInactiveTimeUnitOfMeasureTypeName == null ? 0 : 1);

        if(lockoutInactiveTimeParameterCount == 0 || lockoutInactiveTimeParameterCount == 2) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyTypeName = form.getPartyTypeName();
            var partyType = partyControl.getPartyTypeByName(partyTypeName);

            if(partyType != null) {
                if(partyType.getAllowUserLogins()) {
                    var partyTypeAuditPolicy = partyControl.getPartyTypeAuditPolicy(partyType);

                    if(partyTypeAuditPolicy == null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                        if(timeUnitOfMeasureKind != null) {
                            var lockoutInactiveTimeUnitOfMeasureType = lockoutInactiveTimeUnitOfMeasureTypeName == null ? null
                                    : uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind, lockoutInactiveTimeUnitOfMeasureTypeName);

                            if(lockoutInactiveTimeUnitOfMeasureTypeName == null || lockoutInactiveTimeUnitOfMeasureType != null) {
                                var rawAuditCommands = form.getAuditCommands();
                                var auditCommands = rawAuditCommands == null ? null : Boolean.valueOf(rawAuditCommands);
                                var lockoutInactiveTime = rawRetainUserVisitsTime == null ? null : Long.valueOf(rawRetainUserVisitsTime);
                                var lockoutInactiveTimeConversion = lockoutInactiveTime == null ? null
                                        : new Conversion(uomControl, lockoutInactiveTimeUnitOfMeasureType, lockoutInactiveTime).convertToLowestUnitOfMeasureType();

                                partyControl.createPartyTypeAuditPolicy(partyType, auditCommands, lockoutInactiveTimeConversion == null ? null : lockoutInactiveTimeConversion.getQuantity(), getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownRetainUserVisitsTimeUnitOfMeasureTypeName.name(), lockoutInactiveTimeUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyTypeAuditPolicy.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return null;
    }
    
}
