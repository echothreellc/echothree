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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.invoice.common.InvoiceConstants;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.invoice.server.logic.InvoiceLogic;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.invoice.common.pk.InvoicePK;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.EntityNamesConstants;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InvoiceNameTranslator
        implements EntityInstanceTranslator, SequenceTypeTranslator {

    private final static Map<String, String> invoiceTypesToTargets;
    private final static Map<String, String> sequenceTypesToInvoiceTypes;
    private final static Map<String, String> sequenceTypesToTargets;

    static {
        var targetMap = new HashMap<String, String>();
        
        targetMap.put(InvoiceConstants.InvoiceType_PURCHASE_INVOICE, EntityNamesConstants.Target_PurchaseInvoice);
        targetMap.put(InvoiceConstants.InvoiceType_SALES_INVOICE, EntityNamesConstants.Target_SalesInvoice);

        invoiceTypesToTargets = Collections.unmodifiableMap(targetMap);
        
        targetMap = new HashMap<>();
        
        targetMap.put(SequenceConstants.SequenceType_PURCHASE_INVOICE, InvoiceConstants.InvoiceType_PURCHASE_INVOICE);
        targetMap.put(SequenceConstants.SequenceType_SALES_INVOICE, InvoiceConstants.InvoiceType_SALES_INVOICE);
        
        sequenceTypesToInvoiceTypes = Collections.unmodifiableMap(targetMap);
        
        targetMap = new HashMap<>();
        
        targetMap.put(SequenceConstants.SequenceType_PURCHASE_INVOICE, EntityNamesConstants.Target_PurchaseInvoice);
        targetMap.put(SequenceConstants.SequenceType_SALES_INVOICE, EntityNamesConstants.Target_SalesInvoice);
        
        sequenceTypesToTargets = Collections.unmodifiableMap(targetMap);
    }

    private EntityNames getNames(final Map<String, String> targetMap, final String key, final InvoiceDetail invoiceDetail) {
        String target = targetMap.get(key);
        EntityNames result = null;

        if(target != null) {
            MapWrapper<String> names = new MapWrapper<>(1);

            names.put(EntityNamesConstants.Name_InvoiceName, invoiceDetail.getInvoiceName());

            result = new EntityNames(target, names);
        }

        return result;
    }
    
    @Override
    public EntityNames getNames(final EntityInstance entityInstance) {
        InvoiceDetail invoiceDetail = InvoiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                new InvoicePK(entityInstance.getEntityUniqueId())).getLastDetail();
        String invoiceTypeName = invoiceDetail.getInvoiceType().getLastDetail().getInvoiceTypeName();
        
        return getNames(invoiceTypesToTargets, invoiceTypeName, invoiceDetail);
    }

    @Override
    public EntityInstanceAndNames getNames(final Party requestingParty, final String sequenceTypeName, final String invoiceName,
            final boolean includeEntityInstance) {
        var invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        String invoiceTypeName = sequenceTypesToInvoiceTypes.get(sequenceTypeName);
        EntityInstanceAndNames result = null;
        
        if(invoiceTypeName != null) {
            InvoiceType invoiceType = InvoiceLogic.getInstance().getInvoiceTypeByName(null, invoiceTypeName);
            Invoice invoice = invoiceControl.getInvoiceByName(invoiceType, invoiceName);

            if(invoice != null) {
                var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                EntityNames entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, invoice.getLastDetail());
                
                result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance ? coreControl.getEntityInstanceByBasePK(invoice.getPrimaryKey()) : null, entityNames);
            }
        }
        
        return result;
    }
    
}

