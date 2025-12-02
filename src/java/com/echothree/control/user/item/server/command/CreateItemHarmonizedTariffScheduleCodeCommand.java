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

import com.echothree.control.user.item.common.form.CreateItemHarmonizedTariffScheduleCodeForm;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateItemHarmonizedTariffScheduleCodeCommand
        extends BaseSimpleCommand<CreateItemHarmonizedTariffScheduleCodeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemHarmonizedTariffScheduleCode.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemHarmonizedTariffScheduleCodeCommand */
    public CreateItemHarmonizedTariffScheduleCodeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = form.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var countryName = form.getCountryName();
            var countryGeoCode = geoControl.getCountryByAlias(countryName);
            
            if(countryGeoCode != null) {
                var harmonizedTariffScheduleCodeUseTypeName = form.getHarmonizedTariffScheduleCodeUseTypeName();
                var harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

                if(harmonizedTariffScheduleCodeUseType != null) {
                    var itemHarmonizedTariffScheduleCode = itemControl.getItemHarmonizedTariffScheduleCode(item, countryGeoCode,
                            harmonizedTariffScheduleCodeUseType);

                    if(itemHarmonizedTariffScheduleCode == null) {
                        var harmonizedTariffScheduleCodeName = form.getHarmonizedTariffScheduleCodeName();
                        var harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(countryGeoCode,
                                harmonizedTariffScheduleCodeName);

                        if(harmonizedTariffScheduleCode != null) {
                            var harmonizedTariffScheduleCodeUse = itemControl.getHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCode,
                                    harmonizedTariffScheduleCodeUseType);
                            
                            if(harmonizedTariffScheduleCodeUse != null) {
                                itemControl.createItemHarmonizedTariffScheduleCode(item, countryGeoCode, harmonizedTariffScheduleCodeUseType,
                                        harmonizedTariffScheduleCode, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUse.name(), countryName, harmonizedTariffScheduleCodeName,
                                        harmonizedTariffScheduleCodeUseTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeName.name(), harmonizedTariffScheduleCodeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateItemHarmonizedTariffScheduleCode.name(), itemName, countryName, harmonizedTariffScheduleCodeUseTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
