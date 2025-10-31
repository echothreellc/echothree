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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeUsesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetHarmonizedTariffScheduleCodeUsesCommand
        extends BaseSimpleCommand<GetHarmonizedTariffScheduleCodeUsesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCode.name(), SecurityRoles.HarmonizedTariffScheduleCodeUse.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetHarmonizedTariffScheduleCodeUsesCommand */
    public GetHarmonizedTariffScheduleCodeUsesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getGetHarmonizedTariffScheduleCodeUsesResult();
        var countryName = form.getCountryName();
        var harmonizedTariffScheduleCodeName = form.getHarmonizedTariffScheduleCodeName();
        var harmonizedTariffScheduleCodeUseTypeName = form.getHarmonizedTariffScheduleCodeUseTypeName();
        var parameterCount = (countryName == null  && harmonizedTariffScheduleCodeName == null && harmonizedTariffScheduleCodeUseTypeName != null ? 0 : 1)
                + (countryName != null  && harmonizedTariffScheduleCodeName != null && harmonizedTariffScheduleCodeUseTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            var itemControl = Session.getModelController(ItemControl.class);

            if(harmonizedTariffScheduleCodeUseTypeName != null) {
                var harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

                if(harmonizedTariffScheduleCodeUseType != null) {
                    result.setHarmonizedTariffScheduleCodeUseType(itemControl.getHarmonizedTariffScheduleCodeUseTypeTransfer(getUserVisit(), harmonizedTariffScheduleCodeUseType));
                    result.setHarmonizedTariffScheduleCodeUses(itemControl.getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCodeUseType(getUserVisit(), harmonizedTariffScheduleCodeUseType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
                }
            } else {
                var geoControl = Session.getModelController(GeoControl.class);
                var geoCode = geoControl.getCountryByAlias(countryName);

                if(geoCode != null) {
                    var harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(geoCode, harmonizedTariffScheduleCodeName);

                    if(harmonizedTariffScheduleCode != null) {
                    result.setHarmonizedTariffScheduleCode(itemControl.getHarmonizedTariffScheduleCodeTransfer(getUserVisit(), harmonizedTariffScheduleCode));
                    result.setHarmonizedTariffScheduleCodeUses(itemControl.getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(getUserVisit(), harmonizedTariffScheduleCode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeName.name(), countryName, harmonizedTariffScheduleCodeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
