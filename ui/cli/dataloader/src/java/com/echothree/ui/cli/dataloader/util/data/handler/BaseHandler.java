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

package com.echothree.ui.cli.dataloader.util.data.handler;

import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseEditForm;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.BaseSpec;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class BaseHandler {
    
    protected InitialDataParser initialDataParser;
    protected BaseHandler parentHandler;

    private static Logger logger = null;
    
    private static boolean doVerbose = false;
    
    /** Creates a new instance of BaseHandler */
    protected BaseHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        this.initialDataParser = initialDataParser;
        this.parentHandler = parentHandler;
    }

    @FunctionalInterface
    protected interface ServiceLookup<S> {
        S get() throws NamingException;
    }

    protected static <S> S lookupService(Class<S> serviceType, ServiceLookup<S> serviceLookup)
            throws SAXException {
        try {
            return serviceLookup.get();
        } catch(NamingException exception) {
            throw new SAXException("Unable to look up " + serviceType.getSimpleName(), exception);
        }
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
    
    protected Logger getLogger() {
        if(logger == null) {
            logger = LoggerFactory.getLogger(this.getClass());
        }
        
        return logger;
    }

    protected <S extends BaseSpec, E extends BaseEdit> void updateEditFormValues(final BaseEditForm<S, E> baseEditForm,
            final Map<String, Object> attrsMap, final BaseEditResult<E> result) {
        var edit = result.getEdit();
        var currentValues = edit.get();
        var changed = false;

        for(var key : currentValues.keySet()) {
            var currentValue = currentValues.get(key);

            if(attrsMap.containsKey(key)) {
                var xmlValue = attrsMap.get(key);

                if(!xmlValue.equals(currentValue)) {
                    getLogger().debug("Updating key: \"{}\" from value \"{}\"\" to value \"{}\"", key, currentValue, xmlValue);
                    edit.set(key, xmlValue);
                    changed = true;
                }
            }
        }

        if(changed) {
            getLogger().debug("Updating.");
            baseEditForm.setEdit(edit);
            baseEditForm.setEditMode(EditMode.UPDATE);
        } else {
            getLogger().debug("Abandoning.");
            baseEditForm.setEdit(null);
            baseEditForm.setEditMode(EditMode.ABANDON);
        }
    }

    protected void checkCommandResult(BaseForm commandForm, CommandResult<?> commandResult) {
        if(commandResult.hasExecutionWarnings()) {
            var warnings = new StringJoiner("; ");
            var executionWarnings = commandResult.getExecutionResult().getExecutionWarnings();

            executionWarnings.get(Messages.EXECUTION_WARNING)
                    .forEachRemaining(message -> appendMessage(warnings, "Execution", message));

            getLogger().warn("{} at {}: {}", commandForm.getFormName(), initialDataParser.getSourceLocation(), warnings);
        }

        if(commandResult.hasErrors()) {
            var errors = new StringJoiner("; ");

            if(commandResult.hasSecurityMessages()) {
                var securityMessages = commandResult.getSecurityResult().getSecurityMessages();
                securityMessages.get(Messages.SECURITY_MESSAGE)
                        .forEachRemaining(message -> appendMessage(errors, "Security", message));
            }

            if(commandResult.hasValidationErrors()) {
                var validationMessages = commandResult.getValidationResult().getValidationMessages();
                validationMessages.properties().forEachRemaining(fieldName -> validationMessages.get(fieldName)
                        .forEachRemaining(message -> appendMessage(errors, "Validation (" + fieldName + ")", message)));
            }

            if(commandResult.hasExecutionErrors()) {
                var executionErrors = commandResult.getExecutionResult().getExecutionErrors();
                executionErrors.get(Messages.EXECUTION_ERROR).forEachRemaining(message -> {
                    var key = message.getKey();
                    var errorName = key == null ? "" : key.substring(key.lastIndexOf('.') + 1);
                    if(doVerbose || !errorName.startsWith("Duplicate")) {
                        appendMessage(errors, "Execution", message);
                    }
                });
            }

            if(errors.length() > 0) {
                getLogger().error("{} failed at {}: {}", commandForm.getFormName(), initialDataParser.getSourceLocation(), errors);
            }
        } else if(doVerbose) {
            getLogger().info("{} at {}: {}", commandForm.getFormName(), initialDataParser.getSourceLocation(),
                    commandResult.getResult());
        }
    }

    private static void appendMessage(StringJoiner messages, String category, Message message) {
        var key = message.getKey();
        var text = message.getMessage();

        messages.add(category + " " + key + (text.equals("??" + key + "??") ? "" : " (" + text + ")"));
    }
    
    public static void setDoVerbose(boolean aDoVerbose) {
        doVerbose = aDoVerbose;
    }
    
    protected Map<String, Object> getAttrsMap(final Attributes attrs) {
        var attrsMap = new HashMap<String, Object>();
        var count = attrs.getLength();

        for(var i = 0; i < count; i++) {
            var attributeName = attrs.getQName(i);
            if(!attributeName.isEmpty()) {
                var fieldName = attributeName.substring(0, 1).toUpperCase(Locale.ROOT) + attributeName.substring(1);

                // These form properties accept structured values, while XML attributes are strings.
                switch(fieldName) {
                    case "Limits", "Options", "TransferProperties" -> { }
                    default -> attrsMap.put(fieldName, attrs.getValue(i));
                }
            }
        }
        
        return attrsMap;
    }

    protected String getCommandAction(final BaseSpec spec) {
        return (String)spec.get("CommandAction");
    }
    
}
