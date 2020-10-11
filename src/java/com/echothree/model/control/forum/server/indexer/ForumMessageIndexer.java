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

package com.echothree.model.control.forum.server.indexer;

import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.ForumMessageAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.forum.server.entity.ForumClobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageDetail;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessageTypePartType;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class ForumMessageIndexer
        extends BaseIndexer<ForumMessage> {
    
    ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
    
    /** Creates a new instance of ForumMessageIndexer */
    public ForumMessageIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new ForumMessageAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected ForumMessage getEntity(final EntityInstance entityInstance) {
        return forumControl.getForumMessageByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final ForumMessage forumMessage) {
        ForumMessageDetail forumMessageDetail = forumMessage.getLastDetail();
        ForumThread forumThread = forumMessageDetail.getForumThread();
        List<ForumForumThread> forumForumThreads = forumControl.getForumForumThreadsByForumThread(forumThread);
        List<ForumMessageTypePartType> forumMessageTypePartTypes = forumControl.getForumMessageTypePartTypesByForumMessageTypeAndIncludeInIndex(forumMessageDetail.getForumMessageType());
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, forumMessage.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));
        
        document.add(new Field(IndexConstants.IndexField_ForumMessageName, forumMessageDetail.getForumMessageName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_ForumThreadName, forumThread.getLastDetail().getForumThreadName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new LongPoint(IndexConstants.IndexField_PostedTime, forumMessageDetail.getPostedTime()));

        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        forumMessageTypePartTypes.stream().map((forumMessageTypePartType) -> forumMessageTypePartType.getForumMessagePartType()).forEach((forumMessagePartType) -> {
            ForumMessagePart forumMessagePart = forumControl.getBestForumMessagePart(forumMessage, forumMessagePartType, language);
            if (forumMessagePart != null) {
                MimeTypeUsageType mimeTypeUsageType = forumMessagePartType.getMimeTypeUsageType();

                if(mimeTypeUsageType == null) {
                    String forumMessagePartTypeName = forumMessagePartType.getForumMessagePartTypeName();
                    String string = forumControl.getForumStringMessagePart(forumMessagePart).getString();
                    
                    document.add(new Field(forumMessagePartTypeName, string, FieldTypes.NOT_STORED_TOKENIZED));
                    document.add(new SortedDocValuesField(forumMessagePartTypeName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                            new BytesRef(string)));
                } else {
                    String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

                    if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
                        ForumClobMessagePart forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

                        // TODO: mime type conversion to text/plain happens here
                        document.add(new Field(forumMessagePartType.getForumMessagePartTypeName(), forumClobMessagePart.getClob(), FieldTypes.NOT_STORED_TOKENIZED));
                    } // Others are not supported at this time, DOCUMENT probably should be.
                }
            }
        });
        
        if(forumForumThreads.size() > 0) {
            StringBuilder forumNames = new StringBuilder();

            forumForumThreads.stream().forEach((forumForumThread) -> {
                if(forumNames.length() > 0) {
                    forumNames.append(' ');
                }
                
                forumNames.append(forumForumThread.getForum().getActiveDetail().getForumName());
            });
            
            document.add(new Field(IndexConstants.IndexField_ForumNames, forumNames.toString(), FieldTypes.NOT_STORED_NOT_TOKENIZED));
        }
        
        return document;
    }
   
}
