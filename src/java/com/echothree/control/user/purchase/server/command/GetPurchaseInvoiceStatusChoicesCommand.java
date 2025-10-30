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

package com.echothree.control.user.purchase.server.command;

import com.echothree.control.user.purchase.common.form.GetPurchaseInvoiceStatusChoicesForm;
import com.echothree.control.user.purchase.common.result.PurchaseResultFactory;
import com.echothree.model.control.invoice.server.logic.PurchaseInvoiceLogic;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetPurchaseInvoiceStatusChoicesCommand
        extends BaseSimpleCommand<GetPurchaseInvoiceStatusChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PurchaseInvoiceStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("InvoiceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("DefaultInvoiceStatusChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetPurchaseInvoiceStatusChoicesCommand */
    public GetPurchaseInvoiceStatusChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var purchaseInvoiceLogic = PurchaseInvoiceLogic.getInstance();
        var result = PurchaseResultFactory.getGetPurchaseInvoiceStatusChoicesResult();
        var invoiceName = form.getInvoiceName();
        var invoice = purchaseInvoiceLogic.getInvoiceByName(invoiceName);
        
        if(invoiceName == null || invoice != null) {
            var defaultInvoiceStatusChoice = form.getDefaultInvoiceStatusChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setPurchaseInvoiceStatusChoices(purchaseInvoiceLogic.getPurchaseInvoiceStatusChoices(defaultInvoiceStatusChoice, getPreferredLanguage(), allowNullChoice, invoice,
                    getPartyPK()));
        } else {
            addExecutionError(ExecutionErrors.UnknownPurchaseInvoiceName.name(), invoiceName);
        }
        
        return result;
    }
    
}
