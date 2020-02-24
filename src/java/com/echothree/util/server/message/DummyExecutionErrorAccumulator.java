package com.echothree.util.server.message;

import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;

public class DummyExecutionErrorAccumulator
        implements ExecutionErrorAccumulator{

    private Messages executionErrors = null;

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
        return executionErrors == null ? false : executionErrors.size(Messages.EXECUTION_ERROR) != 0;
    }

}
