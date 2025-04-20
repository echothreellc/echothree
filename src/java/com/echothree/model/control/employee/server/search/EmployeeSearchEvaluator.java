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

package com.echothree.model.control.employee.server.search;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.Indexes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.search.PartySearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class EmployeeSearchEvaluator
        extends PartySearchEvaluator {
    
    private String partyEmployeeName;
    private WorkflowStep employeeStatusWorkflowStep;
    private WorkflowStep employeeAvailabilityWorkflowStep;
    
    /** Creates a new instance of EmployeeSearchEvaluator */
    public EmployeeSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, PartyTypes.EMPLOYEE.name(),
                IndexFields.partyEmployeeName.name(), Indexes.EMPLOYEE.name());
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByEmployeeStatusWorkflowStep(WorkflowStep employeeStatusWorkflowStep) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, parties, partydetails, workflowentitystatuses " +
                    "WHERE par_activedetailid = pardt_partydetailid " +
                    "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid " +
                    "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?"),
                    entityType, employeeStatusWorkflowStep, Session.MAX_TIME);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByEmployeeAvailabilityWorkflowStep(WorkflowStep employeeAvailabilityWorkflowStep) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, parties, partydetails, workflowentitystatuses " +
                    "WHERE par_activedetailid = pardt_partydetailid " +
                    "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid " +
                    "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?"),
                    entityType, employeeAvailabilityWorkflowStep, Session.MAX_TIME);
    }

    /**
     * Returns the partyEmployeeName.
     * @return the partyEmployeeName
     */
    public String getPartyEmployeeName() {
        return partyEmployeeName;
    }

    /**
     * Sets the partyEmployeeName.
     * @param partyEmployeeName the partyEmployeeName to set
     */
    public void setPartyEmployeeName(String partyEmployeeName) {
        this.partyEmployeeName = partyEmployeeName;
    }

    /**
     * Returns the employeeStatusWorkflowStep.
     * @return the employeeStatusWorkflowStep
     */
    public WorkflowStep getEmployeeStatusWorkflowStep() {
        return employeeStatusWorkflowStep;
    }

    /**
     * Sets the employeeStatusWorkflowStep.
     * @param employeeStatusWorkflowStep the employeeStatusWorkflowStep to set
     */
    public void setEmployeeStatusWorkflowStep(WorkflowStep employeeStatusWorkflowStep) {
        this.employeeStatusWorkflowStep = employeeStatusWorkflowStep;
    }

    /**
     * Returns the employeeAvailabilityWorkflowStep.
     * @return the employeeAvailabilityWorkflowStep
     */
    public WorkflowStep getEmployeeAvailabilityWorkflowStep() {
        return employeeAvailabilityWorkflowStep;
    }

    /**
     * Sets the employeeAvailabilityWorkflowStep.
     * @param employeeAvailabilityWorkflowStep the employeeAvailabilityWorkflowStep to set
     */
    public void setEmployeeAvailabilityWorkflowStep(WorkflowStep employeeAvailabilityWorkflowStep) {
        this.employeeAvailabilityWorkflowStep = employeeAvailabilityWorkflowStep;
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;

        if(partyEmployeeName == null) {
            resultSet = super.executeSearch(eea);

            if(resultSet == null || resultSet.size() > 0) {
                if(employeeStatusWorkflowStep != null) {
                    var entityInstancePKHolder = getEntityInstancePKHolderByEmployeeStatusWorkflowStep(employeeStatusWorkflowStep);

                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }

            if(resultSet == null || resultSet.size() > 0) {
                if(employeeAvailabilityWorkflowStep != null) {
                    var entityInstancePKHolder = getEntityInstancePKHolderByEmployeeAvailabilityWorkflowStep(employeeAvailabilityWorkflowStep);

                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
        } else {
            PartyEmployee partyEmployee = null;
            
            if(partyEmployeeName != null) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                
                partyEmployee = employeeControl.getPartyEmployeeByName(partyEmployeeName);
            }
            
            if(partyEmployee != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                resultSet = new EntityInstancePKHolder(1);
                resultSet.add(entityInstanceControl.getEntityInstanceByBasePK(partyEmployee.getPartyPK()).getPrimaryKey(), null);
            }
        }
        
        return resultSet;
    }

}
