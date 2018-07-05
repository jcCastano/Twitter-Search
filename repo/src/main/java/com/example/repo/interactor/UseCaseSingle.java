package com.example.repo.interactor;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCaseSingle<T> {

    protected abstract Single<T> buildUseCaseSingle();

    public void execute(SingleObserver<T> observer, Scheduler scheduler) {
        buildUseCaseSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(observer);
    }

}
