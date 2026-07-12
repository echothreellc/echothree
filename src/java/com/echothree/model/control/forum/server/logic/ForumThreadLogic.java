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

import com.echothree.control.user.forum.common.spec.ForumThreadUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.forum.common.exception.UnknownForumThreadNameException;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ForumThreadLogic
        extends BaseLogic {

    @Inject
    ForumControl forumControl;

    protected ForumThreadLogic() {
        super();
    }

    public ForumThread getForumThreadByName(final ExecutionErrorAccumulator eea, final String forumThreadName,
            final EntityPermission entityPermission) {
        var forumThread = forumControl.getForumThreadByName(forumThreadName, entityPermission);

        if(forumThread == null) {
            handleExecutionError(UnknownForumThreadNameException.class, eea, ExecutionErrors.UnknownForumThreadName.name(), forumThreadName);
        }

        return forumThread;
    }

    public ForumThread getForumThreadByName(final ExecutionErrorAccumulator eea, final String forumThreadName) {
        return getForumThreadByName(eea, forumThreadName, EntityPermission.READ_ONLY);
    }

    public ForumThread getForumThreadByNameForUpdate(final ExecutionErrorAccumulator eea, final String forumThreadName) {
        return getForumThreadByName(eea, forumThreadName, EntityPermission.READ_WRITE);
    }

    public ForumThread getForumThreadByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumThreadUniversalSpec universalSpec, final EntityPermission entityPermission) {
        ForumThread forumThread = null;
        var forumThreadName = universalSpec.getForumThreadName();
        var parameterCount = (forumThreadName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(forumThreadName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ForumThread.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        forumThread = forumControl.getForumThreadByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    forumThread = getForumThreadByName(eea, forumThreadName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return forumThread;
    }

    public ForumThread getForumThreadByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumThreadUniversalSpec universalSpec) {
        return getForumThreadByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ForumThread getForumThreadByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ForumThreadUniversalSpec universalSpec) {
        return getForumThreadByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
