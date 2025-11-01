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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.item.common.exception.MissingDefaultItemTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemTypeLogic
        extends BaseLogic {

    protected ItemTypeLogic() {
        super();
    }

    public static ItemTypeLogic getInstance() {
        return CDI.current().select(ItemTypeLogic.class).get();
    }

    public ItemType getItemTypeByName(final ExecutionErrorAccumulator eea, final String itemTypeName, EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemType = itemControl.getItemTypeByName(itemTypeName, entityPermission);

        if(itemType == null) {
            handleExecutionError(UnknownItemTypeNameException.class, eea, ExecutionErrors.UnknownItemTypeName.name(), itemTypeName);
        }

        return itemType;
    }

    public ItemType getItemTypeByName(final ExecutionErrorAccumulator eea, final String itemTypeName) {
        return getItemTypeByName(eea, itemTypeName, EntityPermission.READ_ONLY);
    }

    public ItemType getItemTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemTypeName) {
        return getItemTypeByName(eea, itemTypeName, EntityPermission.READ_WRITE);
    }

    public ItemType getDefaultItemType(final ExecutionErrorAccumulator eea) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemType = itemControl.getDefaultItemType();

        if(itemType == null) {
            handleExecutionError(MissingDefaultItemTypeException.class, eea, ExecutionErrors.MissingDefaultItemType.name());
        }
        
        return itemType;
    }

}
