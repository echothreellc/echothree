// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.CreatePartyTypeLockoutPolicyForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
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

public class CreatePartyTypeLockoutPolicyCommand
        extends BaseSimpleCommand<CreatePartyTypeLockoutPolicyForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LockoutFailureCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("ResetFailureCountTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ResetFailureCountTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ManualLockoutReset", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("LockoutInactiveTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("LockoutInactiveTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyTypeLockoutPolicyCommand */
    public CreatePartyTypeLockoutPolicyCommand(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String rawResetFailureCountTime = form.getResetFailureCountTime();
        String resetFailureCountTimeUnitOfMeasureTypeName = form.getResetFailureCountTimeUnitOfMeasureTypeName();
        int resetFailureCountTimeParameterCount = (rawResetFailureCountTime == null ? 0 : 1) + (resetFailureCountTimeUnitOfMeasureTypeName == null ? 0 : 1);
        String rawLockoutInactiveTime = form.getLockoutInactiveTime();
        String lockoutInactiveTimeUnitOfMeasureTypeName = form.getLockoutInactiveTimeUnitOfMeasureTypeName();
        int lockoutInactiveTimeParameterCount = (rawLockoutInactiveTime == null ? 0 : 1) + (lockoutInactiveTimeUnitOfMeasureTypeName == null ? 0 : 1);
        
        if((resetFailureCountTimeParameterCount == 0 || resetFailureCountTimeParameterCount == 2) &&
                (lockoutInactiveTimeParameterCount == 0 || lockoutInactiveTimeParameterCount == 2)) {
            var partyControl = Session.getModelController(PartyControl.class);
            String partyTypeName = form.getPartyTypeName();
            PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
            
            if(partyType != null) {
                if(partyType.getAllowUserLogins()) {
                    PartyTypeLockoutPolicy partyTypeLockoutPolicy = partyControl.getPartyTypeLockoutPolicy(partyType);

                    if(partyTypeLockoutPolicy == null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                        if(timeUnitOfMeasureKind != null) {
                            UnitOfMeasureType resetFailureCountTimeUnitOfMeasureType = resetFailureCountTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                    resetFailureCountTimeUnitOfMeasureTypeName);

                            if(resetFailureCountTimeUnitOfMeasureTypeName == null || resetFailureCountTimeUnitOfMeasureType != null) {
                                UnitOfMeasureType lockoutInactiveTimeUnitOfMeasureType = lockoutInactiveTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                        lockoutInactiveTimeUnitOfMeasureTypeName);

                                if(lockoutInactiveTimeUnitOfMeasureTypeName == null || lockoutInactiveTimeUnitOfMeasureType != null) {
                                    String rawLockoutFailureCount = form.getLockoutFailureCount();
                                    Integer lockoutFailureCount = rawLockoutFailureCount == null? null: Integer.valueOf(rawLockoutFailureCount);
                                    Long resetFailureCountTime = rawResetFailureCountTime == null? null: Long.valueOf(rawResetFailureCountTime);
                                    Conversion resetFailureCountTimeConversion = resetFailureCountTime == null? null: new Conversion(uomControl, resetFailureCountTimeUnitOfMeasureType, resetFailureCountTime).convertToLowestUnitOfMeasureType();
                                    String rawManualLockoutReset = form.getManualLockoutReset();
                                    Boolean manualLockoutReset = rawManualLockoutReset == null? null: Boolean.valueOf(rawManualLockoutReset);
                                    Long lockoutInactiveTime = rawLockoutInactiveTime == null? null: Long.valueOf(rawLockoutInactiveTime);
                                    Conversion lockoutInactiveTimeConversion = lockoutInactiveTime == null? null: new Conversion(uomControl, lockoutInactiveTimeUnitOfMeasureType, lockoutInactiveTime).convertToLowestUnitOfMeasureType();

                                    partyControl.createPartyTypeLockoutPolicy(partyType, lockoutFailureCount,
                                            resetFailureCountTimeConversion == null? null: resetFailureCountTimeConversion.getQuantity(),
                                            manualLockoutReset,
                                            lockoutInactiveTimeConversion == null? null: lockoutInactiveTimeConversion.getQuantity(),
                                            getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownLockoutInactiveTimeUnitOfMeasureTypeName.name(), lockoutInactiveTimeUnitOfMeasureTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownResetFailureCountTimeUnitOfMeasureTypeName.name(), resetFailureCountTimeUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyTypeLockoutPolicy.name());
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
