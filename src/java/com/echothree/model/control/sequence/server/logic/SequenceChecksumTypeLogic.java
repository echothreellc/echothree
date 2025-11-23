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

package com.echothree.model.control.sequence.server.logic;

import com.echothree.model.control.sequence.common.exception.UnknownSequenceChecksumTypeNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SequenceChecksumTypeLogic
        extends BaseLogic {

    protected SequenceChecksumTypeLogic() {
        super();
    }

    public static SequenceChecksumTypeLogic getInstance() {
        return CDI.current().select(SequenceChecksumTypeLogic.class).get();
    }

    public SequenceChecksumType getSequenceChecksumTypeByName(final ExecutionErrorAccumulator eea, final String sequenceChecksumTypeName) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceChecksumType = sequenceControl.getSequenceChecksumTypeByName(sequenceChecksumTypeName);

        if(sequenceChecksumType == null) {
            handleExecutionError(UnknownSequenceChecksumTypeNameException.class, eea, ExecutionErrors.UnknownSequenceChecksumTypeName.name(), sequenceChecksumTypeName);
        }

        return sequenceChecksumType;
    }

}
