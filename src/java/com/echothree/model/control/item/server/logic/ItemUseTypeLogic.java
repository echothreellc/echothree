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

import com.echothree.model.control.item.common.exception.MissingDefaultItemUseTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemUseTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemUseTypeLogic
        extends BaseLogic {

    protected ItemUseTypeLogic() {
        super();
    }

    public static ItemUseTypeLogic getInstance() {
        return CDI.current().select(ItemUseTypeLogic.class).get();
    }

    public ItemUseType getItemUseTypeByName(final ExecutionErrorAccumulator eea, final String itemUseTypeName, EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemUseType = itemControl.getItemUseTypeByName(itemUseTypeName, entityPermission);

        if(itemUseType == null) {
            handleExecutionError(UnknownItemUseTypeNameException.class, eea, ExecutionErrors.UnknownItemUseTypeName.name(), itemUseTypeName);
        }

        return itemUseType;
    }

    public ItemUseType getItemUseTypeByName(final ExecutionErrorAccumulator eea, final String itemUseTypeName) {
        return getItemUseTypeByName(eea, itemUseTypeName, EntityPermission.READ_ONLY);
    }

    public ItemUseType getItemUseTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemUseTypeName) {
        return getItemUseTypeByName(eea, itemUseTypeName, EntityPermission.READ_WRITE);
    }

    public ItemUseType getDefaultItemUseType(final ExecutionErrorAccumulator eea) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemUseType = itemControl.getDefaultItemUseType();

        if(itemUseType == null) {
            handleExecutionError(MissingDefaultItemUseTypeException.class, eea, ExecutionErrors.MissingDefaultItemUseType.name());
        }
        
        return itemUseType;
    }

}
