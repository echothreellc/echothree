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

package com.echothree.model.control.index.server.logic;

import com.echothree.model.control.index.common.exception.UnknownIndexTypeNameException;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class IndexTypeLogic
        extends BaseLogic {

    protected IndexTypeLogic() {
        super();
    }

    public static IndexTypeLogic getInstance() {
        return CDI.current().select(IndexTypeLogic.class).get();
    }
    
    public IndexType getIndexTypeByName(final ExecutionErrorAccumulator eea, final String indexTypeName) {
        var indexControl = Session.getModelController(IndexControl.class);
        var indexType = indexControl.getIndexTypeByName(indexTypeName);

        if(indexType == null) {
            handleExecutionError(UnknownIndexTypeNameException.class, eea, ExecutionErrors.UnknownIndexTypeName.name(), indexTypeName);
        }

        return indexType;
    }
    
}
