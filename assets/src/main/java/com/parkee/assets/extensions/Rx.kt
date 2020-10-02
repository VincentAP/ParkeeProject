package com.parkee.assets.extensions

import com.parkee.assets.repo.Repo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo

fun Disposable.addToDisposable() {
    if (Repo.disposable == null) Repo.disposable = CompositeDisposable()
    Repo.disposable?.let { addTo(it) }
}