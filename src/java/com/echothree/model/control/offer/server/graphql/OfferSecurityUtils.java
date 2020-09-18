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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.control.user.offer.server.command.*;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import graphql.schema.DataFetchingEnvironment;

public final class OfferSecurityUtils {

    private static class OfferSecurityUtilsHolder {
        static OfferSecurityUtils instance = new OfferSecurityUtils();
    }
    
    public static OfferSecurityUtils getInstance() {
        return OfferSecurityUtilsHolder.instance;
    }

    public boolean getHasOfferAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferCommand.class);
    }

    public boolean getHasOfferNameElementAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferNameElementCommand.class);
    }

    public boolean getHasOfferCustomerTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferCustomerTypeCommand.class);
    }

    public boolean getHasOfferChainTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferChainTypeCommand.class);
    }

    public boolean getHasUseAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetUseCommand.class);
    }

    public boolean getHasUseNameElementAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetUseNameElementCommand.class);
    }

    public boolean getHasOfferUseAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferUseCommand.class);
    }

    public boolean getHasSourceAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetSourceCommand.class);
    }

    public boolean getHasOfferItemAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferItemCommand.class);
    }

    public boolean getHasOfferItemPriceAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetOfferItemPriceCommand.class);
    }

    public boolean getHasUseTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetUseTypeCommand.class);
    }
}
