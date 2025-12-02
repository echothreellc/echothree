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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.DeleteSourceForm;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class DeleteSourceCommand
        extends BaseSimpleCommand<DeleteSourceForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Source.name(), SecurityRoles.Delete.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SourceName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of DeleteSourceCommand */
    public DeleteSourceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var sourceControl = Session.getModelController(SourceControl.class);
        var sourceName = form.getSourceName();
        var source = sourceControl.getSourceByNameForUpdate(sourceName);

        if(source != null) {
            var offerUse = source.getLastDetail().getOfferUse();

            if(sourceControl.countSourcesByOfferUse(offerUse) > 1) {
                sourceControl.deleteSource(source, getPartyPK());
            } else {
                var offerUseDetail = offerUse.getLastDetail();
                
                addExecutionError(ExecutionErrors.CannotDeleteLastSource.name(), offerUseDetail.getOffer().getLastDetail().getOfferName(),
                        offerUseDetail.getUse().getLastDetail().getUseName(), sourceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSourceName.name(), sourceName);
        }

        return null;
    }

}
