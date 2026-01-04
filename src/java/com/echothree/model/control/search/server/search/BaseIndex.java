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

package com.echothree.model.control.search.server.search;

import com.echothree.model.control.index.common.exception.IndexUnavailableException;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.search.common.exception.SearchIOErrorException;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public abstract class BaseIndex<R extends Object>
        extends BaseLogic {
    
    protected ExecutionErrorAccumulator eea;
    protected IndexControl indexControl;
    protected Index index;
    
    private void init(ExecutionErrorAccumulator eea, IndexControl indexControl, Index index) {
        this.eea = eea;
        this.indexControl = indexControl;
        this.index = index;
    }
    
    protected BaseIndex(ExecutionErrorAccumulator eea, IndexControl indexControl, Index index) {
        init(eea, indexControl, index);
    }
    
    private Log log;
    
    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }

        return log;
    }

    public R execute() {
        return openIndex();
    }
    
    protected abstract R useIndex(IndexReader ir)
            throws IOException;
    
    protected R openIndex() {
        final var indexStatus = indexControl.getIndexStatus(index);
        R result = null;
        
        if(indexStatus.getCreatedTime() == null) {
            handleExecutionError(IndexUnavailableException.class, eea, ExecutionErrors.IndexUnavailable.name());
        } else {
            try(final Directory directory = FSDirectory.open(Paths.get(index.getLastDetail().getDirectory()))) {
                try(final IndexReader ir = DirectoryReader.open(directory)) {
                    if(!eea.hasExecutionErrors()) {
                        result = useIndex(ir);
                    }
                }
            } catch(IOException ioe) {
                handleExecutionError(SearchIOErrorException.class, eea, ExecutionErrors.SearchIOError.name(), ioe.getMessage());
            }
        }
        
        return result;
    }

}
