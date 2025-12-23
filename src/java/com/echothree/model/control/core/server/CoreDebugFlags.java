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

package com.echothree.model.control.core.server;

public interface CoreDebugFlags {
    
    /** Tell sendEvent to log every event that it is told to send.
     */
    public static final boolean LogSentEvents = false;
    
    /** Tell getEntityInstanceByBasePK to log if it is passed a null BasePK, a componentVendorName
     * that cannot be resolved, or a entityType that cannot be resolved.
     */
    public static final boolean LogUnresolvedEntityInstances = true;
    
    /** Tell getEntityInstanceByBasePK to log each step of resolving a BasePK into an EntityInstance.
     */
    public static final boolean LogEntityInstanceResolution = false;
    
    /** Turns on informational messages about Entity Locks in EntityLockControl.
     */
    public static final boolean LogEntityLocks = false;
    
}
