// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

public final class OfferSecurityUtils
        extends BaseGraphQl {

    private static class OfferSecurityUtilsHolder {
        static OfferSecurityUtils instance = new OfferSecurityUtils();
    }
    
    public static OfferSecurityUtils getInstance() {
        return OfferSecurityUtilsHolder.instance;
    }

    public boolean getHasOfferAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferCommand.class);
    }

    public boolean getHasOffersAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOffersCommand.class);
    }

    public boolean getHasOfferNameElementAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferNameElementCommand.class);
    }

    public boolean getHasOfferCustomerTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferCustomerTypeCommand.class);
    }

    public boolean getHasOfferChainTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferChainTypeCommand.class);
    }

    public boolean getHasUseAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetUseCommand.class);
    }

    public boolean getHasUseNameElementAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetUseNameElementCommand.class);
    }

    public boolean getHasOfferUseAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferUseCommand.class);
    }

    public boolean getHasSourceAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetSourceCommand.class);
    }

    public boolean getHasOfferItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferItemCommand.class);
    }

    public boolean getHasOfferItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferItemsCommand.class);
    }

    public boolean getHasOfferItemPriceAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferItemPriceCommand.class);
    }

    public boolean getHasOfferItemPricesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOfferItemPricesCommand.class);
    }

    public boolean getHasUseTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetUseTypeCommand.class);
    }
}
