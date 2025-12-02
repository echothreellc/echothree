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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.edit.ChainEntityRoleTypeEdit;
import com.echothree.control.user.chain.common.form.EditChainEntityRoleTypeForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainEntityRoleTypeResult;
import com.echothree.control.user.chain.common.spec.ChainEntityRoleTypeSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditChainEntityRoleTypeCommand
        extends BaseAbstractEditCommand<ChainEntityRoleTypeSpec, ChainEntityRoleTypeEdit, EditChainEntityRoleTypeResult, ChainEntityRoleType, ChainEntityRoleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainEntityRoleType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainEntityRoleTypeCommand */
    public EditChainEntityRoleTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainEntityRoleTypeResult getResult() {
        return ChainResultFactory.getEditChainEntityRoleTypeResult();
    }

    @Override
    public ChainEntityRoleTypeEdit getEdit() {
        return ChainEditFactory.getChainEntityRoleTypeEdit();
    }

    ChainType chainType;

    @Override
    public ChainEntityRoleType getEntity(EditChainEntityRoleTypeResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainEntityRoleType chainEntityRoleType = null;
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();
            
            chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                var chainEntityRoleTypeName = spec.getChainEntityRoleTypeName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    chainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName);
                } else { // EditMode.UPDATE
                    chainEntityRoleType = chainControl.getChainEntityRoleTypeByNameForUpdate(chainType, chainEntityRoleTypeName);
                }

                if(chainEntityRoleType == null) {
                    addExecutionError(ExecutionErrors.UnknownChainEntityRoleTypeName.name(), chainKindName, chainTypeName, chainEntityRoleTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainEntityRoleType;
    }

    @Override
    public ChainEntityRoleType getLockEntity(ChainEntityRoleType chainEntityRoleType) {
        return chainEntityRoleType;
    }

    @Override
    public void fillInResult(EditChainEntityRoleTypeResult result, ChainEntityRoleType chainEntityRoleType) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainEntityRoleType(chainControl.getChainEntityRoleTypeTransfer(getUserVisit(), chainEntityRoleType));
    }

    @Override
    public void doLock(ChainEntityRoleTypeEdit edit, ChainEntityRoleType chainEntityRoleType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainEntityRoleTypeDescription = chainControl.getChainEntityRoleTypeDescription(chainEntityRoleType, getPreferredLanguage());
        var chainEntityRoleTypeDetail = chainEntityRoleType.getLastDetail();
        var entityTypeDetail = chainEntityRoleTypeDetail.getEntityType().getLastDetail();

        edit.setChainEntityRoleTypeName(chainEntityRoleTypeDetail.getChainEntityRoleTypeName());
        edit.setComponentVendorName(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
        edit.setEntityTypeName(entityTypeDetail.getEntityTypeName());
        edit.setSortOrder(chainEntityRoleTypeDetail.getSortOrder().toString());

        if(chainEntityRoleTypeDescription != null) {
            edit.setDescription(chainEntityRoleTypeDescription.getDescription());
        }
    }

    EntityType entityType;
    
    @Override
    public void canUpdate(ChainEntityRoleType chainEntityRoleType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainTypeDetail = chainType.getLastDetail();
        var chainEntityRoleTypeName = edit.getChainEntityRoleTypeName();
        var duplicateChainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName);

        if(duplicateChainEntityRoleType != null && !chainEntityRoleType.equals(duplicateChainEntityRoleType)) {
            addExecutionError(ExecutionErrors.DuplicateChainEntityRoleTypeName.name(), chainTypeDetail.getChainTypeName(), chainEntityRoleTypeName);
        } else {
            var componentVendorName = edit.getComponentVendorName();
            var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

            if(componentVendor != null) {
                var entityTypeName = edit.getEntityTypeName();
                
                entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

                if(entityType == null) {
                    addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        }
    }

    @Override
    public void doUpdate(ChainEntityRoleType chainEntityRoleType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var partyPK = getPartyPK();
        var chainEntityRoleTypeDetailValue = chainControl.getChainEntityRoleTypeDetailValueForUpdate(chainEntityRoleType);
        var chainEntityRoleTypeDescription = chainControl.getChainEntityRoleTypeDescriptionForUpdate(chainEntityRoleType, getPreferredLanguage());
        var description = edit.getDescription();

        chainEntityRoleTypeDetailValue.setChainEntityRoleTypeName(edit.getChainEntityRoleTypeName());
        chainEntityRoleTypeDetailValue.setEntityTypePK(entityType.getPrimaryKey());
        chainEntityRoleTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainEntityRoleTypeFromValue(chainEntityRoleTypeDetailValue, partyPK);

        if(chainEntityRoleTypeDescription == null && description != null) {
            chainControl.createChainEntityRoleTypeDescription(chainEntityRoleType, getPreferredLanguage(), description, partyPK);
        } else if(chainEntityRoleTypeDescription != null && description == null) {
            chainControl.deleteChainEntityRoleTypeDescription(chainEntityRoleTypeDescription, partyPK);
        } else if(chainEntityRoleTypeDescription != null && description != null) {
            var chainEntityRoleTypeDescriptionValue = chainControl.getChainEntityRoleTypeDescriptionValue(chainEntityRoleTypeDescription);

            chainEntityRoleTypeDescriptionValue.setDescription(description);
            chainControl.updateChainEntityRoleTypeDescriptionFromValue(chainEntityRoleTypeDescriptionValue, partyPK);
        }
    }

}
