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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentForumsForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentForum;
import com.echothree.model.data.content.server.factory.ContentForumFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetContentForumsCommand
        extends BasePaginatedMultipleEntitiesCommand<ContentForum, GetContentForumsForm> {

    @Inject
    ContentControl contentControl;
    @Inject
    ContentLogic contentLogic;

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetContentForumsCommand */
    public GetContentForumsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private ContentCollection contentCollection;

    @Override
    protected void handleForm() {
        contentCollection = contentLogic.getContentCollectionByName(this, form.getContentCollectionName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : contentControl.countContentForumsByContentCollection(contentCollection);
    }

    @Override
    protected Collection<ContentForum> getEntities() {
        return hasExecutionErrors() ? null : contentControl.getContentForums(contentCollection);
    }

    @Override
    protected BaseResult getResult(Collection<ContentForum> entities) {
        var result = ContentResultFactory.getGetContentForumsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setContentCollection(contentControl.getContentCollectionTransfer(userVisit, contentCollection));

            if(session.hasLimit(ContentForumFactory.class)) {
                result.setContentForumCount(getTotalEntities());
            }

            result.setContentForums(contentControl.getContentForumTransfers(userVisit, entities));
        }

        return result;
    }

}
