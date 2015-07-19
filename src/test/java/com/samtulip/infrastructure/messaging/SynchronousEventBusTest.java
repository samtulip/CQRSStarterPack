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

import java.util.Date;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sam Tulip
 */
public class SynchronousEventBusTest {

    @Test( expected = NullPointerException.class )
    public void testRegisterNullHandler() {
        SynchronousEventBus bus = new SynchronousEventBus();
        bus.register(null);
    }
    
    @Test
    public void testRegisterHandler() {
        SynchronousEventBus bus = new SynchronousEventBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestEvent.class );
        bus.register( handler );
    }
    
    @Test( expected = NullPointerException.class)
    public void testPublishNullEvent() {
        SynchronousEventBus bus = new SynchronousEventBus();
        bus.publish( null );
    }
    
    @Test
    public void testPublishUnhandledEvent() {
        SynchronousEventBus bus = new SynchronousEventBus();
        Event event = new TestEvent();
        //nothing happens. We pass
        bus.publish(event);
    }
    
    @Test
    public void testPublishEvent() {
        SynchronousEventBus bus = new SynchronousEventBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestEvent.class );
        bus.register( handler );
        Event event = new TestEvent();
        bus.publish(event);
        verify(handler).handle(event);
    }
    
    @Test
    public void testPublishEventWithMultipleHandlers() {
        SynchronousEventBus bus = new SynchronousEventBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestEvent.class );
        bus.register( handler );
        Handler handler2 = mock(Handler.class);
        when( handler2.getType() ).thenReturn( TestEvent.class );
        bus.register( handler2 );
        Event event = new TestEvent();
        bus.publish(event);
        verify(handler).handle(event);
        verify(handler2).handle(event);
    }
    
    @Test
    public void testPublishEventWithHandlersOfDifferentTypes() {
        SynchronousEventBus bus = new SynchronousEventBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestEvent.class );
        bus.register( handler );
        Handler handler2 = mock(Handler.class);
        when( handler2.getType() ).thenReturn( TestEvent2.class );
        bus.register( handler2 );
        Event event = new TestEvent();
        bus.publish(event);
        verify(handler).handle(event);
        verify(handler2, never()).handle(event);
    }
    
    public static class TestEvent implements Event {

        @Override
        public Date OccurredAt() {
            return new Date();
        }
        
    }
    
    public static class TestEvent2 extends TestEvent {
        
    }

}