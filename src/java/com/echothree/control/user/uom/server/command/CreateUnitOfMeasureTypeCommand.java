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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureTypeForm;
import com.echothree.model.control.accounting.server.logic.SymbolPositionLogic;
import com.echothree.model.control.uom.server.control.UomControl;
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

public class CreateUnitOfMeasureTypeCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SuppressSymbolSeparator", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("SingularDescription", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("PluralDescription", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("Symbol", FieldType.STRING, false, 1L, 20L)
                ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureTypeCommand */
    public CreateUnitOfMeasureTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
            
            if(unitOfMeasureType == null) {
                var symbolPosition = SymbolPositionLogic.getInstance().getSymbolPositionByName(this, form.getSymbolPositionName());
                
                if(!hasExecutionErrors()) {
                    var singularDescription = form.getSingularDescription();
                    var pluralDescription = form.getPluralDescription();
                    var symbol = form.getSymbol();
                    var descriptionCount = (singularDescription == null ? 0 : 1) + (pluralDescription == null ? 0 : 1)
                            + (symbol == null ? 0 : 1);

                    if(descriptionCount == 0 || descriptionCount == 3) {
                        var partyPK = getPartyPK();
                        var suppressSymbolSeparator = Boolean.valueOf(form.getSuppressSymbolSeparator());
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());

                        unitOfMeasureType = uomControl.createUnitOfMeasureType(unitOfMeasureKind, unitOfMeasureTypeName,
                                symbolPosition, suppressSymbolSeparator, isDefault, sortOrder, partyPK);

                        if(descriptionCount == 2) {
                            var language = getPreferredLanguage();

                            uomControl.createUnitOfMeasureTypeDescription(unitOfMeasureType, language, singularDescription,
                                    pluralDescription, symbol, partyPK);
                        }
                    } else {
                        if(singularDescription == null) {
                            addExecutionError(ExecutionErrors.MissingSingularDescription.name());
                        }

                        if(pluralDescription == null) {
                            addExecutionError(ExecutionErrors.MissingPluralDescription.name());
                        }

                        if(pluralDescription == null) {
                            addExecutionError(ExecutionErrors.MissingSymbol.name());
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return null;
    }
    
}
