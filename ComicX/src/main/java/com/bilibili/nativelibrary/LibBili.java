package com.bilibili.nativelibrary;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class LibBili {
    private static native String a(String str);

    private static native IvParameterSpec b(String str) throws InvalidKeyException;

    public static native int getCpuCount();

    @Deprecated
    public static native int getCpuId();

    static native SignedQuery s(SortedMap<String, String> sortedMap);

    static {
        System.loadLibrary("bili");
    }

    @Deprecated
    public static String a() {
        return c("android");
    }

    private static String c(String str) {
        if (TextUtils.equals(str, "android_tv_yst")) {
            return a("android_tv");
        }
        return a(str);
    }

    public static SignedQuery a(Map<String, String> map) {
        return s(map == null ? new TreeMap() : new TreeMap<>(map));
    }

    public static byte[] a(String str, byte[] bArr) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            try {
                return STR2AES(new SecretKeySpec(Arrays.copyOf(bytes, 16), "AES"), b(str), bArr, 1);
            } catch (Exception unused) {
                return bArr;
            }
        } catch (UnsupportedEncodingException unused2) {
            return bArr;
        }
    }

    public static byte[] b(String str, byte[] bArr) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            try {
                return STR2AES(new SecretKeySpec(Arrays.copyOf(bytes, 16), "AES"), b(str), bArr, 2);
            } catch (Exception unused) {
                return bArr;
            }
        } catch (UnsupportedEncodingException unused2) {
            return bArr;
        }
    }

    //下面的是大段的five注释,JADX解析不出来异常处理而已.
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000f A:{Splitter: B:0:0x0000, ExcHandler: java.security.NoSuchAlgorithmException (r2_2 'e' java.security.NoSuchAlgorithmException)} */
    /* JADX WARNING: Missing block: B:3:0x000f, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:5:0x0015, code:
            throw new java.lang.AssertionError(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] STR2AES(SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] bArr, int opMode) throws GeneralSecurityException {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(opMode, secretKey, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    //=======================   计算bilibili登录RSA算法

    @SuppressLint(value = {"TrulyRandom", "DefaultCharset"})
    private static byte[] STR2RSA(String arg2, String arg3) {
        try {
            PublicKey v3 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(arg3.getBytes(), 0)));
            Cipher v0 = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            v0.init(1, v3);
            return v0.doFinal(arg2.getBytes());
        } catch (Exception e) {
            return null;
        }
    }

    private static String pass2encode64(String arg0, String arg1) {
        byte[] v0 = STR2RSA(arg0, arg1);
        if (v0 != null) {
            return Base64.encodeToString(v0, 0);
        }
        return null;
    }

    public static String encryptPassword(String pubkey, String hash, String arg4) {
        String v0 = pubkey.replaceFirst("-----BEGIN PUBLIC KEY-----\n", "").replace("\n-----END PUBLIC KEY-----\n", "");
        String v1 = hash + arg4;
        return pass2encode64(v1, v0);
    }

    /**
     * 计算bilibili加密 公钥与模数可以固定
     *
     * @param pass 提供明文密码
     * @return 你是弱智?返回值是什么还要我重复一遍?
     */
    public static String encryptPassword(String pass) {
        return encryptPassword("-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdScM09sZJqFPX7bvmB2y6i08J\nbHsa0v4THafPbJN9NoaZ9Djz1LmeLkVlmWx1DwgHVW+K7LVWT5FV3johacVRuV98\n37+RNntEK6SE82MPcl7fA++dmW2cLlAjsIIkrX+aIvvSGCuUfcWpWFy3YVDqhuHr\nNDjdNcaefJIQHMW+sQIDAQAB\n-----END PUBLIC KEY-----\n", "1b9c4eba21297457", pass);
    }

    //String pass = encryptPassword("-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdScM09sZJqFPX7bvmB2y6i08J\nbHsa0v4THafPbJN9NoaZ9Djz1LmeLkVlmWx1DwgHVW+K7LVWT5FV3johacVRuV98\n37+RNntEK6SE82MPcl7fA++dmW2cLlAjsIIkrX+aIvvSGCuUfcWpWFy3YVDqhuHr\nNDjdNcaefJIQHMW+sQIDAQAB\n-----END PUBLIC KEY-----\n", "1b9c4eba21297457", "QQQQQQQQ");
    //        System.out.println(pass);
    //
    //        String temp = "appkey=4409e2ce8ffd12b8&build=101602&captcha=Qqqq&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&password=" + pass + "&platform=android&token=c3f891783dcda7af&username=17777777777";
    //
    //        Map<String, String> map = new HashMap();
    //
    //        for (String str : temp.split("&")) {
    //            String[] single = str.split("=");
    //            map.put(single[0], single[1]);
    //        }
    //        //appkey=4409e2ce8ffd12b8&auth_code=07ac650994f71282265747c8daa27231&build=101602&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&platform=android
    //        /*map.put("appkey", "4409e2ce8ffd12b8");
    //        map.put("auth_code", "07ac650994f71282265747c8daa27231");
    //        map.put("build", "101602");
    //        map.put("channel", "master");
    //        map.put("guid", "XZE72D6E5AAB98D33703546128A2DA8335867");
    //        map.put("mobi_app", "android_tv_yst");
    //        map.put("platform", "android");*/
    //        //appkey=4409e2ce8ffd12b8&auth_code=ff38da97755466f53d4add35420a934b&build=101602&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&platform=android&ts=1554050734
    //        SignedQuery ss = LibBili.a(map);
    //        System.out.println(ss.toString());
}
