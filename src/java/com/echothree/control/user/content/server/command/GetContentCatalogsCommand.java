// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.content.common.form.GetContentCatalogsForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentCatalogsResult;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContentCatalogsCommand
        extends BaseSimpleCommand<GetContentCatalogsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentCatalogsCommand */
    public GetContentCatalogsCommand(UserVisitPK userVisitPK, GetContentCatalogsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetContentCatalogsResult result = ContentResultFactory.getGetContentCatalogsResult();
        String contentWebAddressName = form.getContentWebAddressName();
        String contentCollectionName = form.getContentCollectionName();
        int parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);

        if(parameterCount == 1) {
            ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
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
                PartyPK partyPK = getPartyPK();
                UserVisit userVisit = getUserVisitForUpdate();

                AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, userVisit, contentCollection.getPrimaryKey(), partyPK);

                if(!hasExecutionErrors()) {
                    result.setContentCollection(contentControl.getContentCollectionTransfer(userVisit, contentCollection));
                    result.setContentCatalogs(contentControl.getContentCatalogTransfers(userVisit, contentCollection));

                    sendEventUsingNames(contentCollection.getPrimaryKey(), EventTypes.READ.name(), null, null, partyPK);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
