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

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PartyTypePasswordStringPolicyEdit;
import com.echothree.control.user.party.common.form.EditPartyTypePasswordStringPolicyForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditPartyTypePasswordStringPolicyCommand
        extends BaseEditCommand<PartyTypeSpec, PartyTypePasswordStringPolicyEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
                ));
    }
    
    /** Creates a new instance of EditPartyTypePasswordStringPolicyCommand */
    public EditPartyTypePasswordStringPolicyCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditPartyTypePasswordStringPolicyResult();
        var partyTypeName = spec.getPartyTypeName();
        var partyType = partyControl.getPartyTypeByName(partyTypeName);
        
        if(partyType != null) {
            var partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(partyType);
            
            if(partyTypePasswordStringPolicy != null) {
                var uomControl = Session.getModelController(UomControl.class);
                var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                
                if(timeUnitOfMeasureKind != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));
                        
                        if(lockEntity(partyTypePasswordStringPolicy)) {
                            var edit = PartyEditFactory.getPartyTypePasswordStringPolicyEdit();
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
                            
                            result.setEdit(edit);
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
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(partyTypePasswordStringPolicy));
                    } else if(editMode.equals(EditMode.ABANDON)) {
                        unlockEntity(partyTypePasswordStringPolicy);
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var partyTypePasswordStringPolicyDetailValue = partyControl.getPartyTypePasswordStringPolicyDetailValueForUpdate(partyTypePasswordStringPolicy);
                        
                        if(partyTypePasswordStringPolicyDetailValue != null) {
                            var rawMinimumPasswordLifetime = edit.getMinimumPasswordLifetime();
                            var minimumPasswordLifetimeUnitOfMeasureTypeName = edit.getMinimumPasswordLifetimeUnitOfMeasureTypeName();
                            var minimumPasswordLifetimeParameterCount = (rawMinimumPasswordLifetime == null ? 0 : 1) + (minimumPasswordLifetimeUnitOfMeasureTypeName == null ? 0 : 1);
                            var rawMaximumPasswordLifetime = edit.getMaximumPasswordLifetime();
                            var maximumPasswordLifetimeUnitOfMeasureTypeName = edit.getMaximumPasswordLifetimeUnitOfMeasureTypeName();
                            var maximumPasswordLifetimeParameterCount = (rawMaximumPasswordLifetime == null ? 0 : 1) + (maximumPasswordLifetimeUnitOfMeasureTypeName == null ? 0 : 1);
                            var rawExpirationWarningTime = edit.getExpirationWarningTime();
                            var expirationWarningTimeUnitOfMeasureTypeName = edit.getExpirationWarningTimeUnitOfMeasureTypeName();
                            var expirationWarningTimeParameterCount = (rawExpirationWarningTime == null ? 0 : 1) + (expirationWarningTimeUnitOfMeasureTypeName == null ? 0 : 1);
                            
                            if((minimumPasswordLifetimeParameterCount == 0 || minimumPasswordLifetimeParameterCount == 2) &&
                                    (maximumPasswordLifetimeParameterCount == 0 || maximumPasswordLifetimeParameterCount == 2) &&
                                    (expirationWarningTimeParameterCount == 0 || expirationWarningTimeParameterCount == 2)) {
                                var minimumPasswordLifetimeUnitOfMeasureType = minimumPasswordLifetimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                        minimumPasswordLifetimeUnitOfMeasureTypeName);
                                
                                if(minimumPasswordLifetimeUnitOfMeasureTypeName == null || minimumPasswordLifetimeUnitOfMeasureType != null) {
                                    var maximumPasswordLifetimeUnitOfMeasureType = maximumPasswordLifetimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                            maximumPasswordLifetimeUnitOfMeasureTypeName);
                                    
                                    if(maximumPasswordLifetimeUnitOfMeasureTypeName == null || maximumPasswordLifetimeUnitOfMeasureType != null) {
                                        var expirationWarningTimeUnitOfMeasureType = expirationWarningTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                                expirationWarningTimeUnitOfMeasureTypeName);
                                        
                                        if(expirationWarningTimeUnitOfMeasureTypeName == null || expirationWarningTimeUnitOfMeasureType != null) {
                                            if(lockEntityForUpdate(partyTypePasswordStringPolicy)) {
                                                try {
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
                                                } finally {
                                                    unlockEntity(partyTypePasswordStringPolicy);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownExpirationWarningTimeUnitOfMeasureTypeName.name(), expirationWarningTimeUnitOfMeasureTypeName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownMaximumPasswordLifetimeUnitOfMeasureTypeName.name(), maximumPasswordLifetimeUnitOfMeasureTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMinimumPasswordLifetimeUnitOfMeasureTypeName.name(), minimumPasswordLifetimeUnitOfMeasureTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                            }
                            
                            if(hasExecutionErrors()) {
                                result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));
                                result.setEntityLock(getEntityLockTransfer(partyTypePasswordStringPolicy));
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPartyTypePasswordStringPolicy.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTypePasswordStringPolicy.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }
        
        return result;
    }
    
}
