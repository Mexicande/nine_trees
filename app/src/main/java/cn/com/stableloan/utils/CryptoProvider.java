package cn.com.stableloan.utils;

import java.security.Provider;

/**
 * Created by apple on 2017/7/18.
 */

public class CryptoProvider extends Provider {
    private static final long serialVersionUID = 7991202868423459598L ;

    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     *
     */
    protected CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }
}
