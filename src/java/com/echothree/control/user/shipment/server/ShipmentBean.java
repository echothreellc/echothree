// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
        return new CreateFreeOnBoardCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFreeOnBoards(UserVisitPK userVisitPK, GetFreeOnBoardsForm form) {
        return new GetFreeOnBoardsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFreeOnBoard(UserVisitPK userVisitPK, GetFreeOnBoardForm form) {
        return new GetFreeOnBoardCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFreeOnBoardChoices(UserVisitPK userVisitPK, GetFreeOnBoardChoicesForm form) {
        return new GetFreeOnBoardChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultFreeOnBoard(UserVisitPK userVisitPK, SetDefaultFreeOnBoardForm form) {
        return new SetDefaultFreeOnBoardCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editFreeOnBoard(UserVisitPK userVisitPK, EditFreeOnBoardForm form) {
        return new EditFreeOnBoardCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteFreeOnBoard(UserVisitPK userVisitPK, DeleteFreeOnBoardForm form) {
        return new DeleteFreeOnBoardCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Free On Board Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFreeOnBoardDescription(UserVisitPK userVisitPK, CreateFreeOnBoardDescriptionForm form) {
        return new CreateFreeOnBoardDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFreeOnBoardDescriptions(UserVisitPK userVisitPK, GetFreeOnBoardDescriptionsForm form) {
        return new GetFreeOnBoardDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editFreeOnBoardDescription(UserVisitPK userVisitPK, EditFreeOnBoardDescriptionForm form) {
        return new EditFreeOnBoardDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteFreeOnBoardDescription(UserVisitPK userVisitPK, DeleteFreeOnBoardDescriptionForm form) {
        return new DeleteFreeOnBoardDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentType(UserVisitPK userVisitPK, CreateShipmentTypeForm form) {
        return new CreateShipmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTypeChoices(UserVisitPK userVisitPK, GetShipmentTypeChoicesForm form) {
        return new GetShipmentTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentType(UserVisitPK userVisitPK, GetShipmentTypeForm form) {
        return new GetShipmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTypes(UserVisitPK userVisitPK, GetShipmentTypesForm form) {
        return new GetShipmentTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultShipmentType(UserVisitPK userVisitPK, SetDefaultShipmentTypeForm form) {
        return new SetDefaultShipmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentType(UserVisitPK userVisitPK, EditShipmentTypeForm form) {
        return new EditShipmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentType(UserVisitPK userVisitPK, DeleteShipmentTypeForm form) {
        return new DeleteShipmentTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Shipment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentTypeDescription(UserVisitPK userVisitPK, CreateShipmentTypeDescriptionForm form) {
        return new CreateShipmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTypeDescription(UserVisitPK userVisitPK, GetShipmentTypeDescriptionForm form) {
        return new GetShipmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTypeDescriptionsForm form) {
        return new GetShipmentTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentTypeDescription(UserVisitPK userVisitPK, EditShipmentTypeDescriptionForm form) {
        return new EditShipmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTypeDescriptionForm form) {
        return new DeleteShipmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentTimeType(UserVisitPK userVisitPK, CreateShipmentTimeTypeForm form) {
        return new CreateShipmentTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTimeTypeChoices(UserVisitPK userVisitPK, GetShipmentTimeTypeChoicesForm form) {
        return new GetShipmentTimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTimeType(UserVisitPK userVisitPK, GetShipmentTimeTypeForm form) {
        return new GetShipmentTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTimeTypes(UserVisitPK userVisitPK, GetShipmentTimeTypesForm form) {
        return new GetShipmentTimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultShipmentTimeType(UserVisitPK userVisitPK, SetDefaultShipmentTimeTypeForm form) {
        return new SetDefaultShipmentTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentTimeType(UserVisitPK userVisitPK, EditShipmentTimeTypeForm form) {
        return new EditShipmentTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentTimeType(UserVisitPK userVisitPK, DeleteShipmentTimeTypeForm form) {
        return new DeleteShipmentTimeTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentTimeTypeDescription(UserVisitPK userVisitPK, CreateShipmentTimeTypeDescriptionForm form) {
        return new CreateShipmentTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTimeTypeDescription(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionForm form) {
        return new GetShipmentTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentTimeTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionsForm form) {
        return new GetShipmentTimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentTimeTypeDescription(UserVisitPK userVisitPK, EditShipmentTimeTypeDescriptionForm form) {
        return new EditShipmentTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentTimeTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTimeTypeDescriptionForm form) {
        return new DeleteShipmentTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Shipment Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShipmentTypeShippingMethod(UserVisitPK userVisitPK, CreateShipmentTypeShippingMethodForm form) {
        return new CreateShipmentTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getShipmentTypeShippingMethods(UserVisitPK userVisitPK, GetShipmentTypeShippingMethodsForm form) {
        return new GetShipmentTypeShippingMethodsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultShipmentTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultShipmentTypeShippingMethodForm form) {
        return new SetDefaultShipmentTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editShipmentTypeShippingMethod(UserVisitPK userVisitPK, EditShipmentTypeShippingMethodForm form) {
        return new EditShipmentTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteShipmentTypeShippingMethod(UserVisitPK userVisitPK, DeleteShipmentTypeShippingMethodForm form) {
        return new DeleteShipmentTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentAliasType(UserVisitPK userVisitPK, CreateShipmentAliasTypeForm form) {
        return new CreateShipmentAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAliasTypeChoices(UserVisitPK userVisitPK, GetShipmentAliasTypeChoicesForm form) {
        return new GetShipmentAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAliasType(UserVisitPK userVisitPK, GetShipmentAliasTypeForm form) {
        return new GetShipmentAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAliasTypes(UserVisitPK userVisitPK, GetShipmentAliasTypesForm form) {
        return new GetShipmentAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultShipmentAliasType(UserVisitPK userVisitPK, SetDefaultShipmentAliasTypeForm form) {
        return new SetDefaultShipmentAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentAliasType(UserVisitPK userVisitPK, EditShipmentAliasTypeForm form) {
        return new EditShipmentAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentAliasType(UserVisitPK userVisitPK, DeleteShipmentAliasTypeForm form) {
        return new DeleteShipmentAliasTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Shipment Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentAliasTypeDescription(UserVisitPK userVisitPK, CreateShipmentAliasTypeDescriptionForm form) {
        return new CreateShipmentAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAliasTypeDescription(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionForm form) {
        return new GetShipmentAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAliasTypeDescriptions(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionsForm form) {
        return new GetShipmentAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentAliasTypeDescription(UserVisitPK userVisitPK, EditShipmentAliasTypeDescriptionForm form) {
        return new EditShipmentAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentAliasTypeDescription(UserVisitPK userVisitPK, DeleteShipmentAliasTypeDescriptionForm form) {
        return new DeleteShipmentAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Shipment Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createShipmentAlias(UserVisitPK userVisitPK, CreateShipmentAliasForm form) {
        return new CreateShipmentAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAlias(UserVisitPK userVisitPK, GetShipmentAliasForm form) {
        return new GetShipmentAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getShipmentAliases(UserVisitPK userVisitPK, GetShipmentAliasesForm form) {
        return new GetShipmentAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editShipmentAlias(UserVisitPK userVisitPK, EditShipmentAliasForm form) {
        return new EditShipmentAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteShipmentAlias(UserVisitPK userVisitPK, DeleteShipmentAliasForm form) {
        return new DeleteShipmentAliasCommand(userVisitPK, form).run();
    }

}
