// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.factory.UserSessionFactory;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class UserSessionLogic {

    private UserSessionLogic() {
        super();
    }

    private static class UserSessionLogicHolder {
        static UserSessionLogic instance = new UserSessionLogic();
    }

    public static UserSessionLogic getInstance() {
        return UserSessionLogicHolder.instance;
    }

    /**
     * Invalidating the UserSession will clear the PasswordVerified time if necessary.
     *
     * @param userSession The UserSession that should be invalidated.
     * @return An invalid UserSession (may be the same as the userSession parameter).
     */
    public UserSession invalidateUserSession(UserSession userSession) {
        if(userSession.getPasswordVerifiedTime() != null) {
            UserControl userControl = (UserControl)Session.getModelController(UserControl.class);

            if(!userSession.getEntityPermission().equals(EntityPermission.READ_WRITE)) {
                userSession = UserSessionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userSession.getPrimaryKey());
            }

            userControl.deleteUserSession(userSession);

            userSession = userControl.createUserSession(userSession.getUserVisitForUpdate(), userSession.getParty(), userSession.getPartyRelationship(), null);
        }

        return userSession;
    }

    /** Sets the Party and PartyRelationship to null when a UserSession contains the specified Party.
     */
    public void deleteUserSessionsByParty(Party party) {
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);

        userControl.deleteUserSessions(userControl.getUserSessionsByPartyForUpdate(party));
    }

    /** Sets the Party and PartyRelationship to null when a UserSession contains the specified PartyRelationship.
     */
    public void deleteUserSessionsByPartyRelationship(PartyRelationship partyRelationship) {
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);

        userControl.deleteUserSessions(userControl.getUserSessionsByPartyRelationshipForUpdate(partyRelationship));
    }

    public UserSession deleteUserSessionByUserVisit(UserVisit userVisit) {
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        UserSession userSession = userControl.getUserSessionByUserVisitForUpdate(userVisit);

        if(userSession != null) {
            userControl.deleteUserSession(userSession);
        }

        return userSession;
    }

    public UserSession deleteUserSessionByUserVisitPK(UserVisitPK userVisitPK) {
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        
        return deleteUserSessionByUserVisit(userControl.getUserVisitByPKForUpdate(userVisitPK));
    }

}