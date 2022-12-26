// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.index.server.analysis;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.index.server.indexer.IndexerDebugFlags;
import com.echothree.model.control.party.common.Languages;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class BasicAnalyzer
        extends AnalyzerWrapper {
    
    protected Log log = LogFactory.getLog(this.getClass());
    
    private Analyzer defaultAnalyzer;
    private Map<String, Analyzer> fieldAnalyzers;

    protected final void init(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        defaultAnalyzer = getDefaultAnalyzer(eea, language);
        fieldAnalyzers = getFieldAnalyzers(eea, entityType, entityAttributes, tagScopes);
    }
    
    public BasicAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        super(AnalyzerWrapper.PER_FIELD_REUSE_STRATEGY);
        
        init(eea, language, entityType, entityAttributes, tagScopes);
    }

    public BasicAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(AnalyzerWrapper.PER_FIELD_REUSE_STRATEGY);
        
        var coreControl = Session.getModelController(CoreControl.class);
        var tagControl = Session.getModelController(TagControl.class);
        
        init(eea, language, entityType, coreControl.getEntityAttributesByEntityType(entityType), tagControl.getTagScopesByEntityType(entityType));
    }

    @Override
    protected Analyzer getWrappedAnalyzer(String fieldName) {
        Analyzer analyzer = fieldAnalyzers.get(fieldName);
        
        return (analyzer != null) ? analyzer : defaultAnalyzer;
    }

    @Override
    protected TokenStreamComponents wrapComponents(String fieldName, TokenStreamComponents components) {
        return components;
    }

    @Override
    public String toString() {
        return "PerFieldAnalyzerWrapper(" + fieldAnalyzers + ", default=" + defaultAnalyzer + ")";
    }

    protected Analyzer getDefaultAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        Analyzer selectedAnalyzer = null;
        
        if(language != null) {
            String languageIsoName = language.getLanguageIsoName();
            
            if(languageIsoName.equals(Languages.en.name())) {
                selectedAnalyzer = new EnglishAnalyzer();
            } else if(languageIsoName.equals(Languages.de.name())) {
                selectedAnalyzer = new GermanAnalyzer();
            } else if(languageIsoName.equals(Languages.es.name())) {
                selectedAnalyzer = new SpanishAnalyzer();
            } else if(languageIsoName.equals(Languages.fr.name())) {
                selectedAnalyzer = new FrenchAnalyzer();
            } else if(languageIsoName.equals(Languages.jp.name())) {
                selectedAnalyzer = new JapaneseAnalyzer();
            } else if(languageIsoName.equals(Languages.ko.name()) || languageIsoName.equals(Languages.zh.name())) {
                selectedAnalyzer = new CJKAnalyzer();
            }
        }
        
        return selectedAnalyzer == null ? new StandardAnalyzer() : selectedAnalyzer;
    }
    
    protected Map<String, Analyzer> getEntityAttributeFieldAnalyzers(final List<EntityAttribute> entityAttributes, final Map<String, Analyzer> fieldAnalyzers) {
        entityAttributes.stream().map((entityAttribute) -> entityAttribute.getLastDetail()).forEach((entityAttributeDetail) -> {
            String fieldName = entityAttributeDetail.getEntityAttributeName();
            String entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
            if(IndexerDebugFlags.LogBaseAnalyzer) {
                log.info("--- fieldName = " + fieldName + ", entityAttributeTypeName = " + entityAttributeTypeName);
            }

            // EntityAttributeTypes.INTEGER.name() - treated as a NumericField, no Analyzer.
            // EntityAttributeTypes.LONG.name() - treated as a NumericField, no Analyzer.
            // EntityAttributeTypes.DATE.name() - treated as a NumericField, no Analyzer.
            // EntityAttributeTypes.TIME.name() - treated as a NumericField, no Analyzer.
            // EntityAttributeTypes.STRING.name() - uses default Analyzer.
            // EntityAttributeTypes.CLOB.name() - uses default Analyzer.
            // EntityAttributeTypes.GEOPOINT.name() - ignored.
            if (entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                // Use the WhitespaceAnalyzer
                fieldAnalyzers.put(fieldName, new WhitespaceLowerCaseAnalyzer());
            }
        });
        
        return fieldAnalyzers;
    }
    
    protected Map<String, Analyzer> getTagScopeFieldAnalyzers(final List<TagScope> tagScopes, final Map<String, Analyzer> fieldAnalyzers) {
        tagScopes.stream().map((tagScope) -> tagScope.getLastDetail().getTagScopeName()).map((fieldName) -> {
            if(IndexerDebugFlags.LogBaseAnalyzer) {
                log.info("--- fieldName = " + fieldName);
            }
            return fieldName;
        }).forEach((fieldName) -> {
            fieldAnalyzers.put(fieldName, new WhitespaceLowerCaseAnalyzer());
        });
        
        return fieldAnalyzers;
    }
    
    protected Map<String, Analyzer> getWorkflowFieldAnalyzers(final EntityType entityType, final Map<String, Analyzer> fieldAnalyzers) {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        workflowControl.getWorkflowsByEntityType(entityType).stream().map((workflow) -> workflow.getLastDetail().getWorkflowName()).map((fieldName) -> {
            if(IndexerDebugFlags.LogBaseAnalyzer) {
                log.info("--- fieldName = " + fieldName);
            }
            return fieldName;
        }).forEach((fieldName) -> {
            fieldAnalyzers.put(fieldName, new WhitespaceLowerCaseAnalyzer());
        });
        
        return fieldAnalyzers;
    }
    
    protected Map<String, Analyzer> getEntityTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        // No additional Analyzers by default.
        
        return fieldAnalyzers;
    }
    
    protected Map<String, Analyzer> getFieldAnalyzers(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final List<EntityAttribute> entityAttributes, final List<TagScope> tagScopes) {
        return getEntityTypeAnalyzers(getWorkflowFieldAnalyzers(entityType, getTagScopeFieldAnalyzers(tagScopes, getEntityAttributeFieldAnalyzers(entityAttributes, new HashMap<>()))));
    }
    
}
