// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.payment.server.graphql;

import com.echothree.control.user.payment.server.command.GetPaymentMethodCommand;
import com.echothree.control.user.payment.server.command.GetPaymentMethodTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentMethodTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentMethodsCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorActionTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorResultCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionCodesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionsCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorsCommand;
import com.echothree.control.user.shipping.server.command.GetShippingMethodCommand;
import com.echothree.control.user.shipping.server.command.GetShippingMethodsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface PaymentSecurityUtils {

    static boolean getHasPaymentMethodTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentMethodTypeCommand.class);
    }

    static boolean getHasPaymentMethodTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentMethodTypesCommand.class);
    }

    static boolean getHasPaymentMethodAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentMethodCommand.class);
    }

    static boolean getHasPaymentMethodsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentMethodsCommand.class);
    }

    static boolean getHasPaymentProcessorTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTypeCommand.class);
    }

    static boolean getHasPaymentProcessorTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTypesCommand.class);
    }

    static boolean getHasPaymentProcessorAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorCommand.class);
    }

    static boolean getHasPaymentProcessorsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorsCommand.class);
    }

    static boolean getHasPaymentProcessorTransactionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTransactionsCommand.class);
    }

    static boolean getHasPaymentProcessorTransactionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTransactionCommand.class);
    }

    static boolean getHasPaymentProcessorTypeCodeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTypeCodeCommand.class);
    }

    static boolean getHasPaymentProcessorTransactionCodesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTransactionCodesCommand.class);
    }

    static boolean getHasPaymentProcessorActionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorActionTypeCommand.class);
    }

    static boolean getHasPaymentProcessorResultCodeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorResultCodeCommand.class);
    }

    static boolean getHasPaymentProcessorTypeCodeTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTypeCodeTypeCommand.class);
    }

}
