// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.IndexSubfields;
import com.echothree.model.control.index.common.exception.IndexIOErrorException;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.index.server.analysis.BasicAnalyzer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexDetail;
import com.echothree.model.data.index.server.entity.IndexStatus;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
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
    
    protected CoreControl coreControl = Session.getModelController(CoreControl.class);
    protected IndexControl indexControl = Session.getModelController(IndexControl.class);
    protected TagControl tagControl = Session.getModelController(TagControl.class);
    protected WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    protected Log log = LogFactory.getLog(this.getClass());

    protected ExecutionErrorAccumulator eea;
    protected Index index;
    
    protected Language language;
    protected EntityType entityType;
    protected IndexStatus indexStatus;
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
            IndexDetail indexDetail = index.getLastDetail();

            this.language = indexDetail.getLanguage();
            this.entityType = indexDetail.getIndexType().getLastDetail().getEntityType();
            this.indexStatus = indexControl.getIndexStatusForUpdate(index);
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
    
    protected void indexWorkflowEntityStatus(final Document document, final WorkflowEntityStatus workflowEntityStatus) {
        document.add(new Field(workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflow().getLastDetail().getWorkflowName(),
                workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName(), FieldTypes.NOT_STORED_NOT_TOKENIZED));
    }

    /** Index an EntityInstance in all of its Workflows. */
    protected void indexWorkflowEntityStatuses(final Document document, final EntityInstance entityInstance) {
        workflowControl.getWorkflowsByEntityType(entityInstance.getEntityType()).stream().forEach((workflow) -> {
            List<WorkflowEntityStatus> workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);
            if (!workflowEntityStatuses.isEmpty()) {
                StringBuilder workflowStepNamesBuilder = new StringBuilder();
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

    protected void indexEntityTimes(final Document document, final EntityInstance entityInstance) {
        EntityTime entityTime = coreControl.getEntityTime(entityInstance);

        if(entityTime != null) {
            Long createdTime = entityTime.getCreatedTime();
            Long modifiedTime = entityTime.getModifiedTime();
            Long deletedTime = entityTime.getDeletedTime();

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

    protected void indexEntityAttributes(final Document document, final EntityInstance entityInstance) {
        entityAttributes.forEach((entityAttribute) -> {
            EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
            String fieldName = entityAttributeDetail.getEntityAttributeName();
            String entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
            if (entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
                EntityNameAttribute entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);
                
                if(entityNameAttribute != null) {
                    String nameAttribute = entityNameAttribute.getNameAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ", \"nameAttribute = \"" + nameAttribute + "\"");
                    }
                    document.add(new Field(fieldName, nameAttribute, FieldTypes.NOT_STORED_NOT_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                EntityIntegerAttribute entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);
                
                if(entityIntegerAttribute != null) {
                    Integer integerAttribute = entityIntegerAttribute.getIntegerAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" integerAttribute = \"" + integerAttribute + "\"");
                    }
                    document.add(new IntPoint(fieldName, integerAttribute));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                EntityLongAttribute entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);
                
                if(entityLongAttribute != null) {
                    Long longAttribute = entityLongAttribute.getLongAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" longAttribute = \"" + longAttribute + "\"");
                    }
                    document.add(new LongPoint(fieldName, longAttribute));
                }
            } else if (language != null && entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
                EntityStringAttribute entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language);
                
                if(entityStringAttribute != null) {
                    String stringAttribute = entityStringAttribute.getStringAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName = \"" + fieldName + ",\" stringAttribute = \"" + stringAttribute + "\"");
                    }
                    document.add(new Field(fieldName, stringAttribute, FieldTypes.NOT_STORED_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                EntityGeoPointAttribute entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);
                
                if(entityGeoPointAttribute != null) {
                    Integer latitude = entityGeoPointAttribute.getLatitude();
                    Integer longitude = entityGeoPointAttribute.getLongitude();
                    Long elevation = entityGeoPointAttribute.getElevation();
                    Long altitude = entityGeoPointAttribute.getAltitude();
                    
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
                EntityClobAttribute entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);
                
                if(entityClobAttribute != null) {
                    String clobAttribute = entityClobAttribute.getClobAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" clobAttribute = \"" + clobAttribute + "\"");
                    }
                    document.add(new Field(fieldName, clobAttribute, FieldTypes.NOT_STORED_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                EntityDateAttribute entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);
                
                if(entityDateAttribute != null) {
                    Integer dateAttribute = entityDateAttribute.getDateAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" dateAttribute = \"" + dateAttribute + "\"");
                    }
                    document.add(new IntPoint(fieldName, dateAttribute));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                EntityTimeAttribute entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);
                
                if(entityTimeAttribute != null) {
                    Long timeAttribute = entityTimeAttribute.getTimeAttribute();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" dateAttribute = \"" + timeAttribute + "\"");
                    }
                    document.add(new LongPoint(fieldName, timeAttribute));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                EntityListItemAttribute entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);
                
                if(entityListItemAttribute != null) {
                    String entityListItemName = entityListItemAttribute.getEntityListItem().getLastDetail().getEntityListItemName();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" entityListItemName = \"" + entityListItemName + "\"");
                    }
                    document.add(new Field(fieldName, entityListItemName, FieldTypes.NOT_STORED_TOKENIZED));
                }
            } else if (entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes = coreControl.getEntityMultipleListItemAttributes(entityAttribute, entityInstance);
                if (entityMultipleListItemAttributes != null && !entityMultipleListItemAttributes.isEmpty()) {
                    StringBuilder entityListItemNamesBuilder = new StringBuilder();
                    entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) -> {
                        if(entityListItemNamesBuilder.length() != 0) {
                            entityListItemNamesBuilder.append(' ');
                        }
                        
                        entityListItemNamesBuilder.append(entityMultipleListItemAttribute.getEntityListItem().getLastDetail().getEntityListItemName());
                    });
                    String entityListItemNames = entityListItemNamesBuilder.toString();
                    if(IndexerDebugFlags.LogBaseIndexing) {
                        log.info("--- fieldName =\"" + fieldName + ",\" entityListItemNames = \"" + entityListItemNames + "\"");
                    }
                    document.add(new Field(fieldName, entityListItemNames, FieldTypes.NOT_STORED_TOKENIZED));
                }
            }
        });
    }
    
    public void indexEntityTags(final Document document, final EntityInstance entityInstance) {
        List<EntityTag> entityTags = tagControl.getEntityTagsByTaggedEntityInstance(entityInstance);

        entityTags.stream().map((entityTag) -> entityTag.getTag().getLastDetail()).forEach((tagDetail) -> {
            String tagScopeName = tagDetail.getTagScope().getLastDetail().getTagScopeName();
            String tagName = tagDetail.getTagName();

            document.add(new Field(tagScopeName, tagName, FieldTypes.NOT_STORED_TOKENIZED));
        });
    }

    /**
     * Create a Lucene IndexWriter.
     */
    protected void openIndexWriter(final Analyzer analyzer) {
        if(IndexerDebugFlags.LogBaseIndexing) {
            log.info(">>> getIndexWriter");
        }
        
        boolean createIndex = indexStatus.getCreatedTime() != null ? false : true;
        
        if(createIndex) {
            checkIndexDirectory(eea, indexStatus);
        }

        // indexCreatedTime is rechecked because if checkIndexDirectory was called,
        // it will be set to the time the directory was created, vs. remaining null.
        if(!hasExecutionErrors(eea)) {
            try {
                Directory fsDir = FSDirectory.open(Paths.get(index.getLastDetail().getDirectory()));
                IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

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
        Long indexCreatedTime = indexStatus.getCreatedTime();
        
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
        String strDirectory = index.getLastDetail().getDirectory();
        File directory = new File(strDirectory);
        
        if(directory.exists()) {
            if(directory.isDirectory()) {
                File[] files = directory.listFiles();
                
                if(files == null) {
                    handleExecutionError(IndexIOErrorException.class, eea, ExecutionErrors.IndexIOError.name(), "listFiles failed for " + strDirectory);
                } else {
                    int numFiles = files.length;

                    for(int i = 0 ; i < numFiles ; i++) {
                        File indivFile = files[i];

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
        return new BasicAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }

    protected abstract BE getEntity(final EntityInstance entityInstance);
    
    protected abstract Document convertToDocument(final EntityInstance entityInstance, final BE item);
    
    protected void addEntityToIndex(final EntityInstance entityInstance, final BE baseEntity)
        throws IOException {
        Document document = convertToDocument(entityInstance, baseEntity);
        
        if(document != null) {
            indexWriter.addDocument(document);
        }
    }
    
    protected void removeEntityFromIndex(final BE baseEntity)
        throws IOException {
        indexWriter.deleteDocuments(new Term(IndexFields.entityRef.name(), baseEntity.getPrimaryKey().getEntityRef()));
    }

    public void updateIndex(final EntityInstance entityInstance) {
        BE baseEntity = getEntity(entityInstance);
        
        if(baseEntity != null) {
            EntityTime entityTime = coreControl.getEntityTime(entityInstance);
            
            if(entityTime != null) {
                Long modifiedTime = entityTime.getModifiedTime();
                Long deletedTime = entityTime.getDeletedTime();
                
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
