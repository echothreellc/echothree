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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.edit.VendorItemEdit;
import com.echothree.control.user.vendor.common.form.EditVendorItemForm;
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditVendorItemCommand
        extends BaseEditCommand<VendorItemUniversalSpec, VendorItemEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditVendorItemCommand */
    public EditVendorItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        var result = VendorResultFactory.getEditVendorItemResult();

        if(editMode.equals(EditMode.LOCK)) {
            var vendorItem = VendorItemLogic.getInstance().getVendorItemByUniversalSpec(this, spec);

            if(!hasExecutionErrors()) {
                result.setVendorItem(vendorControl.getVendorItemTransfer(getUserVisit(), vendorItem));

                if(lockEntity(vendorItem)) {
                    var edit = VendorEditFactory.getVendorItemEdit();
                    var vendorItemDetail = vendorItem.getLastDetail();
                    var cancellationPolicy = vendorItemDetail.getCancellationPolicy();
                    var returnPolicy = vendorItemDetail.getReturnPolicy();

                    result.setEdit(edit);
                    edit.setVendorItemName(vendorItemDetail.getVendorItemName());
                    edit.setDescription(vendorItemDetail.getDescription());
                    edit.setPriority(vendorItemDetail.getPriority().toString());
                    edit.setCancellationPolicyName(cancellationPolicy == null? null: cancellationPolicy.getLastDetail().getCancellationPolicyName());
                    edit.setReturnPolicyName(returnPolicy == null? null: returnPolicy.getLastDetail().getReturnPolicyName());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }

                result.setEntityLock(getEntityLockTransfer(vendorItem));
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var vendorItem = VendorItemLogic.getInstance().getVendorItemByUniversalSpecForUpdate(this, spec);

            if(!hasExecutionErrors()) {
                var vendorItemName = edit.getVendorItemName();
                var duplicateVendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorItem.getLastDetail().getVendorParty(), vendorItemName);

                if(duplicateVendorItem == null || vendorItem.equals(duplicateVendorItem)) {
                    var cancellationPolicyName = edit.getCancellationPolicyName();
                    CancellationPolicy cancellationPolicy = null;

                    if(cancellationPolicyName != null) {
                        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                        var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.VENDOR_CANCELLATION.name());

                        cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                    }

                    if(cancellationPolicyName == null || cancellationPolicy != null) {
                        var returnPolicyName = edit.getReturnPolicyName();
                        ReturnPolicy returnPolicy = null;

                        if(returnPolicyName != null) {
                            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                            var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.VENDOR_RETURN.name());

                            returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                        }

                        if(returnPolicyName == null || returnPolicy != null) {
                            if(lockEntityForUpdate(vendorItem)) {
                                try {
                                    var vendorItemDetailValue = vendorControl.getVendorItemDetailValueForUpdate(vendorItem);

                                    vendorItemDetailValue.setVendorItemName(edit.getVendorItemName());
                                    vendorItemDetailValue.setDescription(edit.getDescription());
                                    vendorItemDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
                                    vendorItemDetailValue.setCancellationPolicyPK(cancellationPolicy == null? null: cancellationPolicy.getPrimaryKey());
                                    vendorItemDetailValue.setReturnPolicyPK(returnPolicy == null? null: returnPolicy.getPrimaryKey());

                                    vendorControl.updateVendorItemFromValue(vendorItemDetailValue, getPartyPK());
                                } finally {
                                    unlockEntity(vendorItem);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateVendorItemName.name(), vendorItemName);
                }
            }
        }

        return result;
    }
    
}
