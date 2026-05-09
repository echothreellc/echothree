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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.GetForumMessagesForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumMessagesCommand
        extends BasePaginatedMultipleEntitiesCommand<ForumMessage, GetForumMessagesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    @Inject
    ForumLogic forumLogic;

    /** Creates a new instance of GetForumMessagesCommand */
    public GetForumMessagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    ForumThread forumThread;

    @Override
    protected void handleForm() {
        var forumThreadName = form.getForumThreadName();
        var parameterCount = (forumThreadName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(forumThreadName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.ForumThread.name());

                if(!hasExecutionErrors()) {
                    forumThread = forumControl.getForumThreadByEntityInstance(entityInstance);
                }
            } else {
                forumThread = forumControl.getForumThreadByName(forumThreadName);

                if(forumThread == null) {
                    addExecutionError(ExecutionErrors.UnknownForumThreadName.name(), forumThreadName);
                }
            }

            if(!hasExecutionErrors()) {
                if(forumThread.getLastDetail().getPostedTime() > session.getStartTime()
                        && (getParty() == null || !getPartyTypeName().equals(PartyTypes.EMPLOYEE.name()))) {
                    addExecutionError(ExecutionErrors.UnpublishedForumThread.name(), forumThread.getLastDetail().getForumThreadName());
                } else if(form.getUuid() == null && !forumLogic.isForumRoleTypePermitted(this, forumThread, getParty(), ForumConstants.ForumRoleType_READER)) {
                    forumThread = null; // TODO: This should be an ExecutionError also.
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return forumThread == null ? null : forumControl.countForumMessagesByForumThread(forumThread);
    }

    @Override
    protected Collection<ForumMessage> getEntities() {
        return forumThread == null ? null : forumControl.getForumMessagesByForumThread(forumThread);
    }

    @Override
    protected BaseResult getResult(Collection<ForumMessage> entities) {
        var result = ForumResultFactory.getGetForumMessagesResult();

        if(entities != null) {
            result.setForumThread(forumControl.getForumThreadTransfer(getUserVisit(), forumThread));

            if(session.hasLimit(ForumMessageFactory.class)) {
                result.setForumMessageCount(getTotalEntities());
            }

            result.setForumMessages(forumControl.getForumMessageTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
