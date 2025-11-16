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

package com.echothree.model.control.employee.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import com.echothree.model.control.employee.server.transfer.EmployeeTransferCaches;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.employee.common.pk.EmployeeTypePK;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDescription;
import com.echothree.model.data.employee.server.entity.Employment;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveReasonDescription;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.LeaveTypeDescription;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.employee.server.entity.PartyResponsibility;
import com.echothree.model.data.employee.server.entity.PartySkill;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.ResponsibilityTypeDescription;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.employee.server.entity.SkillTypeDescription;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationReasonDescription;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.employee.server.entity.TerminationTypeDescription;
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
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class EmployeeControl
        extends BaseModelControl {
    
    /** Creates a new instance of EmployeeControl */
    protected EmployeeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Transfer Caches
    // --------------------------------------------------------------------------------
    
    @Inject
    EmployeeTransferCaches employeeTransferCaches;

    // --------------------------------------------------------------------------------
    //   Responsibility Types
    // --------------------------------------------------------------------------------
    
    public ResponsibilityType createResponsibilityType(String responsibilityTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultResponsibilityType = getDefaultResponsibilityType();
        var defaultFound = defaultResponsibilityType != null;
        
        if(defaultFound && isDefault) {
            var defaultResponsibilityTypeDetailValue = getDefaultResponsibilityTypeDetailValueForUpdate();
            
            defaultResponsibilityTypeDetailValue.setIsDefault(false);
            updateResponsibilityTypeFromValue(defaultResponsibilityTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var responsibilityType = ResponsibilityTypeFactory.getInstance().create();
        var responsibilityTypeDetail = ResponsibilityTypeDetailFactory.getInstance().create(session,
                responsibilityType, responsibilityTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        responsibilityType = ResponsibilityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, responsibilityType.getPrimaryKey());
        responsibilityType.setActiveDetail(responsibilityTypeDetail);
        responsibilityType.setLastDetail(responsibilityTypeDetail);
        responsibilityType.store();
        
        sendEvent(responsibilityType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

            var ps = ResponsibilityTypeFactory.getInstance().prepareStatement(query);
            
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

        var ps = ResponsibilityTypeFactory.getInstance().prepareStatement(query);
        
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

        var ps = ResponsibilityTypeFactory.getInstance().prepareStatement(query);
        
        return ResponsibilityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<ResponsibilityType> getResponsibilityTypes() {
        return getResponsibilityTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ResponsibilityType> getResponsibilityTypesForUpdate() {
        return getResponsibilityTypes(EntityPermission.READ_WRITE);
    }
    
    public ResponsibilityTypeTransfer getResponsibilityTypeTransfer(UserVisit userVisit, ResponsibilityType responsibilityType) {
        return employeeTransferCaches.getResponsibilityTypeTransferCache().getResponsibilityTypeTransfer(userVisit, responsibilityType);
    }
    
    public List<ResponsibilityTypeTransfer> getResponsibilityTypeTransfers(UserVisit userVisit) {
        var responsibilityTypes = getResponsibilityTypes();
        List<ResponsibilityTypeTransfer> responsibilityTypeTransfers = new ArrayList<>(responsibilityTypes.size());
        var responsibilityTypeTransferCache = employeeTransferCaches.getResponsibilityTypeTransferCache();
        
        responsibilityTypes.forEach((responsibilityType) ->
                responsibilityTypeTransfers.add(responsibilityTypeTransferCache.getResponsibilityTypeTransfer(userVisit, responsibilityType))
        );
        
        return responsibilityTypeTransfers;
    }
    
    public ResponsibilityTypeChoicesBean getResponsibilityTypeChoices(String defaultResponsibilityTypeChoice, Language language,
            boolean allowNullChoice) {
        var responsibilityTypes = getResponsibilityTypes();
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
            var responsibilityTypeDetail = responsibilityType.getLastDetail();
            
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
            var responsibilityType = ResponsibilityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     responsibilityTypeDetailValue.getResponsibilityTypePK());
            var responsibilityTypeDetail = responsibilityType.getActiveDetailForUpdate();
            
            responsibilityTypeDetail.setThruTime(session.START_TIME_LONG);
            responsibilityTypeDetail.store();

            var responsibilityTypePK = responsibilityTypeDetail.getResponsibilityTypePK();
            var responsibilityTypeName = responsibilityTypeDetailValue.getResponsibilityTypeName();
            var isDefault = responsibilityTypeDetailValue.getIsDefault();
            var sortOrder = responsibilityTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultResponsibilityType = getDefaultResponsibilityType();
                var defaultFound = defaultResponsibilityType != null && !defaultResponsibilityType.equals(responsibilityType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultResponsibilityTypeDetailValue = getDefaultResponsibilityTypeDetailValueForUpdate();
                    
                    defaultResponsibilityTypeDetailValue.setIsDefault(false);
                    updateResponsibilityTypeFromValue(defaultResponsibilityTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            responsibilityTypeDetail = ResponsibilityTypeDetailFactory.getInstance().create(responsibilityTypePK,
                    responsibilityTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            responsibilityType.setActiveDetail(responsibilityTypeDetail);
            responsibilityType.setLastDetail(responsibilityTypeDetail);
            
            sendEvent(responsibilityTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateResponsibilityTypeFromValue(ResponsibilityTypeDetailValue responsibilityTypeDetailValue, BasePK updatedBy) {
        updateResponsibilityTypeFromValue(responsibilityTypeDetailValue, true, updatedBy);
    }
    
    public void deleteResponsibilityType(ResponsibilityType responsibilityType, BasePK deletedBy) {
        deleteResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType, deletedBy);
        deletePartyResponsibilitiesByResponsibilityType(responsibilityType, deletedBy);

        var responsibilityTypeDetail = responsibilityType.getLastDetailForUpdate();
        responsibilityTypeDetail.setThruTime(session.START_TIME_LONG);
        responsibilityType.setActiveDetail(null);
        responsibilityType.store();
        
        // Check for default, and pick one if necessary
        var defaultResponsibilityType = getDefaultResponsibilityType();
        if(defaultResponsibilityType == null) {
            var responsibilityTypes = getResponsibilityTypesForUpdate();
            
            if(!responsibilityTypes.isEmpty()) {
                Iterator iter = responsibilityTypes.iterator();
                if(iter.hasNext()) {
                    defaultResponsibilityType = (ResponsibilityType)iter.next();
                }
                var responsibilityTypeDetailValue = Objects.requireNonNull(defaultResponsibilityType).getLastDetailForUpdate().getResponsibilityTypeDetailValue().clone();
                
                responsibilityTypeDetailValue.setIsDefault(true);
                updateResponsibilityTypeFromValue(responsibilityTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(responsibilityType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ResponsibilityTypeDescription createResponsibilityTypeDescription(ResponsibilityType responsibilityType,
            Language language, String description, BasePK createdBy) {
        var responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().create(session,
                responsibilityType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(responsibilityType.getPrimaryKey(), EventTypes.MODIFY, responsibilityTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = ResponsibilityTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = ResponsibilityTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var responsibilityTypeDescription = getResponsibilityTypeDescription(responsibilityType, language);
        
        if(responsibilityTypeDescription == null && !language.getIsDefault()) {
            responsibilityTypeDescription = getResponsibilityTypeDescription(responsibilityType, partyControl.getDefaultLanguage());
        }
        
        if(responsibilityTypeDescription == null) {
            description = responsibilityType.getLastDetail().getResponsibilityTypeName();
        } else {
            description = responsibilityTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public ResponsibilityTypeDescriptionTransfer getResponsibilityTypeDescriptionTransfer(UserVisit userVisit, ResponsibilityTypeDescription responsibilityTypeDescription) {
        return employeeTransferCaches.getResponsibilityTypeDescriptionTransferCache().getResponsibilityTypeDescriptionTransfer(userVisit, responsibilityTypeDescription);
    }
    
    public List<ResponsibilityTypeDescriptionTransfer> getResponsibilityTypeDescriptionTransfers(UserVisit userVisit, ResponsibilityType responsibilityType) {
        var responsibilityTypeDescriptions = getResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType);
        List<ResponsibilityTypeDescriptionTransfer> responsibilityTypeDescriptionTransfers = new ArrayList<>(responsibilityTypeDescriptions.size());
        var responsibilityTypeDescriptionTransferCache = employeeTransferCaches.getResponsibilityTypeDescriptionTransferCache();
        
        responsibilityTypeDescriptions.forEach((responsibilityTypeDescription) ->
                responsibilityTypeDescriptionTransfers.add(responsibilityTypeDescriptionTransferCache.getResponsibilityTypeDescriptionTransfer(userVisit, responsibilityTypeDescription))
        );
        
        return responsibilityTypeDescriptionTransfers;
    }
    
    public void updateResponsibilityTypeDescriptionFromValue(ResponsibilityTypeDescriptionValue responsibilityTypeDescriptionValue, BasePK updatedBy) {
        if(responsibilityTypeDescriptionValue.hasBeenModified()) {
            var responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, responsibilityTypeDescriptionValue.getPrimaryKey());
            
            responsibilityTypeDescription.setThruTime(session.START_TIME_LONG);
            responsibilityTypeDescription.store();

            var responsibilityType = responsibilityTypeDescription.getResponsibilityType();
            var language = responsibilityTypeDescription.getLanguage();
            var description = responsibilityTypeDescriptionValue.getDescription();
            
            responsibilityTypeDescription = ResponsibilityTypeDescriptionFactory.getInstance().create(responsibilityType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(responsibilityType.getPrimaryKey(), EventTypes.MODIFY, responsibilityTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteResponsibilityTypeDescription(ResponsibilityTypeDescription responsibilityTypeDescription, BasePK deletedBy) {
        responsibilityTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(responsibilityTypeDescription.getResponsibilityTypePK(), EventTypes.MODIFY, responsibilityTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteResponsibilityTypeDescriptionsByResponsibilityType(ResponsibilityType responsibilityType, BasePK deletedBy) {
        var responsibilityTypeDescriptions = getResponsibilityTypeDescriptionsByResponsibilityTypeForUpdate(responsibilityType);
        
        responsibilityTypeDescriptions.forEach((responsibilityTypeDescription) -> 
                deleteResponsibilityTypeDescription(responsibilityTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Skill Types
    // --------------------------------------------------------------------------------
    
    public SkillType createSkillType(String skillTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSkillType = getDefaultSkillType();
        var defaultFound = defaultSkillType != null;
        
        if(defaultFound && isDefault) {
            var defaultSkillTypeDetailValue = getDefaultSkillTypeDetailValueForUpdate();
            
            defaultSkillTypeDetailValue.setIsDefault(false);
            updateSkillTypeFromValue(defaultSkillTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var skillType = SkillTypeFactory.getInstance().create();
        var skillTypeDetail = SkillTypeDetailFactory.getInstance().create(session,
                skillType, skillTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        skillType = SkillTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, skillType.getPrimaryKey());
        skillType.setActiveDetail(skillTypeDetail);
        skillType.setLastDetail(skillTypeDetail);
        skillType.store();
        
        sendEvent(skillType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

            var ps = SkillTypeFactory.getInstance().prepareStatement(query);
            
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

        var ps = SkillTypeFactory.getInstance().prepareStatement(query);
        
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

        var ps = SkillTypeFactory.getInstance().prepareStatement(query);
        
        return SkillTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<SkillType> getSkillTypes() {
        return getSkillTypes(EntityPermission.READ_ONLY);
    }
    
    public List<SkillType> getSkillTypesForUpdate() {
        return getSkillTypes(EntityPermission.READ_WRITE);
    }
    
    public SkillTypeTransfer getSkillTypeTransfer(UserVisit userVisit, SkillType skillType) {
        return employeeTransferCaches.getSkillTypeTransferCache().getSkillTypeTransfer(userVisit, skillType);
    }
    
    public List<SkillTypeTransfer> getSkillTypeTransfers(UserVisit userVisit) {
        var skillTypes = getSkillTypes();
        List<SkillTypeTransfer> skillTypeTransfers = new ArrayList<>(skillTypes.size());
        var skillTypeTransferCache = employeeTransferCaches.getSkillTypeTransferCache();
        
        skillTypes.forEach((skillType) ->
                skillTypeTransfers.add(skillTypeTransferCache.getSkillTypeTransfer(userVisit, skillType))
        );
        
        return skillTypeTransfers;
    }
    
    public SkillTypeChoicesBean getSkillTypeChoices(String defaultSkillTypeChoice, Language language, boolean allowNullChoice) {
        var skillTypes = getSkillTypes();
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
            var skillTypeDetail = skillType.getLastDetail();
            
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
            var skillType = SkillTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     skillTypeDetailValue.getSkillTypePK());
            var skillTypeDetail = skillType.getActiveDetailForUpdate();
            
            skillTypeDetail.setThruTime(session.START_TIME_LONG);
            skillTypeDetail.store();

            var skillTypePK = skillTypeDetail.getSkillTypePK();
            var skillTypeName = skillTypeDetailValue.getSkillTypeName();
            var isDefault = skillTypeDetailValue.getIsDefault();
            var sortOrder = skillTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultSkillType = getDefaultSkillType();
                var defaultFound = defaultSkillType != null && !defaultSkillType.equals(skillType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSkillTypeDetailValue = getDefaultSkillTypeDetailValueForUpdate();
                    
                    defaultSkillTypeDetailValue.setIsDefault(false);
                    updateSkillTypeFromValue(defaultSkillTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            skillTypeDetail = SkillTypeDetailFactory.getInstance().create(skillTypePK,
                    skillTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            skillType.setActiveDetail(skillTypeDetail);
            skillType.setLastDetail(skillTypeDetail);
            
            sendEvent(skillTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateSkillTypeFromValue(SkillTypeDetailValue skillTypeDetailValue, BasePK updatedBy) {
        updateSkillTypeFromValue(skillTypeDetailValue, true, updatedBy);
    }
    
    public void deleteSkillType(SkillType skillType, BasePK deletedBy) {
        deleteSkillTypeDescriptionsBySkillType(skillType, deletedBy);
        deletePartySkillsBySkillType(skillType, deletedBy);

        var skillTypeDetail = skillType.getLastDetailForUpdate();
        skillTypeDetail.setThruTime(session.START_TIME_LONG);
        skillType.setActiveDetail(null);
        skillType.store();
        
        // Check for default, and pick one if necessary
        var defaultSkillType = getDefaultSkillType();
        if(defaultSkillType == null) {
            var skillTypes = getSkillTypesForUpdate();
            
            if(!skillTypes.isEmpty()) {
                Iterator iter = skillTypes.iterator();
                if(iter.hasNext()) {
                    defaultSkillType = (SkillType)iter.next();
                }
                var skillTypeDetailValue = Objects.requireNonNull(defaultSkillType).getLastDetailForUpdate().getSkillTypeDetailValue().clone();
                
                skillTypeDetailValue.setIsDefault(true);
                updateSkillTypeFromValue(skillTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(skillType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Skill Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SkillTypeDescription createSkillTypeDescription(SkillType skillType,
            Language language, String description, BasePK createdBy) {
        var skillTypeDescription = SkillTypeDescriptionFactory.getInstance().create(session,
                skillType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(skillType.getPrimaryKey(), EventTypes.MODIFY, skillTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = SkillTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = SkillTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var skillTypeDescription = getSkillTypeDescription(skillType, language);
        
        if(skillTypeDescription == null && !language.getIsDefault()) {
            skillTypeDescription = getSkillTypeDescription(skillType, partyControl.getDefaultLanguage());
        }
        
        if(skillTypeDescription == null) {
            description = skillType.getLastDetail().getSkillTypeName();
        } else {
            description = skillTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public SkillTypeDescriptionTransfer getSkillTypeDescriptionTransfer(UserVisit userVisit, SkillTypeDescription skillTypeDescription) {
        return employeeTransferCaches.getSkillTypeDescriptionTransferCache().getSkillTypeDescriptionTransfer(userVisit, skillTypeDescription);
    }
    
    public List<SkillTypeDescriptionTransfer> getSkillTypeDescriptionTransfers(UserVisit userVisit, SkillType skillType) {
        var skillTypeDescriptions = getSkillTypeDescriptionsBySkillType(skillType);
        List<SkillTypeDescriptionTransfer> skillTypeDescriptionTransfers = new ArrayList<>(skillTypeDescriptions.size());
        var skillTypeDescriptionTransferCache = employeeTransferCaches.getSkillTypeDescriptionTransferCache();
        
        skillTypeDescriptions.forEach((skillTypeDescription) ->
                skillTypeDescriptionTransfers.add(skillTypeDescriptionTransferCache.getSkillTypeDescriptionTransfer(userVisit, skillTypeDescription))
        );
        
        return skillTypeDescriptionTransfers;
    }
    
    public void updateSkillTypeDescriptionFromValue(SkillTypeDescriptionValue skillTypeDescriptionValue, BasePK updatedBy) {
        if(skillTypeDescriptionValue.hasBeenModified()) {
            var skillTypeDescription = SkillTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, skillTypeDescriptionValue.getPrimaryKey());
            
            skillTypeDescription.setThruTime(session.START_TIME_LONG);
            skillTypeDescription.store();

            var skillType = skillTypeDescription.getSkillType();
            var language = skillTypeDescription.getLanguage();
            var description = skillTypeDescriptionValue.getDescription();
            
            skillTypeDescription = SkillTypeDescriptionFactory.getInstance().create(skillType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(skillType.getPrimaryKey(), EventTypes.MODIFY, skillTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSkillTypeDescription(SkillTypeDescription skillTypeDescription, BasePK deletedBy) {
        skillTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(skillTypeDescription.getSkillTypePK(), EventTypes.MODIFY, skillTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSkillTypeDescriptionsBySkillType(SkillType skillType, BasePK deletedBy) {
        var skillTypeDescriptions = getSkillTypeDescriptionsBySkillTypeForUpdate(skillType);
        
        skillTypeDescriptions.forEach((skillTypeDescription) -> 
                deleteSkillTypeDescription(skillTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Leave Types
    // --------------------------------------------------------------------------------

    public LeaveType createLeaveType(String leaveTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLeaveType = getDefaultLeaveType();
        var defaultFound = defaultLeaveType != null;

        if(defaultFound && isDefault) {
            var defaultLeaveTypeDetailValue = getDefaultLeaveTypeDetailValueForUpdate();

            defaultLeaveTypeDetailValue.setIsDefault(false);
            updateLeaveTypeFromValue(defaultLeaveTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var leaveType = LeaveTypeFactory.getInstance().create();
        var leaveTypeDetail = LeaveTypeDetailFactory.getInstance().create(leaveType,
                leaveTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        leaveType = LeaveTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                leaveType.getPrimaryKey());
        leaveType.setActiveDetail(leaveTypeDetail);
        leaveType.setLastDetail(leaveTypeDetail);
        leaveType.store();

        sendEvent(leaveType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

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
        return employeeTransferCaches.getLeaveTypeTransferCache().getLeaveTypeTransfer(userVisit, leaveType);
    }

    public List<LeaveTypeTransfer> getLeaveTypeTransfers(UserVisit userVisit, Collection<LeaveType> leaveTypes) {
        List<LeaveTypeTransfer> leaveTypeTransfers = new ArrayList<>(leaveTypes.size());
        var leaveTypeTransferCache = employeeTransferCaches.getLeaveTypeTransferCache();

        leaveTypes.forEach((leaveType) ->
                leaveTypeTransfers.add(leaveTypeTransferCache.getLeaveTypeTransfer(userVisit, leaveType))
        );

        return leaveTypeTransfers;
    }

    public List<LeaveTypeTransfer> getLeaveTypeTransfers(UserVisit userVisit) {
        return getLeaveTypeTransfers(userVisit, getLeaveTypes());
    }

    public LeaveTypeChoicesBean getLeaveTypeChoices(String defaultLeaveTypeChoice, Language language, boolean allowNullChoice) {
        var leaveTypes = getLeaveTypes();
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
            var leaveTypeDetail = leaveType.getLastDetail();

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
            var leaveType = LeaveTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveTypeDetailValue.getLeaveTypePK());
            var leaveTypeDetail = leaveType.getActiveDetailForUpdate();

            leaveTypeDetail.setThruTime(session.START_TIME_LONG);
            leaveTypeDetail.store();

            var leaveTypePK = leaveTypeDetail.getLeaveTypePK(); // Not updated
            var leaveTypeName = leaveTypeDetailValue.getLeaveTypeName();
            var isDefault = leaveTypeDetailValue.getIsDefault();
            var sortOrder = leaveTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLeaveType = getDefaultLeaveType();
                var defaultFound = defaultLeaveType != null && !defaultLeaveType.equals(leaveType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLeaveTypeDetailValue = getDefaultLeaveTypeDetailValueForUpdate();

                    defaultLeaveTypeDetailValue.setIsDefault(false);
                    updateLeaveTypeFromValue(defaultLeaveTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            leaveTypeDetail = LeaveTypeDetailFactory.getInstance().create(leaveTypePK, leaveTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            leaveType.setActiveDetail(leaveTypeDetail);
            leaveType.setLastDetail(leaveTypeDetail);

            sendEvent(leaveTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLeaveTypeFromValue(LeaveTypeDetailValue leaveTypeDetailValue, BasePK updatedBy) {
        updateLeaveTypeFromValue(leaveTypeDetailValue, true, updatedBy);
    }

    public void deleteLeaveType(LeaveType leaveType, BasePK deletedBy) {
        deleteLeaveTypeDescriptionsByLeaveType(leaveType, deletedBy);

        var leaveTypeDetail = leaveType.getLastDetailForUpdate();
        leaveTypeDetail.setThruTime(session.START_TIME_LONG);
        leaveType.setActiveDetail(null);
        leaveType.store();

        // Check for default, and pick one if necessary
        var defaultLeaveType = getDefaultLeaveType();
        if(defaultLeaveType == null) {
            var leaveTypes = getLeaveTypesForUpdate();

            if(!leaveTypes.isEmpty()) {
                var iter = leaveTypes.iterator();
                if(iter.hasNext()) {
                    defaultLeaveType = iter.next();
                }
                var leaveTypeDetailValue = Objects.requireNonNull(defaultLeaveType).getLastDetailForUpdate().getLeaveTypeDetailValue().clone();

                leaveTypeDetailValue.setIsDefault(true);
                updateLeaveTypeFromValue(leaveTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(leaveType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Leave Type Descriptions
    // --------------------------------------------------------------------------------

    public LeaveTypeDescription createLeaveTypeDescription(LeaveType leaveType,
            Language language, String description, BasePK createdBy) {
        var leaveTypeDescription = LeaveTypeDescriptionFactory.getInstance().create(leaveType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(leaveType.getPrimaryKey(), EventTypes.MODIFY, leaveTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        var leaveTypeDescription = getLeaveTypeDescription(leaveType, language);

        if(leaveTypeDescription == null && !language.getIsDefault()) {
            leaveTypeDescription = getLeaveTypeDescription(leaveType, partyControl.getDefaultLanguage());
        }

        if(leaveTypeDescription == null) {
            description = leaveType.getLastDetail().getLeaveTypeName();
        } else {
            description = leaveTypeDescription.getDescription();
        }

        return description;
    }

    public LeaveTypeDescriptionTransfer getLeaveTypeDescriptionTransfer(UserVisit userVisit, LeaveTypeDescription leaveTypeDescription) {
        return employeeTransferCaches.getLeaveTypeDescriptionTransferCache().getLeaveTypeDescriptionTransfer(userVisit, leaveTypeDescription);
    }

    public List<LeaveTypeDescriptionTransfer> getLeaveTypeDescriptionTransfersByLeaveType(UserVisit userVisit, LeaveType leaveType) {
        var leaveTypeDescriptions = getLeaveTypeDescriptionsByLeaveType(leaveType);
        List<LeaveTypeDescriptionTransfer> leaveTypeDescriptionTransfers = new ArrayList<>(leaveTypeDescriptions.size());
        var leaveTypeDescriptionTransferCache = employeeTransferCaches.getLeaveTypeDescriptionTransferCache();

        leaveTypeDescriptions.forEach((leaveTypeDescription) ->
                leaveTypeDescriptionTransfers.add(leaveTypeDescriptionTransferCache.getLeaveTypeDescriptionTransfer(userVisit, leaveTypeDescription))
        );

        return leaveTypeDescriptionTransfers;
    }

    public void updateLeaveTypeDescriptionFromValue(LeaveTypeDescriptionValue leaveTypeDescriptionValue, BasePK updatedBy) {
        if(leaveTypeDescriptionValue.hasBeenModified()) {
            var leaveTypeDescription = LeaveTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    leaveTypeDescriptionValue.getPrimaryKey());

            leaveTypeDescription.setThruTime(session.START_TIME_LONG);
            leaveTypeDescription.store();

            var leaveType = leaveTypeDescription.getLeaveType();
            var language = leaveTypeDescription.getLanguage();
            var description = leaveTypeDescriptionValue.getDescription();

            leaveTypeDescription = LeaveTypeDescriptionFactory.getInstance().create(leaveType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(leaveType.getPrimaryKey(), EventTypes.MODIFY, leaveTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLeaveTypeDescription(LeaveTypeDescription leaveTypeDescription, BasePK deletedBy) {
        leaveTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(leaveTypeDescription.getLeaveTypePK(), EventTypes.MODIFY, leaveTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLeaveTypeDescriptionsByLeaveType(LeaveType leaveType, BasePK deletedBy) {
        var leaveTypeDescriptions = getLeaveTypeDescriptionsByLeaveTypeForUpdate(leaveType);

        leaveTypeDescriptions.forEach((leaveTypeDescription) -> 
                deleteLeaveTypeDescription(leaveTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Leave Reasons
    // --------------------------------------------------------------------------------

    public LeaveReason createLeaveReason(String leaveReasonName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLeaveReason = getDefaultLeaveReason();
        var defaultFound = defaultLeaveReason != null;

        if(defaultFound && isDefault) {
            var defaultLeaveReasonDetailValue = getDefaultLeaveReasonDetailValueForUpdate();

            defaultLeaveReasonDetailValue.setIsDefault(false);
            updateLeaveReasonFromValue(defaultLeaveReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var leaveReason = LeaveReasonFactory.getInstance().create();
        var leaveReasonDetail = LeaveReasonDetailFactory.getInstance().create(leaveReason,
                leaveReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        leaveReason = LeaveReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                leaveReason.getPrimaryKey());
        leaveReason.setActiveDetail(leaveReasonDetail);
        leaveReason.setLastDetail(leaveReasonDetail);
        leaveReason.store();

        sendEvent(leaveReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

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
        return employeeTransferCaches.getLeaveReasonTransferCache().getLeaveReasonTransfer(userVisit, leaveReason);
    }

    public List<LeaveReasonTransfer> getLeaveReasonTransfers(UserVisit userVisit, Collection<LeaveReason> leaveReasons) {
        List<LeaveReasonTransfer> leaveReasonTransfers = new ArrayList<>(leaveReasons.size());
        var leaveReasonTransferCache = employeeTransferCaches.getLeaveReasonTransferCache();

        leaveReasons.forEach((leaveReason) ->
                leaveReasonTransfers.add(leaveReasonTransferCache.getLeaveReasonTransfer(userVisit, leaveReason))
        );

        return leaveReasonTransfers;
    }

    public List<LeaveReasonTransfer> getLeaveReasonTransfers(UserVisit userVisit) {
        return getLeaveReasonTransfers(userVisit, getLeaveReasons());
    }

    public LeaveReasonChoicesBean getLeaveReasonChoices(String defaultLeaveReasonChoice, Language language, boolean allowNullChoice) {
        var leaveReasons = getLeaveReasons();
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
            var leaveReasonDetail = leaveReason.getLastDetail();

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
            var leaveReason = LeaveReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveReasonDetailValue.getLeaveReasonPK());
            var leaveReasonDetail = leaveReason.getActiveDetailForUpdate();

            leaveReasonDetail.setThruTime(session.START_TIME_LONG);
            leaveReasonDetail.store();

            var leaveReasonPK = leaveReasonDetail.getLeaveReasonPK(); // Not updated
            var leaveReasonName = leaveReasonDetailValue.getLeaveReasonName();
            var isDefault = leaveReasonDetailValue.getIsDefault();
            var sortOrder = leaveReasonDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLeaveReason = getDefaultLeaveReason();
                var defaultFound = defaultLeaveReason != null && !defaultLeaveReason.equals(leaveReason);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLeaveReasonDetailValue = getDefaultLeaveReasonDetailValueForUpdate();

                    defaultLeaveReasonDetailValue.setIsDefault(false);
                    updateLeaveReasonFromValue(defaultLeaveReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            leaveReasonDetail = LeaveReasonDetailFactory.getInstance().create(leaveReasonPK, leaveReasonName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            leaveReason.setActiveDetail(leaveReasonDetail);
            leaveReason.setLastDetail(leaveReasonDetail);

            sendEvent(leaveReasonPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLeaveReasonFromValue(LeaveReasonDetailValue leaveReasonDetailValue, BasePK updatedBy) {
        updateLeaveReasonFromValue(leaveReasonDetailValue, true, updatedBy);
    }

    public void deleteLeaveReason(LeaveReason leaveReason, BasePK deletedBy) {
        deleteLeaveReasonDescriptionsByLeaveReason(leaveReason, deletedBy);

        var leaveReasonDetail = leaveReason.getLastDetailForUpdate();
        leaveReasonDetail.setThruTime(session.START_TIME_LONG);
        leaveReason.setActiveDetail(null);
        leaveReason.store();

        // Check for default, and pick one if necessary
        var defaultLeaveReason = getDefaultLeaveReason();
        if(defaultLeaveReason == null) {
            var leaveReasons = getLeaveReasonsForUpdate();

            if(!leaveReasons.isEmpty()) {
                var iter = leaveReasons.iterator();
                if(iter.hasNext()) {
                    defaultLeaveReason = iter.next();
                }
                var leaveReasonDetailValue = Objects.requireNonNull(defaultLeaveReason).getLastDetailForUpdate().getLeaveReasonDetailValue().clone();

                leaveReasonDetailValue.setIsDefault(true);
                updateLeaveReasonFromValue(leaveReasonDetailValue, false, deletedBy);
            }
        }

        sendEvent(leaveReason.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // --------------------------------------------------------------------------------

    public LeaveReasonDescription createLeaveReasonDescription(LeaveReason leaveReason,
            Language language, String description, BasePK createdBy) {
        var leaveReasonDescription = LeaveReasonDescriptionFactory.getInstance().create(leaveReason,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(leaveReason.getPrimaryKey(), EventTypes.MODIFY, leaveReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        var leaveReasonDescription = getLeaveReasonDescription(leaveReason, language);

        if(leaveReasonDescription == null && !language.getIsDefault()) {
            leaveReasonDescription = getLeaveReasonDescription(leaveReason, partyControl.getDefaultLanguage());
        }

        if(leaveReasonDescription == null) {
            description = leaveReason.getLastDetail().getLeaveReasonName();
        } else {
            description = leaveReasonDescription.getDescription();
        }

        return description;
    }

    public LeaveReasonDescriptionTransfer getLeaveReasonDescriptionTransfer(UserVisit userVisit, LeaveReasonDescription leaveReasonDescription) {
        return employeeTransferCaches.getLeaveReasonDescriptionTransferCache().getLeaveReasonDescriptionTransfer(userVisit, leaveReasonDescription);
    }

    public List<LeaveReasonDescriptionTransfer> getLeaveReasonDescriptionTransfersByLeaveReason(UserVisit userVisit, LeaveReason leaveReason) {
        var leaveReasonDescriptions = getLeaveReasonDescriptionsByLeaveReason(leaveReason);
        List<LeaveReasonDescriptionTransfer> leaveReasonDescriptionTransfers = new ArrayList<>(leaveReasonDescriptions.size());
        var leaveReasonDescriptionTransferCache = employeeTransferCaches.getLeaveReasonDescriptionTransferCache();

        leaveReasonDescriptions.forEach((leaveReasonDescription) ->
                leaveReasonDescriptionTransfers.add(leaveReasonDescriptionTransferCache.getLeaveReasonDescriptionTransfer(userVisit, leaveReasonDescription))
        );

        return leaveReasonDescriptionTransfers;
    }

    public void updateLeaveReasonDescriptionFromValue(LeaveReasonDescriptionValue leaveReasonDescriptionValue, BasePK updatedBy) {
        if(leaveReasonDescriptionValue.hasBeenModified()) {
            var leaveReasonDescription = LeaveReasonDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    leaveReasonDescriptionValue.getPrimaryKey());

            leaveReasonDescription.setThruTime(session.START_TIME_LONG);
            leaveReasonDescription.store();

            var leaveReason = leaveReasonDescription.getLeaveReason();
            var language = leaveReasonDescription.getLanguage();
            var description = leaveReasonDescriptionValue.getDescription();

            leaveReasonDescription = LeaveReasonDescriptionFactory.getInstance().create(leaveReason, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(leaveReason.getPrimaryKey(), EventTypes.MODIFY, leaveReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLeaveReasonDescription(LeaveReasonDescription leaveReasonDescription, BasePK deletedBy) {
        leaveReasonDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(leaveReasonDescription.getLeaveReasonPK(), EventTypes.MODIFY, leaveReasonDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLeaveReasonDescriptionsByLeaveReason(LeaveReason leaveReason, BasePK deletedBy) {
        var leaveReasonDescriptions = getLeaveReasonDescriptionsByLeaveReasonForUpdate(leaveReason);

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
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.LEAVE.name());
        var leaveName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createLeave(leaveName, party, companyParty, leaveType, leaveReason, startTime, endTime, totalTime, createdBy);
    }

    public Leave createLeave(String leaveName, Party party, Party companyParty, LeaveType leaveType, LeaveReason leaveReason, Long startTime, Long endTime,
            Long totalTime, BasePK createdBy) {
        var leave = LeaveFactory.getInstance().create();
        var leaveDetail = LeaveDetailFactory.getInstance().create(leave, leaveName, party, companyParty, leaveType, leaveReason, startTime, endTime,
                totalTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        leave = LeaveFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, leave.getPrimaryKey());
        leave.setActiveDetail(leaveDetail);
        leave.setLastDetail(leaveDetail);
        leave.store();

        var leavePK = leave.getPrimaryKey();
        sendEvent(leavePK, EventTypes.CREATE, null, null, createdBy);
        sendEvent(party.getPrimaryKey(), EventTypes.TOUCH, leavePK, EventTypes.CREATE, createdBy);

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
        var leaveStatusChoicesBean = new LeaveStatusChoicesBean();

        if(leave == null) {
            workflowControl.getWorkflowEntranceChoices(leaveStatusChoicesBean, defaultLeaveStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(LeaveStatusConstants.Workflow_LEAVE_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(leave.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(LeaveStatusConstants.Workflow_LEAVE_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(leaveStatusChoicesBean, defaultLeaveStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return leaveStatusChoicesBean;
    }

    public void setLeaveStatus(ExecutionErrorAccumulator eea, Leave leave, String leaveStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(leave);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(LeaveStatusConstants.Workflow_LEAVE_STATUS,
                entityInstance);
        var workflowDestination = leaveStatusChoice == null ? null
                : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), leaveStatusChoice);

        if(workflowDestination != null || leaveStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownLeaveStatusChoice.name(), leaveStatusChoice);
        }
    }

    public LeaveTransfer getLeaveTransfer(UserVisit userVisit, Leave leave) {
        return employeeTransferCaches.getLeaveTransferCache().getLeaveTransfer(userVisit, leave);
    }

    public List<LeaveTransfer> getLeaveTransfers(UserVisit userVisit, Collection<Leave> leaves) {
        List<LeaveTransfer> leaveTransfers = new ArrayList<>(leaves.size());
        var leaveTransferCache = employeeTransferCaches.getLeaveTransferCache();

        leaves.forEach((leave) ->
                leaveTransfers.add(leaveTransferCache.getLeaveTransfer(userVisit, leave))
        );

        return leaveTransfers;
    }

    public List<LeaveTransfer> getLeaveTransfersByParty(UserVisit userVisit, Party party) {
        return getLeaveTransfers(userVisit, getLeavesByParty(party));
    }

    public void updateLeaveFromValue(LeaveDetailValue leaveDetailValue, BasePK updatedBy) {
        if(leaveDetailValue.hasBeenModified()) {
            var leave = LeaveFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveDetailValue.getLeavePK());
            var leaveDetail = leave.getActiveDetailForUpdate();

            leaveDetail.setThruTime(session.START_TIME_LONG);
            leaveDetail.store();

            var leavePK = leaveDetail.getLeavePK(); // Not updated
            var leaveName = leaveDetail.getLeaveName(); // Not updated
            var partyPK = leaveDetail.getPartyPK(); // Not updated
            var companyPartyPK = leaveDetailValue.getCompanyPartyPK();
            var startTime = leaveDetailValue.getStartTime();
            var endTime = leaveDetailValue.getEndTime();
            var totalTime = leaveDetailValue.getTotalTime();
            var leaveTypePK = leaveDetailValue.getLeaveTypePK();
            var leaveReasonPK = leaveDetailValue.getLeaveReasonPK();

            leaveDetail = LeaveDetailFactory.getInstance().create(leavePK, leaveName, partyPK, companyPartyPK, leaveTypePK, leaveReasonPK, startTime,
                    endTime, totalTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            leave.setActiveDetail(leaveDetail);
            leave.setLastDetail(leaveDetail);

            sendEvent(leavePK, EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(partyPK, EventTypes.TOUCH, leavePK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLeave(Leave leave, BasePK deletedBy) {
        var leaveDetail = leave.getLastDetailForUpdate();
        leaveDetail.setThruTime(session.START_TIME_LONG);
        leaveDetail.store();
        leave.setActiveDetail(null);

        var leavePK = leave.getPrimaryKey();
        sendEvent(leavePK, EventTypes.DELETE, null, null, deletedBy);
        sendEvent(leaveDetail.getParty().getPrimaryKey(), EventTypes.TOUCH, leavePK, EventTypes.DELETE, deletedBy);
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
        var defaultTerminationReason = getDefaultTerminationReason();
        var defaultFound = defaultTerminationReason != null;
        
        if(defaultFound && isDefault) {
            var defaultTerminationReasonDetailValue = getDefaultTerminationReasonDetailValueForUpdate();
            
            defaultTerminationReasonDetailValue.setIsDefault(false);
            updateTerminationReasonFromValue(defaultTerminationReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var terminationReason = TerminationReasonFactory.getInstance().create();
        var terminationReasonDetail = TerminationReasonDetailFactory.getInstance().create(session,
                terminationReason, terminationReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        terminationReason = TerminationReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationReason.getPrimaryKey());
        terminationReason.setActiveDetail(terminationReasonDetail);
        terminationReason.setLastDetail(terminationReasonDetail);
        terminationReason.store();
        
        sendEvent(terminationReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

            var ps = TerminationReasonFactory.getInstance().prepareStatement(query);
            
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

        var ps = TerminationReasonFactory.getInstance().prepareStatement(query);
        
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

        var ps = TerminationReasonFactory.getInstance().prepareStatement(query);
        
        return TerminationReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TerminationReason> getTerminationReasons() {
        return getTerminationReasons(EntityPermission.READ_ONLY);
    }
    
    public List<TerminationReason> getTerminationReasonsForUpdate() {
        return getTerminationReasons(EntityPermission.READ_WRITE);
    }
    
    public TerminationReasonTransfer getTerminationReasonTransfer(UserVisit userVisit, TerminationReason terminationReason) {
        return employeeTransferCaches.getTerminationReasonTransferCache().getTerminationReasonTransfer(userVisit, terminationReason);
    }
    
    public List<TerminationReasonTransfer> getTerminationReasonTransfers(UserVisit userVisit) {
        var terminationReasons = getTerminationReasons();
        List<TerminationReasonTransfer> terminationReasonTransfers = new ArrayList<>(terminationReasons.size());
        var terminationReasonTransferCache = employeeTransferCaches.getTerminationReasonTransferCache();
        
        terminationReasons.forEach((terminationReason) ->
                terminationReasonTransfers.add(terminationReasonTransferCache.getTerminationReasonTransfer(userVisit, terminationReason))
        );
        
        return terminationReasonTransfers;
    }
    
    public TerminationReasonChoicesBean getTerminationReasonChoices(String defaultTerminationReasonChoice, Language language,
            boolean allowNullChoice) {
        var terminationReasons = getTerminationReasons();
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
            var terminationReasonDetail = terminationReason.getLastDetail();
            
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
            var terminationReason = TerminationReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     terminationReasonDetailValue.getTerminationReasonPK());
            var terminationReasonDetail = terminationReason.getActiveDetailForUpdate();
            
            terminationReasonDetail.setThruTime(session.START_TIME_LONG);
            terminationReasonDetail.store();

            var terminationReasonPK = terminationReasonDetail.getTerminationReasonPK();
            var terminationReasonName = terminationReasonDetailValue.getTerminationReasonName();
            var isDefault = terminationReasonDetailValue.getIsDefault();
            var sortOrder = terminationReasonDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultTerminationReason = getDefaultTerminationReason();
                var defaultFound = defaultTerminationReason != null && !defaultTerminationReason.equals(terminationReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTerminationReasonDetailValue = getDefaultTerminationReasonDetailValueForUpdate();
                    
                    defaultTerminationReasonDetailValue.setIsDefault(false);
                    updateTerminationReasonFromValue(defaultTerminationReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            terminationReasonDetail = TerminationReasonDetailFactory.getInstance().create(terminationReasonPK,
                    terminationReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            terminationReason.setActiveDetail(terminationReasonDetail);
            terminationReason.setLastDetail(terminationReasonDetail);
            
            sendEvent(terminationReasonPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateTerminationReasonFromValue(TerminationReasonDetailValue terminationReasonDetailValue, BasePK updatedBy) {
        updateTerminationReasonFromValue(terminationReasonDetailValue, true, updatedBy);
    }
    
    public void deleteTerminationReason(TerminationReason terminationReason, BasePK deletedBy) {
        deleteTerminationReasonDescriptionsByTerminationReason(terminationReason, deletedBy);
        deleteEmploymentsByTerminationReason(terminationReason, deletedBy);

        var terminationReasonDetail = terminationReason.getLastDetailForUpdate();
        terminationReasonDetail.setThruTime(session.START_TIME_LONG);
        terminationReason.setActiveDetail(null);
        terminationReason.store();
        
        // Check for default, and pick one if necessary
        var defaultTerminationReason = getDefaultTerminationReason();
        if(defaultTerminationReason == null) {
            var terminationReasons = getTerminationReasonsForUpdate();
            
            if(!terminationReasons.isEmpty()) {
                Iterator iter = terminationReasons.iterator();
                if(iter.hasNext()) {
                    defaultTerminationReason = (TerminationReason)iter.next();
                }
                var terminationReasonDetailValue = Objects.requireNonNull(defaultTerminationReason).getLastDetailForUpdate().getTerminationReasonDetailValue().clone();
                
                terminationReasonDetailValue.setIsDefault(true);
                updateTerminationReasonFromValue(terminationReasonDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(terminationReason.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // --------------------------------------------------------------------------------
    
    public TerminationReasonDescription createTerminationReasonDescription(TerminationReason terminationReason,
            Language language, String description, BasePK createdBy) {
        var terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().create(session,
                terminationReason, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(terminationReason.getPrimaryKey(), EventTypes.MODIFY, terminationReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = TerminationReasonDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = TerminationReasonDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var terminationReasonDescription = getTerminationReasonDescription(terminationReason, language);
        
        if(terminationReasonDescription == null && !language.getIsDefault()) {
            terminationReasonDescription = getTerminationReasonDescription(terminationReason, partyControl.getDefaultLanguage());
        }
        
        if(terminationReasonDescription == null) {
            description = terminationReason.getLastDetail().getTerminationReasonName();
        } else {
            description = terminationReasonDescription.getDescription();
        }
        
        return description;
    }
    
    public TerminationReasonDescriptionTransfer getTerminationReasonDescriptionTransfer(UserVisit userVisit, TerminationReasonDescription terminationReasonDescription) {
        return employeeTransferCaches.getTerminationReasonDescriptionTransferCache().getTerminationReasonDescriptionTransfer(userVisit, terminationReasonDescription);
    }
    
    public List<TerminationReasonDescriptionTransfer> getTerminationReasonDescriptionTransfers(UserVisit userVisit, TerminationReason terminationReason) {
        var terminationReasonDescriptions = getTerminationReasonDescriptionsByTerminationReason(terminationReason);
        List<TerminationReasonDescriptionTransfer> terminationReasonDescriptionTransfers = new ArrayList<>(terminationReasonDescriptions.size());
        var terminationReasonDescriptionTransferCache = employeeTransferCaches.getTerminationReasonDescriptionTransferCache();
        
        terminationReasonDescriptions.forEach((terminationReasonDescription) ->
                terminationReasonDescriptionTransfers.add(terminationReasonDescriptionTransferCache.getTerminationReasonDescriptionTransfer(userVisit, terminationReasonDescription))
        );
        
        return terminationReasonDescriptionTransfers;
    }
    
    public void updateTerminationReasonDescriptionFromValue(TerminationReasonDescriptionValue terminationReasonDescriptionValue, BasePK updatedBy) {
        if(terminationReasonDescriptionValue.hasBeenModified()) {
            var terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationReasonDescriptionValue.getPrimaryKey());
            
            terminationReasonDescription.setThruTime(session.START_TIME_LONG);
            terminationReasonDescription.store();

            var terminationReason = terminationReasonDescription.getTerminationReason();
            var language = terminationReasonDescription.getLanguage();
            var description = terminationReasonDescriptionValue.getDescription();
            
            terminationReasonDescription = TerminationReasonDescriptionFactory.getInstance().create(terminationReason, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(terminationReason.getPrimaryKey(), EventTypes.MODIFY, terminationReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTerminationReasonDescription(TerminationReasonDescription terminationReasonDescription, BasePK deletedBy) {
        terminationReasonDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(terminationReasonDescription.getTerminationReasonPK(), EventTypes.MODIFY, terminationReasonDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTerminationReasonDescriptionsByTerminationReason(TerminationReason terminationReason, BasePK deletedBy) {
        var terminationReasonDescriptions = getTerminationReasonDescriptionsByTerminationReasonForUpdate(terminationReason);
        
        terminationReasonDescriptions.forEach((terminationReasonDescription) -> 
                deleteTerminationReasonDescription(terminationReasonDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Termination Types
    // --------------------------------------------------------------------------------
    
    public TerminationType createTerminationType(String terminationTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultTerminationType = getDefaultTerminationType();
        var defaultFound = defaultTerminationType != null;
        
        if(defaultFound && isDefault) {
            var defaultTerminationTypeDetailValue = getDefaultTerminationTypeDetailValueForUpdate();
            
            defaultTerminationTypeDetailValue.setIsDefault(false);
            updateTerminationTypeFromValue(defaultTerminationTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var terminationType = TerminationTypeFactory.getInstance().create();
        var terminationTypeDetail = TerminationTypeDetailFactory.getInstance().create(session,
                terminationType, terminationTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        terminationType = TerminationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationType.getPrimaryKey());
        terminationType.setActiveDetail(terminationTypeDetail);
        terminationType.setLastDetail(terminationTypeDetail);
        terminationType.store();
        
        sendEvent(terminationType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

            var ps = TerminationTypeFactory.getInstance().prepareStatement(query);
            
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

        var ps = TerminationTypeFactory.getInstance().prepareStatement(query);
        
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

        var ps = TerminationTypeFactory.getInstance().prepareStatement(query);
        
        return TerminationTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TerminationType> getTerminationTypes() {
        return getTerminationTypes(EntityPermission.READ_ONLY);
    }
    
    public List<TerminationType> getTerminationTypesForUpdate() {
        return getTerminationTypes(EntityPermission.READ_WRITE);
    }
    
    public TerminationTypeTransfer getTerminationTypeTransfer(UserVisit userVisit, TerminationType terminationType) {
        return employeeTransferCaches.getTerminationTypeTransferCache().getTerminationTypeTransfer(userVisit, terminationType);
    }
    
    public List<TerminationTypeTransfer> getTerminationTypeTransfers(UserVisit userVisit) {
        var terminationTypes = getTerminationTypes();
        List<TerminationTypeTransfer> terminationTypeTransfers = new ArrayList<>(terminationTypes.size());
        var terminationTypeTransferCache = employeeTransferCaches.getTerminationTypeTransferCache();
        
        terminationTypes.forEach((terminationType) ->
                terminationTypeTransfers.add(terminationTypeTransferCache.getTerminationTypeTransfer(userVisit, terminationType))
        );
        
        return terminationTypeTransfers;
    }
    
    public TerminationTypeChoicesBean getTerminationTypeChoices(String defaultTerminationTypeChoice, Language language,
            boolean allowNullChoice) {
        var terminationTypes = getTerminationTypes();
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
            var terminationTypeDetail = terminationType.getLastDetail();
            
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
            var terminationType = TerminationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     terminationTypeDetailValue.getTerminationTypePK());
            var terminationTypeDetail = terminationType.getActiveDetailForUpdate();
            
            terminationTypeDetail.setThruTime(session.START_TIME_LONG);
            terminationTypeDetail.store();

            var terminationTypePK = terminationTypeDetail.getTerminationTypePK();
            var terminationTypeName = terminationTypeDetailValue.getTerminationTypeName();
            var isDefault = terminationTypeDetailValue.getIsDefault();
            var sortOrder = terminationTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultTerminationType = getDefaultTerminationType();
                var defaultFound = defaultTerminationType != null && !defaultTerminationType.equals(terminationType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTerminationTypeDetailValue = getDefaultTerminationTypeDetailValueForUpdate();
                    
                    defaultTerminationTypeDetailValue.setIsDefault(false);
                    updateTerminationTypeFromValue(defaultTerminationTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            terminationTypeDetail = TerminationTypeDetailFactory.getInstance().create(terminationTypePK,
                    terminationTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            terminationType.setActiveDetail(terminationTypeDetail);
            terminationType.setLastDetail(terminationTypeDetail);
            
            sendEvent(terminationTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateTerminationTypeFromValue(TerminationTypeDetailValue terminationTypeDetailValue, BasePK updatedBy) {
        updateTerminationTypeFromValue(terminationTypeDetailValue, true, updatedBy);
    }
    
    public void deleteTerminationType(TerminationType terminationType, BasePK deletedBy) {
        deleteTerminationTypeDescriptionsByTerminationType(terminationType, deletedBy);
        deleteEmploymentsByTerminationType(terminationType, deletedBy);

        var terminationTypeDetail = terminationType.getLastDetailForUpdate();
        terminationTypeDetail.setThruTime(session.START_TIME_LONG);
        terminationType.setActiveDetail(null);
        terminationType.store();
        
        // Check for default, and pick one if necessary
        var defaultTerminationType = getDefaultTerminationType();
        if(defaultTerminationType == null) {
            var terminationTypes = getTerminationTypesForUpdate();
            
            if(!terminationTypes.isEmpty()) {
                Iterator iter = terminationTypes.iterator();
                if(iter.hasNext()) {
                    defaultTerminationType = (TerminationType)iter.next();
                }
                var terminationTypeDetailValue = Objects.requireNonNull(defaultTerminationType).getLastDetailForUpdate().getTerminationTypeDetailValue().clone();
                
                terminationTypeDetailValue.setIsDefault(true);
                updateTerminationTypeFromValue(terminationTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(terminationType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Termination Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TerminationTypeDescription createTerminationTypeDescription(TerminationType terminationType,
            Language language, String description, BasePK createdBy) {
        var terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().create(session,
                terminationType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(terminationType.getPrimaryKey(), EventTypes.MODIFY, terminationTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = TerminationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = TerminationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var terminationTypeDescription = getTerminationTypeDescription(terminationType, language);
        
        if(terminationTypeDescription == null && !language.getIsDefault()) {
            terminationTypeDescription = getTerminationTypeDescription(terminationType, partyControl.getDefaultLanguage());
        }
        
        if(terminationTypeDescription == null) {
            description = terminationType.getLastDetail().getTerminationTypeName();
        } else {
            description = terminationTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public TerminationTypeDescriptionTransfer getTerminationTypeDescriptionTransfer(UserVisit userVisit, TerminationTypeDescription terminationTypeDescription) {
        return employeeTransferCaches.getTerminationTypeDescriptionTransferCache().getTerminationTypeDescriptionTransfer(userVisit, terminationTypeDescription);
    }
    
    public List<TerminationTypeDescriptionTransfer> getTerminationTypeDescriptionTransfers(UserVisit userVisit, TerminationType terminationType) {
        var terminationTypeDescriptions = getTerminationTypeDescriptionsByTerminationType(terminationType);
        List<TerminationTypeDescriptionTransfer> terminationTypeDescriptionTransfers = new ArrayList<>(terminationTypeDescriptions.size());
        var terminationTypeDescriptionTransferCache = employeeTransferCaches.getTerminationTypeDescriptionTransferCache();
        
        terminationTypeDescriptions.forEach((terminationTypeDescription) ->
                terminationTypeDescriptionTransfers.add(terminationTypeDescriptionTransferCache.getTerminationTypeDescriptionTransfer(userVisit, terminationTypeDescription))
        );
        
        return terminationTypeDescriptionTransfers;
    }
    
    public void updateTerminationTypeDescriptionFromValue(TerminationTypeDescriptionValue terminationTypeDescriptionValue, BasePK updatedBy) {
        if(terminationTypeDescriptionValue.hasBeenModified()) {
            var terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, terminationTypeDescriptionValue.getPrimaryKey());
            
            terminationTypeDescription.setThruTime(session.START_TIME_LONG);
            terminationTypeDescription.store();

            var terminationType = terminationTypeDescription.getTerminationType();
            var language = terminationTypeDescription.getLanguage();
            var description = terminationTypeDescriptionValue.getDescription();
            
            terminationTypeDescription = TerminationTypeDescriptionFactory.getInstance().create(terminationType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(terminationType.getPrimaryKey(), EventTypes.MODIFY, terminationTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTerminationTypeDescription(TerminationTypeDescription terminationTypeDescription, BasePK deletedBy) {
        terminationTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(terminationTypeDescription.getTerminationTypePK(), EventTypes.MODIFY, terminationTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTerminationTypeDescriptionsByTerminationType(TerminationType terminationType, BasePK deletedBy) {
        var terminationTypeDescriptions = getTerminationTypeDescriptionsByTerminationTypeForUpdate(terminationType);
        
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
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.EMPLOYMENT.name());
        var employmentName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createEmployment(employmentName, party, companyParty, startTime, endTime, terminationType, terminationReason, createdBy);
    }

    public Employment createEmployment(String employmentName, Party party, Party companyParty, Long startTime, Long endTime,
            TerminationType terminationType, TerminationReason terminationReason, BasePK createdBy) {
        var employment = EmploymentFactory.getInstance().create();
        var employmentDetail = EmploymentDetailFactory.getInstance().create(employment, employmentName, party, companyParty, startTime,
                endTime, terminationType, terminationReason, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        employment = EmploymentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, employment.getPrimaryKey());
        employment.setActiveDetail(employmentDetail);
        employment.setLastDetail(employmentDetail);
        employment.store();

        var employmentPK = employment.getPrimaryKey();
        sendEvent(employmentPK, EventTypes.CREATE, null, null, createdBy);
        sendEvent(party.getPrimaryKey(), EventTypes.TOUCH, employmentPK, EventTypes.CREATE, createdBy);

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
        return employeeTransferCaches.getEmploymentTransferCache().getEmploymentTransfer(userVisit, employment);
    }

    public List<EmploymentTransfer> getEmploymentTransfers(UserVisit userVisit, Collection<Employment> employments) {
        List<EmploymentTransfer> employmentTransfers = new ArrayList<>(employments.size());
        var employmentTransferCache = employeeTransferCaches.getEmploymentTransferCache();

        employments.forEach((employment) ->
                employmentTransfers.add(employmentTransferCache.getEmploymentTransfer(userVisit, employment))
        );

        return employmentTransfers;
    }

    public List<EmploymentTransfer> getEmploymentTransfersByParty(UserVisit userVisit, Party party) {
        return getEmploymentTransfers(userVisit, getEmploymentsByParty(party));
    }

    public void updateEmploymentFromValue(EmploymentDetailValue employmentDetailValue, BasePK updatedBy) {
        if(employmentDetailValue.hasBeenModified()) {
            var employment = EmploymentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     employmentDetailValue.getEmploymentPK());
            var employmentDetail = employment.getActiveDetailForUpdate();

            employmentDetail.setThruTime(session.START_TIME_LONG);
            employmentDetail.store();

            var employmentPK = employmentDetail.getEmploymentPK(); // Not updated
            var employmentName = employmentDetail.getEmploymentName(); // Not updated
            var partyPK = employmentDetail.getPartyPK(); // Not updated
            var companyPartyPK = employmentDetailValue.getCompanyPartyPK();
            var startTime = employmentDetailValue.getStartTime();
            var endTime = employmentDetailValue.getEndTime();
            var terminationTypePK = employmentDetailValue.getTerminationTypePK();
            var terminationReasonPK = employmentDetailValue.getTerminationReasonPK();

            employmentDetail = EmploymentDetailFactory.getInstance().create(employmentPK, employmentName, partyPK, companyPartyPK, startTime, endTime,
                    terminationTypePK, terminationReasonPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            employment.setActiveDetail(employmentDetail);
            employment.setLastDetail(employmentDetail);

            sendEvent(employmentPK, EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(partyPK, EventTypes.TOUCH, employmentPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEmployment(Employment employment, BasePK deletedBy) {
        var employmentDetail = employment.getLastDetailForUpdate();
        employmentDetail.setThruTime(session.START_TIME_LONG);
        employmentDetail.store();
        employment.setActiveDetail(null);

        var employmentPK = employment.getPrimaryKey();
        sendEvent(employmentPK, EventTypes.DELETE, null, null, deletedBy);
        sendEvent(employmentDetail.getParty().getPrimaryKey(), EventTypes.TOUCH, employmentPK, EventTypes.DELETE, deletedBy);
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
        var defaultEmployeeType = getDefaultEmployeeType();
        var defaultFound = defaultEmployeeType != null;
        
        if(defaultFound && isDefault) {
            var defaultEmployeeTypeDetailValue = getDefaultEmployeeTypeDetailValueForUpdate();
            
            defaultEmployeeTypeDetailValue.setIsDefault(false);
            updateEmployeeTypeFromValue(defaultEmployeeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var employeeType = EmployeeTypeFactory.getInstance().create();
        var employeeTypeDetail = EmployeeTypeDetailFactory.getInstance().create(employeeType,
                employeeTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        employeeType = EmployeeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                employeeType.getPrimaryKey());
        employeeType.setActiveDetail(employeeTypeDetail);
        employeeType.setLastDetail(employeeTypeDetail);
        employeeType.store();
        
        sendEvent(employeeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return employeeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EmployeeType */
    public EmployeeType getEmployeeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EmployeeTypePK(entityInstance.getEntityUniqueId());

        return EmployeeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EmployeeType getEmployeeTypeByEntityInstance(EntityInstance entityInstance) {
        return getEmployeeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EmployeeType getEmployeeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEmployeeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEmployeeTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM employeetypes, employeetypedetails " +
                        "WHERE empty_activedetailid = emptydt_employeetypedetailid");
    }

    private List<EmployeeType> getEmployeeTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM employeetypes, employeetypedetails " +
                    "WHERE empty_activedetailid = emptydt_employeetypedetailid " +
                    "ORDER BY emptydt_sortorder, emptydt_employeetypename " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM employeetypes, employeetypedetails " +
                    "WHERE empty_activedetailid = emptydt_employeetypedetailid " +
                    "FOR UPDATE";
        }

        var ps = EmployeeTypeFactory.getInstance().prepareStatement(query);
        
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

        var ps = EmployeeTypeFactory.getInstance().prepareStatement(query);
        
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
    
    public EmployeeType getEmployeeTypeByName(String employeeTypeName, EntityPermission entityPermission) {
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

            var ps = EmployeeTypeFactory.getInstance().prepareStatement(query);
            
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
        var employeeTypes = getEmployeeTypes();
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
            var employeeTypeDetail = employeeType.getLastDetail();
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
        return employeeTransferCaches.getEmployeeTypeTransferCache().getEmployeeTypeTransfer(userVisit, employeeType);
    }
    
    public List<EmployeeTypeTransfer> getEmployeeTypeTransfers(UserVisit userVisit) {
        var employeeTypes = getEmployeeTypes();
        List<EmployeeTypeTransfer> employeeTypeTransfers = null;
        
        if(employeeTypes != null) {
            var employeeTypeTransferCache = employeeTransferCaches.getEmployeeTypeTransferCache();
            
            employeeTypeTransfers = new ArrayList<>(employeeTypes.size());
            
            for(var employeeType : employeeTypes) {
                employeeTypeTransfers.add(employeeTypeTransferCache.getEmployeeTypeTransfer(userVisit, employeeType));
            }
        }
        
        return employeeTypeTransfers;
    }
    
    private void updateEmployeeTypeFromValue(EmployeeTypeDetailValue employeeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(employeeTypeDetailValue.hasBeenModified()) {
            var employeeType = EmployeeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     employeeTypeDetailValue.getEmployeeTypePK());
            var employeeTypeDetail = employeeType.getActiveDetailForUpdate();
            
            employeeTypeDetail.setThruTime(session.START_TIME_LONG);
            employeeTypeDetail.store();

            var employeeTypePK = employeeTypeDetail.getEmployeeTypePK();
            var employeeTypeName = employeeTypeDetailValue.getEmployeeTypeName();
            var isDefault = employeeTypeDetailValue.getIsDefault();
            var sortOrder = employeeTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEmployeeType = getDefaultEmployeeType();
                var defaultFound = defaultEmployeeType != null && !defaultEmployeeType.equals(employeeType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEmployeeTypeDetailValue = getDefaultEmployeeTypeDetailValueForUpdate();
                    
                    defaultEmployeeTypeDetailValue.setIsDefault(false);
                    updateEmployeeTypeFromValue(defaultEmployeeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            employeeTypeDetail = EmployeeTypeDetailFactory.getInstance().create(employeeTypePK, employeeTypeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            employeeType.setActiveDetail(employeeTypeDetail);
            employeeType.setLastDetail(employeeTypeDetail);
            
            sendEvent(employeeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEmployeeTypeFromValue(EmployeeTypeDetailValue employeeTypeDetailValue, BasePK updatedBy) {
        updateEmployeeTypeFromValue(employeeTypeDetailValue, true, updatedBy);
    }
    
    public void deleteEmployeeType(EmployeeType employeeType, BasePK deletedBy) {
        deleteEmployeeTypeDescriptionsByEmployeeType(employeeType, deletedBy);

        var employeeTypeDetail = employeeType.getLastDetailForUpdate();
        employeeTypeDetail.setThruTime(session.START_TIME_LONG);
        employeeType.setActiveDetail(null);
        employeeType.store();
        
        // Check for default, and pick one if necessary
        var defaultEmployeeType = getDefaultEmployeeType();
        if(defaultEmployeeType == null) {
            var employeeTypes = getEmployeeTypesForUpdate();
            
            if(!employeeTypes.isEmpty()) {
                var iter = employeeTypes.iterator();
                if(iter.hasNext()) {
                    defaultEmployeeType = iter.next();
                }
                var employeeTypeDetailValue = Objects.requireNonNull(defaultEmployeeType).getLastDetailForUpdate().getEmployeeTypeDetailValue().clone();
                
                employeeTypeDetailValue.setIsDefault(true);
                updateEmployeeTypeFromValue(employeeTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(employeeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EmployeeTypeDescription createEmployeeTypeDescription(EmployeeType employeeType, Language language, String description,
            BasePK createdBy) {
        var employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().create(employeeType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(employeeType.getPrimaryKey(), EventTypes.MODIFY, employeeTypeDescription.getPrimaryKey(),
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

            var ps = EmployeeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = EmployeeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var employeeTypeDescription = getEmployeeTypeDescription(employeeType, language);
        
        if(employeeTypeDescription == null && !language.getIsDefault()) {
            employeeTypeDescription = getEmployeeTypeDescription(employeeType, partyControl.getDefaultLanguage());
        }
        
        if(employeeTypeDescription == null) {
            description = employeeType.getLastDetail().getEmployeeTypeName();
        } else {
            description = employeeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public EmployeeTypeDescriptionTransfer getEmployeeTypeDescriptionTransfer(UserVisit userVisit, EmployeeTypeDescription employeeTypeDescription) {
        return employeeTransferCaches.getEmployeeTypeDescriptionTransferCache().getEmployeeTypeDescriptionTransfer(userVisit, employeeTypeDescription);
    }
    
    public List<EmployeeTypeDescriptionTransfer> getEmployeeTypeDescriptionTransfers(UserVisit userVisit, EmployeeType employeeType) {
        var employeeTypeDescriptions = getEmployeeTypeDescriptionsByEmployeeType(employeeType);
        List<EmployeeTypeDescriptionTransfer> employeeTypeDescriptionTransfers = null;
        
        if(employeeTypeDescriptions != null) {
            var employeeTypeDescriptionTransferCache = employeeTransferCaches.getEmployeeTypeDescriptionTransferCache();
            
            employeeTypeDescriptionTransfers = new ArrayList<>(employeeTypeDescriptions.size());
            
            for(var employeeTypeDescription : employeeTypeDescriptions) {
                employeeTypeDescriptionTransfers.add(employeeTypeDescriptionTransferCache.getEmployeeTypeDescriptionTransfer(userVisit, employeeTypeDescription));
            }
        }
        
        return employeeTypeDescriptionTransfers;
    }
    
    public void updateEmployeeTypeDescriptionFromValue(EmployeeTypeDescriptionValue employeeTypeDescriptionValue, BasePK updatedBy) {
        if(employeeTypeDescriptionValue.hasBeenModified()) {
            var employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     employeeTypeDescriptionValue.getPrimaryKey());
            
            employeeTypeDescription.setThruTime(session.START_TIME_LONG);
            employeeTypeDescription.store();

            var employeeType = employeeTypeDescription.getEmployeeType();
            var language = employeeTypeDescription.getLanguage();
            var description = employeeTypeDescriptionValue.getDescription();
            
            employeeTypeDescription = EmployeeTypeDescriptionFactory.getInstance().create(employeeType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(employeeType.getPrimaryKey(), EventTypes.MODIFY, employeeTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteEmployeeTypeDescription(EmployeeTypeDescription employeeTypeDescription, BasePK deletedBy) {
        employeeTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(employeeTypeDescription.getEmployeeTypePK(), EventTypes.MODIFY,
                employeeTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteEmployeeTypeDescriptionsByEmployeeType(EmployeeType employeeType, BasePK deletedBy) {
        var employeeTypeDescriptions = getEmployeeTypeDescriptionsByEmployeeTypeForUpdate(employeeType);
        
        employeeTypeDescriptions.forEach((employeeTypeDescription) -> 
                deleteEmployeeTypeDescription(employeeTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Employees
    // --------------------------------------------------------------------------------
    
    public PartyEmployee createPartyEmployee(Party party, String partyEmployeeName, EmployeeType employeeType, BasePK createdBy) {
        var partyEmployee = PartyEmployeeFactory.getInstance().create(party, partyEmployeeName, employeeType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyEmployee.getPrimaryKey(), null, createdBy);
        
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
            var ps = PartyEmployeeFactory.getInstance().prepareStatement(
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

            var ps = PartyEmployeeFactory.getInstance().prepareStatement(query);
            
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
            var ps = PartyEmployeeFactory.getInstance().prepareStatement(
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
        var employeeStatusChoicesBean = new EmployeeStatusChoicesBean();
        
        if(employeeParty == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultEmployeeStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(employeeParty.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultEmployeeStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setEmployeeStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmployeeStatusChoice.name(), employeeStatusChoice);
        }
    }
    
    public EmployeeAvailabilityChoicesBean getEmployeeAvailabilityChoices(String defaultEmployeeAvailabilityChoice, Language language,
            boolean allowNullChoice, Party employeeParty, PartyPK partyPK) {
        var employeeAvailabilityChoicesBean = new EmployeeAvailabilityChoicesBean();
        
        if(employeeParty == null) {
            workflowControl.getWorkflowEntranceChoices(employeeAvailabilityChoicesBean, defaultEmployeeAvailabilityChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(employeeParty.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeAvailabilityChoicesBean, defaultEmployeeAvailabilityChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeAvailabilityChoicesBean;
    }
    
    public void setEmployeeAvailability(ExecutionErrorAccumulator eea, Party party, String employeeAvailabilityChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY,
                entityInstance);
        var workflowDestination = employeeAvailabilityChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeAvailabilityChoice);
        
        if(workflowDestination != null || employeeAvailabilityChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmployeeAvailabilityChoice.name(), employeeAvailabilityChoice);
        }
    }

    public List<EmployeeTransfer> getEmployeeTransfers(UserVisit userVisit, Collection<PartyEmployee> partyEmployees) {
        List<EmployeeTransfer> employeeTransfers = new ArrayList<>(partyEmployees.size());
        var employeeTransferCache = employeeTransferCaches.getEmployeeTransferCache();

        partyEmployees.stream().map((partyEmployee) -> partyEmployee.getParty()).forEach((party) -> employeeTransfers.add(employeeTransferCache.getTransfer(userVisit, party)));

        return employeeTransfers;
    }

    public List<EmployeeTransfer> getEmployeeTransfers(UserVisit userVisit) {
        return getEmployeeTransfers(userVisit, getPartyEmployees());
    }

    public EmployeeTransfer getEmployeeTransfer(UserVisit userVisit, PartyEmployee partyEmployee) {
        return employeeTransferCaches.getEmployeeTransferCache().getTransfer(userVisit, partyEmployee);
    }

    public EmployeeTransfer getEmployeeTransfer(UserVisit userVisit, Party party) {
        return employeeTransferCaches.getEmployeeTransferCache().getTransfer(userVisit, party);
    }
    
    public void updatePartyEmployeeFromValue(PartyEmployeeValue partyEmployeeValue, BasePK updatedBy) {
        if(partyEmployeeValue.hasBeenModified()) {
            var partyEmployee = PartyEmployeeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyEmployeeValue.getPrimaryKey());

            partyEmployee.setThruTime(session.START_TIME_LONG);
            partyEmployee.store();

            var partyPK = partyEmployee.getPartyPK(); // Not updated
            var partyEmployeeName = partyEmployeeValue.getPartyEmployeeName();
            var employeeTypePK = partyEmployeeValue.getEmployeeTypePK();

            partyEmployee = PartyEmployeeFactory.getInstance().create(partyPK, partyEmployeeName, employeeTypePK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(partyPK, EventTypes.MODIFY, partyEmployee.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyEmployee(PartyEmployee partyEmployee, BasePK deletedBy) {
        partyEmployee.setThruTime(session.START_TIME_LONG);
        
        sendEvent(partyEmployee.getPartyPK(), EventTypes.MODIFY, partyEmployee.getPrimaryKey(), null, deletedBy);
    }
    
    public void deletePartyEmployeeByParty(Party party, BasePK deletedBy) {
        var partyEmployee = getPartyEmployeeForUpdate(party);
        
        if(partyEmployee != null) {
            deletePartyEmployee(partyEmployee, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Party Responsibilities
    // --------------------------------------------------------------------------------
    
    public PartyResponsibility createPartyResponsibility(Party party, ResponsibilityType responsibilityType, BasePK createdBy) {
        var partyResponsibility = PartyResponsibilityFactory.getInstance().create(party, responsibilityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyResponsibility.getPrimaryKey(), null, createdBy);
        
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

            var ps = PartyResponsibilityFactory.getInstance().prepareStatement(query);
            
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

            var ps = PartyResponsibilityFactory.getInstance().prepareStatement(query);
            
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

            var ps = PartyResponsibilityFactory.getInstance().prepareStatement(query);
            
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
        var partyResponsibility = getPartyResponsibility(party, responsibilityType);
        
        return partyResponsibility == null? null: employeeTransferCaches.getPartyResponsibilityTransferCache().getPartyResponsibilityTransfer(userVisit, partyResponsibility);
    }
    
    public PartyResponsibilityTransfer getPartyResponsibilityTransfer(UserVisit userVisit, PartyResponsibility partyResponsibility) {
        return employeeTransferCaches.getPartyResponsibilityTransferCache().getPartyResponsibilityTransfer(userVisit, partyResponsibility);
    }
    
    public List<PartyResponsibilityTransfer> getPartyResponsibilityTransfers(UserVisit userVisit, Collection<PartyResponsibility> partyResponsibilities) {
        List<PartyResponsibilityTransfer> partyResponsibilityTransfers = new ArrayList<>(partyResponsibilities.size());
        var partyResponsibilityTransferCache = employeeTransferCaches.getPartyResponsibilityTransferCache();
        
        partyResponsibilities.forEach((partyResponsibility) ->
                partyResponsibilityTransfers.add(partyResponsibilityTransferCache.getPartyResponsibilityTransfer(userVisit, partyResponsibility))
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
        
        sendEvent(partyResponsibility.getPartyPK(), EventTypes.MODIFY, partyResponsibility.getPrimaryKey(), null, deletedBy);
    }
    
    public void deletePartyResponsibilityByParty(Party party, BasePK deletedBy) {
        var partyResponsibilities = getPartyResponsibilitiesByPartyForUpdate(party);
        
        partyResponsibilities.forEach((partyResponsibility) -> 
                deletePartyResponsibility(partyResponsibility, deletedBy)
        );
    }
    
    public void deletePartyResponsibilitiesByResponsibilityType(ResponsibilityType responsibilityType, BasePK deletedBy) {
        var partyResponsibilities = getPartyResponsibilitiesByResponsibilityTypeForUpdate(responsibilityType);
        
        partyResponsibilities.forEach((partyResponsibility) -> 
                deletePartyResponsibility(partyResponsibility, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Skills
    // --------------------------------------------------------------------------------
    
    public PartySkill createPartySkill(Party party, SkillType skillType, BasePK createdBy) {
        var partySkill = PartySkillFactory.getInstance().create(party, skillType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partySkill.getPrimaryKey(), null, createdBy);
        
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

            var ps = PartySkillFactory.getInstance().prepareStatement(query);
            
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

            var ps = PartySkillFactory.getInstance().prepareStatement(query);
            
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

            var ps = PartySkillFactory.getInstance().prepareStatement(query);
            
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
        var partySkill = getPartySkill(party, skillType);
        
        return partySkill == null? null: employeeTransferCaches.getPartySkillTransferCache().getPartySkillTransfer(userVisit, partySkill);
    }
    
    public PartySkillTransfer getPartySkillTransfer(UserVisit userVisit, PartySkill partySkill) {
        return employeeTransferCaches.getPartySkillTransferCache().getPartySkillTransfer(userVisit, partySkill);
    }
    
    public List<PartySkillTransfer> getPartySkillTransfers(UserVisit userVisit, Collection<PartySkill> partySkills) {
        List<PartySkillTransfer> partySkillTransfers = new ArrayList<>(partySkills.size());
        var partySkillTransferCache = employeeTransferCaches.getPartySkillTransferCache();
        
        partySkills.forEach((partySkill) ->
                partySkillTransfers.add(partySkillTransferCache.getPartySkillTransfer(userVisit, partySkill))
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
        
        sendEvent(partySkill.getPartyPK(), EventTypes.MODIFY, partySkill.getPrimaryKey(), null, deletedBy);
    }
    
    public void deletePartySkillByParty(Party party, BasePK deletedBy) {
        var partySkills = getPartySkillsByPartyForUpdate(party);
        
        partySkills.forEach((partySkill) -> 
                deletePartySkill(partySkill, deletedBy)
        );
    }
    
    public void deletePartySkillsBySkillType(SkillType skillType, BasePK deletedBy) {
        var partySkills = getPartySkillsBySkillTypeForUpdate(skillType);
        
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
                var party = partyControl.getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

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
                var party = partyControl.getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                employeeObjects.add(new EmployeeObject(party));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return employeeObjects;
    }

}
