// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.index.remote.IndexRemote;
import com.echothree.control.user.index.remote.form.*;
import com.echothree.control.user.index.server.command.*;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
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
        return new CreateIndexTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexTypeChoices(UserVisitPK userVisitPK, GetIndexTypeChoicesForm form) {
        return new GetIndexTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexType(UserVisitPK userVisitPK, GetIndexTypeForm form) {
        return new GetIndexTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexTypes(UserVisitPK userVisitPK, GetIndexTypesForm form) {
        return new GetIndexTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultIndexType(UserVisitPK userVisitPK, SetDefaultIndexTypeForm form) {
        return new SetDefaultIndexTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editIndexType(UserVisitPK userVisitPK, EditIndexTypeForm form) {
        return new EditIndexTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteIndexType(UserVisitPK userVisitPK, DeleteIndexTypeForm form) {
        return new DeleteIndexTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexTypeDescription(UserVisitPK userVisitPK, CreateIndexTypeDescriptionForm form) {
        return new CreateIndexTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexTypeDescription(UserVisitPK userVisitPK, GetIndexTypeDescriptionForm form) {
        return new GetIndexTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexTypeDescriptions(UserVisitPK userVisitPK, GetIndexTypeDescriptionsForm form) {
        return new GetIndexTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editIndexTypeDescription(UserVisitPK userVisitPK, EditIndexTypeDescriptionForm form) {
        return new EditIndexTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteIndexTypeDescription(UserVisitPK userVisitPK, DeleteIndexTypeDescriptionForm form) {
        return new DeleteIndexTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Fields
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexField(UserVisitPK userVisitPK, CreateIndexFieldForm form) {
        return new CreateIndexFieldCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexFieldChoices(UserVisitPK userVisitPK, GetIndexFieldChoicesForm form) {
        return new GetIndexFieldChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexField(UserVisitPK userVisitPK, GetIndexFieldForm form) {
        return new GetIndexFieldCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexFields(UserVisitPK userVisitPK, GetIndexFieldsForm form) {
        return new GetIndexFieldsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultIndexField(UserVisitPK userVisitPK, SetDefaultIndexFieldForm form) {
        return new SetDefaultIndexFieldCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editIndexField(UserVisitPK userVisitPK, EditIndexFieldForm form) {
        return new EditIndexFieldCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteIndexField(UserVisitPK userVisitPK, DeleteIndexFieldForm form) {
        return new DeleteIndexFieldCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Field Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexFieldDescription(UserVisitPK userVisitPK, CreateIndexFieldDescriptionForm form) {
        return new CreateIndexFieldDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexFieldDescription(UserVisitPK userVisitPK, GetIndexFieldDescriptionForm form) {
        return new GetIndexFieldDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexFieldDescriptions(UserVisitPK userVisitPK, GetIndexFieldDescriptionsForm form) {
        return new GetIndexFieldDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editIndexFieldDescription(UserVisitPK userVisitPK, EditIndexFieldDescriptionForm form) {
        return new EditIndexFieldDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteIndexFieldDescription(UserVisitPK userVisitPK, DeleteIndexFieldDescriptionForm form) {
        return new DeleteIndexFieldDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndex(UserVisitPK userVisitPK, CreateIndexForm form) {
        return new CreateIndexCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexChoices(UserVisitPK userVisitPK, GetIndexChoicesForm form) {
        return new GetIndexChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndex(UserVisitPK userVisitPK, GetIndexForm form) {
        return new GetIndexCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexes(UserVisitPK userVisitPK, GetIndexesForm form) {
        return new GetIndexesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultIndex(UserVisitPK userVisitPK, SetDefaultIndexForm form) {
        return new SetDefaultIndexCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editIndex(UserVisitPK userVisitPK, EditIndexForm form) {
        return new EditIndexCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteIndex(UserVisitPK userVisitPK, DeleteIndexForm form) {
        return new DeleteIndexCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexDescription(UserVisitPK userVisitPK, CreateIndexDescriptionForm form) {
        return new CreateIndexDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexDescription(UserVisitPK userVisitPK, GetIndexDescriptionForm form) {
        return new GetIndexDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getIndexDescriptions(UserVisitPK userVisitPK, GetIndexDescriptionsForm form) {
        return new GetIndexDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editIndexDescription(UserVisitPK userVisitPK, EditIndexDescriptionForm form) {
        return new EditIndexDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteIndexDescription(UserVisitPK userVisitPK, DeleteIndexDescriptionForm form) {
        return new DeleteIndexDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult updateIndexes(UserVisitPK userVisitPK) {
        return new UpdateIndexesCommand(userVisitPK).run();
    }
    
    @Override
    public CommandResult forceReindex(UserVisitPK userVisitPK, ForceReindexForm form) {
        return new ForceReindexCommand(userVisitPK, form).run();
    }
    
}
