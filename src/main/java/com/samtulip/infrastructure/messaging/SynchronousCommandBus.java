/*
 * The MIT License
 *
 * Copyright 2015 Sam Tulip.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.samtulip.infrastructure.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sam Tulip
 */
public class SynchronousCommandBus implements CommandBus {

    private final Map<Class, Handler> handlers;

    public SynchronousCommandBus() {
        this.handlers = new HashMap<Class, Handler>();
    }

    @Override
    public <T extends Command> void Send(T command) {
        if (command == null) {
            throw new NullPointerException("Command should not be null");
        }
        final Handler<T> handler = this.handlers.get(command.getClass());
        if (handler != null) {
            handler.Handle(command);
        } else {
            throw new UnsupportedOperationException("No handler found for command " + command.getClass().getName());
        }
    }

    @Override
    public void Register(Handler<Command> handler) {
        if (handler == null) {
            throw new NullPointerException("Handler should not be null");
        }
        this.handlers.put(handler.getType(), handler);
    }

}
