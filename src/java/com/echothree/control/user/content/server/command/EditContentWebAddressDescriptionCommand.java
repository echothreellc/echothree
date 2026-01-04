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

import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.edit.ContentWebAddressDescriptionEdit;
import com.echothree.control.user.content.common.form.EditContentWebAddressDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentWebAddressDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentWebAddressDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditContentWebAddressDescriptionCommand
        extends BaseAbstractEditCommand<ContentWebAddressDescriptionSpec, ContentWebAddressDescriptionEdit, EditContentWebAddressDescriptionResult, ContentWebAddressDescription, ContentWebAddress> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentWebAddress.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentWebAddressDescriptionCommand */
    public EditContentWebAddressDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentWebAddressDescriptionResult getResult() {
        return ContentResultFactory.getEditContentWebAddressDescriptionResult();
    }
    
    @Override
    public ContentWebAddressDescriptionEdit getEdit() {
        return ContentEditFactory.getContentWebAddressDescriptionEdit();
    }
    
    @Override
    public ContentWebAddressDescription getEntity(EditContentWebAddressDescriptionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentWebAddressDescription contentWebAddressDescription = null;
        var contentWebAddressName = spec.getContentWebAddressName();
        var contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);
        
        if(contentWebAddress != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            result.setContentWebAddress(contentControl.getContentWebAddressTransfer(getUserVisit(), contentWebAddress));

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contentWebAddressDescription = contentControl.getContentWebAddressDescription(contentWebAddress, language);
                } else { // EditMode.UPDATE
                    contentWebAddressDescription = contentControl.getContentWebAddressDescriptionForUpdate(contentWebAddress, language);
                }

                if(contentWebAddressDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContentWebAddressDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
        }

        return contentWebAddressDescription;
    }
    
    @Override
    public ContentWebAddress getLockEntity(ContentWebAddressDescription contentWebAddressDescription) {
        return contentWebAddressDescription.getContentWebAddress();
    }
    
    @Override
    public void fillInResult(EditContentWebAddressDescriptionResult result, ContentWebAddressDescription contentWebAddressDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentWebAddressDescription(contentControl.getContentWebAddressDescriptionTransfer(getUserVisit(), contentWebAddressDescription));
    }
    
    @Override
    public void doLock(ContentWebAddressDescriptionEdit edit, ContentWebAddressDescription contentWebAddressDescription) {
        edit.setDescription(contentWebAddressDescription.getDescription());
    }
    
    @Override
    public void doUpdate(ContentWebAddressDescription contentWebAddressDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentWebAddressDescriptionValue = contentControl.getContentWebAddressDescriptionValue(contentWebAddressDescription);
        contentWebAddressDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentWebAddressDescriptionFromValue(contentWebAddressDescriptionValue, getPartyPK());
    }
    
}
