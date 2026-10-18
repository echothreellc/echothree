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
import com.echothree.control.user.offer.common.result.EditOfferCustomerTypeResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferCustomerTypeSpec;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
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
public class EditOfferCustomerTypeCommand
        extends BaseAbstractEditCommand<OfferCustomerTypeSpec, OfferCustomerTypeEdit, EditOfferCustomerTypeResult, OfferCustomerType, Offer> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferCustomerType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    OfferControl offerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    @Inject
    OfferLogic offerLogic;

    /** Creates a new instance of EditOfferCustomerTypeCommand */
    public EditOfferCustomerTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOfferCustomerTypeResult getResult() {
        return OfferResultFactory.getEditOfferCustomerTypeResult();
    }

    @Override
    public OfferCustomerTypeEdit getEdit() {
        return OfferEditFactory.getOfferCustomerTypeEdit();
    }

    @Override
    public OfferCustomerType getEntity(EditOfferCustomerTypeResult result) {
        OfferCustomerType offerCustomerType = null;
        var offerName = spec.getOfferName();
        var offer = offerLogic.getOfferByName(this, offerName);

        if(!hasExecutionErrors()) {
            var customerTypeName = spec.getCustomerTypeName();
            var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);

            if(!hasExecutionErrors()) {
                offerCustomerType = offerControl.getOfferCustomerType(offer, customerType, editModeToEntityPermission(editMode));

                if(offerCustomerType == null) {
                    addExecutionError(ExecutionErrors.UnknownOfferCustomerType.name(), offerName, customerTypeName);
                }
            }
        }

        return offerCustomerType;
    }

    @Override
    public Offer getLockEntity(OfferCustomerType offerCustomerType) {
        return offerCustomerType.getOffer();
    }

    @Override
    public void fillInResult(EditOfferCustomerTypeResult result, OfferCustomerType offerCustomerType) {
        result.setOfferCustomerType(offerControl.getOfferCustomerTypeTransfer(getUserVisit(), offerCustomerType));
    }

    @Override
    public void doLock(OfferCustomerTypeEdit edit, OfferCustomerType offerCustomerType) {
        edit.setIsDefault(offerCustomerType.getIsDefault().toString());
        edit.setSortOrder(offerCustomerType.getSortOrder().toString());
    }

    @Override
    public void doUpdate(OfferCustomerType offerCustomerType) {
        var offerCustomerTypeValue = offerCustomerType.getOfferCustomerTypeValue().clone();

        offerCustomerTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        offerCustomerTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        offerControl.updateOfferCustomerTypeFromValue(offerCustomerTypeValue, getPartyPK());
    }

}
