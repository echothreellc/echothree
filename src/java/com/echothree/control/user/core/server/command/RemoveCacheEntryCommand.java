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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.form.RemoveCacheEntryForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveCacheEntryCommand
        extends BaseSimpleCommand<RemoveCacheEntryForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CacheEntryKey", FieldType.STRING, false, 1L, 200L)
                ));
    }
    
    /** Creates a new instance of RemoveCacheEntryCommand */
    public RemoveCacheEntryCommand(UserVisitPK userVisitPK, RemoveCacheEntryForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        String cacheEntryKey = form.getCacheEntryKey();
        
        if(cacheEntryKey == null) {
            coreControl.removeCacheEntries();
        } else {
            CacheEntry cacheEntry = coreControl.getCacheEntryByCacheEntryKeyForUpdate(cacheEntryKey);

            if(cacheEntry != null) {
                coreControl.removeCacheEntry(cacheEntry);
            } else {
                addExecutionError(ExecutionErrors.UnknownCacheEntryKey.name(), cacheEntryKey);
            }
        }
        
        return null;
    }
    
}
