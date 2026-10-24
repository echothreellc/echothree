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

import com.echothree.control.user.core.common.form.CreateLinkTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.LinkTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CreateLinkTypeCommand
        extends BaseSimpleCommand<CreateLinkTypeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkType.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
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
    LinkTypeLogic linkTypeLogic;

    /** Creates a new instance of CreateLinkTypeCommand */
    public CreateLinkTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getCreateLinkTypeResult();
        var linkType = linkTypeLogic.createLinkType(this, form, getPreferredLanguage(), getPartyPK());

        if(linkType != null) {
            result.setEntityRef(linkType.getPrimaryKey().getEntityRef());
            result.setLinkTypeName(linkType.getLastDetail().getLinkTypeName());
        }

        return result;
    }

}
