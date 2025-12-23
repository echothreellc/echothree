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

/**
 * <p>An encapsulation of an individual message returned by the
 * <code>validate()</code> method of an <code>ActionForm</code>, consisting
 * of a message key (to be used to look up message text in an appropriate
 * message resources database) plus up to four placeholder objects that can
 * be used for parametric replacement in the message text.</p>
 *
 * @since Struts 1.1
 */

public class Message
        implements Serializable {
    
    /**
     * Construct an action message with no replacement values.
     *
     * @param key Message key for this message
     */
    /** Creates a new instance of Message */
    public Message(String key) {
        this.key = key;
    }
    
    /**
     * Construct an action message with the specified replacement values.
     *
     * @param key Message key for this message
     * @param values One or more replacement values
     */
    /** Creates a new instance of Message */
    public Message(String key, Object... values) {
        this.key = key;
        this.values = values;
    }
    
    /**
     * The message key for this message.
     */
    protected String key;
    
    /**
     * The replacement values for this mesasge.
     */
    protected Object values[];
    
    /**
     * The message for this message, with all value substitutions completed.
     */
    protected String message;
    
    /**
     * Get the message key for this message.
     */
    public String getKey() {
        return this.key;
    }
    
    /**
     * Get the replacement values for this message.
     */
    public Object[] getValues() {
        return this.values;
    }
    
    /**
     * Get the message for this message.
     */
    public String getMessage() {
        return message == null ? "??" + key + "??" : message;
    }
    
    /**
     * Set the message for this message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    private StringBuilder valuesToStringBuilder() {
        var sb = new StringBuilder();
        
        if(values != null) {
            for(var i = 0; i < values.length ; i++) {
                if(i != 0)
                    sb.append(", ");
                
                sb.append('"').append(values[i]).append('"');
            }
        } else {
            sb.append((String)null);
        }
        
        return sb;
    }
    
    /**
     * Converts to a string representing the data contained within this Message.
     */
    @Override
    public String toString() {
        return "key = \"" + key + "\", values[] = " + valuesToStringBuilder() + ", message = \"" + message + "\"";
    }
    
}
