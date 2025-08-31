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

import com.echothree.control.user.content.common.form.CreateContentCollectionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.common.ContentSections;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateContentCollectionCommand
        extends BaseSimpleCommand<CreateContentCollectionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCollection.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateContentCollectionCommand */
    public CreateContentCollectionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ContentResultFactory.getCreateContentCollectionResult();
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

        if(contentCollection == null) {
            var offerControl = Session.getModelController(OfferControl.class);
            var defaultOfferName = form.getDefaultOfferName();
            var defaultUseName = form.getDefaultUseName();
            var defaultSourceName = form.getDefaultSourceName();
            OfferUse defaultOfferUse = null;

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
                    } else {
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
                var description = form.getDescription();
                var partyPK = getPartyPK();

                contentCollection = contentControl.createContentCollection(contentCollectionName, defaultOfferUse, partyPK);
                contentControl.createContentSection(contentCollection, ContentSections.ROOT.toString(), null, false, 0, partyPK);

                if(description != null) {
                    var language = getPreferredLanguage();

                    contentControl.createContentCollectionDescription(contentCollection, language, description, partyPK);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentCollectionName.name(), contentCollectionName);
        }

        if(contentCollection != null) {
            result.setContentCollectionName(contentCollection.getLastDetail().getContentCollectionName());
            result.setEntityRef(contentCollection.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
