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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferCustomerTypeEdit;
import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.form.EditOfferCustomerTypeForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferCustomerTypeSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.server.control.OfferControl;
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
public class EditOfferCustomerTypeCommand
        extends BaseEditCommand<OfferCustomerTypeSpec, OfferCustomerTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferCustomerType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditOfferCustomerTypeCommand */
    public EditOfferCustomerTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = Session.getModelController(OfferControl.class);
        var result = OfferResultFactory.getEditOfferCustomerTypeResult();
        var offerName = spec.getOfferName();
        var offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var customerTypeName = spec.getCustomerTypeName();
            var customerType = customerControl.getCustomerTypeByName(customerTypeName);

            if(customerType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var offerCustomerType = offerControl.getOfferCustomerType(offer, customerType);

                    if(offerCustomerType != null) {
                        result.setOfferCustomerType(offerControl.getOfferCustomerTypeTransfer(getUserVisit(), offerCustomerType));

                        if(lockEntity(offer)) {
                            var edit = OfferEditFactory.getOfferCustomerTypeEdit();

                            result.setEdit(edit);
                            edit.setIsDefault(offerCustomerType.getIsDefault().toString());
                            edit.setSortOrder(offerCustomerType.getSortOrder().toString());

                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(offerCustomerType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOfferCustomerType.name(), offerName, customerTypeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var offerCustomerTypeValue = offerControl.getOfferCustomerTypeValueForUpdate(offer, customerType);

                    if(offerCustomerTypeValue != null) {
                        if(lockEntityForUpdate(offer)) {
                            try {
                                offerCustomerTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                offerCustomerTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                offerControl.updateOfferCustomerTypeFromValue(offerCustomerTypeValue, getPartyPK());
                            } finally {
                                unlockEntity(offer);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOfferCustomerType.name(), offerName, customerTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            }
    } else {
        addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
    }
        
        return result;
    }
    
}
