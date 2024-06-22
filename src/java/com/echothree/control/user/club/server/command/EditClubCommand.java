// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.control.user.club.common.form.EditClubForm;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.control.user.club.common.result.EditClubResult;
import com.echothree.control.user.club.common.spec.ClubSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.subscription.common.SubscriptionConstants;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubDescription;
import com.echothree.model.data.club.server.entity.ClubDetail;
import com.echothree.model.data.club.server.value.ClubDescriptionValue;
import com.echothree.model.data.club.server.value.ClubDetailValue;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditClubCommand
        extends BaseEditCommand<ClubSpec, ClubEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ClubPriceFilterName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditClubCommand */
    public EditClubCommand(UserVisitPK userVisitPK, EditClubForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var clubControl = Session.getModelController(ClubControl.class);
        EditClubResult result = ClubResultFactory.getEditClubResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String clubName = spec.getClubName();
            Club club = clubControl.getClubByName(clubName);
            
            if(club != null) {
                result.setClub(clubControl.getClubTransfer(getUserVisit(), club));
                
                if(lockEntity(club)) {
                    ClubDescription clubDescription = clubControl.getClubDescription(club, getPreferredLanguage());
                    ClubEdit edit = ClubEditFactory.getClubEdit();
                    ClubDetail clubDetail = club.getLastDetail();
                    Filter clubPriceFilter = clubDetail.getClubPriceFilter();
                    Currency currency = clubDetail.getCurrency();
                    
                    result.setEdit(edit);
                    edit.setClubName(clubDetail.getClubName());
                    edit.setSubscriptionTypeName(clubDetail.getSubscriptionType().getLastDetail().getSubscriptionTypeName());
                    edit.setClubPriceFilterName(clubPriceFilter == null? null: clubPriceFilter.getLastDetail().getFilterName());
                    edit.setCurrencyIsoName(currency.getCurrencyIsoName());
                    edit.setIsDefault(clubDetail.getIsDefault().toString());
                    edit.setSortOrder(clubDetail.getSortOrder().toString());
                    
                    if(clubDescription != null) {
                        edit.setDescription(clubDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(club));
            } else {
                addExecutionError(ExecutionErrors.UnknownClubName.name(), clubName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String clubName = spec.getClubName();
            Club club = clubControl.getClubByNameForUpdate(clubName);
            
            if(club != null) {
                clubName = edit.getClubName();
                Club duplicateClub = clubControl.getClubByName(clubName);
                
                if(duplicateClub == null || club.equals(duplicateClub)) {
                    var subscriptionControl = Session.getModelController(SubscriptionControl.class);
                    SubscriptionKind subscriptionKind = subscriptionControl.getSubscriptionKindByName(SubscriptionConstants.SubscriptionKind_CLUB);
                    String subscriptionTypeName = edit.getSubscriptionTypeName();
                    SubscriptionType subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
                    
                    if(subscriptionType != null) {
                        club = clubControl.getClubBySubscriptionType(subscriptionType);
                        
                        if(club == null) {
                            String clubPriceFilterName = edit.getClubPriceFilterName();
                            Filter clubPriceFilter = null;
                            
                            if(clubPriceFilterName != null) {
                                var filterControl = Session.getModelController(FilterControl.class);
                                FilterKind filterKind = filterControl.getFilterKindByName(FilterKinds.PRICE.name());
                                FilterType filterType = filterControl.getFilterTypeByName(filterKind, FilterTypes.CLUB.name());
                                
                                if(filterType != null) {
                                    clubPriceFilter = filterControl.getFilterByName(filterType, clubPriceFilterName);
                                }
                            }
                            
                            if(clubPriceFilterName == null || clubPriceFilter != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                String currencyIsoName = edit.getCurrencyIsoName();
                                Currency currency = currencyIsoName == null? null: accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currencyIsoName == null || currency != null) {
                                    if(lockEntityForUpdate(club)) {
                                        try {
                                            var partyPK = getPartyPK();
                                            ClubDetailValue clubDetailValue = clubControl.getClubDetailValueForUpdate(club);
                                            ClubDescription clubDescription = clubControl.getClubDescriptionForUpdate(club, getPreferredLanguage());
                                            String description = edit.getDescription();
                                            
                                            clubDetailValue.setClubName(edit.getClubName());
                                            clubDetailValue.setSubscriptionTypePK(subscriptionType.getPrimaryKey());
                                            clubDetailValue.setClubPriceFilterPK(clubPriceFilter == null? null: clubPriceFilter.getPrimaryKey());
                                            clubDetailValue.setCurrencyPK(currency.getPrimaryKey());
                                            clubDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                            clubDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                            
                                            clubControl.updateClubFromValue(clubDetailValue, partyPK);
                                            
                                            if(clubDescription == null && description != null) {
                                                clubControl.createClubDescription(club, getPreferredLanguage(), description, partyPK);
                                            } else if(clubDescription != null && description == null) {
                                                clubControl.deleteClubDescription(clubDescription, partyPK);
                                            } else if(clubDescription != null && description != null) {
                                                ClubDescriptionValue clubDescriptionValue = clubControl.getClubDescriptionValue(clubDescription);
                                                
                                                clubDescriptionValue.setDescription(description);
                                                clubControl.updateClubDescriptionFromValue(clubDescriptionValue, partyPK);
                                            }
                                        } finally {
                                            unlockEntity(club);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownClubPriceFilterName.name(), clubPriceFilterName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateSubscriptionType.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSubscriptionTypeName.name(), subscriptionTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateClubName.name(), clubName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownClubName.name(), clubName);
            }
        }
        
        return result;
    }
    
}
