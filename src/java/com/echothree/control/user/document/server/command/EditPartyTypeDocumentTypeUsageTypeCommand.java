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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.edit.DocumentEditFactory;
import com.echothree.control.user.document.common.edit.PartyTypeDocumentTypeUsageTypeEdit;
import com.echothree.control.user.document.common.form.EditPartyTypeDocumentTypeUsageTypeForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditPartyTypeDocumentTypeUsageTypeResult;
import com.echothree.control.user.document.common.spec.PartyTypeDocumentTypeUsageTypeSpec;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.model.data.party.server.entity.PartyType;
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
public class EditPartyTypeDocumentTypeUsageTypeCommand
        extends BaseAbstractEditCommand<PartyTypeDocumentTypeUsageTypeSpec, PartyTypeDocumentTypeUsageTypeEdit, EditPartyTypeDocumentTypeUsageTypeResult, PartyTypeDocumentTypeUsageType, PartyType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTypeDocumentTypeUsageType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTypeDocumentTypeUsageTypeCommand */
    public EditPartyTypeDocumentTypeUsageTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyTypeDocumentTypeUsageTypeResult getResult() {
        return DocumentResultFactory.getEditPartyTypeDocumentTypeUsageTypeResult();
    }

    @Override
    public PartyTypeDocumentTypeUsageTypeEdit getEdit() {
        return DocumentEditFactory.getPartyTypeDocumentTypeUsageTypeEdit();
    }

    @Override
    public PartyTypeDocumentTypeUsageType getEntity(EditPartyTypeDocumentTypeUsageTypeResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType = null;
        var partyTypeName = spec.getPartyTypeName();
        var partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType != null) {
            var documentControl = Session.getModelController(DocumentControl.class);
            var documentTypeUsageTypeName = spec.getDocumentTypeUsageTypeName();
            var documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);

            if(documentTypeUsageType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyTypeDocumentTypeUsageType = documentControl.getPartyTypeDocumentTypeUsageType(partyType, documentTypeUsageType);
                } else { // EditMode.UPDATE
                    partyTypeDocumentTypeUsageType = documentControl.getPartyTypeDocumentTypeUsageTypeForUpdate(partyType, documentTypeUsageType);
                }

                if(partyTypeDocumentTypeUsageType == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeDocumentTypeUsageType.name(), partyTypeName, documentTypeUsageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyTypeDocumentTypeUsageType;
    }

    @Override
    public PartyType getLockEntity(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        return partyTypeDocumentTypeUsageType.getPartyType();
    }

    @Override
    public void fillInResult(EditPartyTypeDocumentTypeUsageTypeResult result, PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        var documentControl = Session.getModelController(DocumentControl.class);

        result.setPartyTypeDocumentTypeUsageType(documentControl.getPartyTypeDocumentTypeUsageTypeTransfer(getUserVisit(), partyTypeDocumentTypeUsageType));
    }

    @Override
    public void doLock(PartyTypeDocumentTypeUsageTypeEdit edit, PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        edit.setIsDefault(partyTypeDocumentTypeUsageType.getIsDefault().toString());
        edit.setSortOrder(partyTypeDocumentTypeUsageType.getSortOrder().toString());
    }

    @Override
    public void doUpdate(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var partyTypeDocumentTypeUsageTypeValue = documentControl.getPartyTypeDocumentTypeUsageTypeValueForUpdate(partyTypeDocumentTypeUsageType);

        partyTypeDocumentTypeUsageTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partyTypeDocumentTypeUsageTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        documentControl.updatePartyTypeDocumentTypeUsageTypeFromValue(partyTypeDocumentTypeUsageTypeValue, getPartyPK());
    }
    
}
