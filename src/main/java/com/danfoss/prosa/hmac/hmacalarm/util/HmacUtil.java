package com.danfoss.prosa.hmac.hmacalarm.util;

import org.apache.commons.codec.digest.HmacUtils;

import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

public class HmacUtil {

    private String sharedSecret;

    public HmacUtil(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public String calculateHmac(String data) {
        return new HmacUtils(HMAC_SHA_256, sharedSecret).hmacHex(data);
    }

    public boolean checkHmac(String data, String hmacHex) {
        return calculateHmac(data).equals(hmacHex);
    }

}
