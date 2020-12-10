package ch.epfl.sweng.project.utils;

import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.CompletionStage;

/**
 * This adapter class takes a {@link CompletionStage} and makes it a {@link MutableLiveData}. In
 * particular, it subscribes to the completion of the {@link CompletionStage} and calls
 * {@link MutableLiveData#postValue(T)} when it is completed.
 *
 * If the {@link CompletionStage} returns exceptionally, the null value is posted to the livedata.
 *
 * @param <T>
 */
public final class CompletionStageLiveData<T> extends MutableLiveData<T> {
    public CompletionStageLiveData(CompletionStage<T> completionStage) {
        completionStage
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                })
                .thenAccept(this::postValue);
    }
}
