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

import com.echothree.control.user.core.common.edit.LinkCardinalityEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.result.EditLinkCardinalityResult;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.LinkCardinalityUniversalSpec;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.logic.LinkCardinalityLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkCardinality;
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
public class EditLinkCardinalityCommand
        extends BaseAbstractEditCommand<LinkCardinalityUniversalSpec, LinkCardinalityEdit, EditLinkCardinalityResult, LinkCardinality, LinkCardinality> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkCardinality.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkCardinalityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkCardinalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LinkCardinalityLogic linkCardinalityLogic;

    /** Creates a new instance of EditLinkCardinalityCommand */
    public EditLinkCardinalityCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLinkCardinalityResult getResult() {
        return CoreResultFactory.getEditLinkCardinalityResult();
    }

    @Override
    public LinkCardinalityEdit getEdit() {
        return CoreEditFactory.getLinkCardinalityEdit();
    }

    @Override
    public LinkCardinality getEntity(EditLinkCardinalityResult result) {
        var linkCardinality = linkCardinalityLogic.getLinkCardinalityByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));

        if(!hasExecutionErrors()) {
            result.setLinkCardinality(linkCardinalityControl.getLinkCardinalityTransfer(getUserVisit(), linkCardinality));
        }

        return linkCardinality;
    }

    @Override
    public LinkCardinality getLockEntity(LinkCardinality linkCardinality) {
        return linkCardinality;
    }

    @Override
    public void fillInResult(EditLinkCardinalityResult result, LinkCardinality linkCardinality) {
        result.setLinkCardinality(linkCardinalityControl.getLinkCardinalityTransfer(getUserVisit(), linkCardinality));
    }

    @Override
    public void doLock(LinkCardinalityEdit edit, LinkCardinality linkCardinality) {
        var linkCardinalityDescription = linkCardinalityControl.getLinkCardinalityDescription(linkCardinality, getPreferredLanguage());
        var linkCardinalityDetail = linkCardinality.getLastDetail();

        edit.setLinkCardinalityName(linkCardinalityDetail.getLinkCardinalityName());
        edit.setIsDefault(linkCardinalityDetail.getIsDefault().toString());
        edit.setSortOrder(linkCardinalityDetail.getSortOrder().toString());

        if(linkCardinalityDescription != null) {
            edit.setDescription(linkCardinalityDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LinkCardinality linkCardinality) {
        var linkCardinalityName = edit.getLinkCardinalityName();
        var duplicateLinkCardinality = linkCardinalityControl.getLinkCardinalityByName(linkCardinalityName);

        if(duplicateLinkCardinality != null && !linkCardinality.equals(duplicateLinkCardinality)) {
            addExecutionError(ExecutionErrors.DuplicateLinkCardinalityName.name(), linkCardinalityName);
        }
    }

    @Override
    public void doUpdate(LinkCardinality linkCardinality) {
        var partyPK = getPartyPK();
        var linkCardinalityDetailValue = linkCardinalityControl.getLinkCardinalityDetailValueForUpdate(linkCardinality);
        var linkCardinalityDescription = linkCardinalityControl.getLinkCardinalityDescriptionForUpdate(linkCardinality, getPreferredLanguage());
        var description = edit.getDescription();

        linkCardinalityDetailValue.setLinkCardinalityName(edit.getLinkCardinalityName());
        linkCardinalityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        linkCardinalityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        linkCardinalityLogic.updateLinkCardinalityFromValue(linkCardinalityDetailValue, partyPK);

        if(linkCardinalityDescription == null && description != null) {
            linkCardinalityControl.createLinkCardinalityDescription(linkCardinality, getPreferredLanguage(), description, partyPK);
        } else {
            if(linkCardinalityDescription != null && description == null) {
                linkCardinalityControl.deleteLinkCardinalityDescription(linkCardinalityDescription, partyPK);
            } else {
                if(linkCardinalityDescription != null && description != null) {
                    var linkCardinalityDescriptionValue = linkCardinalityControl.getLinkCardinalityDescriptionValue(linkCardinalityDescription);

                    linkCardinalityDescriptionValue.setDescription(description);
                    linkCardinalityControl.updateLinkCardinalityDescriptionFromValue(linkCardinalityDescriptionValue, partyPK);
                }
            }
        }
    }

}
