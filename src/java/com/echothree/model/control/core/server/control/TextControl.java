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
import com.echothree.model.control.core.common.choice.TextDecorationChoicesBean;
import com.echothree.model.control.core.common.choice.TextTransformationChoicesBean;
import com.echothree.model.control.core.common.transfer.TextDecorationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.TextDecorationTransfer;
import com.echothree.model.control.core.common.transfer.TextTransformationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.TextTransformationTransfer;
import com.echothree.model.data.core.common.pk.TextDecorationPK;
import com.echothree.model.data.core.common.pk.TextTransformationPK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextDecorationDescription;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.core.server.entity.TextTransformationDescription;
import com.echothree.model.data.core.server.factory.TextDecorationDescriptionFactory;
import com.echothree.model.data.core.server.factory.TextDecorationDetailFactory;
import com.echothree.model.data.core.server.factory.TextDecorationFactory;
import com.echothree.model.data.core.server.factory.TextTransformationDescriptionFactory;
import com.echothree.model.data.core.server.factory.TextTransformationDetailFactory;
import com.echothree.model.data.core.server.factory.TextTransformationFactory;
import com.echothree.model.data.core.server.value.TextDecorationDescriptionValue;
import com.echothree.model.data.core.server.value.TextDecorationDetailValue;
import com.echothree.model.data.core.server.value.TextTransformationDescriptionValue;
import com.echothree.model.data.core.server.value.TextTransformationDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TextControl
        extends BaseCoreControl {

    /** Creates a new instance of TextControl */
    protected TextControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Text Decorations
    // --------------------------------------------------------------------------------

    public TextDecoration createTextDecoration(String textDecorationName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultTextDecoration = getDefaultTextDecoration();
        var defaultFound = defaultTextDecoration != null;

        if(defaultFound && isDefault) {
            var defaultTextDecorationDetailValue = getDefaultTextDecorationDetailValueForUpdate();

            defaultTextDecorationDetailValue.setIsDefault(false);
            updateTextDecorationFromValue(defaultTextDecorationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var textDecoration = TextDecorationFactory.getInstance().create();
        var textDecorationDetail = TextDecorationDetailFactory.getInstance().create(textDecoration, textDecorationName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        textDecoration = TextDecorationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, textDecoration.getPrimaryKey());
        textDecoration.setActiveDetail(textDecorationDetail);
        textDecoration.setLastDetail(textDecorationDetail);
        textDecoration.store();

        sendEvent(textDecoration.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return textDecoration;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TextDecoration */
    public TextDecoration getTextDecorationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TextDecorationPK(entityInstance.getEntityUniqueId());

        return TextDecorationFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TextDecoration getTextDecorationByEntityInstance(EntityInstance entityInstance) {
        return getTextDecorationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TextDecoration getTextDecorationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTextDecorationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTextDecorations() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM textdecorations, textdecorationdetails
                WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid
                """);
    }

    private static final Map<EntityPermission, String> getTextDecorationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM textdecorations, textdecorationdetails " +
                        "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                        "AND txtdcrtndt_textdecorationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM textdecorations, textdecorationdetails " +
                        "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                        "AND txtdcrtndt_textdecorationname = ? " +
                        "FOR UPDATE");
        getTextDecorationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextDecoration getTextDecorationByName(String textDecorationName, EntityPermission entityPermission) {
        return TextDecorationFactory.getInstance().getEntityFromQuery(entityPermission, getTextDecorationByNameQueries, textDecorationName);
    }

    public TextDecoration getTextDecorationByName(String textDecorationName) {
        return getTextDecorationByName(textDecorationName, EntityPermission.READ_ONLY);
    }

    public TextDecoration getTextDecorationByNameForUpdate(String textDecorationName) {
        return getTextDecorationByName(textDecorationName, EntityPermission.READ_WRITE);
    }

    public TextDecorationDetailValue getTextDecorationDetailValueForUpdate(TextDecoration textDecoration) {
        return textDecoration == null? null: textDecoration.getLastDetailForUpdate().getTextDecorationDetailValue().clone();
    }

    public TextDecorationDetailValue getTextDecorationDetailValueByNameForUpdate(String textDecorationName) {
        return getTextDecorationDetailValueForUpdate(getTextDecorationByNameForUpdate(textDecorationName));
    }

    private static final Map<EntityPermission, String> getDefaultTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM textdecorations, textdecorationdetails " +
                        "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                        "AND txtdcrtndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM textdecorations, textdecorationdetails " +
                        "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                        "AND txtdcrtndt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextDecoration getDefaultTextDecoration(EntityPermission entityPermission) {
        return TextDecorationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTextDecorationQueries);
    }

    public TextDecoration getDefaultTextDecoration() {
        return getDefaultTextDecoration(EntityPermission.READ_ONLY);
    }

    public TextDecoration getDefaultTextDecorationForUpdate() {
        return getDefaultTextDecoration(EntityPermission.READ_WRITE);
    }

    public TextDecorationDetailValue getDefaultTextDecorationDetailValueForUpdate() {
        return getDefaultTextDecorationForUpdate().getLastDetailForUpdate().getTextDecorationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getTextDecorationsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM textdecorations, textdecorationdetails " +
                        "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                        "ORDER BY txtdcrtndt_sortorder, txtdcrtndt_textdecorationname " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM textdecorations, textdecorationdetails " +
                        "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                        "FOR UPDATE");
        getTextDecorationsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextDecoration> getTextDecorations(EntityPermission entityPermission) {
        return TextDecorationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextDecorationsQueries);
    }

    public List<TextDecoration> getTextDecorations() {
        return getTextDecorations(EntityPermission.READ_ONLY);
    }

    public List<TextDecoration> getTextDecorationsForUpdate() {
        return getTextDecorations(EntityPermission.READ_WRITE);
    }

    public TextDecorationTransfer getTextDecorationTransfer(UserVisit userVisit, TextDecoration textDecoration) {
        return getCoreTransferCaches().getTextDecorationTransferCache().getTextDecorationTransfer(userVisit, textDecoration);
    }

    public List<TextDecorationTransfer> getTextDecorationTransfers(UserVisit userVisit, Collection<TextDecoration> entities) {
        List<TextDecorationTransfer> transfers = new ArrayList<>(entities.size());
        var TransferCache = getCoreTransferCaches().getTextDecorationTransferCache();

        entities.forEach((entity) ->
                Transfers.add(TransferCache.getTextDecorationTransfer(userVisit, entity))
        );

        return transfers;
    }

    public List<TextDecorationTransfer> getTextDecorationTransfers(UserVisit userVisit) {
        return getTextDecorationTransfers(userVisit, getTextDecorations());
    }

    public TextDecorationChoicesBean getTextDecorationChoices(String defaultTextDecorationChoice, Language language, boolean allowNullChoice) {
        var textDecorations = getTextDecorations();
        var size = textDecorations.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultTextDecorationChoice == null) {
                defaultValue = "";
            }
        }

        for(var textDecoration : textDecorations) {
            var textDecorationDetail = textDecoration.getLastDetail();

            var label = getBestTextDecorationDescription(textDecoration, language);
            var value = textDecorationDetail.getTextDecorationName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultTextDecorationChoice != null && defaultTextDecorationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && textDecorationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new TextDecorationChoicesBean(labels, values, defaultValue);
    }

    private void updateTextDecorationFromValue(TextDecorationDetailValue textDecorationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(textDecorationDetailValue.hasBeenModified()) {
            var textDecoration = TextDecorationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    textDecorationDetailValue.getTextDecorationPK());
            var textDecorationDetail = textDecoration.getActiveDetailForUpdate();

            textDecorationDetail.setThruTime(session.START_TIME_LONG);
            textDecorationDetail.store();

            var textDecorationPK = textDecorationDetail.getTextDecorationPK(); // Not updated
            var textDecorationName = textDecorationDetailValue.getTextDecorationName();
            var isDefault = textDecorationDetailValue.getIsDefault();
            var sortOrder = textDecorationDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultTextDecoration = getDefaultTextDecoration();
                var defaultFound = defaultTextDecoration != null && !defaultTextDecoration.equals(textDecoration);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTextDecorationDetailValue = getDefaultTextDecorationDetailValueForUpdate();

                    defaultTextDecorationDetailValue.setIsDefault(false);
                    updateTextDecorationFromValue(defaultTextDecorationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            textDecorationDetail = TextDecorationDetailFactory.getInstance().create(textDecorationPK, textDecorationName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            textDecoration.setActiveDetail(textDecorationDetail);
            textDecoration.setLastDetail(textDecorationDetail);

            sendEvent(textDecorationPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateTextDecorationFromValue(TextDecorationDetailValue textDecorationDetailValue, BasePK updatedBy) {
        updateTextDecorationFromValue(textDecorationDetailValue, true, updatedBy);
    }

    private void deleteTextDecoration(TextDecoration textDecoration, boolean checkDefault, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var textDecorationDetail = textDecoration.getLastDetailForUpdate();

        appearanceControl.deleteAppearanceTextDecorationsByTextDecoration(textDecoration, deletedBy);
        deleteTextDecorationDescriptionsByTextDecoration(textDecoration, deletedBy);

        textDecorationDetail.setThruTime(session.START_TIME_LONG);
        textDecoration.setActiveDetail(null);
        textDecoration.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultTextDecoration = getDefaultTextDecoration();

            if(defaultTextDecoration == null) {
                var textDecorations = getTextDecorationsForUpdate();

                if(!textDecorations.isEmpty()) {
                    var iter = textDecorations.iterator();
                    if(iter.hasNext()) {
                        defaultTextDecoration = iter.next();
                    }
                    var textDecorationDetailValue = Objects.requireNonNull(defaultTextDecoration).getLastDetailForUpdate().getTextDecorationDetailValue().clone();

                    textDecorationDetailValue.setIsDefault(true);
                    updateTextDecorationFromValue(textDecorationDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(textDecoration.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTextDecoration(TextDecoration textDecoration, BasePK deletedBy) {
        deleteTextDecoration(textDecoration, true, deletedBy);
    }

    private void deleteTextDecorations(List<TextDecoration> textDecorations, boolean checkDefault, BasePK deletedBy) {
        textDecorations.forEach((textDecoration) -> deleteTextDecoration(textDecoration, checkDefault, deletedBy));
    }

    public void deleteTextDecorations(List<TextDecoration> textDecorations, BasePK deletedBy) {
        deleteTextDecorations(textDecorations, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Text Decoration Descriptions
    // --------------------------------------------------------------------------------

    public TextDecorationDescription createTextDecorationDescription(TextDecoration textDecoration, Language language, String description, BasePK createdBy) {
        var textDecorationDescription = TextDecorationDescriptionFactory.getInstance().create(textDecoration, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(textDecoration.getPrimaryKey(), EventTypes.MODIFY, textDecorationDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return textDecorationDescription;
    }

    private static final Map<EntityPermission, String> getTextDecorationDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM textdecorationdescriptions " +
                        "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_lang_languageid = ? AND txtdcrtnd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM textdecorationdescriptions " +
                        "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_lang_languageid = ? AND txtdcrtnd_thrutime = ? " +
                        "FOR UPDATE");
        getTextDecorationDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextDecorationDescription getTextDecorationDescription(TextDecoration textDecoration, Language language, EntityPermission entityPermission) {
        return TextDecorationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getTextDecorationDescriptionQueries,
                textDecoration, language, Session.MAX_TIME);
    }

    public TextDecorationDescription getTextDecorationDescription(TextDecoration textDecoration, Language language) {
        return getTextDecorationDescription(textDecoration, language, EntityPermission.READ_ONLY);
    }

    public TextDecorationDescription getTextDecorationDescriptionForUpdate(TextDecoration textDecoration, Language language) {
        return getTextDecorationDescription(textDecoration, language, EntityPermission.READ_WRITE);
    }

    public TextDecorationDescriptionValue getTextDecorationDescriptionValue(TextDecorationDescription textDecorationDescription) {
        return textDecorationDescription == null? null: textDecorationDescription.getTextDecorationDescriptionValue().clone();
    }

    public TextDecorationDescriptionValue getTextDecorationDescriptionValueForUpdate(TextDecoration textDecoration, Language language) {
        return getTextDecorationDescriptionValue(getTextDecorationDescriptionForUpdate(textDecoration, language));
    }

    private static final Map<EntityPermission, String> getTextDecorationDescriptionsByTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM textdecorationdescriptions, languages " +
                        "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_thrutime = ? AND txtdcrtnd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM textdecorationdescriptions " +
                        "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_thrutime = ? " +
                        "FOR UPDATE");
        getTextDecorationDescriptionsByTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextDecorationDescription> getTextDecorationDescriptionsByTextDecoration(TextDecoration textDecoration, EntityPermission entityPermission) {
        return TextDecorationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextDecorationDescriptionsByTextDecorationQueries,
                textDecoration, Session.MAX_TIME);
    }

    public List<TextDecorationDescription> getTextDecorationDescriptionsByTextDecoration(TextDecoration textDecoration) {
        return getTextDecorationDescriptionsByTextDecoration(textDecoration, EntityPermission.READ_ONLY);
    }

    public List<TextDecorationDescription> getTextDecorationDescriptionsByTextDecorationForUpdate(TextDecoration textDecoration) {
        return getTextDecorationDescriptionsByTextDecoration(textDecoration, EntityPermission.READ_WRITE);
    }

    public String getBestTextDecorationDescription(TextDecoration textDecoration, Language language) {
        String description;
        var textDecorationDescription = getTextDecorationDescription(textDecoration, language);

        if(textDecorationDescription == null && !language.getIsDefault()) {
            textDecorationDescription = getTextDecorationDescription(textDecoration, partyControl.getDefaultLanguage());
        }

        if(textDecorationDescription == null) {
            description = textDecoration.getLastDetail().getTextDecorationName();
        } else {
            description = textDecorationDescription.getDescription();
        }

        return description;
    }

    public TextDecorationDescriptionTransfer getTextDecorationDescriptionTransfer(UserVisit userVisit, TextDecorationDescription textDecorationDescription) {
        return getCoreTransferCaches().getTextDecorationDescriptionTransferCache().getTextDecorationDescriptionTransfer(userVisit, textDecorationDescription);
    }

    public List<TextDecorationDescriptionTransfer> getTextDecorationDescriptionTransfersByTextDecoration(UserVisit userVisit, TextDecoration textDecoration) {
        var textDecorationDescriptions = getTextDecorationDescriptionsByTextDecoration(textDecoration);
        List<TextDecorationDescriptionTransfer> textDecorationDescriptionTransfers = new ArrayList<>(textDecorationDescriptions.size());
        var textDecorationDescriptionTransferCache = getCoreTransferCaches().getTextDecorationDescriptionTransferCache();

        textDecorationDescriptions.forEach((textDecorationDescription) ->
                textDecorationDescriptionTransfers.add(textDecorationDescriptionTransferCache.getTextDecorationDescriptionTransfer(userVisit, textDecorationDescription))
        );

        return textDecorationDescriptionTransfers;
    }

    public void updateTextDecorationDescriptionFromValue(TextDecorationDescriptionValue textDecorationDescriptionValue, BasePK updatedBy) {
        if(textDecorationDescriptionValue.hasBeenModified()) {
            var textDecorationDescription = TextDecorationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    textDecorationDescriptionValue.getPrimaryKey());

            textDecorationDescription.setThruTime(session.START_TIME_LONG);
            textDecorationDescription.store();

            var textDecoration = textDecorationDescription.getTextDecoration();
            var language = textDecorationDescription.getLanguage();
            var description = textDecorationDescriptionValue.getDescription();

            textDecorationDescription = TextDecorationDescriptionFactory.getInstance().create(textDecoration, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(textDecoration.getPrimaryKey(), EventTypes.MODIFY, textDecorationDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTextDecorationDescription(TextDecorationDescription textDecorationDescription, BasePK deletedBy) {
        textDecorationDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(textDecorationDescription.getTextDecorationPK(), EventTypes.MODIFY, textDecorationDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTextDecorationDescriptionsByTextDecoration(TextDecoration textDecoration, BasePK deletedBy) {
        var textDecorationDescriptions = getTextDecorationDescriptionsByTextDecorationForUpdate(textDecoration);

        textDecorationDescriptions.forEach((textDecorationDescription) ->
                deleteTextDecorationDescription(textDecorationDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Text Transformations
    // --------------------------------------------------------------------------------

    public TextTransformation createTextTransformation(String textTransformationName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultTextTransformation = getDefaultTextTransformation();
        var defaultFound = defaultTextTransformation != null;

        if(defaultFound && isDefault) {
            var defaultTextTransformationDetailValue = getDefaultTextTransformationDetailValueForUpdate();

            defaultTextTransformationDetailValue.setIsDefault(false);
            updateTextTransformationFromValue(defaultTextTransformationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var textTransformation = TextTransformationFactory.getInstance().create();
        var textTransformationDetail = TextTransformationDetailFactory.getInstance().create(textTransformation, textTransformationName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        textTransformation = TextTransformationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, textTransformation.getPrimaryKey());
        textTransformation.setActiveDetail(textTransformationDetail);
        textTransformation.setLastDetail(textTransformationDetail);
        textTransformation.store();

        sendEvent(textTransformation.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return textTransformation;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TextTransformation */
    public TextTransformation getTextTransformationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TextTransformationPK(entityInstance.getEntityUniqueId());

        return TextTransformationFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TextTransformation getTextTransformationByEntityInstance(EntityInstance entityInstance) {
        return getTextTransformationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TextTransformation getTextTransformationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTextTransformationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTextTransformations() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM texttransformations, texttransformationdetails
                WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid
                """);
    }

    private static final Map<EntityPermission, String> getTextTransformationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM texttransformations, texttransformationdetails " +
                        "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                        "AND txttrnsdt_texttransformationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM texttransformations, texttransformationdetails " +
                        "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                        "AND txttrnsdt_texttransformationname = ? " +
                        "FOR UPDATE");
        getTextTransformationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextTransformation getTextTransformationByName(String textTransformationName, EntityPermission entityPermission) {
        return TextTransformationFactory.getInstance().getEntityFromQuery(entityPermission, getTextTransformationByNameQueries, textTransformationName);
    }

    public TextTransformation getTextTransformationByName(String textTransformationName) {
        return getTextTransformationByName(textTransformationName, EntityPermission.READ_ONLY);
    }

    public TextTransformation getTextTransformationByNameForUpdate(String textTransformationName) {
        return getTextTransformationByName(textTransformationName, EntityPermission.READ_WRITE);
    }

    public TextTransformationDetailValue getTextTransformationDetailValueForUpdate(TextTransformation textTransformation) {
        return textTransformation == null? null: textTransformation.getLastDetailForUpdate().getTextTransformationDetailValue().clone();
    }

    public TextTransformationDetailValue getTextTransformationDetailValueByNameForUpdate(String textTransformationName) {
        return getTextTransformationDetailValueForUpdate(getTextTransformationByNameForUpdate(textTransformationName));
    }

    private static final Map<EntityPermission, String> getDefaultTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM texttransformations, texttransformationdetails " +
                        "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                        "AND txttrnsdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM texttransformations, texttransformationdetails " +
                        "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                        "AND txttrnsdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextTransformation getDefaultTextTransformation(EntityPermission entityPermission) {
        return TextTransformationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTextTransformationQueries);
    }

    public TextTransformation getDefaultTextTransformation() {
        return getDefaultTextTransformation(EntityPermission.READ_ONLY);
    }

    public TextTransformation getDefaultTextTransformationForUpdate() {
        return getDefaultTextTransformation(EntityPermission.READ_WRITE);
    }

    public TextTransformationDetailValue getDefaultTextTransformationDetailValueForUpdate() {
        return getDefaultTextTransformationForUpdate().getLastDetailForUpdate().getTextTransformationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getTextTransformationsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM texttransformations, texttransformationdetails " +
                        "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                        "ORDER BY txttrnsdt_sortorder, txttrnsdt_texttransformationname " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM texttransformations, texttransformationdetails " +
                        "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                        "FOR UPDATE");
        getTextTransformationsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextTransformation> getTextTransformations(EntityPermission entityPermission) {
        return TextTransformationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextTransformationsQueries);
    }

    public List<TextTransformation> getTextTransformations() {
        return getTextTransformations(EntityPermission.READ_ONLY);
    }

    public List<TextTransformation> getTextTransformationsForUpdate() {
        return getTextTransformations(EntityPermission.READ_WRITE);
    }

    public TextTransformationTransfer getTextTransformationTransfer(UserVisit userVisit, TextTransformation textTransformation) {
        return getCoreTransferCaches().getTextTransformationTransferCache().getTextTransformationTransfer(userVisit, textTransformation);
    }

    public List<TextTransformationTransfer> getTextTransformationTransfers(UserVisit userVisit, Collection<TextTransformation> entities) {
        List<TextTransformationTransfer> transfers = new ArrayList<>(entities.size());
        var TransferCache = getCoreTransferCaches().getTextTransformationTransferCache();

        entities.forEach((entity) ->
                Transfers.add(TransferCache.getTextTransformationTransfer(userVisit, entity))
        );

        return transfers;
    }

    public List<TextTransformationTransfer> getTextTransformationTransfers(UserVisit userVisit) {
        return getTextTransformationTransfers(userVisit, getTextTransformations());
    }

    public TextTransformationChoicesBean getTextTransformationChoices(String defaultTextTransformationChoice, Language language, boolean allowNullChoice) {
        var textTransformations = getTextTransformations();
        var size = textTransformations.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultTextTransformationChoice == null) {
                defaultValue = "";
            }
        }

        for(var textTransformation : textTransformations) {
            var textTransformationDetail = textTransformation.getLastDetail();

            var label = getBestTextTransformationDescription(textTransformation, language);
            var value = textTransformationDetail.getTextTransformationName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultTextTransformationChoice != null && defaultTextTransformationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && textTransformationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new TextTransformationChoicesBean(labels, values, defaultValue);
    }

    private void updateTextTransformationFromValue(TextTransformationDetailValue textTransformationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(textTransformationDetailValue.hasBeenModified()) {
            var textTransformation = TextTransformationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    textTransformationDetailValue.getTextTransformationPK());
            var textTransformationDetail = textTransformation.getActiveDetailForUpdate();

            textTransformationDetail.setThruTime(session.START_TIME_LONG);
            textTransformationDetail.store();

            var textTransformationPK = textTransformationDetail.getTextTransformationPK(); // Not updated
            var textTransformationName = textTransformationDetailValue.getTextTransformationName();
            var isDefault = textTransformationDetailValue.getIsDefault();
            var sortOrder = textTransformationDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultTextTransformation = getDefaultTextTransformation();
                var defaultFound = defaultTextTransformation != null && !defaultTextTransformation.equals(textTransformation);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTextTransformationDetailValue = getDefaultTextTransformationDetailValueForUpdate();

                    defaultTextTransformationDetailValue.setIsDefault(false);
                    updateTextTransformationFromValue(defaultTextTransformationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            textTransformationDetail = TextTransformationDetailFactory.getInstance().create(textTransformationPK, textTransformationName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            textTransformation.setActiveDetail(textTransformationDetail);
            textTransformation.setLastDetail(textTransformationDetail);

            sendEvent(textTransformationPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateTextTransformationFromValue(TextTransformationDetailValue textTransformationDetailValue, BasePK updatedBy) {
        updateTextTransformationFromValue(textTransformationDetailValue, true, updatedBy);
    }

    private void deleteTextTransformation(TextTransformation textTransformation, boolean checkDefault, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var textTransformationDetail = textTransformation.getLastDetailForUpdate();

        appearanceControl.deleteAppearanceTextTransformationsByTextTransformation(textTransformation, deletedBy);
        deleteTextTransformationDescriptionsByTextTransformation(textTransformation, deletedBy);

        textTransformationDetail.setThruTime(session.START_TIME_LONG);
        textTransformation.setActiveDetail(null);
        textTransformation.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultTextTransformation = getDefaultTextTransformation();

            if(defaultTextTransformation == null) {
                var textTransformations = getTextTransformationsForUpdate();

                if(!textTransformations.isEmpty()) {
                    var iter = textTransformations.iterator();
                    if(iter.hasNext()) {
                        defaultTextTransformation = iter.next();
                    }
                    var textTransformationDetailValue = Objects.requireNonNull(defaultTextTransformation).getLastDetailForUpdate().getTextTransformationDetailValue().clone();

                    textTransformationDetailValue.setIsDefault(true);
                    updateTextTransformationFromValue(textTransformationDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(textTransformation.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTextTransformation(TextTransformation textTransformation, BasePK deletedBy) {
        deleteTextTransformation(textTransformation, true, deletedBy);
    }

    private void deleteTextTransformations(List<TextTransformation> textTransformations, boolean checkDefault, BasePK deletedBy) {
        textTransformations.forEach((textTransformation) -> deleteTextTransformation(textTransformation, checkDefault, deletedBy));
    }

    public void deleteTextTransformations(List<TextTransformation> textTransformations, BasePK deletedBy) {
        deleteTextTransformations(textTransformations, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Text Transformation Descriptions
    // --------------------------------------------------------------------------------

    public TextTransformationDescription createTextTransformationDescription(TextTransformation textTransformation, Language language, String description, BasePK createdBy) {
        var textTransformationDescription = TextTransformationDescriptionFactory.getInstance().create(textTransformation, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(textTransformation.getPrimaryKey(), EventTypes.MODIFY, textTransformationDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return textTransformationDescription;
    }

    private static final Map<EntityPermission, String> getTextTransformationDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM texttransformationdescriptions " +
                        "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_lang_languageid = ? AND txttrnsd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM texttransformationdescriptions " +
                        "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_lang_languageid = ? AND txttrnsd_thrutime = ? " +
                        "FOR UPDATE");
        getTextTransformationDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextTransformationDescription getTextTransformationDescription(TextTransformation textTransformation, Language language, EntityPermission entityPermission) {
        return TextTransformationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getTextTransformationDescriptionQueries,
                textTransformation, language, Session.MAX_TIME);
    }

    public TextTransformationDescription getTextTransformationDescription(TextTransformation textTransformation, Language language) {
        return getTextTransformationDescription(textTransformation, language, EntityPermission.READ_ONLY);
    }

    public TextTransformationDescription getTextTransformationDescriptionForUpdate(TextTransformation textTransformation, Language language) {
        return getTextTransformationDescription(textTransformation, language, EntityPermission.READ_WRITE);
    }

    public TextTransformationDescriptionValue getTextTransformationDescriptionValue(TextTransformationDescription textTransformationDescription) {
        return textTransformationDescription == null? null: textTransformationDescription.getTextTransformationDescriptionValue().clone();
    }

    public TextTransformationDescriptionValue getTextTransformationDescriptionValueForUpdate(TextTransformation textTransformation, Language language) {
        return getTextTransformationDescriptionValue(getTextTransformationDescriptionForUpdate(textTransformation, language));
    }

    private static final Map<EntityPermission, String> getTextTransformationDescriptionsByTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM texttransformationdescriptions, languages " +
                        "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_thrutime = ? AND txttrnsd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM texttransformationdescriptions " +
                        "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_thrutime = ? " +
                        "FOR UPDATE");
        getTextTransformationDescriptionsByTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextTransformationDescription> getTextTransformationDescriptionsByTextTransformation(TextTransformation textTransformation, EntityPermission entityPermission) {
        return TextTransformationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextTransformationDescriptionsByTextTransformationQueries,
                textTransformation, Session.MAX_TIME);
    }

    public List<TextTransformationDescription> getTextTransformationDescriptionsByTextTransformation(TextTransformation textTransformation) {
        return getTextTransformationDescriptionsByTextTransformation(textTransformation, EntityPermission.READ_ONLY);
    }

    public List<TextTransformationDescription> getTextTransformationDescriptionsByTextTransformationForUpdate(TextTransformation textTransformation) {
        return getTextTransformationDescriptionsByTextTransformation(textTransformation, EntityPermission.READ_WRITE);
    }

    public String getBestTextTransformationDescription(TextTransformation textTransformation, Language language) {
        String description;
        var textTransformationDescription = getTextTransformationDescription(textTransformation, language);

        if(textTransformationDescription == null && !language.getIsDefault()) {
            textTransformationDescription = getTextTransformationDescription(textTransformation, partyControl.getDefaultLanguage());
        }

        if(textTransformationDescription == null) {
            description = textTransformation.getLastDetail().getTextTransformationName();
        } else {
            description = textTransformationDescription.getDescription();
        }

        return description;
    }

    public TextTransformationDescriptionTransfer getTextTransformationDescriptionTransfer(UserVisit userVisit, TextTransformationDescription textTransformationDescription) {
        return getCoreTransferCaches().getTextTransformationDescriptionTransferCache().getTextTransformationDescriptionTransfer(userVisit, textTransformationDescription);
    }

    public List<TextTransformationDescriptionTransfer> getTextTransformationDescriptionTransfersByTextTransformation(UserVisit userVisit, TextTransformation textTransformation) {
        var textTransformationDescriptions = getTextTransformationDescriptionsByTextTransformation(textTransformation);
        List<TextTransformationDescriptionTransfer> textTransformationDescriptionTransfers = new ArrayList<>(textTransformationDescriptions.size());
        var textTransformationDescriptionTransferCache = getCoreTransferCaches().getTextTransformationDescriptionTransferCache();

        textTransformationDescriptions.forEach((textTransformationDescription) ->
                textTransformationDescriptionTransfers.add(textTransformationDescriptionTransferCache.getTextTransformationDescriptionTransfer(userVisit, textTransformationDescription))
        );

        return textTransformationDescriptionTransfers;
    }

    public void updateTextTransformationDescriptionFromValue(TextTransformationDescriptionValue textTransformationDescriptionValue, BasePK updatedBy) {
        if(textTransformationDescriptionValue.hasBeenModified()) {
            var textTransformationDescription = TextTransformationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    textTransformationDescriptionValue.getPrimaryKey());

            textTransformationDescription.setThruTime(session.START_TIME_LONG);
            textTransformationDescription.store();

            var textTransformation = textTransformationDescription.getTextTransformation();
            var language = textTransformationDescription.getLanguage();
            var description = textTransformationDescriptionValue.getDescription();

            textTransformationDescription = TextTransformationDescriptionFactory.getInstance().create(textTransformation, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(textTransformation.getPrimaryKey(), EventTypes.MODIFY, textTransformationDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTextTransformationDescription(TextTransformationDescription textTransformationDescription, BasePK deletedBy) {
        textTransformationDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(textTransformationDescription.getTextTransformationPK(), EventTypes.MODIFY, textTransformationDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTextTransformationDescriptionsByTextTransformation(TextTransformation textTransformation, BasePK deletedBy) {
        var textTransformationDescriptions = getTextTransformationDescriptionsByTextTransformationForUpdate(textTransformation);

        textTransformationDescriptions.forEach((textTransformationDescription) ->
                deleteTextTransformationDescription(textTransformationDescription, deletedBy)
        );
    }

}
