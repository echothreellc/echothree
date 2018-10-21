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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.remote.edit.IndexEditFactory;
import com.echothree.control.user.index.remote.edit.IndexFieldDescriptionEdit;
import com.echothree.control.user.index.remote.form.EditIndexFieldDescriptionForm;
import com.echothree.control.user.index.remote.result.EditIndexFieldDescriptionResult;
import com.echothree.control.user.index.remote.result.IndexResultFactory;
import com.echothree.control.user.index.remote.spec.IndexFieldDescriptionSpec;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexFieldDescription;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.value.IndexFieldDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditIndexFieldDescriptionCommand
        extends BaseAbstractEditCommand<IndexFieldDescriptionSpec, IndexFieldDescriptionEdit, EditIndexFieldDescriptionResult, IndexFieldDescription, IndexField> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.IndexField.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexFieldName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditIndexFieldDescriptionCommand */
    public EditIndexFieldDescriptionCommand(UserVisitPK userVisitPK, EditIndexFieldDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditIndexFieldDescriptionResult getResult() {
        return IndexResultFactory.getEditIndexFieldDescriptionResult();
    }

    @Override
    public IndexFieldDescriptionEdit getEdit() {
        return IndexEditFactory.getIndexFieldDescriptionEdit();
    }

    @Override
    public IndexFieldDescription getEntity(EditIndexFieldDescriptionResult result) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        IndexFieldDescription indexFieldDescription = null;
        String indexTypeName = spec.getIndexTypeName();
        IndexType indexType = indexControl.getIndexTypeByName(indexTypeName);

        if(indexType != null) {
            String indexFieldName = spec.getIndexFieldName();
            IndexField indexField = indexControl.getIndexFieldByName(indexType, indexFieldName);

            if(indexField != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        indexFieldDescription = indexControl.getIndexFieldDescription(indexField, language);
                    } else { // EditMode.UPDATE
                        indexFieldDescription = indexControl.getIndexFieldDescriptionForUpdate(indexField, language);
                    }

                    if(indexFieldDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownIndexFieldDescription.name(), indexTypeName, indexFieldName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownIndexFieldName.name(), indexTypeName, indexFieldName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownIndexTypeName.name(), indexTypeName);
        }

        return indexFieldDescription;
    }

    @Override
    public IndexField getLockEntity(IndexFieldDescription indexFieldDescription) {
        return indexFieldDescription.getIndexField();
    }

    @Override
    public void fillInResult(EditIndexFieldDescriptionResult result, IndexFieldDescription indexFieldDescription) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);

        result.setIndexFieldDescription(indexControl.getIndexFieldDescriptionTransfer(getUserVisit(), indexFieldDescription));
    }

    @Override
    public void doLock(IndexFieldDescriptionEdit edit, IndexFieldDescription indexFieldDescription) {
        edit.setDescription(indexFieldDescription.getDescription());
    }

    @Override
    public void doUpdate(IndexFieldDescription indexFieldDescription) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        IndexFieldDescriptionValue indexFieldDescriptionValue = indexControl.getIndexFieldDescriptionValue(indexFieldDescription);

        indexFieldDescriptionValue.setDescription(edit.getDescription());

        indexControl.updateIndexFieldDescriptionFromValue(indexFieldDescriptionValue, getPartyPK());
    }

}
