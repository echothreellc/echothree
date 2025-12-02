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

import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.edit.ShipmentTimeTypeDescriptionEdit;
import com.echothree.control.user.shipment.common.result.EditShipmentTimeTypeDescriptionResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentTimeTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeType;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeTypeDescription;
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
public class EditShipmentTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<ShipmentTimeTypeDescriptionSpec, ShipmentTimeTypeDescriptionEdit, EditShipmentTimeTypeDescriptionResult, ShipmentTimeTypeDescription, ShipmentTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShipmentTimeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditShipmentTimeTypeDescriptionCommand */
    public EditShipmentTimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditShipmentTimeTypeDescriptionResult getResult() {
        return ShipmentResultFactory.getEditShipmentTimeTypeDescriptionResult();
    }

    @Override
    public ShipmentTimeTypeDescriptionEdit getEdit() {
        return ShipmentEditFactory.getShipmentTimeTypeDescriptionEdit();
    }

    @Override
    public ShipmentTimeTypeDescription getEntity(EditShipmentTimeTypeDescriptionResult result) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        ShipmentTimeTypeDescription shipmentTimeTypeDescription = null;
        var shipmentTypeName = spec.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentTimeTypeName = spec.getShipmentTimeTypeName();
            var shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

            if(shipmentTimeType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        shipmentTimeTypeDescription = shipmentControl.getShipmentTimeTypeDescription(shipmentTimeType, language);
                    } else { // EditMode.UPDATE
                        shipmentTimeTypeDescription = shipmentControl.getShipmentTimeTypeDescriptionForUpdate(shipmentTimeType, language);
                    }

                    if(shipmentTimeTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeDescription.name(), shipmentTypeName, shipmentTimeTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), shipmentTypeName, shipmentTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentTimeTypeDescription;
    }

    @Override
    public ShipmentTimeType getLockEntity(ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        return shipmentTimeTypeDescription.getShipmentTimeType();
    }

    @Override
    public void fillInResult(EditShipmentTimeTypeDescriptionResult result, ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);

        result.setShipmentTimeTypeDescription(shipmentControl.getShipmentTimeTypeDescriptionTransfer(getUserVisit(), shipmentTimeTypeDescription));
    }

    @Override
    public void doLock(ShipmentTimeTypeDescriptionEdit edit, ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        edit.setDescription(shipmentTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTimeTypeDescriptionValue = shipmentControl.getShipmentTimeTypeDescriptionValue(shipmentTimeTypeDescription);
        shipmentTimeTypeDescriptionValue.setDescription(edit.getDescription());

        shipmentControl.updateShipmentTimeTypeDescriptionFromValue(shipmentTimeTypeDescriptionValue, getPartyPK());
    }
    
}
