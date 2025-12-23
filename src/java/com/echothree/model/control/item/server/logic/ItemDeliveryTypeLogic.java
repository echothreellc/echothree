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

import com.echothree.model.control.item.common.exception.MissingDefaultItemDeliveryTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemDeliveryTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemDeliveryTypeLogic
        extends BaseLogic {

    protected ItemDeliveryTypeLogic() {
        super();
    }

    public static ItemDeliveryTypeLogic getInstance() {
        return CDI.current().select(ItemDeliveryTypeLogic.class).get();
    }

    public ItemDeliveryType getItemDeliveryTypeByName(final ExecutionErrorAccumulator eea, final String itemDeliveryTypeName, EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDeliveryType = itemControl.getItemDeliveryTypeByName(itemDeliveryTypeName, entityPermission);

        if(itemDeliveryType == null) {
            handleExecutionError(UnknownItemDeliveryTypeNameException.class, eea, ExecutionErrors.UnknownItemDeliveryTypeName.name(), itemDeliveryTypeName);
        }

        return itemDeliveryType;
    }

    public ItemDeliveryType getItemDeliveryTypeByName(final ExecutionErrorAccumulator eea, final String itemDeliveryTypeName) {
        return getItemDeliveryTypeByName(eea, itemDeliveryTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDeliveryType getItemDeliveryTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemDeliveryTypeName) {
        return getItemDeliveryTypeByName(eea, itemDeliveryTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDeliveryType getDefaultItemDeliveryType(final ExecutionErrorAccumulator eea) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDeliveryType = itemControl.getDefaultItemDeliveryType();

        if(itemDeliveryType == null) {
            handleExecutionError(MissingDefaultItemDeliveryTypeException.class, eea, ExecutionErrors.MissingDefaultItemDeliveryType.name());
        }
        
        return itemDeliveryType;
    }

}
