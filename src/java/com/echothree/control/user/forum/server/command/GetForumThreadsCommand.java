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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.GetForumThreadsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.factory.ForumThreadFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetForumThreadsCommand
        extends BaseSimpleCommand<GetForumThreadsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("IncludeFutureForumThreads", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetForumThreadsCommand */
    public GetForumThreadsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ForumResultFactory.getGetForumThreadsResult();
        var forumName = form.getForumName();
        var parameterCount = (forumName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var forumControl = Session.getModelController(ForumControl.class);
            Forum forum = null;

            if(forumName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Forum.name());
                
                if(!hasExecutionErrors()) {
                    forum = forumControl.getForumByEntityInstance(entityInstance);
                }
            } else {
                forum = forumControl.getForumByName(forumName);

                if(forum == null) {
                    addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                }
            }

            // If the UUID for the Forum is specified, then bypass the ForumRoleType check.
            if(!hasExecutionErrors()) {
                if(form.getUuid() != null || ForumLogic.getInstance().isForumRoleTypePermitted(this, forum, getParty(), ForumConstants.ForumRoleType_READER)) {
                    var includeFutureForumThreads = Boolean.parseBoolean(form.getIncludeFutureForumThreads());
                    var userVisit = getUserVisit();

                    if(session.hasLimit(ForumThreadFactory.class)) {
                        result.setForumThreadCount(forumControl.countForumThreadsByForum(forum, includeFutureForumThreads));
                    }

                    result.setForum(forumControl.getForumTransfer(userVisit, forum));
                    result.setForumThreads(forumControl.getForumThreadTransfersByForum(userVisit, forum, includeFutureForumThreads));
                } else {
                    addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
