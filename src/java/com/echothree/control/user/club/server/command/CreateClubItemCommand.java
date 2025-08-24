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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.form.CreateClubItemForm;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.item.common.ItemUseTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateClubItemCommand
        extends BaseSimpleCommand<CreateClubItemForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ClubItemTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SubscriptionTime", FieldType.UNSIGNED_LONG, false, null, null)
        ));
    }
    
    /** Creates a new instance of CreateClubItemCommand */
    public CreateClubItemCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var clubControl = Session.getModelController(ClubControl.class);
        var clubName = form.getClubName();
        var club = clubControl.getClubByName(clubName);
        
        if(club != null) {
            var clubItemTypeName = form.getClubItemTypeName();
            var clubItemType = clubControl.getClubItemTypeByName(clubItemTypeName);
            
            if(clubItemType != null) {
                var itemControl = Session.getModelController(ItemControl.class);
                var itemName = form.getItemName();
                var item = itemControl.getItemByName(itemName);
                
                if(item != null) {
                    var itemUseType = ItemUseTypes.valueOf(item.getLastDetail().getItemUseType().getItemUseTypeName());
                    
                    if(itemUseType == ItemUseTypes.CLUB) {
                        var clubItem = clubControl.getClubItem(club, clubItemType, item);
                        
                        if(clubItem == null) {
                            var uomControl = Session.getModelController(UomControl.class);
                            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                            UnitOfMeasureType unitOfMeasureType = null;
                            
                            if(unitOfMeasureTypeName != null) {
                                var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                                
                                unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                            }
                            
                            if(unitOfMeasureTypeName == null || unitOfMeasureType != null) {
                                var strSubscriptionTime = form.getSubscriptionTime();
                                var subscriptionTime = strSubscriptionTime == null? null: Long.valueOf(strSubscriptionTime);
                                
                                if(unitOfMeasureTypeName == null || subscriptionTime != null) {
                                    Conversion conversion = null;
                                    
                                    if(subscriptionTime != null) {
                                        conversion = new Conversion(uomControl, unitOfMeasureType, subscriptionTime).convertToLowestUnitOfMeasureType();
                                    }
                                    
                                    clubControl.createClubItem(club, clubItemType, item, conversion == null? null: conversion.getQuantity(), getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.MissingSubscriptionTime.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateClubItem.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidItemUseType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownClubItemTypeName.name(), clubItemTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownClubName.name(), clubName);
        }
        
        return null;
    }
    
}
