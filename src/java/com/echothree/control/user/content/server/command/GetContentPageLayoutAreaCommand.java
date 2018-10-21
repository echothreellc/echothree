// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.content.remote.form.GetContentPageLayoutAreaForm;
import com.echothree.control.user.content.remote.result.ContentResultFactory;
import com.echothree.control.user.content.remote.result.GetContentPageLayoutAreaResult;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContentPageLayoutAreaCommand
        extends BaseSingleEntityCommand<ContentPageLayoutArea, GetContentPageLayoutAreaForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageLayoutAreaCommand */
    public GetContentPageLayoutAreaCommand(UserVisitPK userVisitPK, GetContentPageLayoutAreaForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ContentPageLayoutArea getEntity() {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        GetContentPageLayoutAreaResult result = ContentResultFactory.getGetContentPageLayoutAreaResult();
        String contentCollectionName = form.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        ContentPageLayoutArea contentPageLayoutArea = null;
        
        if(contentCollection != null) {
            String contentSectionName = form.getContentSectionName();
            ContentSection contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                String contentPageName = form.getContentPageName();
                ContentPage contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                    ContentPageLayout contentPageLayout = contentPage.getLastDetail().getContentPageLayout();
                    
                    contentPageLayoutArea = contentControl.getContentPageLayoutArea(contentPageLayout, sortOrder);
                    
                    if(contentPageLayoutArea == null) {
                        addExecutionError(ExecutionErrors.UnknownContentPageLayoutArea.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return contentPageLayoutArea;
    }
    
    @Override
    protected BaseResult getTransfer(ContentPageLayoutArea contentPageLayoutArea) {
        GetContentPageLayoutAreaResult result = ContentResultFactory.getGetContentPageLayoutAreaResult();

        if (contentPageLayoutArea != null) {
            ContentControl contentControl = (ContentControl) Session.getModelController(ContentControl.class);

            result.setContentPageLayoutArea(contentControl.getContentPageLayoutAreaTransfer(getUserVisit(), contentPageLayoutArea));
        }

        return result;
    }
    
}
