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

package com.echothree.model.control.forum.server.indexer;

import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.forum.server.analyzer.ForumMessageAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class ForumMessageIndexer
        extends BaseIndexer<ForumMessage> {
    
    ForumControl forumControl = Session.getModelController(ForumControl.class);
    
    /** Creates a new instance of ForumMessageIndexer */
    public ForumMessageIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new ForumMessageAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }
    
    @Override
    protected ForumMessage getEntity(final EntityInstance entityInstance) {
        return forumControl.getForumMessageByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final ForumMessage forumMessage) {
        var forumMessageDetail = forumMessage.getLastDetail();
        var forumThread = forumMessageDetail.getForumThread();
        var forumForumThreads = forumControl.getForumForumThreadsByForumThread(forumThread);
        var forumMessageTypePartTypes = forumControl.getForumMessageTypePartTypesByForumMessageTypeAndIncludeInIndex(forumMessageDetail.getForumMessageType());

        var document = newDocumentWithEntityInstanceFields(entityInstance, forumMessage.getPrimaryKey());

        document.add(new Field(IndexFields.forumMessageName.name(), forumMessageDetail.getForumMessageName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexFields.forumThreadName.name(), forumThread.getLastDetail().getForumThreadName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new LongPoint(IndexFields.postedTime.name(), forumMessageDetail.getPostedTime()));

        forumMessageTypePartTypes.stream().map((forumMessageTypePartType) -> forumMessageTypePartType.getForumMessagePartType()).forEach((forumMessagePartType) -> {
            var forumMessagePart = forumControl.getBestForumMessagePart(forumMessage, forumMessagePartType, language);
            if (forumMessagePart != null) {
                var mimeTypeUsageType = forumMessagePartType.getMimeTypeUsageType();

                if(mimeTypeUsageType == null) {
                    var forumMessagePartTypeName = forumMessagePartType.getForumMessagePartTypeName();
                    var string = forumControl.getForumStringMessagePart(forumMessagePart).getString();
                    
                    document.add(new Field(forumMessagePartTypeName, string, FieldTypes.NOT_STORED_TOKENIZED));
                    document.add(new SortedDocValuesField(forumMessagePartTypeName + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                            new BytesRef(string)));
                } else {
                    var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

                    if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
                        var forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

                        // TODO: mime type conversion to text/plain happens here
                        document.add(new Field(forumMessagePartType.getForumMessagePartTypeName(), forumClobMessagePart.getClob(), FieldTypes.NOT_STORED_TOKENIZED));
                    } // Others are not supported at this time, DOCUMENT probably should be.
                }
            }
        });
        
        if(forumForumThreads.size() > 0) {
            var forumNames = new StringBuilder();

            forumForumThreads.forEach((forumForumThread) -> {
                if(forumNames.length() > 0) {
                    forumNames.append(' ');
                }
                
                forumNames.append(forumForumThread.getForum().getLastDetail().getForumName());
            });
            
            document.add(new Field(IndexFields.forumNames.name(), forumNames.toString(), FieldTypes.NOT_STORED_NOT_TOKENIZED));
        }
        
        return document;
    }
   
}
