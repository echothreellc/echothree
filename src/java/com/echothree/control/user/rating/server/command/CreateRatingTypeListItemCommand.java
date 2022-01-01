// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.form.CreateRatingTypeListItemForm;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
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

public class CreateRatingTypeListItemCommand
        extends BaseSimpleCommand<CreateRatingTypeListItemForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateRatingTypeListItemCommand */
    public CreateRatingTypeListItemCommand(UserVisitPK userVisitPK, CreateRatingTypeListItemForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String componentVendorName = form.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = form.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var ratingControl = Session.getModelController(RatingControl.class);
                String ratingTypeName = form.getRatingTypeName();
                RatingType ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
                
                if(ratingType != null) {
                    String ratingTypeListItemName = form.getRatingTypeListItemName();
                    RatingTypeListItem ratingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);
                    
                    if(ratingTypeListItem == null) {
                        var partyPK = getPartyPK();
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        
                        ratingTypeListItem = ratingControl.createRatingTypeListItem(ratingType, ratingTypeListItemName, isDefault,
                                sortOrder, partyPK);
                        
                        if(description != null) {
                            ratingControl.createRatingTypeListItemDescription(ratingTypeListItem, getPreferredLanguage(), description,
                                    partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), ratingTypeListItemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), ratingTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return null;
    }
    
}
