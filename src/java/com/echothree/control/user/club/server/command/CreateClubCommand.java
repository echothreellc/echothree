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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.form.CreateClubForm;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateClubCommand
        extends BaseSimpleCommand<CreateClubForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ClubPriceFilterName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateClubCommand */
    public CreateClubCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var clubControl = Session.getModelController(ClubControl.class);
        var clubName = form.getClubName();
        var club = clubControl.getClubByName(clubName);
        
        if(club == null) {
            var subscriptionControl = Session.getModelController(SubscriptionControl.class);
            var subscriptionKind = subscriptionControl.getSubscriptionKindByName(SubscriptionConstants.SubscriptionKind_CLUB);
            var subscriptionTypeName = form.getSubscriptionTypeName();
            var subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
            
            if(subscriptionType != null) {
                club = clubControl.getClubBySubscriptionType(subscriptionType);
                
                if(club == null) {
                    var clubPriceFilterName = form.getClubPriceFilterName();
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
                        var currencyIsoName = form.getCurrencyIsoName();
                        var currency = currencyIsoName == null? null: accountingControl.getCurrencyByIsoName(currencyIsoName);
                        
                        if(currencyIsoName == null || currency != null) {
                            var partyPK = getPartyPK();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();
                            
                            club = clubControl.createClub(clubName, subscriptionType, clubPriceFilter, currency, isDefault, sortOrder, partyPK);
                            
                            if(description != null) {
                                clubControl.createClubDescription(club, getPreferredLanguage(), description, partyPK);
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
        
        return null;
    }
    
}
