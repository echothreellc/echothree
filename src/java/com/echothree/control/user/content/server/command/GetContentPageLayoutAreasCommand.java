// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.content.common.result.GetContentPageLayoutAreasResult;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetContentPageLayoutAreasCommand
        extends BaseMultipleEntitiesCommand<ContentPageLayoutArea, GetContentPageLayoutAreasForm> {
    
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
    public GetContentPageLayoutAreasCommand(UserVisitPK userVisitPK, GetContentPageLayoutAreasForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected Collection<ContentPageLayoutArea> getEntities() {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        GetContentPageLayoutAreasResult result = ContentResultFactory.getGetContentPageLayoutAreasResult();
        String contentCollectionName = form.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        Collection<ContentPageLayoutArea> contentPageLayoutAreas = null;
        
        if(contentCollection != null) {
            String contentSectionName = form.getContentSectionName();
            ContentSection contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                String contentPageName = form.getContentPageName();
                ContentPage contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    contentPageLayoutAreas = contentControl.getContentPageLayoutAreasByContentPageLayout(contentPage.getLastDetail().getContentPageLayout());
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return contentPageLayoutAreas;
    }
    
    @Override
    protected BaseResult getTransfers(Collection<ContentPageLayoutArea> entities) {
        GetContentPageLayoutAreasResult result = ContentResultFactory.getGetContentPageLayoutAreasResult();
        
        if(entities != null) {
            ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
            
            result.setContentPageLayoutAreas(contentControl.getContentPageLayoutAreaTransfers(getUserVisit(), entities));
        }
        
        return result;
    }
    
}
