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

import com.echothree.control.user.item.common.spec.ItemAliasChecksumTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.ItemAliasChecksumTypes;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemAliasChecksumTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemAliasChecksumTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.checksum.BooklandEanChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Ean13ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Gtin12ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Gtin13ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Gtin14ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Gtin8ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Isbn10ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.Isbn13ChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.UpcAChecksumLogic;
import com.echothree.model.control.item.server.logic.checksum.UpcEChecksumLogic;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemAliasChecksumTypeLogic
        extends BaseLogic {

    protected ItemAliasChecksumTypeLogic() {
        super();
    }

    public static ItemAliasChecksumTypeLogic getInstance() {
        return CDI.current().select(ItemAliasChecksumTypeLogic.class).get();
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(final ExecutionErrorAccumulator eea, final String itemAliasChecksumTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemAliasChecksumType = itemControl.getItemAliasChecksumTypeByName(itemAliasChecksumTypeName, entityPermission);

        if(itemAliasChecksumType == null) {
            handleExecutionError(UnknownItemAliasChecksumTypeNameException.class, eea, ExecutionErrors.UnknownItemAliasChecksumTypeName.name(), itemAliasChecksumTypeName);
        }

        return itemAliasChecksumType;
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(final ExecutionErrorAccumulator eea, final String itemAliasChecksumTypeName) {
        return getItemAliasChecksumTypeByName(eea, itemAliasChecksumTypeName, EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemAliasChecksumTypeName) {
        return getItemAliasChecksumTypeByName(eea, itemAliasChecksumTypeName, EntityPermission.READ_WRITE);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemAliasChecksumTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemAliasChecksumType itemAliasChecksumType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var itemAliasChecksumTypeName = universalSpec.getItemAliasChecksumTypeName();
        var parameterCount = (itemAliasChecksumTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemAliasChecksumType = itemControl.getDefaultItemAliasChecksumType(entityPermission);

                    if(itemAliasChecksumType == null) {
                        handleExecutionError(UnknownDefaultItemAliasChecksumTypeException.class, eea, ExecutionErrors.UnknownDefaultItemAliasChecksumType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemAliasChecksumTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemAliasChecksumType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemAliasChecksumType = itemControl.getItemAliasChecksumTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemAliasChecksumType = getItemAliasChecksumTypeByName(eea, itemAliasChecksumTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemAliasChecksumType;
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemAliasChecksumTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemAliasChecksumTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemAliasChecksumTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemAliasChecksumTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }


    public void checkItemAliasChecksum(final ExecutionErrorAccumulator eea, final ItemAliasType itemAliasType, final String alias) {
        var itemAliasChecksumType = itemAliasType.getLastDetail().getItemAliasChecksumType();

        if(itemAliasChecksumType != null) {
            switch(ItemAliasChecksumTypes.valueOf(itemAliasChecksumType.getItemAliasChecksumTypeName())) {
                case ISBN_10 -> Isbn10ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case ISBN_13 -> Isbn13ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case UPC_A -> UpcAChecksumLogic.getInstance().checkChecksum(eea, alias);
                case UPC_E -> UpcEChecksumLogic.getInstance().checkChecksum(eea, alias);
                case EAN_13 -> Ean13ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case BOOKLAND_EAN -> BooklandEanChecksumLogic.getInstance().checkChecksum(eea, alias);
                case GTIN_8 -> Gtin8ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case GTIN_12 -> Gtin12ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case GTIN_13 -> Gtin13ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case GTIN_14 -> Gtin14ChecksumLogic.getInstance().checkChecksum(eea, alias);
                case NONE -> {}
            }
        }
    }

}
