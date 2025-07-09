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

package com.echothree.ui.web.main.action.salesorder.salesorderbatch;

import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.common.result.GetSalesOrderBatchStatusChoicesResult;
import com.echothree.model.control.sales.common.choice.SalesOrderBatchStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SalesOrderBatchStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private SalesOrderBatchStatusChoicesBean salesOrderBatchStatusChoices;
    
    private String batchName;
    private String salesOrderBatchStatusChoice;
    
    public void setupSalesOrderBatchStatusChoices()
            throws NamingException {
        if(salesOrderBatchStatusChoices == null) {
            var form = SalesUtil.getHome().getGetSalesOrderBatchStatusChoicesForm();

            form.setBatchName(batchName);
            form.setDefaultSalesOrderBatchStatusChoice(salesOrderBatchStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = SalesUtil.getHome().getSalesOrderBatchStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getSalesOrderBatchStatusChoicesResult = (GetSalesOrderBatchStatusChoicesResult)executionResult.getResult();
            salesOrderBatchStatusChoices = getSalesOrderBatchStatusChoicesResult.getSalesOrderBatchStatusChoices();

            if(salesOrderBatchStatusChoice == null) {
                salesOrderBatchStatusChoice = salesOrderBatchStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getBatchName() {
        return batchName;
    }
    
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    
    public String getSalesOrderBatchStatusChoice() {
        return salesOrderBatchStatusChoice;
    }
    
    public void setSalesOrderBatchStatusChoice(String salesOrderBatchStatusChoice) {
        this.salesOrderBatchStatusChoice = salesOrderBatchStatusChoice;
    }
    
    public List<LabelValueBean> getSalesOrderBatchStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSalesOrderBatchStatusChoices();
        if(salesOrderBatchStatusChoices != null)
            choices = convertChoices(salesOrderBatchStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
