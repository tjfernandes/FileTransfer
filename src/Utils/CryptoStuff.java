package Utils; /**
 * * A utility class for encrypt/decrypt a file.
 **/


// In this version we separate the definition of ALGORITHM
// and the definition of CIPHERSUITE parameterization to be
// more clear and correct the utilization and generalization of
// use ...

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.Key;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;


public class CryptoStuff {

    // Will use AES and a Parameteriztion for Enc/Dec processing
    // In this case will will use CTR mode and No Padding
    // You can use other parameterizations for your tests
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CTR/NoPadding";
    // Initializaton vector (fixed) we will use ...
    private static final byte[] ivBytes = new byte[]
            {
                    0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00,
                    0x0f, 0x0d, 0x0e, 0x0c, 0x0b, 0x0a, 0x09, 0x08
            };


    public static byte[] encrypt(String key, byte[] buf)
            throws CryptoException {
        return doCrypto(Cipher.ENCRYPT_MODE, key, buf);
    }

    public static byte[] decrypt(String key, byte[] buf)
            throws CryptoException {
        return doCrypto(Cipher.DECRYPT_MODE, key, buf);
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] buf) throws CryptoException {
        try {

            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey, ivSpec);

            return cipher.doFinal(buf);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                 | InvalidKeyException | BadPaddingException
                 | IllegalBlockSizeException
                 | InvalidAlgorithmParameterException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }

    }

}
