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

package com.echothree.control.user.queue.server.command;

import com.echothree.control.user.queue.common.edit.QueueEditFactory;
import com.echothree.control.user.queue.common.edit.QueueTypeDescriptionEdit;
import com.echothree.control.user.queue.common.form.EditQueueTypeDescriptionForm;
import com.echothree.control.user.queue.common.result.EditQueueTypeDescriptionResult;
import com.echothree.control.user.queue.common.result.QueueResultFactory;
import com.echothree.control.user.queue.common.spec.QueueTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueueTypeDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditQueueTypeDescriptionCommand
        extends BaseAbstractEditCommand<QueueTypeDescriptionSpec, QueueTypeDescriptionEdit, EditQueueTypeDescriptionResult, QueueTypeDescription, QueueType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueueType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("QueueTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditQueueTypeDescriptionCommand */
    public EditQueueTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditQueueTypeDescriptionResult getResult() {
        return QueueResultFactory.getEditQueueTypeDescriptionResult();
    }

    @Override
    public QueueTypeDescriptionEdit getEdit() {
        return QueueEditFactory.getQueueTypeDescriptionEdit();
    }

    @Override
    public QueueTypeDescription getEntity(EditQueueTypeDescriptionResult result) {
        var queueControl = Session.getModelController(QueueControl.class);
        QueueTypeDescription queueTypeDescription = null;
        var queueTypeName = spec.getQueueTypeName();
        var queueType = queueControl.getQueueTypeByName(queueTypeName);

        if(queueType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    queueTypeDescription = queueControl.getQueueTypeDescription(queueType, language);
                } else { // EditMode.UPDATE
                    queueTypeDescription = queueControl.getQueueTypeDescriptionForUpdate(queueType, language);
                }

                if(queueTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownQueueTypeDescription.name(), queueTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownQueueTypeName.name(), queueTypeName);
        }

        return queueTypeDescription;
    }

    @Override
    public QueueType getLockEntity(QueueTypeDescription queueTypeDescription) {
        return queueTypeDescription.getQueueType();
    }

    @Override
    public void fillInResult(EditQueueTypeDescriptionResult result, QueueTypeDescription queueTypeDescription) {
        var queueControl = Session.getModelController(QueueControl.class);

        result.setQueueTypeDescription(queueControl.getQueueTypeDescriptionTransfer(getUserVisit(), queueTypeDescription));
    }

    @Override
    public void doLock(QueueTypeDescriptionEdit edit, QueueTypeDescription queueTypeDescription) {
        edit.setDescription(queueTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(QueueTypeDescription queueTypeDescription) {
        var queueControl = Session.getModelController(QueueControl.class);
        var queueTypeDescriptionValue = queueControl.getQueueTypeDescriptionValue(queueTypeDescription);
        queueTypeDescriptionValue.setDescription(edit.getDescription());

        queueControl.updateQueueTypeDescriptionFromValue(queueTypeDescriptionValue, getPartyPK());
    }
    
}
