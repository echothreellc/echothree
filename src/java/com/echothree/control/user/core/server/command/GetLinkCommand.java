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

import com.echothree.control.user.core.common.form.GetLinkForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.control.core.server.logic.LinkLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetLinkCommand
        extends BaseSingleEntityCommand<Link, GetLinkForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Link.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SourceEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("SourceUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("TargetEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("TargetUuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    LinkControl linkControl;

    @Inject
    LinkLogic linkLogic;

    /** Creates a new instance of GetLinkCommand */
    public GetLinkCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Link getEntity() {
        Link link = null;
        var definition = linkLogic.getDefinition(this, form, false);

        if(!hasExecutionErrors()) {
            link = linkLogic.getLink(this, definition.linkType(), definition.sourceEntityInstance(), definition.targetEntityInstance());

            if(!hasExecutionErrors()) {
                sendEvent(link.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        }

        return link;
    }

    @Override
    protected BaseResult getResult(Link link) {
        var result = CoreResultFactory.getGetLinkResult();

        if(link != null) {
            result.setLink(linkControl.getLinkTransfer(getUserVisit(), link));
        }

        return result;
    }

}
