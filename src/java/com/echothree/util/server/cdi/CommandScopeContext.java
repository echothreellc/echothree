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
    private final ThreadLocal<Deque<ContextLayer>> layers =
            ThreadLocal.withInitial(ArrayDeque::new);

    @Override
    public Class<? extends Annotation> getScope() {
        log.info("CommandScopeContext.getScope()");
        return CommandScope.class;
    }

    @Override
    public boolean isActive() {
        var isActive = !layers.get().isEmpty();

        log.info("CommandScopeContext.isActive() = {}", isActive);

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
        log.info("CommandScopeContext.activate()");

        Deque<ContextLayer> deque = layers.get();
        if (deque.isEmpty()) {
            deque.push(new ContextLayer());
        }
    }

    /**
     * Deactivate the scope for the current thread, destroying all layers.
     * Typically called at the end of a request.
     */
    public void deactivate() {
        log.info("CommandScopeContext.deactivate()");

        Deque<ContextLayer> deque = layers.get();
        while (!deque.isEmpty()) {
            destroyLayer(deque.pop());
        }

        log.info("layers empty, removing ThreadLocal");
        layers.remove();
    }

    /**
     * Push a new nested layer on top of the current one.
     * Returns an AutoCloseable handle so you can use try-with-resources.
     */
    public ScopeHandle push() {
        log.info("CommandScopeContext.push()");

        if (!isActive()) {
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
        log.info("CommandScopeContext.pop()");

        Deque<ContextLayer> deque = layers.get();
        if (deque.isEmpty()) {
            throw new ContextNotActiveException("No active @StackedRequestScoped layer to pop");
        }

        ContextLayer layer = deque.pop();
        destroyLayer(layer);

        if (deque.isEmpty()) {
            log.info("layers empty, removing ThreadLocal");

            // Optionally clean up thread-local completely
            layers.remove();
        } else {
            log.info("{} layers remaining", deque.size());
        }
    }

    // --------------------------------------------------------------------------------
    //   CDI Context Methods
    // --------------------------------------------------------------------------------

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        log.info("CommandScopeContext.get(Contextual<T> contextual, CreationalContext<T> creationalContext)");

        ContextLayer layer = currentLayer();
        InstanceHandle<T> handle = (InstanceHandle<T>) layer.instances.get(contextual);
        if (handle != null) {
            return handle.instance;
        }

        if (creationalContext == null) {
            return null;
        }

        T instance = contextual.create(creationalContext);
        handle = new InstanceHandle<>(instance, creationalContext);
        layer.instances.put(contextual, handle);
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Contextual<T> contextual) {
        log.info("CommandScopeContext.get(Contextual<T> contextual)");

        ContextLayer layer = currentLayer();
        InstanceHandle<T> handle = (InstanceHandle<T>) layer.instances.get(contextual);
        return handle != null ? handle.instance : null;
    }

    // --------------------------------------------------------------------------------
    //   Internal Helpers
    // --------------------------------------------------------------------------------

    private ContextLayer currentLayer() {
        Deque<ContextLayer> deque = layers.get();

        if (deque.isEmpty()) {
            throw new ContextNotActiveException("@Command context is not active");
        }

        return deque.peek();
    }

    private void destroyLayer(ContextLayer layer) {
        for (Map.Entry<Contextual<?>, InstanceHandle<?>> entry : layer.instances.entrySet()) {
            Contextual<?> contextual = entry.getKey();
            InstanceHandle<?> handle = entry.getValue();

            try {
                // Generics on Contextual#destroy require matching <T> for both the instance and the
                // CreationalContext<T>. Since we store them in wildcarded holders, cast safely here.
                @SuppressWarnings({"rawtypes", "unchecked"})
                Contextual rawContextual = contextual;
                @SuppressWarnings("unchecked")
                CreationalContext<Object> cc = (CreationalContext<Object>) handle.creationalContext;

                log.info("destroying " + handle.instance.getClass().getName());
                rawContextual.destroy(handle.instance, cc);
            } catch (Exception e) {
                // Log and continue; don't stop destroying other beans
                e.printStackTrace();
            }
        }
        layer.instances.clear();
    }

    private static final class ContextLayer {
        final Map<Contextual<?>, InstanceHandle<?>> instances = new HashMap<>();
    }

    private static final class InstanceHandle<T> {
        final T instance;
        final CreationalContext<T> creationalContext;

        InstanceHandle(T instance, CreationalContext<T> creationalContext) {
            this.instance = instance;
            this.creationalContext = creationalContext;
        }
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
            if (!closed) {
                pop();
                closed = true;
            }
        }
    }

}
