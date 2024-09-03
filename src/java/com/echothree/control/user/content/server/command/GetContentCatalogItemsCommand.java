// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.content.common.form.GetContentCatalogItemsForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetContentCatalogItemsCommand
        extends BaseMultipleEntitiesCommand<ContentCatalogItem, GetContentCatalogItemsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCatalogItem.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentCatalogItemsCommand */
    public GetContentCatalogItemsCommand(UserVisitPK userVisitPK, GetContentCatalogItemsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ContentCatalog contentCatalog;
    
    @Override
    protected Collection<ContentCatalogItem> getEntities() {
        var contentWebAddressName = form.getContentWebAddressName();
        var contentCollectionName = form.getContentCollectionName();
        var parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);
        Collection<ContentCatalogItem> contentCatalogItems = null;

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
                var partyPK = getPartyPK();
                var userVisit = getUserVisitForUpdate();

                contentCatalog = contentCatalogName == null ? contentControl.getDefaultContentCatalog(contentCollection)
                        : contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

                if(contentCatalog != null) {
                    AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, userVisit, contentCatalog.getPrimaryKey(), partyPK);

                    if(!hasExecutionErrors()) {
                        contentCatalogItems = contentControl.getContentCatalogItemsByContentCatalog(contentCatalog);
                    }
                } else {
                    if(contentCatalogName == null) {
                        addExecutionError(ExecutionErrors.UnknownDefaultContentCatalog.name(),
                                contentCollection.getLastDetail().getContentCollectionName());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(),
                                contentCollection.getLastDetail().getContentCollectionName(), contentCatalogName);
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return contentCatalogItems;
    }
    
    @Override
    protected BaseResult getResult(Collection<ContentCatalogItem> entities) {
        var result = ContentResultFactory.getGetContentCatalogItemsResult();

        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ContentCatalogItemFactory.class)) {
                result.setContentCatalogItemCount(contentControl.countContentCatalogItemsByContentCatalog(contentCatalog));
            }

            result.setContentCatalog(contentControl.getContentCatalogTransfer(userVisit, contentCatalog));
            result.setContentCatalogItems(contentControl.getContentCatalogItemTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
