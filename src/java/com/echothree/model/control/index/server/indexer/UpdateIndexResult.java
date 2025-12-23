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

package com.echothree.model.control.index.server.indexer;

public class UpdateIndexResult {

    private long remainingTime;
    private boolean indexModified;
    private boolean indexingComplete;
    
    public UpdateIndexResult(long remainingTime, boolean indexModified, boolean indexingComplete) {
        this.remainingTime = remainingTime;
        this.indexModified = indexModified;
        this.indexingComplete = indexingComplete;
    }

    /**
     * Returns the remainingTime.
     * @return the remainingTime
     */
    public long getRemainingTime() {
        return remainingTime;
    }

    /**
     * Sets the remainingTime.
     * @param remainingTime the remainingTime to set
     */
    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Returns the indexModified.
     * @return the indexModified
     */
    public boolean getIndexModified() {
        return indexModified;
    }

    /**
     * Sets the indexModified.
     * @param indexModified the indexModified to set
     */
    public void setIndexModified(boolean indexModified) {
        this.indexModified = indexModified;
    }
    
    /**
     * Returns the indexingComplete.
     * @return the indexingComplete
     */
    public boolean getIndexingComplete() {
        return indexingComplete;
    }

    /**
     * Sets the indexingComplete.
     * @param indexingComplete the indexingComplete to set
     */
    public void setIndexingComplete(boolean indexingComplete) {
        this.indexingComplete = indexingComplete;
    }
    
}
