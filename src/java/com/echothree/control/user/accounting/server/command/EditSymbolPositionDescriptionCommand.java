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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.SymbolPositionDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditSymbolPositionDescriptionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditSymbolPositionDescriptionResult;
import com.echothree.control.user.accounting.common.spec.SymbolPositionDescriptionSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDescription;
import com.echothree.model.data.accounting.server.value.SymbolPositionDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditSymbolPositionDescriptionCommand
        extends BaseEditCommand<SymbolPositionDescriptionSpec, SymbolPositionDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SymbolPosition.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditSymbolPositionDescriptionCommand */
    public EditSymbolPositionDescriptionCommand(UserVisitPK userVisitPK, EditSymbolPositionDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditSymbolPositionDescriptionResult result = AccountingResultFactory.getEditSymbolPositionDescriptionResult();
        String symbolPositionName = spec.getSymbolPositionName();
        SymbolPosition symbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName);
        
        if(symbolPosition != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    SymbolPositionDescription symbolPositionDescription = accountingControl.getSymbolPositionDescription(symbolPosition, language);
                    
                    if(symbolPositionDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setSymbolPositionDescription(accountingControl.getSymbolPositionDescriptionTransfer(getUserVisit(), symbolPositionDescription));

                            if(lockEntity(symbolPosition)) {
                                SymbolPositionDescriptionEdit edit = AccountingEditFactory.getSymbolPositionDescriptionEdit();

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
                    SymbolPositionDescriptionValue symbolPositionDescriptionValue = accountingControl.getSymbolPositionDescriptionValueForUpdate(symbolPosition, language);
                    
                    if(symbolPositionDescriptionValue != null) {
                        if(lockEntityForUpdate(symbolPosition)) {
                            try {
                                String description = edit.getDescription();
                                
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
