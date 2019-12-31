// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.offer.common.exception.UnknownOfferUseException;
import com.echothree.model.control.offer.common.exception.UnknownUseNameException;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class OfferUseLogic
        extends BaseLogic {

    private OfferUseLogic() {
        super();
    }

    private static class OfferUseLogicHolder {
        static OfferUseLogic instance = new OfferUseLogic();
    }

    public static OfferUseLogic getInstance() {
        return OfferUseLogicHolder.instance;
    }
    
    public Use getUseByName(final ExecutionErrorAccumulator eea, final String useName) {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        Use use = offerControl.getUseByName(useName);

        if(use == null) {
            handleExecutionError(UnknownUseNameException.class, eea, ExecutionErrors.UnknownUseName.name(), useName);
        }

        return use;
    }

    public OfferUse getOfferUseByName(final ExecutionErrorAccumulator eea, final String offerName, final String useName) {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        Offer offer = OfferLogic.getInstance().getOfferByName(eea, offerName);
        OfferUse offerUse = null;
        
        if(offer != null) {
            Use use = UseLogic.getInstance().getUseByName(eea, useName);
            
            if(use != null) {
                offerUse = offerControl.getOfferUse(offer, use);
                
                if(offerUse == null) {
                    handleExecutionError(UnknownOfferUseException.class, eea, ExecutionErrors.UnknownOfferUse.name(), offerName, useName);
                }
            }
        }
        
        return offerUse;
    }
    
}
