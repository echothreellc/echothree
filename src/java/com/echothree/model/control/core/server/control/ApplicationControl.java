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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.ApplicationChoicesBean;
import com.echothree.model.control.core.common.choice.ApplicationEditorChoicesBean;
import com.echothree.model.control.core.common.choice.ApplicationEditorUseChoicesBean;
import com.echothree.model.control.core.common.transfer.ApplicationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.party.server.control.PartyApplicationEditorUseControl;
import com.echothree.model.data.core.common.pk.ApplicationPK;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationDescription;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDescription;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.ApplicationDescriptionFactory;
import com.echothree.model.data.core.server.factory.ApplicationDetailFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorDetailFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseDescriptionFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseDetailFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseFactory;
import com.echothree.model.data.core.server.factory.ApplicationFactory;
import com.echothree.model.data.core.server.value.ApplicationDescriptionValue;
import com.echothree.model.data.core.server.value.ApplicationDetailValue;
import com.echothree.model.data.core.server.value.ApplicationEditorDetailValue;
import com.echothree.model.data.core.server.value.ApplicationEditorUseDescriptionValue;
import com.echothree.model.data.core.server.value.ApplicationEditorUseDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ApplicationControl
        extends BaseCoreControl {

    /** Creates a new instance of ApplicationControl */
    protected ApplicationControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------

    public Application createApplication(String applicationName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultApplication = getDefaultApplication();
        var defaultFound = defaultApplication != null;

        if(defaultFound && isDefault) {
            var defaultApplicationDetailValue = getDefaultApplicationDetailValueForUpdate();

            defaultApplicationDetailValue.setIsDefault(false);
            updateApplicationFromValue(defaultApplicationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var application = ApplicationFactory.getInstance().create();
        var applicationDetail = ApplicationDetailFactory.getInstance().create(application, applicationName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        application = ApplicationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, application.getPrimaryKey());
        application.setActiveDetail(applicationDetail);
        application.setLastDetail(applicationDetail);
        application.store();

        sendEvent(application.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return application;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Application */
    public Application getApplicationByEntityInstance(EntityInstance entityInstance) {
        var pk = new ApplicationPK(entityInstance.getEntityUniqueId());

        return ApplicationFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    private static final Map<EntityPermission, String> getApplicationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applications, applicationdetails " +
                        "WHERE appl_activedetailid = appldt_applicationdetailid " +
                        "AND appldt_applicationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applications, applicationdetails " +
                        "WHERE appl_activedetailid = appldt_applicationdetailid " +
                        "AND appldt_applicationname = ? " +
                        "FOR UPDATE");
        getApplicationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Application getApplicationByName(String applicationName, EntityPermission entityPermission) {
        return ApplicationFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationByNameQueries, applicationName);
    }

    public Application getApplicationByName(String applicationName) {
        return getApplicationByName(applicationName, EntityPermission.READ_ONLY);
    }

    public Application getApplicationByNameForUpdate(String applicationName) {
        return getApplicationByName(applicationName, EntityPermission.READ_WRITE);
    }

    public ApplicationDetailValue getApplicationDetailValueForUpdate(Application application) {
        return application == null? null: application.getLastDetailForUpdate().getApplicationDetailValue().clone();
    }

    public ApplicationDetailValue getApplicationDetailValueByNameForUpdate(String applicationName) {
        return getApplicationDetailValueForUpdate(getApplicationByNameForUpdate(applicationName));
    }

    private static final Map<EntityPermission, String> getDefaultApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applications, applicationdetails " +
                        "WHERE appl_activedetailid = appldt_applicationdetailid " +
                        "AND appldt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applications, applicationdetails " +
                        "WHERE appl_activedetailid = appldt_applicationdetailid " +
                        "AND appldt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private Application getDefaultApplication(EntityPermission entityPermission) {
        return ApplicationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultApplicationQueries);
    }

    public Application getDefaultApplication() {
        return getDefaultApplication(EntityPermission.READ_ONLY);
    }

    public Application getDefaultApplicationForUpdate() {
        return getDefaultApplication(EntityPermission.READ_WRITE);
    }

    public ApplicationDetailValue getDefaultApplicationDetailValueForUpdate() {
        return getDefaultApplicationForUpdate().getLastDetailForUpdate().getApplicationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getApplicationsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applications, applicationdetails " +
                        "WHERE appl_activedetailid = appldt_applicationdetailid " +
                        "ORDER BY appldt_sortorder, appldt_applicationname " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applications, applicationdetails " +
                        "WHERE appl_activedetailid = appldt_applicationdetailid " +
                        "FOR UPDATE");
        getApplicationsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Application> getApplications(EntityPermission entityPermission) {
        return ApplicationFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationsQueries);
    }

    public List<Application> getApplications() {
        return getApplications(EntityPermission.READ_ONLY);
    }

    public List<Application> getApplicationsForUpdate() {
        return getApplications(EntityPermission.READ_WRITE);
    }

    public ApplicationTransfer getApplicationTransfer(UserVisit userVisit, Application application) {
        return getCoreTransferCaches(userVisit).getApplicationTransferCache().getApplicationTransfer(application);
    }

    public List<ApplicationTransfer> getApplicationTransfers(UserVisit userVisit) {
        var applications = getApplications();
        List<ApplicationTransfer> applicationTransfers = new ArrayList<>(applications.size());
        var applicationTransferCache = getCoreTransferCaches(userVisit).getApplicationTransferCache();

        applications.forEach((application) ->
                applicationTransfers.add(applicationTransferCache.getApplicationTransfer(application))
        );

        return applicationTransfers;
    }

    public ApplicationChoicesBean getApplicationChoices(String defaultApplicationChoice, Language language, boolean allowNullChoice) {
        var applications = getApplications();
        var size = applications.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultApplicationChoice == null) {
                defaultValue = "";
            }
        }

        for(var application : applications) {
            var applicationDetail = application.getLastDetail();

            var label = getBestApplicationDescription(application, language);
            var value = applicationDetail.getApplicationName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultApplicationChoice != null && defaultApplicationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && applicationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ApplicationChoicesBean(labels, values, defaultValue);
    }

    private void updateApplicationFromValue(ApplicationDetailValue applicationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(applicationDetailValue.hasBeenModified()) {
            var application = ApplicationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationDetailValue.getApplicationPK());
            var applicationDetail = application.getActiveDetailForUpdate();

            applicationDetail.setThruTime(session.START_TIME_LONG);
            applicationDetail.store();

            var applicationPK = applicationDetail.getApplicationPK(); // Not updated
            var applicationName = applicationDetailValue.getApplicationName();
            var isDefault = applicationDetailValue.getIsDefault();
            var sortOrder = applicationDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultApplication = getDefaultApplication();
                var defaultFound = defaultApplication != null && !defaultApplication.equals(application);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultApplicationDetailValue = getDefaultApplicationDetailValueForUpdate();

                    defaultApplicationDetailValue.setIsDefault(false);
                    updateApplicationFromValue(defaultApplicationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            applicationDetail = ApplicationDetailFactory.getInstance().create(applicationPK, applicationName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            application.setActiveDetail(applicationDetail);
            application.setLastDetail(applicationDetail);

            sendEvent(applicationPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateApplicationFromValue(ApplicationDetailValue applicationDetailValue, BasePK updatedBy) {
        updateApplicationFromValue(applicationDetailValue, true, updatedBy);
    }

    private void deleteApplication(Application application, boolean checkDefault, BasePK deletedBy) {
        var applicationDetail = application.getLastDetailForUpdate();

        deleteApplicationDescriptionsByApplication(application, deletedBy);
        deleteApplicationEditorsByApplication(application, deletedBy);
        deleteApplicationEditorUsesByApplication(application, deletedBy);

        applicationDetail.setThruTime(session.START_TIME_LONG);
        application.setActiveDetail(null);
        application.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultApplication = getDefaultApplication();

            if(defaultApplication == null) {
                var applications = getApplicationsForUpdate();

                if(!applications.isEmpty()) {
                    var iter = applications.iterator();
                    if(iter.hasNext()) {
                        defaultApplication = iter.next();
                    }
                    var applicationDetailValue = Objects.requireNonNull(defaultApplication).getLastDetailForUpdate().getApplicationDetailValue().clone();

                    applicationDetailValue.setIsDefault(true);
                    updateApplicationFromValue(applicationDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(application.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteApplication(Application application, BasePK deletedBy) {
        deleteApplication(application, true, deletedBy);
    }

    private void deleteApplications(List<Application> applications, boolean checkDefault, BasePK deletedBy) {
        applications.forEach((application) -> deleteApplication(application, checkDefault, deletedBy));
    }

    public void deleteApplications(List<Application> applications, BasePK deletedBy) {
        deleteApplications(applications, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Application Descriptions
    // --------------------------------------------------------------------------------

    public ApplicationDescription createApplicationDescription(Application application, Language language, String description, BasePK createdBy) {
        var applicationDescription = ApplicationDescriptionFactory.getInstance().create(application, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(application.getPrimaryKey(), EventTypes.MODIFY, applicationDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return applicationDescription;
    }

    private static final Map<EntityPermission, String> getApplicationDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applicationdescriptions " +
                        "WHERE appld_appl_applicationid = ? AND appld_lang_languageid = ? AND appld_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applicationdescriptions " +
                        "WHERE appld_appl_applicationid = ? AND appld_lang_languageid = ? AND appld_thrutime = ? " +
                        "FOR UPDATE");
        getApplicationDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationDescription getApplicationDescription(Application application, Language language, EntityPermission entityPermission) {
        return ApplicationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationDescriptionQueries,
                application, language, Session.MAX_TIME);
    }

    public ApplicationDescription getApplicationDescription(Application application, Language language) {
        return getApplicationDescription(application, language, EntityPermission.READ_ONLY);
    }

    public ApplicationDescription getApplicationDescriptionForUpdate(Application application, Language language) {
        return getApplicationDescription(application, language, EntityPermission.READ_WRITE);
    }

    public ApplicationDescriptionValue getApplicationDescriptionValue(ApplicationDescription applicationDescription) {
        return applicationDescription == null? null: applicationDescription.getApplicationDescriptionValue().clone();
    }

    public ApplicationDescriptionValue getApplicationDescriptionValueForUpdate(Application application, Language language) {
        return getApplicationDescriptionValue(getApplicationDescriptionForUpdate(application, language));
    }

    private static final Map<EntityPermission, String> getApplicationDescriptionsByApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applicationdescriptions, languages " +
                        "WHERE appld_appl_applicationid = ? AND appld_thrutime = ? AND appld_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applicationdescriptions " +
                        "WHERE appld_appl_applicationid = ? AND appld_thrutime = ? " +
                        "FOR UPDATE");
        getApplicationDescriptionsByApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationDescription> getApplicationDescriptionsByApplication(Application application, EntityPermission entityPermission) {
        return ApplicationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationDescriptionsByApplicationQueries,
                application, Session.MAX_TIME);
    }

    public List<ApplicationDescription> getApplicationDescriptionsByApplication(Application application) {
        return getApplicationDescriptionsByApplication(application, EntityPermission.READ_ONLY);
    }

    public List<ApplicationDescription> getApplicationDescriptionsByApplicationForUpdate(Application application) {
        return getApplicationDescriptionsByApplication(application, EntityPermission.READ_WRITE);
    }

    public String getBestApplicationDescription(Application application, Language language) {
        String description;
        var applicationDescription = getApplicationDescription(application, language);

        if(applicationDescription == null && !language.getIsDefault()) {
            applicationDescription = getApplicationDescription(application, getPartyControl().getDefaultLanguage());
        }

        if(applicationDescription == null) {
            description = application.getLastDetail().getApplicationName();
        } else {
            description = applicationDescription.getDescription();
        }

        return description;
    }

    public ApplicationDescriptionTransfer getApplicationDescriptionTransfer(UserVisit userVisit, ApplicationDescription applicationDescription) {
        return getCoreTransferCaches(userVisit).getApplicationDescriptionTransferCache().getApplicationDescriptionTransfer(applicationDescription);
    }

    public List<ApplicationDescriptionTransfer> getApplicationDescriptionTransfersByApplication(UserVisit userVisit, Application application) {
        var applicationDescriptions = getApplicationDescriptionsByApplication(application);
        List<ApplicationDescriptionTransfer> applicationDescriptionTransfers = new ArrayList<>(applicationDescriptions.size());
        var applicationDescriptionTransferCache = getCoreTransferCaches(userVisit).getApplicationDescriptionTransferCache();

        applicationDescriptions.forEach((applicationDescription) ->
                applicationDescriptionTransfers.add(applicationDescriptionTransferCache.getApplicationDescriptionTransfer(applicationDescription))
        );

        return applicationDescriptionTransfers;
    }

    public void updateApplicationDescriptionFromValue(ApplicationDescriptionValue applicationDescriptionValue, BasePK updatedBy) {
        if(applicationDescriptionValue.hasBeenModified()) {
            var applicationDescription = ApplicationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationDescriptionValue.getPrimaryKey());

            applicationDescription.setThruTime(session.START_TIME_LONG);
            applicationDescription.store();

            var application = applicationDescription.getApplication();
            var language = applicationDescription.getLanguage();
            var description = applicationDescriptionValue.getDescription();

            applicationDescription = ApplicationDescriptionFactory.getInstance().create(application, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(application.getPrimaryKey(), EventTypes.MODIFY, applicationDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteApplicationDescription(ApplicationDescription applicationDescription, BasePK deletedBy) {
        applicationDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(applicationDescription.getApplicationPK(), EventTypes.MODIFY, applicationDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteApplicationDescriptionsByApplication(Application application, BasePK deletedBy) {
        var applicationDescriptions = getApplicationDescriptionsByApplicationForUpdate(application);

        applicationDescriptions.forEach((applicationDescription) ->
                deleteApplicationDescription(applicationDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Application Editors
    // --------------------------------------------------------------------------------

    public ApplicationEditor createApplicationEditor(Application application, Editor editor, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultApplicationEditor = getDefaultApplicationEditor(application);
        var defaultFound = defaultApplicationEditor != null;

        if(defaultFound && isDefault) {
            var defaultApplicationEditorDetailValue = getDefaultApplicationEditorDetailValueForUpdate(application);

            defaultApplicationEditorDetailValue.setIsDefault(false);
            updateApplicationEditorFromValue(defaultApplicationEditorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var applicationEditor = ApplicationEditorFactory.getInstance().create();
        var applicationEditorDetail = ApplicationEditorDetailFactory.getInstance().create(applicationEditor, application, editor, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        applicationEditor = ApplicationEditorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, applicationEditor.getPrimaryKey());
        applicationEditor.setActiveDetail(applicationEditorDetail);
        applicationEditor.setLastDetail(applicationEditorDetail);
        applicationEditor.store();

        sendEvent(applicationEditor.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return applicationEditor;
    }

    private static final Map<EntityPermission, String> getApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                        + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_edtr_editorid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                        + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_edtr_editorid = ? "
                        + "FOR UPDATE");
        getApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditor getApplicationEditor(Application application, Editor editor, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationEditorQueries,
                application, editor);
    }

    public ApplicationEditor getApplicationEditor(Application application, Editor editor) {
        return getApplicationEditor(application, editor, EntityPermission.READ_ONLY);
    }

    public ApplicationEditor getApplicationEditorForUpdate(Application application, Editor editor) {
        return getApplicationEditor(application, editor, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorDetailValue getApplicationEditorDetailValueForUpdate(ApplicationEditor applicationEditor) {
        return applicationEditor == null? null: applicationEditor.getLastDetailForUpdate().getApplicationEditorDetailValue().clone();
    }

    public ApplicationEditorDetailValue getApplicationEditorDetailValueForUpdate(Application application, Editor editor) {
        return getApplicationEditorDetailValueForUpdate(getApplicationEditorForUpdate(application, editor));
    }

    private static final Map<EntityPermission, String> getDefaultApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                        + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                        + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_isdefault = 1 "
                        + "FOR UPDATE");
        getDefaultApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditor getDefaultApplicationEditor(Application application, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultApplicationEditorQueries,
                application);
    }

    public ApplicationEditor getDefaultApplicationEditor(Application application) {
        return getDefaultApplicationEditor(application, EntityPermission.READ_ONLY);
    }

    public ApplicationEditor getDefaultApplicationEditorForUpdate(Application application) {
        return getDefaultApplicationEditor(application, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorDetailValue getDefaultApplicationEditorDetailValueForUpdate(Application application) {
        return getDefaultApplicationEditorForUpdate(application).getLastDetailForUpdate().getApplicationEditorDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getApplicationEditorsByApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails, editors, editordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_appl_applicationid = ? "
                        + "AND appledtrdt_edtr_editorid = edtr_editorid AND edtr_lastdetailid = edtrdt_editordetailid "
                        + "ORDER BY edtrdt_sortorder, edtrdt_editorname "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_appl_applicationid = ? "
                        + "FOR UPDATE");
        getApplicationEditorsByApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditor> getApplicationEditorsByApplication(Application application, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorsByApplicationQueries,
                application);
    }

    public List<ApplicationEditor> getApplicationEditorsByApplication(Application application) {
        return getApplicationEditorsByApplication(application, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditor> getApplicationEditorsByApplicationForUpdate(Application application) {
        return getApplicationEditorsByApplication(application, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getApplicationEditorsByEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails, applications, applicationdetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_edtr_editorid = ? "
                        + "AND appledtrdt_appl_applicationid = appl_applicationid AND appl_lastdetailid = appldt_applicationdetailid "
                        + "ORDER BY appldt_sortorder, appldt_applicationname "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditors, applicationeditordetails "
                        + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_edtr_editorid = ? "
                        + "FOR UPDATE");
        getApplicationEditorsByEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditor> getApplicationEditorsByEditor(Editor editor, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorsByEditorQueries,
                editor);
    }

    public List<ApplicationEditor> getApplicationEditorsByEditor(Editor editor) {
        return getApplicationEditorsByEditor(editor, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditor> getApplicationEditorsByEditorForUpdate(Editor editor) {
        return getApplicationEditorsByEditor(editor, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorTransfer getApplicationEditorTransfer(UserVisit userVisit, ApplicationEditor applicationEditor) {
        return getCoreTransferCaches(userVisit).getApplicationEditorTransferCache().getApplicationEditorTransfer(applicationEditor);
    }

    public List<ApplicationEditorTransfer> getApplicationEditorTransfers(List<ApplicationEditor> applicationEditors, UserVisit userVisit) {
        List<ApplicationEditorTransfer> applicationEditorTransfers = new ArrayList<>(applicationEditors.size());
        var applicationEditorTransferCache = getCoreTransferCaches(userVisit).getApplicationEditorTransferCache();

        applicationEditors.forEach((applicationEditor) ->
                applicationEditorTransfers.add(applicationEditorTransferCache.getApplicationEditorTransfer(applicationEditor))
        );

        return applicationEditorTransfers;
    }

    public List<ApplicationEditorTransfer> getApplicationEditorTransfersByApplication(UserVisit userVisit, Application application) {
        return getApplicationEditorTransfers(getApplicationEditorsByApplication(application), userVisit);
    }

    public List<ApplicationEditorTransfer> getApplicationEditorTransfersByEditor(UserVisit userVisit, Editor editor) {
        return getApplicationEditorTransfers(getApplicationEditorsByEditor(editor), userVisit);
    }

    public ApplicationEditorChoicesBean getApplicationEditorChoices(String defaultApplicationEditorChoice, Language language, boolean allowNullChoice,
            Application application) {
        var editorControl = Session.getModelController(EditorControl.class);
        var applicationEditors = getApplicationEditorsByApplication(application);
        var size = applicationEditors.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultApplicationEditorChoice == null) {
                defaultValue = "";
            }
        }

        for(var applicationEditor : applicationEditors) {
            var applicationEditorDetail = applicationEditor.getLastDetail();
            var editor = applicationEditorDetail.getEditor();

            var label = editorControl.getBestEditorDescription(editor, language);
            var value = editor.getLastDetail().getEditorName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultApplicationEditorChoice != null && defaultApplicationEditorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && applicationEditorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ApplicationEditorChoicesBean(labels, values, defaultValue);
    }

    private void updateApplicationEditorFromValue(ApplicationEditorDetailValue applicationEditorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(applicationEditorDetailValue.hasBeenModified()) {
            var applicationEditor = ApplicationEditorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationEditorDetailValue.getApplicationEditorPK());
            var applicationEditorDetail = applicationEditor.getActiveDetailForUpdate();

            applicationEditorDetail.setThruTime(session.START_TIME_LONG);
            applicationEditorDetail.store();

            var applicationEditorPK = applicationEditorDetail.getApplicationEditorPK(); // Not updated
            var application = applicationEditorDetail.getApplication();
            var applicationPK = application.getPrimaryKey(); // Not updated
            var editorPK = applicationEditorDetail.getEditorPK(); // Not updated
            var isDefault = applicationEditorDetailValue.getIsDefault();
            var sortOrder = applicationEditorDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultApplicationEditor = getDefaultApplicationEditor(application);
                var defaultFound = defaultApplicationEditor != null && !defaultApplicationEditor.equals(applicationEditor);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultApplicationEditorDetailValue = getDefaultApplicationEditorDetailValueForUpdate(application);

                    defaultApplicationEditorDetailValue.setIsDefault(false);
                    updateApplicationEditorFromValue(defaultApplicationEditorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            applicationEditorDetail = ApplicationEditorDetailFactory.getInstance().create(applicationEditorPK, applicationPK, editorPK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            applicationEditor.setActiveDetail(applicationEditorDetail);
            applicationEditor.setLastDetail(applicationEditorDetail);

            sendEvent(applicationEditorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateApplicationEditorFromValue(ApplicationEditorDetailValue applicationEditorDetailValue, BasePK updatedBy) {
        updateApplicationEditorFromValue(applicationEditorDetailValue, true, updatedBy);
    }

    private void deleteApplicationEditor(ApplicationEditor applicationEditor, boolean checkDefault, BasePK deletedBy) {
        var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
        var applicationEditorDetail = applicationEditor.getLastDetailForUpdate();
        var application = applicationEditorDetail.getApplication();

        deleteApplicationEditorUsesByDefaultApplicationEditor(applicationEditor, deletedBy);
        partyApplicationEditorUseControl.deletePartyApplicationEditorUsesByApplicationEditor(applicationEditor, deletedBy);

        applicationEditorDetail.setThruTime(session.START_TIME_LONG);
        applicationEditor.setActiveDetail(null);
        applicationEditor.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultApplicationEditor = getDefaultApplicationEditor(application);

            if(defaultApplicationEditor == null) {
                var applicationEditors = getApplicationEditorsByApplicationForUpdate(application);

                if(!applicationEditors.isEmpty()) {
                    var iter = applicationEditors.iterator();
                    if(iter.hasNext()) {
                        defaultApplicationEditor = iter.next();
                    }
                    var applicationEditorDetailValue = Objects.requireNonNull(defaultApplicationEditor).getLastDetailForUpdate().getApplicationEditorDetailValue().clone();

                    applicationEditorDetailValue.setIsDefault(true);
                    updateApplicationEditorFromValue(applicationEditorDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(applicationEditor.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteApplicationEditor(ApplicationEditor applicationEditor, BasePK deletedBy) {
        deleteApplicationEditor(applicationEditor, true, deletedBy);
    }

    private void deleteApplicationEditors(List<ApplicationEditor> applicationEditors, boolean checkDefault, BasePK deletedBy) {
        applicationEditors.forEach((applicationEditor) -> deleteApplicationEditor(applicationEditor, checkDefault, deletedBy));
    }

    public void deleteApplicationEditors(List<ApplicationEditor> applicationEditors, BasePK deletedBy) {
        deleteApplicationEditors(applicationEditors, true, deletedBy);
    }

    public void deleteApplicationEditorsByApplication(Application application, BasePK deletedBy) {
        deleteApplicationEditors(getApplicationEditorsByApplicationForUpdate(application), false, deletedBy);
    }

    public void deleteApplicationEditorsByEditor(Editor editor, BasePK deletedBy) {
        deleteApplicationEditors(getApplicationEditorsByEditorForUpdate(editor), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------

    public ApplicationEditorUse createApplicationEditorUse(Application application, String applicationEditorUseName, ApplicationEditor defaultApplicationEditor,
            Integer defaultHeight, Integer defaultWidth, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultApplicationEditorUse = getDefaultApplicationEditorUse(application);
        var defaultFound = defaultApplicationEditorUse != null;

        if(defaultFound && isDefault) {
            var defaultApplicationEditorUseDetailValue = getDefaultApplicationEditorUseDetailValueForUpdate(application);

            defaultApplicationEditorUseDetailValue.setIsDefault(false);
            updateApplicationEditorUseFromValue(defaultApplicationEditorUseDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var applicationEditorUse = ApplicationEditorUseFactory.getInstance().create();
        var applicationEditorUseDetail = ApplicationEditorUseDetailFactory.getInstance().create(applicationEditorUse, application,
                applicationEditorUseName, defaultApplicationEditor, defaultHeight, defaultWidth, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        applicationEditorUse = ApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, applicationEditorUse.getPrimaryKey());
        applicationEditorUse.setActiveDetail(applicationEditorUseDetail);
        applicationEditorUse.setLastDetail(applicationEditorUseDetail);
        applicationEditorUse.store();

        sendEvent(applicationEditorUse.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return applicationEditorUse;
    }

    private static final Map<EntityPermission, String> getApplicationEditorUseByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                        + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_applicationeditorusename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                        + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_applicationeditorusename = ? "
                        + "FOR UPDATE");
        getApplicationEditorUseByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditorUse getApplicationEditorUseByName(Application application, String applicationEditorUseName, EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationEditorUseByNameQueries,
                application, applicationEditorUseName);
    }

    public ApplicationEditorUse getApplicationEditorUseByName(Application application, String applicationEditorUseName) {
        return getApplicationEditorUseByName(application, applicationEditorUseName, EntityPermission.READ_ONLY);
    }

    public ApplicationEditorUse getApplicationEditorUseByNameForUpdate(Application application, String applicationEditorUseName) {
        return getApplicationEditorUseByName(application, applicationEditorUseName, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseDetailValue getApplicationEditorUseDetailValueForUpdate(ApplicationEditorUse applicationEditorUse) {
        return applicationEditorUse == null ? null : applicationEditorUse.getLastDetailForUpdate().getApplicationEditorUseDetailValue().clone();
    }

    public ApplicationEditorUseDetailValue getApplicationEditorUseDetailValueByNameForUpdate(Application application, String applicationEditorUseName) {
        return getApplicationEditorUseDetailValueForUpdate(getApplicationEditorUseByNameForUpdate(application, applicationEditorUseName));
    }

    private static final Map<EntityPermission, String> getDefaultApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                        + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                        + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_isdefault = 1 "
                        + "FOR UPDATE");
        getDefaultApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditorUse getDefaultApplicationEditorUse(Application application, EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultApplicationEditorUseQueries,
                application);
    }

    public ApplicationEditorUse getDefaultApplicationEditorUse(Application application) {
        return getDefaultApplicationEditorUse(application, EntityPermission.READ_ONLY);
    }

    public ApplicationEditorUse getDefaultApplicationEditorUseForUpdate(Application application) {
        return getDefaultApplicationEditorUse(application, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseDetailValue getDefaultApplicationEditorUseDetailValueForUpdate(Application application) {
        return getDefaultApplicationEditorUseForUpdate(application).getLastDetailForUpdate().getApplicationEditorUseDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getApplicationEditorUsesByApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_appl_applicationid = ? "
                        + "ORDER BY appledtrusedt_sortorder, appledtrusedt_applicationeditorusename "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_appl_applicationid = ? "
                        + "FOR UPDATE");
        getApplicationEditorUsesByApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditorUse> getApplicationEditorUsesByApplication(Application application, EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorUsesByApplicationQueries,
                application);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByApplication(Application application) {
        return getApplicationEditorUsesByApplication(application, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByApplicationForUpdate(Application application) {
        return getApplicationEditorUsesByApplication(application, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getApplicationEditorUsesByDefaultApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails, applications, applicationdetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_defaultapplicationeditorid = ? "
                        + "AND appledtrusedt_appl_applicationid = appl_applicationid AND appl_lastdetailid = appldt_applicationdetailid "
                        + "ORDER BY appledtrusedt_sortorder, appledtrusedt_applicationeditorusename, appldt_sortorder, appldt_applicationname "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM applicationeditoruses, applicationeditorusedetails "
                        + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_defaultapplicationeditorid = ? "
                        + "FOR UPDATE");
        getApplicationEditorUsesByDefaultApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditorUse> getApplicationEditorUsesByDefaultApplicationEditor(ApplicationEditor defaultApplicationEditor,
            EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorUsesByDefaultApplicationEditorQueries,
                defaultApplicationEditor);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByDefaultApplicationEditor(ApplicationEditor defaultApplicationEditor) {
        return getApplicationEditorUsesByDefaultApplicationEditor(defaultApplicationEditor, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByDefaultApplicationEditorForUpdate(ApplicationEditor defaultApplicationEditor) {
        return getApplicationEditorUsesByDefaultApplicationEditor(defaultApplicationEditor, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseTransfer getApplicationEditorUseTransfer(UserVisit userVisit, ApplicationEditorUse applicationEditorUse) {
        return getCoreTransferCaches(userVisit).getApplicationEditorUseTransferCache().getApplicationEditorUseTransfer(applicationEditorUse);
    }

    public List<ApplicationEditorUseTransfer> getApplicationEditorUseTransfers(List<ApplicationEditorUse> applicationEditorUses, UserVisit userVisit) {
        List<ApplicationEditorUseTransfer> applicationEditorUseTransfers = new ArrayList<>(applicationEditorUses.size());
        var applicationEditorUseTransferCache = getCoreTransferCaches(userVisit).getApplicationEditorUseTransferCache();

        applicationEditorUses.forEach((applicationEditorUse) ->
                applicationEditorUseTransfers.add(applicationEditorUseTransferCache.getApplicationEditorUseTransfer(applicationEditorUse))
        );

        return applicationEditorUseTransfers;
    }

    public List<ApplicationEditorUseTransfer> getApplicationEditorUseTransfersByApplication(UserVisit userVisit, Application application) {
        return getApplicationEditorUseTransfers(getApplicationEditorUsesByApplication(application), userVisit);
    }

    public List<ApplicationEditorUseTransfer> getApplicationEditorUseTransfersByDefaultApplicationEditor(UserVisit userVisit, ApplicationEditor defaultApplicationEditor) {
        return getApplicationEditorUseTransfers(getApplicationEditorUsesByDefaultApplicationEditor(defaultApplicationEditor), userVisit);
    }

    public ApplicationEditorUseChoicesBean getApplicationEditorUseChoices(String defaultApplicationEditorUseChoice, Language language, boolean allowNullChoice,
            Application application) {
        var applicationEditorUses = getApplicationEditorUsesByApplication(application);
        var size = applicationEditorUses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultApplicationEditorUseChoice == null) {
                defaultValue = "";
            }
        }

        for(var applicationEditorUse : applicationEditorUses) {
            var applicationEditorUseDetail = applicationEditorUse.getLastDetail();

            var label = getBestApplicationEditorUseDescription(applicationEditorUse, language);
            var value = applicationEditorUseDetail.getApplicationEditorUseName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultApplicationEditorUseChoice != null && defaultApplicationEditorUseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && applicationEditorUseDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ApplicationEditorUseChoicesBean(labels, values, defaultValue);
    }

    private void updateApplicationEditorUseFromValue(ApplicationEditorUseDetailValue applicationEditorUseDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(applicationEditorUseDetailValue.hasBeenModified()) {
            var applicationEditorUse = ApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationEditorUseDetailValue.getApplicationEditorUsePK());
            var applicationEditorUseDetail = applicationEditorUse.getActiveDetailForUpdate();

            applicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
            applicationEditorUseDetail.store();

            var applicationEditorUsePK = applicationEditorUseDetail.getApplicationEditorUsePK(); // Not updated
            var application = applicationEditorUseDetail.getApplication();
            var applicationPK = application.getPrimaryKey(); // Not updated
            var applicationEditorUseName = applicationEditorUseDetailValue.getApplicationEditorUseName();
            var defaultApplicationEditorPK = applicationEditorUseDetailValue.getDefaultApplicationEditorPK();
            var defaultHeight = applicationEditorUseDetailValue.getDefaultHeight();
            var defaultWidth = applicationEditorUseDetailValue.getDefaultWidth();
            var isDefault = applicationEditorUseDetailValue.getIsDefault();
            var sortOrder = applicationEditorUseDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultApplicationEditorUse = getDefaultApplicationEditorUse(application);
                var defaultFound = defaultApplicationEditorUse != null && !defaultApplicationEditorUse.equals(applicationEditorUse);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultApplicationEditorUseDetailValue = getDefaultApplicationEditorUseDetailValueForUpdate(application);

                    defaultApplicationEditorUseDetailValue.setIsDefault(false);
                    updateApplicationEditorUseFromValue(defaultApplicationEditorUseDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            applicationEditorUseDetail = ApplicationEditorUseDetailFactory.getInstance().create(applicationEditorUsePK, applicationPK, applicationEditorUseName,
                    defaultApplicationEditorPK, defaultHeight, defaultWidth, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            applicationEditorUse.setActiveDetail(applicationEditorUseDetail);
            applicationEditorUse.setLastDetail(applicationEditorUseDetail);

            sendEvent(applicationEditorUsePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateApplicationEditorUseFromValue(ApplicationEditorUseDetailValue applicationEditorUseDetailValue, BasePK updatedBy) {
        updateApplicationEditorUseFromValue(applicationEditorUseDetailValue, true, updatedBy);
    }

    private void deleteApplicationEditorUse(ApplicationEditorUse applicationEditorUse, boolean checkDefault, BasePK deletedBy) {
        var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
        var applicationEditorUseDetail = applicationEditorUse.getLastDetailForUpdate();
        var application = applicationEditorUseDetail.getApplication();

        deleteApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse, deletedBy);
        partyApplicationEditorUseControl.deletePartyApplicationEditorUsesByParty(applicationEditorUse, deletedBy);

        applicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
        applicationEditorUse.setActiveDetail(null);
        applicationEditorUse.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultApplicationEditorUse = getDefaultApplicationEditorUse(application);

            if(defaultApplicationEditorUse == null) {
                var applicationEditorUses = getApplicationEditorUsesByApplicationForUpdate(application);

                if(!applicationEditorUses.isEmpty()) {
                    var iter = applicationEditorUses.iterator();
                    if(iter.hasNext()) {
                        defaultApplicationEditorUse = iter.next();
                    }
                    var applicationEditorUseDetailValue = Objects.requireNonNull(defaultApplicationEditorUse).getLastDetailForUpdate().getApplicationEditorUseDetailValue().clone();

                    applicationEditorUseDetailValue.setIsDefault(true);
                    updateApplicationEditorUseFromValue(applicationEditorUseDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(applicationEditorUse.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteApplicationEditorUse(ApplicationEditorUse applicationEditorUse, BasePK deletedBy) {
        deleteApplicationEditorUse(applicationEditorUse, true, deletedBy);
    }

    private void deleteApplicationEditorUses(List<ApplicationEditorUse> applicationEditorUses, boolean checkDefault, BasePK deletedBy) {
        applicationEditorUses.forEach((applicationEditorUse) -> deleteApplicationEditorUse(applicationEditorUse, checkDefault, deletedBy));
    }

    public void deleteApplicationEditorUses(List<ApplicationEditorUse> applicationEditorUses, BasePK deletedBy) {
        deleteApplicationEditorUses(applicationEditorUses, true, deletedBy);
    }

    public void deleteApplicationEditorUsesByApplication(Application application, BasePK deletedBy) {
        deleteApplicationEditorUses(getApplicationEditorUsesByApplicationForUpdate(application), false, deletedBy);
    }

    public void deleteApplicationEditorUsesByDefaultApplicationEditor(ApplicationEditor defaultApplicationEditor, BasePK deletedBy) {
        deleteApplicationEditorUses(getApplicationEditorUsesByDefaultApplicationEditorForUpdate(defaultApplicationEditor), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------

    public ApplicationEditorUseDescription createApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language,
            String description, BasePK createdBy) {
        var applicationEditorUseDescription = ApplicationEditorUseDescriptionFactory.getInstance().create(applicationEditorUse,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(applicationEditorUse.getPrimaryKey(), EventTypes.MODIFY, applicationEditorUseDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return applicationEditorUseDescription;
    }

    private static final Map<EntityPermission, String> getApplicationEditorUseDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applicationeditorusedescriptions " +
                        "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_lang_languageid = ? AND appledtrused_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applicationeditorusedescriptions " +
                        "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_lang_languageid = ? AND appledtrused_thrutime = ? " +
                        "FOR UPDATE");
        getApplicationEditorUseDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditorUseDescription getApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language, EntityPermission entityPermission) {
        return ApplicationEditorUseDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationEditorUseDescriptionQueries,
                applicationEditorUse, language, Session.MAX_TIME);
    }

    public ApplicationEditorUseDescription getApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language) {
        return getApplicationEditorUseDescription(applicationEditorUse, language, EntityPermission.READ_ONLY);
    }

    public ApplicationEditorUseDescription getApplicationEditorUseDescriptionForUpdate(ApplicationEditorUse applicationEditorUse, Language language) {
        return getApplicationEditorUseDescription(applicationEditorUse, language, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseDescriptionValue getApplicationEditorUseDescriptionValue(ApplicationEditorUseDescription applicationEditorUseDescription) {
        return applicationEditorUseDescription == null? null: applicationEditorUseDescription.getApplicationEditorUseDescriptionValue().clone();
    }

    public ApplicationEditorUseDescriptionValue getApplicationEditorUseDescriptionValueForUpdate(ApplicationEditorUse applicationEditorUse, Language language) {
        return getApplicationEditorUseDescriptionValue(getApplicationEditorUseDescriptionForUpdate(applicationEditorUse, language));
    }

    private static final Map<EntityPermission, String> getApplicationEditorUseDescriptionsByApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM applicationeditorusedescriptions, languages " +
                        "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_thrutime = ? AND appledtrused_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM applicationeditorusedescriptions " +
                        "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_thrutime = ? " +
                        "FOR UPDATE");
        getApplicationEditorUseDescriptionsByApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditorUseDescription> getApplicationEditorUseDescriptionsByApplicationEditorUse(ApplicationEditorUse applicationEditorUse, EntityPermission entityPermission) {
        return ApplicationEditorUseDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorUseDescriptionsByApplicationEditorUseQueries,
                applicationEditorUse, Session.MAX_TIME);
    }

    public List<ApplicationEditorUseDescription> getApplicationEditorUseDescriptionsByApplicationEditorUse(ApplicationEditorUse applicationEditorUse) {
        return getApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditorUseDescription> getApplicationEditorUseDescriptionsByApplicationEditorUseForUpdate(ApplicationEditorUse applicationEditorUse) {
        return getApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_WRITE);
    }

    public String getBestApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language) {
        String description;
        var applicationEditorUseDescription = getApplicationEditorUseDescription(applicationEditorUse, language);

        if(applicationEditorUseDescription == null && !language.getIsDefault()) {
            applicationEditorUseDescription = getApplicationEditorUseDescription(applicationEditorUse, getPartyControl().getDefaultLanguage());
        }

        if(applicationEditorUseDescription == null) {
            description = applicationEditorUse.getLastDetail().getApplicationEditorUseName();
        } else {
            description = applicationEditorUseDescription.getDescription();
        }

        return description;
    }

    public ApplicationEditorUseDescriptionTransfer getApplicationEditorUseDescriptionTransfer(UserVisit userVisit, ApplicationEditorUseDescription applicationEditorUseDescription) {
        return getCoreTransferCaches(userVisit).getApplicationEditorUseDescriptionTransferCache().getApplicationEditorUseDescriptionTransfer(applicationEditorUseDescription);
    }

    public List<ApplicationEditorUseDescriptionTransfer> getApplicationEditorUseDescriptionTransfersByApplicationEditorUse(UserVisit userVisit, ApplicationEditorUse applicationEditorUse) {
        var applicationEditorUseDescriptions = getApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse);
        List<ApplicationEditorUseDescriptionTransfer> applicationEditorUseDescriptionTransfers = new ArrayList<>(applicationEditorUseDescriptions.size());
        var applicationEditorUseDescriptionTransferCache = getCoreTransferCaches(userVisit).getApplicationEditorUseDescriptionTransferCache();

        applicationEditorUseDescriptions.forEach((applicationEditorUseDescription) ->
                applicationEditorUseDescriptionTransfers.add(applicationEditorUseDescriptionTransferCache.getApplicationEditorUseDescriptionTransfer(applicationEditorUseDescription))
        );

        return applicationEditorUseDescriptionTransfers;
    }

    public void updateApplicationEditorUseDescriptionFromValue(ApplicationEditorUseDescriptionValue applicationEditorUseDescriptionValue, BasePK updatedBy) {
        if(applicationEditorUseDescriptionValue.hasBeenModified()) {
            var applicationEditorUseDescription = ApplicationEditorUseDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationEditorUseDescriptionValue.getPrimaryKey());

            applicationEditorUseDescription.setThruTime(session.START_TIME_LONG);
            applicationEditorUseDescription.store();

            var applicationEditorUse = applicationEditorUseDescription.getApplicationEditorUse();
            var language = applicationEditorUseDescription.getLanguage();
            var description = applicationEditorUseDescriptionValue.getDescription();

            applicationEditorUseDescription = ApplicationEditorUseDescriptionFactory.getInstance().create(applicationEditorUse, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(applicationEditorUse.getPrimaryKey(), EventTypes.MODIFY, applicationEditorUseDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteApplicationEditorUseDescription(ApplicationEditorUseDescription applicationEditorUseDescription, BasePK deletedBy) {
        applicationEditorUseDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(applicationEditorUseDescription.getApplicationEditorUsePK(), EventTypes.MODIFY, applicationEditorUseDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteApplicationEditorUseDescriptionsByApplicationEditorUse(ApplicationEditorUse applicationEditorUse, BasePK deletedBy) {
        var applicationEditorUseDescriptions = getApplicationEditorUseDescriptionsByApplicationEditorUseForUpdate(applicationEditorUse);

        applicationEditorUseDescriptions.forEach((applicationEditorUseDescription) ->
                deleteApplicationEditorUseDescription(applicationEditorUseDescription, deletedBy)
        );
    }

}
