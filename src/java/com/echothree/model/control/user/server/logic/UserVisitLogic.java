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

package com.echothree.model.control.user.server.logic;

import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.workrequirement.server.logic.WorkRequirementLogic;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.factory.UserVisitFactory;
import com.echothree.model.data.user.server.value.UserKeyDetailValue;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class UserVisitLogic
        extends BaseLogic {
    
    private UserVisitLogic() {
        super();
    }
    
    private static class UserVisitLogicHolder {
        static UserVisitLogic instance = new UserVisitLogic();
    }
    
    public static UserVisitLogic getInstance() {
        return UserVisitLogicHolder.instance;
    }
    
    private void cleanupUserVisitDependencies(final UserVisit userVisit, final Long endTime, final PartyPK cleanedUpBy) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);

        orderControl.deleteOrderUserVisitsByUserVisit(userVisit);

        // Clear any searches since they may contain results that should not be accessible by another user.
        searchControl.removeUserVisitSearchesByUserVisit(userVisit);

        WorkRequirementLogic.getInstance().endWorkTimesByUserVisit(userVisit, endTime, cleanedUpBy);
    }
    
    public void invalidateUserVisit(UserVisit userVisit, PartyPK invalidatedBy) {
        if(userVisit != null) {
            var userControl = (UserControl)Session.getModelController(UserControl.class);
            UserSession userSession = UserSessionLogic.getInstance().deleteUserSessionByUserVisit(userVisit);
            
            cleanupUserVisitDependencies(userVisit, null, invalidatedBy);
            
            if(userVisit.getRetainUntilTime() == null) {
                if(userSession != null) {
                    userControl.removeUserSession(userSession);
                }
                
                userControl.removeUserVisit(userVisit);
            } else {
                userControl.deleteUserVisit(userVisit);
            }
        }
    }
    
    public void invalidateUserVisit(UserVisitPK userVisitPK, PartyPK invalidatedBy) {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        
        invalidateUserVisit(userControl.getUserVisitByPKForUpdate(userVisitPK), invalidatedBy);
    }
    
    public void invalidateAbandonedUserVisits(Long abandonedTime, PartyPK invalidatedBy) {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        
        for(UserVisit userVisit: userControl.getAbandonedUserVisits(abandonedTime)) {
            userVisit = UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userVisit.getPrimaryKey());
            
            invalidateUserVisit(userVisit, invalidatedBy);
        }
    }
    
    public void removeInvalidatedUserVisits() {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        
        for(UserVisit userVisit: userControl.getInvalidatedUserVisits()) {
            userVisit = UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userVisit.getPrimaryKey());
            
            userControl.removeUserVisit(userVisit);
        }
    }
    
    /** Disassociate any Party from the specified UserVisit by deleting the UserSession, keep all the Preferred* properties.
     */
    public void disassociatePartyFromUserVisit(final UserVisit userVisit, final Long endTime, final PartyPK disassociatedBy) {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        UserSession userSession = userControl.getUserSessionByUserVisitForUpdate(userVisit);
        
        if(userSession != null) {
            // If there wasn't a specific UserSession and Party assocaited with the UserVisit,
            // then we shouldn't need to perform this step.
            cleanupUserVisitDependencies(userVisit, endTime, disassociatedBy);

            userControl.deleteUserSession(userSession);
        }
    }
    
    /** Remove any Party's specific connection to the UserVisit and its UserKey. Clearing the connection in the UserKey is one step beyond
     * what's provided by disassociatePartyFromUserVisit(...).
     */
    public void logout(final UserVisitPK userVisitPK, final Long endTime, final PartyPK loggedOutBy) {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        UserVisit userVisit = userControl.getUserVisitByPK(userVisitPK);
        UserKey userKey = userVisit.getUserKey();
        UserKeyDetailValue userKeyDetailValue = userControl.getUserKeyDetailValueByPKForUpdate(userKey.getLastDetail().getPrimaryKey());

        if(userKeyDetailValue.getPartyPK() != null || userKeyDetailValue.getPartyRelationshipPK() != null) {
            userKeyDetailValue.setPartyPK(null);
            userKeyDetailValue.setPartyRelationshipPK(null);
            userControl.updateUserKeyFromValue(userKeyDetailValue);
        }

        disassociatePartyFromUserVisit(userVisit, endTime, loggedOutBy);

        // TODO: Create audit trail
    }
    
}
