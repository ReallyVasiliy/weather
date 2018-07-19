package us.kulakov.weather.data;

import android.support.annotation.NonNull;
import android.util.LruCache;

import io.reactivex.Observable;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
abstract class BaseRepository {

    private LruCache<String, Observable<?>> apiObservables = createLruCache();

    @NonNull
    private LruCache<String, Observable<?>> createLruCache() {
        return new LruCache<>(50);
    }

    @SuppressWarnings("unchecked")
    <T> Observable<T> cacheObservable(String symbol, Observable<T> observable) {
        Observable<T> cachedObservable = (Observable<T>) apiObservables.get(symbol);
        if (cachedObservable != null) {
            return cachedObservable;
        }
        cachedObservable = observable;
        updateCache(symbol, cachedObservable);
        return cachedObservable;
    }

    private <T> void updateCache(String stockSymbol, Observable<T> observable) {
        apiObservables.put(stockSymbol, observable);
    }
}