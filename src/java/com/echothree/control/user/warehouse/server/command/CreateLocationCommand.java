// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.CreateLocationForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.common.workflow.LocationStatusConstants;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.LocationUseTypeLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDetail;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateLocationCommand
        extends BaseSimpleCommand<CreateLocationForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Location.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Velocity", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateLocationCommand */
    public CreateLocationCommand(UserVisitPK userVisitPK, CreateLocationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = WarehouseResultFactory.getCreateLocationResult();
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            String locationName = form.getLocationName();
            Location location = warehouseControl.getLocationByName(warehouseParty, locationName);
            
            if(location == null) {
                String locationTypeName = form.getLocationTypeName();
                LocationType locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                
                if(locationType != null) {
                    Collection locationNameElements = warehouseControl.getLocationNameElementsByLocationType(locationType);
                    int endIndex = 0;
                    boolean validLocationName = true;
                    
                    for(Iterator iter = locationNameElements.iterator(); iter.hasNext() && validLocationName;) {
                        LocationNameElement locationNameElement = (LocationNameElement)iter.next();
                        LocationNameElementDetail locationNameElementDetail = locationNameElement.getLastDetail();
                        String validationPattern = locationNameElementDetail.getValidationPattern();
                        
                        if(validationPattern != null) {
                            try {
                                Pattern pattern = Pattern.compile(validationPattern);
                                int beginIndex = locationNameElementDetail.getOffset();
                                
                                endIndex = beginIndex + locationNameElementDetail.getLength();
                                String substr = locationName.substring(beginIndex, endIndex);
                                Matcher m = pattern.matcher(substr);
                                
                                if(!m.matches()) {
                                    validLocationName = false;
                                }
                            } catch (IndexOutOfBoundsException ioobe) {
                                validLocationName = false;
                            }
                        }
                    }
                    
                    if(locationName.length() > endIndex)
                        validLocationName = false;
                    
                    if(validLocationName) {
                        var locationUseTypeName = form.getLocationUseTypeName();
                        var locationUseType = LocationUseTypeLogic.getInstance().getLocationUseTypeByName(this, locationUseTypeName, null, false);
                        
                        if(!hasExecutionErrors()) {
                            boolean multipleUseError = false;
                            
                            if(!locationUseType.getAllowMultiple()) {
                                if(warehouseControl.countLocationsByLocationUseType(warehouseParty, locationUseType) != 0)
                                    multipleUseError = true;
                            }
                            
                            if(!multipleUseError) {
                                var inventoryControl = Session.getModelController(InventoryControl.class);
                                String inventoryLocationGroupName = form.getInventoryLocationGroupName();
                                InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
                                
                                if(inventoryLocationGroup != null) {
                                    var coreControl = getCoreControl();
                                    Integer velocity = Integer.valueOf(form.getVelocity());
                                    var workflowControl = Session.getModelController(WorkflowControl.class);
                                    BasePK createdBy = getPartyPK();
                                    var description = form.getDescription();
                                    
                                    location = warehouseControl.createLocation(warehouseParty, locationName, locationType, locationUseType,
                                            velocity, inventoryLocationGroup, createdBy);
                                    
                                    EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(inventoryLocationGroup.getPrimaryKey());
                                    WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS, entityInstance);
                                    String workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
                                    String workflowEntranceName = null;
                                    
                                    if(workflowStepName.equals(InventoryLocationGroupStatusConstants.WorkflowStep_ACTIVE))
                                        workflowEntranceName = LocationStatusConstants.WorkflowEntrance_NEW_LOCATION_ACTIVE;
                                    else if(workflowStepName.equals(InventoryLocationGroupStatusConstants.WorkflowStep_INVENTORY_PREP))
                                        workflowEntranceName = LocationStatusConstants.WorkflowEntrance_NEW_LOCATION_INVENTORY_PREP;
                                    else if(workflowStepName.equals(InventoryLocationGroupStatusConstants.WorkflowStep_INVENTORY))
                                        workflowEntranceName = LocationStatusConstants.WorkflowEntrance_NEW_LOCATION_INVENTORY;
                                    
                                    entityInstance = coreControl.getEntityInstanceByBasePK(location.getPrimaryKey());
                                    workflowControl.addEntityToWorkflowUsingNames(null, LocationStatusConstants.Workflow_LOCATION_STATUS, workflowEntranceName, entityInstance, null, null, createdBy);
                                    
                                    if(description != null) {
                                        warehouseControl.createLocationDescription(location, getPreferredLanguage(), description, createdBy);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MultipleLocationUseTypesNotAllowed.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidLocationName.name(), locationName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateLocationName.name(), locationName);
            }
            
            if(location != null) {
                result.setEntityRef(location.getPrimaryKey().getEntityRef());
                result.setLocationName(location.getLastDetail().getLocationName());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
