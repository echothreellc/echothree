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

import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;

/**
 * Custom tag that iterates the elements of a message collection.
 * It defaults to retrieving the messages from <code>Globals.ERROR_KEY</code>,
 * but if the message attribute is set to true then the messages will be
 * retrieved from <code>Globals.MESSAGE_KEY</code>. This is an alternative
 * to the default <code>ErrorsTag</code>.
 *
 * @since Struts 1.1
 */
public class ExecutionErrorsTag
        extends BaseMessageTag {
    
    /**
     * Iterator of the elements of this error collection, while we are actually
     * running.
     */
    protected Iterator<Message> iterator = null;
    
    /**
     * Whether or not any error messages have been processed.
     */
    protected boolean processed = false;
    
    /**
     * The message resource key for errors header.
     */
    protected String header = null;
    
    /**
     * The message resource key for errors footer.
     */
    protected String footer = null;
    
    public String getHeader() {
        return this.header;
    }
    
    public void setHeader(String header) {
        this.header = header;
    }
    
    public String getFooter() {
        return this.footer;
    }
    
    public void setFooter(String footer) {
        this.footer = footer;
    }
    
    public Messages getMessages(PageContext pageContext, String paramName)
    throws JspException {
        var messages = new Messages();
        var commandResult = (CommandResult)pageContext.findAttribute(paramName);
        
        if(commandResult != null) {
            var executionResult = commandResult.getExecutionResult();
            
            if(executionResult != null) {
                var pageMessages = executionResult.getExecutionErrors();
                
                if(pageMessages != null) {
                    messages.add(pageMessages);
                }
            }
        }
        
        return messages;
    }
    
    /**
     * Construct an iterator for the specified collection, and begin
     * looping through the body once per element.
     *
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doStartTag()
    throws JspException {
        // Initialize for a new request.
        processed = false;
        
        // Make a local copy of the name attribute that we can modify.
        var messages = getMessages(pageContext, commandResultVar);
        
        // Acquire the collection we are going to iterate over
        this.iterator = messages.get(Messages.EXECUTION_ERROR);
        
        // Store the first value and evaluate, or skip the body if none
        if (!this.iterator.hasNext()) {
            return SKIP_BODY;
        }
        
        setMessageAttribute(iterator.next());
        
        if (header != null && header.length() > 0) {
            var headerMessage = TagUtils.getInstance().message(pageContext, bundle, locale, header);
            
            if (headerMessage != null) {
                TagUtils.getInstance().write(pageContext, headerMessage);
            }
        }
        
        // Set the processed variable to true so the
        // doEndTag() knows processing took place
        processed = true;
        
        return EVAL_BODY_BUFFERED;
    }
    
    /**
     * Make the next collection element available and loop, or
     * finish the iterations if there are no more elements.
     *
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doAfterBody()
    throws JspException {
        // Render the output from this iteration to the output stream
        if(bodyContent != null) {
            TagUtils.getInstance().writePrevious(pageContext, bodyContent.getString());
            bodyContent.clearBody();
        }
        
        // Decide whether to iterate or quit
        if(iterator.hasNext()) {
            setMessageAttribute((Message)iterator.next());
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
        
    }
    
    /**
     * Clean up after processing this enumeration.
     *
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doEndTag()
    throws JspException {
        if (processed && footer != null && footer.length() > 0) {
            var footerMessage = TagUtils.getInstance().message(pageContext, bundle, locale, footer);
            
            if (footerMessage != null) {
                TagUtils.getInstance().write(pageContext, footerMessage);
            }
        }
        
        return EVAL_PAGE;
    }
    
    /**
     * Release all allocated resources.
     */
    @Override
    public void release() {
        super.release();
        
        iterator = null;
        processed = false;
        header = null;
        footer = null;
    }
    
}
