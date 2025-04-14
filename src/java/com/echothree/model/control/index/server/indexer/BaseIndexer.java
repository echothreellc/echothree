// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.index.server.indexer;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.ComponentVendorControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.IndexSubfields;
import com.echothree.model.control.index.common.exception.IndexIOErrorException;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexStatus;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.SerialMergeScheduler;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public abstract class BaseIndexer<BE extends BaseEntity>
        extends BaseLogic
        implements Closeable {

    protected AppearanceControl appearanceControl = Session.getModelController(AppearanceControl.class);
    protected CoreControl coreControl = Session.getModelController(CoreControl.class);
    protected ComponentVendorControl componentVendorControl = Session.getModelController(ComponentVendorControl.class);
    protected EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    protected IndexControl indexControl = Session.getModelController(IndexControl.class);
    protected TagControl tagControl = Session.getModelController(TagControl.class);
    protected WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    protected Log log = LogFactory.getLog(this.getClass());

    protected ExecutionErrorAccumulator eea;
    protected Index index;
    
    protected Language language;
    protected EntityType entityType;
    protected IndexStatus indexStatus;
    protected List<EntityAliasType> entityAliasTypes;
    protected List<EntityAttribute> entityAttributes;
    protected List<TagScope> tagScopes;
    
    protected IndexWriter indexWriter;
    
    protected BaseIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        this.eea = eea;
        this.index = index;
    }
    
    public Index getIndex() {
        return index;
    }
    
    public void open() {
        if(indexWriter == null) {
            var indexDetail = index.getLastDetail();

            this.language = indexDetail.getLanguage();
            this.entityType = indexDetail.getIndexType().getLastDetail().getEntityType();
            this.indexStatus = indexControl.getIndexStatusForUpdate(index);
            this.entityAliasTypes = coreControl.getEntityAliasTypesByEntityType(entityType);
            this.entityAttributes = coreControl.getEntityAttributesByEntityType(entityType);
            this.tagScopes = tagControl.getTagScopesByEntityType(entityType);

            openIndexWriter(getAnalyzer());
        }
    }
    
    public EntityType getEntityType() {
        return entityType;
    }
    
    @Override
    public void close() {
        if(indexWriter != null) {
            closeIndexWriter();
        }
    }
    
    /** Index an EntityInstance in all of its Workflows. */
    private void indexWorkflowEntityStatuses(final Document document, final EntityInstance entityInstance) {
        workflowControl.getWorkflowsByEntityType(entityInstance.getEntityType()).forEach((workflow) -> {
            var workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);

            if (!workflowEntityStatuses.isEmpty()) {
                var workflowStepNamesBuilder = new StringBuilder();

                workflowEntityStatuses.forEach((workflowEntityStatus) -> {
                    if(workflowStepNamesBuilder.length() != 0) {
                        workflowStepNamesBuilder.append(' ');
                    }

                    workflowStepNamesBuilder.append(workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName());
                });

                document.add(new Field(workflow.getLastDetail().getWorkflowName(), workflowStepNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
            }
        });
    }

    private void indexEntityTimes(final Document document, final EntityInstance entityInstance) {
        var entityTime = coreControl.getEntityTime(entityInstance);

        if(entityTime != null) {
            var createdTime = entityTime.getCreatedTime();
            var modifiedTime = entityTime.getModifiedTime();
            var deletedTime = entityTime.getDeletedTime();

            if(createdTime != null) {
                document.add(new LongPoint(IndexFields.createdTime.name(), createdTime));
            }

            if(modifiedTime != null) {
                document.add(new LongPoint(IndexFields.modifiedTime.name(), modifiedTime));
            }

            if(deletedTime != null) {
                document.add(new LongPoint(IndexFields.deletedTime.name(), deletedTime));
            }
        }
    }

    private void indexEntityAliases(final Document document, final EntityInstance entityInstance) {
        var entityAliases = coreControl.getEntityAliasesByEntityInstance(entityInstance);

        for(var entityAlias : entityAliases) {
            var fieldName = entityAlias.getEntityAliasType().getLastDetail().getEntityAliasTypeName();
            var alias = entityAlias.getAlias();

            document.add(new Field(fieldName, alias, FieldTypes.NOT_STORED_NOT_TOKENIZED));
        }

    }

    private void indexEntityAttributes(final Document document, final EntityInstance entityInstance) {
        entityAttributes.forEach((entityAttribute) -> {
            var entityAttributeDetail = entityAttribute.getLastDetail();
            var fieldName = entityAttributeDetail.getEntityAttributeName();
            var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

            if (entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
                var entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);

                if(entityBooleanAttribute != null) {
                    var booleanAttribute = entityBooleanAttribute.getBooleanAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ", \"booleanAttribute = \"" + booleanAttribute + "\"");
                    }
                    document.add(new Field(fieldName, booleanAttribute.toString(), FieldTypes.NOT_STORED_NOT_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
                var entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);

                if(entityNameAttribute != null) {
                    var nameAttribute = entityNameAttribute.getNameAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ", \"nameAttribute = \"" + nameAttribute + "\"");
                    }
                    document.add(new Field(fieldName, nameAttribute, FieldTypes.NOT_STORED_NOT_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                var entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);
                
                if(entityIntegerAttribute != null) {
                    var integerAttribute = entityIntegerAttribute.getIntegerAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" integerAttribute = \"" + integerAttribute + "\"");
                    }
                    document.add(new IntPoint(fieldName, integerAttribute));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                var entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);
                
                if(entityLongAttribute != null) {
                    var longAttribute = entityLongAttribute.getLongAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" longAttribute = \"" + longAttribute + "\"");
                    }
                    document.add(new LongPoint(fieldName, longAttribute));
                }
            } else if (language != null && entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
                var entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language);
                
                if(entityStringAttribute != null) {
                    var stringAttribute = entityStringAttribute.getStringAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName = \"" + fieldName + ",\" stringAttribute = \"" + stringAttribute + "\"");
                    }
                    document.add(new Field(fieldName, stringAttribute, FieldTypes.NOT_STORED_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                var entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);
                
                if(entityGeoPointAttribute != null) {
                    var latitude = entityGeoPointAttribute.getLatitude();
                    var longitude = entityGeoPointAttribute.getLongitude();
                    var elevation = entityGeoPointAttribute.getElevation();
                    var altitude = entityGeoPointAttribute.getAltitude();
                    
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName = \"" + fieldName + ",\" latitude = \"" + latitude + ",\" longitude = \"" + longitude + ",\" elevation = \"" + elevation + ",\" altitude = \"" + altitude + "\"");
                    }
                    
                    document.add(new IntPoint(fieldName + IndexConstants.INDEX_SUBFIELD_SEPARATOR + IndexSubfields.latitude.name(), latitude));
                    document.add(new IntPoint(fieldName + IndexConstants.INDEX_SUBFIELD_SEPARATOR + IndexSubfields.longitude.name(), longitude));
                    
                    if(elevation != null) {
                        document.add(new LongPoint(fieldName + IndexConstants.INDEX_SUBFIELD_SEPARATOR + IndexSubfields.elevation.name(), elevation));
                    }
                    
                    if(altitude != null) {
                        document.add(new LongPoint(fieldName + IndexConstants.INDEX_SUBFIELD_SEPARATOR + IndexSubfields.altitude.name(), altitude));
                    }
                }
            } else if (language != null && entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                // TODO: MIME type should be taken into account
                var entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);
                
                if(entityClobAttribute != null) {
                    var clobAttribute = entityClobAttribute.getClobAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" clobAttribute = \"" + clobAttribute + "\"");
                    }
                    document.add(new Field(fieldName, clobAttribute, FieldTypes.NOT_STORED_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                var entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);
                
                if(entityDateAttribute != null) {
                    var dateAttribute = entityDateAttribute.getDateAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" dateAttribute = \"" + dateAttribute + "\"");
                    }
                    document.add(new IntPoint(fieldName, dateAttribute));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                var entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);
                
                if(entityTimeAttribute != null) {
                    var timeAttribute = entityTimeAttribute.getTimeAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" dateAttribute = \"" + timeAttribute + "\"");
                    }
                    document.add(new LongPoint(fieldName, timeAttribute));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                var entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);
                
                if(entityListItemAttribute != null) {
                    var entityListItemName = entityListItemAttribute.getEntityListItem().getLastDetail().getEntityListItemName();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" entityListItemName = \"" + entityListItemName + "\"");
                    }
                    document.add(new Field(fieldName, entityListItemName, FieldTypes.NOT_STORED_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                var entityMultipleListItemAttributes = coreControl.getEntityMultipleListItemAttributes(entityAttribute, entityInstance);
                if (entityMultipleListItemAttributes != null && !entityMultipleListItemAttributes.isEmpty()) {
                    var entityListItemNamesBuilder = new StringBuilder();
                    entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) -> {
                        if(entityListItemNamesBuilder.length() != 0) {
                            entityListItemNamesBuilder.append(' ');
                        }
                        
                        entityListItemNamesBuilder.append(entityMultipleListItemAttribute.getEntityListItem().getLastDetail().getEntityListItemName());
                    });
                    var entityListItemNames = entityListItemNamesBuilder.toString();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" entityListItemNames = \"" + entityListItemNames + "\"");
                    }
                    document.add(new Field(fieldName, entityListItemNames, FieldTypes.NOT_STORED_TOKENIZED));
                }
            }
        });
    }

    private void indexEntityTags(final Document document, final EntityInstance entityInstance) {
        var entityTags = tagControl.getEntityTagsByTaggedEntityInstance(entityInstance);

        entityTags.stream().map((entityTag) -> entityTag.getTag().getLastDetail()).forEach((tagDetail) -> {
            var tagScopeName = tagDetail.getTagScope().getLastDetail().getTagScopeName();
            var tagName = tagDetail.getTagName();

            document.add(new Field(tagScopeName, tagName, FieldTypes.NOT_STORED_TOKENIZED));
        });
    }

    private void indexEntityAppearance(final Document document, final EntityInstance entityInstance) {
        var entityAppearance = appearanceControl.getEntityAppearance(entityInstance);

        if(entityAppearance != null) {
            var entityAppearanceName = entityAppearance.getAppearance().getLastDetail().getAppearanceName();

            document.add(new Field(IndexFields.appearance.name(), entityAppearanceName, FieldTypes.NOT_STORED_TOKENIZED));
        }
    }

    protected void addEntityInstanceToDocument(final Document document, final EntityInstance entityInstance, final BasePK basePK) {
        document.add(new Field(IndexFields.entityRef.name(), basePK.getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexFields.entityInstanceId.name(), entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));
    }

    protected void addEntityInstanceFieldsToDocument(final Document document, final EntityInstance entityInstance) {
        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAliases(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);
        indexEntityAppearance(document, entityInstance);
    }

    protected Document newDocumentWithEntityInstanceFields(final EntityInstance entityInstance, final BasePK basePK) {
        final var document = new Document();

        addEntityInstanceToDocument(document, entityInstance, basePK);
        addEntityInstanceFieldsToDocument(document, entityInstance);

        return document;
    }

    /**
     * Create a Lucene IndexWriter.
     */
    protected void openIndexWriter(final Analyzer analyzer) {
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info(">>> getIndexWriter");
        }

        var createIndex = indexStatus.getCreatedTime() != null ? false : true;
        
        if(createIndex) {
            checkIndexDirectory(eea, indexStatus);
        }

        // indexCreatedTime is rechecked because if checkIndexDirectory was called,
        // it will be set to the time the directory was created, vs. remaining null.
        if(!hasExecutionErrors(eea)) {
            try {
                Directory fsDir = FSDirectory.open(Paths.get(index.getLastDetail().getDirectory()));
                var indexWriterConfig = new IndexWriterConfig(analyzer);

                indexWriterConfig.setMergeScheduler(new SerialMergeScheduler());
                indexWriterConfig.setOpenMode(createIndex ? IndexWriterConfig.OpenMode.CREATE : IndexWriterConfig.OpenMode.APPEND);

                indexWriter = new IndexWriter(fsDir, indexWriterConfig);
            } catch (IOException ioe1) {
                // indexWriter will remain null, signaling that index was not able to be opened
                if(IndexerDebugFlags.LogBaseIndexing) {
                    log.info("--- new IndexWriter failed");
                }
                
                try {
                    if(indexWriter != null) {
                        indexWriter.close();
                    }
                } catch (IOException ioe2) {
                    // ioe2 discarded.
                } finally {
                    indexWriter = null;
                }
                
                handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), ioe1.getMessage());
            }
        }
        
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info("<<< getIndexWriter");
        }
    }
    
    protected void closeIndexWriter() {
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info(">>> closeIndexWriter");
        }
        
        try {
            indexWriter.commit();
            indexWriter.close();
        } catch(IOException ioe) {
            // unrecoverable error
            // TODO: Index should be marked as possibly invalid
            if(IndexerDebugFlags.LogBaseIndexing) {
                log.info("--- indexWriter.close failed");
            }

            handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), ioe.getMessage());
        }

        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info("<<< closeIndexWriter");
        }
    }
    
    private void checkIndexDirectory(final ExecutionErrorAccumulator eea, final IndexStatus indexStatus) {
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info("--- checkIndexDirectory, index = " + index);
        }
        var indexCreatedTime = indexStatus.getCreatedTime();
        
        if(indexCreatedTime == null) {
            indexCreatedTime = createIndexDirectory(eea);
            
            if(!hasExecutionErrors(eea)) {
                indexStatus.setCreatedTime(indexCreatedTime);
            }
        }
    }
    
    private Long createIndexDirectory(final ExecutionErrorAccumulator eea) {
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info(">>> createIndexDirectory, index = " + index);
        }
        Long indexCreatedTime = null;
        var strDirectory = index.getLastDetail().getDirectory();
        var directory = new File(strDirectory);
        
        if(directory.exists()) {
            if(directory.isDirectory()) {
                var files = directory.listFiles();
                
                if(files == null) {
                    handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), "listFiles failed for " + strDirectory);
                } else {
                    var numFiles = files.length;

                    for(var i = 0; i < numFiles ; i++) {
                        var indivFile = files[i];

                        if(indivFile.isFile()) {
                            if(!indivFile.delete()) {
                                handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), "delete failed for " + indivFile.getPath());
                            }
                        }
                    }
                }
            } else {
                handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), "directory isn't a directory:  " + directory.getPath());
            }
        } else {
            if(!directory.mkdirs()) {
                handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), "mkdirs failed for " + directory.getPath());
            }
        }
        
        if(!hasExecutionErrors(eea)) {
            indexCreatedTime = System.currentTimeMillis();
        }
        
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info("<<< createIndexDirectory, indexCreatedTime = " + indexCreatedTime);
        }
        return indexCreatedTime;
    }
    
    public void forceReindex() {
        indexStatus.setCreatedTime(null);
    }
    
    protected Analyzer getAnalyzer() {
        return new BasicAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }

    protected abstract BE getEntity(final EntityInstance entityInstance);
    
    protected abstract Document convertToDocument(final EntityInstance entityInstance, final BE item);
    
    protected void addEntityToIndex(final EntityInstance entityInstance, final BE baseEntity)
        throws IOException {
        var document = convertToDocument(entityInstance, baseEntity);
        
        if(document != null) {
            indexWriter.addDocument(document);
        }
    }
    
    protected void removeEntityFromIndex(final BE baseEntity)
        throws IOException {
        indexWriter.deleteDocuments(new Term(IndexFields.entityRef.name(), baseEntity.getPrimaryKey().getEntityRef()));
    }

    public void updateIndex(final EntityInstance entityInstance) {
        var baseEntity = getEntity(entityInstance);
        
        if(baseEntity != null) {
            var entityTime = coreControl.getEntityTime(entityInstance);
            
            if(entityTime != null) {
                var modifiedTime = entityTime.getModifiedTime();
                var deletedTime = entityTime.getDeletedTime();
                
                try {
                    if(modifiedTime != null || deletedTime != null) {
                        removeEntityFromIndex(baseEntity);
                    }

                    if(deletedTime == null) {
                        addEntityToIndex(entityInstance, baseEntity);
                    }
                } catch(IOException ioe) {
                    handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), ioe.getMessage());
                }
            }
        }
    }

}
