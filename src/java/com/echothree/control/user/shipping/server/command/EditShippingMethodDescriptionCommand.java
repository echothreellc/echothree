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

package com.echothree.control.user.shipping.server.command;

import com.echothree.control.user.shipping.common.edit.ShippingEditFactory;
import com.echothree.control.user.shipping.common.edit.ShippingMethodDescriptionEdit;
import com.echothree.control.user.shipping.common.form.EditShippingMethodDescriptionForm;
import com.echothree.control.user.shipping.common.result.EditShippingMethodDescriptionResult;
import com.echothree.control.user.shipping.common.result.ShippingResultFactory;
import com.echothree.control.user.shipping.common.spec.ShippingMethodDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditShippingMethodDescriptionCommand
        extends BaseAbstractEditCommand<ShippingMethodDescriptionSpec, ShippingMethodDescriptionEdit, EditShippingMethodDescriptionResult, ShippingMethodDescription, ShippingMethod> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethod.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditShippingMethodDescriptionCommand */
    public EditShippingMethodDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditShippingMethodDescriptionResult getResult() {
        return ShippingResultFactory.getEditShippingMethodDescriptionResult();
    }

    @Override
    public ShippingMethodDescriptionEdit getEdit() {
        return ShippingEditFactory.getShippingMethodDescriptionEdit();
    }

    @Override
    public ShippingMethodDescription getEntity(EditShippingMethodDescriptionResult result) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        ShippingMethodDescription shippingMethodDescription = null;
        var shippingMethodName = spec.getShippingMethodName();
        var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);

        if(shippingMethod != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    shippingMethodDescription = shippingControl.getShippingMethodDescription(shippingMethod, language);
                } else { // EditMode.UPDATE
                    shippingMethodDescription = shippingControl.getShippingMethodDescriptionForUpdate(shippingMethod, language);
                }

                if(shippingMethodDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownShippingMethodDescription.name(), shippingMethodName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
        }

        return shippingMethodDescription;
    }

    @Override
    public ShippingMethod getLockEntity(ShippingMethodDescription shippingMethodDescription) {
        return shippingMethodDescription.getShippingMethod();
    }

    @Override
    public void fillInResult(EditShippingMethodDescriptionResult result, ShippingMethodDescription shippingMethodDescription) {
        var shippingControl = Session.getModelController(ShippingControl.class);

        result.setShippingMethodDescription(shippingControl.getShippingMethodDescriptionTransfer(getUserVisit(), shippingMethodDescription));
    }

    @Override
    public void doLock(ShippingMethodDescriptionEdit edit, ShippingMethodDescription shippingMethodDescription) {
        edit.setDescription(shippingMethodDescription.getDescription());
    }

    @Override
    public void doUpdate(ShippingMethodDescription shippingMethodDescription) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethodDescriptionValue = shippingControl.getShippingMethodDescriptionValue(shippingMethodDescription);
        shippingMethodDescriptionValue.setDescription(edit.getDescription());

        shippingControl.updateShippingMethodDescriptionFromValue(shippingMethodDescriptionValue, getPartyPK());
    }
    
}
