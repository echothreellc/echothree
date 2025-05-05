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

import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.edit.ContentWebAddressEdit;
import com.echothree.control.user.content.common.form.EditContentWebAddressForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentWebAddressResult;
import com.echothree.control.user.content.common.spec.ContentWebAddressSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
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

public class EditContentWebAddressCommand
        extends BaseAbstractEditCommand<ContentWebAddressSpec, ContentWebAddressEdit, EditContentWebAddressResult, ContentWebAddress, ContentWebAddress> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentWebAddress.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentWebAddressCommand */
    public EditContentWebAddressCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentWebAddressResult getResult() {
        return ContentResultFactory.getEditContentWebAddressResult();
    }
    
    @Override
    public ContentWebAddressEdit getEdit() {
        return ContentEditFactory.getContentWebAddressEdit();
    }
    
    @Override
    public ContentWebAddress getEntity(EditContentWebAddressResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentWebAddress contentWebAddress;
        var contentWebAddressName = spec.getContentWebAddressName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);
        } else { // EditMode.UPDATE
            contentWebAddress = contentControl.getContentWebAddressByNameForUpdate(contentWebAddressName);
        }

        if(contentWebAddress != null) {
            result.setContentWebAddress(contentControl.getContentWebAddressTransfer(getUserVisit(), contentWebAddress));
        } else {
            addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
        }

        return contentWebAddress;
    }
    
    @Override
    public ContentWebAddress getLockEntity(ContentWebAddress contentWebAddress) {
        return contentWebAddress;
    }
    
    @Override
    public void fillInResult(EditContentWebAddressResult result, ContentWebAddress contentWebAddress) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentWebAddress(contentControl.getContentWebAddressTransfer(getUserVisit(), contentWebAddress));
    }
    
    @Override
    public void doLock(ContentWebAddressEdit edit, ContentWebAddress contentWebAddress) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentWebAddressDescription = contentControl.getContentWebAddressDescription(contentWebAddress, getPreferredLanguage());
        var contentWebAddressDetail = contentWebAddress.getLastDetail();

        edit.setContentWebAddressName(contentWebAddressDetail.getContentWebAddressName());
        edit.setContentCollectionName(contentWebAddressDetail.getContentCollection().getLastDetail().getContentCollectionName());

        if(contentWebAddressDescription != null) {
            edit.setDescription(contentWebAddressDescription.getDescription());
        }
    }
    
    ContentCollection contentCollection = null;
    
    @Override
    public void canUpdate(ContentWebAddress contentWebAddress) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentWebAddressName = edit.getContentWebAddressName();
        var duplicateContentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);

        if(duplicateContentWebAddress == null || contentWebAddress.equals(duplicateContentWebAddress)) {
            var contentCollectionName = edit.getContentCollectionName();

            contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

            if(contentCollection == null) {
                addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentWebAddressName.name(), contentWebAddressName);
        }
    }
    
    @Override
    public void doUpdate(ContentWebAddress contentWebAddress) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentWebAddressDetailValue = contentControl.getContentWebAddressDetailValueForUpdate(contentWebAddress);
        var contentWebAddressDescription = contentControl.getContentWebAddressDescriptionForUpdate(contentWebAddress, getPreferredLanguage());
        var description = edit.getDescription();

        contentWebAddressDetailValue.setContentWebAddressName(edit.getContentWebAddressName());
        contentWebAddressDetailValue.setContentCollectionPK(contentCollection.getPrimaryKey());

        contentControl.updateContentWebAddressFromValue(contentWebAddressDetailValue, partyPK);

        if(contentWebAddressDescription == null && description != null) {
            contentControl.createContentWebAddressDescription(contentWebAddress, getPreferredLanguage(), description, partyPK);
        } else if(contentWebAddressDescription != null && description == null) {
            contentControl.deleteContentWebAddressDescription(contentWebAddressDescription, partyPK);
        } else if(contentWebAddressDescription != null && description != null) {
            var contentWebAddressDescriptionValue = contentControl.getContentWebAddressDescriptionValue(contentWebAddressDescription);

            contentWebAddressDescriptionValue.setDescription(description);
            contentControl.updateContentWebAddressDescriptionFromValue(contentWebAddressDescriptionValue, partyPK);
        }
    }
    
}
