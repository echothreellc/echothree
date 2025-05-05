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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.CreatePaymentMethodForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
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

public class CreatePaymentMethodCommand
        extends BaseSimpleCommand<CreatePaymentMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> formCheckFieldDefinitions;
    private final static List<FieldDefinition> formCreditCardFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethod.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SalesOrderItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
        
        formCheckFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HoldDays", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
        
        formCreditCardFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RequestNameOnCard", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequireNameOnCard", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CheckCardNumber", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequestExpirationDate", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequireExpirationDate", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CheckExpirationDate", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequestSecurityCode", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequireSecurityCode", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CardNumberValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("SecurityCodeValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("RetainCreditCard", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RetainSecurityCode", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequestBilling", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequireBilling", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequestIssuer", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequireIssuer", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePaymentMethodCommand */
    public CreatePaymentMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, null, false);
    }
    
    @Override
    protected ValidationResult validate() {
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            var paymentMethodTypeName = form.getPaymentMethodTypeName();
            
            if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
                validationResult = validator.validate(form, formCheckFieldDefinitions);
            } else if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                validationResult = validator.validate(form, formCreditCardFieldDefinitions);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var result = PaymentResultFactory.getCreatePaymentMethodResult();
        var paymentMethodName = form.getPaymentMethodName();
        var paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

        if(paymentMethod == null) {
            var paymentMethodTypeName = form.getPaymentMethodTypeName();
            var paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(this, paymentMethodTypeName);

            if(!hasExecutionErrors()) {
                var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
                var paymentProcessorName = form.getPaymentProcessorName();
                var paymentProcessor = paymentProcessorName == null ? null : paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);

                if(paymentProcessorName == null || paymentProcessor != null) {
                    var selectorControl = Session.getModelController(SelectorControl.class);
                    var itemSelectorName = form.getItemSelectorName();
                    var itemSelector = itemSelectorName == null ? null : selectorControl.getSelectorUsingNames(this, SelectorKinds.ITEM.name(),
                            SelectorTypes.PAYMENT_METHOD.name(), itemSelectorName, ExecutionErrors.UnknownItemSelectorName.name());

                    if(!hasExecutionErrors()) {
                        var salesOrderItemSelectorName = form.getSalesOrderItemSelectorName();
                        var salesOrderItemSelector = salesOrderItemSelectorName == null ? null : selectorControl.getSelectorUsingNames(this,
                                SelectorKinds.SALES_ORDER_ITEM.name(), SelectorTypes.PAYMENT_METHOD.name(), salesOrderItemSelectorName,
                                ExecutionErrors.UnknownSalesOrderItemSelectorName.name());

                        if(!hasExecutionErrors()) {
                            var partyPK = getPartyPK();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

                            paymentMethod = paymentMethodControl.createPaymentMethod(paymentMethodName, paymentMethodType, paymentProcessor, itemSelector, salesOrderItemSelector,
                                    isDefault, sortOrder, partyPK);

                            if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
                                var holdDays = Integer.valueOf(form.getHoldDays());

                                paymentMethodControl.createPaymentMethodCheck(paymentMethod, holdDays, partyPK);
                            } else {
                                if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                                    var requestNameOnCard = Boolean.valueOf(form.getRequestNameOnCard());
                                    var requireNameOnCard = Boolean.valueOf(form.getRequireNameOnCard());
                                    var checkCardNumber = Boolean.valueOf(form.getCheckCardNumber());
                                    var requestExpirationDate = Boolean.valueOf(form.getRequestExpirationDate());
                                    var requireExpirationDate = Boolean.valueOf(form.getRequireExpirationDate());
                                    var checkExpirationDate = Boolean.valueOf(form.getCheckExpirationDate());
                                    var requestSecurityCode = Boolean.valueOf(form.getRequestSecurityCode());
                                    var requireSecurityCode = Boolean.valueOf(form.getRequireSecurityCode());
                                    var cardNumberValidationPattern = form.getCardNumberValidationPattern();
                                    var securityCodeValidationPattern = form.getSecurityCodeValidationPattern();
                                    var retainCreditCard = Boolean.valueOf(form.getRetainCreditCard());
                                    var retainSecurityCode = Boolean.valueOf(form.getRetainSecurityCode());
                                    var requestBilling = Boolean.valueOf(form.getRequestBilling());
                                    var requireBilling = Boolean.valueOf(form.getRequireBilling());
                                    var requestIssuer = Boolean.valueOf(form.getRequestIssuer());
                                    var requireIssuer = Boolean.valueOf(form.getRequireIssuer());

                                    paymentMethodControl.createPaymentMethodCreditCard(paymentMethod, requestNameOnCard, requireNameOnCard,
                                            checkCardNumber, requestExpirationDate, requireExpirationDate, checkExpirationDate, requestSecurityCode,
                                            requireSecurityCode, cardNumberValidationPattern, securityCodeValidationPattern, retainCreditCard,
                                            retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer, partyPK);
                                }
                            }

                            if(description != null) {
                                var language = getPreferredLanguage();

                                paymentMethodControl.createPaymentMethodDescription(paymentMethod, language, description, partyPK);
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPaymentProcessorName.name(), paymentProcessorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePaymentMethodName.name(), paymentMethodName);
        }

        if(paymentMethod != null) {
            result.setEntityRef(paymentMethod.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
