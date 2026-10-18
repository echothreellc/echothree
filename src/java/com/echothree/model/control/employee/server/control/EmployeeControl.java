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
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.EMPLOYEE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.EMPLOYEE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.EMPLOYMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.EMPLOYMENT_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.LEAVES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.LEAVE_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.LEAVE_REASONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.LEAVE_REASON_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.LEAVE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.LEAVE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.PARTY_RESPONSIBILITIES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.PARTY_RESPONSIBILITIES_RESPONSIBILITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.PARTY_SKILLS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.PARTY_SKILLS_SKILL_TYPE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.RESPONSIBILITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.RESPONSIBILITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.RESPONSIBILITY_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.SKILL_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.SKILL_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.SKILL_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.TERMINATION_REASONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.TERMINATION_REASON_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.TERMINATION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.employee.EmployeeForeignKeys.TERMINATION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.employee.EmployeeTypeDescriptions.EmployeeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.employee.EmployeeTypeDetails.EmployeeTypeDetails;
import static com.echothree.model.jooq.server.tables.employee.EmployeeTypes.EmployeeTypes;
import static com.echothree.model.jooq.server.tables.employee.EmploymentDetails.EmploymentDetails;
import static com.echothree.model.jooq.server.tables.employee.Employments.Employments;
import static com.echothree.model.jooq.server.tables.employee.LeaveDetails.LeaveDetails;
import static com.echothree.model.jooq.server.tables.employee.LeaveReasonDescriptions.LeaveReasonDescriptions;
import static com.echothree.model.jooq.server.tables.employee.LeaveReasonDetails.LeaveReasonDetails;
import static com.echothree.model.jooq.server.tables.employee.LeaveReasons.LeaveReasons;
import static com.echothree.model.jooq.server.tables.employee.LeaveTypeDescriptions.LeaveTypeDescriptions;
import static com.echothree.model.jooq.server.tables.employee.LeaveTypeDetails.LeaveTypeDetails;
import static com.echothree.model.jooq.server.tables.employee.LeaveTypes.LeaveTypes;
import static com.echothree.model.jooq.server.tables.employee.Leaves.Leaves;
import static com.echothree.model.jooq.server.tables.employee.PartyEmployees.PartyEmployees;
import static com.echothree.model.jooq.server.tables.employee.PartyResponsibilities.PartyResponsibilities;
import static com.echothree.model.jooq.server.tables.employee.PartySkills.PartySkills;
import static com.echothree.model.jooq.server.tables.employee.ResponsibilityTypeDescriptions.ResponsibilityTypeDescriptions;
import static com.echothree.model.jooq.server.tables.employee.ResponsibilityTypeDetails.ResponsibilityTypeDetails;
import static com.echothree.model.jooq.server.tables.employee.ResponsibilityTypes.ResponsibilityTypes;
import static com.echothree.model.jooq.server.tables.employee.SkillTypeDescriptions.SkillTypeDescriptions;
import static com.echothree.model.jooq.server.tables.employee.SkillTypeDetails.SkillTypeDetails;
import static com.echothree.model.jooq.server.tables.employee.SkillTypes.SkillTypes;
import static com.echothree.model.jooq.server.tables.employee.TerminationReasonDescriptions.TerminationReasonDescriptions;
import static com.echothree.model.jooq.server.tables.employee.TerminationReasonDetails.TerminationReasonDetails;
import static com.echothree.model.jooq.server.tables.employee.TerminationReasons.TerminationReasons;
import static com.echothree.model.jooq.server.tables.employee.TerminationTypeDescriptions.TerminationTypeDescriptions;
import static com.echothree.model.jooq.server.tables.employee.TerminationTypeDetails.TerminationTypeDetails;
import static com.echothree.model.jooq.server.tables.employee.TerminationTypes.TerminationTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class EmployeeControl
        extends BaseModelControl {
    
    @Inject
    protected SearchControl searchControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of EmployeeControl */
    protected EmployeeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Responsibility Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ResponsibilityTypeDetailFactory responsibilityTypeDetailFactory;

    @Inject
    protected ResponsibilityTypeFactory responsibilityTypeFactory;

    @Inject
    ResponsibilityTypeTransferCache responsibilityTypeTransferCache;

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

        var responsibilityType = responsibilityTypeFactory.create();
        var responsibilityTypeDetail = responsibilityTypeDetailFactory.create(
                responsibilityType, responsibilityTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        responsibilityType = responsibilityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, responsibilityType.getPrimaryKey());
        responsibilityType.setActiveDetail(responsibilityTypeDetail);
        responsibilityType.setLastDetail(responsibilityTypeDetail);
        responsibilityType.store();
        
        sendEvent(responsibilityType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return responsibilityType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ResponsibilityType */
    public ResponsibilityType getResponsibilityTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ResponsibilityTypePK(entityInstance.getEntityUniqueId());

        return responsibilityTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ResponsibilityType getResponsibilityTypeByEntityInstance(EntityInstance entityInstance) {
        return getResponsibilityTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ResponsibilityType getResponsibilityTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getResponsibilityTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countResponsibilityTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ResponsibilityTypes)
                .join(ResponsibilityTypeDetails).onKey(RESPONSIBILITY_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ResponsibilityType getResponsibilityTypeByName(String responsibilityTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ResponsibilityTypes.fields())
                .from(ResponsibilityTypes)
                .join(ResponsibilityTypeDetails).onKey(RESPONSIBILITY_TYPES_ACTIVE_DETAIL_FK)
                .where(ResponsibilityTypeDetails.RESPONSIBILITY_TYPE_NAME.eq(responsibilityTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return responsibilityTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ResponsibilityTypes.fields())
                .from(ResponsibilityTypes)
                .join(ResponsibilityTypeDetails).onKey(RESPONSIBILITY_TYPES_ACTIVE_DETAIL_FK)
                .where(ResponsibilityTypeDetails.SORT_ORDER.eq(1));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return responsibilityTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ResponsibilityTypes.fields())
                .from(ResponsibilityTypes)
                .join(ResponsibilityTypeDetails).onKey(RESPONSIBILITY_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ResponsibilityTypeDetails.SORT_ORDER, ResponsibilityTypeDetails.RESPONSIBILITY_TYPE_NAME),
                    ResponsibilityTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return responsibilityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ResponsibilityType> getResponsibilityTypes() {
        return getResponsibilityTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ResponsibilityType> getResponsibilityTypesForUpdate() {
        return getResponsibilityTypes(EntityPermission.READ_WRITE);
    }
    
    public ResponsibilityTypeTransfer getResponsibilityTypeTransfer(UserVisit userVisit, ResponsibilityType responsibilityType) {
        return responsibilityTypeTransferCache.getResponsibilityTypeTransfer(userVisit, responsibilityType);
    }
    
    public List<ResponsibilityTypeTransfer> getResponsibilityTypeTransfers(UserVisit userVisit, Collection<ResponsibilityType> responsibilityTypes) {
        List<ResponsibilityTypeTransfer> responsibilityTypeTransfers = new ArrayList<>(responsibilityTypes.size());

        responsibilityTypes.forEach((responsibilityType) ->
                responsibilityTypeTransfers.add(responsibilityTypeTransferCache.getResponsibilityTypeTransfer(userVisit, responsibilityType))
        );

        return responsibilityTypeTransfers;
    }

    public List<ResponsibilityTypeTransfer> getResponsibilityTypeTransfers(UserVisit userVisit) {
        return getResponsibilityTypeTransfers(userVisit, getResponsibilityTypes());
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
            var responsibilityType = responsibilityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     responsibilityTypeDetailValue.getResponsibilityTypePK());
            var responsibilityTypeDetail = responsibilityType.getActiveDetailForUpdate();
            
            responsibilityTypeDetail.setThruTime(session.getStartTime());
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
            
            responsibilityTypeDetail = responsibilityTypeDetailFactory.create(responsibilityTypePK,
                    responsibilityTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        responsibilityTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ResponsibilityTypeDescriptionFactory responsibilityTypeDescriptionFactory;

    @Inject
    ResponsibilityTypeDescriptionTransferCache responsibilityTypeDescriptionTransferCache;

    public ResponsibilityTypeDescription createResponsibilityTypeDescription(ResponsibilityType responsibilityType,
            Language language, String description, BasePK createdBy) {
        var responsibilityTypeDescription = responsibilityTypeDescriptionFactory.create(
                responsibilityType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(responsibilityType.getPrimaryKey(), EventTypes.MODIFY, responsibilityTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return responsibilityTypeDescription;
    }
    
    private ResponsibilityTypeDescription getResponsibilityTypeDescription(ResponsibilityType responsibilityType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ResponsibilityTypeDescriptions.fields())
                .from(ResponsibilityTypeDescriptions)
                .where(ResponsibilityTypeDescriptions.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                        ResponsibilityTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ResponsibilityTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return responsibilityTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ResponsibilityTypeDescriptions.fields())
                    .from(ResponsibilityTypeDescriptions)
                    .join(Languages).onKey(RESPONSIBILITY_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ResponsibilityTypeDescriptions.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                            ResponsibilityTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ResponsibilityTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ResponsibilityTypeDescriptions.fields())
                    .from(ResponsibilityTypeDescriptions)
                    .where(ResponsibilityTypeDescriptions.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                            ResponsibilityTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return responsibilityTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return responsibilityTypeDescriptionTransferCache.getResponsibilityTypeDescriptionTransfer(userVisit, responsibilityTypeDescription);
    }
    
    public List<ResponsibilityTypeDescriptionTransfer> getResponsibilityTypeDescriptionTransfers(UserVisit userVisit, ResponsibilityType responsibilityType) {
        var responsibilityTypeDescriptions = getResponsibilityTypeDescriptionsByResponsibilityType(responsibilityType);
        List<ResponsibilityTypeDescriptionTransfer> responsibilityTypeDescriptionTransfers = new ArrayList<>(responsibilityTypeDescriptions.size());
        
        responsibilityTypeDescriptions.forEach((responsibilityTypeDescription) ->
                responsibilityTypeDescriptionTransfers.add(responsibilityTypeDescriptionTransferCache.getResponsibilityTypeDescriptionTransfer(userVisit, responsibilityTypeDescription))
        );
        
        return responsibilityTypeDescriptionTransfers;
    }
    
    public void updateResponsibilityTypeDescriptionFromValue(ResponsibilityTypeDescriptionValue responsibilityTypeDescriptionValue, BasePK updatedBy) {
        if(responsibilityTypeDescriptionValue.hasBeenModified()) {
            var responsibilityTypeDescription = responsibilityTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, responsibilityTypeDescriptionValue.getPrimaryKey());
            
            responsibilityTypeDescription.setThruTime(session.getStartTime());
            responsibilityTypeDescription.store();

            var responsibilityType = responsibilityTypeDescription.getResponsibilityType();
            var language = responsibilityTypeDescription.getLanguage();
            var description = responsibilityTypeDescriptionValue.getDescription();
            
            responsibilityTypeDescription = responsibilityTypeDescriptionFactory.create(responsibilityType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(responsibilityType.getPrimaryKey(), EventTypes.MODIFY, responsibilityTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteResponsibilityTypeDescription(ResponsibilityTypeDescription responsibilityTypeDescription, BasePK deletedBy) {
        responsibilityTypeDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected SkillTypeDetailFactory skillTypeDetailFactory;

    @Inject
    protected SkillTypeFactory skillTypeFactory;

    @Inject
    SkillTypeTransferCache skillTypeTransferCache;

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

        var skillType = skillTypeFactory.create();
        var skillTypeDetail = skillTypeDetailFactory.create(
                skillType, skillTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        skillType = skillTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, skillType.getPrimaryKey());
        skillType.setActiveDetail(skillTypeDetail);
        skillType.setLastDetail(skillTypeDetail);
        skillType.store();
        
        sendEvent(skillType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return skillType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SkillType */
    public SkillType getSkillTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SkillTypePK(entityInstance.getEntityUniqueId());

        return skillTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SkillType getSkillTypeByEntityInstance(EntityInstance entityInstance) {
        return getSkillTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SkillType getSkillTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSkillTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSkillTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SkillTypes)
                .join(SkillTypeDetails).onKey(SKILL_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SkillType getSkillTypeByName(String skillTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SkillTypes.fields())
                .from(SkillTypes)
                .join(SkillTypeDetails).onKey(SKILL_TYPES_ACTIVE_DETAIL_FK)
                .where(SkillTypeDetails.SKILL_TYPE_NAME.eq(skillTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return skillTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(SkillTypes.fields())
                .from(SkillTypes)
                .join(SkillTypeDetails).onKey(SKILL_TYPES_ACTIVE_DETAIL_FK)
                .where(SkillTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return skillTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(SkillTypes.fields())
                .from(SkillTypes)
                .join(SkillTypeDetails).onKey(SKILL_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SkillTypeDetails.SORT_ORDER, SkillTypeDetails.SKILL_TYPE_NAME),
                    SkillTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return skillTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SkillType> getSkillTypes() {
        return getSkillTypes(EntityPermission.READ_ONLY);
    }
    
    public List<SkillType> getSkillTypesForUpdate() {
        return getSkillTypes(EntityPermission.READ_WRITE);
    }
    
    public SkillTypeTransfer getSkillTypeTransfer(UserVisit userVisit, SkillType skillType) {
        return skillTypeTransferCache.getSkillTypeTransfer(userVisit, skillType);
    }
    
    public List<SkillTypeTransfer> getSkillTypeTransfers(UserVisit userVisit, Collection<SkillType> skillTypes) {
        List<SkillTypeTransfer> skillTypeTransfers = new ArrayList<>(skillTypes.size());

        skillTypes.forEach((skillType) ->
                skillTypeTransfers.add(skillTypeTransferCache.getSkillTypeTransfer(userVisit, skillType))
        );

        return skillTypeTransfers;
    }

    public List<SkillTypeTransfer> getSkillTypeTransfers(UserVisit userVisit) {
        return getSkillTypeTransfers(userVisit, getSkillTypes());
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
            var skillType = skillTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     skillTypeDetailValue.getSkillTypePK());
            var skillTypeDetail = skillType.getActiveDetailForUpdate();
            
            skillTypeDetail.setThruTime(session.getStartTime());
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
            
            skillTypeDetail = skillTypeDetailFactory.create(skillTypePK,
                    skillTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        skillTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected SkillTypeDescriptionFactory skillTypeDescriptionFactory;

    @Inject
    SkillTypeDescriptionTransferCache skillTypeDescriptionTransferCache;

    public SkillTypeDescription createSkillTypeDescription(SkillType skillType,
            Language language, String description, BasePK createdBy) {
        var skillTypeDescription = skillTypeDescriptionFactory.create(
                skillType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(skillType.getPrimaryKey(), EventTypes.MODIFY, skillTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return skillTypeDescription;
    }
    
    private SkillTypeDescription getSkillTypeDescription(SkillType skillType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SkillTypeDescriptions.fields())
                .from(SkillTypeDescriptions)
                .where(SkillTypeDescriptions.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                        SkillTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SkillTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return skillTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SkillTypeDescriptions.fields())
                    .from(SkillTypeDescriptions)
                    .join(Languages).onKey(SKILL_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SkillTypeDescriptions.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                            SkillTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SkillTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SkillTypeDescriptions.fields())
                    .from(SkillTypeDescriptions)
                    .where(SkillTypeDescriptions.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                            SkillTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return skillTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return skillTypeDescriptionTransferCache.getSkillTypeDescriptionTransfer(userVisit, skillTypeDescription);
    }
    
    public List<SkillTypeDescriptionTransfer> getSkillTypeDescriptionTransfers(UserVisit userVisit, SkillType skillType) {
        var skillTypeDescriptions = getSkillTypeDescriptionsBySkillType(skillType);
        List<SkillTypeDescriptionTransfer> skillTypeDescriptionTransfers = new ArrayList<>(skillTypeDescriptions.size());
        
        skillTypeDescriptions.forEach((skillTypeDescription) ->
                skillTypeDescriptionTransfers.add(skillTypeDescriptionTransferCache.getSkillTypeDescriptionTransfer(userVisit, skillTypeDescription))
        );
        
        return skillTypeDescriptionTransfers;
    }
    
    public void updateSkillTypeDescriptionFromValue(SkillTypeDescriptionValue skillTypeDescriptionValue, BasePK updatedBy) {
        if(skillTypeDescriptionValue.hasBeenModified()) {
            var skillTypeDescription = skillTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, skillTypeDescriptionValue.getPrimaryKey());
            
            skillTypeDescription.setThruTime(session.getStartTime());
            skillTypeDescription.store();

            var skillType = skillTypeDescription.getSkillType();
            var language = skillTypeDescription.getLanguage();
            var description = skillTypeDescriptionValue.getDescription();
            
            skillTypeDescription = skillTypeDescriptionFactory.create(skillType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(skillType.getPrimaryKey(), EventTypes.MODIFY, skillTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSkillTypeDescription(SkillTypeDescription skillTypeDescription, BasePK deletedBy) {
        skillTypeDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected LeaveTypeDetailFactory leaveTypeDetailFactory;

    @Inject
    protected LeaveTypeFactory leaveTypeFactory;

    @Inject
    LeaveTypeTransferCache leaveTypeTransferCache;

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

        var leaveType = leaveTypeFactory.create();
        var leaveTypeDetail = leaveTypeDetailFactory.create(leaveType,
                leaveTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        leaveType = leaveTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                leaveType.getPrimaryKey());
        leaveType.setActiveDetail(leaveTypeDetail);
        leaveType.setLastDetail(leaveTypeDetail);
        leaveType.store();

        sendEvent(leaveType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return leaveType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.LeaveType */
    public LeaveType getLeaveTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LeaveTypePK(entityInstance.getEntityUniqueId());

        return leaveTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public LeaveType getLeaveTypeByEntityInstance(EntityInstance entityInstance) {
        return getLeaveTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LeaveType getLeaveTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLeaveTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLeaveTypes() {
        return session.getDslContext()
                .selectCount()
                .from(LeaveTypes)
                .join(LeaveTypeDetails).onKey(LEAVE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LeaveType getLeaveTypeByName(String leaveTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveTypes.fields())
                .from(LeaveTypes)
                .join(LeaveTypeDetails).onKey(LEAVE_TYPES_ACTIVE_DETAIL_FK)
                .where(LeaveTypeDetails.LEAVE_TYPE_NAME.eq(leaveTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private LeaveType getDefaultLeaveType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveTypes.fields())
                .from(LeaveTypes)
                .join(LeaveTypeDetails).onKey(LEAVE_TYPES_ACTIVE_DETAIL_FK)
                .where(LeaveTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<LeaveType> getLeaveTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveTypes.fields())
                .from(LeaveTypes)
                .join(LeaveTypeDetails).onKey(LEAVE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LeaveTypeDetails.SORT_ORDER, LeaveTypeDetails.LEAVE_TYPE_NAME),
                    LeaveTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LeaveType> getLeaveTypes() {
        return getLeaveTypes(EntityPermission.READ_ONLY);
    }

    public List<LeaveType> getLeaveTypesForUpdate() {
        return getLeaveTypes(EntityPermission.READ_WRITE);
    }

    public LeaveTypeTransfer getLeaveTypeTransfer(UserVisit userVisit, LeaveType leaveType) {
        return leaveTypeTransferCache.getLeaveTypeTransfer(userVisit, leaveType);
    }

    public List<LeaveTypeTransfer> getLeaveTypeTransfers(UserVisit userVisit, Collection<LeaveType> leaveTypes) {
        List<LeaveTypeTransfer> leaveTypeTransfers = new ArrayList<>(leaveTypes.size());

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
            var leaveType = leaveTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveTypeDetailValue.getLeaveTypePK());
            var leaveTypeDetail = leaveType.getActiveDetailForUpdate();

            leaveTypeDetail.setThruTime(session.getStartTime());
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

            leaveTypeDetail = leaveTypeDetailFactory.create(leaveTypePK, leaveTypeName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

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
        leaveTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected LeaveTypeDescriptionFactory leaveTypeDescriptionFactory;

    @Inject
    LeaveTypeDescriptionTransferCache leaveTypeDescriptionTransferCache;

    public LeaveTypeDescription createLeaveTypeDescription(LeaveType leaveType,
            Language language, String description, BasePK createdBy) {
        var leaveTypeDescription = leaveTypeDescriptionFactory.create(leaveType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(leaveType.getPrimaryKey(), EventTypes.MODIFY, leaveTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return leaveTypeDescription;
    }

    private LeaveTypeDescription getLeaveTypeDescription(LeaveType leaveType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveTypeDescriptions.fields())
                .from(LeaveTypeDescriptions)
                .where(LeaveTypeDescriptions.LEAVE_TYPE.eq(leaveType.getPrimaryKey()),
                        LeaveTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        LeaveTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<LeaveTypeDescription> getLeaveTypeDescriptionsByLeaveType(LeaveType leaveType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LeaveTypeDescriptions.fields())
                    .from(LeaveTypeDescriptions)
                    .join(Languages).onKey(LEAVE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(LeaveTypeDescriptions.LEAVE_TYPE.eq(leaveType.getPrimaryKey()),
                            LeaveTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LeaveTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LeaveTypeDescriptions.fields())
                    .from(LeaveTypeDescriptions)
                    .where(LeaveTypeDescriptions.LEAVE_TYPE.eq(leaveType.getPrimaryKey()),
                            LeaveTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return leaveTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return leaveTypeDescriptionTransferCache.getLeaveTypeDescriptionTransfer(userVisit, leaveTypeDescription);
    }

    public List<LeaveTypeDescriptionTransfer> getLeaveTypeDescriptionTransfersByLeaveType(UserVisit userVisit, LeaveType leaveType) {
        var leaveTypeDescriptions = getLeaveTypeDescriptionsByLeaveType(leaveType);
        List<LeaveTypeDescriptionTransfer> leaveTypeDescriptionTransfers = new ArrayList<>(leaveTypeDescriptions.size());

        leaveTypeDescriptions.forEach((leaveTypeDescription) ->
                leaveTypeDescriptionTransfers.add(leaveTypeDescriptionTransferCache.getLeaveTypeDescriptionTransfer(userVisit, leaveTypeDescription))
        );

        return leaveTypeDescriptionTransfers;
    }

    public void updateLeaveTypeDescriptionFromValue(LeaveTypeDescriptionValue leaveTypeDescriptionValue, BasePK updatedBy) {
        if(leaveTypeDescriptionValue.hasBeenModified()) {
            var leaveTypeDescription = leaveTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    leaveTypeDescriptionValue.getPrimaryKey());

            leaveTypeDescription.setThruTime(session.getStartTime());
            leaveTypeDescription.store();

            var leaveType = leaveTypeDescription.getLeaveType();
            var language = leaveTypeDescription.getLanguage();
            var description = leaveTypeDescriptionValue.getDescription();

            leaveTypeDescription = leaveTypeDescriptionFactory.create(leaveType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(leaveType.getPrimaryKey(), EventTypes.MODIFY, leaveTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLeaveTypeDescription(LeaveTypeDescription leaveTypeDescription, BasePK deletedBy) {
        leaveTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected LeaveReasonDetailFactory leaveReasonDetailFactory;

    @Inject
    protected LeaveReasonFactory leaveReasonFactory;

    @Inject
    LeaveReasonTransferCache leaveReasonTransferCache;

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

        var leaveReason = leaveReasonFactory.create();
        var leaveReasonDetail = leaveReasonDetailFactory.create(leaveReason,
                leaveReasonName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        leaveReason = leaveReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                leaveReason.getPrimaryKey());
        leaveReason.setActiveDetail(leaveReasonDetail);
        leaveReason.setLastDetail(leaveReasonDetail);
        leaveReason.store();

        sendEvent(leaveReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return leaveReason;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.LeaveReason */
    public LeaveReason getLeaveReasonByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LeaveReasonPK(entityInstance.getEntityUniqueId());

        return leaveReasonFactory.getEntityFromPK(entityPermission, pk);
    }

    public LeaveReason getLeaveReasonByEntityInstance(EntityInstance entityInstance) {
        return getLeaveReasonByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LeaveReason getLeaveReasonByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLeaveReasonByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLeaveReasons() {
        return session.getDslContext()
                .selectCount()
                .from(LeaveReasons)
                .join(LeaveReasonDetails).onKey(LEAVE_REASONS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LeaveReason getLeaveReasonByName(String leaveReasonName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveReasons.fields())
                .from(LeaveReasons)
                .join(LeaveReasonDetails).onKey(LEAVE_REASONS_ACTIVE_DETAIL_FK)
                .where(LeaveReasonDetails.LEAVE_REASON_NAME.eq(leaveReasonName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveReasonFactory.getEntityFromQuery(entityPermission, query);
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

    private LeaveReason getDefaultLeaveReason(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveReasons.fields())
                .from(LeaveReasons)
                .join(LeaveReasonDetails).onKey(LEAVE_REASONS_ACTIVE_DETAIL_FK)
                .where(LeaveReasonDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveReasonFactory.getEntityFromQuery(entityPermission, query);
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

    private List<LeaveReason> getLeaveReasons(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveReasons.fields())
                .from(LeaveReasons)
                .join(LeaveReasonDetails).onKey(LEAVE_REASONS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LeaveReasonDetails.SORT_ORDER, LeaveReasonDetails.LEAVE_REASON_NAME),
                    LeaveReasonFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LeaveReason> getLeaveReasons() {
        return getLeaveReasons(EntityPermission.READ_ONLY);
    }

    public List<LeaveReason> getLeaveReasonsForUpdate() {
        return getLeaveReasons(EntityPermission.READ_WRITE);
    }

    public LeaveReasonTransfer getLeaveReasonTransfer(UserVisit userVisit, LeaveReason leaveReason) {
        return leaveReasonTransferCache.getLeaveReasonTransfer(userVisit, leaveReason);
    }

    public List<LeaveReasonTransfer> getLeaveReasonTransfers(UserVisit userVisit, Collection<LeaveReason> leaveReasons) {
        List<LeaveReasonTransfer> leaveReasonTransfers = new ArrayList<>(leaveReasons.size());

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
            var leaveReason = leaveReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveReasonDetailValue.getLeaveReasonPK());
            var leaveReasonDetail = leaveReason.getActiveDetailForUpdate();

            leaveReasonDetail.setThruTime(session.getStartTime());
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

            leaveReasonDetail = leaveReasonDetailFactory.create(leaveReasonPK, leaveReasonName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

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
        leaveReasonDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected LeaveReasonDescriptionFactory leaveReasonDescriptionFactory;

    @Inject
    LeaveReasonDescriptionTransferCache leaveReasonDescriptionTransferCache;

    public LeaveReasonDescription createLeaveReasonDescription(LeaveReason leaveReason,
            Language language, String description, BasePK createdBy) {
        var leaveReasonDescription = leaveReasonDescriptionFactory.create(leaveReason,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(leaveReason.getPrimaryKey(), EventTypes.MODIFY, leaveReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return leaveReasonDescription;
    }

    private LeaveReasonDescription getLeaveReasonDescription(LeaveReason leaveReason,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LeaveReasonDescriptions.fields())
                .from(LeaveReasonDescriptions)
                .where(LeaveReasonDescriptions.LEAVE_REASON.eq(leaveReason.getPrimaryKey()),
                        LeaveReasonDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        LeaveReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveReasonDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<LeaveReasonDescription> getLeaveReasonDescriptionsByLeaveReason(LeaveReason leaveReason,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LeaveReasonDescriptions.fields())
                    .from(LeaveReasonDescriptions)
                    .join(Languages).onKey(LEAVE_REASON_DESCRIPTIONS_LANGUAGE_FK)
                    .where(LeaveReasonDescriptions.LEAVE_REASON.eq(leaveReason.getPrimaryKey()),
                            LeaveReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LeaveReasonDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LeaveReasonDescriptions.fields())
                    .from(LeaveReasonDescriptions)
                    .where(LeaveReasonDescriptions.LEAVE_REASON.eq(leaveReason.getPrimaryKey()),
                            LeaveReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return leaveReasonDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return leaveReasonDescriptionTransferCache.getLeaveReasonDescriptionTransfer(userVisit, leaveReasonDescription);
    }

    public List<LeaveReasonDescriptionTransfer> getLeaveReasonDescriptionTransfersByLeaveReason(UserVisit userVisit, LeaveReason leaveReason) {
        var leaveReasonDescriptions = getLeaveReasonDescriptionsByLeaveReason(leaveReason);
        List<LeaveReasonDescriptionTransfer> leaveReasonDescriptionTransfers = new ArrayList<>(leaveReasonDescriptions.size());

        leaveReasonDescriptions.forEach((leaveReasonDescription) ->
                leaveReasonDescriptionTransfers.add(leaveReasonDescriptionTransferCache.getLeaveReasonDescriptionTransfer(userVisit, leaveReasonDescription))
        );

        return leaveReasonDescriptionTransfers;
    }

    public void updateLeaveReasonDescriptionFromValue(LeaveReasonDescriptionValue leaveReasonDescriptionValue, BasePK updatedBy) {
        if(leaveReasonDescriptionValue.hasBeenModified()) {
            var leaveReasonDescription = leaveReasonDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    leaveReasonDescriptionValue.getPrimaryKey());

            leaveReasonDescription.setThruTime(session.getStartTime());
            leaveReasonDescription.store();

            var leaveReason = leaveReasonDescription.getLeaveReason();
            var language = leaveReasonDescription.getLanguage();
            var description = leaveReasonDescriptionValue.getDescription();

            leaveReasonDescription = leaveReasonDescriptionFactory.create(leaveReason, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(leaveReason.getPrimaryKey(), EventTypes.MODIFY, leaveReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLeaveReasonDescription(LeaveReasonDescription leaveReasonDescription, BasePK deletedBy) {
        leaveReasonDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected LeaveDetailFactory leaveDetailFactory;

    @Inject
    protected LeaveFactory leaveFactory;

    @Inject
    LeaveTransferCache leaveTransferCache;

    public Leave createLeave(Party party, Party companyParty, LeaveType leaveType, LeaveReason leaveReason, Long startTime, Long endTime, Long totalTime,
            BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.LEAVE.name());
        var leaveName = sequenceGeneratorLogic.getNextSequenceValue(sequence);

        return createLeave(leaveName, party, companyParty, leaveType, leaveReason, startTime, endTime, totalTime, createdBy);
    }

    public Leave createLeave(String leaveName, Party party, Party companyParty, LeaveType leaveType, LeaveReason leaveReason, Long startTime, Long endTime,
            Long totalTime, BasePK createdBy) {
        var leave = leaveFactory.create();
        var leaveDetail = leaveDetailFactory.create(leave, leaveName, party, companyParty, leaveType, leaveReason, startTime, endTime,
                totalTime, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        leave = leaveFactory.getEntityFromPK(EntityPermission.READ_WRITE, leave.getPrimaryKey());
        leave.setActiveDetail(leaveDetail);
        leave.setLastDetail(leaveDetail);
        leave.store();

        var leavePK = leave.getPrimaryKey();
        sendEvent(leavePK, EventTypes.CREATE, null, null, createdBy);
        sendEvent(party.getPrimaryKey(), EventTypes.TOUCH, leavePK, EventTypes.CREATE, createdBy);

        return leave;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Leave */
    public Leave getLeaveByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LeavePK(entityInstance.getEntityUniqueId());

        return leaveFactory.getEntityFromPK(entityPermission, pk);
    }

    public Leave getLeaveByEntityInstance(EntityInstance entityInstance) {
        return getLeaveByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Leave getLeaveByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLeaveByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLeavesByParty(Party party) {
        return session.getDslContext()
                .selectCount()
                .from(Leaves)
                .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                .where(LeaveDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLeavesByCompanyParty(Party companyParty) {
        return session.getDslContext()
                .selectCount()
                .from(Leaves)
                .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                .where(LeaveDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLeavesByLeaveType(LeaveType leaveType) {
        return session.getDslContext()
                .selectCount()
                .from(Leaves)
                .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                .where(LeaveDetails.LEAVE_TYPE.eq(leaveType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLeavesByLeaveReason(LeaveReason leaveReason) {
        return session.getDslContext()
                .selectCount()
                .from(Leaves)
                .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                .where(LeaveDetails.LEAVE_REASON.eq(leaveReason.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Leave getLeaveByName(String leaveName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Leaves.fields())
                .from(Leaves)
                .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                .where(LeaveDetails.LEAVE_NAME.eq(leaveName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveFactory.getEntityFromQuery(entityPermission, query);
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

    private List<Leave> getLeavesByParty(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Leaves.fields())
                .from(Leaves)
                .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                .where(LeaveDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LeaveDetails.LEAVE_NAME),
                    LeaveFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return leaveFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Leave> getLeavesByParty(Party party) {
        return getLeavesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByPartyForUpdate(Party party) {
        return getLeavesByParty(party, EntityPermission.READ_WRITE);
    }

    private List<Leave> getLeavesByCompanyParty(Party companyParty, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Leaves.fields())
                    .from(Leaves)
                    .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(LEAVE_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(LeaveDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                    .orderBy(LeaveDetails.LEAVE_NAME),
                    LeaveFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Leaves.fields())
                    .from(Leaves)
                    .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                    .where(LeaveDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                    .forUpdate();
        };

        return leaveFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Leave> getLeavesByCompanyParty(Party companyParty) {
        return getLeavesByCompanyParty(companyParty, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByCompanyPartyForUpdate(Party companyParty) {
        return getLeavesByCompanyParty(companyParty, EntityPermission.READ_WRITE);
    }

    private List<Leave> getLeavesByLeaveType(LeaveType leaveType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Leaves.fields())
                    .from(Leaves)
                    .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(LEAVE_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(LeaveDetails.LEAVE_TYPE.eq(leaveType.getPrimaryKey()))
                    .orderBy(LeaveDetails.LEAVE_NAME),
                    LeaveFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Leaves.fields())
                    .from(Leaves)
                    .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                    .where(LeaveDetails.LEAVE_TYPE.eq(leaveType.getPrimaryKey()))
                    .forUpdate();
        };

        return leaveFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Leave> getLeavesByLeaveType(LeaveType leaveType) {
        return getLeavesByLeaveType(leaveType, EntityPermission.READ_ONLY);
    }

    public List<Leave> getLeavesByLeaveTypeForUpdate(LeaveType leaveType) {
        return getLeavesByLeaveType(leaveType, EntityPermission.READ_WRITE);
    }

    private List<Leave> getLeavesByLeaveReason(LeaveReason leaveReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Leaves.fields())
                    .from(Leaves)
                    .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(LEAVE_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(LeaveDetails.LEAVE_REASON.eq(leaveReason.getPrimaryKey()))
                    .orderBy(LeaveDetails.LEAVE_NAME),
                    LeaveFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Leaves.fields())
                    .from(Leaves)
                    .join(LeaveDetails).onKey(LEAVES_ACTIVE_DETAIL_FK)
                    .where(LeaveDetails.LEAVE_REASON.eq(leaveReason.getPrimaryKey()))
                    .forUpdate();
        };

        return leaveFactory.getEntitiesFromQuery(entityPermission, query);
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
        return leaveTransferCache.getLeaveTransfer(userVisit, leave);
    }

    public List<LeaveTransfer> getLeaveTransfers(UserVisit userVisit, Collection<Leave> leaves) {
        List<LeaveTransfer> leaveTransfers = new ArrayList<>(leaves.size());

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
            var leave = leaveFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     leaveDetailValue.getLeavePK());
            var leaveDetail = leave.getActiveDetailForUpdate();

            leaveDetail.setThruTime(session.getStartTime());
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

            leaveDetail = leaveDetailFactory.create(leavePK, leaveName, partyPK, companyPartyPK, leaveTypePK, leaveReasonPK, startTime,
                    endTime, totalTime, session.getStartTime(), Session.MAX_TIME);

            leave.setActiveDetail(leaveDetail);
            leave.setLastDetail(leaveDetail);

            sendEvent(leavePK, EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(partyPK, EventTypes.TOUCH, leavePK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLeave(Leave leave, BasePK deletedBy) {
        var leaveDetail = leave.getLastDetailForUpdate();
        leaveDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected TerminationReasonDetailFactory terminationReasonDetailFactory;

    @Inject
    protected TerminationReasonFactory terminationReasonFactory;

    @Inject
    TerminationReasonTransferCache terminationReasonTransferCache;

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

        var terminationReason = terminationReasonFactory.create();
        var terminationReasonDetail = terminationReasonDetailFactory.create(
                terminationReason, terminationReasonName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        terminationReason = terminationReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE, terminationReason.getPrimaryKey());
        terminationReason.setActiveDetail(terminationReasonDetail);
        terminationReason.setLastDetail(terminationReasonDetail);
        terminationReason.store();
        
        sendEvent(terminationReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return terminationReason;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TerminationReason */
    public TerminationReason getTerminationReasonByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TerminationReasonPK(entityInstance.getEntityUniqueId());

        return terminationReasonFactory.getEntityFromPK(entityPermission, pk);
    }

    public TerminationReason getTerminationReasonByEntityInstance(EntityInstance entityInstance) {
        return getTerminationReasonByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TerminationReason getTerminationReasonByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTerminationReasonByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTerminationReasons() {
        return session.getDslContext()
                .selectCount()
                .from(TerminationReasons)
                .join(TerminationReasonDetails).onKey(TERMINATION_REASONS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private TerminationReason getTerminationReasonByName(String terminationReasonName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TerminationReasons.fields())
                .from(TerminationReasons)
                .join(TerminationReasonDetails).onKey(TERMINATION_REASONS_ACTIVE_DETAIL_FK)
                .where(TerminationReasonDetails.TERMINATION_REASON_NAME.eq(terminationReasonName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationReasonFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(TerminationReasons.fields())
                .from(TerminationReasons)
                .join(TerminationReasonDetails).onKey(TERMINATION_REASONS_ACTIVE_DETAIL_FK)
                .where(TerminationReasonDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationReasonFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(TerminationReasons.fields())
                .from(TerminationReasons)
                .join(TerminationReasonDetails).onKey(TERMINATION_REASONS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TerminationReasonDetails.SORT_ORDER, TerminationReasonDetails.TERMINATION_REASON_NAME),
                    TerminationReasonFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TerminationReason> getTerminationReasons() {
        return getTerminationReasons(EntityPermission.READ_ONLY);
    }
    
    public List<TerminationReason> getTerminationReasonsForUpdate() {
        return getTerminationReasons(EntityPermission.READ_WRITE);
    }
    
    public TerminationReasonTransfer getTerminationReasonTransfer(UserVisit userVisit, TerminationReason terminationReason) {
        return terminationReasonTransferCache.getTerminationReasonTransfer(userVisit, terminationReason);
    }
    
    public List<TerminationReasonTransfer> getTerminationReasonTransfers(UserVisit userVisit, Collection<TerminationReason> terminationReasons) {
        List<TerminationReasonTransfer> terminationReasonTransfers = new ArrayList<>(terminationReasons.size());
        
        terminationReasons.forEach((terminationReason) ->
                terminationReasonTransfers.add(terminationReasonTransferCache.getTerminationReasonTransfer(userVisit, terminationReason))
        );
        
        return terminationReasonTransfers;
    }
    
    public List<TerminationReasonTransfer> getTerminationReasonTransfers(UserVisit userVisit) {
        return getTerminationReasonTransfers(userVisit, getTerminationReasons());
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
            var terminationReason = terminationReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     terminationReasonDetailValue.getTerminationReasonPK());
            var terminationReasonDetail = terminationReason.getActiveDetailForUpdate();
            
            terminationReasonDetail.setThruTime(session.getStartTime());
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
            
            terminationReasonDetail = terminationReasonDetailFactory.create(terminationReasonPK,
                    terminationReasonName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        terminationReasonDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected TerminationReasonDescriptionFactory terminationReasonDescriptionFactory;

    @Inject
    TerminationReasonDescriptionTransferCache terminationReasonDescriptionTransferCache;

    public TerminationReasonDescription createTerminationReasonDescription(TerminationReason terminationReason,
            Language language, String description, BasePK createdBy) {
        var terminationReasonDescription = terminationReasonDescriptionFactory.create(
                terminationReason, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(terminationReason.getPrimaryKey(), EventTypes.MODIFY, terminationReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return terminationReasonDescription;
    }
    
    private TerminationReasonDescription getTerminationReasonDescription(TerminationReason terminationReason,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TerminationReasonDescriptions.fields())
                .from(TerminationReasonDescriptions)
                .where(TerminationReasonDescriptions.TERMINATION_REASON.eq(terminationReason.getPrimaryKey()),
                        TerminationReasonDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        TerminationReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationReasonDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TerminationReasonDescriptions.fields())
                    .from(TerminationReasonDescriptions)
                    .join(Languages).onKey(TERMINATION_REASON_DESCRIPTIONS_LANGUAGE_FK)
                    .where(TerminationReasonDescriptions.TERMINATION_REASON.eq(terminationReason.getPrimaryKey()),
                            TerminationReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TerminationReasonDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TerminationReasonDescriptions.fields())
                    .from(TerminationReasonDescriptions)
                    .where(TerminationReasonDescriptions.TERMINATION_REASON.eq(terminationReason.getPrimaryKey()),
                            TerminationReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return terminationReasonDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return terminationReasonDescriptionTransferCache.getTerminationReasonDescriptionTransfer(userVisit, terminationReasonDescription);
    }
    
    public List<TerminationReasonDescriptionTransfer> getTerminationReasonDescriptionTransfers(UserVisit userVisit, TerminationReason terminationReason) {
        var terminationReasonDescriptions = getTerminationReasonDescriptionsByTerminationReason(terminationReason);
        List<TerminationReasonDescriptionTransfer> terminationReasonDescriptionTransfers = new ArrayList<>(terminationReasonDescriptions.size());
        
        terminationReasonDescriptions.forEach((terminationReasonDescription) ->
                terminationReasonDescriptionTransfers.add(terminationReasonDescriptionTransferCache.getTerminationReasonDescriptionTransfer(userVisit, terminationReasonDescription))
        );
        
        return terminationReasonDescriptionTransfers;
    }
    
    public void updateTerminationReasonDescriptionFromValue(TerminationReasonDescriptionValue terminationReasonDescriptionValue, BasePK updatedBy) {
        if(terminationReasonDescriptionValue.hasBeenModified()) {
            var terminationReasonDescription = terminationReasonDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, terminationReasonDescriptionValue.getPrimaryKey());
            
            terminationReasonDescription.setThruTime(session.getStartTime());
            terminationReasonDescription.store();

            var terminationReason = terminationReasonDescription.getTerminationReason();
            var language = terminationReasonDescription.getLanguage();
            var description = terminationReasonDescriptionValue.getDescription();
            
            terminationReasonDescription = terminationReasonDescriptionFactory.create(terminationReason, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(terminationReason.getPrimaryKey(), EventTypes.MODIFY, terminationReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTerminationReasonDescription(TerminationReasonDescription terminationReasonDescription, BasePK deletedBy) {
        terminationReasonDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected TerminationTypeDetailFactory terminationTypeDetailFactory;

    @Inject
    protected TerminationTypeFactory terminationTypeFactory;

    @Inject
    TerminationTypeTransferCache terminationTypeTransferCache;

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

        var terminationType = terminationTypeFactory.create();
        var terminationTypeDetail = terminationTypeDetailFactory.create(
                terminationType, terminationTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        terminationType = terminationTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, terminationType.getPrimaryKey());
        terminationType.setActiveDetail(terminationTypeDetail);
        terminationType.setLastDetail(terminationTypeDetail);
        terminationType.store();
        
        sendEvent(terminationType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return terminationType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TerminationType */
    public TerminationType getTerminationTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TerminationTypePK(entityInstance.getEntityUniqueId());

        return terminationTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public TerminationType getTerminationTypeByEntityInstance(EntityInstance entityInstance) {
        return getTerminationTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TerminationType getTerminationTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTerminationTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTerminationTypes() {
        return session.getDslContext()
                .selectCount()
                .from(TerminationTypes)
                .join(TerminationTypeDetails).onKey(TERMINATION_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public TerminationType getTerminationTypeByName(String terminationTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TerminationTypes.fields())
                .from(TerminationTypes)
                .join(TerminationTypeDetails).onKey(TERMINATION_TYPES_ACTIVE_DETAIL_FK)
                .where(TerminationTypeDetails.TERMINATION_TYPE_NAME.eq(terminationTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(TerminationTypes.fields())
                .from(TerminationTypes)
                .join(TerminationTypeDetails).onKey(TERMINATION_TYPES_ACTIVE_DETAIL_FK)
                .where(TerminationTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(TerminationTypes.fields())
                .from(TerminationTypes)
                .join(TerminationTypeDetails).onKey(TERMINATION_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TerminationTypeDetails.SORT_ORDER, TerminationTypeDetails.TERMINATION_TYPE_NAME),
                    TerminationTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TerminationType> getTerminationTypes() {
        return getTerminationTypes(EntityPermission.READ_ONLY);
    }
    
    public List<TerminationType> getTerminationTypesForUpdate() {
        return getTerminationTypes(EntityPermission.READ_WRITE);
    }
    
    public TerminationTypeTransfer getTerminationTypeTransfer(UserVisit userVisit, TerminationType terminationType) {
        return terminationTypeTransferCache.getTerminationTypeTransfer(userVisit, terminationType);
    }
    
    public List<TerminationTypeTransfer> getTerminationTypeTransfers(UserVisit userVisit, Collection<TerminationType> terminationTypes) {
        List<TerminationTypeTransfer> terminationTypeTransfers = new ArrayList<>(terminationTypes.size());

        terminationTypes.forEach((terminationType) ->
                terminationTypeTransfers.add(terminationTypeTransferCache.getTerminationTypeTransfer(userVisit, terminationType))
        );

        return terminationTypeTransfers;
    }

    public List<TerminationTypeTransfer> getTerminationTypeTransfers(UserVisit userVisit) {
        return getTerminationTypeTransfers(userVisit, getTerminationTypes());
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
            var terminationType = terminationTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     terminationTypeDetailValue.getTerminationTypePK());
            var terminationTypeDetail = terminationType.getActiveDetailForUpdate();
            
            terminationTypeDetail.setThruTime(session.getStartTime());
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
            
            terminationTypeDetail = terminationTypeDetailFactory.create(terminationTypePK,
                    terminationTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        terminationTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected TerminationTypeDescriptionFactory terminationTypeDescriptionFactory;

    @Inject
    TerminationTypeDescriptionTransferCache terminationTypeDescriptionTransferCache;

    public TerminationTypeDescription createTerminationTypeDescription(TerminationType terminationType,
            Language language, String description, BasePK createdBy) {
        var terminationTypeDescription = terminationTypeDescriptionFactory.create(
                terminationType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(terminationType.getPrimaryKey(), EventTypes.MODIFY, terminationTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return terminationTypeDescription;
    }
    
    private TerminationTypeDescription getTerminationTypeDescription(TerminationType terminationType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TerminationTypeDescriptions.fields())
                .from(TerminationTypeDescriptions)
                .where(TerminationTypeDescriptions.TERMINATION_TYPE.eq(terminationType.getPrimaryKey()),
                        TerminationTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        TerminationTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return terminationTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TerminationTypeDescriptions.fields())
                    .from(TerminationTypeDescriptions)
                    .join(Languages).onKey(TERMINATION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(TerminationTypeDescriptions.TERMINATION_TYPE.eq(terminationType.getPrimaryKey()),
                            TerminationTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TerminationTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TerminationTypeDescriptions.fields())
                    .from(TerminationTypeDescriptions)
                    .where(TerminationTypeDescriptions.TERMINATION_TYPE.eq(terminationType.getPrimaryKey()),
                            TerminationTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return terminationTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return terminationTypeDescriptionTransferCache.getTerminationTypeDescriptionTransfer(userVisit, terminationTypeDescription);
    }
    
    public List<TerminationTypeDescriptionTransfer> getTerminationTypeDescriptionTransfers(UserVisit userVisit, TerminationType terminationType) {
        var terminationTypeDescriptions = getTerminationTypeDescriptionsByTerminationType(terminationType);
        List<TerminationTypeDescriptionTransfer> terminationTypeDescriptionTransfers = new ArrayList<>(terminationTypeDescriptions.size());
        
        terminationTypeDescriptions.forEach((terminationTypeDescription) ->
                terminationTypeDescriptionTransfers.add(terminationTypeDescriptionTransferCache.getTerminationTypeDescriptionTransfer(userVisit, terminationTypeDescription))
        );
        
        return terminationTypeDescriptionTransfers;
    }
    
    public void updateTerminationTypeDescriptionFromValue(TerminationTypeDescriptionValue terminationTypeDescriptionValue, BasePK updatedBy) {
        if(terminationTypeDescriptionValue.hasBeenModified()) {
            var terminationTypeDescription = terminationTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, terminationTypeDescriptionValue.getPrimaryKey());
            
            terminationTypeDescription.setThruTime(session.getStartTime());
            terminationTypeDescription.store();

            var terminationType = terminationTypeDescription.getTerminationType();
            var language = terminationTypeDescription.getLanguage();
            var description = terminationTypeDescriptionValue.getDescription();
            
            terminationTypeDescription = terminationTypeDescriptionFactory.create(terminationType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(terminationType.getPrimaryKey(), EventTypes.MODIFY, terminationTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTerminationTypeDescription(TerminationTypeDescription terminationTypeDescription, BasePK deletedBy) {
        terminationTypeDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EmploymentDetailFactory employmentDetailFactory;

    @Inject
    protected EmploymentFactory employmentFactory;

    @Inject
    EmploymentTransferCache employmentTransferCache;

    public Employment createEmployment(Party party, Party companyParty, Long startTime, Long endTime, TerminationType terminationType,
            TerminationReason terminationReason, BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.EMPLOYMENT.name());
        var employmentName = sequenceGeneratorLogic.getNextSequenceValue(sequence);

        return createEmployment(employmentName, party, companyParty, startTime, endTime, terminationType, terminationReason, createdBy);
    }

    public Employment createEmployment(String employmentName, Party party, Party companyParty, Long startTime, Long endTime,
            TerminationType terminationType, TerminationReason terminationReason, BasePK createdBy) {
        var employment = employmentFactory.create();
        var employmentDetail = employmentDetailFactory.create(employment, employmentName, party, companyParty, startTime,
                endTime, terminationType, terminationReason, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        employment = employmentFactory.getEntityFromPK(EntityPermission.READ_WRITE, employment.getPrimaryKey());
        employment.setActiveDetail(employmentDetail);
        employment.setLastDetail(employmentDetail);
        employment.store();

        var employmentPK = employment.getPrimaryKey();
        sendEvent(employmentPK, EventTypes.CREATE, null, null, createdBy);
        sendEvent(party.getPrimaryKey(), EventTypes.TOUCH, employmentPK, EventTypes.CREATE, createdBy);

        return employment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Employment */
    public Employment getEmploymentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EmploymentPK(entityInstance.getEntityUniqueId());

        return employmentFactory.getEntityFromPK(entityPermission, pk);
    }

    public Employment getEmploymentByEntityInstance(EntityInstance entityInstance) {
        return getEmploymentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Employment getEmploymentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEmploymentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEmploymentsByParty(Party party) {
        return session.getDslContext()
                .selectCount()
                .from(Employments)
                .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                .where(EmploymentDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEmploymentsByCompanyParty(Party companyParty) {
        return session.getDslContext()
                .selectCount()
                .from(Employments)
                .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                .where(EmploymentDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEmploymentsByTerminationType(TerminationType terminationType) {
        return session.getDslContext()
                .selectCount()
                .from(Employments)
                .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                .where(EmploymentDetails.TERMINATION_TYPE.eq(terminationType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEmploymentsByTerminationReason(TerminationReason terminationReason) {
        return session.getDslContext()
                .selectCount()
                .from(Employments)
                .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                .where(EmploymentDetails.TERMINATION_REASON.eq(terminationReason.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Employment getEmploymentByName(String employmentName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Employments.fields())
                .from(Employments)
                .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                .where(EmploymentDetails.EMPLOYMENT_NAME.eq(employmentName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return employmentFactory.getEntityFromQuery(entityPermission, query);
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

    private List<Employment> getEmploymentsByParty(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Employments.fields())
                .from(Employments)
                .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                .where(EmploymentDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(EmploymentDetails.EMPLOYMENT_NAME),
                    EmploymentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return employmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Employment> getEmploymentsByParty(Party party) {
        return getEmploymentsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByPartyForUpdate(Party party) {
        return getEmploymentsByParty(party, EntityPermission.READ_WRITE);
    }

    private List<Employment> getEmploymentsByCompanyParty(Party companyParty, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Employments.fields())
                    .from(Employments)
                    .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(EMPLOYMENT_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(EmploymentDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                    .orderBy(EmploymentDetails.EMPLOYMENT_NAME),
                    EmploymentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Employments.fields())
                    .from(Employments)
                    .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                    .where(EmploymentDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                    .forUpdate();
        };

        return employmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Employment> getEmploymentsByCompanyParty(Party companyParty) {
        return getEmploymentsByCompanyParty(companyParty, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByCompanyPartyForUpdate(Party companyParty) {
        return getEmploymentsByCompanyParty(companyParty, EntityPermission.READ_WRITE);
    }

    private List<Employment> getEmploymentsByTerminationType(TerminationType terminationType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Employments.fields())
                    .from(Employments)
                    .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(EMPLOYMENT_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(EmploymentDetails.TERMINATION_TYPE.eq(terminationType.getPrimaryKey()))
                    .orderBy(EmploymentDetails.EMPLOYMENT_NAME),
                    EmploymentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Employments.fields())
                    .from(Employments)
                    .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                    .where(EmploymentDetails.TERMINATION_TYPE.eq(terminationType.getPrimaryKey()))
                    .forUpdate();
        };

        return employmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Employment> getEmploymentsByTerminationType(TerminationType terminationType) {
        return getEmploymentsByTerminationType(terminationType, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByTerminationTypeForUpdate(TerminationType terminationType) {
        return getEmploymentsByTerminationType(terminationType, EntityPermission.READ_WRITE);
    }

    private List<Employment> getEmploymentsByTerminationReason(TerminationReason terminationReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Employments.fields())
                    .from(Employments)
                    .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(EMPLOYMENT_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(EmploymentDetails.TERMINATION_REASON.eq(terminationReason.getPrimaryKey()))
                    .orderBy(EmploymentDetails.EMPLOYMENT_NAME),
                    EmploymentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Employments.fields())
                    .from(Employments)
                    .join(EmploymentDetails).onKey(EMPLOYMENTS_ACTIVE_DETAIL_FK)
                    .where(EmploymentDetails.TERMINATION_REASON.eq(terminationReason.getPrimaryKey()))
                    .forUpdate();
        };

        return employmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Employment> getEmploymentsByTerminationReason(TerminationReason terminationReason) {
        return getEmploymentsByTerminationReason(terminationReason, EntityPermission.READ_ONLY);
    }

    public List<Employment> getEmploymentsByTerminationReasonForUpdate(TerminationReason terminationReason) {
        return getEmploymentsByTerminationReason(terminationReason, EntityPermission.READ_WRITE);
    }

    public EmploymentTransfer getEmploymentTransfer(UserVisit userVisit, Employment employment) {
        return employmentTransferCache.getEmploymentTransfer(userVisit, employment);
    }

    public List<EmploymentTransfer> getEmploymentTransfers(UserVisit userVisit, Collection<Employment> employments) {
        List<EmploymentTransfer> employmentTransfers = new ArrayList<>(employments.size());

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
            var employment = employmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     employmentDetailValue.getEmploymentPK());
            var employmentDetail = employment.getActiveDetailForUpdate();

            employmentDetail.setThruTime(session.getStartTime());
            employmentDetail.store();

            var employmentPK = employmentDetail.getEmploymentPK(); // Not updated
            var employmentName = employmentDetail.getEmploymentName(); // Not updated
            var partyPK = employmentDetail.getPartyPK(); // Not updated
            var companyPartyPK = employmentDetailValue.getCompanyPartyPK();
            var startTime = employmentDetailValue.getStartTime();
            var endTime = employmentDetailValue.getEndTime();
            var terminationTypePK = employmentDetailValue.getTerminationTypePK();
            var terminationReasonPK = employmentDetailValue.getTerminationReasonPK();

            employmentDetail = employmentDetailFactory.create(employmentPK, employmentName, partyPK, companyPartyPK, startTime, endTime,
                    terminationTypePK, terminationReasonPK, session.getStartTime(), Session.MAX_TIME);

            employment.setActiveDetail(employmentDetail);
            employment.setLastDetail(employmentDetail);

            sendEvent(employmentPK, EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(partyPK, EventTypes.TOUCH, employmentPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEmployment(Employment employment, BasePK deletedBy) {
        var employmentDetail = employment.getLastDetailForUpdate();
        employmentDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected EmployeeTypeDetailFactory employeeTypeDetailFactory;

    @Inject
    protected EmployeeTypeFactory employeeTypeFactory;

    @Inject
    EmployeeTypeTransferCache employeeTypeTransferCache;

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

        var employeeType = employeeTypeFactory.create();
        var employeeTypeDetail = employeeTypeDetailFactory.create(employeeType,
                employeeTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        employeeType = employeeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

        return employeeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EmployeeType getEmployeeTypeByEntityInstance(EntityInstance entityInstance) {
        return getEmployeeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EmployeeType getEmployeeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEmployeeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEmployeeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(EmployeeTypes)
                .join(EmployeeTypeDetails).onKey(EMPLOYEE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<EmployeeType> getEmployeeTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EmployeeTypes.fields())
                .from(EmployeeTypes)
                .join(EmployeeTypeDetails).onKey(EMPLOYEE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(EmployeeTypeDetails.SORT_ORDER, EmployeeTypeDetails.EMPLOYEE_TYPE_NAME),
                    EmployeeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return employeeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EmployeeType> getEmployeeTypes() {
        return getEmployeeTypes(EntityPermission.READ_ONLY);
    }
    
    public List<EmployeeType> getEmployeeTypesForUpdate() {
        return getEmployeeTypes(EntityPermission.READ_WRITE);
    }
    
    private EmployeeType getDefaultEmployeeType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EmployeeTypes.fields())
                .from(EmployeeTypes)
                .join(EmployeeTypeDetails).onKey(EMPLOYEE_TYPES_ACTIVE_DETAIL_FK)
                .where(EmployeeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return employeeTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(EmployeeTypes.fields())
                .from(EmployeeTypes)
                .join(EmployeeTypeDetails).onKey(EMPLOYEE_TYPES_ACTIVE_DETAIL_FK)
                .where(EmployeeTypeDetails.EMPLOYEE_TYPE_NAME.eq(employeeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return employeeTypeFactory.getEntityFromQuery(entityPermission, query);
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
        return employeeTypeTransferCache.getEmployeeTypeTransfer(userVisit, employeeType);
    }
    
    public List<EmployeeTypeTransfer> getEmployeeTypeTransfers(UserVisit userVisit) {
        var employeeTypes = getEmployeeTypes();
        List<EmployeeTypeTransfer> employeeTypeTransfers = null;
        
        if(employeeTypes != null) {
            
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
            var employeeType = employeeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     employeeTypeDetailValue.getEmployeeTypePK());
            var employeeTypeDetail = employeeType.getActiveDetailForUpdate();
            
            employeeTypeDetail.setThruTime(session.getStartTime());
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
            
            employeeTypeDetail = employeeTypeDetailFactory.create(employeeTypePK, employeeTypeName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        employeeTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected EmployeeTypeDescriptionFactory employeeTypeDescriptionFactory;

    @Inject
    EmployeeTypeDescriptionTransferCache employeeTypeDescriptionTransferCache;

    public EmployeeTypeDescription createEmployeeTypeDescription(EmployeeType employeeType, Language language, String description,
            BasePK createdBy) {
        var employeeTypeDescription = employeeTypeDescriptionFactory.create(employeeType,
                language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(employeeType.getPrimaryKey(), EventTypes.MODIFY, employeeTypeDescription.getPrimaryKey(),
                null, createdBy);
        
        return employeeTypeDescription;
    }
    
    private EmployeeTypeDescription getEmployeeTypeDescription(EmployeeType employeeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EmployeeTypeDescriptions.fields())
                .from(EmployeeTypeDescriptions)
                .where(EmployeeTypeDescriptions.EMPLOYEE_TYPE.eq(employeeType.getPrimaryKey()),
                        EmployeeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EmployeeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return employeeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EmployeeTypeDescriptions.fields())
                    .from(EmployeeTypeDescriptions)
                    .join(Languages).onKey(EMPLOYEE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EmployeeTypeDescriptions.EMPLOYEE_TYPE.eq(employeeType.getPrimaryKey()),
                            EmployeeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EmployeeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EmployeeTypeDescriptions.fields())
                    .from(EmployeeTypeDescriptions)
                    .where(EmployeeTypeDescriptions.EMPLOYEE_TYPE.eq(employeeType.getPrimaryKey()),
                            EmployeeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return employeeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return employeeTypeDescriptionTransferCache.getEmployeeTypeDescriptionTransfer(userVisit, employeeTypeDescription);
    }
    
    public List<EmployeeTypeDescriptionTransfer> getEmployeeTypeDescriptionTransfers(UserVisit userVisit, EmployeeType employeeType) {
        var employeeTypeDescriptions = getEmployeeTypeDescriptionsByEmployeeType(employeeType);
        List<EmployeeTypeDescriptionTransfer> employeeTypeDescriptionTransfers = null;
        
        if(employeeTypeDescriptions != null) {
            
            employeeTypeDescriptionTransfers = new ArrayList<>(employeeTypeDescriptions.size());
            
            for(var employeeTypeDescription : employeeTypeDescriptions) {
                employeeTypeDescriptionTransfers.add(employeeTypeDescriptionTransferCache.getEmployeeTypeDescriptionTransfer(userVisit, employeeTypeDescription));
            }
        }
        
        return employeeTypeDescriptionTransfers;
    }
    
    public void updateEmployeeTypeDescriptionFromValue(EmployeeTypeDescriptionValue employeeTypeDescriptionValue, BasePK updatedBy) {
        if(employeeTypeDescriptionValue.hasBeenModified()) {
            var employeeTypeDescription = employeeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     employeeTypeDescriptionValue.getPrimaryKey());
            
            employeeTypeDescription.setThruTime(session.getStartTime());
            employeeTypeDescription.store();

            var employeeType = employeeTypeDescription.getEmployeeType();
            var language = employeeTypeDescription.getLanguage();
            var description = employeeTypeDescriptionValue.getDescription();
            
            employeeTypeDescription = employeeTypeDescriptionFactory.create(employeeType, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(employeeType.getPrimaryKey(), EventTypes.MODIFY, employeeTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteEmployeeTypeDescription(EmployeeTypeDescription employeeTypeDescription, BasePK deletedBy) {
        employeeTypeDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PartyEmployeeFactory partyEmployeeFactory;

    @Inject
    EmployeeTransferCache employeeTransferCache;

    public PartyEmployee createPartyEmployee(Party party, String partyEmployeeName, EmployeeType employeeType, BasePK createdBy) {
        var partyEmployee = partyEmployeeFactory.create(party, partyEmployeeName, employeeType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyEmployee.getPrimaryKey(), null, createdBy);
        
        return partyEmployee;
    }

    public long countPartyEmployees() {
        return session.getDslContext()
                .selectCount()
                .from(PartyEmployees)
                .where(PartyEmployees.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<PartyEmployee> getPartyEmployees() {
        var query = session.applyLimit(session.getDslContext()
                .select(PartyEmployees.fields())
                .from(PartyEmployees)
                .where(PartyEmployees.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(PartyEmployees.PARTY_EMPLOYEE_NAME),
                PartyEmployeeFactory.class);

        return partyEmployeeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private PartyEmployee getPartyEmployee(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyEmployees.fields())
                .from(PartyEmployees)
                .where(PartyEmployees.PARTY.eq(party.getPrimaryKey()),
                        PartyEmployees.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyEmployeeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyEmployee getPartyEmployee(Party party) {
        return getPartyEmployee(party, EntityPermission.READ_ONLY);
    }
    
    public PartyEmployee getPartyEmployeeForUpdate(Party party) {
        return getPartyEmployee(party, EntityPermission.READ_WRITE);
    }
    
    public PartyEmployee getPartyEmployeeByName(String partyEmployeeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyEmployees.fields())
                .from(PartyEmployees)
                .where(PartyEmployees.PARTY_EMPLOYEE_NAME.eq(partyEmployeeName),
                        PartyEmployees.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyEmployeeFactory.getEntityFromQuery(entityPermission, query);
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

        partyEmployees.stream().map((partyEmployee) -> partyEmployee.getParty()).forEach((party) -> employeeTransfers.add(employeeTransferCache.getTransfer(userVisit, party)));

        return employeeTransfers;
    }

    public List<EmployeeTransfer> getEmployeeTransfers(UserVisit userVisit) {
        return getEmployeeTransfers(userVisit, getPartyEmployees());
    }

    public EmployeeTransfer getEmployeeTransfer(UserVisit userVisit, PartyEmployee partyEmployee) {
        return employeeTransferCache.getTransfer(userVisit, partyEmployee);
    }

    public EmployeeTransfer getEmployeeTransfer(UserVisit userVisit, Party party) {
        return employeeTransferCache.getTransfer(userVisit, party);
    }
    
    public void updatePartyEmployeeFromValue(PartyEmployeeValue partyEmployeeValue, BasePK updatedBy) {
        if(partyEmployeeValue.hasBeenModified()) {
            var partyEmployee = partyEmployeeFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyEmployeeValue.getPrimaryKey());

            partyEmployee.setThruTime(session.getStartTime());
            partyEmployee.store();

            var partyPK = partyEmployee.getPartyPK(); // Not updated
            var partyEmployeeName = partyEmployeeValue.getPartyEmployeeName();
            var employeeTypePK = partyEmployeeValue.getEmployeeTypePK();

            partyEmployee = partyEmployeeFactory.create(partyPK, partyEmployeeName, employeeTypePK, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, partyEmployee.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyEmployee(PartyEmployee partyEmployee, BasePK deletedBy) {
        partyEmployee.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PartyResponsibilityFactory partyResponsibilityFactory;

    @Inject
    PartyResponsibilityTransferCache partyResponsibilityTransferCache;

    public PartyResponsibility createPartyResponsibility(Party party, ResponsibilityType responsibilityType, BasePK createdBy) {
        var partyResponsibility = partyResponsibilityFactory.create(party, responsibilityType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyResponsibility.getPrimaryKey(), null, createdBy);
        
        return partyResponsibility;
    }

    public long countPartyResponsibilitiesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyResponsibilities)
                .where(PartyResponsibilities.PARTY.eq(party.getPrimaryKey()),
                        PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyResponsibilitiesByResponsibilityType(final ResponsibilityType responsibilityType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyResponsibilities)
                .where(PartyResponsibilities.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                        PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyResponsibility getPartyResponsibility(Party party, ResponsibilityType responsibilityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyResponsibilities.fields())
                .from(PartyResponsibilities)
                .where(PartyResponsibilities.PARTY.eq(party.getPrimaryKey()),
                        PartyResponsibilities.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                        PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyResponsibilityFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyResponsibilities.fields())
                    .from(PartyResponsibilities)
                    .join(ResponsibilityTypes).onKey(PARTY_RESPONSIBILITIES_RESPONSIBILITY_TYPE_FK)
                    .join(ResponsibilityTypeDetails).onKey(RESPONSIBILITY_TYPES_LAST_DETAIL_FK)
                    .where(PartyResponsibilities.PARTY.eq(party.getPrimaryKey()),
                            PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ResponsibilityTypeDetails.SORT_ORDER, ResponsibilityTypeDetails.RESPONSIBILITY_TYPE_NAME),
                    PartyResponsibilityFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyResponsibilities.fields())
                    .from(PartyResponsibilities)
                    .where(PartyResponsibilities.PARTY.eq(party.getPrimaryKey()),
                            PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyResponsibilityFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByParty(Party party) {
        return getPartyResponsibilitiesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByPartyForUpdate(Party party) {
        return getPartyResponsibilitiesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyResponsibility> getPartyResponsibilitiesByResponsibilityType(ResponsibilityType responsibilityType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyResponsibilities.fields())
                    .from(PartyResponsibilities)
                    .join(Parties).onKey(PARTY_RESPONSIBILITIES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyResponsibilities.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                            PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyResponsibilityFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyResponsibilities.fields())
                    .from(PartyResponsibilities)
                    .where(PartyResponsibilities.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                            PartyResponsibilities.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyResponsibilityFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByResponsibilityType(ResponsibilityType responsibilityType) {
        return getPartyResponsibilitiesByResponsibilityType(responsibilityType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyResponsibility> getPartyResponsibilitiesByResponsibilityTypeForUpdate(ResponsibilityType responsibilityType) {
        return getPartyResponsibilitiesByResponsibilityType(responsibilityType, EntityPermission.READ_WRITE);
    }
    
    public PartyResponsibilityTransfer getPartyResponsibilityTransfer(UserVisit userVisit, Party party, ResponsibilityType responsibilityType) {
        var partyResponsibility = getPartyResponsibility(party, responsibilityType);
        
        return partyResponsibility == null? null: partyResponsibilityTransferCache.getPartyResponsibilityTransfer(userVisit, partyResponsibility);
    }
    
    public PartyResponsibilityTransfer getPartyResponsibilityTransfer(UserVisit userVisit, PartyResponsibility partyResponsibility) {
        return partyResponsibilityTransferCache.getPartyResponsibilityTransfer(userVisit, partyResponsibility);
    }
    
    public List<PartyResponsibilityTransfer> getPartyResponsibilityTransfers(UserVisit userVisit, Collection<PartyResponsibility> partyResponsibilities) {
        List<PartyResponsibilityTransfer> partyResponsibilityTransfers = new ArrayList<>(partyResponsibilities.size());
        
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
        partyResponsibility.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PartySkillFactory partySkillFactory;

    @Inject
    PartySkillTransferCache partySkillTransferCache;

    public PartySkill createPartySkill(Party party, SkillType skillType, BasePK createdBy) {
        var partySkill = partySkillFactory.create(party, skillType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partySkill.getPrimaryKey(), null, createdBy);
        
        return partySkill;
    }

    public long countPartySkillsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartySkills)
                .where(PartySkills.PARTY.eq(party.getPrimaryKey()),
                        PartySkills.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartySkillsBySkillType(final SkillType skillType) {
        return session.getDslContext()
                .selectCount()
                .from(PartySkills)
                .where(PartySkills.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                        PartySkills.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartySkill getPartySkill(Party party, SkillType skillType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySkills.fields())
                .from(PartySkills)
                .where(PartySkills.PARTY.eq(party.getPrimaryKey()),
                        PartySkills.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                        PartySkills.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySkillFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySkills.fields())
                    .from(PartySkills)
                    .join(SkillTypes).onKey(PARTY_SKILLS_SKILL_TYPE_FK)
                    .join(SkillTypeDetails).onKey(SKILL_TYPES_LAST_DETAIL_FK)
                    .where(PartySkills.PARTY.eq(party.getPrimaryKey()),
                            PartySkills.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SkillTypeDetails.SORT_ORDER, SkillTypeDetails.SKILL_TYPE_NAME),
                    PartySkillFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySkills.fields())
                    .from(PartySkills)
                    .where(PartySkills.PARTY.eq(party.getPrimaryKey()),
                            PartySkills.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySkillFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartySkill> getPartySkillsByParty(Party party) {
        return getPartySkillsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartySkill> getPartySkillsByPartyForUpdate(Party party) {
        return getPartySkillsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartySkill> getPartySkillsBySkillType(SkillType skillType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySkills.fields())
                    .from(PartySkills)
                    .join(Parties).onKey(PARTY_SKILLS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartySkills.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                            PartySkills.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartySkillFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySkills.fields())
                    .from(PartySkills)
                    .where(PartySkills.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                            PartySkills.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySkillFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartySkill> getPartySkillsBySkillType(SkillType skillType) {
        return getPartySkillsBySkillType(skillType, EntityPermission.READ_ONLY);
    }
    
    public List<PartySkill> getPartySkillsBySkillTypeForUpdate(SkillType skillType) {
        return getPartySkillsBySkillType(skillType, EntityPermission.READ_WRITE);
    }
    
    public PartySkillTransfer getPartySkillTransfer(UserVisit userVisit, Party party, SkillType skillType) {
        var partySkill = getPartySkill(party, skillType);
        
        return partySkill == null? null: partySkillTransferCache.getPartySkillTransfer(userVisit, partySkill);
    }
    
    public PartySkillTransfer getPartySkillTransfer(UserVisit userVisit, PartySkill partySkill) {
        return partySkillTransferCache.getPartySkillTransfer(userVisit, partySkill);
    }
    
    public List<PartySkillTransfer> getPartySkillTransfers(UserVisit userVisit, Collection<PartySkill> partySkills) {
        List<PartySkillTransfer> partySkillTransfers = new ArrayList<>(partySkills.size());
        
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
        partySkill.setThruTime(session.getStartTime());
        
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

    private List<Long> getEmployeeIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<EmployeeResultTransfer> getEmployeeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getEmployeeIdsFromUserVisitSearch(userVisitSearch);
        var employeeResultTransfers = new ArrayList<EmployeeResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeEmployee = options != null && options.contains(SearchOptions.EmployeeResultIncludeEmployee);

        for(var entityUniqueId : entityIds) {
            var party = partyControl.getPartyByPK(new PartyPK(entityUniqueId));

            employeeResultTransfers.add(new EmployeeResultTransfer(party.getLastDetail().getPartyName(),
                    includeEmployee ? getEmployeeTransfer(userVisit, party) : null));
        }

        return employeeResultTransfers;
    }

    public List<EmployeeObject> getEmployeeObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var employeeObjects = new ArrayList<EmployeeObject>();

        for(var entityUniqueId : getEmployeeIdsFromUserVisitSearch(userVisitSearch)) {
            var party = partyControl.getPartyByPK(new PartyPK(entityUniqueId));

            employeeObjects.add(new EmployeeObject(party));
        }

        return employeeObjects;
    }

}
