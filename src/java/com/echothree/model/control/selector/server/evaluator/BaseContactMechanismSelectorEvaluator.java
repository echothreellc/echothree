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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.factory.ContactMechanismFactory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.payment.common.pk.PartyPaymentMethodContactMechanismPK;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodContactMechanismFactory;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class BaseContactMechanismSelectorEvaluator
        extends BaseSelectorEvaluator {
    
    protected ContactControl contactControl = Session.getModelController(ContactControl.class);
    
    protected BaseContactMechanismSelectorEvaluator(Session session, BasePK evaluatedBy, Class logClass) {
        super(session, evaluatedBy, logClass);
    }
    
    @Override
    protected Boolean evaluateSelectorNode(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseContactMechanismSelectorEvaluator)
            log.info(">>> BaseContactMechanismSelectorEvaluator.evaluateSelectorNode(snd = " + snd + ")");
        Boolean result;
        
        if(snd != null) {
            result = super.evaluateSelectorNode(snd);
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BaseContactMechanismSelectorEvaluator)
                log.info("--- snd was null, evaluating to FALSE");
            result = false;
        }
        
        if(BaseSelectorEvaluatorDebugFlags.BaseContactMechanismSelectorEvaluator)
            log.info("<<< BaseContactMechanismSelectorEvaluator.evaluateSelectorNode, result = " + result);
        return result;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanism or a
     * ECHO_THREE.PartyPaymentMethodContactMechanism */
    protected ContactMechanism getContactMechanismFromEntityInstance(EntityInstance entityInstance) {
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        var componentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
        var entityTypeName = entityTypeDetail.getEntityTypeName();
        ContactMechanism contactMechanism = null;
        
        if(componentVendorName.equals(ComponentVendors.ECHO_THREE.name())) {
            if(entityTypeName.equals(EntityTypes.ContactMechanism.name())) {
                var pk = new ContactMechanismPK(entityInstance.getEntityUniqueId());
                contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
            } else if(entityTypeName.equals(EntityTypes.PartyPaymentMethodContactMechanism.name())) {
                var pk = new PartyPaymentMethodContactMechanismPK(entityInstance.getEntityUniqueId());
                var partyPaymentMethodContactMechanism = PartyPaymentMethodContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
                
                contactMechanism = partyPaymentMethodContactMechanism.getPartyContactMechanismPurpose().getLastDetail().getPartyContactMechanism().getLastDetail().getContactMechanism();
            }
        }
        
        return contactMechanism;
    }
    
    protected Boolean isContactMechanismSelected(CachedSelector cachedSelector, EntityInstance entityInstance, ContactMechanism contactMechanism) {
        if(BaseSelectorEvaluatorDebugFlags.BaseContactMechanismSelectorEvaluator)
            log.info(">>> BaseContactMechanismSelectorEvaluator(cachedSelector = " + cachedSelector + ", entityInstance = "
                    + entityInstance + ", contactMechanism = " + contactMechanism + ")");
        
        this.cachedSelector = cachedSelector;
        this.entityInstance = entityInstance;

        var result = evaluateSelectorNode(cachedSelector.getRootSelectorNodeDetail());
        
        this.entityInstance = null;
        this.cachedSelector = null;
        
        if(BaseSelectorEvaluatorDebugFlags.BaseContactMechanismSelectorEvaluator)
            log.info("<<< BaseContactMechanismSelectorEvaluator, result = " + result);
        return result;
    }
    
    protected Boolean isContactMechanismSelected(CachedSelector cachedSelector, ContactMechanism contactMechanism) {
        return isContactMechanismSelected(cachedSelector, getEntityInstanceByBasePK(contactMechanism.getPrimaryKey()), contactMechanism);
    }
    
}
