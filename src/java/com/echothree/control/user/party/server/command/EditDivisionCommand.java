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

import com.echothree.control.user.party.common.edit.DivisionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditDivisionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.DivisionUniversalSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.DivisionLogic;
import com.echothree.model.control.party.server.logic.DateTimeFormatLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditDivisionCommand
        extends BaseEditCommand<DivisionUniversalSpec, DivisionEdit, EditDivisionResult, PartyDivision, Party> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, true, null, null),
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
    DivisionLogic divisionLogic;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    DateTimeFormatLogic dateTimeFormatLogic;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    TimeZoneLogic timeZoneLogic;

    /** Creates a new instance of EditDivisionCommand */
    public EditDivisionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditDivisionResult getResult() {
        return PartyResultFactory.getEditDivisionResult();
    }

    @Override
    public DivisionEdit getEdit() {
        return PartyEditFactory.getDivisionEdit();
    }

    @Override
    public PartyDivision getEntity(EditDivisionResult result) {
        return divisionLogic.getPartyDivisionByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public Party getLockEntity(PartyDivision partyDivision) {
        return partyDivision.getParty();
    }

    @Override
    public void fillInResult(EditDivisionResult result, PartyDivision partyDivision) {
        result.setDivision(partyControl.getDivisionTransfer(getUserVisit(), partyDivision));
    }

    @Override
    public void doLock(DivisionEdit edit, PartyDivision partyDivision) {
        var party = partyDivision.getParty();
        var partyDetail = party.getLastDetail();
        var partyGroup = partyControl.getPartyGroup(party);
        var preferredLanguage = partyDetail.getPreferredLanguage();
        var preferredCurrency = partyDetail.getPreferredCurrency();
        var preferredTimeZone = partyDetail.getPreferredTimeZone();
        var preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();

        edit.setDivisionName(partyDivision.getPartyDivisionName());
        edit.setName(partyGroup.getName());
        edit.setPreferredLanguageIsoName(preferredLanguage == null? null: preferredLanguage.getLanguageIsoName());
        edit.setPreferredCurrencyIsoName(preferredCurrency == null? null: preferredCurrency.getCurrencyIsoName());
        edit.setPreferredJavaTimeZoneName(preferredTimeZone == null? null: preferredTimeZone.getLastDetail().getJavaTimeZoneName());
        edit.setPreferredDateTimeFormatName(preferredDateTimeFormat == null? null: preferredDateTimeFormat.getLastDetail().getDateTimeFormatName());
        edit.setIsDefault(partyDivision.getIsDefault().toString());
        edit.setSortOrder(partyDivision.getSortOrder().toString());
    }

    @Override
    public void canUpdate(PartyDivision partyDivision) {
        var divisionName = edit.getDivisionName();
        var companyParty = partyDivision.getCompanyParty();
        var duplicatePartyDivision = partyControl.getPartyDivisionByName(companyParty, divisionName);

        if(duplicatePartyDivision != null && !partyDivision.equals(duplicatePartyDivision)) {
            var partyCompany = partyControl.getPartyCompany(companyParty);

            addExecutionError(ExecutionErrors.DuplicateDivisionName.name(), partyCompany.getPartyCompanyName(), divisionName);
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
    public void doUpdate(PartyDivision partyDivision) {
        var party = partyDivision.getParty();
        var updatedBy = getPartyPK();
        var partyDivisionValue = partyControl.getPartyDivisionValueForUpdate(partyDivision);
        var partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
        var partyGroupValue = partyControl.getPartyGroupValueForUpdate(party);

        partyDivisionValue.setPartyDivisionName(edit.getDivisionName());
        partyDivisionValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partyDivisionValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        partyGroupValue.setName(edit.getName());
        partyDetailValue.setPreferredLanguagePK(preferredLanguage == null ? null : preferredLanguage.getPrimaryKey());
        partyDetailValue.setPreferredCurrencyPK(preferredCurrency == null ? null : preferredCurrency.getPrimaryKey());
        partyDetailValue.setPreferredTimeZonePK(preferredTimeZone == null ? null : preferredTimeZone.getPrimaryKey());
        partyDetailValue.setPreferredDateTimeFormatPK(preferredDateTimeFormat == null ? null : preferredDateTimeFormat.getPrimaryKey());

        partyControl.updatePartyDivisionFromValue(partyDivisionValue, updatedBy);
        partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
        partyControl.updatePartyGroupFromValue(partyGroupValue, updatedBy);
    }

}
