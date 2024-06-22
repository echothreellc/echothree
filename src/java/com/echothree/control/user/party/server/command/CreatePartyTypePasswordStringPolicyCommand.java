// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.CreatePartyTypePasswordStringPolicyForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
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

public class CreatePartyTypePasswordStringPolicyCommand
        extends BaseSimpleCommand<CreatePartyTypePasswordStringPolicyForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
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
    
    /** Creates a new instance of CreatePartyTypePasswordStringPolicyCommand */
    public CreatePartyTypePasswordStringPolicyCommand(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String rawMinimumPasswordLifetime = form.getMinimumPasswordLifetime();
        String minimumPasswordLifetimeUnitOfMeasureTypeName = form.getMinimumPasswordLifetimeUnitOfMeasureTypeName();
        int minimumPasswordLifetimeParameterCount = (rawMinimumPasswordLifetime == null ? 0 : 1) + (minimumPasswordLifetimeUnitOfMeasureTypeName == null ? 0 : 1);
        String rawMaximumPasswordLifetime = form.getMaximumPasswordLifetime();
        String maximumPasswordLifetimeUnitOfMeasureTypeName = form.getMaximumPasswordLifetimeUnitOfMeasureTypeName();
        int maximumPasswordLifetimeParameterCount = (rawMaximumPasswordLifetime == null ? 0 : 1) + (maximumPasswordLifetimeUnitOfMeasureTypeName == null ? 0 : 1);
        String rawExpirationWarningTime = form.getExpirationWarningTime();
        String expirationWarningTimeUnitOfMeasureTypeName = form.getExpirationWarningTimeUnitOfMeasureTypeName();
        int expirationWarningTimeParameterCount = (rawExpirationWarningTime == null ? 0 : 1) + (expirationWarningTimeUnitOfMeasureTypeName == null ? 0 : 1);
        
        if((minimumPasswordLifetimeParameterCount == 0 || minimumPasswordLifetimeParameterCount == 2) &&
                (maximumPasswordLifetimeParameterCount == 0 || maximumPasswordLifetimeParameterCount == 2) &&
                (expirationWarningTimeParameterCount == 0 || expirationWarningTimeParameterCount == 2)) {
            var partyControl = Session.getModelController(PartyControl.class);
            String partyTypeName = form.getPartyTypeName();
            PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
            
            if(partyType != null) {
                if(partyType.getAllowUserLogins()) {
                    PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(partyType);

                    if(partyTypePasswordStringPolicy == null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                        if(timeUnitOfMeasureKind != null) {
                            UnitOfMeasureType minimumPasswordLifetimeUnitOfMeasureType = minimumPasswordLifetimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                    minimumPasswordLifetimeUnitOfMeasureTypeName);

                            if(minimumPasswordLifetimeUnitOfMeasureTypeName == null || minimumPasswordLifetimeUnitOfMeasureType != null) {
                                UnitOfMeasureType maximumPasswordLifetimeUnitOfMeasureType = maximumPasswordLifetimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                        maximumPasswordLifetimeUnitOfMeasureTypeName);

                                if(maximumPasswordLifetimeUnitOfMeasureTypeName == null || maximumPasswordLifetimeUnitOfMeasureType != null) {
                                    UnitOfMeasureType expirationWarningTimeUnitOfMeasureType = expirationWarningTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                            expirationWarningTimeUnitOfMeasureTypeName);

                                    if(expirationWarningTimeUnitOfMeasureTypeName == null || expirationWarningTimeUnitOfMeasureType != null) {
                                        Boolean forceChangeAfterCreate = Boolean.valueOf(form.getForceChangeAfterCreate());
                                        Boolean forceChangeAfterReset = Boolean.valueOf(form.getForceChangeAfterReset());
                                        Boolean allowChange = Boolean.valueOf(form.getAllowChange());
                                        String rawPasswordHistory = form.getPasswordHistory();
                                        Integer passwordHistory = rawPasswordHistory == null? null: Integer.valueOf(rawPasswordHistory);
                                        Long minimumPasswordLifetime = rawMinimumPasswordLifetime == null? null: Long.valueOf(rawMinimumPasswordLifetime);
                                        Conversion minimumPasswordLifetimeConversion = minimumPasswordLifetime == null? null: new Conversion(uomControl, minimumPasswordLifetimeUnitOfMeasureType, minimumPasswordLifetime).convertToLowestUnitOfMeasureType();
                                        Long maximumPasswordLifetime = rawMaximumPasswordLifetime == null? null: Long.valueOf(rawMaximumPasswordLifetime);
                                        Conversion maximumPasswordLifetimeConversion = maximumPasswordLifetime == null? null: new Conversion(uomControl, maximumPasswordLifetimeUnitOfMeasureType, maximumPasswordLifetime).convertToLowestUnitOfMeasureType();
                                        Long expirationWarningTime = rawExpirationWarningTime == null? null: Long.valueOf(rawExpirationWarningTime);
                                        Conversion expirationWarningTimeConversion = expirationWarningTime == null? null: new Conversion(uomControl, expirationWarningTimeUnitOfMeasureType, expirationWarningTime).convertToLowestUnitOfMeasureType();
                                        String rawExpiredLoginsPermitted = form.getExpiredLoginsPermitted();
                                        Integer expiredLoginsPermitted = rawExpiredLoginsPermitted == null? null: Integer.valueOf(rawExpiredLoginsPermitted);
                                        String rawMinimumLength = form.getMinimumLength();
                                        Integer minimumLength = rawMinimumLength == null? null: Integer.valueOf(rawMinimumLength);
                                        String rawMaximumLength = form.getMaximumLength();
                                        Integer maximumLength = rawMaximumLength == null? null: Integer.valueOf(rawMaximumLength);
                                        String rawRequiredDigitCount = form.getRequiredDigitCount();
                                        Integer requiredDigitCount = rawRequiredDigitCount == null? null: Integer.valueOf(rawRequiredDigitCount);
                                        String rawRequiredLetterCount = form.getRequiredLetterCount();
                                        Integer requiredLetterCount = rawRequiredLetterCount == null? null: Integer.valueOf(rawRequiredLetterCount);
                                        String rawRequiredUpperCaseCount = form.getRequiredUpperCaseCount();
                                        Integer requiredUpperCaseCount = rawRequiredUpperCaseCount == null? null: Integer.valueOf(rawRequiredUpperCaseCount);
                                        String rawRequiredLowerCaseCount = form.getRequiredLowerCaseCount();
                                        Integer requiredLowerCaseCount = rawRequiredLowerCaseCount == null? null: Integer.valueOf(rawRequiredLowerCaseCount);
                                        String rawMaximumRepeated = form.getMaximumRepeated();
                                        Integer maximumRepeated = rawMaximumRepeated == null? null: Integer.valueOf(rawMaximumRepeated);
                                        String rawMinimumCharacterTypes = form.getMinimumCharacterTypes();
                                        Integer minimumCharacterTypes = rawMinimumCharacterTypes == null? null: Integer.valueOf(rawMinimumCharacterTypes);

                                        partyControl.createPartyTypePasswordStringPolicy(partyType, forceChangeAfterCreate, forceChangeAfterReset, allowChange,
                                                passwordHistory, minimumPasswordLifetimeConversion == null? null: minimumPasswordLifetimeConversion.getQuantity(),
                                                maximumPasswordLifetimeConversion == null? null: maximumPasswordLifetimeConversion.getQuantity(),
                                                expirationWarningTimeConversion == null? null: expirationWarningTimeConversion.getQuantity(),
                                                expiredLoginsPermitted, minimumLength, maximumLength, requiredDigitCount,
                                                requiredLetterCount, requiredUpperCaseCount, requiredLowerCaseCount, maximumRepeated,
                                                minimumCharacterTypes, getPartyPK());
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
                            addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyTypePasswordStringPolicy.name());
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
