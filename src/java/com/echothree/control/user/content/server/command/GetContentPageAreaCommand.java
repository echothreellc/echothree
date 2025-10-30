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

import com.echothree.control.user.content.common.form.GetContentPageAreaForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.content.server.entity.ContentPageArea;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetContentPageAreaCommand
        extends BaseSingleEntityCommand<ContentPageArea, GetContentPageAreaForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageAreaCommand */
    public GetContentPageAreaCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ContentPageArea getEntity() {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        ContentPageArea contentPageArea = null;

        if(contentCollection != null) {
            var contentSectionName = form.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var contentPageName = form.getContentPageName();
                var contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var contentPageLayout = contentPage.getLastDetail().getContentPageLayout();
                    var contentPageLayoutArea = contentControl.getContentPageLayoutArea(contentPageLayout, sortOrder);
                    
                    if(contentPageLayoutArea != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = form.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            contentPageArea = contentControl.getContentPageArea(contentPage, contentPageLayoutArea, language);
                            
                            if(contentPageArea == null) {
                                addExecutionError(ExecutionErrors.UnknownContentPageArea.name(), contentCollectionName, contentSectionName, contentPageName,
                                        sortOrder.toString(), languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentPageLayoutArea.name(), contentCollectionName, contentSectionName, contentPageName,
                                sortOrder.toString());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentCollectionName, contentSectionName, contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentCollectionName, contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return contentPageArea;
    }
    
    @Override
    protected BaseResult getResult(ContentPageArea contentPageArea) {
        var result = ContentResultFactory.getGetContentPageAreaResult();

        if (contentPageArea != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            
            result.setContentPageArea(contentControl.getContentPageAreaTransfer(getUserVisit(), contentPageArea));
        }

        return result;
    }
    
}
