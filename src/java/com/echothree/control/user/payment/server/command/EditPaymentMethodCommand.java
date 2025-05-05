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

import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.edit.PaymentMethodEdit;
import com.echothree.control.user.payment.common.form.EditPaymentMethodForm;
import com.echothree.control.user.payment.common.result.EditPaymentMethodResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentMethodSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPaymentMethodCommand
        extends BaseAbstractEditCommand<PaymentMethodSpec, PaymentMethodEdit, EditPaymentMethodResult, PaymentMethod, PaymentMethod> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> editCheckFieldDefinitions;
    private final static List<FieldDefinition> editCreditCardFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethod.name(), SecurityRoles.Edit.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SalesOrderItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
        
        editCheckFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HoldDays", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
        
        editCreditCardFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of EditPaymentMethodCommand */
    public EditPaymentMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected ValidationResult validateEdit(Validator validator) {
        var validationResult = validator.validate(edit, getEditFieldDefinitions());
        
        if(!validationResult.getHasErrors()) {
            var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
            var paymentMethod = paymentMethodControl.getPaymentMethodByName(spec.getPaymentMethodName());
            
            if(paymentMethod != null) {
                var paymentMethodTypeName = paymentMethod.getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();
                
                if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
                    validationResult = validator.validate(edit, editCheckFieldDefinitions);
                } else if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                    validationResult = validator.validate(edit, editCreditCardFieldDefinitions);
                }
            }
        }
        
        return validationResult;
    }
    
    @Override
    public EditPaymentMethodResult getResult() {
        return PaymentResultFactory.getEditPaymentMethodResult();
    }

    @Override
    public PaymentMethodEdit getEdit() {
        return PaymentEditFactory.getPaymentMethodEdit();
    }

    @Override
    public PaymentMethod getEntity(EditPaymentMethodResult result) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        PaymentMethod paymentMethod;
        var paymentMethodName = spec.getPaymentMethodName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);
        } else { // EditMode.UPDATE
            paymentMethod = paymentMethodControl.getPaymentMethodByNameForUpdate(paymentMethodName);
        }

        if(paymentMethod != null) {
            result.setPaymentMethod(paymentMethodControl.getPaymentMethodTransfer(getUserVisit(), paymentMethod));
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
        }

        return paymentMethod;
    }

    @Override
    public PaymentMethod getLockEntity(PaymentMethod paymentMethod) {
        return paymentMethod;
    }

    @Override
    public void fillInResult(EditPaymentMethodResult result, PaymentMethod paymentMethod) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

        result.setPaymentMethod(paymentMethodControl.getPaymentMethodTransfer(getUserVisit(), paymentMethod));
    }

    Selector itemSelector = null;
    Selector salesOrderItemSelector = null;

    @Override
    public void doLock(PaymentMethodEdit edit, PaymentMethod paymentMethod) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var paymentMethodDescription = paymentMethodControl.getPaymentMethodDescription(paymentMethod, getPreferredLanguage());
        var paymentMethodDetail = paymentMethod.getLastDetail();
        var paymentMethodTypeName = paymentMethodDetail.getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();

        itemSelector = paymentMethodDetail.getItemSelector();
        salesOrderItemSelector = paymentMethodDetail.getSalesOrderItemSelector();

        edit.setPaymentMethodName(paymentMethodDetail.getPaymentMethodName());
        edit.setItemSelectorName(itemSelector == null? null: itemSelector.getLastDetail().getSelectorName());
        edit.setSalesOrderItemSelectorName(salesOrderItemSelector == null? null: salesOrderItemSelector.getLastDetail().getSelectorName());
        edit.setIsDefault(paymentMethodDetail.getIsDefault().toString());
        edit.setSortOrder(paymentMethodDetail.getSortOrder().toString());

        if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
            var paymentMethodCheck = paymentMethodControl.getPaymentMethodCheck(paymentMethod);

            edit.setHoldDays(paymentMethodCheck.getHoldDays().toString());
        } else if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
            var paymentMethodCreditCard = paymentMethodControl.getPaymentMethodCreditCard(paymentMethod);

            edit.setRequestNameOnCard(paymentMethodCreditCard.getRequestNameOnCard().toString());
            edit.setRequireNameOnCard(paymentMethodCreditCard.getRequireNameOnCard().toString());
            edit.setCheckCardNumber(paymentMethodCreditCard.getCheckCardNumber().toString());
            edit.setRequestExpirationDate(paymentMethodCreditCard.getRequestExpirationDate().toString());
            edit.setRequireExpirationDate(paymentMethodCreditCard.getRequireExpirationDate().toString());
            edit.setCheckExpirationDate(paymentMethodCreditCard.getCheckExpirationDate().toString());
            edit.setRequestSecurityCode(paymentMethodCreditCard.getRequestSecurityCode().toString());
            edit.setRequireSecurityCode(paymentMethodCreditCard.getRequireSecurityCode().toString());
            edit.setCardNumberValidationPattern(paymentMethodCreditCard.getCardNumberValidationPattern());
            edit.setSecurityCodeValidationPattern(paymentMethodCreditCard.getSecurityCodeValidationPattern());
            edit.setRetainCreditCard(paymentMethodCreditCard.getRetainCreditCard().toString());
            edit.setRetainSecurityCode(paymentMethodCreditCard.getRetainSecurityCode().toString());
            edit.setRequestBilling(paymentMethodCreditCard.getRequestBilling().toString());
            edit.setRequireBilling(paymentMethodCreditCard.getRequireBilling().toString());
            edit.setRequestIssuer(paymentMethodCreditCard.getRequestIssuer().toString());
            edit.setRequireIssuer(paymentMethodCreditCard.getRequireIssuer().toString());
        }

        if(paymentMethodDescription != null) {
            edit.setDescription(paymentMethodDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PaymentMethod paymentMethod) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var paymentMethodName = edit.getPaymentMethodName();
        var duplicatePaymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

        if(duplicatePaymentMethod != null && !paymentMethod.equals(duplicatePaymentMethod)) {
            addExecutionError(ExecutionErrors.DuplicatePaymentMethodName.name(), paymentMethodName);
        } else {
            var selectorControl = Session.getModelController(SelectorControl.class);
            var itemSelectorName = edit.getItemSelectorName();
            itemSelector = itemSelectorName == null ? null : selectorControl.getSelectorUsingNames(this, SelectorKinds.ITEM.name(),
                    SelectorTypes.PAYMENT_METHOD.name(), itemSelectorName, ExecutionErrors.UnknownItemSelectorName.name());

            if(!hasExecutionErrors()) {
                var salesOrderItemSelectorName = edit.getSalesOrderItemSelectorName();
                salesOrderItemSelector = salesOrderItemSelectorName == null ? null : selectorControl.getSelectorUsingNames(this,
                        SelectorKinds.SALES_ORDER_ITEM.name(), SelectorTypes.PAYMENT_METHOD.name(), salesOrderItemSelectorName,
                        ExecutionErrors.UnknownSalesOrderItemSelectorName.name());
            }
        }
    }

    @Override
    public void doUpdate(PaymentMethod paymentMethod) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var partyPK = getPartyPK();
        var paymentMethodDetailValue = paymentMethodControl.getPaymentMethodDetailValueForUpdate(paymentMethod);
        var paymentMethodDescription = paymentMethodControl.getPaymentMethodDescriptionForUpdate(paymentMethod, getPreferredLanguage());
        var paymentMethodTypeName = paymentMethod.getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();
        var description = edit.getDescription();

        paymentMethodDetailValue.setPaymentMethodName(edit.getPaymentMethodName());
        paymentMethodDetailValue.setItemSelectorPK(itemSelector == null? null: itemSelector.getPrimaryKey());
        paymentMethodDetailValue.setSalesOrderItemSelectorPK(salesOrderItemSelector == null? null: salesOrderItemSelector.getPrimaryKey());
        paymentMethodDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        paymentMethodDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        paymentMethodControl.updatePaymentMethodFromValue(paymentMethodDetailValue, partyPK);

        if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
            var paymentMethodCheckValue = paymentMethodControl.getPaymentMethodCheckValueForUpdate(paymentMethod);

            paymentMethodCheckValue.setHoldDays(Integer.valueOf(edit.getHoldDays()));

            paymentMethodControl.updatePaymentMethodCheckFromValue(paymentMethodCheckValue, partyPK);
        } else {
            if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                var paymentMethodCreditCardValue = paymentMethodControl.getPaymentMethodCreditCardValueForUpdate(paymentMethod);

                paymentMethodCreditCardValue.setRequestNameOnCard(Boolean.valueOf(edit.getRequestNameOnCard()));
                paymentMethodCreditCardValue.setRequireNameOnCard(Boolean.valueOf(edit.getRequireNameOnCard()));
                paymentMethodCreditCardValue.setCheckCardNumber(Boolean.valueOf(edit.getCheckCardNumber()));
                paymentMethodCreditCardValue.setRequestExpirationDate(Boolean.valueOf(edit.getRequestExpirationDate()));
                paymentMethodCreditCardValue.setRequireExpirationDate(Boolean.valueOf(edit.getRequireExpirationDate()));
                paymentMethodCreditCardValue.setCheckExpirationDate(Boolean.valueOf(edit.getCheckExpirationDate()));
                paymentMethodCreditCardValue.setRequestSecurityCode(Boolean.valueOf(edit.getRequestSecurityCode()));
                paymentMethodCreditCardValue.setRequireSecurityCode(Boolean.valueOf(edit.getRequireSecurityCode()));
                paymentMethodCreditCardValue.setCardNumberValidationPattern(edit.getCardNumberValidationPattern());
                paymentMethodCreditCardValue.setSecurityCodeValidationPattern(edit.getSecurityCodeValidationPattern());
                paymentMethodCreditCardValue.setRetainCreditCard(Boolean.valueOf(edit.getRetainCreditCard()));
                paymentMethodCreditCardValue.setRetainSecurityCode(Boolean.valueOf(edit.getRetainSecurityCode()));
                paymentMethodCreditCardValue.setRequestBilling(Boolean.valueOf(edit.getRequestBilling()));
                paymentMethodCreditCardValue.setRequireBilling(Boolean.valueOf(edit.getRequireBilling()));
                paymentMethodCreditCardValue.setRequestIssuer(Boolean.valueOf(edit.getRequestIssuer()));
                paymentMethodCreditCardValue.setRequireIssuer(Boolean.valueOf(edit.getRequireIssuer()));

                paymentMethodControl.updatePaymentMethodCreditCardFromValue(paymentMethodCreditCardValue, partyPK);
            }
        }

        if(paymentMethodDescription == null && description != null) {
            paymentMethodControl.createPaymentMethodDescription(paymentMethod, getPreferredLanguage(), description, partyPK);
        } else {
            if(paymentMethodDescription != null && description == null) {
                paymentMethodControl.deletePaymentMethodDescription(paymentMethodDescription, partyPK);
            } else {
                if(paymentMethodDescription != null && description != null) {
                    var paymentMethodDescriptionValue = paymentMethodControl.getPaymentMethodDescriptionValue(paymentMethodDescription);

                    paymentMethodDescriptionValue.setDescription(description);
                    paymentMethodControl.updatePaymentMethodDescriptionFromValue(paymentMethodDescriptionValue, partyPK);
                }
            }
        }
    }

}
