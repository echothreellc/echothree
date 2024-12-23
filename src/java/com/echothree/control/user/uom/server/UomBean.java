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

package com.echothree.control.user.uom.server;

import com.echothree.control.user.uom.common.UomRemote;
import com.echothree.control.user.uom.common.form.*;
import com.echothree.control.user.uom.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class UomBean
        extends UomFormsImpl
        implements UomRemote, UomLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "UomBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kinds
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKind(UserVisitPK userVisitPK, CreateUnitOfMeasureKindForm form) {
        return new CreateUnitOfMeasureKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKinds(UserVisitPK userVisitPK, GetUnitOfMeasureKindsForm form) {
        return new GetUnitOfMeasureKindsCommand(userVisitPK, form).run();
    }
    
    
    @Override
    public CommandResult getUnitOfMeasureKind(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form) {
        return new GetUnitOfMeasureKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form) {
        return new GetUnitOfMeasureKindChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureKind(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindForm form) {
        return new SetDefaultUnitOfMeasureKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureKind(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form) {
        return new EditUnitOfMeasureKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKind(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindForm form) {
        return new DeleteUnitOfMeasureKindCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindDescriptionForm form) {
        return new CreateUnitOfMeasureKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureKindDescriptionsForm form) {
        return new GetUnitOfMeasureKindDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureKindDescription(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form) {
        return new EditUnitOfMeasureKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKindDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindDescriptionForm form) {
        return new DeleteUnitOfMeasureKindDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureType(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeForm form) {
        return new CreateUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypes(UserVisitPK userVisitPK, GetUnitOfMeasureTypesForm form) {
        return new GetUnitOfMeasureTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureType(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form) {
        return new GetUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form) {
        return new GetUnitOfMeasureTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureChoices(UserVisitPK userVisitPK, GetUnitOfMeasureChoicesForm form) {
        return new GetUnitOfMeasureChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureTypeForm form) {
        return new SetDefaultUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureType(UserVisitPK userVisitPK, EditUnitOfMeasureTypeForm form) {
        return new EditUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureType(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeForm form) {
        return new DeleteUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form) {
        return new CreateUnitOfMeasureTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypeDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form) {
        return new GetUnitOfMeasureTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, EditUnitOfMeasureTypeDescriptionForm form) {
        return new EditUnitOfMeasureTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeDescriptionForm form) {
        return new DeleteUnitOfMeasureTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form) {
        return new CreateUnitOfMeasureTypeVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, EditUnitOfMeasureTypeVolumeForm form) {
        return new EditUnitOfMeasureTypeVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeVolumeForm form) {
        return new DeleteUnitOfMeasureTypeVolumeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form) {
        return new CreateUnitOfMeasureTypeWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form) {
        return new EditUnitOfMeasureTypeWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeWeightForm form) {
        return new DeleteUnitOfMeasureTypeWeightCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureEquivalent(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form) {
        return new CreateUnitOfMeasureEquivalentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureEquivalents(UserVisitPK userVisitPK, GetUnitOfMeasureEquivalentsForm form) {
        return new GetUnitOfMeasureEquivalentsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureEquivalent(UserVisitPK userVisitPK, EditUnitOfMeasureEquivalentForm form) {
        return new EditUnitOfMeasureEquivalentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureEquivalent(UserVisitPK userVisitPK, DeleteUnitOfMeasureEquivalentForm form) {
        return new DeleteUnitOfMeasureEquivalentCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUseType(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeForm form) {
        return new CreateUnitOfMeasureKindUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeChoicesForm form) {
        return new GetUnitOfMeasureKindUseTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseTypes(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form) {
        return new GetUnitOfMeasureKindUseTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseType(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeForm form) {
        return new GetUnitOfMeasureKindUseTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUseTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeDescriptionForm form) {
        return new CreateUnitOfMeasureKindUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUse(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseForm form) {
        return new CreateUnitOfMeasureKindUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUses(UserVisitPK userVisitPK, GetUnitOfMeasureKindUsesForm form) {
        return new GetUnitOfMeasureKindUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUse(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseForm form) {
        return new GetUnitOfMeasureKindUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureKindUse(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindUseForm form) {
        return new SetDefaultUnitOfMeasureKindUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUnitOfMeasureKindUse(UserVisitPK userVisitPK, EditUnitOfMeasureKindUseForm form) {
        return new EditUnitOfMeasureKindUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKindUse(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindUseForm form) {
        return new DeleteUnitOfMeasureKindUseCommand(userVisitPK, form).run();
    }
    
}
