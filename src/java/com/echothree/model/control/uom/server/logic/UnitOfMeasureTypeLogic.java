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

package com.echothree.model.control.uom.server.logic;

import com.echothree.model.control.uom.common.exception.InvalidUnitOfMeasureSpecificationException;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindUseException;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureTypeNameException;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UnitOfMeasureTypeLogic
    extends BaseLogic {

    protected UnitOfMeasureTypeLogic() {
        super();
    }

    public static UnitOfMeasureTypeLogic getInstance() {
        return CDI.current().select(UnitOfMeasureTypeLogic.class).get();
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByName(final ExecutionErrorAccumulator eea, final UnitOfMeasureKind unitOfMeasureKind,
            final String unitOfMeasureTypeName) {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

        if(unitOfMeasureType == null) {
            handleExecutionError(UnknownUnitOfMeasureTypeNameException.class, eea, ExecutionErrors.UnknownUnitOfMeasureTypeName.name(),
                    unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(), unitOfMeasureTypeName);
        }

        return unitOfMeasureType;
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindName,
            final String unitOfMeasureTypeName) {
        var unitOfMeasureKind = UnitOfMeasureKindLogic.getInstance().getUnitOfMeasureKindByName(eea, unitOfMeasureKindName);
        UnitOfMeasureType unitOfMeasureType = null;

        if(!hasExecutionErrors(eea)) {
            unitOfMeasureType = getUnitOfMeasureTypeByName(eea, unitOfMeasureKind, unitOfMeasureTypeName);
        }
        
        return unitOfMeasureType;
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureName,
            String unitOfMeasureKindName, String unitOfMeasureTypeName) {
        UnitOfMeasureType unitOfMeasureType = null;

        if(unitOfMeasureName != null) {
            var splitUomName = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(unitOfMeasureName).toArray(new String[0]);

            if(splitUomName.length == 2) {
                unitOfMeasureKindName = splitUomName[0];
                unitOfMeasureTypeName = splitUomName[1];
            }
        }

        if(unitOfMeasureKindName != null && unitOfMeasureTypeName != null) {
            unitOfMeasureType = getUnitOfMeasureTypeByName(eea, unitOfMeasureKindName, unitOfMeasureTypeName);
        } else {
            handleExecutionError(InvalidUnitOfMeasureSpecificationException.class, eea, ExecutionErrors.InvalidUnitOfMeasureSpecification.name());
        }

        return unitOfMeasureType;
    }

    public Long checkUnitOfMeasure(final ExecutionErrorAccumulator eea, final UnitOfMeasureKind unitOfMeasureKind, final String value,
            final String unitOfMeasureTypeName, final Class<? extends BaseException> missingErrorClass, final String missingErrorKey,
            final Class<? extends BaseException> unitOfMeasureTypeMissingErrorClass, final String unitOfMeasureTypeMissingErrorKey,
            final Class<? extends BaseException> unitOfMeasureTypeUnknownErrorClass, final String unitOfMeasureTypeUnknownErrorKey) {
        Long result = null;
        var parameterCount = (unitOfMeasureTypeName == null ? 0 : 1) + (value == null ? 0 : 1);

        if(parameterCount == 2) {
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

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
        var parameterCount = (unitOfMeasureTypeName == null ? 0 : 1) + (value == null ? 0 : 1);

        if(parameterCount == 2) {
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(unitOfMeasureKindUseTypeName);

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
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(unitOfMeasureKindUseTypeName);
            
            if(unitOfMeasureKind != null) {
                var conversion = new Conversion(uomControl, unitOfMeasureKind, value).convertToHighestUnitOfMeasureType();
                
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
