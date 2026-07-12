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

import com.echothree.control.user.forum.common.spec.ForumMessageUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.forum.common.exception.UnknownForumMessageNameException;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ForumMessageLogic
        extends BaseLogic {

    @Inject
    ForumControl forumControl;

    protected ForumMessageLogic() {
        super();
    }

    public ForumMessage getForumMessageByName(final ExecutionErrorAccumulator eea, final String forumMessageName,
            final EntityPermission entityPermission) {
        var forumMessage = forumControl.getForumMessageByName(forumMessageName, entityPermission);

        if(forumMessage == null) {
            handleExecutionError(UnknownForumMessageNameException.class, eea, ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
        }

        return forumMessage;
    }

    public ForumMessage getForumMessageByName(final ExecutionErrorAccumulator eea, final String forumMessageName) {
        return getForumMessageByName(eea, forumMessageName, EntityPermission.READ_ONLY);
    }

    public ForumMessage getForumMessageByNameForUpdate(final ExecutionErrorAccumulator eea, final String forumMessageName) {
        return getForumMessageByName(eea, forumMessageName, EntityPermission.READ_WRITE);
    }

    public ForumMessage getForumMessageByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumMessageUniversalSpec universalSpec, final EntityPermission entityPermission) {
        ForumMessage forumMessage = null;
        var forumMessageName = universalSpec.getForumMessageName();
        var parameterCount = (forumMessageName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(forumMessageName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ForumMessage.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        forumMessage = forumControl.getForumMessageByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    forumMessage = getForumMessageByName(eea, forumMessageName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return forumMessage;
    }

    public ForumMessage getForumMessageByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumMessageUniversalSpec universalSpec) {
        return getForumMessageByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ForumMessage getForumMessageByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ForumMessageUniversalSpec universalSpec) {
        return getForumMessageByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
