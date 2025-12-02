package com.echothree.util.server.cdi;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public class CommandScopeContext implements Context {

    private final Map<Contextual<?>, Object> beanInstances = new HashMap<>();

    @Override
    public Class<? extends Annotation> getScope() {
        System.err.println("CommandScopeContext.getScope()");
        return CommandScope.class;
    }

    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        System.err.println("get(Contextual<T> contextual, CreationalContext<T> creationalContext)");
        if (!isActive()) {
            return null;
        }
        // Retrieve or create the bean instance
        T instance = (T) beanInstances.get(contextual);
        if (instance == null) {
            instance = contextual.create(creationalContext);
            beanInstances.put(contextual, instance);
        }
        return instance;
    }

    @Override
    public <T> T get(Contextual<T> contextual) {
        System.err.println("CommandScopeContext.get(Contextual<T> contextual)");
        if (!isActive()) {
            return null;
        }
        return (T) beanInstances.get(contextual);
    }

    @Override
    public boolean isActive() {
        System.err.println("CommandScopeContext.isActive()");
        // Define when your custom scope is active
        // This could be based on a thread-local, request attribute, etc.
        return true; // For demonstration, assume it's always active
    }

    public void activate() {
        System.err.println("CommandScopeContext.activate()");
    }

    public void deactivate() {
        System.err.println("CommandScopeContext.deactivate()");
    }

//    // You might also need methods to activate/deactivate the context,
//    // and to destroy beans when the context ends.
//    public void activate() {
//        // Logic to activate the context
//    }
//
//    public void deactivate() {
//        // Logic to deactivate the context and destroy beans
//        beanInstances.forEach((contextual, instance) -> contextual.destroy(instance));
//        beanInstances.clear();
//    }
}
