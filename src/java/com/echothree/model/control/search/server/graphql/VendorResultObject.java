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

package com.echothree.model.control.search.server.graphql;

import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.data.party.server.entity.Party;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("vendor result object")
@GraphQLName("VendorResult")
public class VendorResultObject {
    
    private final Party party;
    
    public VendorResultObject(Party party) {
        this.party = party;
    }
    
    @GraphQLField
    @GraphQLDescription("vendor")
    @GraphQLNonNull
    public PartyObject getVendor() {
        return new PartyObject(party);
    }
    
}
