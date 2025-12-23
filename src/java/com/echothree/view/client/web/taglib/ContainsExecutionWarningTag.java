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
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;

/**
 * Custom tag that iterates the elements of a message collection.
 * It defaults to retrieving the messages from <code>Globals.WARNING_KEY</code>,
 * but if the message attribute is set to true then the messages will be
 * retrieved from <code>Globals.MESSAGE_KEY</code>. This is an alternative
 * to the default <code>WarningsTag</code>.
 *
 * @since Struts 1.1
 */
public class ContainsExecutionWarningTag
        extends BaseTag {

    /**
     * The request attribute key for our warning messages (if any).
     */
    protected String commandResultVar = TagConstants.CommandResultName;
    /**
     * The key for the execution warning that we're going to look for.
     */
    protected String key = null;

    public String getCommandResultVar() {
        return commandResultVar;
    }

    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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
        var commandResult = (CommandResult)pageContext.findAttribute(commandResultVar);

        if(commandResult != null) {
            if(commandResult.containsExecutionWarning(key)) {
                return EVAL_BODY_BUFFERED;
            }
        }

        return SKIP_BODY;
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

        return SKIP_BODY;
    }

    /**
     * Release all allocated resources.
     */
    @Override
    public void release() {
        super.release();

        commandResultVar = TagConstants.CommandResultName;
        key = null;
    }

}
