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
import com.echothree.control.user.queue.common.edit.QueueTypeEdit;
import com.echothree.control.user.queue.common.form.EditQueueTypeForm;
import com.echothree.control.user.queue.common.result.EditQueueTypeResult;
import com.echothree.control.user.queue.common.result.QueueResultFactory;
import com.echothree.control.user.queue.common.spec.QueueTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.queue.server.entity.QueueType;
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
public class EditQueueTypeCommand
        extends BaseAbstractEditCommand<QueueTypeSpec, QueueTypeEdit, EditQueueTypeResult, QueueType, QueueType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueueType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("QueueTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("QueueTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditQueueTypeCommand */
    public EditQueueTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditQueueTypeResult getResult() {
        return QueueResultFactory.getEditQueueTypeResult();
    }

    @Override
    public QueueTypeEdit getEdit() {
        return QueueEditFactory.getQueueTypeEdit();
    }

    @Override
    public QueueType getEntity(EditQueueTypeResult result) {
        var queueControl = Session.getModelController(QueueControl.class);
        QueueType queueType;
        var queueTypeName = spec.getQueueTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            queueType = queueControl.getQueueTypeByName(queueTypeName);
        } else { // EditMode.UPDATE
            queueType = queueControl.getQueueTypeByNameForUpdate(queueTypeName);
        }

        if(queueType == null) {
            addExecutionError(ExecutionErrors.UnknownQueueTypeName.name(), queueTypeName);
        }

        return queueType;
    }

    @Override
    public QueueType getLockEntity(QueueType queueType) {
        return queueType;
    }

    @Override
    public void fillInResult(EditQueueTypeResult result, QueueType queueType) {
        var queueControl = Session.getModelController(QueueControl.class);

        result.setQueueType(queueControl.getQueueTypeTransfer(getUserVisit(), queueType));
    }

    @Override
    public void doLock(QueueTypeEdit edit, QueueType queueType) {
        var queueControl = Session.getModelController(QueueControl.class);
        var queueTypeDescription = queueControl.getQueueTypeDescription(queueType, getPreferredLanguage());
        var queueTypeDetail = queueType.getLastDetail();

        edit.setQueueTypeName(queueTypeDetail.getQueueTypeName());
        edit.setIsDefault(queueTypeDetail.getIsDefault().toString());
        edit.setSortOrder(queueTypeDetail.getSortOrder().toString());

        if(queueTypeDescription != null) {
            edit.setDescription(queueTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(QueueType queueType) {
        var queueControl = Session.getModelController(QueueControl.class);
        var queueTypeName = edit.getQueueTypeName();
        var duplicateQueueType = queueControl.getQueueTypeByName(queueTypeName);

        if(duplicateQueueType != null && !queueType.equals(duplicateQueueType)) {
            addExecutionError(ExecutionErrors.DuplicateQueueTypeName.name(), queueTypeName);
        }
    }

    @Override
    public void doUpdate(QueueType queueType) {
        var queueControl = Session.getModelController(QueueControl.class);
        var partyPK = getPartyPK();
        var queueTypeDetailValue = queueControl.getQueueTypeDetailValueForUpdate(queueType);
        var queueTypeDescription = queueControl.getQueueTypeDescriptionForUpdate(queueType, getPreferredLanguage());
        var description = edit.getDescription();

        queueTypeDetailValue.setQueueTypeName(edit.getQueueTypeName());
        queueTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        queueTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        queueControl.updateQueueTypeFromValue(queueTypeDetailValue, partyPK);

        if(queueTypeDescription == null && description != null) {
            queueControl.createQueueTypeDescription(queueType, getPreferredLanguage(), description, partyPK);
        } else {
            if(queueTypeDescription != null && description == null) {
                queueControl.deleteQueueTypeDescription(queueTypeDescription, partyPK);
            } else {
                if(queueTypeDescription != null && description != null) {
                    var queueTypeDescriptionValue = queueControl.getQueueTypeDescriptionValue(queueTypeDescription);

                    queueTypeDescriptionValue.setDescription(description);
                    queueControl.updateQueueTypeDescriptionFromValue(queueTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
