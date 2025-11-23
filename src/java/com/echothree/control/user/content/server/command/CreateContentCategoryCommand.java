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

import com.echothree.control.user.content.common.form.CreateContentCategoryForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
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
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.selector.server.entity.Selector;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateContentCategoryCommand */
    public CreateContentCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ContentResultFactory.getCreateContentCategoryResult();
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        ContentCategory contentCategory = null;
        
        if(contentCollection != null) {
            var contentCatalogName = form.getContentCatalogName();
            var contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            
            if(contentCatalog != null) {
                var contentCategoryName = form.getContentCategoryName();
                
                contentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);
                
                if(contentCategory == null) {
                    var parentContentCategoryName = form.getParentContentCategoryName();
                    
                    if(parentContentCategoryName == null)
                        parentContentCategoryName = ContentCategories.ROOT.toString();

                    var parentContentCategory = parentContentCategoryName == null? null: contentControl.getContentCategoryByName(contentCatalog,
                            parentContentCategoryName);
                    
                    if(parentContentCategory == null) {
                        addExecutionError(ExecutionErrors.UnknownParentContentCategoryName.name(), parentContentCategoryName);
                    } else {
                        var offerControl = Session.getModelController(OfferControl.class);
                        var defaultOfferName = form.getDefaultOfferName();
                        var defaultUseName = form.getDefaultUseName();
                        var defaultSourceName = form.getDefaultSourceName();
                        OfferUse defaultOfferUse = null;
                        var invalidDefaultOfferOrSourceSpecification = false;
                        
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
                        } else if(defaultOfferName == null && defaultUseName == null && defaultSourceName == null) {
                            // nothing
                        } else {
                            addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
                            invalidDefaultOfferOrSourceSpecification = true;
                        }
                        
                        if(!invalidDefaultOfferOrSourceSpecification) {
                            var invalidOfferCompany = false;
                            
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
                                    invalidOfferCompany = true;
                                }
                            }
                            
                            if(!invalidOfferCompany) {
                                var contentCategoryItemSelectorName = form.getContentCategoryItemSelectorName();
                                Selector contentCategoryItemSelector = null;
                                
                                if(contentCategoryItemSelectorName != null) {
                                    var selectorControl = Session.getModelController(SelectorControl.class);
                                    var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());
                                    
                                    if(selectorKind != null) {
                                        var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CONTENT_CATEGORY.name());
                                        
                                        if(selectorType != null) {
                                            contentCategoryItemSelector = selectorControl.getSelectorByName(selectorType, contentCategoryItemSelectorName);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.CONTENT_CATEGORY.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                                    }
                                }
                                
                                if(contentCategoryItemSelectorName != null && contentCategoryItemSelector == null) {
                                    addExecutionError(ExecutionErrors.UnknownContentCategoryItemSelectorName.name(), contentCategoryItemSelectorName);
                                } else {
                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();
                                    var createdBy = getPartyPK();
                                    
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

        if(contentCategory != null) {
            var contentCategoryDetail = contentCategory.getLastDetail();
            var contentCatalogDetail = contentCategoryDetail.getContentCatalog().getLastDetail();

            result.setContentCollectionName(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName());
            result.setContentCatalogName(contentCatalogDetail.getContentCatalogName());
            result.setContentCategoryName(contentCategoryDetail.getContentCategoryName());
            result.setEntityRef(contentCategory.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
