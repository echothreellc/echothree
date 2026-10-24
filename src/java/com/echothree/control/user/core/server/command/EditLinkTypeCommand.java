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
import com.echothree.control.user.core.common.edit.LinkTypeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditLinkTypeResult;
import com.echothree.control.user.core.common.spec.LinkTypeUniversalSpec;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.control.core.server.logic.LinkTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.MimeType;
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
public class EditLinkTypeCommand
        extends BaseAbstractEditCommand<LinkTypeUniversalSpec, LinkTypeEdit, EditLinkTypeResult, LinkType, LinkType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentLinkTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SourceComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SourceEntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("TargetComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TargetEntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("LinkCardinalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LinkDirectionalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsTransitive", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("InverseLinkTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("GuidanceMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Guidance", FieldType.STRING, false, null, null)
        );
    }

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    LinkTypeLogic linkTypeLogic;

    /** Creates a new instance of EditLinkTypeCommand */
    public EditLinkTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLinkTypeResult getResult() {
        return CoreResultFactory.getEditLinkTypeResult();
    }

    @Override
    public LinkTypeEdit getEdit() {
        return CoreEditFactory.getLinkTypeEdit();
    }

    @Override
    public LinkType getEntity(EditLinkTypeResult result) {
        var linkType = linkTypeLogic.getLinkTypeByUniversalSpec(this, spec,
                editModeToEntityPermission(editMode));

        if(!hasExecutionErrors()) {
            result.setLinkType(linkTypeControl.getLinkTypeTransfer(getUserVisit(), linkType));
        }

        return linkType;
    }

    @Override
    public LinkType getLockEntity(LinkType linkType) {
        return linkType;
    }

    @Override
    public void fillInResult(EditLinkTypeResult result, LinkType linkType) {
        result.setLinkType(linkTypeControl.getLinkTypeTransfer(getUserVisit(), linkType));
    }

    private LinkTypeLogic.Definition definition;
    private MimeType guidanceMimeType;

    @Override
    public void doLock(LinkTypeEdit edit, LinkType linkType) {
        var linkTypeDetail = linkType.getLastDetail();
        var linkTypeTranslation = linkTypeControl.getLinkTypeTranslation(linkType, getPreferredLanguage());
        var sourceEntityTypeDetail = linkTypeDetail.getSourceEntityType().getLastDetail();
        var targetEntityTypeDetail = linkTypeDetail.getTargetEntityType().getLastDetail();

        edit.setLinkTypeName(linkTypeDetail.getLinkTypeName());
        edit.setParentLinkTypeName(linkTypeDetail.getParentLinkType() == null ? null : linkTypeDetail.getParentLinkType().getLastDetail().getLinkTypeName());
        edit.setSourceComponentVendorName(sourceEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
        edit.setSourceEntityTypeName(sourceEntityTypeDetail.getEntityTypeName());
        edit.setTargetComponentVendorName(targetEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
        edit.setTargetEntityTypeName(targetEntityTypeDetail.getEntityTypeName());
        edit.setLinkCardinalityName(linkTypeDetail.getLinkCardinality().getLastDetail().getLinkCardinalityName());
        edit.setLinkDirectionalityName(linkTypeDetail.getLinkDirectionality().getLastDetail().getLinkDirectionalityName());
        edit.setIsTransitive(linkTypeDetail.getIsTransitive().toString());
        edit.setInverseLinkTypeName(linkTypeDetail.getInverseLinkType() == null ? null : linkTypeDetail.getInverseLinkType().getLastDetail().getLinkTypeName());
        edit.setSortOrder(linkTypeDetail.getSortOrder().toString());

        if(linkTypeTranslation != null) {
            var guidanceMimeType = linkTypeTranslation.getGuidanceMimeType();

            edit.setDescription(linkTypeTranslation.getDescription());
            edit.setGuidanceMimeTypeName(guidanceMimeType == null ? null : guidanceMimeType.getLastDetail().getMimeTypeName());
            edit.setGuidance(linkTypeTranslation.getGuidance());
        }
    }

    @Override
    public void canUpdate(LinkType linkType) {
        var duplicateLinkType = linkTypeControl.getLinkTypeByName(edit.getLinkTypeName());

        if(duplicateLinkType != null && !duplicateLinkType.equals(linkType)) {
            addExecutionError(ExecutionErrors.DuplicateLinkTypeName.name(), edit.getLinkTypeName());
        }

        if(!hasExecutionErrors()) {
            definition = linkTypeLogic.getDefinition(this, edit, linkType);
        }

        if(!hasExecutionErrors()) {
            guidanceMimeType = linkTypeLogic.checkGuidance(this, edit);
            if(edit.getDescription() == null && edit.getGuidance() != null) {
                addExecutionError(ExecutionErrors.MissingRequiredLinkTypeDescription.name());
            }
        }
    }

    @Override
    public void doUpdate(LinkType linkType) {
        var partyPK = getPartyPK();

        linkTypeLogic.updateLinkType(linkType, edit, definition, partyPK);

        var linkTypeTranslation = linkTypeControl.getLinkTypeTranslationForUpdate(linkType, getPreferredLanguage());
        if(edit.getDescription() == null) {
            if(linkTypeTranslation != null) {
                linkTypeControl.deleteLinkTypeTranslation(linkTypeTranslation, partyPK);
            }
        } else if(linkTypeTranslation == null) {
            linkTypeControl.createLinkTypeTranslation(linkType, getPreferredLanguage(), edit.getDescription(), guidanceMimeType, edit.getGuidance(), partyPK);
        } else {
            var linkTypeTranslationValue = linkTypeControl.getLinkTypeTranslationValue(linkTypeTranslation);

            linkTypeTranslationValue.setDescription(edit.getDescription());
            linkTypeTranslationValue.setGuidanceMimeTypePK(guidanceMimeType == null ? null : guidanceMimeType.getPrimaryKey());
            linkTypeTranslationValue.setGuidance(edit.getGuidance());

            linkTypeControl.updateLinkTypeTranslationFromValue(linkTypeTranslationValue, partyPK);
        }
    }

}
