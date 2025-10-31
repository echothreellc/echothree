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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.edit.PicklistAliasEdit;
import com.echothree.control.user.picklist.common.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.common.form.EditPicklistAliasForm;
import com.echothree.control.user.picklist.common.result.EditPicklistAliasResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.common.spec.PicklistAliasSpec;
import com.echothree.control.user.picklist.server.command.util.PicklistAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.picklist.server.entity.PicklistAlias;
import com.echothree.model.data.picklist.server.entity.PicklistAliasType;
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
public class EditPicklistAliasCommand
        extends BaseAbstractEditCommand<PicklistAliasSpec, PicklistAliasEdit, EditPicklistAliasResult, PicklistAlias, PicklistAlias> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPicklistAliasCommand */
    public EditPicklistAliasCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PicklistAliasUtil.getInstance().getSecurityRoleGroupNameByPicklistTypeSpec(spec), SecurityRoles.Edit.name())
                )))
        )));
    }

    @Override
    public EditPicklistAliasResult getResult() {
        return PicklistResultFactory.getEditPicklistAliasResult();
    }

    @Override
    public PicklistAliasEdit getEdit() {
        return PicklistEditFactory.getPicklistAliasEdit();
    }

    PicklistAliasType picklistAliasType;
    
    @Override
    public PicklistAlias getEntity(EditPicklistAliasResult result) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        PicklistAlias picklistAlias = null;
        var picklistTypeName = spec.getPicklistTypeName();
        var picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            var picklistName = spec.getPicklistName();
            var picklist = picklistControl.getPicklistByName(picklistType, picklistName);

            if(picklist != null) {
                var picklistAliasTypeName = spec.getPicklistAliasTypeName();

                picklistAliasType = picklistControl.getPicklistAliasTypeByName(picklistType, picklistAliasTypeName);

                if(picklistAliasType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        picklistAlias = picklistControl.getPicklistAlias(picklist, picklistAliasType);
                    } else { // EditMode.UPDATE
                        picklistAlias = picklistControl.getPicklistAliasForUpdate(picklist, picklistAliasType);
                    }

                    if(picklistAlias != null) {
                        result.setPicklistAlias(picklistControl.getPicklistAliasTransfer(getUserVisit(), picklistAlias));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPicklistAlias.name(), picklistTypeName, picklistName, picklistAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPicklistAliasTypeName.name(), picklistTypeName, picklistAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistName.name(), picklistTypeName, picklistName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistAlias;
    }

    @Override
    public PicklistAlias getLockEntity(PicklistAlias picklistAlias) {
        return picklistAlias;
    }

    @Override
    public void fillInResult(EditPicklistAliasResult result, PicklistAlias picklistAlias) {
        var picklistControl = Session.getModelController(PicklistControl.class);

        result.setPicklistAlias(picklistControl.getPicklistAliasTransfer(getUserVisit(), picklistAlias));
    }

    @Override
    public void doLock(PicklistAliasEdit edit, PicklistAlias picklistAlias) {
        edit.setAlias(picklistAlias.getAlias());
    }

    @Override
    public void canUpdate(PicklistAlias picklistAlias) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var alias = edit.getAlias();
        var duplicatePicklistAlias = picklistControl.getPicklistAliasByAlias(picklistAliasType, alias);

        if(duplicatePicklistAlias != null && !picklistAlias.equals(duplicatePicklistAlias)) {
            var picklistAliasTypeDetail = picklistAlias.getPicklistAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicatePicklistAlias.name(), picklistAliasTypeDetail.getPicklistType().getLastDetail().getPicklistTypeName(),
                    picklistAliasTypeDetail.getPicklistAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(PicklistAlias picklistAlias) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistAliasValue = picklistControl.getPicklistAliasValue(picklistAlias);

        picklistAliasValue.setAlias(edit.getAlias());

        picklistControl.updatePicklistAliasFromValue(picklistAliasValue, getPartyPK());
    }

}
