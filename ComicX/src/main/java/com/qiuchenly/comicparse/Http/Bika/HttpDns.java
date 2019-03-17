package com.qiuchenly.comicparse.Http.Bika;

import com.qiuchenly.comicparse.Core.Comic;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Dns;

public class HttpDns implements Dns {
    private static ArrayList<InetAddress> mResult = null;
    private String[] addresses = null;

    public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
        if (addresses == null)
            addresses = PreferenceHelper.getDnsIp(Objects.requireNonNull(Comic.INSTANCE.getContext()));
        //fix reload DNS Address,once load,everywhere can load.
        if (mResult != null) {
            return mResult;
        }
        try {
            ArrayList<InetAddress> result = new ArrayList<>();
            if (this.addresses.length <= 0) {
                mResult = new ArrayList<>();
            } else {
                for (String byName : this.addresses) {
                    result.add(InetAddress.getByName(byName));
                }
            }
            mResult = result;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Dns.SYSTEM.lookup(hostname);
        }
    }
}