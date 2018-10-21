// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.picklist.server;

import com.echothree.control.user.picklist.remote.PicklistRemote;
import com.echothree.control.user.picklist.remote.form.*;
import com.echothree.control.user.picklist.server.command.*;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class PicklistBean
        extends PicklistFormsImpl
        implements PicklistRemote, PicklistLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "PicklistBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Picklist Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistType(UserVisitPK userVisitPK, CreatePicklistTypeForm form) {
        return new CreatePicklistTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTypeChoices(UserVisitPK userVisitPK, GetPicklistTypeChoicesForm form) {
        return new GetPicklistTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistType(UserVisitPK userVisitPK, GetPicklistTypeForm form) {
        return new GetPicklistTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTypes(UserVisitPK userVisitPK, GetPicklistTypesForm form) {
        return new GetPicklistTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPicklistType(UserVisitPK userVisitPK, SetDefaultPicklistTypeForm form) {
        return new SetDefaultPicklistTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistType(UserVisitPK userVisitPK, EditPicklistTypeForm form) {
        return new EditPicklistTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistType(UserVisitPK userVisitPK, DeletePicklistTypeForm form) {
        return new DeletePicklistTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTypeDescription(UserVisitPK userVisitPK, CreatePicklistTypeDescriptionForm form) {
        return new CreatePicklistTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTypeDescription(UserVisitPK userVisitPK, GetPicklistTypeDescriptionForm form) {
        return new GetPicklistTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTypeDescriptionsForm form) {
        return new GetPicklistTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistTypeDescription(UserVisitPK userVisitPK, EditPicklistTypeDescriptionForm form) {
        return new EditPicklistTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistTypeDescription(UserVisitPK userVisitPK, DeletePicklistTypeDescriptionForm form) {
        return new DeletePicklistTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTimeType(UserVisitPK userVisitPK, CreatePicklistTimeTypeForm form) {
        return new CreatePicklistTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTimeTypeChoices(UserVisitPK userVisitPK, GetPicklistTimeTypeChoicesForm form) {
        return new GetPicklistTimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTimeType(UserVisitPK userVisitPK, GetPicklistTimeTypeForm form) {
        return new GetPicklistTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTimeTypes(UserVisitPK userVisitPK, GetPicklistTimeTypesForm form) {
        return new GetPicklistTimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPicklistTimeType(UserVisitPK userVisitPK, SetDefaultPicklistTimeTypeForm form) {
        return new SetDefaultPicklistTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistTimeType(UserVisitPK userVisitPK, EditPicklistTimeTypeForm form) {
        return new EditPicklistTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistTimeType(UserVisitPK userVisitPK, DeletePicklistTimeTypeForm form) {
        return new DeletePicklistTimeTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTimeTypeDescription(UserVisitPK userVisitPK, CreatePicklistTimeTypeDescriptionForm form) {
        return new CreatePicklistTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTimeTypeDescription(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionForm form) {
        return new GetPicklistTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistTimeTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionsForm form) {
        return new GetPicklistTimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistTimeTypeDescription(UserVisitPK userVisitPK, EditPicklistTimeTypeDescriptionForm form) {
        return new EditPicklistTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistTimeTypeDescription(UserVisitPK userVisitPK, DeletePicklistTimeTypeDescriptionForm form) {
        return new DeletePicklistTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAliasType(UserVisitPK userVisitPK, CreatePicklistAliasTypeForm form) {
        return new CreatePicklistAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAliasTypeChoices(UserVisitPK userVisitPK, GetPicklistAliasTypeChoicesForm form) {
        return new GetPicklistAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAliasType(UserVisitPK userVisitPK, GetPicklistAliasTypeForm form) {
        return new GetPicklistAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAliasTypes(UserVisitPK userVisitPK, GetPicklistAliasTypesForm form) {
        return new GetPicklistAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPicklistAliasType(UserVisitPK userVisitPK, SetDefaultPicklistAliasTypeForm form) {
        return new SetDefaultPicklistAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistAliasType(UserVisitPK userVisitPK, EditPicklistAliasTypeForm form) {
        return new EditPicklistAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistAliasType(UserVisitPK userVisitPK, DeletePicklistAliasTypeForm form) {
        return new DeletePicklistAliasTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAliasTypeDescription(UserVisitPK userVisitPK, CreatePicklistAliasTypeDescriptionForm form) {
        return new CreatePicklistAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAliasTypeDescription(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionForm form) {
        return new GetPicklistAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAliasTypeDescriptions(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionsForm form) {
        return new GetPicklistAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistAliasTypeDescription(UserVisitPK userVisitPK, EditPicklistAliasTypeDescriptionForm form) {
        return new EditPicklistAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistAliasTypeDescription(UserVisitPK userVisitPK, DeletePicklistAliasTypeDescriptionForm form) {
        return new DeletePicklistAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAlias(UserVisitPK userVisitPK, CreatePicklistAliasForm form) {
        return new CreatePicklistAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAlias(UserVisitPK userVisitPK, GetPicklistAliasForm form) {
        return new GetPicklistAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPicklistAliases(UserVisitPK userVisitPK, GetPicklistAliasesForm form) {
        return new GetPicklistAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPicklistAlias(UserVisitPK userVisitPK, EditPicklistAliasForm form) {
        return new EditPicklistAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePicklistAlias(UserVisitPK userVisitPK, DeletePicklistAliasForm form) {
        return new DeletePicklistAliasCommand(userVisitPK, form).run();
    }

}
