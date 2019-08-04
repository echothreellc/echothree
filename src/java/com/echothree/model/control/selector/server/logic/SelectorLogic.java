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
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SelectorLogic
        extends BaseLogic {

    private SelectorLogic() {
        super();
    }
    
    private static class SelectorLogicHolder {
        static SelectorLogic instance = new SelectorLogic();
    }
    
    public static SelectorLogic getInstance() {
        return SelectorLogicHolder.instance;
    }
    
    public Selector getSelectorByName(final ExecutionErrorAccumulator eea, final SelectorType selectorType,
            final String selectorName) {
        var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        var selector = selectorControl.getSelectorByName(selectorType, selectorName);

        if(selector == null) {
            var selectorTypeDetail = selectorType.getLastDetail();

            handleExecutionError(UnknownSelectorNameException.class, eea, ExecutionErrors.UnknownSelectorName.name(),
                    selectorTypeDetail.getSelectorKind().getLastDetail().getSelectorKindName(),
                    selectorTypeDetail.getSelectorTypeName(), selectorName);
        }

        return selector;
    }

    public Selector getSelectorByName(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final String selectorTypeName, final String selectorName) {
        var selectorType = SelectorTypeLogic.getInstance().getSelectorTypeByName(eea, selectorKindName, selectorTypeName);
        Selector selector = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            selector = getSelectorByName(eea, selectorType, selectorName);
        }

        return selector;
    }

}
