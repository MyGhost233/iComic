package com.qiuchenly.comicparse.Http.BikaApi;

import com.qiuchenly.comicparse.Core.Comic;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Dns;

public class HttpDns implements Dns {
    String[] addresses = PreferenceHelper.getDnsIp(Objects.requireNonNull(Comic.INSTANCE.getContext()));

    public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
        try {
            ArrayList<InetAddress> result = new ArrayList<>();
            if (this.addresses == null || this.addresses.length <= 0) {
            } else {
                for (String byName : this.addresses) {
                    result.add(InetAddress.getByName(byName));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Dns.SYSTEM.lookup(hostname);
        }
    }
}