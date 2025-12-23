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

package com.echothree.model.control.core.server.analyzer;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.index.server.analyzer.WhitespaceLowerCaseAnalyzer;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;

public class EntityAttributeAnalyzer
        extends BasicAnalyzer {

    public EntityAttributeAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        super(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }

    public EntityAttributeAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(eea, language, entityType);
    }
    
    @Override
    protected Map<String, Analyzer> getEntityTypeFieldAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        super.getEntityTypeFieldAnalyzers(fieldAnalyzers);

        fieldAnalyzers.put(IndexFields.componentVendorName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.entityTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.entityAttributeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.entityAttributeTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.trackRevisions.name(), new WhitespaceLowerCaseAnalyzer());

        return fieldAnalyzers;
    }
    
}
