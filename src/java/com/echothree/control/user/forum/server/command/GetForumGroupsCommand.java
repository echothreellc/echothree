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

import com.echothree.control.user.forum.common.form.GetForumGroupsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.factory.ForumGroupFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<ForumGroup, GetForumGroupsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    ForumControl forumControl;

    /** Creates a new instance of GetForumGroupsCommand */
    public GetForumGroupsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return forumControl.countForumGroups();
    }

    @Override
    protected Collection<ForumGroup> getEntities() {
        return forumControl.getForumGroups();
    }

    @Override
    protected BaseResult getResult(Collection<ForumGroup> entities) {
        var result = ForumResultFactory.getGetForumGroupsResult();

        if(entities != null) {
            if(session.hasLimit(ForumGroupFactory.class)) {
                result.setForumGroupCount(getTotalEntities());
            }

            result.setForumGroups(forumControl.getForumGroupTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
