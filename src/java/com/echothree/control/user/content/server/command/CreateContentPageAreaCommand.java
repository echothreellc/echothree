// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.content.common.form.CreateContentPageAreaForm;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateContentPageAreaCommand
        extends BaseSimpleCommand<CreateContentPageAreaForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPageArea.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                // ContentPageAreaBlob is not validated
                new FieldDefinition("ContentPageAreaClob", FieldType.STRING, false, 1L, null),
                new FieldDefinition("ContentPageAreaUrl", FieldType.URL, false, 1L, 200L)
                ));
    }
    
    /** Creates a new instance of CreateContentPageAreaCommand */
    public CreateContentPageAreaCommand(UserVisitPK userVisitPK, CreateContentPageAreaForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        String contentCollectionName = form.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            String contentSectionName = form.getContentSectionName();
            ContentSection contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                String contentPageName = form.getContentPageName();
                ContentPage contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                    ContentPageLayout contentPageLayout = contentPage.getLastDetail().getContentPageLayout();
                    ContentPageLayoutArea contentPageLayoutArea = contentControl.getContentPageLayoutArea(contentPageLayout, sortOrder);
                    
                    if(contentPageLayoutArea != null) {
                        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                        String languageIsoName = form.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            ContentPageArea contentPageArea = contentControl.getContentPageArea(contentPage, contentPageLayoutArea, language);
                            
                            if(contentPageArea == null) {
                                var coreControl = getCoreControl();
                                String mimeTypeName = form.getMimeTypeName();
                                MimeType mimeType = coreControl.getMimeTypeByName(mimeTypeName);
                                
                                if(mimeType != null) {
                                    String description = form.getDescription();
                                    ByteArray contentPageAreaBlob = form.getContentPageAreaBlob();
                                    String contentPageAreaClob = form.getContentPageAreaClob();
                                    String contentPageAreaUrl = form.getContentPageAreaUrl();
                                    
                                    contentPageArea = contentControl.createContentPageArea(contentPage, contentPageLayoutArea, language, mimeType, getPartyPK());
                                    ContentPageAreaDetail contentPageAreaDetail = contentPageArea.getLastDetail();
                                    
                                    if(contentPageAreaBlob != null)
                                        contentControl.createContentPageAreaBlob(contentPageAreaDetail, contentPageAreaBlob);
                                    
                                    if(contentPageAreaClob != null)
                                        contentControl.createContentPageAreaClob(contentPageAreaDetail, contentPageAreaClob);
                                    
                                    if(contentPageAreaUrl != null)
                                        contentControl.createContentPageAreaUrl(contentPageAreaDetail, contentPageAreaUrl);
                                    
                                    if(description != null)
                                        contentControl.createContentPageAreaString(contentPageAreaDetail, description);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateContentPageArea.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
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
        
        return null;
    }
    
}
