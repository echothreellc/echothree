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

import com.echothree.control.user.core.common.form.GetLinksForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.control.core.server.logic.LinkLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.core.server.factory.LinkFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.jooq.Condition;

@Dependent
public class GetLinksCommand
        extends BasePaginatedMultipleEntitiesCommand<Link, GetLinksForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Link.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SourceEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("SourceUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("TargetEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("TargetUuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    LinkControl linkControl;

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    LinkLogic linkLogic;

    /** Creates a new instance of GetLinksCommand */
    public GetLinksCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Condition condition;
    private LinkLogic.Definition definition;

    @Override
    protected void handleForm() {
        definition = linkLogic.getDefinition(this, form, true);

        if(!hasExecutionErrors()) {
            condition = linkControl.getLinkFilter(definition.linkType(), definition.sourceEntityInstance(), definition.targetEntityInstance());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : linkControl.countLinks(condition);
    }

    @Override
    protected Collection<Link> getEntities() {
        return hasExecutionErrors() ? null : linkControl.getLinks(condition);
    }

    @Override
    protected BaseResult getResult(Collection<Link> entities) {
        var result = CoreResultFactory.getGetLinksResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(definition.linkType() != null) {
                result.setLinkType(linkTypeControl.getLinkTypeTransfer(userVisit, definition.linkType()));
            }

            if(definition.sourceEntityInstance() != null) {
                result.setSourceEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit,
                        definition.sourceEntityInstance(), false, false, false, false));
            }

            if(definition.targetEntityInstance() != null) {
                result.setTargetEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit,
                        definition.targetEntityInstance(), false, false, false, false));
            }

            if(session.hasLimit(LinkFactory.class)) {
                result.setLinkCount(getTotalEntities());
            }

            result.setLinks(linkControl.getLinkTransfers(userVisit, entities));
        }

        return result;
    }

}
