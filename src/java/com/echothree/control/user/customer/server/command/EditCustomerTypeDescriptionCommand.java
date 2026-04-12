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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.edit.CustomerEditFactory;
import com.echothree.control.user.customer.common.edit.CustomerTypeDescriptionEdit;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerTypeDescriptionResult;
import com.echothree.control.user.customer.common.spec.CustomerTypeDescriptionSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDescription;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCustomerTypeDescriptionCommand
        extends BaseAbstractEditCommand<CustomerTypeDescriptionSpec, CustomerTypeDescriptionEdit, EditCustomerTypeDescriptionResult, CustomerTypeDescription, CustomerType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditCustomerTypeDescriptionCommand */
    public EditCustomerTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    CustomerControl customerControl;

    @Inject
    PartyControl partyControl;

    @Override
    protected EditCustomerTypeDescriptionResult getResult() {
        return CustomerResultFactory.getEditCustomerTypeDescriptionResult();
    }

    @Override
    protected CustomerTypeDescriptionEdit getEdit() {
        return CustomerEditFactory.getCustomerTypeDescriptionEdit();
    }

    @Override
    protected CustomerTypeDescription getEntity(EditCustomerTypeDescriptionResult result) {
        CustomerTypeDescription customerTypeDescription = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    customerTypeDescription = customerControl.getCustomerTypeDescription(customerType, language);
                } else { // EditMode.UPDATE
                    customerTypeDescription = customerControl.getCustomerTypeDescriptionForUpdate(customerType, language);
                }

                if(customerTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeDescription.name(), customerTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }

        return customerTypeDescription;
    }

    @Override
    protected CustomerType getLockEntity(CustomerTypeDescription customerTypeDescription) {
        return customerTypeDescription.getCustomerType();
    }

    @Override
    protected void fillInResult(EditCustomerTypeDescriptionResult result, CustomerTypeDescription customerTypeDescription) {
        result.setCustomerTypeDescription(customerControl.getCustomerTypeDescriptionTransfer(getUserVisit(), customerTypeDescription));
    }

    @Override
    protected void doLock(CustomerTypeDescriptionEdit edit, CustomerTypeDescription customerTypeDescription) {
        edit.setDescription(customerTypeDescription.getDescription());
    }

    @Override
    protected void doUpdate(CustomerTypeDescription customerTypeDescription) {
        var customerTypeDescriptionValue = customerControl.getCustomerTypeDescriptionValue(customerTypeDescription);

        customerTypeDescriptionValue.setDescription(edit.getDescription());

        customerControl.updateCustomerTypeDescriptionFromValue(customerTypeDescriptionValue, getPartyPK());
    }
    
}
