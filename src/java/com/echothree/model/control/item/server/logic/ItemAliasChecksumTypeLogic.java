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

package com.echothree.model.control.item.server.logic;

import com.echothree.control.user.item.common.spec.ItemAliasChecksumTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemAliasChecksumTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemAliasChecksumTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class ItemAliasChecksumTypeLogic
        extends BaseLogic {
    
    private ItemAliasChecksumTypeLogic() {
        super();
    }
    
    private static class ItemAliasChecksumTypeLogicHolder {
        static ItemAliasChecksumTypeLogic instance = new ItemAliasChecksumTypeLogic();
    }
    
    public static ItemAliasChecksumTypeLogic getInstance() {
        return ItemAliasChecksumTypeLogicHolder.instance;
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
            case 0:
                if(allowDefault) {
                    itemAliasChecksumType = itemControl.getDefaultItemAliasChecksumType(entityPermission);

                    if(itemAliasChecksumType == null) {
                        handleExecutionError(UnknownDefaultItemAliasChecksumTypeException.class, eea, ExecutionErrors.UnknownDefaultItemAliasChecksumType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(itemAliasChecksumTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHOTHREE.name(), EntityTypes.ItemAliasChecksumType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemAliasChecksumType = itemControl.getItemAliasChecksumTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemAliasChecksumType = getItemAliasChecksumTypeByName(eea, itemAliasChecksumTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
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

    private int getDigit(String alias, int offset) {
        int result = -1;
        char digit = alias.charAt(offset);

        if(digit >= '0' && digit <= '9') {
            result = digit - '0';
        }

        return result;
    }

    private int getIsbn10CheckDigit(String alias, int offset) {
        int result = -1;
        char digit = alias.charAt(offset);

        if(digit >= '0' && digit <= '9') {
            result = digit - '0';
        } else if(digit == 'X') {
            result = 10;
        }

        return result;
    }

    private void checkIsbn10Checksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 10) {
            boolean hasCharacterError = false;
            int runningTotal = 0;
            int checksum = 0;
            
            for(int i = 0; i < 9; i++) {
                int digit = getDigit(alias, i);

                if(digit == -1) {
                    hasCharacterError = true;
                    break;
                } else {
                    runningTotal += digit;
                    checksum += runningTotal;
                }
            }

            if(!hasCharacterError) {
                int checkDigit = getIsbn10CheckDigit(alias, 9);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    runningTotal += checkDigit;
                    checksum += runningTotal;
                    
                    if(checksum % 11 != 0) {
                        eea.addExecutionError(ExecutionErrors.IncorrectIsbn10Checksum.name());
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectIsbn10Character.name());
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectIsbn10Length.name());
        }
    }

    private void checkIsbn13Checksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 13) {
            boolean hasCharacterError = false;
            int checksum = 0;
            for(int i = 0; i < 12; i += 2) {
                int digit = getDigit(alias, i);

                if(digit == -1) {
                    hasCharacterError = true;
                    break;
                }

                checksum += digit;
            }

            if(!hasCharacterError) {
                for(int i = 1; i < 12; i += 2) {
                    int digit = getDigit(alias, i);

                    if(digit == -1) {
                        hasCharacterError = true;
                        break;
                    }

                    checksum += getDigit(alias, i) * 3;
                }
            }

            if(!hasCharacterError) {
                int checkDigit = getIsbn10CheckDigit(alias, 12);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    checksum += checkDigit;

                    if(!(checksum % 10 == 0)) {
                        eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Checksum.name());
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Character.name());
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Length.name());
        }
    }

    private void checkUpcAChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 12) {
            boolean hasCharacterError = false;
            int totalA = 0;
            int totalB = 0;

            for(int i = 0; i < 11; i++) {
                int digit = getDigit(alias, i);

                if(digit == -1) {
                    hasCharacterError = true;
                    break;
                } else {
                    if(i % 2 == 0) {
                        totalA += digit;
                    } else {
                        totalB += digit;
                    }
                }
            }

            if(!hasCharacterError) {
                int checkDigit = getDigit(alias, 11);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    int intermediate = (10 - (totalA * 3 + totalB) % 10) % 10;

                    if(intermediate != checkDigit) {
                        eea.addExecutionError(ExecutionErrors.IncorrectUpcAChecksum.name());
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectUpcACharacter.name());
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectUpcALength.name());
        }
    }

    public void checkItemAliasChecksum(final ExecutionErrorAccumulator eea, final ItemAliasType itemAliasType, final String alias) {
        ItemAliasChecksumType itemAliasChecksumType = itemAliasType.getLastDetail().getItemAliasChecksumType();

        if(itemAliasChecksumType != null) {
            String itemAliasChecksumTypeName = itemAliasChecksumType.getItemAliasChecksumTypeName();

            if(itemAliasChecksumTypeName.equals(ItemConstants.ItemAliasChecksumType_ISBN_10)) {
                checkIsbn10Checksum(eea, alias);
            } else if(itemAliasChecksumTypeName.equals(ItemConstants.ItemAliasChecksumType_ISBN_13)) {
                checkIsbn13Checksum(eea, alias);
            } else if(itemAliasChecksumTypeName.equals(ItemConstants.ItemAliasChecksumType_UPC_A)) {
                checkUpcAChecksum(eea, alias);
            }
        }
    }


}
