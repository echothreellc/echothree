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

package com.echothree.control.user.accounting.common.form;

import com.echothree.control.user.accounting.common.spec.CurrencySpec;
import com.echothree.control.user.accounting.common.spec.SymbolPositionSpec;

public interface CreateCurrencyForm
        extends CurrencySpec, SymbolPositionSpec {
    
    String getSymbol();
    void setSymbol(String symbol);
    
    String getSymbolOnListStart();
    void setSymbolOnListStart(String symbolOnListStart);
    
    String getSymbolOnListMember();
    void setSymbolOnListMember(String symbolOnListMember);
    
    String getSymbolOnSubtotal();
    void setSymbolOnSubtotal(String symbolOnSubtotal);
    
    String getSymbolOnTotal();
    void setSymbolOnTotal(String symbolOnTotal);
    
    String getGroupingSeparator();
    void setGroupingSeparator(String groupingSeparator);
    
    String getGroupingSize();
    void setGroupingSize(String groupingSize);
    
    String getFractionSeparator();
    void setFractionSeparator(String fractionSeparator);
    
    String getDefaultFractionDigits();
    void setDefaultFractionDigits(String defaultFractionDigits);
    
    String getPriceUnitFractionDigits();
    void setPriceUnitFractionDigits(String priceUnitFractionDigits);
    
    String getPriceLineFractionDigits();
    void setPriceLineFractionDigits(String priceLineFractionDigits);
    
    String getCostUnitFractionDigits();
    void setCostUnitFractionDigits(String costUnitFractionDigits);
    
    String getCostLineFractionDigits();
    void setCostLineFractionDigits(String costLineFractionDigits);
    
    String getMinusSign();
    void setMinusSign(String minusSign);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
