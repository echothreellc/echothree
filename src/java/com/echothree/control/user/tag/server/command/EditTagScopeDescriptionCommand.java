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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.edit.TagEditFactory;
import com.echothree.control.user.tag.common.edit.TagScopeDescriptionEdit;
import com.echothree.control.user.tag.common.form.EditTagScopeDescriptionForm;
import com.echothree.control.user.tag.common.result.EditTagScopeDescriptionResult;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.control.user.tag.common.spec.TagScopeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditTagScopeDescriptionCommand
        extends BaseAbstractEditCommand<TagScopeDescriptionSpec, TagScopeDescriptionEdit, EditTagScopeDescriptionResult, TagScopeDescription, TagScope> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TagScope.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditTagScopeDescriptionCommand */
    public EditTagScopeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTagScopeDescriptionResult getResult() {
        return TagResultFactory.getEditTagScopeDescriptionResult();
    }

    @Override
    public TagScopeDescriptionEdit getEdit() {
        return TagEditFactory.getTagScopeDescriptionEdit();
    }

    @Override
    public TagScopeDescription getEntity(EditTagScopeDescriptionResult result) {
        var tagControl = Session.getModelController(TagControl.class);
        TagScopeDescription tagScopeDescription = null;
        var tagScopeName = spec.getTagScopeName();
        var tagScope = tagControl.getTagScopeByName(tagScopeName);

        if(tagScope != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    tagScopeDescription = tagControl.getTagScopeDescription(tagScope, language);
                } else { // EditMode.UPDATE
                    tagScopeDescription = tagControl.getTagScopeDescriptionForUpdate(tagScope, language);
                }

                if(tagScopeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTagScopeDescription.name(), tagScopeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
        }

        return tagScopeDescription;
    }

    @Override
    public TagScope getLockEntity(TagScopeDescription tagScopeDescription) {
        return tagScopeDescription.getTagScope();
    }

    @Override
    public void fillInResult(EditTagScopeDescriptionResult result, TagScopeDescription tagScopeDescription) {
        var tagControl = Session.getModelController(TagControl.class);

        result.setTagScopeDescription(tagControl.getTagScopeDescriptionTransfer(getUserVisit(), tagScopeDescription));
    }

    @Override
    public void doLock(TagScopeDescriptionEdit edit, TagScopeDescription tagScopeDescription) {
        edit.setDescription(tagScopeDescription.getDescription());
    }

    @Override
    public void doUpdate(TagScopeDescription tagScopeDescription) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagScopeDescriptionValue = tagControl.getTagScopeDescriptionValue(tagScopeDescription);
        tagScopeDescriptionValue.setDescription(edit.getDescription());

        tagControl.updateTagScopeDescriptionFromValue(tagScopeDescriptionValue, getPartyPK());
    }

}
