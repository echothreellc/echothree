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

package com.echothree.control.user.shipment.common;

import com.echothree.control.user.shipment.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ShipmentService
        extends ShipmentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();

    // -------------------------------------------------------------------------
    //   Free On Boards
    // -------------------------------------------------------------------------

    CommandResult createFreeOnBoard(UserVisitPK userVisitPK, CreateFreeOnBoardForm form);

    CommandResult getFreeOnBoards(UserVisitPK userVisitPK, GetFreeOnBoardsForm form);

    CommandResult getFreeOnBoard(UserVisitPK userVisitPK, GetFreeOnBoardForm form);

    CommandResult getFreeOnBoardChoices(UserVisitPK userVisitPK, GetFreeOnBoardChoicesForm form);

    CommandResult setDefaultFreeOnBoard(UserVisitPK userVisitPK, SetDefaultFreeOnBoardForm form);

    CommandResult editFreeOnBoard(UserVisitPK userVisitPK, EditFreeOnBoardForm form);

    CommandResult deleteFreeOnBoard(UserVisitPK userVisitPK, DeleteFreeOnBoardForm form);

    // -------------------------------------------------------------------------
    //   Free On Board Descriptions
    // -------------------------------------------------------------------------

    CommandResult createFreeOnBoardDescription(UserVisitPK userVisitPK, CreateFreeOnBoardDescriptionForm form);

    CommandResult getFreeOnBoardDescriptions(UserVisitPK userVisitPK, GetFreeOnBoardDescriptionsForm form);

    CommandResult editFreeOnBoardDescription(UserVisitPK userVisitPK, EditFreeOnBoardDescriptionForm form);

    CommandResult deleteFreeOnBoardDescription(UserVisitPK userVisitPK, DeleteFreeOnBoardDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

    CommandResult createShipmentType(UserVisitPK userVisitPK, CreateShipmentTypeForm form);

    CommandResult getShipmentTypeChoices(UserVisitPK userVisitPK, GetShipmentTypeChoicesForm form);

    CommandResult getShipmentType(UserVisitPK userVisitPK, GetShipmentTypeForm form);

    CommandResult getShipmentTypes(UserVisitPK userVisitPK, GetShipmentTypesForm form);

    CommandResult setDefaultShipmentType(UserVisitPK userVisitPK, SetDefaultShipmentTypeForm form);

    CommandResult editShipmentType(UserVisitPK userVisitPK, EditShipmentTypeForm form);

    CommandResult deleteShipmentType(UserVisitPK userVisitPK, DeleteShipmentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createShipmentTypeDescription(UserVisitPK userVisitPK, CreateShipmentTypeDescriptionForm form);

    CommandResult getShipmentTypeDescription(UserVisitPK userVisitPK, GetShipmentTypeDescriptionForm form);

    CommandResult getShipmentTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTypeDescriptionsForm form);

    CommandResult editShipmentTypeDescription(UserVisitPK userVisitPK, EditShipmentTypeDescriptionForm form);

    CommandResult deleteShipmentTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Time Types
    // --------------------------------------------------------------------------------

    CommandResult createShipmentTimeType(UserVisitPK userVisitPK, CreateShipmentTimeTypeForm form);

    CommandResult getShipmentTimeTypeChoices(UserVisitPK userVisitPK, GetShipmentTimeTypeChoicesForm form);

    CommandResult getShipmentTimeType(UserVisitPK userVisitPK, GetShipmentTimeTypeForm form);

    CommandResult getShipmentTimeTypes(UserVisitPK userVisitPK, GetShipmentTimeTypesForm form);

    CommandResult setDefaultShipmentTimeType(UserVisitPK userVisitPK, SetDefaultShipmentTimeTypeForm form);

    CommandResult editShipmentTimeType(UserVisitPK userVisitPK, EditShipmentTimeTypeForm form);

    CommandResult deleteShipmentTimeType(UserVisitPK userVisitPK, DeleteShipmentTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createShipmentTimeTypeDescription(UserVisitPK userVisitPK, CreateShipmentTimeTypeDescriptionForm form);

    CommandResult getShipmentTimeTypeDescription(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionForm form);

    CommandResult getShipmentTimeTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionsForm form);

    CommandResult editShipmentTimeTypeDescription(UserVisitPK userVisitPK, EditShipmentTimeTypeDescriptionForm form);

    CommandResult deleteShipmentTimeTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTimeTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Shipment Type Shipping Methods
    // -------------------------------------------------------------------------

    CommandResult createShipmentTypeShippingMethod(UserVisitPK userVisitPK, CreateShipmentTypeShippingMethodForm form);

    CommandResult getShipmentTypeShippingMethods(UserVisitPK userVisitPK, GetShipmentTypeShippingMethodsForm form);

    CommandResult setDefaultShipmentTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultShipmentTypeShippingMethodForm form);

    CommandResult editShipmentTypeShippingMethod(UserVisitPK userVisitPK, EditShipmentTypeShippingMethodForm form);

    CommandResult deleteShipmentTypeShippingMethod(UserVisitPK userVisitPK, DeleteShipmentTypeShippingMethodForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Alias Types
    // --------------------------------------------------------------------------------

    CommandResult createShipmentAliasType(UserVisitPK userVisitPK, CreateShipmentAliasTypeForm form);

    CommandResult getShipmentAliasTypeChoices(UserVisitPK userVisitPK, GetShipmentAliasTypeChoicesForm form);

    CommandResult getShipmentAliasType(UserVisitPK userVisitPK, GetShipmentAliasTypeForm form);

    CommandResult getShipmentAliasTypes(UserVisitPK userVisitPK, GetShipmentAliasTypesForm form);

    CommandResult setDefaultShipmentAliasType(UserVisitPK userVisitPK, SetDefaultShipmentAliasTypeForm form);

    CommandResult editShipmentAliasType(UserVisitPK userVisitPK, EditShipmentAliasTypeForm form);

    CommandResult deleteShipmentAliasType(UserVisitPK userVisitPK, DeleteShipmentAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createShipmentAliasTypeDescription(UserVisitPK userVisitPK, CreateShipmentAliasTypeDescriptionForm form);

    CommandResult getShipmentAliasTypeDescription(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionForm form);

    CommandResult getShipmentAliasTypeDescriptions(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionsForm form);

    CommandResult editShipmentAliasTypeDescription(UserVisitPK userVisitPK, EditShipmentAliasTypeDescriptionForm form);

    CommandResult deleteShipmentAliasTypeDescription(UserVisitPK userVisitPK, DeleteShipmentAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Aliases
    // --------------------------------------------------------------------------------

    CommandResult createShipmentAlias(UserVisitPK userVisitPK, CreateShipmentAliasForm form);

    CommandResult getShipmentAlias(UserVisitPK userVisitPK, GetShipmentAliasForm form);

    CommandResult getShipmentAliases(UserVisitPK userVisitPK, GetShipmentAliasesForm form);

    CommandResult editShipmentAlias(UserVisitPK userVisitPK, EditShipmentAliasForm form);

    CommandResult deleteShipmentAlias(UserVisitPK userVisitPK, DeleteShipmentAliasForm form);

}
