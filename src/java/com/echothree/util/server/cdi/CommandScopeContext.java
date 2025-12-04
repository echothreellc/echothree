package com.echothree.util.server.cdi;

import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandScopeContext implements Context {

    private static final Logger log = LoggerFactory.getLogger(CommandScopeContext.class);

    /**
     * Per-thread stack of context layers.
     * Each layer holds the contextual instances for that "sub-scope".
     */
    private final ThreadLocal<Deque<ContextLayer>> layers = ThreadLocal.withInitial(ArrayDeque::new);

    @Override
    public Class<? extends Annotation> getScope() {
        log.debug("CommandScopeContext.getScope()");
        return CommandScope.class;
    }

    @Override
    public boolean isActive() {
        final var isActive = !layers.get().isEmpty();

        log.debug("CommandScopeContext.isActive() = {}", isActive);

        return isActive;
    }

    // --------------------------------------------------------------------------------
    //   Public Lifecycle API
    // --------------------------------------------------------------------------------

    /**
     * Activate the root scope for the current thread if not already active.
     * Typically called at the start of a request.
     */
    public void activate() {
        log.debug("CommandScopeContext.activate()");

        final var deque = layers.get();
        if(deque.isEmpty()) {
            deque.push(new ContextLayer());
        }
    }

    /**
     * Deactivate the scope for the current thread, destroying all layers.
     * Typically called at the end of a request.
     */
    public void deactivate() {
        log.debug("CommandScopeContext.deactivate()");

        final var deque = layers.get();
        while (!deque.isEmpty()) {
            destroyLayer(deque.pop());
        }

        log.debug("layers empty, removing ThreadLocal");
        layers.remove();
    }

    /**
     * Push a new nested layer on top of the current one.
     * Returns an AutoCloseable handle so you can use try-with-resources.
     */
    public ScopeHandle push() {
        log.debug("CommandScopeContext.push()");

        if(!isActive()) {
            // You can choose to implicitly activate() here instead
            throw new ContextNotActiveException("@StackedRequestScoped context is not active; call activate() first");
        }
        
        layers.get().push(new ContextLayer());
        return new ScopeHandle();
    }

    /**
     * Pop the current layer, destroying its beans.
     */
    public void pop() {
        log.debug("CommandScopeContext.pop()");

        final var deque = layers.get();
        if(deque.isEmpty()) {
            throw new ContextNotActiveException("No active @StackedRequestScoped layer to pop");
        }

        final var layer = deque.pop();
        destroyLayer(layer);

        if(deque.isEmpty()) {
            log.debug("layers empty, removing ThreadLocal");

            // Optionally clean up thread-local completely
            layers.remove();
        } else {
            log.debug("{} layers remaining", deque.size());
        }
    }

    // --------------------------------------------------------------------------------
    //   CDI Context Methods
    // --------------------------------------------------------------------------------

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
        log.debug("CommandScopeContext.get(Contextual<T> contextual, CreationalContext<T> creationalContext)");

        final var layer = currentLayer();
        var handle = (InstanceHandle<T>) layer.instances.get(contextual);
        if(handle != null) {
            return handle.instance;
        }

        if(creationalContext == null) {
            return null;
        }

        final var instance = contextual.create(creationalContext);
        handle = new InstanceHandle<>(instance, creationalContext);
        layer.instances.put(contextual, handle);

        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final Contextual<T> contextual) {
        log.debug("CommandScopeContext.get(Contextual<T> contextual)");

        final var layer = currentLayer();
        final var handle = (InstanceHandle<T>) layer.instances.get(contextual);

        return handle != null ? handle.instance : null;
    }

    // --------------------------------------------------------------------------------
    //   Internal Helpers
    // --------------------------------------------------------------------------------

    private ContextLayer currentLayer() {
        final var deque = layers.get();

        if(deque.isEmpty()) {
            throw new ContextNotActiveException("@Command context is not active");
        }

        return deque.peek();
    }

    private void destroyLayer(final ContextLayer layer) {
        for (var entry : layer.instances.entrySet()) {
            final var contextual = entry.getKey();
            final var handle = entry.getValue();

            try {
                // Generics on Contextual#destroy require matching <T> for both the instance and the
                // CreationalContext<T>. Since we store them in wildcarded holders, cast safely here.
                @SuppressWarnings("unchecked")
                Contextual<Object> typedContextual = (Contextual<Object>) contextual;
                @SuppressWarnings("unchecked")
                CreationalContext<Object> cc = (CreationalContext<Object>) handle.creationalContext;

                log.debug("destroying {}", handle.instance.getClass().getName());
                typedContextual.destroy(handle.instance, cc);
            } catch (Exception e) {
                // Log and continue; don't stop destroying other beans
                log.warn("Exception destroying bean {}", handle.instance.getClass().getName(), e);
            }
        }
        layer.instances.clear();
    }

    private static final class ContextLayer {
        final Map<Contextual<?>, InstanceHandle<?>> instances = new HashMap<>();
    }

    private record InstanceHandle<T>(T instance, CreationalContext<T> creationalContext) {
    }

    /**
     * Handle used for try-with-resources push/pop.
     */
    public final class ScopeHandle implements AutoCloseable {
        private boolean closed;

        private ScopeHandle() {
        }

        @Override
        public void close() {
            if(!closed) {
                pop();
                closed = true;
            }
        }
    }

}
