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
        return new CreateUnitOfMeasureKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKinds(UserVisitPK userVisitPK, GetUnitOfMeasureKindsForm form) {
        return new GetUnitOfMeasureKindsCommand().run(userVisitPK, form);
    }
    
    
    @Override
    public CommandResult getUnitOfMeasureKind(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form) {
        return new GetUnitOfMeasureKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form) {
        return new GetUnitOfMeasureKindChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureKind(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindForm form) {
        return new SetDefaultUnitOfMeasureKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureKind(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form) {
        return new EditUnitOfMeasureKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKind(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindForm form) {
        return new DeleteUnitOfMeasureKindCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindDescriptionForm form) {
        return new CreateUnitOfMeasureKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureKindDescriptionsForm form) {
        return new GetUnitOfMeasureKindDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureKindDescription(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form) {
        return new EditUnitOfMeasureKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKindDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindDescriptionForm form) {
        return new DeleteUnitOfMeasureKindDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureType(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeForm form) {
        return new CreateUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypes(UserVisitPK userVisitPK, GetUnitOfMeasureTypesForm form) {
        return new GetUnitOfMeasureTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureType(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form) {
        return new GetUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form) {
        return new GetUnitOfMeasureTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureChoices(UserVisitPK userVisitPK, GetUnitOfMeasureChoicesForm form) {
        return new GetUnitOfMeasureChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureTypeForm form) {
        return new SetDefaultUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureType(UserVisitPK userVisitPK, EditUnitOfMeasureTypeForm form) {
        return new EditUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureType(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeForm form) {
        return new DeleteUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form) {
        return new CreateUnitOfMeasureTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypeDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form) {
        return new GetUnitOfMeasureTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, EditUnitOfMeasureTypeDescriptionForm form) {
        return new EditUnitOfMeasureTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeDescriptionForm form) {
        return new DeleteUnitOfMeasureTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form) {
        return new CreateUnitOfMeasureTypeVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, EditUnitOfMeasureTypeVolumeForm form) {
        return new EditUnitOfMeasureTypeVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeVolumeForm form) {
        return new DeleteUnitOfMeasureTypeVolumeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form) {
        return new CreateUnitOfMeasureTypeWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form) {
        return new EditUnitOfMeasureTypeWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeWeightForm form) {
        return new DeleteUnitOfMeasureTypeWeightCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureEquivalent(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form) {
        return new CreateUnitOfMeasureEquivalentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureEquivalents(UserVisitPK userVisitPK, GetUnitOfMeasureEquivalentsForm form) {
        return new GetUnitOfMeasureEquivalentsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureEquivalent(UserVisitPK userVisitPK, EditUnitOfMeasureEquivalentForm form) {
        return new EditUnitOfMeasureEquivalentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureEquivalent(UserVisitPK userVisitPK, DeleteUnitOfMeasureEquivalentForm form) {
        return new DeleteUnitOfMeasureEquivalentCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUseType(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeForm form) {
        return new CreateUnitOfMeasureKindUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeChoicesForm form) {
        return new GetUnitOfMeasureKindUseTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseTypes(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form) {
        return new GetUnitOfMeasureKindUseTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseType(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeForm form) {
        return new GetUnitOfMeasureKindUseTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUseTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeDescriptionForm form) {
        return new CreateUnitOfMeasureKindUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUse(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseForm form) {
        return new CreateUnitOfMeasureKindUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUses(UserVisitPK userVisitPK, GetUnitOfMeasureKindUsesForm form) {
        return new GetUnitOfMeasureKindUsesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUse(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseForm form) {
        return new GetUnitOfMeasureKindUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureKindUse(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindUseForm form) {
        return new SetDefaultUnitOfMeasureKindUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureKindUse(UserVisitPK userVisitPK, EditUnitOfMeasureKindUseForm form) {
        return new EditUnitOfMeasureKindUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKindUse(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindUseForm form) {
        return new DeleteUnitOfMeasureKindUseCommand().run(userVisitPK, form);
    }
    
}
