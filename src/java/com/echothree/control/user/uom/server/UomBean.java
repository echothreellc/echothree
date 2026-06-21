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
import com.echothree.control.user.uom.common.result.*;
import com.echothree.control.user.uom.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
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
    public CommandResult<VoidResult> createUnitOfMeasureKind(UserVisitPK userVisitPK, CreateUnitOfMeasureKindForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindsResult> getUnitOfMeasureKinds(UserVisitPK userVisitPK, GetUnitOfMeasureKindsForm form) {
        return CDI.current().select(GetUnitOfMeasureKindsCommand.class).get().run(userVisitPK, form);
    }
    
    
    @Override
    public CommandResult<GetUnitOfMeasureKindResult> getUnitOfMeasureKind(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form) {
        return CDI.current().select(GetUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindChoicesResult> getUnitOfMeasureKindChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultUnitOfMeasureKind(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindForm form) {
        return CDI.current().select(SetDefaultUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureKindResult> editUnitOfMeasureKind(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form) {
        return CDI.current().select(EditUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureKind(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindForm form) {
        return CDI.current().select(DeleteUnitOfMeasureKindCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureKindDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindDescriptionForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindDescriptionsResult> getUnitOfMeasureKindDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureKindDescriptionsForm form) {
        return CDI.current().select(GetUnitOfMeasureKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureKindDescriptionResult> editUnitOfMeasureKindDescription(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form) {
        return CDI.current().select(EditUnitOfMeasureKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureKindDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindDescriptionForm form) {
        return CDI.current().select(DeleteUnitOfMeasureKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureType(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureTypesResult> getUnitOfMeasureTypes(UserVisitPK userVisitPK, GetUnitOfMeasureTypesForm form) {
        return CDI.current().select(GetUnitOfMeasureTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureTypeResult> getUnitOfMeasureType(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form) {
        return CDI.current().select(GetUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureTypeChoicesResult> getUnitOfMeasureTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureChoicesResult> getUnitOfMeasureChoices(UserVisitPK userVisitPK, GetUnitOfMeasureChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureTypeForm form) {
        return CDI.current().select(SetDefaultUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureTypeResult> editUnitOfMeasureType(UserVisitPK userVisitPK, EditUnitOfMeasureTypeForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureType(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureTypeDescriptionsResult> getUnitOfMeasureTypeDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form) {
        return CDI.current().select(GetUnitOfMeasureTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureTypeDescriptionResult> editUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, EditUnitOfMeasureTypeDescriptionForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeDescriptionForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureTypeVolumeResult> editUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, EditUnitOfMeasureTypeVolumeForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeVolumeForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form) {
        return CDI.current().select(CreateUnitOfMeasureTypeWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureTypeWeightResult> editUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form) {
        return CDI.current().select(EditUnitOfMeasureTypeWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeWeightForm form) {
        return CDI.current().select(DeleteUnitOfMeasureTypeWeightCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureEquivalent(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form) {
        return CDI.current().select(CreateUnitOfMeasureEquivalentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureEquivalentsResult> getUnitOfMeasureEquivalents(UserVisitPK userVisitPK, GetUnitOfMeasureEquivalentsForm form) {
        return CDI.current().select(GetUnitOfMeasureEquivalentsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureEquivalentResult> editUnitOfMeasureEquivalent(UserVisitPK userVisitPK, EditUnitOfMeasureEquivalentForm form) {
        return CDI.current().select(EditUnitOfMeasureEquivalentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureEquivalent(UserVisitPK userVisitPK, DeleteUnitOfMeasureEquivalentForm form) {
        return CDI.current().select(DeleteUnitOfMeasureEquivalentCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureKindUseType(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindUseTypeChoicesResult> getUnitOfMeasureKindUseTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeChoicesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindUseTypesResult> getUnitOfMeasureKindUseTypes(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindUseTypeResult> getUnitOfMeasureKindUseType(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureKindUseTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeDescriptionForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createUnitOfMeasureKindUse(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseForm form) {
        return CDI.current().select(CreateUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindUsesResult> getUnitOfMeasureKindUses(UserVisitPK userVisitPK, GetUnitOfMeasureKindUsesForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetUnitOfMeasureKindUseResult> getUnitOfMeasureKindUse(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseForm form) {
        return CDI.current().select(GetUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultUnitOfMeasureKindUse(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindUseForm form) {
        return CDI.current().select(SetDefaultUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditUnitOfMeasureKindUseResult> editUnitOfMeasureKindUse(UserVisitPK userVisitPK, EditUnitOfMeasureKindUseForm form) {
        return CDI.current().select(EditUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteUnitOfMeasureKindUse(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindUseForm form) {
        return CDI.current().select(DeleteUnitOfMeasureKindUseCommand.class).get().run(userVisitPK, form);
    }
    
}
