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

package com.echothree.control.user.uom.server;

import com.echothree.control.user.uom.common.UomRemote;
import com.echothree.control.user.uom.common.form.*;
import com.echothree.control.user.uom.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKinds(UserVisitPK userVisitPK, GetUnitOfMeasureKindsForm form) {
        return CDI.current().select(GetUnitOfMeasureKindsCommand.class).get().run(userVisitPK, form);
    }
    
    
    @Override
    public CommandResult getUnitOfMeasureKind(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form) {
        return CDI.current().select(GetUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureKind(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindForm form) {
        return CDI.current().select(SetDefaultUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureKind(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form) {
        return CDI.current().select(EditUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKind(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindForm form) {
        return CDI.current().select(DeleteUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindDescriptionForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureKindDescriptionsForm form) {
        return CDI.current().select(GetUnitOfMeasureKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureKindDescription(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form) {
        return CDI.current().select(EditUnitOfMeasureKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKindDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindDescriptionForm form) {
        return CDI.current().select(DeleteUnitOfMeasureKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureType(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypes(UserVisitPK userVisitPK, GetUnitOfMeasureTypesForm form) {
        return CDI.current().select(GetUnitOfMeasureTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureType(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form) {
        return CDI.current().select(GetUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureChoices(UserVisitPK userVisitPK, GetUnitOfMeasureChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureTypeForm form) {
        return CDI.current().select(SetDefaultUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureType(UserVisitPK userVisitPK, EditUnitOfMeasureTypeForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureType(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureTypeDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form) {
        return CDI.current().select(GetUnitOfMeasureTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, EditUnitOfMeasureTypeDescriptionForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeDescriptionForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, EditUnitOfMeasureTypeVolumeForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeVolumeForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeWeightForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeWeightCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureEquivalent(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form) {
        return CDI.current().select(CreateUnitOfMeasureEquivalentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureEquivalents(UserVisitPK userVisitPK, GetUnitOfMeasureEquivalentsForm form) {
        return CDI.current().select(GetUnitOfMeasureEquivalentsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureEquivalent(UserVisitPK userVisitPK, EditUnitOfMeasureEquivalentForm form) {
        return CDI.current().select(EditUnitOfMeasureEquivalentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureEquivalent(UserVisitPK userVisitPK, DeleteUnitOfMeasureEquivalentForm form) {
        return CDI.current().select(DeleteUnitOfMeasureEquivalentCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUseType(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseTypes(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUseType(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUseTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeDescriptionForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUnitOfMeasureKindUse(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUses(UserVisitPK userVisitPK, GetUnitOfMeasureKindUsesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUnitOfMeasureKindUse(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUnitOfMeasureKindUse(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindUseForm form) {
        return CDI.current().select(SetDefaultUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUnitOfMeasureKindUse(UserVisitPK userVisitPK, EditUnitOfMeasureKindUseForm form) {
        return CDI.current().select(EditUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUnitOfMeasureKindUse(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindUseForm form) {
        return CDI.current().select(DeleteUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
}
