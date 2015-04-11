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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the EventBus. Runs event handlers synchronously.
 * @author Sam Tulip
 */
public class SynchronousEventBus implements EventBus {

    private final Map<Class, List<Handler<Event>>> handlers;

    public SynchronousEventBus() {
        this.handlers = new HashMap<Class, List<Handler<Event>>>();
    }

    @Override
    public <T extends Event> void publish(T event) {
        if (event == null) {
            throw new NullPointerException("Event should not be null");
        }
        final List<Handler<Event>> eventHandlers = this.handlers.get(event.getClass());
        if (eventHandlers != null) {
            for (final Handler handler : eventHandlers) {
                handler.Handle(event);
            }
        }
    }

    @Override
    public void Register(Handler<Event> handler) {
        if (handler == null) {
            throw new NullPointerException("Handler should not be null");
        }
        List<Handler<Event>> eventHandlers = this.handlers.get(handler.getType());
        if (eventHandlers == null) {
            eventHandlers = new ArrayList<Handler<Event>>();
            this.handlers.put(handler.getType(), eventHandlers);
        }
        eventHandlers.add(handler);
    }

}
