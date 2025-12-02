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
import com.echothree.control.user.shipment.common.edit.FreeOnBoardDescriptionEdit;
import com.echothree.control.user.shipment.common.form.EditFreeOnBoardDescriptionForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.FreeOnBoardDescriptionSpec;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
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
public class EditFreeOnBoardDescriptionCommand
        extends BaseEditCommand<FreeOnBoardDescriptionSpec, FreeOnBoardDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FreeOnBoard.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditFreeOnBoardDescriptionCommand */
    public EditFreeOnBoardDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var result = ShipmentResultFactory.getEditFreeOnBoardDescriptionResult();
        var freeOnBoardName = spec.getFreeOnBoardName();
        var freeOnBoard = freeOnBoardControl.getFreeOnBoardByName(freeOnBoardName);
        
        if(freeOnBoard != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    var freeOnBoardDescription = freeOnBoardControl.getFreeOnBoardDescription(freeOnBoard, language);
                    
                    if(freeOnBoardDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setFreeOnBoardDescription(freeOnBoardControl.getFreeOnBoardDescriptionTransfer(getUserVisit(), freeOnBoardDescription));

                            if(lockEntity(freeOnBoard)) {
                                var edit = ShipmentEditFactory.getFreeOnBoardDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(freeOnBoardDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(freeOnBoard));
                        } else { // EditMode.ABANDON
                            unlockEntity(freeOnBoard);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFreeOnBoardDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var freeOnBoardDescriptionValue = freeOnBoardControl.getFreeOnBoardDescriptionValueForUpdate(freeOnBoard, language);
                    
                    if(freeOnBoardDescriptionValue != null) {
                        if(lockEntityForUpdate(freeOnBoard)) {
                            try {
                                var description = edit.getDescription();
                                
                                freeOnBoardDescriptionValue.setDescription(description);
                                
                                freeOnBoardControl.updateFreeOnBoardDescriptionFromValue(freeOnBoardDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(freeOnBoard);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFreeOnBoardDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFreeOnBoardName.name(), freeOnBoardName);
        }
        
        return result;
    }
    
}
