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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.edit.IndexEditFactory;
import com.echothree.control.user.index.common.edit.IndexFieldEdit;
import com.echothree.control.user.index.common.form.EditIndexFieldForm;
import com.echothree.control.user.index.common.result.EditIndexFieldResult;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.control.user.index.common.spec.IndexFieldSpec;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexType;
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

public class EditIndexFieldCommand
        extends BaseAbstractEditCommand<IndexFieldSpec, IndexFieldEdit, EditIndexFieldResult, IndexField, IndexField> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.IndexField.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexFieldName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexFieldName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditIndexFieldCommand */
    public EditIndexFieldCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditIndexFieldResult getResult() {
        return IndexResultFactory.getEditIndexFieldResult();
    }

    @Override
    public IndexFieldEdit getEdit() {
        return IndexEditFactory.getIndexFieldEdit();
    }

    IndexType indexType;

    @Override
    public IndexField getEntity(EditIndexFieldResult result) {
        var indexControl = Session.getModelController(IndexControl.class);
        IndexField indexField = null;
        var indexTypeName = spec.getIndexTypeName();

        indexType = indexControl.getIndexTypeByName(indexTypeName);

        if(indexType != null) {
            var indexFieldName = spec.getIndexFieldName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                indexField = indexControl.getIndexFieldByName(indexType, indexFieldName);
            } else { // EditMode.UPDATE
                indexField = indexControl.getIndexFieldByNameForUpdate(indexType, indexFieldName);
            }

            if(indexField == null) {
                addExecutionError(ExecutionErrors.UnknownIndexFieldName.name(), indexTypeName, indexFieldName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownIndexTypeName.name(), indexTypeName);
        }

        return indexField;
    }

    @Override
    public IndexField getLockEntity(IndexField indexField) {
        return indexField;
    }

    @Override
    public void fillInResult(EditIndexFieldResult result, IndexField indexField) {
        var indexControl = Session.getModelController(IndexControl.class);

        result.setIndexField(indexControl.getIndexFieldTransfer(getUserVisit(), indexField));
    }

    @Override
    public void doLock(IndexFieldEdit edit, IndexField indexField) {
        var indexControl = Session.getModelController(IndexControl.class);
        var indexFieldDescription = indexControl.getIndexFieldDescription(indexField, getPreferredLanguage());
        var indexFieldDetail = indexField.getLastDetail();

        edit.setIndexFieldName(indexFieldDetail.getIndexFieldName());
        edit.setIsDefault(indexFieldDetail.getIsDefault().toString());
        edit.setSortOrder(indexFieldDetail.getSortOrder().toString());

        if(indexFieldDescription != null) {
            edit.setDescription(indexFieldDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(IndexField indexField) {
        var indexControl = Session.getModelController(IndexControl.class);
        var indexTypeDetail = indexType.getLastDetail();
        var indexFieldName = edit.getIndexFieldName();
        var duplicateIndexField = indexControl.getIndexFieldByName(indexType, indexFieldName);

        if(duplicateIndexField != null && !indexField.equals(duplicateIndexField)) {
            addExecutionError(ExecutionErrors.DuplicateIndexFieldName.name(), indexTypeDetail.getIndexTypeName(), indexFieldName);
        }
    }

    @Override
    public void doUpdate(IndexField indexField) {
        var indexControl = Session.getModelController(IndexControl.class);
        var partyPK = getPartyPK();
        var indexFieldDetailValue = indexControl.getIndexFieldDetailValueForUpdate(indexField);
        var indexFieldDescription = indexControl.getIndexFieldDescriptionForUpdate(indexField, getPreferredLanguage());
        var description = edit.getDescription();

        indexFieldDetailValue.setIndexFieldName(edit.getIndexFieldName());
        indexFieldDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        indexFieldDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        indexControl.updateIndexFieldFromValue(indexFieldDetailValue, partyPK);

        if(indexFieldDescription == null && description != null) {
            indexControl.createIndexFieldDescription(indexField, getPreferredLanguage(), description, partyPK);
        } else if(indexFieldDescription != null && description == null) {
            indexControl.deleteIndexFieldDescription(indexFieldDescription, partyPK);
        } else if(indexFieldDescription != null && description != null) {
            var indexFieldDescriptionValue = indexControl.getIndexFieldDescriptionValue(indexFieldDescription);

            indexFieldDescriptionValue.setDescription(description);
            indexControl.updateIndexFieldDescriptionFromValue(indexFieldDescriptionValue, partyPK);
        }
    }

}
