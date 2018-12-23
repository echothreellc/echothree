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

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureTypeDescriptionForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
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

public class CreateUnitOfMeasureTypeDescriptionCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SingularDescription", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("PluralDescription", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Symbol", FieldType.STRING, true, 1L, 20L)
                ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureTypeDescriptionCommand */
    public CreateUnitOfMeasureTypeDescriptionCommand(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
            
            if(unitOfMeasureType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    UnitOfMeasureTypeDescription unitOfMeasureTypeDescription = uomControl.getUnitOfMeasureTypeDescription(unitOfMeasureType,
                            language);
                    
                    if(unitOfMeasureTypeDescription == null) {
                        String singularDescription = form.getSingularDescription();
                        String pluralDescription = form.getPluralDescription();
                        String symbol = form.getSymbol();
                        
                        uomControl.createUnitOfMeasureTypeDescription(unitOfMeasureType, language, singularDescription,
                                pluralDescription, symbol, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureTypeDescription.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return null;
    }
    
}
