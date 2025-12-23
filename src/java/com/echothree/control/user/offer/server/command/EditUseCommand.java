// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.UseEdit;
import com.echothree.control.user.offer.common.form.EditUseForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseSpec;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.offer.server.logic.UseLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditUseCommand
        extends BaseEditCommand<UseSpec, UseEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Use.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditUseCommand */
    public EditUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var useControl = Session.getModelController(UseControl.class);
        var result = OfferResultFactory.getEditUseResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var useName = spec.getUseName();
            var use = useControl.getUseByName(useName);
            
            if(use != null) {
                result.setUse(useControl.getUseTransfer(getUserVisit(), use));
                
                if(lockEntity(use)) {
                    var useDescription = useControl.getUseDescription(use, getPreferredLanguage());
                    var edit = OfferEditFactory.getUseEdit();
                    var useDetail = use.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setUseName(useDetail.getUseName());
                    edit.setUseTypeName(useDetail.getUseType().getLastDetail().getUseTypeName());
                    edit.setIsDefault(useDetail.getIsDefault().toString());
                    edit.setSortOrder(useDetail.getSortOrder().toString());
                    
                    if(useDescription != null) {
                        edit.setDescription(useDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(use));
            } else {
                addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var useName = spec.getUseName();
            var use = useControl.getUseByNameForUpdate(useName);
            
            if(use != null) {
                useName = edit.getUseName();
                var duplicateUse = useControl.getUseByName(useName);
                
                if(duplicateUse == null || use.equals(duplicateUse)) {
                    var useTypeControl = Session.getModelController(UseTypeControl.class);
                    var useTypeName = edit.getUseTypeName();
                    var useType = useTypeControl.getUseTypeByName(useTypeName);
                    
                    if(useType != null) {
                        if(lockEntityForUpdate(use)) {
                            try {
                                var partyPK = getPartyPK();
                                var useDetailValue = useControl.getUseDetailValueForUpdate(use);
                                var useDescription = useControl.getUseDescriptionForUpdate(use, getPreferredLanguage());
                                var description = edit.getDescription();
                                
                                useDetailValue.setUseName(edit.getUseName());
                                useDetailValue.setUseTypePK(useType.getPrimaryKey());
                                useDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                useDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                UseLogic.getInstance().updateUseFromValue(useDetailValue, partyPK);
                                
                                if(useDescription == null && description != null) {
                                    useControl.createUseDescription(use, getPreferredLanguage(), description, partyPK);
                                } else if(useDescription != null && description == null) {
                                    useControl.deleteUseDescription(useDescription, partyPK);
                                } else if(useDescription != null && description != null) {
                                    var useDescriptionValue = useControl.getUseDescriptionValue(useDescription);
                                    
                                    useDescriptionValue.setDescription(description);
                                    useControl.updateUseDescriptionFromValue(useDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(use);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateUseName.name(), useName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
            }
        }
        
        return result;
    }
    
}
