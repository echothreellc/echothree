// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.forum.server.logic;

import com.echothree.model.control.forum.common.exception.UnknownForumPartyRoleException;
import com.echothree.model.control.forum.common.exception.UnknownForumPartyTypeRoleException;
import com.echothree.model.control.forum.common.exception.UnknownForumRoleTypeNameException;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.party.common.exception.PartyRequiredException;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class ForumLogic
        extends BaseLogic {
    
    private ForumLogic() {
        super();
    }
    
    private static class ForumLogicHolder {
        static ForumLogic instance = new ForumLogic();
    }
    
    public static ForumLogic getInstance() {
        return ForumLogicHolder.instance;
    }
    
    public ForumRoleType getForumRoleTypeByName(final ExecutionErrorAccumulator eea, final String forumRoleTypeName) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        ForumRoleType forumRoleType = forumControl.getForumRoleTypeByName(forumRoleTypeName);

        if(forumRoleType == null) {
            handleExecutionError(UnknownForumRoleTypeNameException.class, eea, ExecutionErrors.UnknownForumRoleTypeName.name(), forumRoleTypeName);
        }

        return forumRoleType;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final Forum forum, final Party party, final String forumRoleTypeName) {
        ForumRoleType forumRoleType = getForumRoleTypeByName(eea, forumRoleTypeName);

        return isForumRoleTypePermitted(eea, forum, party, forumRoleType);
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final Forum forum, final Party party, final ForumRoleType forumRoleType) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        boolean hasForumPartyTypeRoles = forumControl.hasForumPartyTypeRoles(forum, forumRoleType);
        boolean hasForumPartyRoles = forumControl.hasForumPartyRoles(forum, forumRoleType);
        boolean permitted = !(hasForumPartyTypeRoles || hasForumPartyRoles);

        if(!permitted) {
            if(party == null) {
                handleExecutionError(PartyRequiredException.class, eea, ExecutionErrors.PartyRequired.name());
            } else {
                boolean hasForumPartyTypeRole = false;
                boolean hasForumPartyRole = false;

                if(hasForumPartyTypeRoles) {
                    PartyType partyType = party.getLastDetail().getPartyType();

                    hasForumPartyTypeRole = forumControl.hasForumPartyTypeRole(forum, partyType, forumRoleType);
                }

                if(hasForumPartyRoles) {
                    hasForumPartyRole = forumControl.hasForumPartyRole(forum, party, forumRoleType);
                }

                permitted |= hasForumPartyTypeRole || hasForumPartyRole;

                if(!permitted) {
                    String forumName = forum.getLastDetail().getForumName();
                    String forumRoleTypeName = forumRoleType.getForumRoleTypeName();

                    if(!hasForumPartyRole) {
                        String partyName = party.getLastDetail().getPartyName();

                        handleExecutionError(UnknownForumPartyRoleException.class, eea, ExecutionErrors.UnknownForumPartyRole.name(), forumName, partyName, forumRoleTypeName);
                    }

                    if(!hasForumPartyTypeRole) {
                        String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

                        handleExecutionError(UnknownForumPartyTypeRoleException.class, eea, ExecutionErrors.UnknownForumPartyTypeRole.name(), forumName, partyTypeName, forumRoleTypeName);
                    }
                }
            }
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumThread forumThread, final Party party, final String forumRoleTypeName) {
        ForumRoleType forumRoleType = getForumRoleTypeByName(eea, forumRoleTypeName);
        boolean permitted = false;

        if(!hasExecutionErrors(eea)) {
            permitted = isForumRoleTypePermitted(eea, forumThread, party, forumRoleType);
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumThread forumThread, final Party party, final ForumRoleType forumRoleType) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        List<ForumForumThread> forumForumThreads = forumControl.getForumForumThreadsByForumThread(forumThread);
        boolean permitted = false;

        for(ForumForumThread forumForumThread : forumForumThreads) {
            Forum forum = forumForumThread.getForum();

            permitted |= isForumRoleTypePermitted(eea, forum, party, forumRoleType);

            if(permitted) {
                break;
            }
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumMessage forumMessage, final Party party, final String forumRoleTypeName) {
        ForumRoleType forumRoleType = getForumRoleTypeByName(eea, forumRoleTypeName);
        boolean permitted = false;

        if(!hasExecutionErrors(eea)) {
            permitted = isForumRoleTypePermitted(eea, forumMessage, party, forumRoleType);
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumMessage forumMessage, final Party party, final ForumRoleType forumRoleType) {
        ForumThread forumThread = forumMessage.getLastDetail().getForumThread();

        return isForumRoleTypePermitted(eea, forumThread, party, forumRoleType);
    }
    
}
