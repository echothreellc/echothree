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

import com.echothree.control.user.content.common.form.CreateContentCategoryForm;
import com.echothree.model.control.content.common.ContentCategories;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
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

public class CreateContentCategoryCommand
        extends BaseSimpleCommand<CreateContentCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCategory.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentContentCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCategoryItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateContentCategoryCommand */
    public CreateContentCategoryCommand(UserVisitPK userVisitPK, CreateContentCategoryForm form) {
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
            
            if(contentCatalog != null) {
                String contentCategoryName = form.getContentCategoryName();
                ContentCategory contentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);
                
                if(contentCategory == null) {
                    String parentContentCategoryName = form.getParentContentCategoryName();
                    
                    if(parentContentCategoryName == null)
                        parentContentCategoryName = ContentCategories.ROOT.toString();
                    
                    ContentCategory parentContentCategory = parentContentCategoryName == null? null: contentControl.getContentCategoryByName(contentCatalog,
                            parentContentCategoryName);
                    
                    if(parentContentCategory == null) {
                        addExecutionError(ExecutionErrors.UnknownParentContentCategoryName.name(), parentContentCategoryName);
                    } else {
                        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
                        String defaultOfferName = form.getDefaultOfferName();
                        String defaultUseName = form.getDefaultUseName();
                        String defaultSourceName = form.getDefaultSourceName();
                        OfferUse defaultOfferUse = null;
                        boolean invalidDefaultOfferOrSourceSpecification = false;
                        
                        if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                            Offer defaultOffer = offerControl.getOfferByName(defaultOfferName);
                            
                            if(defaultOffer != null) {
                                var useControl = (UseControl)Session.getModelController(UseControl.class);
                                Use defaultUse = useControl.getUseByName(defaultUseName);
                                
                                if(defaultUse != null) {
                                    var offerUseControl = (OfferUseControl)Session.getModelController(OfferUseControl.class);
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
                            var sourceControl = (SourceControl)Session.getModelController(SourceControl.class);
                            Source source = sourceControl.getSourceByName(defaultSourceName);
                            
                            if(source != null) {
                                defaultOfferUse = source.getLastDetail().getOfferUse();
                            } else {
                                addExecutionError(ExecutionErrors.UnknownDefaultSourceName.name(), defaultSourceName);
                            }
                        } else if(defaultOfferName == null && defaultUseName == null && defaultSourceName == null) {
                            // nothing
                        } else {
                            addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
                            invalidDefaultOfferOrSourceSpecification = true;
                        }
                        
                        if(!invalidDefaultOfferOrSourceSpecification) {
                            boolean invalidOfferCompany = false;
                            
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
                                    invalidOfferCompany = true;
                                }
                            }
                            
                            if(!invalidOfferCompany) {
                                String contentCategoryItemSelectorName = form.getContentCategoryItemSelectorName();
                                Selector contentCategoryItemSelector = null;
                                
                                if(contentCategoryItemSelectorName != null) {
                                    var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                                    SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);
                                    
                                    if(selectorKind != null) {
                                        SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CONTENT_CATEGORY);
                                        
                                        if(selectorType != null) {
                                            contentCategoryItemSelector = selectorControl.getSelectorByName(selectorType, contentCategoryItemSelectorName);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorType_CONTENT_CATEGORY);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ITEM);
                                    }
                                }
                                
                                if(contentCategoryItemSelectorName != null && contentCategoryItemSelector == null) {
                                    addExecutionError(ExecutionErrors.UnknownContentCategoryItemSelectorName.name(), contentCategoryItemSelectorName);
                                } else {
                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();
                                    PartyPK createdBy = getPartyPK();
                                    
                                    contentCategory = contentControl.createContentCategory(contentCatalog, contentCategoryName,
                                            parentContentCategory, defaultOfferUse, contentCategoryItemSelector, isDefault,
                                            sortOrder, createdBy);
                                    
                                    if(description != null) {
                                        contentControl.createContentCategoryDescription(contentCategory, getPreferredLanguage(),
                                                description, createdBy);
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidOfferCompany.name());
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateContentCategoryName.name(), contentCategoryName);
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
