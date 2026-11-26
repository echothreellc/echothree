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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.DepartmentEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditDepartmentResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.DepartmentUniversalSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.DateTimeFormatLogic;
import com.echothree.model.control.party.server.logic.DepartmentLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditDepartmentCommand
        extends BaseEditCommand<DepartmentUniversalSpec, DepartmentEdit, EditDepartmentResult, PartyDepartment, Party> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DepartmentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DepartmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Name", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    private Language preferredLanguage;
    private Currency preferredCurrency;
    private TimeZone preferredTimeZone;
    private DateTimeFormat preferredDateTimeFormat;

    @Inject
    PartyControl partyControl;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    DateTimeFormatLogic dateTimeFormatLogic;

    @Inject
    DepartmentLogic departmentLogic;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    TimeZoneLogic timeZoneLogic;

    /** Creates a new instance of EditDepartmentCommand */
    public EditDepartmentCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditDepartmentResult getResult() {
        return PartyResultFactory.getEditDepartmentResult();
    }

    @Override
    public DepartmentEdit getEdit() {
        return PartyEditFactory.getDepartmentEdit();
    }

    @Override
    public PartyDepartment getEntity(EditDepartmentResult result) {
        return departmentLogic.getPartyDepartmentByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public Party getLockEntity(PartyDepartment partyDepartment) {
        return partyDepartment.getParty();
    }

    @Override
    public void fillInResult(EditDepartmentResult result, PartyDepartment partyDepartment) {
        result.setDepartment(partyControl.getDepartmentTransfer(getUserVisit(), partyDepartment));
    }

    @Override
    public void doLock(DepartmentEdit edit, PartyDepartment partyDepartment) {
        var party = partyDepartment.getParty();
        var partyDetail = party.getLastDetail();
        var partyGroup = partyControl.getPartyGroup(party);
        var preferredLanguage = partyDetail.getPreferredLanguage();
        var preferredCurrency = partyDetail.getPreferredCurrency();
        var preferredTimeZone = partyDetail.getPreferredTimeZone();
        var preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();

        edit.setDepartmentName(partyDepartment.getPartyDepartmentName());
        edit.setName(partyGroup.getName());
        edit.setPreferredLanguageIsoName(preferredLanguage == null? null: preferredLanguage.getLanguageIsoName());
        edit.setPreferredCurrencyIsoName(preferredCurrency == null? null: preferredCurrency.getCurrencyIsoName());
        edit.setPreferredJavaTimeZoneName(preferredTimeZone == null? null: preferredTimeZone.getLastDetail().getJavaTimeZoneName());
        edit.setPreferredDateTimeFormatName(preferredDateTimeFormat == null? null: preferredDateTimeFormat.getLastDetail().getDateTimeFormatName());
        edit.setIsDefault(partyDepartment.getIsDefault().toString());
        edit.setSortOrder(partyDepartment.getSortOrder().toString());
    }

    @Override
    public void canUpdate(PartyDepartment partyDepartment) {
        var departmentName = edit.getDepartmentName();
        var divisionParty = partyDepartment.getDivisionParty();
        var duplicatePartyDepartment = partyControl.getPartyDepartmentByName(divisionParty, departmentName);

        if(duplicatePartyDepartment != null && !partyDepartment.equals(duplicatePartyDepartment)) {
            var partyDivision = partyControl.getPartyDivision(divisionParty);
            var partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());

            addExecutionError(ExecutionErrors.DuplicateDepartmentName.name(), partyCompany.getPartyCompanyName(),
                    partyDivision.getPartyDivisionName(), departmentName);
        }

        var preferredLanguageIsoName = edit.getPreferredLanguageIsoName();
        var preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();
        var preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();
        var preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();

        preferredLanguage = preferredLanguageIsoName == null ? null : languageLogic.getLanguageByName(this, preferredLanguageIsoName);
        preferredCurrency = preferredCurrencyIsoName == null ? null : currencyLogic.getCurrencyByName(this, preferredCurrencyIsoName);
        preferredTimeZone = preferredJavaTimeZoneName == null ? null : timeZoneLogic.getTimeZoneByName(this, preferredJavaTimeZoneName);
        preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : dateTimeFormatLogic.getDateTimeFormatByName(this, preferredDateTimeFormatName);
    }

    @Override
    public void doUpdate(PartyDepartment partyDepartment) {
        var party = partyDepartment.getParty();
        var updatedBy = getPartyPK();
        var partyDepartmentValue = partyControl.getPartyDepartmentValueForUpdate(partyDepartment);
        var partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
        var partyGroupValue = partyControl.getPartyGroupValueForUpdate(party);

        partyDepartmentValue.setPartyDepartmentName(edit.getDepartmentName());
        partyDepartmentValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partyDepartmentValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        partyGroupValue.setName(edit.getName());
        partyDetailValue.setPreferredLanguagePK(preferredLanguage == null ? null : preferredLanguage.getPrimaryKey());
        partyDetailValue.setPreferredCurrencyPK(preferredCurrency == null ? null : preferredCurrency.getPrimaryKey());
        partyDetailValue.setPreferredTimeZonePK(preferredTimeZone == null ? null : preferredTimeZone.getPrimaryKey());
        partyDetailValue.setPreferredDateTimeFormatPK(preferredDateTimeFormat == null ? null : preferredDateTimeFormat.getPrimaryKey());

        partyControl.updatePartyDepartmentFromValue(partyDepartmentValue, updatedBy);
        partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
        partyControl.updatePartyGroupFromValue(partyGroupValue, updatedBy);
    }

}
