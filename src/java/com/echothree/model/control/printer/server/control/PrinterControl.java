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

package com.echothree.model.control.printer.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.printer.common.choice.PrinterGroupChoicesBean;
import com.echothree.model.control.printer.common.choice.PrinterGroupJobStatusChoicesBean;
import com.echothree.model.control.printer.common.choice.PrinterGroupStatusChoicesBean;
import com.echothree.model.control.printer.common.choice.PrinterGroupUseTypeChoicesBean;
import com.echothree.model.control.printer.common.choice.PrinterStatusChoicesBean;
import com.echothree.model.control.printer.common.transfer.PartyPrinterGroupUseTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterDescriptionTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupDescriptionTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupJobTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupUseTypeDescriptionTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupUseTypeTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterTransfer;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.printer.common.workflow.PrinterGroupStatusConstants;
import com.echothree.model.control.printer.common.workflow.PrinterStatusConstants;
import com.echothree.model.control.printer.server.transfer.PartyPrinterGroupUseTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterDescriptionTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterGroupDescriptionTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterGroupJobTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterGroupTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterGroupUseTypeDescriptionTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterGroupUseTypeTransferCache;
import com.echothree.model.control.printer.server.transfer.PrinterTransferCache;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.printer.common.pk.PrinterGroupJobPK;
import com.echothree.model.data.printer.common.pk.PrinterGroupPK;
import com.echothree.model.data.printer.common.pk.PrinterGroupUseTypePK;
import com.echothree.model.data.printer.common.pk.PrinterPK;
import com.echothree.model.data.printer.server.entity.PartyPrinterGroupUse;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.printer.server.entity.PrinterDescription;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupDescription;
import com.echothree.model.data.printer.server.entity.PrinterGroupJob;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseTypeDescription;
import com.echothree.model.data.printer.server.factory.PartyPrinterGroupUseFactory;
import com.echothree.model.data.printer.server.factory.PrinterDescriptionFactory;
import com.echothree.model.data.printer.server.factory.PrinterDetailFactory;
import com.echothree.model.data.printer.server.factory.PrinterFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupDescriptionFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupDetailFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupJobDetailFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupJobFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupUseTypeDescriptionFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupUseTypeDetailFactory;
import com.echothree.model.data.printer.server.factory.PrinterGroupUseTypeFactory;
import com.echothree.model.data.printer.server.value.PartyPrinterGroupUseValue;
import com.echothree.model.data.printer.server.value.PrinterDescriptionValue;
import com.echothree.model.data.printer.server.value.PrinterDetailValue;
import com.echothree.model.data.printer.server.value.PrinterGroupDescriptionValue;
import com.echothree.model.data.printer.server.value.PrinterGroupDetailValue;
import com.echothree.model.data.printer.server.value.PrinterGroupJobDetailValue;
import com.echothree.model.data.printer.server.value.PrinterGroupUseTypeDescriptionValue;
import com.echothree.model.data.printer.server.value.PrinterGroupUseTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PARTY_PRINTER_GROUP_USES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PARTY_PRINTER_GROUP_USES_PRINTER_GROUP_USE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_GROUP_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_GROUP_JOBS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_GROUP_USE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_GROUP_USE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.printer.PrinterForeignKeys.PRINTER_GROUP_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.printer.PartyPrinterGroupUses.PartyPrinterGroupUses;
import static com.echothree.model.jooq.server.tables.printer.PrinterDescriptions.PrinterDescriptions;
import static com.echothree.model.jooq.server.tables.printer.PrinterDetails.PrinterDetails;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupDescriptions.PrinterGroupDescriptions;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupDetails.PrinterGroupDetails;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupJobDetails.PrinterGroupJobDetails;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupJobs.PrinterGroupJobs;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupUseTypeDescriptions.PrinterGroupUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupUseTypeDetails.PrinterGroupUseTypeDetails;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroupUseTypes.PrinterGroupUseTypes;
import static com.echothree.model.jooq.server.tables.printer.PrinterGroups.PrinterGroups;
import static com.echothree.model.jooq.server.tables.printer.Printers.Printers;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityStatuses.WorkflowEntityStatuses;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class PrinterControl
        extends BaseModelControl {
    
    @Inject
    private DocumentControl documentControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    private SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of PrinterControl */
    protected PrinterControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Printer Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected PrinterGroupDetailFactory printerGroupDetailFactory;

    @Inject
    private PrinterGroupFactory printerGroupFactory;

    @Inject
    private PrinterGroupTransferCache printerGroupTransferCache;

    public PrinterGroup createPrinterGroup(String printerGroupName, Long keepPrintedJobsTime, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPrinterGroup = getDefaultPrinterGroup();
        var defaultFound = defaultPrinterGroup != null;

        if(defaultFound && isDefault) {
            var defaultPrinterGroupDetailValue = getDefaultPrinterGroupDetailValueForUpdate();

            defaultPrinterGroupDetailValue.setIsDefault(false);
            updatePrinterGroupFromValue(defaultPrinterGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var printerGroup = printerGroupFactory.create();
        var printerGroupDetail = printerGroupDetailFactory.create(printerGroup, printerGroupName, keepPrintedJobsTime, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        printerGroup = printerGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                printerGroup.getPrimaryKey());
        printerGroup.setActiveDetail(printerGroupDetail);
        printerGroup.setLastDetail(printerGroupDetail);
        printerGroup.store();

        sendEvent(printerGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printerGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PrinterGroup */
    public PrinterGroup getPrinterGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PrinterGroupPK(entityInstance.getEntityUniqueId());

        return printerGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public PrinterGroup getPrinterGroupByEntityInstance(EntityInstance entityInstance) {
        return getPrinterGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PrinterGroup getPrinterGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPrinterGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPrinterGroups() {
        return session.getDslContext()
                .selectCount()
                .from(PrinterGroups)
                .join(PrinterGroupDetails).onKey(PRINTER_GROUPS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PrinterGroup getPrinterGroupByName(String printerGroupName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroups.fields())
                .from(PrinterGroups)
                .join(PrinterGroupDetails).onKey(PRINTER_GROUPS_ACTIVE_DETAIL_FK)
                .where(PrinterGroupDetails.PRINTER_GROUP_NAME.eq(printerGroupName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroup getPrinterGroupByName(String printerGroupName) {
        return getPrinterGroupByName(printerGroupName, EntityPermission.READ_ONLY);
    }

    public PrinterGroup getPrinterGroupByNameForUpdate(String printerGroupName) {
        return getPrinterGroupByName(printerGroupName, EntityPermission.READ_WRITE);
    }

    public PrinterGroupDetailValue getPrinterGroupDetailValueForUpdate(PrinterGroup printerGroup) {
        return printerGroup == null? null: printerGroup.getLastDetailForUpdate().getPrinterGroupDetailValue().clone();
    }

    public PrinterGroupDetailValue getPrinterGroupDetailValueByNameForUpdate(String printerGroupName) {
        return getPrinterGroupDetailValueForUpdate(getPrinterGroupByNameForUpdate(printerGroupName));
    }

    private PrinterGroup getDefaultPrinterGroup(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroups.fields())
                .from(PrinterGroups)
                .join(PrinterGroupDetails).onKey(PRINTER_GROUPS_ACTIVE_DETAIL_FK)
                .where(PrinterGroupDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroup getDefaultPrinterGroup() {
        return getDefaultPrinterGroup(EntityPermission.READ_ONLY);
    }

    public PrinterGroup getDefaultPrinterGroupForUpdate() {
        return getDefaultPrinterGroup(EntityPermission.READ_WRITE);
    }

    public PrinterGroupDetailValue getDefaultPrinterGroupDetailValueForUpdate() {
        return getDefaultPrinterGroupForUpdate().getLastDetailForUpdate().getPrinterGroupDetailValue().clone();
    }

    private List<PrinterGroup> getPrinterGroups(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroups.fields())
                .from(PrinterGroups)
                .join(PrinterGroupDetails).onKey(PRINTER_GROUPS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterGroupDetails.SORT_ORDER, PrinterGroupDetails.PRINTER_GROUP_NAME),
                    PrinterGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroup> getPrinterGroups() {
        return getPrinterGroups(EntityPermission.READ_ONLY);
    }

    public List<PrinterGroup> getPrinterGroupsForUpdate() {
        return getPrinterGroups(EntityPermission.READ_WRITE);
    }

    public PrinterGroupTransfer getPrinterGroupTransfer(UserVisit userVisit, PrinterGroup printerGroup) {
        return printerGroupTransferCache.getPrinterGroupTransfer(userVisit, printerGroup);
    }

    public List<PrinterGroupTransfer> getPrinterGroupTransfers(UserVisit userVisit, Collection<PrinterGroup> printerGroups) {
        List<PrinterGroupTransfer> printerGroupTransfers = new ArrayList<>(printerGroups.size());

        printerGroups.forEach((printerGroup) ->
                printerGroupTransfers.add(printerGroupTransferCache.getPrinterGroupTransfer(userVisit, printerGroup))
        );

        return printerGroupTransfers;
    }

    public List<PrinterGroupTransfer> getPrinterGroupTransfers(UserVisit userVisit) {
        return getPrinterGroupTransfers(userVisit, getPrinterGroups());
    }

    public PrinterGroupChoicesBean getPrinterGroupChoices(String defaultPrinterGroupChoice, Language language, boolean allowNullChoice) {
        var printerGroups = getPrinterGroups();
        var size = printerGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPrinterGroupChoice == null) {
                defaultValue = "";
            }
        }

        for(var printerGroup : printerGroups) {
            var printerGroupDetail = printerGroup.getLastDetail();

            var label = getBestPrinterGroupDescription(printerGroup, language);
            var value = printerGroupDetail.getPrinterGroupName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPrinterGroupChoice != null && defaultPrinterGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && printerGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PrinterGroupChoicesBean(labels, values, defaultValue);
    }

    private void updatePrinterGroupFromValue(PrinterGroupDetailValue printerGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(printerGroupDetailValue.hasBeenModified()) {
            var printerGroup = printerGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     printerGroupDetailValue.getPrinterGroupPK());
            var printerGroupDetail = printerGroup.getActiveDetailForUpdate();

            printerGroupDetail.setThruTime(session.getStartTime());
            printerGroupDetail.store();

            var printerGroupPK = printerGroupDetail.getPrinterGroupPK(); // Not updated
            var printerGroupName = printerGroupDetailValue.getPrinterGroupName();
            var keepPrintedJobsTime = printerGroupDetailValue.getKeepPrintedJobsTime();
            var isDefault = printerGroupDetailValue.getIsDefault();
            var sortOrder = printerGroupDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPrinterGroup = getDefaultPrinterGroup();
                var defaultFound = defaultPrinterGroup != null && !defaultPrinterGroup.equals(printerGroup);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPrinterGroupDetailValue = getDefaultPrinterGroupDetailValueForUpdate();

                    defaultPrinterGroupDetailValue.setIsDefault(false);
                    updatePrinterGroupFromValue(defaultPrinterGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            printerGroupDetail = printerGroupDetailFactory.create(printerGroupPK, printerGroupName, keepPrintedJobsTime, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            printerGroup.setActiveDetail(printerGroupDetail);
            printerGroup.setLastDetail(printerGroupDetail);

            sendEvent(printerGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePrinterGroupFromValue(PrinterGroupDetailValue printerGroupDetailValue, BasePK updatedBy) {
        updatePrinterGroupFromValue(printerGroupDetailValue, true, updatedBy);
    }

    public PrinterGroupStatusChoicesBean getPrinterGroupStatusChoices(String defaultPrinterGroupStatusChoice, Language language, PrinterGroup printerGroup,
            PartyPK partyPK) {
        var printerGroupStatusChoicesBean = new PrinterGroupStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(printerGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PrinterGroupStatusConstants.Workflow_PRINTER_GROUP_STATUS,
                entityInstance);

        workflowControl.getWorkflowDestinationChoices(printerGroupStatusChoicesBean, defaultPrinterGroupStatusChoice, language,
                false, workflowEntityStatus.getWorkflowStep(), partyPK);

        return printerGroupStatusChoicesBean;
    }

    public void setPrinterGroupStatus(ExecutionErrorAccumulator eea, PrinterGroup printerGroup, String printerGroupStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(printerGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PrinterGroupStatusConstants.Workflow_PRINTER_GROUP_STATUS,
                entityInstance);
        var workflowDestination = printerGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), printerGroupStatusChoice);

        if(workflowDestination != null || printerGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPrinterGroupStatusChoice.name(), printerGroupStatusChoice);
        }
    }
    
    public void deletePrinterGroup(PrinterGroup printerGroup, BasePK deletedBy) {
        deletePrinterGroupDescriptionsByPrinterGroup(printerGroup, deletedBy);
        deletePrintersByPrinterGroup(printerGroup, deletedBy);
        deletePartyPrinterGroupUsesByPrinterGroup(printerGroup, deletedBy);
        removePrinterGroupJobsByPrinterGroup(printerGroup);

        var printerGroupDetail = printerGroup.getLastDetailForUpdate();
        printerGroupDetail.setThruTime(session.getStartTime());
        printerGroup.setActiveDetail(null);
        printerGroup.store();

        // Check for default, and pick one if necessary
        var defaultPrinterGroup = getDefaultPrinterGroup();
        if(defaultPrinterGroup == null) {
            var printerGroups = getPrinterGroupsForUpdate();

            if(!printerGroups.isEmpty()) {
                var iter = printerGroups.iterator();
                if(iter.hasNext()) {
                    defaultPrinterGroup = iter.next();
                }
                var printerGroupDetailValue = Objects.requireNonNull(defaultPrinterGroup).getLastDetailForUpdate().getPrinterGroupDetailValue().clone();

                printerGroupDetailValue.setIsDefault(true);
                updatePrinterGroupFromValue(printerGroupDetailValue, false, deletedBy);
            }
        }

        sendEvent(printerGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Printer Group Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PrinterGroupDescriptionFactory printerGroupDescriptionFactory;

    @Inject
    private PrinterGroupDescriptionTransferCache printerGroupDescriptionTransferCache;

    public PrinterGroupDescription createPrinterGroupDescription(PrinterGroup printerGroup,
            Language language, String description, BasePK createdBy) {
        var printerGroupDescription = printerGroupDescriptionFactory.create(printerGroup,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(printerGroup.getPrimaryKey(), EventTypes.MODIFY, printerGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return printerGroupDescription;
    }

    private PrinterGroupDescription getPrinterGroupDescription(PrinterGroup printerGroup,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupDescriptions.fields())
                .from(PrinterGroupDescriptions)
                .where(PrinterGroupDescriptions.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()),
                        PrinterGroupDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PrinterGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroupDescription getPrinterGroupDescription(PrinterGroup printerGroup, Language language) {
        return getPrinterGroupDescription(printerGroup, language, EntityPermission.READ_ONLY);
    }

    public PrinterGroupDescription getPrinterGroupDescriptionForUpdate(PrinterGroup printerGroup, Language language) {
        return getPrinterGroupDescription(printerGroup, language, EntityPermission.READ_WRITE);
    }

    public PrinterGroupDescriptionValue getPrinterGroupDescriptionValue(PrinterGroupDescription printerGroupDescription) {
        return printerGroupDescription == null? null: printerGroupDescription.getPrinterGroupDescriptionValue().clone();
    }

    public PrinterGroupDescriptionValue getPrinterGroupDescriptionValueForUpdate(PrinterGroup printerGroup, Language language) {
        return getPrinterGroupDescriptionValue(getPrinterGroupDescriptionForUpdate(printerGroup, language));
    }

    private List<PrinterGroupDescription> getPrinterGroupDescriptionsByPrinterGroup(PrinterGroup printerGroup,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PrinterGroupDescriptions.fields())
                    .from(PrinterGroupDescriptions)
                    .join(Languages).onKey(PRINTER_GROUP_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PrinterGroupDescriptions.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()),
                            PrinterGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PrinterGroupDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PrinterGroupDescriptions.fields())
                    .from(PrinterGroupDescriptions)
                    .where(PrinterGroupDescriptions.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()),
                            PrinterGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return printerGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroupDescription> getPrinterGroupDescriptionsByPrinterGroup(PrinterGroup printerGroup) {
        return getPrinterGroupDescriptionsByPrinterGroup(printerGroup, EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupDescription> getPrinterGroupDescriptionsByPrinterGroupForUpdate(PrinterGroup printerGroup) {
        return getPrinterGroupDescriptionsByPrinterGroup(printerGroup, EntityPermission.READ_WRITE);
    }

    public String getBestPrinterGroupDescription(PrinterGroup printerGroup, Language language) {
        String description;
        var printerGroupDescription = getPrinterGroupDescription(printerGroup, language);

        if(printerGroupDescription == null && !language.getIsDefault()) {
            printerGroupDescription = getPrinterGroupDescription(printerGroup, partyControl.getDefaultLanguage());
        }

        if(printerGroupDescription == null) {
            description = printerGroup.getLastDetail().getPrinterGroupName();
        } else {
            description = printerGroupDescription.getDescription();
        }

        return description;
    }

    public PrinterGroupDescriptionTransfer getPrinterGroupDescriptionTransfer(UserVisit userVisit, PrinterGroupDescription printerGroupDescription) {
        return printerGroupDescriptionTransferCache.getPrinterGroupDescriptionTransfer(userVisit, printerGroupDescription);
    }

    public List<PrinterGroupDescriptionTransfer> getPrinterGroupDescriptionTransfersByPrinterGroup(UserVisit userVisit, PrinterGroup printerGroup) {
        var printerGroupDescriptions = getPrinterGroupDescriptionsByPrinterGroup(printerGroup);
        List<PrinterGroupDescriptionTransfer> printerGroupDescriptionTransfers = new ArrayList<>(printerGroupDescriptions.size());

        printerGroupDescriptions.forEach((printerGroupDescription) ->
                printerGroupDescriptionTransfers.add(printerGroupDescriptionTransferCache.getPrinterGroupDescriptionTransfer(userVisit, printerGroupDescription))
        );

        return printerGroupDescriptionTransfers;
    }

    public void updatePrinterGroupDescriptionFromValue(PrinterGroupDescriptionValue printerGroupDescriptionValue, BasePK updatedBy) {
        if(printerGroupDescriptionValue.hasBeenModified()) {
            var printerGroupDescription = printerGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    printerGroupDescriptionValue.getPrimaryKey());

            printerGroupDescription.setThruTime(session.getStartTime());
            printerGroupDescription.store();

            var printerGroup = printerGroupDescription.getPrinterGroup();
            var language = printerGroupDescription.getLanguage();
            var description = printerGroupDescriptionValue.getDescription();

            printerGroupDescription = printerGroupDescriptionFactory.create(printerGroup, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(printerGroup.getPrimaryKey(), EventTypes.MODIFY, printerGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePrinterGroupDescription(PrinterGroupDescription printerGroupDescription, BasePK deletedBy) {
        printerGroupDescription.setThruTime(session.getStartTime());

        sendEvent(printerGroupDescription.getPrinterGroupPK(), EventTypes.MODIFY, printerGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePrinterGroupDescriptionsByPrinterGroup(PrinterGroup printerGroup, BasePK deletedBy) {
        var printerGroupDescriptions = getPrinterGroupDescriptionsByPrinterGroupForUpdate(printerGroup);

        printerGroupDescriptions.forEach((printerGroupDescription) -> 
                deletePrinterGroupDescription(printerGroupDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Printers
    // --------------------------------------------------------------------------------

    @Inject
    protected PrinterDetailFactory printerDetailFactory;

    @Inject
    private PrinterFactory printerFactory;

    @Inject
    private PrinterTransferCache printerTransferCache;

    public Printer createPrinter(String printerName, PrinterGroup printerGroup, Integer priority, BasePK createdBy) {
        var printer = printerFactory.create();
        var printerDetail = printerDetailFactory.create(printer, printerName, printerGroup, priority, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        printer = printerFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                printer.getPrimaryKey());
        printer.setActiveDetail(printerDetail);
        printer.setLastDetail(printerDetail);
        printer.store();

        sendEvent(printer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printer;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Printer */
    public Printer getPrinterByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PrinterPK(entityInstance.getEntityUniqueId());

        return printerFactory.getEntityFromPK(entityPermission, pk);
    }

    public Printer getPrinterByEntityInstance(EntityInstance entityInstance) {
        return getPrinterByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Printer getPrinterByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPrinterByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPrinters() {
        return session.getDslContext()
                .selectCount()
                .from(Printers)
                .join(PrinterDetails).onKey(PRINTERS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPrintersByPrinterGroup(final PrinterGroup printerGroup) {
        return session.getDslContext()
                .selectCount()
                .from(Printers)
                .join(PrinterDetails).onKey(PRINTERS_ACTIVE_DETAIL_FK)
                .where(PrinterDetails.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Printer getPrinterByName(String printerName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Printers.fields())
                .from(Printers)
                .join(PrinterDetails).onKey(PRINTERS_ACTIVE_DETAIL_FK)
                .where(PrinterDetails.PRINTER_NAME.eq(printerName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerFactory.getEntityFromQuery(entityPermission, query);
    }

    public Printer getPrinterByName(String printerName) {
        return getPrinterByName(printerName, EntityPermission.READ_ONLY);
    }

    public Printer getPrinterByNameForUpdate(String printerName) {
        return getPrinterByName(printerName, EntityPermission.READ_WRITE);
    }

    public PrinterDetailValue getPrinterDetailValueForUpdate(Printer printer) {
        return printer == null? null: printer.getLastDetailForUpdate().getPrinterDetailValue().clone();
    }

    public PrinterDetailValue getPrinterDetailValueByNameForUpdate(String printerName) {
        return getPrinterDetailValueForUpdate(getPrinterByNameForUpdate(printerName));
    }

    private List<Printer> getPrinters(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Printers.fields())
                .from(Printers)
                .join(PrinterDetails).onKey(PRINTERS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterDetails.PRIORITY, PrinterDetails.PRINTER_NAME),
                    PrinterFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Printer> getPrinters() {
        return getPrinters(EntityPermission.READ_ONLY);
    }

    public List<Printer> getPrintersForUpdate() {
        return getPrinters(EntityPermission.READ_WRITE);
    }

    private List<Printer> getPrintersByPrinterGroup(PrinterGroup printerGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Printers.fields())
                .from(Printers)
                .join(PrinterDetails).onKey(PRINTERS_ACTIVE_DETAIL_FK)
                .where(PrinterDetails.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterDetails.PRIORITY, PrinterDetails.PRINTER_NAME),
                    PrinterFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Printer> getPrintersByPrinterGroup(PrinterGroup printerGroup) {
        return getPrintersByPrinterGroup(printerGroup, EntityPermission.READ_ONLY);
    }

    public List<Printer> getPrintersByPrinterGroupForUpdate(PrinterGroup printerGroup) {
        return getPrintersByPrinterGroup(printerGroup, EntityPermission.READ_WRITE);
    }

    public PrinterTransfer getPrinterTransfer(UserVisit userVisit, Printer printer) {
        return printerTransferCache.getPrinterTransfer(userVisit, printer);
    }

    public List<PrinterTransfer> getPrinterTransfers(UserVisit userVisit, Collection<Printer> printers) {
        List<PrinterTransfer> printerTransfers = new ArrayList<>(printers.size());

        printers.forEach((printer) ->
                printerTransfers.add(printerTransferCache.getPrinterTransfer(userVisit, printer))
        );

        return printerTransfers;
    }

    public List<PrinterTransfer> getPrinterTransfers(UserVisit userVisit) {
        return getPrinterTransfers(userVisit, getPrinters());
    }

    public List<PrinterTransfer> getPrinterTransfersByPrinterGroup(UserVisit userVisit, PrinterGroup printerGroup) {
        return getPrinterTransfers(userVisit, getPrintersByPrinterGroup(printerGroup));
    }

    public void updatePrinterFromValue(PrinterDetailValue printerDetailValue, BasePK updatedBy) {
        if(printerDetailValue.hasBeenModified()) {
            var printer = printerFactory.getEntityFromPK(EntityPermission.READ_WRITE, printerDetailValue.getPrinterPK());
            var printerDetail = printer.getActiveDetailForUpdate();

            printerDetail.setThruTime(session.getStartTime());
            printerDetail.store();

            var printerPK = printerDetail.getPrinterPK(); // Not updated
            var printerName = printerDetailValue.getPrinterName();
            var printerGroupPK = printerDetailValue.getPrinterGroupPK();
            var priority = printerDetailValue.getPriority();

            printerDetail = printerDetailFactory.create(printerPK, printerName, printerGroupPK, priority, session.getStartTime(),
                    Session.MAX_TIME);

            printer.setActiveDetail(printerDetail);
            printer.setLastDetail(printerDetail);

            sendEvent(printerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public PrinterStatusChoicesBean getPrinterStatusChoices(String defaultPrinterStatusChoice, Language language, Printer printer, PartyPK partyPK) {
        var printerStatusChoicesBean = new PrinterStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(printer);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PrinterStatusConstants.Workflow_PRINTER_STATUS,
                entityInstance);

        workflowControl.getWorkflowDestinationChoices(printerStatusChoicesBean, defaultPrinterStatusChoice, language,
                false, workflowEntityStatus.getWorkflowStep(), partyPK);

        return printerStatusChoicesBean;
    }

    public void setPrinterStatus(ExecutionErrorAccumulator eea, Printer printer, String printerStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(printer);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PrinterStatusConstants.Workflow_PRINTER_STATUS,
                entityInstance);
        var workflowDestination = printerStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), printerStatusChoice);

        if(workflowDestination != null || printerStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPrinterStatusChoice.name(), printerStatusChoice);
        }
    }

    public void deletePrinter(Printer printer, BasePK deletedBy) {
        deletePrinterDescriptionsByPrinter(printer, deletedBy);

        var printerDetail = printer.getLastDetailForUpdate();
        printerDetail.setThruTime(session.getStartTime());
        printer.setActiveDetail(null);
        printer.store();

        sendEvent(printer.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePrinters(List<Printer> printers, BasePK deletedBy) {
        printers.forEach((printer) -> 
                deletePrinter(printer, deletedBy)
        );
    }

    public void deletePrintersByPrinterGroup(PrinterGroup printerGroup, BasePK deletedBy) {
        deletePrinters(getPrintersByPrinterGroupForUpdate(printerGroup), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Printer Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PrinterDescriptionFactory printerDescriptionFactory;

    @Inject
    private PrinterDescriptionTransferCache printerDescriptionTransferCache;

    public PrinterDescription createPrinterDescription(Printer printer, Language language, String description, BasePK createdBy) {
        var printerDescription = printerDescriptionFactory.create(printer, language, description, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(printer.getPrimaryKey(), EventTypes.MODIFY, printerDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return printerDescription;
    }

    private PrinterDescription getPrinterDescription(Printer printer,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterDescriptions.fields())
                .from(PrinterDescriptions)
                .where(PrinterDescriptions.PRINTER.eq(printer.getPrimaryKey()),
                        PrinterDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PrinterDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterDescription getPrinterDescription(Printer printer, Language language) {
        return getPrinterDescription(printer, language, EntityPermission.READ_ONLY);
    }

    public PrinterDescription getPrinterDescriptionForUpdate(Printer printer, Language language) {
        return getPrinterDescription(printer, language, EntityPermission.READ_WRITE);
    }

    public PrinterDescriptionValue getPrinterDescriptionValue(PrinterDescription printerDescription) {
        return printerDescription == null? null: printerDescription.getPrinterDescriptionValue().clone();
    }

    public PrinterDescriptionValue getPrinterDescriptionValueForUpdate(Printer printer, Language language) {
        return getPrinterDescriptionValue(getPrinterDescriptionForUpdate(printer, language));
    }

    private List<PrinterDescription> getPrinterDescriptionsByPrinter(Printer printer,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PrinterDescriptions.fields())
                    .from(PrinterDescriptions)
                    .join(Languages).onKey(PRINTER_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PrinterDescriptions.PRINTER.eq(printer.getPrimaryKey()),
                            PrinterDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PrinterDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PrinterDescriptions.fields())
                    .from(PrinterDescriptions)
                    .where(PrinterDescriptions.PRINTER.eq(printer.getPrimaryKey()),
                            PrinterDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return printerDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterDescription> getPrinterDescriptionsByPrinter(Printer printer) {
        return getPrinterDescriptionsByPrinter(printer, EntityPermission.READ_ONLY);
    }

    public List<PrinterDescription> getPrinterDescriptionsByPrinterForUpdate(Printer printer) {
        return getPrinterDescriptionsByPrinter(printer, EntityPermission.READ_WRITE);
    }

    public String getBestPrinterDescription(Printer printer, Language language) {
        String description;
        var printerDescription = getPrinterDescription(printer, language);

        if(printerDescription == null && !language.getIsDefault()) {
            printerDescription = getPrinterDescription(printer, partyControl.getDefaultLanguage());
        }

        if(printerDescription == null) {
            description = printer.getLastDetail().getPrinterName();
        } else {
            description = printerDescription.getDescription();
        }

        return description;
    }

    public PrinterDescriptionTransfer getPrinterDescriptionTransfer(UserVisit userVisit, PrinterDescription printerDescription) {
        return printerDescriptionTransferCache.getPrinterDescriptionTransfer(userVisit, printerDescription);
    }

    public List<PrinterDescriptionTransfer> getPrinterDescriptionTransfersByPrinter(UserVisit userVisit, Printer printer) {
        var printerDescriptions = getPrinterDescriptionsByPrinter(printer);
        List<PrinterDescriptionTransfer> printerDescriptionTransfers = new ArrayList<>(printerDescriptions.size());

        printerDescriptions.forEach((printerDescription) ->
                printerDescriptionTransfers.add(printerDescriptionTransferCache.getPrinterDescriptionTransfer(userVisit, printerDescription))
        );

        return printerDescriptionTransfers;
    }

    public void updatePrinterDescriptionFromValue(PrinterDescriptionValue printerDescriptionValue, BasePK updatedBy) {
        if(printerDescriptionValue.hasBeenModified()) {
            var printerDescription = printerDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    printerDescriptionValue.getPrimaryKey());

            printerDescription.setThruTime(session.getStartTime());
            printerDescription.store();

            var printer = printerDescription.getPrinter();
            var language = printerDescription.getLanguage();
            var description = printerDescriptionValue.getDescription();

            printerDescription = printerDescriptionFactory.create(printer, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(printer.getPrimaryKey(), EventTypes.MODIFY, printerDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePrinterDescription(PrinterDescription printerDescription, BasePK deletedBy) {
        printerDescription.setThruTime(session.getStartTime());

        sendEvent(printerDescription.getPrinterPK(), EventTypes.MODIFY, printerDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePrinterDescriptionsByPrinter(Printer printer, BasePK deletedBy) {
        var printerDescriptions = getPrinterDescriptionsByPrinterForUpdate(printer);

        printerDescriptions.forEach((printerDescription) -> 
                deletePrinterDescription(printerDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Printer Group Jobs
    // --------------------------------------------------------------------------------

    @Inject
    protected PrinterGroupJobDetailFactory printerGroupJobDetailFactory;

    @Inject
    private PrinterGroupJobFactory printerGroupJobFactory;

    @Inject
    private PrinterGroupJobTransferCache printerGroupJobTransferCache;

    public PrinterGroupJob createPrinterGroupJob(PrinterGroup printerGroup, Document document, Integer copies, Integer priority, BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.PRINTER_GROUP_JOB.name()));
        var printerGroupJobName = sequenceGeneratorLogic.getNextSequenceValue(sequence);

        return createPrinterGroupJob(printerGroupJobName, printerGroup, document, copies, priority, createdBy);
    }

    public PrinterGroupJob createPrinterGroupJob(String printerGroupJobName, PrinterGroup printerGroup, Document document, Integer copies, Integer priority,
            BasePK createdBy) {
        var printerGroupJob = printerGroupJobFactory.create();
        var printerGroupJobDetail = printerGroupJobDetailFactory.create(printerGroupJob, printerGroupJobName, printerGroup,
                document, copies, priority, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        printerGroupJob = printerGroupJobFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                printerGroupJob.getPrimaryKey());
        printerGroupJob.setActiveDetail(printerGroupJobDetail);
        printerGroupJob.setLastDetail(printerGroupJobDetail);
        printerGroupJob.store();

        sendEvent(printerGroupJob.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printerGroupJob;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PrinterGroupJob */
    public PrinterGroupJob getPrinterGroupJobByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PrinterGroupJobPK(entityInstance.getEntityUniqueId());

        return printerGroupJobFactory.getEntityFromPK(entityPermission, pk);
    }

    public PrinterGroupJob getPrinterGroupJobByEntityInstance(EntityInstance entityInstance) {
        return getPrinterGroupJobByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PrinterGroupJob getPrinterGroupJobByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPrinterGroupJobByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPrinterGroupJobsByPrinterGroup(final PrinterGroup printerGroup) {
        return session.getDslContext()
                .selectCount()
                .from(PrinterGroupJobs)
                .join(PrinterGroupJobDetails).onKey(PRINTER_GROUP_JOBS_ACTIVE_DETAIL_FK)
                .where(PrinterGroupJobDetails.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPrinterGroupJobsByPrinterGroupJobStatus(final WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityStatuses)
                .where(WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PrinterGroupJob getPrinterGroupJobByName(String printerGroupJobName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupJobs.fields())
                .from(PrinterGroupJobs)
                .join(PrinterGroupJobDetails).onKey(PRINTER_GROUP_JOBS_ACTIVE_DETAIL_FK)
                .where(PrinterGroupJobDetails.PRINTER_GROUP_JOB_NAME.eq(printerGroupJobName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupJobFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroupJob getPrinterGroupJobByName(String printerGroupJobName) {
        return getPrinterGroupJobByName(printerGroupJobName, EntityPermission.READ_ONLY);
    }

    public PrinterGroupJob getPrinterGroupJobByNameForUpdate(String printerGroupJobName) {
        return getPrinterGroupJobByName(printerGroupJobName, EntityPermission.READ_WRITE);
    }

    public PrinterGroupJobDetailValue getPrinterGroupJobDetailValueForUpdate(PrinterGroupJob printerGroupJob) {
        return printerGroupJob == null? null: printerGroupJob.getLastDetailForUpdate().getPrinterGroupJobDetailValue().clone();
    }

    public PrinterGroupJobDetailValue getPrinterGroupJobDetailValueByNameForUpdate(String printerGroupJobName) {
        return getPrinterGroupJobDetailValueForUpdate(getPrinterGroupJobByNameForUpdate(printerGroupJobName));
    }

    private List<PrinterGroupJob> getPrinterGroupJobs(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupJobs.fields())
                .from(PrinterGroupJobs)
                .join(PrinterGroupJobDetails).onKey(PRINTER_GROUP_JOBS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterGroupJobDetails.PRINTER_GROUP_JOB_NAME),
                    PrinterGroupJobFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupJobFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroupJob> getPrinterGroupJobs() {
        return getPrinterGroupJobs(EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsForUpdate() {
        return getPrinterGroupJobs(EntityPermission.READ_WRITE);
    }

    private List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupJobs.fields())
                .from(PrinterGroupJobs)
                .join(PrinterGroupJobDetails).onKey(PRINTER_GROUP_JOBS_ACTIVE_DETAIL_FK)
                .where(PrinterGroupJobDetails.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterGroupJobDetails.PRIORITY.desc(), PrinterGroupJobDetails.PRINTER_GROUP_JOB_NAME),
                    PrinterGroupJobFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupJobFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup) {
        return getPrinterGroupJobsByPrinterGroup(printerGroup, EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroupForUpdate(PrinterGroup printerGroup) {
        return getPrinterGroupJobsByPrinterGroup(printerGroup, EntityPermission.READ_WRITE);
    }

    private List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroupJobStatus(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupJobs.fields())
                .from(PrinterGroupJobs)
                .join(EntityInstances).on(PrinterGroupJobs.PRINTER_GROUP_JOB.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                .join(PrinterGroupJobDetails).onKey(PRINTER_GROUP_JOBS_ACTIVE_DETAIL_FK)
                .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                .where(ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.PrinterGroupJob.name()),
                        WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterGroupJobDetails.PRIORITY.desc(), PrinterGroupJobDetails.PRINTER_GROUP_JOB_NAME),
                    PrinterGroupJobFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupJobFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroupJobStatus(WorkflowStep workflowStep) {
        return getPrinterGroupJobsByPrinterGroupJobStatus(workflowStep, EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroupJobStatusForUpdate(WorkflowStep workflowStep) {
        return getPrinterGroupJobsByPrinterGroupJobStatus(workflowStep, EntityPermission.READ_WRITE);
    }

    public PrinterGroupJobTransfer getPrinterGroupJobTransfer(UserVisit userVisit, PrinterGroupJob printerGroupJob) {
        return printerGroupJobTransferCache.getPrinterGroupJobTransfer(userVisit, printerGroupJob);
    }

    public List<PrinterGroupJobTransfer> getPrinterGroupJobTransfers(UserVisit userVisit, Collection<PrinterGroupJob> printerGroupJobs) {
        List<PrinterGroupJobTransfer> printerGroupJobTransfers = new ArrayList<>(printerGroupJobs.size());

        printerGroupJobs.forEach((printerGroupJob) ->
                printerGroupJobTransfers.add(printerGroupJobTransferCache.getPrinterGroupJobTransfer(userVisit, printerGroupJob))
        );

        return printerGroupJobTransfers;
    }

    public List<PrinterGroupJobTransfer> getPrinterGroupJobTransfers(UserVisit userVisit) {
        return getPrinterGroupJobTransfers(userVisit, getPrinterGroupJobs());
    }

    public List<PrinterGroupJobTransfer> getPrinterGroupJobTransfersByPrinterGroup(UserVisit userVisit, PrinterGroup printerGroup) {
        return getPrinterGroupJobTransfers(userVisit, getPrinterGroupJobsByPrinterGroup(printerGroup));
    }

    public List<PrinterGroupJobTransfer> getPrinterGroupJobTransfersByPrinterGroupJobStatus(UserVisit userVisit, WorkflowStep workflowStep) {
        return getPrinterGroupJobTransfers(userVisit, getPrinterGroupJobsByPrinterGroupJobStatus(workflowStep));
    }

    public void updatePrinterGroupJobFromValue(PrinterGroupJobDetailValue printerGroupJobDetailValue, BasePK updatedBy) {
        if(printerGroupJobDetailValue.hasBeenModified()) {
            var printerGroupJob = printerGroupJobFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     printerGroupJobDetailValue.getPrinterGroupJobPK());
            var printerGroupJobDetail = printerGroupJob.getActiveDetailForUpdate();

            printerGroupJobDetail.setThruTime(session.getStartTime());
            printerGroupJobDetail.store();

            var printerGroupJobPK = printerGroupJobDetail.getPrinterGroupJobPK(); // Not updated
            var printerGroupJobName = printerGroupJobDetailValue.getPrinterGroupJobName();
            var printerGroupPK = printerGroupJobDetailValue.getPrinterGroupPK();
            var documentPK = printerGroupJobDetail.getDocumentPK(); // Not updated
            var copies = printerGroupJobDetailValue.getCopies();
            var priority = printerGroupJobDetailValue.getPriority();

            printerGroupJobDetail = printerGroupJobDetailFactory.create(printerGroupJobPK, printerGroupJobName, printerGroupPK, documentPK,
                    copies, priority, session.getStartTime(), Session.MAX_TIME);

            printerGroupJob.setActiveDetail(printerGroupJobDetail);
            printerGroupJob.setLastDetail(printerGroupJobDetail);

            sendEvent(printerGroupJobPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public PrinterGroupJobStatusChoicesBean getPrinterGroupJobStatusChoices(String defaultPrinterGroupJobStatusChoice, Language language,
            PrinterGroupJob printerGroupJob, PartyPK partyPK) {
        var printerGroupJobStatusChoicesBean = new PrinterGroupJobStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(printerGroupJob);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS,
                entityInstance);

        workflowControl.getWorkflowDestinationChoices(printerGroupJobStatusChoicesBean, defaultPrinterGroupJobStatusChoice, language,
                false, workflowEntityStatus.getWorkflowStep(), partyPK);

        return printerGroupJobStatusChoicesBean;
    }

    public void setPrinterGroupJobStatus(ExecutionErrorAccumulator eea, PrinterGroupJob printerGroupJob, String printerGroupJobStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(printerGroupJob);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS, entityInstance);
        var workflowDestination = printerGroupJobStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), printerGroupJobStatusChoice);

        if(workflowDestination != null || printerGroupJobStatusChoice == null) {
            Long triggerTime = null;

            if(workflowDestination != null) {
                var workflowDestinationName = workflowDestination.getLastDetail().getWorkflowDestinationName();

                if(workflowDestinationName.equals(PrinterGroupJobStatusConstants.WorkflowDestination_QUEUED_TO_PRINTED)
                        || workflowDestinationName.equals(PrinterGroupJobStatusConstants.WorkflowDestination_QUEUED_TO_DELETED)
                        || workflowDestinationName.equals(PrinterGroupJobStatusConstants.WorkflowDestination_PRINTED_TO_DELETED)
                        || workflowDestinationName.equals(PrinterGroupJobStatusConstants.WorkflowDestination_ERRORED_TO_DELETED)) {
                    var keepPrintedJobsTime = printerGroupJob.getLastDetail().getPrinterGroup().getLastDetail().getKeepPrintedJobsTime();

                    if(keepPrintedJobsTime != null) {
                        triggerTime = session.getStartTime() + keepPrintedJobsTime;
                    }
                }
            }

            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPrinterGroupJobStatusChoice.name(), printerGroupJobStatusChoice);
        }
    }

    public void deletePrinterGroupJob(PrinterGroupJob printerGroupJob, BasePK deletedBy) {
        var printerGroupJobDetail = printerGroupJob.getLastDetailForUpdate();

        documentControl.deleteDocument(printerGroupJobDetail.getDocumentForUpdate(), deletedBy);

        printerGroupJobDetail.setThruTime(session.getStartTime());
        printerGroupJob.setActiveDetail(null);
        printerGroupJob.store();

        sendEvent(printerGroupJob.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePrinterGroupJobs(List<PrinterGroupJob> printerGroupJobs, BasePK deletedBy) {
        printerGroupJobs.forEach((printerGroupJob) -> 
                deletePrinterGroupJob(printerGroupJob, deletedBy)
        );
    }

    public void deletePrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup, BasePK deletedBy) {
        deletePrinterGroupJobs(getPrinterGroupJobsByPrinterGroupForUpdate(printerGroup), deletedBy);
    }

    public void removePrinterGroupJob(PrinterGroupJob printerGroupJob) {

        // Cascades to the PrinterGroupJob, so a seprate remove isn't required.
        documentControl.removeDocument(printerGroupJob.getLastDetail().getDocumentForUpdate());

        entityInstanceControl.removeEntityInstanceByBasePK(printerGroupJob.getPrimaryKey());
    }

    public void removePrinterGroupJobs(List<PrinterGroupJob> printerGroupJobs) {
        printerGroupJobs.forEach((printerGroupJob) -> {
            removePrinterGroupJob(printerGroupJob);
        });
    }

    public void removePrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup) {
        removePrinterGroupJobs(getPrinterGroupJobsByPrinterGroupForUpdate(printerGroup));
    }

    // --------------------------------------------------------------------------------
    //   Printer Group Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PrinterGroupUseTypeDetailFactory printerGroupUseTypeDetailFactory;

    @Inject
    private PrinterGroupUseTypeFactory printerGroupUseTypeFactory;

    @Inject
    private PrinterGroupUseTypeTransferCache printerGroupUseTypeTransferCache;

    public PrinterGroupUseType createPrinterGroupUseType(String printerGroupUseTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPrinterGroupUseType = getDefaultPrinterGroupUseType();
        var defaultFound = defaultPrinterGroupUseType != null;

        if(defaultFound && isDefault) {
            var defaultPrinterGroupUseTypeDetailValue = getDefaultPrinterGroupUseTypeDetailValueForUpdate();

            defaultPrinterGroupUseTypeDetailValue.setIsDefault(false);
            updatePrinterGroupUseTypeFromValue(defaultPrinterGroupUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var printerGroupUseType = printerGroupUseTypeFactory.create();
        var printerGroupUseTypeDetail = printerGroupUseTypeDetailFactory.create(printerGroupUseType,
                printerGroupUseTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        printerGroupUseType = printerGroupUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                printerGroupUseType.getPrimaryKey());
        printerGroupUseType.setActiveDetail(printerGroupUseTypeDetail);
        printerGroupUseType.setLastDetail(printerGroupUseTypeDetail);
        printerGroupUseType.store();

        sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printerGroupUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PrinterGroupUseType */
    public PrinterGroupUseType getPrinterGroupUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PrinterGroupUseTypePK(entityInstance.getEntityUniqueId());

        return printerGroupUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PrinterGroupUseType getPrinterGroupUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getPrinterGroupUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PrinterGroupUseType getPrinterGroupUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPrinterGroupUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPrinterGroupUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(PrinterGroupUseTypes)
                .join(PrinterGroupUseTypeDetails).onKey(PRINTER_GROUP_USE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PrinterGroupUseType getPrinterGroupUseTypeByName(String printerGroupUseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupUseTypes.fields())
                .from(PrinterGroupUseTypes)
                .join(PrinterGroupUseTypeDetails).onKey(PRINTER_GROUP_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(PrinterGroupUseTypeDetails.PRINTER_GROUP_USE_TYPE_NAME.eq(printerGroupUseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroupUseType getPrinterGroupUseTypeByName(String printerGroupUseTypeName) {
        return getPrinterGroupUseTypeByName(printerGroupUseTypeName, EntityPermission.READ_ONLY);
    }

    public PrinterGroupUseType getPrinterGroupUseTypeByNameForUpdate(String printerGroupUseTypeName) {
        return getPrinterGroupUseTypeByName(printerGroupUseTypeName, EntityPermission.READ_WRITE);
    }

    public PrinterGroupUseTypeDetailValue getPrinterGroupUseTypeDetailValueForUpdate(PrinterGroupUseType printerGroupUseType) {
        return printerGroupUseType == null? null: printerGroupUseType.getLastDetailForUpdate().getPrinterGroupUseTypeDetailValue().clone();
    }

    public PrinterGroupUseTypeDetailValue getPrinterGroupUseTypeDetailValueByNameForUpdate(String printerGroupUseTypeName) {
        return getPrinterGroupUseTypeDetailValueForUpdate(getPrinterGroupUseTypeByNameForUpdate(printerGroupUseTypeName));
    }

    private PrinterGroupUseType getDefaultPrinterGroupUseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupUseTypes.fields())
                .from(PrinterGroupUseTypes)
                .join(PrinterGroupUseTypeDetails).onKey(PRINTER_GROUP_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(PrinterGroupUseTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroupUseType getDefaultPrinterGroupUseType() {
        return getDefaultPrinterGroupUseType(EntityPermission.READ_ONLY);
    }

    public PrinterGroupUseType getDefaultPrinterGroupUseTypeForUpdate() {
        return getDefaultPrinterGroupUseType(EntityPermission.READ_WRITE);
    }

    public PrinterGroupUseTypeDetailValue getDefaultPrinterGroupUseTypeDetailValueForUpdate() {
        return getDefaultPrinterGroupUseTypeForUpdate().getLastDetailForUpdate().getPrinterGroupUseTypeDetailValue().clone();
    }

    private List<PrinterGroupUseType> getPrinterGroupUseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupUseTypes.fields())
                .from(PrinterGroupUseTypes)
                .join(PrinterGroupUseTypeDetails).onKey(PRINTER_GROUP_USE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PrinterGroupUseTypeDetails.SORT_ORDER, PrinterGroupUseTypeDetails.PRINTER_GROUP_USE_TYPE_NAME),
                    PrinterGroupUseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupUseTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroupUseType> getPrinterGroupUseTypes() {
        return getPrinterGroupUseTypes(EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupUseType> getPrinterGroupUseTypesForUpdate() {
        return getPrinterGroupUseTypes(EntityPermission.READ_WRITE);
    }

    public PrinterGroupUseTypeTransfer getPrinterGroupUseTypeTransfer(UserVisit userVisit, PrinterGroupUseType printerGroupUseType) {
        return printerGroupUseTypeTransferCache.getPrinterGroupUseTypeTransfer(userVisit, printerGroupUseType);
    }

    public List<PrinterGroupUseTypeTransfer> getPrinterGroupUseTypeTransfers(UserVisit userVisit, Collection<PrinterGroupUseType> printerGroupUseTypes) {
        List<PrinterGroupUseTypeTransfer> printerGroupUseTypeTransfers = new ArrayList<>(printerGroupUseTypes.size());

        printerGroupUseTypes.forEach((printerGroupUseType) ->
                printerGroupUseTypeTransfers.add(printerGroupUseTypeTransferCache.getPrinterGroupUseTypeTransfer(userVisit, printerGroupUseType))
        );

        return printerGroupUseTypeTransfers;
    }

    public List<PrinterGroupUseTypeTransfer> getPrinterGroupUseTypeTransfers(UserVisit userVisit) {
        return getPrinterGroupUseTypeTransfers(userVisit, getPrinterGroupUseTypes());
    }

    public PrinterGroupUseTypeChoicesBean getPrinterGroupUseTypeChoices(String defaultPrinterGroupUseTypeChoice, Language language, boolean allowNullChoice) {
        var printerGroupUseTypes = getPrinterGroupUseTypes();
        var size = printerGroupUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPrinterGroupUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var printerGroupUseType : printerGroupUseTypes) {
            var printerGroupUseTypeDetail = printerGroupUseType.getLastDetail();

            var label = getBestPrinterGroupUseTypeDescription(printerGroupUseType, language);
            var value = printerGroupUseTypeDetail.getPrinterGroupUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPrinterGroupUseTypeChoice != null && defaultPrinterGroupUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && printerGroupUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PrinterGroupUseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePrinterGroupUseTypeFromValue(PrinterGroupUseTypeDetailValue printerGroupUseTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(printerGroupUseTypeDetailValue.hasBeenModified()) {
            var printerGroupUseType = printerGroupUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     printerGroupUseTypeDetailValue.getPrinterGroupUseTypePK());
            var printerGroupUseTypeDetail = printerGroupUseType.getActiveDetailForUpdate();

            printerGroupUseTypeDetail.setThruTime(session.getStartTime());
            printerGroupUseTypeDetail.store();

            var printerGroupUseTypePK = printerGroupUseTypeDetail.getPrinterGroupUseTypePK(); // Not updated
            var printerGroupUseTypeName = printerGroupUseTypeDetailValue.getPrinterGroupUseTypeName();
            var isDefault = printerGroupUseTypeDetailValue.getIsDefault();
            var sortOrder = printerGroupUseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPrinterGroupUseType = getDefaultPrinterGroupUseType();
                var defaultFound = defaultPrinterGroupUseType != null && !defaultPrinterGroupUseType.equals(printerGroupUseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPrinterGroupUseTypeDetailValue = getDefaultPrinterGroupUseTypeDetailValueForUpdate();

                    defaultPrinterGroupUseTypeDetailValue.setIsDefault(false);
                    updatePrinterGroupUseTypeFromValue(defaultPrinterGroupUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            printerGroupUseTypeDetail = printerGroupUseTypeDetailFactory.create(printerGroupUseTypePK, printerGroupUseTypeName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            printerGroupUseType.setActiveDetail(printerGroupUseTypeDetail);
            printerGroupUseType.setLastDetail(printerGroupUseTypeDetail);

            sendEvent(printerGroupUseTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePrinterGroupUseTypeFromValue(PrinterGroupUseTypeDetailValue printerGroupUseTypeDetailValue, BasePK updatedBy) {
        updatePrinterGroupUseTypeFromValue(printerGroupUseTypeDetailValue, true, updatedBy);
    }

    public void deletePrinterGroupUseType(PrinterGroupUseType printerGroupUseType, BasePK deletedBy) {
        deletePrinterGroupUseTypeDescriptionsByPrinterGroupUseType(printerGroupUseType, deletedBy);
        deletePartyPrinterGroupUsesByPrinterGroupUseType(printerGroupUseType, deletedBy);

        var printerGroupUseTypeDetail = printerGroupUseType.getLastDetailForUpdate();
        printerGroupUseTypeDetail.setThruTime(session.getStartTime());
        printerGroupUseType.setActiveDetail(null);
        printerGroupUseType.store();

        // Check for default, and pick one if necessary
        var defaultPrinterGroupUseType = getDefaultPrinterGroupUseType();
        if(defaultPrinterGroupUseType == null) {
            var printerGroupUseTypes = getPrinterGroupUseTypesForUpdate();

            if(!printerGroupUseTypes.isEmpty()) {
                var iter = printerGroupUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultPrinterGroupUseType = iter.next();
                }
                var printerGroupUseTypeDetailValue = Objects.requireNonNull(defaultPrinterGroupUseType).getLastDetailForUpdate().getPrinterGroupUseTypeDetailValue().clone();

                printerGroupUseTypeDetailValue.setIsDefault(true);
                updatePrinterGroupUseTypeFromValue(printerGroupUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Printer Group Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PrinterGroupUseTypeDescriptionFactory printerGroupUseTypeDescriptionFactory;

    @Inject
    private PrinterGroupUseTypeDescriptionTransferCache printerGroupUseTypeDescriptionTransferCache;

    public PrinterGroupUseTypeDescription createPrinterGroupUseTypeDescription(PrinterGroupUseType printerGroupUseType,
            Language language, String description, BasePK createdBy) {
        var printerGroupUseTypeDescription = printerGroupUseTypeDescriptionFactory.create(printerGroupUseType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.MODIFY, printerGroupUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return printerGroupUseTypeDescription;
    }

    private PrinterGroupUseTypeDescription getPrinterGroupUseTypeDescription(PrinterGroupUseType printerGroupUseType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PrinterGroupUseTypeDescriptions.fields())
                .from(PrinterGroupUseTypeDescriptions)
                .where(PrinterGroupUseTypeDescriptions.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                        PrinterGroupUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PrinterGroupUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return printerGroupUseTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PrinterGroupUseTypeDescription getPrinterGroupUseTypeDescription(PrinterGroupUseType printerGroupUseType, Language language) {
        return getPrinterGroupUseTypeDescription(printerGroupUseType, language, EntityPermission.READ_ONLY);
    }

    public PrinterGroupUseTypeDescription getPrinterGroupUseTypeDescriptionForUpdate(PrinterGroupUseType printerGroupUseType, Language language) {
        return getPrinterGroupUseTypeDescription(printerGroupUseType, language, EntityPermission.READ_WRITE);
    }

    public PrinterGroupUseTypeDescriptionValue getPrinterGroupUseTypeDescriptionValue(PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        return printerGroupUseTypeDescription == null? null: printerGroupUseTypeDescription.getPrinterGroupUseTypeDescriptionValue().clone();
    }

    public PrinterGroupUseTypeDescriptionValue getPrinterGroupUseTypeDescriptionValueForUpdate(PrinterGroupUseType printerGroupUseType, Language language) {
        return getPrinterGroupUseTypeDescriptionValue(getPrinterGroupUseTypeDescriptionForUpdate(printerGroupUseType, language));
    }

    private List<PrinterGroupUseTypeDescription> getPrinterGroupUseTypeDescriptionsByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PrinterGroupUseTypeDescriptions.fields())
                    .from(PrinterGroupUseTypeDescriptions)
                    .join(Languages).onKey(PRINTER_GROUP_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PrinterGroupUseTypeDescriptions.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                            PrinterGroupUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PrinterGroupUseTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PrinterGroupUseTypeDescriptions.fields())
                    .from(PrinterGroupUseTypeDescriptions)
                    .where(PrinterGroupUseTypeDescriptions.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                            PrinterGroupUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return printerGroupUseTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PrinterGroupUseTypeDescription> getPrinterGroupUseTypeDescriptionsByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType) {
        return getPrinterGroupUseTypeDescriptionsByPrinterGroupUseType(printerGroupUseType, EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupUseTypeDescription> getPrinterGroupUseTypeDescriptionsByPrinterGroupUseTypeForUpdate(PrinterGroupUseType printerGroupUseType) {
        return getPrinterGroupUseTypeDescriptionsByPrinterGroupUseType(printerGroupUseType, EntityPermission.READ_WRITE);
    }

    public String getBestPrinterGroupUseTypeDescription(PrinterGroupUseType printerGroupUseType, Language language) {
        String description;
        var printerGroupUseTypeDescription = getPrinterGroupUseTypeDescription(printerGroupUseType, language);

        if(printerGroupUseTypeDescription == null && !language.getIsDefault()) {
            printerGroupUseTypeDescription = getPrinterGroupUseTypeDescription(printerGroupUseType, partyControl.getDefaultLanguage());
        }

        if(printerGroupUseTypeDescription == null) {
            description = printerGroupUseType.getLastDetail().getPrinterGroupUseTypeName();
        } else {
            description = printerGroupUseTypeDescription.getDescription();
        }

        return description;
    }

    public PrinterGroupUseTypeDescriptionTransfer getPrinterGroupUseTypeDescriptionTransfer(UserVisit userVisit, PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        return printerGroupUseTypeDescriptionTransferCache.getPrinterGroupUseTypeDescriptionTransfer(userVisit, printerGroupUseTypeDescription);
    }

    public List<PrinterGroupUseTypeDescriptionTransfer> getPrinterGroupUseTypeDescriptionTransfersByPrinterGroupUseType(UserVisit userVisit, PrinterGroupUseType printerGroupUseType) {
        var printerGroupUseTypeDescriptions = getPrinterGroupUseTypeDescriptionsByPrinterGroupUseType(printerGroupUseType);
        List<PrinterGroupUseTypeDescriptionTransfer> printerGroupUseTypeDescriptionTransfers = new ArrayList<>(printerGroupUseTypeDescriptions.size());

        printerGroupUseTypeDescriptions.forEach((printerGroupUseTypeDescription) ->
                printerGroupUseTypeDescriptionTransfers.add(printerGroupUseTypeDescriptionTransferCache.getPrinterGroupUseTypeDescriptionTransfer(userVisit, printerGroupUseTypeDescription))
        );

        return printerGroupUseTypeDescriptionTransfers;
    }

    public void updatePrinterGroupUseTypeDescriptionFromValue(PrinterGroupUseTypeDescriptionValue printerGroupUseTypeDescriptionValue, BasePK updatedBy) {
        if(printerGroupUseTypeDescriptionValue.hasBeenModified()) {
            var printerGroupUseTypeDescription = printerGroupUseTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    printerGroupUseTypeDescriptionValue.getPrimaryKey());

            printerGroupUseTypeDescription.setThruTime(session.getStartTime());
            printerGroupUseTypeDescription.store();

            var printerGroupUseType = printerGroupUseTypeDescription.getPrinterGroupUseType();
            var language = printerGroupUseTypeDescription.getLanguage();
            var description = printerGroupUseTypeDescriptionValue.getDescription();

            printerGroupUseTypeDescription = printerGroupUseTypeDescriptionFactory.create(printerGroupUseType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.MODIFY, printerGroupUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePrinterGroupUseTypeDescription(PrinterGroupUseTypeDescription printerGroupUseTypeDescription, BasePK deletedBy) {
        printerGroupUseTypeDescription.setThruTime(session.getStartTime());

        sendEvent(printerGroupUseTypeDescription.getPrinterGroupUseTypePK(), EventTypes.MODIFY, printerGroupUseTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePrinterGroupUseTypeDescriptionsByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType, BasePK deletedBy) {
        var printerGroupUseTypeDescriptions = getPrinterGroupUseTypeDescriptionsByPrinterGroupUseTypeForUpdate(printerGroupUseType);

        printerGroupUseTypeDescriptions.forEach((printerGroupUseTypeDescription) -> 
                deletePrinterGroupUseTypeDescription(printerGroupUseTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Party Printer Group Uses
    // --------------------------------------------------------------------------------

    @Inject
    private PartyPrinterGroupUseFactory partyPrinterGroupUseFactory;

    @Inject
    private PartyPrinterGroupUseTransferCache partyPrinterGroupUseTransferCache;

    public PartyPrinterGroupUse createPartyPrinterGroupUse(Party party, PrinterGroupUseType printerGroupUseType, PrinterGroup printerGroup, BasePK createdBy) {
        var partyPrinterGroupUse = partyPrinterGroupUseFactory.create(party, printerGroupUseType, printerGroup,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyPrinterGroupUse.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPrinterGroupUse;
    }

    public long countPartyPrinterGroupUsesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPrinterGroupUses)
                .where(PartyPrinterGroupUses.PARTY.eq(party.getPrimaryKey()),
                        PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyPrinterGroupUsesByPrinterGroupUseType(final PrinterGroupUseType printerGroupUseType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPrinterGroupUses)
                .where(PartyPrinterGroupUses.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                        PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyPrinterGroupUsesByPrinterGroup(final PrinterGroup printerGroup) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPrinterGroupUses)
                .where(PartyPrinterGroupUses.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()),
                        PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyPrinterGroupUse getPartyPrinterGroupUse(Party party, PrinterGroupUseType printerGroupUseType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPrinterGroupUses.fields())
                .from(PartyPrinterGroupUses)
                .where(PartyPrinterGroupUses.PARTY.eq(party.getPrimaryKey()),
                        PartyPrinterGroupUses.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                        PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPrinterGroupUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyPrinterGroupUse getPartyPrinterGroupUse(Party party, PrinterGroupUseType printerGroupUseType) {
        return getPartyPrinterGroupUse(party, printerGroupUseType, EntityPermission.READ_ONLY);
    }

    public PartyPrinterGroupUse getPartyPrinterGroupUseUsingNames(Party party, String printerGroupUseTypeName) {
        var printerGroupUseType = getPrinterGroupUseTypeByName(printerGroupUseTypeName);
        PartyPrinterGroupUse partyPrinterGroupUse = null;

        if(printerGroupUseType != null) {
            partyPrinterGroupUse = getPartyPrinterGroupUse(party, printerGroupUseType, EntityPermission.READ_ONLY);
        }

        return partyPrinterGroupUse;
    }

    public PartyPrinterGroupUse getPartyPrinterGroupUseForUpdate(Party party, PrinterGroupUseType printerGroupUseType) {
        return getPartyPrinterGroupUse(party, printerGroupUseType, EntityPermission.READ_WRITE);
    }

    private List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPrinterGroupUses.fields())
                    .from(PartyPrinterGroupUses)
                    .join(PrinterGroupUseTypes).onKey(PARTY_PRINTER_GROUP_USES_PRINTER_GROUP_USE_TYPE_FK)
                    .join(PrinterGroupUseTypeDetails).onKey(PRINTER_GROUP_USE_TYPES_LAST_DETAIL_FK)
                    .where(PartyPrinterGroupUses.PARTY.eq(party.getPrimaryKey()),
                            PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PrinterGroupUseTypeDetails.SORT_ORDER, PrinterGroupUseTypeDetails.PRINTER_GROUP_USE_TYPE_NAME),
                    PartyPrinterGroupUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyPrinterGroupUses.fields())
                    .from(PartyPrinterGroupUses)
                    .where(PartyPrinterGroupUses.PARTY.eq(party.getPrimaryKey()),
                            PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyPrinterGroupUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByParty(Party party) {
        return getPartyPrinterGroupUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPartyForUpdate(Party party) {
        return getPartyPrinterGroupUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroup(PrinterGroup printerGroup, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPrinterGroupUses.fields())
                    .from(PartyPrinterGroupUses)
                    .join(Parties).onKey(PARTY_PRINTER_GROUP_USES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyPrinterGroupUses.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()),
                            PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyPrinterGroupUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyPrinterGroupUses.fields())
                    .from(PartyPrinterGroupUses)
                    .where(PartyPrinterGroupUses.PRINTER_GROUP.eq(printerGroup.getPrimaryKey()),
                            PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyPrinterGroupUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroup(PrinterGroup printerGroup) {
        return getPartyPrinterGroupUsesByPrinterGroup(printerGroup, EntityPermission.READ_ONLY);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroupForUpdate(PrinterGroup printerGroup) {
        return getPartyPrinterGroupUsesByPrinterGroup(printerGroup, EntityPermission.READ_WRITE);
    }

    private List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPrinterGroupUses.fields())
                    .from(PartyPrinterGroupUses)
                    .join(Parties).onKey(PARTY_PRINTER_GROUP_USES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyPrinterGroupUses.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                            PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyPrinterGroupUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyPrinterGroupUses.fields())
                    .from(PartyPrinterGroupUses)
                    .where(PartyPrinterGroupUses.PRINTER_GROUP_USE_TYPE.eq(printerGroupUseType.getPrimaryKey()),
                            PartyPrinterGroupUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyPrinterGroupUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType) {
        return getPartyPrinterGroupUsesByPrinterGroupUseType(printerGroupUseType, EntityPermission.READ_ONLY);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroupUseTypeForUpdate(PrinterGroupUseType printerGroupUseType) {
        return getPartyPrinterGroupUsesByPrinterGroupUseType(printerGroupUseType, EntityPermission.READ_WRITE);
    }

    public PartyPrinterGroupUseValue getPartyPrinterGroupUseValue(PartyPrinterGroupUse partyPrinterGroupUse) {
        return partyPrinterGroupUse == null? null: partyPrinterGroupUse.getPartyPrinterGroupUseValue().clone();
    }
    
    public PartyPrinterGroupUseValue getPartyPrinterGroupUseValueForUpdate(Party party, PrinterGroupUseType printerGroupUseType) {
        return getPartyPrinterGroupUseValue(getPartyPrinterGroupUseForUpdate(party, printerGroupUseType));
    }
    
    public PartyPrinterGroupUseTransfer getPartyPrinterGroupUseTransfer(UserVisit userVisit, PartyPrinterGroupUse partyPrinterGroupUse) {
        return partyPrinterGroupUseTransferCache.getPartyPrinterGroupUseTransfer(userVisit, partyPrinterGroupUse);
    }

    public List<PartyPrinterGroupUseTransfer> getPartyPrinterGroupUseTransfers(UserVisit userVisit, Collection<PartyPrinterGroupUse> partyPrinterGroupUses) {
        List<PartyPrinterGroupUseTransfer> partyPrinterGroupUseTransfers = new ArrayList<>(partyPrinterGroupUses.size());

        partyPrinterGroupUses.forEach((partyPrinterGroupUse) ->
                partyPrinterGroupUseTransfers.add(partyPrinterGroupUseTransferCache.getPartyPrinterGroupUseTransfer(userVisit, partyPrinterGroupUse))
        );

        return partyPrinterGroupUseTransfers;
    }

    public List<PartyPrinterGroupUseTransfer> getPartyPrinterGroupUseTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyPrinterGroupUseTransfers(userVisit, getPartyPrinterGroupUsesByParty(party));
    }

    public void updatePartyPrinterGroupUseFromValue(PartyPrinterGroupUseValue partyPrinterGroupUseValue, BasePK updatedBy) {
        if(partyPrinterGroupUseValue.hasBeenModified()) {
            var partyPrinterGroupUse = partyPrinterGroupUseFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyPrinterGroupUseValue.getPrimaryKey());
            
            partyPrinterGroupUse.setThruTime(session.getStartTime());
            partyPrinterGroupUse.store();

            var partyPK = partyPrinterGroupUse.getPartyPK(); // Not updated
            var printerGroupUseTypePK = partyPrinterGroupUse.getPrinterGroupUseTypePK(); // Not updated
            var printerGroupPK = partyPrinterGroupUseValue.getPrinterGroupPK();
            
            partyPrinterGroupUse = partyPrinterGroupUseFactory.create(partyPK, printerGroupUseTypePK,
                    printerGroupPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyPrinterGroupUse.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyPrinterGroupUse(PartyPrinterGroupUse partyPrinterGroupUse, BasePK deletedBy) {
        partyPrinterGroupUse.setThruTime(session.getStartTime());

        sendEvent(partyPrinterGroupUse.getPartyPK(), EventTypes.MODIFY, partyPrinterGroupUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyPrinterGroupUses(List<PartyPrinterGroupUse> partyPrinterGroupUses, BasePK deletedBy) {
        partyPrinterGroupUses.forEach((partyPrinterGroupUse) -> 
                deletePartyPrinterGroupUse(partyPrinterGroupUse, deletedBy)
        );
    }

    public void deletePartyPrinterGroupUsesByParty(Party party, BasePK deletedBy) {
        deletePartyPrinterGroupUses(getPartyPrinterGroupUsesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyPrinterGroupUsesByPrinterGroup(PrinterGroup printerGroup, BasePK deletedBy) {
        deletePartyPrinterGroupUses(getPartyPrinterGroupUsesByPrinterGroupForUpdate(printerGroup), deletedBy);
    }

    public void deletePartyPrinterGroupUsesByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType, BasePK deletedBy) {
        deletePartyPrinterGroupUses(getPartyPrinterGroupUsesByPrinterGroupUseTypeForUpdate(printerGroupUseType), deletedBy);
    }

}
