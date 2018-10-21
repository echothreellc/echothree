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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.remote.edit.CustomerEditFactory;
import com.echothree.control.user.customer.remote.edit.CustomerTypeDescriptionEdit;
import com.echothree.control.user.customer.remote.form.EditCustomerTypeDescriptionForm;
import com.echothree.control.user.customer.remote.result.CustomerResultFactory;
import com.echothree.control.user.customer.remote.result.EditCustomerTypeDescriptionResult;
import com.echothree.control.user.customer.remote.spec.CustomerTypeDescriptionSpec;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDescription;
import com.echothree.model.data.customer.server.value.CustomerTypeDescriptionValue;
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

public class EditCustomerTypeDescriptionCommand
        extends BaseEditCommand<CustomerTypeDescriptionSpec, CustomerTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.CustomerType.name(), SecurityRoles.Description.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCustomerTypeDescriptionCommand */
    public EditCustomerTypeDescriptionCommand(UserVisitPK userVisitPK, EditCustomerTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        EditCustomerTypeDescriptionResult result = CustomerResultFactory.getEditCustomerTypeDescriptionResult();
        String customerTypeName = spec.getCustomerTypeName();
        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    CustomerTypeDescription customerTypeDescription = customerControl.getCustomerTypeDescription(customerType, language);
                    
                    if(customerTypeDescription != null) {
                        result.setCustomerTypeDescription(customerControl.getCustomerTypeDescriptionTransfer(getUserVisit(), customerTypeDescription));
                        
                        if(lockEntity(customerType)) {
                            CustomerTypeDescriptionEdit edit = CustomerEditFactory.getCustomerTypeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(customerTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(customerType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    CustomerTypeDescriptionValue customerTypeDescriptionValue = customerControl.getCustomerTypeDescriptionValueForUpdate(customerType, language);
                    
                    if(customerTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(customerType)) {
                            try {
                                String description = edit.getDescription();
                                
                                customerTypeDescriptionValue.setDescription(description);
                                
                                customerControl.updateCustomerTypeDescriptionFromValue(customerTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(customerType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return result;
    }
    
}
