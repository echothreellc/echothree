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

import com.echothree.control.user.content.common.form.GetContentCategoriesForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentCategoriesResult;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.factory.ContentCategoryFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetContentCategoriesCommand
        extends BaseMultipleEntitiesCommand<ContentCategory, GetContentCategoriesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ParentContentCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentCategoriesCommand */
    public GetContentCategoriesCommand(UserVisitPK userVisitPK, GetContentCategoriesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ContentCatalog contentCatalog;
    private ContentCategory parentContentCategory;
    
    @Override
    protected Collection<ContentCategory> getEntities() {
        String contentWebAddressName = form.getContentWebAddressName();
        String contentCollectionName = form.getContentCollectionName();
        int parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);
        Collection<ContentCategory> contentCategories = null;

        if(parameterCount == 1) {
            var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
            ContentCollection contentCollection = null;

            if(contentWebAddressName != null) {
                ContentWebAddress contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);

                if(contentWebAddress != null) {
                    contentCollection = contentWebAddress.getLastDetail().getContentCollection();
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
                }
            } else {
                contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

                if(contentCollection == null) {
                    addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
                }
            }

            if(!hasExecutionErrors()) {
                var partyPK = getPartyPK();
                UserVisit userVisit = getUserVisitForUpdate();
                String contentCatalogName = form.getContentCatalogName();

                contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

                if(contentCatalogName == null || contentCatalog != null) {
                    if(contentCatalog == null) {
                        contentCatalog = contentControl.getDefaultContentCatalog(contentCollection);
                    }

                    if(contentCatalog != null) {
                        String parentContentCategoryName = form.getParentContentCategoryName();
                        
                        parentContentCategory = parentContentCategoryName == null ? null : contentControl.getContentCategoryByName(contentCatalog, parentContentCategoryName);

                        if(parentContentCategoryName == null || parentContentCategory != null) {
                            AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, userVisit, contentCatalog.getPrimaryKey(), partyPK);

                            if(!hasExecutionErrors()) {
                                if(parentContentCategory == null) {
                                    contentCategories = contentControl.getContentCategories(contentCatalog);
                                } else {
                                    contentCategories = contentControl.getContentCategoriesByParentContentCategory(parentContentCategory);
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownParentContentCategoryName.name(),
                                    contentCollection.getLastDetail().getContentCollectionName(), parentContentCategoryName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownDefaultContentCatalog.name(),
                                contentCollection.getLastDetail().getContentCollectionName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(),
                            contentCollection.getLastDetail().getContentCollectionName(), contentCatalogName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return contentCategories;
    }
    
    @Override
    protected BaseResult getTransfers(Collection<ContentCategory> entities) {
        GetContentCategoriesResult result = ContentResultFactory.getGetContentCategoriesResult();

        if(entities != null) {
            var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
            UserVisit userVisit = getUserVisit();

            result.setContentCatalog(contentControl.getContentCatalogTransfer(userVisit, contentCatalog));

            if(parentContentCategory == null) {
                if(session.hasLimit(ContentCategoryFactory.class)) {
                    result.setContentCategoryCount(contentControl.countContentCategoriesByContentCatalog(contentCatalog));
                }
            } else {
                if(session.hasLimit(ContentCategoryFactory.class)) {
                    result.setContentCategoryCount(contentControl.countContentCategoriesByParentContentCategory(parentContentCategory));
                }

                result.setParentContentCategory(contentControl.getContentCategoryTransfer(userVisit, parentContentCategory));
            }
            
            result.setContentCategories(contentControl.getContentCategoryTransfers(userVisit, entities));
        }

        return result;
    }
    
}
