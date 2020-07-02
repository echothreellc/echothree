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

package com.echothree.model.control.search.server.search;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.index.server.analysis.BasicAnalyzer;
import com.echothree.model.control.index.server.logic.IndexLogic;
import com.echothree.model.control.index.server.logic.IndexTypeLogic;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.exception.SearchIOErrorException;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

public abstract class BaseEvaluator
        extends BaseLogic {
    
    private Log log;
    
    protected UserVisit userVisit;
    protected SearchType searchType;
    protected SearchDefaultOperator searchDefaultOperator;
    protected EntityType entityType;
    
    protected Index index;
    
    protected SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
    private CoreControl coreControl;
    private IndexControl indexControl;
    private UserControl userControl;
    
    private void init(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, EntityType entityType, String indexTypeName,
            Language language, String indexName) {
        this.userVisit = userVisit;
        this.searchType = searchType;
        this.searchDefaultOperator = searchDefaultOperator;
        this.entityType = entityType;

        // indexName should be null if the index you're using supports multiple languages.
        if(indexTypeName != null) {
            index = getIndexByIndexType(IndexTypeLogic.getInstance().getIndexTypeByName(null, indexTypeName), language);
        } else if(indexName != null) {
            index = IndexLogic.getInstance().getIndexByName(null, indexName);
        } else {
            // If neither were set, then there are no Lucene indexes used for this search.
            index = null;
        }
    }
    
    protected BaseEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, String componentVendorName,
            String entityTypeName, String indexTypeName, Language language, String indexName) {
        init(userVisit, searchType, searchDefaultOperator,
                entityTypeName == null ? null : EntityTypeLogic.getInstance().getEntityTypeByName(null, componentVendorName, entityTypeName),
                indexTypeName, language, indexName);
    }
    
    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }

        return log;
    }

    protected CoreControl getCoreControl() {
        if(coreControl == null) {
            coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        }

        return coreControl;
    }
    
    protected IndexControl getIndexControl() {
        if(indexControl == null) {
            indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        }

        return indexControl;
    }
    
    protected UserControl getUserControl() {
        if(userControl == null) {
            userControl = (UserControl)Session.getModelController(UserControl.class);
        }
        
        return userControl;
    }
    
    protected String getSearchDefaultOperatorName() {
        return searchDefaultOperator.getLastDetail().getSearchDefaultOperatorName();
    }
    
    protected String getLanguageIsoName() {
        Language language = index.getLastDetail().getLanguage();
        
        return language == null ? null : language.getLanguageIsoName();
    }
    
    // -------------------------------------------------------------------------
    //   Common Lucene Index Support
    // -------------------------------------------------------------------------
    
    private Index getIndexByIndexType(final IndexType indexType, final Language language) {
        return getIndexControl().getBestIndex(indexType, language == null ? getUserControl().getPreferredLanguageFromUserVisit(userVisit) : language);
    }
    
    protected Language getLanguage() {
        return index.getLastDetail().getLanguage();
    }
    
    protected String q = null;
    
    /**
     * Returns the q.
     * @return the q
     */
    public String getQ() {
        return q;
    }

    /**
     * Sets the q.
     * @param q the q to set
     */
    public void setQ(final ExecutionErrorAccumulator eea, final String q) {
        this.q = q;
    }
    
    protected Query query = null;
    
    protected void parseQuery(final ExecutionErrorAccumulator eea, final String field, final String[] fields) {
        if(q != null) {
            QueryParser qp = fields == null ? new AttributeQueryParser(eea, getDateFields(), getDateTimeFields(), entityType, userVisit, field, getCachedAnalyzer(null, getLanguage()))
                    : new AttributeMultiFieldQueryParser(eea, getDateFields(), getDateTimeFields(), entityType, userVisit, fields, getCachedAnalyzer(null, getLanguage()));
            String searchDefaultOperatorName = getSearchDefaultOperatorName();

            switch(searchDefaultOperatorName) {
                case SearchConstants.SearchDefaultOperator_AND:
                    qp.setDefaultOperator(QueryParser.Operator.AND);
                    break;
                case SearchConstants.SearchDefaultOperator_OR:
                    qp.setDefaultOperator(QueryParser.Operator.OR);
                    break;
            }

            try {
                query = qp.parse(q);
            } catch(ParseException pe) {
                handleExecutionError(SearchIOErrorException.class, eea, ExecutionErrors.SearchParseError.name(), pe.getMessage());
            }
        }
    }
    
    protected String field;
    protected String[] fields;

    /**
     * Returns the field.
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the field.
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Returns the fields.
     * @return the fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * Sets the fields.
     * @param fields the fields to set
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }

    protected Set<String> getDateFields() {
        return null;
    }
    
    protected Set<String> getDateTimeFields() {
        return null;
    }
    
    private Analyzer cachedAnalyzer = null;
    
    protected Analyzer getCachedAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        // Some search evaluators will override getAnalyzer(...), so this is our private version that keeps
        // from having to run to getAnalyzer(...) multiple times (specially a problem for spelling check).
        if(cachedAnalyzer == null) {
            cachedAnalyzer = getAnalyzer(eea, language);
        }
        
        return cachedAnalyzer;
    }
    
    protected Analyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new BasicAnalyzer(eea, language, entityType);
    }
    
}
