// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.accounting.remote.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.remote.edit.GlAccountDescriptionEdit;
import com.echothree.control.user.accounting.remote.form.EditGlAccountDescriptionForm;
import com.echothree.control.user.accounting.remote.result.AccountingResultFactory;
import com.echothree.control.user.accounting.remote.result.EditGlAccountDescriptionResult;
import com.echothree.control.user.accounting.remote.spec.GlAccountDescriptionSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.model.data.accounting.server.value.GlAccountDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditGlAccountDescriptionCommand
        extends BaseEditCommand<GlAccountDescriptionSpec, GlAccountDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditGlAccountDescriptionCommand */
    public EditGlAccountDescriptionCommand(UserVisitPK userVisitPK, EditGlAccountDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditGlAccountDescriptionResult result = AccountingResultFactory.getEditGlAccountDescriptionResult();
        String glAccountName = spec.getGlAccountName();
        GlAccount glAccount = accountingControl.getGlAccountByName(glAccountName);
        
        if(glAccount != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    GlAccountDescription glAccountDescription = accountingControl.getGlAccountDescription(glAccount, language);
                    
                    if(glAccountDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setGlAccountDescription(accountingControl.getGlAccountDescriptionTransfer(getUserVisit(), glAccountDescription));

                            if(lockEntity(glAccount)) {
                                GlAccountDescriptionEdit edit = AccountingEditFactory.getGlAccountDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(glAccountDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(glAccount));
                        } else { // EditMode.ABANDON
                            unlockEntity(glAccount);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGlAccountDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    GlAccountDescriptionValue glAccountDescriptionValue = accountingControl.getGlAccountDescriptionValueForUpdate(glAccount, language);
                    
                    if(glAccountDescriptionValue != null) {
                        if(lockEntityForUpdate(glAccount)) {
                            try {
                                String description = edit.getDescription();
                                
                                glAccountDescriptionValue.setDescription(description);
                                
                                accountingControl.updateGlAccountDescriptionFromValue(glAccountDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(glAccount);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGlAccountDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
        }
        
        return result;
    }
    
}
