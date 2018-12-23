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

import com.echothree.control.user.party.common.edit.CompanyEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditCompanyForm;
import com.echothree.control.user.party.common.result.EditCompanyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.CompanySpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.value.PartyCompanyValue;
import com.echothree.model.data.party.server.value.PartyDetailValue;
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

public class EditCompanyCommand
        extends BaseEditCommand<CompanySpec, CompanyEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Name", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditCompanyCommand */
    public EditCompanyCommand(UserVisitPK userVisitPK, EditCompanyForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditCompanyResult result = PartyResultFactory.getEditCompanyResult();
        String originalCompanyName = spec.getCompanyName();
        PartyCompany partyCompany = partyControl.getPartyCompanyByNameForUpdate(originalCompanyName);
        
        if(partyCompany != null) {
            Party party = partyCompany.getParty();
            
            if(editMode.equals(EditMode.LOCK)) {
                result.setCompany(partyControl.getCompanyTransfer(getUserVisit(), partyCompany));
                
                if(lockEntity(party)) {
                    CompanyEdit edit = PartyEditFactory.getCompanyEdit();
                    PartyDetail partyDetail = party.getLastDetail();
                    PartyGroup partyGroup = partyControl.getPartyGroup(party);
                    Language preferredLanguage = partyDetail.getPreferredLanguage();
                    Currency preferredCurrency = partyDetail.getPreferredCurrency();
                    TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
                    DateTimeFormat preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                    
                    result.setEdit(edit);
                    edit.setCompanyName(partyCompany.getPartyCompanyName());
                    edit.setName(partyGroup.getName());
                    edit.setPreferredLanguageIsoName(preferredLanguage == null? null: preferredLanguage.getLanguageIsoName());
                    edit.setPreferredCurrencyIsoName(preferredCurrency == null? null: preferredCurrency.getCurrencyIsoName());
                    edit.setPreferredJavaTimeZoneName(preferredTimeZone == null? null: preferredTimeZone.getLastDetail().getJavaTimeZoneName());
                    edit.setPreferredDateTimeFormatName(preferredDateTimeFormat == null? null: preferredDateTimeFormat.getLastDetail().getDateTimeFormatName());
                    edit.setIsDefault(partyCompany.getIsDefault().toString());
                    edit.setSortOrder(partyCompany.getSortOrder().toString());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(party));
            } else if(editMode.equals(EditMode.ABANDON)) {
                unlockEntity(party);
            } else if(editMode.equals(EditMode.UPDATE)) {
                PartyCompanyValue partyCompanyValue = partyControl.getPartyCompanyValueForUpdate(partyCompany);
                String companyName = edit.getCompanyName();
                PartyCompany duplicatePartyCompany = partyControl.getPartyCompanyByName(companyName);
                
                if(duplicatePartyCompany == null || duplicatePartyCompany.getPrimaryKey().equals(partyCompanyValue.getPrimaryKey())) {
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
                                            
                                            partyCompanyValue.setPartyCompanyName(companyName);
                                            partyGroupValue.setName(edit.getName());
                                            partyCompanyValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                            partyCompanyValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                            
                                            partyControl.updatePartyCompanyFromValue(partyCompanyValue, updatedBy);
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
                    addExecutionError(ExecutionErrors.DuplicateCompanyName.name(), companyName);
                }
                
                if(hasExecutionErrors()) {
                    result.setCompany(partyControl.getCompanyTransfer(getUserVisit(), partyCompany));
                    result.setEntityLock(getEntityLockTransfer(party));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCompanyName.name(), originalCompanyName);
        }
        
        return result;
    }
    
}
