// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.party.server.indexer;

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public abstract class PartyIndexer
        extends BaseIndexer<Party> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    PartyType partyType;
    String entityNameIndexField;
    
    protected PartyIndexer(final ExecutionErrorAccumulator eea, final Index index, final String partyTypeName, final String entityNameIndexField) {
        super(eea, index);
        
        this.partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        this.entityNameIndexField = entityNameIndexField;
    }
    
    @Override
    protected Party getEntity(final EntityInstance entityInstance) {
        return partyControl.getPartyByEntityInstance(entityInstance);

    }
    
    protected abstract String getEntityNameFromParty(final Party party);
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final Party party) {
        PartyDetail partyDetail = party.getLastDetail();
        Document document = null;

        if(partyDetail.getPartyType().equals(partyType)) {
            PartyGroup partyGroup = partyControl.getPartyGroup(party);
            Person person = partyControl.getPerson(party);
            String name = partyGroup == null ? null : partyGroup.getName();
            String entityName = getEntityNameFromParty(party);

            document = new Document();
            
            document.add(new Field(IndexConstants.IndexField_EntityRef, party.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));
            
            document.add(new Field(IndexConstants.IndexField_PartyName, partyDetail.getPartyName(), FieldTypes.NOT_STORED_NOT_TOKENIZED));

            if(entityName != null) {
                document.add(new Field(entityNameIndexField, entityName, FieldTypes.NOT_STORED_NOT_TOKENIZED));
            }

            if(name != null) {
                document.add(new Field(IndexConstants.IndexField_Name, name, FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(person != null) {
                String firstName = person.getFirstName();
                String middleName = person.getMiddleName();
                String lastName = person.getLastName();

                if(firstName != null) {
                    document.add(new Field(IndexConstants.IndexField_FirstName, firstName, FieldTypes.NOT_STORED_TOKENIZED));
                }
                if(middleName != null) {
                    document.add(new Field(IndexConstants.IndexField_MiddleName, middleName, FieldTypes.NOT_STORED_TOKENIZED));
                }
                if(lastName != null) {
                    document.add(new Field(IndexConstants.IndexField_LastName, lastName, FieldTypes.NOT_STORED_TOKENIZED));
                }
            }

            indexWorkflowEntityStatuses(document, entityInstance);
            indexEntityTimes(document, entityInstance);
            indexEntityAttributes(document, entityInstance);
            indexEntityTags(document, entityInstance);
        }

        return document;
    }

}
