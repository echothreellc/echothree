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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PartyTypeLockoutPolicyEdit;
import com.echothree.control.user.party.common.result.EditPartyTypeLockoutPolicyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeUniversalSpec;
import com.echothree.model.control.party.common.exception.UnknownLockoutInactiveTimeUnitOfMeasureTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownResetFailureCountTimeUnitOfMeasureTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyTypeLockoutPolicyLogic;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPartyTypeLockoutPolicyCommand
        extends BaseEditCommand<PartyTypeUniversalSpec, PartyTypeLockoutPolicyEdit,
                EditPartyTypeLockoutPolicyResult, PartyTypeLockoutPolicy, PartyTypeLockoutPolicy> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LockoutFailureCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("ResetFailureCountTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ResetFailureCountTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ManualLockoutReset", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("LockoutInactiveTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("LockoutInactiveTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    private UnitOfMeasureKind timeUnitOfMeasureKind;
    private UnitOfMeasureType resetFailureCountTimeUnitOfMeasureType;
    private UnitOfMeasureType lockoutInactiveTimeUnitOfMeasureType;

    @Inject
    PartyControl partyControl;

    @Inject
    UomControl uomControl;

    @Inject
    PartyTypeLockoutPolicyLogic partyTypeLockoutPolicyLogic;

    @Inject
    PartyTypeLogic partyTypeLogic;

    @Inject
    UnitOfMeasureKindLogic unitOfMeasureKindLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    /** Creates a new instance of EditPartyTypeLockoutPolicyCommand */
    public EditPartyTypeLockoutPolicyCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyTypeLockoutPolicyResult getResult() {
        return PartyResultFactory.getEditPartyTypeLockoutPolicyResult();
    }

    @Override
    public PartyTypeLockoutPolicyEdit getEdit() {
        return PartyEditFactory.getPartyTypeLockoutPolicyEdit();
    }

    @Override
    public PartyTypeLockoutPolicy getEntity(EditPartyTypeLockoutPolicyResult result) {
        var partyType = partyTypeLogic.getPartyTypeByUniversalSpec(this, spec, false);
        PartyTypeLockoutPolicy partyTypeLockoutPolicy = null;

        if(!hasExecutionErrors()) {
            partyTypeLockoutPolicy = partyTypeLockoutPolicyLogic.getPartyTypeLockoutPolicy(this, partyType, editModeToEntityPermission(editMode));
        }

        return partyTypeLockoutPolicy;
    }

    @Override
    public PartyTypeLockoutPolicy getLockEntity(PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        return partyTypeLockoutPolicy;
    }

    @Override
    public void fillInResult(EditPartyTypeLockoutPolicyResult result, PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyTypeLockoutPolicy.getLastDetail().getPartyType()));
    }

    @Override
    public void canEdit(PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        timeUnitOfMeasureKind = unitOfMeasureKindLogic.getTimeUnitOfMeasureKind(this);
    }

    @Override
    public void doLock(PartyTypeLockoutPolicyEdit edit, PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        var partyTypeLockoutPolicyDetail = partyTypeLockoutPolicy.getLastDetail();
        var lockoutFailureCount = partyTypeLockoutPolicyDetail.getLockoutFailureCount();
        var resetFailureCountTime = partyTypeLockoutPolicyDetail.getResetFailureCountTime();
        var resetFailureCountTimeConversion = resetFailureCountTime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, resetFailureCountTime).convertToHighestUnitOfMeasureType();
        var manualLockoutReset = partyTypeLockoutPolicyDetail.getManualLockoutReset();
        var lockoutInactiveTime = partyTypeLockoutPolicyDetail.getLockoutInactiveTime();
        var lockoutInactiveTimeConversion = lockoutInactiveTime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, lockoutInactiveTime).convertToHighestUnitOfMeasureType();

        edit.setLockoutFailureCount(lockoutFailureCount == null? null: lockoutFailureCount.toString());
        if(resetFailureCountTimeConversion != null) {
            edit.setResetFailureCountTime(resetFailureCountTimeConversion.getQuantity().toString());
            edit.setResetFailureCountTimeUnitOfMeasureTypeName(resetFailureCountTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
        edit.setManualLockoutReset(manualLockoutReset == null? null: manualLockoutReset.toString());
        if(lockoutInactiveTimeConversion != null) {
            edit.setLockoutInactiveTime(lockoutInactiveTimeConversion.getQuantity().toString());
            edit.setLockoutInactiveTimeUnitOfMeasureTypeName(lockoutInactiveTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
    }

    private boolean isCompleteTimePair(String value, String unitOfMeasureTypeName) {
        return (value == null) == (unitOfMeasureTypeName == null);
    }

    @Override
    public void canUpdate(PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        var resetFailureCountTimeUnitOfMeasureTypeName = edit.getResetFailureCountTimeUnitOfMeasureTypeName();
        var lockoutInactiveTimeUnitOfMeasureTypeName = edit.getLockoutInactiveTimeUnitOfMeasureTypeName();

        if(isCompleteTimePair(edit.getResetFailureCountTime(), resetFailureCountTimeUnitOfMeasureTypeName)
                && isCompleteTimePair(edit.getLockoutInactiveTime(), lockoutInactiveTimeUnitOfMeasureTypeName)) {
            resetFailureCountTimeUnitOfMeasureType = resetFailureCountTimeUnitOfMeasureTypeName == null ? null
                    : unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, timeUnitOfMeasureKind, resetFailureCountTimeUnitOfMeasureTypeName,
                            UnknownResetFailureCountTimeUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownResetFailureCountTimeUnitOfMeasureTypeName.name());

            if(!hasExecutionErrors()) {
                lockoutInactiveTimeUnitOfMeasureType = lockoutInactiveTimeUnitOfMeasureTypeName == null ? null
                        : unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, timeUnitOfMeasureKind, lockoutInactiveTimeUnitOfMeasureTypeName,
                                UnknownLockoutInactiveTimeUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownLockoutInactiveTimeUnitOfMeasureTypeName.name());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    public void doUpdate(PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        var partyTypeLockoutPolicyDetailValue = partyControl.getPartyTypeLockoutPolicyDetailValueForUpdate(partyTypeLockoutPolicy);
        var rawResetFailureCountTime = edit.getResetFailureCountTime();
        var rawLockoutInactiveTime = edit.getLockoutInactiveTime();
        var rawLockoutFailureCount = edit.getLockoutFailureCount();
        var lockoutFailureCount = rawLockoutFailureCount == null? null: Integer.valueOf(rawLockoutFailureCount);
        var resetFailureCountTime = rawResetFailureCountTime == null? null: Long.valueOf(rawResetFailureCountTime);
        var resetFailureCountTimeConversion = resetFailureCountTime == null? null: new Conversion(uomControl, resetFailureCountTimeUnitOfMeasureType, resetFailureCountTime).convertToLowestUnitOfMeasureType();
        var manualLockoutReset = Boolean.valueOf(edit.getManualLockoutReset());
        var lockoutInactiveTime = rawLockoutInactiveTime == null? null: Long.valueOf(rawLockoutInactiveTime);
        var lockoutInactiveTimeConversion = lockoutInactiveTime == null? null: new Conversion(uomControl, lockoutInactiveTimeUnitOfMeasureType, lockoutInactiveTime).convertToLowestUnitOfMeasureType();

        partyTypeLockoutPolicyDetailValue.setLockoutFailureCount(lockoutFailureCount);
        partyTypeLockoutPolicyDetailValue.setResetFailureCountTime(resetFailureCountTimeConversion == null? null: resetFailureCountTimeConversion.getQuantity());
        partyTypeLockoutPolicyDetailValue.setManualLockoutReset(manualLockoutReset);
        partyTypeLockoutPolicyDetailValue.setLockoutInactiveTime(lockoutInactiveTimeConversion == null? null: lockoutInactiveTimeConversion.getQuantity());

        partyControl.updatePartyTypeLockoutPolicyFromValue(partyTypeLockoutPolicyDetailValue, getPartyPK());
    }

}
