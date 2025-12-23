// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetCacheEntryResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.CacheEntryDependencyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.view.client.web.WebConstants;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class CacheTag
        extends BaseTag
        implements TryCatchFinally {
    
    protected static ConcurrentMap<String, Lock> cacheEntryLocks = new ConcurrentHashMap<>();
    
    protected Lock lock;
    
    protected String key;
    protected String validForTime;
    protected String validForTimeUnitOfMeasureTypeName;
    
    private void init() {
        key = null;
        validForTime = null;
        validForTimeUnitOfMeasureTypeName = null;
    }
    
    /** Creates a new instance of CacheTag */
    public CacheTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public void setValidForTime(String validForTime) {
        this.validForTime = validForTime;
    }

    public void setValidForTimeUnitOfMeasureTypeName(String validForTimeUnitOfMeasureTypeName) {
        this.validForTimeUnitOfMeasureTypeName = validForTimeUnitOfMeasureTypeName;
    }
    
    protected static class EntityRefs {
        
        protected Set<String> entityRefs = new HashSet<>();
        
        protected EntityRefs previousEntityRefs;
        
        public EntityRefs(EntityRefs previousEntityRefs) {
            this.previousEntityRefs = previousEntityRefs;
        }

        /**
         * @return the entityRefs
         */
        public Set<String> getEntityRefs() {
            return entityRefs;
        }

        /**
         * @return the previousEntityRefs
         */
        public EntityRefs getPreviousEntityRefs() {
            return previousEntityRefs;
        }
        
        public void addEntityRefs(Set<String> newEntityRefs) {
            // Add to ourselves, and all our parents (in the cache of nested cache tags).
            for(var currentEntityRefs = this; currentEntityRefs != null ; currentEntityRefs = currentEntityRefs.getPreviousEntityRefs()) {
                currentEntityRefs.getEntityRefs().addAll(newEntityRefs);
            }
        }

    }

    protected Lock getCacheEntryLock() {
        var cacheEntryLock = cacheEntryLocks.get(key);
        
        if(cacheEntryLock == null) {
            Lock newCacheEntryLock = new ReentrantLock();
            
            cacheEntryLock = cacheEntryLocks.putIfAbsent(key, newCacheEntryLock);
            if(cacheEntryLock == null) {
                cacheEntryLock = newCacheEntryLock;
            }
        }
        
        return cacheEntryLock;
    }
    
    protected String getBaseType()
            throws JspException {
        String baseType;

        try {
            baseType = new ContentType(pageContext.getResponse().getContentType()).getBaseType();
        } catch(ParseException pe) {
            throw new JspException(pe);
        }

        return baseType;
    }

    protected EntityRefs getEntityRefs() {
        return (EntityRefs)pageContext.getAttribute(WebConstants.Attribute_ENTITY_REFS, PageContext.REQUEST_SCOPE);
    }
    
    protected boolean hasEnclosingEntityRefs() {
        return getEntityRefs() != null;
    }
    
    protected boolean pushEntityRefs() {
        var currentEntityRefs = getEntityRefs();

        pageContext.setAttribute(WebConstants.Attribute_ENTITY_REFS, new EntityRefs(currentEntityRefs), PageContext.REQUEST_SCOPE);
        
        return currentEntityRefs != null;
    }
    
    protected EntityRefs popEntityRefs() {
        var currentEntityRefs = getEntityRefs();

        pageContext.setAttribute(WebConstants.Attribute_ENTITY_REFS, currentEntityRefs.getPreviousEntityRefs(), PageContext.REQUEST_SCOPE);

        return currentEntityRefs;
    }
    
    protected CacheEntryTransfer getExistingCacheEntry()
            throws JspException {
        CacheEntryTransfer existingCacheEntry = null;
        
        try {
            var commandForm = CoreUtil.getHome().getGetCacheEntryForm();
            Set<String> commandOptions = new HashSet<>();
            var includeCacheEntryDependencies = hasEnclosingEntityRefs();

            commandForm.setCacheEntryKey(key);

            commandOptions.add(CoreOptions.CacheEntryIncludeClob);
            // If we're enclosed by another et:cache tag...
            if(includeCacheEntryDependencies) {
                // ...then we need to request all the dependencies for this key in case it already exists. These will
                // be added to the enclosing et:cache tag's dependencies.
                commandOptions.add(CoreOptions.CacheEntryIncludeCacheEntryDependencies);
            }
            commandForm.setOptions(commandOptions);

            var transferProperties = new TransferProperties()
                    .addClassAndProperty(CacheEntryTransfer.class, CoreProperties.MIME_TYPE)
                    .addClassAndProperty(MimeTypeTransfer.class, CoreProperties.MIME_TYPE_NAME);
            // If we're enclosed by another et:cache tag...
            if(includeCacheEntryDependencies) {
                // ...then limit the properties that are included on the CacheEntryDependency TOs.
                transferProperties.addClassAndProperty(CacheEntryDependencyTransfer.class, CoreProperties.ENTITY_INSTANCE)
                        .addClassAndProperties(EntityInstanceTransfer.class, CoreProperties.ENTITY_TYPE, CoreProperties.ENTITY_UNIQUE_ID, CoreProperties.ENTITY_REF)
                        .addClassAndProperties(EntityTypeTransfer.class, CoreProperties.ENTITY_TYPE_NAME, CoreProperties.COMPONENT_VENDOR)
                        .addClassAndProperties(ComponentVendorTransfer.class, CoreProperties.COMPONENT_VENDOR_NAME);
            }
            
            commandForm.setTransferProperties(transferProperties);

            var commandResult = CoreUtil.getHome().getCacheEntry(getUserVisitPK(), commandForm);
            if(commandResult.hasErrors()) {
                if(!commandResult.containsExecutionError(ExecutionErrors.UnknownCacheEntryKey.name())) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetCacheEntryResult)executionResult.getResult();
                
                existingCacheEntry = result.getCacheEntry();

                if(!existingCacheEntry.getMimeType().getMimeTypeName().equals(getBaseType())) {
                    throw new JspException("cacheEntry's mimeTypeName does not match pageContext's contentType");
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return existingCacheEntry;
    }

    protected void writeExistingCacheEntry(CacheEntryTransfer existingCacheEntry)
            throws JspException {
        var currentEntityRefs = getEntityRefs();

        try {
            pageContext.getOut().write(existingCacheEntry.getClob());
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }

        // If there is an et:cache tag enclosing this instance, then we need to add any returned dependencies to it.
        if(currentEntityRefs != null) {
            var cacheEntryDependencies = existingCacheEntry.getCacheEntryDependencies().getList();

            if(cacheEntryDependencies != null) {
                var size = cacheEntryDependencies.size();

                if(size != 0) {
                    Set<String> entityRefs = new HashSet<>(cacheEntryDependencies.size());

                    cacheEntryDependencies.forEach((cacheEntryDependency) -> {
                        entityRefs.add(cacheEntryDependency.getEntityInstance().getEntityRef());
                    });

                    currentEntityRefs.addEntityRefs(entityRefs);
                }
            }
        }
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        // We can only skip the body if the cache has the data.
        var returnCode = EVAL_BODY_BUFFERED;

        var cacheEntry = getExistingCacheEntry();
        
        // Make two attempts at getting the cached content, one before the rendering lock is
        // acquired, and one after it is acquired - covering the case of someone else rendering
        // it while we were waiting for acquisition.
        if (cacheEntry != null) {
            // Cache entry was found, skip the body.
            writeExistingCacheEntry(cacheEntry);

            returnCode = SKIP_BODY;
        } else {
            lock = getCacheEntryLock();
            lock.lock();
            
            cacheEntry = getExistingCacheEntry();

            if(cacheEntry != null) {
                // Cache entry was found, skip the body.
                writeExistingCacheEntry(cacheEntry);
                
                returnCode = SKIP_BODY;
            } else {
                pushEntityRefs();
            }
        }

        return returnCode;
    }
    
    @Override
    public int doAfterBody()
            throws JspException {
        if(bodyContent != null) {
            var body = bodyContent.getString();

            if(body != null) {
                try {
                    var commandForm = CoreUtil.getHome().getCreateCacheEntryForm();

                    commandForm.setCacheEntryKey(key);
                    commandForm.setMimeTypeName(getBaseType());
                    commandForm.setValidForTime(validForTime);
                    commandForm.setValidForTimeUnitOfMeasureTypeName(validForTimeUnitOfMeasureTypeName);
                    commandForm.setClob(body);

                    var currentEntityRefs = popEntityRefs();

                    var entityRefs = currentEntityRefs.getEntityRefs();
                    if(entityRefs.size() > 0) {
                        commandForm.setEntityRefs(entityRefs);
                    }

                    var commandResult = CoreUtil.getHome().createCacheEntry(getUserVisitPK(), commandForm);
                    if(commandResult.hasErrors() && !commandResult.containsExecutionError(ExecutionErrors.DuplicateCacheEntryKey.name())) {
                        getLog().error(commandResult);
                    }
                } catch(NamingException ne) {
                    throw new JspException(ne);
                }

                try {
                    bodyContent.clearBody();
                    bodyContent.write(body);
                    bodyContent.writeOut(bodyContent.getEnclosingWriter());
                } catch(IOException ioe) {
                    throw new JspException(ioe);
                }
            }
        }

        return SKIP_BODY;
    }

    @Override
    public void doCatch(Throwable t)
             throws Throwable {
        throw t;
    }

    @Override
    public void doFinally() {
        if(lock != null) {
            lock.unlock();
            lock = null;
        }
    }
    
}
