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

package com.echothree.control.user.shipment.server;

import com.echothree.control.user.shipment.common.ShipmentRemote;
import com.echothree.control.user.shipment.common.form.*;
import com.echothree.control.user.shipment.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ShipmentBean
        extends ShipmentFormsImpl
        implements ShipmentRemote, ShipmentLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ShipmentBean is alive!";
    }

    // -------------------------------------------------------------------------
    //   Free On Boards
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFreeOnBoard(UserVisitPK userVisitPK, CreateFreeOnBoardForm form) {
        return new CreateFreeOnBoardCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFreeOnBoards(UserVisitPK userVisitPK, GetFreeOnBoardsForm form) {
        return new GetFreeOnBoardsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFreeOnBoard(UserVisitPK userVisitPK, GetFreeOnBoardForm form) {
        return new GetFreeOnBoardCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFreeOnBoardChoices(UserVisitPK userVisitPK, GetFreeOnBoardChoicesForm form) {
        return new GetFreeOnBoardChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultFreeOnBoard(UserVisitPK userVisitPK, SetDefaultFreeOnBoardForm form) {
        return new SetDefaultFreeOnBoardCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFreeOnBoard(UserVisitPK userVisitPK, EditFreeOnBoardForm form) {
        return new EditFreeOnBoardCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFreeOnBoard(UserVisitPK userVisitPK, DeleteFreeOnBoardForm form) {
        return new DeleteFreeOnBoardCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Free On Board Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFreeOnBoardDescription(UserVisitPK userVisitPK, CreateFreeOnBoardDescriptionForm form) {
        return new CreateFreeOnBoardDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFreeOnBoardDescriptions(UserVisitPK userVisitPK, GetFreeOnBoardDescriptionsForm form) {
        return new GetFreeOnBoardDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFreeOnBoardDescription(UserVisitPK userVisitPK, EditFreeOnBoardDescriptionForm form) {
        return new EditFreeOnBoardDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFreeOnBoardDescription(UserVisitPK userVisitPK, DeleteFreeOnBoardDescriptionForm form) {
        return new DeleteFreeOnBoardDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentType(UserVisitPK userVisitPK, CreateShipmentTypeForm form) {
        return new CreateShipmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTypeChoices(UserVisitPK userVisitPK, GetShipmentTypeChoicesForm form) {
        return new GetShipmentTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentType(UserVisitPK userVisitPK, GetShipmentTypeForm form) {
        return new GetShipmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTypes(UserVisitPK userVisitPK, GetShipmentTypesForm form) {
        return new GetShipmentTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultShipmentType(UserVisitPK userVisitPK, SetDefaultShipmentTypeForm form) {
        return new SetDefaultShipmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentType(UserVisitPK userVisitPK, EditShipmentTypeForm form) {
        return new EditShipmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentType(UserVisitPK userVisitPK, DeleteShipmentTypeForm form) {
        return new DeleteShipmentTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentTypeDescription(UserVisitPK userVisitPK, CreateShipmentTypeDescriptionForm form) {
        return new CreateShipmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTypeDescription(UserVisitPK userVisitPK, GetShipmentTypeDescriptionForm form) {
        return new GetShipmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTypeDescriptionsForm form) {
        return new GetShipmentTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentTypeDescription(UserVisitPK userVisitPK, EditShipmentTypeDescriptionForm form) {
        return new EditShipmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTypeDescriptionForm form) {
        return new DeleteShipmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentTimeType(UserVisitPK userVisitPK, CreateShipmentTimeTypeForm form) {
        return new CreateShipmentTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTimeTypeChoices(UserVisitPK userVisitPK, GetShipmentTimeTypeChoicesForm form) {
        return new GetShipmentTimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTimeType(UserVisitPK userVisitPK, GetShipmentTimeTypeForm form) {
        return new GetShipmentTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTimeTypes(UserVisitPK userVisitPK, GetShipmentTimeTypesForm form) {
        return new GetShipmentTimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultShipmentTimeType(UserVisitPK userVisitPK, SetDefaultShipmentTimeTypeForm form) {
        return new SetDefaultShipmentTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentTimeType(UserVisitPK userVisitPK, EditShipmentTimeTypeForm form) {
        return new EditShipmentTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentTimeType(UserVisitPK userVisitPK, DeleteShipmentTimeTypeForm form) {
        return new DeleteShipmentTimeTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentTimeTypeDescription(UserVisitPK userVisitPK, CreateShipmentTimeTypeDescriptionForm form) {
        return new CreateShipmentTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTimeTypeDescription(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionForm form) {
        return new GetShipmentTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentTimeTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionsForm form) {
        return new GetShipmentTimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentTimeTypeDescription(UserVisitPK userVisitPK, EditShipmentTimeTypeDescriptionForm form) {
        return new EditShipmentTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentTimeTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTimeTypeDescriptionForm form) {
        return new DeleteShipmentTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Shipment Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShipmentTypeShippingMethod(UserVisitPK userVisitPK, CreateShipmentTypeShippingMethodForm form) {
        return new CreateShipmentTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShipmentTypeShippingMethods(UserVisitPK userVisitPK, GetShipmentTypeShippingMethodsForm form) {
        return new GetShipmentTypeShippingMethodsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultShipmentTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultShipmentTypeShippingMethodForm form) {
        return new SetDefaultShipmentTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editShipmentTypeShippingMethod(UserVisitPK userVisitPK, EditShipmentTypeShippingMethodForm form) {
        return new EditShipmentTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteShipmentTypeShippingMethod(UserVisitPK userVisitPK, DeleteShipmentTypeShippingMethodForm form) {
        return new DeleteShipmentTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentAliasType(UserVisitPK userVisitPK, CreateShipmentAliasTypeForm form) {
        return new CreateShipmentAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAliasTypeChoices(UserVisitPK userVisitPK, GetShipmentAliasTypeChoicesForm form) {
        return new GetShipmentAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAliasType(UserVisitPK userVisitPK, GetShipmentAliasTypeForm form) {
        return new GetShipmentAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAliasTypes(UserVisitPK userVisitPK, GetShipmentAliasTypesForm form) {
        return new GetShipmentAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultShipmentAliasType(UserVisitPK userVisitPK, SetDefaultShipmentAliasTypeForm form) {
        return new SetDefaultShipmentAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentAliasType(UserVisitPK userVisitPK, EditShipmentAliasTypeForm form) {
        return new EditShipmentAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentAliasType(UserVisitPK userVisitPK, DeleteShipmentAliasTypeForm form) {
        return new DeleteShipmentAliasTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentAliasTypeDescription(UserVisitPK userVisitPK, CreateShipmentAliasTypeDescriptionForm form) {
        return new CreateShipmentAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAliasTypeDescription(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionForm form) {
        return new GetShipmentAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAliasTypeDescriptions(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionsForm form) {
        return new GetShipmentAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentAliasTypeDescription(UserVisitPK userVisitPK, EditShipmentAliasTypeDescriptionForm form) {
        return new EditShipmentAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentAliasTypeDescription(UserVisitPK userVisitPK, DeleteShipmentAliasTypeDescriptionForm form) {
        return new DeleteShipmentAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentAlias(UserVisitPK userVisitPK, CreateShipmentAliasForm form) {
        return new CreateShipmentAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAlias(UserVisitPK userVisitPK, GetShipmentAliasForm form) {
        return new GetShipmentAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShipmentAliases(UserVisitPK userVisitPK, GetShipmentAliasesForm form) {
        return new GetShipmentAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShipmentAlias(UserVisitPK userVisitPK, EditShipmentAliasForm form) {
        return new EditShipmentAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShipmentAlias(UserVisitPK userVisitPK, DeleteShipmentAliasForm form) {
        return new DeleteShipmentAliasCommand().run(userVisitPK, form);
    }

}
