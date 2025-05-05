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

package com.echothree.control.user.index.server;

import com.echothree.control.user.index.common.IndexRemote;
import com.echothree.control.user.index.common.form.*;
import com.echothree.control.user.index.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class IndexBean
        extends IndexFormsImpl
        implements IndexRemote, IndexLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "IndexBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Index Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexType(UserVisitPK userVisitPK, CreateIndexTypeForm form) {
        return new CreateIndexTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypeChoices(UserVisitPK userVisitPK, GetIndexTypeChoicesForm form) {
        return new GetIndexTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexType(UserVisitPK userVisitPK, GetIndexTypeForm form) {
        return new GetIndexTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypes(UserVisitPK userVisitPK, GetIndexTypesForm form) {
        return new GetIndexTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultIndexType(UserVisitPK userVisitPK, SetDefaultIndexTypeForm form) {
        return new SetDefaultIndexTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexType(UserVisitPK userVisitPK, EditIndexTypeForm form) {
        return new EditIndexTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexType(UserVisitPK userVisitPK, DeleteIndexTypeForm form) {
        return new DeleteIndexTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexTypeDescription(UserVisitPK userVisitPK, CreateIndexTypeDescriptionForm form) {
        return new CreateIndexTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypeDescription(UserVisitPK userVisitPK, GetIndexTypeDescriptionForm form) {
        return new GetIndexTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypeDescriptions(UserVisitPK userVisitPK, GetIndexTypeDescriptionsForm form) {
        return new GetIndexTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexTypeDescription(UserVisitPK userVisitPK, EditIndexTypeDescriptionForm form) {
        return new EditIndexTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexTypeDescription(UserVisitPK userVisitPK, DeleteIndexTypeDescriptionForm form) {
        return new DeleteIndexTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Fields
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexField(UserVisitPK userVisitPK, CreateIndexFieldForm form) {
        return new CreateIndexFieldCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFieldChoices(UserVisitPK userVisitPK, GetIndexFieldChoicesForm form) {
        return new GetIndexFieldChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexField(UserVisitPK userVisitPK, GetIndexFieldForm form) {
        return new GetIndexFieldCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFields(UserVisitPK userVisitPK, GetIndexFieldsForm form) {
        return new GetIndexFieldsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultIndexField(UserVisitPK userVisitPK, SetDefaultIndexFieldForm form) {
        return new SetDefaultIndexFieldCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexField(UserVisitPK userVisitPK, EditIndexFieldForm form) {
        return new EditIndexFieldCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexField(UserVisitPK userVisitPK, DeleteIndexFieldForm form) {
        return new DeleteIndexFieldCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Field Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexFieldDescription(UserVisitPK userVisitPK, CreateIndexFieldDescriptionForm form) {
        return new CreateIndexFieldDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFieldDescription(UserVisitPK userVisitPK, GetIndexFieldDescriptionForm form) {
        return new GetIndexFieldDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFieldDescriptions(UserVisitPK userVisitPK, GetIndexFieldDescriptionsForm form) {
        return new GetIndexFieldDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexFieldDescription(UserVisitPK userVisitPK, EditIndexFieldDescriptionForm form) {
        return new EditIndexFieldDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexFieldDescription(UserVisitPK userVisitPK, DeleteIndexFieldDescriptionForm form) {
        return new DeleteIndexFieldDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndex(UserVisitPK userVisitPK, CreateIndexForm form) {
        return new CreateIndexCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexChoices(UserVisitPK userVisitPK, GetIndexChoicesForm form) {
        return new GetIndexChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndex(UserVisitPK userVisitPK, GetIndexForm form) {
        return new GetIndexCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexes(UserVisitPK userVisitPK, GetIndexesForm form) {
        return new GetIndexesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultIndex(UserVisitPK userVisitPK, SetDefaultIndexForm form) {
        return new SetDefaultIndexCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndex(UserVisitPK userVisitPK, EditIndexForm form) {
        return new EditIndexCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndex(UserVisitPK userVisitPK, DeleteIndexForm form) {
        return new DeleteIndexCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexDescription(UserVisitPK userVisitPK, CreateIndexDescriptionForm form) {
        return new CreateIndexDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexDescription(UserVisitPK userVisitPK, GetIndexDescriptionForm form) {
        return new GetIndexDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexDescriptions(UserVisitPK userVisitPK, GetIndexDescriptionsForm form) {
        return new GetIndexDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexDescription(UserVisitPK userVisitPK, EditIndexDescriptionForm form) {
        return new EditIndexDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexDescription(UserVisitPK userVisitPK, DeleteIndexDescriptionForm form) {
        return new DeleteIndexDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult updateIndexes(UserVisitPK userVisitPK, UpdateIndexesForm form) {
        return new UpdateIndexesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult forceReindex(UserVisitPK userVisitPK, ForceReindexForm form) {
        return new ForceReindexCommand().run(userVisitPK, form);
    }
    
}
