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

package com.echothree.ui.web.main.action.advertising.offerchaintype.Add;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.result.GetChainTypeResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetOfferResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseAddAction
        extends MainBaseAction<ActionForm> {
    
    protected void setupOffer(HttpServletRequest request, String offerName)
            throws NamingException {
        var commandForm = OfferUtil.getHome().getGetOfferForm();
        commandForm.setOfferName(offerName);

        var commandResult = OfferUtil.getHome().getOffer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetOfferResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.OFFER, result.getOffer());
        
    }
    
    protected void setupOffer(HttpServletRequest request)
            throws NamingException {
        setupOffer(request, request.getParameter(ParameterConstants.OFFER_NAME));
    }
    
    protected void setupChainType(final HttpServletRequest request, final String chainKindName, final String chainTypeName)
            throws NamingException {
        var commandForm = ChainUtil.getHome().getGetChainTypeForm();
        
        commandForm.setChainKindName(chainKindName);
        commandForm.setChainTypeName(chainTypeName);

        var commandResult = ChainUtil.getHome().getChainType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetChainTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.CHAIN_TYPE, result.getChainType());
    }
    
}
