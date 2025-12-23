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

package com.echothree.model.control.graphql.server.util;

import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import graphql.schema.DataFetchingEnvironment;

public interface BaseGraphQl {

    String GRAPHQL_EXECUTION_CONTEXT = "com.echothree.model.control.graphql.server.util.GraphQlExecutionContext";

    static GraphQlExecutionContext getGraphQlExecutionContext(final DataFetchingEnvironment env) {
        return env.getGraphQlContext().get(GRAPHQL_EXECUTION_CONTEXT);
    }

    static UserVisitPK getUserVisitPK(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getUserVisitPK();
    }

    static UserVisit getUserVisit(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getUserVisit();
    }

    static UserSession getUserSession(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getUserSession();
    }

    static String getRemoteInet4Address(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getRemoteInet4Address();
    }

    static Language getLanguageEntity(final DataFetchingEnvironment env) {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.getPreferredLanguageFromUserVisit(getUserVisit(env));
    }

    static Currency getCurrencyEntity(final DataFetchingEnvironment env) {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.getPreferredCurrencyFromUserVisit(getUserVisit(env));
    }

    static TimeZone getTimeZoneEntity(final DataFetchingEnvironment env) {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.getPreferredTimeZoneFromUserVisit(getUserVisit(env));
    }

    static java.util.TimeZone getJavaTimeZone(final DataFetchingEnvironment env) {
        return java.util.TimeZone.getTimeZone(getTimeZoneEntity(env).getLastDetail().getJavaTimeZoneName());
    }

    static DateTimeFormat getDateTimeFormatEntity(final DataFetchingEnvironment env) {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.getPreferredDateTimeFormatFromUserVisit(getUserVisit(env));
    }

}
