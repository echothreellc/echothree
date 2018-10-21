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

package com.echothree.ui.web.main.action.advertising.offer;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.remote.form.GetFilterChoicesForm;
import com.echothree.control.user.filter.remote.result.GetFilterChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.remote.form.GetSelectorChoicesForm;
import com.echothree.control.user.selector.remote.result.GetSelectorChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.remote.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.remote.result.GetSequenceChoicesResult;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.remote.choice.FilterChoicesBean;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.remote.choice.SelectorChoicesBean;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.remote.choice.SequenceChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="OfferAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean salesOrderSequenceChoices;
    private SelectorChoicesBean offerItemSelectorChoices;
    private FilterChoicesBean offerItemPriceFilterChoices;
    
    private String offerName;
    private String salesOrderSequenceChoice;
    private String companyName;
    private String divisionName;
    private String departmentName;
    private String offerItemSelectorChoice;
    private String offerItemPriceFilterChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupSalesOrderSequenceChoices() {
        if(salesOrderSequenceChoices == null) {
            try {
                GetSequenceChoicesForm form = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                form.setSequenceTypeName(SequenceConstants.SequenceType_SALES_ORDER);
                form.setDefaultSequenceChoice(salesOrderSequenceChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                salesOrderSequenceChoices = result.getSequenceChoices();
                
                if(salesOrderSequenceChoice == null) {
                    salesOrderSequenceChoice = salesOrderSequenceChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, salesOrderSequenceChoices remains null, no default
            }
        }
    }
    
    public void setupOfferItemSelectorChoices() {
        if(offerItemSelectorChoices == null) {
            try {
                GetSelectorChoicesForm form = SelectorUtil.getHome().getGetSelectorChoicesForm();
                
                form.setSelectorKindName(SelectorConstants.SelectorKind_ITEM);
                form.setSelectorTypeName(SelectorConstants.SelectorType_OFFER);
                form.setDefaultSelectorChoice(offerItemSelectorChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSelectorChoicesResult result = (GetSelectorChoicesResult)executionResult.getResult();
                offerItemSelectorChoices = result.getSelectorChoices();
                
                if(offerItemSelectorChoice == null) {
                    offerItemSelectorChoice = offerItemSelectorChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, offerItemSelectorChoices remains null, no default
            }
        }
    }
    
    public void setupOfferItemPriceFilterChoices() {
        if(offerItemPriceFilterChoices == null) {
            try {
                GetFilterChoicesForm form = FilterUtil.getHome().getGetFilterChoicesForm();
                
                form.setFilterKindName(FilterConstants.FilterKind_PRICE);
                form.setFilterTypeName(FilterConstants.FilterType_OFFER_ITEM_PRICE);
                form.setDefaultFilterChoice(offerItemPriceFilterChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = FilterUtil.getHome().getFilterChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetFilterChoicesResult result = (GetFilterChoicesResult)executionResult.getResult();
                offerItemPriceFilterChoices = result.getFilterChoices();
                
                if(offerItemPriceFilterChoice == null) {
                    offerItemPriceFilterChoice = offerItemPriceFilterChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, offerItemPriceFilterChoices remains null, no default
            }
        }
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public String getOfferName() {
        return offerName;
    }
    
    public List<LabelValueBean> getSalesOrderSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupSalesOrderSequenceChoices();
        if(salesOrderSequenceChoices != null) {
            choices = convertChoices(salesOrderSequenceChoices);
        }
        
        return choices;
    }
    
    public void setSalesOrderSequenceChoice(String salesOrderSequenceChoice) {
        this.salesOrderSequenceChoice = salesOrderSequenceChoice;
    }
    
    public String getSalesOrderSequenceChoice() {
        setupSalesOrderSequenceChoices();
        
        return salesOrderSequenceChoice;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getDivisionName() {
        return divisionName;
    }
    
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public List<LabelValueBean> getOfferItemSelectorChoices() {
        List<LabelValueBean> choices = null;
        
        setupOfferItemSelectorChoices();
        if(offerItemSelectorChoices != null) {
            choices = convertChoices(offerItemSelectorChoices);
        }
        
        return choices;
    }
    
    public void setOfferItemSelectorChoice(String offerItemSelectorChoice) {
        this.offerItemSelectorChoice = offerItemSelectorChoice;
    }
    
    public String getOfferItemSelectorChoice() {
        setupOfferItemSelectorChoices();
        
        return offerItemSelectorChoice;
    }
    
    public List<LabelValueBean> getOfferItemPriceFilterChoices() {
        List<LabelValueBean> choices = null;
        
        setupOfferItemPriceFilterChoices();
        if(offerItemPriceFilterChoices != null) {
            choices = convertChoices(offerItemPriceFilterChoices);
        }
        
        return choices;
    }
    
    public void setOfferItemPriceFilterChoice(String offerItemPriceFilterChoice) {
        this.offerItemPriceFilterChoice = offerItemPriceFilterChoice;
    }
    
    public String getOfferItemPriceFilterChoice() {
        setupOfferItemPriceFilterChoices();
        
        return offerItemPriceFilterChoice;
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
        
        isDefault = Boolean.FALSE;
    }
    
}
