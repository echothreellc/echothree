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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.DivisionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditDivisionForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.DivisionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditDivisionCommand
        extends BaseEditCommand<DivisionSpec, DivisionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Name", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditDivisionCommand */
    public EditDivisionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditDivisionResult();
        var companyName = spec.getCompanyName();
        var partyCompany = partyControl.getPartyCompanyByName(companyName);
        
        if(partyCompany != null) {
            var originalDivisionName = spec.getDivisionName();
            var partyCompanyParty = partyCompany.getParty();
            var partyDivision = partyControl.getPartyDivisionByNameForUpdate(partyCompanyParty, originalDivisionName);
            
            if(partyDivision != null) {
                var party = partyDivision.getParty();
                
                if(editMode.equals(EditMode.LOCK)) {
                    result.setDivision(partyControl.getDivisionTransfer(getUserVisit(), partyDivision));
                    
                    if(lockEntity(party)) {
                        var edit = PartyEditFactory.getDivisionEdit();
                        var partyDetail = party.getLastDetail();
                        var partyGroup = partyControl.getPartyGroup(party);
                        var preferredLanguage = partyDetail.getPreferredLanguage();
                        var preferredCurrency = partyDetail.getPreferredCurrency();
                        var preferredTimeZone = partyDetail.getPreferredTimeZone();
                        var preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                        
                        result.setEdit(edit);
                        edit.setDivisionName(partyDivision.getPartyDivisionName());
                        edit.setName(partyGroup.getName());
                        edit.setPreferredLanguageIsoName(preferredLanguage == null? null: preferredLanguage.getLanguageIsoName());
                        edit.setPreferredCurrencyIsoName(preferredCurrency == null? null: preferredCurrency.getCurrencyIsoName());
                        edit.setPreferredJavaTimeZoneName(preferredTimeZone == null? null: preferredTimeZone.getLastDetail().getJavaTimeZoneName());
                        edit.setPreferredDateTimeFormatName(preferredDateTimeFormat == null? null: preferredDateTimeFormat.getLastDetail().getDateTimeFormatName());
                        edit.setIsDefault(partyDivision.getIsDefault().toString());
                        edit.setSortOrder(partyDivision.getSortOrder().toString());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(party));
                } else if(editMode.equals(EditMode.ABANDON)) {
                    unlockEntity(party);
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var partyDivisionValue = partyControl.getPartyDivisionValueForUpdate(partyDivision);
                    var divisionName = edit.getDivisionName();
                    var duplicatePartyDivision = partyControl.getPartyDivisionByName(partyCompanyParty, divisionName);
                    
                    if(duplicatePartyDivision == null || duplicatePartyDivision.getPrimaryKey().equals(partyDivisionValue.getPrimaryKey())) {
                        var preferredLanguageIsoName = edit.getPreferredLanguageIsoName();
                        var preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
                        
                        if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                            var preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();
                            var preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                            
                            if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                var preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();
                                var preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                                
                                if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                    var preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();
                                    Currency preferredCurrency;
                                    
                                    if(preferredCurrencyIsoName == null)
                                        preferredCurrency = null;
                                    else {
                                        var accountingControl = Session.getModelController(AccountingControl.class);
                                        preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                    }
                                    
                                    if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                        if(lockEntityForUpdate(party)) {
                                            try {
                                                var updatedBy = getPartyPK();
                                                var partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
                                                var partyGroupValue = partyControl.getPartyGroupValueForUpdate(party);
                                                
                                                partyDivisionValue.setPartyDivisionName(divisionName);
                                                partyGroupValue.setName(edit.getName());
                                                partyDivisionValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                                partyDivisionValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                                
                                                partyControl.updatePartyDivisionFromValue(partyDivisionValue, updatedBy);
                                                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
                                                partyControl.updatePartyGroupFromValue(partyGroupValue, updatedBy);
                                            } finally {
                                                unlockEntity(party);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), preferredCurrencyIsoName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), preferredDateTimeFormatName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), preferredJavaTimeZoneName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), preferredLanguageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateDivisionName.name(), divisionName);
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setDivision(partyControl.getDivisionTransfer(getUserVisit(), partyDivision));
                        result.setEntityLock(getEntityLockTransfer(party));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDivisionName.name(), originalDivisionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
        }
        
        return result;
    }
    
}
