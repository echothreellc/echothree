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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.UnknownMimeTypeNameException;
import com.echothree.model.control.core.common.exception.UnknownMimeTypeUsageException;
import com.echothree.model.control.core.common.exception.UnknownMimeTypeUsageTypeNameException;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class MimeTypeLogic
        extends BaseLogic {

    protected MimeTypeLogic() {
        super();
    }

    public static MimeTypeLogic getInstance() {
        return CDI.current().select(MimeTypeLogic.class).get();
    }
    
    public MimeType getMimeTypeByName(final ExecutionErrorAccumulator eea, final String mimeTypeName) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

        if(mimeType == null) {
            handleExecutionError(UnknownMimeTypeNameException.class, eea, ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }

        return mimeType;
    }
    
    public MimeTypeUsageType getMimeTypeUsageTypeByName(final ExecutionErrorAccumulator eea, final String mimeTypeUsageTypeName) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);

        if(mimeTypeUsageType == null) {
            handleExecutionError(UnknownMimeTypeUsageTypeNameException.class, eea, ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
        }

        return mimeTypeUsageType;
    }
    
    public MimeTypeUsage getMimeTypeUsage(final ExecutionErrorAccumulator eea, final MimeType mimeType, final MimeTypeUsageType mimeTypeUsageType) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeUsage = mimeTypeControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);

        if(mimeTypeUsage == null) {
            handleExecutionError(UnknownMimeTypeUsageException.class, eea, ExecutionErrors.UnknownMimeTypeUsage.name(), mimeType.getLastDetail().getMimeTypeName(),
                    mimeTypeUsageType.getMimeTypeUsageTypeName());
        }

        return mimeTypeUsage;
    }
    
    public MimeType checkMimeType(ExecutionErrorAccumulator eea, String mimeTypeName, String value, String mimeTypeUsageTypeName, String mimeTypeMissingError,
            String missingError, String mimeTypeUnknownError, String mimeTypeUsageError) {
        MimeType mimeType = null;
        var parameterCount = (mimeTypeName == null ? 0 : 1) + (value == null ? 0 : 1);
        
        if(parameterCount == 2) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            
            mimeType = mimeTypeName == null? null: mimeTypeControl.getMimeTypeByName(mimeTypeName);
            
            if(mimeTypeName == null || mimeType != null) {
                if(mimeTypeUsageTypeName != null) {
                    var mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
                    
                    if(mimeTypeUsageType != null) {
                        if(mimeTypeControl.getMimeTypeUsage(mimeType, mimeTypeUsageType) == null) {
                            eea.addExecutionError(mimeTypeUsageError, mimeTypeName, mimeTypeUsageTypeName);
                        }
                    } else {
                        eea.addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                    }
                }
            } else {
                eea.addExecutionError(mimeTypeUnknownError, mimeTypeName);
            }
        } else if(parameterCount == 1) {
            if(mimeTypeName == null) {
                eea.addExecutionError(mimeTypeMissingError);
            }

            if(value == null) {
                eea.addExecutionError(missingError);
            }
        }
        
        return mimeType;
    }

}
