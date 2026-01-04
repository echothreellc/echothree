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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.message.Messages;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("command result object")
@GraphQLName("CommandResult")
public class CommandResultObject {
    
    private final CommandResult commandResult; // Always Present
    
    public CommandResultObject(CommandResult commandResult) {
        this.commandResult = commandResult;
    }

    @GraphQLField
    @GraphQLDescription("has security messages")
    @GraphQLNonNull
    public Boolean hasSecurityMessages() {
        return commandResult.hasSecurityMessages();
    }

    @GraphQLField
    @GraphQLDescription("security messages")
    public List<MessageObject> securityMessages() {
        List<MessageObject> securityMessages = null;
        
        if(commandResult.hasSecurityMessages()) {
            securityMessages = getListOfSecurityMessages();
        }
        
        return securityMessages;
    }

    @GraphQLField
    @GraphQLDescription("has validation errors")
    @GraphQLNonNull
    public Boolean hasValidationErrors() {
        return commandResult.hasValidationErrors();
    }

    @GraphQLField
    @GraphQLDescription("validation errors")
    public List<MessageObject> validationErrors() {
        List<MessageObject> validationErrors = null;
        
        if(commandResult.hasValidationErrors()) {
            validationErrors = getListOfValidationErrors();
        }
        
        return validationErrors;
    }

    @GraphQLField
    @GraphQLDescription("has execution warnings")
    @GraphQLNonNull
    public Boolean hasExecutionWarnings() {
        return commandResult.hasExecutionWarnings();
    }

    @GraphQLField
    @GraphQLDescription("execution warnings")
    public List<MessageObject> executionWarnings() {
        List<MessageObject> executionWarnings = null;
        
        if(commandResult.hasExecutionErrors()) {
            executionWarnings = getListOfExecutionWarningsOrErrors(Messages.EXECUTION_WARNING);
        }
        
        return executionWarnings;
    }

    @GraphQLField
    @GraphQLDescription("has execution errors")
    @GraphQLNonNull
    public Boolean hasExecutionErrors() {
        return commandResult.hasExecutionErrors();
    }

    @GraphQLField
    @GraphQLDescription("execution errors")
    public List<MessageObject> executionErrors() {
        List<MessageObject> executionErrors = null;
        
        if(commandResult.hasExecutionErrors()) {
            executionErrors = getListOfExecutionWarningsOrErrors(Messages.EXECUTION_ERROR);
        }
        
        return executionErrors;
    }

    @GraphQLField
    @GraphQLDescription("has errors")
    @GraphQLNonNull
    public Boolean hasErrors() {
        return commandResult.hasErrors();
    }

    @GraphQLField
    @GraphQLDescription("has warnings")
    @GraphQLNonNull
    public Boolean hasWarnings() {
        return commandResult.hasWarnings();
    }
    
    private List<MessageObject> getListOfSecurityMessages() {
        List<MessageObject> listOfMessages = null;
        var securityResult = commandResult.getSecurityResult();

        if(securityResult != null) {
            listOfMessages = getListOfMessages(securityResult.getSecurityMessages(), Messages.SECURITY_MESSAGE);
        }
        
        return listOfMessages;
    }

    private List<MessageObject> getListOfValidationErrors() {
        List<MessageObject> listOfMessages = null;
        var validationResult = commandResult.getValidationResult();
        var validationMessages = validationResult.getValidationMessages();
        
        if(!validationMessages.isEmpty()) {
            var propertyIterator = validationMessages.properties();
            listOfMessages = new ArrayList<>();

            while(propertyIterator.hasNext()) {
                var property = propertyIterator.next();
                
                listOfMessages.addAll(getListOfMessages(validationMessages, property));
            }
        }
        
        return listOfMessages;
    }

    private List<MessageObject> getListOfExecutionWarningsOrErrors(String property) {
        List<MessageObject> listOfMessages = null;
        var executionResult = commandResult.getExecutionResult();

        if(executionResult != null) {
            listOfMessages = getListOfMessages(executionResult.getExecutionErrors(), property);
        }
        
        return listOfMessages;
    }

    private List<MessageObject> getListOfMessages(Messages messages, String property) {
        List<MessageObject> listOfMessages = null;

        if(messages != null) {
            var iterator = property == null ? messages.get() : messages.get(property);

            listOfMessages = new ArrayList<>();

            for (var i = iterator; i.hasNext(); ) {
                var e = i.next();

                listOfMessages.add(new MessageObject(property, e.getKey(), e.getMessage()));
            }
        }

        return listOfMessages;
    }

}
