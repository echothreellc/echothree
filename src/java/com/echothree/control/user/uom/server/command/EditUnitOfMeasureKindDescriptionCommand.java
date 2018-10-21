// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.remote.edit.UnitOfMeasureKindDescriptionEdit;
import com.echothree.control.user.uom.remote.edit.UomEditFactory;
import com.echothree.control.user.uom.remote.form.EditUnitOfMeasureKindDescriptionForm;
import com.echothree.control.user.uom.remote.result.EditUnitOfMeasureKindDescriptionResult;
import com.echothree.control.user.uom.remote.result.UomResultFactory;
import com.echothree.control.user.uom.remote.spec.UnitOfMeasureKindDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDescription;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDescriptionValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditUnitOfMeasureKindDescriptionCommand
        extends BaseEditCommand<UnitOfMeasureKindDescriptionSpec, UnitOfMeasureKindDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditUnitOfMeasureKindDescriptionCommand */
    public EditUnitOfMeasureKindDescriptionCommand(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        EditUnitOfMeasureKindDescriptionResult result = UomResultFactory.getEditUnitOfMeasureKindDescriptionResult();
        String unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    UnitOfMeasureKindDescription unitOfMeasureKindDescription = uomControl.getUnitOfMeasureKindDescription(unitOfMeasureKind, language);
                    
                    if(unitOfMeasureKindDescription != null) {
                        result.setUnitOfMeasureKindDescription(uomControl.getUnitOfMeasureKindDescriptionTransfer(getUserVisit(), unitOfMeasureKindDescription));
                        
                        if(lockEntity(unitOfMeasureKind)) {
                            UnitOfMeasureKindDescriptionEdit edit = UomEditFactory.getUnitOfMeasureKindDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(unitOfMeasureKindDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(unitOfMeasureKind));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    UnitOfMeasureKindDescriptionValue unitOfMeasureKindDescriptionValue = uomControl.getUnitOfMeasureKindDescriptionValueForUpdate(unitOfMeasureKind, language);
                    
                    if(unitOfMeasureKindDescriptionValue != null) {
                        if(lockEntityForUpdate(unitOfMeasureKind)) {
                            try {
                                String description = edit.getDescription();
                                
                                unitOfMeasureKindDescriptionValue.setDescription(description);
                                
                                uomControl.updateUnitOfMeasureKindDescriptionFromValue(unitOfMeasureKindDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(unitOfMeasureKind);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return result;
    }
    
}
