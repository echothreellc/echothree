// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import static com.google.common.base.Charsets.UTF_8;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Export {
    
    private static final String accessLog = "access_log.";
    private static final String eol = System.getProperty("line.separator");
    
    /** Creates a new instance of Export */
    public Export() {
    }
    
    public void execute()
            throws SQLException, IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String fileSuffix = sdf.format(date);
        Connection conn = null;
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtSelectVirtualHosts = null;
        PreparedStatement pstmtSelectLogEntries = null;
        PreparedStatement pstmtDeleteLogEntries = null;
        ResultSet rsVhosts = null;
        ResultSet rsLogEntries = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apache", "apache", "aehasooxo");
            
            pstmtUpdate = conn.prepareStatement("UPDATE apachelog SET l_needsexport = 0 WHERE l_needsresolution = 0 ORDER BY l_time LIMIT 75000");
            pstmtSelectVirtualHosts = conn.prepareStatement("SELECT v_vhost, v_logdirectory FROM vhosts ORDER BY v_vhost");
            pstmtSelectLogEntries = conn.prepareStatement("SELECT l_logentry FROM apachelog WHERE l_needsresolution = 0 AND l_needsexport = 0 AND l_vhost = ? ORDER BY l_time");
            pstmtDeleteLogEntries = conn.prepareStatement("DELETE FROM apachelog WHERE l_needsexport = 0");
            
            pstmtUpdate.executeUpdate();
            
            rsVhosts = pstmtSelectVirtualHosts.executeQuery();
            while(rsVhosts.next()) {
                String v_vhost = rsVhosts.getString(1);
                String v_logdirectory = rsVhosts.getString(2);
                String logFileName = new StringBuilder(v_logdirectory).append(File.separator).append(accessLog).append(fileSuffix).toString();
                
                try (PrintWriter log = new PrintWriter(Files.newBufferedWriter(Paths.get(logFileName), UTF_8))) {
                    pstmtSelectLogEntries.setString(1, v_vhost);
                    rsLogEntries = pstmtSelectLogEntries.executeQuery();
                    
                    while(rsLogEntries.next()) {
                        log.println(rsLogEntries.getString(1));
                    }
                    
                    rsLogEntries.close();
                    rsLogEntries = null;
                }
            }
            
            pstmtDeleteLogEntries.executeUpdate();
        } finally {
            if(rsVhosts != null) {
                try {
                    rsVhosts.close();
                } catch (SQLException se) {
                    // Nothing
                }
                rsVhosts = null;
            }
            
            if(rsLogEntries != null) {
                try {
                    rsLogEntries.close();
                } catch (SQLException se) {
                    // Nothing
                }
                rsLogEntries = null;
            }
            
            if(pstmtUpdate != null) {
                try {
                    pstmtUpdate.close();
                } catch (SQLException se) {
                    // Nothing
                }
                pstmtUpdate = null;
            }
            
            if(pstmtSelectVirtualHosts != null) {
                try {
                    pstmtSelectVirtualHosts.close();
                } catch (SQLException se) {
                    // Nothing
                }
                pstmtSelectVirtualHosts = null;
            }
            
            if(pstmtSelectLogEntries != null) {
                try {
                    pstmtSelectLogEntries.close();
                } catch (SQLException se) {
                    // Nothing
                }
                pstmtSelectLogEntries = null;
            }
            
            if(pstmtDeleteLogEntries != null) {
                try {
                    pstmtDeleteLogEntries.close();
                } catch (SQLException se) {
                    // Nothing
                }
                pstmtDeleteLogEntries = null;
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
