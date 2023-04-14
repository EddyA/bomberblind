package utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple2<U, V> {

    private U first; // first element of that tuple.
    private V second; // second element of that tuple.
}
