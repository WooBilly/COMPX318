/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 // https://docs.oracle.com/javase/tutorial/essential/concurrency/simple.html
 
 // https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
 
public class ThreadSample {
 
    // Display a message, preceded by the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }
 
	// The class implementing the sample thread
    private static class MessageLoop implements Runnable {
        
		// The actual thread code
		public void run() {
            String info[] = { "One", "Two", "Three", "Four" };
            for (int i = 0; i < info.length; i++) {
				try {
                    Thread.sleep(4000);
				} catch (InterruptedException e) {
					threadMessage("I wasn't finished");
					break;
				}
                threadMessage(info[i]);
            }
        }
    }
 
    public static void main(String args[])
        throws InterruptedException {
 
        // delay, in milliseconds before we interrupt MessageLoop
        long patience = 30000;   // 30 seconds
 
		// start the MessageLoop thread
        long startTime = System.currentTimeMillis();
        threadMessage("Starting MessageLoop thread");
        Thread t = new Thread(new MessageLoop());
        t.start();
 
        // loop until MessageLoop thread exits
        threadMessage("Waiting for MessageLoop thread to finish");
        while (t.isAlive()) {
            threadMessage("waiting...");
            t.join(1000);   // wait for 1 second or finish
            if (((System.currentTimeMillis() - startTime) > patience)
                  && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                // Shouldn't be long now
                // -- wait indefinitely
                t.join();
            }
        }
        threadMessage("Finished");
    }
}