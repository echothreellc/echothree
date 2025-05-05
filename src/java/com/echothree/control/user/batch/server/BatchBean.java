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

package com.echothree.control.user.batch.server;

import com.echothree.control.user.batch.common.BatchRemote;
import com.echothree.control.user.batch.common.form.*;
import com.echothree.control.user.batch.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class BatchBean
        extends BatchFormsImpl
        implements BatchRemote, BatchLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "BatchBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBatchType(UserVisitPK userVisitPK, CreateBatchTypeForm form) {
        return new CreateBatchTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBatchTypeChoices(UserVisitPK userVisitPK, GetBatchTypeChoicesForm form) {
        return new GetBatchTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBatchType(UserVisitPK userVisitPK, GetBatchTypeForm form) {
        return new GetBatchTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBatchTypes(UserVisitPK userVisitPK, GetBatchTypesForm form) {
        return new GetBatchTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultBatchType(UserVisitPK userVisitPK, SetDefaultBatchTypeForm form) {
        return new SetDefaultBatchTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editBatchType(UserVisitPK userVisitPK, EditBatchTypeForm form) {
        return new EditBatchTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteBatchType(UserVisitPK userVisitPK, DeleteBatchTypeForm form) {
        return new DeleteBatchTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchTypeDescription(UserVisitPK userVisitPK, CreateBatchTypeDescriptionForm form) {
        return new CreateBatchTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeDescription(UserVisitPK userVisitPK, GetBatchTypeDescriptionForm form) {
        return new GetBatchTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeDescriptions(UserVisitPK userVisitPK, GetBatchTypeDescriptionsForm form) {
        return new GetBatchTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchTypeDescription(UserVisitPK userVisitPK, EditBatchTypeDescriptionForm form) {
        return new EditBatchTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchTypeDescription(UserVisitPK userVisitPK, DeleteBatchTypeDescriptionForm form) {
        return new DeleteBatchTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchTypeEntityType(UserVisitPK userVisitPK, CreateBatchTypeEntityTypeForm form) {
        return new CreateBatchTypeEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeEntityType(UserVisitPK userVisitPK, GetBatchTypeEntityTypeForm form) {
        return new GetBatchTypeEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeEntityTypes(UserVisitPK userVisitPK, GetBatchTypeEntityTypesForm form) {
        return new GetBatchTypeEntityTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchTypeEntityType(UserVisitPK userVisitPK, DeleteBatchTypeEntityTypeForm form) {
        return new DeleteBatchTypeEntityTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAliasType(UserVisitPK userVisitPK, CreateBatchAliasTypeForm form) {
        return new CreateBatchAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypeChoices(UserVisitPK userVisitPK, GetBatchAliasTypeChoicesForm form) {
        return new GetBatchAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasType(UserVisitPK userVisitPK, GetBatchAliasTypeForm form) {
        return new GetBatchAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypes(UserVisitPK userVisitPK, GetBatchAliasTypesForm form) {
        return new GetBatchAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultBatchAliasType(UserVisitPK userVisitPK, SetDefaultBatchAliasTypeForm form) {
        return new SetDefaultBatchAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchAliasType(UserVisitPK userVisitPK, EditBatchAliasTypeForm form) {
        return new EditBatchAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchAliasType(UserVisitPK userVisitPK, DeleteBatchAliasTypeForm form) {
        return new DeleteBatchAliasTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAliasTypeDescription(UserVisitPK userVisitPK, CreateBatchAliasTypeDescriptionForm form) {
        return new CreateBatchAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypeDescription(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionForm form) {
        return new GetBatchAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypeDescriptions(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionsForm form) {
        return new GetBatchAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchAliasTypeDescription(UserVisitPK userVisitPK, EditBatchAliasTypeDescriptionForm form) {
        return new EditBatchAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchAliasTypeDescription(UserVisitPK userVisitPK, DeleteBatchAliasTypeDescriptionForm form) {
        return new DeleteBatchAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAlias(UserVisitPK userVisitPK, CreateBatchAliasForm form) {
        return new CreateBatchAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAlias(UserVisitPK userVisitPK, GetBatchAliasForm form) {
        return new GetBatchAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliases(UserVisitPK userVisitPK, GetBatchAliasesForm form) {
        return new GetBatchAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchAlias(UserVisitPK userVisitPK, EditBatchAliasForm form) {
        return new EditBatchAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchAlias(UserVisitPK userVisitPK, DeleteBatchAliasForm form) {
        return new DeleteBatchAliasCommand().run(userVisitPK, form);
    }

}
