// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.util.server.persistence;

import com.echothree.util.common.exception.PersistenceDatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityIdGenerator {
    
    static private final String selectQuery = "SELECT eid_lastentityid FROM entityids WHERE eid_componentvendorname = ? AND eid_entitytypename = ?";
    static private final String updateQuery = "UPDATE entityids SET eid_lastentityid = ? WHERE eid_componentvendorname = ? AND eid_entitytypename = ? AND eid_lastentityid = ?";
    static private final String insertQuery = "INSERT INTO entityids (eid_componentvendorname, eid_entitytypename, eid_lastentityid) VALUES (?, ?, ?)";
    
    static private final int defaultChunkSize = 100;
    static private final int retryCount = 5;
    
    private String componentVendorName;
    private String entityTypeName;
    private int chunkSize;
    private long guardedValue = 0;
    private long currentValue = 0;
    
    private void init(String componentVendorName, String entityTypeName, int chunkSize) {
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.chunkSize = chunkSize;
    }
    
    /** Creates a new instance of EntityIdGenerator */
    public EntityIdGenerator(String componentVendorName, String entityTypeName, int chunkSize) {
        init(componentVendorName, entityTypeName, chunkSize);
    }
    
    /** Creates a new instance of EntityIdGenerator */
    public EntityIdGenerator(String componentVendorName, String entityTypeName) {
        init(componentVendorName, entityTypeName, defaultChunkSize);
    }
    
    public Connection getConnection() {
        return DslContextFactory.getInstance().getNTDslContext().parsingConnection();
    }
    
    private boolean getNewChunk()
    throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long currentMax;
        
        try {
            conn = getConnection();
            ps = conn.prepareStatement(selectQuery);
            ps.setString(1, componentVendorName);
            ps.setString(2, entityTypeName);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                currentMax = rs.getLong(1);
                
                if(rs != null) {
                    try{
                        rs.close();
                    } catch (Exception e) {
                    } finally {
                        rs = null;
                    }
                }
                
                if(ps != null) {
                    try{
                        ps.close();
                    } catch (Exception e){
                    } finally {
                        ps = null;
                    }
                }
                
                ps = conn.prepareStatement(updateQuery);
                ps.setLong(1, currentMax + chunkSize);
                ps.setString(2, componentVendorName);
                ps.setString(3, entityTypeName);
                ps.setLong(4, currentMax);
                
                if(ps.executeUpdate() != 1)
                    return false;
                else {
                    guardedValue = currentMax + chunkSize;
                    currentValue = currentMax;
                    return true;
                }
            } else {
                if(rs != null) {
                    try{
                        rs.close();
                    } catch (Exception e) {
                    } finally {
                        rs = null;
                    }
                }
                
                if(ps != null) {
                    try{
                        ps.close();
                    } catch (Exception e) {
                    } finally {
                        ps = null;
                    }
                }
                
                ps = conn.prepareStatement(insertQuery);
                ps.setString(1, componentVendorName);
                ps.setString(2, entityTypeName);
                ps.setLong(3, chunkSize);
                if(ps.executeUpdate() != 1) {
                    return false;
                } else {
                    guardedValue = chunkSize;
                    currentValue = 0;
                    return true;
                }
            }
        } finally {
            if(rs != null) {
                try{
                    rs.close();
                } catch (Exception e) {
                } finally {
                    rs = null;
                }
            }
            
            if(ps != null) {
                try{
                    ps.close();
                } catch (Exception e) {
                } finally {
                    ps = null;
                }
            }
            
            if(conn != null) {
                try{
                    conn.close();
                } catch (Exception e) {
                } finally {
                    conn = null;
                }
            }
        }
    }
    
    public synchronized long getNextEntityId() {
        if(currentValue == guardedValue) {
            try {
                for(int iter = 0; iter <= retryCount; iter++) {
                    if(getNewChunk())
                        break;
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }
        
        if(currentValue == guardedValue)
            throw new PersistenceDatabaseException("Could not get next increment value");
        
        return ++currentValue;
    }
    
}
