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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.GetGeoCodeScopeForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeScopeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetGeoCodeScopeCommand
        extends BaseSingleEntityCommand<GeoCodeScope, GetGeoCodeScopeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeScope.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetGeoCodeScopeCommand */
    public GetGeoCodeScopeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected GeoCodeScope getEntity() {
        var geoCodeScope = GeoCodeScopeLogic.getInstance().getGeoCodeScopeByUniversalSpec(this, form, true);

        if(geoCodeScope != null) {
            sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return geoCodeScope;
    }

    @Override
    protected BaseResult getResult(GeoCodeScope entity) {
        var geoControl = Session.getModelController(GeoControl.class);
        var result = GeoResultFactory.getGetGeoCodeScopeResult();

        if(entity != null) {
            result.setGeoCodeScope(geoControl.getGeoCodeScopeTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
