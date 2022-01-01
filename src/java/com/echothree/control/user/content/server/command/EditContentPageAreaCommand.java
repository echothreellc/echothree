// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.edit.ContentPageAreaEdit;
import com.echothree.control.user.content.common.form.EditContentPageAreaForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentPageAreaResult;
import com.echothree.control.user.content.common.spec.ContentPageAreaSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaBlob;
import com.echothree.model.data.content.server.entity.ContentPageAreaClob;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import com.echothree.model.data.content.server.entity.ContentPageAreaString;
import com.echothree.model.data.content.server.entity.ContentPageAreaUrl;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.value.ContentPageAreaDetailValue;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditContentPageAreaCommand
        extends BaseAbstractEditCommand<ContentPageAreaSpec, ContentPageAreaEdit, EditContentPageAreaResult, ContentPageArea, ContentPageArea> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPageArea.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("ContentPageAreaClob", FieldType.STRING, false, 1L, null),
                new FieldDefinition("ContentPageAreaUrl", FieldType.URL, false, 1L, 200L)
                ));
    }
    
    /** Creates a new instance of EditContentPageAreaCommand */
    public EditContentPageAreaCommand(UserVisitPK userVisitPK, EditContentPageAreaForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentPageAreaResult getResult() {
        return ContentResultFactory.getEditContentPageAreaResult();
    }
    
    @Override
    public ContentPageAreaEdit getEdit() {
        return ContentEditFactory.getContentPageAreaEdit();
    }
    
    @Override
    public ContentPageArea getEntity(EditContentPageAreaResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentPageArea contentPageArea = null;
        String contentCollectionName = spec.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            String contentSectionName = spec.getContentSectionName();
            ContentSection contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                String contentPageName = spec.getContentPageName();
                ContentPage contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    Integer sortOrder = Integer.valueOf(spec.getSortOrder());
                    ContentPageLayout contentPageLayout = contentPage.getLastDetail().getContentPageLayout();
                    ContentPageLayoutArea contentPageLayoutArea = contentControl.getContentPageLayoutArea(contentPageLayout, sortOrder);
                    
                    if(contentPageLayoutArea != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        String languageIsoName = spec.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                contentPageArea = contentControl.getContentPageArea(contentPage, contentPageLayoutArea, language);
                            } else { // EditMode.UPDATE
                                contentPageArea = contentControl.getContentPageAreaForUpdate(contentPage, contentPageLayoutArea, language);
                            }

                            if(contentPageArea == null) {
                                addExecutionError(ExecutionErrors.UnknownContentPageArea.name());
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

        return contentPageArea;
    }
    
    @Override
    public ContentPageArea getLockEntity(ContentPageArea contentPageArea) {
        return contentPageArea;
    }
    
    @Override
    public void fillInResult(EditContentPageAreaResult result, ContentPageArea contentPageArea) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentPageArea(contentControl.getContentPageAreaTransfer(getUserVisit(), contentPageArea));
    }
    
    @Override
    public void doLock(ContentPageAreaEdit edit, ContentPageArea contentPageArea) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentPageAreaDetail contentPageAreaDetail = contentPageArea.getLastDetail();
        ContentPageAreaBlob contentPageAreaBlob = contentControl.getContentPageAreaBlob(contentPageAreaDetail);
        ContentPageAreaClob contentPageAreaClob = contentControl.getContentPageAreaClob(contentPageAreaDetail);
        ContentPageAreaString contentPageAreaString = contentControl.getContentPageAreaString(contentPageAreaDetail);
        ContentPageAreaUrl contentPageAreaUrl = contentControl.getContentPageAreaUrl(contentPageAreaDetail);

        edit.setMimeTypeName(contentPageArea.getLastDetail().getMimeType().getLastDetail().getMimeTypeName());

        if(contentPageAreaBlob != null) {
            edit.setContentPageAreaBlob(contentPageAreaBlob.getBlob());
        }

        if(contentPageAreaClob != null) {
            edit.setContentPageAreaClob(contentPageAreaClob.getClob());
        }

        if(contentPageAreaString != null) {
            edit.setDescription(contentPageAreaString.getString());
        }

        if(contentPageAreaUrl != null) {
            edit.setContentPageAreaUrl(contentPageAreaUrl.getUrl());
        }
    }
    
   MimeType mimeType = null;
    
    @Override
    public void canUpdate(ContentPageArea contentPageArea) {
        var coreControl = getCoreControl();
        String mimeTypeName = edit.getMimeTypeName();
        
        mimeType = coreControl.getMimeTypeByName(mimeTypeName);

        if(mimeType == null) {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }
    }
    
    @Override
    public void doUpdate(ContentPageArea contentPageArea) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentPageAreaDetailValue contentPageAreaDetailValue = contentControl.getContentPageAreaDetailValueForUpdate(contentPageArea);
        String description = edit.getDescription();
        ByteArray contentPageAreaBlob = edit.getContentPageAreaBlob();
        String contentPageAreaClob = edit.getContentPageAreaClob();
        String contentPageAreaUrl = edit.getContentPageAreaUrl();

        contentPageAreaDetailValue.setMimeTypePK(mimeType.getPrimaryKey());

        ContentPageAreaDetail contentPageAreaDetail = contentControl.updateContentPageAreaFromValue(contentPageAreaDetailValue, true, getPartyPK());

        if(contentPageAreaBlob != null) {
            contentControl.createContentPageAreaBlob(contentPageAreaDetail, contentPageAreaBlob);
        }

        if(contentPageAreaClob != null) {
            contentControl.createContentPageAreaClob(contentPageAreaDetail, contentPageAreaClob);
        }

        if(contentPageAreaUrl != null) {
            contentControl.createContentPageAreaUrl(contentPageAreaDetail, contentPageAreaUrl);
        }

        if(description != null) {
            contentControl.createContentPageAreaString(contentPageAreaDetail, description);
        }
    }
    
}
