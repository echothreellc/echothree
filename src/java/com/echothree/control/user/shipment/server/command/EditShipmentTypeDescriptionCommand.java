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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.edit.ShipmentTypeDescriptionEdit;
import com.echothree.control.user.shipment.common.result.EditShipmentTypeDescriptionResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeDescription;
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
public class EditShipmentTypeDescriptionCommand
        extends BaseAbstractEditCommand<ShipmentTypeDescriptionSpec, ShipmentTypeDescriptionEdit, EditShipmentTypeDescriptionResult, ShipmentTypeDescription, ShipmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShipmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditShipmentTypeDescriptionCommand */
    public EditShipmentTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditShipmentTypeDescriptionResult getResult() {
        return ShipmentResultFactory.getEditShipmentTypeDescriptionResult();
    }

    @Override
    public ShipmentTypeDescriptionEdit getEdit() {
        return ShipmentEditFactory.getShipmentTypeDescriptionEdit();
    }

    @Override
    public ShipmentTypeDescription getEntity(EditShipmentTypeDescriptionResult result) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        ShipmentTypeDescription shipmentTypeDescription = null;
        var shipmentTypeName = spec.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    shipmentTypeDescription = shipmentControl.getShipmentTypeDescription(shipmentType, language);
                } else { // EditMode.UPDATE
                    shipmentTypeDescription = shipmentControl.getShipmentTypeDescriptionForUpdate(shipmentType, language);
                }

                if(shipmentTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownShipmentTypeDescription.name(), shipmentTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentTypeDescription;
    }

    @Override
    public ShipmentType getLockEntity(ShipmentTypeDescription shipmentTypeDescription) {
        return shipmentTypeDescription.getShipmentType();
    }

    @Override
    public void fillInResult(EditShipmentTypeDescriptionResult result, ShipmentTypeDescription shipmentTypeDescription) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);

        result.setShipmentTypeDescription(shipmentControl.getShipmentTypeDescriptionTransfer(getUserVisit(), shipmentTypeDescription));
    }

    @Override
    public void doLock(ShipmentTypeDescriptionEdit edit, ShipmentTypeDescription shipmentTypeDescription) {
        edit.setDescription(shipmentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ShipmentTypeDescription shipmentTypeDescription) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTypeDescriptionValue = shipmentControl.getShipmentTypeDescriptionValue(shipmentTypeDescription);
        shipmentTypeDescriptionValue.setDescription(edit.getDescription());

        shipmentControl.updateShipmentTypeDescriptionFromValue(shipmentTypeDescriptionValue, getPartyPK());
    }
    
}
