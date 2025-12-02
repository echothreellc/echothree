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

import com.echothree.control.user.content.common.edit.ContentCollectionEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCollectionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCollectionResult;
import com.echothree.control.user.content.common.spec.ContentCollectionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
public class EditContentCollectionCommand
        extends BaseAbstractEditCommand<ContentCollectionSpec, ContentCollectionEdit, EditContentCollectionResult, ContentCollection, ContentCollection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCollection.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentCollectionCommand */
    public EditContentCollectionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCollectionResult getResult() {
        return ContentResultFactory.getEditContentCollectionResult();
    }
    
    @Override
    public ContentCollectionEdit getEdit() {
        return ContentEditFactory.getContentCollectionEdit();
    }
    
    @Override
    public ContentCollection getEntity(EditContentCollectionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentCollection contentCollection;
        var contentCollectionName = spec.getContentCollectionName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        } else { // EditMode.UPDATE
            contentCollection = contentControl.getContentCollectionByNameForUpdate(contentCollectionName);
        }

        if(contentCollection != null) {
            result.setContentCollection(contentControl.getContentCollectionTransfer(getUserVisit(), contentCollection));
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCollection;
    }
    
    @Override
    public ContentCollection getLockEntity(ContentCollection contentCollection) {
        return contentCollection;
    }
    
    @Override
    public void fillInResult(EditContentCollectionResult result, ContentCollection contentCollection) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentCollection(contentControl.getContentCollectionTransfer(getUserVisit(), contentCollection));
    }
    
    @Override
    public void doLock(ContentCollectionEdit edit, ContentCollection contentCollection) {
        var contentControl = Session.getModelController(ContentControl.class);
        var sourceControl = Session.getModelController(SourceControl.class);
        var contentCollectionDescription = contentControl.getContentCollectionDescription(contentCollection, getPreferredLanguage());
        var contentCollectionDetail = contentCollection.getLastDetail();
        var offerUse = contentCollectionDetail.getDefaultOfferUse();
        var defaultOfferUseDetail = offerUse.getLastDetail();
        var sources = sourceControl.getSourcesByOfferUse(offerUse);

        edit.setContentCollectionName(contentCollectionDetail.getContentCollectionName());
        edit.setDefaultOfferName(defaultOfferUseDetail.getOffer().getLastDetail().getOfferName());
        edit.setDefaultUseName(defaultOfferUseDetail.getUse().getLastDetail().getUseName());
        edit.setDefaultSourceName(sources.iterator().next().getLastDetail().getSourceName());

        if(contentCollectionDescription != null) {
            edit.setDescription(contentCollectionDescription.getDescription());
        }
    }
    
    OfferUse defaultOfferUse = null;
    
    @Override
    public void canUpdate(ContentCollection contentCollection) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = edit.getContentCollectionName();
        var duplicateContentCollection = contentControl.getContentCollectionByName(contentCollectionName);

        if(duplicateContentCollection == null || contentCollection.equals(duplicateContentCollection)) {
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
                var sourceControl = Session.getModelController(SourceControl.class);
                // If all three parameters are null, then try to get the default Source and use its OfferUse.
                var source = sourceControl.getDefaultSource();

                if(source != null) {
                    defaultOfferUse = source.getLastDetail().getOfferUse();
                } else {
                    addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
                }
            }

            if(defaultOfferUse != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var defaultOffer = defaultOfferUse.getLastDetail().getOffer();
                var defaultPartyDepartment = partyControl.getPartyDepartment(defaultOffer.getLastDetail().getDepartmentParty());
                var defaultPartyDivision = partyControl.getPartyDivision(defaultPartyDepartment.getDivisionParty());
                var defaultPartyCompany = partyControl.getPartyCompany(defaultPartyDivision.getCompanyParty());

                var collectionOffer = contentCollection.getLastDetail().getDefaultOfferUse().getLastDetail().getOffer();
                var collectionPartyDepartment = partyControl.getPartyDepartment(collectionOffer.getLastDetail().getDepartmentParty());
                var collectionPartyDivision = partyControl.getPartyDivision(collectionPartyDepartment.getDivisionParty());
                var collectionPartyCompany = partyControl.getPartyCompany(collectionPartyDivision.getCompanyParty());

                if(!defaultPartyCompany.equals(collectionPartyCompany)) {
                    addExecutionError(ExecutionErrors.InvalidOfferCompany.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentCollectionName.name(), contentCollectionName);
        }
    }
    
    @Override
    public void doUpdate(ContentCollection contentCollection) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentCollectionDetailValue = contentControl.getContentCollectionDetailValueForUpdate(contentCollection);
        var contentCollectionDescription = contentControl.getContentCollectionDescriptionForUpdate(contentCollection, getPreferredLanguage());
        var description = edit.getDescription();

        contentCollectionDetailValue.setContentCollectionName(edit.getContentCollectionName());
        contentCollectionDetailValue.setDefaultOfferUsePK(defaultOfferUse.getPrimaryKey());

        contentControl.updateContentCollectionFromValue(contentCollectionDetailValue, partyPK);

        if(contentCollectionDescription == null && description != null) {
            contentControl.createContentCollectionDescription(contentCollection, getPreferredLanguage(), description, partyPK);
        } else if(contentCollectionDescription != null && description == null) {
            contentControl.deleteContentCollectionDescription(contentCollectionDescription, partyPK);
        } else if(contentCollectionDescription != null && description != null) {
            var contentCollectionDescriptionValue = contentControl.getContentCollectionDescriptionValue(contentCollectionDescription);

            contentCollectionDescriptionValue.setDescription(description);
            contentControl.updateContentCollectionDescriptionFromValue(contentCollectionDescriptionValue, partyPK);
        }
    }
    
}
