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
import com.google.common.base.Charsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportConsumerThread
        extends Thread {
    
    private SmartQueue<String> queue;
    
    /** Creates a new instance of ImportConsumerThread */
    public ImportConsumerThread(SmartQueue queue) {
        this.queue = queue;
    }
    
    int uniqueIdEnd = 0;
    int timeStart;
    int timeEnd;
    int vhostStart;
    int minimumLength;
    
    /** Read the first line logged by the web server, and figure out how long the
     * unique id portion of the line is. This is done because Apache 2.0 uses 24
     * characters, and 1.3 uses 19 (Unix) or 24 (Windows).
     */
    public String firstReadLine() {
        String first = queue.take();
        
        if(first != null) {
            uniqueIdEnd = first.indexOf(' ');

            if(uniqueIdEnd == 19 || uniqueIdEnd == 24) {
                timeStart = uniqueIdEnd + 1;
                timeEnd = timeStart + 14;
                vhostStart = timeEnd + 1;
                minimumLength = vhostStart + 1;
            } else {
                uniqueIdEnd = 0;
            }
        }
        
        return first;
    }

    public String nextReadLine() {
        return uniqueIdEnd == 0 ? firstReadLine() : queue.take();
    }

    public boolean containsSpace(String str) {
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        boolean result = false;

        for(int i = 0; i < bytes.length; i++) {
            if(bytes[i] == ' ') {
                result = true;
            }
        }

        return result;
    }

    @Override
    public void run() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apache", "apache", "aehasooxo");
            pstmt = conn.prepareStatement("INSERT INTO apachelog VALUES (?, ?, 1, 1, ?, ?)");
            
            while(!queue.isEmpty() || !queue.onEnd()) {
                String logLine = nextReadLine();

                if(uniqueIdEnd != 0 && logLine.length() > minimumLength) {
                    String uniqueId = logLine.substring(0, uniqueIdEnd);

                    if(!containsSpace(uniqueId)) {
                        String time = logLine.substring(timeStart, timeEnd);
                        int vhostEnd = logLine.indexOf(' ', vhostStart);

                        if(vhostEnd != -1) {
                            String vhost = logLine.substring(vhostStart, vhostEnd).toLowerCase();

                            pstmt.setString(1, uniqueId);
                            pstmt.setString(2, time);
                            pstmt.setString(3, vhost);
                            pstmt.setString(4, logLine.substring(vhostEnd + 1));
                            
                            try {
                                pstmt.executeUpdate();
                            } catch (SQLException se) {
                                // If the INSERT fails, it's OK, just continue on.
                                se.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    // Nothing
                }
                
                pstmt = null;
            }
            
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException se) {
                    // Nothing
                }
                
                conn = null;
            }
        }
    }
}
