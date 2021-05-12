package com.lab.swholocron.ui.arch

import com.lab.swholocron.util.observable.Observable

/**
 * Wrapper/Proxy to the actual view
 */
interface ProxyView<LISTENER> : Observable<LISTENER> {
}

abstract class BaseProxyView<LISTENER>() : ProxyView<LISTENER> {
    private val observableDelegate = Observable.create<LISTENER>()

    override fun registerListener(listener: LISTENER) {
        observableDelegate.registerListener(listener)
    }

    override fun unregisterListener(listener: LISTENER) {
        observableDelegate.unregisterListener(listener)
    }

    override fun getListeners(): Set<LISTENER> {
        return observableDelegate.getListeners()
    }
}