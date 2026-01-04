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

package com.echothree.control.user.core.common.edit;

import com.echothree.control.user.uom.common.spec.UnitOfMeasureTypeSpec;
import com.echothree.control.user.workflow.common.spec.WorkflowSpec;

public interface EntityAttributeEdit
        extends EntityAttributeDescriptionEdit, UnitOfMeasureTypeSpec, WorkflowSpec {
    
    String getEntityAttributeName();
    void setEntityAttributeName(String entityAttributeName);
    
    String getTrackRevisions();
    void setTrackRevisions(String trackRevisions);
    
    String getCheckContentWebAddress();
    void setCheckContentWebAddress(String checkContentWebAddress);
    
    String getValidationPattern();
    void setValidationPattern(String validationPattern);
    
    String getUpperRangeIntegerValue();
    void setUpperRangeIntegerValue(String upperRangeIntegerValue);
    
    String getUpperLimitIntegerValue();
    void setUpperLimitIntegerValue(String upperLimitIntegerValue);
    
    String getLowerLimitIntegerValue();
    void setLowerLimitIntegerValue(String lowerLimitIntegerValue);
    
    String getLowerRangeIntegerValue();
    void setLowerRangeIntegerValue(String lowerRangeIntegerValue);
    
    String getUpperRangeLongValue();
    void setUpperRangeLongValue(String upperRangeLongValue);
    
    String getUpperLimitLongValue();
    void setUpperLimitLongValue(String upperLimitLongValue);
    
    String getLowerLimitLongValue();
    void setLowerLimitLongValue(String lowerLimitLongValue);
    
    String getLowerRangeLongValue();
    void setLowerRangeLongValue(String lowerRangeLongValue);
    
    String getEntityListItemSequenceName();
    void setEntityListItemSequenceName(String entityListItemSequenceName);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
