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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.item.common.exception.MissingDefaultItemInventoryTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemInventoryTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemInventoryTypeLogic
        extends BaseLogic {

    protected ItemInventoryTypeLogic() {
        super();
    }

    public static ItemInventoryTypeLogic getInstance() {
        return CDI.current().select(ItemInventoryTypeLogic.class).get();
    }

    public ItemInventoryType getItemInventoryTypeByName(final ExecutionErrorAccumulator eea, final String itemInventoryTypeName, EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemInventoryType = itemControl.getItemInventoryTypeByName(itemInventoryTypeName, entityPermission);

        if(itemInventoryType == null) {
            handleExecutionError(UnknownItemInventoryTypeNameException.class, eea, ExecutionErrors.UnknownItemInventoryTypeName.name(), itemInventoryTypeName);
        }

        return itemInventoryType;
    }

    public ItemInventoryType getItemInventoryTypeByName(final ExecutionErrorAccumulator eea, final String itemInventoryTypeName) {
        return getItemInventoryTypeByName(eea, itemInventoryTypeName, EntityPermission.READ_ONLY);
    }

    public ItemInventoryType getItemInventoryTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemInventoryTypeName) {
        return getItemInventoryTypeByName(eea, itemInventoryTypeName, EntityPermission.READ_WRITE);
    }

    public ItemInventoryType getDefaultItemInventoryType(final ExecutionErrorAccumulator eea) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemInventoryType = itemControl.getDefaultItemInventoryType();

        if(itemInventoryType == null) {
            handleExecutionError(MissingDefaultItemInventoryTypeException.class, eea, ExecutionErrors.MissingDefaultItemInventoryType.name());
        }
        
        return itemInventoryType;
    }

}
