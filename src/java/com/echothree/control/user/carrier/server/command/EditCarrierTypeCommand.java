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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.edit.CarrierEditFactory;
import com.echothree.control.user.carrier.common.edit.CarrierTypeEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierTypeForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierTypeResult;
import com.echothree.control.user.carrier.common.spec.CarrierTypeSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.CarrierType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditCarrierTypeCommand
        extends BaseAbstractEditCommand<CarrierTypeSpec, CarrierTypeEdit, EditCarrierTypeResult, CarrierType, CarrierType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCarrierTypeCommand */
    public EditCarrierTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCarrierTypeResult getResult() {
        return CarrierResultFactory.getEditCarrierTypeResult();
    }
    
    @Override
    public CarrierTypeEdit getEdit() {
        return CarrierEditFactory.getCarrierTypeEdit();
    }
    
    @Override
    public CarrierType getEntity(EditCarrierTypeResult result) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        CarrierType carrierType;
        var carrierTypeName = spec.getCarrierTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            carrierType = carrierControl.getCarrierTypeByName(carrierTypeName);
        } else { // EditMode.UPDATE
            carrierType = carrierControl.getCarrierTypeByNameForUpdate(carrierTypeName);
        }

        if(carrierType != null) {
            result.setCarrierType(carrierControl.getCarrierTypeTransfer(getUserVisit(), carrierType));
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierTypeName.name(), carrierTypeName);
        }

        return carrierType;
    }
    
    @Override
    public CarrierType getLockEntity(CarrierType carrierType) {
        return carrierType;
    }
    
    @Override
    public void fillInResult(EditCarrierTypeResult result, CarrierType carrierType) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        
        result.setCarrierType(carrierControl.getCarrierTypeTransfer(getUserVisit(), carrierType));
    }
    
    @Override
    public void doLock(CarrierTypeEdit edit, CarrierType carrierType) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierTypeDescription = carrierControl.getCarrierTypeDescription(carrierType, getPreferredLanguage());
        var carrierTypeDetail = carrierType.getLastDetail();

        edit.setCarrierTypeName(carrierTypeDetail.getCarrierTypeName());
        edit.setIsDefault(carrierTypeDetail.getIsDefault().toString());
        edit.setSortOrder(carrierTypeDetail.getSortOrder().toString());

        if(carrierTypeDescription != null) {
            edit.setDescription(carrierTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(CarrierType carrierType) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierTypeName = edit.getCarrierTypeName();
        var duplicateCarrierType = carrierControl.getCarrierTypeByName(carrierTypeName);

        if(duplicateCarrierType != null && !carrierType.equals(duplicateCarrierType)) {
            addExecutionError(ExecutionErrors.DuplicateCarrierTypeName.name(), carrierTypeName);
        }
    }
    
    @Override
    public void doUpdate(CarrierType carrierType) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var partyPK = getPartyPK();
        var carrierTypeDetailValue = carrierControl.getCarrierTypeDetailValueForUpdate(carrierType);
        var carrierTypeDescription = carrierControl.getCarrierTypeDescriptionForUpdate(carrierType, getPreferredLanguage());
        var description = edit.getDescription();

        carrierTypeDetailValue.setCarrierTypeName(edit.getCarrierTypeName());
        carrierTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        carrierTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        carrierControl.updateCarrierTypeFromValue(carrierTypeDetailValue, partyPK);

        if(carrierTypeDescription == null && description != null) {
            carrierControl.createCarrierTypeDescription(carrierType, getPreferredLanguage(), description, partyPK);
        } else if(carrierTypeDescription != null && description == null) {
            carrierControl.deleteCarrierTypeDescription(carrierTypeDescription, partyPK);
        } else if(carrierTypeDescription != null && description != null) {
            var carrierTypeDescriptionValue = carrierControl.getCarrierTypeDescriptionValue(carrierTypeDescription);

            carrierTypeDescriptionValue.setDescription(description);
            carrierControl.updateCarrierTypeDescriptionFromValue(carrierTypeDescriptionValue, partyPK);
        }
    }
    
}
