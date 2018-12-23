// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.uom.server.logic;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindUseException;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureTypeNameException;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class UnitOfMeasureTypeLogic
    extends BaseLogic {
    
    private UnitOfMeasureTypeLogic() {
        super();
    }
    
    private static class UnitOfMeasureTypeLogicHolder {
        static UnitOfMeasureTypeLogic instance = new UnitOfMeasureTypeLogic();
    }
    
    public static UnitOfMeasureTypeLogic getInstance() {
        return UnitOfMeasureTypeLogicHolder.instance;
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByName(final ExecutionErrorAccumulator eea, final UnitOfMeasureKind unitOfMeasureKind, final String unitOfMeasureTypeName) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

        if(unitOfMeasureType == null) {
            handleExecutionError(UnknownUnitOfMeasureTypeNameException.class, eea, ExecutionErrors.UnknownUnitOfMeasureTypeName.name(),
                    unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(), unitOfMeasureTypeName);
        }

        return unitOfMeasureType;
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindName, final String unitOfMeasureTypeName) {
        UnitOfMeasureKind unitOfMeasureKind = UnitOfMeasureKindLogic.getInstance().getUnitOfMeasureKindByName(eea, unitOfMeasureKindName);
        UnitOfMeasureType unitOfMeasureType = null;

        if(!hasExecutionErrors(eea)) {
            unitOfMeasureType = getUnitOfMeasureTypeByName(eea, unitOfMeasureKind, unitOfMeasureTypeName);
        }
        
        return unitOfMeasureType;
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        UnitOfMeasureType unitOfMeasureType = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.UnitOfMeasureType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            
            unitOfMeasureType = uomControl.getUnitOfMeasureTypeByEntityInstance(entityInstance, entityPermission);
        }

        return unitOfMeasureType;
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureTypeByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureTypeByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public Long checkUnitOfMeasure(final ExecutionErrorAccumulator eea, final UnitOfMeasureKind unitOfMeasureKind, final String value,
            final String unitOfMeasureTypeName, final Class<? extends BaseException> missingErrorClass, final String missingErrorKey,
            final Class<? extends BaseException> unitOfMeasureTypeMissingErrorClass, final String unitOfMeasureTypeMissingErrorKey,
            final Class<? extends BaseException> unitOfMeasureTypeUnknownErrorClass, final String unitOfMeasureTypeUnknownErrorKey) {
        Long result = null;
        int parameterCount = (unitOfMeasureTypeName == null ? 0 : 1) + (value == null ? 0 : 1);

        if(parameterCount == 2) {
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                result = new Conversion(uomControl, unitOfMeasureType, Long.valueOf(value)).convertToLowestUnitOfMeasureType().getQuantity();
            } else {
                handleExecutionError(unitOfMeasureTypeUnknownErrorClass, eea, unitOfMeasureTypeUnknownErrorKey,
                        unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(), unitOfMeasureTypeName);
            }
        } else {
            if(parameterCount == 1) {
                if(unitOfMeasureTypeName == null) {
                    handleExecutionError(unitOfMeasureTypeMissingErrorClass, eea, unitOfMeasureTypeMissingErrorKey);
                }

                if(value == null) {
                    handleExecutionError(missingErrorClass, eea, missingErrorKey);
                }
            }
        }

        return result;
    }

    public Long checkUnitOfMeasure(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindUseTypeName, final String value,
            final String unitOfMeasureTypeName, final Class<? extends BaseException> missingErrorClass, final String missingErrorKey,
            final Class<? extends BaseException> unitOfMeasureTypeMissingErrorClass, final String unitOfMeasureTypeMissingErrorKey,
            final Class<? extends BaseException> unitOfMeasureTypeUnknownErrorClass, final String unitOfMeasureTypeUnknownErrorKey) {
        Long result = null;
        int parameterCount = (unitOfMeasureTypeName == null ? 0 : 1) + (value == null ? 0 : 1);

        if(parameterCount == 2) {
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(unitOfMeasureKindUseTypeName);

            if(unitOfMeasureKind != null) {
                result = checkUnitOfMeasure(eea, unitOfMeasureKind, value, unitOfMeasureTypeName, missingErrorClass, missingErrorKey,
                        unitOfMeasureTypeMissingErrorClass, unitOfMeasureTypeMissingErrorKey, unitOfMeasureTypeUnknownErrorClass,
                        unitOfMeasureTypeUnknownErrorKey);
            } else {
                handleExecutionError(UnknownUnitOfMeasureKindUseException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindUse.name(), unitOfMeasureKindUseTypeName);
            }
        } else {
            if(parameterCount == 1) {
                if(unitOfMeasureTypeName == null) {
                    handleExecutionError(unitOfMeasureTypeMissingErrorClass, eea, unitOfMeasureTypeMissingErrorKey);
                }

                if(value == null) {
                    handleExecutionError(missingErrorClass, eea, missingErrorKey);
                }
            }
        }

        return result;
    }

    public static class StringUnitOfMeasure {
        
        private String value;
        private String unitOfMeasureKindName;
        private String unitOfMeasureTypeName;
        
        public StringUnitOfMeasure(String value, String unitOfMeasureKindName, String unitOfMeasureTypeName) {
            this.value = value;
            this.unitOfMeasureKindName = unitOfMeasureKindName;
            this.unitOfMeasureTypeName = unitOfMeasureTypeName;
        }

        public StringUnitOfMeasure() {
            this(null, null, null);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnitOfMeasureKindName() {
            return unitOfMeasureKindName;
        }

        public void setUnitOfMeasureKindName(String unitOfMeasureKindName) {
            this.unitOfMeasureKindName = unitOfMeasureKindName;
        }

        public String getUnitOfMeasureTypeName() {
            return unitOfMeasureTypeName;
        }

        public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
            this.unitOfMeasureTypeName = unitOfMeasureTypeName;
        }
        
    }
    
    public StringUnitOfMeasure unitOfMeasureToString(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindUseTypeName, final Long value) {
        StringUnitOfMeasure result = null;
        
        if(value != null) {
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(unitOfMeasureKindUseTypeName);
            
            if(unitOfMeasureKind != null) {
                Conversion conversion = new Conversion(uomControl, unitOfMeasureKind, value).convertToHighestUnitOfMeasureType();
                
                result = new StringUnitOfMeasure(conversion.getQuantity().toString(), unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(),
                        conversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
            } else {
                handleExecutionError(UnknownUnitOfMeasureKindUseException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindUse.name(), unitOfMeasureKindUseTypeName);
            }
        } else {
            result = new StringUnitOfMeasure();
        }
        
        return result;
    }
    
}
