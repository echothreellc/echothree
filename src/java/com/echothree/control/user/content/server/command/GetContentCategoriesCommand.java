// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.factory.ContentCategoryFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetContentCategoriesCommand
        extends BasePaginatedMultipleEntitiesCommand<ContentCategory, GetContentCategoriesForm> {
    
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
    public GetContentCategoriesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ContentCatalog contentCatalog;
    private ContentCategory parentContentCategory;

    @Override
    protected void handleForm() {
        var contentWebAddressName = form.getContentWebAddressName();
        var contentCollectionName = form.getContentCollectionName();
        var parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);

        if(parameterCount == 1) {
            var contentControl = Session.getModelController(ContentControl.class);
            ContentCollection contentCollection = null;

            if(contentWebAddressName != null) {
                var contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);

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
                var contentCatalogName = form.getContentCatalogName();

                contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

                if(contentCatalogName == null || contentCatalog != null) {
                    if(contentCatalog == null) {
                        contentCatalog = contentControl.getDefaultContentCatalog(contentCollection);
                    }

                    if(contentCatalog != null) {
                        var parentContentCategoryName = form.getParentContentCategoryName();

                        if(parentContentCategoryName == null) {
                            parentContentCategory = null;
                        } else {
                            parentContentCategory = contentControl.getContentCategoryByName(contentCatalog, parentContentCategoryName);

                            if(parentContentCategory == null) {
                                addExecutionError(ExecutionErrors.UnknownParentContentCategoryName.name(),
                                        contentCollection.getLastDetail().getContentCollectionName(), parentContentCategoryName);
                            }
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

        if(!hasExecutionErrors()) {
            AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, getUserVisitForUpdate(),
                    contentCatalog.getPrimaryKey(), getPartyPK());
        }
    }

    @Override
    protected Long getTotalEntities() {
        var contentControl = Session.getModelController(ContentControl.class);

        return hasExecutionErrors() ? null :
                parentContentCategory == null ?
                        contentControl.countContentCategoriesByContentCatalog(contentCatalog) :
                        contentControl.countContentCategoriesByParentContentCategory(parentContentCategory);
    }

    
    @Override
    protected Collection<ContentCategory> getEntities() {
        Collection<ContentCategory> contentCategories = null;

        if(!hasExecutionErrors()) {
            var contentControl = Session.getModelController(ContentControl.class);

            if(parentContentCategory == null) {
                contentCategories = contentControl.getContentCategories(contentCatalog);
            } else {
                contentCategories = contentControl.getContentCategoriesByParentContentCategory(parentContentCategory);
            }
        }

        return contentCategories;
    }
    
    @Override
    protected BaseResult getResult(Collection<ContentCategory> entities) {
        var result = ContentResultFactory.getGetContentCategoriesResult();

        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            var userVisit = getUserVisit();

            result.setContentCatalog(contentControl.getContentCatalogTransfer(userVisit, contentCatalog));

            if(parentContentCategory != null) {
                result.setParentContentCategory(contentControl.getContentCategoryTransfer(userVisit, parentContentCategory));
            }

            if(session.hasLimit(ContentCategoryFactory.class)) {
                result.setContentCategoryCount(getTotalEntities());
            }

            result.setContentCategories(contentControl.getContentCategoryTransfers(userVisit, entities));
        }

        return result;
    }
    
}
