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

package com.echothree.util.server.persistence;

import com.echothree.model.control.core.server.CoreControl;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.Workflow_BASE_ENCRYPTION_KEY_STATUS;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.BaseEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.exception.PersistenceEncryptionException;
import com.echothree.util.common.persistence.EncryptionConstants;
import com.echothree.util.common.string.MD5Utils;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BaseKeys;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.infinispan.Cache;

public class EncryptionUtils {

    private static final String externalPrefix = "#EXTERNAL#";
    private static final String fqnBaseKeys = "/com/echothree/security/base";
    private static final String cacheBaseKey1 = "BaseKey1";
    private static final String cacheBaseKey2 = "BaseKey2";
    
    protected Log log = LogFactory.getLog(EncryptionUtils.class);

    private EncryptionUtils() {
        super();
    }

    private static class EncryptionUtilsHolder {
        static EncryptionUtils instance = new EncryptionUtils();
    }

    public static EncryptionUtils getInstance() {
        return EncryptionUtilsHolder.instance;
    }

    public Random getRandom() {
        Random random;

        try {
            random = SecureRandom.getInstance(EncryptionConstants.randomAlgorithm);
        } catch (NoSuchAlgorithmException nsae) {
            log.warn("SecureRandom was unable to find an instance of " + EncryptionConstants.randomAlgorithm + ", falling back to Random");
            random = new Random();
        }

        return random;
    }

    private byte[] generateInitializationVector() {
        byte[] bytes = new byte[16];

        getRandom().nextBytes(bytes);

        return bytes;
    }

    private BaseKey generateBaseKey(final String cacheBaseKeyName) {
        SecretKey secretKey;

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(EncryptionConstants.algorithm);

            keyGenerator.init(EncryptionConstants.keysize);

            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException nsae) {
            throw new PersistenceEncryptionException(nsae);
        }

        Cache<String, Object> cache = ThreadCaches.currentCaches().getSecurityCache();
        byte[] iv = generateInitializationVector();

        BaseKey baseKey = new BaseKey(secretKey, iv);
        cache.put(fqnBaseKeys + "/" + cacheBaseKeyName, baseKey);

