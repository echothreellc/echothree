// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GraphQLDescription("company object")
@GraphQLName("Company")
public class CompanyObject
        extends BasePartyObject {

    public CompanyObject(Party party) {
        super(party);
    }

    public CompanyObject(PartyCompany partyCompany) {
        super(partyCompany.getParty());

        this.partyCompany = partyCompany;
    }

    private PartyCompany partyCompany;  // Optional, use getPartyCompany()

    protected PartyCompany getPartyCompany() {
        if(partyCompany == null) {
            var partyControl = Session.getModelController(PartyControl.class);

            partyCompany = partyControl.getPartyCompany(party);
        }

        return partyCompany;
    }

    @GraphQLField
    @GraphQLDescription("company name")
    @GraphQLNonNull
    public String getCompanyName() {
        return getPartyCompany().getPartyCompanyName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPartyCompany().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPartyCompany().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("divisions")
    public List<DivisionObject> getDivisions(final DataFetchingEnvironment env) {
        if(PartySecurityUtils.getInstance().getHasDivisionsAccess(env)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var entities = partyControl.getDivisionsByCompany(party);

            return entities.stream().map(DivisionObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("division count")
    public Long getDivisionCount(final DataFetchingEnvironment env) {
        if(PartySecurityUtils.getInstance().getHasDivisionsAccess(env)) {
            var partyControl = Session.getModelController(PartyControl.class);

            return partyControl.countPartyDivisions(party);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("items")
    public List<ItemObject> getItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getInstance().getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var entities = itemControl.getItemsByCompanyParty(party);

            return entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("item count")
    public Long getItemCount(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getInstance().getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);

            return itemControl.countItemsByCompanyParty(party);
        } else {
            return null;
        }
    }

}
