// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.sequence.server.logic;

import com.echothree.model.control.sequence.common.exception.UnknownSequenceTypeNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SequenceTypeLogic
        extends BaseLogic {
    
    private SequenceTypeLogic() {
        super();
    }
    
    private static class SequenceLogicHolder {
        static SequenceTypeLogic instance = new SequenceTypeLogic();
    }
    
    public static SequenceTypeLogic getInstance() {
        return SequenceLogicHolder.instance;
    }
    
    public SequenceType getSequenceTypeByName(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);

        if(sequenceType == null) {
            handleExecutionError(UnknownSequenceTypeNameException.class, eea, ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceType;
    }
    
}
