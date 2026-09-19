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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.EditorChoicesBean;
import com.echothree.model.control.core.common.transfer.EditorDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EditorTransfer;
import com.echothree.model.control.core.server.transfer.EditorDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EditorTransferCache;
import com.echothree.model.data.core.common.pk.EditorPK;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.core.server.entity.EditorDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.EditorDescriptionFactory;
import com.echothree.model.data.core.server.factory.EditorDetailFactory;
import com.echothree.model.data.core.server.factory.EditorFactory;
import com.echothree.model.data.core.server.value.EditorDescriptionValue;
import com.echothree.model.data.core.server.value.EditorDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EDITORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EDITOR_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.core.EditorDescriptions.EditorDescriptions;
import static com.echothree.model.jooq.server.tables.core.EditorDetails.EditorDetails;
import static com.echothree.model.jooq.server.tables.core.Editors.Editors;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class EditorControl
        extends BaseModelControl {

    @Inject
    protected ApplicationControl applicationControl;

    /** Creates a new instance of EditorControl */
    protected EditorControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------

    @Inject
    protected EditorDetailFactory editorDetailFactory;

    @Inject
    protected EditorFactory editorFactory;

    @Inject
    EditorTransferCache editorTransferCache;

    public Editor createEditor(String editorName, Boolean hasDimensions, Integer minimumHeight, Integer minimumWidth, Integer maximumHeight,
            Integer maximumWidth, Integer defaultHeight, Integer defaultWidth, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEditor = getDefaultEditor();
        var defaultFound = defaultEditor != null;

        if(defaultFound && isDefault) {
            var defaultEditorDetailValue = getDefaultEditorDetailValueForUpdate();

            defaultEditorDetailValue.setIsDefault(false);
            updateEditorFromValue(defaultEditorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var editor = editorFactory.create();
        var editorDetail = editorDetailFactory.create(editor, editorName, hasDimensions, minimumHeight, minimumWidth, maximumHeight,
                maximumWidth, defaultHeight, defaultWidth, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        editor = editorFactory.getEntityFromPK(EntityPermission.READ_WRITE, editor.getPrimaryKey());
        editor.setActiveDetail(editorDetail);
        editor.setLastDetail(editorDetail);
        editor.store();

        sendEvent(editor.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return editor;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Editor */
    public Editor getEditorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EditorPK(entityInstance.getEntityUniqueId());

        return editorFactory.getEntityFromPK(entityPermission, pk);
    }

    public Editor getEditorByEntityInstance(EntityInstance entityInstance) {
        return getEditorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Editor getEditorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEditorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEditors() {
        return session.getDslContext()
                .selectCount()
                .from(Editors)
                .join(EditorDetails).onKey(EDITORS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Editor getEditorByName(String editorName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Editors.fields())
                .from(Editors)
                .join(EditorDetails).onKey(EDITORS_ACTIVE_DETAIL_FK)
                .where(EditorDetails.EDITOR_NAME.eq(editorName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return editorFactory.getEntityFromQuery(entityPermission, query);
    }

    public Editor getEditorByName(String editorName) {
        return getEditorByName(editorName, EntityPermission.READ_ONLY);
    }

    public Editor getEditorByNameForUpdate(String editorName) {
        return getEditorByName(editorName, EntityPermission.READ_WRITE);
    }

    public EditorDetailValue getEditorDetailValueForUpdate(Editor editor) {
        return editor == null? null: editor.getLastDetailForUpdate().getEditorDetailValue().clone();
    }

    public EditorDetailValue getEditorDetailValueByNameForUpdate(String editorName) {
        return getEditorDetailValueForUpdate(getEditorByNameForUpdate(editorName));
    }

    private Editor getDefaultEditor(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Editors.fields())
                .from(Editors)
                .join(EditorDetails).onKey(EDITORS_ACTIVE_DETAIL_FK)
                .where(EditorDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return editorFactory.getEntityFromQuery(entityPermission, query);
    }

    public Editor getDefaultEditor() {
        return getDefaultEditor(EntityPermission.READ_ONLY);
    }

    public Editor getDefaultEditorForUpdate() {
        return getDefaultEditor(EntityPermission.READ_WRITE);
    }

    public EditorDetailValue getDefaultEditorDetailValueForUpdate() {
        return getDefaultEditorForUpdate().getLastDetailForUpdate().getEditorDetailValue().clone();
    }

    private List<Editor> getEditors(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Editors.fields())
                    .from(Editors)
                    .join(EditorDetails).onKey(EDITORS_ACTIVE_DETAIL_FK)
                    .orderBy(EditorDetails.SORT_ORDER, EditorDetails.EDITOR_NAME),
                    EditorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Editors.fields())
                    .from(Editors)
                    .where(Editors.ACTIVE_DETAIL.isNotNull())
                    .forUpdate();
        };

        return editorFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Editor> getEditors() {
        return getEditors(EntityPermission.READ_ONLY);
    }

    public List<Editor> getEditorsForUpdate() {
        return getEditors(EntityPermission.READ_WRITE);
    }

    public EditorTransfer getEditorTransfer(UserVisit userVisit, Editor editor) {
        return editorTransferCache.getEditorTransfer(userVisit, editor);
    }

    public List<EditorTransfer> getEditorTransfers(UserVisit userVisit, Collection<Editor> editors) {
        List<EditorTransfer> editorTransfers = new ArrayList<>(editors.size());

        editors.forEach((editor) ->
                editorTransfers.add(editorTransferCache.getEditorTransfer(userVisit, editor))
        );

        return editorTransfers;
    }

    public List<EditorTransfer> getEditorTransfers(UserVisit userVisit) {
        return getEditorTransfers(userVisit, getEditors());
    }

    public EditorChoicesBean getEditorChoices(String defaultEditorChoice, Language language, boolean allowNullChoice) {
        var editors = getEditors();
        var size = editors.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEditorChoice == null) {
                defaultValue = "";
            }
        }

        for(var editor : editors) {
            var editorDetail = editor.getLastDetail();

            var label = getBestEditorDescription(editor, language);
            var value = editorDetail.getEditorName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultEditorChoice != null && defaultEditorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && editorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new EditorChoicesBean(labels, values, defaultValue);
    }

    private void updateEditorFromValue(EditorDetailValue editorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(editorDetailValue.hasBeenModified()) {
            var editor = editorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    editorDetailValue.getEditorPK());
            var editorDetail = editor.getActiveDetailForUpdate();

            editorDetail.setThruTime(session.getStartTime());
            editorDetail.store();

            var editorPK = editorDetail.getEditorPK(); // Not updated
            var editorName = editorDetailValue.getEditorName();
            var hasDimensions = editorDetailValue.getHasDimensions();
            var minimumHeight = editorDetailValue.getMinimumHeight();
            var minimumWidth = editorDetailValue.getMinimumWidth();
            var maximumHeight = editorDetailValue.getMaximumHeight();
            var maximumWidth = editorDetailValue.getMaximumWidth();
            var defaultHeight = editorDetailValue.getDefaultHeight();
            var defaultWidth = editorDetailValue.getDefaultWidth();
            var isDefault = editorDetailValue.getIsDefault();
            var sortOrder = editorDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultEditor = getDefaultEditor();
                var defaultFound = defaultEditor != null && !defaultEditor.equals(editor);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEditorDetailValue = getDefaultEditorDetailValueForUpdate();

                    defaultEditorDetailValue.setIsDefault(false);
                    updateEditorFromValue(defaultEditorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            editorDetail = editorDetailFactory.create(editorPK, editorName, hasDimensions, minimumHeight, minimumWidth, maximumHeight,
                    maximumWidth, defaultHeight, defaultWidth, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            editor.setActiveDetail(editorDetail);
            editor.setLastDetail(editorDetail);

            sendEvent(editorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateEditorFromValue(EditorDetailValue editorDetailValue, BasePK updatedBy) {
        updateEditorFromValue(editorDetailValue, true, updatedBy);
    }

    private void deleteEditor(Editor editor, boolean checkDefault, BasePK deletedBy) {
        var editorDetail = editor.getLastDetailForUpdate();

        deleteEditorDescriptionsByEditor(editor, deletedBy);
        applicationControl.deleteApplicationEditorsByEditor(editor, deletedBy);

        editorDetail.setThruTime(session.getStartTime());
        editor.setActiveDetail(null);
        editor.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultEditor = getDefaultEditor();

            if(defaultEditor == null) {
                var editors = getEditorsForUpdate();

                if(!editors.isEmpty()) {
                    var iter = editors.iterator();
                    if(iter.hasNext()) {
                        defaultEditor = iter.next();
                    }
                    var editorDetailValue = Objects.requireNonNull(defaultEditor).getLastDetailForUpdate().getEditorDetailValue().clone();

                    editorDetailValue.setIsDefault(true);
                    updateEditorFromValue(editorDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(editor.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteEditor(Editor editor, BasePK deletedBy) {
        deleteEditor(editor, true, deletedBy);
    }

    private void deleteEditors(List<Editor> editors, boolean checkDefault, BasePK deletedBy) {
        editors.forEach((editor) -> deleteEditor(editor, checkDefault, deletedBy));
    }

    public void deleteEditors(List<Editor> editors, BasePK deletedBy) {
        deleteEditors(editors, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Editor Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EditorDescriptionFactory editorDescriptionFactory;

    @Inject
    EditorDescriptionTransferCache editorDescriptionTransferCache;

        public EditorDescription createEditorDescription(Editor editor, Language language, String description, BasePK createdBy) {
        var editorDescription = editorDescriptionFactory.create(editor, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(editor.getPrimaryKey(), EventTypes.MODIFY, editorDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return editorDescription;
    }

    private EditorDescription getEditorDescription(Editor editor, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EditorDescriptions.fields())
                .from(EditorDescriptions)
                .where(EditorDescriptions.EDITOR.eq(editor.getPrimaryKey()),
                        EditorDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EditorDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return editorDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public EditorDescription getEditorDescription(Editor editor, Language language) {
        return getEditorDescription(editor, language, EntityPermission.READ_ONLY);
    }

    public EditorDescription getEditorDescriptionForUpdate(Editor editor, Language language) {
        return getEditorDescription(editor, language, EntityPermission.READ_WRITE);
    }

    public EditorDescriptionValue getEditorDescriptionValue(EditorDescription editorDescription) {
        return editorDescription == null? null: editorDescription.getEditorDescriptionValue().clone();
    }

    public EditorDescriptionValue getEditorDescriptionValueForUpdate(Editor editor, Language language) {
        return getEditorDescriptionValue(getEditorDescriptionForUpdate(editor, language));
    }

    private List<EditorDescription> getEditorDescriptionsByEditor(Editor editor, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EditorDescriptions.fields())
                    .from(EditorDescriptions)
                    .join(Languages).onKey(EDITOR_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EditorDescriptions.EDITOR.eq(editor.getPrimaryKey()),
                            EditorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EditorDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EditorDescriptions.fields())
                    .from(EditorDescriptions)
                    .where(EditorDescriptions.EDITOR.eq(editor.getPrimaryKey()),
                            EditorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return editorDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EditorDescription> getEditorDescriptionsByEditor(Editor editor) {
        return getEditorDescriptionsByEditor(editor, EntityPermission.READ_ONLY);
    }

    public List<EditorDescription> getEditorDescriptionsByEditorForUpdate(Editor editor) {
        return getEditorDescriptionsByEditor(editor, EntityPermission.READ_WRITE);
    }

    public String getBestEditorDescription(Editor editor, Language language) {
        String description;
        var editorDescription = getEditorDescription(editor, language);

        if(editorDescription == null && !language.getIsDefault()) {
            editorDescription = getEditorDescription(editor, partyControl.getDefaultLanguage());
        }

        if(editorDescription == null) {
            description = editor.getLastDetail().getEditorName();
        } else {
            description = editorDescription.getDescription();
        }

        return description;
    }

    public EditorDescriptionTransfer getEditorDescriptionTransfer(UserVisit userVisit, EditorDescription editorDescription) {
        return editorDescriptionTransferCache.getEditorDescriptionTransfer(userVisit, editorDescription);
    }

    public List<EditorDescriptionTransfer> getEditorDescriptionTransfersByEditor(UserVisit userVisit, Editor editor) {
        var editorDescriptions = getEditorDescriptionsByEditor(editor);
        List<EditorDescriptionTransfer> editorDescriptionTransfers = new ArrayList<>(editorDescriptions.size());

        editorDescriptions.forEach((editorDescription) ->
                editorDescriptionTransfers.add(editorDescriptionTransferCache.getEditorDescriptionTransfer(userVisit, editorDescription))
        );

        return editorDescriptionTransfers;
    }

    public void updateEditorDescriptionFromValue(EditorDescriptionValue editorDescriptionValue, BasePK updatedBy) {
        if(editorDescriptionValue.hasBeenModified()) {
            var editorDescription = editorDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    editorDescriptionValue.getPrimaryKey());

            editorDescription.setThruTime(session.getStartTime());
            editorDescription.store();

            var editor = editorDescription.getEditor();
            var language = editorDescription.getLanguage();
            var description = editorDescriptionValue.getDescription();

            editorDescription = editorDescriptionFactory.create(editor, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(editor.getPrimaryKey(), EventTypes.MODIFY, editorDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEditorDescription(EditorDescription editorDescription, BasePK deletedBy) {
        editorDescription.setThruTime(session.getStartTime());

        sendEvent(editorDescription.getEditorPK(), EventTypes.MODIFY, editorDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteEditorDescriptionsByEditor(Editor editor, BasePK deletedBy) {
        var editorDescriptions = getEditorDescriptionsByEditorForUpdate(editor);

        editorDescriptions.forEach((editorDescription) ->
                deleteEditorDescription(editorDescription, deletedBy)
        );
    }

}
