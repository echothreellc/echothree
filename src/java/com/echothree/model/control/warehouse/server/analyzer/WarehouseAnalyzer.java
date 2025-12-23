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

package com.echothree.model.control.warehouse.server.analyzer;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.party.server.analyzer.PartyAnalyzer;
import com.echothree.model.control.index.server.analyzer.WhitespaceLowerCaseAnalyzer;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;

public class WarehouseAnalyzer
        extends PartyAnalyzer {

    public WarehouseAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes, final PartyType partyType,
            final String entityNameIndexField) {
        super(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes, partyType, entityNameIndexField);
    }

    public WarehouseAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType,
            final PartyType partyType, final String entityNameIndexField) {
        super(eea, language, entityType, partyType, entityNameIndexField);
    }

    @Override
    protected Map<String, Analyzer> getAdditionalAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        fieldAnalyzers.put(IndexFields.warehouseTypeName.name(), new WhitespaceLowerCaseAnalyzer());

        return fieldAnalyzers;
    }

}
