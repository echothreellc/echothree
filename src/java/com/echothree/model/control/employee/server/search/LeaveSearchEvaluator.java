// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.employee.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class LeaveSearchEvaluator
        extends BaseSearchEvaluator {
    
    private String leaveName;
    private Party party;
    private Party companyParty;
    private LeaveType leaveType;
    private LeaveReason leaveReason;
    private WorkflowStep leaveStatusWorkflowStep;
    
    /** Creates a new instance of LeaveSearchEvaluator */
    public LeaveSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, null, ComponentVendors.ECHOTHREE.name(),
                EntityTypes.Leave.name(), null, null, null);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByParty(Party party) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, leaves, leavedetails " +
                    "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_par_partyid = ? " +
                    "AND eni_ent_entitytypeid = ? AND lv_leaveid = eni_entityuniqueid"),
                    party, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCompanyParty(Party companyParty) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, leaves, leavedetails " +
                    "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_companypartyid = ? " +
                    "AND eni_ent_entitytypeid = ? AND lv_leaveid = eni_entityuniqueid"),
                    companyParty, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByLeaveType(LeaveType leaveType) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, leaves, leavedetails " +
                    "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_lvtyp_leavetypeid = ? " +
                    "AND eni_ent_entitytypeid = ? AND lv_leaveid = eni_entityuniqueid"),
                    leaveType, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByLeaveReason(LeaveReason leaveReason) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, leaves, leavedetails " +
                    "WHERE lv_activedetailid = lvdt_leavedetailid AND lvdt_lvrsn_leavereasonid = ? " +
                    "AND eni_ent_entitytypeid = ? AND lv_leaveid = eni_entityuniqueid"),
                    leaveReason, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByLeaveStatusWorkflowStep(WorkflowStep leaveStatusWorkflowStep) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, leaves, leavedetails, workflowentitystatuses " +
                    "WHERE lv_activedetailid = lvdt_leavedetailid " +
                    "AND eni_ent_entitytypeid = ? AND lv_leaveid = eni_entityuniqueid " +
                    "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?"),
                    entityType, leaveStatusWorkflowStep, Session.MAX_TIME);
    }
 
    /**
     * @return the leaveName
     */
    public String getLeaveName() {
        return leaveName;
    }

    /**
     * @param leaveName the leaveName to set
     */
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    /**
     * @return the party
     */
    public Party getParty() {
        return party;
    }

    /**
     * @param party the party to set
     */
    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * @return the companyParty
     */
    public Party getCompanyParty() {
        return companyParty;
    }

    /**
     * @param companyParty the companyParty to set
     */
    public void setCompanyParty(Party companyParty) {
        this.companyParty = companyParty;
    }

    /**
     * @return the leaveType
     */
    public LeaveType getLeaveType() {
        return leaveType;
    }

    /**
     * @param leaveType the leaveType to set
     */
    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    /**
     * @return the leaveReason
     */
    public LeaveReason getLeaveReason() {
        return leaveReason;
    }

    /**
     * @param leaveReason the leaveReason to set
     */
    public void setLeaveReason(LeaveReason leaveReason) {
        this.leaveReason = leaveReason;
    }

    /**
     * @return the leaveStatusWorkflowStep
     */
    public WorkflowStep getLeaveStatusWorkflowStep() {
        return leaveStatusWorkflowStep;
    }

    /**
     * @param leaveStatusWorkflowStep the leaveStatusWorkflowStep to set
     */
    public void setLeaveStatusWorkflowStep(WorkflowStep leaveStatusWorkflowStep) {
        this.leaveStatusWorkflowStep = leaveStatusWorkflowStep;
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        
        if(leaveName == null) {
            resultSet = super.executeSearch(eea);
            
            if(resultSet == null || resultSet.size() > 0) {
                if(party != null) {
                    EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByParty(party);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
            
            if(resultSet == null || resultSet.size() > 0) {
                if(companyParty != null) {
                    EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByCompanyParty(companyParty);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
            
            if(resultSet == null || resultSet.size() > 0) {
                if(leaveType != null) {
                    EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByLeaveType(leaveType);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
            
            if(resultSet == null || resultSet.size() > 0) {
                if(leaveReason != null) {
                    EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByLeaveReason(leaveReason);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }

            if(resultSet == null || resultSet.size() > 0) {
                if(leaveStatusWorkflowStep != null) {
                    EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByLeaveStatusWorkflowStep(leaveStatusWorkflowStep);

                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
        } else {
            var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            Leave leave = employeeControl.getLeaveByName(leaveName);
            
            if(leave != null) {
                resultSet = new EntityInstancePKHolder(1);
                resultSet.add(getCoreControl().getEntityInstanceByBasePK(leave.getPrimaryKey()).getPrimaryKey(), null);
            }
        }
        
        return resultSet;
    }

}
