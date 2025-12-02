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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentPageLayoutAreasForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.factory.ContentPageFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutAreaFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetContentPageLayoutAreasCommand
        extends BasePaginatedMultipleEntitiesCommand<ContentPageLayoutArea, GetContentPageLayoutAreasForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageLayoutAreasCommand */
    public GetContentPageLayoutAreasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    ContentPage contentPage;
    
    @Override
    protected void handleForm() {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

        if(contentCollection != null) {
            var contentSectionName = form.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);

            if(contentSection != null) {
                var contentPageName = form.getContentPageName();

                contentPage = contentControl.getContentPageByName(contentSection, contentPageName);

                if(contentPage == null) {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
    }
    
    @Override
    protected Long getTotalEntities() {
        var contentControl = Session.getModelController(ContentControl.class);

        return hasExecutionErrors() ? null :
                contentControl.countContentPageLayoutAreasByContentPageLayout(contentPage.getLastDetail().getContentPageLayout());
    }
    
    @Override
    protected Collection<ContentPageLayoutArea> getEntities() {
        Collection<ContentPageLayoutArea> contentPageLayoutAreas = null;

        if(!hasExecutionErrors()) {
            var contentControl = Session.getModelController(ContentControl.class);

            contentPageLayoutAreas = contentControl.getContentPageLayoutAreasByContentPageLayout(contentPage.getLastDetail().getContentPageLayout());
        }

        return contentPageLayoutAreas;
    }
    
    @Override
    protected BaseResult getResult(Collection<ContentPageLayoutArea> entities) {
        var result = ContentResultFactory.getGetContentPageLayoutAreasResult();
        
        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            var userVisit = getUserVisit();

            result.setContentPage(contentControl.getContentPageTransfer(userVisit, contentPage));

            if(session.hasLimit(ContentPageLayoutAreaFactory.class)) {
                result.setContentPageLayoutAreaCount(getTotalEntities());
            }

            result.setContentPageLayoutAreas(contentControl.getContentPageLayoutAreaTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
