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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.CommandMessageTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.CommandDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTranslationTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.common.transfer.CommandTransfer;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.CommandDescription;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDescription;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.factory.CommandDescriptionFactory;
import com.echothree.model.data.core.server.factory.CommandDetailFactory;
import com.echothree.model.data.core.server.factory.CommandFactory;
import com.echothree.model.data.core.server.factory.CommandMessageDetailFactory;
import com.echothree.model.data.core.server.factory.CommandMessageFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTranslationFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTypeDetailFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTypeFactory;
import com.echothree.model.data.core.server.value.CommandDescriptionValue;
import com.echothree.model.data.core.server.value.CommandDetailValue;
import com.echothree.model.data.core.server.value.CommandMessageDetailValue;
import com.echothree.model.data.core.server.value.CommandMessageTranslationValue;
import com.echothree.model.data.core.server.value.CommandMessageTypeDescriptionValue;
import com.echothree.model.data.core.server.value.CommandMessageTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CommandControl
        extends BaseCoreControl {

    /** Creates a new instance of CommandControl */
    public CommandControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Commands
    // --------------------------------------------------------------------------------

    public Command createCommand(ComponentVendor componentVendor, String commandName, Integer sortOrder, BasePK createdBy) {
        var command = CommandFactory.getInstance().create();
        var commandDetail = CommandDetailFactory.getInstance().create(command, componentVendor,
                commandName, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        command = CommandFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, command.getPrimaryKey());
        command.setActiveDetail(commandDetail);
        command.setLastDetail(commandDetail);
        command.store();

        sendEvent(command.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return command;
    }

    private Command getCommandByName(ComponentVendor componentVendor, String commandName, EntityPermission entityPermission) {
        Command command;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? AND cmddt_commandname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? AND cmddt_commandname = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandFactory.getInstance().prepareStatement(query);

            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            ps.setString(2, commandName);

            command = CommandFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return command;
    }

    public Command getCommandByName(ComponentVendor componentVendor, String commandName) {
        return getCommandByName(componentVendor, commandName, EntityPermission.READ_ONLY);
    }

    public Command getCommandByNameForUpdate(ComponentVendor componentVendor, String commandName) {
        return getCommandByName(componentVendor, commandName, EntityPermission.READ_WRITE);
    }

    public CommandDetailValue getCommandDetailValueForUpdate(Command command) {
        return command == null? null: command.getLastDetailForUpdate().getCommandDetailValue().clone();
    }

    public CommandDetailValue getCommandDetailValueByNameForUpdate(ComponentVendor componentVendor, String commandName) {
        return getCommandDetailValueForUpdate(getCommandByNameForUpdate(componentVendor, commandName));
    }

    private List<Command> getCommandsByComponentVendor(ComponentVendor componentVendor, EntityPermission entityPermission) {
        List<Command> commands;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? " +
                        "ORDER BY cmddt_sortorder, cmddt_commandname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandFactory.getInstance().prepareStatement(query);

            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());

            commands = CommandFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commands;
    }

    public List<Command> getCommandsByComponentVendor(ComponentVendor componentVendor) {
        return getCommandsByComponentVendor(componentVendor, EntityPermission.READ_ONLY);
    }

    public List<Command> getCommandsByComponentVendorForUpdate(ComponentVendor componentVendor) {
        return getCommandsByComponentVendor(componentVendor, EntityPermission.READ_WRITE);
    }

    public List<Command> getCommands() {
        var ps = CommandFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "ORDER BY cmddt_sortorder, cmddt_commandname");

        return CommandFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public CommandTransfer getCommandTransfer(UserVisit userVisit, Command command) {
        return getCoreTransferCaches(userVisit).getCommandTransferCache().getCommandTransfer(command);
    }

    private List<CommandTransfer> getCommandTransfers(UserVisit userVisit, Collection<Command> commands) {
        List<CommandTransfer> commandTransfers = new ArrayList<>(commands.size());
        var commandTransferCache = getCoreTransferCaches(userVisit).getCommandTransferCache();

        commands.forEach((command) ->
                commandTransfers.add(commandTransferCache.getCommandTransfer(command))
        );

        return commandTransfers;
    }

    public List<CommandTransfer> getCommandTransfers(UserVisit userVisit) {
        return getCommandTransfers(userVisit, getCommands());
    }

    public List<CommandTransfer> getCommandTransfersByComponentVendor(UserVisit userVisit, ComponentVendor componentVendor) {
        return getCommandTransfers(userVisit, getCommandsByComponentVendor(componentVendor));
    }

    public void updateCommandFromValue(CommandDetailValue commandDetailValue, BasePK updatedBy) {
        if(commandDetailValue.hasBeenModified()) {
            var command = CommandFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandDetailValue.getCommandPK());
            var commandDetail = command.getActiveDetailForUpdate();

            commandDetail.setThruTime(session.START_TIME_LONG);
            commandDetail.store();

            var commandPK = commandDetail.getCommandPK();
            var componentVendorPK = commandDetail.getComponentVendorPK(); // Not updated
            var commandName = commandDetailValue.getCommandName();
            var sortOrder = commandDetailValue.getSortOrder();

            commandDetail = CommandDetailFactory.getInstance().create(commandPK, componentVendorPK, commandName,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            command.setActiveDetail(commandDetail);
            command.setLastDetail(commandDetail);

            sendEvent(commandPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteCommand(Command command, BasePK deletedBy) {
        deleteCommandDescriptionsByCommand(command, deletedBy);

        var commandDetail = command.getLastDetailForUpdate();
        commandDetail.setThruTime(session.START_TIME_LONG);
        command.setActiveDetail(null);
        command.store();

        sendEvent(command.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCommandsByComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        var commands = getCommandsByComponentVendorForUpdate(componentVendor);

        commands.forEach((command) ->
                deleteCommand(command, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Command Descriptions
    // --------------------------------------------------------------------------------

    public CommandDescription createCommandDescription(Command command, Language language, String description,
            BasePK createdBy) {
        var commandDescription = CommandDescriptionFactory.getInstance().create(command,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(command.getPrimaryKey(), EventTypes.MODIFY, commandDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return commandDescription;
    }

    private CommandDescription getCommandDescription(Command command, Language language,
            EntityPermission entityPermission) {
        CommandDescription commandDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_lang_languageid = ? AND cmdd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_lang_languageid = ? AND cmdd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, command.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            commandDescription = CommandDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandDescription;
    }

    public CommandDescription getCommandDescription(Command command, Language language) {
        return getCommandDescription(command, language, EntityPermission.READ_ONLY);
    }

    public CommandDescription getCommandDescriptionForUpdate(Command command, Language language) {
        return getCommandDescription(command, language, EntityPermission.READ_WRITE);
    }

    public CommandDescriptionValue getCommandDescriptionValue(CommandDescription commandDescription) {
        return commandDescription == null? null: commandDescription.getCommandDescriptionValue().clone();
    }

    public CommandDescriptionValue getCommandDescriptionValueForUpdate(Command command, Language language) {
        return getCommandDescriptionValue(getCommandDescriptionForUpdate(command, language));
    }

    private List<CommandDescription> getCommandDescriptionsByCommand(Command command,
            EntityPermission entityPermission) {
        List<CommandDescription> commandDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions, languages " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_thrutime = ? AND cmdd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, command.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            commandDescriptions = CommandDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandDescriptions;
    }

    public List<CommandDescription> getCommandDescriptionsByCommand(Command command) {
        return getCommandDescriptionsByCommand(command, EntityPermission.READ_ONLY);
    }

    public List<CommandDescription> getCommandDescriptionsByCommandForUpdate(Command command) {
        return getCommandDescriptionsByCommand(command, EntityPermission.READ_WRITE);
    }

    public String getBestCommandDescription(Command command, Language language) {
        String description;
        var commandDescription = getCommandDescription(command, language);

        if(commandDescription == null && !language.getIsDefault()) {
            commandDescription = getCommandDescription(command, getPartyControl().getDefaultLanguage());
        }

        if(commandDescription == null) {
            description = command.getLastDetail().getCommandName();
        } else {
            description = commandDescription.getDescription();
        }

        return description;
    }

    public CommandDescriptionTransfer getCommandDescriptionTransfer(UserVisit userVisit, CommandDescription commandDescription) {
        return getCoreTransferCaches(userVisit).getCommandDescriptionTransferCache().getCommandDescriptionTransfer(commandDescription);
    }

    public List<CommandDescriptionTransfer> getCommandDescriptionTransfersByCommand(UserVisit userVisit,
            Command command) {
        var commandDescriptions = getCommandDescriptionsByCommand(command);
        List<CommandDescriptionTransfer> commandDescriptionTransfers = new ArrayList<>(commandDescriptions.size());
        var commandDescriptionTransferCache = getCoreTransferCaches(userVisit).getCommandDescriptionTransferCache();

        commandDescriptions.forEach((commandDescription) ->
                commandDescriptionTransfers.add(commandDescriptionTransferCache.getCommandDescriptionTransfer(commandDescription))
        );

        return commandDescriptionTransfers;
    }

    public void updateCommandDescriptionFromValue(CommandDescriptionValue commandDescriptionValue, BasePK updatedBy) {
        if(commandDescriptionValue.hasBeenModified()) {
            var commandDescription = CommandDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandDescriptionValue.getPrimaryKey());

            commandDescription.setThruTime(session.START_TIME_LONG);
            commandDescription.store();

            var command = commandDescription.getCommand();
            var language = commandDescription.getLanguage();
            var description = commandDescriptionValue.getDescription();

            commandDescription = CommandDescriptionFactory.getInstance().create(command, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(command.getPrimaryKey(), EventTypes.MODIFY, commandDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCommandDescription(CommandDescription commandDescription, BasePK deletedBy) {
        commandDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(commandDescription.getCommandPK(), EventTypes.MODIFY, commandDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteCommandDescriptionsByCommand(Command command, BasePK deletedBy) {
        var commandDescriptions = getCommandDescriptionsByCommandForUpdate(command);

        commandDescriptions.forEach((commandDescription) ->
                deleteCommandDescription(commandDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------

    public CommandMessageType createCommandMessageType(String commandMessageTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCommandMessageType = getDefaultCommandMessageType();
        var defaultFound = defaultCommandMessageType != null;

        if(defaultFound && isDefault) {
            var defaultCommandMessageTypeDetailValue = getDefaultCommandMessageTypeDetailValueForUpdate();

            defaultCommandMessageTypeDetailValue.setIsDefault(false);
            updateCommandMessageTypeFromValue(defaultCommandMessageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var commandMessageType = CommandMessageTypeFactory.getInstance().create();
        var commandMessageTypeDetail = CommandMessageTypeDetailFactory.getInstance().create(commandMessageType,
                commandMessageTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        commandMessageType = CommandMessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                commandMessageType.getPrimaryKey());
        commandMessageType.setActiveDetail(commandMessageTypeDetail);
        commandMessageType.setLastDetail(commandMessageTypeDetail);
        commandMessageType.store();

        sendEvent(commandMessageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return commandMessageType;
    }

    private List<CommandMessageType> getCommandMessageTypes(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid " +
                    "ORDER BY cmdmssgtydt_sortorder, cmdmssgtydt_commandmessagetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid " +
                    "FOR UPDATE";
        }

        var ps = CommandMessageTypeFactory.getInstance().prepareStatement(query);

        return CommandMessageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<CommandMessageType> getCommandMessageTypes() {
        return getCommandMessageTypes(EntityPermission.READ_ONLY);
    }

    public List<CommandMessageType> getCommandMessageTypesForUpdate() {
        return getCommandMessageTypes(EntityPermission.READ_WRITE);
    }

    private CommandMessageType getDefaultCommandMessageType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = CommandMessageTypeFactory.getInstance().prepareStatement(query);

        return CommandMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }

    public CommandMessageType getDefaultCommandMessageType() {
        return getDefaultCommandMessageType(EntityPermission.READ_ONLY);
    }

    public CommandMessageType getDefaultCommandMessageTypeForUpdate() {
        return getDefaultCommandMessageType(EntityPermission.READ_WRITE);
    }

    public CommandMessageTypeDetailValue getDefaultCommandMessageTypeDetailValueForUpdate() {
        return getDefaultCommandMessageTypeForUpdate().getLastDetailForUpdate().getCommandMessageTypeDetailValue().clone();
    }

    private CommandMessageType getCommandMessageTypeByName(String commandMessageTypeName, EntityPermission entityPermission) {
        CommandMessageType commandMessageType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypes, commandmessagetypedetails " +
                        "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_commandmessagetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypes, commandmessagetypedetails " +
                        "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_commandmessagetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, commandMessageTypeName);

            commandMessageType = CommandMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessageType;
    }

    public CommandMessageType getCommandMessageTypeByName(String commandMessageTypeName) {
        return getCommandMessageTypeByName(commandMessageTypeName, EntityPermission.READ_ONLY);
    }

    public CommandMessageType getCommandMessageTypeByNameForUpdate(String commandMessageTypeName) {
        return getCommandMessageTypeByName(commandMessageTypeName, EntityPermission.READ_WRITE);
    }

    public CommandMessageTypeDetailValue getCommandMessageTypeDetailValueForUpdate(CommandMessageType commandMessageType) {
        return commandMessageType == null? null: commandMessageType.getLastDetailForUpdate().getCommandMessageTypeDetailValue().clone();
    }

    public CommandMessageTypeDetailValue getCommandMessageTypeDetailValueByNameForUpdate(String commandMessageTypeName) {
        return getCommandMessageTypeDetailValueForUpdate(getCommandMessageTypeByNameForUpdate(commandMessageTypeName));
    }

    public CommandMessageTypeChoicesBean getCommandMessageTypeChoices(String defaultCommandMessageTypeChoice, Language language,
            boolean allowNullChoice) {
        var commandMessageTypes = getCommandMessageTypes();
        var size = commandMessageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCommandMessageTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var commandMessageType : commandMessageTypes) {
            var commandMessageTypeDetail = commandMessageType.getLastDetail();
            var label = getBestCommandMessageTypeDescription(commandMessageType, language);
            var value = commandMessageTypeDetail.getCommandMessageTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCommandMessageTypeChoice != null && defaultCommandMessageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && commandMessageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CommandMessageTypeChoicesBean(labels, values, defaultValue);
    }

    public CommandMessageTypeTransfer getCommandMessageTypeTransfer(UserVisit userVisit, CommandMessageType commandMessageType) {
        return getCoreTransferCaches(userVisit).getCommandMessageTypeTransferCache().getCommandMessageTypeTransfer(commandMessageType);
    }

    public List<CommandMessageTypeTransfer> getCommandMessageTypeTransfers(UserVisit userVisit) {
        var commandMessageTypes = getCommandMessageTypes();
        List<CommandMessageTypeTransfer> commandMessageTypeTransfers = new ArrayList<>(commandMessageTypes.size());
        var commandMessageTypeTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTypeTransferCache();

        commandMessageTypes.forEach((commandMessageType) ->
                commandMessageTypeTransfers.add(commandMessageTypeTransferCache.getCommandMessageTypeTransfer(commandMessageType))
        );

        return commandMessageTypeTransfers;
    }

    private void updateCommandMessageTypeFromValue(CommandMessageTypeDetailValue commandMessageTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(commandMessageTypeDetailValue.hasBeenModified()) {
            var commandMessageType = CommandMessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTypeDetailValue.getCommandMessageTypePK());
            var commandMessageTypeDetail = commandMessageType.getActiveDetailForUpdate();

            commandMessageTypeDetail.setThruTime(session.START_TIME_LONG);
            commandMessageTypeDetail.store();

            var commandMessageTypePK = commandMessageTypeDetail.getCommandMessageTypePK();
            var commandMessageTypeName = commandMessageTypeDetailValue.getCommandMessageTypeName();
            var isDefault = commandMessageTypeDetailValue.getIsDefault();
            var sortOrder = commandMessageTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCommandMessageType = getDefaultCommandMessageType();
                var defaultFound = defaultCommandMessageType != null && !defaultCommandMessageType.equals(commandMessageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCommandMessageTypeDetailValue = getDefaultCommandMessageTypeDetailValueForUpdate();

                    defaultCommandMessageTypeDetailValue.setIsDefault(false);
                    updateCommandMessageTypeFromValue(defaultCommandMessageTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            commandMessageTypeDetail = CommandMessageTypeDetailFactory.getInstance().create(commandMessageTypePK, commandMessageTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            commandMessageType.setActiveDetail(commandMessageTypeDetail);
            commandMessageType.setLastDetail(commandMessageTypeDetail);

            sendEvent(commandMessageTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCommandMessageTypeFromValue(CommandMessageTypeDetailValue commandMessageTypeDetailValue, BasePK updatedBy) {
        updateCommandMessageTypeFromValue(commandMessageTypeDetailValue, true, updatedBy);
    }

    public void deleteCommandMessageType(CommandMessageType commandMessageType, BasePK deletedBy) {
        deleteCommandMessagesByCommandMessageType(commandMessageType, deletedBy);
        deleteCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType, deletedBy);

        var commandMessageTypeDetail = commandMessageType.getLastDetailForUpdate();
        commandMessageTypeDetail.setThruTime(session.START_TIME_LONG);
        commandMessageType.setActiveDetail(null);
        commandMessageType.store();

        // Check for default, and pick one if necessary
        var defaultCommandMessageType = getDefaultCommandMessageType();
        if(defaultCommandMessageType == null) {
            var commandMessageTypes = getCommandMessageTypesForUpdate();

            if(!commandMessageTypes.isEmpty()) {
                var iter = commandMessageTypes.iterator();
                if(iter.hasNext()) {
                    defaultCommandMessageType = iter.next();
                }
                var commandMessageTypeDetailValue = Objects.requireNonNull(defaultCommandMessageType).getLastDetailForUpdate().getCommandMessageTypeDetailValue().clone();

                commandMessageTypeDetailValue.setIsDefault(true);
                updateCommandMessageTypeFromValue(commandMessageTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(commandMessageType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------

    public CommandMessageTypeDescription createCommandMessageTypeDescription(CommandMessageType commandMessageType,
            Language language, String description, BasePK createdBy) {
        var commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().create(commandMessageType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(commandMessageType.getPrimaryKey(), EventTypes.MODIFY, commandMessageTypeDescription.getPrimaryKey(),
                null, createdBy);

        return commandMessageTypeDescription;
    }

    private CommandMessageTypeDescription getCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language,
            EntityPermission entityPermission) {
        CommandMessageTypeDescription commandMessageTypeDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_lang_languageid = ? AND cmdmssgtyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_lang_languageid = ? AND cmdmssgtyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessageTypeDescription;
    }

    public CommandMessageTypeDescription getCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language) {
        return getCommandMessageTypeDescription(commandMessageType, language, EntityPermission.READ_ONLY);
    }

    public CommandMessageTypeDescription getCommandMessageTypeDescriptionForUpdate(CommandMessageType commandMessageType, Language language) {
        return getCommandMessageTypeDescription(commandMessageType, language, EntityPermission.READ_WRITE);
    }

    public CommandMessageTypeDescriptionValue getCommandMessageTypeDescriptionValue(CommandMessageTypeDescription commandMessageTypeDescription) {
        return commandMessageTypeDescription == null? null: commandMessageTypeDescription.getCommandMessageTypeDescriptionValue().clone();
    }

    public CommandMessageTypeDescriptionValue getCommandMessageTypeDescriptionValueForUpdate(CommandMessageType commandMessageType, Language language) {
        return getCommandMessageTypeDescriptionValue(getCommandMessageTypeDescriptionForUpdate(commandMessageType, language));
    }

    private List<CommandMessageTypeDescription> getCommandMessageTypeDescriptionsByCommandMessageType(CommandMessageType commandMessageType,
            EntityPermission entityPermission) {
        List<CommandMessageTypeDescription> commandMessageTypeDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions, languages " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_thrutime = ? AND cmdmssgtyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            commandMessageTypeDescriptions = CommandMessageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessageTypeDescriptions;
    }

    public List<CommandMessageTypeDescription> getCommandMessageTypeDescriptionsByCommandMessageType(CommandMessageType commandMessageType) {
        return getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType, EntityPermission.READ_ONLY);
    }

    public List<CommandMessageTypeDescription> getCommandMessageTypeDescriptionsByCommandMessageTypeForUpdate(CommandMessageType commandMessageType) {
        return getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType, EntityPermission.READ_WRITE);
    }

    public String getBestCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language) {
        String description;
        var commandMessageTypeDescription = getCommandMessageTypeDescription(commandMessageType, language);

        if(commandMessageTypeDescription == null && !language.getIsDefault()) {
            commandMessageTypeDescription = getCommandMessageTypeDescription(commandMessageType, getPartyControl().getDefaultLanguage());
        }

        if(commandMessageTypeDescription == null) {
            description = commandMessageType.getLastDetail().getCommandMessageTypeName();
        } else {
            description = commandMessageTypeDescription.getDescription();
        }

        return description;
    }

    public CommandMessageTypeDescriptionTransfer getCommandMessageTypeDescriptionTransfer(UserVisit userVisit, CommandMessageTypeDescription commandMessageTypeDescription) {
        return getCoreTransferCaches(userVisit).getCommandMessageTypeDescriptionTransferCache().getCommandMessageTypeDescriptionTransfer(commandMessageTypeDescription);
    }

    public List<CommandMessageTypeDescriptionTransfer> getCommandMessageTypeDescriptionTransfers(UserVisit userVisit, CommandMessageType commandMessageType) {
        var commandMessageTypeDescriptions = getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType);
        List<CommandMessageTypeDescriptionTransfer> commandMessageTypeDescriptionTransfers = new ArrayList<>(commandMessageTypeDescriptions.size());
        var commandMessageTypeDescriptionTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTypeDescriptionTransferCache();

        commandMessageTypeDescriptions.forEach((commandMessageTypeDescription) ->
                commandMessageTypeDescriptionTransfers.add(commandMessageTypeDescriptionTransferCache.getCommandMessageTypeDescriptionTransfer(commandMessageTypeDescription))
        );

        return commandMessageTypeDescriptionTransfers;
    }

    public void updateCommandMessageTypeDescriptionFromValue(CommandMessageTypeDescriptionValue commandMessageTypeDescriptionValue, BasePK updatedBy) {
        if(commandMessageTypeDescriptionValue.hasBeenModified()) {
            var commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTypeDescriptionValue.getPrimaryKey());

            commandMessageTypeDescription.setThruTime(session.START_TIME_LONG);
            commandMessageTypeDescription.store();

            var commandMessageType = commandMessageTypeDescription.getCommandMessageType();
            var language = commandMessageTypeDescription.getLanguage();
            var description = commandMessageTypeDescriptionValue.getDescription();

            commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().create(commandMessageType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(commandMessageType.getPrimaryKey(), EventTypes.MODIFY, commandMessageTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }

    public void deleteCommandMessageTypeDescription(CommandMessageTypeDescription commandMessageTypeDescription, BasePK deletedBy) {
        commandMessageTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(commandMessageTypeDescription.getCommandMessageTypePK(), EventTypes.MODIFY,
                commandMessageTypeDescription.getPrimaryKey(), null, deletedBy);
    }

    public void deleteCommandMessageTypeDescriptionsByCommandMessageType(CommandMessageType commandMessageType, BasePK deletedBy) {
        var commandMessageTypeDescriptions = getCommandMessageTypeDescriptionsByCommandMessageTypeForUpdate(commandMessageType);

        commandMessageTypeDescriptions.forEach((commandMessageTypeDescription) ->
                deleteCommandMessageTypeDescription(commandMessageTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Command Messages
    // --------------------------------------------------------------------------------

    public CommandMessage createCommandMessage(CommandMessageType commandMessageType, String commandMessageKey, BasePK createdBy) {
        var commandMessage = CommandMessageFactory.getInstance().create();
        var commandMessageDetail = CommandMessageDetailFactory.getInstance().create(commandMessage,
                commandMessageType, commandMessageKey, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        commandMessage = CommandMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                commandMessage.getPrimaryKey());
        commandMessage.setActiveDetail(commandMessageDetail);
        commandMessage.setLastDetail(commandMessageDetail);
        commandMessage.store();

        sendEvent(commandMessage.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return commandMessage;
    }

    private CommandMessage getCommandMessageByKey(CommandMessageType commandMessageType, String commandMessageKey, EntityPermission entityPermission) {
        CommandMessage commandMessage;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? AND cmdmssgdt_commandmessagekey = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? AND cmdmssgdt_commandmessagekey = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageFactory.getInstance().prepareStatement(query);

            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            ps.setString(2, commandMessageKey);

            commandMessage = CommandMessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessage;
    }

    public CommandMessage getCommandMessageByKey(CommandMessageType commandMessageType, String commandMessageKey) {
        return getCommandMessageByKey(commandMessageType, commandMessageKey, EntityPermission.READ_ONLY);
    }

    public CommandMessage getCommandMessageByKeyForUpdate(CommandMessageType commandMessageType, String commandMessageKey) {
        return getCommandMessageByKey(commandMessageType, commandMessageKey, EntityPermission.READ_WRITE);
    }

    public CommandMessageDetailValue getCommandMessageDetailValueForUpdate(CommandMessage commandMessage) {
        return commandMessage == null? null: commandMessage.getLastDetailForUpdate().getCommandMessageDetailValue().clone();
    }

    public CommandMessageDetailValue getCommandMessageDetailValueByKeyForUpdate(CommandMessageType commandMessageType, String commandMessageKey) {
        return getCommandMessageDetailValueForUpdate(getCommandMessageByKeyForUpdate(commandMessageType, commandMessageKey));
    }

    private List<CommandMessage> getCommandMessagesByCommandMessageType(CommandMessageType commandMessageType, EntityPermission entityPermission) {
        List<CommandMessage> commandMessages;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? " +
                        "ORDER BY cmdmssgdt_commandmessagekey";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageFactory.getInstance().prepareStatement(query);

            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());

            commandMessages = CommandMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessages;
    }

    public List<CommandMessage> getCommandMessagesByCommandMessageType(CommandMessageType commandMessageType) {
        return getCommandMessagesByCommandMessageType(commandMessageType, EntityPermission.READ_ONLY);
    }

    public List<CommandMessage> getCommandMessagesByCommandMessageTypeForUpdate(CommandMessageType commandMessageType) {
        return getCommandMessagesByCommandMessageType(commandMessageType, EntityPermission.READ_WRITE);
    }

    public CommandMessageTransfer getCommandMessageTransfer(UserVisit userVisit, CommandMessage commandMessage) {
        return getCoreTransferCaches(userVisit).getCommandMessageTransferCache().getCommandMessageTransfer(commandMessage);
    }

    private List<CommandMessageTransfer> getCommandMessageTransfers(UserVisit userVisit, Collection<CommandMessage> commandMessages) {
        List<CommandMessageTransfer> commandMessageTransfers = new ArrayList<>(commandMessages.size());
        var commandMessageTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTransferCache();

        commandMessages.forEach((commandMessage) ->
                commandMessageTransfers.add(commandMessageTransferCache.getCommandMessageTransfer(commandMessage))
        );

        return commandMessageTransfers;
    }

    public List<CommandMessageTransfer> getCommandMessageTransfers(UserVisit userVisit, CommandMessageType commandMessageType) {
        return getCommandMessageTransfers(userVisit, getCommandMessagesByCommandMessageType(commandMessageType));
    }

    public void updateCommandMessageFromValue(CommandMessageDetailValue commandMessageDetailValue, BasePK updatedBy) {
        if(commandMessageDetailValue.hasBeenModified()) {
            var commandMessage = CommandMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageDetailValue.getCommandMessagePK());
            var commandMessageDetail = commandMessage.getActiveDetailForUpdate();

            commandMessageDetail.setThruTime(session.START_TIME_LONG);
            commandMessageDetail.store();

            var commandMessagePK = commandMessageDetail.getCommandMessagePK();
            var commandMessageTypePK = commandMessageDetail.getCommandMessageTypePK(); // Not updated
            var commandMessageKey = commandMessageDetailValue.getCommandMessageKey();

            commandMessageDetail = CommandMessageDetailFactory.getInstance().create(commandMessagePK, commandMessageTypePK, commandMessageKey,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            commandMessage.setActiveDetail(commandMessageDetail);
            commandMessage.setLastDetail(commandMessageDetail);

            sendEvent(commandMessagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteCommandMessage(CommandMessage commandMessage, BasePK deletedBy) {
        deleteCommandMessageTranslationsByCommandMessage(commandMessage, deletedBy);

        var commandMessageDetail = commandMessage.getLastDetailForUpdate();
        commandMessageDetail.setThruTime(session.START_TIME_LONG);
        commandMessage.setActiveDetail(null);
        commandMessage.store();

        sendEvent(commandMessage.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCommandMessages(List<CommandMessage> commandMessages, BasePK deletedBy) {
        commandMessages.forEach((commandMessage) ->
                deleteCommandMessage(commandMessage, deletedBy)
        );
    }

    public void deleteCommandMessagesByCommandMessageType(CommandMessageType commandMessageType, BasePK deletedBy) {
        deleteCommandMessages(getCommandMessagesByCommandMessageTypeForUpdate(commandMessageType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Command Message Strings
    // --------------------------------------------------------------------------------

    public CommandMessageTranslation createCommandMessageTranslation(CommandMessage commandMessage, Language language, String translation, BasePK createdBy) {
        var commandMessageTranslation = CommandMessageTranslationFactory.getInstance().create(commandMessage, language, translation,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(commandMessageTranslation.getCommandMessagePK(), EventTypes.MODIFY, commandMessageTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return commandMessageTranslation;
    }

    private List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage, EntityPermission entityPermission) {
        List<CommandMessageTranslation> commandMessageTranslations;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations, languages " +
                        "WHERE cmdmssgtr_cmdmssg_commandMessageid = ? AND cmdmssgtr_thrutime = ? " +
                        "AND cmdmssgtr_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations " +
                        "WHERE cmdmssgtr_cmdmssg_commandMessageid = ? AND cmdmssgtr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageTranslationFactory.getInstance().prepareStatement(query);

            ps.setLong(1, commandMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            commandMessageTranslations = CommandMessageTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessageTranslations;
    }

    public List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage) {
        return getCommandMessageTranslationsByCommandMessage(commandMessage, EntityPermission.READ_ONLY);
    }

    public List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessageForUpdate(CommandMessage commandMessage) {
        return getCommandMessageTranslationsByCommandMessage(commandMessage, EntityPermission.READ_WRITE);
    }

    public CommandMessageTranslation getBestCommandMessageTranslation(CommandMessage commandMessage, Language language) {
        var commandMessageTranslation = getCommandMessageTranslation(commandMessage, language);

        if(commandMessageTranslation == null && !language.getIsDefault()) {
            commandMessageTranslation = getCommandMessageTranslation(commandMessage, getPartyControl().getDefaultLanguage());
        }

        return commandMessageTranslation;
    }

    private CommandMessageTranslation getCommandMessageTranslation(CommandMessage commandMessage, Language language, EntityPermission entityPermission) {
        CommandMessageTranslation commandMessageTranslation;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations " +
                        "WHERE cmdmssgtr_cmdmssg_commandmessageid = ? AND cmdmssgtr_lang_languageid = ? AND cmdmssgtr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations " +
                        "WHERE cmdmssgtr_cmdmssg_commandmessageid = ? AND cmdmssgtr_lang_languageid = ? AND cmdmssgtr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommandMessageTranslationFactory.getInstance().prepareStatement(query);

            ps.setLong(1, commandMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            commandMessageTranslation = CommandMessageTranslationFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return commandMessageTranslation;
    }

    public CommandMessageTranslation getCommandMessageTranslation(CommandMessage commandMessage, Language language) {
        return getCommandMessageTranslation(commandMessage, language, EntityPermission.READ_ONLY);
    }

    public CommandMessageTranslation getCommandMessageTranslationForUpdate(CommandMessage commandMessage, Language language) {
        return getCommandMessageTranslation(commandMessage, language, EntityPermission.READ_WRITE);
    }

    public CommandMessageTranslationValue getCommandMessageTranslationValue(CommandMessageTranslation commandMessageTranslation) {
        return commandMessageTranslation == null? null: commandMessageTranslation.getCommandMessageTranslationValue().clone();
    }

    public CommandMessageTranslationValue getCommandMessageTranslationValueForUpdate(CommandMessage commandMessage, Language language) {
        var commandMessageTranslation = getCommandMessageTranslationForUpdate(commandMessage, language);

        return commandMessageTranslation == null? null: getCommandMessageTranslationValue(commandMessageTranslation);
    }

    public List<CommandMessageTranslationTransfer> getCommandMessageTranslationTransfers(UserVisit userVisit, Collection<CommandMessageTranslation> commandMessageTranslations) {
        List<CommandMessageTranslationTransfer> commandMessageTranslationTransfers = new ArrayList<>(commandMessageTranslations.size());
        var commandMessageTranslationTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTranslationTransferCache();

        commandMessageTranslations.forEach((commandMessageTranslation) ->
                commandMessageTranslationTransfers.add(commandMessageTranslationTransferCache.getCommandMessageTranslationTransfer(commandMessageTranslation))
        );

        return commandMessageTranslationTransfers;
    }

    public List<CommandMessageTranslationTransfer> getCommandMessageTranslationTransfersByCommandMessage(UserVisit userVisit, CommandMessage commandMessage) {
        return getCommandMessageTranslationTransfers(userVisit, getCommandMessageTranslationsByCommandMessage(commandMessage));
    }

    public CommandMessageTranslationTransfer getCommandMessageTranslationTransfer(UserVisit userVisit, CommandMessageTranslation commandMessageTranslation) {
        return getCoreTransferCaches(userVisit).getCommandMessageTranslationTransferCache().getCommandMessageTranslationTransfer(commandMessageTranslation);
    }

    public void updateCommandMessageTranslationFromValue(CommandMessageTranslationValue commandMessageTranslationValue, BasePK updatedBy) {
        if(commandMessageTranslationValue.hasBeenModified()) {
            var commandMessageTranslation = CommandMessageTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTranslationValue.getPrimaryKey());

            commandMessageTranslation.setThruTime(session.START_TIME_LONG);
            commandMessageTranslation.store();

            var commandMessagePK = commandMessageTranslation.getCommandMessagePK(); // Not updated
            var languagePK = commandMessageTranslation.getLanguagePK(); // Not updated
            var translation = commandMessageTranslationValue.getTranslation();

            commandMessageTranslation = CommandMessageTranslationFactory.getInstance().create(commandMessagePK, languagePK, translation, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(commandMessagePK, EventTypes.MODIFY, commandMessageTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCommandMessageTranslation(CommandMessageTranslation commandMessageTranslation, BasePK deletedBy) {
        commandMessageTranslation.setThruTime(session.START_TIME_LONG);

        sendEvent(commandMessageTranslation.getCommandMessagePK(), EventTypes.MODIFY, commandMessageTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteCommandMessageTranslations(List<CommandMessageTranslation> commandMessageTranslations, BasePK deletedBy) {
        commandMessageTranslations.forEach((commandMessageTranslation) ->
                deleteCommandMessageTranslation(commandMessageTranslation, deletedBy)
        );
    }

    public void deleteCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage, BasePK deletedBy) {
        deleteCommandMessageTranslations(getCommandMessageTranslationsByCommandMessageForUpdate(commandMessage), deletedBy);
    }

}
