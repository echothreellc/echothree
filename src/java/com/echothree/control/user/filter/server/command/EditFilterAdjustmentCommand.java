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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterAdjustmentEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.form.EditFilterAdjustmentForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditFilterAdjustmentCommand
        extends BaseEditCommand<FilterAdjustmentSpec, FilterAdjustmentEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditFilterAdjustmentCommand */
    public EditFilterAdjustmentCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        var result = FilterResultFactory.getEditFilterAdjustmentResult();
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                var filterAdjustmentName = spec.getFilterAdjustmentName();
                var filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
                
                if(filterAdjustment != null) {
                    result.setFilterAdjustment(filterControl.getFilterAdjustmentTransfer(getUserVisit(), filterAdjustment));
                    
                    if(lockEntity(filterAdjustment)) {
                        var filterAdjustmentDescription = filterControl.getFilterAdjustmentDescription(filterAdjustment, getPreferredLanguage());
                        var edit = FilterEditFactory.getFilterAdjustmentEdit();
                        var filterAdjustmentDetail = filterAdjustment.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setFilterAdjustmentName(filterAdjustmentDetail.getFilterAdjustmentName());
                        edit.setFilterAdjustmentSourceName(filterAdjustmentDetail.getFilterAdjustmentSource().getFilterAdjustmentSourceName());
                        edit.setIsDefault(filterAdjustmentDetail.getIsDefault().toString());
                        edit.setSortOrder(filterAdjustmentDetail.getSortOrder().toString());
                        
                        if(filterAdjustmentDescription != null) {
                            edit.setDescription(filterAdjustmentDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(filterAdjustment));
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var filterAdjustmentName = spec.getFilterAdjustmentName();
                var filterAdjustment = filterControl.getFilterAdjustmentByNameForUpdate(filterKind, filterAdjustmentName);
                
                if(filterAdjustment != null) {
                    filterAdjustmentName = edit.getFilterAdjustmentName();
                    var duplicateFilterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
                    
                    if(duplicateFilterAdjustment == null || filterAdjustment.equals(duplicateFilterAdjustment)) {
                        var filterAdjustmentSourceName = edit.getFilterAdjustmentSourceName();
                        var filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);
                        
                        if(filterAdjustmentSource != null) {
                            if(lockEntityForUpdate(filterAdjustment)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var filterAdjustmentDetailValue = filterControl.getFilterAdjustmentDetailValueForUpdate(filterAdjustment);
                                    var filterAdjustmentDescription = filterControl.getFilterAdjustmentDescriptionForUpdate(filterAdjustment, getPreferredLanguage());
                                    var description = edit.getDescription();
                                    
                                    filterAdjustmentDetailValue.setFilterAdjustmentName(edit.getFilterAdjustmentName());
                                    filterAdjustmentDetailValue.setFilterAdjustmentSourcePK(filterAdjustmentSource.getPrimaryKey());
                                    filterAdjustmentDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    filterAdjustmentDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    filterControl.updateFilterAdjustmentFromValue(filterAdjustmentDetailValue, partyPK);
                                    
                                    if(filterAdjustmentDescription == null && description != null) {
                                        filterControl.createFilterAdjustmentDescription(filterAdjustment, getPreferredLanguage(), description, partyPK);
                                    } else if(filterAdjustmentDescription != null && description == null) {
                                        filterControl.deleteFilterAdjustmentDescription(filterAdjustmentDescription, partyPK);
                                    } else if(filterAdjustmentDescription != null && description != null) {
                                        var filterAdjustmentDescriptionValue = filterControl.getFilterAdjustmentDescriptionValue(filterAdjustmentDescription);
                                        
                                        filterAdjustmentDescriptionValue.setDescription(description);
                                        filterControl.updateFilterAdjustmentDescriptionFromValue(filterAdjustmentDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(filterAdjustment);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentSourceName.name(), filterAdjustmentSourceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateFilterAdjustmentName.name(), filterAdjustmentName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return result;
    }
    
}
