// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import static com.google.common.base.Charsets.UTF_8;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImportProducerThread
        extends Thread {

    private final SmartQueue<String> queue;

    /** Creates a new instance of ImportProducerThread */
    public ImportProducerThread(SmartQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        InputStreamReader isr = new InputStreamReader(System.in, UTF_8);
        BufferedReader br = new BufferedReader(isr);

        try {
            for(String logLine = br.readLine() ; logLine != null ; logLine = br.readLine()) {
                queue.put(logLine);
            }
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }

        queue.end();
    }

}
