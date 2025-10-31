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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.CreateCurrencyForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.SymbolPositionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateCurrencyCommand
        extends BaseSimpleCommand<CreateCurrencyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Currency.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, 3L),
                new FieldDefinition("Symbol", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SymbolOnListStart", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("SymbolOnListMember", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("SymbolOnSubtotal", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("SymbolOnTotal", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("GroupingSeparator", FieldType.STRING, true, 1L, 1L),
                new FieldDefinition("GroupingSize", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("FractionSeparator", FieldType.STRING, false, 1L, 1L),
                new FieldDefinition("DefaultFractionDigits", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("PriceUnitFractionDigits", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("PriceLineFractionDigits", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("CostUnitFractionDigits", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("CostLineFractionDigits", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinusSign", FieldType.STRING, true, 1L, 1L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCurrencyCommand */
    public CreateCurrencyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = form.getCurrencyIsoName();
        var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
        
        if(currency == null) {
            var symbolPosition = SymbolPositionLogic.getInstance().getSymbolPositionByName(this, form.getSymbolPositionName());
            
            if(!hasExecutionErrors()) {
                var groupingSeparator = form.getGroupingSeparator();
                var groupingSize = Integer.valueOf(form.getGroupingSize());
                var minusSign = form.getMinusSign();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var symbol = form.getSymbol();
                var symbolOnListStart = symbol == null? null: Boolean.valueOf(form.getSymbolOnListStart());
                var symbolOnListMember = symbol == null? null: Boolean.valueOf(form.getSymbolOnListMember());
                var symbolOnSubtotal = symbol == null? null: Boolean.valueOf(form.getSymbolOnSubtotal());
                var symbolOnTotal = symbol == null? null: Boolean.valueOf(form.getSymbolOnTotal());

                if(symbol == null || (symbol != null && symbolOnListStart != null && symbolOnListMember != null
                        && symbolOnSubtotal != null && symbolOnTotal != null)) {
                    var fractionSeparator = form.getFractionSeparator();
                    var defaultFractionDigits = fractionSeparator == null? null: Integer.valueOf(form.getDefaultFractionDigits());
                    var priceUnitFractionDigits = fractionSeparator == null? null: Integer.valueOf(form.getPriceUnitFractionDigits());
                    var priceLineFractionDigits = fractionSeparator == null? null: Integer.valueOf(form.getPriceLineFractionDigits());
                    var costUnitFractionDigits = fractionSeparator == null? null: Integer.valueOf(form.getCostUnitFractionDigits());
                    var costLineFractionDigits = fractionSeparator == null? null: Integer.valueOf(form.getCostLineFractionDigits());

                    if(fractionSeparator == null || (defaultFractionDigits != null && priceUnitFractionDigits != null
                            && priceLineFractionDigits != null && costUnitFractionDigits != null && costLineFractionDigits != null)) {
                        accountingControl.createCurrency(currencyIsoName, symbol, symbolPosition, symbolOnListStart, symbolOnListMember,
                                symbolOnSubtotal, symbolOnTotal, groupingSeparator, groupingSize, fractionSeparator, defaultFractionDigits,
                                priceUnitFractionDigits, priceLineFractionDigits, costUnitFractionDigits, costLineFractionDigits, minusSign,
                                isDefault, sortOrder, getPartyPK());
                    } else {
                        if(defaultFractionDigits == null) {
                            addExecutionError(ExecutionErrors.MissingDefaultFractionDigits.name());
                        }
                        if(priceUnitFractionDigits == null) {
                            addExecutionError(ExecutionErrors.MissingPriceUnitFractionDigits.name());
                        }
                        if(priceLineFractionDigits == null) {
                            addExecutionError(ExecutionErrors.MissingPriceLineFractionDigits.name());
                        }
                        if(costUnitFractionDigits == null) {
                            addExecutionError(ExecutionErrors.MissingCostUnitFractionDigits.name());
                        }
                        if(costLineFractionDigits == null) {
                            addExecutionError(ExecutionErrors.MissingCostLineFractionDigits.name());
                        }
                    }
                } else {
                    if(symbolOnListStart == null) {
                        addExecutionError(ExecutionErrors.MissingSymbolOnListStart.name());
                    }
                    if(symbolOnListMember == null) {
                        addExecutionError(ExecutionErrors.MissingSymbolOnListMember.name());
                    }
                    if(symbolOnSubtotal == null) {
                        addExecutionError(ExecutionErrors.MissingSymbolOnSubtotal.name());
                    }
                    if(symbolOnTotal == null) {
                        addExecutionError(ExecutionErrors.MissingSymbolOnTotal.name());
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateCurrencyIsoName.name(), currencyIsoName);
        }
        
        return null;
    }
    
}
