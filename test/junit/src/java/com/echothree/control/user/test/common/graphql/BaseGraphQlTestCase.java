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

package com.echothree.control.user.test.common.graphql;

public class BaseGraphQlTestCase
        extends GraphQlTestCase {

    protected String getUserSessionParty()
            throws Exception {
        var entityAttributeTypeBody = executeUsingPost("query { userSession { party { id } } }");
        
        return getString(entityAttributeTypeBody, "data.userSession.party.id");
    }

    protected String getDefaultLanguage()
            throws Exception {
        var entityAttributeTypeBody = executeUsingPost("query { language { id } }");
        
        return getString(entityAttributeTypeBody, "data.language.id");
    }

    protected String getDefaultCurrency()
            throws Exception {
        var entityAttributeTypeBody = executeUsingPost("query { currency { id } }");
        
        return getString(entityAttributeTypeBody, "data.currency.id");
    }

    protected String getDefaultTimeZone()
            throws Exception {
        var entityAttributeTypeBody = executeUsingPost("query { timeZone { id } }");
        
        return getString(entityAttributeTypeBody, "data.timeZone.id");
    }

    protected String getDefaultDateTimeFormat()
            throws Exception {
        var entityAttributeTypeBody = executeUsingPost("query { dateTimeFormat { id } }");
        
        return getString(entityAttributeTypeBody, "data.dateTimeFormat.id");
    }

    protected String getEntityAttributeTypeId(String entityAttributeTypeName)
            throws Exception {
        var entityAttributeTypeBody = executeUsingPost("query { entityAttributeType(entityAttributeTypeName: \"" + entityAttributeTypeName + "\") { id } }");
        
        return getString(entityAttributeTypeBody, "data.entityAttributeType.id");
    }
    
}
