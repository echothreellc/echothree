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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PartyTypePasswordStringPolicyEdit;
import com.echothree.control.user.party.common.form.EditPartyTypePasswordStringPolicyForm;
import com.echothree.control.user.party.common.result.EditPartyTypePasswordStringPolicyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicyDetail;
import com.echothree.model.data.party.server.value.PartyTypePasswordStringPolicyDetailValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
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
    public EditPartyTypePasswordStringPolicyCommand(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditPartyTypePasswordStringPolicyResult result = PartyResultFactory.getEditPartyTypePasswordStringPolicyResult();
        String partyTypeName = spec.getPartyTypeName();
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
        
        if(partyType != null) {
            PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(partyType);
            
            if(partyTypePasswordStringPolicy != null) {
                var uomControl = (UomControl)Session.getModelController(UomControl.class);
                UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                
                if(timeUnitOfMeasureKind != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));
                        
                        if(lockEntity(partyTypePasswordStringPolicy)) {
                            PartyTypePasswordStringPolicyEdit edit = PartyEditFactory.getPartyTypePasswordStringPolicyEdit();
                            PartyTypePasswordStringPolicyDetail partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetail();
                            Integer passwordHistory = partyTypePasswordStringPolicyDetail.getPasswordHistory();
                            Long minimumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMinimumPasswordLifetime();
                            Conversion minimumPasswordLifetimeConversion = minimumPasswordLifetime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, minimumPasswordLifetime).convertToHighestUnitOfMeasureType();
                            Long maximumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMaximumPasswordLifetime();
                            Conversion maximumPasswordLifetimeConversion = maximumPasswordLifetime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, maximumPasswordLifetime).convertToHighestUnitOfMeasureType();
                            Long expirationWarningTime = partyTypePasswordStringPolicyDetail.getExpirationWarningTime();
                            Conversion expirationWarningTimeConversion = expirationWarningTime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, expirationWarningTime).convertToHighestUnitOfMeasureType();
                            Integer expiredLoginsPermitted = partyTypePasswordStringPolicyDetail.getExpiredLoginsPermitted();
                            Integer minimumLength = partyTypePasswordStringPolicyDetail.getMinimumLength();
                            Integer maximumLength = partyTypePasswordStringPolicyDetail.getMaximumLength();
                            Integer requiredDigitCount = partyTypePasswordStringPolicyDetail.getRequiredDigitCount();
                            Integer requiredLetterCount = partyTypePasswordStringPolicyDetail.getRequiredLetterCount();
                            Integer requiredUpperCaseCount = partyTypePasswordStringPolicyDetail.getRequiredUpperCaseCount();
                            Integer requiredLowerCaseCount = partyTypePasswordStringPolicyDetail.getRequiredLowerCaseCount();
                            Integer maximumRepeated = partyTypePasswordStringPolicyDetail.getMaximumRepeated();
                            Integer minimumCharacterTypes = partyTypePasswordStringPolicyDetail.getMinimumCharacterTypes();
                            
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
                        PartyTypePasswordStringPolicyDetailValue partyTypePasswordStringPolicyDetailValue = partyControl.getPartyTypePasswordStringPolicyDetailValueForUpdate(partyTypePasswordStringPolicy);
                        
                        if(partyTypePasswordStringPolicyDetailValue != null) {
                            String rawMinimumPasswordLifetime = edit.getMinimumPasswordLifetime();
                            String minimumPasswordLifetimeUnitOfMeasureTypeName = edit.getMinimumPasswordLifetimeUnitOfMeasureTypeName();
                            int minimumPasswordLifetimeParameterCount = (rawMinimumPasswordLifetime == null? 0: 1) + (minimumPasswordLifetimeUnitOfMeasureTypeName == null? 0: 1);
                            String rawMaximumPasswordLifetime = edit.getMaximumPasswordLifetime();
                            String maximumPasswordLifetimeUnitOfMeasureTypeName = edit.getMaximumPasswordLifetimeUnitOfMeasureTypeName();
                            int maximumPasswordLifetimeParameterCount = (rawMaximumPasswordLifetime == null? 0: 1) + (maximumPasswordLifetimeUnitOfMeasureTypeName == null? 0: 1);
                            String rawExpirationWarningTime = edit.getExpirationWarningTime();
                            String expirationWarningTimeUnitOfMeasureTypeName = edit.getExpirationWarningTimeUnitOfMeasureTypeName();
                            int expirationWarningTimeParameterCount = (rawExpirationWarningTime == null? 0: 1) + (expirationWarningTimeUnitOfMeasureTypeName == null? 0: 1);
                            
                            if((minimumPasswordLifetimeParameterCount == 0 || minimumPasswordLifetimeParameterCount == 2) &&
                                    (maximumPasswordLifetimeParameterCount == 0 || maximumPasswordLifetimeParameterCount == 2) &&
                                    (expirationWarningTimeParameterCount == 0 || expirationWarningTimeParameterCount == 2)) {
                                UnitOfMeasureType minimumPasswordLifetimeUnitOfMeasureType = minimumPasswordLifetimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                        minimumPasswordLifetimeUnitOfMeasureTypeName);
                                
                                if(minimumPasswordLifetimeUnitOfMeasureTypeName == null || minimumPasswordLifetimeUnitOfMeasureType != null) {
                                    UnitOfMeasureType maximumPasswordLifetimeUnitOfMeasureType = maximumPasswordLifetimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                            maximumPasswordLifetimeUnitOfMeasureTypeName);
                                    
                                    if(maximumPasswordLifetimeUnitOfMeasureTypeName == null || maximumPasswordLifetimeUnitOfMeasureType != null) {
                                        UnitOfMeasureType expirationWarningTimeUnitOfMeasureType = expirationWarningTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                                expirationWarningTimeUnitOfMeasureTypeName);
                                        
                                        if(expirationWarningTimeUnitOfMeasureTypeName == null || expirationWarningTimeUnitOfMeasureType != null) {
                                            if(lockEntityForUpdate(partyTypePasswordStringPolicy)) {
                                                try {
                                                    Boolean forceChangeAfterCreate = Boolean.valueOf(edit.getForceChangeAfterCreate());
                                                    Boolean forceChangeAfterReset = Boolean.valueOf(edit.getForceChangeAfterReset());
                                                    Boolean allowChange = Boolean.valueOf(edit.getAllowChange());
                                                    String rawPasswordHistory = edit.getPasswordHistory();
                                                    Integer passwordHistory = rawPasswordHistory == null? null: Integer.valueOf(rawPasswordHistory);
                                                    Long minimumPasswordLifetime = rawMinimumPasswordLifetime == null? null: Long.valueOf(rawMinimumPasswordLifetime);
                                                    Conversion minimumPasswordLifetimeConversion = minimumPasswordLifetime == null? null: new Conversion(uomControl, minimumPasswordLifetimeUnitOfMeasureType, minimumPasswordLifetime).convertToLowestUnitOfMeasureType();
                                                    Long maximumPasswordLifetime = rawMaximumPasswordLifetime == null? null: Long.valueOf(rawMaximumPasswordLifetime);
                                                    Conversion maximumPasswordLifetimeConversion = maximumPasswordLifetime == null? null: new Conversion(uomControl, maximumPasswordLifetimeUnitOfMeasureType, maximumPasswordLifetime).convertToLowestUnitOfMeasureType();
                                                    Long expirationWarningTime = rawExpirationWarningTime == null? null: Long.valueOf(rawExpirationWarningTime);
                                                    Conversion expirationWarningTimeConversion = expirationWarningTime == null? null: new Conversion(uomControl, expirationWarningTimeUnitOfMeasureType, expirationWarningTime).convertToLowestUnitOfMeasureType();
                                                    String rawExpiredLoginsPermitted = edit.getExpiredLoginsPermitted();
                                                    Integer expiredLoginsPermitted = rawExpiredLoginsPermitted == null? null: Integer.valueOf(rawExpiredLoginsPermitted);
                                                    String rawMinimumLength = edit.getMinimumLength();
                                                    Integer minimumLength = rawMinimumLength == null? null: Integer.valueOf(rawMinimumLength);
                                                    String rawMaximumLength = edit.getMaximumLength();
                                                    Integer maximumLength = rawMaximumLength == null? null: Integer.valueOf(rawMaximumLength);
                                                    String rawRequiredDigitCount = edit.getRequiredDigitCount();
                                                    Integer requiredDigitCount = rawRequiredDigitCount == null? null: Integer.valueOf(rawRequiredDigitCount);
                                                    String rawRequiredLetterCount = edit.getRequiredLetterCount();
                                                    Integer requiredLetterCount = rawRequiredLetterCount == null? null: Integer.valueOf(rawRequiredLetterCount);
                                                    String rawRequiredUpperCaseCount = edit.getRequiredUpperCaseCount();
                                                    Integer requiredUpperCaseCount = rawRequiredUpperCaseCount == null? null: Integer.valueOf(rawRequiredUpperCaseCount);
                                                    String rawRequiredLowerCaseCount = edit.getRequiredLowerCaseCount();
                                                    Integer requiredLowerCaseCount = rawRequiredLowerCaseCount == null? null: Integer.valueOf(rawRequiredLowerCaseCount);
                                                    String rawMaximumRepeated = edit.getMaximumRepeated();
                                                    Integer maximumRepeated = rawMaximumRepeated == null? null: Integer.valueOf(rawMaximumRepeated);
                                                    String rawMinimumCharacterTypes = edit.getMinimumCharacterTypes();
                                                    Integer minimumCharacterTypes = rawMinimumCharacterTypes == null? null: Integer.valueOf(rawMinimumCharacterTypes);
                                                    
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
