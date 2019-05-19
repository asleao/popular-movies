package br.com.popularmovies.data;

import android.os.SystemClock;

import java.util.HashMap;

public class CacheControl {
    private long timeout;
    private HashMap<String, Long> mCachedRequests = new HashMap<>();

    public CacheControl(long timeout) {
        this.timeout = timeout;
    }

    public void addRequest(String cacheId, Long timestamp) {
        mCachedRequests.put(cacheId, timestamp);
    }

    public boolean shouldFetch(String cacheId) {
        return mCachedRequests.get(cacheId) == null ||
                SystemClock.uptimeMillis() - mCachedRequests.get(cacheId) > timeout;
    }
}
