package ch.epfl.sweng.presentation.utils;

import static androidx.lifecycle.Transformations.map;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A class that provides static methods to transform LiveData objects. */
public final class LiveDataTransformations {

  private LiveDataTransformations() {
    // No instances.
  }

  /**
   * Combines the latest values of the given LiveData objects into a single LiveData object.
   *
   * @param list the list of LiveData objects to combine.
   * @param <T> the type of the values to combine.
   * @return a LiveData object that emits the combined values.
   * @see #combineLatestNonNull(List) for a version that only emits non-null values.
   */
  public static <T> LiveData<List<T>> combineLatest(List<LiveData<T>> list) {
    var result = new MediatorLiveData<List<T>>();
    var initial = new ArrayList<T>(list.size());
    result.postValue(initial);
    for (int i = 0; i < list.size(); i++) {
      final int index = i;
      initial.add(null);
      result.addSource(
          list.get(i),
          value -> {
            var current = requireNonNull(result.getValue());
            var updated = new ArrayList<>(current);
            updated.set(index, value);
            result.postValue(updated);
          });
    }
    return result;
  }

  /**
   * Combines the latest values of the given LiveData objects into a single LiveData object. Null
   * values will be filtered out.
   *
   * @param list the list of LiveData objects to combine.
   * @param <T> the type of the values to combine.
   * @return a LiveData object that emits the combined values.
   * @see #combineLatest(List) a version that does not filter out null values.
   */
  public static <T> LiveData<List<T>> combineLatestNonNull(List<LiveData<T>> list) {
    return map(combineLatest(list), v -> v.stream().filter(Objects::nonNull).collect(toList()));
  }
}
