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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentCollectionsForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.factory.ContentCollectionFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetContentCollectionsCommand
        extends BasePaginatedMultipleEntitiesCommand<ContentCollection, GetContentCollectionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCollection.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetContentCollectionsCommand */
    public GetContentCollectionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var contentControl = Session.getModelController(ContentControl.class);

        return contentControl.countContentCollections();
    }

    @Override
    protected Collection<ContentCollection> getEntities() {
        var contentControl = Session.getModelController(ContentControl.class);

        return contentControl.getContentCollections();
    }

    @Override
    protected BaseResult getResult(Collection<ContentCollection> entities) {
        var result = ContentResultFactory.getGetContentCollectionsResult();

        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);

            if(session.hasLimit(ContentCollectionFactory.class)) {
                result.setContentCollectionCount(getTotalEntities());
            }

            result.setContentCollections(contentControl.getContentCollectionTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
