// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.selector.common.exception.UnknownSelectorNameException;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SelectorTypeLogic
        extends BaseLogic {

    private SelectorTypeLogic() {
        super();
    }
    
    private static class SelectorTypeLogicHolder {
        static SelectorTypeLogic instance = new SelectorTypeLogic();
    }
    
    public static SelectorTypeLogic getInstance() {
        return SelectorTypeLogicHolder.instance;
    }
    
    public SelectorType getSelectorTypeByName(final ExecutionErrorAccumulator eea, final SelectorKind selectorKind,
            final String selectorTypeName) {
        var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

        if(selectorType == null) {
            handleExecutionError(UnknownSelectorNameException.class, eea, ExecutionErrors.UnknownSelectorTypeName.name(),
                    selectorKind.getLastDetail().getSelectorKindName(), selectorTypeName);
        }

        return selectorType;
    }

    public SelectorType getSelectorTypeByName(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final String selectorTypeName) {
        var selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(eea, selectorKindName);
        SelectorType selectorType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            selectorType = getSelectorTypeByName(eea, selectorKind, selectorTypeName);
        }

        return selectorType;
    }

}
