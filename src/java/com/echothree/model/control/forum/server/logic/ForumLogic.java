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

package com.echothree.model.control.forum.server.logic;

import com.echothree.model.control.forum.common.exception.UnknownForumPartyRoleException;
import com.echothree.model.control.forum.common.exception.UnknownForumPartyTypeRoleException;
import com.echothree.model.control.forum.common.exception.UnknownForumRoleTypeNameException;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.common.exception.PartyRequiredException;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ForumLogic
        extends BaseLogic {

    protected ForumLogic() {
        super();
    }

    public static ForumLogic getInstance() {
        return CDI.current().select(ForumLogic.class).get();
    }
    
    public ForumRoleType getForumRoleTypeByName(final ExecutionErrorAccumulator eea, final String forumRoleTypeName) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumRoleType = forumControl.getForumRoleTypeByName(forumRoleTypeName);

        if(forumRoleType == null) {
            handleExecutionError(UnknownForumRoleTypeNameException.class, eea, ExecutionErrors.UnknownForumRoleTypeName.name(), forumRoleTypeName);
        }

        return forumRoleType;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final Forum forum, final Party party, final String forumRoleTypeName) {
        var forumRoleType = getForumRoleTypeByName(eea, forumRoleTypeName);

        return isForumRoleTypePermitted(eea, forum, party, forumRoleType);
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final Forum forum, final Party party, final ForumRoleType forumRoleType) {
        var forumControl = Session.getModelController(ForumControl.class);
        var hasForumPartyTypeRoles = forumControl.hasForumPartyTypeRoles(forum, forumRoleType);
        var hasForumPartyRoles = forumControl.hasForumPartyRoles(forum, forumRoleType);
        var permitted = !(hasForumPartyTypeRoles || hasForumPartyRoles);

        if(!permitted) {
            if(party == null) {
                handleExecutionError(PartyRequiredException.class, eea, ExecutionErrors.PartyRequired.name());
            } else {
                var hasForumPartyTypeRole = false;
                var hasForumPartyRole = false;

                if(hasForumPartyTypeRoles) {
                    var partyType = party.getLastDetail().getPartyType();

                    hasForumPartyTypeRole = forumControl.hasForumPartyTypeRole(forum, partyType, forumRoleType);
                }

                if(hasForumPartyRoles) {
                    hasForumPartyRole = forumControl.hasForumPartyRole(forum, party, forumRoleType);
                }

                permitted |= hasForumPartyTypeRole || hasForumPartyRole;

                if(!permitted) {
                    var forumName = forum.getLastDetail().getForumName();
                    var forumRoleTypeName = forumRoleType.getForumRoleTypeName();

                    if(!hasForumPartyRole) {
                        var partyName = party.getLastDetail().getPartyName();

                        handleExecutionError(UnknownForumPartyRoleException.class, eea, ExecutionErrors.UnknownForumPartyRole.name(), forumName, partyName, forumRoleTypeName);
                    }

                    if(!hasForumPartyTypeRole) {
                        var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

                        handleExecutionError(UnknownForumPartyTypeRoleException.class, eea, ExecutionErrors.UnknownForumPartyTypeRole.name(), forumName, partyTypeName, forumRoleTypeName);
                    }
                }
            }
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumThread forumThread, final Party party, final String forumRoleTypeName) {
        var forumRoleType = getForumRoleTypeByName(eea, forumRoleTypeName);
        var permitted = false;

        if(!hasExecutionErrors(eea)) {
            permitted = isForumRoleTypePermitted(eea, forumThread, party, forumRoleType);
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumThread forumThread, final Party party, final ForumRoleType forumRoleType) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumForumThreads = forumControl.getForumForumThreadsByForumThread(forumThread);
        var permitted = false;

        for(var forumForumThread : forumForumThreads) {
            var forum = forumForumThread.getForum();

            permitted |= isForumRoleTypePermitted(eea, forum, party, forumRoleType);

            if(permitted) {
                break;
            }
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumMessage forumMessage, final Party party, final String forumRoleTypeName) {
        var forumRoleType = getForumRoleTypeByName(eea, forumRoleTypeName);
        var permitted = false;

        if(!hasExecutionErrors(eea)) {
            permitted = isForumRoleTypePermitted(eea, forumMessage, party, forumRoleType);
        }

        return permitted;
    }

    public boolean isForumRoleTypePermitted(final ExecutionErrorAccumulator eea, final ForumMessage forumMessage, final Party party, final ForumRoleType forumRoleType) {
        var forumThread = forumMessage.getLastDetail().getForumThread();

        return isForumRoleTypePermitted(eea, forumThread, party, forumRoleType);
    }
    
}
