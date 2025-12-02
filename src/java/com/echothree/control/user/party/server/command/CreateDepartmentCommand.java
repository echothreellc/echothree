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

import com.echothree.control.user.party.common.form.CreateDepartmentForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateDepartmentCommand
        extends BaseSimpleCommand<CreateDepartmentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DepartmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Name", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateDepartmentCommand */
    public CreateDepartmentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = PartyResultFactory.getCreateDepartmentResult();
        var partyControl = Session.getModelController(PartyControl.class);
        var companyName = form.getCompanyName();
        var partyCompany = partyControl.getPartyCompanyByName(companyName);
        
        if(partyCompany != null) {
            var divisionName = form.getDivisionName();
            var partyCompanyParty = partyCompany.getParty();
            var partyDivision = partyControl.getPartyDivisionByName(partyCompanyParty, divisionName);
            
            if(partyDivision != null) {
                var departmentName = form.getDepartmentName();
                var partyDivisionParty = partyDivision.getParty();
                var partyDepartment = partyControl.getPartyDepartmentByName(partyDivisionParty, departmentName);
                
                if(partyDepartment == null) {
                    var preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                    var preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
                    
                    if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                        var preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                        var preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                        
                        if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                            var preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                            var preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                            
                            if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                var preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                                Currency preferredCurrency;
                                
                                if(preferredCurrencyIsoName == null)
                                    preferredCurrency = null;
                                else {
                                    var accountingControl = Session.getModelController(AccountingControl.class);
                                    preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                }
                                
                                if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                    var partyType = partyControl.getPartyTypeByName(PartyTypes.DEPARTMENT.name());
                                    BasePK createdBy = getPartyPK();
                                    var name = form.getName();
                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());

                                    var party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, createdBy);
                                    partyControl.createPartyGroup(party, name, createdBy);
                                    partyDepartment = partyControl.createPartyDepartment(party, partyDivisionParty, departmentName, isDefault, sortOrder, createdBy);
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
                    addExecutionError(ExecutionErrors.DuplicateDepartmentName.name(), departmentName);
                }
                
                if(partyDepartment != null) {
                    var party = partyDepartment.getParty();
                    
                    result.setEntityRef(party.getPrimaryKey().getEntityRef());
                    result.setDepartmentName(partyDepartment.getPartyDepartmentName());
                    result.setPartyName(party.getLastDetail().getPartyName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDivisionName.name(), divisionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
        }
        
        return result;
    }
    
}
