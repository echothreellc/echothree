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

package com.echothree.model.control.printer.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class PrinterControl
        extends BaseModelControl {
    
    /** Creates a new instance of PrinterControl */
    protected PrinterControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Printer Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    PrinterGroupTransferCache printerGroupTransferCache;

    @Inject
    PrinterGroupDescriptionTransferCache printerGroupDescriptionTransferCache;

    @Inject
    PrinterGroupUseTypeTransferCache printerGroupUseTypeTransferCache;

    @Inject
    PrinterGroupUseTypeDescriptionTransferCache printerGroupUseTypeDescriptionTransferCache;

    @Inject
    PartyPrinterGroupUseTransferCache partyPrinterGroupUseTransferCache;

    @Inject
    PrinterTransferCache printerTransferCache;

    @Inject
    PrinterDescriptionTransferCache printerDescriptionTransferCache;

    @Inject
    PrinterGroupJobTransferCache printerGroupJobTransferCache;

    // --------------------------------------------------------------------------------
    //   Printer Groups
    // --------------------------------------------------------------------------------

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

        var printerGroup = PrinterGroupFactory.getInstance().create();
        var printerGroupDetail = PrinterGroupDetailFactory.getInstance().create(printerGroup, printerGroupName, keepPrintedJobsTime, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        printerGroup = PrinterGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                printerGroup.getPrimaryKey());
        printerGroup.setActiveDetail(printerGroupDetail);
        printerGroup.setLastDetail(printerGroupDetail);
        printerGroup.store();

        sendEvent(printerGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printerGroup;
    }

    private static final Map<EntityPermission, String> getPrinterGroupByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "AND prngrpdt_printergroupname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "AND prngrpdt_printergroupname = ? " +
                "FOR UPDATE");
        getPrinterGroupByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroup getPrinterGroupByName(String printerGroupName, EntityPermission entityPermission) {
        return PrinterGroupFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterGroupByNameQueries, printerGroupName);
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

    private static final Map<EntityPermission, String> getDefaultPrinterGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "AND prngrpdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "AND prngrpdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPrinterGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroup getDefaultPrinterGroup(EntityPermission entityPermission) {
        return PrinterGroupFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPrinterGroupQueries);
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

    private static final Map<EntityPermission, String> getPrinterGroupsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "ORDER BY prngrpdt_sortorder, prngrpdt_printergroupname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "FOR UPDATE");
        getPrinterGroupsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroup> getPrinterGroups(EntityPermission entityPermission) {
        return PrinterGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupsQueries);
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
            var printerGroup = PrinterGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     printerGroupDetailValue.getPrinterGroupPK());
            var printerGroupDetail = printerGroup.getActiveDetailForUpdate();

            printerGroupDetail.setThruTime(session.START_TIME_LONG);
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

            printerGroupDetail = PrinterGroupDetailFactory.getInstance().create(printerGroupPK, printerGroupName, keepPrintedJobsTime, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        printerGroupDetail.setThruTime(session.START_TIME_LONG);
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

    public PrinterGroupDescription createPrinterGroupDescription(PrinterGroup printerGroup,
            Language language, String description, BasePK createdBy) {
        var printerGroupDescription = PrinterGroupDescriptionFactory.getInstance().create(printerGroup,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(printerGroup.getPrimaryKey(), EventTypes.MODIFY, printerGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return printerGroupDescription;
    }

    private static final Map<EntityPermission, String> getPrinterGroupDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupdescriptions " +
                "WHERE prngrpd_prngrp_printergroupid = ? AND prngrpd_lang_languageid = ? AND prngrpd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupdescriptions " +
                "WHERE prngrpd_prngrp_printergroupid = ? AND prngrpd_lang_languageid = ? AND prngrpd_thrutime = ? " +
                "FOR UPDATE");
        getPrinterGroupDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroupDescription getPrinterGroupDescription(PrinterGroup printerGroup,
            Language language, EntityPermission entityPermission) {
        return PrinterGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterGroupDescriptionQueries,
                printerGroup, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getPrinterGroupDescriptionsByPrinterGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupdescriptions, languages " +
                "WHERE prngrpd_prngrp_printergroupid = ? AND prngrpd_thrutime = ? AND prngrpd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupdescriptions " +
                "WHERE prngrpd_prngrp_printergroupid = ? AND prngrpd_thrutime = ? " +
                "FOR UPDATE");
        getPrinterGroupDescriptionsByPrinterGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroupDescription> getPrinterGroupDescriptionsByPrinterGroup(PrinterGroup printerGroup,
            EntityPermission entityPermission) {
        return PrinterGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupDescriptionsByPrinterGroupQueries,
                printerGroup, Session.MAX_TIME);
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
            var printerGroupDescription = PrinterGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    printerGroupDescriptionValue.getPrimaryKey());

            printerGroupDescription.setThruTime(session.START_TIME_LONG);
            printerGroupDescription.store();

            var printerGroup = printerGroupDescription.getPrinterGroup();
            var language = printerGroupDescription.getLanguage();
            var description = printerGroupDescriptionValue.getDescription();

            printerGroupDescription = PrinterGroupDescriptionFactory.getInstance().create(printerGroup, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(printerGroup.getPrimaryKey(), EventTypes.MODIFY, printerGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePrinterGroupDescription(PrinterGroupDescription printerGroupDescription, BasePK deletedBy) {
        printerGroupDescription.setThruTime(session.START_TIME_LONG);

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

    public Printer createPrinter(String printerName, PrinterGroup printerGroup, Integer priority, BasePK createdBy) {
        var printer = PrinterFactory.getInstance().create();
        var printerDetail = PrinterDetailFactory.getInstance().create(printer, printerName, printerGroup, priority, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        printer = PrinterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                printer.getPrimaryKey());
        printer.setActiveDetail(printerDetail);
        printer.setLastDetail(printerDetail);
        printer.store();

        sendEvent(printer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printer;
    }

    public long countPrintersByPrinterGroup(PrinterGroup printerGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM printergroups, printergroupdetails " +
                "WHERE prngrp_activedetailid = prngrpdt_printergroupdetailid " +
                "AND prngrpdt_prngrp_printergroupid = ?",
                printerGroup);
    }

    private static final Map<EntityPermission, String> getPrinterByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "AND prndt_printername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "AND prndt_printername = ? " +
                "FOR UPDATE");
        getPrinterByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Printer getPrinterByName(String printerName, EntityPermission entityPermission) {
        return PrinterFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterByNameQueries, printerName);
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

    private static final Map<EntityPermission, String> getDefaultPrinterQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "AND prndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "AND prndt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPrinterQueries = Collections.unmodifiableMap(queryMap);
    }

    private Printer getDefaultPrinter(EntityPermission entityPermission) {
        return PrinterFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPrinterQueries);
    }

    public Printer getDefaultPrinter() {
        return getDefaultPrinter(EntityPermission.READ_ONLY);
    }

    public Printer getDefaultPrinterForUpdate() {
        return getDefaultPrinter(EntityPermission.READ_WRITE);
    }

    public PrinterDetailValue getDefaultPrinterDetailValueForUpdate() {
        return getDefaultPrinterForUpdate().getLastDetailForUpdate().getPrinterDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPrintersQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "ORDER BY prndt_priority, prndt_printername " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "FOR UPDATE");
        getPrintersQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Printer> getPrinters(EntityPermission entityPermission) {
        return PrinterFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrintersQueries);
    }

    public List<Printer> getPrinters() {
        return getPrinters(EntityPermission.READ_ONLY);
    }

    public List<Printer> getPrintersForUpdate() {
        return getPrinters(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPrintersByPrinterGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "AND prndt_prngrp_printergroupid = ? " +
                "ORDER BY prndt_priority, prndt_printername " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printers, printerdetails " +
                "WHERE prn_activedetailid = prndt_printerdetailid " +
                "AND prndt_prngrp_printergroupid = ? " +
                "FOR UPDATE");
        getPrintersByPrinterGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Printer> getPrintersByPrinterGroup(PrinterGroup printerGroup, EntityPermission entityPermission) {
        return PrinterFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrintersByPrinterGroupQueries, printerGroup);
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
            var printer = PrinterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, printerDetailValue.getPrinterPK());
            var printerDetail = printer.getActiveDetailForUpdate();

            printerDetail.setThruTime(session.START_TIME_LONG);
            printerDetail.store();

            var printerPK = printerDetail.getPrinterPK(); // Not updated
            var printerName = printerDetailValue.getPrinterName();
            var printerGroupPK = printerDetailValue.getPrinterGroupPK();
            var priority = printerDetailValue.getPriority();

            printerDetail = PrinterDetailFactory.getInstance().create(printerPK, printerName, printerGroupPK, priority, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

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
        printerDetail.setThruTime(session.START_TIME_LONG);
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

    public PrinterDescription createPrinterDescription(Printer printer, Language language, String description, BasePK createdBy) {
        var printerDescription = PrinterDescriptionFactory.getInstance().create(printer, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(printer.getPrimaryKey(), EventTypes.MODIFY, printerDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return printerDescription;
    }

    private static final Map<EntityPermission, String> getPrinterDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printerdescriptions " +
                "WHERE prnd_prn_printerid = ? AND prnd_lang_languageid = ? AND prnd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printerdescriptions " +
                "WHERE prnd_prn_printerid = ? AND prnd_lang_languageid = ? AND prnd_thrutime = ? " +
                "FOR UPDATE");
        getPrinterDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterDescription getPrinterDescription(Printer printer,
            Language language, EntityPermission entityPermission) {
        return PrinterDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterDescriptionQueries,
                printer, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getPrinterDescriptionsByPrinterQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printerdescriptions, languages " +
                "WHERE prnd_prn_printerid = ? AND prnd_thrutime = ? AND prnd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printerdescriptions " +
                "WHERE prnd_prn_printerid = ? AND prnd_thrutime = ? " +
                "FOR UPDATE");
        getPrinterDescriptionsByPrinterQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterDescription> getPrinterDescriptionsByPrinter(Printer printer,
            EntityPermission entityPermission) {
        return PrinterDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterDescriptionsByPrinterQueries,
                printer, Session.MAX_TIME);
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
            var printerDescription = PrinterDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    printerDescriptionValue.getPrimaryKey());

            printerDescription.setThruTime(session.START_TIME_LONG);
            printerDescription.store();

            var printer = printerDescription.getPrinter();
            var language = printerDescription.getLanguage();
            var description = printerDescriptionValue.getDescription();

            printerDescription = PrinterDescriptionFactory.getInstance().create(printer, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(printer.getPrimaryKey(), EventTypes.MODIFY, printerDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePrinterDescription(PrinterDescription printerDescription, BasePK deletedBy) {
        printerDescription.setThruTime(session.START_TIME_LONG);

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

    public PrinterGroupJob createPrinterGroupJob(PrinterGroup printerGroup, Document document, Integer copies, Integer priority, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.PRINTER_GROUP_JOB.name()));
        var printerGroupJobName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createPrinterGroupJob(printerGroupJobName, printerGroup, document, copies, priority, createdBy);
    }

    public PrinterGroupJob createPrinterGroupJob(String printerGroupJobName, PrinterGroup printerGroup, Document document, Integer copies, Integer priority,
            BasePK createdBy) {
        var printerGroupJob = PrinterGroupJobFactory.getInstance().create();
        var printerGroupJobDetail = PrinterGroupJobDetailFactory.getInstance().create(printerGroupJob, printerGroupJobName, printerGroup,
                document, copies, priority, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        printerGroupJob = PrinterGroupJobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                printerGroupJob.getPrimaryKey());
        printerGroupJob.setActiveDetail(printerGroupJobDetail);
        printerGroupJob.setLastDetail(printerGroupJobDetail);
        printerGroupJob.store();

        sendEvent(printerGroupJob.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printerGroupJob;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PrinterGroupJob */
    public PrinterGroupJob getPrinterGroupJobByEntityInstance(EntityInstance entityInstance) {
        var pk = new PrinterGroupJobPK(entityInstance.getEntityUniqueId());
        var printerGroupJob = PrinterGroupJobFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return printerGroupJob;
    }

    private PrinterGroupJob convertEntityInstanceToPrinterGroupJob(final EntityInstance entityInstance, final EntityPermission entityPermission) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        PrinterGroupJob printerGroupJob = null;

        if(entityInstanceControl.verifyEntityInstance(entityInstance, ComponentVendors.ECHO_THREE.name(), EntityTypes.PrinterGroupJob.name())) {
            printerGroupJob = PrinterGroupJobFactory.getInstance().getEntityFromPK(entityPermission, new PrinterGroupJobPK(entityInstance.getEntityUniqueId()));
        }

        return printerGroupJob;
    }

    public PrinterGroupJob convertEntityInstanceToPrinterGroupJob(final EntityInstance entityInstance) {
        return convertEntityInstanceToPrinterGroupJob(entityInstance, EntityPermission.READ_ONLY);
    }

    public PrinterGroupJob convertEntityInstanceToPrinterGroupJobForUpdate(final EntityInstance entityInstance) {
        return convertEntityInstanceToPrinterGroupJob(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_prngrp_printergroupid = ?",
                printerGroup);
    }

    private static final Map<EntityPermission, String> getPrinterGroupJobByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_printergroupjobname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_printergroupjobname = ? " +
                "FOR UPDATE");
        getPrinterGroupJobByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroupJob getPrinterGroupJobByName(String printerGroupJobName, EntityPermission entityPermission) {
        return PrinterGroupJobFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterGroupJobByNameQueries, printerGroupJobName);
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

    private static final Map<EntityPermission, String> getDefaultPrinterGroupJobQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPrinterGroupJobQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroupJob getDefaultPrinterGroupJob(EntityPermission entityPermission) {
        return PrinterGroupJobFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPrinterGroupJobQueries);
    }

    public PrinterGroupJob getDefaultPrinterGroupJob() {
        return getDefaultPrinterGroupJob(EntityPermission.READ_ONLY);
    }

    public PrinterGroupJob getDefaultPrinterGroupJobForUpdate() {
        return getDefaultPrinterGroupJob(EntityPermission.READ_WRITE);
    }

    public PrinterGroupJobDetailValue getDefaultPrinterGroupJobDetailValueForUpdate() {
        return getDefaultPrinterGroupJobForUpdate().getLastDetailForUpdate().getPrinterGroupJobDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPrinterGroupJobsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "ORDER BY prngrpjdt_sortorder, prngrpjdt_printergroupjobname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "FOR UPDATE");
        getPrinterGroupJobsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroupJob> getPrinterGroupJobs(EntityPermission entityPermission) {
        return PrinterGroupJobFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupJobsQueries);
    }

    public List<PrinterGroupJob> getPrinterGroupJobs() {
        return getPrinterGroupJobs(EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsForUpdate() {
        return getPrinterGroupJobs(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPrinterGroupJobsByPrinterGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_prngrp_printergroupid = ? " +
                "ORDER BY prngrpjdt_priority DESC, prngrpjdt_printergroupjobname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupjobs, printergroupjobdetails " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND prngrpjdt_prngrp_printergroupid = ? " +
                "FOR UPDATE");
        getPrinterGroupJobsByPrinterGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup, EntityPermission entityPermission) {
        return PrinterGroupJobFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupJobsByPrinterGroupQueries, printerGroup);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroup(PrinterGroup printerGroup) {
        return getPrinterGroupJobsByPrinterGroup(printerGroup, EntityPermission.READ_ONLY);
    }

    public List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroupForUpdate(PrinterGroup printerGroup) {
        return getPrinterGroupJobsByPrinterGroup(printerGroup, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPrinterGroupJobsByPrinterGroupJobStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, printergroupjobs, printergroupjobdetails, workflowentitystatuses " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND ent_activedetailid = entdt_entitytypedetailid " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                "AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND prngrpj_printergroupjobid = eni_entityuniqueid " +
                "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                "ORDER BY prngrpjdt_priority DESC, prngrpjdt_printergroupjobname" +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, printergroupjobs, printergroupjobdetails, workflowentitystatuses " +
                "WHERE prngrpj_activedetailid = prngrpjdt_printergroupjobdetailid " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND ent_activedetailid = entdt_entitytypedetailid " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                "AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND prngrpj_printergroupjobid = eni_entityuniqueid " +
                "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                "FOR UPDATE");
        getPrinterGroupJobsByPrinterGroupJobStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroupJob> getPrinterGroupJobsByPrinterGroupJobStatus(WorkflowStep workflowStep, EntityPermission entityPermission) {
        return PrinterGroupJobFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupJobsByPrinterGroupJobStatusQueries,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.PrinterGroupJob.name(), workflowStep, Session.MAX_TIME);
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
            var printerGroupJob = PrinterGroupJobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     printerGroupJobDetailValue.getPrinterGroupJobPK());
            var printerGroupJobDetail = printerGroupJob.getActiveDetailForUpdate();

            printerGroupJobDetail.setThruTime(session.START_TIME_LONG);
            printerGroupJobDetail.store();

            var printerGroupJobPK = printerGroupJobDetail.getPrinterGroupJobPK(); // Not updated
            var printerGroupJobName = printerGroupJobDetailValue.getPrinterGroupJobName();
            var printerGroupPK = printerGroupJobDetailValue.getPrinterGroupPK();
            var documentPK = printerGroupJobDetail.getDocumentPK(); // Not updated
            var copies = printerGroupJobDetailValue.getCopies();
            var priority = printerGroupJobDetailValue.getPriority();

            printerGroupJobDetail = PrinterGroupJobDetailFactory.getInstance().create(printerGroupJobPK, printerGroupJobName, printerGroupPK, documentPK,
                    copies, priority, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
                        triggerTime = session.START_TIME_LONG + keepPrintedJobsTime;
                    }
                }
            }

            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPrinterGroupJobStatusChoice.name(), printerGroupJobStatusChoice);
        }
    }

    public void deletePrinterGroupJob(PrinterGroupJob printerGroupJob, BasePK deletedBy) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var printerGroupJobDetail = printerGroupJob.getLastDetailForUpdate();

        documentControl.deleteDocument(printerGroupJobDetail.getDocumentForUpdate(), deletedBy);

        printerGroupJobDetail.setThruTime(session.START_TIME_LONG);
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
        var documentControl = Session.getModelController(DocumentControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

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

        var printerGroupUseType = PrinterGroupUseTypeFactory.getInstance().create();
        var printerGroupUseTypeDetail = PrinterGroupUseTypeDetailFactory.getInstance().create(printerGroupUseType,
                printerGroupUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        printerGroupUseType = PrinterGroupUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                printerGroupUseType.getPrimaryKey());
        printerGroupUseType.setActiveDetail(printerGroupUseTypeDetail);
        printerGroupUseType.setLastDetail(printerGroupUseTypeDetail);
        printerGroupUseType.store();

        sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return printerGroupUseType;
    }

    private static final Map<EntityPermission, String> getPrinterGroupUseTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupusetypes, printergroupusetypedetails " +
                "WHERE prngrpusetyp_activedetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "AND prngrpusetypdt_printergroupusetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupusetypes, printergroupusetypedetails " +
                "WHERE prngrpusetyp_activedetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "AND prngrpusetypdt_printergroupusetypename = ? " +
                "FOR UPDATE");
        getPrinterGroupUseTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroupUseType getPrinterGroupUseTypeByName(String printerGroupUseTypeName, EntityPermission entityPermission) {
        return PrinterGroupUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterGroupUseTypeByNameQueries, printerGroupUseTypeName);
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

    private static final Map<EntityPermission, String> getDefaultPrinterGroupUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupusetypes, printergroupusetypedetails " +
                "WHERE prngrpusetyp_activedetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "AND prngrpusetypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupusetypes, printergroupusetypedetails " +
                "WHERE prngrpusetyp_activedetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "AND prngrpusetypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPrinterGroupUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroupUseType getDefaultPrinterGroupUseType(EntityPermission entityPermission) {
        return PrinterGroupUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPrinterGroupUseTypeQueries);
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

    private static final Map<EntityPermission, String> getPrinterGroupUseTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupusetypes, printergroupusetypedetails " +
                "WHERE prngrpusetyp_activedetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "ORDER BY prngrpusetypdt_sortorder, prngrpusetypdt_printergroupusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupusetypes, printergroupusetypedetails " +
                "WHERE prngrpusetyp_activedetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "FOR UPDATE");
        getPrinterGroupUseTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroupUseType> getPrinterGroupUseTypes(EntityPermission entityPermission) {
        return PrinterGroupUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupUseTypesQueries);
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
            var printerGroupUseType = PrinterGroupUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     printerGroupUseTypeDetailValue.getPrinterGroupUseTypePK());
            var printerGroupUseTypeDetail = printerGroupUseType.getActiveDetailForUpdate();

            printerGroupUseTypeDetail.setThruTime(session.START_TIME_LONG);
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

            printerGroupUseTypeDetail = PrinterGroupUseTypeDetailFactory.getInstance().create(printerGroupUseTypePK, printerGroupUseTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        printerGroupUseTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public PrinterGroupUseTypeDescription createPrinterGroupUseTypeDescription(PrinterGroupUseType printerGroupUseType,
            Language language, String description, BasePK createdBy) {
        var printerGroupUseTypeDescription = PrinterGroupUseTypeDescriptionFactory.getInstance().create(printerGroupUseType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.MODIFY, printerGroupUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return printerGroupUseTypeDescription;
    }

    private static final Map<EntityPermission, String> getPrinterGroupUseTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupusetypedescriptions " +
                "WHERE prngrpusetypd_prngrpusetyp_printergroupusetypeid = ? AND prngrpusetypd_lang_languageid = ? AND prngrpusetypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupusetypedescriptions " +
                "WHERE prngrpusetypd_prngrpusetyp_printergroupusetypeid = ? AND prngrpusetypd_lang_languageid = ? AND prngrpusetypd_thrutime = ? " +
                "FOR UPDATE");
        getPrinterGroupUseTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PrinterGroupUseTypeDescription getPrinterGroupUseTypeDescription(PrinterGroupUseType printerGroupUseType,
            Language language, EntityPermission entityPermission) {
        return PrinterGroupUseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPrinterGroupUseTypeDescriptionQueries,
                printerGroupUseType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getPrinterGroupUseTypeDescriptionsByPrinterGroupUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM printergroupusetypedescriptions, languages " +
                "WHERE prngrpusetypd_prngrpusetyp_printergroupusetypeid = ? AND prngrpusetypd_thrutime = ? AND prngrpusetypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM printergroupusetypedescriptions " +
                "WHERE prngrpusetypd_prngrpusetyp_printergroupusetypeid = ? AND prngrpusetypd_thrutime = ? " +
                "FOR UPDATE");
        getPrinterGroupUseTypeDescriptionsByPrinterGroupUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PrinterGroupUseTypeDescription> getPrinterGroupUseTypeDescriptionsByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType,
            EntityPermission entityPermission) {
        return PrinterGroupUseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPrinterGroupUseTypeDescriptionsByPrinterGroupUseTypeQueries,
                printerGroupUseType, Session.MAX_TIME);
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
            var printerGroupUseTypeDescription = PrinterGroupUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    printerGroupUseTypeDescriptionValue.getPrimaryKey());

            printerGroupUseTypeDescription.setThruTime(session.START_TIME_LONG);
            printerGroupUseTypeDescription.store();

            var printerGroupUseType = printerGroupUseTypeDescription.getPrinterGroupUseType();
            var language = printerGroupUseTypeDescription.getLanguage();
            var description = printerGroupUseTypeDescriptionValue.getDescription();

            printerGroupUseTypeDescription = PrinterGroupUseTypeDescriptionFactory.getInstance().create(printerGroupUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(printerGroupUseType.getPrimaryKey(), EventTypes.MODIFY, printerGroupUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePrinterGroupUseTypeDescription(PrinterGroupUseTypeDescription printerGroupUseTypeDescription, BasePK deletedBy) {
        printerGroupUseTypeDescription.setThruTime(session.START_TIME_LONG);

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
    
    public PartyPrinterGroupUse createPartyPrinterGroupUse(Party party, PrinterGroupUseType printerGroupUseType, PrinterGroup printerGroup, BasePK createdBy) {
        var partyPrinterGroupUse = PartyPrinterGroupUseFactory.getInstance().create(party, printerGroupUseType, printerGroup,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyPrinterGroupUse.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPrinterGroupUse;
    }
    
    private static final Map<EntityPermission, String> getPartyPrinterGroupUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses " +
                "WHERE parprngrpuse_par_partyid = ? AND parprngrpuse_prngrpusetyp_printergroupusetypeid = ? " +
                "AND parprngrpuse_thrutime = ?" +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses " +
                "WHERE parprngrpuse_par_partyid = ? AND parprngrpuse_prngrpusetyp_printergroupusetypeid = ? " +
                "AND parprngrpuse_thrutime = ? " +
                "FOR UPDATE");
        getPartyPrinterGroupUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyPrinterGroupUse getPartyPrinterGroupUse(Party party, PrinterGroupUseType printerGroupUseType, EntityPermission entityPermission) {
        return PartyPrinterGroupUseFactory.getInstance().getEntityFromQuery(entityPermission, getPartyPrinterGroupUseQueries,
                party, printerGroupUseType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getPartyPrinterGroupUsesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses, printergroupusetypes, printergroupusetypedetails " +
                "WHERE parprngrpuse_par_partyid = ? AND parprngrpuse_thrutime = ? " +
                "AND parprngrpuse_prngrpusetyp_printergroupusetypeid = prngrpusetyp_printergroupusetypeid AND prngrpusetyp_lastdetailid = prngrpusetypdt_printergroupusetypedetailid " +
                "ORDER BY prngrpusetypdt_sortorder, prngrpusetypdt_printergroupusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses " +
                "WHERE parprngrpuse_par_partyid = ? AND parprngrpuse_thrutime = ? " +
                "FOR UPDATE");
        getPartyPrinterGroupUsesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByParty(Party party, EntityPermission entityPermission) {
        return PartyPrinterGroupUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyPrinterGroupUsesByPartyQueries,
                party, Session.MAX_TIME);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByParty(Party party) {
        return getPartyPrinterGroupUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPartyForUpdate(Party party) {
        return getPartyPrinterGroupUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyPrinterGroupUsesByPrinterGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses, parties, partydetails " +
                "WHERE parprngrpuse_prngrp_printergroupid = ? AND parprngrpuse_thrutime = ? " +
                "AND parprngrpuse_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses " +
                "WHERE parprngrpuse_prngrp_printergroupid = ? AND parprngrpuse_thrutime = ? " +
                "FOR UPDATE");
        getPartyPrinterGroupUsesByPrinterGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroup(PrinterGroup printerGroup, EntityPermission entityPermission) {
        return PartyPrinterGroupUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyPrinterGroupUsesByPrinterGroupQueries,
                printerGroup, Session.MAX_TIME);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroup(PrinterGroup printerGroup) {
        return getPartyPrinterGroupUsesByPrinterGroup(printerGroup, EntityPermission.READ_ONLY);
    }

    public List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroupForUpdate(PrinterGroup printerGroup) {
        return getPartyPrinterGroupUsesByPrinterGroup(printerGroup, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyPrinterGroupUsesByPrinterGroupUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses, parties, partydetails " +
                "WHERE parprngrpuse_prngrpusetyp_printergroupusetypeid = ? AND parprngrpuse_thrutime = ? " +
                "AND parprngrpuse_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyprintergroupuses " +
                "WHERE parprngrpuse_prngrpusetyp_printergroupusetypeid = ? AND parprngrpuse_thrutime = ? " +
                "FOR UPDATE");
        getPartyPrinterGroupUsesByPrinterGroupUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyPrinterGroupUse> getPartyPrinterGroupUsesByPrinterGroupUseType(PrinterGroupUseType printerGroupUseType, EntityPermission entityPermission) {
        return PartyPrinterGroupUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyPrinterGroupUsesByPrinterGroupUseTypeQueries,
                printerGroupUseType, Session.MAX_TIME);
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
            var partyPrinterGroupUse = PartyPrinterGroupUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyPrinterGroupUseValue.getPrimaryKey());
            
            partyPrinterGroupUse.setThruTime(session.START_TIME_LONG);
            partyPrinterGroupUse.store();

            var partyPK = partyPrinterGroupUse.getPartyPK(); // Not updated
            var printerGroupUseTypePK = partyPrinterGroupUse.getPrinterGroupUseTypePK(); // Not updated
            var printerGroupPK = partyPrinterGroupUseValue.getPrinterGroupPK();
            
            partyPrinterGroupUse = PartyPrinterGroupUseFactory.getInstance().create(partyPK, printerGroupUseTypePK,
                    printerGroupPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyPrinterGroupUse.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyPrinterGroupUse(PartyPrinterGroupUse partyPrinterGroupUse, BasePK deletedBy) {
        partyPrinterGroupUse.setThruTime(session.START_TIME_LONG);

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
