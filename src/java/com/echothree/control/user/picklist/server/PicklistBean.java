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

package com.echothree.control.user.picklist.server;

import com.echothree.control.user.picklist.common.PicklistRemote;
import com.echothree.control.user.picklist.common.form.*;
import com.echothree.control.user.picklist.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
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
        return new CreatePicklistTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypeChoices(UserVisitPK userVisitPK, GetPicklistTypeChoicesForm form) {
        return new GetPicklistTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistType(UserVisitPK userVisitPK, GetPicklistTypeForm form) {
        return new GetPicklistTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypes(UserVisitPK userVisitPK, GetPicklistTypesForm form) {
        return new GetPicklistTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPicklistType(UserVisitPK userVisitPK, SetDefaultPicklistTypeForm form) {
        return new SetDefaultPicklistTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistType(UserVisitPK userVisitPK, EditPicklistTypeForm form) {
        return new EditPicklistTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistType(UserVisitPK userVisitPK, DeletePicklistTypeForm form) {
        return new DeletePicklistTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTypeDescription(UserVisitPK userVisitPK, CreatePicklistTypeDescriptionForm form) {
        return new CreatePicklistTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypeDescription(UserVisitPK userVisitPK, GetPicklistTypeDescriptionForm form) {
        return new GetPicklistTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTypeDescriptionsForm form) {
        return new GetPicklistTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistTypeDescription(UserVisitPK userVisitPK, EditPicklistTypeDescriptionForm form) {
        return new EditPicklistTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistTypeDescription(UserVisitPK userVisitPK, DeletePicklistTypeDescriptionForm form) {
        return new DeletePicklistTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTimeType(UserVisitPK userVisitPK, CreatePicklistTimeTypeForm form) {
        return new CreatePicklistTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypeChoices(UserVisitPK userVisitPK, GetPicklistTimeTypeChoicesForm form) {
        return new GetPicklistTimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeType(UserVisitPK userVisitPK, GetPicklistTimeTypeForm form) {
        return new GetPicklistTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypes(UserVisitPK userVisitPK, GetPicklistTimeTypesForm form) {
        return new GetPicklistTimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPicklistTimeType(UserVisitPK userVisitPK, SetDefaultPicklistTimeTypeForm form) {
        return new SetDefaultPicklistTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistTimeType(UserVisitPK userVisitPK, EditPicklistTimeTypeForm form) {
        return new EditPicklistTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistTimeType(UserVisitPK userVisitPK, DeletePicklistTimeTypeForm form) {
        return new DeletePicklistTimeTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTimeTypeDescription(UserVisitPK userVisitPK, CreatePicklistTimeTypeDescriptionForm form) {
        return new CreatePicklistTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypeDescription(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionForm form) {
        return new GetPicklistTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionsForm form) {
        return new GetPicklistTimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistTimeTypeDescription(UserVisitPK userVisitPK, EditPicklistTimeTypeDescriptionForm form) {
        return new EditPicklistTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistTimeTypeDescription(UserVisitPK userVisitPK, DeletePicklistTimeTypeDescriptionForm form) {
        return new DeletePicklistTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAliasType(UserVisitPK userVisitPK, CreatePicklistAliasTypeForm form) {
        return new CreatePicklistAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypeChoices(UserVisitPK userVisitPK, GetPicklistAliasTypeChoicesForm form) {
        return new GetPicklistAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasType(UserVisitPK userVisitPK, GetPicklistAliasTypeForm form) {
        return new GetPicklistAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypes(UserVisitPK userVisitPK, GetPicklistAliasTypesForm form) {
        return new GetPicklistAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPicklistAliasType(UserVisitPK userVisitPK, SetDefaultPicklistAliasTypeForm form) {
        return new SetDefaultPicklistAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistAliasType(UserVisitPK userVisitPK, EditPicklistAliasTypeForm form) {
        return new EditPicklistAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistAliasType(UserVisitPK userVisitPK, DeletePicklistAliasTypeForm form) {
        return new DeletePicklistAliasTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAliasTypeDescription(UserVisitPK userVisitPK, CreatePicklistAliasTypeDescriptionForm form) {
        return new CreatePicklistAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypeDescription(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionForm form) {
        return new GetPicklistAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypeDescriptions(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionsForm form) {
        return new GetPicklistAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistAliasTypeDescription(UserVisitPK userVisitPK, EditPicklistAliasTypeDescriptionForm form) {
        return new EditPicklistAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistAliasTypeDescription(UserVisitPK userVisitPK, DeletePicklistAliasTypeDescriptionForm form) {
        return new DeletePicklistAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAlias(UserVisitPK userVisitPK, CreatePicklistAliasForm form) {
        return new CreatePicklistAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAlias(UserVisitPK userVisitPK, GetPicklistAliasForm form) {
        return new GetPicklistAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliases(UserVisitPK userVisitPK, GetPicklistAliasesForm form) {
        return new GetPicklistAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistAlias(UserVisitPK userVisitPK, EditPicklistAliasForm form) {
        return new EditPicklistAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistAlias(UserVisitPK userVisitPK, DeletePicklistAliasForm form) {
        return new DeletePicklistAliasCommand().run(userVisitPK, form);
    }

}
