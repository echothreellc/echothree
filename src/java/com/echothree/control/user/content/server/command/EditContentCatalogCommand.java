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

import com.echothree.control.user.content.common.edit.ContentCatalogEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCatalogForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCatalogResult;
import com.echothree.control.user.content.common.spec.ContentCatalogSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentCatalogCommand */
    public EditContentCatalogCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var contentControl = Session.getModelController(ContentControl.class);
        ContentCatalog contentCatalog = null;
        var contentCollectionName = spec.getContentCollectionName();
        
        contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentCatalogName = spec.getContentCatalogName();

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
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentCatalog(contentControl.getContentCatalogTransfer(getUserVisit(), contentCatalog));
    }
    
    @Override
    public void doLock(ContentCatalogEdit edit, ContentCatalog contentCatalog) {
        var contentControl = Session.getModelController(ContentControl.class);
        var sourceControl = Session.getModelController(SourceControl.class);
        var contentCatalogDescription = contentControl.getContentCatalogDescription(contentCatalog, getPreferredLanguage());
        var contentCatalogDetail = contentCatalog.getLastDetail();
        var offerUse = contentCatalogDetail.getDefaultOfferUse();
        var defaultOfferUseDetail = offerUse.getLastDetail();
        var sources = defaultOfferUse == null ? null : sourceControl.getSourcesByOfferUse(defaultOfferUse); // List of Sources if there are any for the OfferUse
        var defaultSourceName = sources == null ? null : sources.iterator().next().getLastDetail().getSourceName(); // From the List, the first available Source's SourceName

        edit.setContentCatalogName(contentCatalogDetail.getContentCatalogName());
        edit.setDefaultOfferName(defaultSourceName == null ? (defaultOfferUseDetail == null? null: defaultOfferUseDetail.getOffer().getLastDetail().getOfferName()) : null);
        edit.setDefaultUseName(defaultSourceName == null ? (defaultOfferUseDetail == null ? null : defaultOfferUseDetail.getUse().getLastDetail().getUseName()) : null);
        edit.setDefaultSourceName(defaultSourceName);
        edit.setIsDefault(contentCatalogDetail.getIsDefault().toString());
        edit.setSortOrder(contentCatalogDetail.getSortOrder().toString());

        if(contentCatalogDescription != null) {
            edit.setDescription(contentCatalogDescription.getDescription());
        }
    }
    
    OfferUse defaultOfferUse = null;
    
    @Override
    public void canUpdate(ContentCatalog contentCatalog) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCatalogName = edit.getContentCatalogName();
        var duplicateContentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

        if(duplicateContentCatalog == null || contentCatalog.equals(duplicateContentCatalog)) {
            var offerControl = Session.getModelController(OfferControl.class);
            var defaultOfferName = edit.getDefaultOfferName();
            var defaultUseName = edit.getDefaultUseName();
            var defaultSourceName = edit.getDefaultSourceName();

            if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                var defaultOffer = offerControl.getOfferByName(defaultOfferName);

                if(defaultOffer != null) {
                    var useControl = Session.getModelController(UseControl.class);
                    var defaultUse = useControl.getUseByName(defaultUseName);

                    if(defaultUse != null) {
                        var offerUseControl = Session.getModelController(OfferUseControl.class);
                        defaultOfferUse = offerUseControl.getOfferUse(defaultOffer, defaultUse);

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
                var sourceControl = Session.getModelController(SourceControl.class);
                var source = sourceControl.getSourceByName(defaultSourceName);

                if(source != null) {
                    defaultOfferUse = source.getLastDetail().getOfferUse();
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultSourceName.name(), defaultSourceName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
            }

            if(defaultOfferUse != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var defaultOffer = defaultOfferUse.getLastDetail().getOffer();
                var defaultPartyDepartment = partyControl.getPartyDepartment(defaultOffer.getLastDetail().getDepartmentParty());
                var defaultPartyDivision = partyControl.getPartyDivision(defaultPartyDepartment.getDivisionParty());
                var defaultPartyCompany = partyControl.getPartyCompany(defaultPartyDivision.getCompanyParty());

                var catalogOffer = contentCatalog.getLastDetail().getDefaultOfferUse().getLastDetail().getOffer();
                var catalogPartyDepartment = partyControl.getPartyDepartment(catalogOffer.getLastDetail().getDepartmentParty());
                var catalogPartyDivision = partyControl.getPartyDivision(catalogPartyDepartment.getDivisionParty());
                var catalogPartyCompany = partyControl.getPartyCompany(catalogPartyDivision.getCompanyParty());

                if(!defaultPartyCompany.equals(catalogPartyCompany)) {
                    addExecutionError(ExecutionErrors.InvalidOfferCompany.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentCatalogName.name(), contentCatalogName);
        }
    }
    
    @Override
    public void doUpdate(ContentCatalog contentCatalog) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentCatalogDetailValue = contentControl.getContentCatalogDetailValueForUpdate(contentCatalog);
        var contentCatalogDescription = contentControl.getContentCatalogDescriptionForUpdate(contentCatalog, getPreferredLanguage());
        var description = edit.getDescription();

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
            var contentCatalogDescriptionValue = contentControl.getContentCatalogDescriptionValue(contentCatalogDescription);

            contentCatalogDescriptionValue.setDescription(description);
            contentControl.updateContentCatalogDescriptionFromValue(contentCatalogDescriptionValue, partyPK);
        }
    }
    
}

