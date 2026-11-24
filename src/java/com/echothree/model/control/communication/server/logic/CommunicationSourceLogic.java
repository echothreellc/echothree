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

package com.echothree.model.control.communication.server.logic;

import com.echothree.model.control.communication.common.exception.UnknownCommunicationSourceNameException;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommunicationSourceLogic
        extends BaseLogic {

    @Inject
    CommunicationControl communicationControl;

    protected CommunicationSourceLogic() {
        super();
    }

    public CommunicationSource getCommunicationSourceByName(final ExecutionErrorAccumulator eea,
            final String communicationSourceName, final EntityPermission entityPermission) {
        var communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName, entityPermission);

        if(communicationSource == null) {
            handleExecutionError(UnknownCommunicationSourceNameException.class, eea,
                    ExecutionErrors.UnknownCommunicationSourceName.name(), communicationSourceName);
        }

        return communicationSource;
    }

    public CommunicationSource getCommunicationSourceByName(final ExecutionErrorAccumulator eea,
            final String communicationSourceName) {
        return getCommunicationSourceByName(eea, communicationSourceName, EntityPermission.READ_ONLY);
    }

    public CommunicationSource getCommunicationSourceByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String communicationSourceName) {
        return getCommunicationSourceByName(eea, communicationSourceName, EntityPermission.READ_WRITE);
    }

}
