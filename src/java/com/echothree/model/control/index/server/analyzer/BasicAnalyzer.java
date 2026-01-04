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

package com.echothree.model.control.index.server.analyzer;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.indexer.IndexerDebugFlags;
import com.echothree.model.control.party.common.Languages;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private ExecutionErrorAccumulator eea;
    private EntityType entityType;
    private List<EntityAliasType> entityAliasTypes;
    private List<EntityAttribute> entityAttributes;
    private List<TagScope> tagScopes;

    private Analyzer defaultAnalyzer;
    private Map<String, Analyzer> cachedFieldAnalyzers = null;

    private void init(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType,
            final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        this.eea = eea;
        this.entityType = entityType;
        this.entityAliasTypes = entityAliasTypes;
        this.entityAttributes = entityAttributes;
        this.tagScopes = tagScopes;

        defaultAnalyzer = getDefaultAnalyzer(eea, language);
    }

    @Override
    public void close() {
        super.close();
        
        defaultAnalyzer.close();
        defaultAnalyzer = null;

        if(cachedFieldAnalyzers != null) {
            for(var cachedFieldAnalyzer : cachedFieldAnalyzers.values()) {
                cachedFieldAnalyzer.close();
            }
            cachedFieldAnalyzers = null;
        }
    }
    
    public BasicAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType,
            final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        super(AnalyzerWrapper.PER_FIELD_REUSE_STRATEGY);
        
        init(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }

    public BasicAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(AnalyzerWrapper.PER_FIELD_REUSE_STRATEGY);

        var coreControl = Session.getModelController(CoreControl.class);
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var tagControl = Session.getModelController(TagControl.class);
        
        init(eea, language, entityType, entityAliasControl.getEntityAliasTypesByEntityType(entityType),
                coreControl.getEntityAttributesByEntityType(entityType), tagControl.getTagScopesByEntityType(entityType));
    }

    @Override
    protected Analyzer getWrappedAnalyzer(String fieldName) {
        // Hold a cache of Analyzers.
        if(cachedFieldAnalyzers == null) {
            cachedFieldAnalyzers = getFieldAnalyzers(eea, entityType, entityAttributes, tagScopes);
        }

        var analyzer = cachedFieldAnalyzers.get(fieldName);
        
        return (analyzer != null) ? analyzer : defaultAnalyzer;
    }

    @Override
    public String toString() {
        return "BasicAnalyzer(" + cachedFieldAnalyzers + ", default=" + defaultAnalyzer + ")";
    }

    @SuppressWarnings("resource") // This is taken care of in our close() method.
    private Analyzer getDefaultAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        Analyzer selectedAnalyzer = null;
        
        if(language != null) {
            var languageIsoName = language.getLanguageIsoName();
            
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

    private Map<String, Analyzer> getEntityAliasesFieldAnalyzers(final List<EntityAliasType> entityAliasTypes, final Map<String, Analyzer> fieldAnalyzers) {
        entityAliasTypes.stream().map(EntityAliasType::getLastDetail).forEach((entityAliasTypeDetail) -> {
            var fieldName = entityAliasTypeDetail.getEntityAliasTypeName();
            if(IndexerDebugFlags.LogBaseAnalyzer) {
                log.info("--- fieldName = " + fieldName);
            }

            fieldAnalyzers.put(fieldName, new WhitespaceLowerCaseAnalyzer());
        });

        return fieldAnalyzers;
    }

    private Map<String, Analyzer> getEntityAttributeFieldAnalyzers(final List<EntityAttribute> entityAttributes, final Map<String, Analyzer> fieldAnalyzers) {
        entityAttributes.stream().map(EntityAttribute::getLastDetail).forEach((entityAttributeDetail) -> {
            var fieldName = entityAttributeDetail.getEntityAttributeName();
            var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
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
            if (entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                // Use the WhitespaceAnalyzer
                fieldAnalyzers.put(fieldName, new WhitespaceLowerCaseAnalyzer());
            }
        });
        
        return fieldAnalyzers;
    }

    private Map<String, Analyzer> getTagScopeFieldAnalyzers(final List<TagScope> tagScopes, final Map<String, Analyzer> fieldAnalyzers) {
        tagScopes.forEach(tagScope -> {
            var tagScopeName = tagScope.getLastDetail().getTagScopeName();
            if(IndexerDebugFlags.LogBaseAnalyzer) {
                log.info("--- fieldName = " + tagScopeName);
            }
            fieldAnalyzers.put(tagScopeName, new WhitespaceLowerCaseAnalyzer());
        });

        return fieldAnalyzers;
    }

    private Map<String, Analyzer> getWorkflowFieldAnalyzers(final EntityType entityType, final Map<String, Analyzer> fieldAnalyzers) {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        workflowControl.getWorkflowsByEntityType(entityType).forEach(workflow -> {
            var workflowName = workflow.getLastDetail().getWorkflowName();
            if(IndexerDebugFlags.LogBaseAnalyzer) {
                log.info("--- fieldName = " + workflowName);
            }
            fieldAnalyzers.put(workflowName, new WhitespaceLowerCaseAnalyzer());
        });

        return fieldAnalyzers;
    }

    private Map<String, Analyzer> getAppearanceFieldAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        fieldAnalyzers.put(IndexFields.appearance.name(), new WhitespaceLowerCaseAnalyzer());

        return fieldAnalyzers;
    }

    protected Map<String, Analyzer> getEntityTypeFieldAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        // No additional Analyzers by default.
        
        return fieldAnalyzers;
    }

    protected Map<String, Analyzer> getFieldAnalyzers(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final List<EntityAttribute> entityAttributes, final List<TagScope> tagScopes) {
        return getEntityTypeFieldAnalyzers(
                getAppearanceFieldAnalyzers(
                        getWorkflowFieldAnalyzers(entityType,
                                getTagScopeFieldAnalyzers(tagScopes,
                                        getEntityAliasesFieldAnalyzers(entityAliasTypes,
                                                getEntityAttributeFieldAnalyzers(entityAttributes, new HashMap<>())
                                        )
                                )
                        )
                )
        );
    }

    public Set<String> getDateFields() {
        return null;
    }

    public Set<String> getDateTimeFields() {
        return null;
    }

    public Set<String> getIntFields() {
        return null;
    }

    public Set<String> getLongFields() {
        return null;
    }

    public Set<String> getFloatFields() {
        return null;
    }

    public Set<String> getDoubleFields() {
        return null;
    }

}
