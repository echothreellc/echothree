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

import com.echothree.control.user.content.common.form.GetContentPageAreasForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageArea;
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

public class GetContentPageAreasCommand
        extends BaseMultipleEntitiesCommand<ContentPageArea, GetContentPageAreasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageAreasCommand */
    public GetContentPageAreasCommand(UserVisitPK userVisitPK, GetContentPageAreasForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<ContentPageArea> getEntities() {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        Collection<ContentPageArea> contentPageAreas = null;
        
        if(contentCollection != null) {
            var contentSectionName = form.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var contentPageName = form.getContentPageName();
                var contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    contentPageAreas = contentControl.getContentPageAreasByContentPage(contentPage);
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return contentPageAreas;
    }
    
    @Override
    protected BaseResult getResult(Collection<ContentPageArea> entities) {
        var result = ContentResultFactory.getGetContentPageAreasResult();
                
        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);

            result.setContentPageAreas(contentControl.getContentPageAreaTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
