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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.SymbolPositionDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditSymbolPositionDescriptionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.SymbolPositionDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
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
public class EditSymbolPositionDescriptionCommand
        extends BaseEditCommand<SymbolPositionDescriptionSpec, SymbolPositionDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SymbolPosition.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSymbolPositionDescriptionCommand */
    public EditSymbolPositionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditSymbolPositionDescriptionResult();
        var symbolPositionName = spec.getSymbolPositionName();
        var symbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName);
        
        if(symbolPosition != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    var symbolPositionDescription = accountingControl.getSymbolPositionDescription(symbolPosition, language);
                    
                    if(symbolPositionDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setSymbolPositionDescription(accountingControl.getSymbolPositionDescriptionTransfer(getUserVisit(), symbolPositionDescription));

                            if(lockEntity(symbolPosition)) {
                                var edit = AccountingEditFactory.getSymbolPositionDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(symbolPositionDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(symbolPosition));
                        } else { // EditMode.ABANDON
                            unlockEntity(symbolPosition);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSymbolPositionDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var symbolPositionDescriptionValue = accountingControl.getSymbolPositionDescriptionValueForUpdate(symbolPosition, language);
                    
                    if(symbolPositionDescriptionValue != null) {
                        if(lockEntityForUpdate(symbolPosition)) {
                            try {
                                var description = edit.getDescription();
                                
                                symbolPositionDescriptionValue.setDescription(description);
                                
                                accountingControl.updateSymbolPositionDescriptionFromValue(symbolPositionDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(symbolPosition);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSymbolPositionDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSymbolPositionName.name(), symbolPositionName);
        }
        
        return result;
    }
    
}
