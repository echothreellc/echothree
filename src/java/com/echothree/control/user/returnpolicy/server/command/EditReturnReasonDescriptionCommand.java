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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonDescriptionEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnReasonDescriptionForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnReasonDescriptionResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDescription;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonDescriptionValue;
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

public class EditReturnReasonDescriptionCommand
        extends BaseEditCommand<ReturnReasonDescriptionSpec, ReturnReasonDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnReason.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditReturnReasonDescriptionCommand */
    public EditReturnReasonDescriptionCommand(UserVisitPK userVisitPK, EditReturnReasonDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        EditReturnReasonDescriptionResult result = ReturnPolicyResultFactory.getEditReturnReasonDescriptionResult();
        String returnKindName = spec.getReturnKindName();
        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            String returnReasonName = spec.getReturnReasonName();
            ReturnReason returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);
            
            if(returnReason != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        ReturnReasonDescription returnReasonDescription = returnPolicyControl.getReturnReasonDescription(returnReason, language);
                        
                        if(returnReasonDescription != null) {
                            result.setReturnReasonDescription(returnPolicyControl.getReturnReasonDescriptionTransfer(getUserVisit(), returnReasonDescription));
                            
                            if(lockEntity(returnReason)) {
                                ReturnReasonDescriptionEdit edit = ReturnPolicyEditFactory.getReturnReasonDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(returnReasonDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(returnReason));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnReasonDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        ReturnReasonDescriptionValue returnReasonDescriptionValue = returnPolicyControl.getReturnReasonDescriptionValueForUpdate(returnReason, language);
                        
                        if(returnReasonDescriptionValue != null) {
                            if(lockEntityForUpdate(returnReason)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    returnReasonDescriptionValue.setDescription(description);
                                    
                                    returnPolicyControl.updateReturnReasonDescriptionFromValue(returnReasonDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(returnReason);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnReasonDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }
        
        return result;
    }
    
}
