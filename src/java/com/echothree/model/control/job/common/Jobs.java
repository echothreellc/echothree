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

package com.echothree.model.control.job.common;

public enum Jobs {
    
    DATA_INDEXER,
    INVALIDATE_ABANDONED_USER_VISITS,
    LICENSE_UPDATE,
    PRINT_PRINTER_GROUP_JOBS,
    PROCESS_CHAIN_INSTANCE_STATUSES,
    PROCESS_QUEUED_EVENTS,
    PROCESS_WORKFLOW_TRIGGERS,
    REMOVE_INACTIVE_USER_KEYS,
    REMOVE_INVALIDATED_USER_VISITS,
    REMOVE_EXPIRED_ENTITY_LOCKS
    
}
