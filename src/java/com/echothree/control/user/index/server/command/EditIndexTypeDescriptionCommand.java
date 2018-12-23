// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.edit.IndexEditFactory;
import com.echothree.control.user.index.common.edit.IndexTypeDescriptionEdit;
import com.echothree.control.user.index.common.form.EditIndexTypeDescriptionForm;
import com.echothree.control.user.index.common.result.EditIndexTypeDescriptionResult;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.control.user.index.common.spec.IndexTypeDescriptionSpec;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.entity.IndexTypeDescription;
import com.echothree.model.data.index.server.value.IndexTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditIndexTypeDescriptionCommand
        extends BaseAbstractEditCommand<IndexTypeDescriptionSpec, IndexTypeDescriptionEdit, EditIndexTypeDescriptionResult, IndexTypeDescription, IndexType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.IndexType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditIndexTypeDescriptionCommand */
    public EditIndexTypeDescriptionCommand(UserVisitPK userVisitPK, EditIndexTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditIndexTypeDescriptionResult getResult() {
        return IndexResultFactory.getEditIndexTypeDescriptionResult();
    }

    @Override
    public IndexTypeDescriptionEdit getEdit() {
        return IndexEditFactory.getIndexTypeDescriptionEdit();
    }

    @Override
    public IndexTypeDescription getEntity(EditIndexTypeDescriptionResult result) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        IndexTypeDescription indexTypeDescription = null;
        String indexTypeName = spec.getIndexTypeName();
        IndexType indexType = indexControl.getIndexTypeByName(indexTypeName);

        if(indexType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    indexTypeDescription = indexControl.getIndexTypeDescription(indexType, language);
                } else { // EditMode.UPDATE
                    indexTypeDescription = indexControl.getIndexTypeDescriptionForUpdate(indexType, language);
                }

                if(indexTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownIndexTypeDescription.name(), indexTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownIndexTypeName.name(), indexTypeName);
        }

        return indexTypeDescription;
    }

    @Override
    public IndexType getLockEntity(IndexTypeDescription indexTypeDescription) {
        return indexTypeDescription.getIndexType();
    }

    @Override
    public void fillInResult(EditIndexTypeDescriptionResult result, IndexTypeDescription indexTypeDescription) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);

        result.setIndexTypeDescription(indexControl.getIndexTypeDescriptionTransfer(getUserVisit(), indexTypeDescription));
    }

    @Override
    public void doLock(IndexTypeDescriptionEdit edit, IndexTypeDescription indexTypeDescription) {
        edit.setDescription(indexTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(IndexTypeDescription indexTypeDescription) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        IndexTypeDescriptionValue indexTypeDescriptionValue = indexControl.getIndexTypeDescriptionValue(indexTypeDescription);
        indexTypeDescriptionValue.setDescription(edit.getDescription());

        indexControl.updateIndexTypeDescriptionFromValue(indexTypeDescriptionValue, getPartyPK());
    }
    
}
