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

package com.echothree.model.control.sequence.server.logic;

import com.echothree.model.control.sequence.common.exception.UnknownSequenceEncoderTypeNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SequenceEncoderTypeLogic
        extends BaseLogic {

    protected SequenceEncoderTypeLogic() {
        super();
    }

    public static SequenceEncoderTypeLogic getInstance() {
        return CDI.current().select(SequenceEncoderTypeLogic.class).get();
    }

    public SequenceEncoderType getSequenceEncoderTypeByName(final ExecutionErrorAccumulator eea, final String sequenceEncoderTypeName) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceEncoderType = sequenceControl.getSequenceEncoderTypeByName(sequenceEncoderTypeName);

        if(sequenceEncoderType == null) {
            handleExecutionError(UnknownSequenceEncoderTypeNameException.class, eea, ExecutionErrors.UnknownSequenceEncoderTypeName.name(), sequenceEncoderTypeName);
        }

        return sequenceEncoderType;
    }

}
