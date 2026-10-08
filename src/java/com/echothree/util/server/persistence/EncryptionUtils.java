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

package com.echothree.util.server.persistence;

import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.Workflow_BASE_ENCRYPTION_KEY_STATUS;
import com.echothree.model.control.core.server.control.EncryptionKeyControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.exception.PersistenceEncryptionException;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BaseKeys;
import com.echothree.util.common.persistence.EncryptionConstants;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

public class EncryptionUtils {

    public enum EntityEncryptionKeyTypes {
        INTERNAL,
        EXTERNAL,
        HMAC_SHA256
    }

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
        var bytes = new byte[16];

        getRandom().nextBytes(bytes);

        return bytes;
    }

    public String generateRowInitializationVector() {
        var iv = generateInitializationVector();
        var baseEncoding = BaseEncoding.base64();

        return baseEncoding.encode(iv);
    }

    private BaseKey generateBaseKey(final String cacheBaseKeyName) {
        SecretKey secretKey;

        try {
            var keyGenerator = KeyGenerator.getInstance(EncryptionConstants.algorithm);

            keyGenerator.init(EncryptionConstants.keysize);

            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException nsae) {
            throw new PersistenceEncryptionException(nsae);
        }

        var cache = ThreadCaches.currentCaches().getSecurityCache();
        var iv = generateInitializationVector();

        var baseKey = new BaseKey(secretKey, iv);
        cache.put(fqnBaseKeys + "/" + cacheBaseKeyName, baseKey);

        return baseKey;
    }

    // From: http://forum.java.sun.com/thread.jspa?threadID=471971&messageID=2182261
    private byte[] xorArrays(byte[] a, byte[] b) throws IllegalArgumentException {
        if(b.length != a.length) {
            throw new IllegalArgumentException("length of byte[] b must be == length of byte[] a");
        }

        var c = new byte[a.length];
        for(var i = 0; i < a.length; i++) {
            c[i] = (byte)(a[i] ^ b[i]);
        }

        return c;
    }

    private BaseKey xorBaseKeys(BaseKey baseKey1, BaseKey baseKey2) {
        SecretKey key3 = new SecretKeySpec(xorArrays(baseKey1.getKey().getEncoded(), baseKey2.getKey().getEncoded()), "AES");
        var iv3 = xorArrays(baseKey1.getIv(), baseKey2.getIv());

        return new BaseKey(key3, iv3);
    }

    private BaseKeys createBaseKeys(final ExecutionErrorAccumulator eea, final PartyPK createdBy) {
        var encryptionKeyControl = Session.getModelController(EncryptionKeyControl.class);
        var baseKey1 = generateBaseKey(cacheBaseKey1);
        var baseKey2 = generateBaseKey(cacheBaseKey2);
        var baseKey3 = xorBaseKeys(baseKey1, baseKey2);
        var baseEncryptionKey = encryptionKeyControl.createBaseEncryptionKey(eea, baseKey1, baseKey2, createdBy);

        return baseEncryptionKey == null? null: new BaseKeys(baseKey1, baseKey2, baseKey3, baseEncryptionKey.getBaseEncryptionKeyName());
    }
    
    public BaseKeys generateBaseKeys(final ExecutionErrorAccumulator eea, final PartyPK createdBy) {
        var encryptionKeyControl = Session.getModelController(EncryptionKeyControl.class);
        BaseKeys baseKeys = null;

        if(encryptionKeyControl.countEntityEncryptionKeys() == 0) {
            baseKeys = createBaseKeys(eea, createdBy);
            log.info(baseKeys == null? "Base Encryption Keys Not Generated": "Base Encryption Keys Generated");
        } else {
            log.error("Base Encryption Keys Already Exist");
        }

        return baseKeys;
    }

    private void validateBaseKeys(final BaseKeys baseKeys, final int minimumKeysRequired) {
        var baseKeyCount = baseKeys.getBaseKeyCount();

        if(baseKeyCount < minimumKeysRequired) {
            throw new PersistenceEncryptionException(baseKeyCount == 0 ? "Base Encryption Keys Missing" : "Base Encryption Keys Incomplete");
        } else {
            var baseKey1 = baseKeys.getBaseKey1();
            var baseKey2 = baseKeys.getBaseKey2();
            var baseKey3 = baseKeys.getBaseKey3();

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

            var encryptionKeyControl = Session.getModelController(EncryptionKeyControl.class);
            var sha1Hash = Sha1Utils.getInstance().encode(baseKey1, baseKey2);
            var baseEncryptionKey = encryptionKeyControl.getBaseEncryptionKeyBySha1Hash(sha1Hash);

            if(baseEncryptionKey != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(baseEncryptionKey.getPrimaryKey());
                var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS, entityInstance);

                if(!workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName().equals(WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE)) {
                    throw new PersistenceEncryptionException("Supplied Base Encryption Keys Not Active");
                }
            } else {
                throw new PersistenceEncryptionException("Supplied Base Encryption Keys Not Valid");
            }
        }
    }

    public void loadBaseKeys(final BaseKeys baseKeys) {
        var cache = ThreadCaches.currentCaches().getSecurityCache();

        validateBaseKeys(baseKeys, 2);

        cache.put(fqnBaseKeys + "/" + cacheBaseKey1, baseKeys.getBaseKey1());
        cache.put(fqnBaseKeys + "/" + cacheBaseKey2, baseKeys.getBaseKey2());
        log.info("Base Encryption Keys Loaded");
    }

    public BaseKeys changeBaseKeys(final ExecutionErrorAccumulator eea, final BaseKeys oldBaseKeys,
            final PartyPK changedBy) {
        var encryptionKeyControl = Session.getModelController(EncryptionKeyControl.class);

        validateBaseKeys(oldBaseKeys, 3);
        var newBaseKeys = createBaseKeys(eea, changedBy);

        var oldCipher1 = getInitializedCipher(oldBaseKeys.getKey1(), oldBaseKeys.getIv1(), Cipher.DECRYPT_MODE);
        var oldCipher2 = getInitializedCipher(oldBaseKeys.getKey2(), oldBaseKeys.getIv2(), Cipher.DECRYPT_MODE);
        var newCipher1 = getInitializedCipher(newBaseKeys.getKey1(), newBaseKeys.getIv1(), Cipher.ENCRYPT_MODE);
        var newCipher2 = getInitializedCipher(newBaseKeys.getKey2(), newBaseKeys.getIv2(), Cipher.ENCRYPT_MODE);

        var entityEncryptionKeys = encryptionKeyControl.getEntityEncryptionKeysForUpdate();

        for(var entityEncryptionKey : entityEncryptionKeys) {
            var baseEncoding = BaseEncoding.base64();
            var encryptedKey = baseEncoding.decode(entityEncryptionKey.getSecretKey());

            try {
                var decryptedKey = oldCipher1.doFinal(oldCipher2.doFinal(encryptedKey));

                encryptedKey = newCipher2.doFinal(newCipher1.doFinal(decryptedKey));
            } catch (IllegalStateException ise) {
                throw new PersistenceEncryptionException(ise);
            } catch (IllegalBlockSizeException ibse) {
                throw new PersistenceEncryptionException(ibse);
            } catch (BadPaddingException bpe) {
                throw new PersistenceEncryptionException(bpe);
            }

            entityEncryptionKey.setSecretKey(baseEncoding.encode(encryptedKey));
        }

        log.info("Base Encryption Keys Changed");

        return newBaseKeys;
    }

    private BaseKeys getBaseKeys() {
        var cache = ThreadCaches.currentCaches().getSecurityCache();
        var baseKey1 = (BaseKey)cache.get(fqnBaseKeys + "/" + cacheBaseKey1);
        var baseKey2 = (BaseKey)cache.get(fqnBaseKeys + "/" + cacheBaseKey2);

        var cacheCount = (baseKey1 == null ? 0 : 1) + (baseKey2 == null ? 0 : 1);

        if(cacheCount != 2) {
            throw new PersistenceEncryptionException(cacheCount == 0 ? "Base Encryption Keys Missing" : "Base Encryption Keys Incomplete");
        }

        var baseKeys = new BaseKeys(baseKey1, baseKey2);

        return baseKeys;
    }

    private String getEntityEncryptionKeyName(final BaseFactory<?, ?> baseFactory) {
        return baseFactory.getComponentVendorName() + '.' + baseFactory.getEntityTypeName();
    }

    public String encrypt(final BaseFactory<?, ?> baseFactory, final String rowInitializationVector, final String value) {
        return encrypt(getEntityEncryptionKeyName(baseFactory), rowInitializationVector, false, value);
    }

    public String encrypt(final String entityEncryptionKeyName, final String rowInitializationVector, final Boolean isExternal, final String value) {
        String encryptedValue = null;

        if(value != null) {
            try {
                encryptedValue = BaseEncoding.base64().encode(getCipher(entityEncryptionKeyName, rowInitializationVector,
                        isExternal ? EntityEncryptionKeyTypes.EXTERNAL : EntityEncryptionKeyTypes.INTERNAL, Cipher.ENCRYPT_MODE).doFinal(value.getBytes(StandardCharsets.UTF_8)));
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

    public String decrypt(final BaseFactory<?, ?> baseFactory, final String rowInitializationVector, final String value) {
        return decrypt(getEntityEncryptionKeyName(baseFactory), rowInitializationVector, false, value);
    }

    public String decrypt(final String entityEncryptionKeyName, final String rowInitializationVector, Boolean isExternal, final String value) {
        String decryptedValue = null;

        if(value != null) {
            try {
                decryptedValue = new String(getCipher(entityEncryptionKeyName, rowInitializationVector,
                        isExternal ? EntityEncryptionKeyTypes.EXTERNAL : EntityEncryptionKeyTypes.INTERNAL, Cipher.DECRYPT_MODE).doFinal(BaseEncoding.base64().decode(value)), StandardCharsets.UTF_8);
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
        Cipher cipher;

        try {
            cipher = Cipher.getInstance(EncryptionConstants.transformation);
        } catch (NoSuchAlgorithmException nsae) {
            throw new PersistenceEncryptionException(nsae);
        } catch (NoSuchPaddingException nspe) {
            throw new PersistenceEncryptionException(nspe);
        }

        // Setup cipher
        try {
            var ivParameterSpec = new IvParameterSpec(iv);

            cipher.init(cipherMode, secretKey, ivParameterSpec);
        } catch (InvalidKeyException ike) {
            throw new PersistenceEncryptionException(ike);
        } catch (InvalidAlgorithmParameterException iape) {
            throw new PersistenceEncryptionException(iape);
        }

        return cipher;
    }

    private byte[] encryptDataUsingBaseKeys(final byte[] decryptedData) {
        var baseKeys = getBaseKeys();
        var cipher1 = getInitializedCipher(baseKeys.getKey1(), baseKeys.getIv1(), Cipher.ENCRYPT_MODE);
        var cipher2 = getInitializedCipher(baseKeys.getKey2(), baseKeys.getIv2(), Cipher.ENCRYPT_MODE);
        byte[] encryptedData;

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

    private byte[] decryptDataUsingBaseKeys(final byte[] encryptedData) {
        var baseKeys = getBaseKeys();
        var cipher1 = getInitializedCipher(baseKeys.getKey1(), baseKeys.getIv1(), Cipher.DECRYPT_MODE);
        var cipher2 = getInitializedCipher(baseKeys.getKey2(), baseKeys.getIv2(), Cipher.DECRYPT_MODE);
        byte[] decryptedData;

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

    public EntityEncryptionKey getEntityEncryptionKey(final String algorithm, final int keysize, final BaseFactory<?, ?> baseFactory,
            final EntityEncryptionKeyTypes entityEncryptionKeyType) {
        return getEntityEncryptionKey(algorithm, keysize, getEntityEncryptionKeyName(baseFactory), entityEncryptionKeyType);
    }

    public EntityEncryptionKey getEntityEncryptionKey(final String algorithm, final int keysize, final String entityEncryptionKeyName,
            final EntityEncryptionKeyTypes entityEncryptionKeyType) {
        var encryptionKeyControl = Session.getModelController(EncryptionKeyControl.class);
        var suffix = switch(entityEncryptionKeyType) {
            case INTERNAL -> "";
            case EXTERNAL, HMAC_SHA256 -> ".#" + entityEncryptionKeyType.name() + "#";
        };
        var entityEncryptionKeyNameHash = Sha256Utils.getInstance().hash((entityEncryptionKeyName + suffix)
                .getBytes(StandardCharsets.UTF_8));
        var entityEncryptionKey = encryptionKeyControl.getEntityEncryptionKeyByNameHash(entityEncryptionKeyNameHash);

        if(entityEncryptionKey == null) {
            // Key has not yet been generated for this EntityType
            SecretKey secretKey;

            try {
                var keyGenerator = KeyGenerator.getInstance(algorithm);

                keyGenerator.init(keysize);

                secretKey = keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException nsae) {
                throw new PersistenceEncryptionException(nsae);
            }

            var key = secretKey.getEncoded();
            var encryptedKey = encryptDataUsingBaseKeys(key);
            var baseEncoding = BaseEncoding.base64();

            entityEncryptionKey = encryptionKeyControl.createEntityEncryptionKey(entityEncryptionKeyNameHash,
                    baseEncoding.encode(encryptedKey));
        }

        return entityEncryptionKey;
    }

    private Cipher getCipher(final String entityEncryptionKeyName, final String rowInitializationVector,
            final EntityEncryptionKeyTypes entityEncryptionKeyType, final int cipherMode) {
        var entityEncryptionKey = getEntityEncryptionKey(EncryptionConstants.algorithm, EncryptionConstants.keysize,
                entityEncryptionKeyName, entityEncryptionKeyType);
        var baseEncoding = BaseEncoding.base64();
        var encryptedKey = baseEncoding.decode(entityEncryptionKey.getSecretKey());
        var key = decryptDataUsingBaseKeys(encryptedKey);
        var iv = baseEncoding.decode(rowInitializationVector);

        SecretKey secretKey = new SecretKeySpec(key, EncryptionConstants.algorithm);

        return getInitializedCipher(secretKey, iv, cipherMode);
    }

}
