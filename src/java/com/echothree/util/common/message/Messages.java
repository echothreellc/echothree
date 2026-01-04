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

/* ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Struts", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.echothree.util.common.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>A class that encapsulates messages.  Messages can be either global
 * or they are specific to a particular bean property.</p>
 *
 * <p>Each individual message is described by an <code>Message</code>
 * object, which contains a message key (to be looked up in an appropriate
 * message resources database), and up to four placeholder arguments used for
 * parametric substitution in the resulting message.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - It is assumed that these objects
 * are created and manipulated only within the context of a single thread.
 * Therefore, no synchronization is required for access to internal
 * collections.</p>
 *
 * @since Struts 1.1
 */

public class Messages
        implements Serializable {
    
    // ----------------------------------------------------- Manifest Constants
    
    /**
     * The "property name" marker to use for BaseCommand messages, as opposed to
     * those related to a specific property.
     */
    public static final String EXECUTION_WARNING = "com.echothree.util.common.message.EXECUTION_WARNING";
    public static final String EXECUTION_ERROR = "com.echothree.util.common.message.EXECUTION_ERROR";
    public static final String SECURITY_MESSAGE = "com.echothree.util.common.message.SECURITY_MESSAGE";
    
    // ----------------------------------------------------- Instance Variables
    
    /**
     * The accumulated set of <code>Message</code> objects (represented
     * as an ArrayList) for each property, keyed by property name.
     */
    protected Map<String, MessageItem> messages = new HashMap<>();
    
    /**
     * The current number of the property/key being added.  This is used
     * to maintain the order messages are added.
     */
    protected int iCount = 0;
    
    // --------------------------------------------------------- Public Methods
    
    /**
     * Create an empty <code>Messages</code> object.
     */
    /** Creates a new instance of Messages */
    public Messages() {
        super();
    }
    
    /**
     * Create an <code>Messages</code> object initialized with the given
     * messages.
     *
     * @param messages The messages to be initially added to this object.
     * This parameter can be <code>null</code>.
     * @since Struts 1.1
     */
    /** Creates a new instance of Messages */
    public Messages(Messages messages) {
        super();
        this.add(messages);
    }
    
    /**
     * Add a message to the set of messages for the specified property.  An
     * order of the property/key is maintained based on the initial addition
     * of the property/key.
     *
     * @param property  Property name (or Messages.GLOBAL_MESSAGE)
     * @param message   The message to be added
     */
    public Messages add(String property, Message message) {
        var item = messages.get(property);
        Map<String, Message> hashMap;
        
        if(item == null) {
            hashMap = new HashMap<>();
            item = new MessageItem(hashMap, iCount++);
            
            messages.put(property, item);
        } else {
            hashMap = item.getHashMap();
        }
        
        hashMap.put(message.getKey(), message);

        return this;
    }
    
    /**
     * Adds the messages from the given <code>Messages</code> object to
     * this set of messages.  The messages are added in the order they are returned from
     * the properties() method.  If a message's property is already in the current
     * <code>Messages</code> object it is added to the end of the list for that
     * property.  If a message's property is not in the current list it is added to the end
     * of the properties.
     *
     * @param messages The <code>Messages</code> object to be added.
     * This parameter can be <code>null</code>.
     * @since Struts 1.1
     */
    public Messages add(Messages messages) {
        if(messages == null) {
            return this;
        }
        // loop over properties
        Iterator props = messages.properties();
        while(props.hasNext()) {
            var property = (String) props.next();
            
            // loop over messages for each property
            Iterator msgs = messages.get(property);
            while(msgs.hasNext()) {
                var msg = (Message)msgs.next();
                this.add(property, msg);
            }
        }

        return this;
    }
    
    /**
     * Clear all messages recorded by this object.
     */
    public void clear() {
        messages.clear();
    }
    
    /**
     * Return <code>true</code> if there are no messages recorded
     * in this collection, or <code>false</code> otherwise.
     * @since Struts 1.1
     */
    public boolean isEmpty(){
        return messages.isEmpty();
    }
    
    /**
     * Return the set of all recorded messages, without distinction
     * by which property the messages are associated with.  If there are
     * no messages recorded, an empty enumeration is returned.
     */
    public Iterator<Message> get() {
        if(messages.isEmpty()) {
            return Collections.<Message>emptyList().iterator();
        }
        
        List<Message> results = new ArrayList<>();
        List<MessageItem> actionItems = new ArrayList<>();
        
        for(var i = messages.values().iterator(); i.hasNext();) {
            actionItems.add(i.next());
        }
        
        // Sort MessageItems based on the initial order the
        // property/key was added to Messages.
        Collections.sort(actionItems, (MessageItem o1, MessageItem o2) -> o1.getOrder() - o2.getOrder());
        
        actionItems.forEach((ami) -> {
            for(var messages = ami.getHashMap().values().iterator(); messages.hasNext();) {
                results.add(messages.next());
            }
        });
        
        return results.iterator();
    }
    
    /**
     * Return the set of messages related to a specific property.
     * If there are no such messages, an empty enumeration is returned.
     *
     * @param property Property name (or Messages.GLOBAL_MESSAGE)
     */
    public Iterator<Message> get(String property) {
        var item = (MessageItem) messages.get(property);
        
        if(item == null) {
            return Collections.<Message>emptyList().iterator();
        } else {
            return item.getHashMap().values().iterator();
        }
    }
    
    public boolean containsKey(String property, String key) {
        var item = (MessageItem)messages.get(property);
        boolean result;
        
        if(item != null) {
            result = item.getHashMap().containsKey(key);
        } else {
            result = false;
        }
        
        return result;
    }
    
    public boolean containsKeys(String property, String... keys) {
        var item = (MessageItem)messages.get(property);
        var result = false;
        
        if(item != null) {
            for(var key : Arrays.asList(keys)) {
                result = item.getHashMap().containsKey(key);
                if(result) {
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Return the set of property names for which at least one message has
     * been recorded.  If there are no messages, an empty Iterator is returned.
     * If you have recorded global messages, the String value of
     * <code>Messages.GLOBAL_MESSAGE</code> will be one of the returned
     * property names.
     */
    public Iterator<String> properties() {
        return messages.keySet().iterator();
    }
    
    /**
     * Return the number of messages recorded for all properties (including
     * global messages).  <strong>NOTE</strong> - it is more efficient to call
     * <code>empty()</code> if all you care about is whether or not there are
     * any messages at all.
     */
    public int size() {
        var total = 0;
        
        for(Iterator i = messages.values().iterator(); i.hasNext();) {
            var ami = (MessageItem)i.next();
            total += ami.getHashMap().size();
        }
        
        return total;
    }
    
    /**
     * Return the number of messages associated with the specified property.
     *
     * @param property Property name (or Messages.GLOBAL_MESSAGE)
     */
    public int size(String property) {
        var ami = (MessageItem) messages.get(property);
        
        if(ami == null) {
            return 0;
        } else {
            return ami.getHashMap().size();
        }
    }
    
    /**
     * This class is used to store a set of messages associated with a
     * property/key and the position it was initially added to list.
     */
    protected static class MessageItem
            implements Serializable {
        
        /**
         * The list of <code>Message</code>s.
         */
        protected Map<String, Message> hashMap;
        
        /**
         * The position in the list of messages.
         */
        protected int iOrder;
        
        public MessageItem(Map<String, Message> hashMap, int iOrder) {
            this.hashMap = hashMap;
            this.iOrder = iOrder;
        }
        
        public Map<String, Message> getHashMap() {
            return hashMap;
        }
        
        public void setHashMap(Map<String, Message> hashMap) {
            this.hashMap = hashMap;
        }
        
        public int getOrder() {
            return iOrder;
        }
        
        public void setOrder(int iOrder) {
            this.iOrder = iOrder;
        }
        
        /**
         * Converts to a string representing the data contained within this set of MessageItem.
         */
        @Override
        public String toString() {
            return "{ hashMap = " + hashMap + ", iOrder = " + iOrder + " }";
        }
        
    }
    
    /**
     * Converts to a string representing the data contained within this set of Messages.
     */
    @Override
    public String toString() {
        return "{ messages = " + messages + ", iCount = " + iCount + " }";
    }
    
}
