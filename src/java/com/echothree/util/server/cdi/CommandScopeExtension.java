package com.echothree.util.server.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class CommandScopeExtension implements Extension {

    private static CommandScopeContext commandScopeContext;

    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
        System.err.println("CommandScopeExtension.afterBeanDiscovery()");

        commandScopeContext = new CommandScopeContext();
        abd.addContext(commandScopeContext);
    }

    public static CommandScopeContext getMyCustomScopeContext() {
        return commandScopeContext;
    }

}
