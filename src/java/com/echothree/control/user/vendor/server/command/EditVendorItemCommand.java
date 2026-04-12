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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.edit.VendorItemEdit;
import com.echothree.control.user.vendor.common.result.EditVendorItemResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorItemUniversalSpec;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorItemLogic;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.vendor.server.entity.VendorItem;
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
public class EditVendorItemCommand
        extends BaseAbstractEditCommand<VendorItemUniversalSpec, VendorItemEdit, EditVendorItemResult, VendorItem, VendorItem> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    VendorControl vendorControl;

    @Inject
    VendorItemLogic vendorItemLogic;

    /** Creates a new instance of EditVendorItemCommand */
    public EditVendorItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditVendorItemResult getResult() {
        return VendorResultFactory.getEditVendorItemResult();
    }

    @Override
    public VendorItemEdit getEdit() {
        return VendorEditFactory.getVendorItemEdit();
    }

    @Override
    public VendorItem getEntity(EditVendorItemResult result) {
        return vendorItemLogic.getVendorItemByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public VendorItem getLockEntity(VendorItem vendorItem) {
        return vendorItem;
    }

    @Override
    public void fillInResult(EditVendorItemResult result, VendorItem vendorItem) {
        result.setVendorItem(vendorControl.getVendorItemTransfer(getUserVisit(), vendorItem));
    }

    @Override
    public void doLock(VendorItemEdit edit, VendorItem vendorItem) {
        var vendorItemDetail = vendorItem.getLastDetail();
        var cancellationPolicy = vendorItemDetail.getCancellationPolicy();
        var returnPolicy = vendorItemDetail.getReturnPolicy();

        edit.setVendorItemName(vendorItemDetail.getVendorItemName());
        edit.setDescription(vendorItemDetail.getDescription());
        edit.setPriority(vendorItemDetail.getPriority().toString());
        edit.setCancellationPolicyName(cancellationPolicy == null? null: cancellationPolicy.getLastDetail().getCancellationPolicyName());
        edit.setReturnPolicyName(returnPolicy == null? null: returnPolicy.getLastDetail().getReturnPolicyName());
    }

    CancellationPolicy cancellationPolicy;
    ReturnPolicy returnPolicy;

    @Override
    public void canUpdate(VendorItem vendorItem) {
        var vendorItemName = edit.getVendorItemName();
        var duplicateVendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorItem.getLastDetail().getVendorParty(), vendorItemName);

        if(duplicateVendorItem == null || vendorItem.equals(duplicateVendorItem)) {
            var cancellationPolicyName = edit.getCancellationPolicyName();
            var returnPolicyName = edit.getReturnPolicyName();

            if(cancellationPolicyName != null) {
                var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.VENDOR_CANCELLATION.name());

                cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

                if(cancellationPolicy == null) {
                    addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                }
            }

            if(returnPolicyName != null) {
                var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.VENDOR_RETURN.name());

                returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);

                if(returnPolicy == null) {
                    addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateVendorItemName.name(), vendorItemName);
        }
    }

    @Override
    public void doUpdate(VendorItem vendorItem) {
        var vendorItemDetailValue = vendorControl.getVendorItemDetailValueForUpdate(vendorItem);

        vendorItemDetailValue.setVendorItemName(edit.getVendorItemName());
        vendorItemDetailValue.setDescription(edit.getDescription());
        vendorItemDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
        vendorItemDetailValue.setCancellationPolicyPK(cancellationPolicy == null? null: cancellationPolicy.getPrimaryKey());
        vendorItemDetailValue.setReturnPolicyPK(returnPolicy == null? null: returnPolicy.getPrimaryKey());

        vendorControl.updateVendorItemFromValue(vendorItemDetailValue, getPartyPK());
    }
    
}
