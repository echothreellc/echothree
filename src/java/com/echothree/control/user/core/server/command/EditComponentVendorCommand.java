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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.ComponentVendorEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditComponentVendorForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditComponentVendorResult;
import com.echothree.control.user.core.common.spec.ComponentVendorSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditComponentVendorCommand
        extends BaseAbstractEditCommand<ComponentVendorSpec, ComponentVendorEdit, EditComponentVendorResult, ComponentVendor, ComponentVendor> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ComponentVendor.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditComponentVendorCommand */
    public EditComponentVendorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditComponentVendorResult getResult() {
        return CoreResultFactory.getEditComponentVendorResult();
    }

    @Override
    public ComponentVendorEdit getEdit() {
        return CoreEditFactory.getComponentVendorEdit();
    }

    @Override
    public ComponentVendor getEntity(EditComponentVendorResult result) {
        ComponentVendor componentVendor;
        var componentVendorName = spec.getComponentVendorName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
        } else { // EditMode.UPDATE
            componentVendor = getComponentControl().getComponentVendorByNameForUpdate(componentVendorName);
        }

        if(componentVendor != null) {
            result.setComponentVendor(getComponentControl().getComponentVendorTransfer(getUserVisit(), componentVendor));
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return componentVendor;
    }

    @Override
    public ComponentVendor getLockEntity(ComponentVendor componentVendor) {
        return componentVendor;
    }

    @Override
    public void fillInResult(EditComponentVendorResult result, ComponentVendor componentVendor) {
        result.setComponentVendor(getComponentControl().getComponentVendorTransfer(getUserVisit(), componentVendor));
    }

    @Override
    public void doLock(ComponentVendorEdit edit, ComponentVendor componentVendor) {
        var componentVendorDetail = componentVendor.getLastDetail();

        edit.setComponentVendorName(componentVendorDetail.getComponentVendorName());
        edit.setDescription(componentVendorDetail.getDescription());
    }

    @Override
    public void canUpdate(ComponentVendor componentVendor) {
        var componentVendorName = edit.getComponentVendorName();
        var duplicateComponentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

        if(duplicateComponentVendor != null && !componentVendor.equals(duplicateComponentVendor)) {
            addExecutionError(ExecutionErrors.DuplicateComponentVendorName.name(), componentVendorName);
        }
    }

    @Override
    public void doUpdate(ComponentVendor componentVendor) {
        var componentVendorDetailValue = getComponentControl().getComponentVendorDetailValueForUpdate(componentVendor);

        componentVendorDetailValue.setComponentVendorName(edit.getComponentVendorName());
        componentVendorDetailValue.setDescription(edit.getDescription());

        getComponentControl().updateComponentVendorFromValue(componentVendorDetailValue, getPartyPK());

    }
    
}
