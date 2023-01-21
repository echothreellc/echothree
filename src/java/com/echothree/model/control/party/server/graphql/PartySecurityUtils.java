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

package com.echothree.model.control.party.server.graphql;

import com.echothree.control.user.customer.server.command.GetCustomerCommand;
import com.echothree.control.user.employee.server.command.GetEmployeeCommand;
import com.echothree.control.user.party.server.command.GetCompanyCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetDepartmentCommand;
import com.echothree.control.user.party.server.command.GetDepartmentsCommand;
import com.echothree.control.user.party.server.command.GetDivisionCommand;
import com.echothree.control.user.party.server.command.GetDivisionsCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetPartyRelationshipCommand;
import com.echothree.control.user.party.server.command.GetPartyRelationshipsCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.control.user.vendor.server.command.GetVendorCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.control.GraphQlSecurityCommand;
import graphql.schema.DataFetchingEnvironment;

public final class PartySecurityUtils
        extends BaseGraphQl {

    private static class PartySecurityUtilsHolder {
        static PartySecurityUtils instance = new PartySecurityUtils();
    }
    
    public static PartySecurityUtils getInstance() {
        return PartySecurityUtilsHolder.instance;
    }

    public boolean getHasPartyRelationshipAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetPartyRelationshipCommand.class);
    }

    public boolean getHasPartyRelationshipsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetPartyRelationshipsCommand.class);
    }

    public boolean getHasLanguageAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetLanguageCommand.class);
    }

    public boolean getHasTimeZoneAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTimeZoneCommand.class);
    }

    public boolean getHasDateTimeFormatAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetDateTimeFormatCommand.class);
    }

    public boolean getHasDivisionsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetDivisionsCommand.class);
    }

    public boolean getHasDepartmentsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetDepartmentsCommand.class);
    }

    public boolean getHasPartyAccess(final DataFetchingEnvironment env, final Party party) {
        var partyTypeEnum = PartyTypes.valueOf(party.getLastDetail().getPartyType().getPartyTypeName());

        Class<? extends GraphQlSecurityCommand> command = switch(partyTypeEnum) {
            case CUSTOMER -> GetCustomerCommand.class;
            case EMPLOYEE -> GetEmployeeCommand.class;
            case VENDOR -> GetVendorCommand.class;
            case COMPANY -> GetCompanyCommand.class;
            case DIVISION -> GetDivisionCommand.class;
            case DEPARTMENT -> GetDepartmentCommand.class;
            default -> throw new RuntimeException("Unhandled PartyType");
        };

        return getGraphQlExecutionContext(env).hasAccess(command);
    }

}
