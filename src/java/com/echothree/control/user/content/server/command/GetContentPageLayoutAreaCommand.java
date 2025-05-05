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

import com.echothree.control.user.content.common.form.GetContentPageLayoutAreaForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContentPageLayoutAreaCommand
        extends BaseSingleEntityCommand<ContentPageLayoutArea, GetContentPageLayoutAreaForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
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
    public GetContentPageLayoutAreaCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ContentPageLayoutArea getEntity() {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        ContentPageLayoutArea contentPageLayoutArea = null;
        
        if(contentCollection != null) {
            var contentSectionName = form.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var contentPageName = form.getContentPageName();
                var contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var contentPageLayout = contentPage.getLastDetail().getContentPageLayout();
                    
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
    protected BaseResult getResult(ContentPageLayoutArea contentPageLayoutArea) {
        var result = ContentResultFactory.getGetContentPageLayoutAreaResult();

        if (contentPageLayoutArea != null) {
            var contentControl = Session.getModelController(ContentControl.class);

            result.setContentPageLayoutArea(contentControl.getContentPageLayoutAreaTransfer(getUserVisit(), contentPageLayoutArea));
        }

        return result;
    }
    
}
