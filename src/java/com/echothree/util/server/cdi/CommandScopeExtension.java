package com.echothree.util.server.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandScopeExtension implements Extension {

    private static final Logger log = LoggerFactory.getLogger(CommandScopeContext.class);

    private static CommandScopeContext commandScopeContext;

    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
        log.debug("CommandScopeExtension.afterBeanDiscovery()");

        commandScopeContext = new CommandScopeContext();
        abd.addContext(commandScopeContext);
    }

    public static CommandScopeContext getCommandScopeContext() {
        return commandScopeContext;
    }

}
