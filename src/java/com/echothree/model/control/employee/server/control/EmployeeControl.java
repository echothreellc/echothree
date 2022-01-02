// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.employee.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.employee.common.choice.EmployeeAvailabilityChoicesBean;
import com.echothree.model.control.employee.common.choice.EmployeeStatusChoicesBean;
import com.echothree.model.control.employee.common.choice.EmployeeTypeChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveReasonChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveStatusChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveTypeChoicesBean;
import com.echothree.model.control.employee.common.choice.ResponsibilityTypeChoicesBean;
import com.echothree.model.control.employee.common.choice.SkillTypeChoicesBean;
import com.echothree.model.control.employee.common.choice.TerminationReasonChoicesBean;
import com.echothree.model.control.employee.common.choice.TerminationTypeChoicesBean;
import com.echothree.model.control.employee.common.transfer.EmployeeResultTransfer;
import com.echothree.model.control.employee.common.transfer.EmployeeTransfer;
import com.echothree.model.control.employee.common.transfer.EmployeeTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.EmployeeTypeTransfer;
import com.echothree.model.control.employee.common.transfer.EmploymentTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveReasonDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveReasonTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveTypeTransfer;
import com.echothree.model.control.employee.common.transfer.PartyResponsibilityTransfer;
import com.echothree.model.control.employee.common.transfer.PartySkillTransfer;
import com.echothree.model.control.employee.common.transfer.ResponsibilityTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.ResponsibilityTypeTransfer;
import com.echothree.model.control.employee.common.transfer.SkillTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.SkillTypeTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationReasonDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationReasonTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationTypeTransfer;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.employee.server.graphql.EmployeeObject;
import com.echothree.model.control.employee.server.transfer.EmployeeTransferCache;
import com.echothree.model.control.employee.server.transfer.EmployeeTransferCaches;
import com.echothree.model.control.employee.server.transfer.EmployeeTypeDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.EmployeeTypeTransferCache;
import com.echothree.model.control.employee.server.transfer.EmploymentTransferCache;
import com.echothree.model.control.employee.server.transfer.LeaveReasonDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.LeaveReasonTransferCache;
import com.echothree.model.control.employee.server.transfer.LeaveTransferCache;
import com.echothree.model.control.employee.server.transfer.LeaveTypeDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.LeaveTypeTransferCache;
import com.echothree.model.control.employee.server.transfer.PartyResponsibilityTransferCache;
import com.echothree.model.control.employee.server.transfer.PartySkillTransferCache;
import com.echothree.model.control.employee.server.transfer.ResponsibilityTypeDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.ResponsibilityTypeTransferCache;
import com.echothree.model.control.employee.server.transfer.SkillTypeDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.SkillTypeTransferCache;
import com.echothree.model.control.employee.server.transfer.TerminationReasonDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.TerminationReasonTransferCache;
import com.echothree.model.control.employee.server.transfer.TerminationTypeDescriptionTransferCache;
import com.echothree.model.control.employee.server.transfer.TerminationTypeTransferCache;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.employee.common.pk.EmployeeTypePK;
import com.echothree.model.data.employee.common.pk.EmploymentPK;
import com.echothree.model.data.employee.common.pk.LeavePK;
import com.echothree.model.data.employee.common.pk.LeaveReasonPK;
import com.echothree.model.data.employee.common.pk.LeaveTypePK;
import com.echothree.model.data.employee.common.pk.ResponsibilityTypePK;
import com.echothree.model.data.employee.common.pk.SkillTypePK;
import com.echothree.model.data.employee.common.pk.TerminationReasonPK;
import com.echothree.model.data.employee.common.pk.TerminationTypePK;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDescription;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDetail;
import com.echothree.model.data.employee.server.entity.Employment;
import com.echothree.model.data.employee.server.entity.EmploymentDetail;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.entity.LeaveDetail;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveReasonDescription;
import com.echothree.model.data.employee.server.entity.LeaveReasonDetail;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.LeaveTypeDescription;
import com.echothree.model.data.employee.server.entity.LeaveTypeDetail;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.employee.server.entity.PartyResponsibility;
import com.echothree.model.data.employee.server.entity.PartySkill;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.ResponsibilityTypeDescription;
import com.echothree.model.data.employee.server.entity.ResponsibilityTypeDetail;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.employee.server.entity.SkillTypeDescription;
import com.echothree.model.data.employee.server.entity.SkillTypeDetail;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationReasonDescription;
import com.echothree.model.data.employee.server.entity.TerminationReasonDetail;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.employee.server.entity.TerminationTypeDescription;
import com.echothree.model.data.employee.server.entity.TerminationTypeDetail;
import com.echothree.model.data.employee.server.factory.EmployeeTypeDescriptionFactory;
import com.echothree.model.data.employee.server.factory.EmployeeTypeDetailFactory;
import com.echothree.model.data.employee.server.factory.EmployeeTypeFactory;
import com.echothree.model.data.employee.server.factory.EmploymentDetailFactory;
import com.echothree.model.data.employee.server.factory.EmploymentFactory;
import com.echothree.model.data.employee.server.factory.LeaveDetailFactory;
import com.echothree.model.data.employee.server.factory.LeaveFactory;
import com.echothree.model.data.employee.server.factory.LeaveReasonDescriptionFactory;
import com.echothree.model.data.employee.server.factory.LeaveReasonDetailFactory;
import com.echothree.model.data.employee.server.factory.LeaveReasonFactory;
import com.echothree.model.data.employee.server.factory.LeaveTypeDescriptionFactory;
import com.echothree.model.data.employee.server.factory.LeaveTypeDetailFactory;
import com.echothree.model.data.employee.server.factory.LeaveTypeFactory;
import com.echothree.model.data.employee.server.factory.PartyEmployeeFactory;
import com.echothree.model.data.employee.server.factory.PartyResponsibilityFactory;
import com.echothree.model.data.employee.server.factory.PartySkillFactory;
import com.echothree.model.data.employee.server.factory.ResponsibilityTypeDescriptionFactory;
import com.echothree.model.data.employee.server.factory.ResponsibilityTypeDetailFactory;
import com.echothree.model.data.employee.server.factory.ResponsibilityTypeFactory;
import com.echothree.model.data.employee.server.factory.SkillTypeDescriptionFactory;
import com.echothree.model.data.employee.server.factory.SkillTypeDetailFactory;
import com.echothree.model.data.employee.server.factory.SkillTypeFactory;
import com.echothree.model.data.employee.server.factory.TerminationReasonDescriptionFactory;
import com.echothree.model.data.employee.server.factory.TerminationReasonDetailFactory;
import com.echothree.model.data.employee.server.factory.TerminationReasonFactory;
import com.echothree.model.data.employee.server.factory.TerminationTypeDescriptionFactory;
import com.echothree.model.data.employee.server.factory.TerminationTypeDetailFactory;
import com.echothree.model.data.employee.server.factory.TerminationTypeFactory;
import com.echothree.model.data.employee.server.value.EmployeeTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.EmployeeTypeDetailValue;
import com.echothree.model.data.employee.server.value.EmploymentDetailValue;
import com.echothree.model.data.employee.server.value.LeaveDetailValue;
import com.echothree.model.data.employee.server.value.LeaveReasonDescriptionValue;
import com.echothree.model.data.employee.server.value.LeaveReasonDetailValue;
import com.echothree.model.data.employee.server.value.LeaveTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.LeaveTypeDetailValue;
import com.echothree.model.data.employee.server.value.PartyEmployeeValue;
import com.echothree.model.data.employee.server.value.PartyResponsibilityValue;
import com.echothree.model.data.employee.server.value.PartySkillValue;
import com.echothree.model.data.employee.server.value.ResponsibilityTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.ResponsibilityTypeDetailValue;
import com.echothree.model.data.employee.server.value.SkillTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.SkillTypeDetailValue;
import com.echothree.model.data.employee.server.value.TerminationReasonDescriptionValue;
import com.echothree.model.data.employee.server.value.TerminationReasonDetailValue;
import com.echothree.model.data.employee.server.value.TerminationTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.TerminationTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmployeeControl
        extends BaseModelControl {
    
    /** Creates a new instance of EmployeeControl */
    public EmployeeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Transfer Caches
    // --------------------------------------------------------------------------------
    
    private EmployeeTransferCaches employeeTransferCaches;
    
    public EmployeeTransferCaches getEmployeeTransferCaches(UserVisit userVisit) {
        if(employeeTransferCaches == null) {
            employeeTransferCaches = new EmployeeTransferCaches(userVisit, this);
        }
        
        return employeeTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Responsibility Types
    // --------------------------------------------------------------------------------
    
    public ResponsibilityType createResponsibilityType(String responsibilityTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ResponsibilityType defaultResponsibilityType = getDefaultResponsibilityType();
        boolean defaultFound = defaultResponsibilityType != null;
        
        if(defaultFound && isDefault) {
            ResponsibilityTypeDetailValue defaultResponsibilityTypeDetailValue = getDefaultResponsibilityTypeDetailValueForUpdate();
            
            defaultResponsibilityTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateResponsibilityTypeFromValue(defaultResponsibilityTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ResponsibilityType responsibilityType = ResponsibilityTypeFactory.getInstance().create();
        ResponsibilityTypeDetail responsibilityTypeDetail = ResponsibilityTypeDetailFactory.getInstance().create(session,
                responsibilityType, responsibilityTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        responsibilityType = ResponsibilityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, responsibilityType.getPrimaryKey());
        responsibilityType.setActiveDetail(responsibilityTypeDetail);
        responsibilityType.setLastDetail(responsibilityTypeDetail);
        responsibilityType.store();
        
        sendEventUsingNames(responsibilityType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return responsibilityType;
    }
    
    private ResponsibilityType getResponsibilityTypeByName(String responsibilityTypeName, EntityPermission entityPermission) {
        ResponsibilityType responsibilityType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM responsibilitytypes, responsibilitytypedetails " +
                        "WHERE rsptyp_activedetailid = rsptypdt_responsibilitytypedetailid AND rsptypdt_responsibilitytypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM responsibilitytypes, responsibilitytypedetails " +
                        "WHERE rsptyp_activedetailid = rsptypdt_responsibilitytypedetailid AND rsptypdt_responsibilitytypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ResponsibilityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, responsibilityTypeName);
            
            responsibilityType = ResponsibilityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return responsibilityType;
    }
    
    public ResponsibilityType getResponsibilityTypeByName(String responsibilityTypeName) {
        return getResponsibilityTypeByName(responsibilityTypeName, EntityPermission.READ_ONLY);
    }
    
    public ResponsibilityType getResponsibilityTypeByNameForUpdate(String responsibilityTypeName) {
        return getResponsibilityTypeByName(responsibilityTypeName, EntityPermission.READ_WRITE);
    }
    
    public ResponsibilityTypeDetailValue getResponsibilityTypeDetailValueForUpdate(ResponsibilityType responsibilityType) {
        return responsibilityType == null? null: responsibilityType.getLastDetailForUpdate().getResponsibilityTypeDetailValue().clone();
    }
    
    public ResponsibilityTypeDetailValue getResponsibilityTypeDetailValueByNameForUpdate(String responsibilityTypeName) {
        return getResponsibilityTypeDetailValueForUpdate(getResponsibilityTypeByNameForUpdate(responsibilityTypeName));
    }
    
    private ResponsibilityType getDefaultResponsibilityType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM responsibilitytypes, responsibilitytypedetails " +
                    "WHERE rsptyp_activedetailid = rsptypdt_responsibilitytypedetailid AND rsptypdt_sortorder = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM responsibilitytypes, responsibilitytypedetails " +
                    "WHERE rsptyp_activedetailid = rsptypdt_responsibilitytypedetailid AND rsptypdt_sortorder = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = ResponsibilityTypeFactory.getInstance().prepareStatement(query);
        
        return ResponsibilityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public ResponsibilityType getDefaultResponsibilityType() {
        return getDefaultResponsibilityType(EntityPermission.READ_ONLY);
    }
    
    public ResponsibilityType getDefaultResponsibilityTypeForUpdate() {
        return getDefaultResponsibilityType(EntityPermission.READ_WRITE);
    }
    
    public ResponsibilityTypeDetailValue getDefaultResponsibilityTypeDetailValueForUpdate() {
        return getDefaultResponsibilityTypeForUpdate().getLastDetailForUpdate().getResponsibilityTypeDetailValue().clone();
    }
    
    private List<ResponsibilityType> getResponsibilityTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM responsibilitytypes, responsibilitytypedetails " +
                    "WHERE rsptyp_activedetailid = rsptypdt_responsibilitytypedetailid " +
                    "ORDER BY rsptypdt_sortorder, rsptypdt_responsibilitytypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM responsibilitytypes, responsibilitytypedetails " +
                    "WHERE rsptyp_activedetailid = rsptypdt_responsibilitytypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = ResponsibilityTypeFactory.getInstance().prepareStatement(query);
        
        return ResponsibilityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<ResponsibilityType> getResponsibilityTypes() {
        return getResponsibilityTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ResponsibilityType> getResponsibilityTypesForUpdate() {
        return getResponsibilityTypes(EntityPermission.READ_WRITE);
    }
    
    public ResponsibilityTypeTransfer getResponsibilityTypeTransfer(UserVisit userVisit, ResponsibilityType responsibilityType) {
        return getEmployeeTransferCaches(userVisit).getResponsibilityTypeTransferCache().getResponsibilityTypeTransfer(responsibilityType);
    }
    
    public List<ResponsibilityTypeTransfer> getResponsibilityTypeTransfers(UserVisit userVisit) {
        List<ResponsibilityType> responsibilityTypes = getResponsibilityTypes();
        List<ResponsibilityTypeTransfer> responsibilityTypeTransfers = new ArrayList<>(responsibilityTypes.size());
        ResponsibilityTypeTransferCache responsibilityTypeTransferCache = getEmployeeTransferCaches(userVisit).getResponsibilityTypeTransferCache();
        
        responsibilityTypes.forEach((responsibilityType) ->
                responsibilityTypeTransfers.add(responsibilityTypeTransferCache.getResponsibilityTypeTransfer(responsibilityType))
        );
        
        return responsibilityTypeTransfers;
    }
    
    public ResponsibilityTypeChoicesBean getResponsibilityTypeChoices(String defaultResponsibilityTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ResponsibilityType> responsibilityTypes = getResponsibilityTypes();
        var size = responsibilityTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultResponsibilityTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var responsibilityType : responsibilityTypes) {
            ResponsibilityTypeDetail responsibilityTypeDetail = responsibilityType.getLastDetail();
            
            var label = getBestResponsibilityTypeDescription(responsibilityType, language);
            var value = responsibilityTypeDetail.getResponsibilityTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultResponsibilityTypeChoice != null && defaultResponsibilityTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && responsibilityTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ResponsibilityTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateResponsibilityTypeFromValue(ResponsibilityTypeDetailValue responsibilityTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(responsibilityTypeDetailValue.hasBeenModified()) {
            ResponsibilityType responsibilityType = ResponsibilityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     responsibilityTypeDetailValue.getResponsibilityTypePK());
            ResponsibilityTypeDetail responsibilityTypeDetail = responsibilityType.getActiveDetailForUpdate();
            
            responsibilityTypeDetail.setThruTime(session.START_TIME_LONG);
            responsibilityTypeDetail.store();
            
            ResponsibilityTypePK responsibilityTypePK = responsibilityTypeDetail.getResponsibilityTypePK();
            String responsibilityTypeName = responsibilityTypeDetailValue.getResponsibilityTypeName();
            Boolean isDefault = responsibilityTypeDetailValue.getIsDefault();
            Integer sortOrder = responsibilityTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                ResponsibilityType defaultResponsibilityType = getDefaultResponsibilityType();
                boolean defaultFound = defaultResponsibilityType != null && !defaultResponsibilityType.equals(responsibilityType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ResponsibilityTypeDetailValue defaultResponsibilityTypeDetailValue = getDefaultResponsibilityTypeDetailValueForUpdate();
                    
                    defaultResponsibilityTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateResponsibilityTypeFromValue(defaultResponsibilityTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            responsibilityTypeDetail = ResponsibilityTypeDetailFactory.getInstance().create(responsibilityTypePK,
                    responsibilityTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            responsibilityType.setActiveDetail(responsibilityTypeDetail);
            responsibilityType.setLastDetail(responsibilityTypeDetail);
            
            sendEventUsingNames(responsibilityTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateResponsibilityTypeFromValue(ResponsibilityTypeDetailValue responsibilityTypeDetailValue, BasePK updatedBy) {
        updateResponsibilityTypeFromValue(responsibilityTypeDetailValue, true, updatedBy);
    }
    
    public void deleteResponsibilityType(ResponsibilityType responsibilityType, BasePK deletedBy) {
        deleteResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType, deletedBy);
        deletePartyResponsibilitiesByResponsibilityType(responsibilityType, deletedBy);
        
        ResponsibilityTypeDetail responsibilityTypeDetail = responsibilityType.getLastDetailForUpdate();
        responsibilityTypeDetail.setThruTime(session.START_TIME_LONG);
        responsibilityType.setActiveDetail(null);
        responsibilityType.store();
        
        // Check for default, and pick one if necessary
        ResponsibilityType defaultResponsibilityType = getDefaultResponsibilityType();
        if(defaultResponsibilityType == null) {
            List<ResponsibilityType> responsibilityTypes = getResponsibilityTypesForUpdate();
            
            if(!responsibilityTypes.isEmpty()) {
                Iterator iter = responsibilityTypes.iterator();
                if(iter.hasNext()) {
                    defaultResponsibilityType = (ResponsibilityType)iter.next();
                }
                ResponsibilityTypeDetailValue responsibilityTypeDetailValue = Objects.requireNonNull(defaultResponsibilityType).getLastDetailForUpdate().getResponsibilityTypeDetailValue().clone();
                
                responsibilityTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateResponsibilityTypeFromValue(responsibilityTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(responsibilityType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ResponsibilityTypeDescription createResponsibilityTypeDescription(ResponsibilityType responsibilityType,
            Language language, String description, BasePK createdBy) {
        ResponsibilityTypeDescription responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().create(session,
                responsibilityType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(responsibilityType.getPrimaryKey(), EventTypes.MODIFY.name(), responsibilityTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return responsibilityTypeDescription;
    }
    
    private ResponsibilityTypeDescription getResponsibilityTypeDescription(ResponsibilityType responsibilityType,
            Language language, EntityPermission entityPermission) {
        ResponsibilityTypeDescription responsibilityTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM responsibilitytypedescriptions " +
                        "WHERE rsptypd_rsptyp_responsibilitytypeid = ? AND rsptypd_lang_languageid = ? AND rsptypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM responsibilitytypedescriptions " +
                        "WHERE rsptypd_rsptyp_responsibilitytypeid = ? AND rsptypd_lang_languageid = ? AND rsptypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ResponsibilityTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, responsibilityType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return responsibilityTypeDescription;
    }
    
    public ResponsibilityTypeDescription getResponsibilityTypeDescription(ResponsibilityType responsibilityType, Language language) {
        return getResponsibilityTypeDescription(responsibilityType, language, EntityPermission.READ_ONLY);
    }
    
    public ResponsibilityTypeDescription getResponsibilityTypeDescriptionForUpdate(ResponsibilityType responsibilityType, Language language) {
        return getResponsibilityTypeDescription(responsibilityType, language, EntityPermission.READ_WRITE);
    }
    
    public ResponsibilityTypeDescriptionValue getResponsibilityTypeDescriptionValue(ResponsibilityTypeDescription responsibilityTypeDescription) {
        return responsibilityTypeDescription == null? null: responsibilityTypeDescription.getResponsibilityTypeDescriptionValue().clone();
    }
    
    public ResponsibilityTypeDescriptionValue getResponsibilityTypeDescriptionValueForUpdate(ResponsibilityType responsibilityType, Language language) {
        return getResponsibilityTypeDescriptionValue(getResponsibilityTypeDescriptionForUpdate(responsibilityType, language));
    }
    
    private List<ResponsibilityTypeDescription> getResponsibilityTypeDescriptionsByResponsibilityType(ResponsibilityType responsibilityType, EntityPermission entityPermission) {
        List<ResponsibilityTypeDescription> responsibilityTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM responsibilitytypedescriptions, languages " +
                        "WHERE rsptypd_rsptyp_responsibilitytypeid = ? AND rsptypd_thrutime = ? AND rsptypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM responsibilitytypedescriptions " +
                        "WHERE rsptypd_rsptyp_responsibilitytypeid = ? AND rsptypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ResponsibilityTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, responsibilityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            responsibilityTypeDescriptions = ResponsibilityTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return responsibilityTypeDescriptions;
    }
    
    public List<ResponsibilityTypeDescription> getResponsibilityTypeDescriptionsByResponsibilityType(ResponsibilityType responsibilityType) {
        return getResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType, EntityPermission.READ_ONLY);
    }
    
    public List<ResponsibilityTypeDescription> getResponsibilityTypeDescriptionsByResponsibilityTypeForUpdate(ResponsibilityType responsibilityType) {
        return getResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType, EntityPermission.READ_WRITE);
    }
    
    public String getBestResponsibilityTypeDescription(ResponsibilityType responsibilityType, Language language) {
        String description;
        ResponsibilityTypeDescription responsibilityTypeDescription = getResponsibilityTypeDescription(responsibilityType, language);
        
        if(responsibilityTypeDescription == null && !language.getIsDefault()) {
            responsibilityTypeDescription = getResponsibilityTypeDescription(responsibilityType, getPartyControl().getDefaultLanguage());
        }
        
        if(responsibilityTypeDescription == null) {
            description = responsibilityType.getLastDetail().getResponsibilityTypeName();
        } else {
            description = responsibilityTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public ResponsibilityTypeDescriptionTransfer getResponsibilityTypeDescriptionTransfer(UserVisit userVisit, ResponsibilityTypeDescription responsibilityTypeDescription) {
        return getEmployeeTransferCaches(userVisit).getResponsibilityTypeDescriptionTransferCache().getResponsibilityTypeDescriptionTransfer(responsibilityTypeDescription);
    }
    
    public List<ResponsibilityTypeDescriptionTransfer> getResponsibilityTypeDescriptionTransfers(UserVisit userVisit, ResponsibilityType responsibilityType) {
        List<ResponsibilityTypeDescription> responsibilityTypeDescriptions = getResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType);
        List<ResponsibilityTypeDescriptionTransfer> responsibilityTypeDescriptionTransfers = new ArrayList<>(responsibilityTypeDescriptions.size());
        ResponsibilityTypeDescriptionTransferCache responsibilityTypeDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getResponsibilityTypeDescriptionTransferCache();
        
        responsibilityTypeDescriptions.forEach((responsibilityTypeDescription) ->
                responsibilityTypeDescriptionTransfers.add(responsibilityTypeDescriptionTransferCache.getResponsibilityTypeDescriptionTransfer(responsibilityTypeDescription))
        );
        
        return responsibilityTypeDescriptionTransfers;
    }
    
    public void updateResponsibilityTypeDescriptionFromValue(ResponsibilityTypeDescriptionValue responsibilityTypeDescriptionValue, BasePK updatedBy) {
        if(responsibilityTypeDescriptionValue.hasBeenModified()) {
            ResponsibilityTypeDescription responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, responsibilityTypeDescriptionValue.getPrimaryKey());
            
            responsibilityTypeDescription.setThruTime(session.START_TIME_LONG);
            responsibilityTypeDescription.store();
            
            ResponsibilityType responsibilityType = responsibilityTypeDescription.getResponsibilityType();
            Language language = responsibilityTypeDescription.getLanguage();
            String description = responsibilityTypeDescriptionValue.getDescription();
            
            responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().create(responsibilityType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(responsibilityType.getPrimaryKey(), EventTypes.MODIFY.name(), responsibilityTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteResponsibilityTypeDescription(ResponsibilityTypeDescription responsibilityTypeDescription, BasePK deletedBy) {
        responsibilityTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(responsibilityTypeDescription.getResponsibilityTypePK(), EventTypes.MODIFY.name(), responsibilityTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteResponsibilityTypeDescriptionsByResponsibilityType(ResponsibilityType responsibilityType, BasePK deletedBy) {
        List<ResponsibilityTypeDescription> responsibilityTypeDescriptions = getResponsibilityTypeDescriptionsByResponsibilityTypeForUpdate(responsibilityType);
        
        responsibilityTypeDescriptions.forEach((responsibilityTypeDescription) -> 
                deleteResponsibilityTypeDescription(responsibilityTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Skill Types
    // --------------------------------------------------------------------------------
    
    public SkillType createSkillType(String skillTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SkillType defaultSkillType = getDefaultSkillType();
        boolean defaultFound = defaultSkillType != null;
        
        if(defaultFound && isDefault) {
            SkillTypeDetailValue defaultSkillTypeDetailValue = getDefaultSkillTypeDetailValueForUpdate();
            
            defaultSkillTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSkillTypeFromValue(defaultSkillTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        SkillType skillType = SkillTypeFactory.getInstance().create();
        SkillTypeDetail skillTypeDetail = SkillTypeDetailFactory.getInstance().create(session,
                skillType, skillTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        skillType = SkillTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, skillType.getPrimaryKey());
        skillType.setActiveDetail(skillTypeDetail);
        skillType.setLastDetail(skillTypeDetail);
        skillType.store();
        
        sendEventUsingNames(skillType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return skillType;
    }
    
    private SkillType getSkillTypeByName(String skillTypeName, EntityPermission entityPermission) {
        SkillType skillType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM skilltypes, skilltypedetails " +
                        "WHERE skltyp_activedetailid = skltypdt_skilltypedetailid AND skltypdt_skilltypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM skilltypes, skilltypedetails " +
                        "WHERE skltyp_activedetailid = skltypdt_skilltypedetailid AND skltypdt_skilltypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SkillTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, skillTypeName);
            
            skillType = SkillTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return skillType;
    }
    
    public SkillType getSkillTypeByName(String skillTypeName) {
        return getSkillTypeByName(skillTypeName, EntityPermission.READ_ONLY);
    }
    
    public SkillType getSkillTypeByNameForUpdate(String skillTypeName) {
        return getSkillTypeByName(skillTypeName, EntityPermission.READ_WRITE);
    }
    
    public SkillTypeDetailValue getSkillTypeDetailValueForUpdate(SkillType skillType) {
        return skillType == null? null: skillType.getLastDetailForUpdate().getSkillTypeDetailValue().clone();
    }
    
    public SkillTypeDetailValue getSkillTypeDetailValueByNameForUpdate(String skillTypeName) {
        return getSkillTypeDetailValueForUpdate(getSkillTypeByNameForUpdate(skillTypeName));
    }
    
    private SkillType getDefaultSkillType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM skilltypes, skilltypedetails " +
                    "WHERE skltyp_activedetailid = skltypdt_skilltypedetailid AND skltypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM skilltypes, skilltypedetails " +
                    "WHERE skltyp_activedetailid = skltypdt_skilltypedetailid AND skltypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = SkillTypeFactory.getInstance().prepareStatement(query);
        
        return SkillTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public SkillType getDefaultSkillType() {
        return getDefaultSkillType(EntityPermission.READ_ONLY);
    }
    
    public SkillType getDefaultSkillTypeForUpdate() {
        return getDefaultSkillType(EntityPermission.READ_WRITE);
    }
    
    public SkillTypeDetailValue getDefaultSkillTypeDetailValueForUpdate() {
        return getDefaultSkillTypeForUpdate().getLastDetailForUpdate().getSkillTypeDetailValue().clone();
    }
    
    private List<SkillType> getSkillTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM skilltypes, skilltypedetails " +
                    "WHERE skltyp_activedetailid = skltypdt_skilltypedetailid " +
                    "ORDER BY skltypdt_sortorder, skltypdt_skilltypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM skilltypes, skilltypedetails " +
                    "WHERE skltyp_activedetailid = skltypdt_skilltypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = SkillTypeFactory.getInstance().prepareStatement(query);
        
        return SkillTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<SkillType> getSkillTypes() {
        return getSkillTypes(EntityPermission.READ_ONLY);
    }
    
    public List<SkillType> getSkillTypesForUpdate() {
        return getSkillTypes(EntityPermission.READ_WRITE);
    }
    
    public SkillTypeTransfer getSkillTypeTransfer(UserVisit userVisit, SkillType skillType) {
        return getEmployeeTransferCaches(userVisit).getSkillTypeTransferCache().getSkillTypeTransfer(skillType);
    }
    
    public List<SkillTypeTransfer> getSkillTypeTransfers(UserVisit userVisit) {
        List<SkillType> skillTypes = getSkillTypes();
        List<SkillTypeTransfer> skillTypeTransfers = new ArrayList<>(skillTypes.size());
        SkillTypeTransferCache skillTypeTransferCache = getEmployeeTransferCaches(userVisit).getSkillTypeTransferCache();
        
        skillTypes.forEach((skillType) ->
                skillTypeTransfers.add(skillTypeTransferCache.getSkillTypeTransfer(skillType))
        );
        
        return skillTypeTransfers;
    }
    
    public SkillTypeChoicesBean getSkillTypeChoices(String defaultSkillTypeChoice, Language language, boolean allowNullChoice) {
        List<SkillType> skillTypes = getSkillTypes();
        var size = skillTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSkillTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var skillType : skillTypes) {
            SkillTypeDetail skillTypeDetail = skillType.getLastDetail();
            
            var label = getBestSkillTypeDescription(skillType, language);
            var value = skillTypeDetail.getSkillTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSkillTypeChoice != null && defaultSkillTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && skillTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SkillTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateSkillTypeFromValue(SkillTypeDetailValue skillTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(skillTypeDetailValue.hasBeenModified()) {
            SkillType skillType = SkillTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     skillTypeDetailValue.getSkillTypePK());
            SkillTypeDetail skillTypeDetail = skillType.getActiveDetailForUpdate();
            
            skillTypeDetail.setThruTime(session.START_TIME_LONG);
            skillTypeDetail.store();
            
            SkillTypePK skillTypePK = skillTypeDetail.getSkillTypePK();
            String skillTypeName = skillTypeDetailValue.getSkillTypeName();
            Boolean isDefault = skillTypeDetailValue.getIsDefault();
            Integer sortOrder = skillTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                SkillType defaultSkillType = getDefaultSkillType();
                boolean defaultFound = defaultSkillType != null && !defaultSkillType.equals(skillType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SkillTypeDetailValue defaultSkillTypeDetailValue = getDefaultSkillTypeDetailValueForUpdate();
                    
                    defaultSkillTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSkillTypeFromValue(defaultSkillTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            skillTypeDetail = SkillTypeDetailFactory.getInstance().create(skillTypePK,
                    skillTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            skillType.setActiveDetail(skillTypeDetail);
            skillType.setLastDetail(skillTypeDetail);
            
            sendEventUsingNames(skillTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateSkillTypeFromValue(SkillTypeDetailValue skillTypeDetailValue, BasePK updatedBy) {
        updateSkillTypeFromValue(skillTypeDetailValue, true, updatedBy);
    }
    
    public void deleteSkillType(SkillType skillType, BasePK deletedBy) {
        deleteSkillTypeDescriptionsBySkillType(skillType, deletedBy);
        deletePartySkillsBySkillType(skillType, deletedBy);
        
        SkillTypeDetail skillTypeDetail = skillType.getLastDetailForUpdate();
        skillTypeDetail.setThruTime(session.START_TIME_LONG);
        skillType.setActiveDetail(null);
        skillType.store();
        
        // Check for default, and pick one if necessary
        SkillType defaultSkillType = getDefaultSkillType();
        if(defaultSkillType == null) {
            List<SkillType> skillTypes = getSkillTypesForUpdate();
            
            if(!skillTypes.isEmpty()) {
                Iterator iter = skillTypes.iterator();
                if(iter.hasNext()) {
                    defaultSkillType = (SkillType)iter.next();
                }
                SkillTypeDetailValue skillTypeDetailValue = Objects.requireNonNull(defaultSkillType).getLastDetailForUpdate().getSkillTypeDetailValue().clone();
                
                skillTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateSkillTypeFromValue(skillTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(skillType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Skill Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SkillTypeDescription createSkillTypeDescription(SkillType skillType,
            Language language, String description, BasePK createdBy) {
        SkillTypeDescription skillTypeDescription = SkillTypeDescriptionFactory.getInstance().create(session,
                skillType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(skillType.getPrimaryKey(), EventTypes.MODIFY.name(), skillTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return skillTypeDescription;
    }
    
    private SkillTypeDescription getSkillTypeDescription(SkillType skillType,
            Language language, EntityPermission entityPermission) {
        SkillTypeDescription skillTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM skilltypedescriptions " +
                        "WHERE skltypd_skltyp_skilltypeid = ? AND skltypd_lang_languageid = ? AND skltypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM skilltypedescriptions " +
                        "WHERE skltypd_skltyp_skilltypeid = ? AND skltypd_lang_languageid = ? AND skltypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SkillTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, skillType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            skillTypeDescription = SkillTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return skillTypeDescription;
    }
    
    public SkillTypeDescription getSkillTypeDescription(SkillType skillType, Language language) {
        return getSkillTypeDescription(skillType, language, EntityPermission.READ_ONLY);
    }
    
    public SkillTypeDescription getSkillTypeDescriptionForUpdate(SkillType skillType, Language language) {
        return getSkillTypeDescription(skillType, language, EntityPermission.READ_WRITE);
    }
    
    public SkillTypeDescriptionValue getSkillTypeDescriptionValue(SkillTypeDescription skillTypeDescription) {
        return skillTypeDescription == null? null: skillTypeDescription.getSkillTypeDescriptionValue().clone();
    }
    
    public SkillTypeDescriptionValue getSkillTypeDescriptionValueForUpdate(SkillType skillType, Language language) {
        return getSkillTypeDescriptionValue(getSkillTypeDescriptionForUpdate(skillType, language));
    }
    
    private List<SkillTypeDescription> getSkillTypeDescriptionsBySkillType(SkillType skillType, EntityPermission entityPermission) {
        List<SkillTypeDescription> skillTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM skilltypedescriptions, languages " +
                        "WHERE skltypd_skltyp_skilltypeid = ? AND skltypd_thrutime = ? AND skltypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM skilltypedescriptions " +
                        "WHERE skltypd_skltyp_skilltypeid = ? AND skltypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SkillTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, skillType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            skillTypeDescriptions = SkillTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return skillTypeDescriptions;
    }
    
    public List<SkillTypeDescription> getSkillTypeDescriptionsBySkillType(SkillType skillType) {
        return getSkillTypeDescriptionsBySkillType(skillType, EntityPermission.READ_ONLY);
    }
    
    public List<SkillTypeDescription> getSkillTypeDescriptionsBySkillTypeForUpdate(SkillType skillType) {
        return getSkillTypeDescriptionsBySkillType(skillType, EntityPermission.READ_WRITE);
    }
    
    public String getBestSkillTypeDescription(SkillType skillType, Language language) {
        String description;
        SkillTypeDescription skillTypeDescription = getSkillTypeDescription(skillType, language);
        
        if(skillTypeDescription == null && !language.getIsDefault()) {
            skillTypeDescription = getSkillTypeDescription(skillType, getPartyControl().getDefaultLanguage());
        }
        
        if(skillTypeDescription == null) {
            description = skillType.getLastDetail().getSkillTypeName();
        } else {
            description = skillTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public SkillTypeDescriptionTransfer getSkillTypeDescriptionTransfer(UserVisit userVisit, SkillTypeDescription skillTypeDescription) {
        return getEmployeeTransferCaches(userVisit).getSkillTypeDescriptionTransferCache().getSkillTypeDescriptionTransfer(skillTypeDescription);
    }
    
    public List<SkillTypeDescriptionTransfer> getSkillTypeDescriptionTransfers(UserVisit userVisit, SkillType skillType) {
        List<SkillTypeDescription> skillTypeDescriptions = getSkillTypeDescriptionsBySkillType(skillType);
        List<SkillTypeDescriptionTransfer> skillTypeDescriptionTransfers = new ArrayList<>(skillTypeDescriptions.size());
        SkillTypeDescriptionTransferCache skillTypeDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getSkillTypeDescriptionTransferCache();
        
        skillTypeDescriptions.forEach((skillTypeDescription) ->
                skillTypeDescriptionTransfers.add(skillTypeDescriptionTransferCache.getSkillTypeDescriptionTransfer(skillTypeDescription))
        );
        
        return skillTypeDescriptionTransfers;
    }
    
    public void updateSkillTypeDescriptionFromValue(SkillTypeDescriptionValue skillTypeDescriptionValue, BasePK updatedBy) {
        if(skillTypeDescriptionValue.hasBeenModified()) {
            SkillTypeDescription skillTypeDescription = SkillTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, skillTypeDescriptionValue.getPrimaryKey());
            
            skillTypeDescription.setThruTime(session.START_TIME_LONG);
            skillTypeDescription.store();
            
            SkillType skillType = skillTypeDescription.getSkillType();
            Language language = skillTypeDescription.getLanguage();
            String description = skillTypeDescriptionValue.getDescription();
            
            skillTypeDescription = SkillTypeDescriptionFactory.getInstance().create(skillType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(skillType.getPrimaryKey(), EventTypes.MODIFY.name(), skillTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSkillTypeDescription(SkillTypeDescription skillTypeDescription, BasePK deletedBy) {
        skillTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(skillTypeDescription.getSkillTypePK(), EventTypes.MODIFY.name(), skillTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteSkillTypeDescriptionsBySkillType(SkillType skillType, BasePK deletedBy) {
        List<SkillTypeDescription> skillTypeDescriptions = getSkillTypeDescriptionsBySkillTypeForUpdate(skillType);
        
        skillTypeDescriptions.forEach((skillTypeDescription) -> 
                deleteSkillTypeDescription(skillTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Leave Types
    // --------------------------------------------------------------------------------

    public LeaveType createLeaveType(String leaveTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        LeaveType defaultLeaveType = getDefaultLeaveType();
        boolean defaultFound = defaultLeaveType != null;

        if(defaultFound && isDefault) {
            LeaveTypeDetailValue defaultLeaveTypeDetailValue = getDefaultLeaveTypeDetailValueForUpdate();

            defaultLeaveTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateLeaveTypeFromValue(defaultLeaveTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        LeaveType leaveType = LeaveTypeFactory.getInstance().create();
        LeaveTypeDetail leaveTypeDetail = LeaveTypeDetailFactory.getInstance().create(leaveType,
                leaveTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        leaveType = LeaveTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                leaveType.getPrimaryKey());
        leaveType.setActiveDetail(leaveTypeDetail);
        leaveType.setLastDetail(leaveTypeDetail);
        leaveType.store();

        sendEventUsingNames(leaveType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return leaveType;
    }

    private static final Map<EntityPermission, String> getLeaveTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavetypes, leavetypedetails " +
                "WHERE lvtyp_activedetailid = lvtypdt_leavetypedetailid " +
                "AND lvtypdt_leavetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavetypes, leavetypedetails " +
                "WHERE lvtyp_activedetailid = lvtypdt_leavetypedetailid " +
                "AND lvtypdt_leavetypename = ? " +
                "FOR UPDATE");
        getLeaveTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LeaveType getLeaveTypeByName(String leaveTypeName, EntityPermission entityPermission) {
        return LeaveTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLeaveTypeByNameQueries, leaveTypeName);
    }

    public LeaveType getLeaveTypeByName(String leaveTypeName) {
        return getLeaveTypeByName(leaveTypeName, EntityPermission.READ_ONLY);
    }

    public LeaveType getLeaveTypeByNameForUpdate(String leaveTypeName) {
        return getLeaveTypeByName(leaveTypeName, EntityPermission.READ_WRITE);
    }

    public LeaveTypeDetailValue getLeaveTypeDetailValueForUpdate(LeaveType leaveType) {
        return leaveType == null? null: leaveType.getLastDetailForUpdate().getLeaveTypeDetailValue().clone();
    }

    public LeaveTypeDetailValue getLeaveTypeDetailValueByNameForUpdate(String leaveTypeName) {
        return getLeaveTypeDetailValueForUpdate(getLeaveTypeByNameForUpdate(leaveTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultLeaveTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavetypes, leavetypedetails " +
                "WHERE lvtyp_activedetailid = lvtypdt_leavetypedetailid " +
                "AND lvtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavetypes, leavetypedetails " +
                "WHERE lvtyp_activedetailid = lvtypdt_leavetypedetailid " +
                "AND lvtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLeaveTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LeaveType getDefaultLeaveType(EntityPermission entityPermission) {
        return LeaveTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLeaveTypeQueries);
    }

    public LeaveType getDefaultLeaveType() {
        return getDefaultLeaveType(EntityPermission.READ_ONLY);
    }

    public LeaveType getDefaultLeaveTypeForUpdate() {
        return getDefaultLeaveType(EntityPermission.READ_WRITE);
    }

    public LeaveTypeDetailValue getDefaultLeaveTypeDetailValueForUpdate() {
        return getDefaultLeaveTypeForUpdate().getLastDetailForUpdate().getLeaveTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getLeaveTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavetypes, leavetypedetails " +
                "WHERE lvtyp_activedetailid = lvtypdt_leavetypedetailid " +
                "ORDER BY lvtypdt_sortorder, lvtypdt_leavetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavetypes, leavetypedetails " +
                "WHERE lvtyp_activedetailid = lvtypdt_leavetypedetailid " +
                "FOR UPDATE");
        getLeaveTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LeaveType> getLeaveTypes(EntityPermission entityPermission) {
        return LeaveTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveTypesQueries);
    }

    public List<LeaveType> getLeaveTypes() {
        return getLeaveTypes(EntityPermission.READ_ONLY);
    }

    public List<LeaveType> getLeaveTypesForUpdate() {
        return getLeaveTypes(EntityPermission.READ_WRITE);
    }

    public LeaveTypeTransfer getLeaveTypeTransfer(UserVisit userVisit, LeaveType leaveType) {
        return getEmployeeTransferCaches(userVisit).getLeaveTypeTransferCache().getLeaveTypeTransfer(leaveType);
    }

    public List<LeaveTypeTransfer> getLeaveTypeTransfers(UserVisit userVisit, List<LeaveType> leaveTypes) {
        List<LeaveTypeTransfer> leaveTypeTransfers = new ArrayList<>(leaveTypes.size());
        LeaveTypeTransferCache leaveTypeTransferCache = getEmployeeTransferCaches(userVisit).getLeaveTypeTransferCache();

        leaveTypes.forEach((leaveType) ->
                leaveTypeTransfers.add(leaveTypeTransferCache.getLeaveTypeTransfer(leaveType))
        );

        return leaveTypeTransfers;
    }

    public List<LeaveTypeTransfer> getLeaveTypeTransfers(UserVisit userVisit) {
        return getLeaveTypeTransfers(userVisit, getLeaveTypes());
    }

    public LeaveTypeChoicesBean getLeaveTypeChoices(String defaultLeaveTypeChoice, Language language, boolean allowNullChoice) {
        List<LeaveType> leaveTypes = getLeaveTypes();
        var size = leaveTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLeaveTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var leaveType : leaveTypes) {
            LeaveTypeDetail leaveTypeDetail = leaveType.getLastDetail();

            var label = getBestLeaveTypeDescription(leaveType, language);
            var value = leaveTypeDetail.getLeaveTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultLeaveTypeChoice != null && defaultLeaveTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && leaveTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LeaveTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLeaveTypeFromValue(LeaveTypeDetailValue leaveTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(leaveTypeDetailValue.hasBeenModified()) {
            LeaveType leaveType = LeaveTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveTypeDetailValue.getLeaveTypePK());
            LeaveTypeDetail leaveTypeDetail = leaveType.getActiveDetailForUpdate();

            leaveTypeDetail.setThruTime(session.START_TIME_LONG);
            leaveTypeDetail.store();

            LeaveTypePK leaveTypePK = leaveTypeDetail.getLeaveTypePK(); // Not updated
            String leaveTypeName = leaveTypeDetailValue.getLeaveTypeName();
            Boolean isDefault = leaveTypeDetailValue.getIsDefault();
            Integer sortOrder = leaveTypeDetailValue.getSortOrder();

            if(checkDefault) {
                LeaveType defaultLeaveType = getDefaultLeaveType();
                boolean defaultFound = defaultLeaveType != null && !defaultLeaveType.equals(leaveType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LeaveTypeDetailValue defaultLeaveTypeDetailValue = getDefaultLeaveTypeDetailValueForUpdate();

                    defaultLeaveTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateLeaveTypeFromValue(defaultLeaveTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            leaveTypeDetail = LeaveTypeDetailFactory.getInstance().create(leaveTypePK, leaveTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            leaveType.setActiveDetail(leaveTypeDetail);
            leaveType.setLastDetail(leaveTypeDetail);

            sendEventUsingNames(leaveTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateLeaveTypeFromValue(LeaveTypeDetailValue leaveTypeDetailValue, BasePK updatedBy) {
        updateLeaveTypeFromValue(leaveTypeDetailValue, true, updatedBy);
    }

    public void deleteLeaveType(LeaveType leaveType, BasePK deletedBy) {
        deleteLeaveTypeDescriptionsByLeaveType(leaveType, deletedBy);

        LeaveTypeDetail leaveTypeDetail = leaveType.getLastDetailForUpdate();
        leaveTypeDetail.setThruTime(session.START_TIME_LONG);
        leaveType.setActiveDetail(null);
        leaveType.store();

        // Check for default, and pick one if necessary
        LeaveType defaultLeaveType = getDefaultLeaveType();
        if(defaultLeaveType == null) {
            List<LeaveType> leaveTypes = getLeaveTypesForUpdate();

            if(!leaveTypes.isEmpty()) {
                Iterator<LeaveType> iter = leaveTypes.iterator();
                if(iter.hasNext()) {
                    defaultLeaveType = iter.next();
                }
                LeaveTypeDetailValue leaveTypeDetailValue = Objects.requireNonNull(defaultLeaveType).getLastDetailForUpdate().getLeaveTypeDetailValue().clone();

                leaveTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateLeaveTypeFromValue(leaveTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(leaveType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Leave Type Descriptions
    // --------------------------------------------------------------------------------

    public LeaveTypeDescription createLeaveTypeDescription(LeaveType leaveType,
            Language language, String description, BasePK createdBy) {
        LeaveTypeDescription leaveTypeDescription = LeaveTypeDescriptionFactory.getInstance().create(leaveType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(leaveType.getPrimaryKey(), EventTypes.MODIFY.name(), leaveTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return leaveTypeDescription;
    }

    private static final Map<EntityPermission, String> getLeaveTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavetypedescriptions " +
                "WHERE lvtypd_lvtyp_leavetypeid = ? AND lvtypd_lang_languageid = ? AND lvtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavetypedescriptions " +
                "WHERE lvtypd_lvtyp_leavetypeid = ? AND lvtypd_lang_languageid = ? AND lvtypd_thrutime = ? " +
                "FOR UPDATE");
        getLeaveTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LeaveTypeDescription getLeaveTypeDescription(LeaveType leaveType,
            Language language, EntityPermission entityPermission) {
        return LeaveTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLeaveTypeDescriptionQueries,
                leaveType, language, Session.MAX_TIME);
    }

    public LeaveTypeDescription getLeaveTypeDescription(LeaveType leaveType, Language language) {
        return getLeaveTypeDescription(leaveType, language, EntityPermission.READ_ONLY);
    }

    public LeaveTypeDescription getLeaveTypeDescriptionForUpdate(LeaveType leaveType, Language language) {
        return getLeaveTypeDescription(leaveType, language, EntityPermission.READ_WRITE);
    }

    public LeaveTypeDescriptionValue getLeaveTypeDescriptionValue(LeaveTypeDescription leaveTypeDescription) {
        return leaveTypeDescription == null? null: leaveTypeDescription.getLeaveTypeDescriptionValue().clone();
    }

    public LeaveTypeDescriptionValue getLeaveTypeDescriptionValueForUpdate(LeaveType leaveType, Language language) {
        return getLeaveTypeDescriptionValue(getLeaveTypeDescriptionForUpdate(leaveType, language));
    }

    private static final Map<EntityPermission, String> getLeaveTypeDescriptionsByLeaveTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavetypedescriptions, languages " +
                "WHERE lvtypd_lvtyp_leavetypeid = ? AND lvtypd_thrutime = ? AND lvtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavetypedescriptions " +
                "WHERE lvtypd_lvtyp_leavetypeid = ? AND lvtypd_thrutime = ? " +
                "FOR UPDATE");
        getLeaveTypeDescriptionsByLeaveTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LeaveTypeDescription> getLeaveTypeDescriptionsByLeaveType(LeaveType leaveType,
            EntityPermission entityPermission) {
        return LeaveTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveTypeDescriptionsByLeaveTypeQueries,
                leaveType, Session.MAX_TIME);
    }

    public List<LeaveTypeDescription> getLeaveTypeDescriptionsByLeaveType(LeaveType leaveType) {
        return getLeaveTypeDescriptionsByLeaveType(leaveType, EntityPermission.READ_ONLY);
    }

    public List<LeaveTypeDescription> getLeaveTypeDescriptionsByLeaveTypeForUpdate(LeaveType leaveType) {
        return getLeaveTypeDescriptionsByLeaveType(leaveType, EntityPermission.READ_WRITE);
    }

    public String getBestLeaveTypeDescription(LeaveType leaveType, Language language) {
        String description;
        LeaveTypeDescription leaveTypeDescription = getLeaveTypeDescription(leaveType, language);

        if(leaveTypeDescription == null && !language.getIsDefault()) {
            leaveTypeDescription = getLeaveTypeDescription(leaveType, getPartyControl().getDefaultLanguage());
        }

        if(leaveTypeDescription == null) {
            description = leaveType.getLastDetail().getLeaveTypeName();
        } else {
            description = leaveTypeDescription.getDescription();
        }

        return description;
    }

    public LeaveTypeDescriptionTransfer getLeaveTypeDescriptionTransfer(UserVisit userVisit, LeaveTypeDescription leaveTypeDescription) {
        return getEmployeeTransferCaches(userVisit).getLeaveTypeDescriptionTransferCache().getLeaveTypeDescriptionTransfer(leaveTypeDescription);
    }

    public List<LeaveTypeDescriptionTransfer> getLeaveTypeDescriptionTransfersByLeaveType(UserVisit userVisit, LeaveType leaveType) {
        List<LeaveTypeDescription> leaveTypeDescriptions = getLeaveTypeDescriptionsByLeaveType(leaveType);
        List<LeaveTypeDescriptionTransfer> leaveTypeDescriptionTransfers = new ArrayList<>(leaveTypeDescriptions.size());
        LeaveTypeDescriptionTransferCache leaveTypeDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getLeaveTypeDescriptionTransferCache();

        leaveTypeDescriptions.forEach((leaveTypeDescription) ->
                leaveTypeDescriptionTransfers.add(leaveTypeDescriptionTransferCache.getLeaveTypeDescriptionTransfer(leaveTypeDescription))
        );

        return leaveTypeDescriptionTransfers;
    }

    public void updateLeaveTypeDescriptionFromValue(LeaveTypeDescriptionValue leaveTypeDescriptionValue, BasePK updatedBy) {
        if(leaveTypeDescriptionValue.hasBeenModified()) {
            LeaveTypeDescription leaveTypeDescription = LeaveTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    leaveTypeDescriptionValue.getPrimaryKey());

            leaveTypeDescription.setThruTime(session.START_TIME_LONG);
            leaveTypeDescription.store();

            LeaveType leaveType = leaveTypeDescription.getLeaveType();
            Language language = leaveTypeDescription.getLanguage();
            String description = leaveTypeDescriptionValue.getDescription();

            leaveTypeDescription = LeaveTypeDescriptionFactory.getInstance().create(leaveType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(leaveType.getPrimaryKey(), EventTypes.MODIFY.name(), leaveTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLeaveTypeDescription(LeaveTypeDescription leaveTypeDescription, BasePK deletedBy) {
        leaveTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(leaveTypeDescription.getLeaveTypePK(), EventTypes.MODIFY.name(), leaveTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLeaveTypeDescriptionsByLeaveType(LeaveType leaveType, BasePK deletedBy) {
        List<LeaveTypeDescription> leaveTypeDescriptions = getLeaveTypeDescriptionsByLeaveTypeForUpdate(leaveType);

        leaveTypeDescriptions.forEach((leaveTypeDescription) -> 
                deleteLeaveTypeDescription(leaveTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Leave Reasons
    // --------------------------------------------------------------------------------

    public LeaveReason createLeaveReason(String leaveReasonName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        LeaveReason defaultLeaveReason = getDefaultLeaveReason();
        boolean defaultFound = defaultLeaveReason != null;

        if(defaultFound && isDefault) {
            LeaveReasonDetailValue defaultLeaveReasonDetailValue = getDefaultLeaveReasonDetailValueForUpdate();

            defaultLeaveReasonDetailValue.setIsDefault(Boolean.FALSE);
            updateLeaveReasonFromValue(defaultLeaveReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        LeaveReason leaveReason = LeaveReasonFactory.getInstance().create();
        LeaveReasonDetail leaveReasonDetail = LeaveReasonDetailFactory.getInstance().create(leaveReason,
                leaveReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        leaveReason = LeaveReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                leaveReason.getPrimaryKey());
        leaveReason.setActiveDetail(leaveReasonDetail);
        leaveReason.setLastDetail(leaveReasonDetail);
        leaveReason.store();

        sendEventUsingNames(leaveReason.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return leaveReason;
    }

    private static final Map<EntityPermission, String> getLeaveReasonByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavereasons, leavereasondetails " +
                "WHERE lvrsn_activedetailid = lvrsndt_leavereasondetailid " +
                "AND lvrsndt_leavereasonname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavereasons, leavereasondetails " +
                "WHERE lvrsn_activedetailid = lvrsndt_leavereasondetailid " +
                "AND lvrsndt_leavereasonname = ? " +
                "FOR UPDATE");
        getLeaveReasonByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LeaveReason getLeaveReasonByName(String leaveReasonName, EntityPermission entityPermission) {
        return LeaveReasonFactory.getInstance().getEntityFromQuery(entityPermission, getLeaveReasonByNameQueries, leaveReasonName);
    }

    public LeaveReason getLeaveReasonByName(String leaveReasonName) {
        return getLeaveReasonByName(leaveReasonName, EntityPermission.READ_ONLY);
    }

    public LeaveReason getLeaveReasonByNameForUpdate(String leaveReasonName) {
        return getLeaveReasonByName(leaveReasonName, EntityPermission.READ_WRITE);
    }

    public LeaveReasonDetailValue getLeaveReasonDetailValueForUpdate(LeaveReason leaveReason) {
        return leaveReason == null? null: leaveReason.getLastDetailForUpdate().getLeaveReasonDetailValue().clone();
    }

    public LeaveReasonDetailValue getLeaveReasonDetailValueByNameForUpdate(String leaveReasonName) {
        return getLeaveReasonDetailValueForUpdate(getLeaveReasonByNameForUpdate(leaveReasonName));
    }

    private static final Map<EntityPermission, String> getDefaultLeaveReasonQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavereasons, leavereasondetails " +
                "WHERE lvrsn_activedetailid = lvrsndt_leavereasondetailid " +
                "AND lvrsndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavereasons, leavereasondetails " +
                "WHERE lvrsn_activedetailid = lvrsndt_leavereasondetailid " +
                "AND lvrsndt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLeaveReasonQueries = Collections.unmodifiableMap(queryMap);
    }

    private LeaveReason getDefaultLeaveReason(EntityPermission entityPermission) {
        return LeaveReasonFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLeaveReasonQueries);
    }

    public LeaveReason getDefaultLeaveReason() {
        return getDefaultLeaveReason(EntityPermission.READ_ONLY);
    }

    public LeaveReason getDefaultLeaveReasonForUpdate() {
        return getDefaultLeaveReason(EntityPermission.READ_WRITE);
    }

    public LeaveReasonDetailValue getDefaultLeaveReasonDetailValueForUpdate() {
        return getDefaultLeaveReasonForUpdate().getLastDetailForUpdate().getLeaveReasonDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getLeaveReasonsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavereasons, leavereasondetails " +
                "WHERE lvrsn_activedetailid = lvrsndt_leavereasondetailid " +
                "ORDER BY lvrsndt_sortorder, lvrsndt_leavereasonname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavereasons, leavereasondetails " +
                "WHERE lvrsn_activedetailid = lvrsndt_leavereasondetailid " +
                "FOR UPDATE");
        getLeaveReasonsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LeaveReason> getLeaveReasons(EntityPermission entityPermission) {
        return LeaveReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveReasonsQueries);
    }

    public List<LeaveReason> getLeaveReasons() {
        return getLeaveReasons(EntityPermission.READ_ONLY);
    }

    public List<LeaveReason> getLeaveReasonsForUpdate() {
        return getLeaveReasons(EntityPermission.READ_WRITE);
    }

    public LeaveReasonTransfer getLeaveReasonTransfer(UserVisit userVisit, LeaveReason leaveReason) {
        return getEmployeeTransferCaches(userVisit).getLeaveReasonTransferCache().getLeaveReasonTransfer(leaveReason);
    }

    public List<LeaveReasonTransfer> getLeaveReasonTransfers(UserVisit userVisit, List<LeaveReason> leaveReasons) {
        List<LeaveReasonTransfer> leaveReasonTransfers = new ArrayList<>(leaveReasons.size());
        LeaveReasonTransferCache leaveReasonTransferCache = getEmployeeTransferCaches(userVisit).getLeaveReasonTransferCache();

        leaveReasons.forEach((leaveReason) ->
                leaveReasonTransfers.add(leaveReasonTransferCache.getLeaveReasonTransfer(leaveReason))
        );

        return leaveReasonTransfers;
    }

    public List<LeaveReasonTransfer> getLeaveReasonTransfers(UserVisit userVisit) {
        return getLeaveReasonTransfers(userVisit, getLeaveReasons());
    }

    public LeaveReasonChoicesBean getLeaveReasonChoices(String defaultLeaveReasonChoice, Language language, boolean allowNullChoice) {
        List<LeaveReason> leaveReasons = getLeaveReasons();
        var size = leaveReasons.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLeaveReasonChoice == null) {
                defaultValue = "";
            }
        }

        for(var leaveReason : leaveReasons) {
            LeaveReasonDetail leaveReasonDetail = leaveReason.getLastDetail();

            var label = getBestLeaveReasonDescription(leaveReason, language);
            var value = leaveReasonDetail.getLeaveReasonName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultLeaveReasonChoice != null && defaultLeaveReasonChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && leaveReasonDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LeaveReasonChoicesBean(labels, values, defaultValue);
    }

    private void updateLeaveReasonFromValue(LeaveReasonDetailValue leaveReasonDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(leaveReasonDetailValue.hasBeenModified()) {
            LeaveReason leaveReason = LeaveReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveReasonDetailValue.getLeaveReasonPK());
            LeaveReasonDetail leaveReasonDetail = leaveReason.getActiveDetailForUpdate();

            leaveReasonDetail.setThruTime(session.START_TIME_LONG);
            leaveReasonDetail.store();

            LeaveReasonPK leaveReasonPK = leaveReasonDetail.getLeaveReasonPK(); // Not updated
            String leaveReasonName = leaveReasonDetailValue.getLeaveReasonName();
            Boolean isDefault = leaveReasonDetailValue.getIsDefault();
            Integer sortOrder = leaveReasonDetailValue.getSortOrder();

            if(checkDefault) {
                LeaveReason defaultLeaveReason = getDefaultLeaveReason();
                boolean defaultFound = defaultLeaveReason != null && !defaultLeaveReason.equals(leaveReason);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LeaveReasonDetailValue defaultLeaveReasonDetailValue = getDefaultLeaveReasonDetailValueForUpdate();

                    defaultLeaveReasonDetailValue.setIsDefault(Boolean.FALSE);
                    updateLeaveReasonFromValue(defaultLeaveReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            leaveReasonDetail = LeaveReasonDetailFactory.getInstance().create(leaveReasonPK, leaveReasonName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            leaveReason.setActiveDetail(leaveReasonDetail);
            leaveReason.setLastDetail(leaveReasonDetail);

            sendEventUsingNames(leaveReasonPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateLeaveReasonFromValue(LeaveReasonDetailValue leaveReasonDetailValue, BasePK updatedBy) {
        updateLeaveReasonFromValue(leaveReasonDetailValue, true, updatedBy);
    }

    public void deleteLeaveReason(LeaveReason leaveReason, BasePK deletedBy) {
        deleteLeaveReasonDescriptionsByLeaveReason(leaveReason, deletedBy);

        LeaveReasonDetail leaveReasonDetail = leaveReason.getLastDetailForUpdate();
        leaveReasonDetail.setThruTime(session.START_TIME_LONG);
        leaveReason.setActiveDetail(null);
        leaveReason.store();

        // Check for default, and pick one if necessary
        LeaveReason defaultLeaveReason = getDefaultLeaveReason();
        if(defaultLeaveReason == null) {
            List<LeaveReason> leaveReasons = getLeaveReasonsForUpdate();

            if(!leaveReasons.isEmpty()) {
                Iterator<LeaveReason> iter = leaveReasons.iterator();
                if(iter.hasNext()) {
                    defaultLeaveReason = iter.next();
                }
                LeaveReasonDetailValue leaveReasonDetailValue = Objects.requireNonNull(defaultLeaveReason).getLastDetailForUpdate().getLeaveReasonDetailValue().clone();

                leaveReasonDetailValue.setIsDefault(Boolean.TRUE);
                updateLeaveReasonFromValue(leaveReasonDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(leaveReason.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // --------------------------------------------------------------------------------

    public LeaveReasonDescription createLeaveReasonDescription(LeaveReason leaveReason,
            Language language, String description, BasePK createdBy) {
        LeaveReasonDescription leaveReasonDescription = LeaveReasonDescriptionFactory.getInstance().create(leaveReason,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(leaveReason.getPrimaryKey(), EventTypes.MODIFY.name(), leaveReasonDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return leaveReasonDescription;
    }

    private static final Map<EntityPermission, String> getLeaveReasonDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavereasondescriptions " +
                "WHERE lvrsnd_lvrsn_leavereasonid = ? AND lvrsnd_lang_languageid = ? AND lvrsnd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavereasondescriptions " +
                "WHERE lvrsnd_lvrsn_leavereasonid = ? AND lvrsnd_lang_languageid = ? AND lvrsnd_thrutime = ? " +
                "FOR UPDATE");
        getLeaveReasonDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LeaveReasonDescription getLeaveReasonDescription(LeaveReason leaveReason,
            Language language, EntityPermission entityPermission) {
        return LeaveReasonDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLeaveReasonDescriptionQueries,
                leaveReason, language, Session.MAX_TIME);
    }

    public LeaveReasonDescription getLeaveReasonDescription(LeaveReason leaveReason, Language language) {
        return getLeaveReasonDescription(leaveReason, language, EntityPermission.READ_ONLY);
    }

    public LeaveReasonDescription getLeaveReasonDescriptionForUpdate(LeaveReason leaveReason, Language language) {
        return getLeaveReasonDescription(leaveReason, language, EntityPermission.READ_WRITE);
    }

    public LeaveReasonDescriptionValue getLeaveReasonDescriptionValue(LeaveReasonDescription leaveReasonDescription) {
        return leaveReasonDescription == null? null: leaveReasonDescription.getLeaveReasonDescriptionValue().clone();
    }

    public LeaveReasonDescriptionValue getLeaveReasonDescriptionValueForUpdate(LeaveReason leaveReason, Language language) {
        return getLeaveReasonDescriptionValue(getLeaveReasonDescriptionForUpdate(leaveReason, language));
    }

    private static final Map<EntityPermission, String> getLeaveReasonDescriptionsByLeaveReasonQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM leavereasondescriptions, languages " +
                "WHERE lvrsnd_lvrsn_leavereasonid = ? AND lvrsnd_thrutime = ? AND lvrsnd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM leavereasondescriptions " +
                "WHERE lvrsnd_lvrsn_leavereasonid = ? AND lvrsnd_thrutime = ? " +
                "FOR UPDATE");
        getLeaveReasonDescriptionsByLeaveReasonQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LeaveReasonDescription> getLeaveReasonDescriptionsByLeaveReason(LeaveReason leaveReason,
            EntityPermission entityPermission) {
        return LeaveReasonDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveReasonDescriptionsByLeaveReasonQueries,
                leaveReason, Session.MAX_TIME);
    }

    public List<LeaveReasonDescription> getLeaveReasonDescriptionsByLeaveReason(LeaveReason leaveReason) {
        return getLeaveReasonDescriptionsByLeaveReason(leaveReason, EntityPermission.READ_ONLY);
    }

    public List<LeaveReasonDescription> getLeaveReasonDescriptionsByLeaveReasonForUpdate(LeaveReason leaveReason) {
        return getLeaveReasonDescriptionsByLeaveReason(leaveReason, EntityPermission.READ_WRITE);
    }

    public String getBestLeaveReasonDescription(LeaveReason leaveReason, Language language) {
        String description;
        LeaveReasonDescription leaveReasonDescription = getLeaveReasonDescription(leaveReason, language);

        if(leaveReasonDescription == null && !language.getIsDefault()) {
            leaveReasonDescription = getLeaveReasonDescription(leaveReason, getPartyControl().getDefaultLanguage());
        }

        if(leaveReasonDescription == null) {
            description = leaveReason.getLastDetail().getLeaveReasonName();
        } else {
            description = leaveReasonDescription.getDescription();
        }

        return description;
    }

    public LeaveReasonDescriptionTransfer getLeaveReasonDescriptionTransfer(UserVisit userVisit, LeaveReasonDescription leaveReasonDescription) {
        return getEmployeeTransferCaches(userVisit).getLeaveReasonDescriptionTransferCache().getLeaveReasonDescriptionTransfer(leaveReasonDescription);
    }

    public List<LeaveReasonDescriptionTransfer> getLeaveReasonDescriptionTransfersByLeaveReason(UserVisit userVisit, LeaveReason leaveReason) {
        List<LeaveReasonDescription> leaveReasonDescriptions = getLeaveReasonDescriptionsByLeaveReason(leaveReason);
        List<LeaveReasonDescriptionTransfer> leaveReasonDescriptionTransfers = new ArrayList<>(leaveReasonDescriptions.size());
        LeaveReasonDescriptionTransferCache leaveReasonDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getLeaveReasonDescriptionTransferCache();

        leaveReasonDescriptions.forEach((leaveReasonDescription) ->
                leaveReasonDescriptionTransfers.add(leaveReasonDescriptionTransferCache.getLeaveReasonDescriptionTransfer(leaveReasonDescription))
        );

        return leaveReasonDescriptionTransfers;
    }

    public void updateLeaveReasonDescriptionFromValue(LeaveReasonDescriptionValue leaveReasonDescriptionValue, BasePK updatedBy) {
        if(leaveReasonDescriptionValue.hasBeenModified()) {
            LeaveReasonDescription leaveReasonDescription = LeaveReasonDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    leaveReasonDescriptionValue.getPrimaryKey());

            leaveReasonDescription.setThruTime(session.START_TIME_LONG);
            leaveReasonDescription.store();

            LeaveReason leaveReason = leaveReasonDescription.getLeaveReason();
            Language language = leaveReasonDescription.getLanguage();
            String description = leaveReasonDescriptionValue.getDescription();

            leaveReasonDescription = LeaveReasonDescriptionFactory.getInstance().create(leaveReason, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(leaveReason.getPrimaryKey(), EventTypes.MODIFY.name(), leaveReasonDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLeaveReasonDescription(LeaveReasonDescription leaveReasonDescription, BasePK deletedBy) {
        leaveReasonDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(leaveReasonDescription.getLeaveReasonPK(), EventTypes.MODIFY.name(), leaveReasonDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLeaveReasonDescriptionsByLeaveReason(LeaveReason leaveReason, BasePK deletedBy) {
        List<LeaveReasonDescription> leaveReasonDescriptions = getLeaveReasonDescriptionsByLeaveReasonForUpdate(leaveReason);

        leaveReasonDescriptions.forEach((leaveReasonDescription) -> 
                deleteLeaveReasonDescription(leaveReasonDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Leaves
    // --------------------------------------------------------------------------------

    public Leave createLeave(Party party, Party companyParty, LeaveType leaveType, LeaveReason leaveReason, Long startTime, Long endTime, Long totalTime,
            BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.LEAVE.name());
        String leaveName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createLeave(leaveName, party, companyParty, leaveType, leaveReason, startTime, endTime, totalTime, createdBy);
    }

    public Leave createLeave(String leaveName, Party party, Party companyParty, LeaveType leaveType, LeaveReason leaveReason, Long startTime, Long endTime,
            Long totalTime, BasePK createdBy) {
        Leave leave = LeaveFactory.getInstance().create();
        LeaveDetail leaveDetail = LeaveDetailFactory.getInstance().create(leave, leaveName, party, companyParty, leaveType, leaveReason, startTime, endTime,
                totalTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        leave = LeaveFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, leave.getPrimaryKey());
        leave.setActiveDetail(leaveDetail);
        leave.setLastDetail(leaveDetail);
        leave.store();

        LeavePK leavePK = leave.getPrimaryKey();
        sendEventUsingNames(leavePK, EventTypes.CREATE.name(), null, null, createdBy);
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.TOUCH.name(), leavePK, EventTypes.CREATE.name(), createdBy);

        return leave;
    }

    public long countLeavesByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid "
                + "AND lvdt_par_partyid = ?",
                party);
    }

    public long countLeavesByCompanyParty(Party companyParty) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid "
                + "AND lvdt_companypartyid = ?",
                companyParty);
    }

    public long countLeavesByLeaveType(LeaveType leaveType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid "
                + "AND lvdt_lvtyp_leavetypeid = ?",
                leaveType);
    }

    public long countLeavesByLeaveReason(LeaveReason leaveReason) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid "
                + "AND lvdt_lvrsn_leavereasonid = ?",
                leaveReason);
    }

    private static final Map<EntityPermission, String> getLeaveByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_leavename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_leavename = ? "
                + "FOR UPDATE");
        getLeaveByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Leave getLeaveByName(String leaveName, EntityPermission entityPermission) {
        return LeaveFactory.getInstance().getEntityFromQuery(entityPermission, getLeaveByNameQueries,
                leaveName);
    }

    public Leave getLeaveByName(String leaveName) {
        return getLeaveByName(leaveName, EntityPermission.READ_ONLY);
    }

    public Leave getLeaveByNameForUpdate(String leaveName) {
        return getLeaveByName(leaveName, EntityPermission.READ_WRITE);
    }

    public LeaveDetailValue getLeaveDetailValueForUpdate(Leave leave) {
        return leave == null ? null : leave.getLastDetailForUpdate().getLeaveDetailValue().clone();
    }

    public LeaveDetailValue getLeaveDetailValueByNameForUpdate(String leaveName) {
        return getLeaveDetailValueForUpdate(getLeaveByNameForUpdate(leaveName));
    }

    private static final Map<EntityPermission, String> getLeaveByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_par_partyid = ? "
                + "ORDER BY lvdt_leavename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_par_partyid = ? "
                + "FOR UPDATE");
        getLeaveByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Leave> getLeavesByParty(Party party, EntityPermission entityPermission) {
        return LeaveFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveByPartyQueries,
                party);
    }

    public List<Leave> getLeavesByParty(Party party) {
        return getLeavesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByPartyForUpdate(Party party) {
        return getLeavesByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLeaveByCompanyPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails, parties, partydetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND lvdt_companypartyid = ? "
                + "ORDER BY lvdt_leavename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_companypartyid = ? "
                + "FOR UPDATE");
        getLeaveByCompanyPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Leave> getLeavesByCompanyParty(Party companyParty, EntityPermission entityPermission) {
        return LeaveFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveByCompanyPartyQueries,
                companyParty);
    }

    public List<Leave> getLeavesByCompanyParty(Party companyParty) {
        return getLeavesByCompanyParty(companyParty, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByCompanyPartyForUpdate(Party companyParty) {
        return getLeavesByCompanyParty(companyParty, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLeaveByLeaveTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails, parties, partydetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND lvdt_lvtyp_leavetypeid = ? "
                + "ORDER BY lvdt_leavename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_lvtyp_leavetypeid = ? "
                + "FOR UPDATE");
        getLeaveByLeaveTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Leave> getLeavesByLeaveType(LeaveType leaveType, EntityPermission entityPermission) {
        return LeaveFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveByLeaveTypeQueries,
                leaveType);
    }

    public List<Leave> getLeavesByLeaveType(LeaveType leaveType) {
        return getLeavesByLeaveType(leaveType, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByLeaveTypeForUpdate(LeaveType leaveType) {
        return getLeavesByLeaveType(leaveType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLeaveByLeaveReasonQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails, parties, partydetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND lvdt_lvrsn_leavereasonid = ? "
                + "ORDER BY lvdt_leavename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM leaves, leavedetails "
                + "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_lvrsn_leavereasonid = ? "
                + "FOR UPDATE");
        getLeaveByLeaveReasonQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Leave> getLeavesByLeaveReason(LeaveReason leaveReason, EntityPermission entityPermission) {
        return LeaveFactory.getInstance().getEntitiesFromQuery(entityPermission, getLeaveByLeaveReasonQueries,
                leaveReason);
    }

    public List<Leave> getLeavesByLeaveReason(LeaveReason leaveReason) {
        return getLeavesByLeaveReason(leaveReason, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByLeaveReasonForUpdate(LeaveReason leaveReason) {
        return getLeavesByLeaveReason(leaveReason, EntityPermission.READ_WRITE);
    }

    public LeaveStatusChoicesBean getLeaveStatusChoices(String defaultLeaveStatusChoice, Language language,
            boolean allowNullChoice, Leave leave, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        LeaveStatusChoicesBean leaveStatusChoicesBean = new LeaveStatusChoicesBean();

        if(leave == null) {
            workflowControl.getWorkflowEntranceChoices(leaveStatusChoicesBean, defaultLeaveStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(LeaveStatusConstants.Workflow_LEAVE_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(leave.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(LeaveStatusConstants.Workflow_LEAVE_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(leaveStatusChoicesBean, defaultLeaveStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return leaveStatusChoicesBean;
    }

    public void setLeaveStatus(ExecutionErrorAccumulator eea, Leave leave, String leaveStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(leave);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(LeaveStatusConstants.Workflow_LEAVE_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = leaveStatusChoice == null ? null
                : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), leaveStatusChoice);

        if(workflowDestination != null || leaveStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownLeaveStatusChoice.name(), leaveStatusChoice);
        }
    }

    public LeaveTransfer getLeaveTransfer(UserVisit userVisit, Leave leave) {
        return getEmployeeTransferCaches(userVisit).getLeaveTransferCache().getLeaveTransfer(leave);
    }

    public List<LeaveTransfer> getLeaveTransfers(UserVisit userVisit, List<Leave> leaves) {
        List<LeaveTransfer> leaveTransfers = new ArrayList<>(leaves.size());
        LeaveTransferCache leaveTransferCache = getEmployeeTransferCaches(userVisit).getLeaveTransferCache();

        leaves.forEach((leave) ->
                leaveTransfers.add(leaveTransferCache.getLeaveTransfer(leave))
        );

        return leaveTransfers;
    }

    public List<LeaveTransfer> getLeaveTransfersByParty(UserVisit userVisit, Party party) {
        return getLeaveTransfers(userVisit, getLeavesByParty(party));
    }

    public void updateLeaveFromValue(LeaveDetailValue leaveDetailValue, BasePK updatedBy) {
        if(leaveDetailValue.hasBeenModified()) {
            Leave leave = LeaveFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveDetailValue.getLeavePK());
            LeaveDetail leaveDetail = leave.getActiveDetailForUpdate();

            leaveDetail.setThruTime(session.START_TIME_LONG);
            leaveDetail.store();

            LeavePK leavePK = leaveDetail.getLeavePK(); // Not updated
            String leaveName = leaveDetail.getLeaveName(); // Not updated
            PartyPK partyPK = leaveDetail.getPartyPK(); // Not updated
            PartyPK companyPartyPK = leaveDetailValue.getCompanyPartyPK();
            Long startTime = leaveDetailValue.getStartTime();
            Long endTime = leaveDetailValue.getEndTime();
            Long totalTime = leaveDetailValue.getTotalTime();
            LeaveTypePK leaveTypePK = leaveDetailValue.getLeaveTypePK();
            LeaveReasonPK leaveReasonPK = leaveDetailValue.getLeaveReasonPK();

            leaveDetail = LeaveDetailFactory.getInstance().create(leavePK, leaveName, partyPK, companyPartyPK, leaveTypePK, leaveReasonPK, startTime,
                    endTime, totalTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            leave.setActiveDetail(leaveDetail);
            leave.setLastDetail(leaveDetail);

            sendEventUsingNames(leavePK, EventTypes.MODIFY.name(), null, null, updatedBy);
            sendEventUsingNames(partyPK, EventTypes.TOUCH.name(), leavePK, EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLeave(Leave leave, BasePK deletedBy) {
        LeaveDetail leaveDetail = leave.getLastDetailForUpdate();
        leaveDetail.setThruTime(session.START_TIME_LONG);
        leaveDetail.store();
        leave.setActiveDetail(null);

        LeavePK leavePK = leave.getPrimaryKey();
        sendEventUsingNames(leavePK, EventTypes.DELETE.name(), null, null, deletedBy);
        sendEventUsingNames(leaveDetail.getParty().getPrimaryKey(), EventTypes.TOUCH.name(), leavePK, EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteLeaves(List<Leave> leaves, BasePK deletedBy) {
        leaves.forEach((leave) -> 
                deleteLeave(leave, deletedBy)
        );
    }

    public void deleteLeavesByParty(Party party, BasePK deletedBy) {
        deleteLeaves(getLeavesByPartyForUpdate(party), deletedBy);
    }

    public void deleteLeavesByLeaveType(LeaveType leaveType, BasePK deletedBy) {
        deleteLeaves(getLeavesByLeaveTypeForUpdate(leaveType), deletedBy);
    }

    public void deleteLeavesByLeaveReason(LeaveReason leaveReason, BasePK deletedBy) {
        deleteLeaves(getLeavesByLeaveReasonForUpdate(leaveReason), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Termination Reasons
    // --------------------------------------------------------------------------------
    
    public TerminationReason createTerminationReason(String terminationReasonName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        TerminationReason defaultTerminationReason = getDefaultTerminationReason();
        boolean defaultFound = defaultTerminationReason != null;
        
        if(defaultFound && isDefault) {
            TerminationReasonDetailValue defaultTerminationReasonDetailValue = getDefaultTerminationReasonDetailValueForUpdate();
            
            defaultTerminationReasonDetailValue.setIsDefault(Boolean.FALSE);
            updateTerminationReasonFromValue(defaultTerminationReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        TerminationReason terminationReason = TerminationReasonFactory.getInstance().create();
        TerminationReasonDetail terminationReasonDetail = TerminationReasonDetailFactory.getInstance().create(session,
                terminationReason, terminationReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        terminationReason = TerminationReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationReason.getPrimaryKey());
        terminationReason.setActiveDetail(terminationReasonDetail);
        terminationReason.setLastDetail(terminationReasonDetail);
        terminationReason.store();
        
        sendEventUsingNames(terminationReason.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return terminationReason;
    }
    
    private TerminationReason getTerminationReasonByName(String terminationReasonName, EntityPermission entityPermission) {
        TerminationReason terminationReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationreasons, terminationreasondetails " +
                        "WHERE trmnrsn_activedetailid = trmnrsndt_terminationreasondetailid AND trmnrsndt_terminationreasonname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationreasons, terminationreasondetails " +
                        "WHERE trmnrsn_activedetailid = trmnrsndt_terminationreasondetailid AND trmnrsndt_terminationreasonname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TerminationReasonFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, terminationReasonName);
            
            terminationReason = TerminationReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return terminationReason;
    }
    
    public TerminationReason getTerminationReasonByName(String terminationReasonName) {
        return getTerminationReasonByName(terminationReasonName, EntityPermission.READ_ONLY);
    }
    
    public TerminationReason getTerminationReasonByNameForUpdate(String terminationReasonName) {
        return getTerminationReasonByName(terminationReasonName, EntityPermission.READ_WRITE);
    }
    
    public TerminationReasonDetailValue getTerminationReasonDetailValueForUpdate(TerminationReason terminationReason) {
        return terminationReason == null? null: terminationReason.getLastDetailForUpdate().getTerminationReasonDetailValue().clone();
    }
    
    public TerminationReasonDetailValue getTerminationReasonDetailValueByNameForUpdate(String terminationReasonName) {
        return getTerminationReasonDetailValueForUpdate(getTerminationReasonByNameForUpdate(terminationReasonName));
    }
    
    private TerminationReason getDefaultTerminationReason(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationreasons, terminationreasondetails " +
                    "WHERE trmnrsn_activedetailid = trmnrsndt_terminationreasondetailid AND trmnrsndt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationreasons, terminationreasondetails " +
                    "WHERE trmnrsn_activedetailid = trmnrsndt_terminationreasondetailid AND trmnrsndt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TerminationReasonFactory.getInstance().prepareStatement(query);
        
        return TerminationReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public TerminationReason getDefaultTerminationReason() {
        return getDefaultTerminationReason(EntityPermission.READ_ONLY);
    }
    
    public TerminationReason getDefaultTerminationReasonForUpdate() {
        return getDefaultTerminationReason(EntityPermission.READ_WRITE);
    }
    
    public TerminationReasonDetailValue getDefaultTerminationReasonDetailValueForUpdate() {
        return getDefaultTerminationReasonForUpdate().getLastDetailForUpdate().getTerminationReasonDetailValue().clone();
    }
    
    private List<TerminationReason> getTerminationReasons(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationreasons, terminationreasondetails " +
                    "WHERE trmnrsn_activedetailid = trmnrsndt_terminationreasondetailid " +
                    "ORDER BY trmnrsndt_sortorder, trmnrsndt_terminationreasonname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationreasons, terminationreasondetails " +
                    "WHERE trmnrsn_activedetailid = trmnrsndt_terminationreasondetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TerminationReasonFactory.getInstance().prepareStatement(query);
        
        return TerminationReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TerminationReason> getTerminationReasons() {
        return getTerminationReasons(EntityPermission.READ_ONLY);
    }
    
    public List<TerminationReason> getTerminationReasonsForUpdate() {
        return getTerminationReasons(EntityPermission.READ_WRITE);
    }
    
    public TerminationReasonTransfer getTerminationReasonTransfer(UserVisit userVisit, TerminationReason terminationReason) {
        return getEmployeeTransferCaches(userVisit).getTerminationReasonTransferCache().getTerminationReasonTransfer(terminationReason);
    }
    
    public List<TerminationReasonTransfer> getTerminationReasonTransfers(UserVisit userVisit) {
        List<TerminationReason> terminationReasons = getTerminationReasons();
        List<TerminationReasonTransfer> terminationReasonTransfers = new ArrayList<>(terminationReasons.size());
        TerminationReasonTransferCache terminationReasonTransferCache = getEmployeeTransferCaches(userVisit).getTerminationReasonTransferCache();
        
        terminationReasons.forEach((terminationReason) ->
                terminationReasonTransfers.add(terminationReasonTransferCache.getTerminationReasonTransfer(terminationReason))
        );
        
        return terminationReasonTransfers;
    }
    
    public TerminationReasonChoicesBean getTerminationReasonChoices(String defaultTerminationReasonChoice, Language language,
            boolean allowNullChoice) {
        List<TerminationReason> terminationReasons = getTerminationReasons();
        var size = terminationReasons.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTerminationReasonChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var terminationReason : terminationReasons) {
            TerminationReasonDetail terminationReasonDetail = terminationReason.getLastDetail();
            
            var label = getBestTerminationReasonDescription(terminationReason, language);
            var value = terminationReasonDetail.getTerminationReasonName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTerminationReasonChoice != null && defaultTerminationReasonChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && terminationReasonDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TerminationReasonChoicesBean(labels, values, defaultValue);
    }
    
    private void updateTerminationReasonFromValue(TerminationReasonDetailValue terminationReasonDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(terminationReasonDetailValue.hasBeenModified()) {
            TerminationReason terminationReason = TerminationReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     terminationReasonDetailValue.getTerminationReasonPK());
            TerminationReasonDetail terminationReasonDetail = terminationReason.getActiveDetailForUpdate();
            
            terminationReasonDetail.setThruTime(session.START_TIME_LONG);
            terminationReasonDetail.store();
            
            TerminationReasonPK terminationReasonPK = terminationReasonDetail.getTerminationReasonPK();
            String terminationReasonName = terminationReasonDetailValue.getTerminationReasonName();
            Boolean isDefault = terminationReasonDetailValue.getIsDefault();
            Integer sortOrder = terminationReasonDetailValue.getSortOrder();
            
            if(checkDefault) {
                TerminationReason defaultTerminationReason = getDefaultTerminationReason();
                boolean defaultFound = defaultTerminationReason != null && !defaultTerminationReason.equals(terminationReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TerminationReasonDetailValue defaultTerminationReasonDetailValue = getDefaultTerminationReasonDetailValueForUpdate();
                    
                    defaultTerminationReasonDetailValue.setIsDefault(Boolean.FALSE);
                    updateTerminationReasonFromValue(defaultTerminationReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            terminationReasonDetail = TerminationReasonDetailFactory.getInstance().create(terminationReasonPK,
                    terminationReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            terminationReason.setActiveDetail(terminationReasonDetail);
            terminationReason.setLastDetail(terminationReasonDetail);
            
            sendEventUsingNames(terminationReasonPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateTerminationReasonFromValue(TerminationReasonDetailValue terminationReasonDetailValue, BasePK updatedBy) {
        updateTerminationReasonFromValue(terminationReasonDetailValue, true, updatedBy);
    }
    
    public void deleteTerminationReason(TerminationReason terminationReason, BasePK deletedBy) {
        deleteTerminationReasonDescriptionsByTerminationReason(terminationReason, deletedBy);
        deleteEmploymentsByTerminationReason(terminationReason, deletedBy);
        
        TerminationReasonDetail terminationReasonDetail = terminationReason.getLastDetailForUpdate();
        terminationReasonDetail.setThruTime(session.START_TIME_LONG);
        terminationReason.setActiveDetail(null);
        terminationReason.store();
        
        // Check for default, and pick one if necessary
        TerminationReason defaultTerminationReason = getDefaultTerminationReason();
        if(defaultTerminationReason == null) {
            List<TerminationReason> terminationReasons = getTerminationReasonsForUpdate();
            
            if(!terminationReasons.isEmpty()) {
                Iterator iter = terminationReasons.iterator();
                if(iter.hasNext()) {
                    defaultTerminationReason = (TerminationReason)iter.next();
                }
                TerminationReasonDetailValue terminationReasonDetailValue = Objects.requireNonNull(defaultTerminationReason).getLastDetailForUpdate().getTerminationReasonDetailValue().clone();
                
                terminationReasonDetailValue.setIsDefault(Boolean.TRUE);
                updateTerminationReasonFromValue(terminationReasonDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(terminationReason.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // --------------------------------------------------------------------------------
    
    public TerminationReasonDescription createTerminationReasonDescription(TerminationReason terminationReason,
            Language language, String description, BasePK createdBy) {
        TerminationReasonDescription terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().create(session,
                terminationReason, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(terminationReason.getPrimaryKey(), EventTypes.MODIFY.name(), terminationReasonDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return terminationReasonDescription;
    }
    
    private TerminationReasonDescription getTerminationReasonDescription(TerminationReason terminationReason,
            Language language, EntityPermission entityPermission) {
        TerminationReasonDescription terminationReasonDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationreasondescriptions " +
                        "WHERE trmnrsnd_trmnrsn_terminationreasonid = ? AND trmnrsnd_lang_languageid = ? AND trmnrsnd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationreasondescriptions " +
                        "WHERE trmnrsnd_trmnrsn_terminationreasonid = ? AND trmnrsnd_lang_languageid = ? AND trmnrsnd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TerminationReasonDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, terminationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return terminationReasonDescription;
    }
    
    public TerminationReasonDescription getTerminationReasonDescription(TerminationReason terminationReason, Language language) {
        return getTerminationReasonDescription(terminationReason, language, EntityPermission.READ_ONLY);
    }
    
    public TerminationReasonDescription getTerminationReasonDescriptionForUpdate(TerminationReason terminationReason, Language language) {
        return getTerminationReasonDescription(terminationReason, language, EntityPermission.READ_WRITE);
    }
    
    public TerminationReasonDescriptionValue getTerminationReasonDescriptionValue(TerminationReasonDescription terminationReasonDescription) {
        return terminationReasonDescription == null? null: terminationReasonDescription.getTerminationReasonDescriptionValue().clone();
    }
    
    public TerminationReasonDescriptionValue getTerminationReasonDescriptionValueForUpdate(TerminationReason terminationReason, Language language) {
        return getTerminationReasonDescriptionValue(getTerminationReasonDescriptionForUpdate(terminationReason, language));
    }
    
    private List<TerminationReasonDescription> getTerminationReasonDescriptionsByTerminationReason(TerminationReason terminationReason, EntityPermission entityPermission) {
        List<TerminationReasonDescription> terminationReasonDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationreasondescriptions, languages " +
                        "WHERE trmnrsnd_trmnrsn_terminationreasonid = ? AND trmnrsnd_thrutime = ? AND trmnrsnd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationreasondescriptions " +
                        "WHERE trmnrsnd_trmnrsn_terminationreasonid = ? AND trmnrsnd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TerminationReasonDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, terminationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            terminationReasonDescriptions = TerminationReasonDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return terminationReasonDescriptions;
    }
    
    public List<TerminationReasonDescription> getTerminationReasonDescriptionsByTerminationReason(TerminationReason terminationReason) {
        return getTerminationReasonDescriptionsByTerminationReason(terminationReason, EntityPermission.READ_ONLY);
    }
    
    public List<TerminationReasonDescription> getTerminationReasonDescriptionsByTerminationReasonForUpdate(TerminationReason terminationReason) {
        return getTerminationReasonDescriptionsByTerminationReason(terminationReason, EntityPermission.READ_WRITE);
    }
    
    public String getBestTerminationReasonDescription(TerminationReason terminationReason, Language language) {
        String description;
        TerminationReasonDescription terminationReasonDescription = getTerminationReasonDescription(terminationReason, language);
        
        if(terminationReasonDescription == null && !language.getIsDefault()) {
            terminationReasonDescription = getTerminationReasonDescription(terminationReason, getPartyControl().getDefaultLanguage());
        }
        
        if(terminationReasonDescription == null) {
            description = terminationReason.getLastDetail().getTerminationReasonName();
        } else {
            description = terminationReasonDescription.getDescription();
        }
        
        return description;
    }
    
    public TerminationReasonDescriptionTransfer getTerminationReasonDescriptionTransfer(UserVisit userVisit, TerminationReasonDescription terminationReasonDescription) {
        return getEmployeeTransferCaches(userVisit).getTerminationReasonDescriptionTransferCache().getTerminationReasonDescriptionTransfer(terminationReasonDescription);
    }
    
    public List<TerminationReasonDescriptionTransfer> getTerminationReasonDescriptionTransfers(UserVisit userVisit, TerminationReason terminationReason) {
        List<TerminationReasonDescription> terminationReasonDescriptions = getTerminationReasonDescriptionsByTerminationReason(terminationReason);
        List<TerminationReasonDescriptionTransfer> terminationReasonDescriptionTransfers = new ArrayList<>(terminationReasonDescriptions.size());
        TerminationReasonDescriptionTransferCache terminationReasonDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getTerminationReasonDescriptionTransferCache();
        
        terminationReasonDescriptions.forEach((terminationReasonDescription) ->
                terminationReasonDescriptionTransfers.add(terminationReasonDescriptionTransferCache.getTerminationReasonDescriptionTransfer(terminationReasonDescription))
        );
        
        return terminationReasonDescriptionTransfers;
    }
    
    public void updateTerminationReasonDescriptionFromValue(TerminationReasonDescriptionValue terminationReasonDescriptionValue, BasePK updatedBy) {
        if(terminationReasonDescriptionValue.hasBeenModified()) {
            TerminationReasonDescription terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationReasonDescriptionValue.getPrimaryKey());
            
            terminationReasonDescription.setThruTime(session.START_TIME_LONG);
            terminationReasonDescription.store();
            
            TerminationReason terminationReason = terminationReasonDescription.getTerminationReason();
            Language language = terminationReasonDescription.getLanguage();
            String description = terminationReasonDescriptionValue.getDescription();
            
            terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().create(terminationReason, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(terminationReason.getPrimaryKey(), EventTypes.MODIFY.name(), terminationReasonDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTerminationReasonDescription(TerminationReasonDescription terminationReasonDescription, BasePK deletedBy) {
        terminationReasonDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(terminationReasonDescription.getTerminationReasonPK(), EventTypes.MODIFY.name(), terminationReasonDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteTerminationReasonDescriptionsByTerminationReason(TerminationReason terminationReason, BasePK deletedBy) {
        List<TerminationReasonDescription> terminationReasonDescriptions = getTerminationReasonDescriptionsByTerminationReasonForUpdate(terminationReason);
        
        terminationReasonDescriptions.forEach((terminationReasonDescription) -> 
                deleteTerminationReasonDescription(terminationReasonDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Termination Types
    // --------------------------------------------------------------------------------
    
    public TerminationType createTerminationType(String terminationTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        TerminationType defaultTerminationType = getDefaultTerminationType();
        boolean defaultFound = defaultTerminationType != null;
        
        if(defaultFound && isDefault) {
            TerminationTypeDetailValue defaultTerminationTypeDetailValue = getDefaultTerminationTypeDetailValueForUpdate();
            
            defaultTerminationTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateTerminationTypeFromValue(defaultTerminationTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        TerminationType terminationType = TerminationTypeFactory.getInstance().create();
        TerminationTypeDetail terminationTypeDetail = TerminationTypeDetailFactory.getInstance().create(session,
                terminationType, terminationTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        terminationType = TerminationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationType.getPrimaryKey());
        terminationType.setActiveDetail(terminationTypeDetail);
        terminationType.setLastDetail(terminationTypeDetail);
        terminationType.store();
        
        sendEventUsingNames(terminationType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return terminationType;
    }
    
    private TerminationType getTerminationTypeByName(String terminationTypeName, EntityPermission entityPermission) {
        TerminationType terminationType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationtypes, terminationtypedetails " +
                        "WHERE trmntyp_activedetailid = trmntypdt_terminationtypedetailid AND trmntypdt_terminationtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationtypes, terminationtypedetails " +
                        "WHERE trmntyp_activedetailid = trmntypdt_terminationtypedetailid AND trmntypdt_terminationtypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TerminationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, terminationTypeName);
            
            terminationType = TerminationTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return terminationType;
    }
    
    public TerminationType getTerminationTypeByName(String terminationTypeName) {
        return getTerminationTypeByName(terminationTypeName, EntityPermission.READ_ONLY);
    }
    
    public TerminationType getTerminationTypeByNameForUpdate(String terminationTypeName) {
        return getTerminationTypeByName(terminationTypeName, EntityPermission.READ_WRITE);
    }
    
    public TerminationTypeDetailValue getTerminationTypeDetailValueForUpdate(TerminationType terminationType) {
        return terminationType == null? null: terminationType.getLastDetailForUpdate().getTerminationTypeDetailValue().clone();
    }
    
    public TerminationTypeDetailValue getTerminationTypeDetailValueByNameForUpdate(String terminationTypeName) {
        return getTerminationTypeDetailValueForUpdate(getTerminationTypeByNameForUpdate(terminationTypeName));
    }
    
    private TerminationType getDefaultTerminationType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationtypes, terminationtypedetails " +
                    "WHERE trmntyp_activedetailid = trmntypdt_terminationtypedetailid AND trmntypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationtypes, terminationtypedetails " +
                    "WHERE trmntyp_activedetailid = trmntypdt_terminationtypedetailid AND trmntypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TerminationTypeFactory.getInstance().prepareStatement(query);
        
        return TerminationTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public TerminationType getDefaultTerminationType() {
        return getDefaultTerminationType(EntityPermission.READ_ONLY);
    }
    
    public TerminationType getDefaultTerminationTypeForUpdate() {
        return getDefaultTerminationType(EntityPermission.READ_WRITE);
    }
    
    public TerminationTypeDetailValue getDefaultTerminationTypeDetailValueForUpdate() {
        return getDefaultTerminationTypeForUpdate().getLastDetailForUpdate().getTerminationTypeDetailValue().clone();
    }
    
    private List<TerminationType> getTerminationTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationtypes, terminationtypedetails " +
                    "WHERE trmntyp_activedetailid = trmntypdt_terminationtypedetailid " +
                    "ORDER BY trmntypdt_sortorder, trmntypdt_terminationtypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terminationtypes, terminationtypedetails " +
                    "WHERE trmntyp_activedetailid = trmntypdt_terminationtypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TerminationTypeFactory.getInstance().prepareStatement(query);
        
        return TerminationTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TerminationType> getTerminationTypes() {
        return getTerminationTypes(EntityPermission.READ_ONLY);
    }
    
    public List<TerminationType> getTerminationTypesForUpdate() {
        return getTerminationTypes(EntityPermission.READ_WRITE);
    }
    
    public TerminationTypeTransfer getTerminationTypeTransfer(UserVisit userVisit, TerminationType terminationType) {
        return getEmployeeTransferCaches(userVisit).getTerminationTypeTransferCache().getTerminationTypeTransfer(terminationType);
    }
    
    public List<TerminationTypeTransfer> getTerminationTypeTransfers(UserVisit userVisit) {
        List<TerminationType> terminationTypes = getTerminationTypes();
        List<TerminationTypeTransfer> terminationTypeTransfers = new ArrayList<>(terminationTypes.size());
        TerminationTypeTransferCache terminationTypeTransferCache = getEmployeeTransferCaches(userVisit).getTerminationTypeTransferCache();
        
        terminationTypes.forEach((terminationType) ->
                terminationTypeTransfers.add(terminationTypeTransferCache.getTerminationTypeTransfer(terminationType))
        );
        
        return terminationTypeTransfers;
    }
    
    public TerminationTypeChoicesBean getTerminationTypeChoices(String defaultTerminationTypeChoice, Language language,
            boolean allowNullChoice) {
        List<TerminationType> terminationTypes = getTerminationTypes();
        var size = terminationTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTerminationTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var terminationType : terminationTypes) {
            TerminationTypeDetail terminationTypeDetail = terminationType.getLastDetail();
            
            var label = getBestTerminationTypeDescription(terminationType, language);
            var value = terminationTypeDetail.getTerminationTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTerminationTypeChoice != null && defaultTerminationTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && terminationTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TerminationTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateTerminationTypeFromValue(TerminationTypeDetailValue terminationTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(terminationTypeDetailValue.hasBeenModified()) {
            TerminationType terminationType = TerminationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     terminationTypeDetailValue.getTerminationTypePK());
            TerminationTypeDetail terminationTypeDetail = terminationType.getActiveDetailForUpdate();
            
            terminationTypeDetail.setThruTime(session.START_TIME_LONG);
            terminationTypeDetail.store();
            
            TerminationTypePK terminationTypePK = terminationTypeDetail.getTerminationTypePK();
            String terminationTypeName = terminationTypeDetailValue.getTerminationTypeName();
            Boolean isDefault = terminationTypeDetailValue.getIsDefault();
            Integer sortOrder = terminationTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                TerminationType defaultTerminationType = getDefaultTerminationType();
                boolean defaultFound = defaultTerminationType != null && !defaultTerminationType.equals(terminationType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TerminationTypeDetailValue defaultTerminationTypeDetailValue = getDefaultTerminationTypeDetailValueForUpdate();
                    
                    defaultTerminationTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateTerminationTypeFromValue(defaultTerminationTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            terminationTypeDetail = TerminationTypeDetailFactory.getInstance().create(terminationTypePK,
                    terminationTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            terminationType.setActiveDetail(terminationTypeDetail);
            terminationType.setLastDetail(terminationTypeDetail);
            
            sendEventUsingNames(terminationTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateTerminationTypeFromValue(TerminationTypeDetailValue terminationTypeDetailValue, BasePK updatedBy) {
        updateTerminationTypeFromValue(terminationTypeDetailValue, true, updatedBy);
    }
    
    public void deleteTerminationType(TerminationType terminationType, BasePK deletedBy) {
        deleteTerminationTypeDescriptionsByTerminationType(terminationType, deletedBy);
        deleteEmploymentsByTerminationType(terminationType, deletedBy);
        
        TerminationTypeDetail terminationTypeDetail = terminationType.getLastDetailForUpdate();
        terminationTypeDetail.setThruTime(session.START_TIME_LONG);
        terminationType.setActiveDetail(null);
        terminationType.store();
        
        // Check for default, and pick one if necessary
        TerminationType defaultTerminationType = getDefaultTerminationType();
        if(defaultTerminationType == null) {
            List<TerminationType> terminationTypes = getTerminationTypesForUpdate();
            
            if(!terminationTypes.isEmpty()) {
                Iterator iter = terminationTypes.iterator();
                if(iter.hasNext()) {
                    defaultTerminationType = (TerminationType)iter.next();
                }
                TerminationTypeDetailValue terminationTypeDetailValue = Objects.requireNonNull(defaultTerminationType).getLastDetailForUpdate().getTerminationTypeDetailValue().clone();
                
                terminationTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateTerminationTypeFromValue(terminationTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(terminationType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Termination Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TerminationTypeDescription createTerminationTypeDescription(TerminationType terminationType,
            Language language, String description, BasePK createdBy) {
        TerminationTypeDescription terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().create(session,
                terminationType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(terminationType.getPrimaryKey(), EventTypes.MODIFY.name(), terminationTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return terminationTypeDescription;
    }
    
    private TerminationTypeDescription getTerminationTypeDescription(TerminationType terminationType,
            Language language, EntityPermission entityPermission) {
        TerminationTypeDescription terminationTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationtypedescriptions " +
                        "WHERE trmntypd_trmntyp_terminationtypeid = ? AND trmntypd_lang_languageid = ? AND trmntypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationtypedescriptions " +
                        "WHERE trmntypd_trmntyp_terminationtypeid = ? AND trmntypd_lang_languageid = ? AND trmntypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TerminationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, terminationType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return terminationTypeDescription;
    }
    
    public TerminationTypeDescription getTerminationTypeDescription(TerminationType terminationType, Language language) {
        return getTerminationTypeDescription(terminationType, language, EntityPermission.READ_ONLY);
    }
    
    public TerminationTypeDescription getTerminationTypeDescriptionForUpdate(TerminationType terminationType, Language language) {
        return getTerminationTypeDescription(terminationType, language, EntityPermission.READ_WRITE);
    }
    
    public TerminationTypeDescriptionValue getTerminationTypeDescriptionValue(TerminationTypeDescription terminationTypeDescription) {
        return terminationTypeDescription == null? null: terminationTypeDescription.getTerminationTypeDescriptionValue().clone();
    }
    
    public TerminationTypeDescriptionValue getTerminationTypeDescriptionValueForUpdate(TerminationType terminationType, Language language) {
        return getTerminationTypeDescriptionValue(getTerminationTypeDescriptionForUpdate(terminationType, language));
    }
    
    private List<TerminationTypeDescription> getTerminationTypeDescriptionsByTerminationType(TerminationType terminationType, EntityPermission entityPermission) {
        List<TerminationTypeDescription> terminationTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationtypedescriptions, languages " +
                        "WHERE trmntypd_trmntyp_terminationtypeid = ? AND trmntypd_thrutime = ? AND trmntypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terminationtypedescriptions " +
                        "WHERE trmntypd_trmntyp_terminationtypeid = ? AND trmntypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TerminationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, terminationType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            terminationTypeDescriptions = TerminationTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return terminationTypeDescriptions;
    }
    
    public List<TerminationTypeDescription> getTerminationTypeDescriptionsByTerminationType(TerminationType terminationType) {
        return getTerminationTypeDescriptionsByTerminationType(terminationType, EntityPermission.READ_ONLY);
    }
    
    public List<TerminationTypeDescription> getTerminationTypeDescriptionsByTerminationTypeForUpdate(TerminationType terminationType) {
        return getTerminationTypeDescriptionsByTerminationType(terminationType, EntityPermission.READ_WRITE);
    }
    
    public String getBestTerminationTypeDescription(TerminationType terminationType, Language language) {
        String description;
        TerminationTypeDescription terminationTypeDescription = getTerminationTypeDescription(terminationType, language);
        
        if(terminationTypeDescription == null && !language.getIsDefault()) {
            terminationTypeDescription = getTerminationTypeDescription(terminationType, getPartyControl().getDefaultLanguage());
        }
        
        if(terminationTypeDescription == null) {
            description = terminationType.getLastDetail().getTerminationTypeName();
        } else {
            description = terminationTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public TerminationTypeDescriptionTransfer getTerminationTypeDescriptionTransfer(UserVisit userVisit, TerminationTypeDescription terminationTypeDescription) {
        return getEmployeeTransferCaches(userVisit).getTerminationTypeDescriptionTransferCache().getTerminationTypeDescriptionTransfer(terminationTypeDescription);
    }
    
    public List<TerminationTypeDescriptionTransfer> getTerminationTypeDescriptionTransfers(UserVisit userVisit, TerminationType terminationType) {
        List<TerminationTypeDescription> terminationTypeDescriptions = getTerminationTypeDescriptionsByTerminationType(terminationType);
        List<TerminationTypeDescriptionTransfer> terminationTypeDescriptionTransfers = new ArrayList<>(terminationTypeDescriptions.size());
        TerminationTypeDescriptionTransferCache terminationTypeDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getTerminationTypeDescriptionTransferCache();
        
        terminationTypeDescriptions.forEach((terminationTypeDescription) ->
                terminationTypeDescriptionTransfers.add(terminationTypeDescriptionTransferCache.getTerminationTypeDescriptionTransfer(terminationTypeDescription))
        );
        
        return terminationTypeDescriptionTransfers;
    }
    
    public void updateTerminationTypeDescriptionFromValue(TerminationTypeDescriptionValue terminationTypeDescriptionValue, BasePK updatedBy) {
        if(terminationTypeDescriptionValue.hasBeenModified()) {
            TerminationTypeDescription terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationTypeDescriptionValue.getPrimaryKey());
            
            terminationTypeDescription.setThruTime(session.START_TIME_LONG);
            terminationTypeDescription.store();
            
            TerminationType terminationType = terminationTypeDescription.getTerminationType();
            Language language = terminationTypeDescription.getLanguage();
            String description = terminationTypeDescriptionValue.getDescription();
            
            terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().create(terminationType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(terminationType.getPrimaryKey(), EventTypes.MODIFY.name(), terminationTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTerminationTypeDescription(TerminationTypeDescription terminationTypeDescription, BasePK deletedBy) {
        terminationTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(terminationTypeDescription.getTerminationTypePK(), EventTypes.MODIFY.name(), terminationTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteTerminationTypeDescriptionsByTerminationType(TerminationType terminationType, BasePK deletedBy) {
        List<TerminationTypeDescription> terminationTypeDescriptions = getTerminationTypeDescriptionsByTerminationTypeForUpdate(terminationType);
        
        terminationTypeDescriptions.forEach((terminationTypeDescription) -> 
                deleteTerminationTypeDescription(terminationTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Employments
    // --------------------------------------------------------------------------------

    public Employment createEmployment(Party party, Party companyParty, Long startTime, Long endTime, TerminationType terminationType,
            TerminationReason terminationReason, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.EMPLOYMENT.name());
        String employmentName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createEmployment(employmentName, party, companyParty, startTime, endTime, terminationType, terminationReason, createdBy);
    }

    public Employment createEmployment(String employmentName, Party party, Party companyParty, Long startTime, Long endTime,
            TerminationType terminationType, TerminationReason terminationReason, BasePK createdBy) {
        Employment employment = EmploymentFactory.getInstance().create();
        EmploymentDetail employmentDetail = EmploymentDetailFactory.getInstance().create(employment, employmentName, party, companyParty, startTime,
                endTime, terminationType, terminationReason, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        employment = EmploymentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, employment.getPrimaryKey());
        employment.setActiveDetail(employmentDetail);
        employment.setLastDetail(employmentDetail);
        employment.store();

        EmploymentPK employmentPK = employment.getPrimaryKey();
        sendEventUsingNames(employmentPK, EventTypes.CREATE.name(), null, null, createdBy);
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.TOUCH.name(), employmentPK, EventTypes.CREATE.name(), createdBy);

        return employment;
    }

    public long countEmploymentsByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid "
                + "AND empmntdt_par_partyid = ?",
                party);
    }

    public long countEmploymentsByCompanyParty(Party companyParty) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid "
                + "AND empmntdt_companypartyid = ?",
                companyParty);
    }

    public long countEmploymentsByTerminationType(TerminationType terminationType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid "
                + "AND empmntdt_trmntyp_terminationtypeid = ?",
                terminationType);
    }

    public long countEmploymentsByTerminationReason(TerminationReason terminationReason) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid "
                + "AND empmntdt_trmnrsn_terminationreasonid = ?",
                terminationReason);
    }

    private static final Map<EntityPermission, String> getEmploymentByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_employmentname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_employmentname = ? "
                + "FOR UPDATE");
        getEmploymentByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Employment getEmploymentByName(String employmentName, EntityPermission entityPermission) {
        return EmploymentFactory.getInstance().getEntityFromQuery(entityPermission, getEmploymentByNameQueries,
                employmentName);
    }

    public Employment getEmploymentByName(String employmentName) {
        return getEmploymentByName(employmentName, EntityPermission.READ_ONLY);
    }

    public Employment getEmploymentByNameForUpdate(String employmentName) {
        return getEmploymentByName(employmentName, EntityPermission.READ_WRITE);
    }

    public EmploymentDetailValue getEmploymentDetailValueForUpdate(Employment employment) {
        return employment == null ? null : employment.getLastDetailForUpdate().getEmploymentDetailValue().clone();
    }

    public EmploymentDetailValue getEmploymentDetailValueByNameForUpdate(String employmentName) {
        return getEmploymentDetailValueForUpdate(getEmploymentByNameForUpdate(employmentName));
    }

    private static final Map<EntityPermission, String> getEmploymentByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_par_partyid = ? "
                + "ORDER BY empmntdt_employmentname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_par_partyid = ? "
                + "FOR UPDATE");
        getEmploymentByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Employment> getEmploymentsByParty(Party party, EntityPermission entityPermission) {
        return EmploymentFactory.getInstance().getEntitiesFromQuery(entityPermission, getEmploymentByPartyQueries,
                party);
    }

    public List<Employment> getEmploymentsByParty(Party party) {
        return getEmploymentsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByPartyForUpdate(Party party) {
        return getEmploymentsByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getEmploymentByCompanyPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails, parties, partydetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND empmntdt_companypartyid = ? "
                + "ORDER BY empmntdt_employmentname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_companypartyid = ? "
                + "FOR UPDATE");
        getEmploymentByCompanyPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Employment> getEmploymentsByCompanyParty(Party companyParty, EntityPermission entityPermission) {
        return EmploymentFactory.getInstance().getEntitiesFromQuery(entityPermission, getEmploymentByCompanyPartyQueries,
                companyParty);
    }

    public List<Employment> getEmploymentsByCompanyParty(Party companyParty) {
        return getEmploymentsByCompanyParty(companyParty, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByCompanyPartyForUpdate(Party companyParty) {
        return getEmploymentsByCompanyParty(companyParty, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getEmploymentByTerminationTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails, parties, partydetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND empmntdt_trmntyp_terminationtypeid = ? "
                + "ORDER BY empmntdt_employmentname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_trmntyp_terminationtypeid = ? "
                + "FOR UPDATE");
        getEmploymentByTerminationTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Employment> getEmploymentsByTerminationType(TerminationType terminationType, EntityPermission entityPermission) {
        return EmploymentFactory.getInstance().getEntitiesFromQuery(entityPermission, getEmploymentByTerminationTypeQueries,
                terminationType);
    }

    public List<Employment> getEmploymentsByTerminationType(TerminationType terminationType) {
        return getEmploymentsByTerminationType(terminationType, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByTerminationTypeForUpdate(TerminationType terminationType) {
        return getEmploymentsByTerminationType(terminationType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getEmploymentByTerminationReasonQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails, parties, partydetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND empmntdt_trmnrsn_terminationreasonid = ? "
                + "ORDER BY empmntdt_employmentname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM employments, employmentdetails "
                + "WHERE empmnt_activedetailid = empmntdt_employmentdetailid AND empmntdt_trmnrsn_terminationreasonid = ? "
                + "FOR UPDATE");
        getEmploymentByTerminationReasonQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Employment> getEmploymentsByTerminationReason(TerminationReason terminationReason, EntityPermission entityPermission) {
        return EmploymentFactory.getInstance().getEntitiesFromQuery(entityPermission, getEmploymentByTerminationReasonQueries,
                terminationReason);
    }

    public List<Employment> getEmploymentsByTerminationReason(TerminationReason terminationReason) {
        return getEmploymentsByTerminationReason(terminationReason, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByTerminationReasonForUpdate(TerminationReason terminationReason) {
        return getEmploymentsByTerminationReason(terminationReason, EntityPermission.READ_WRITE);
    }

    public EmploymentTransfer getEmploymentTransfer(UserVisit userVisit, Employment employment) {
        return getEmployeeTransferCaches(userVisit).getEmploymentTransferCache().getEmploymentTransfer(employment);
    }

    public List<EmploymentTransfer> getEmploymentTransfers(UserVisit userVisit, List<Employment> employments) {
        List<EmploymentTransfer> employmentTransfers = new ArrayList<>(employments.size());
        EmploymentTransferCache employmentTransferCache = getEmployeeTransferCaches(userVisit).getEmploymentTransferCache();

        employments.forEach((employment) ->
                employmentTransfers.add(employmentTransferCache.getEmploymentTransfer(employment))
        );

        return employmentTransfers;
    }

    public List<EmploymentTransfer> getEmploymentTransfersByParty(UserVisit userVisit, Party party) {
        return getEmploymentTransfers(userVisit, getEmploymentsByParty(party));
    }

    public void updateEmploymentFromValue(EmploymentDetailValue employmentDetailValue, BasePK updatedBy) {
        if(employmentDetailValue.hasBeenModified()) {
            Employment employment = EmploymentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     employmentDetailValue.getEmploymentPK());
            EmploymentDetail employmentDetail = employment.getActiveDetailForUpdate();

            employmentDetail.setThruTime(session.START_TIME_LONG);
            employmentDetail.store();

            EmploymentPK employmentPK = employmentDetail.getEmploymentPK(); // Not updated
            String employmentName = employmentDetail.getEmploymentName(); // Not updated
            PartyPK partyPK = employmentDetail.getPartyPK(); // Not updated
            PartyPK companyPartyPK = employmentDetailValue.getCompanyPartyPK();
            Long startTime = employmentDetailValue.getStartTime();
            Long endTime = employmentDetailValue.getEndTime();
            TerminationTypePK terminationTypePK = employmentDetailValue.getTerminationTypePK();
            TerminationReasonPK terminationReasonPK = employmentDetailValue.getTerminationReasonPK();

            employmentDetail = EmploymentDetailFactory.getInstance().create(employmentPK, employmentName, partyPK, companyPartyPK, startTime, endTime,
                    terminationTypePK, terminationReasonPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            employment.setActiveDetail(employmentDetail);
            employment.setLastDetail(employmentDetail);

            sendEventUsingNames(employmentPK, EventTypes.MODIFY.name(), null, null, updatedBy);
            sendEventUsingNames(partyPK, EventTypes.TOUCH.name(), employmentPK, EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteEmployment(Employment employment, BasePK deletedBy) {
        EmploymentDetail employmentDetail = employment.getLastDetailForUpdate();
        employmentDetail.setThruTime(session.START_TIME_LONG);
        employmentDetail.store();
        employment.setActiveDetail(null);

        EmploymentPK employmentPK = employment.getPrimaryKey();
        sendEventUsingNames(employmentPK, EventTypes.DELETE.name(), null, null, deletedBy);
        sendEventUsingNames(employmentDetail.getParty().getPrimaryKey(), EventTypes.TOUCH.name(), employmentPK, EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteEmployments(List<Employment> employments, BasePK deletedBy) {
        employments.forEach((employment) -> 
                deleteEmployment(employment, deletedBy)
        );
    }

    public void deleteEmploymentsByParty(Party party, BasePK deletedBy) {
        deleteEmployments(getEmploymentsByPartyForUpdate(party), deletedBy);
    }

    public void deleteEmploymentsByTerminationType(TerminationType terminationType, BasePK deletedBy) {
        deleteEmployments(getEmploymentsByTerminationTypeForUpdate(terminationType), deletedBy);
    }

    public void deleteEmploymentsByTerminationReason(TerminationReason terminationReason, BasePK deletedBy) {
        deleteEmployments(getEmploymentsByTerminationReasonForUpdate(terminationReason), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Employee Types
    // --------------------------------------------------------------------------------
    
    public EmployeeType createEmployeeType(String employeeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        EmployeeType defaultEmployeeType = getDefaultEmployeeType();
        boolean defaultFound = defaultEmployeeType != null;
        
        if(defaultFound && isDefault) {
            EmployeeTypeDetailValue defaultEmployeeTypeDetailValue = getDefaultEmployeeTypeDetailValueForUpdate();
            
            defaultEmployeeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateEmployeeTypeFromValue(defaultEmployeeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        EmployeeType employeeType = EmployeeTypeFactory.getInstance().create();
        EmployeeTypeDetail employeeTypeDetail = EmployeeTypeDetailFactory.getInstance().create(employeeType,
                employeeTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        employeeType = EmployeeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                employeeType.getPrimaryKey());
        employeeType.setActiveDetail(employeeTypeDetail);
        employeeType.setLastDetail(employeeTypeDetail);
        employeeType.store();
        
        sendEventUsingNames(employeeType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return employeeType;
    }
    
    private List<EmployeeType> getEmployeeTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM employeetypes, employeetypedetails " +
                    "WHERE empty_activedetailid = emptydt_employeetypedetailid " +
                    "ORDER BY emptydt_sortorder, emptydt_employeetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM employeetypes, employeetypedetails " +
                    "WHERE empty_activedetailid = emptydt_employeetypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = EmployeeTypeFactory.getInstance().prepareStatement(query);
        
        return EmployeeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<EmployeeType> getEmployeeTypes() {
        return getEmployeeTypes(EntityPermission.READ_ONLY);
    }
    
    public List<EmployeeType> getEmployeeTypesForUpdate() {
        return getEmployeeTypes(EntityPermission.READ_WRITE);
    }
    
    private EmployeeType getDefaultEmployeeType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM employeetypes, employeetypedetails " +
                    "WHERE empty_activedetailid = emptydt_employeetypedetailid AND emptydt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM employeetypes, employeetypedetails " +
                    "WHERE empty_activedetailid = emptydt_employeetypedetailid AND emptydt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = EmployeeTypeFactory.getInstance().prepareStatement(query);
        
        return EmployeeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public EmployeeType getDefaultEmployeeType() {
        return getDefaultEmployeeType(EntityPermission.READ_ONLY);
    }
    
    public EmployeeType getDefaultEmployeeTypeForUpdate() {
        return getDefaultEmployeeType(EntityPermission.READ_WRITE);
    }
    
    public EmployeeTypeDetailValue getDefaultEmployeeTypeDetailValueForUpdate() {
        return getDefaultEmployeeTypeForUpdate().getLastDetailForUpdate().getEmployeeTypeDetailValue().clone();
    }
    
    private EmployeeType getEmployeeTypeByName(String employeeTypeName, EntityPermission entityPermission) {
        EmployeeType employeeType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM employeetypes, employeetypedetails " +
                        "WHERE empty_activedetailid = emptydt_employeetypedetailid AND emptydt_employeetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM employeetypes, employeetypedetails " +
                        "WHERE empty_activedetailid = emptydt_employeetypedetailid AND emptydt_employeetypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EmployeeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, employeeTypeName);
            
            employeeType = EmployeeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return employeeType;
    }
    
    public EmployeeType getEmployeeTypeByName(String employeeTypeName) {
        return getEmployeeTypeByName(employeeTypeName, EntityPermission.READ_ONLY);
    }
    
    public EmployeeType getEmployeeTypeByNameForUpdate(String employeeTypeName) {
        return getEmployeeTypeByName(employeeTypeName, EntityPermission.READ_WRITE);
    }
    
    public EmployeeTypeDetailValue getEmployeeTypeDetailValueForUpdate(EmployeeType employeeType) {
        return employeeType == null? null: employeeType.getLastDetailForUpdate().getEmployeeTypeDetailValue().clone();
    }
    
    public EmployeeTypeDetailValue getEmployeeTypeDetailValueByNameForUpdate(String employeeTypeName) {
        return getEmployeeTypeDetailValueForUpdate(getEmployeeTypeByNameForUpdate(employeeTypeName));
    }
    
    public EmployeeTypeChoicesBean getEmployeeTypeChoices(String defaultEmployeeTypeChoice, Language language,
            boolean allowNullChoice) {
        List<EmployeeType> employeeTypes = getEmployeeTypes();
        var size = employeeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEmployeeTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var employeeType : employeeTypes) {
            EmployeeTypeDetail employeeTypeDetail = employeeType.getLastDetail();
            var label = getBestEmployeeTypeDescription(employeeType, language);
            var value = employeeTypeDetail.getEmployeeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEmployeeTypeChoice != null && defaultEmployeeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && employeeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EmployeeTypeChoicesBean(labels, values, defaultValue);
    }
    
    public EmployeeTypeTransfer getEmployeeTypeTransfer(UserVisit userVisit, EmployeeType employeeType) {
        return getEmployeeTransferCaches(userVisit).getEmployeeTypeTransferCache().getEmployeeTypeTransfer(employeeType);
    }
    
    public List<EmployeeTypeTransfer> getEmployeeTypeTransfers(UserVisit userVisit) {
        List<EmployeeType> employeeTypes = getEmployeeTypes();
        List<EmployeeTypeTransfer> employeeTypeTransfers = null;
        
        if(employeeTypes != null) {
            EmployeeTypeTransferCache employeeTypeTransferCache = getEmployeeTransferCaches(userVisit).getEmployeeTypeTransferCache();
            
            employeeTypeTransfers = new ArrayList<>(employeeTypes.size());
            
            for(var employeeType : employeeTypes) {
                employeeTypeTransfers.add(employeeTypeTransferCache.getEmployeeTypeTransfer(employeeType));
            }
        }
        
        return employeeTypeTransfers;
    }
    
    private void updateEmployeeTypeFromValue(EmployeeTypeDetailValue employeeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(employeeTypeDetailValue.hasBeenModified()) {
            EmployeeType employeeType = EmployeeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     employeeTypeDetailValue.getEmployeeTypePK());
            EmployeeTypeDetail employeeTypeDetail = employeeType.getActiveDetailForUpdate();
            
            employeeTypeDetail.setThruTime(session.START_TIME_LONG);
            employeeTypeDetail.store();
            
            EmployeeTypePK employeeTypePK = employeeTypeDetail.getEmployeeTypePK();
            String employeeTypeName = employeeTypeDetailValue.getEmployeeTypeName();
            Boolean isDefault = employeeTypeDetailValue.getIsDefault();
            Integer sortOrder = employeeTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                EmployeeType defaultEmployeeType = getDefaultEmployeeType();
                boolean defaultFound = defaultEmployeeType != null && !defaultEmployeeType.equals(employeeType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    EmployeeTypeDetailValue defaultEmployeeTypeDetailValue = getDefaultEmployeeTypeDetailValueForUpdate();
                    
                    defaultEmployeeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateEmployeeTypeFromValue(defaultEmployeeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            employeeTypeDetail = EmployeeTypeDetailFactory.getInstance().create(employeeTypePK, employeeTypeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            employeeType.setActiveDetail(employeeTypeDetail);
            employeeType.setLastDetail(employeeTypeDetail);
            
            sendEventUsingNames(employeeTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateEmployeeTypeFromValue(EmployeeTypeDetailValue employeeTypeDetailValue, BasePK updatedBy) {
        updateEmployeeTypeFromValue(employeeTypeDetailValue, true, updatedBy);
    }
    
    public void deleteEmployeeType(EmployeeType employeeType, BasePK deletedBy) {
        deleteEmployeeTypeDescriptionsByEmployeeType(employeeType, deletedBy);
        
        EmployeeTypeDetail employeeTypeDetail = employeeType.getLastDetailForUpdate();
        employeeTypeDetail.setThruTime(session.START_TIME_LONG);
        employeeType.setActiveDetail(null);
        employeeType.store();
        
        // Check for default, and pick one if necessary
        EmployeeType defaultEmployeeType = getDefaultEmployeeType();
        if(defaultEmployeeType == null) {
            List<EmployeeType> employeeTypes = getEmployeeTypesForUpdate();
            
            if(!employeeTypes.isEmpty()) {
                Iterator<EmployeeType> iter = employeeTypes.iterator();
                if(iter.hasNext()) {
                    defaultEmployeeType = iter.next();
                }
                EmployeeTypeDetailValue employeeTypeDetailValue = Objects.requireNonNull(defaultEmployeeType).getLastDetailForUpdate().getEmployeeTypeDetailValue().clone();
                
                employeeTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateEmployeeTypeFromValue(employeeTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(employeeType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EmployeeTypeDescription createEmployeeTypeDescription(EmployeeType employeeType, Language language, String description,
            BasePK createdBy) {
        EmployeeTypeDescription employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().create(employeeType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(employeeType.getPrimaryKey(), EventTypes.MODIFY.name(), employeeTypeDescription.getPrimaryKey(),
                null, createdBy);
        
        return employeeTypeDescription;
    }
    
    private EmployeeTypeDescription getEmployeeTypeDescription(EmployeeType employeeType, Language language, EntityPermission entityPermission) {
        EmployeeTypeDescription employeeTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM employeetypedescriptions " +
                        "WHERE emptyd_empty_employeetypeid = ? AND emptyd_lang_languageid = ? AND emptyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM employeetypedescriptions " +
                        "WHERE emptyd_empty_employeetypeid = ? AND emptyd_lang_languageid = ? AND emptyd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EmployeeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, employeeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return employeeTypeDescription;
    }
    
    public EmployeeTypeDescription getEmployeeTypeDescription(EmployeeType employeeType, Language language) {
        return getEmployeeTypeDescription(employeeType, language, EntityPermission.READ_ONLY);
    }
    
    public EmployeeTypeDescription getEmployeeTypeDescriptionForUpdate(EmployeeType employeeType, Language language) {
        return getEmployeeTypeDescription(employeeType, language, EntityPermission.READ_WRITE);
    }
    
    public EmployeeTypeDescriptionValue getEmployeeTypeDescriptionValue(EmployeeTypeDescription employeeTypeDescription) {
        return employeeTypeDescription == null? null: employeeTypeDescription.getEmployeeTypeDescriptionValue().clone();
    }
    
    public EmployeeTypeDescriptionValue getEmployeeTypeDescriptionValueForUpdate(EmployeeType employeeType, Language language) {
        return getEmployeeTypeDescriptionValue(getEmployeeTypeDescriptionForUpdate(employeeType, language));
    }
    
    private List<EmployeeTypeDescription> getEmployeeTypeDescriptionsByEmployeeType(EmployeeType employeeType, EntityPermission entityPermission) {
        List<EmployeeTypeDescription> employeeTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM employeetypedescriptions, languages " +
                        "WHERE emptyd_empty_employeetypeid = ? AND emptyd_thrutime = ? AND emptyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM employeetypedescriptions " +
                        "WHERE emptyd_empty_employeetypeid = ? AND emptyd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EmployeeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, employeeType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            employeeTypeDescriptions = EmployeeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return employeeTypeDescriptions;
    }
    
    public List<EmployeeTypeDescription> getEmployeeTypeDescriptionsByEmployeeType(EmployeeType employeeType) {
        return getEmployeeTypeDescriptionsByEmployeeType(employeeType, EntityPermission.READ_ONLY);
    }
    
    public List<EmployeeTypeDescription> getEmployeeTypeDescriptionsByEmployeeTypeForUpdate(EmployeeType employeeType) {
        return getEmployeeTypeDescriptionsByEmployeeType(employeeType, EntityPermission.READ_WRITE);
    }
    
    public String getBestEmployeeTypeDescription(EmployeeType employeeType, Language language) {
        String description;
        EmployeeTypeDescription employeeTypeDescription = getEmployeeTypeDescription(employeeType, language);
        
        if(employeeTypeDescription == null && !language.getIsDefault()) {
            employeeTypeDescription = getEmployeeTypeDescription(employeeType, getPartyControl().getDefaultLanguage());
        }
        
        if(employeeTypeDescription == null) {
            description = employeeType.getLastDetail().getEmployeeTypeName();
        } else {
            description = employeeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public EmployeeTypeDescriptionTransfer getEmployeeTypeDescriptionTransfer(UserVisit userVisit, EmployeeTypeDescription employeeTypeDescription) {
        return getEmployeeTransferCaches(userVisit).getEmployeeTypeDescriptionTransferCache().getEmployeeTypeDescriptionTransfer(employeeTypeDescription);
    }
    
    public List<EmployeeTypeDescriptionTransfer> getEmployeeTypeDescriptionTransfers(UserVisit userVisit, EmployeeType employeeType) {
        List<EmployeeTypeDescription> employeeTypeDescriptions = getEmployeeTypeDescriptionsByEmployeeType(employeeType);
        List<EmployeeTypeDescriptionTransfer> employeeTypeDescriptionTransfers = null;
        
        if(employeeTypeDescriptions != null) {
            EmployeeTypeDescriptionTransferCache employeeTypeDescriptionTransferCache = getEmployeeTransferCaches(userVisit).getEmployeeTypeDescriptionTransferCache();
            
            employeeTypeDescriptionTransfers = new ArrayList<>(employeeTypeDescriptions.size());
            
            for(var employeeTypeDescription : employeeTypeDescriptions) {
                employeeTypeDescriptionTransfers.add(employeeTypeDescriptionTransferCache.getEmployeeTypeDescriptionTransfer(employeeTypeDescription));
            }
        }
        
        return employeeTypeDescriptionTransfers;
    }
    
    public void updateEmployeeTypeDescriptionFromValue(EmployeeTypeDescriptionValue employeeTypeDescriptionValue, BasePK updatedBy) {
        if(employeeTypeDescriptionValue.hasBeenModified()) {
            EmployeeTypeDescription employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     employeeTypeDescriptionValue.getPrimaryKey());
            
            employeeTypeDescription.setThruTime(session.START_TIME_LONG);
            employeeTypeDescription.store();
            
            EmployeeType employeeType = employeeTypeDescription.getEmployeeType();
            Language language = employeeTypeDescription.getLanguage();
            String description = employeeTypeDescriptionValue.getDescription();
            
            employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().create(employeeType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(employeeType.getPrimaryKey(), EventTypes.MODIFY.name(), employeeTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteEmployeeTypeDescription(EmployeeTypeDescription employeeTypeDescription, BasePK deletedBy) {
        employeeTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(employeeTypeDescription.getEmployeeTypePK(), EventTypes.MODIFY.name(),
                employeeTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteEmployeeTypeDescriptionsByEmployeeType(EmployeeType employeeType, BasePK deletedBy) {
        List<EmployeeTypeDescription> employeeTypeDescriptions = getEmployeeTypeDescriptionsByEmployeeTypeForUpdate(employeeType);
        
        employeeTypeDescriptions.forEach((employeeTypeDescription) -> 
                deleteEmployeeTypeDescription(employeeTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Employees
    // --------------------------------------------------------------------------------
    
    public PartyEmployee createPartyEmployee(Party party, String partyEmployeeName, EmployeeType employeeType, BasePK createdBy) {
        PartyEmployee partyEmployee = PartyEmployeeFactory.getInstance().create(party, partyEmployeeName, employeeType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyEmployee.getPrimaryKey(), null, createdBy);
        
        return partyEmployee;
    }

    public long countPartyEmployees() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyemployees " +
                "WHERE pemp_thrutime = ?",
                Session.MAX_TIME);
    }

    public List<PartyEmployee> getPartyEmployees() {
        List<PartyEmployee> partyEmployees;
        
        try {
            PreparedStatement ps = PartyEmployeeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partyemployees " +
                    "WHERE pemp_thrutime = ? " +
                    "ORDER BY pemp_partyemployeename " +
                    "_LIMIT_");
            
            ps.setLong(1, Session.MAX_TIME);
            
            partyEmployees = PartyEmployeeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEmployees;
    }
    
    private PartyEmployee getPartyEmployee(Party party, EntityPermission entityPermission) {
        PartyEmployee partyEmployee;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyemployees " +
                        "WHERE pemp_par_partyid = ? AND pemp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyemployees " +
                        "WHERE pemp_par_partyid = ? AND pemp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyEmployeeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEmployee = PartyEmployeeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEmployee;
    }
    
    public PartyEmployee getPartyEmployee(Party party) {
        return getPartyEmployee(party, EntityPermission.READ_ONLY);
    }
    
    public PartyEmployee getPartyEmployeeForUpdate(Party party) {
        return getPartyEmployee(party, EntityPermission.READ_WRITE);
    }
    
    public PartyEmployee getPartyEmployeeByName(String partyEmployeeName, EntityPermission entityPermission) {
        PartyEmployee partyEmployee;
        
        try {
            PreparedStatement ps = PartyEmployeeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partyemployees " +
                    "WHERE pemp_partyemployeename = ? AND pemp_thrutime = ?");
            
            ps.setString(1, partyEmployeeName);
            ps.setLong(2, Session.MAX_TIME);
            
            partyEmployee = PartyEmployeeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEmployee;
    }
    
    public PartyEmployee getPartyEmployeeByName(String partyEmployeeName) {
        return getPartyEmployeeByName(partyEmployeeName, EntityPermission.READ_ONLY);
    }
    
    public PartyEmployee getPartyEmployeeByNameForUpdate(String partyEmployeeName) {
        return getPartyEmployeeByName(partyEmployeeName, EntityPermission.READ_WRITE);
    }
    
    public PartyEmployeeValue getPartyEmployeeValue(PartyEmployee partyEmployee) {
        return partyEmployee == null? null: partyEmployee.getPartyEmployeeValue().clone();
    }
    
    public PartyEmployeeValue getPartyEmployeeValueByNameForUpdate(String partyEmployeeName) {
        return getPartyEmployeeValue(getPartyEmployeeByNameForUpdate(partyEmployeeName));
    }
    
    public EmployeeStatusChoicesBean getEmployeeStatusChoices(String defaultEmployeeStatusChoice, Language language,
            boolean allowNullChoice, Party employeeParty, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        EmployeeStatusChoicesBean employeeStatusChoicesBean = new EmployeeStatusChoicesBean();
        
        if(employeeParty == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultEmployeeStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(employeeParty.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultEmployeeStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setEmployeeStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(party);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmployeeStatusChoice.name(), employeeStatusChoice);
        }
    }
    
    public EmployeeAvailabilityChoicesBean getEmployeeAvailabilityChoices(String defaultEmployeeAvailabilityChoice, Language language,
            boolean allowNullChoice, Party employeeParty, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        EmployeeAvailabilityChoicesBean employeeAvailabilityChoicesBean = new EmployeeAvailabilityChoicesBean();
        
        if(employeeParty == null) {
            workflowControl.getWorkflowEntranceChoices(employeeAvailabilityChoicesBean, defaultEmployeeAvailabilityChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(employeeParty.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeAvailabilityChoicesBean, defaultEmployeeAvailabilityChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeAvailabilityChoicesBean;
    }
    
    public void setEmployeeAvailability(ExecutionErrorAccumulator eea, Party party, String employeeAvailabilityChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(party);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY,
                entityInstance);
        WorkflowDestination workflowDestination = employeeAvailabilityChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeAvailabilityChoice);
        
        if(workflowDestination != null || employeeAvailabilityChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmployeeAvailabilityChoice.name(), employeeAvailabilityChoice);
        }
    }

    public List<EmployeeTransfer> getEmployeeTransfers(UserVisit userVisit, Collection<PartyEmployee> partyEmployees) {
        List<EmployeeTransfer> employeeTransfers = new ArrayList<>(partyEmployees.size());
        EmployeeTransferCache employeeTransferCache = getEmployeeTransferCaches(userVisit).getEmployeeTransferCache();

        partyEmployees.stream().map((partyEmployee) -> partyEmployee.getParty()).forEach((party) -> employeeTransfers.add(employeeTransferCache.getTransfer(party)));

        return employeeTransfers;
    }

    public List<EmployeeTransfer> getEmployeeTransfers(UserVisit userVisit) {
        return getEmployeeTransfers(userVisit, getPartyEmployees());
    }

    public EmployeeTransfer getEmployeeTransfer(UserVisit userVisit, PartyEmployee partyEmployee) {
        return getEmployeeTransferCaches(userVisit).getEmployeeTransferCache().getTransfer(partyEmployee);
    }

    public EmployeeTransfer getEmployeeTransfer(UserVisit userVisit, Party party) {
        return getEmployeeTransferCaches(userVisit).getEmployeeTransferCache().getTransfer(party);
    }
    
    public void updatePartyEmployeeFromValue(PartyEmployeeValue partyEmployeeValue, BasePK updatedBy) {
        if(partyEmployeeValue.hasBeenModified()) {
            PartyEmployee partyEmployee = PartyEmployeeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyEmployeeValue.getPrimaryKey());

            partyEmployee.setThruTime(session.START_TIME_LONG);
            partyEmployee.store();

            PartyPK partyPK = partyEmployee.getPartyPK(); // Not updated
            String partyEmployeeName = partyEmployeeValue.getPartyEmployeeName();
            EmployeeTypePK employeeTypePK = partyEmployeeValue.getEmployeeTypePK();

            partyEmployee = PartyEmployeeFactory.getInstance().create(partyPK, partyEmployeeName, employeeTypePK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyEmployee.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyEmployee(PartyEmployee partyEmployee, BasePK deletedBy) {
        partyEmployee.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyEmployee.getPartyPK(), EventTypes.MODIFY.name(), partyEmployee.getPrimaryKey(), null, deletedBy);
    }
    
    public void deletePartyEmployeeByParty(Party party, BasePK deletedBy) {
        PartyEmployee partyEmployee = getPartyEmployeeForUpdate(party);
        
        if(partyEmployee != null) {
            deletePartyEmployee(partyEmployee, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Party Responsibilities
    // --------------------------------------------------------------------------------
    
    public PartyResponsibility createPartyResponsibility(Party party, ResponsibilityType responsibilityType, BasePK createdBy) {
        PartyResponsibility partyResponsibility = PartyResponsibilityFactory.getInstance().create(party, responsibilityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyResponsibility.getPrimaryKey(), null, createdBy);
        
        return partyResponsibility;
    }
    
    private PartyResponsibility getPartyResponsibility(Party party, ResponsibilityType responsibilityType, EntityPermission entityPermission) {
        PartyResponsibility partyResponsibility;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyresponsibilities " +
                        "WHERE parrsp_par_partyid = ? AND parrsp_rsptyp_responsibilitytypeid = ? AND parrsp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyresponsibilities " +
                        "WHERE parrsp_par_partyid = ? AND parrsp_rsptyp_responsibilitytypeid = ? AND parrsp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyResponsibilityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, responsibilityType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partyResponsibility = PartyResponsibilityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyResponsibility;
    }
    
    public PartyResponsibility getPartyResponsibility(Party party, ResponsibilityType responsibilityType) {
        return getPartyResponsibility(party, responsibilityType, EntityPermission.READ_ONLY);
    }
    
    public PartyResponsibility getPartyResponsibilityForUpdate(Party party, ResponsibilityType responsibilityType) {
        return getPartyResponsibility(party, responsibilityType, EntityPermission.READ_WRITE);
    }
    
    public PartyResponsibilityValue getPartyResponsibilityValue(PartyResponsibility partyResponsibility) {
        return partyResponsibility == null? null: partyResponsibility.getPartyResponsibilityValue().clone();
    }
    
    public PartyResponsibilityValue getPartyResponsibilityValueForUpdate(Party party, ResponsibilityType responsibilityType) {
        return getPartyResponsibilityValue(getPartyResponsibilityForUpdate(party, responsibilityType));
    }
    
    private List<PartyResponsibility> getPartyResponsibilitiesByParty(Party party, EntityPermission entityPermission) {
        List<PartyResponsibility> partyResponsibilities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyresponsibilities, responsibilitytypes, responsibilitytypedetails " +
                        "WHERE parrsp_par_partyid = ? AND parrsp_thrutime = ? " +
                        "AND parrsp_rsptyp_responsibilitytypeid = rsptyp_responsibilitytypeid AND rsptyp_lastdetailid = rsptypdt_responsibilitytypedetailid " +
                        "ORDER BY rsptypdt_sortorder, rsptypdt_responsibilitytypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyresponsibilities " +
                        "WHERE parrsp_par_partyid = ? AND parrsp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyResponsibilityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyResponsibilities = PartyResponsibilityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyResponsibilities;
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByParty(Party party) {
        return getPartyResponsibilitiesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByPartyForUpdate(Party party) {
        return getPartyResponsibilitiesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyResponsibility> getPartyResponsibilitiesByResponsibilityType(ResponsibilityType responsibilityType, EntityPermission entityPermission) {
        List<PartyResponsibility> partyResponsibilities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyresponsibilities, parties, partydetails " +
                        "WHERE parrsp_rsptyp_responsibilitytypeid = ? AND parrsp_thrutime = ? " +
                        "AND parrsp_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyresponsibilities " +
                        "WHERE parrsp_rsptyp_responsibilitytypeid = ? AND parrsp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyResponsibilityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, responsibilityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyResponsibilities = PartyResponsibilityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyResponsibilities;
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByResponsibilityType(ResponsibilityType responsibilityType) {
        return getPartyResponsibilitiesByResponsibilityType(responsibilityType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByResponsibilityTypeForUpdate(ResponsibilityType responsibilityType) {
        return getPartyResponsibilitiesByResponsibilityType(responsibilityType, EntityPermission.READ_WRITE);
    }
    
    public PartyResponsibilityTransfer getPartyResponsibilityTransfer(UserVisit userVisit, Party party, ResponsibilityType responsibilityType) {
        PartyResponsibility partyResponsibility = getPartyResponsibility(party, responsibilityType);
        
        return partyResponsibility == null? null: getEmployeeTransferCaches(userVisit).getPartyResponsibilityTransferCache().getPartyResponsibilityTransfer(partyResponsibility);
    }
    
    public PartyResponsibilityTransfer getPartyResponsibilityTransfer(UserVisit userVisit, PartyResponsibility partyResponsibility) {
        return getEmployeeTransferCaches(userVisit).getPartyResponsibilityTransferCache().getPartyResponsibilityTransfer(partyResponsibility);
    }
    
    public List<PartyResponsibilityTransfer> getPartyResponsibilityTransfers(UserVisit userVisit, List<PartyResponsibility> partyResponsibilities) {
        List<PartyResponsibilityTransfer> partyResponsibilityTransfers = new ArrayList<>(partyResponsibilities.size());
        PartyResponsibilityTransferCache partyResponsibilityTransferCache = getEmployeeTransferCaches(userVisit).getPartyResponsibilityTransferCache();
        
        partyResponsibilities.forEach((partyResponsibility) ->
                partyResponsibilityTransfers.add(partyResponsibilityTransferCache.getPartyResponsibilityTransfer(partyResponsibility))
        );
        
        return partyResponsibilityTransfers;
    }
    
    public List<PartyResponsibilityTransfer> getPartyResponsibilityTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyResponsibilityTransfers(userVisit, getPartyResponsibilitiesByParty(party));
    }
    
    public List<PartyResponsibilityTransfer> getPartyResponsibilityTransfersByResponsibilityType(UserVisit userVisit, ResponsibilityType responsibilityType) {
        return getPartyResponsibilityTransfers(userVisit, getPartyResponsibilitiesByResponsibilityType(responsibilityType));
    }
    
    public void deletePartyResponsibility(PartyResponsibility partyResponsibility, BasePK deletedBy) {
        partyResponsibility.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyResponsibility.getPartyPK(), EventTypes.MODIFY.name(), partyResponsibility.getPrimaryKey(), null, deletedBy);
    }
    
    public void deletePartyResponsibilityByParty(Party party, BasePK deletedBy) {
        List<PartyResponsibility> partyResponsibilities = getPartyResponsibilitiesByPartyForUpdate(party);
        
        partyResponsibilities.forEach((partyResponsibility) -> 
                deletePartyResponsibility(partyResponsibility, deletedBy)
        );
    }
    
    public void deletePartyResponsibilitiesByResponsibilityType(ResponsibilityType responsibilityType, BasePK deletedBy) {
        List<PartyResponsibility> partyResponsibilities = getPartyResponsibilitiesByResponsibilityTypeForUpdate(responsibilityType);
        
        partyResponsibilities.forEach((partyResponsibility) -> 
                deletePartyResponsibility(partyResponsibility, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Skills
    // --------------------------------------------------------------------------------
    
    public PartySkill createPartySkill(Party party, SkillType skillType, BasePK createdBy) {
        PartySkill partySkill = PartySkillFactory.getInstance().create(party, skillType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partySkill.getPrimaryKey(), null, createdBy);
        
        return partySkill;
    }
    
    private PartySkill getPartySkill(Party party, SkillType skillType, EntityPermission entityPermission) {
        PartySkill partySkill;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyskills " +
                        "WHERE parskl_par_partyid = ? AND parskl_skltyp_skilltypeid = ? AND parskl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyskills " +
                        "WHERE parskl_par_partyid = ? AND parskl_skltyp_skilltypeid = ? AND parskl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySkillFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, skillType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySkill = PartySkillFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySkill;
    }
    
    public PartySkill getPartySkill(Party party, SkillType skillType) {
        return getPartySkill(party, skillType, EntityPermission.READ_ONLY);
    }
    
    public PartySkill getPartySkillForUpdate(Party party, SkillType skillType) {
        return getPartySkill(party, skillType, EntityPermission.READ_WRITE);
    }
    
    public PartySkillValue getPartySkillValue(PartySkill partySkill) {
        return partySkill == null? null: partySkill.getPartySkillValue().clone();
    }
    
    public PartySkillValue getPartySkillValueForUpdate(Party party, SkillType skillType) {
        return getPartySkillValue(getPartySkillForUpdate(party, skillType));
    }
    
    private List<PartySkill> getPartySkillsByParty(Party party, EntityPermission entityPermission) {
        List<PartySkill> partySkills;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyskills, skilltypes, skilltypedetails " +
                        "WHERE parskl_par_partyid = ? AND parskl_thrutime = ? " +
                        "AND parskl_skltyp_skilltypeid = skltyp_skilltypeid AND skltyp_lastdetailid = skltypdt_skilltypedetailid " +
                        "ORDER BY skltypdt_sortorder, skltypdt_skilltypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyskills " +
                        "WHERE parskl_par_partyid = ? AND parskl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySkillFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySkills = PartySkillFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySkills;
    }
    
    public List<PartySkill> getPartySkillsByParty(Party party) {
        return getPartySkillsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartySkill> getPartySkillsByPartyForUpdate(Party party) {
        return getPartySkillsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartySkill> getPartySkillsBySkillType(SkillType skillType, EntityPermission entityPermission) {
        List<PartySkill> partySkills;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyskills, parties, partydetails " +
                        "WHERE parskl_skltyp_skilltypeid = ? AND parskl_thrutime = ? " +
                        "AND parskl_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyskills " +
                        "WHERE parskl_skltyp_skilltypeid = ? AND parskl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySkillFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, skillType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySkills = PartySkillFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySkills;
    }
    
    public List<PartySkill> getPartySkillsBySkillType(SkillType skillType) {
        return getPartySkillsBySkillType(skillType, EntityPermission.READ_ONLY);
    }
    
    public List<PartySkill> getPartySkillsBySkillTypeForUpdate(SkillType skillType) {
        return getPartySkillsBySkillType(skillType, EntityPermission.READ_WRITE);
    }
    
    public PartySkillTransfer getPartySkillTransfer(UserVisit userVisit, Party party, SkillType skillType) {
        PartySkill partySkill = getPartySkill(party, skillType);
        
        return partySkill == null? null: getEmployeeTransferCaches(userVisit).getPartySkillTransferCache().getPartySkillTransfer(partySkill);
    }
    
    public PartySkillTransfer getPartySkillTransfer(UserVisit userVisit, PartySkill partySkill) {
        return getEmployeeTransferCaches(userVisit).getPartySkillTransferCache().getPartySkillTransfer(partySkill);
    }
    
    public List<PartySkillTransfer> getPartySkillTransfers(UserVisit userVisit, List<PartySkill> partySkills) {
        List<PartySkillTransfer> partySkillTransfers = new ArrayList<>(partySkills.size());
        PartySkillTransferCache partySkillTransferCache = getEmployeeTransferCaches(userVisit).getPartySkillTransferCache();
        
        partySkills.forEach((partySkill) ->
                partySkillTransfers.add(partySkillTransferCache.getPartySkillTransfer(partySkill))
        );
        
        return partySkillTransfers;
    }
    
    public List<PartySkillTransfer> getPartySkillTransfersByParty(UserVisit userVisit, Party party) {
        return getPartySkillTransfers(userVisit, getPartySkillsByParty(party));
    }
    
    public List<PartySkillTransfer> getPartySkillTransfersBySkillType(UserVisit userVisit, SkillType skillType) {
        return getPartySkillTransfers(userVisit, getPartySkillsBySkillType(skillType));
    }
    
    public void deletePartySkill(PartySkill partySkill, BasePK deletedBy) {
        partySkill.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partySkill.getPartyPK(), EventTypes.MODIFY.name(), partySkill.getPrimaryKey(), null, deletedBy);
    }
    
    public void deletePartySkillByParty(Party party, BasePK deletedBy) {
        List<PartySkill> partySkills = getPartySkillsByPartyForUpdate(party);
        
        partySkills.forEach((partySkill) -> 
                deletePartySkill(partySkill, deletedBy)
        );
    }
    
    public void deletePartySkillsBySkillType(SkillType skillType, BasePK deletedBy) {
        List<PartySkill> partySkills = getPartySkillsBySkillTypeForUpdate(skillType);
        
        partySkills.forEach((partySkill) -> 
                deletePartySkill(partySkill, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Employee Searches
    // --------------------------------------------------------------------------------

    public List<EmployeeResultTransfer> getEmployeeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var employeeResultTransfers = new ArrayList<EmployeeResultTransfer>();
        var includeEmployee = false;

        var options = session.getOptions();
        if(options != null) {
            includeEmployee = options.contains(SearchOptions.EmployeeResultIncludeEmployee);
        }

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            var employeeControl = Session.getModelController(EmployeeControl.class);

            while(rs.next()) {
                var party = getPartyControl().getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                employeeResultTransfers.add(new EmployeeResultTransfer(party.getLastDetail().getPartyName(),
                        includeEmployee ? employeeControl.getEmployeeTransfer(userVisit, party) : null));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return employeeResultTransfers;
    }

    public List<EmployeeObject> getEmployeeObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var employeeObjects = new ArrayList<EmployeeObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var party = getPartyControl().getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                employeeObjects.add(new EmployeeObject(party));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return employeeObjects;
    }

}
