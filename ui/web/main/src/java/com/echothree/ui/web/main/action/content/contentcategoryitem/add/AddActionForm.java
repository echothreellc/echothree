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

package com.echothree.ui.web.main.action.content.contentcategoryitem.add;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetInventoryConditionChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentCategoryItemAdd")
public class AddActionForm
        extends BaseActionForm {

    private InventoryConditionChoicesBean inventoryConditionChoices;
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private CurrencyChoicesBean currencyChoices;

    private String contentCollectionName;
    private String contentCatalogName;
    private String contentCategoryName;
    private String itemName;
    private String inventoryConditionChoice;
    private String currencyChoice;
    private String unitOfMeasureTypeChoice;
    private Boolean isDefault;
    private String sortOrder;

    private void setupInventoryConditionChoices()
            throws NamingException {
        if(inventoryConditionChoices == null) {
            var form = InventoryUtil.getHome().getGetInventoryConditionChoicesForm();

            form.setInventoryConditionUseTypeName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
            form.setDefaultInventoryConditionChoice(inventoryConditionChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = InventoryUtil.getHome().getInventoryConditionChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getInventoryConditionChoicesResult = (GetInventoryConditionChoicesResult)executionResult.getResult();
            inventoryConditionChoices = getInventoryConditionChoicesResult.getInventoryConditionChoices();

            if(inventoryConditionChoice == null) {
                inventoryConditionChoice = inventoryConditionChoices.getDefaultValue();
            }
        }
    }

    private void setupUnitOfMeasureTypeChoices()
            throws NamingException {
        if(unitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

                form.setItemName(itemName);
                form.setDefaultUnitOfMeasureTypeChoice(unitOfMeasureTypeChoice);
                form.setAllowNullChoice(String.valueOf(false));

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                unitOfMeasureTypeChoices = result.getUnitOfMeasureTypeChoices();

                if(unitOfMeasureTypeChoice == null) {
                    unitOfMeasureTypeChoice = unitOfMeasureTypeChoices.getDefaultValue();
                }
        }
    }

    public void setupCurrencyChoices()
            throws NamingException {
        if(currencyChoices == null) {
            var form = AccountingUtil.getHome().getGetCurrencyChoicesForm();

                form.setDefaultCurrencyChoice(currencyChoice);
                form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getCurrencyChoicesResult = (GetCurrencyChoicesResult)executionResult.getResult();
                currencyChoices = getCurrencyChoicesResult.getCurrencyChoices();

                if(currencyChoice == null) {
                    currencyChoice = currencyChoices.getDefaultValue();
                }
        }
    }

    public String getContentCollectionName() {
        return contentCollectionName;
    }

    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    public String getContentCatalogName() {
        return contentCatalogName;
    }

    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    public String getContentCategoryName() {
        return contentCategoryName;
    }

    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<LabelValueBean> getInventoryConditionChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupInventoryConditionChoices();
        if(inventoryConditionChoices != null)
            choices = convertChoices(inventoryConditionChoices);

        return choices;
    }

    public String getInventoryConditionChoice()
            throws NamingException {
        setupInventoryConditionChoices();
        
        return inventoryConditionChoice;
    }

    public void setInventoryConditionChoice(String inventoryConditionChoice) {
        this.inventoryConditionChoice = inventoryConditionChoice;
    }

    public String getCurrencyChoice()
            throws NamingException {
        setupCurrencyChoices();

        return currencyChoice;
    }

    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }

    public List<LabelValueBean> getCurrencyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupCurrencyChoices();
        if(currencyChoices != null)
            choices = convertChoices(currencyChoices);

        return choices;
    }

    public String getUnitOfMeasureTypeChoice()
            throws NamingException {
        setupUnitOfMeasureTypeChoices();

        return unitOfMeasureTypeChoice;
    }

    public void setUnitOfMeasureTypeChoice(String unitOfMeasureTypeChoice) {
        this.unitOfMeasureTypeChoice = unitOfMeasureTypeChoice;
    }

    public List<LabelValueBean> getUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupUnitOfMeasureTypeChoices();
        if(unitOfMeasureTypeChoices != null) {
            choices = convertChoices(unitOfMeasureTypeChoices);
        }

        return choices;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setIsDefault(false);
    }
    
}
