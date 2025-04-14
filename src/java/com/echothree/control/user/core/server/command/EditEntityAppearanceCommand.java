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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityAppearanceEdit;
import com.echothree.control.user.core.common.form.EditEntityAppearanceForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityRefSpec;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityAppearanceCommand
        extends BaseEditCommand<EntityRefSpec, EntityAppearanceEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAppearance.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityAppearanceCommand */
    public EditEntityAppearanceCommand(UserVisitPK userVisitPK, EditEntityAppearanceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        var result = CoreResultFactory.getEditEntityAppearanceResult();
        var entityRef = spec.getEntityRef();
        var entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance != null) {
            EntityAppearance entityAppearance = null;
            var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                var appearanceControl = Session.getModelController(AppearanceControl.class);

                entityAppearance = appearanceControl.getEntityAppearance(entityInstance);

                if(entityAppearance != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setEntityAppearance(appearanceControl.getEntityAppearanceTransfer(getUserVisit(), entityAppearance));

                        if(lockEntity(basePK)) {
                            var edit = CoreEditFactory.getEntityAppearanceEdit();

                            result.setEdit(edit);
                            edit.setAppearanceName(entityAppearance.getAppearance().getLastDetail().getAppearanceName());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                    } else { // EditMode.ABANDON
                        unlockEntity(basePK);
                        basePK = null;
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAppearance.name(), entityRef);
                }
            } else {
                if(editMode.equals(EditMode.UPDATE)) {
                    var appearanceName = edit.getAppearanceName();
                    var appearance = AppearanceLogic.getInstance().getAppearanceByName(this, appearanceName);

                    if(!hasExecutionErrors()) {
                        var appearanceControl = Session.getModelController(AppearanceControl.class);

                        entityAppearance = appearanceControl.getEntityAppearanceForUpdate(entityInstance);

                        if(entityAppearance != null) {
                            if(lockEntityForUpdate(basePK)) {
                                try {
                                    var entityAppearanceValue = appearanceControl.getEntityAppearanceValue(entityAppearance);

                                    entityAppearanceValue.setAppearancePK(appearance.getPrimaryKey());

                                    appearanceControl.updateEntityAppearanceFromValue(entityAppearanceValue, getPartyPK());
                                } finally {
                                    unlockEntity(basePK);
                                    basePK = null;
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAppearance.name(), entityRef);
                        }
                    }
                }
            }

            if(basePK != null) {
                result.setEntityLock(getEntityLockTransfer(basePK));
            }

            if(entityAppearance != null) {
                var appearanceControl = Session.getModelController(AppearanceControl.class);

                result.setEntityAppearance(appearanceControl.getEntityAppearanceTransfer(getUserVisit(), entityAppearance));
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }

        return result;
    }
    
}
