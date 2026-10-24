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
import com.echothree.control.user.core.common.edit.LinkTypeTranslationEdit;
import com.echothree.control.user.core.common.result.EditLinkTypeTranslationResult;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.LinkTypeTranslationSpec;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.control.core.server.logic.LinkTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.LinkTypeTranslation;
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
public class EditLinkTypeTranslationCommand
        extends BaseAbstractEditCommand<LinkTypeTranslationSpec, LinkTypeTranslationEdit, EditLinkTypeTranslationResult, LinkTypeTranslation, LinkType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkType.name(), SecurityRoles.Translation.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("GuidanceMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Guidance", FieldType.STRING, false, null, null)
        );
    }

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    LinkTypeLogic linkTypeLogic;

    /** Creates a new instance of EditLinkTypeTranslationCommand */
    public EditLinkTypeTranslationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLinkTypeTranslationResult getResult() {
        return CoreResultFactory.getEditLinkTypeTranslationResult();
    }

    @Override
    public LinkTypeTranslationEdit getEdit() {
        return CoreEditFactory.getLinkTypeTranslationEdit();
    }

    @Override
    public LinkTypeTranslation getEntity(EditLinkTypeTranslationResult result) {
        LinkTypeTranslation linkTypeTranslation = null;
        var linkTypeName = spec.getLinkTypeName();
        var linkType = linkTypeLogic.getLinkTypeByName(this, linkTypeName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                linkTypeTranslation = linkTypeControl.getLinkTypeTranslation(linkType, language,
                        editModeToEntityPermission(editMode));

                if(linkTypeTranslation == null) {
                    addExecutionError(ExecutionErrors.UnknownLinkTypeTranslation.name(), linkTypeName, languageIsoName);
                }
            }
        }

        return linkTypeTranslation;
    }

    @Override
    public LinkType getLockEntity(LinkTypeTranslation linkTypeTranslation) {
        return linkTypeTranslation.getLinkType();
    }

    @Override
    public void fillInResult(EditLinkTypeTranslationResult result, LinkTypeTranslation linkTypeTranslation) {
        result.setLinkTypeTranslation(linkTypeControl.getLinkTypeTranslationTransfer(getUserVisit(), linkTypeTranslation));
    }

    @Override
    public void doLock(LinkTypeTranslationEdit edit, LinkTypeTranslation linkTypeTranslation) {
        var mimeType = linkTypeTranslation.getGuidanceMimeType();

        edit.setDescription(linkTypeTranslation.getDescription());
        edit.setGuidanceMimeTypeName(mimeType == null ? null : mimeType.getLastDetail().getMimeTypeName());
        edit.setGuidance(linkTypeTranslation.getGuidance());
    }

    private MimeType guidanceMimeType;

    @Override
    public void canUpdate(LinkTypeTranslation linkTypeTranslation) {
        guidanceMimeType = linkTypeLogic.checkGuidance(this, edit);
    }

    @Override
    public void doUpdate(LinkTypeTranslation linkTypeTranslation) {
        var linkTypeTranslationValue = linkTypeControl.getLinkTypeTranslationValue(linkTypeTranslation);

        linkTypeTranslationValue.setDescription(edit.getDescription());
        linkTypeTranslationValue.setGuidanceMimeTypePK(guidanceMimeType == null ? null : guidanceMimeType.getPrimaryKey());
        linkTypeTranslationValue.setGuidance(edit.getGuidance());

        linkTypeControl.updateLinkTypeTranslationFromValue(linkTypeTranslationValue, getPartyPK());
    }

}
