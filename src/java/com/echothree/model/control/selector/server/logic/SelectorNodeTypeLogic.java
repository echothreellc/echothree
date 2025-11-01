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

package com.echothree.model.control.selector.server.logic;

import com.echothree.model.control.selector.common.exception.UnknownSelectorNodeTypeNameException;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SelectorNodeTypeLogic
        extends BaseLogic {

    protected SelectorNodeTypeLogic() {
        super();
    }

    public static SelectorNodeTypeLogic getInstance() {
        return CDI.current().select(SelectorNodeTypeLogic.class).get();
    }
    
    public SelectorNodeType getSelectorNodeTypeByName(final ExecutionErrorAccumulator eea, final String selectorNodeTypeName) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);

        if(selectorNodeType == null) {
            handleExecutionError(UnknownSelectorNodeTypeNameException.class, eea, ExecutionErrors.UnknownSelectorNodeTypeName.name(), selectorNodeTypeName);
        }

        return selectorNodeType;
    }

}
