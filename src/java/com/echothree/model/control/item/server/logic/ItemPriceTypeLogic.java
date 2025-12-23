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

import com.echothree.model.control.item.common.exception.MissingDefaultItemPriceTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemPriceTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemPriceTypeLogic
        extends BaseLogic {

    protected ItemPriceTypeLogic() {
        super();
    }

    public static ItemPriceTypeLogic getInstance() {
        return CDI.current().select(ItemPriceTypeLogic.class).get();
    }

    public ItemPriceType getItemPriceTypeByName(final ExecutionErrorAccumulator eea, final String itemPriceTypeName, EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemPriceType = itemControl.getItemPriceTypeByName(itemPriceTypeName, entityPermission);

        if(itemPriceType == null) {
            handleExecutionError(UnknownItemPriceTypeNameException.class, eea, ExecutionErrors.UnknownItemPriceTypeName.name(), itemPriceTypeName);
        }

        return itemPriceType;
    }

    public ItemPriceType getItemPriceTypeByName(final ExecutionErrorAccumulator eea, final String itemPriceTypeName) {
        return getItemPriceTypeByName(eea, itemPriceTypeName, EntityPermission.READ_ONLY);
    }

    public ItemPriceType getItemPriceTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemPriceTypeName) {
        return getItemPriceTypeByName(eea, itemPriceTypeName, EntityPermission.READ_WRITE);
    }

    public ItemPriceType getDefaultItemPriceType(final ExecutionErrorAccumulator eea) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemPriceType = itemControl.getDefaultItemPriceType();

        if(itemPriceType == null) {
            handleExecutionError(MissingDefaultItemPriceTypeException.class, eea, ExecutionErrors.MissingDefaultItemPriceType.name());
        }
        
        return itemPriceType;
    }

}
