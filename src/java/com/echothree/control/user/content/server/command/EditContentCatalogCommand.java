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

import com.echothree.control.user.content.common.edit.ContentCatalogEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCatalogForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCatalogResult;
import com.echothree.control.user.content.common.spec.ContentCatalogSpec;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDescription;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.value.ContentCatalogDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCatalogDetailValue;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditContentCatalogCommand
        extends BaseAbstractEditCommand<ContentCatalogSpec, ContentCatalogEdit, EditContentCatalogResult, ContentCatalog, ContentCatalog> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCatalog.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditContentCatalogCommand */
    public EditContentCatalogCommand(UserVisitPK userVisitPK, EditContentCatalogForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCatalogResult getResult() {
        return ContentResultFactory.getEditContentCatalogResult();
    }
    
    @Override
    public ContentCatalogEdit getEdit() {
        return ContentEditFactory.getContentCatalogEdit();
    }
    
    ContentCollection contentCollection = null;
    
    @Override
    public ContentCatalog getEntity(EditContentCatalogResult result) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentCatalog contentCatalog = null;
        String contentCollectionName = spec.getContentCollectionName();
        
        contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            String contentCatalogName = spec.getContentCatalogName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            } else { // EditMode.UPDATE
                contentCatalog = contentControl.getContentCatalogByNameForUpdate(contentCollection, contentCatalogName);
            }

            if(contentCatalog != null) {
                result.setContentCatalog(contentControl.getContentCatalogTransfer(getUserVisit(), contentCatalog));
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCatalog;
    }
    
    @Override
    public ContentCatalog getLockEntity(ContentCatalog contentCatalog) {
        return contentCatalog;
    }
    
    @Override
    public void fillInResult(EditContentCatalogResult result, ContentCatalog contentCatalog) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        result.setContentCatalog(contentControl.getContentCatalogTransfer(getUserVisit(), contentCatalog));
    }
    
    @Override
    public void doLock(ContentCatalogEdit edit, ContentCatalog contentCatalog) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        ContentCatalogDescription contentCatalogDescription = contentControl.getContentCatalogDescription(contentCatalog, getPreferredLanguage());
        ContentCatalogDetail contentCatalogDetail = contentCatalog.getLastDetail();
        OfferUse offerUse = contentCatalogDetail.getDefaultOfferUse();
        OfferUseDetail defaultOfferUseDetail = offerUse.getLastDetail();
        List<Source> sources = offerControl.getSourcesByOfferUse(offerUse);

        edit.setContentCatalogName(contentCatalogDetail.getContentCatalogName());
        edit.setDefaultOfferName(defaultOfferUseDetail.getOffer().getLastDetail().getOfferName());
        edit.setDefaultUseName(defaultOfferUseDetail.getUse().getLastDetail().getUseName());
        edit.setDefaultSourceName(sources.iterator().next().getLastDetail().getSourceName());
        edit.setIsDefault(contentCatalogDetail.getIsDefault().toString());
        edit.setSortOrder(contentCatalogDetail.getSortOrder().toString());

        if(contentCatalogDescription != null) {
            edit.setDescription(contentCatalogDescription.getDescription());
        }
    }
    
    OfferUse defaultOfferUse = null;
    
    @Override
    public void canUpdate(ContentCatalog contentCatalog) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        String contentCatalogName = edit.getContentCatalogName();
        ContentCatalog duplicateContentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

        if(duplicateContentCatalog == null || contentCatalog.equals(duplicateContentCatalog)) {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            String defaultOfferName = edit.getDefaultOfferName();
            String defaultUseName = edit.getDefaultUseName();
            String defaultSourceName = edit.getDefaultSourceName();

            if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                Offer defaultOffer = offerControl.getOfferByName(defaultOfferName);

                if(defaultOffer != null) {
                    Use defaultUse = offerControl.getUseByName(defaultUseName);

                    if(defaultUse != null) {
                        defaultOfferUse = offerControl.getOfferUse(defaultOffer, defaultUse);

                        if(defaultOfferUse == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultOfferUse.name());
                        }
                    }  else {
                        addExecutionError(ExecutionErrors.UnknownDefaultUseName.name(), defaultUseName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultOfferName.name(), defaultOfferName);
                }
            } else if(defaultOfferName == null && defaultUseName == null && defaultSourceName != null) {
                Source source = offerControl.getSourceByName(defaultSourceName);

                if(source != null) {
                    defaultOfferUse = source.getLastDetail().getOfferUse();
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultSourceName.name(), defaultSourceName);
                }
            } else {
                // If all three parameters are null, then try to get the default Source and use its OfferUse.
                Source source = offerControl.getDefaultSource();

                if(source != null) {
                    defaultOfferUse = source.getLastDetail().getOfferUse();
                } else {
                    addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
                }
            }

            if(defaultOfferUse != null) {
                if(defaultOfferUse != null) {
                    var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    Offer defaultOffer = defaultOfferUse.getLastDetail().getOffer();
                    PartyDepartment defaultPartyDepartment = partyControl.getPartyDepartment(defaultOffer.getLastDetail().getDepartmentParty());
                    PartyDivision defaultPartyDivision = partyControl.getPartyDivision(defaultPartyDepartment.getDivisionParty());
                    PartyCompany defaultPartyCompany = partyControl.getPartyCompany(defaultPartyDivision.getCompanyParty());

                    Offer catalogOffer = contentCatalog.getLastDetail().getDefaultOfferUse().getLastDetail().getOffer();
                    PartyDepartment catalogPartyDepartment = partyControl.getPartyDepartment(catalogOffer.getLastDetail().getDepartmentParty());
                    PartyDivision catalogPartyDivision = partyControl.getPartyDivision(catalogPartyDepartment.getDivisionParty());
                    PartyCompany catalogPartyCompany = partyControl.getPartyCompany(catalogPartyDivision.getCompanyParty());

                    if(!defaultPartyCompany.equals(catalogPartyCompany)) {
                        addExecutionError(ExecutionErrors.InvalidOfferCompany.name());
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentCatalogName.name(), contentCatalogName);
        }
    }
    
    @Override
    public void doUpdate(ContentCatalog contentCatalog) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        PartyPK partyPK = getPartyPK();
        ContentCatalogDetailValue contentCatalogDetailValue = contentControl.getContentCatalogDetailValueForUpdate(contentCatalog);
        ContentCatalogDescription contentCatalogDescription = contentControl.getContentCatalogDescriptionForUpdate(contentCatalog, getPreferredLanguage());
        String description = edit.getDescription();

        contentCatalogDetailValue.setContentCatalogName(edit.getContentCatalogName());
        contentCatalogDetailValue.setDefaultOfferUsePK(defaultOfferUse.getPrimaryKey());
        contentCatalogDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contentCatalogDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contentControl.updateContentCatalogFromValue(contentCatalogDetailValue, partyPK);

        if(contentCatalogDescription == null && description != null) {
            contentControl.createContentCatalogDescription(contentCatalog, getPreferredLanguage(), description, partyPK);
        } else if(contentCatalogDescription != null && description == null) {
            contentControl.deleteContentCatalogDescription(contentCatalogDescription, partyPK);
        } else if(contentCatalogDescription != null && description != null) {
            ContentCatalogDescriptionValue contentCatalogDescriptionValue = contentControl.getContentCatalogDescriptionValue(contentCatalogDescription);

            contentCatalogDescriptionValue.setDescription(description);
            contentControl.updateContentCatalogDescriptionFromValue(contentCatalogDescriptionValue, partyPK);
        }
    }
    
}

