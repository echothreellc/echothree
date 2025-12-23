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

package com.echothree.control.user.core.client.helper;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.result.ChangeBaseKeysResult;
import com.echothree.control.user.core.common.result.GenerateBaseKeysResult;
import com.echothree.control.user.core.common.result.GetBaseEncryptionKeyResult;
import com.echothree.model.control.core.common.transfer.BaseEncryptionKeyTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BaseKeys;
import com.echothree.util.common.persistence.EncryptionConstants;
import com.google.common.io.BaseEncoding;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseKeysHelper {
    
    private BaseKeysHelper() {
        super();
    }
    
    private static class BaseKeysHelperHolder {
        static BaseKeysHelper instance = new BaseKeysHelper();
    }
    
    public static BaseKeysHelper getInstance() {
        return BaseKeysHelperHolder.instance;
    }

    private Log log = LogFactory.getLog(this.getClass());

    private void storeBaseKeyToProperties(String baseEncryptionKeyName, BaseKey baseKey, String which)
            throws IOException {
        var baseEncoding = BaseEncoding.base64();
        var keyProperties = new Properties();

        keyProperties.setProperty("key", baseEncoding.encode(baseKey.getKey().getEncoded()));
        keyProperties.setProperty("iv", baseEncoding.encode(baseKey.getIv()));
        keyProperties.setProperty("which", which);

        var hostName = InetAddress.getLocalHost().getHostName();
        var propertiesPath = new StringBuilder(System.getProperty("user.home"))
                    .append(File.separator).append("keys/media").append(which)
                    .append(File.separator).append(hostName)
                    .append(File.separator).append(baseEncryptionKeyName);

            if(new File(propertiesPath.toString()).mkdirs()) {
                var fileName = propertiesPath.append(File.separator).append("key.xml").toString();

                keyProperties.storeToXML(new FileOutputStream(propertiesPath.toString()), null);
                log.info("Key #" + which + " stored to " + fileName);
            } else {
                // Save key into current user's home directory, and let them sort it out.
                log.error("Storage of key #" + which + " failed!");
                keyProperties.storeToXML(new FileOutputStream(System.getProperty("user.home") + File.separator + "key" + which + ".xml"), null);
            }
    }

    private void writeBaseKeysToFiles(BaseKeys baseKeys)
            throws IOException {
        var baseEncryptionKeyName = baseKeys.getBaseEncryptionKeyName();

        storeBaseKeyToProperties(baseEncryptionKeyName, baseKeys.getBaseKey1(), "1");
        storeBaseKeyToProperties(baseEncryptionKeyName, baseKeys.getBaseKey2(), "2");
        storeBaseKeyToProperties(baseEncryptionKeyName, baseKeys.getBaseKey3(), "3");
    }

    public void handleGenerateBaseKeys(UserVisitPK userVisitPK)
            throws NamingException, IOException {
        var commandResult = CoreUtil.getHome().generateBaseKeys(userVisitPK);
        var executionResult = commandResult.getExecutionResult();
        var result = (GenerateBaseKeysResult)executionResult.getResult();
        var baseKeys = result.getBaseKeys();

        if(baseKeys != null) {
            writeBaseKeysToFiles(baseKeys);
        }
    }

    private String getActiveBaseEncryptionKeyName(UserVisitPK userVisitPK)
            throws NamingException {
        BaseEncryptionKeyTransfer baseEncryptionKey;
        var commandForm = CoreFormFactory.getGetBaseEncryptionKeyForm();
        var commandResult = CoreUtil.getHome().getBaseEncryptionKey(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetBaseEncryptionKeyResult)executionResult.getResult();

        baseEncryptionKey = result.getBaseEncryptionKey();

        return baseEncryptionKey == null ? null : baseEncryptionKey.getBaseEncryptionKeyName();
    }

    private void loadBaseKeyFromProperties(Map<String, BaseKey> baseKeyMap, String baseEncryptionKeyName, String whichMedia)
            throws IOException {
        var baseEncoding = BaseEncoding.base64();
        var keyProperties = new Properties();
        var hostName = InetAddress.getLocalHost().getHostName();
        var fileName = System.getProperty("user.home") +
                File.separator + "keys/media" + whichMedia +
                File.separator + hostName +
                File.separator + baseEncryptionKeyName +
                File.separator + "key.xml";

        keyProperties.loadFromXML(new FileInputStream(fileName));

        var secretKey1 = new SecretKeySpec(baseEncoding.decode(keyProperties.getProperty("key")), EncryptionConstants.algorithm);
        var iv1 = baseEncoding.decode(keyProperties.getProperty("iv"));

        var baseKey = new BaseKey(secretKey1, iv1);
        var which = keyProperties.getProperty("which");
        baseKeyMap.put(which, baseKey);
        log.info("Key #" + which + " restored from " + fileName);
    }

    private BaseKeys getBaseKeysFromFiles(UserVisitPK userVisitPK)
            throws NamingException, IOException {
        var baseKeyMap = new HashMap<String, BaseKey>();
        var baseEncryptionKeyName = getActiveBaseEncryptionKeyName(userVisitPK);

        loadBaseKeyFromProperties(baseKeyMap, baseEncryptionKeyName, "1");
        loadBaseKeyFromProperties(baseKeyMap, baseEncryptionKeyName, "2");
        loadBaseKeyFromProperties(baseKeyMap, baseEncryptionKeyName, "3");

        var baseKey1 = baseKeyMap.get("1");
        var baseKey2 = baseKeyMap.get("2");
        var baseKey3 = baseKeyMap.get("3");

        return new BaseKeys(baseKey1, baseKey2, baseKey3);
    }

    public void handleLoadBaseKeys(UserVisitPK userVisitPK)
            throws NamingException, IOException {
        var baseKeys = getBaseKeysFromFiles(userVisitPK);

        if(baseKeys.getBaseKeyCount() > 1) {
            log.info("Two or more Base Encryption Keys found, loading keys.");
            var commandForm = CoreFormFactory.getLoadBaseKeysForm();

            commandForm.setBaseKeys(baseKeys);

            CoreUtil.getHome().loadBaseKeys(userVisitPK, commandForm);
        } else {
            log.error("A minimum of two Base Encryption Keys are required, not loading key.");
        }
    }

    public void handleChangeBaseKeys(UserVisitPK userVisitPK)
            throws NamingException, IOException {
        var baseKeys = getBaseKeysFromFiles(userVisitPK);

        if(baseKeys.getBaseKeyCount() == 3) {
            log.info("Three Base Encryption Keys found, changing keys.");
            var commandForm = CoreFormFactory.getChangeBaseKeysForm();

            commandForm.setBaseKeys(baseKeys);

            var commandResult = CoreUtil.getHome().changeBaseKeys(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (ChangeBaseKeysResult)executionResult.getResult();

            writeBaseKeysToFiles(result.getBaseKeys());
        } else {
            log.error("Three Base Encryption Keys are required, not changing keys.");
        }
    }

}
