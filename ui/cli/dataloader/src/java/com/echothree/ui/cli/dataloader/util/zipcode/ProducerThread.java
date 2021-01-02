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

package com.echothree.ui.cli.dataloader.util.zipcode;

import com.echothree.util.common.collection.SmartQueue;
import static com.google.common.base.Charsets.UTF_8;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ProducerThread
        extends Thread {
    
    private final SmartQueue queue;
    private final String filename;
    
    /** Creates a new instance of ProducerThread */
    public ProducerThread(SmartQueue queue, String filename) {
        this.queue=queue;
        this.filename=filename;
    }
    
    @Override
    public void run() {
        try {
            HashMap zipCodeDataByState = new HashMap(75);
            
            try (BufferedReader in = Files.newBufferedReader(Paths.get(filename), UTF_8)) {
                for(String zipCodeLine = in.readLine(); zipCodeLine != null; zipCodeLine = in.readLine()) {
                    ZipCodeData zipCodeData = new ZipCodeData(zipCodeLine);
                    
                    if(zipCodeData.getCopyrightDetailCode().equals("D")) {
                        if(zipCodeData.getCityStateMailingNameIndicator().equals("Y")) {
                            String stateAbbreviation = zipCodeData.getStateAbbreviation();
                            ArrayList stateZipCodeList = (ArrayList)zipCodeDataByState.get(stateAbbreviation);
                            
                            if(stateZipCodeList == null) {
                                stateZipCodeList = new ArrayList();
                                zipCodeDataByState.put(stateAbbreviation, stateZipCodeList);
                            }
                            
                            stateZipCodeList.add(zipCodeData);
                        }
                    }
                }
            }
            
            Collection zipCodeArrays = zipCodeDataByState.values();
            for(Iterator iter = zipCodeArrays.iterator(); iter.hasNext();) {
                queue.put(iter.next());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        queue.end();
    }
    
}

