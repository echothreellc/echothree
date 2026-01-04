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

package com.echothree.ui.cli.dataloader.util.zipcode;

import com.echothree.util.common.collection.SmartQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProducerThread
        extends Thread {
    
    private final SmartQueue<List<ZipCodeData>> queue;

    /** Creates a new instance of ProducerThread */
    public ProducerThread(SmartQueue<List<ZipCodeData>> queue) {
        this.queue=queue;
    }
    
    @Override
    public void run() {
        try {
            var zipCodeDataByState = new HashMap<String, List<ZipCodeData>>(75);

            try (var in = new BufferedReader(new InputStreamReader(ProducerThread.class.getResource("/city-state-product/city-state-product.txt").openStream(), StandardCharsets.UTF_8))) {
                for(var zipCodeLine = in.readLine(); zipCodeLine != null; zipCodeLine = in.readLine()) {
                    var zipCodeData = new ZipCodeData(zipCodeLine);
                    
                    if("D".equals(zipCodeData.getCopyrightDetailCode())) {
                        if("Y".equals(zipCodeData.getCityStateMailingNameIndicator())) {
                            var stateAbbreviation = zipCodeData.getStateAbbreviation();
                            var stateZipCodeList = zipCodeDataByState.computeIfAbsent(stateAbbreviation, k -> new ArrayList<>());

                            stateZipCodeList.add(zipCodeData);
                        }
                    }
                }
            }
            
            var zipCodeLists = zipCodeDataByState.values();
            for(var zipCodeList : zipCodeLists) {
                queue.put(zipCodeList);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        queue.end();
    }
    
}

