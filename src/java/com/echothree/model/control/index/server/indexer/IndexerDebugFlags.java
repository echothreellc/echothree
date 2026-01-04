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

public interface IndexerDebugFlags {
    
    boolean LogBaseAnalyzer = false;
    boolean LogBaseIndexing = false;
    boolean LogContactMechanismIndexing = false;
    boolean LogContentCategoryIndexing = false;
    boolean LogEntityListItemIndexing = false;
    boolean LogEntityTypeIndexing = false;
    boolean LogForumMessageIndexing = false;
    boolean LogHarmonizedTariffScheduleCodeIndexing = false;
    boolean LogItemIndexing = false;
    boolean LogPartyIndexing = false;
    boolean LogSecurityRoleGroupIndexing = false;
    boolean LogSecurityRoleIndexing = false;
    
}
