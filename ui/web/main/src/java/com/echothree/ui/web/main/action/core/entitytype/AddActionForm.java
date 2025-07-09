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

package com.echothree.ui.web.main.action.core.entitytype;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean lockTimeoutUnitOfMeasureTypeChoices;

    private String componentVendorName;
    private String entityTypeName;
    private Boolean keepAllHistory;
    private String lockTimeout;
    private String lockTimeoutUnitOfMeasureTypeChoice;
    private Boolean isExtensible;
    private String sortOrder;
    private String description;
    
    private void setupLockTimeoutUnitOfMeasureTypeChoices()
            throws NamingException {
        if(lockTimeoutUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(lockTimeoutUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            lockTimeoutUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(lockTimeoutUnitOfMeasureTypeChoice == null) {
                lockTimeoutUnitOfMeasureTypeChoice = lockTimeoutUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }

    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }
    
   public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    public Boolean getKeepAllHistory() {
        return keepAllHistory;
    }
    
    public void setKeepAllHistory(Boolean keepAllHistory) {
        this.keepAllHistory = keepAllHistory;
    }
    
    public String getLockTimeout() {
        return lockTimeout;
    }

    public void setLockTimeout(String lockTimeout) {
        this.lockTimeout = lockTimeout;
    }

    public List<LabelValueBean> getLockTimeoutUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupLockTimeoutUnitOfMeasureTypeChoices();
        if(lockTimeoutUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(lockTimeoutUnitOfMeasureTypeChoices);
        }

        return choices;
    }

    public String getLockTimeoutUnitOfMeasureTypeChoice()
            throws NamingException {
        setupLockTimeoutUnitOfMeasureTypeChoices();
        return lockTimeoutUnitOfMeasureTypeChoice;
    }

    public void setLockTimeoutUnitOfMeasureTypeChoice(String lockTimeoutUnitOfMeasureTypeChoice) {
        this.lockTimeoutUnitOfMeasureTypeChoice = lockTimeoutUnitOfMeasureTypeChoice;
    }

    public Boolean getIsExtensible() {
        return isExtensible;
    }

    public void setIsExtensible(Boolean isExtensible) {
        this.isExtensible = isExtensible;
    }

    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        keepAllHistory = false;
        isExtensible = false;
    }

}
