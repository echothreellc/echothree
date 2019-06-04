// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDescription;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.value.ContentCategoryDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCategoryDetailValue;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
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
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditContentCategoryCommand
        extends BaseAbstractEditCommand<ContentCategorySpec, ContentCategoryEdit, EditContentCategoryResult, ContentCategory, ContentCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditContentCategoryCommand */
    public EditContentCategoryCommand(UserVisitPK userVisitPK, EditContentCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentCategory contentCategory = null;
        String contentCollectionName = spec.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            String contentCatalogName = spec.getContentCatalogName();
            
            contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

            if(contentCatalog != null) {
                String contentCategoryName = spec.getContentCategoryName();

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
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        result.setContentCategory(contentControl.getContentCategoryTransfer(getUserVisit(), contentCategory));
    }
    
    @Override
    public void doLock(ContentCategoryEdit edit, ContentCategory contentCategory) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        ContentCategoryDescription contentCategoryDescription = contentControl.getContentCategoryDescription(contentCategory, getPreferredLanguage());
        ContentCategoryDetail contentCategoryDetail = contentCategory.getLastDetail();
        ContentCategory parentContentCategory = contentCategoryDetail.getParentContentCategory();
        OfferUse defaultOfferUse = contentCategoryDetail.getDefaultOfferUse();
        OfferUseDetail defaultOfferUseDetail = defaultOfferUse == null? null: defaultOfferUse.getLastDetail();
        List<Source> defaultSources = defaultOfferUse == null? null: offerControl.getSourcesByOfferUse(defaultOfferUse);
        Selector contentCategoryItemSelector = contentCategoryDetail.getContentCategoryItemSelector();

        edit.setContentCategoryName(contentCategoryDetail.getContentCategoryName());
        edit.setParentContentCategoryName(parentContentCategory == null ? null : parentContentCategory.getLastDetail().getContentCategoryName());
        edit.setDefaultOfferName(defaultOfferUseDetail == null? null: defaultOfferUseDetail.getOffer().getLastDetail().getOfferName());
        edit.setDefaultUseName(defaultOfferUseDetail == null? null: defaultOfferUseDetail.getUse().getLastDetail().getUseName());
        edit.setDefaultSourceName(defaultSources == null? null: defaultSources.iterator().next().getLastDetail().getSourceName());
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
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        String contentCategoryName = edit.getContentCategoryName();
        ContentCategory duplicateContentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);

        if(duplicateContentCategory == null || contentCategory.equals(duplicateContentCategory)) {
            String parentContentCategoryName = edit.getParentContentCategoryName();
            
            parentContentCategory = contentControl.getContentCategoryByName(contentCatalog, parentContentCategoryName == null ?
                    ContentCategories.ROOT.toString() : parentContentCategoryName);

            if(parentContentCategory != null) {
                if(contentControl.isParentContentCategorySafe(contentCategory, parentContentCategory)) {
                    var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
                    String defaultOfferName = edit.getDefaultOfferName();
                    String defaultUseName = edit.getDefaultUseName();
                    String defaultSourceName = edit.getDefaultSourceName();
                    boolean invalidDefaultOfferOrSourceSpecification = false;

                    if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                        Offer defaultOffer = offerControl.getOfferByName(defaultOfferName);

                        if(defaultOffer != null) {
                            Use defaultUse = offerControl.getUseByName(defaultUseName);

                            if(defaultUse != null) {
                                defaultOfferUse = offerControl.getOfferUse(defaultOffer, defaultUse);

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
                        Source source = offerControl.getSourceByName(defaultSourceName);

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
                            String contentCategoryItemSelectorName = edit.getContentCategoryItemSelectorName();

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
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        PartyPK partyPK = getPartyPK();
        ContentCategoryDetailValue contentCategoryDetailValue = contentControl.getContentCategoryDetailValueForUpdate(contentCategory);
        ContentCategoryDescription contentCategoryDescription = contentControl.getContentCategoryDescriptionForUpdate(contentCategory, getPreferredLanguage());
        String description = edit.getDescription();

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
            ContentCategoryDescriptionValue contentCategoryDescriptionValue = contentControl.getContentCategoryDescriptionValue(contentCategoryDescription);

            contentCategoryDescriptionValue.setDescription(description);
            contentControl.updateContentCategoryDescriptionFromValue(contentCategoryDescriptionValue, partyPK);
        }
    }
    
}
