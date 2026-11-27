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

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseEditForm;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.BaseSpec;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
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

    protected record EditOutcome<E extends BaseEdit, C extends BaseResult>(boolean successful, E lockedEdit, C createdRecord) {
    }

    protected <S extends BaseSpec, E extends BaseEdit, F extends BaseEditForm<S, E>, R extends BaseEditResult<E>,
            C extends BaseForm, CR extends BaseResult> EditOutcome<E, CR> editOrCreate(Map<String, Object> suppliedValues,
            S spec, F editForm, BiFunction<UserVisitPK, F, CommandResult<R>> editCommand,
            C createForm, BiFunction<UserVisitPK, C, CommandResult<CR>> createCommand,
            ExecutionErrors missingRecordError) throws SAXException {
        setFormFields(spec, suppliedValues);
        editForm.setSpec(spec);
        var userVisit = initialDataParser.getUserVisit();
        editForm.setEditMode(EditMode.LOCK);

        var lockResult = editCommand.apply(userVisit, editForm);
        EditOutcome<E, CR> outcome;
        if(lockResult.hasErrors()) {
            if(lockResult.containsExecutionError(missingRecordError.name()) && !lockResult.hasSecurityMessages()
                    && !lockResult.hasValidationErrors()
                    && lockResult.getExecutionResult().getExecutionErrors().size(Messages.EXECUTION_ERROR) == 1) {
                setFormFields(createForm, suppliedValues);
                var createResult = createCommand.apply(userVisit, createForm);
                checkCommandResult(createForm, createResult);
                var successful = !createResult.hasErrors();
                outcome = new EditOutcome<>(successful, null, successful ? createResult.getResult() : null);
            } else {
                checkCommandResult(editForm, lockResult);
                outcome = new EditOutcome<>(false, null, null);
            }
        } else {
            var edit = completeEdit(userVisit, editForm, editCommand, lockResult, suppliedValues);
            outcome = new EditOutcome<>(edit != null, edit, null);
        }

        return outcome;
    }

    private <E extends BaseEdit, F extends BaseEditForm<?, E>, R extends BaseEditResult<E>>
            E completeEdit(UserVisitPK userVisit, F editForm, BiFunction<UserVisitPK, F, CommandResult<R>> editCommand,
            CommandResult<R> lockResult, Map<String, Object> suppliedValues) throws SAXException {
        boolean successful = true;
        boolean abandonLock = true;
        E edit;

        try {
            checkCommandResult(editForm, lockResult);
            var lockedRecord = lockResult.getResult();
            edit = lockedRecord == null ? null : lockedRecord.getEdit();
            if(edit == null) {
                throw new SAXException(editForm.getFormName() + " returned no edit payload after LOCK");
            }

            if(applyEditFields(edit, suppliedValues, editForm.getSpec())) {
                editForm.setEdit(edit);
                editForm.setEditMode(EditMode.UPDATE);

                var updateResult = editCommand.apply(userVisit, editForm);
                successful = !updateResult.hasErrors();
                abandonLock = !successful && !updateResult.containsExecutionError(ExecutionErrors.EntityLockStale.name());
                checkCommandResult(editForm, updateResult);
            }
        } catch(SAXException | RuntimeException | Error failure) {
            if(abandonLock) {
                abandonEditAfterFailure(userVisit, editForm, editCommand, failure);
            }
            throw failure;
        }

        if(abandonLock) {
            var abandoned = abandonEdit(userVisit, editForm, editCommand);
            successful = successful && abandoned;
        }

        return successful ? edit : null;
    }

    protected static void setFormFields(BaseForm form, Map<String, Object> suppliedValues) {
        for(var fieldName : getStringFormFields(form)) {
            if(suppliedValues.containsKey(fieldName)) {
                form.set(fieldName, suppliedValues.get(fieldName));
            }
        }
    }

    private static List<String> getStringFormFields(BaseForm form) {
        // Form factories expose the domain setters through the proxy's interfaces.
        return Arrays.stream(form.getClass().getInterfaces())
                .filter(BaseForm.class::isAssignableFrom)
                .flatMap(formType -> Arrays.stream(formType.getMethods()))
                .filter(method -> method.getDeclaringClass() != BaseForm.class && Modifier.isAbstract(method.getModifiers())
                        && method.getReturnType() == void.class && method.getParameterCount() == 1
                        && method.getParameterTypes()[0] == String.class)
                .map(method -> method.getName())
                .filter(methodName -> methodName.startsWith("set") && methodName.length() > 3)
                .map(methodName -> methodName.substring(3))
                .distinct()
                .toList();
    }

    private static boolean applyEditFields(BaseEdit edit, Map<String, Object> suppliedValues, BaseSpec spec) {
        var identityFields = spec.get();
        var changed = false;

        for(var fieldName : getStringFormFields(edit)) {
            var suppliedValue = suppliedValues.get(fieldName);
            if(suppliedValues.containsKey(fieldName) && !identityFields.containsKey(fieldName)
                    && !Objects.equals(edit.get(fieldName), suppliedValue)) {
                edit.set(fieldName, suppliedValue);
                changed = true;
            }
        }

        return changed;
    }

    protected static boolean shouldEdit(Map<String, Object> suppliedValues) {
        var commandAction = suppliedValues.get("CommandAction");
        return commandAction == null || commandAction.equals("create");
    }

    @SuppressWarnings("ReferenceEquality") // Throwable.addSuppressed rejects self-suppression by identity.
    private <F extends BaseEditForm<?, ?>, R extends BaseEditResult<?>> void abandonEditAfterFailure(UserVisitPK userVisit,
            F editForm, BiFunction<UserVisitPK, F, CommandResult<R>> editCommand, Throwable failure) {
        try {
            abandonEdit(userVisit, editForm, editCommand);
        } catch(RuntimeException | Error abandonFailure) {
            if(abandonFailure != failure) {
                failure.addSuppressed(abandonFailure);
            }
        }
    }

    private <F extends BaseEditForm<?, ?>, R extends BaseEditResult<?>> boolean abandonEdit(UserVisitPK userVisit,
            F editForm, BiFunction<UserVisitPK, F, CommandResult<R>> editCommand) {
        editForm.setEdit(null);
        editForm.setEditMode(EditMode.ABANDON);

        var abandonResult = editCommand.apply(userVisit, editForm);
        checkCommandResult(editForm, abandonResult);
        return !abandonResult.hasErrors();
    }

    protected void skipCurrentElement() {
        initialDataParser.pushHandler(new BaseHandler(initialDataParser, this) {
            private int depth = 1;

            @Override
            public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) {
                depth++;
            }

            @Override
            public void endElement(String namespaceURI, String localName, String qName) {
                if(--depth == 0) {
                    initialDataParser.popHandler();
                }
            }
        });
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
