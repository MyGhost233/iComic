package com.bilibili.nativelibrary;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BL */
public final class SignedQuery {
    private static final char[] c = "0123456789ABCDEF".toCharArray();
    public final String a;
    public final String b;

    public SignedQuery(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String toString() {
        /*if (this.a == null) {
            return "";
        }
        if (this.b == null) {
            return this.a;
        }*/
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a);
        stringBuilder.append("&sign=");
        stringBuilder.append(this.b);
        return stringBuilder.toString();
    }

    /**
     * 静态方法,该方法为native层调用后组建请求字符串,不可更改方法名与方法签名
     *
     * @param map native层传递参数
     * @return 返回给native层进一步加密
     */
    static String r(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder(256);
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if (!TextUtils.isEmpty(str)) {
                stringBuilder.append(a(str));
                stringBuilder.append("=");
                String str2 = (String) entry.getValue();
                if (str2 == null) {
                    str2 = "";
                } else {
                    str2 = a(str2);
                }
                stringBuilder.append(str2);
                stringBuilder.append("&");
            }
        }
        int length = stringBuilder.length();
        if (length > 0) {
            stringBuilder.deleteCharAt(length - 1);
        }
        if (length == 0) {
            return null;
        }
        return stringBuilder.toString();
    }

    static String a(String str) {
        return a(str, null);
    }

    static String a(String str, String str2) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder stringBuilder = null;
        int i = 0;
        while (i < length) {
            int i2 = i;
            while (i2 < length && a(str.charAt(i2), str2)) {
                i2++;
            }
            if (i2 != length) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                if (i2 > i) {
                    stringBuilder.append(str, i, i2);
                }
                i = i2 + 1;
                while (i < length && !a(str.charAt(i), str2)) {
                    i++;
                }
                try {
                    byte[] bytes = str.substring(i2, i).getBytes("UTF-8");
                    int length2 = bytes.length;
                    for (int i3 = 0; i3 < length2; i3++) {
                        stringBuilder.append('%');
                        stringBuilder.append(c[(bytes[i3] & 240) >> 4]);
                        stringBuilder.append(c[bytes[i3] & 15]);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError(e);
                }
            } else if (i == 0) {
                return str;
            } else {
                stringBuilder.append(str, i, length);
                return stringBuilder.toString();
            }
        }
        if (stringBuilder != null) {
            str = stringBuilder.toString();
        }
        return str;
    }

    private static boolean a(char c, String str) {
        return (c >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || !((c < '0' || c > '9') && "-_.~".indexOf(c) == -1 && (str == null || str.indexOf(c) == -1)));
    }
}
