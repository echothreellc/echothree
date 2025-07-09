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

package com.echothree.ui.web.main.action.uom.unitofmeasuretype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetSymbolPositionChoicesResult;
import com.echothree.model.control.accounting.common.choice.SymbolPositionChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UnitOfMeasureTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SymbolPositionChoicesBean symbolPositionChoices = null;
    
    private String unitOfMeasureKindName;
    private String unitOfMeasureTypeName;
    private String symbolPositionChoice = null;
    private Boolean suppressSymbolSeparator;
    private Boolean isDefault;
    private String sortOrder;
    private String singularDescription;
    private String pluralDescription;
    private String symbol;
    
    private void setupSymbolPositionChoices() {
        if(symbolPositionChoices == null) {
            try {
                var commandForm = AccountingUtil.getHome().getGetSymbolPositionChoicesForm();
                
                commandForm.setDefaultSymbolPositionChoice(symbolPositionChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = AccountingUtil.getHome().getSymbolPositionChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getSymbolPositionChoicesResult = (GetSymbolPositionChoicesResult)executionResult.getResult();
                symbolPositionChoices = getSymbolPositionChoicesResult.getSymbolPositionChoices();
                
                if(symbolPositionChoice == null) {
                    symbolPositionChoice = symbolPositionChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, symbolPositionChoices remains null, no default
            }
        }
    }
    
    public void setUnitOfMeasureKindName(String unitOfMeasureKindName) {
        this.unitOfMeasureKindName = unitOfMeasureKindName;
    }
    
    public String getUnitOfMeasureKindName() {
        return unitOfMeasureKindName;
    }
    
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }
    
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }
    
    public List<LabelValueBean> getSymbolPositionChoices() {
        List<LabelValueBean> choices = null;
        
        setupSymbolPositionChoices();
        if(symbolPositionChoices != null) {
            choices = convertChoices(symbolPositionChoices);
        }
        
        return choices;
    }
    
    public void setSymbolPositionChoice(String symbolPositionChoice) {
        this.symbolPositionChoice = symbolPositionChoice;
    }
    
    public String getSymbolPositionChoice() {
        setupSymbolPositionChoices();
        
        return symbolPositionChoice;
    }
    
    public Boolean getSuppressSymbolSeparator() {
        return suppressSymbolSeparator;
    }
    
    public void setSuppressSymbolSeparator(Boolean suppressSymbolSeparator) {
        this.suppressSymbolSeparator = suppressSymbolSeparator;
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
    
    public String getSingularDescription() {
        return singularDescription;
    }
    
    public void setSingularDescription(String singularDescription) {
        this.singularDescription = singularDescription;
    }
    
    public String getPluralDescription() {
        return pluralDescription;
    }
    
    public void setPluralDescription(String pluralDescription) {
        this.pluralDescription = pluralDescription;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
