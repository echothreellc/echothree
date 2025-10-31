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

import com.echothree.control.user.content.common.edit.ContentCategoryEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCategoryForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCategoryResult;
import com.echothree.control.user.content.common.spec.ContentCategorySpec;
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
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.selector.server.entity.Selector;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditContentCategoryCommand
        extends BaseAbstractEditCommand<ContentCategorySpec, ContentCategoryEdit, EditContentCategoryResult, ContentCategory, ContentCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCategory.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of EditContentCategoryCommand */
    public EditContentCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCategoryResult getResult() {
        return ContentResultFactory.getEditContentCategoryResult();
    }
    
    @Override
    public ContentCategoryEdit getEdit() {
        return ContentEditFactory.getContentCategoryEdit();
    }
    
    ContentCatalog contentCatalog = null;
    
    @Override
    public ContentCategory getEntity(EditContentCategoryResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentCategory contentCategory = null;
        var contentCollectionName = spec.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentCatalogName = spec.getContentCatalogName();
            
            contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

            if(contentCatalog != null) {
                var contentCategoryName = spec.getContentCategoryName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);
                } else { // EditMode.UPDATE
                    contentCategory = contentControl.getContentCategoryByNameForUpdate(contentCatalog, contentCategoryName);
                }

                if(contentCategory != null) {
                    result.setContentCategory(contentControl.getContentCategoryTransfer(getUserVisit(), contentCategory));
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentCategoryName.name(), contentCategoryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCategory;
    }
    
    @Override
    public ContentCategory getLockEntity(ContentCategory contentCategory) {
        return contentCategory;
    }
    
    @Override
    public void fillInResult(EditContentCategoryResult result, ContentCategory contentCategory) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentCategory(contentControl.getContentCategoryTransfer(getUserVisit(), contentCategory));
    }
    
    @Override
    public void doLock(ContentCategoryEdit edit, ContentCategory contentCategory) {
        var contentControl = Session.getModelController(ContentControl.class);
        var sourceControl = Session.getModelController(SourceControl.class);
        var contentCategoryDescription = contentControl.getContentCategoryDescription(contentCategory, getPreferredLanguage());
        var contentCategoryDetail = contentCategory.getLastDetail();
        var parentContentCategory = contentCategoryDetail.getParentContentCategory();
        var defaultOfferUse = contentCategoryDetail.getDefaultOfferUse();
        var defaultOfferUseDetail = defaultOfferUse == null ? null : defaultOfferUse.getLastDetail();
        var sources = defaultOfferUse == null ? null : sourceControl.getSourcesByOfferUse(defaultOfferUse); // List of Sources if there are any for the OfferUse
        var defaultSourceName = sources == null ? null : sources.iterator().next().getLastDetail().getSourceName(); // From the List, the first available Source's SourceName
        var contentCategoryItemSelector = contentCategoryDetail.getContentCategoryItemSelector();

        edit.setContentCategoryName(contentCategoryDetail.getContentCategoryName());
        edit.setParentContentCategoryName(parentContentCategory == null ? null : parentContentCategory.getLastDetail().getContentCategoryName());
        edit.setDefaultOfferName(defaultSourceName == null ? (defaultOfferUseDetail == null? null: defaultOfferUseDetail.getOffer().getLastDetail().getOfferName()) : null);
        edit.setDefaultUseName(defaultSourceName == null ? (defaultOfferUseDetail == null ? null : defaultOfferUseDetail.getUse().getLastDetail().getUseName()) : null);
        edit.setDefaultSourceName(defaultSourceName);
        edit.setContentCategoryItemSelectorName(contentCategoryItemSelector == null? null: contentCategoryItemSelector.getLastDetail().getSelectorName());
        edit.setIsDefault(contentCategoryDetail.getIsDefault().toString());
        edit.setSortOrder(contentCategoryDetail.getSortOrder().toString());

        if(contentCategoryDescription != null) {
            edit.setDescription(contentCategoryDescription.getDescription());
        }
    }
    
    ContentCategory parentContentCategory = null;
    OfferUse defaultOfferUse = null;
    Selector contentCategoryItemSelector = null;
   
    @Override
    public void canUpdate(ContentCategory contentCategory) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCategoryName = edit.getContentCategoryName();
        var duplicateContentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);

        if(duplicateContentCategory == null || contentCategory.equals(duplicateContentCategory)) {
            var parentContentCategoryName = edit.getParentContentCategoryName();
            
            parentContentCategory = contentControl.getContentCategoryByName(contentCatalog, parentContentCategoryName == null ?
                    ContentCategories.ROOT.toString() : parentContentCategoryName);

            if(parentContentCategory != null) {
                if(contentControl.isParentContentCategorySafe(contentCategory, parentContentCategory)) {
                    var offerControl = Session.getModelController(OfferControl.class);
                    var defaultOfferName = edit.getDefaultOfferName();
                    var defaultUseName = edit.getDefaultUseName();
                    var defaultSourceName = edit.getDefaultSourceName();
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
                            var contentCategoryItemSelectorName = edit.getContentCategoryItemSelectorName();

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
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidOfferCompany.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentContentCategory.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentContentCategoryName.name(), parentContentCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentCategoryName.name(), contentCategoryName);
        }
    }
    
    @Override
    public void doUpdate(ContentCategory contentCategory) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentCategoryDetailValue = contentControl.getContentCategoryDetailValueForUpdate(contentCategory);
        var contentCategoryDescription = contentControl.getContentCategoryDescriptionForUpdate(contentCategory, getPreferredLanguage());
        var description = edit.getDescription();

        contentCategoryDetailValue.setContentCategoryName(edit.getContentCategoryName());
        contentCategoryDetailValue.setParentContentCategoryPK(parentContentCategory == null ? null : parentContentCategory.getPrimaryKey());
        contentCategoryDetailValue.setDefaultOfferUsePK(defaultOfferUse == null? null: defaultOfferUse.getPrimaryKey());
        contentCategoryDetailValue.setContentCategoryItemSelectorPK(contentCategoryItemSelector == null? null: contentCategoryItemSelector.getPrimaryKey());
        contentCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contentCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contentControl.updateContentCategoryFromValue(contentCategoryDetailValue, partyPK);

        if(contentCategoryDescription == null && description != null) {
            contentControl.createContentCategoryDescription(contentCategory, getPreferredLanguage(), description, partyPK);
        } else if(contentCategoryDescription != null && description == null) {
            contentControl.deleteContentCategoryDescription(contentCategoryDescription, partyPK);
        } else if(contentCategoryDescription != null && description != null) {
            var contentCategoryDescriptionValue = contentControl.getContentCategoryDescriptionValue(contentCategoryDescription);

            contentCategoryDescriptionValue.setDescription(description);
            contentControl.updateContentCategoryDescriptionFromValue(contentCategoryDescriptionValue, partyPK);
        }
    }
    
}
