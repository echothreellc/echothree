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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.CommandMessageTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.CommandDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTranslationTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.common.transfer.CommandTransfer;
import com.echothree.model.control.core.server.transfer.CommandDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTranslationTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTypeTransferCache;
import com.echothree.model.control.core.server.transfer.CommandTransferCache;
import com.echothree.model.data.core.common.pk.CommandMessagePK;
import com.echothree.model.data.core.common.pk.CommandMessageTypePK;
import com.echothree.model.data.core.common.pk.CommandPK;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.CommandDescription;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDescription;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMMANDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMMAND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMMAND_MESSAGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMMAND_MESSAGE_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMMAND_MESSAGE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMMAND_MESSAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.core.CommandDescriptions.CommandDescriptions;
import static com.echothree.model.jooq.server.tables.core.CommandDetails.CommandDetails;
import static com.echothree.model.jooq.server.tables.core.CommandMessageDetails.CommandMessageDetails;
import static com.echothree.model.jooq.server.tables.core.CommandMessageTranslations.CommandMessageTranslations;
import static com.echothree.model.jooq.server.tables.core.CommandMessageTypeDescriptions.CommandMessageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.CommandMessageTypeDetails.CommandMessageTypeDetails;
import static com.echothree.model.jooq.server.tables.core.CommandMessageTypes.CommandMessageTypes;
import static com.echothree.model.jooq.server.tables.core.CommandMessages.CommandMessages;
import static com.echothree.model.jooq.server.tables.core.Commands.Commands;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class CommandControl
        extends BaseModelControl {

    /** Creates a new instance of CommandControl */
    protected CommandControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Commands
    // --------------------------------------------------------------------------------

    @Inject
    protected CommandDetailFactory commandDetailFactory;

    @Inject
    protected CommandFactory commandFactory;

    @Inject
    CommandTransferCache commandTransferCache;

    public Command createCommand(ComponentVendor componentVendor, String commandName, Integer sortOrder, BasePK createdBy) {
        var command = commandFactory.create();
        var commandDetail = commandDetailFactory.create(command, componentVendor,
                commandName, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        command = commandFactory.getEntityFromPK(EntityPermission.READ_WRITE, command.getPrimaryKey());
        command.setActiveDetail(commandDetail);
        command.setLastDetail(commandDetail);
        command.store();

        sendEvent(command.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return command;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Command */
    public Command getCommandByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommandPK(entityInstance.getEntityUniqueId());

        return commandFactory.getEntityFromPK(entityPermission, pk);
    }

    public Command getCommandByEntityInstance(EntityInstance entityInstance) {
        return getCommandByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Command getCommandByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommandByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommands() {
        return session.getDslContext()
                .selectCount()
                .from(Commands)
                .join(CommandDetails).onKey(COMMANDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCommandsByComponentVendor(final ComponentVendor componentVendor) {
        return session.getDslContext()
                .selectCount()
                .from(Commands)
                .join(CommandDetails).onKey(COMMANDS_ACTIVE_DETAIL_FK)
                .where(CommandDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Command getCommandByName(ComponentVendor componentVendor, String commandName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Commands.fields())
                .from(Commands)
                .join(CommandDetails).onKey(COMMANDS_ACTIVE_DETAIL_FK)
                .where(CommandDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()),
                        CommandDetails.COMMAND_NAME.eq(commandName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Commands.fields())
                    .from(Commands)
                    .join(CommandDetails).onKey(COMMANDS_ACTIVE_DETAIL_FK)
                    .where(CommandDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()))
                    .orderBy(CommandDetails.SORT_ORDER, CommandDetails.COMMAND_NAME),
                    CommandFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Commands.fields())
                    .from(Commands)
                    .join(CommandDetails).onKey(COMMANDS_ACTIVE_DETAIL_FK)
                    .where(CommandDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()))
                    .forUpdate();
        };

        return commandFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Command> getCommandsByComponentVendor(ComponentVendor componentVendor) {
        return getCommandsByComponentVendor(componentVendor, EntityPermission.READ_ONLY);
    }

    public List<Command> getCommandsByComponentVendorForUpdate(ComponentVendor componentVendor) {
        return getCommandsByComponentVendor(componentVendor, EntityPermission.READ_WRITE);
    }

    public List<Command> getCommands() {
        var query = session.applyLimit(session.getDslContext()
                .select(Commands.fields())
                .from(Commands)
                .join(CommandDetails).onKey(COMMANDS_ACTIVE_DETAIL_FK)
                .orderBy(CommandDetails.SORT_ORDER, CommandDetails.COMMAND_NAME),
                CommandFactory.class);

        return commandFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public CommandTransfer getCommandTransfer(UserVisit userVisit, Command command) {
        return commandTransferCache.getCommandTransfer(userVisit, command);
    }

    public List<CommandTransfer> getCommandTransfers(UserVisit userVisit, Collection<Command> commands) {
        List<CommandTransfer> commandTransfers = new ArrayList<>(commands.size());

        commands.forEach((command) ->
                commandTransfers.add(commandTransferCache.getCommandTransfer(userVisit, command))
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
            var command = commandFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    commandDetailValue.getCommandPK());
            var commandDetail = command.getActiveDetailForUpdate();

            commandDetail.setThruTime(session.getStartTime());
            commandDetail.store();

            var commandPK = commandDetail.getCommandPK();
            var componentVendorPK = commandDetail.getComponentVendorPK(); // Not updated
            var commandName = commandDetailValue.getCommandName();
            var sortOrder = commandDetailValue.getSortOrder();

            commandDetail = commandDetailFactory.create(commandPK, componentVendorPK, commandName,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            command.setActiveDetail(commandDetail);
            command.setLastDetail(commandDetail);

            sendEvent(commandPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteCommand(Command command, BasePK deletedBy) {
        deleteCommandDescriptionsByCommand(command, deletedBy);

        var commandDetail = command.getLastDetailForUpdate();
        commandDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected CommandDescriptionFactory commandDescriptionFactory;

    @Inject
    CommandDescriptionTransferCache commandDescriptionTransferCache;

    public CommandDescription createCommandDescription(Command command, Language language, String description,
            BasePK createdBy) {
        var commandDescription = commandDescriptionFactory.create(command,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(command.getPrimaryKey(), EventTypes.MODIFY, commandDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return commandDescription;
    }

    private CommandDescription getCommandDescription(Command command, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommandDescriptions.fields())
                .from(CommandDescriptions)
                .where(CommandDescriptions.COMMAND.eq(command.getPrimaryKey()),
                        CommandDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CommandDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommandDescriptions.fields())
                    .from(CommandDescriptions)
                    .join(Languages).onKey(COMMAND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CommandDescriptions.COMMAND.eq(command.getPrimaryKey()),
                            CommandDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommandDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommandDescriptions.fields())
                    .from(CommandDescriptions)
                    .where(CommandDescriptions.COMMAND.eq(command.getPrimaryKey()),
                            CommandDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commandDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            commandDescription = getCommandDescription(command, partyControl.getDefaultLanguage());
        }

        if(commandDescription == null) {
            description = command.getLastDetail().getCommandName();
        } else {
            description = commandDescription.getDescription();
        }

        return description;
    }

    public CommandDescriptionTransfer getCommandDescriptionTransfer(UserVisit userVisit, CommandDescription commandDescription) {
        return commandDescriptionTransferCache.getCommandDescriptionTransfer(userVisit, commandDescription);
    }

    public List<CommandDescriptionTransfer> getCommandDescriptionTransfersByCommand(UserVisit userVisit,
            Command command) {
        var commandDescriptions = getCommandDescriptionsByCommand(command);
        List<CommandDescriptionTransfer> commandDescriptionTransfers = new ArrayList<>(commandDescriptions.size());

        commandDescriptions.forEach((commandDescription) ->
                commandDescriptionTransfers.add(commandDescriptionTransferCache.getCommandDescriptionTransfer(userVisit, commandDescription))
        );

        return commandDescriptionTransfers;
    }

    public void updateCommandDescriptionFromValue(CommandDescriptionValue commandDescriptionValue, BasePK updatedBy) {
        if(commandDescriptionValue.hasBeenModified()) {
            var commandDescription = commandDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    commandDescriptionValue.getPrimaryKey());

            commandDescription.setThruTime(session.getStartTime());
            commandDescription.store();

            var command = commandDescription.getCommand();
            var language = commandDescription.getLanguage();
            var description = commandDescriptionValue.getDescription();

            commandDescription = commandDescriptionFactory.create(command, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(command.getPrimaryKey(), EventTypes.MODIFY, commandDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCommandDescription(CommandDescription commandDescription, BasePK deletedBy) {
        commandDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected CommandMessageTypeDetailFactory commandMessageTypeDetailFactory;

    @Inject
    protected CommandMessageTypeFactory commandMessageTypeFactory;

    @Inject
    CommandMessageTypeTransferCache commandMessageTypeTransferCache;

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

        var commandMessageType = commandMessageTypeFactory.create();
        var commandMessageTypeDetail = commandMessageTypeDetailFactory.create(commandMessageType,
                commandMessageTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        commandMessageType = commandMessageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                commandMessageType.getPrimaryKey());
        commandMessageType.setActiveDetail(commandMessageTypeDetail);
        commandMessageType.setLastDetail(commandMessageTypeDetail);
        commandMessageType.store();

        sendEvent(commandMessageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return commandMessageType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommandMessageType */
    public CommandMessageType getCommandMessageTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommandMessageTypePK(entityInstance.getEntityUniqueId());

        return commandMessageTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommandMessageType getCommandMessageTypeByEntityInstance(EntityInstance entityInstance) {
        return getCommandMessageTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommandMessageType getCommandMessageTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommandMessageTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommandMessageTypes() {
        return session.getDslContext()
                .selectCount()
                .from(CommandMessageTypes)
                .join(CommandMessageTypeDetails).onKey(COMMAND_MESSAGE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CommandMessageType> getCommandMessageTypes(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommandMessageTypes.fields())
                    .from(CommandMessageTypes)
                    .join(CommandMessageTypeDetails).onKey(COMMAND_MESSAGE_TYPES_ACTIVE_DETAIL_FK)
                    .orderBy(CommandMessageTypeDetails.SORT_ORDER, CommandMessageTypeDetails.COMMAND_MESSAGE_TYPE_NAME),
                    CommandMessageTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommandMessageTypes.fields())
                    .from(CommandMessageTypes)
                    .where(CommandMessageTypes.ACTIVE_DETAIL.isNotNull())
                    .forUpdate();
        };

        return commandMessageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CommandMessageType> getCommandMessageTypes() {
        return getCommandMessageTypes(EntityPermission.READ_ONLY);
    }

    public List<CommandMessageType> getCommandMessageTypesForUpdate() {
        return getCommandMessageTypes(EntityPermission.READ_WRITE);
    }

    private CommandMessageType getDefaultCommandMessageType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommandMessageTypes.fields())
                .from(CommandMessageTypes)
                .join(CommandMessageTypeDetails).onKey(COMMAND_MESSAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(CommandMessageTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandMessageTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(CommandMessageTypes.fields())
                .from(CommandMessageTypes)
                .join(CommandMessageTypeDetails).onKey(COMMAND_MESSAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(CommandMessageTypeDetails.COMMAND_MESSAGE_TYPE_NAME.eq(commandMessageTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandMessageTypeFactory.getEntityFromQuery(entityPermission, query);
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
        return commandMessageTypeTransferCache.getCommandMessageTypeTransfer(userVisit, commandMessageType);
    }

    public List<CommandMessageTypeTransfer> getCommandMessageTypeTransfers(UserVisit userVisit, Collection<CommandMessageType> commandMessageTypes) {
        List<CommandMessageTypeTransfer> commandMessageTypeTransfers = new ArrayList<>(commandMessageTypes.size());

        commandMessageTypes.forEach((commandMessageType) ->
                commandMessageTypeTransfers.add(commandMessageTypeTransferCache.getCommandMessageTypeTransfer(userVisit, commandMessageType))
        );

        return commandMessageTypeTransfers;
    }

    public List<CommandMessageTypeTransfer> getCommandMessageTypeTransfers(UserVisit userVisit) {
        return getCommandMessageTypeTransfers(userVisit, getCommandMessageTypes());
    }

    private void updateCommandMessageTypeFromValue(CommandMessageTypeDetailValue commandMessageTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(commandMessageTypeDetailValue.hasBeenModified()) {
            var commandMessageType = commandMessageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTypeDetailValue.getCommandMessageTypePK());
            var commandMessageTypeDetail = commandMessageType.getActiveDetailForUpdate();

            commandMessageTypeDetail.setThruTime(session.getStartTime());
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

            commandMessageTypeDetail = commandMessageTypeDetailFactory.create(commandMessageTypePK, commandMessageTypeName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

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
        commandMessageTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected CommandMessageTypeDescriptionFactory commandMessageTypeDescriptionFactory;

    @Inject
    CommandMessageTypeDescriptionTransferCache commandMessageTypeDescriptionTransferCache;

    public CommandMessageTypeDescription createCommandMessageTypeDescription(CommandMessageType commandMessageType,
            Language language, String description, BasePK createdBy) {
        var commandMessageTypeDescription = commandMessageTypeDescriptionFactory.create(commandMessageType,
                language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(commandMessageType.getPrimaryKey(), EventTypes.MODIFY, commandMessageTypeDescription.getPrimaryKey(),
                null, createdBy);

        return commandMessageTypeDescription;
    }

    private CommandMessageTypeDescription getCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommandMessageTypeDescriptions.fields())
                .from(CommandMessageTypeDescriptions)
                .where(CommandMessageTypeDescriptions.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()),
                        CommandMessageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CommandMessageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandMessageTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommandMessageTypeDescriptions.fields())
                    .from(CommandMessageTypeDescriptions)
                    .join(Languages).onKey(COMMAND_MESSAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CommandMessageTypeDescriptions.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()),
                            CommandMessageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommandMessageTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommandMessageTypeDescriptions.fields())
                    .from(CommandMessageTypeDescriptions)
                    .where(CommandMessageTypeDescriptions.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()),
                            CommandMessageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commandMessageTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            commandMessageTypeDescription = getCommandMessageTypeDescription(commandMessageType, partyControl.getDefaultLanguage());
        }

        if(commandMessageTypeDescription == null) {
            description = commandMessageType.getLastDetail().getCommandMessageTypeName();
        } else {
            description = commandMessageTypeDescription.getDescription();
        }

        return description;
    }

    public CommandMessageTypeDescriptionTransfer getCommandMessageTypeDescriptionTransfer(UserVisit userVisit, CommandMessageTypeDescription commandMessageTypeDescription) {
        return commandMessageTypeDescriptionTransferCache.getCommandMessageTypeDescriptionTransfer(userVisit, commandMessageTypeDescription);
    }

    public List<CommandMessageTypeDescriptionTransfer> getCommandMessageTypeDescriptionTransfers(UserVisit userVisit, CommandMessageType commandMessageType) {
        var commandMessageTypeDescriptions = getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType);
        List<CommandMessageTypeDescriptionTransfer> commandMessageTypeDescriptionTransfers = new ArrayList<>(commandMessageTypeDescriptions.size());

        commandMessageTypeDescriptions.forEach((commandMessageTypeDescription) ->
                commandMessageTypeDescriptionTransfers.add(commandMessageTypeDescriptionTransferCache.getCommandMessageTypeDescriptionTransfer(userVisit, commandMessageTypeDescription))
        );

        return commandMessageTypeDescriptionTransfers;
    }

    public void updateCommandMessageTypeDescriptionFromValue(CommandMessageTypeDescriptionValue commandMessageTypeDescriptionValue, BasePK updatedBy) {
        if(commandMessageTypeDescriptionValue.hasBeenModified()) {
            var commandMessageTypeDescription = commandMessageTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTypeDescriptionValue.getPrimaryKey());

            commandMessageTypeDescription.setThruTime(session.getStartTime());
            commandMessageTypeDescription.store();

            var commandMessageType = commandMessageTypeDescription.getCommandMessageType();
            var language = commandMessageTypeDescription.getLanguage();
            var description = commandMessageTypeDescriptionValue.getDescription();

            commandMessageTypeDescription = commandMessageTypeDescriptionFactory.create(commandMessageType, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(commandMessageType.getPrimaryKey(), EventTypes.MODIFY, commandMessageTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }

    public void deleteCommandMessageTypeDescription(CommandMessageTypeDescription commandMessageTypeDescription, BasePK deletedBy) {
        commandMessageTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected CommandMessageDetailFactory commandMessageDetailFactory;

    @Inject
    protected CommandMessageFactory commandMessageFactory;

    @Inject
    CommandMessageTransferCache commandMessageTransferCache;

    public CommandMessage createCommandMessage(CommandMessageType commandMessageType, String commandMessageKey, BasePK createdBy) {
        var commandMessage = commandMessageFactory.create();
        var commandMessageDetail = commandMessageDetailFactory.create(commandMessage,
                commandMessageType, commandMessageKey, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        commandMessage = commandMessageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                commandMessage.getPrimaryKey());
        commandMessage.setActiveDetail(commandMessageDetail);
        commandMessage.setLastDetail(commandMessageDetail);
        commandMessage.store();

        sendEvent(commandMessage.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return commandMessage;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommandMessage */
    public CommandMessage getCommandMessageByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommandMessagePK(entityInstance.getEntityUniqueId());

        return commandMessageFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommandMessage getCommandMessageByEntityInstance(EntityInstance entityInstance) {
        return getCommandMessageByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommandMessage getCommandMessageByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommandMessageByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommandMessagesByCommandMessageType(final CommandMessageType commandMessageType) {
        return session.getDslContext()
                .selectCount()
                .from(CommandMessages)
                .join(CommandMessageDetails).onKey(COMMAND_MESSAGES_ACTIVE_DETAIL_FK)
                .where(CommandMessageDetails.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CommandMessage getCommandMessageByKey(CommandMessageType commandMessageType, String commandMessageKey, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommandMessages.fields())
                .from(CommandMessages)
                .join(CommandMessageDetails).onKey(COMMAND_MESSAGES_ACTIVE_DETAIL_FK)
                .where(CommandMessageDetails.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()),
                        CommandMessageDetails.COMMAND_MESSAGE_KEY.eq(commandMessageKey));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandMessageFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommandMessages.fields())
                    .from(CommandMessages)
                    .join(CommandMessageDetails).onKey(COMMAND_MESSAGES_ACTIVE_DETAIL_FK)
                    .where(CommandMessageDetails.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()))
                    .orderBy(CommandMessageDetails.COMMAND_MESSAGE_KEY),
                    CommandMessageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommandMessages.fields())
                    .from(CommandMessages)
                    .join(CommandMessageDetails).onKey(COMMAND_MESSAGES_ACTIVE_DETAIL_FK)
                    .where(CommandMessageDetails.COMMAND_MESSAGE_TYPE.eq(commandMessageType.getPrimaryKey()))
                    .forUpdate();
        };

        return commandMessageFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CommandMessage> getCommandMessagesByCommandMessageType(CommandMessageType commandMessageType) {
        return getCommandMessagesByCommandMessageType(commandMessageType, EntityPermission.READ_ONLY);
    }

    public List<CommandMessage> getCommandMessagesByCommandMessageTypeForUpdate(CommandMessageType commandMessageType) {
        return getCommandMessagesByCommandMessageType(commandMessageType, EntityPermission.READ_WRITE);
    }

    public CommandMessageTransfer getCommandMessageTransfer(UserVisit userVisit, CommandMessage commandMessage) {
        return commandMessageTransferCache.getCommandMessageTransfer(userVisit, commandMessage);
    }

    public List<CommandMessageTransfer> getCommandMessageTransfers(UserVisit userVisit, Collection<CommandMessage> commandMessages) {
        List<CommandMessageTransfer> commandMessageTransfers = new ArrayList<>(commandMessages.size());

        commandMessages.forEach((commandMessage) ->
                commandMessageTransfers.add(commandMessageTransferCache.getCommandMessageTransfer(userVisit, commandMessage))
        );

        return commandMessageTransfers;
    }

    public List<CommandMessageTransfer> getCommandMessageTransfers(UserVisit userVisit, CommandMessageType commandMessageType) {
        return getCommandMessageTransfers(userVisit, getCommandMessagesByCommandMessageType(commandMessageType));
    }

    public void updateCommandMessageFromValue(CommandMessageDetailValue commandMessageDetailValue, BasePK updatedBy) {
        if(commandMessageDetailValue.hasBeenModified()) {
            var commandMessage = commandMessageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageDetailValue.getCommandMessagePK());
            var commandMessageDetail = commandMessage.getActiveDetailForUpdate();

            commandMessageDetail.setThruTime(session.getStartTime());
            commandMessageDetail.store();

            var commandMessagePK = commandMessageDetail.getCommandMessagePK();
            var commandMessageTypePK = commandMessageDetail.getCommandMessageTypePK(); // Not updated
            var commandMessageKey = commandMessageDetailValue.getCommandMessageKey();

            commandMessageDetail = commandMessageDetailFactory.create(commandMessagePK, commandMessageTypePK, commandMessageKey,
                    session.getStartTime(), Session.MAX_TIME);

            commandMessage.setActiveDetail(commandMessageDetail);
            commandMessage.setLastDetail(commandMessageDetail);

            sendEvent(commandMessagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteCommandMessage(CommandMessage commandMessage, BasePK deletedBy) {
        deleteCommandMessageTranslationsByCommandMessage(commandMessage, deletedBy);

        var commandMessageDetail = commandMessage.getLastDetailForUpdate();
        commandMessageDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected CommandMessageTranslationFactory commandMessageTranslationFactory;

    @Inject
    CommandMessageTranslationTransferCache commandMessageTranslationTransferCache;

    public CommandMessageTranslation createCommandMessageTranslation(CommandMessage commandMessage, Language language, String translation, BasePK createdBy) {
        var commandMessageTranslation = commandMessageTranslationFactory.create(commandMessage, language, translation,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(commandMessageTranslation.getCommandMessagePK(), EventTypes.MODIFY, commandMessageTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return commandMessageTranslation;
    }

    private List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommandMessageTranslations.fields())
                    .from(CommandMessageTranslations)
                    .join(Languages).onKey(COMMAND_MESSAGE_TRANSLATIONS_LANGUAGE_FK)
                    .where(CommandMessageTranslations.COMMAND_MESSAGE.eq(commandMessage.getPrimaryKey()),
                            CommandMessageTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommandMessageTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommandMessageTranslations.fields())
                    .from(CommandMessageTranslations)
                    .where(CommandMessageTranslations.COMMAND_MESSAGE.eq(commandMessage.getPrimaryKey()),
                            CommandMessageTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commandMessageTranslationFactory.getEntitiesFromQuery(entityPermission, query);
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
            commandMessageTranslation = getCommandMessageTranslation(commandMessage, partyControl.getDefaultLanguage());
        }

        return commandMessageTranslation;
    }

    private CommandMessageTranslation getCommandMessageTranslation(CommandMessage commandMessage, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommandMessageTranslations.fields())
                .from(CommandMessageTranslations)
                .where(CommandMessageTranslations.COMMAND_MESSAGE.eq(commandMessage.getPrimaryKey()),
                        CommandMessageTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        CommandMessageTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commandMessageTranslationFactory.getEntityFromQuery(entityPermission, query);
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

        commandMessageTranslations.forEach((commandMessageTranslation) ->
                commandMessageTranslationTransfers.add(commandMessageTranslationTransferCache.getCommandMessageTranslationTransfer(userVisit, commandMessageTranslation))
        );

        return commandMessageTranslationTransfers;
    }

    public List<CommandMessageTranslationTransfer> getCommandMessageTranslationTransfersByCommandMessage(UserVisit userVisit, CommandMessage commandMessage) {
        return getCommandMessageTranslationTransfers(userVisit, getCommandMessageTranslationsByCommandMessage(commandMessage));
    }

    public CommandMessageTranslationTransfer getCommandMessageTranslationTransfer(UserVisit userVisit, CommandMessageTranslation commandMessageTranslation) {
        return commandMessageTranslationTransferCache.getCommandMessageTranslationTransfer(userVisit, commandMessageTranslation);
    }

    public void updateCommandMessageTranslationFromValue(CommandMessageTranslationValue commandMessageTranslationValue, BasePK updatedBy) {
        if(commandMessageTranslationValue.hasBeenModified()) {
            var commandMessageTranslation = commandMessageTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTranslationValue.getPrimaryKey());

            commandMessageTranslation.setThruTime(session.getStartTime());
            commandMessageTranslation.store();

            var commandMessagePK = commandMessageTranslation.getCommandMessagePK(); // Not updated
            var languagePK = commandMessageTranslation.getLanguagePK(); // Not updated
            var translation = commandMessageTranslationValue.getTranslation();

            commandMessageTranslation = commandMessageTranslationFactory.create(commandMessagePK, languagePK, translation, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(commandMessagePK, EventTypes.MODIFY, commandMessageTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCommandMessageTranslation(CommandMessageTranslation commandMessageTranslation, BasePK deletedBy) {
        commandMessageTranslation.setThruTime(session.getStartTime());

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
