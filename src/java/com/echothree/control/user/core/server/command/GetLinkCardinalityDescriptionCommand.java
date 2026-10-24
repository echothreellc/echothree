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

import com.echothree.control.user.core.common.form.GetLinkCardinalityDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.logic.LinkCardinalityLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class GetLinkCardinalityDescriptionCommand
        extends BaseSimpleCommand<GetLinkCardinalityDescriptionForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LinkCardinality.name(), SecurityRoles.Description.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LinkCardinalityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    LinkCardinalityLogic linkCardinalityLogic;

    /** Creates a new instance of GetLinkCardinalityDescriptionCommand */
    public GetLinkCardinalityDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getGetLinkCardinalityDescriptionResult();
        var linkCardinalityName = form.getLinkCardinalityName();
        var linkCardinality = linkCardinalityLogic.getLinkCardinalityByName(this, linkCardinalityName);

        if(!hasExecutionErrors()) {
            var languageIsoName = form.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                var linkCardinalityDescription = linkCardinalityControl.getLinkCardinalityDescription(linkCardinality, language);

                if(linkCardinalityDescription != null) {
                    result.setLinkCardinalityDescription(linkCardinalityControl.getLinkCardinalityDescriptionTransfer(getUserVisit(), linkCardinalityDescription));
                } else {
                    addExecutionError(ExecutionErrors.UnknownLinkCardinalityDescription.name(), linkCardinalityName, languageIsoName);
                }
            }
        }

        return result;
    }

}
