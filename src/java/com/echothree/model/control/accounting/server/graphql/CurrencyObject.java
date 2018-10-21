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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.control.user.accounting.server.command.GetSymbolPositionCommand;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("currency object")
@GraphQLName("Currency")
public class CurrencyObject
        extends BaseEntityInstanceObject {

    private final Currency currency; // Always Present
    
    public CurrencyObject(Currency currency) {
        super(currency.getPrimaryKey());
        
        this.currency = currency;
    }
    
    private Boolean hasSymbolPositionAccess;
    
    private boolean getHasSymbolPositionAccess(final DataFetchingEnvironment env) {
        if(hasSymbolPositionAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetSymbolPositionCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasSymbolPositionAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasSymbolPositionAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("currency iso name")
    @GraphQLNonNull
    public String getCurrencyIsoName() {
        return currency.getCurrencyIsoName();
    }

    @GraphQLField
    @GraphQLDescription("symbol")
    public String getSymbol() {
        return currency.getSymbol();
    }

    @GraphQLField
    @GraphQLDescription("symbol position")
    public SymbolPositionObject getSymbolPosition(final DataFetchingEnvironment env) {
        return getHasSymbolPositionAccess(env) ? new SymbolPositionObject(currency.getSymbolPosition()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("symbol on list start")
    public Boolean getSymbolOnListStart() {
        return currency.getSymbolOnListStart();
    }

    @GraphQLField
    @GraphQLDescription("symbol on list member")
    public Boolean getSymbolOnListMember() {
        return currency.getSymbolOnListMember();
    }

    @GraphQLField
    @GraphQLDescription("symbol on subtotal")
    public Boolean getSymbolOnSubtotal() {
        return currency.getSymbolOnSubtotal();
    }

    @GraphQLField
    @GraphQLDescription("symbol on total")
    public Boolean getSymbolOnTotal() {
        return currency.getSymbolOnTotal();
    }

    @GraphQLField
    @GraphQLDescription("grouping separator")
    @GraphQLNonNull
    public String getGroupingSeparator() {
        return currency.getGroupingSeparator();
    }

    @GraphQLField
    @GraphQLDescription("grouping size")
    @GraphQLNonNull
    public Integer getGroupingSize() {
        return currency.getGroupingSize();
    }

    @GraphQLField
    @GraphQLDescription("fraction separator")
    public String getFractionSeparator() {
        return currency.getFractionSeparator();
    }

    @GraphQLField
    @GraphQLDescription("default fraction digits")
    public Integer getDefaultFractionDigits() {
        return currency.getDefaultFractionDigits();
    }

    @GraphQLField
    @GraphQLDescription("price unit fraction digits")
    public Integer getPriceUnitFractionDigits() {
        return currency.getPriceUnitFractionDigits();
    }

    @GraphQLField
    @GraphQLDescription("price line fraction digits")
    public Integer getPriceLineFractionDigits() {
        return currency.getPriceLineFractionDigits();
    }

    @GraphQLField
    @GraphQLDescription("cost unit fraction digits")
    public Integer getCostUnitFractionDigits() {
        return currency.getCostUnitFractionDigits();
    }

    @GraphQLField
    @GraphQLDescription("cost line fraction digits")
    public Integer getCostLineFractionDigits() {
        return currency.getCostLineFractionDigits();
    }

    @GraphQLField
    @GraphQLDescription("minus sign")
    @GraphQLNonNull
    public String getMinusSign() {
        return currency.getMinusSign();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return currency.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return currency.getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return accountingControl.getBestCurrencyDescription(currency, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }

}
