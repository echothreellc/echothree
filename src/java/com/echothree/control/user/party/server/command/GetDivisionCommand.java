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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetDivisionForm;
import com.echothree.control.user.party.common.result.GetDivisionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.server.logic.DivisionLogic;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetDivisionCommand
        extends BaseSimpleCommand<GetDivisionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of GetDivisionCommand */
    public GetDivisionCommand(UserVisitPK userVisitPK, GetDivisionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        GetDivisionResult result = PartyResultFactory.getGetDivisionResult();
        String companyName = form.getCompanyName();
        PartyCompany partyCompany = CompanyLogic.getInstance().getPartyCompanyByName(this, companyName, null, false);

        if(!hasExecutionErrors()) {
            String divisionName = form.getDivisionName();
            String partyName = form.getPartyName();
            PartyDivision partyDivision = DivisionLogic.getInstance().getPartyDivisionByName(this, partyCompany == null ? null : partyCompany.getParty(), divisionName, partyName, true);

            if(!hasExecutionErrors()) {
                var partyControl = Session.getModelController(PartyControl.class);
                result.setDivision(partyControl.getDivisionTransfer(getUserVisit(), partyDivision));

                sendEventUsingNames(partyDivision.getPartyPK(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        }

        return result;
    }

}
