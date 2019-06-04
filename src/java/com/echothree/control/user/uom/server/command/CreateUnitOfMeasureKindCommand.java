// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureKindForm;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateUnitOfMeasureKindCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureKindForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FractionDigits", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureKindCommand */
    public CreateUnitOfMeasureKindCommand(UserVisitPK userVisitPK, CreateUnitOfMeasureKindForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind == null) {
            PartyPK partyPK = getPartyPK();
            Integer fractionDigits = Integer.valueOf(form.getFractionDigits());
            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
            Integer sortOrder = Integer.valueOf(form.getSortOrder());
            String description = form.getDescription();
            
            unitOfMeasureKind = uomControl.createUnitOfMeasureKind(unitOfMeasureKindName, fractionDigits, isDefault, sortOrder, partyPK);
            
            if(description != null) {
                Language language = getPreferredLanguage();
                
                uomControl.createUnitOfMeasureKindDescription(unitOfMeasureKind, language, description, partyPK);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return null;
    }
    
}
