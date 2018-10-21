// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.remote.form.GetCurrencyChoicesForm;
import com.echothree.control.user.accounting.remote.result.GetCurrencyChoicesResult;
import com.echothree.control.user.batch.common.BatchUtil;
import com.echothree.control.user.batch.remote.form.GetBatchAliasTypeChoicesForm;
import com.echothree.control.user.batch.remote.result.GetBatchAliasTypeChoicesResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.remote.form.GetPaymentMethodChoicesForm;
import com.echothree.control.user.payment.remote.result.GetPaymentMethodChoicesResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.remote.form.GetWorkflowStepChoicesForm;
import com.echothree.control.user.workflow.remote.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.accounting.remote.choice.CurrencyChoicesBean;
import com.echothree.model.control.batch.common.BatchConstants;
import com.echothree.model.control.batch.remote.choice.BatchAliasTypeChoicesBean;
import com.echothree.model.control.payment.remote.choice.PaymentMethodChoicesBean;
import com.echothree.model.control.sales.common.workflow.SalesOrderBatchStatusConstants;
import com.echothree.model.control.workflow.remote.choice.WorkflowStepChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SalesOrderBatchMain")
public class MainActionForm
        extends BaseActionForm {
    
    private CurrencyChoicesBean currencyChoices;
    private PaymentMethodChoicesBean paymentMethodChoices;
    private WorkflowStepChoicesBean salesOrderBatchStatusChoices;
    private BatchAliasTypeChoicesBean batchAliasTypeChoices;
    
    private String batchName;
    private String currencyChoice;
    private String paymentMethodChoice;
    private String salesOrderBatchStatusChoice;
    private String batchAliasTypeChoice;
    private String alias;
    private String createdSince;
    private String modifiedSince;
    
    public void setupCurrencyChoices() {
        if(currencyChoices == null) {
            try {
                GetCurrencyChoicesForm form = AccountingUtil.getHome().getGetCurrencyChoicesForm();
                
                form.setDefaultCurrencyChoice(currencyChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCurrencyChoicesResult result = (GetCurrencyChoicesResult)executionResult.getResult();
                currencyChoices = result.getCurrencyChoices();
                
                if(currencyChoice == null) {
                    currencyChoice = currencyChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, currencyChoices remains null, no default
            }
        }
    }
    
    private void setupPaymentMethodChoices() {
        if(paymentMethodChoices == null) {
            try {
                GetPaymentMethodChoicesForm form = PaymentUtil.getHome().getGetPaymentMethodChoicesForm();
                
                form.setDefaultPaymentMethodChoice(paymentMethodChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = PaymentUtil.getHome().getPaymentMethodChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPaymentMethodChoicesResult getPaymentMethodChoicesResult = (GetPaymentMethodChoicesResult)executionResult.getResult();
                paymentMethodChoices = getPaymentMethodChoicesResult.getPaymentMethodChoices();
                
                if(paymentMethodChoice == null)
                    paymentMethodChoice = paymentMethodChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, paymentMethodChoices remains null, no default
            }
        }
    }
    
    private void setupSalesOrderBatchStatusChoices() {
        if(salesOrderBatchStatusChoices == null) {
            try {
                GetWorkflowStepChoicesForm form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();
                
                form.setWorkflowName(SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS);
                form.setDefaultWorkflowStepChoice(salesOrderBatchStatusChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetWorkflowStepChoicesResult result = (GetWorkflowStepChoicesResult)executionResult.getResult();
                salesOrderBatchStatusChoices = result.getWorkflowStepChoices();
                
                if(salesOrderBatchStatusChoice == null) {
                    salesOrderBatchStatusChoice = salesOrderBatchStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, salesOrderBatchStatusChoices remains null, no default
            }
        }
    }
    
    private void setupBatchAliasTypeChoices() {
        if(batchAliasTypeChoices == null) {
            try {
                GetBatchAliasTypeChoicesForm form = BatchUtil.getHome().getGetBatchAliasTypeChoicesForm();

                form.setBatchTypeName(BatchConstants.BatchType_SALES_ORDER);
                form.setDefaultBatchAliasTypeChoice(batchAliasTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = BatchUtil.getHome().getBatchAliasTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetBatchAliasTypeChoicesResult getBatchAliasTypeChoicesResult = (GetBatchAliasTypeChoicesResult)executionResult.getResult();
                batchAliasTypeChoices = getBatchAliasTypeChoicesResult.getBatchAliasTypeChoices();

                if(batchAliasTypeChoice == null) {
                    batchAliasTypeChoice = batchAliasTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, batchAliasTypeChoices remains null, no default
            }
        }
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public String getCurrencyChoice() {
        setupCurrencyChoices();
        
        return currencyChoice;
    }
    
    public List<LabelValueBean> getCurrencyChoices() {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null) {
            choices = convertChoices(currencyChoices);
        }
        
        return choices;
    }
    
    public String getPaymentMethodChoice() {
        return paymentMethodChoice;
    }
    
    public void setPaymentMethodChoice(String paymentMethodChoice) {
        this.paymentMethodChoice = paymentMethodChoice;
    }
    
    public List<LabelValueBean> getPaymentMethodChoices() {
        List<LabelValueBean> choices = null;
        
        setupPaymentMethodChoices();
        if(paymentMethodChoices != null) {
            choices = convertChoices(paymentMethodChoices);
        }
        
        return choices;
    }
    
    public String getSalesOrderBatchStatusChoice() {
        setupSalesOrderBatchStatusChoices();
        
        return salesOrderBatchStatusChoice;
    }
    
    public void setSalesOrderBatchStatusChoice(String salesOrderBatchStatusChoice) {
        this.salesOrderBatchStatusChoice = salesOrderBatchStatusChoice;
    }
    
    public List<LabelValueBean> getSalesOrderBatchStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupSalesOrderBatchStatusChoices();
        if(salesOrderBatchStatusChoices != null) {
            choices = convertChoices(salesOrderBatchStatusChoices);
        }
        
        return choices;
    }

    public String getBatchAliasTypeChoice() {
        setupBatchAliasTypeChoices();
        
        return batchAliasTypeChoice;
    }
    
    public void setBatchAliasTypeChoice(String batchAliasTypeChoice) {
        this.batchAliasTypeChoice = batchAliasTypeChoice;
    }
    
    public List<LabelValueBean> getBatchAliasTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupBatchAliasTypeChoices();
        if(batchAliasTypeChoices != null) {
            choices = convertChoices(batchAliasTypeChoices);
        }

        return choices;
    }
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getCreatedSince() {
        return createdSince;
    }

    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

}
