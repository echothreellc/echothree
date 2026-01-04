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

package com.echothree.util.common.validation;

import java.io.Serializable;

public class FieldDefinition
        implements Serializable {
    
    private String fieldName = null;
    private FieldType fieldType = null;
    private Boolean isRequired = null;
    private Long minimumValue = null;
    private Long maximumValue = null;
    
    /** Creates a new instance of FieldDefinition
     */
    /** Creates a new instance of FieldDefinition */
    public FieldDefinition(String fieldName, FieldType fieldType, Boolean isRequired, Long minimumValue, Long maximumValue) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.isRequired = isRequired;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }
    
    /** Creates a new instance of FieldDefinition
     */
    /** Creates a new instance of FieldDefinition */
    public FieldDefinition(FieldDefinition fieldDefinition) {
        fieldName = fieldDefinition.getFieldName();
        fieldType = fieldDefinition.getFieldType();
        isRequired = fieldDefinition.getIsRequired();
        minimumValue = fieldDefinition.getMinimumValue();
        maximumValue = fieldDefinition.getMaximumValue();
    }
    
    /** Creates a new instance of FieldDefinition */
    public FieldDefinition() {
    }
    
    /** fieldName is a colon separated list of values. The first value should be the name
     * of the field in the submitted form. Following that, there may be zero or more values
     * that should be in the format "name,field" - where name is the name of the parameter, and
     * field is the form field that the parameter's value may be found in. For example:
     * "unitPrice:currencyIsoName,currencyIsoName"
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
    
    public FieldType getFieldType() {
        return fieldType;
    }
    
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
    
    public Boolean getIsRequired() {
        return isRequired;
    }
    
    /** minimumValue has two meanings. When dealing with Integers or Longs, it sets
     * a lower limit for the acceptable value of the field, if its not equal to null.
     * However, if the field is a String, it will set a minimum length required for
     * the String, if its not equal to null.
     * @param minimumValue The new minimumValue, may be null
     */
    public void setMinimumValue(Long minimumValue) {
        this.minimumValue = minimumValue;
    }
    
    /** Get the current minimumValue
     * @return The current minimumValue, may be null
     */    
    public Long getMinimumValue() {
        return minimumValue;
    }
    
    /** maximumValue has two meanings. When dealing with Integers or Longs, it sets
     * an upper limit for the acceptable value of the field, if its not equal to null.
     * However, if the field is a String, it will set a maximum length for the String,
     * if its not equal to null.
     * @param maximumValue The new maximumValue, may be null
     */
    public void setMaximumValue(Long maximumValue) {
        this.maximumValue = maximumValue;
    }
    
    /** Get the current maximumValue
     * @return The current maximumValue, may be null
     */    
    public Long getMaximumValue() {
        return maximumValue;
    }
    
}
