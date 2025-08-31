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

package com.echothree.ui.web.main.action.configuration.printergroup;

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

@SproutForm(name="PrinterGroupAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean keepPrintedJobsTimeUnitOfMeasureTypeChoices;

    private String printerGroupName;
    private String keepPrintedJobsTime;
    private String keepPrintedJobsTimeUnitOfMeasureTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupKeepPrintedJobsTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(keepPrintedJobsTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(keepPrintedJobsTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            keepPrintedJobsTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(keepPrintedJobsTimeUnitOfMeasureTypeChoice == null) {
                keepPrintedJobsTimeUnitOfMeasureTypeChoice = keepPrintedJobsTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }

    public void setPrinterGroupName(String printerGroupName) {
        this.printerGroupName = printerGroupName;
    }
    
    public String getPrinterGroupName() {
        return printerGroupName;
    }
    
    public String getKeepPrintedJobsTime() {
        return keepPrintedJobsTime;
    }

    public void setKeepPrintedJobsTime(String keepPrintedJobsTime) {
        this.keepPrintedJobsTime = keepPrintedJobsTime;
    }

    public List<LabelValueBean> getKeepPrintedJobsTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupKeepPrintedJobsTimeUnitOfMeasureTypeChoices();
        if(keepPrintedJobsTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(keepPrintedJobsTimeUnitOfMeasureTypeChoices);
        }

        return choices;
    }

    public String getKeepPrintedJobsTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupKeepPrintedJobsTimeUnitOfMeasureTypeChoices();
        return keepPrintedJobsTimeUnitOfMeasureTypeChoice;
    }

    public void setKeepPrintedJobsTimeUnitOfMeasureTypeChoice(String keepPrintedJobsTimeUnitOfMeasureTypeChoice) {
        this.keepPrintedJobsTimeUnitOfMeasureTypeChoice = keepPrintedJobsTimeUnitOfMeasureTypeChoice;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
        
        isDefault = false;
    }
    
}
