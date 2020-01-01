// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.purchase.common.form.CreatePurchaseInvoiceForm;
import com.echothree.control.user.purchase.common.result.CreatePurchaseInvoiceResult;
import com.echothree.control.user.purchase.common.result.PurchaseResultFactory;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.invoice.server.logic.PurchaseInvoiceLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.invoice.common.workflow.PurchaseInvoiceStatusConstants;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
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

public class CreatePurchaseInvoiceCommand
        extends BaseSimpleCommand<CreatePurchaseInvoiceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PurchaseInvoice.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BillFromPartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BillFromContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BillToPartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BillToContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InvoicedTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("DueTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("PaidTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Reference", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePurchaseInvoiceCommand */
    public CreatePurchaseInvoiceCommand(UserVisitPK userVisitPK, CreatePurchaseInvoiceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreatePurchaseInvoiceResult result = PurchaseResultFactory.getCreatePurchaseInvoiceResult();
        Invoice invoice = null;
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String currencyIsoName = form.getCurrencyIsoName();
        Currency currency = currencyIsoName == null ? null : accountingControl.getCurrencyByIsoName(currencyIsoName);

        if(currencyIsoName == null || currency != null) {
            var termControl = (TermControl)Session.getModelController(TermControl.class);
            String termName = form.getTermName();
            Term term = termName == null ? null : termControl.getTermByName(termName);

            if(termName == null || term != null) {
                var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
                String vendorName = form.getVendorName();
                String billFromPartyName = form.getBillFromPartyName();
                int parameterCount = (vendorName == null? 0: 1) + (billFromPartyName == null? 0: 1);

                if(parameterCount == 1) {
                    var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    Party billFrom = null;
                    
                    if(vendorName == null) {
                        billFrom = partyControl.getPartyByName(billFromPartyName);
                        
                        if(billFrom != null) {
                            if(!billFrom.getLastDetail().getPartyType().getPartyTypeName().equals(PartyConstants.PartyType_VENDOR)) {
                                addExecutionError(ExecutionErrors.InvalidBillFromPartyType.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownBillFromPartyName.name(), billFromPartyName);
                        }
                    } else {
                        Vendor vendor = vendorControl.getVendorByName(vendorName);
                        
                        if(vendor != null) {
                            billFrom = vendor.getParty();
                        } else {
                            addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                        }
                    }
                    
                    if(!hasExecutionErrors()) {
                        var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                        String billFromContactMechanismName = form.getBillFromContactMechanismName();
                        PartyContactMechanism billFromContactMechanism = billFromContactMechanismName == null? null: contactControl.getPartyContactMechanismByContactMechanismName(this, billFrom, billFromContactMechanismName);
                        
                        if(billFromContactMechanismName == null || billFromContactMechanism != null) {
                            String companyName = form.getCompanyName();
                            String billToPartyName = form.getBillToPartyName();

                            parameterCount = (companyName == null? 0: 1) + (billToPartyName == null? 0: 1);

                            if(parameterCount < 2) {
                                Party billTo = null;

                                if(companyName != null) {
                                    PartyCompany partyCompany = companyName == null ? null : partyControl.getPartyCompanyByName(companyName);

                                    if(partyCompany != null) {
                                        billTo = partyCompany.getParty();
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                                    }
                                } else if(billToPartyName != null) {
                                    billTo = partyControl.getPartyByName(billToPartyName);

                                    if(billTo != null) {
                                        if(!billTo.getLastDetail().getPartyType().getPartyTypeName().equals(PartyConstants.PartyType_COMPANY)) {
                                            addExecutionError(ExecutionErrors.InvalidBillToPartyType.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownBillToPartyName.name(), billToPartyName);
                                    }
                                } else {
                                    billTo = getUserSession().getPartyRelationship().getFromParty();
                                }

                                if(!hasExecutionErrors()) {
                                    String billToContactMechanismName = form.getBillToContactMechanismName();
                                    PartyContactMechanism billToContactMechanism = billToContactMechanismName == null? null: contactControl.getPartyContactMechanismByContactMechanismName(this, billTo, billToContactMechanismName);
                                    
                                    if(billToContactMechanismName == null || billToContactMechanism != null) {
                                        String strInvoicedTime = form.getInvoicedTime();
                                        Long invoicedTime = strInvoicedTime == null ? null : Long.valueOf(strInvoicedTime);
                                        String strDueTime = form.getDueTime();
                                        Long dueTime = strDueTime == null ? null : Long.valueOf(strDueTime);
                                        String strPaidTime = form.getPaidTime();
                                        Long paidTime = strPaidTime == null ? null : Long.valueOf(strPaidTime);
                                        String reference = form.getReference();
                                        String description = form.getDescription();

                                        invoice = PurchaseInvoiceLogic.getInstance().createInvoice(session, this, billFrom, billFromContactMechanism, billTo,
                                                billToContactMechanism, currency, term, reference, description, invoicedTime, dueTime, paidTime,
                                                PurchaseInvoiceStatusConstants.WorkflowEntrance_NEW_ENTRY, getPartyPK());
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTermName.name(), termName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
        }
        
        if(invoice != null) {
            result.setEntityRef(invoice.getPrimaryKey().getEntityRef());
            result.setInvoiceName(invoice.getLastDetail().getInvoiceName());
        }
        
        return result;
    }
    
}
