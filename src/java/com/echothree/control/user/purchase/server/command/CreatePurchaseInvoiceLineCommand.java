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

import com.echothree.control.user.purchase.common.form.CreatePurchaseInvoiceLineForm;
import com.echothree.control.user.purchase.common.result.PurchaseResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.invoice.common.InvoiceTypes;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.invoice.server.logic.InvoiceLogic;
import com.echothree.model.control.invoice.server.logic.PurchaseInvoiceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreatePurchaseInvoiceLineCommand
        extends BaseSimpleCommand<CreatePurchaseInvoiceLineForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PurchaseInvoiceLine.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceLineSequence", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("ParentInvoiceLine", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("Amount", FieldType.COST_UNIT, true, null, null),
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreatePurchaseInvoiceLineCommand */
    public CreatePurchaseInvoiceLineCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected void setupValidator(Validator validator) {
        var invoiceName = form.getInvoiceName();
        var invoice = invoiceName == null? null: PurchaseInvoiceLogic.getInstance().getInvoiceByName(invoiceName);
        
        if(invoice != null) {
            validator.setCurrency(InvoiceLogic.getInstance().getInvoiceCurrency(invoice));
        }
    }
    
    @Override
    protected BaseResult execute() {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var result = PurchaseResultFactory.getCreatePurchaseInvoiceLineResult();
        InvoiceLine invoiceLine = null;
        var invoiceName = form.getInvoiceName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(InvoiceTypes.PURCHASE_INVOICE.name());
        var invoice = invoiceControl.getInvoiceByName(invoiceType, invoiceName);
        
        if(invoice != null) {
            var rawParentInvoiceLine = form.getParentInvoiceLine();
            var intParentInvoiceLine = rawParentInvoiceLine == null? null: Integer.valueOf(rawParentInvoiceLine);
            var parentInvoiceLine = intParentInvoiceLine == null? null: invoiceControl.getInvoiceLine(invoice, intParentInvoiceLine);
            
            if(rawParentInvoiceLine == null || parentInvoiceLine != null) {
                var invoiceTypeName = form.getInvoiceLineTypeName();
                var invoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceTypeName);
                
                if(invoiceLineType != null) {
                    var accountingControl = Session.getModelController(AccountingControl.class);
                    var glAccountName = form.getGlAccountName();
                    var glAccount = glAccountName == null? null: accountingControl.getGlAccountByName(glAccountName);
                    
                    if(glAccountName == null || glAccount != null) {
                        var strInvoiceLineSequence = form.getInvoiceLineSequence();
                        var invoiceLineSequence = strInvoiceLineSequence == null? null: Integer.valueOf(strInvoiceLineSequence);
                        
                        if(invoiceLineSequence == null || invoiceControl.getInvoiceLine(invoice, invoiceLineSequence) == null) {
                            var amount = Long.valueOf(form.getAmount());
                            var description = form.getDescription();

                            invoiceLine = PurchaseInvoiceLogic.getInstance().createInvoiceLine(this, invoice, invoiceLineSequence, parentInvoiceLine, amount, invoiceLineType, glAccount,
                                    description, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateInvoiceLineSequence.name(), invoiceName, strInvoiceLineSequence);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeName.name(), InvoiceTypes.PURCHASE_INVOICE.name(), invoiceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentInvoiceLine.name(), invoiceName, rawParentInvoiceLine);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPurchaseInvoiceName.name(), invoiceName);
        }
        
        if(invoiceLine != null) {
            var invoiceLineDetail = invoiceLine.getLastDetail();
            
            result.setEntityRef(invoiceLine.getPrimaryKey().getEntityRef());
            result.setInvoiceName(invoiceLineDetail.getInvoice().getLastDetail().getInvoiceName());
            result.setInvoiceLineSequence(invoiceLineDetail.getInvoiceLineSequence().toString());
        }
        
        return result;
    }
    
}
