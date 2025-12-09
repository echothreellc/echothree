// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.util.server.control;

import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.model.control.core.common.CommandMessageTypes;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.factory.UserVisitFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.message.SecurityMessages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.server.cdi.CommandScopeExtension;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionWarningAccumulator;
import com.echothree.util.server.message.MessageUtils;
import com.echothree.util.server.message.SecurityMessageAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseCommand
        implements ExecutionWarningAccumulator, ExecutionErrorAccumulator, SecurityMessageAccumulator {

    private Log log;

    private final CommandSecurityDefinition commandSecurityDefinition;

    protected Session session;

    private UserVisitPK userVisitPK;
    private UserVisit userVisit;
    private UserSession userSession;
    
    private Party party;
    
    private Messages executionWarnings;
    private Messages executionErrors;
    private Messages securityMessages;
    
    private String componentVendorName;
    private String commandName;
    
    private boolean checkIdentityVerifiedTime = true;
    private boolean updateLastCommandTime = true;
    private boolean logCommand = true;

    @Inject
    protected UserControl userControl;

    @Inject
    protected CoreControl coreControl;

    @Inject
    protected ComponentControl componentControl;

    @Inject
    protected EntityTypeControl entityTypeControl;

    @Inject
    protected EventControl eventControl;

    @Inject
    protected CommandControl commandControl;

//    @Inject
//    protected LicenseCheckLogic licenseCheckLogic;

    @Inject
    protected SecurityRoleLogic securityRoleLogic;

    protected BaseCommand(CommandSecurityDefinition commandSecurityDefinition) {
        if(ControlDebugFlags.LogBaseCommands) {
            getLog().info("BaseCommand()");
        }

        this.commandSecurityDefinition = commandSecurityDefinition;
    }

    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    private void setupNames() {
        Class<? extends BaseCommand> c = this.getClass();
        var className = c.getName();
        var nameOffset = className.lastIndexOf('.');
        
        componentVendorName = ComponentVendors.ECHO_THREE.name();
        commandName = new String(className.getBytes(StandardCharsets.UTF_8), nameOffset + 1, className.length() - nameOffset - 8, StandardCharsets.UTF_8);
    }
    
    public String getComponentVendorName() {
        if(componentVendorName == null) {
            setupNames();
        }
        
        return componentVendorName;
    }
    
    public String getCommandName() {
        if(commandName == null) {
            setupNames();
        }
        
        return commandName;
    }
    
    public Party getCompanyParty() {
        Party companyParty = null;
        var partyRelationship = userSession.getPartyRelationship();
        
        if(partyRelationship != null) {
            companyParty = partyRelationship.getFromParty();
        }
        
        return companyParty;
    }
    
    public PartyPK getPartyPK() {
        if(party == null) {
            getParty();
        }
        
        return party == null? null: party.getPrimaryKey();
    }
    
    public Party getParty() {
        if(party == null) {
            party = userControl.getPartyFromUserVisitPK(userVisitPK);
        }
        
        return party;
    }
    
    public PartyType getPartyType() {
        PartyType partyType = null;
        
        if(getParty() != null) {
            partyType = party.getLastDetail().getPartyType();
        }
        
        return partyType;
    }
    
    public String getPartyTypeName() {
        var partyType = getPartyType();

        return partyType == null ? null : partyType.getPartyTypeName();
    }
    
    public UserVisitPK getUserVisitPK() {
        return userVisitPK;
    }

    public void setUserVisitPK(UserVisitPK userVisitPK) {
        this.userVisitPK = userVisitPK;
        userVisit = null;
    }

    private UserVisit getUserVisit(EntityPermission entityPermission) {
        if(userVisitPK != null) {
            if(userVisit == null) {
                userVisit = UserVisitFactory.getInstance().getEntityFromPK(entityPermission, userVisitPK);
            } else {
                if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                    if(!userVisit.getEntityPermission().equals(EntityPermission.READ_WRITE)) {
                        userVisit = UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userVisitPK);
                    }
                }
            }
        }

        return userVisit;
    }
    
    public UserVisit getUserVisit() {
        return getUserVisit(EntityPermission.READ_ONLY);
    }
    
    public UserVisit getUserVisitForUpdate() {
        return getUserVisit(EntityPermission.READ_WRITE);
    }
    
    public UserSession getUserSession() {
        return userSession;
    }
    
    public Session getSession() {
        return session;
    }
    
    public UserControl getUserControl() {
        return userControl;
    }
    
    public Language getPreferredLanguage() {
        return userControl.getPreferredLanguageFromUserVisit(getUserVisit());
    }
    
    public Language getPreferredLanguage(Party party) {
        return userControl.getPreferredLanguageFromParty(party);
    }
    
    public Currency getPreferredCurrency() {
        return userControl.getPreferredCurrencyFromUserVisit(getUserVisit());
    }
    
    public Currency getPreferredCurrency(Party party) {
        return userControl.getPreferredCurrencyFromParty(party);
    }
    
    public TimeZone getPreferredTimeZone() {
        return userControl.getPreferredTimeZoneFromUserVisit(getUserVisit());
    }
    
    public TimeZone getPreferredTimeZone(Party party) {
        return userControl.getPreferredTimeZoneFromParty(party);
    }
    
    public DateTimeFormat getPreferredDateTimeFormat() {
        return userControl.getPreferredDateTimeFormatFromUserVisit(getUserVisit());
    }
    
    public DateTimeFormat getPreferredDateTimeFormat(Party party) {
        return userControl.getPreferredDateTimeFormatFromParty(party);
    }
    
    public boolean getCheckIdentityVerifiedTime() {
        return checkIdentityVerifiedTime;
    }

    public void setCheckIdentityVerifiedTime(boolean checkIdentityVerifiedTime) {
        this.checkIdentityVerifiedTime = checkIdentityVerifiedTime;
    }

    public boolean getUpdateLastCommandTime() {
        return updateLastCommandTime;
    }

    public void setUpdateLastCommandTime(boolean updateLastCommandTime) {
        this.updateLastCommandTime = updateLastCommandTime;
    }

    public boolean getLogCommand() {
        return logCommand;
    }

    public void setLogCommand(boolean logCommand) {
        this.logCommand = logCommand;
    }

    private void checkUserVisit() {
        if(getUserVisit() != null) {
            userSession = userControl.getUserSessionByUserVisit(userVisit);

            if(userSession != null && checkIdentityVerifiedTime) {
                var identityVerifiedTime = userSession.getIdentityVerifiedTime();

                if(identityVerifiedTime != null) {
                    var timeSinceLastCommand = session.getStartTime() - userVisit.getLastCommandTime();

                    // If it has been > 15 minutes since their last command, invalidate the UserSession.
                    if(timeSinceLastCommand > 15 * 60 * 1000) {
                        userSession = UserSessionLogic.getInstance().invalidateUserSession(userSession);
                    }
                }
            }
        }
    }

    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return commandSecurityDefinition;
    }
    
    // Returns true if everything passes.
    protected boolean checkCommandSecurityDefinition() {
        var passed = true;
        var myCommandSecurityDefinition = getCommandSecurityDefinition();
        
        if(myCommandSecurityDefinition != null) {
            var partyTypeName = getParty() == null ? null : party.getLastDetail().getPartyType().getPartyTypeName();
            var foundPartyType = false;
            var foundPartySecurityRole = false;

            for(var partyTypeDefinition : myCommandSecurityDefinition.getPartyTypeDefinitions()) {
                if(partyTypeName == null) {
                    if(partyTypeDefinition.getPartyTypeName() == null) {
                        foundPartyType = true;
                        foundPartySecurityRole = true;
                        break;
                    }
                } else {
                    if(partyTypeDefinition.getPartyTypeName().equals(partyTypeName)) {
                        var securityRoleDefinitions = partyTypeDefinition.getSecurityRoleDefinitions();

                        if(securityRoleDefinitions == null) {
                            foundPartySecurityRole = true;
                        } else {
                            for(var securityRoleDefinition : securityRoleDefinitions) {
                                var securityRoleGroupName = securityRoleDefinition.getSecurityRoleGroupName();
                                var securityRoleName = securityRoleDefinition.getSecurityRoleName();

                                if(securityRoleGroupName != null && securityRoleName != null) {
                                    foundPartySecurityRole = securityRoleLogic.hasSecurityRoleUsingNames(this, party, securityRoleGroupName,
                                            securityRoleName);
                                }

                                if(foundPartySecurityRole) {
                                    break;
                                }
                            }
                        }

                        foundPartyType = true;
                        break;
                    }
                }
            }

            if(!foundPartyType || !foundPartySecurityRole) {
                passed = false;
            }
        }
        
        return passed;
    }

    // Returns true if everything passes.
    protected boolean checkOptionalSecurityRoles() {
        return true;
    }

    protected SecurityResult security() {
        if(!(checkCommandSecurityDefinition() && checkOptionalSecurityRoles())) {
            addSecurityMessage(SecurityMessages.InsufficientSecurity.name());
        }

        return securityMessages == null ? null : new SecurityResult(securityMessages);
    }
    
    @Override
    public void addSecurityMessage(Message message) {
        if(securityMessages == null) {
            securityMessages = new Messages();
        }
        
        securityMessages.add(Messages.SECURITY_MESSAGE, message);
    }
    
    @Override
    public void addSecurityMessage(String key, Object... values) {
        addSecurityMessage(new Message(key, values));
    }
    
    @Override
    public Messages getSecurityMessages() {
        return securityMessages;
    }
    
    @Override
    public boolean hasSecurityMessages() {
        return securityMessages != null && securityMessages.size(Messages.SECURITY_MESSAGE) != 0;
    }
    
    protected ValidationResult validate() {
        if(ControlDebugFlags.LogBaseCommands) {
            log.info("validate()");
        }
        
        return null;
    }
    
    protected abstract BaseResult execute();
    
    @Override
    public void addExecutionWarning(Message message) {
        if(executionWarnings == null) {
            executionWarnings = new Messages();
        }
        
        executionWarnings.add(Messages.EXECUTION_WARNING, message);
    }
    
    @Override
    public void addExecutionWarning(String key, Object... values) {
        addExecutionWarning(new Message(key, values));
    }
    
    @Override
    public Messages getExecutionWarnings() {
        return executionWarnings;
    }
    
    @Override
    public boolean hasExecutionWarnings() {
        return executionWarnings != null && executionWarnings.size(Messages.EXECUTION_WARNING) != 0;
    }
    
    @Override
    public void addExecutionError(Message message) {
        if(executionErrors == null) {
            executionErrors = new Messages();
        }
        
        executionErrors.add(Messages.EXECUTION_ERROR, message);
    }
    
    @Override
    public void addExecutionError(String key, Object... values) {
        addExecutionError(new Message(key, values));
    }
    
    @Override
    public Messages getExecutionErrors() {
        return executionErrors;
    }
    
    @Override
    public boolean hasExecutionErrors() {
        return executionErrors != null && executionErrors.size(Messages.EXECUTION_ERROR) != 0;
    }
    
    protected BaseResult getBaseResultAfterErrors() {
        return null;
    }

    protected void setupSession() {
        initSession();
    }

    // Called by setupSession() and canQueryByGraphQl()
    protected void initSession() {
        session = ThreadSession.currentSession();
    }

    protected void teardownSession() {
        session = null;
    }

    public Future<CommandResult> runAsync(UserVisitPK userVisitPK) {
        return new AsyncResult<>(run(userVisitPK));
    }

    public CommandResult run(UserVisitPK userVisitPK)
            throws BaseException {
        if(ControlDebugFlags.LogBaseCommands) {
            log.info(">>> run()");
        }

        this.userVisitPK = userVisitPK;

        if(CommandScopeExtension.getCommandScopeContext().isActive()) {
            CommandScopeExtension.getCommandScopeContext().push();
        } else {
            CommandScopeExtension.getCommandScopeContext().activate();
        }
        setupSession();

        SecurityResult securityResult;
        ValidationResult validationResult = null;
        ExecutionResult executionResult;
        CommandResult commandResult;

        try {
            BaseResult baseResult = null;

//            if(licenseCheckLogic.permitExecution(session)) {
                checkUserVisit();
                securityResult = security();

                if(securityResult == null || !securityResult.getHasMessages()) {
                    validationResult = validate();

                    if(validationResult == null || !validationResult.getHasErrors()) {
                        baseResult = execute();
                    }
                }
//            } else {
//                addExecutionError(ExecutionErrors.LicenseCheckFailed.name());
//            }

            executionResult = new ExecutionResult(executionWarnings, executionErrors, baseResult == null ? getBaseResultAfterErrors() : baseResult);

            // Don't waste time getting the preferredLanguage if we don't need to.
            if((securityResult != null && securityResult.getHasMessages())
                    || (executionResult.getHasWarnings() || executionResult.getHasErrors())
                    || (validationResult != null && validationResult.getHasErrors())) {
                var preferredLanguage = getPreferredLanguage();

                if(securityResult != null) {
                    MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Security.name(), securityResult.getSecurityMessages());
                }

                MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Warning.name(), executionResult.getExecutionWarnings());
                MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Error.name(), executionResult.getExecutionErrors());

                if(validationResult != null) {
                    MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Validation.name(), validationResult.getValidationMessages());
                }
            }

            if(updateLastCommandTime) {
                if(getUserVisitForUpdate() == null) {
                    getLog().error("Command not logged, unknown userVisit");
                } else {
                    userVisit.setLastCommandTime(Math.max(session.getStartTime(), userVisit.getLastCommandTime()));

                    // TODO: Check PartyTypeAuditPolicy to see if the command should be logged
                    if(logCommand) {
                        var componentVendor = componentControl.getComponentVendorByName(getComponentVendorName());

                        if(componentVendor != null) {
                            getCommandName();
                            getParty(); // TODO: should only use if UserSession.IdentityVerifiedTime != null

                            if(ControlDebugFlags.CheckCommandNameLength) {
                                if(commandName.length() > 80) {
                                    getLog().error("commandName length > 80 characters, " + commandName);
                                    commandName = commandName.substring(0, 79);
                                }
                            }

                            var command = commandControl.getCommandByName(componentVendor, commandName);

                            if(command == null) {
                                command = commandControl.createCommand(componentVendor, commandName, 1, party == null ? null : party.getPrimaryKey());
                            }

                            if(command != null) {
                                var userVisitStatus = userControl.getUserVisitStatusForUpdate(userVisit);

                                if(userVisitStatus != null) {
                                    Integer userVisitCommandSequence = userVisitStatus.getUserVisitCommandSequence() + 1;
                                    var hadSecurityErrors = securityResult == null ? null : securityResult.getHasMessages();
                                    var hadValidationErrors = validationResult == null ? null : validationResult.getHasErrors();
                                    var hasExecutionErrors = executionResult.getHasErrors();

                                    userVisitStatus.setUserVisitCommandSequence(userVisitCommandSequence);
                                    userVisitStatus.store();

                                    userControl.createUserVisitCommand(userVisit, userVisitCommandSequence, party, command, session.getStartTime(),
                                            System.currentTimeMillis(), hadSecurityErrors, hadValidationErrors, hasExecutionErrors);
                                } else {
                                    getLog().error("Command not logged, unknown userVisitStatus for " + userVisit.getPrimaryKey());
                                }
                            } else {
                                getLog().error("Command not logged, unknown (and could not create) commandName = " + commandName);
                            }
                        } else {
                            getLog().error("Command not logged, unknown componentVendorName = " + componentVendorName);
                        }
                    }
                }
            }
        } finally {
            teardownSession();
            CommandScopeExtension.getCommandScopeContext().pop();
        }

        // The Session for this Thread must NOT be utilized by anything after teardownSession() has been called.
        commandResult = new CommandResult(securityResult, validationResult, executionResult);

        if(commandResult.hasSecurityMessages() || commandResult.hasValidationErrors()) {
            getLog().info("commandResult = " + commandResult);
        }

        if(ControlDebugFlags.LogBaseCommands) {
            if(commandResult.hasExecutionErrors()) {
                log.info("<<< run(), returning executionResult = " + commandResult.getExecutionResult());
            } else {
                log.info("<<< run()");
            }
        }

        return commandResult;
    }

    // --------------------------------------------------------------------------------
    //   Security Utilities
    // --------------------------------------------------------------------------------

    protected boolean canSpecifyParty() {
        var partyType = getPartyType();
        var result = false; // Default to most restrictive result.

        if(partyType != null) {
            var partyTypeName = partyType.getPartyTypeName();

            // Of PartyTypes that may login only EMPLOYEEs or UTILITYs may specify another Party. CUSTOMERs and
            // VENDORs may not.
            result = partyTypeName.equals(PartyTypes.EMPLOYEE.name())
                    || partyTypeName.equals(PartyTypes.UTILITY.name());
        }

        return result;
    }

    protected SecurityResult selfOnly(PartySpec spec) {
        var hasInsufficientSecurity = !canSpecifyParty() && spec.getPartyName() != null;

        return hasInsufficientSecurity ? getInsufficientSecurityResult() : null;
    }

    protected SecurityResult getInsufficientSecurityResult() {
        return new SecurityResult(new Messages().add(Messages.SECURITY_MESSAGE, new Message(SecurityMessages.InsufficientSecurity.name())));
    }

    // --------------------------------------------------------------------------------
    //   Event Utilities
    // --------------------------------------------------------------------------------

    protected Event sendEvent(final BasePK basePK, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var entityInstance = coreControl.getEntityInstanceByBasePK(basePK);
        var relatedEntityInstance = relatedBasePK == null ? null : coreControl.getEntityInstanceByBasePK(relatedBasePK);
        
        return sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
    }
    
    protected Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var relatedEntityInstance = relatedBasePK == null ? null : coreControl.getEntityInstanceByBasePK(relatedBasePK);

        return sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
    }
    
    protected Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final EntityInstance relatedEntityInstance,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        Event event = null;
        
        if(createdByBasePK != null) {
            event = eventControl.sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType,
                createdByBasePK);
        }
        
        return event;
    }
    
    // --------------------------------------------------------------------------------
    //   Option Utilities
    // --------------------------------------------------------------------------------

    /** This should only be called an override of setupSession(). After that, TransferCaches may have cached knowledge
     * that specific options were set.
     * @param option The option to remove.
     */
    protected void removeOption(String option) {
        session.getOptions().remove(option);
    }

    // --------------------------------------------------------------------------------
    //   Transfer Property Utilities
    // --------------------------------------------------------------------------------

    /** This should only be called an override of setupSession(). After that, TransferCaches may have cached knowledge
     * that specific properties were filtered.
     * @param clazz The Class whose properties should be examined.
     * @param property The property to remove.
     */
    protected void removeFilteredTransferProperty(Class<? extends BaseTransfer> clazz, String property) {
        var transferProperties = session.getTransferProperties();

        if(transferProperties != null) {
            var properties = transferProperties.getProperties(clazz);

            if(properties != null) {
                properties.remove(property);
            }
        }
    }
    
}
