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

package com.echothree.model.control.party.server.analyzer;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.index.server.analyzer.WhitespaceLowerCaseAnalyzer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;

public class PartyAnalyzer
        extends BasicAnalyzer {

    private PartyType partyType;
    private String entityNameIndexField;

    private void init(final PartyType partyType, final String entityNameIndexField) {
        this.partyType = partyType;
        this.entityNameIndexField = entityNameIndexField;
    }

    public PartyAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes, final PartyType partyType, final String entityNameIndexField) {
        super(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);

        init(partyType, entityNameIndexField);
    }

    public PartyAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType,
            final PartyType partyType, final String entityNameIndexField) {
        super(eea, language, entityType);

        init(partyType, entityNameIndexField);
    }

    protected Map<String, Analyzer> getPartyAliasTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        var partyControl = Session.getModelController(PartyControl.class);

        partyControl.getPartyAliasTypes(partyType).forEach((partyAliasType)->
                fieldAnalyzers.put(partyAliasType.getLastDetail().getPartyAliasTypeName(),
                        new WhitespaceLowerCaseAnalyzer()));

        return fieldAnalyzers;
    }

    protected Map<String, Analyzer> getAdditionalAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        return fieldAnalyzers;
    }

    @Override
    protected Map<String, Analyzer> getEntityTypeFieldAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        super.getEntityTypeFieldAnalyzers(fieldAnalyzers);
        
        fieldAnalyzers.put(IndexFields.partyName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(entityNameIndexField, new WhitespaceLowerCaseAnalyzer());

        return getAdditionalAnalyzers(getPartyAliasTypeAnalyzers(fieldAnalyzers));
    }
    
}
