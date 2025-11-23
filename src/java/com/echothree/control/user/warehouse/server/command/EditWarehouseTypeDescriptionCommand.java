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
import com.echothree.control.user.warehouse.common.edit.WarehouseTypeDescriptionEdit;
import com.echothree.control.user.warehouse.common.form.EditWarehouseTypeDescriptionForm;
import com.echothree.control.user.warehouse.common.result.EditWarehouseTypeDescriptionResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.WarehouseTypeDescriptionSpec;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.model.data.warehouse.server.entity.WarehouseTypeDescription;
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
public class EditWarehouseTypeDescriptionCommand
        extends BaseAbstractEditCommand<WarehouseTypeDescriptionSpec, WarehouseTypeDescriptionEdit, EditWarehouseTypeDescriptionResult, WarehouseTypeDescription, WarehouseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WarehouseType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditWarehouseTypeDescriptionCommand */
    public EditWarehouseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWarehouseTypeDescriptionResult getResult() {
        return WarehouseResultFactory.getEditWarehouseTypeDescriptionResult();
    }

    @Override
    public WarehouseTypeDescriptionEdit getEdit() {
        return WarehouseEditFactory.getWarehouseTypeDescriptionEdit();
    }

    @Override
    public WarehouseTypeDescription getEntity(EditWarehouseTypeDescriptionResult result) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        WarehouseTypeDescription warehouseTypeDescription = null;
        var warehouseTypeName = spec.getWarehouseTypeName();
        var warehouseType = warehouseControl.getWarehouseTypeByName(warehouseTypeName);

        if(warehouseType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    warehouseTypeDescription = warehouseControl.getWarehouseTypeDescription(warehouseType, language);
                } else { // EditMode.UPDATE
                    warehouseTypeDescription = warehouseControl.getWarehouseTypeDescriptionForUpdate(warehouseType, language);
                }

                if(warehouseTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownWarehouseTypeDescription.name(), warehouseTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseTypeName.name(), warehouseTypeName);
        }

        return warehouseTypeDescription;
    }

    @Override
    public WarehouseType getLockEntity(WarehouseTypeDescription warehouseTypeDescription) {
        return warehouseTypeDescription.getWarehouseType();
    }

    @Override
    public void fillInResult(EditWarehouseTypeDescriptionResult result, WarehouseTypeDescription warehouseTypeDescription) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);

        result.setWarehouseTypeDescription(warehouseControl.getWarehouseTypeDescriptionTransfer(getUserVisit(), warehouseTypeDescription));
    }

    @Override
    public void doLock(WarehouseTypeDescriptionEdit edit, WarehouseTypeDescription warehouseTypeDescription) {
        edit.setDescription(warehouseTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(WarehouseTypeDescription warehouseTypeDescription) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseTypeDescriptionValue = warehouseControl.getWarehouseTypeDescriptionValue(warehouseTypeDescription);

        warehouseTypeDescriptionValue.setDescription(edit.getDescription());

        warehouseControl.updateWarehouseTypeDescriptionFromValue(warehouseTypeDescriptionValue, getPartyPK());
    }

}
