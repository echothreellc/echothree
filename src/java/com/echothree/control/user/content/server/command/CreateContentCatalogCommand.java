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

import com.echothree.control.user.content.common.form.CreateContentCatalogForm;
import com.echothree.model.control.content.common.ContentCategories;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateContentCatalogCommand
        extends BaseSimpleCommand<CreateContentCatalogForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCatalog.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateContentCatalogCommand */
    public CreateContentCatalogCommand(UserVisitPK userVisitPK, CreateContentCatalogForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        String contentCollectionName = form.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            String contentCatalogName = form.getContentCatalogName();
            ContentCatalog contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            
            if(contentCatalog == null) {
                var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
                String defaultOfferName = form.getDefaultOfferName();
                String defaultUseName = form.getDefaultUseName();
                String defaultSourceName = form.getDefaultSourceName();
                OfferUse defaultOfferUse = null;

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
                } else if(defaultOfferName == null && defaultUseName == null && defaultSourceName == null) {
                    defaultOfferUse = contentCollection.getLastDetail().getDefaultOfferUse();
                } else {
                    addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
                }
                
                if(defaultOfferUse != null) {
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();
                    var partyPK = getPartyPK();
                    
                    contentCatalog = contentControl.createContentCatalog(contentCollection, contentCatalogName, defaultOfferUse,
                            isDefault, sortOrder, partyPK);
                    contentControl.createContentCategory(contentCatalog, ContentCategories.ROOT.toString(), null,
                            defaultOfferUse, null, Boolean.FALSE, 0, partyPK);
                    
                    if(description != null) {
                        Language language = getPreferredLanguage();
                        
                        contentControl.createContentCatalogDescription(contentCatalog, language, description, partyPK);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return null;
    }
    
}
