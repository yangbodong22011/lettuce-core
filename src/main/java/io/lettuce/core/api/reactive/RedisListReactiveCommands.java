/*
 * Copyright 2017-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lettuce.core.api.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.lettuce.core.KeyValue;
import io.lettuce.core.LPosArgs;
import io.lettuce.core.output.ValueStreamingChannel;

/**
 * Reactive executed commands for Lists.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 4.0
 * @generated by io.lettuce.apigenerator.CreateReactiveApi
 */
public interface RedisListReactiveCommands<K, V> {

    /**
     * Remove and get the first element in a list, or block until one is available.
     *
     * @param timeout the timeout in seconds.
     * @param keys the keys.
     * @return KeyValue&lt;K,V&gt; array-reply specifically:
     *
     *         A {@code null} multi-bulk when no element could be popped and the timeout expired. A two-element multi-bulk with
     *         the first element being the name of the key where an element was popped and the second element being the value of
     *         the popped element.
     */
    Mono<KeyValue<K, V>> blpop(long timeout, K... keys);

    /**
     * Remove and get the last element in a list, or block until one is available.
     *
     * @param timeout the timeout in seconds.
     * @param keys the keys.
     * @return KeyValue&lt;K,V&gt; array-reply specifically:
     *
     *         A {@code null} multi-bulk when no element could be popped and the timeout expired. A two-element multi-bulk with
     *         the first element being the name of the key where an element was popped and the second element being the value of
     *         the popped element.
     */
    Mono<KeyValue<K, V>> brpop(long timeout, K... keys);

    /**
     * Pop a value from a list, push it to another list and return it; or block until one is available.
     *
     * @param timeout the timeout in seconds.
     * @param source the source key.
     * @param destination the destination type: key.
     * @return V bulk-string-reply the element being popped from {@code source} and pushed to {@code destination}. If
     *         {@code timeout} is reached, a.
     */
    Mono<V> brpoplpush(long timeout, K source, K destination);

    /**
     * Get an element from a list by its index.
     *
     * @param key the key.
     * @param index the index type: long.
     * @return V bulk-string-reply the requested element, or {@code null} when {@code index} is out of range.
     */
    Mono<V> lindex(K key, long index);

    /**
     * Insert an element before or after another element in a list.
     *
     * @param key the key.
     * @param before the before.
     * @param pivot the pivot.
     * @param value the value.
     * @return Long integer-reply the length of the list after the insert operation, or {@code -1} when the value {@code pivot}
     *         was not found.
     */
    Mono<Long> linsert(K key, boolean before, V pivot, V value);

    /**
     * Get the length of a list.
     *
     * @param key the key.
     * @return Long integer-reply the length of the list at {@code key}.
     */
    Mono<Long> llen(K key);

    /**
     * Remove and get the first element in a list.
     *
     * @param key the key.
     * @return V bulk-string-reply the value of the first element, or {@code null} when {@code key} does not exist.
     */
    Mono<V> lpop(K key);

    /**
     * Return the index of matching elements inside a Redis list. By default, when no options are given, it will scan the list
     * from head to tail, looking for the first match of "element". If the element is found, its index (the zero-based position
     * in the list) is returned. Otherwise, if no match is found, {@code null} is returned. The returned elements indexes are
     * always referring to what {@link #lindex(java.lang.Object, long)} would return. So first element from head is {@code 0},
     * and so forth.
     *
     * @param key the key.
     * @param value the element to search for.
     * @return V integer-reply representing the matching element, or null if there is no match.
     * @since 5.3.2
     */
    Mono<Long> lpos(K key, V value);

    /**
     * Return the index of matching elements inside a Redis list. By default, when no options are given, it will scan the list
     * from head to tail, looking for the first match of "element". If the element is found, its index (the zero-based position
     * in the list) is returned. Otherwise, if no match is found, {@code null} is returned. The returned elements indexes are
     * always referring to what {@link #lindex(java.lang.Object, long)} would return. So first element from head is {@code 0},
     * and so forth.
     *
     * @param key the key.
     * @param value the element to search for.
     * @param args command arguments to configure{@code FIRST} and {@code MAXLEN} options.
     * @return V integer-reply representing the matching element, or null if there is no match.
     * @since 5.3.2
     */
    Mono<Long> lpos(K key, V value, LPosArgs args);

