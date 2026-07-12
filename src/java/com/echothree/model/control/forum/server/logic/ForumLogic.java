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

import com.echothree.control.user.forum.common.spec.ForumUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.forum.common.exception.UnknownForumNameException;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ForumLogic
        extends BaseLogic {

    @Inject
    ForumControl forumControl;

    protected ForumLogic() {
        super();
    }

    public Forum getForumByName(final ExecutionErrorAccumulator eea, final String forumName,
            final EntityPermission entityPermission) {
        var forum = forumControl.getForumByName(forumName, entityPermission);

        if(forum == null) {
            handleExecutionError(UnknownForumNameException.class, eea, ExecutionErrors.UnknownForumName.name(), forumName);
        }

        return forum;
    }

    public Forum getForumByName(final ExecutionErrorAccumulator eea, final String forumName) {
        return getForumByName(eea, forumName, EntityPermission.READ_ONLY);
    }

    public Forum getForumByNameForUpdate(final ExecutionErrorAccumulator eea, final String forumName) {
        return getForumByName(eea, forumName, EntityPermission.READ_WRITE);
    }

    public Forum getForumByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Forum forum = null;
        var forumName = universalSpec.getForumName();
        var parameterCount = (forumName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(forumName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Forum.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        forum = forumControl.getForumByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    forum = getForumByName(eea, forumName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return forum;
    }

    public Forum getForumByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumUniversalSpec universalSpec) {
        return getForumByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Forum getForumByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ForumUniversalSpec universalSpec) {
        return getForumByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
