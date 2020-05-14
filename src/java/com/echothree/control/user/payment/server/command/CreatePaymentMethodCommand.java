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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.CreatePaymentMethodForm;
import com.echothree.control.user.payment.common.result.CreatePaymentMethodResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.server.control.PaymentControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.selector.server.entity.Selector;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
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
    public CreatePaymentMethodCommand(UserVisitPK userVisitPK, CreatePaymentMethodForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, null, false);
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            String paymentMethodTypeName = form.getPaymentMethodTypeName();
            
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
        var paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        CreatePaymentMethodResult result = PaymentResultFactory.getCreatePaymentMethodResult();
        String paymentMethodName = form.getPaymentMethodName();
        PaymentMethod paymentMethod = paymentControl.getPaymentMethodByName(paymentMethodName);

        if(paymentMethod == null) {
            String paymentMethodTypeName = form.getPaymentMethodTypeName();
            PaymentMethodType paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(this, paymentMethodTypeName);

            if(!hasExecutionErrors()) {
                String paymentProcessorName = form.getPaymentProcessorName();
                PaymentProcessor paymentProcessor = paymentProcessorName == null ? null : paymentControl.getPaymentProcessorByName(paymentProcessorName);

                if(paymentProcessorName == null || paymentProcessor != null) {
                    var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                    String itemSelectorName = form.getItemSelectorName();
                    Selector itemSelector = itemSelectorName == null ? null : selectorControl.getSelectorUsingNames(this, SelectorConstants.SelectorKind_ITEM,
                            SelectorConstants.SelectorType_PAYMENT_METHOD, itemSelectorName, ExecutionErrors.UnknownItemSelectorName.name());

                    if(!hasExecutionErrors()) {
                        String salesOrderItemSelectorName = form.getSalesOrderItemSelectorName();
                        Selector salesOrderItemSelector = salesOrderItemSelectorName == null ? null : selectorControl.getSelectorUsingNames(this,
                                SelectorConstants.SelectorKind_SALES_ORDER_ITEM, SelectorConstants.SelectorType_PAYMENT_METHOD, salesOrderItemSelectorName,
                                ExecutionErrors.UnknownSalesOrderItemSelectorName.name());

                        if(!hasExecutionErrors()) {
                            PartyPK partyPK = getPartyPK();
                            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();

                            paymentMethod = paymentControl.createPaymentMethod(paymentMethodName, paymentMethodType, paymentProcessor, itemSelector, salesOrderItemSelector,
                                    isDefault, sortOrder, partyPK);

                            if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
                                Integer holdDays = Integer.valueOf(form.getHoldDays());

                                paymentControl.createPaymentMethodCheck(paymentMethod, holdDays, partyPK);
                            } else {
                                if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                                    Boolean requestNameOnCard = Boolean.valueOf(form.getRequestNameOnCard());
                                    Boolean requireNameOnCard = Boolean.valueOf(form.getRequireNameOnCard());
                                    Boolean checkCardNumber = Boolean.valueOf(form.getCheckCardNumber());
                                    Boolean requestExpirationDate = Boolean.valueOf(form.getRequestExpirationDate());
                                    Boolean requireExpirationDate = Boolean.valueOf(form.getRequireExpirationDate());
                                    Boolean checkExpirationDate = Boolean.valueOf(form.getCheckExpirationDate());
                                    Boolean requestSecurityCode = Boolean.valueOf(form.getRequestSecurityCode());
                                    Boolean requireSecurityCode = Boolean.valueOf(form.getRequireSecurityCode());
                                    String cardNumberValidationPattern = form.getCardNumberValidationPattern();
                                    String securityCodeValidationPattern = form.getSecurityCodeValidationPattern();
                                    Boolean retainCreditCard = Boolean.valueOf(form.getRetainCreditCard());
                                    Boolean retainSecurityCode = Boolean.valueOf(form.getRetainSecurityCode());
                                    Boolean requestBilling = Boolean.valueOf(form.getRequestBilling());
                                    Boolean requireBilling = Boolean.valueOf(form.getRequireBilling());
                                    Boolean requestIssuer = Boolean.valueOf(form.getRequestIssuer());
                                    Boolean requireIssuer = Boolean.valueOf(form.getRequireIssuer());

                                    paymentControl.createPaymentMethodCreditCard(paymentMethod, requestNameOnCard, requireNameOnCard,
                                            checkCardNumber, requestExpirationDate, requireExpirationDate, checkExpirationDate, requestSecurityCode,
                                            requireSecurityCode, cardNumberValidationPattern, securityCodeValidationPattern, retainCreditCard,
                                            retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer, partyPK);
                                }
                            }

                            if(description != null) {
                                Language language = getPreferredLanguage();

                                paymentControl.createPaymentMethodDescription(paymentMethod, language, description, partyPK);
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
