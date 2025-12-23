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

package com.echothree.model.control.customer.server.indexer;

import com.echothree.model.control.customer.server.analyzer.CustomerAnalyzer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.indexer.PartyIndexer;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class CustomerIndexer
        extends PartyIndexer {
    
    CustomerControl customerControl = Session.getModelController(CustomerControl.class);
    
    /** Creates a new instance of CustomerIndexer */
    public CustomerIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index, PartyTypes.CUSTOMER.name(), IndexFields.customerName.name());
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new CustomerAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes, partyType, entityNameIndexField);
    }

    @Override
    protected String getEntityNameFromParty(final Party party) {
        var customer = customerControl.getCustomer(party);

        return customer == null ? null : customer.getCustomerName();
    }

    @Override
    protected void addPartyFieldsToDocument(final Party party, final String entityName, final Document document) {
        super.addPartyFieldsToDocument(party, entityName, document);

        var customer = customerControl.getCustomer(party);
        if(customer != null) {
            document.add(new Field(IndexFields.customerTypeName.name(), customer.getCustomerType().getLastDetail().getCustomerTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        }
    }

}