        return baseKey;
    }

    // From: http://forum.java.sun.com/thread.jspa?threadID=471971&messageID=2182261
    private byte[] xorArrays(byte[] a, byte[] b) throws IllegalArgumentException {
        if(b.length != a.length) {
            throw new IllegalArgumentException("length of byte[] b must be == length of byte[] a");
        }

        byte[] c = new byte[a.length];
        for(int i = 0; i < a.length; i++) {
            c[i] = (byte)(a[i] ^ b[i]);
        }

        return c;
    }

    private BaseKey xorBaseKeys(BaseKey baseKey1, BaseKey baseKey2) {
        SecretKey key3 = new SecretKeySpec(xorArrays(baseKey1.getKey().getEncoded(), baseKey2.getKey().getEncoded()), "AES");
        byte[] iv3 = xorArrays(baseKey1.getIv(), baseKey2.getIv());

        return new BaseKey(key3, iv3);
    }

    private BaseKeys createBaseKeys(final ExecutionErrorAccumulator eea, final PartyPK createdBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        BaseKey baseKey1 = generateBaseKey(cacheBaseKey1);
        BaseKey baseKey2 = generateBaseKey(cacheBaseKey2);
        BaseKey baseKey3 = xorBaseKeys(baseKey1, baseKey2);
        BaseEncryptionKey baseEncryptionKey = coreControl.createBaseEncryptionKey(eea, baseKey1, baseKey2, createdBy);

        return baseEncryptionKey == null? null: new BaseKeys(baseKey1, baseKey2, baseKey3, baseEncryptionKey.getBaseEncryptionKeyName());
    }
    
    public BaseKeys generateBaseKeys(final ExecutionErrorAccumulator eea, final PartyPK createdBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        BaseKeys baseKeys = null;

        if(coreControl.countEntityEncryptionKeys() == 0) {
            baseKeys = createBaseKeys(eea, createdBy);
            log.info(baseKeys == null? "Base Encryption Keys Not Generated": "Base Encryption Keys Generated");
        } else {
            log.error("Base Encryption Keys Already Exist");
        }

        return baseKeys;
    }

    private void validateBaseKeys(final BaseKeys baseKeys, final int minimumKeysRequired) {
        int baseKeyCount = baseKeys.getBaseKeyCount();

        if(baseKeyCount < minimumKeysRequired) {
            throw new PersistenceEncryptionException(baseKeyCount == 0 ? "Base Encryption Keys Missing" : "Base Encryption Keys Incomplete");
        } else {
            BaseKey baseKey1 = baseKeys.getBaseKey1();
            BaseKey baseKey2 = baseKeys.getBaseKey2();
            BaseKey baseKey3 = baseKeys.getBaseKey3();

            if(baseKeyCount == 2 && (baseKey1 == null || baseKey2 == null)) {
                // Recovery using third key is needed.
                if(baseKey1 == null) {
                    // Recover baseKey1
                    baseKey1 = xorBaseKeys(baseKey2, baseKey3);
                    baseKeys.setBaseKey1(baseKey1);
                } else {
                    // Recover baseKey2
                    baseKey2 = xorBaseKeys(baseKey1, baseKey3);
                    baseKeys.setBaseKey2(baseKey2);
                }
            } else if(baseKeyCount == 3) {
                // Verify third key is correct based on first two.
                if(!baseKey3.equals(xorBaseKeys(baseKey1, baseKey2))) {
                    throw new PersistenceEncryptionException("Third key is not correct");
                }
            }

            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            String sha1Hash = Sha1Utils.getInstance().encode(baseKey1, baseKey2);
            BaseEncryptionKey baseEncryptionKey = coreControl.getBaseEncryptionKeyBySha1Hash(sha1Hash);

            if(baseEncryptionKey != null) {
                var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(baseEncryptionKey.getPrimaryKey());
                WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS, entityInstance);

                if(!workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName().equals(WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE)) {
                    throw new PersistenceEncryptionException("Supplied Base Encryption Keys Not Active");
                }
            } else {
                throw new PersistenceEncryptionException("Supplied Base Encryption Keys Not Valid");
            }
        }
    }

    public void loadBaseKeys(final BaseKeys baseKeys) {
        Cache<String, Object> cache = ThreadCaches.currentCaches().getSecurityCache();

        validateBaseKeys(baseKeys, 2);

        cache.put(fqnBaseKeys + "/" + cacheBaseKey1, baseKeys.getBaseKey1());
        cache.put(fqnBaseKeys + "/" + cacheBaseKey2, baseKeys.getBaseKey2());
        log.info("Base Encryption Keys Loaded");
    }

    public BaseKeys changeBaseKeys(final ExecutionErrorAccumulator eea, final BaseKeys oldBaseKeys,
            final PartyPK changedBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        validateBaseKeys(oldBaseKeys, 3);
        BaseKeys newBaseKeys = createBaseKeys(eea, changedBy);

        Cipher oldCipher1 = getInitializedCipher(oldBaseKeys.getKey1(), oldBaseKeys.getIv1(), Cipher.DECRYPT_MODE);
        Cipher oldCipher2 = getInitializedCipher(oldBaseKeys.getKey2(), oldBaseKeys.getIv2(), Cipher.DECRYPT_MODE);
        Cipher newCipher1 = getInitializedCipher(newBaseKeys.getKey1(), newBaseKeys.getIv1(), Cipher.ENCRYPT_MODE);
        Cipher newCipher2 = getInitializedCipher(newBaseKeys.getKey2(), newBaseKeys.getIv2(), Cipher.ENCRYPT_MODE);

        List<EntityEncryptionKey> entityEncryptionKeys = coreControl.getEntityEncryptionKeysForUpdate();

        for(EntityEncryptionKey entityEncryptionKey: entityEncryptionKeys) {
            BaseEncoding baseEncoding = BaseEncoding.base64();
            byte[] encryptedKey = baseEncoding.decode(entityEncryptionKey.getSecretKey());
            byte[] encryptedIv = baseEncoding.decode(entityEncryptionKey.getInitializationVector());

            try {
                byte[] decryptedKey = oldCipher1.doFinal(oldCipher2.doFinal(encryptedKey));
                byte[] decryptedIv = oldCipher1.doFinal(oldCipher2.doFinal(encryptedIv));

                encryptedKey = newCipher2.doFinal(newCipher1.doFinal(decryptedKey));
                encryptedIv = newCipher2.doFinal(newCipher1.doFinal(decryptedIv));
            } catch (IllegalStateException ise) {
                throw new PersistenceEncryptionException(ise);
            } catch (IllegalBlockSizeException ibse) {
                throw new PersistenceEncryptionException(ibse);
            } catch (BadPaddingException bpe) {
                throw new PersistenceEncryptionException(bpe);
            }

            entityEncryptionKey.setSecretKey(baseEncoding.encode(encryptedKey));
            entityEncryptionKey.setInitializationVector(baseEncoding.encode(encryptedIv));
        }

        log.info("Base Encryption Keys Changed");

        return newBaseKeys;
    }

    private BaseKeys getBaseKeys() {
        Cache<String, Object> cache = ThreadCaches.currentCaches().getSecurityCache();
        BaseKey baseKey1 = (BaseKey)cache.get(fqnBaseKeys + "/" + cacheBaseKey1);
        BaseKey baseKey2 = (BaseKey)cache.get(fqnBaseKeys + "/" + cacheBaseKey2);

        int cacheCount = (baseKey1 == null ? 0 : 1) + (baseKey2 == null ? 0 : 1);

        if(cacheCount != 2) {
            throw new PersistenceEncryptionException(cacheCount == 0 ? "Base Encryption Keys Missing" : "Base Encryption Keys Incomplete");
        }

        BaseKeys baseKeys = new BaseKeys(baseKey1, baseKey2);

        return baseKeys;
    }

    public String encrypt(final String entityTypeName, final String entityColumnName, final String value) {
        return encrypt(entityTypeName, entityColumnName, Boolean.FALSE, value);
    }

    public String encrypt(final String entityTypeName, final String entityColumnName, final Boolean isExternal, final String value) {
        String encryptedValue = null;

        if(value != null) {
            try {
                encryptedValue = BaseEncoding.base64().encode(getCipher(entityTypeName, entityColumnName, isExternal, Cipher.ENCRYPT_MODE).doFinal(value.getBytes(Charsets.UTF_8)));
            } catch (IllegalStateException ise) {
                throw new PersistenceEncryptionException(ise);
            } catch (IllegalBlockSizeException ibse) {
                throw new PersistenceEncryptionException(ibse);
            } catch (BadPaddingException bpe) {
                throw new PersistenceEncryptionException(bpe);
            }
        }

        return encryptedValue;
    }

    public String decrypt(final String entityTypeName, final String entityColumnName, final String value) {
        return decrypt(entityTypeName, entityColumnName, Boolean.FALSE, value);
    }

    public String decrypt(final String entityTypeName, final String entityColumnName, Boolean isExternal, final String value) {
        String decryptedValue = null;

        if(value != null) {
            try {
                decryptedValue = new String(getCipher(entityTypeName, entityColumnName, isExternal, Cipher.DECRYPT_MODE).doFinal(BaseEncoding.base64().decode(value)), Charsets.UTF_8);
            } catch (IllegalStateException ise) {
                throw new PersistenceEncryptionException(ise);
            } catch (IllegalBlockSizeException ibse) {
                throw new PersistenceEncryptionException(ibse);
            } catch (BadPaddingException bpe) {
                throw new PersistenceEncryptionException(bpe);
            }
        }

        return decryptedValue;
    }

    private Cipher getInitializedCipher(final SecretKey secretKey, final byte[] iv, final int cipherMode) {
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance(EncryptionConstants.transformation);
        } catch (NoSuchAlgorithmException nsae) {
            throw new PersistenceEncryptionException(nsae);
        } catch (NoSuchPaddingException nspe) {
            throw new PersistenceEncryptionException(nspe);
        }

        // Setup cipher
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            cipher.init(cipherMode, secretKey, ivParameterSpec);
        } catch (InvalidKeyException ike) {
            throw new PersistenceEncryptionException(ike);
        } catch (InvalidAlgorithmParameterException iape) {
            throw new PersistenceEncryptionException(iape);
        }

        return cipher;
    }

    private byte[] encryptDataUsingBaseKeys(final BaseKeys baseKeys, final byte[] decryptedData) {
        Cipher cipher1 = getInitializedCipher(baseKeys.getKey1(), baseKeys.getIv1(), Cipher.ENCRYPT_MODE);
        Cipher cipher2 = getInitializedCipher(baseKeys.getKey2(), baseKeys.getIv2(), Cipher.ENCRYPT_MODE);
        byte[] encryptedData = null;

        try {
            encryptedData = cipher2.doFinal(cipher1.doFinal(decryptedData));
        } catch (IllegalStateException ise) {
            throw new PersistenceEncryptionException(ise);
        } catch (IllegalBlockSizeException ibse) {
            throw new PersistenceEncryptionException(ibse);
        } catch (BadPaddingException bpe) {
            throw new PersistenceEncryptionException(bpe);
        }

        return encryptedData;
    }

    private byte[] decryptDataUsingBaseKeys(final BaseKeys baseKeys, final byte[] encryptedData) {
        Cipher cipher1 = getInitializedCipher(baseKeys.getKey1(), baseKeys.getIv1(), Cipher.DECRYPT_MODE);
        Cipher cipher2 = getInitializedCipher(baseKeys.getKey2(), baseKeys.getIv2(), Cipher.DECRYPT_MODE);
        byte[] decryptedData = null;

        try {
            decryptedData = cipher1.doFinal(cipher2.doFinal(encryptedData));
        } catch (IllegalStateException ise) {
            throw new PersistenceEncryptionException(ise);
        } catch (IllegalBlockSizeException ibse) {
            throw new PersistenceEncryptionException(ibse);
        } catch (BadPaddingException bpe) {
            throw new PersistenceEncryptionException(bpe);
        }

        return decryptedData;
    }

    private Cipher getCipher(final String entityTypeName, final String entityColumnName, final Boolean isExternal, final int cipherMode) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        String entityEncryptionKeyName = MD5Utils.getInstance().encode(new StringBuilder(entityTypeName).append('.').append(isExternal? externalPrefix: entityColumnName).toString());
        SecretKey secretKey;
        byte[] iv;

        BaseEncoding baseEncoding = BaseEncoding.base64();
        EntityEncryptionKey entityEncryptionKey = coreControl.getEntityEncryptionKeyByName(entityEncryptionKeyName);
        BaseKeys baseKeys = getBaseKeys();

        if(entityEncryptionKey == null) {
            // Key has not yet been generated for this EntityType
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(EncryptionConstants.algorithm);

                keyGenerator.init(EncryptionConstants.keysize);

                secretKey = keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException nsae) {
                throw new PersistenceEncryptionException(nsae);
            }

            byte[] key = secretKey.getEncoded();
            iv = generateInitializationVector();

            byte[] encryptedKey = encryptDataUsingBaseKeys(baseKeys, key);
            byte[] encryptedIv = encryptDataUsingBaseKeys(baseKeys, iv);

            coreControl.createEntityEncryptionKey(entityEncryptionKeyName, isExternal,
                    baseEncoding.encode(encryptedKey), baseEncoding.encode(encryptedIv));
        } else {
            // Key has been generated for this EntityType
            byte[] encryptedKey = baseEncoding.decode(entityEncryptionKey.getSecretKey());
            byte[] encryptedIv = baseEncoding.decode(entityEncryptionKey.getInitializationVector());

            byte[] key = decryptDataUsingBaseKeys(baseKeys, encryptedKey);
            iv = decryptDataUsingBaseKeys(baseKeys, encryptedIv);

            secretKey = new SecretKeySpec(key, EncryptionConstants.algorithm);
        }

        return getInitializedCipher(secretKey, iv, cipherMode);
    }

}