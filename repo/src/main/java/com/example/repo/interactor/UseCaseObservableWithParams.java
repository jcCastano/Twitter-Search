package com.example.repo.interactor;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCaseObservableWithParams<T, Params> {

    protected abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(Observer<T> observer, Params params, Scheduler scheduler) {
        buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(observer);
    }

}
