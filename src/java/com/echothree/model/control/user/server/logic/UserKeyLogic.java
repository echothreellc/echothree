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

package com.echothree.model.control.user.server.logic;

import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.factory.UserKeyFactory;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UserKeyLogic {

    protected UserKeyLogic() {
        super();
    }

    public static UserKeyLogic getInstance() {
        return CDI.current().select(UserKeyLogic.class).get();
    }
    
    public void removeInactiveUserKeys(final Long remainingTime, final Long inactiveTime) {
        var userControl = Session.getModelController(UserControl.class);
        var startTime = System.currentTimeMillis();
        long entityCount = 0;
        
        for(var userKey : userControl.getInactiveUserKeys(inactiveTime)) {
            entityCount++;
            if(entityCount % 1000 == 0 && System.currentTimeMillis() - startTime > remainingTime) {
                break;
            }
            
            userKey = UserKeyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userKey.getPrimaryKey());
            
            userControl.removeUserKey(userKey);
        }
    }

    /** Sets the Party and PartyRelationship to null.
     */
    public void clearUserKey(UserKey userKey) {
        var userControl = Session.getModelController(UserControl.class);
        var userKeyDetailValue = userControl.getUserKeyDetailValueForUpdate(userKey);

        userKeyDetailValue.setPartyPK(null);
        userKeyDetailValue.setPartyRelationshipPK(null);

        userControl.updateUserKeyFromValue(userKeyDetailValue);
    }

    /** Calls clearUserKey(...) for each UserKey in the List.
     */
    public void clearUserKeys(List<UserKey> userKeys) {
        userKeys.forEach((userKey) -> {
            clearUserKey(userKey);
        });
    }

    /** Sets the Party and PartyRelationship to null when a UserKey contains the specified Party.
     */
    public void clearUserKeysByParty(Party party) {
        var userControl = Session.getModelController(UserControl.class);

        clearUserKeys(userControl.getUserKeysByPartyForUpdate(party));
    }

    /** Sets the Party and PartyRelationship to null when a UserKey contains the specified PartyRelationship.
     */
    public void clearUserKeysByPartyRelationship(PartyRelationship partyRelationship) {
        var userControl = Session.getModelController(UserControl.class);

        clearUserKeys(userControl.getUserKeysByPartyRelationshipForUpdate(partyRelationship));
    }
    
}
