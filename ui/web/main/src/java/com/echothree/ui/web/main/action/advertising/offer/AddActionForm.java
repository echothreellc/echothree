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

package com.echothree.ui.web.main.action.advertising.offer;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.GetFilterChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.filter.common.choice.FilterChoicesBean;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
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
    
    public void setupSalesOrderSequenceChoices()
            throws NamingException {
        if(salesOrderSequenceChoices == null) {
            var form = SequenceUtil.getHome().getGetSequenceChoicesForm();

            form.setSequenceTypeName(SequenceTypes.SALES_ORDER.name());
            form.setDefaultSequenceChoice(salesOrderSequenceChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSequenceChoicesResult)executionResult.getResult();
            salesOrderSequenceChoices = result.getSequenceChoices();

            if(salesOrderSequenceChoice == null) {
                salesOrderSequenceChoice = salesOrderSequenceChoices.getDefaultValue();
            }
        }
    }
    
    public void setupOfferItemSelectorChoices()
            throws NamingException {
        if(offerItemSelectorChoices == null) {
            var form = SelectorUtil.getHome().getGetSelectorChoicesForm();

            form.setSelectorKindName(SelectorKinds.ITEM.name());
            form.setSelectorTypeName(SelectorTypes.OFFER.name());
            form.setDefaultSelectorChoice(offerItemSelectorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorChoicesResult)executionResult.getResult();
            offerItemSelectorChoices = result.getSelectorChoices();

            if(offerItemSelectorChoice == null) {
                offerItemSelectorChoice = offerItemSelectorChoices.getDefaultValue();
            }
        }
    }
    
    public void setupOfferItemPriceFilterChoices()
            throws NamingException {
        if(offerItemPriceFilterChoices == null) {
            var form = FilterUtil.getHome().getGetFilterChoicesForm();

            form.setFilterKindName(FilterKinds.PRICE.name());
            form.setFilterTypeName(FilterTypes.OFFER_ITEM_PRICE.name());
            form.setDefaultFilterChoice(offerItemPriceFilterChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = FilterUtil.getHome().getFilterChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetFilterChoicesResult)executionResult.getResult();
            offerItemPriceFilterChoices = result.getFilterChoices();

            if(offerItemPriceFilterChoice == null) {
                offerItemPriceFilterChoice = offerItemPriceFilterChoices.getDefaultValue();
            }
        }
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public String getOfferName() {
        return offerName;
    }
    
    public List<LabelValueBean> getSalesOrderSequenceChoices()
            throws NamingException {
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
    
    public String getSalesOrderSequenceChoice()
            throws NamingException {
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
    
    public List<LabelValueBean> getOfferItemSelectorChoices()
            throws NamingException {
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
    
    public String getOfferItemSelectorChoice()
            throws NamingException {
        setupOfferItemSelectorChoices();
        
        return offerItemSelectorChoice;
    }
    
    public List<LabelValueBean> getOfferItemPriceFilterChoices()
            throws NamingException {
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
    
    public String getOfferItemPriceFilterChoice()
            throws NamingException {
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
        
        isDefault = false;
    }
    
}
