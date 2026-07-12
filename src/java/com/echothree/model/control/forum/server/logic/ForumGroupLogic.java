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

import com.echothree.model.control.forum.common.exception.UnknownForumGroupNameException;
import com.echothree.control.user.forum.common.spec.ForumGroupUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ForumGroupLogic
        extends BaseLogic {

    @Inject
    ForumControl forumControl;

    protected ForumGroupLogic() {
        super();
    }

    public static ForumGroupLogic getInstance() {
        return CDI.current().select(ForumGroupLogic.class).get();
    }

    public ForumGroup getForumGroupByName(final ExecutionErrorAccumulator eea, final String forumGroupName,
            final EntityPermission entityPermission) {
        var forumGroup = forumControl.getForumGroupByName(forumGroupName, entityPermission);

        if(forumGroup == null) {
            handleExecutionError(UnknownForumGroupNameException.class, eea, ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
        }

        return forumGroup;
    }

    public ForumGroup getForumGroupByName(final ExecutionErrorAccumulator eea, final String forumGroupName) {
        return getForumGroupByName(eea, forumGroupName, EntityPermission.READ_ONLY);
    }

    public ForumGroup getForumGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final String forumGroupName) {
        return getForumGroupByName(eea, forumGroupName, EntityPermission.READ_WRITE);
    }

    public ForumGroup getForumGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumGroupUniversalSpec universalSpec, final EntityPermission entityPermission) {
        ForumGroup forumGroup = null;
        var forumGroupName = universalSpec.getForumGroupName();
        var parameterCount = (forumGroupName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(forumGroupName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ForumGroup.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        forumGroup = forumControl.getForumGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    forumGroup = getForumGroupByName(eea, forumGroupName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return forumGroup;
    }

    public ForumGroup getForumGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ForumGroupUniversalSpec universalSpec) {
        return getForumGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ForumGroup getForumGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ForumGroupUniversalSpec universalSpec) {
        return getForumGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
