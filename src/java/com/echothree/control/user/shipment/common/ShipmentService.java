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
import com.echothree.control.user.shipment.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface ShipmentService
        extends ShipmentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();

    // -------------------------------------------------------------------------
    //   Free On Boards
    // -------------------------------------------------------------------------

    CommandResult<CreateFreeOnBoardResult> createFreeOnBoard(UserVisitPK userVisitPK, CreateFreeOnBoardForm form);

    CommandResult<GetFreeOnBoardsResult> getFreeOnBoards(UserVisitPK userVisitPK, GetFreeOnBoardsForm form);

    CommandResult<GetFreeOnBoardResult> getFreeOnBoard(UserVisitPK userVisitPK, GetFreeOnBoardForm form);

    CommandResult<GetFreeOnBoardChoicesResult> getFreeOnBoardChoices(UserVisitPK userVisitPK, GetFreeOnBoardChoicesForm form);

    CommandResult<VoidResult> setDefaultFreeOnBoard(UserVisitPK userVisitPK, SetDefaultFreeOnBoardForm form);

    CommandResult<EditFreeOnBoardResult> editFreeOnBoard(UserVisitPK userVisitPK, EditFreeOnBoardForm form);

    CommandResult<VoidResult> deleteFreeOnBoard(UserVisitPK userVisitPK, DeleteFreeOnBoardForm form);

    // -------------------------------------------------------------------------
    //   Free On Board Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createFreeOnBoardDescription(UserVisitPK userVisitPK, CreateFreeOnBoardDescriptionForm form);

    CommandResult<GetFreeOnBoardDescriptionsResult> getFreeOnBoardDescriptions(UserVisitPK userVisitPK, GetFreeOnBoardDescriptionsForm form);

    CommandResult<EditFreeOnBoardDescriptionResult> editFreeOnBoardDescription(UserVisitPK userVisitPK, EditFreeOnBoardDescriptionForm form);

    CommandResult<VoidResult> deleteFreeOnBoardDescription(UserVisitPK userVisitPK, DeleteFreeOnBoardDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentType(UserVisitPK userVisitPK, CreateShipmentTypeForm form);

    CommandResult<GetShipmentTypeChoicesResult> getShipmentTypeChoices(UserVisitPK userVisitPK, GetShipmentTypeChoicesForm form);

    CommandResult<GetShipmentTypeResult> getShipmentType(UserVisitPK userVisitPK, GetShipmentTypeForm form);

    CommandResult<GetShipmentTypesResult> getShipmentTypes(UserVisitPK userVisitPK, GetShipmentTypesForm form);

    CommandResult<VoidResult> setDefaultShipmentType(UserVisitPK userVisitPK, SetDefaultShipmentTypeForm form);

    CommandResult<EditShipmentTypeResult> editShipmentType(UserVisitPK userVisitPK, EditShipmentTypeForm form);

    CommandResult<VoidResult> deleteShipmentType(UserVisitPK userVisitPK, DeleteShipmentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentTypeDescription(UserVisitPK userVisitPK, CreateShipmentTypeDescriptionForm form);

    CommandResult<GetShipmentTypeDescriptionResult> getShipmentTypeDescription(UserVisitPK userVisitPK, GetShipmentTypeDescriptionForm form);

    CommandResult<GetShipmentTypeDescriptionsResult> getShipmentTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTypeDescriptionsForm form);

    CommandResult<EditShipmentTypeDescriptionResult> editShipmentTypeDescription(UserVisitPK userVisitPK, EditShipmentTypeDescriptionForm form);

    CommandResult<VoidResult> deleteShipmentTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Time Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentTimeType(UserVisitPK userVisitPK, CreateShipmentTimeTypeForm form);

    CommandResult<GetShipmentTimeTypeChoicesResult> getShipmentTimeTypeChoices(UserVisitPK userVisitPK, GetShipmentTimeTypeChoicesForm form);

    CommandResult<GetShipmentTimeTypeResult> getShipmentTimeType(UserVisitPK userVisitPK, GetShipmentTimeTypeForm form);

    CommandResult<GetShipmentTimeTypesResult> getShipmentTimeTypes(UserVisitPK userVisitPK, GetShipmentTimeTypesForm form);

    CommandResult<VoidResult> setDefaultShipmentTimeType(UserVisitPK userVisitPK, SetDefaultShipmentTimeTypeForm form);

    CommandResult<EditShipmentTimeTypeResult> editShipmentTimeType(UserVisitPK userVisitPK, EditShipmentTimeTypeForm form);

    CommandResult<VoidResult> deleteShipmentTimeType(UserVisitPK userVisitPK, DeleteShipmentTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentTimeTypeDescription(UserVisitPK userVisitPK, CreateShipmentTimeTypeDescriptionForm form);

    CommandResult<GetShipmentTimeTypeDescriptionResult> getShipmentTimeTypeDescription(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionForm form);

    CommandResult<GetShipmentTimeTypeDescriptionsResult> getShipmentTimeTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionsForm form);

    CommandResult<EditShipmentTimeTypeDescriptionResult> editShipmentTimeTypeDescription(UserVisitPK userVisitPK, EditShipmentTimeTypeDescriptionForm form);

    CommandResult<VoidResult> deleteShipmentTimeTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTimeTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Shipment Type Shipping Methods
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentTypeShippingMethod(UserVisitPK userVisitPK, CreateShipmentTypeShippingMethodForm form);

    CommandResult<GetShipmentTypeShippingMethodsResult> getShipmentTypeShippingMethods(UserVisitPK userVisitPK, GetShipmentTypeShippingMethodsForm form);

    CommandResult<VoidResult> setDefaultShipmentTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultShipmentTypeShippingMethodForm form);

    CommandResult<EditShipmentTypeShippingMethodResult> editShipmentTypeShippingMethod(UserVisitPK userVisitPK, EditShipmentTypeShippingMethodForm form);

    CommandResult<VoidResult> deleteShipmentTypeShippingMethod(UserVisitPK userVisitPK, DeleteShipmentTypeShippingMethodForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentAliasType(UserVisitPK userVisitPK, CreateShipmentAliasTypeForm form);

    CommandResult<GetShipmentAliasTypeChoicesResult> getShipmentAliasTypeChoices(UserVisitPK userVisitPK, GetShipmentAliasTypeChoicesForm form);

    CommandResult<GetShipmentAliasTypeResult> getShipmentAliasType(UserVisitPK userVisitPK, GetShipmentAliasTypeForm form);

    CommandResult<GetShipmentAliasTypesResult> getShipmentAliasTypes(UserVisitPK userVisitPK, GetShipmentAliasTypesForm form);

    CommandResult<VoidResult> setDefaultShipmentAliasType(UserVisitPK userVisitPK, SetDefaultShipmentAliasTypeForm form);

    CommandResult<EditShipmentAliasTypeResult> editShipmentAliasType(UserVisitPK userVisitPK, EditShipmentAliasTypeForm form);

    CommandResult<VoidResult> deleteShipmentAliasType(UserVisitPK userVisitPK, DeleteShipmentAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentAliasTypeDescription(UserVisitPK userVisitPK, CreateShipmentAliasTypeDescriptionForm form);

    CommandResult<GetShipmentAliasTypeDescriptionResult> getShipmentAliasTypeDescription(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionForm form);

    CommandResult<GetShipmentAliasTypeDescriptionsResult> getShipmentAliasTypeDescriptions(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionsForm form);

    CommandResult<EditShipmentAliasTypeDescriptionResult> editShipmentAliasTypeDescription(UserVisitPK userVisitPK, EditShipmentAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deleteShipmentAliasTypeDescription(UserVisitPK userVisitPK, DeleteShipmentAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Shipment Aliases
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createShipmentAlias(UserVisitPK userVisitPK, CreateShipmentAliasForm form);

    CommandResult<GetShipmentAliasResult> getShipmentAlias(UserVisitPK userVisitPK, GetShipmentAliasForm form);

    CommandResult<GetShipmentAliasesResult> getShipmentAliases(UserVisitPK userVisitPK, GetShipmentAliasesForm form);

    CommandResult<EditShipmentAliasResult> editShipmentAlias(UserVisitPK userVisitPK, EditShipmentAliasForm form);

    CommandResult<VoidResult> deleteShipmentAlias(UserVisitPK userVisitPK, DeleteShipmentAliasForm form);

}
