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

import com.echothree.control.user.content.common.form.GetContentSectionsForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class GetContentSectionsCommand
        extends BaseMultipleEntitiesCommand<ContentSection, GetContentSectionsForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ParentContentSectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentSectionsCommand */
    public GetContentSectionsCommand(UserVisitPK userVisitPK, GetContentSectionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ContentSection parentContentSection;
    private ContentCollection contentCollection;
    
    @Override
    protected Collection<ContentSection> getEntities() {
        var contentWebAddressName = form.getContentWebAddressName();
        var contentCollectionName = form.getContentCollectionName();
        var parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);
        Collection<ContentSection> contentSections = null;

        if(parameterCount == 1) {
            var contentControl = Session.getModelController(ContentControl.class);

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
                var parentContentSectionName = form.getParentContentSectionName();
                var partyPK = getPartyPK();

                parentContentSection = parentContentSectionName == null ? null : contentControl.getContentSectionByName(contentCollection, parentContentSectionName);
                
                if(parentContentSectionName == null || parentContentSection != null) {
                    AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, getUserVisitForUpdate(), contentCollection.getPrimaryKey(), partyPK);

                    if(!hasExecutionErrors()) {
                        if(parentContentSection == null) {
                            contentSections = contentControl.getContentSections(contentCollection);
                        } else {
                            contentSections = contentControl.getContentSectionsByParentContentSection(parentContentSection);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownParentContentSectionName.name(), contentCollection.getLastDetail().getContentCollectionName(), parentContentSectionName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return contentSections;
    }
    
    @Override
    protected BaseResult getResult(Collection<ContentSection> entities) {
        var result = ContentResultFactory.getGetContentSectionsResult();
        
        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            var userVisit = getUserVisit();

            result.setContentCollection(contentControl.getContentCollectionTransfer(userVisit, contentCollection));
            result.setContentSections(contentControl.getContentSectionTransfers(userVisit, entities));

            if(parentContentSection != null) {
                result.setParentContentSection(contentControl.getContentSectionTransfer(userVisit, parentContentSection));
            }
        }
                        
        return result;
    }
    
}
