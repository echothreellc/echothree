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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.edit.ShipmentAliasTypeDescriptionEdit;
import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.result.EditShipmentAliasTypeDescriptionResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentAliasTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasType;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditShipmentAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<ShipmentAliasTypeDescriptionSpec, ShipmentAliasTypeDescriptionEdit, EditShipmentAliasTypeDescriptionResult, ShipmentAliasTypeDescription, ShipmentAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShipmentAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditShipmentAliasTypeDescriptionCommand */
    public EditShipmentAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditShipmentAliasTypeDescriptionResult getResult() {
        return ShipmentResultFactory.getEditShipmentAliasTypeDescriptionResult();
    }

    @Override
    public ShipmentAliasTypeDescriptionEdit getEdit() {
        return ShipmentEditFactory.getShipmentAliasTypeDescriptionEdit();
    }

    @Override
    public ShipmentAliasTypeDescription getEntity(EditShipmentAliasTypeDescriptionResult result) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        ShipmentAliasTypeDescription shipmentAliasTypeDescription = null;
        var shipmentTypeName = spec.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentAliasTypeName = spec.getShipmentAliasTypeName();
            var shipmentAliasType = shipmentControl.getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName);

            if(shipmentAliasType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        shipmentAliasTypeDescription = shipmentControl.getShipmentAliasTypeDescription(shipmentAliasType, language);
                    } else { // EditMode.UPDATE
                        shipmentAliasTypeDescription = shipmentControl.getShipmentAliasTypeDescriptionForUpdate(shipmentAliasType, language);
                    }

                    if(shipmentAliasTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownShipmentAliasTypeDescription.name(), shipmentTypeName, shipmentAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentAliasTypeName.name(), shipmentTypeName, shipmentAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentAliasTypeDescription;
    }

    @Override
    public ShipmentAliasType getLockEntity(ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        return shipmentAliasTypeDescription.getShipmentAliasType();
    }

    @Override
    public void fillInResult(EditShipmentAliasTypeDescriptionResult result, ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);

        result.setShipmentAliasTypeDescription(shipmentControl.getShipmentAliasTypeDescriptionTransfer(getUserVisit(), shipmentAliasTypeDescription));
    }

    @Override
    public void doLock(ShipmentAliasTypeDescriptionEdit edit, ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        edit.setDescription(shipmentAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentAliasTypeDescriptionValue = shipmentControl.getShipmentAliasTypeDescriptionValue(shipmentAliasTypeDescription);

        shipmentAliasTypeDescriptionValue.setDescription(edit.getDescription());

        shipmentControl.updateShipmentAliasTypeDescriptionFromValue(shipmentAliasTypeDescriptionValue, getPartyPK());
    }


}
