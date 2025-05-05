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

import com.echothree.control.user.index.common.edit.IndexEdit;
import com.echothree.control.user.index.common.edit.IndexEditFactory;
import com.echothree.control.user.index.common.form.EditIndexForm;
import com.echothree.control.user.index.common.result.EditIndexResult;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.control.user.index.common.spec.IndexSpec;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.Index;
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

public class EditIndexCommand
        extends BaseAbstractEditCommand<IndexSpec, IndexEdit, EditIndexResult, Index, Index> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Index.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Directory", FieldType.STRING, true, null, 80L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditIndexCommand */
    public EditIndexCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditIndexResult getResult() {
        return IndexResultFactory.getEditIndexResult();
    }

    @Override
    public IndexEdit getEdit() {
        return IndexEditFactory.getIndexEdit();
    }

    @Override
    public Index getEntity(EditIndexResult result) {
        var indexControl = Session.getModelController(IndexControl.class);
        Index index;
        var indexName = spec.getIndexName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            index = indexControl.getIndexByName(indexName);
        } else { // EditMode.UPDATE
            index = indexControl.getIndexByNameForUpdate(indexName);
        }

        if(index == null) {
            addExecutionError(ExecutionErrors.UnknownIndexName.name(), indexName);
        }

        return index;
    }

    @Override
    public Index getLockEntity(Index index) {
        return index;
    }

    @Override
    public void fillInResult(EditIndexResult result, Index index) {
        var indexControl = Session.getModelController(IndexControl.class);

        result.setIndex(indexControl.getIndexTransfer(getUserVisit(), index));
    }

    @Override
    public void doLock(IndexEdit edit, Index index) {
        var indexControl = Session.getModelController(IndexControl.class);
        var indexDescription = indexControl.getIndexDescription(index, getPreferredLanguage());
        var indexDetail = index.getLastDetail();

        edit.setIndexName(indexDetail.getIndexName());
        edit.setIsDefault(indexDetail.getIsDefault().toString());
        edit.setSortOrder(indexDetail.getSortOrder().toString());

        if(indexDescription != null) {
            edit.setDescription(indexDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Index index) {
        var indexControl = Session.getModelController(IndexControl.class);
        var indexName = edit.getIndexName();
        var duplicateIndex = indexControl.getIndexByName(indexName);

        if(duplicateIndex != null && !index.equals(duplicateIndex)) {
            addExecutionError(ExecutionErrors.DuplicateIndexName.name(), indexName);
        }
    }

    @Override
    public void doUpdate(Index index) {
        var indexControl = Session.getModelController(IndexControl.class);
        var partyPK = getPartyPK();
        var indexDetailValue = indexControl.getIndexDetailValueForUpdate(index);
        var indexDescription = indexControl.getIndexDescriptionForUpdate(index, getPreferredLanguage());
        var description = edit.getDescription();

        indexDetailValue.setIndexName(edit.getIndexName());
        indexDetailValue.setDirectory(edit.getDirectory());
        indexDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        indexDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        indexControl.updateIndexFromValue(indexDetailValue, partyPK);

        if(indexDescription == null && description != null) {
            indexControl.createIndexDescription(index, getPreferredLanguage(), description, partyPK);
        } else {
            if(indexDescription != null && description == null) {
                indexControl.deleteIndexDescription(indexDescription, partyPK);
            } else {
                if(indexDescription != null && description != null) {
                    var indexDescriptionValue = indexControl.getIndexDescriptionValue(indexDescription);

                    indexDescriptionValue.setDescription(description);
                    indexControl.updateIndexDescriptionFromValue(indexDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
