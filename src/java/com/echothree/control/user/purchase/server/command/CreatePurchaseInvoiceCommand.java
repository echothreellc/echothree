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

import com.echothree.control.user.purchase.common.form.CreatePurchaseInvoiceForm;
import com.echothree.control.user.purchase.common.result.PurchaseResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.invoice.common.workflow.PurchaseInvoiceStatusConstants;
import com.echothree.model.control.invoice.server.logic.PurchaseInvoiceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.party.server.entity.Party;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreatePurchaseInvoiceCommand
        extends BaseSimpleCommand<CreatePurchaseInvoiceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Reference", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreatePurchaseInvoiceCommand */
    public CreatePurchaseInvoiceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = PurchaseResultFactory.getCreatePurchaseInvoiceResult();
        Invoice invoice = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = form.getCurrencyIsoName();
        var currency = currencyIsoName == null ? null : accountingControl.getCurrencyByIsoName(currencyIsoName);

        if(currencyIsoName == null || currency != null) {
            var termName = form.getTermName();
            var freeOnBoardName = form.getFreeOnBoardName();
            var term = termName == null ? null : TermLogic.getInstance().getTermByName(this, termName);
            var freeOnBoard = freeOnBoardName == null ? null : FreeOnBoardLogic.getInstance().getFreeOnBoardByName(this, freeOnBoardName);

            if(!hasExecutionErrors()) {
                var vendorControl = Session.getModelController(VendorControl.class);
                var vendorName = form.getVendorName();
                var billFromPartyName = form.getBillFromPartyName();
                var parameterCount = (vendorName == null ? 0 : 1) + (billFromPartyName == null ? 0 : 1);

                if(parameterCount == 1) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    Party billFrom = null;
                    
                    if(vendorName == null) {
                        billFrom = partyControl.getPartyByName(billFromPartyName);
                        
                        if(billFrom != null) {
                            if(!billFrom.getLastDetail().getPartyType().getPartyTypeName().equals(PartyTypes.VENDOR.name())) {
                                addExecutionError(ExecutionErrors.InvalidBillFromPartyType.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownBillFromPartyName.name(), billFromPartyName);
                        }
                    } else {
                        var vendor = vendorControl.getVendorByName(vendorName);
                        
                        if(vendor != null) {
                            billFrom = vendor.getParty();
                        } else {
                            addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                        }
                    }
                    
                    if(!hasExecutionErrors()) {
                        var contactControl = Session.getModelController(ContactControl.class);
                        var billFromContactMechanismName = form.getBillFromContactMechanismName();
                        var billFromContactMechanism = billFromContactMechanismName == null? null: contactControl.getPartyContactMechanismByContactMechanismName(this, billFrom, billFromContactMechanismName);
                        
                        if(billFromContactMechanismName == null || billFromContactMechanism != null) {
                            var companyName = form.getCompanyName();
                            var billToPartyName = form.getBillToPartyName();

                            parameterCount = (companyName == null ? 0 : 1) + (billToPartyName == null ? 0 : 1);

                            if(parameterCount < 2) {
                                Party billTo = null;

                                if(companyName != null) {
                                    var partyCompany = partyControl.getPartyCompanyByName(companyName);

                                    if(partyCompany != null) {
                                        billTo = partyCompany.getParty();
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                                    }
                                } else if(billToPartyName != null) {
                                    billTo = partyControl.getPartyByName(billToPartyName);

                                    if(billTo != null) {
                                        if(!billTo.getLastDetail().getPartyType().getPartyTypeName().equals(PartyTypes.COMPANY.name())) {
                                            addExecutionError(ExecutionErrors.InvalidBillToPartyType.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownBillToPartyName.name(), billToPartyName);
                                    }
                                } else {
                                    billTo = getUserSession().getPartyRelationship().getFromParty();
                                }

                                if(!hasExecutionErrors()) {
                                    var billToContactMechanismName = form.getBillToContactMechanismName();
                                    var billToContactMechanism = billToContactMechanismName == null ? null : contactControl.getPartyContactMechanismByContactMechanismName(this, billTo, billToContactMechanismName);
                                    
                                    if(billToContactMechanismName == null || billToContactMechanism != null) {
                                        var strInvoicedTime = form.getInvoicedTime();
                                        var invoicedTime = strInvoicedTime == null ? null : Long.valueOf(strInvoicedTime);
                                        var strDueTime = form.getDueTime();
                                        var dueTime = strDueTime == null ? null : Long.valueOf(strDueTime);
                                        var strPaidTime = form.getPaidTime();
                                        var paidTime = strPaidTime == null ? null : Long.valueOf(strPaidTime);
                                        var reference = form.getReference();
                                        var description = form.getDescription();

                                        invoice = PurchaseInvoiceLogic.getInstance().createInvoice(session, this, billFrom, billFromContactMechanism, billTo,
                                                billToContactMechanism, currency, term, freeOnBoard, reference, description, invoicedTime, dueTime, paidTime,
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
