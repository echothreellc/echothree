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
import com.echothree.control.user.shipment.common.edit.FreeOnBoardEdit;
import com.echothree.control.user.shipment.common.form.EditFreeOnBoardForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.result.EditFreeOnBoardResult;
import com.echothree.control.user.shipment.common.spec.FreeOnBoardUniversalSpec;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditFreeOnBoardCommand
        extends BaseAbstractEditCommand<FreeOnBoardUniversalSpec, FreeOnBoardEdit, EditFreeOnBoardResult, FreeOnBoard, FreeOnBoard> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FreeOnBoard.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditFreeOnBoardCommand */
    public EditFreeOnBoardCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditFreeOnBoardResult getResult() {
        return ShipmentResultFactory.getEditFreeOnBoardResult();
    }
    
    @Override
    public FreeOnBoardEdit getEdit() {
        return ShipmentEditFactory.getFreeOnBoardEdit();
    }
    
    @Override
    public FreeOnBoard getEntity(EditFreeOnBoardResult result) {
        return FreeOnBoardLogic.getInstance().getFreeOnBoardByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public FreeOnBoard getLockEntity(FreeOnBoard freeOnBoard) {
        return freeOnBoard;
    }
    
    @Override
    public void fillInResult(EditFreeOnBoardResult result, FreeOnBoard freeOnBoard) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        
        result.setFreeOnBoard(freeOnBoardControl.getFreeOnBoardTransfer(getUserVisit(), freeOnBoard));
    }
    
    @Override
    public void doLock(FreeOnBoardEdit edit, FreeOnBoard freeOnBoard) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var freeOnBoardDescription = freeOnBoardControl.getFreeOnBoardDescription(freeOnBoard, getPreferredLanguage());
        var freeOnBoardDetail = freeOnBoard.getLastDetail();
        
        edit.setFreeOnBoardName(freeOnBoardDetail.getFreeOnBoardName());
        edit.setIsDefault(freeOnBoardDetail.getIsDefault().toString());
        edit.setSortOrder(freeOnBoardDetail.getSortOrder().toString());

        if(freeOnBoardDescription != null) {
            edit.setDescription(freeOnBoardDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(FreeOnBoard freeOnBoard) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var freeOnBoardName = edit.getFreeOnBoardName();
        var duplicateFreeOnBoard = freeOnBoardControl.getFreeOnBoardByName(freeOnBoardName);

        if(duplicateFreeOnBoard != null && !freeOnBoard.equals(duplicateFreeOnBoard)) {
            addExecutionError(ExecutionErrors.DuplicateFreeOnBoardName.name(), freeOnBoardName);
        }
    }
    
    @Override
    public void doUpdate(FreeOnBoard freeOnBoard) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var partyPK = getPartyPK();
        var freeOnBoardDetailValue = freeOnBoardControl.getFreeOnBoardDetailValueForUpdate(freeOnBoard);
        var freeOnBoardDescription = freeOnBoardControl.getFreeOnBoardDescriptionForUpdate(freeOnBoard, getPreferredLanguage());
        var description = edit.getDescription();

        freeOnBoardDetailValue.setFreeOnBoardName(edit.getFreeOnBoardName());
        freeOnBoardDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        freeOnBoardDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        freeOnBoardControl.updateFreeOnBoardFromValue(freeOnBoardDetailValue, partyPK);

        if(freeOnBoardDescription == null && description != null) {
            freeOnBoardControl.createFreeOnBoardDescription(freeOnBoard, getPreferredLanguage(), description, partyPK);
        } else if(freeOnBoardDescription != null && description == null) {
            freeOnBoardControl.deleteFreeOnBoardDescription(freeOnBoardDescription, partyPK);
        } else if(freeOnBoardDescription != null && description != null) {
            var freeOnBoardDescriptionValue = freeOnBoardControl.getFreeOnBoardDescriptionValue(freeOnBoardDescription);

            freeOnBoardDescriptionValue.setDescription(description);
            freeOnBoardControl.updateFreeOnBoardDescriptionFromValue(freeOnBoardDescriptionValue, partyPK);
        }
    }
    
}
