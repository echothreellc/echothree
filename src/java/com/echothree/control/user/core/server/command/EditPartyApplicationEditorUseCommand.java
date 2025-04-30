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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.PartyApplicationEditorUseEdit;
import com.echothree.control.user.core.common.form.EditPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditPartyApplicationEditorUseResult;
import com.echothree.control.user.core.common.spec.PartyApplicationEditorUseSpec;
import com.echothree.model.control.core.server.control.PartyApplicationEditorUseControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.core.server.logic.EditorLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyApplicationEditorUseCommand
        extends BaseAbstractEditCommand<PartyApplicationEditorUseSpec, PartyApplicationEditorUseEdit, EditPartyApplicationEditorUseResult, PartyApplicationEditorUse, PartyApplicationEditorUse> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyApplicationEditorUse.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PreferredWidth", FieldType.UNSIGNED_INTEGER, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyApplicationEditorUseCommand */
    public EditPartyApplicationEditorUseCommand(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyApplicationEditorUseResult getResult() {
        return CoreResultFactory.getEditPartyApplicationEditorUseResult();
    }

    @Override
    public PartyApplicationEditorUseEdit getEdit() {
        return CoreEditFactory.getPartyApplicationEditorUseEdit();
    }

    Application application;
    
    @Override
    public PartyApplicationEditorUse getEntity(EditPartyApplicationEditorUseResult result) {
        PartyApplicationEditorUse partyApplicationEditorUse = null;
        var partyName = spec.getPartyName();
        var party = partyName == null ? getParty() : PartyLogic.getInstance().getPartyByName(this, partyName);
        
        if(!hasExecutionErrors()) {
            var applicationName = spec.getApplicationName();
            
            application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
            
            if(!hasExecutionErrors()) {
                var applicationEditorUseName = spec.getApplicationEditorUseName();
                var applicationEditorUse = ApplicationLogic.getInstance().getApplicationEditorUseByName(this, application, applicationEditorUseName);
                
                if(!hasExecutionErrors()) {
                    var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
                    
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        partyApplicationEditorUse = partyApplicationEditorUseControl.getPartyApplicationEditorUse(party, applicationEditorUse);
                    } else { // EditMode.UPDATE
                        partyApplicationEditorUse = partyApplicationEditorUseControl.getPartyApplicationEditorUseForUpdate(party, applicationEditorUse);
                    }
                    
                    if(partyApplicationEditorUse == null) {
                        addExecutionError(ExecutionErrors.DuplicatePartyApplicationEditorUse.name(), partyName, applicationName, applicationEditorUseName);
                    }
                }
            }
        }

        return partyApplicationEditorUse;
    }

    @Override
    public PartyApplicationEditorUse getLockEntity(PartyApplicationEditorUse partyApplicationEditorUse) {
        return partyApplicationEditorUse;
    }

    @Override
    public void fillInResult(EditPartyApplicationEditorUseResult result, PartyApplicationEditorUse partyApplicationEditorUse) {
        var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);

        result.setPartyApplicationEditorUse(partyApplicationEditorUseControl.getPartyApplicationEditorUseTransfer(getUserVisit(), partyApplicationEditorUse));
    }

    ApplicationEditor applicationEditor;
    
    @Override
    public void doLock(PartyApplicationEditorUseEdit edit, PartyApplicationEditorUse partyApplicationEditorUse) {
        var partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetail();
        var preferredHeight = partyApplicationEditorUseDetail.getPreferredHeight();
        var preferredWidth = partyApplicationEditorUseDetail.getPreferredWidth();

        applicationEditor = partyApplicationEditorUseDetail.getApplicationEditor();
       
        edit.setEditorName(applicationEditor == null ? null : applicationEditor.getLastDetail().getEditor().getLastDetail().getEditorName());
        edit.setPreferredHeight(preferredHeight == null ? null : preferredHeight.toString());
        edit.setPreferredWidth(preferredWidth == null ? null : preferredWidth.toString());
    }

    @Override
    public void canUpdate(PartyApplicationEditorUse partyApplicationEditorUse) {
        var editorName = edit.getEditorName();
        var editor = editorName == null ? null : EditorLogic.getInstance().getEditorByName(this, editorName);

        if(!hasExecutionErrors()) {
            applicationEditor = editor == null ? null : ApplicationLogic.getInstance().getApplicationEditor(this, application, editor);
        }
    }

    @Override
    public void doUpdate(PartyApplicationEditorUse partyApplicationEditorUse) {
        var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
        var partyPK = getPartyPK();
        var partyApplicationEditorUseDetailValue = partyApplicationEditorUseControl.getPartyApplicationEditorUseDetailValueForUpdate(partyApplicationEditorUse);
        var strPreferredHeight = edit.getPreferredHeight();
        var preferredHeight = strPreferredHeight == null ? null : Integer.valueOf(strPreferredHeight);
        var strPreferredWidth = edit.getPreferredWidth();
        var preferredWidth = strPreferredWidth == null ? null : Integer.valueOf(strPreferredWidth);

        partyApplicationEditorUseDetailValue.setApplicationEditorPK(applicationEditor == null ? null : applicationEditor.getPrimaryKey());
        partyApplicationEditorUseDetailValue.setPreferredHeight(preferredHeight);
        partyApplicationEditorUseDetailValue.setPreferredWidth(preferredWidth);

        partyApplicationEditorUseControl.updatePartyApplicationEditorUseFromValue(partyApplicationEditorUseDetailValue, partyPK);
    }
    
}
