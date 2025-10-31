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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.edit.WarehouseTypeEdit;
import com.echothree.control.user.warehouse.common.form.EditWarehouseTypeForm;
import com.echothree.control.user.warehouse.common.result.EditWarehouseTypeResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.WarehouseTypeSpec;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditWarehouseTypeCommand
        extends BaseAbstractEditCommand<WarehouseTypeSpec, WarehouseTypeEdit, EditWarehouseTypeResult, WarehouseType, WarehouseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WarehouseType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditWarehouseTypeCommand */
    public EditWarehouseTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWarehouseTypeResult getResult() {
        return WarehouseResultFactory.getEditWarehouseTypeResult();
    }

    @Override
    public WarehouseTypeEdit getEdit() {
        return WarehouseEditFactory.getWarehouseTypeEdit();
    }

    @Override
    public WarehouseType getEntity(EditWarehouseTypeResult result) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        WarehouseType warehouseType;
        var warehouseTypeName = spec.getWarehouseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            warehouseType = warehouseControl.getWarehouseTypeByName(warehouseTypeName);
        } else { // EditMode.UPDATE
            warehouseType = warehouseControl.getWarehouseTypeByNameForUpdate(warehouseTypeName);
        }

        if(warehouseType == null) {
            addExecutionError(ExecutionErrors.UnknownWarehouseTypeName.name(), warehouseTypeName);
        }

        return warehouseType;
    }

    @Override
    public WarehouseType getLockEntity(WarehouseType warehouseType) {
        return warehouseType;
    }

    @Override
    public void fillInResult(EditWarehouseTypeResult result, WarehouseType warehouseType) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);

        result.setWarehouseType(warehouseControl.getWarehouseTypeTransfer(getUserVisit(), warehouseType));
    }

    @Override
    public void doLock(WarehouseTypeEdit edit, WarehouseType warehouseType) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseTypeDescription = warehouseControl.getWarehouseTypeDescription(warehouseType, getPreferredLanguage());
        var warehouseTypeDetail = warehouseType.getLastDetail();

        edit.setWarehouseTypeName(warehouseTypeDetail.getWarehouseTypeName());
        edit.setPriority(warehouseTypeDetail.getPriority().toString());
        edit.setIsDefault(warehouseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(warehouseTypeDetail.getSortOrder().toString());

        if(warehouseTypeDescription != null) {
            edit.setDescription(warehouseTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(WarehouseType warehouseType) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseTypeName = edit.getWarehouseTypeName();
        var duplicateWarehouseType = warehouseControl.getWarehouseTypeByName(warehouseTypeName);

        if(duplicateWarehouseType != null && !warehouseType.equals(duplicateWarehouseType)) {
            addExecutionError(ExecutionErrors.DuplicateWarehouseTypeName.name(), warehouseTypeName);
        }
    }

    @Override
    public void doUpdate(WarehouseType warehouseType) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var partyPK = getPartyPK();
        var warehouseTypeDetailValue = warehouseControl.getWarehouseTypeDetailValueForUpdate(warehouseType);
        var warehouseTypeDescription = warehouseControl.getWarehouseTypeDescriptionForUpdate(warehouseType, getPreferredLanguage());
        var description = edit.getDescription();

        warehouseTypeDetailValue.setWarehouseTypeName(edit.getWarehouseTypeName());
        warehouseTypeDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
        warehouseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        warehouseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        warehouseControl.updateWarehouseTypeFromValue(warehouseTypeDetailValue, partyPK);

        if(warehouseTypeDescription == null && description != null) {
            warehouseControl.createWarehouseTypeDescription(warehouseType, getPreferredLanguage(), description, partyPK);
        } else if(warehouseTypeDescription != null && description == null) {
            warehouseControl.deleteWarehouseTypeDescription(warehouseTypeDescription, partyPK);
        } else if(warehouseTypeDescription != null && description != null) {
            var warehouseTypeDescriptionValue = warehouseControl.getWarehouseTypeDescriptionValue(warehouseTypeDescription);

            warehouseTypeDescriptionValue.setDescription(description);
            warehouseControl.updateWarehouseTypeDescriptionFromValue(warehouseTypeDescriptionValue, partyPK);
        }
    }

}
