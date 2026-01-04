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
import com.echothree.control.user.club.common.form.EditClubForm;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.control.user.club.common.spec.ClubSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.subscription.common.SubscriptionConstants;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.filter.server.entity.Filter;
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
import javax.enterprise.context.Dependent;

@Dependent
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
    public EditClubCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var clubControl = Session.getModelController(ClubControl.class);
        var result = ClubResultFactory.getEditClubResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var clubName = spec.getClubName();
            var club = clubControl.getClubByName(clubName);
            
            if(club != null) {
                result.setClub(clubControl.getClubTransfer(getUserVisit(), club));
                
                if(lockEntity(club)) {
                    var clubDescription = clubControl.getClubDescription(club, getPreferredLanguage());
                    var edit = ClubEditFactory.getClubEdit();
                    var clubDetail = club.getLastDetail();
                    var clubPriceFilter = clubDetail.getClubPriceFilter();
                    var currency = clubDetail.getCurrency();
                    
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
            var clubName = spec.getClubName();
            var club = clubControl.getClubByNameForUpdate(clubName);
            
            if(club != null) {
                clubName = edit.getClubName();
                var duplicateClub = clubControl.getClubByName(clubName);
                
                if(duplicateClub == null || club.equals(duplicateClub)) {
                    var subscriptionControl = Session.getModelController(SubscriptionControl.class);
                    var subscriptionKind = subscriptionControl.getSubscriptionKindByName(SubscriptionConstants.SubscriptionKind_CLUB);
                    var subscriptionTypeName = edit.getSubscriptionTypeName();
                    var subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
                    
                    if(subscriptionType != null) {
                        club = clubControl.getClubBySubscriptionType(subscriptionType);
                        
                        if(club == null) {
                            var clubPriceFilterName = edit.getClubPriceFilterName();
                            Filter clubPriceFilter = null;
                            
                            if(clubPriceFilterName != null) {
                                var filterControl = Session.getModelController(FilterControl.class);
                                var filterKind = filterControl.getFilterKindByName(FilterKinds.PRICE.name());
                                var filterType = filterControl.getFilterTypeByName(filterKind, FilterTypes.CLUB.name());
                                
                                if(filterType != null) {
                                    clubPriceFilter = filterControl.getFilterByName(filterType, clubPriceFilterName);
                                }
                            }
                            
                            if(clubPriceFilterName == null || clubPriceFilter != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                var currencyIsoName = edit.getCurrencyIsoName();
                                var currency = currencyIsoName == null? null: accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currencyIsoName == null || currency != null) {
                                    if(lockEntityForUpdate(club)) {
                                        try {
                                            var partyPK = getPartyPK();
                                            var clubDetailValue = clubControl.getClubDetailValueForUpdate(club);
                                            var clubDescription = clubControl.getClubDescriptionForUpdate(club, getPreferredLanguage());
                                            var description = edit.getDescription();
                                            
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
                                                var clubDescriptionValue = clubControl.getClubDescriptionValue(clubDescription);
                                                
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
