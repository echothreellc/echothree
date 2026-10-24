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

import com.echothree.control.user.core.common.edit.LinkDirectionalityEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.result.EditLinkDirectionalityResult;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.LinkDirectionalityUniversalSpec;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.core.server.logic.LinkDirectionalityLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
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
public class EditLinkDirectionalityCommand
        extends BaseAbstractEditCommand<LinkDirectionalityUniversalSpec, LinkDirectionalityEdit, EditLinkDirectionalityResult, LinkDirectionality, LinkDirectionality> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkDirectionality.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkDirectionalityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkDirectionalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    @Inject
    LinkDirectionalityLogic linkDirectionalityLogic;

    /** Creates a new instance of EditLinkDirectionalityCommand */
    public EditLinkDirectionalityCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLinkDirectionalityResult getResult() {
        return CoreResultFactory.getEditLinkDirectionalityResult();
    }

    @Override
    public LinkDirectionalityEdit getEdit() {
        return CoreEditFactory.getLinkDirectionalityEdit();
    }

    @Override
    public LinkDirectionality getEntity(EditLinkDirectionalityResult result) {
        var linkDirectionality = linkDirectionalityLogic.getLinkDirectionalityByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));

        if(!hasExecutionErrors()) {
            result.setLinkDirectionality(linkDirectionalityControl.getLinkDirectionalityTransfer(getUserVisit(), linkDirectionality));
        }

        return linkDirectionality;
    }

    @Override
    public LinkDirectionality getLockEntity(LinkDirectionality linkDirectionality) {
        return linkDirectionality;
    }

    @Override
    public void fillInResult(EditLinkDirectionalityResult result, LinkDirectionality linkDirectionality) {
        result.setLinkDirectionality(linkDirectionalityControl.getLinkDirectionalityTransfer(getUserVisit(), linkDirectionality));
    }

    @Override
    public void doLock(LinkDirectionalityEdit edit, LinkDirectionality linkDirectionality) {
        var linkDirectionalityDescription = linkDirectionalityControl.getLinkDirectionalityDescription(linkDirectionality, getPreferredLanguage());
        var linkDirectionalityDetail = linkDirectionality.getLastDetail();

        edit.setLinkDirectionalityName(linkDirectionalityDetail.getLinkDirectionalityName());
        edit.setIsDefault(linkDirectionalityDetail.getIsDefault().toString());
        edit.setSortOrder(linkDirectionalityDetail.getSortOrder().toString());

        if(linkDirectionalityDescription != null) {
            edit.setDescription(linkDirectionalityDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LinkDirectionality linkDirectionality) {
        var linkDirectionalityName = edit.getLinkDirectionalityName();
        var duplicateLinkDirectionality = linkDirectionalityControl.getLinkDirectionalityByName(linkDirectionalityName);

        if(duplicateLinkDirectionality != null && !linkDirectionality.equals(duplicateLinkDirectionality)) {
            addExecutionError(ExecutionErrors.DuplicateLinkDirectionalityName.name(), linkDirectionalityName);
        }
    }

    @Override
    public void doUpdate(LinkDirectionality linkDirectionality) {
        var partyPK = getPartyPK();
        var linkDirectionalityDetailValue = linkDirectionalityControl.getLinkDirectionalityDetailValueForUpdate(linkDirectionality);
        var linkDirectionalityDescription = linkDirectionalityControl.getLinkDirectionalityDescriptionForUpdate(linkDirectionality, getPreferredLanguage());
        var description = edit.getDescription();

        linkDirectionalityDetailValue.setLinkDirectionalityName(edit.getLinkDirectionalityName());
        linkDirectionalityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        linkDirectionalityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        linkDirectionalityLogic.updateLinkDirectionalityFromValue(linkDirectionalityDetailValue, partyPK);

        if(linkDirectionalityDescription == null && description != null) {
            linkDirectionalityControl.createLinkDirectionalityDescription(linkDirectionality, getPreferredLanguage(), description, partyPK);
        } else {
            if(linkDirectionalityDescription != null && description == null) {
                linkDirectionalityControl.deleteLinkDirectionalityDescription(linkDirectionalityDescription, partyPK);
            } else {
                if(linkDirectionalityDescription != null && description != null) {
                    var linkDirectionalityDescriptionValue = linkDirectionalityControl.getLinkDirectionalityDescriptionValue(linkDirectionalityDescription);

                    linkDirectionalityDescriptionValue.setDescription(description);
                    linkDirectionalityControl.updateLinkDirectionalityDescriptionFromValue(linkDirectionalityDescriptionValue, partyPK);
                }
            }
        }
    }

}
