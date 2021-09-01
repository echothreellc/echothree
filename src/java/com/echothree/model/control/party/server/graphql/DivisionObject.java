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

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GraphQLDescription("division object")
@GraphQLName("Division")
public class DivisionObject
        extends BasePartyObject {

    public DivisionObject(Party party) {
        super(party);
    }

    public DivisionObject(PartyDivision partyDivision) {
        super(partyDivision.getParty());

        this.partyDivision = partyDivision;
    }

    private PartyDivision partyDivision;  // Optional, use getPartyDivision()

    protected PartyDivision getPartyDivision() {
        if(partyDivision == null) {
            var partyControl = Session.getModelController(PartyControl.class);

            partyDivision = partyControl.getPartyDivision(party);
        }

        return partyDivision;
    }
    @GraphQLField
    @GraphQLDescription("company")
    public CompanyObject getCompany(final DataFetchingEnvironment env) {
        var companyParty = getPartyDivision().getCompanyParty();

        return PartySecurityUtils.getInstance().getHasPartyAccess(env, companyParty) ? new CompanyObject(companyParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("division name")
    @GraphQLNonNull
    public String getDivisionName() {
        return getPartyDivision().getPartyDivisionName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPartyDivision().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPartyDivision().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("departments")
    public List<DepartmentObject> getDepartments(final DataFetchingEnvironment env) {
        if(PartySecurityUtils.getInstance().getHasDepartmentsAccess(env)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var entities = partyControl.getDepartmentsByDivision(party);

            return entities.stream().map(DepartmentObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("department count")
    public Long getDepartmentCount(final DataFetchingEnvironment env) {
        if(PartySecurityUtils.getInstance().getHasDepartmentsAccess(env)) {
            var partyControl = Session.getModelController(PartyControl.class);

            return partyControl.countPartyDepartments(party);
        } else {
            return null;
        }
    }

}
