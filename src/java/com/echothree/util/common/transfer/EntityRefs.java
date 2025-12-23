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

package com.echothree.util.common.transfer;

import com.google.common.base.Splitter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityRefs
        implements Serializable {

    private Log log = LogFactory.getLog(EntityRefs.class);

    private Set<String> componentVendorNames = new HashSet<>();
    private Map<String, Set<String>> entityTypeNames = new HashMap<>();
    private Map<String, Map<String, Set<String>>> entityRefs = new HashMap<>();

    private void addComponentVendors(String componentVendorNames) {
        if(componentVendorNames != null) {
            for(var splitComponentVendorName : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(componentVendorNames).toArray(new String[0])) {
                var splitComponentVendorNameParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(splitComponentVendorName).toArray(new String[0]);

                if(splitComponentVendorNameParts.length == 1) {
                    this.componentVendorNames.add(splitComponentVendorNameParts[0]);
                } else {
                    log.error("Invalid componentVendorName: " + splitComponentVendorName);
                }
            }
        }
    }

    private void addEntityTypeNames(String entityTypeNames) {
        if(entityTypeNames != null) {
            for(var splitEntityTypeName : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(entityTypeNames).toArray(new String[0])) {
                var splitEntityTypeNameParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(splitEntityTypeName).toArray(new String[0]);

                if(splitEntityTypeNameParts.length == 2) {
                    // Check for, and add, the ComponentVendorName.
                    var entityTypes = this.entityTypeNames.get(splitEntityTypeNameParts[0]);

                    if(entityTypes == null) {
                        entityTypes = new HashSet<>(1);
                        this.entityTypeNames.put(splitEntityTypeNameParts[0], entityTypes);
                    }

                    // Add the EntityTypeName.
                    entityTypes.add(splitEntityTypeNameParts[1]);
                } else {
                    log.error("Invalid entityTypeName: " + splitEntityTypeName);
                }
            }
        }
    }

    private void addEntityRefs(String entityRefs) {
        if(entityRefs != null) {
            for(var splitEntityRef : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(entityRefs).toArray(new String[0])) {
                var splitEntityRefParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(splitEntityRef).toArray(new String[0]);

                if(splitEntityRefParts.length == 3) {
                    // Check for, and add, the ComponentVendorName.
                    var entityTypes = this.entityRefs.get(splitEntityRefParts[0]);

                    if(entityTypes == null) {
                        entityTypes = new HashMap<>(1);
                        this.entityRefs.put(splitEntityRefParts[0], entityTypes);
                    }

                    // Check for, and add, the EntityTypeName.
                    var entityUniqueIds = entityTypes.get(splitEntityRefParts[1]);

                    if(entityUniqueIds == null) {
                        entityUniqueIds = new HashSet<>(1);
                        entityTypes.put(splitEntityRefParts[1], entityUniqueIds);
                    }

                    // Add the UniqueEntityId.
                    entityUniqueIds.add(splitEntityRefParts[2]);
                } else {
                    log.error("Invalid entityRef: " + splitEntityRef);
                }
            }
        }
    }

    /** Creates a new instance of EntityRefs */
    public EntityRefs(String componentVendorNames, String entityTypeNames, String entityRefs) {
        addComponentVendors(componentVendorNames);
        addEntityTypeNames(entityTypeNames);
        addEntityRefs(entityRefs);
    }

    private boolean containsCompnenentVendorName(String componentVendorName) {
        return componentVendorNames.contains(componentVendorName);
    }

    private boolean containsEntityTypeName(String componentVendorName, String entityTypeName) {
        var entityTypes = this.entityTypeNames.get(componentVendorName);

        return entityTypes == null ? false : entityTypes.contains(entityTypeName);
    }

    private boolean containsEntityRef(String componentVendorName, String entityTypeName, String entityUniqueId) {
        var entityTypes = this.entityRefs.get(componentVendorName);
        var entityUniqueIds = entityTypes == null ? null : entityTypes.get(entityTypeName);

        return entityUniqueIds == null ? false : entityUniqueIds.contains(entityUniqueId);
    }

    public boolean contains(String entityRef) {
        var result = false;
        var entityRefParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(entityRef).toArray(new String[0]);

        if(entityRefParts.length == 3) {
            var componentVendorName = entityRefParts[0];
            var entityTypeName = entityRefParts[1];
            var entityUniqueId = entityRefParts[2];

            result = containsCompnenentVendorName(componentVendorName) || containsEntityTypeName(componentVendorName, entityTypeName)
                    || containsEntityRef(componentVendorName, entityTypeName, entityUniqueId);
        } else {
            log.error("Invalid entityRef: " + entityRef);
        }

        return result;
    }

}
