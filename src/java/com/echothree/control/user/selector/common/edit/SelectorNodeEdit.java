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

package com.echothree.control.user.selector.common.edit;

import com.echothree.control.user.accounting.common.spec.ItemAccountingCategorySpec;
import com.echothree.control.user.core.common.spec.EntityListItemSpec;
import com.echothree.control.user.employee.common.spec.ResponsibilityTypeSpec;
import com.echothree.control.user.employee.common.spec.SkillTypeSpec;
import com.echothree.control.user.geo.common.spec.CountrySpec;
import com.echothree.control.user.geo.common.spec.GeoCodeSpec;
import com.echothree.control.user.item.common.spec.ItemCategorySpec;
import com.echothree.control.user.payment.common.spec.PaymentMethodSpec;
import com.echothree.control.user.payment.common.spec.PaymentProcessorSpec;
import com.echothree.control.user.training.common.spec.TrainingClassSpec;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategorySpec;
import com.echothree.control.user.workflow.common.spec.WorkflowStepSpec;

public interface SelectorNodeEdit
        extends EntityListItemSpec, ResponsibilityTypeSpec, SkillTypeSpec, TrainingClassSpec,
        WorkflowStepSpec, ItemCategorySpec, ItemAccountingCategorySpec, ItemPurchasingCategorySpec, PaymentMethodSpec,
        PaymentProcessorSpec, GeoCodeSpec, CountrySpec {
    
    String getSelectorNodeName();
    void setSelectorNodeName(String selectorNodeName);
    
    String getIsRootSelectorNode();
    void setIsRootSelectorNode(String isRootSelectorNode);
    
    String getNegate();
    void setNegate(String negate);
    
    String getDescription();
    void setDescription(String description);
    
    // For Boolean nodes:
    String getSelectorBooleanTypeName();
    void setSelectorBooleanTypeName(String selectorBooleanTypeName);
    
    String getLeftSelectorNodeName();
    void setLeftSelectorNodeName(String leftSelectorNodeName);
    
    String getRightSelectorNodeName();
    void setRightSelectorNodeName(String rightSelectorNodeName);
    
    // For Item*Category nodes:
    String getCheckParents();
    void setCheckParents(String checkParents);
    
}
