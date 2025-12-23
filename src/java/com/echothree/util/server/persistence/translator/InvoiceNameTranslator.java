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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.invoice.common.InvoiceTypes;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.invoice.server.logic.InvoiceLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.invoice.common.pk.InvoicePK;
import com.echothree.model.data.invoice.server.entity.InvoiceDetail;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.Names;
import com.echothree.util.common.persistence.Targets;
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
        var invoiceTypesToTargetsMap = new HashMap<String, String>();

        invoiceTypesToTargetsMap.put(InvoiceTypes.PURCHASE_INVOICE.name(), Targets.PurchaseInvoice.name());
        invoiceTypesToTargetsMap.put(InvoiceTypes.SALES_INVOICE.name(), Targets.SalesInvoice.name());

        invoiceTypesToTargets = Collections.unmodifiableMap(invoiceTypesToTargetsMap);

        var sequenceTypesToInvoiceTypesMap = new HashMap<String, String>();

        sequenceTypesToInvoiceTypesMap.put(SequenceTypes.PURCHASE_INVOICE.name(), InvoiceTypes.PURCHASE_INVOICE.name());
        sequenceTypesToInvoiceTypesMap.put(SequenceTypes.SALES_INVOICE.name(), InvoiceTypes.SALES_INVOICE.name());
        
        sequenceTypesToInvoiceTypes = Collections.unmodifiableMap(sequenceTypesToInvoiceTypesMap);

        var sequenceTypesToTargetsMap = new HashMap<String, String>();

        sequenceTypesToTargetsMap.put(SequenceTypes.PURCHASE_INVOICE.name(), Targets.PurchaseInvoice.name());
        sequenceTypesToTargetsMap.put(SequenceTypes.SALES_INVOICE.name(), Targets.SalesInvoice.name());
        
        sequenceTypesToTargets = Collections.unmodifiableMap(sequenceTypesToTargetsMap);
    }

    private EntityNames getNames(final Map<String, String> targetMap, final String key, final InvoiceDetail invoiceDetail) {
        EntityNames result = null;
        var target = targetMap.get(key);

        if(target != null) {
            var names = new MapWrapper<String>(1);

            names.put(Names.InvoiceName.name(), invoiceDetail.getInvoiceName());

            result = new EntityNames(target, names);
        }

        return result;
    }
    
    @Override
    public EntityNames getNames(final EntityInstance entityInstance) {
        var invoiceDetail = InvoiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                new InvoicePK(entityInstance.getEntityUniqueId())).getLastDetail();
        var invoiceTypeName = invoiceDetail.getInvoiceType().getLastDetail().getInvoiceTypeName();
        
        return getNames(invoiceTypesToTargets, invoiceTypeName, invoiceDetail);
    }

    @Override
    public EntityInstanceAndNames getNames(final String sequenceTypeName, final String invoiceName,
            final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceTypeName = sequenceTypesToInvoiceTypes.get(sequenceTypeName);

        if(invoiceTypeName != null) {
            var invoiceType = InvoiceLogic.getInstance().getInvoiceTypeByName(null, invoiceTypeName);
            var invoice = invoiceControl.getInvoiceByName(invoiceType, invoiceName);

            if(invoice != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, invoice.getLastDetail());
                
                result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance ? entityInstanceControl.getEntityInstanceByBasePK(invoice.getPrimaryKey()) : null, entityNames);
            }
        }
        
        return result;
    }
    
}

