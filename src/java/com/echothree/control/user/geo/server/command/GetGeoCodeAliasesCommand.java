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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.GetGeoCodeAliasesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.factory.GeoCodeAliasFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetGeoCodeAliasesCommand
        extends BasePaginatedMultipleEntitiesCommand<GeoCodeAlias, GetGeoCodeAliasesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeAlias.name(), SecurityRoles.List.name())
                        ))
                ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null)
                );
    }

    /** Creates a new instance of GetGeoCodeAliasesCommand */
    public GetGeoCodeAliasesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    GeoControl geoControl;

    GeoCode geoCode;

    @Override
    protected void handleForm() {
        var geoCodeName = form.getGeoCodeName();

        geoCode = geoControl.getGeoCodeByName(geoCodeName);

        if(geoCode == null) {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return geoCode == null ? null : geoControl.countGeoCodeAliasesByGeoCode(geoCode);
    }

    @Override
    protected Collection<GeoCodeAlias> getEntities() {
        return geoCode == null ? null : geoControl.getGeoCodeAliasesByGeoCode(geoCode);
    }

    @Override
    protected BaseResult getResult(Collection<GeoCodeAlias> entities) {
        var result = GeoResultFactory.getGetGeoCodeAliasesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setGeoCode(geoControl.getGeoCodeTransfer(userVisit, geoCode));

            if(session.hasLimit(GeoCodeAliasFactory.class)) {
                result.setGeoCodeAliasCount(getTotalEntities());
            }

            result.setGeoCodeAliases(geoControl.getGeoCodeAliasTransfers(userVisit, entities));
        }

        return result;
    }

}
