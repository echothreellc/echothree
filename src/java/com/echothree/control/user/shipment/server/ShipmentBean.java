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

package com.echothree.control.user.shipment.server;

import com.echothree.control.user.shipment.common.ShipmentRemote;
import com.echothree.control.user.shipment.common.form.*;
import com.echothree.control.user.shipment.common.result.*;
import com.echothree.control.user.shipment.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<?> createFreeOnBoard(UserVisitPK userVisitPK, CreateFreeOnBoardForm form) {
        return CDI.current().select(CreateFreeOnBoardCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetFreeOnBoardsResult> getFreeOnBoards(UserVisitPK userVisitPK, GetFreeOnBoardsForm form) {
        return CDI.current().select(GetFreeOnBoardsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetFreeOnBoardResult> getFreeOnBoard(UserVisitPK userVisitPK, GetFreeOnBoardForm form) {
        return CDI.current().select(GetFreeOnBoardCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetFreeOnBoardChoicesResult> getFreeOnBoardChoices(UserVisitPK userVisitPK, GetFreeOnBoardChoicesForm form) {
        return CDI.current().select(GetFreeOnBoardChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultFreeOnBoard(UserVisitPK userVisitPK, SetDefaultFreeOnBoardForm form) {
        return CDI.current().select(SetDefaultFreeOnBoardCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditFreeOnBoardResult> editFreeOnBoard(UserVisitPK userVisitPK, EditFreeOnBoardForm form) {
        return CDI.current().select(EditFreeOnBoardCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteFreeOnBoard(UserVisitPK userVisitPK, DeleteFreeOnBoardForm form) {
        return CDI.current().select(DeleteFreeOnBoardCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Free On Board Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createFreeOnBoardDescription(UserVisitPK userVisitPK, CreateFreeOnBoardDescriptionForm form) {
        return CDI.current().select(CreateFreeOnBoardDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetFreeOnBoardDescriptionsResult> getFreeOnBoardDescriptions(UserVisitPK userVisitPK, GetFreeOnBoardDescriptionsForm form) {
        return CDI.current().select(GetFreeOnBoardDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditFreeOnBoardDescriptionResult> editFreeOnBoardDescription(UserVisitPK userVisitPK, EditFreeOnBoardDescriptionForm form) {
        return CDI.current().select(EditFreeOnBoardDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteFreeOnBoardDescription(UserVisitPK userVisitPK, DeleteFreeOnBoardDescriptionForm form) {
        return CDI.current().select(DeleteFreeOnBoardDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentType(UserVisitPK userVisitPK, CreateShipmentTypeForm form) {
        return CDI.current().select(CreateShipmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTypeChoicesResult> getShipmentTypeChoices(UserVisitPK userVisitPK, GetShipmentTypeChoicesForm form) {
        return CDI.current().select(GetShipmentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTypeResult> getShipmentType(UserVisitPK userVisitPK, GetShipmentTypeForm form) {
        return CDI.current().select(GetShipmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTypesResult> getShipmentTypes(UserVisitPK userVisitPK, GetShipmentTypesForm form) {
        return CDI.current().select(GetShipmentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultShipmentType(UserVisitPK userVisitPK, SetDefaultShipmentTypeForm form) {
        return CDI.current().select(SetDefaultShipmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentTypeResult> editShipmentType(UserVisitPK userVisitPK, EditShipmentTypeForm form) {
        return CDI.current().select(EditShipmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentType(UserVisitPK userVisitPK, DeleteShipmentTypeForm form) {
        return CDI.current().select(DeleteShipmentTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentTypeDescription(UserVisitPK userVisitPK, CreateShipmentTypeDescriptionForm form) {
        return CDI.current().select(CreateShipmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTypeDescriptionResult> getShipmentTypeDescription(UserVisitPK userVisitPK, GetShipmentTypeDescriptionForm form) {
        return CDI.current().select(GetShipmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTypeDescriptionsResult> getShipmentTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTypeDescriptionsForm form) {
        return CDI.current().select(GetShipmentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentTypeDescriptionResult> editShipmentTypeDescription(UserVisitPK userVisitPK, EditShipmentTypeDescriptionForm form) {
        return CDI.current().select(EditShipmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTypeDescriptionForm form) {
        return CDI.current().select(DeleteShipmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentTimeType(UserVisitPK userVisitPK, CreateShipmentTimeTypeForm form) {
        return CDI.current().select(CreateShipmentTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTimeTypeChoicesResult> getShipmentTimeTypeChoices(UserVisitPK userVisitPK, GetShipmentTimeTypeChoicesForm form) {
        return CDI.current().select(GetShipmentTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTimeTypeResult> getShipmentTimeType(UserVisitPK userVisitPK, GetShipmentTimeTypeForm form) {
        return CDI.current().select(GetShipmentTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTimeTypesResult> getShipmentTimeTypes(UserVisitPK userVisitPK, GetShipmentTimeTypesForm form) {
        return CDI.current().select(GetShipmentTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultShipmentTimeType(UserVisitPK userVisitPK, SetDefaultShipmentTimeTypeForm form) {
        return CDI.current().select(SetDefaultShipmentTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentTimeTypeResult> editShipmentTimeType(UserVisitPK userVisitPK, EditShipmentTimeTypeForm form) {
        return CDI.current().select(EditShipmentTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentTimeType(UserVisitPK userVisitPK, DeleteShipmentTimeTypeForm form) {
        return CDI.current().select(DeleteShipmentTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentTimeTypeDescription(UserVisitPK userVisitPK, CreateShipmentTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateShipmentTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTimeTypeDescriptionResult> getShipmentTimeTypeDescription(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionForm form) {
        return CDI.current().select(GetShipmentTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentTimeTypeDescriptionsResult> getShipmentTimeTypeDescriptions(UserVisitPK userVisitPK, GetShipmentTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetShipmentTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentTimeTypeDescriptionResult> editShipmentTimeTypeDescription(UserVisitPK userVisitPK, EditShipmentTimeTypeDescriptionForm form) {
        return CDI.current().select(EditShipmentTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentTimeTypeDescription(UserVisitPK userVisitPK, DeleteShipmentTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteShipmentTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Shipment Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createShipmentTypeShippingMethod(UserVisitPK userVisitPK, CreateShipmentTypeShippingMethodForm form) {
        return CDI.current().select(CreateShipmentTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetShipmentTypeShippingMethodsResult> getShipmentTypeShippingMethods(UserVisitPK userVisitPK, GetShipmentTypeShippingMethodsForm form) {
        return CDI.current().select(GetShipmentTypeShippingMethodsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultShipmentTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultShipmentTypeShippingMethodForm form) {
        return CDI.current().select(SetDefaultShipmentTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditShipmentTypeShippingMethodResult> editShipmentTypeShippingMethod(UserVisitPK userVisitPK, EditShipmentTypeShippingMethodForm form) {
        return CDI.current().select(EditShipmentTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteShipmentTypeShippingMethod(UserVisitPK userVisitPK, DeleteShipmentTypeShippingMethodForm form) {
        return CDI.current().select(DeleteShipmentTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentAliasType(UserVisitPK userVisitPK, CreateShipmentAliasTypeForm form) {
        return CDI.current().select(CreateShipmentAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasTypeChoicesResult> getShipmentAliasTypeChoices(UserVisitPK userVisitPK, GetShipmentAliasTypeChoicesForm form) {
        return CDI.current().select(GetShipmentAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasTypeResult> getShipmentAliasType(UserVisitPK userVisitPK, GetShipmentAliasTypeForm form) {
        return CDI.current().select(GetShipmentAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasTypesResult> getShipmentAliasTypes(UserVisitPK userVisitPK, GetShipmentAliasTypesForm form) {
        return CDI.current().select(GetShipmentAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultShipmentAliasType(UserVisitPK userVisitPK, SetDefaultShipmentAliasTypeForm form) {
        return CDI.current().select(SetDefaultShipmentAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentAliasTypeResult> editShipmentAliasType(UserVisitPK userVisitPK, EditShipmentAliasTypeForm form) {
        return CDI.current().select(EditShipmentAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentAliasType(UserVisitPK userVisitPK, DeleteShipmentAliasTypeForm form) {
        return CDI.current().select(DeleteShipmentAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentAliasTypeDescription(UserVisitPK userVisitPK, CreateShipmentAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateShipmentAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasTypeDescriptionResult> getShipmentAliasTypeDescription(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionForm form) {
        return CDI.current().select(GetShipmentAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasTypeDescriptionsResult> getShipmentAliasTypeDescriptions(UserVisitPK userVisitPK, GetShipmentAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetShipmentAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentAliasTypeDescriptionResult> editShipmentAliasTypeDescription(UserVisitPK userVisitPK, EditShipmentAliasTypeDescriptionForm form) {
        return CDI.current().select(EditShipmentAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentAliasTypeDescription(UserVisitPK userVisitPK, DeleteShipmentAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteShipmentAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createShipmentAlias(UserVisitPK userVisitPK, CreateShipmentAliasForm form) {
        return CDI.current().select(CreateShipmentAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasResult> getShipmentAlias(UserVisitPK userVisitPK, GetShipmentAliasForm form) {
        return CDI.current().select(GetShipmentAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetShipmentAliasesResult> getShipmentAliases(UserVisitPK userVisitPK, GetShipmentAliasesForm form) {
        return CDI.current().select(GetShipmentAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditShipmentAliasResult> editShipmentAlias(UserVisitPK userVisitPK, EditShipmentAliasForm form) {
        return CDI.current().select(EditShipmentAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteShipmentAlias(UserVisitPK userVisitPK, DeleteShipmentAliasForm form) {
        return CDI.current().select(DeleteShipmentAliasCommand.class).get().run(userVisitPK, form);
    }

}
