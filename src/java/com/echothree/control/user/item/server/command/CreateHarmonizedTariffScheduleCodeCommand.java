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

import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeForm;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
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
public class CreateHarmonizedTariffScheduleCodeCommand
        extends BaseSimpleCommand<CreateHarmonizedTariffScheduleCodeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCode.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.HARMONIZED_TARIFF_SCHEDULE_CODE, true, null, null),
                new FieldDefinition("FirstHarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SecondHarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateHarmonizedTariffScheduleCodeCommand */
    public CreateHarmonizedTariffScheduleCodeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        var countryName = form.getCountryName();
        var geoCode = geoControl.getCountryByAlias(countryName);
        
        if(geoCode != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var harmonizedTariffScheduleCodeName = form.getHarmonizedTariffScheduleCodeName();
            var harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(geoCode, harmonizedTariffScheduleCodeName);
            
            if(harmonizedTariffScheduleCode == null) {
                var firstHarmonizedTariffScheduleCodeUnitName = form.getFirstHarmonizedTariffScheduleCodeUnitName();
                var firstHarmonizedTariffScheduleCodeUnit = firstHarmonizedTariffScheduleCodeUnitName == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitByName(firstHarmonizedTariffScheduleCodeUnitName);
                
                if(firstHarmonizedTariffScheduleCodeUnitName == null || firstHarmonizedTariffScheduleCodeUnit != null) {
                    var secondHarmonizedTariffScheduleCodeUnitName = form.getSecondHarmonizedTariffScheduleCodeUnitName();
                    var secondHarmonizedTariffScheduleCodeUnit = secondHarmonizedTariffScheduleCodeUnitName == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitByName(secondHarmonizedTariffScheduleCodeUnitName);

                    if(secondHarmonizedTariffScheduleCodeUnitName == null || secondHarmonizedTariffScheduleCodeUnit != null) {
                        var overview = form.getOverview();
                        var overviewMimeType = MimeTypeLogic.getInstance().checkMimeType(this, form.getOverviewMimeTypeName(), overview, MimeTypeUsageTypes.TEXT.name(),
                                ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                                ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());

                        if(!hasExecutionErrors()) {
                            var partyPK = getPartyPK();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

                            harmonizedTariffScheduleCode = itemControl.createHarmonizedTariffScheduleCode(geoCode, harmonizedTariffScheduleCodeName,
                                    firstHarmonizedTariffScheduleCodeUnit, secondHarmonizedTariffScheduleCodeUnit, isDefault, sortOrder, partyPK);

                            if(description != null) {
                                itemControl.createHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, getPreferredLanguage(), description,
                                        overviewMimeType, overview, partyPK);
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSecondHarmonizedTariffScheduleCodeUnitName.name(), secondHarmonizedTariffScheduleCodeUnitName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFirstHarmonizedTariffScheduleCodeUnitName.name(), firstHarmonizedTariffScheduleCodeUnitName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateHarmonizedTariffScheduleCodeName.name(), countryName, harmonizedTariffScheduleCodeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }
        
        return null;
    }
    
}
