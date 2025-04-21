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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateCacheEntryForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.CacheEntryControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCacheEntryCommand
        extends BaseSimpleCommand<CreateCacheEntryForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CacheEntryKey", FieldType.STRING, true, 1L, 200L),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("ValidForTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ValidForTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Clob", FieldType.STRING, false, 1L, null)
                ));
    }
    
    /** Creates a new instance of CreateCacheEntryCommand */
    public CreateCacheEntryCommand(UserVisitPK userVisitPK, CreateCacheEntryForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var cacheEntryControl = Session.getModelController(CacheEntryControl.class);
        var cacheEntryKey = form.getCacheEntryKey();
        var cacheEntry = cacheEntryControl.getCacheEntryByCacheEntryKey(cacheEntryKey);

        if(cacheEntry == null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var mimeTypeName = form.getMimeTypeName();
            var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

            if(mimeType != null) {
                var validForTime = UnitOfMeasureTypeLogic.getInstance().checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                        form.getValidForTime(), form.getValidForTimeUnitOfMeasureTypeName(),
                        null, ExecutionErrors.MissingRequiredValidForTime.name(), null, ExecutionErrors.MissingRequiredValidForTimeUnitOfMeasureTypeName.name(),
                        null, ExecutionErrors.UnknownValidForTimeUnitOfMeasureTypeName.name());

                if(!hasExecutionErrors()) {
                    var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
                    var entityRefs = form.getEntityRefs();

                    try {
                        if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                            var clob = form.getClob();

                            if(clob != null) {
                                cacheEntryControl.createCacheEntry(cacheEntryKey, mimeType, session.START_TIME_LONG,
                                        validForTime == null ? null : session.START_TIME + validForTime, clob, null, entityRefs);
                            } else {
                                addExecutionError(ExecutionErrors.MissingClob.name());
                            }
                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                            var blob = form.getBlob();

                            if(blob != null) {
                                cacheEntryControl.createCacheEntry(cacheEntryKey, mimeType, session.START_TIME_LONG,
                                        validForTime == null ? null : session.START_TIME + validForTime, null, blob, entityRefs);
                            } else {
                                addExecutionError(ExecutionErrors.MissingBlob.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidMimeType.name(), mimeTypeName);
                        }
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            // Duplicate key in index, add this as a regular error, and continue. Tested w/ MySQL only.
                            addExecutionError(ExecutionErrors.DuplicateCacheEntryKey.name(), cacheEntryKey);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateCacheEntryKey.name(), cacheEntryKey);
        }
        
        return null;
    }
    
}
