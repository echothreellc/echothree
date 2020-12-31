// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.CreateCancellationPolicyForm;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCancellationPolicyCommand
        extends BaseSimpleCommand<CreateCancellationPolicyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicy.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PolicyMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Policy", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCancellationPolicyCommand */
    public CreateCancellationPolicyCommand(UserVisitPK userVisitPK, CreateCancellationPolicyForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        String cancellationKindName = form.getCancellationKindName();
        CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
        
        if(cancellationKind != null) {
            String cancellationPolicyName = form.getCancellationPolicyName();
            CancellationPolicy cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind,cancellationPolicyName);
            
            if(cancellationPolicy == null) {
                MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
                String policy = form.getPolicy();
                MimeType policyMimeType = mimeTypeLogic.checkMimeType(this, form.getPolicyMimeTypeName(), policy, MimeTypeUsageTypes.TEXT.name(),
                        ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                        ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());

                if(!hasExecutionErrors()) {
                    var partyPK = getPartyPK();
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();

                    cancellationPolicy = cancellationPolicyControl.createCancellationPolicy(cancellationKind, cancellationPolicyName, isDefault, sortOrder,
                            partyPK);

                    if(description != null || policy != null) {
                        cancellationPolicyControl.createCancellationPolicyTranslation(cancellationPolicy, getPreferredLanguage(), description, policyMimeType,
                                policy, partyPK);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateCancellationPolicyName.name(), cancellationPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }
        
        return null;
    }
    
}
