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
import com.echothree.control.user.party.common.edit.PartyTypePasswordStringPolicyEdit;
import com.echothree.control.user.party.common.result.EditPartyTypePasswordStringPolicyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeUniversalSpec;
import com.echothree.model.control.party.common.exception.UnknownExpirationWarningTimeUnitOfMeasureTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownMaximumPasswordLifetimeUnitOfMeasureTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownMinimumPasswordLifetimeUnitOfMeasureTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.party.server.logic.PartyTypePasswordStringPolicyLogic;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
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
public class EditPartyTypePasswordStringPolicyCommand
        extends BaseEditCommand<PartyTypeUniversalSpec, PartyTypePasswordStringPolicyEdit,
                EditPartyTypePasswordStringPolicyResult, PartyTypePasswordStringPolicy, PartyTypePasswordStringPolicy> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForceChangeAfterCreate", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("ForceChangeAfterReset", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowChange", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("PasswordHistory", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumPasswordLifetime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MinimumPasswordLifetimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumPasswordLifetime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumPasswordLifetimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ExpirationWarningTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ExpirationWarningTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ExpiredLoginsPermitted", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumLength", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumLength", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("RequiredDigitCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("RequiredLetterCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("RequiredUpperCaseCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("RequiredLowerCaseCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumRepeated", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumCharacterTypes", FieldType.UNSIGNED_INTEGER, false, null, null)
        );
    }

    private UnitOfMeasureKind timeUnitOfMeasureKind;
    private UnitOfMeasureType minimumPasswordLifetimeUnitOfMeasureType;
    private UnitOfMeasureType maximumPasswordLifetimeUnitOfMeasureType;
    private UnitOfMeasureType expirationWarningTimeUnitOfMeasureType;

    @Inject
    PartyControl partyControl;

    @Inject
    UomControl uomControl;

    @Inject
    PartyTypeLogic partyTypeLogic;

    @Inject
    PartyTypePasswordStringPolicyLogic partyTypePasswordStringPolicyLogic;

    @Inject
    UnitOfMeasureKindLogic unitOfMeasureKindLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    /** Creates a new instance of EditPartyTypePasswordStringPolicyCommand */
    public EditPartyTypePasswordStringPolicyCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyTypePasswordStringPolicyResult getResult() {
        return PartyResultFactory.getEditPartyTypePasswordStringPolicyResult();
    }

    @Override
    public PartyTypePasswordStringPolicyEdit getEdit() {
        return PartyEditFactory.getPartyTypePasswordStringPolicyEdit();
    }

    @Override
    public PartyTypePasswordStringPolicy getEntity(EditPartyTypePasswordStringPolicyResult result) {
        var partyType = partyTypeLogic.getPartyTypeByUniversalSpec(this, spec, false);
        PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = null;

        if(!hasExecutionErrors()) {
            partyTypePasswordStringPolicy = partyTypePasswordStringPolicyLogic.getPartyTypePasswordStringPolicy(this, partyType,
                    editModeToEntityPermission(editMode));
        }

        return partyTypePasswordStringPolicy;
    }

    @Override
    public PartyTypePasswordStringPolicy getLockEntity(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        return partyTypePasswordStringPolicy;
    }

    @Override
    public void fillInResult(EditPartyTypePasswordStringPolicyResult result, PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyTypePasswordStringPolicy.getLastDetail().getPartyType()));
    }

    @Override
    public void canEdit(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        timeUnitOfMeasureKind = unitOfMeasureKindLogic.getTimeUnitOfMeasureKind(this);
    }

    @Override
    public void doLock(PartyTypePasswordStringPolicyEdit edit, PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        var partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetail();
        var passwordHistory = partyTypePasswordStringPolicyDetail.getPasswordHistory();
        var minimumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMinimumPasswordLifetime();
        var minimumPasswordLifetimeConversion = minimumPasswordLifetime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, minimumPasswordLifetime).convertToHighestUnitOfMeasureType();
        var maximumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMaximumPasswordLifetime();
        var maximumPasswordLifetimeConversion = maximumPasswordLifetime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, maximumPasswordLifetime).convertToHighestUnitOfMeasureType();
        var expirationWarningTime = partyTypePasswordStringPolicyDetail.getExpirationWarningTime();
        var expirationWarningTimeConversion = expirationWarningTime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, expirationWarningTime).convertToHighestUnitOfMeasureType();
        var expiredLoginsPermitted = partyTypePasswordStringPolicyDetail.getExpiredLoginsPermitted();
        var minimumLength = partyTypePasswordStringPolicyDetail.getMinimumLength();
        var maximumLength = partyTypePasswordStringPolicyDetail.getMaximumLength();
        var requiredDigitCount = partyTypePasswordStringPolicyDetail.getRequiredDigitCount();
        var requiredLetterCount = partyTypePasswordStringPolicyDetail.getRequiredLetterCount();
        var requiredUpperCaseCount = partyTypePasswordStringPolicyDetail.getRequiredUpperCaseCount();
        var requiredLowerCaseCount = partyTypePasswordStringPolicyDetail.getRequiredLowerCaseCount();
        var maximumRepeated = partyTypePasswordStringPolicyDetail.getMaximumRepeated();
        var minimumCharacterTypes = partyTypePasswordStringPolicyDetail.getMinimumCharacterTypes();

        edit.setForceChangeAfterCreate(partyTypePasswordStringPolicyDetail.getForceChangeAfterCreate().toString());
        edit.setForceChangeAfterReset(partyTypePasswordStringPolicyDetail.getForceChangeAfterReset().toString());
        edit.setAllowChange(partyTypePasswordStringPolicyDetail.getAllowChange().toString());
        edit.setPasswordHistory(passwordHistory == null? null: passwordHistory.toString());
        if(minimumPasswordLifetimeConversion != null) {
            edit.setMinimumPasswordLifetime(minimumPasswordLifetimeConversion.getQuantity().toString());
            edit.setMinimumPasswordLifetimeUnitOfMeasureTypeName(minimumPasswordLifetimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
        if(maximumPasswordLifetimeConversion != null) {
            edit.setMaximumPasswordLifetime(maximumPasswordLifetimeConversion.getQuantity().toString());
            edit.setMaximumPasswordLifetimeUnitOfMeasureTypeName(maximumPasswordLifetimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
        if(expirationWarningTimeConversion != null) {
            edit.setExpirationWarningTime(expirationWarningTimeConversion.getQuantity().toString());
            edit.setExpirationWarningTimeUnitOfMeasureTypeName(expirationWarningTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
        edit.setExpiredLoginsPermitted(expiredLoginsPermitted == null? null: expiredLoginsPermitted.toString());
        edit.setMinimumLength(minimumLength == null? null: minimumLength.toString());
        edit.setMaximumLength(maximumLength == null? null: maximumLength.toString());
        edit.setRequiredDigitCount(requiredDigitCount == null? null: requiredDigitCount.toString());
        edit.setRequiredLetterCount(requiredLetterCount == null? null: requiredLetterCount.toString());
        edit.setRequiredUpperCaseCount(requiredUpperCaseCount == null? null: requiredUpperCaseCount.toString());
        edit.setRequiredLowerCaseCount(requiredLowerCaseCount == null? null: requiredLowerCaseCount.toString());
        edit.setMaximumRepeated(maximumRepeated == null? null: maximumRepeated.toString());
        edit.setMinimumCharacterTypes(minimumCharacterTypes == null? null: minimumCharacterTypes.toString());
    }

    private boolean isCompleteTimePair(String value, String unitOfMeasureTypeName) {
        return (value == null) == (unitOfMeasureTypeName == null);
    }

    @Override
    public void canUpdate(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        var minimumPasswordLifetimeUnitOfMeasureTypeName = edit.getMinimumPasswordLifetimeUnitOfMeasureTypeName();
        var maximumPasswordLifetimeUnitOfMeasureTypeName = edit.getMaximumPasswordLifetimeUnitOfMeasureTypeName();
        var expirationWarningTimeUnitOfMeasureTypeName = edit.getExpirationWarningTimeUnitOfMeasureTypeName();

        if(isCompleteTimePair(edit.getMinimumPasswordLifetime(), minimumPasswordLifetimeUnitOfMeasureTypeName)
                && isCompleteTimePair(edit.getMaximumPasswordLifetime(), maximumPasswordLifetimeUnitOfMeasureTypeName)
                && isCompleteTimePair(edit.getExpirationWarningTime(), expirationWarningTimeUnitOfMeasureTypeName)) {
            minimumPasswordLifetimeUnitOfMeasureType = minimumPasswordLifetimeUnitOfMeasureTypeName == null ? null
                    : unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, timeUnitOfMeasureKind, minimumPasswordLifetimeUnitOfMeasureTypeName,
                            UnknownMinimumPasswordLifetimeUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownMinimumPasswordLifetimeUnitOfMeasureTypeName.name());

            if(!hasExecutionErrors()) {
                maximumPasswordLifetimeUnitOfMeasureType = maximumPasswordLifetimeUnitOfMeasureTypeName == null ? null
                        : unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, timeUnitOfMeasureKind, maximumPasswordLifetimeUnitOfMeasureTypeName,
                                UnknownMaximumPasswordLifetimeUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownMaximumPasswordLifetimeUnitOfMeasureTypeName.name());
            }

            if(!hasExecutionErrors()) {
                expirationWarningTimeUnitOfMeasureType = expirationWarningTimeUnitOfMeasureTypeName == null ? null
                        : unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, timeUnitOfMeasureKind, expirationWarningTimeUnitOfMeasureTypeName,
                                UnknownExpirationWarningTimeUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownExpirationWarningTimeUnitOfMeasureTypeName.name());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    public void doUpdate(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        var partyTypePasswordStringPolicyDetailValue = partyControl.getPartyTypePasswordStringPolicyDetailValueForUpdate(partyTypePasswordStringPolicy);
        var rawMinimumPasswordLifetime = edit.getMinimumPasswordLifetime();
        var rawMaximumPasswordLifetime = edit.getMaximumPasswordLifetime();
        var rawExpirationWarningTime = edit.getExpirationWarningTime();
        var forceChangeAfterCreate = Boolean.valueOf(edit.getForceChangeAfterCreate());
        var forceChangeAfterReset = Boolean.valueOf(edit.getForceChangeAfterReset());
        var allowChange = Boolean.valueOf(edit.getAllowChange());
        var rawPasswordHistory = edit.getPasswordHistory();
        var passwordHistory = rawPasswordHistory == null? null: Integer.valueOf(rawPasswordHistory);
        var minimumPasswordLifetime = rawMinimumPasswordLifetime == null? null: Long.valueOf(rawMinimumPasswordLifetime);
        var minimumPasswordLifetimeConversion = minimumPasswordLifetime == null? null: new Conversion(uomControl, minimumPasswordLifetimeUnitOfMeasureType, minimumPasswordLifetime).convertToLowestUnitOfMeasureType();
        var maximumPasswordLifetime = rawMaximumPasswordLifetime == null? null: Long.valueOf(rawMaximumPasswordLifetime);
        var maximumPasswordLifetimeConversion = maximumPasswordLifetime == null? null: new Conversion(uomControl, maximumPasswordLifetimeUnitOfMeasureType, maximumPasswordLifetime).convertToLowestUnitOfMeasureType();
        var expirationWarningTime = rawExpirationWarningTime == null? null: Long.valueOf(rawExpirationWarningTime);
        var expirationWarningTimeConversion = expirationWarningTime == null? null: new Conversion(uomControl, expirationWarningTimeUnitOfMeasureType, expirationWarningTime).convertToLowestUnitOfMeasureType();
        var rawExpiredLoginsPermitted = edit.getExpiredLoginsPermitted();
        var expiredLoginsPermitted = rawExpiredLoginsPermitted == null? null: Integer.valueOf(rawExpiredLoginsPermitted);
        var rawMinimumLength = edit.getMinimumLength();
        var minimumLength = rawMinimumLength == null? null: Integer.valueOf(rawMinimumLength);
        var rawMaximumLength = edit.getMaximumLength();
        var maximumLength = rawMaximumLength == null? null: Integer.valueOf(rawMaximumLength);
        var rawRequiredDigitCount = edit.getRequiredDigitCount();
        var requiredDigitCount = rawRequiredDigitCount == null? null: Integer.valueOf(rawRequiredDigitCount);
        var rawRequiredLetterCount = edit.getRequiredLetterCount();
        var requiredLetterCount = rawRequiredLetterCount == null? null: Integer.valueOf(rawRequiredLetterCount);
        var rawRequiredUpperCaseCount = edit.getRequiredUpperCaseCount();
        var requiredUpperCaseCount = rawRequiredUpperCaseCount == null? null: Integer.valueOf(rawRequiredUpperCaseCount);
        var rawRequiredLowerCaseCount = edit.getRequiredLowerCaseCount();
        var requiredLowerCaseCount = rawRequiredLowerCaseCount == null? null: Integer.valueOf(rawRequiredLowerCaseCount);
        var rawMaximumRepeated = edit.getMaximumRepeated();
        var maximumRepeated = rawMaximumRepeated == null? null: Integer.valueOf(rawMaximumRepeated);
        var rawMinimumCharacterTypes = edit.getMinimumCharacterTypes();
        var minimumCharacterTypes = rawMinimumCharacterTypes == null? null: Integer.valueOf(rawMinimumCharacterTypes);

        partyTypePasswordStringPolicyDetailValue.setForceChangeAfterCreate(forceChangeAfterCreate);
        partyTypePasswordStringPolicyDetailValue.setForceChangeAfterReset(forceChangeAfterReset);
        partyTypePasswordStringPolicyDetailValue.setAllowChange(allowChange);
        partyTypePasswordStringPolicyDetailValue.setPasswordHistory(passwordHistory);
        partyTypePasswordStringPolicyDetailValue.setMinimumPasswordLifetime(minimumPasswordLifetimeConversion == null? null: minimumPasswordLifetimeConversion.getQuantity());
        partyTypePasswordStringPolicyDetailValue.setMaximumPasswordLifetime(maximumPasswordLifetimeConversion == null? null: maximumPasswordLifetimeConversion.getQuantity());
        partyTypePasswordStringPolicyDetailValue.setExpirationWarningTime(expirationWarningTimeConversion == null? null: expirationWarningTimeConversion.getQuantity());
        partyTypePasswordStringPolicyDetailValue.setExpiredLoginsPermitted(expiredLoginsPermitted);
        partyTypePasswordStringPolicyDetailValue.setMinimumLength(minimumLength);
        partyTypePasswordStringPolicyDetailValue.setMaximumLength(maximumLength);
        partyTypePasswordStringPolicyDetailValue.setRequiredDigitCount(requiredDigitCount);
        partyTypePasswordStringPolicyDetailValue.setRequiredLetterCount(requiredLetterCount);
        partyTypePasswordStringPolicyDetailValue.setRequiredUpperCaseCount(requiredUpperCaseCount);
        partyTypePasswordStringPolicyDetailValue.setRequiredLowerCaseCount(requiredLowerCaseCount);
        partyTypePasswordStringPolicyDetailValue.setMaximumRepeated(maximumRepeated);
        partyTypePasswordStringPolicyDetailValue.setMinimumCharacterTypes(minimumCharacterTypes);

        partyControl.updatePartyTypePasswordStringPolicyFromValue(partyTypePasswordStringPolicyDetailValue, getPartyPK());
    }

}
