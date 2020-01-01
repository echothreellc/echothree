// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.cli.logging;

import com.echothree.util.common.collection.SmartQueue;

public class Import {
    
    public void execute()
            throws InterruptedException {
        SmartQueue<String> queue = new SmartQueue(false, false, 524288, null);
        ImportProducerThread prod = new ImportProducerThread(queue);
        ImportConsumerThread cons1 = new ImportConsumerThread(queue);
        ImportConsumerThread cons2 = new ImportConsumerThread(queue);
        ImportConsumerThread cons3 = new ImportConsumerThread(queue);
        ImportConsumerThread cons4 = new ImportConsumerThread(queue);

        prod.setName("Producer");
        cons1.setName("Consumer 1");
        cons2.setName("Consumer 2");
        cons3.setName("Consumer 3");
        cons4.setName("Consumer 4");

        prod.start();
        cons1.start();
        cons2.start();
        cons3.start();
        cons4.start();

        cons1.join();
        cons2.join();
        cons3.join();
        cons4.join();
    }
    
}
