package com.example.repo.interactor;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCaseObservable<T> {

    protected abstract Observable<T> buildUseCaseObservable();

    public void execute(Observer<T> observer, Scheduler scheduler) {
        buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(observer);
    }

}
