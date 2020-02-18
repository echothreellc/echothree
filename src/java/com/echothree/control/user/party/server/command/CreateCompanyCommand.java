// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.CreateCompanyForm;
import com.echothree.control.user.party.common.result.CreateCompanyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCompanyCommand
        extends BaseSimpleCommand<CreateCompanyForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of CreateCompanyCommand */
    public CreateCompanyCommand(UserVisitPK userVisitPK, CreateCompanyForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateCompanyResult result = PartyResultFactory.getCreateCompanyResult();
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String companyName = form.getCompanyName();
        PartyCompany partyCompany = partyControl.getPartyCompanyByName(companyName);
        
        if(partyCompany == null) {
            String preferredLanguageIsoName = form.getPreferredLanguageIsoName();
            Language preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
            
            if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                String preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                TimeZone preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                
                if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                    String preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                    DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                    
                    if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                        String preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                        Currency preferredCurrency;
                        
                        if(preferredCurrencyIsoName == null)
                            preferredCurrency = null;
                        else {
                            var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                        }
                        
                        if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                            PartyType partyType = partyControl.getPartyTypeByName(PartyTypes.COMPANY.name());
                            BasePK createdBy = getPartyPK();
                            String name = form.getName();
                            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            
                            Party party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, createdBy);
                            partyControl.createPartyGroup(party, name, createdBy);
                            partyCompany = partyControl.createPartyCompany(party, companyName, isDefault, sortOrder, createdBy);
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
        
        if(partyCompany != null) {
            Party party = partyCompany.getParty();
            
            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setCompanyName(partyCompany.getPartyCompanyName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }
        
        return result;
    }
    
}
