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
import com.echothree.control.user.core.common.edit.LinkCardinalityDescriptionEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditLinkCardinalityDescriptionResult;
import com.echothree.control.user.core.common.spec.LinkCardinalityDescriptionSpec;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.logic.LinkCardinalityLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.entity.LinkCardinalityDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditLinkCardinalityDescriptionCommand
        extends BaseAbstractEditCommand<LinkCardinalityDescriptionSpec, LinkCardinalityDescriptionEdit, EditLinkCardinalityDescriptionResult, LinkCardinalityDescription, LinkCardinality> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkCardinality.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkCardinalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    LinkCardinalityLogic linkCardinalityLogic;

    /** Creates a new instance of EditLinkCardinalityDescriptionCommand */
    public EditLinkCardinalityDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLinkCardinalityDescriptionResult getResult() {
        return CoreResultFactory.getEditLinkCardinalityDescriptionResult();
    }

    @Override
    public LinkCardinalityDescriptionEdit getEdit() {
        return CoreEditFactory.getLinkCardinalityDescriptionEdit();
    }

    @Override
    public LinkCardinalityDescription getEntity(EditLinkCardinalityDescriptionResult result) {
        LinkCardinalityDescription linkCardinalityDescription = null;
        var linkCardinalityName = spec.getLinkCardinalityName();
        var linkCardinality = linkCardinalityLogic.getLinkCardinalityByName(this, linkCardinalityName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                linkCardinalityDescription = linkCardinalityControl.getLinkCardinalityDescription(linkCardinality, language,
                        editModeToEntityPermission(editMode));

                if(linkCardinalityDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLinkCardinalityDescription.name(), linkCardinalityName, languageIsoName);
                }
            }
        }

        return linkCardinalityDescription;
    }

    @Override
    public LinkCardinality getLockEntity(LinkCardinalityDescription linkCardinalityDescription) {
        return linkCardinalityDescription.getLinkCardinality();
    }

    @Override
    public void fillInResult(EditLinkCardinalityDescriptionResult result, LinkCardinalityDescription linkCardinalityDescription) {
        result.setLinkCardinalityDescription(linkCardinalityControl.getLinkCardinalityDescriptionTransfer(getUserVisit(), linkCardinalityDescription));
    }

    @Override
    public void doLock(LinkCardinalityDescriptionEdit edit, LinkCardinalityDescription linkCardinalityDescription) {
        edit.setDescription(linkCardinalityDescription.getDescription());
    }

    @Override
    public void doUpdate(LinkCardinalityDescription linkCardinalityDescription) {
        var linkCardinalityDescriptionValue = linkCardinalityControl.getLinkCardinalityDescriptionValue(linkCardinalityDescription);
        linkCardinalityDescriptionValue.setDescription(edit.getDescription());

        linkCardinalityControl.updateLinkCardinalityDescriptionFromValue(linkCardinalityDescriptionValue, getPartyPK());
    }

}
