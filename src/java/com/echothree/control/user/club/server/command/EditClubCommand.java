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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.edit.ClubEdit;
import com.echothree.control.user.club.common.edit.ClubEditFactory;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.control.user.club.common.result.EditClubResult;
import com.echothree.control.user.club.common.spec.ClubUniversalSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.club.server.logic.ClubLogic;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.filter.server.logic.FilterLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.subscription.common.SubscriptionConstants;
import com.echothree.model.control.subscription.server.logic.SubscriptionTypeLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditClubCommand
        extends BaseEditCommand<ClubUniversalSpec, ClubEdit, EditClubResult, Club, Club> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Club.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ClubName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ClubPriceFilterName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    private SubscriptionType subscriptionType;
    private Filter clubPriceFilter;
    private Currency currency;

    @Inject
    ClubControl clubControl;

    @Inject
    ClubLogic clubLogic;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    FilterLogic filterLogic;

    @Inject
    SubscriptionTypeLogic subscriptionTypeLogic;

    /** Creates a new instance of EditClubCommand */
    public EditClubCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditClubResult getResult() {
        return ClubResultFactory.getEditClubResult();
    }

    @Override
    public ClubEdit getEdit() {
        return ClubEditFactory.getClubEdit();
    }

    @Override
    public Club getEntity(EditClubResult result) {
        return clubLogic.getClubByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Club getLockEntity(Club club) {
        return club;
    }

    @Override
    public void fillInResult(EditClubResult result, Club club) {
        result.setClub(clubControl.getClubTransfer(getUserVisit(), club));
    }

    @Override
    public void doLock(ClubEdit edit, Club club) {
        var clubDescription = clubControl.getClubDescription(club, getPreferredLanguage());
        var clubDetail = club.getLastDetail();
        var clubPriceFilter = clubDetail.getClubPriceFilter();
        var currency = clubDetail.getCurrency();

        edit.setClubName(clubDetail.getClubName());
        edit.setSubscriptionTypeName(clubDetail.getSubscriptionType().getLastDetail().getSubscriptionTypeName());
        edit.setClubPriceFilterName(clubPriceFilter == null? null: clubPriceFilter.getLastDetail().getFilterName());
        edit.setCurrencyIsoName(currency == null ? null : currency.getCurrencyIsoName());
        edit.setIsDefault(clubDetail.getIsDefault().toString());
        edit.setSortOrder(clubDetail.getSortOrder().toString());

        if(clubDescription != null) {
            edit.setDescription(clubDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Club club) {
        var clubName = edit.getClubName();
        var duplicateClub = clubControl.getClubByName(clubName);
        var subscriptionTypeName = edit.getSubscriptionTypeName();
        var clubPriceFilterName = edit.getClubPriceFilterName();
        var currencyIsoName = edit.getCurrencyIsoName();

        if(duplicateClub != null && !club.equals(duplicateClub)) {
            addExecutionError(ExecutionErrors.DuplicateClubName.name(), clubName);
        }

        subscriptionType = subscriptionTypeLogic.getSubscriptionTypeByName(this,
                SubscriptionConstants.SubscriptionKind_CLUB, subscriptionTypeName);
        clubPriceFilter = clubPriceFilterName == null ? null : filterLogic.getFilterByName(this,
                FilterKinds.PRICE.name(), FilterTypes.CLUB.name(), clubPriceFilterName);
        currency = currencyIsoName == null ? null : currencyLogic.getCurrencyByName(this, currencyIsoName);

        if(!hasExecutionErrors()) {
            var clubBySubscriptionType = clubControl.getClubBySubscriptionType(subscriptionType);

            if(clubBySubscriptionType != null && !club.equals(clubBySubscriptionType)) {
                addExecutionError(ExecutionErrors.DuplicateSubscriptionType.name(),
                        SubscriptionConstants.SubscriptionKind_CLUB, subscriptionTypeName);
            }
        }
    }

    @Override
    public void doUpdate(Club club) {
        var partyPK = getPartyPK();
        var clubDetailValue = clubControl.getClubDetailValueForUpdate(club);
        var clubDescription = clubControl.getClubDescriptionForUpdate(club, getPreferredLanguage());
        var description = edit.getDescription();

        clubDetailValue.setClubName(edit.getClubName());
        clubDetailValue.setSubscriptionTypePK(subscriptionType.getPrimaryKey());
        clubDetailValue.setClubPriceFilterPK(clubPriceFilter == null? null: clubPriceFilter.getPrimaryKey());
        clubDetailValue.setCurrencyPK(currency == null ? null : currency.getPrimaryKey());
        clubDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        clubDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        clubControl.updateClubFromValue(clubDetailValue, partyPK);

        if(clubDescription == null && description != null) {
            clubControl.createClubDescription(club, getPreferredLanguage(), description, partyPK);
        } else if(clubDescription != null && description == null) {
            clubControl.deleteClubDescription(clubDescription, partyPK);
        } else if(clubDescription != null && description != null) {
            var clubDescriptionValue = clubControl.getClubDescriptionValue(clubDescription);

            clubDescriptionValue.setDescription(description);
            clubControl.updateClubDescriptionFromValue(clubDescriptionValue, partyPK);
        }
    }

}
