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

import com.echothree.control.user.geo.common.edit.GeoCodeCurrencyEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeCurrencyForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeCurrencySpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditGeoCodeCurrencyCommand
        extends BaseEditCommand<GeoCodeCurrencySpec, GeoCodeCurrencyEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeCurrency.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditGeoCodeCurrencyCommand */
    public EditGeoCodeCurrencyCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        var result = GeoResultFactory.getEditGeoCodeCurrencyResult();
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var currencyIsoName = spec.getCurrencyIsoName();
            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var geoCodeCurrency = geoControl.getGeoCodeCurrency(geoCode, currency);
                    
                    if(geoCodeCurrency != null) {
                        result.setGeoCodeCurrency(geoControl.getGeoCodeCurrencyTransfer(getUserVisit(), geoCodeCurrency));
                        
                        if(lockEntity(geoCode)) {
                            var edit = GeoEditFactory.getGeoCodeCurrencyEdit();
                            
                            result.setEdit(edit);
                            edit.setIsDefault(geoCodeCurrency.getIsDefault().toString());
                            edit.setSortOrder(geoCodeCurrency.getSortOrder().toString());
                            
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeCurrency.name(), geoCodeName, currencyIsoName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var geoCodeCurrency = geoControl.getGeoCodeCurrencyForUpdate(geoCode, currency);
                    
                    if(geoCodeCurrency != null) {
                        var geoCodeCurrencyValue = geoControl.getGeoCodeCurrencyValue(geoCodeCurrency);
                        
                        if(lockEntityForUpdate(geoCode)) {
                            try {
                                geoCodeCurrencyValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                geoCodeCurrencyValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                geoControl.updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, getPartyPK());
                            } finally {
                                unlockEntity(geoCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeCurrency.name(), geoCodeName, currencyIsoName);
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setGeoCodeCurrency(geoControl.getGeoCodeCurrencyTransfer(getUserVisit(), geoCodeCurrency));
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return result;
    }
    
}
