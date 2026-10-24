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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.LinkDirectionalityDescriptionEdit;
import com.echothree.control.user.core.common.result.EditLinkDirectionalityDescriptionResult;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.LinkDirectionalityDescriptionSpec;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.core.server.logic.LinkDirectionalityLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkDirectionalityDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class EditLinkDirectionalityDescriptionCommand
        extends BaseAbstractEditCommand<LinkDirectionalityDescriptionSpec, LinkDirectionalityDescriptionEdit, EditLinkDirectionalityDescriptionResult, LinkDirectionalityDescription, LinkDirectionality> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkDirectionality.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkDirectionalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    LinkDirectionalityLogic linkDirectionalityLogic;

    /** Creates a new instance of EditLinkDirectionalityDescriptionCommand */
    public EditLinkDirectionalityDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLinkDirectionalityDescriptionResult getResult() {
        return CoreResultFactory.getEditLinkDirectionalityDescriptionResult();
    }

    @Override
    public LinkDirectionalityDescriptionEdit getEdit() {
        return CoreEditFactory.getLinkDirectionalityDescriptionEdit();
    }

    @Override
    public LinkDirectionalityDescription getEntity(EditLinkDirectionalityDescriptionResult result) {
        LinkDirectionalityDescription linkDirectionalityDescription = null;
        var linkDirectionalityName = spec.getLinkDirectionalityName();
        var linkDirectionality = linkDirectionalityLogic.getLinkDirectionalityByName(this, linkDirectionalityName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                linkDirectionalityDescription = linkDirectionalityControl.getLinkDirectionalityDescription(linkDirectionality, language,
                        editModeToEntityPermission(editMode));

                if(linkDirectionalityDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLinkDirectionalityDescription.name(), linkDirectionalityName, languageIsoName);
                }
            }
        }

        return linkDirectionalityDescription;
    }

    @Override
    public LinkDirectionality getLockEntity(LinkDirectionalityDescription linkDirectionalityDescription) {
        return linkDirectionalityDescription.getLinkDirectionality();
    }

    @Override
    public void fillInResult(EditLinkDirectionalityDescriptionResult result, LinkDirectionalityDescription linkDirectionalityDescription) {
        result.setLinkDirectionalityDescription(linkDirectionalityControl.getLinkDirectionalityDescriptionTransfer(getUserVisit(), linkDirectionalityDescription));
    }

    @Override
    public void doLock(LinkDirectionalityDescriptionEdit edit, LinkDirectionalityDescription linkDirectionalityDescription) {
        edit.setDescription(linkDirectionalityDescription.getDescription());
    }

    @Override
    public void doUpdate(LinkDirectionalityDescription linkDirectionalityDescription) {
        var linkDirectionalityDescriptionValue = linkDirectionalityControl.getLinkDirectionalityDescriptionValue(linkDirectionalityDescription);
        linkDirectionalityDescriptionValue.setDescription(edit.getDescription());

        linkDirectionalityControl.updateLinkDirectionalityDescriptionFromValue(linkDirectionalityDescriptionValue, getPartyPK());
    }

}