    /**
     * Return the index of matching elements inside a Redis list using the {@code COUNT} option. By default, when no options are
     * given, it will scan the list from head to tail, looking for the first match of "element". The returned elements indexes
     * are always referring to what {@link #lindex(java.lang.Object, long)} would return. So first element from head is
     * {@code 0}, and so forth.
     *
     * @param key the key.
     * @param value the element to search for.
     * @param count limit the number of matches.
     * @return V integer-reply representing the matching elements, or empty if there is no match.
     * @since 5.3.2
     */
    Flux<Long> lpos(K key, V value, int count);

    /**
     * Return the index of matching elements inside a Redis list using the {@code COUNT} option. By default, when no options are
     * given, it will scan the list from head to tail, looking for the first match of "element". The returned elements indexes
     * are always referring to what {@link #lindex(java.lang.Object, long)} would return. So first element from head is
     * {@code 0}, and so forth.
     *
     * @param key the key.
     * @param value the element to search for.
     * @param count limit the number of matches.
     * @param args command arguments to configure{@code FIRST} and {@code MAXLEN} options.
     * @return V integer-reply representing the matching elements, or empty if there is no match.
     * @since 5.3.2
     */
    Flux<Long> lpos(K key, V value, int count, LPosArgs args);

    /**
     * Prepend one or multiple values to a list.
     *
     * @param key the key.
     * @param values the value.
     * @return Long integer-reply the length of the list after the push operations.
     */
    Mono<Long> lpush(K key, V... values);

    /**
     * Prepend values to a list, only if the list exists.
     *
     * @param key the key.
     * @param values the values.
     * @return Long integer-reply the length of the list after the push operation.
     */
    Mono<Long> lpushx(K key, V... values);

    /**
     * Get a range of elements from a list.
     *
     * @param key the key.
     * @param start the start type: long.
     * @param stop the stop type: long.
     * @return V array-reply list of elements in the specified range.
     */
    Flux<V> lrange(K key, long start, long stop);

    /**
     * Get a range of elements from a list.
     *
     * @param channel the channel.
     * @param key the key.
     * @param start the start type: long.
     * @param stop the stop type: long.
     * @return Long count of elements in the specified range.
     * @deprecated since 6.0 in favor of consuming large results through the {@link org.reactivestreams.Publisher} returned by
     *             {@link #lrange}.
     */
    @Deprecated
    Mono<Long> lrange(ValueStreamingChannel<V> channel, K key, long start, long stop);

    /**
     * Remove elements from a list.
     *
     * @param key the key.
     * @param count the count type: long.
     * @param value the value.
     * @return Long integer-reply the number of removed elements.
     */
    Mono<Long> lrem(K key, long count, V value);

    /**
     * Set the value of an element in a list by its index.
     *
     * @param key the key.
     * @param index the index type: long.
     * @param value the value.
     * @return String simple-string-reply.
     */
    Mono<String> lset(K key, long index, V value);

    /**
     * Trim a list to the specified range.
     *
     * @param key the key.
     * @param start the start type: long.
     * @param stop the stop type: long.
     * @return String simple-string-reply.
     */
    Mono<String> ltrim(K key, long start, long stop);

    /**
     * Remove and get the last element in a list.
     *
     * @param key the key.
     * @return V bulk-string-reply the value of the last element, or {@code null} when {@code key} does not exist.
     */
    Mono<V> rpop(K key);

    /**
     * Remove the last element in a list, append it to another list and return it.
     *
     * @param source the source key.
     * @param destination the destination type: key.
     * @return V bulk-string-reply the element being popped and pushed.
     */
    Mono<V> rpoplpush(K source, K destination);

    /**
     * Append one or multiple values to a list.
     *
     * @param key the key.
     * @param values the value.
     * @return Long integer-reply the length of the list after the push operation.
     */
    Mono<Long> rpush(K key, V... values);

    /**
     * Append values to a list, only if the list exists.
     *
     * @param key the key.
     * @param values the values.
     * @return Long integer-reply the length of the list after the push operation.
     */
    Mono<Long> rpushx(K key, V... values);
}
