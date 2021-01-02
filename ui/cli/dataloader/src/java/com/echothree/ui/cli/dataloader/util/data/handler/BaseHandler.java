// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler;

import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.string.StringUtils;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class BaseHandler {
    
    protected InitialDataParser initialDataParser;
    protected BaseHandler parentHandler;
    
    private Log log = null;
    
    private static boolean doErrors = false;
    private static boolean doVerbose = false;
    
    /** Creates a new instance of BaseHandler */
    public BaseHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        this.initialDataParser = initialDataParser;
        this.parentHandler = parentHandler;
    }
    
    public abstract void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException;
    
    public abstract void endElement(String namespaceURI, String localName, String qName)
            throws SAXException;
    
    public void characters(char ch[], int start, int length)
            throws SAXException {
        // Ignored
    }
    
    /** Ignorable whitespace. */
    public void ignorableWhitespace(char ch[], int start, int length)
            throws SAXException {
        // Ignored
    }
    
    /** Processing instruction. */
    public void processingInstruction(String target, String data)
            throws SAXException {
        // Ignored
    }
    
    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    protected void checkCommandResult(CommandResult commandResult) {
        if(doVerbose || (commandResult.hasErrors() && doErrors)) {
            var executionResult = commandResult.getExecutionResult();
            
            if(commandResult.hasErrors())
                getLog().error(executionResult);
            else
                getLog().info(executionResult);
        }
    }
    
    public static void setDoErrors(boolean aDoErrors) {
        doErrors = aDoErrors;
    }
    
    public static void setDoVerbose(boolean aDoVerbose) {
        doVerbose = aDoVerbose;
    }
    
    protected Map<String, Object> getAttrsMap(final Attributes attrs) {
        var attrsMap = new HashMap<String, Object>();
        var count = attrs.getLength();

        for(var i = 0 ; i < count ; i++) {
            attrsMap.put(StringUtils.getInstance().upperCaseFirstCharacter(attrs.getQName(i)), attrs.getValue(i));
        }
        
        return attrsMap;
    }
    
}
