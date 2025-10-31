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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreatePicklistTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypeChoices(UserVisitPK userVisitPK, GetPicklistTypeChoicesForm form) {
        return CDI.current().select(GetPicklistTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistType(UserVisitPK userVisitPK, GetPicklistTypeForm form) {
        return CDI.current().select(GetPicklistTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypes(UserVisitPK userVisitPK, GetPicklistTypesForm form) {
        return CDI.current().select(GetPicklistTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPicklistType(UserVisitPK userVisitPK, SetDefaultPicklistTypeForm form) {
        return CDI.current().select(SetDefaultPicklistTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistType(UserVisitPK userVisitPK, EditPicklistTypeForm form) {
        return CDI.current().select(EditPicklistTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistType(UserVisitPK userVisitPK, DeletePicklistTypeForm form) {
        return CDI.current().select(DeletePicklistTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTypeDescription(UserVisitPK userVisitPK, CreatePicklistTypeDescriptionForm form) {
        return CDI.current().select(CreatePicklistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypeDescription(UserVisitPK userVisitPK, GetPicklistTypeDescriptionForm form) {
        return CDI.current().select(GetPicklistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTypeDescriptionsForm form) {
        return CDI.current().select(GetPicklistTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistTypeDescription(UserVisitPK userVisitPK, EditPicklistTypeDescriptionForm form) {
        return CDI.current().select(EditPicklistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistTypeDescription(UserVisitPK userVisitPK, DeletePicklistTypeDescriptionForm form) {
        return CDI.current().select(DeletePicklistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTimeType(UserVisitPK userVisitPK, CreatePicklistTimeTypeForm form) {
        return CDI.current().select(CreatePicklistTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypeChoices(UserVisitPK userVisitPK, GetPicklistTimeTypeChoicesForm form) {
        return CDI.current().select(GetPicklistTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeType(UserVisitPK userVisitPK, GetPicklistTimeTypeForm form) {
        return CDI.current().select(GetPicklistTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypes(UserVisitPK userVisitPK, GetPicklistTimeTypesForm form) {
        return CDI.current().select(GetPicklistTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPicklistTimeType(UserVisitPK userVisitPK, SetDefaultPicklistTimeTypeForm form) {
        return CDI.current().select(SetDefaultPicklistTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistTimeType(UserVisitPK userVisitPK, EditPicklistTimeTypeForm form) {
        return CDI.current().select(EditPicklistTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistTimeType(UserVisitPK userVisitPK, DeletePicklistTimeTypeForm form) {
        return CDI.current().select(DeletePicklistTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistTimeTypeDescription(UserVisitPK userVisitPK, CreatePicklistTimeTypeDescriptionForm form) {
        return CDI.current().select(CreatePicklistTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypeDescription(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionForm form) {
        return CDI.current().select(GetPicklistTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistTimeTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetPicklistTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistTimeTypeDescription(UserVisitPK userVisitPK, EditPicklistTimeTypeDescriptionForm form) {
        return CDI.current().select(EditPicklistTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistTimeTypeDescription(UserVisitPK userVisitPK, DeletePicklistTimeTypeDescriptionForm form) {
        return CDI.current().select(DeletePicklistTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAliasType(UserVisitPK userVisitPK, CreatePicklistAliasTypeForm form) {
        return CDI.current().select(CreatePicklistAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypeChoices(UserVisitPK userVisitPK, GetPicklistAliasTypeChoicesForm form) {
        return CDI.current().select(GetPicklistAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasType(UserVisitPK userVisitPK, GetPicklistAliasTypeForm form) {
        return CDI.current().select(GetPicklistAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypes(UserVisitPK userVisitPK, GetPicklistAliasTypesForm form) {
        return CDI.current().select(GetPicklistAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPicklistAliasType(UserVisitPK userVisitPK, SetDefaultPicklistAliasTypeForm form) {
        return CDI.current().select(SetDefaultPicklistAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistAliasType(UserVisitPK userVisitPK, EditPicklistAliasTypeForm form) {
        return CDI.current().select(EditPicklistAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistAliasType(UserVisitPK userVisitPK, DeletePicklistAliasTypeForm form) {
        return CDI.current().select(DeletePicklistAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAliasTypeDescription(UserVisitPK userVisitPK, CreatePicklistAliasTypeDescriptionForm form) {
        return CDI.current().select(CreatePicklistAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypeDescription(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionForm form) {
        return CDI.current().select(GetPicklistAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliasTypeDescriptions(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetPicklistAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistAliasTypeDescription(UserVisitPK userVisitPK, EditPicklistAliasTypeDescriptionForm form) {
        return CDI.current().select(EditPicklistAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistAliasTypeDescription(UserVisitPK userVisitPK, DeletePicklistAliasTypeDescriptionForm form) {
        return CDI.current().select(DeletePicklistAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPicklistAlias(UserVisitPK userVisitPK, CreatePicklistAliasForm form) {
        return CDI.current().select(CreatePicklistAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAlias(UserVisitPK userVisitPK, GetPicklistAliasForm form) {
        return CDI.current().select(GetPicklistAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPicklistAliases(UserVisitPK userVisitPK, GetPicklistAliasesForm form) {
        return CDI.current().select(GetPicklistAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPicklistAlias(UserVisitPK userVisitPK, EditPicklistAliasForm form) {
        return CDI.current().select(EditPicklistAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePicklistAlias(UserVisitPK userVisitPK, DeletePicklistAliasForm form) {
        return CDI.current().select(DeletePicklistAliasCommand.class).get().run(userVisitPK, form);
    }

}
