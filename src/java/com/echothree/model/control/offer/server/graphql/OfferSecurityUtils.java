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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.control.user.offer.server.command.GetOfferChainTypeCommand;
import com.echothree.control.user.offer.server.command.GetOfferCommand;
import com.echothree.control.user.offer.server.command.GetOfferCustomerTypeCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemPriceCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemPricesCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemsCommand;
import com.echothree.control.user.offer.server.command.GetOfferNameElementCommand;
import com.echothree.control.user.offer.server.command.GetOfferUseCommand;
import com.echothree.control.user.offer.server.command.GetOffersCommand;
import com.echothree.control.user.offer.server.command.GetSourceCommand;
import com.echothree.control.user.offer.server.command.GetUseCommand;
import com.echothree.control.user.offer.server.command.GetUseNameElementCommand;
import com.echothree.control.user.offer.server.command.GetUseTypeCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface OfferSecurityUtils {

    static boolean getHasOfferAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferCommand.class);
    }

    static boolean getHasOffersAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOffersCommand.class);
    }

    static boolean getHasOfferNameElementAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferNameElementCommand.class);
    }

    static boolean getHasOfferCustomerTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferCustomerTypeCommand.class);
    }

    static boolean getHasOfferChainTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferChainTypeCommand.class);
    }

    static boolean getHasUseAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUseCommand.class);
    }

    static boolean getHasUseNameElementAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUseNameElementCommand.class);
    }

    static boolean getHasOfferUseAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferUseCommand.class);
    }

    static boolean getHasSourceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSourceCommand.class);
    }

    static boolean getHasOfferItemAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferItemCommand.class);
    }

    static boolean getHasOfferItemsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferItemsCommand.class);
    }

    static boolean getHasOfferItemPriceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferItemPriceCommand.class);
    }

    static boolean getHasOfferItemPricesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOfferItemPricesCommand.class);
    }

    static boolean getHasUseTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUseTypeCommand.class);
    }
}
