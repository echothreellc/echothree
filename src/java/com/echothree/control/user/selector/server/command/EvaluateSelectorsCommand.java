// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.result.EvaluateSelectorsResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.selector.server.evaluator.EmployeeSelectorEvaluator;
import com.echothree.model.control.selector.server.evaluator.OfferItemSelectorEvaluator;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;

public class EvaluateSelectorsCommand
        extends BaseSimpleCommand {
    
    /** Creates a new instance of EvaluateSelectorsCommand */
    public EvaluateSelectorsCommand(UserVisitPK userVisitPK) {
        super(userVisitPK, null, false);
    }
    
    @Override
    protected BaseResult execute() {
        Long maximumTime = (long) 90 * 1000; // 90 seconds
        EvaluateSelectorsResult result = SelectorResultFactory.getEvaluateSelectorsResult();
        
        OfferItemSelectorEvaluator offerItemSelectorEvaluator = new OfferItemSelectorEvaluator(session, getPartyPK());
        maximumTime = offerItemSelectorEvaluator.evaluate(maximumTime);
        
        if(maximumTime > 0) {
            EmployeeSelectorEvaluator employeeSelectorEvaluator = new EmployeeSelectorEvaluator(session, getPartyPK());
            maximumTime = employeeSelectorEvaluator.evaluate(maximumTime);
        }
        
        result.setEvaluateSelectorsComplete(maximumTime > 0);
        
        return result;
    }
    
}
