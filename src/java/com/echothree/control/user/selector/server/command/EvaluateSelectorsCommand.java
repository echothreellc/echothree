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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.EvaluateSelectorsForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.selector.server.evaluator.EmployeeSelectorEvaluator;
import com.echothree.model.control.selector.server.evaluator.OfferItemSelectorEvaluator;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EvaluateSelectorsCommand
        extends BaseSimpleCommand<EvaluateSelectorsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null))
        );
    }

    /** Creates a new instance of EvaluateSelectorsCommand */
    public EvaluateSelectorsCommand() {
        super(COMMAND_SECURITY_DEFINITION, null, false);
    }
    
    @Override
    protected BaseResult execute() {
        var maximumTime = 90 * 1000L; // 90 seconds
        var result = SelectorResultFactory.getEvaluateSelectorsResult();

        var offerItemSelectorEvaluator = new OfferItemSelectorEvaluator(session, getPartyPK());
        maximumTime = offerItemSelectorEvaluator.evaluate(maximumTime);
        
        if(maximumTime > 0) {
            var employeeSelectorEvaluator = new EmployeeSelectorEvaluator(session, getPartyPK());
            maximumTime = employeeSelectorEvaluator.evaluate(maximumTime);
        }
        
        result.setEvaluateSelectorsComplete(maximumTime > 0);
        
        return result;
    }
    
}
