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

import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.BaseWrapper;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseTransferUtils {

    private static Log log = LogFactory.getLog(BaseTransferUtils.class);
    
    private BaseTransferUtils() {
        super();
    }

    private static class BaseTransferUtilsHolder {
        static BaseTransferUtils instance = new BaseTransferUtils();
    }

    public static BaseTransferUtils getInstance() {
        return BaseTransferUtilsHolder.instance;
    }

    private void getEntityRefsFromBaseWrapper(EntityRefExclusions entityRefExclusions, Set<String> entityRefs, Set<Object> visitedObjects,
            BaseWrapper<?> baseWrapper, int indentCount) {
        var collection = baseWrapper.getCollection();

        if(!collection.isEmpty()) {
            collection.stream().filter((nextDependsOn) -> !visitedObjects.contains(nextDependsOn)).forEach((nextDependsOn) -> {
                getEntityRefs(entityRefExclusions, entityRefs, visitedObjects, nextDependsOn, indentCount + 1);
            });
        }
    }

    private Set<String> getEntityRefs(EntityRefExclusions entityRefExclusions, Set<String> entityRefs, Set<Object> visitedObjects, Object dependsOn,
            int indentCount) {
        if(BaseTransferUtilsDebugFlags.LogVisits) {
            log.info(StringUtils.getInstance().getIndent(4, indentCount) + "Visiting " + dependsOn);
        }
        visitedObjects.add(dependsOn);
        
        if(dependsOn instanceof BaseTransfer) {
            var entityInstance = ((BaseTransfer)dependsOn).getEntityInstance();
            var includeMethods = true;
            
            if(entityInstance != null) {
                var nextDependsOnEntityRef = entityInstance.getEntityRef();

                if(nextDependsOnEntityRef != null) {
                    if(entityRefExclusions != null && nextDependsOnEntityRef != null && entityRefExclusions.contains(nextDependsOnEntityRef)) {
                        if(BaseTransferUtilsDebugFlags.LogGetEntityRefs) {
                            log.info(StringUtils.getInstance().getIndent(4, indentCount) + "Excluding " + nextDependsOnEntityRef);
                        }

                        includeMethods = false;
                    } else {
                        if(BaseTransferUtilsDebugFlags.LogGetEntityRefs) {
                            log.info(StringUtils.getInstance().getIndent(4, indentCount) + "Including " + nextDependsOnEntityRef);
                        }

                        entityRefs.add(nextDependsOnEntityRef);
                     }
                }
            }
            
            if(includeMethods) {
                var methods = dependsOn.getClass().getMethods();
                
                for(var method : methods) {
                    var name = method.getName();

                    if(name.startsWith("get")) {
                        try {
                            var returnType = method.getReturnType();

                            if(BaseTransfer.class.isAssignableFrom(returnType)) {
                                // If it's a BaseTransfer...
                                var nextDependsOn = (BaseTransfer)method.invoke(dependsOn);
                                
                                if(nextDependsOn != null) {
                                    if(!visitedObjects.contains(nextDependsOn)) {
                                        getEntityRefs(entityRefExclusions, entityRefs, visitedObjects, nextDependsOn, indentCount + 1);
                                    } else {
                                        if(BaseTransferUtilsDebugFlags.LogVisits) {
                                            log.info(StringUtils.getInstance().getIndent(4, indentCount) + "Already visited " + nextDependsOn);
                                        }
                                    }
                                }
                            } else if(BaseWrapper.class.isAssignableFrom(returnType)) {
                                // If it's a BaseWrapper
                                var baseWrapper = (BaseWrapper<?>)method.invoke(dependsOn);

                                if(baseWrapper != null) {
                                    if(BaseTransferUtilsDebugFlags.LogGetEntityRefs) {
                                        log.info(StringUtils.getInstance().getIndent(4, indentCount) + "BaseWrapper found: " + name.substring(3));
                                    }

                                    getEntityRefsFromBaseWrapper(entityRefExclusions, entityRefs, visitedObjects, baseWrapper, indentCount);
                                }
                            }
                        } catch(IllegalAccessException iea) {
                            throw new RuntimeException(iea);
                        } catch(IllegalArgumentException iea) {
                            throw new RuntimeException(iea);
                        } catch(InvocationTargetException ite) {
                            throw new RuntimeException(ite);
                        }
                    }
                }
            }
        } else if(dependsOn instanceof BaseWrapper) {
            var baseWrapper = (BaseWrapper<?>)dependsOn;
            
            if(baseWrapper != null) {
                if(BaseTransferUtilsDebugFlags.LogGetEntityRefs) {
                    log.info(StringUtils.getInstance().getIndent(4, indentCount) + "BaseWrapper passed in");
                }
                
                getEntityRefsFromBaseWrapper(entityRefExclusions, entityRefs, visitedObjects, baseWrapper, indentCount);
            }
        }
        
        return entityRefs;
    }

    public Set<String> getEntityRefs(EntityRefExclusions entityRefExclusions, Object object) {
        Set<String> entityRefs = new HashSet<>();
        Set<Object> visitedObjects = new HashSet<>();

        if(BaseTransferUtilsDebugFlags.LogGetEntityRefs) {
            log.info("Starting getEntityRefs for " + object);
        }
        
        if(object instanceof BaseTransfer || object instanceof BaseWrapper) {
            getEntityRefs(entityRefExclusions, entityRefs, visitedObjects, object, 1);
        } else {
            throw new IllegalArgumentException("Object for getEntityRefs(...) must be a BaseTransfer or BaseWrapper object.");
        }
        
        if(BaseTransferUtilsDebugFlags.LogGetEntityRefs) {
            log.info("Ending getEntityRefs");
        }
        
        return entityRefs;
    }

}
