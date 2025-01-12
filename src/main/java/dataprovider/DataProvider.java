package dataprovider;

import java.util.stream.Stream;

public interface DataProvider<T> {
    Stream<T> provide();
}
