// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.party.common.result.EditDivisionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.DivisionSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyDivisionValue;
import com.echothree.model.data.party.server.value.PartyGroupValue;
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
    public EditDivisionCommand(UserVisitPK userVisitPK, EditDivisionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditDivisionResult result = PartyResultFactory.getEditDivisionResult();
        String companyName = spec.getCompanyName();
        PartyCompany partyCompany = partyControl.getPartyCompanyByName(companyName);
        
        if(partyCompany != null) {
            String originalDivisionName = spec.getDivisionName();
            Party partyCompanyParty = partyCompany.getParty();
            PartyDivision partyDivision = partyControl.getPartyDivisionByNameForUpdate(partyCompanyParty, originalDivisionName);
            
            if(partyDivision != null) {
                Party party = partyDivision.getParty();
                
                if(editMode.equals(EditMode.LOCK)) {
                    result.setDivision(partyControl.getDivisionTransfer(getUserVisit(), partyDivision));
                    
                    if(lockEntity(party)) {
                        DivisionEdit edit = PartyEditFactory.getDivisionEdit();
                        PartyDetail partyDetail = party.getLastDetail();
                        PartyGroup partyGroup = partyControl.getPartyGroup(party);
                        Language preferredLanguage = partyDetail.getPreferredLanguage();
                        Currency preferredCurrency = partyDetail.getPreferredCurrency();
                        TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
                        DateTimeFormat preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                        
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
                    PartyDivisionValue partyDivisionValue = partyControl.getPartyDivisionValueForUpdate(partyDivision);
                    String divisionName = edit.getDivisionName();
                    PartyDivision duplicatePartyDivision = partyControl.getPartyDivisionByName(partyCompanyParty, divisionName);
                    
                    if(duplicatePartyDivision == null || duplicatePartyDivision.getPrimaryKey().equals(partyDivisionValue.getPrimaryKey())) {
                        String preferredLanguageIsoName = edit.getPreferredLanguageIsoName();
                        Language preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
                        
                        if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                            String preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();
                            TimeZone preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                            
                            if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                String preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();
                                DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                                
                                if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                    String preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();
                                    Currency preferredCurrency;
                                    
                                    if(preferredCurrencyIsoName == null)
                                        preferredCurrency = null;
                                    else {
                                        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                        preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                    }
                                    
                                    if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                        if(lockEntityForUpdate(party)) {
                                            try {
                                                PartyPK updatedBy = getPartyPK();
                                                PartyDetailValue partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
                                                PartyGroupValue partyGroupValue = partyControl.getPartyGroupValueForUpdate(party);
                                                
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
