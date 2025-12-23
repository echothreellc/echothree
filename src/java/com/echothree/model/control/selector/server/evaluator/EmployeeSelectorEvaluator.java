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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EmployeeSelectorEvaluator
        extends BasePartySelectorEvaluator {
    
    protected PartyType partyType;
    
    /** Creates a new instance of EmployeeSelectorEvaluator */
    public EmployeeSelectorEvaluator(Session session, BasePK evaluatedBy) {
        super(session, evaluatedBy, EmployeeSelectorEvaluator.class);
        
        partyType = partyControl.getPartyTypeByName(PartyTypes.EMPLOYEE.name());
    }
    
    Long addPartyEntitiesToSelector(CachedSelectorWithTime cachedSelectorWithTime, List<EntityTime> entityTimes, long remainingTime) {
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info(">>> EmployeeSelectorEvaluator.addPartyEntitiesToSelector");
        var startTime = System.currentTimeMillis();
        long entityCount = 0;
        Long selectionTime = null;
        
        for(var entityTime : entityTimes) {
            entityCount++;
            if(!(entityCount % 10 == 0)) {
                if((System.currentTimeMillis() - startTime) > remainingTime)
                    break;
            }

            var entityInstance = entityTime.getEntityInstance();
            var party = PartyLogic.getInstance().getPartyFromEntityInstance(entityInstance);
            if(party == null) {
                log.error("--- EmployeeSelectorEvaluator.addPartyEntitiesToSelector found null Party: " + entityInstance);
            } else {
                var partyDetail = party.getLastDetail();
                
                selectionTime = entityTime.getCreatedTime();
                
                if(partyDetail.getPartyType().equals(partyType)) {
                    if(entityTime.getDeletedTime() == null) {
                        var selected = isPartySelected(cachedSelectorWithTime, entityInstance, party);
                        var employeeSelector = cachedSelectorWithTime.getSelector();
                        
                        if(selected) {
                            var selectorParty = selectorControl.getSelectorParty(employeeSelector, party);
                            
                            if(selectorParty == null) {
                                selectorControl.createSelectorParty(employeeSelector, party, evaluatedBy);
                            }
                        }
                    }
                }
            }
        }
        
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info("<<< EmployeeSelectorEvaluator.addPartyEntitiesToSelector, selectionTime = " + selectionTime);
        return selectionTime;
    }
    
    Long updatePartyEntitiesInSelector(CachedSelectorWithTime cachedSelectorWithTime, List<EntityTime> entityTimes, long remainingTime) {
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info(">>> EmployeeSelectorEvaluator.updatePartyEntitiesInSelector");
        var startTime = System.currentTimeMillis();
        long entityCount = 0;
        Long selectionTime = null;
        
        for(var entityTime : entityTimes) {
            entityCount++;
            if(!(entityCount % 10 == 0)) {
                if((System.currentTimeMillis() - startTime) > remainingTime)
                    break;
            }

            var entityInstance = entityTime.getEntityInstance();
            var party = PartyLogic.getInstance().getPartyFromEntityInstance(entityInstance);
            if(party == null) {
                log.error("--- EmployeeSelectorEvaluator.updatePartyEntitiesInSelector found null Party: " + entityInstance);
            } else {
                var partyDetail = party.getLastDetail();
                
                selectionTime = entityTime.getModifiedTime();
                
                if(partyDetail.getPartyType().equals(partyType)) {
                    if(entityTime.getDeletedTime() == null) {
                        var selected = isPartySelected(cachedSelectorWithTime, entityInstance, party);
                        var employeeSelector = cachedSelectorWithTime.getSelector();
                        
                        if(selected) {
                            var selectorParty = selectorControl.getSelectorParty(employeeSelector, party);
                            
                            if(selectorParty == null) {
                                selectorControl.createSelectorParty(employeeSelector, party, evaluatedBy);
                            }
                        } else {
                            var selectorParty = selectorControl.getSelectorPartyForUpdate(employeeSelector, party);
                            
                            if(selectorParty != null) {
                                selectorControl.deleteSelectorParty(selectorParty, evaluatedBy);
                            }
                        }
                    }
                }
            }
        }
        
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info("<<< EmployeeSelectorEvaluator.updatePartyEntitiesInSelector, selectionTime = " + selectionTime);
        return selectionTime;
    }
    
    Long removePartyEntitiesFromSelector(Selector employeeSelector, List<EntityTime> entityTimes, long remainingTime) {
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info(">>> EmployeeSelectorEvaluator.removeItemEntitiesFromOffer");
        var startTime = System.currentTimeMillis();
        long entityCount = 0;
        Long selectionTime = null;
        
        for(var entityTime : entityTimes) {
            entityCount++;
            if(!(entityCount % 10 == 0)) {
                if((System.currentTimeMillis() - startTime) > remainingTime)
                    break;
            }

            var entityInstance = entityTime.getEntityInstance();
            var party = PartyLogic.getInstance().getPartyFromEntityInstance(entityInstance);
            if(party == null) {
                log.error("--- EmployeeSelectorEvaluator.removePartyEntitiesFromSelector found null Party: " + entityInstance);
            } else {
                var partyDetail = party.getLastDetail();
                
                selectionTime = entityTime.getDeletedTime();
                
                if(partyDetail.getPartyType().equals(partyType)) {
                    var selectorParty = selectorControl.getSelectorPartyForUpdate(employeeSelector, party);
                    
                    if(selectorParty != null) {
                        selectorControl.deleteSelectorParty(selectorParty, evaluatedBy);
                    }
                }
            }
        }
        
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info("<<< EmployeeSelectorEvaluator.removeItemEntitiesFromOffer, selectionTime = " + selectionTime);
        return selectionTime;
    }
    
    public Long evaluate(Long maximumTime) {
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info(">>> EmployeeSelectorEvaluator.evaluate");
        
        long remainingTime = maximumTime;
        var componentVendor = componentControl.getComponentVendorByName(ComponentVendors.ECHO_THREE.name());
        
        if(componentVendor != null) {
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, EntityTypes.Party.name());
            
            if(entityType != null) {
                var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.EMPLOYEE.name());
                var employeeSelectors = selectorControl.getSelectorsBySelectorKind(selectorKind);
                
                for(var employeeSelector : employeeSelectors) {
                    var cachedSelectorWithTime = new CachedSelectorWithTime(employeeSelector);
                    
                    if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator) {
                        log.info("--- employeeSelector = " + employeeSelector);
                    }

                    var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(employeeSelector.getPrimaryKey());
                    var entityTime = eventControl.getEntityTime(entityInstance);
                    var entityCreatedTime = entityTime.getCreatedTime();
                    var entityModifiedTime = entityTime.getModifiedTime();
                    var lastModifiedTime = entityModifiedTime != null? entityModifiedTime: entityCreatedTime;
                    var lastEvaluationTime = cachedSelectorWithTime.getLastEvaluationTime();
                    
                    if(lastEvaluationTime != null) {
                        if(lastModifiedTime > lastEvaluationTime) {
                            if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
                                log.info("--- selector modified since last evaluation");
                            selectorControl.deleteSelectorPartiesBySelector(employeeSelector, evaluatedBy);
                            
                            cachedSelectorWithTime.setLastEvaluationTime(null);
                            cachedSelectorWithTime.setMaxEntityCreatedTime(null);
                            cachedSelectorWithTime.setMaxEntityModifiedTime(null);
                            cachedSelectorWithTime.setMaxEntityDeletedTime(null);
                        }
                    }

                    var selectionTime = cachedSelectorWithTime.getMaxEntityCreatedTime();
                    
                    List<EntityTime> entityTimes;
                    if(selectionTime == null) {
                        entityTimes = eventControl.getEntityTimesByEntityType(entityType);
                    } else {
                        entityTimes = eventControl.getEntityTimesByEntityTypeCreatedAfter(entityType, selectionTime);
                    }
                    
                    if(entityTimes != null) {
                        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
                            log.info("--- entityTimes.size() = " + entityTimes.size());

                        var indexingStartTime = System.currentTimeMillis();
                        selectionTime = addPartyEntitiesToSelector(cachedSelectorWithTime, entityTimes, remainingTime);
                        remainingTime -= System.currentTimeMillis() - indexingStartTime;
                    }
                    
                    if(selectionTime != null) {
                        cachedSelectorWithTime.setMaxEntityCreatedTime(selectionTime);
                    }
                    
                    if(remainingTime > 0) {
                        selectionTime = cachedSelectorWithTime.getMaxEntityModifiedTime();
                        if(selectionTime == null) {
                            selectionTime = Long.valueOf(0);
                        }
                        
                        entityTimes = eventControl.getEntityTimesByEntityTypeModifiedAfter(entityType, selectionTime);
                        
                        if(entityTimes != null) {
                            if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
                                log.info("--- entityTimes.size() = " + entityTimes.size());

                            var indexingStartTime = System.currentTimeMillis();
                            selectionTime = updatePartyEntitiesInSelector(cachedSelectorWithTime, entityTimes, remainingTime);
                            remainingTime -= System.currentTimeMillis() - indexingStartTime;
                        }
                        
                        if(selectionTime != null) {
                            cachedSelectorWithTime.setMaxEntityModifiedTime(selectionTime);
                        }
                    }
                    
                    if(remainingTime > 0) {
                        selectionTime = cachedSelectorWithTime.getMaxEntityDeletedTime();
                        if(selectionTime == null) {
                            selectionTime = Long.valueOf(0);
                        }
                        
                        entityTimes = eventControl.getEntityTimesByEntityTypeDeletedAfter(entityType, selectionTime);
                        
                        if(entityTimes != null) {
                            if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
                                log.info("--- entityTimes.size() = " + entityTimes.size());

                            var indexingStartTime = System.currentTimeMillis();
                            selectionTime = removePartyEntitiesFromSelector(employeeSelector, entityTimes, remainingTime);
                            remainingTime -= System.currentTimeMillis() - indexingStartTime;
                        }
                        
                        if(selectionTime != null) {
                            cachedSelectorWithTime.setMaxEntityDeletedTime(selectionTime);
                        }
                    }
                    
                    cachedSelectorWithTime.setLastEvaluationTime(session.getStartTime());
                }
            } // Error, unknown entityTypeName
        } // Error, unknown componentVendorName
        
        if(BaseSelectorEvaluatorDebugFlags.EmployeeSelectorEvaluator)
            log.info("<<< EmployeeSelectorEvaluator.evaluate, remainingTime = " + remainingTime);
        return remainingTime;
    }
    
}
