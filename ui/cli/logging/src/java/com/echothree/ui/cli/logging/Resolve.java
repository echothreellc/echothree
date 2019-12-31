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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Resolve {
    
    /** Creates a new instance of Resolve */
    public Resolve() {
    }
    
    public void execute()
        throws SQLException {
        Connection conn = null;
        PreparedStatement pstmtSelect = null;
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtDelete = null;
        ResultSet resultSet = null;
        Map<String, String> ipCache = new HashMap<>();
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apache", "apache", "aehasooxo");
            
            pstmtSelect = conn.prepareStatement("SELECT l_uniqueid, l_logentry FROM apachelog WHERE l_needsresolution = 1 ORDER BY l_time LIMIT 75000");
            pstmtUpdate = conn.prepareStatement("UPDATE apachelog SET l_logentry = ?, l_needsresolution = 0 WHERE l_uniqueid = ?");
            pstmtDelete = conn.prepareStatement("DELETE FROM apachelog WHERE l_uniqueid = ?");
            
            resultSet = pstmtSelect.executeQuery();
            while(resultSet.next()) {
                String l_uniqueid = resultSet.getString(1);
                String l_logentry = resultSet.getString(2);
                int ipAddressEnd = l_logentry.indexOf(' ');

                // If there's no ' ', then the line is likely corrupt. Delete it.
                if(ipAddressEnd == -1) {
                    pstmtDelete.setString(1, l_uniqueid);
                    pstmtDelete.executeUpdate();
                } else {
                    String ipAddress = l_logentry.substring(0, ipAddressEnd);
                    String canonicalHostName = ipCache.get(ipAddress);

                    if(canonicalHostName == null) {
                        InetAddress inetAddress = null;
                        
                        try {
                            inetAddress = InetAddress.getByName(ipAddress);
                        } catch (UnknownHostException uhe) {
                            // Nothing.
                        }

                        canonicalHostName = inetAddress == null? ipAddress: inetAddress.getCanonicalHostName();
                        ipCache.put(ipAddress, canonicalHostName);
                    }

                    l_logentry = new StringBuilder(canonicalHostName).append(l_logentry, ipAddressEnd,  l_logentry.length()).toString();

                    pstmtUpdate.setString(1, l_logentry);
                    pstmtUpdate.setString(2, l_uniqueid);
                    pstmtUpdate.executeUpdate();
                }
            }
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException se) {
                    // Nothing
                }
                
                resultSet = null;
            }
            
            if(pstmtDelete != null) {
                try {
                    pstmtDelete.close();
                } catch (SQLException se) {
                    // Nothing
                }
                
                pstmtDelete = null;
            }

            if(pstmtUpdate != null) {
                try {
                    pstmtUpdate.close();
                } catch (SQLException se) {
                    // Nothing
                }
                
                pstmtUpdate = null;
            }
            
            if(pstmtSelect != null) {
                try {
                    pstmtSelect.close();
                } catch (SQLException se) {
                    // Nothing
                }
                
                pstmtSelect = null;
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
