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

import com.echothree.control.user.core.common.form.GetLinkTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.core.server.logic.LinkCardinalityLogic;
import com.echothree.model.control.core.server.logic.LinkDirectionalityLogic;
import com.echothree.model.control.core.server.logic.LinkTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.factory.LinkTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetLinkTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<LinkType, GetLinkTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ParentLinkTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SourceComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SourceEntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("TargetComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TargetEntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("LinkCardinalityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LinkDirectionalityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InverseLinkTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    EntityTypeLogic entityTypeLogic;

    @Inject
    LinkCardinalityLogic linkCardinalityLogic;

    @Inject
    LinkDirectionalityLogic linkDirectionalityLogic;

    @Inject
    LinkTypeLogic linkTypeLogic;

    /** Creates a new instance of GetLinkTypesCommand */
    public GetLinkTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Condition condition;

    @Override
    protected void handleForm() {
        if((form.getSourceComponentVendorName() == null) != (form.getSourceEntityTypeName() == null)
                || (form.getTargetComponentVendorName() == null) != (form.getTargetEntityTypeName() == null)) {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        } else {
            var parentLinkType = form.getParentLinkTypeName() == null ? null : linkTypeLogic.getLinkTypeByName(this, form.getParentLinkTypeName());

            if(!hasExecutionErrors()) {
                var sourceEntityType = form.getSourceEntityTypeName() == null ? null : entityTypeLogic.getEntityTypeByName(this,
                        form.getSourceComponentVendorName(), form.getSourceEntityTypeName());

                if(!hasExecutionErrors()) {
                    var targetEntityType = form.getTargetEntityTypeName() == null ? null : entityTypeLogic.getEntityTypeByName(this,
                            form.getTargetComponentVendorName(), form.getTargetEntityTypeName());

                    if(!hasExecutionErrors()) {
                        var linkCardinality = form.getLinkCardinalityName() == null ? null : linkCardinalityLogic.getLinkCardinalityByName(this, form.getLinkCardinalityName());

                        if(!hasExecutionErrors()) {
                            var linkDirectionality = form.getLinkDirectionalityName() == null ? null : linkDirectionalityLogic.getLinkDirectionalityByName(this, form.getLinkDirectionalityName());

                            if(!hasExecutionErrors()) {
                                var inverseLinkType = form.getInverseLinkTypeName() == null ? null : linkTypeLogic.getLinkTypeByName(this, form.getInverseLinkTypeName());

                                if(!hasExecutionErrors()) {
                                    condition = linkTypeControl.getLinkTypeFilter(parentLinkType, sourceEntityType, targetEntityType, linkCardinality, linkDirectionality, inverseLinkType);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : linkTypeControl.countLinkTypes(condition);
    }

    @Override
    protected Collection<LinkType> getEntities() {
        return hasExecutionErrors() ? null : linkTypeControl.getLinkTypes(condition);
    }

    @Override
    protected BaseResult getResult(Collection<LinkType> entities) {
        var result = CoreResultFactory.getGetLinkTypesResult();

        if(entities != null) {
            if(session.hasLimit(LinkTypeFactory.class)) {
                result.setLinkTypeCount(getTotalEntities());
            }

            result.setLinkTypes(linkTypeControl.getLinkTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
