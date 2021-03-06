package io.datanerds.avropatch;

import io.datanerds.avropatch.value.DefaultSchema;
import io.datanerds.avropatch.operation.Operation;
import org.apache.avro.reflect.AvroSchema;

import javax.annotation.Generated;
import javax.annotation.concurrent.ThreadSafe;
import java.util.*;
import java.util.stream.Stream;

/**
 * This class represents a JSON PATCH operation holding a sequence of operations to apply to a given object. It provides
 * shortcuts for accessing the list of operations and some additional fields for metadata information, such as the
 * resource identifier, a timestamp and a {@link Map} holding arbitrary header information.
 *
 * @see <a href="https://tools.ietf.org/html/rfc6902">https://tools.ietf.org/html/rfc6902</a>
 */
@ThreadSafe
public class Patch<T> {

    @AvroSchema(DefaultSchema.HEADERS)
    private final Map<String, ? super Object> headers;
    @AvroSchema(DefaultSchema.VALUE)
    private final T resource;
    @AvroSchema(DefaultSchema.TIMESTAMP)
    private final Date timestamp;
    private final List<Operation> operations;

    @SuppressWarnings("unused") // no-arg constructor needed by Avro
    private Patch() {
        this(null);
    }

    /**
     * Minimalistic constructor setting the timestamp to {@code new Date()} and initializing the operation list and
     * headers map with {@link Collections#emptyList()} / {@link Collections#emptyMap()}.
     * @param resource resource identifier
     */
    public Patch(T resource) {
        this(resource, Collections.emptyList(), Collections.emptyMap());
    }

    /**
     * This constructor sets the timestamp to {@code new Date()} and initializes the headers map with
     * {@link Collections#emptyMap()}.
     * @param resource resource identifier
     * @param operations sequence of operations to apply to a object identifiable by {@code resource}
     */
    public Patch(T resource, List<Operation> operations) {
        this(resource, operations, Collections.emptyMap());
    }

    /**
     * This constructor sets the timestamp to {@code new Date()} and initializes the operation list with
     * {@link Collections#emptyList()}.
     * @param resource resource identifier
     * @param headers arbitrary header information
     */
    public Patch(T resource, Map<String, ?> headers) {
        this(resource, Collections.emptyList(), headers, new Date());
    }

    /**
     * This constructor sets the timestamp to {@code new Date()}.
     * @param resource resource identifier
     * @param operations sequence of operations to apply to a object identifiable by {@code resource}
     * @param headers arbitrary header information
     */
    public Patch(T resource, List<Operation> operations, Map<String, ?> headers) {
        this(resource, operations, headers, new Date());
    }

    /**
     *
     * @param resource resource identifier
     * @param operations sequence of operations to apply to a object identifiable by {@code resource}
     * @param headers arbitrary header information
     * @param timestamp timestamp
     */
    public Patch(T resource, List<Operation> operations, Map<String, ?> headers, Date timestamp) {
        Objects.nonNull(resource);
        this.operations = unmodifiableNullableList(operations);
        this.resource = resource;
        this.timestamp = timestamp;
        this.headers = Collections.unmodifiableMap(new HashMap<>(Optional.ofNullable(headers).orElse(Collections.emptyMap())));
    }

    private List<Operation> unmodifiableNullableList(List<Operation> operations) {
        return Collections.unmodifiableList(
                new ArrayList<>(Optional.ofNullable(operations).orElse(Collections.emptyList())));
    }

    public Operation getOperation(int index) {
        return operations.get(index);
    }

    public int size() {
        return operations.size();
    }

    public Stream<Operation> stream() {
        return operations.stream();
    }

    public boolean isEmpty() {
        return operations.isEmpty();
    }

    public Map<String, ? super Object> getHeaders() {
        return headers;
    }

    /**
     * Convenient method to retrieve and cast a specific header value.
     * @param name name of the header
     * @param <V> expected value type for given header
     * @return header value if exists, {@code null} otherwise
     * @throws ClassCastException
     */
    public <V> V getHeader(String name) {
        return (V) headers.get(name);
    }

    public boolean hasHeader(String name) {
        return headers.containsKey(name);
    }

    public T getResource() {
        return resource;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    @Generated("IntelliJ IDEA")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patch<T> patch = (Patch<T>) o;
        return Objects.equals(headers, patch.headers) &&
                Objects.equals(resource, patch.resource) &&
                Objects.equals(timestamp, patch.timestamp) &&
                Objects.equals(operations, patch.operations);
    }

    @Override
    @Generated("IntelliJ IDEA")
    public int hashCode() {
        return Objects.hash(headers, resource, timestamp, operations);
    }
}
