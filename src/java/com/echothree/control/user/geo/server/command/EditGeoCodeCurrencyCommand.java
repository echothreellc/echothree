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

import com.echothree.control.user.geo.common.edit.GeoCodeCurrencyEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.result.EditGeoCodeCurrencyResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeCurrencySpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditGeoCodeCurrencyCommand
        extends BaseAbstractEditCommand<GeoCodeCurrencySpec, GeoCodeCurrencyEdit, EditGeoCodeCurrencyResult, GeoCodeCurrency, GeoCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeCurrency.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    GeoCodeLogic geoCodeLogic;

    /** Creates a new instance of EditGeoCodeCurrencyCommand */
    public EditGeoCodeCurrencyCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeCurrencyResult getResult() {
        return GeoResultFactory.getEditGeoCodeCurrencyResult();
    }

    @Override
    public GeoCodeCurrencyEdit getEdit() {
        return GeoEditFactory.getGeoCodeCurrencyEdit();
    }

    @Override
    public GeoCodeCurrency getEntity(EditGeoCodeCurrencyResult result) {
        GeoCodeCurrency geoCodeCurrency = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(this, geoCodeName);

        if(!hasExecutionErrors()) {
            var currencyIsoName = spec.getCurrencyIsoName();
            var currency = currencyLogic.getCurrencyByName(this, currencyIsoName);

            if(!hasExecutionErrors()) {
                geoCodeCurrency = geoControl.getGeoCodeCurrency(geoCode, currency, editModeToEntityPermission(editMode));

                if(geoCodeCurrency == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeCurrency.name(), geoCodeName, currencyIsoName);
                }
            }
        }

        return geoCodeCurrency;
    }

    @Override
    public GeoCode getLockEntity(GeoCodeCurrency geoCodeCurrency) {
        return geoCodeCurrency.getGeoCode();
    }

    @Override
    public void fillInResult(EditGeoCodeCurrencyResult result, GeoCodeCurrency geoCodeCurrency) {
        result.setGeoCodeCurrency(geoControl.getGeoCodeCurrencyTransfer(getUserVisit(), geoCodeCurrency));
    }

    @Override
    public void doLock(GeoCodeCurrencyEdit edit, GeoCodeCurrency geoCodeCurrency) {
        edit.setIsDefault(geoCodeCurrency.getIsDefault().toString());
        edit.setSortOrder(geoCodeCurrency.getSortOrder().toString());
    }

    @Override
    public void doUpdate(GeoCodeCurrency geoCodeCurrency) {
        var geoCodeCurrencyValue = geoControl.getGeoCodeCurrencyValue(geoCodeCurrency);

        geoCodeCurrencyValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeCurrencyValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, getPartyPK());
    }

}
