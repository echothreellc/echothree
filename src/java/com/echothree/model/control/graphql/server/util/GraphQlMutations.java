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

package com.echothree.model.control.graphql.server.util;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.CreateContentPageLayoutResult;
import com.echothree.control.user.content.common.result.EditContentPageLayoutResult;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.CreateInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemCategoryResult;
import com.echothree.control.user.item.common.result.EditItemCategoryResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.control.user.party.common.result.CreateCustomerWithLoginResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.CreatePaymentMethodTypeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentMethodTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorTypeResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.SearchCustomersResult;
import com.echothree.control.user.search.server.graphql.SearchCustomersResultObject;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.EditUserLoginResult;
import com.echothree.model.control.graphql.server.graphql.CommandResultObject;
import com.echothree.model.control.graphql.server.graphql.CommandResultWithIdObject;
import com.echothree.util.common.command.EditMode;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.annotationTypes.GraphQLRelayMutation;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import javax.naming.NamingException;

@GraphQLName("mutation")
public class GraphQlMutations {

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorTypeName") final String originalPaymentProcessorTypeName,
            @GraphQLName("id") final String id,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = PaymentUtil.getHome().getPaymentProcessorTypeUniversalSpec();

            spec.setPaymentProcessorTypeName(originalPaymentProcessorTypeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorType(context.getUserVisitPK(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstanceFromEntityRef(result.getPaymentProcessorType().getEntityInstance().getEntityRef());

                if(arguments.containsKey("paymentProcessorTypeName"))
                    edit.setPaymentProcessorTypeName(paymentProcessorTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorType(context.getUserVisitPK(), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetDefaultPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentProcessorType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getCreatePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentMethodType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentMethodTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getDeletePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentMethodType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentMethodTypeName") final String originalPaymentMethodTypeName,
            @GraphQLName("id") final String id,
            @GraphQLName("paymentMethodTypeName") final String paymentMethodTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = PaymentUtil.getHome().getPaymentMethodTypeUniversalSpec();

            spec.setPaymentMethodTypeName(originalPaymentMethodTypeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentMethodTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentMethodType(context.getUserVisitPK(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentMethodTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstanceFromEntityRef(result.getPaymentMethodType().getEntityInstance().getEntityRef());

                if(arguments.containsKey("paymentMethodTypeName"))
                    edit.setPaymentMethodTypeName(paymentMethodTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentMethodType(context.getUserVisitPK(), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetDefaultPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentMethodType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorResultCode(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorResultCodeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorResultCode(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorResultCodeName") final String originalPaymentProcessorResultCodeName,
            @GraphQLName("id") final String id,
            @GraphQLName("paymentProcessorResultCodeName") final String paymentProcessorResultCodeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = PaymentUtil.getHome().getPaymentProcessorResultCodeUniversalSpec();

            spec.setPaymentProcessorResultCodeName(originalPaymentProcessorResultCodeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorResultCodeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(context.getUserVisitPK(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorResultCodeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstanceFromEntityRef(result.getPaymentProcessorResultCode().getEntityInstance().getEntityRef());

                if(arguments.containsKey("paymentProcessorResultCodeName"))
                    edit.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(context.getUserVisitPK(), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetDefaultPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentProcessorResultCode(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorActionType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorActionTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorActionType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorActionTypeName") final String originalPaymentProcessorActionTypeName,
            @GraphQLName("id") final String id,
            @GraphQLName("paymentProcessorActionTypeName") final String paymentProcessorActionTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = PaymentUtil.getHome().getPaymentProcessorActionTypeUniversalSpec();

            spec.setPaymentProcessorActionTypeName(originalPaymentProcessorActionTypeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorActionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(context.getUserVisitPK(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorActionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstanceFromEntityRef(result.getPaymentProcessorActionType().getEntityInstance().getEntityRef());

                if(arguments.containsKey("paymentProcessorActionTypeName"))
                    edit.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(context.getUserVisitPK(), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetDefaultPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentProcessorActionType(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = InventoryUtil.getHome().getCreateInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = InventoryUtil.getHome().createInventoryCondition(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateInventoryConditionResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = InventoryUtil.getHome().getDeleteInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);

            var commandResult = InventoryUtil.getHome().deleteInventoryCondition(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("originalInventoryConditionName") final String originalInventoryConditionName,
            @GraphQLName("id") final String id,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = InventoryUtil.getHome().getInventoryConditionUniversalSpec();

            spec.setInventoryConditionName(originalInventoryConditionName);
            spec.setUlid(id);
            
            var commandForm = InventoryUtil.getHome().getEditInventoryConditionForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryCondition(context.getUserVisitPK(), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditInventoryConditionResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getInventoryCondition().getEntityInstance().getEntityRef());

                if(arguments.containsKey("inventoryConditionName"))
                    edit.setInventoryConditionName(inventoryConditionName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = InventoryUtil.getHome().editInventoryCondition(context.getUserVisitPK(), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetDefaultInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = InventoryUtil.getHome().getSetDefaultInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);

            var commandResult = InventoryUtil.getHome().setDefaultInventoryCondition(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = ContentUtil.getHome().getCreateContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentPageLayout(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentPageLayoutResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = ContentUtil.getHome().getDeleteContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);

            var commandResult = ContentUtil.getHome().deleteContentPageLayout(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("originalContentPageLayoutName") final String originalContentPageLayoutName,
            @GraphQLName("id") final String id,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = ContentUtil.getHome().getContentPageLayoutUniversalSpec();

            spec.setContentPageLayoutName(originalContentPageLayoutName);
            spec.setUlid(id);
            
            var commandForm = ContentUtil.getHome().getEditContentPageLayoutForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentPageLayout(context.getUserVisitPK(), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentPageLayoutResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getContentPageLayout().getEntityInstance().getEntityRef());

                if(arguments.containsKey("contentPageLayoutName"))
                    edit.setContentPageLayoutName(contentPageLayoutName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = ContentUtil.getHome().editContentPageLayout(context.getUserVisitPK(), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetDefaultContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = ContentUtil.getHome().getSetDefaultContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);

            var commandResult = ContentUtil.getHome().setDefaultContentPageLayout(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetUserVisitPreferredLanguage(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredLanguage(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetUserVisitPreferredCurrency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredCurrency(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetUserVisitPreferredTimeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") @GraphQLNonNull final String javaTimeZoneName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredTimeZone(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject setSetUserVisitPreferredDateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") @GraphQLNonNull final String dateTimeFormatName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredDateTimeFormat(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = CoreUtil.getHome().getCreateEntityListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);

            var commandResult = CoreUtil.getHome().createEntityListItemAttribute(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = CoreUtil.getHome().getCreateEntityMultipleListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);

            var commandResult = CoreUtil.getHome().createEntityMultipleListItemAttribute(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchCustomersResultObject searchCustomers(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("customerTypeName") final String customerTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("name") final String name,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("countryName") final String countryName,
            @GraphQLName("areaCode") final String areaCode,
            @GraphQLName("telephoneNumber") final String telephoneNumber,
            @GraphQLName("telephoneExtension") final String telephoneExtension,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince) {
        SearchCustomersResultObject commandResultObject = new SearchCustomersResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = SearchUtil.getHome().getSearchCustomersForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setName(name);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setCountryName(countryName);
            commandForm.setAreaCode(areaCode);
            commandForm.setTelephoneNumber(telephoneNumber);
            commandForm.setTelephoneExtension(telephoneExtension);
            commandForm.setCustomerName(customerName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);

            var commandResult = SearchUtil.getHome().searchCustomers(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
            commandResultObject.setResult(commandResult.hasErrors() ? null : (SearchCustomersResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject clearCustomerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = SearchUtil.getHome().getClearCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearCustomerResults(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyId") final String partyId,
            @GraphQLName("username") final String username,
            @GraphQLName("password1") @GraphQLNonNull final String password1,
            @GraphQLName("password2") @GraphQLNonNull final String password2,
            @GraphQLName("recoveryQuestionName") final String recoveryQuestionName,
            @GraphQLName("answer") final String answer) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = UserUtil.getHome().getCreateUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUlid(partyId);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = UserUtil.getHome().createUserLogin(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyId") final String partyId,
            @GraphQLName("username") @GraphQLNonNull final String username) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = PartyUtil.getHome().getPartyUniversalSpec();

            spec.setPartyName(partyName);
            spec.setUlid(partyId);
            
            var commandForm = UserUtil.getHome().getEditUserLoginForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = UserUtil.getHome().editUserLogin(context.getUserVisitPK(), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUserLoginResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                if(arguments.containsKey("username"))
                    edit.setUsername(username);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = UserUtil.getHome().editUserLogin(context.getUserVisitPK(), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyId") final String partyId) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = UserUtil.getHome().getDeleteUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUlid(partyId);

            commandResultObject.setCommandResult(UserUtil.getHome().deleteUserLogin(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") @GraphQLNonNull final String itemCategoryName,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = ItemUtil.getHome().getCreateItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setParentItemCategoryName(parentItemCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemCategory(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);
            
            if(!commandResult.hasErrors()) {
                var result = (CreateItemCategoryResult)commandResult.getExecutionResult().getResult();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemCategoryName") final String originalItemCategoryName,
            @GraphQLName("id") final String id,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var spec = ItemUtil.getHome().getItemCategoryUniversalSpec();

            spec.setItemCategoryName(originalItemCategoryName);
            spec.setUlid(id);
            
            var commandForm = ItemUtil.getHome().getEditItemCategoryForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemCategory(context.getUserVisitPK(), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemCategoryResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getItemCategory().getEntityInstance().getEntityRef());

                if(arguments.containsKey("itemCategoryName"))
                    edit.setItemCategoryName(itemCategoryName);
                if(arguments.containsKey("parentItemCategoryName"))
                    edit.setParentItemCategoryName(parentItemCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = ItemUtil.getHome().editItemCategory(context.getUserVisitPK(), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = ItemUtil.getHome().getDeleteItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemCategory(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject unlockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = CoreUtil.getHome().getUnlockEntityForm();

            commandForm.setUlid(id);

            commandResultObject.setCommandResult(CoreUtil.getHome().unlockEntity(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject lockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = CoreUtil.getHome().getLockEntityForm();

            commandForm.setUlid(id);

            commandResultObject.setCommandResult(CoreUtil.getHome().lockEntity(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject customerLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = AuthenticationUtil.getHome().getCustomerLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(context.getRemoteInet4Address());

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().customerLogin(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createCustomer(final DataFetchingEnvironment env,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") @GraphQLNonNull final String firstName,
            @GraphQLName("lastName") @GraphQLNonNull final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("initialOfferName") final String initialOfferName,
            @GraphQLName("initialUseName") final String initialUseName,
            @GraphQLName("initialSourceName") final String initialSourceName,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PartyUtil.getHome().getCreateCustomerForm();

            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setInitialOfferName(initialOfferName);
            commandForm.setInitialUseName(initialUseName);
            commandForm.setInitialSourceName(initialSourceName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);

            var commandResult = PartyUtil.getHome().createCustomer(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerResult)commandResult.getExecutionResult().getResult();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createCustomerWithLogin(final DataFetchingEnvironment env,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") @GraphQLNonNull final String firstName,
            @GraphQLName("lastName") @GraphQLNonNull final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("initialOfferName") final String initialOfferName,
            @GraphQLName("initialUseName") final String initialUseName,
            @GraphQLName("initialSourceName") final String initialSourceName,
            @GraphQLName("emailAddress") @GraphQLNonNull final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password1") @GraphQLNonNull final String password1,
            @GraphQLName("password2") @GraphQLNonNull final String password2,
            @GraphQLName("recoveryQuestionName") @GraphQLNonNull final String recoveryQuestionName,
            @GraphQLName("answer") @GraphQLNonNull final String answer) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = PartyUtil.getHome().getCreateCustomerWithLoginForm();

            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setInitialOfferName(initialOfferName);
            commandForm.setInitialUseName(initialUseName);
            commandForm.setInitialSourceName(initialSourceName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = PartyUtil.getHome().createCustomerWithLogin(context.getUserVisitPK(), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerWithLoginResult)commandResult.getExecutionResult().getResult();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject employeeLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = AuthenticationUtil.getHome().getEmployeeLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(context.getRemoteInet4Address());
            commandForm.setCompanyName(companyName);

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().employeeLogin(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject vendorLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = AuthenticationUtil.getHome().getVendorLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(context.getRemoteInet4Address());

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().vendorLogin(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setPassword")
    public static CommandResultObject setPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("oldPassword") final String oldPassword,
            @GraphQLName("newPassword1") @GraphQLNonNull final String newPassword1,
            @GraphQLName("newPassword2") @GraphQLNonNull final String newPassword2) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = AuthenticationUtil.getHome().getSetPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setEmployeeName(employeeName);
            commandForm.setCustomerName(customerName);
            commandForm.setVendorName(vendorName);
            commandForm.setOldPassword(oldPassword);
            commandForm.setNewPassword1(newPassword1);
            commandForm.setNewPassword2(newPassword2);

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().setPassword(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject recoverPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("username") final String username,
            @GraphQLName("answer") final String answer) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();
            var commandForm = AuthenticationUtil.getHome().getRecoverPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setUsername(username);
            commandForm.setAnswer(answer);

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().recoverPassword(context.getUserVisitPK(), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject idle(final DataFetchingEnvironment env) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().idle(context.getUserVisitPK()));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject logout(final DataFetchingEnvironment env) {
        var commandResultObject = new CommandResultObject();

        try {
            GraphQlContext context = env.getContext();

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().logout(context.getUserVisitPK()));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

}
