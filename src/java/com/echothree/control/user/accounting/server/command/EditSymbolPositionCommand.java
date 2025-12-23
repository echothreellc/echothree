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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.SymbolPositionEdit;
import com.echothree.control.user.accounting.common.form.EditSymbolPositionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.SymbolPositionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditSymbolPositionCommand
        extends BaseEditCommand<SymbolPositionSpec, SymbolPositionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SymbolPosition.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSymbolPositionCommand */
    public EditSymbolPositionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditSymbolPositionResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            var symbolPositionName = spec.getSymbolPositionName();
            var symbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName);
            
            if(symbolPosition != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(symbolPosition)) {
                        var symbolPositionDescription = accountingControl.getSymbolPositionDescription(symbolPosition, getPreferredLanguage());
                        var edit = AccountingEditFactory.getSymbolPositionEdit();
                        var symbolPositionDetail = symbolPosition.getLastDetail();

                        result.setSymbolPosition(accountingControl.getSymbolPositionTransfer(getUserVisit(), symbolPosition));

                        result.setEdit(edit);
                        edit.setSymbolPositionName(symbolPositionDetail.getSymbolPositionName());
                        edit.setIsDefault(symbolPositionDetail.getIsDefault().toString());
                        edit.setSortOrder(symbolPositionDetail.getSortOrder().toString());

                        if(symbolPositionDescription != null) {
                            edit.setDescription(symbolPositionDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(symbolPosition));
                } else { // EditMode.ABANDON
                    unlockEntity(symbolPosition);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSymbolPositionName.name(), symbolPositionName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var symbolPositionName = spec.getSymbolPositionName();
            var symbolPosition = accountingControl.getSymbolPositionByNameForUpdate(symbolPositionName);
            
            if(symbolPosition != null) {
                symbolPositionName = edit.getSymbolPositionName();
                var duplicateSymbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName);
                
                if(duplicateSymbolPosition == null || symbolPosition.equals(duplicateSymbolPosition)) {
                    if(lockEntityForUpdate(symbolPosition)) {
                        try {
                            var partyPK = getPartyPK();
                            var symbolPositionDetailValue = accountingControl.getSymbolPositionDetailValueForUpdate(symbolPosition);
                            var symbolPositionDescription = accountingControl.getSymbolPositionDescriptionForUpdate(symbolPosition, getPreferredLanguage());
                            var description = edit.getDescription();
                            
                            symbolPositionDetailValue.setSymbolPositionName(edit.getSymbolPositionName());
                            symbolPositionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            symbolPositionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            accountingControl.updateSymbolPositionFromValue(symbolPositionDetailValue, partyPK);
                            
                            if(symbolPositionDescription == null && description != null) {
                                accountingControl.createSymbolPositionDescription(symbolPosition, getPreferredLanguage(), description, partyPK);
                            } else if(symbolPositionDescription != null && description == null) {
                                accountingControl.deleteSymbolPositionDescription(symbolPositionDescription, partyPK);
                            } else if(symbolPositionDescription != null && description != null) {
                                var symbolPositionDescriptionValue = accountingControl.getSymbolPositionDescriptionValue(symbolPositionDescription);
                                
                                symbolPositionDescriptionValue.setDescription(description);
                                accountingControl.updateSymbolPositionDescriptionFromValue(symbolPositionDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(symbolPosition);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateSymbolPositionName.name(), symbolPositionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSymbolPositionName.name(), symbolPositionName);
            }
            
            if(hasExecutionErrors()) {
                result.setSymbolPosition(accountingControl.getSymbolPositionTransfer(getUserVisit(), symbolPosition));
                result.setEntityLock(getEntityLockTransfer(symbolPosition));
            }
        }
        
        return result;
    }
    
}
