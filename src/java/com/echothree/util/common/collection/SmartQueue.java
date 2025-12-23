// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.common.collection;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SmartQueue<E> {
    
    Log log = LogFactory.getLog(this.getClass());

    private final boolean monitorQueue;
    private final int queueLen;
    private final AtomicLong estimatedTotal;
    private final boolean monitorProgress;

    private final List<E> list;

    private NumberFormat percentFormat;
    private final AtomicLong produced = new AtomicLong(0);
    private final AtomicLong consumed = new AtomicLong(0);

    private boolean eof = false;

    /** Creates a new instance of SmartQueue */
    public SmartQueue(final boolean monitorQueue, final boolean monitorProgress, final int queueLen, final Long estimatedTotal) {
        this.monitorQueue = monitorQueue;
        this.queueLen = queueLen;
        this.estimatedTotal = estimatedTotal == null ? null : new AtomicLong(estimatedTotal);
        this.monitorProgress = monitorProgress;

        list = Collections.synchronizedList(new ArrayList<E>(queueLen));

        if(estimatedTotal != null && estimatedTotal != 0 && monitorProgress) {
            percentFormat = NumberFormat.getPercentInstance();
        }
    }

    public synchronized void put(E data) {
        while(list.size() >= queueLen) {
            try {
                if(monitorQueue) {
                    log.info("Waiting to put data");
                }

                wait();
            } catch(InterruptedException ignored) {
            }
        }

        list.add(data);

        if(estimatedTotal != null) {
            var producedTotal = produced.incrementAndGet();

            if(monitorProgress) {
                if(producedTotal > estimatedTotal.get()) {
                    estimatedTotal.set(producedTotal);
                }

                var myEstimatedTotal = estimatedTotal.get();
                if(producedTotal % 20 == 0 || producedTotal == myEstimatedTotal) {
                    log.info("Produced progress: " + producedTotal + " of " + myEstimatedTotal + " completed (" + percentFormat.format(producedTotal * 1.0 / myEstimatedTotal) + ").");
                }
            }
        }

        notifyAll();
    }
    
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }
    
    public synchronized E take() {
        E obj = null;

        while(list.size() <= 0 && !eof) {
            try {
                if(monitorQueue) {
                    log.info("Waiting to consume data");
                }

                wait();
            } catch(InterruptedException ignored) {
            }
        }

        if(list.size() > 0) {
            try {
                obj = list.remove(0);

                if(estimatedTotal != null) {
                    var consumedTotal = consumed.incrementAndGet();

                    if(monitorProgress) {
                        if(consumedTotal > estimatedTotal.get()) {
                            estimatedTotal.set(consumedTotal);
                        }

                        var myEstimatedTotal = estimatedTotal.get();
                        if(consumedTotal % 20 == 0 || consumedTotal == myEstimatedTotal) {
                            log.info("Consumed progress: " + consumedTotal + " of " + myEstimatedTotal + " completed (" + percentFormat.format(consumedTotal * 1.0 / myEstimatedTotal) + ").");
                        }
                    }
                }
            } catch(IndexOutOfBoundsException ignored) {
                // obj stays null.
            }
        } else {
            if(monitorQueue) {
                log.info("Woke up because end of document");
            }
        }

        notifyAll();

        return obj;
    }

    public synchronized void end() {
        eof = true;
        notifyAll();
    }

    public synchronized boolean onEnd() {
        return eof;
    }
    
}
