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

package com.echothree.model.control.offer.server.logic;

import com.echothree.model.control.offer.common.exception.UnknownSourceNameException;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SourceLogic
        extends BaseLogic {

    protected SourceLogic() {
        super();
    }

    public static SourceLogic getInstance() {
        return CDI.current().select(SourceLogic.class).get();
    }
    
    public Source getSourceByName(final ExecutionErrorAccumulator eea, final String sourceName) {
        var sourceControl = Session.getModelController(SourceControl.class);
        var source = sourceControl.getSourceByName(sourceName);

        if(source == null) {
            handleExecutionError(UnknownSourceNameException.class, eea, ExecutionErrors.UnknownSourceName.name(), sourceName);
        }

        return source;
    }
    
}
