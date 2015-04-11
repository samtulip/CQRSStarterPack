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

import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sam Tulip
 */
public class SynchronousCommandBusTest {

    @Test( expected = NullPointerException.class )
    public void testNullHandler() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        bus.Register(null);
    }
    
    @Test
    public void testHandler() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestCommand.class );
        bus.Register( handler );
    }
    
    @Test( expected = NullPointerException.class )
    public void testNullCommand() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        bus.send( null );
    }
    
    @Test( expected = UnsupportedOperationException.class )
    public void testUnSupportedCommand() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        bus.send( new TestCommand() );
    }
    
    @Test
    public void testCommand() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestCommand.class );
        bus.Register( handler );
        Command command = new TestCommand();
        bus.send( command );
        verify( handler ).Handle(command);
    }
    
    @Test
    public void testCommand2() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestCommand.class );
        Handler handler2 = mock(Handler.class);
        when( handler2.getType() ).thenReturn( TestCommand2.class );
        bus.Register( handler );
        bus.Register( handler2 );
        Command command = new TestCommand2();
        bus.send( command );
        verify( handler2 ).Handle(command);
    }
    
    @Test( expected = UnsupportedOperationException.class)
    public void testCommand3() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestCommand.class );
        bus.Register( handler );
        Command command = new TestCommand3();
        bus.send( command );
    }
    
    @Test( expected = IllegalArgumentException.class) 
    public void testRegisterMultipleHandlers() {
        SynchronousCommandBus bus = new SynchronousCommandBus();
        Handler handler = mock(Handler.class);
        when( handler.getType() ).thenReturn( TestCommand.class );
        bus.Register( handler );
        bus.Register( handler );
    }
    
    public static class TestCommand implements Command {
        
    }
    
    public static class TestCommand2 implements Command {
        
    }
    
    public static class TestCommand3 implements Command {
        
    }
}
