/*
 * Copyright 2011-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lambdaworks.redis;

import java.util.List;
import java.util.Map;

import com.lambdaworks.redis.api.async.RedisHashAsyncCommands;
import com.lambdaworks.redis.output.KeyStreamingChannel;
import com.lambdaworks.redis.output.KeyValueStreamingChannel;
import com.lambdaworks.redis.output.ValueStreamingChannel;

/**
 * Asynchronous executed commands for Hashes (Key-Value pairs).
 *
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 3.0
 * @deprecated Use {@link RedisHashAsyncCommands}
 */
@Deprecated
public interface RedisHashesAsyncConnection<K, V> {

    /**
     * Delete one or more hash fields.
     *
     * @param key the key
     * @param fields the field type: key
     * @return RedisFuture&lt;Long&gt; integer-reply the number of fields that were removed from the hash, not including
     *         specified but non existing fields.
     */
    RedisFuture<Long> hdel(K key, K... fields);

    /**
     * Determine if a hash field exists.
     *
     * @param key the key
     * @param field the field type: key
     * @return RedisFuture&lt;Boolean&gt; integer-reply specifically:
     *
     *         {@literal true} if the hash contains {@code field}. {@literal false} if the hash does not contain {@code field},
     *         or {@code key} does not exist.
     */
    RedisFuture<Boolean> hexists(K key, K field);

    /**
     * Get the value of a hash field.
     *
     * @param key the key
     * @param field the field type: key
     * @return RedisFuture&lt;V&gt; bulk-string-reply the value associated with {@code field}, or {@code null} when
     *         {@code field} is not present in the hash or {@code key} does not exist.
     */
    RedisFuture<V> hget(K key, K field);

    /**
     * Increment the integer value of a hash field by the given number.
     *
     * @param key the key
     * @param field the field type: key
     * @param amount the increment type: long
     * @return RedisFuture&lt;Long&gt; integer-reply the value at {@code field} after the increment operation.
     */
    RedisFuture<Long> hincrby(K key, K field, long amount);

    /**
     * Increment the float value of a hash field by the given amount.
     *
     * @param key the key
     * @param field the field type: key
     * @param amount the increment type: double
     * @return RedisFuture&lt;Double;&gt; bulk-string-reply the value of {@code field} after the increment.
     */
    RedisFuture<Double> hincrbyfloat(K key, K field, double amount);

    /**
     * Get all the fields and values in a hash.
     *
     * @param key the key
     * @return RedisFuture&lt;Map&lt;K,V&gt;&gt; array-reply list of fields and their values stored in the hash, or an empty
     *         list when {@code key} does not exist.
     */
    RedisFuture<Map<K, V>> hgetall(K key);

    /**
     * Stream over all the fields and values in a hash.
     *
     * @param channel the channel
     * @param key the key
     *
     * @return RedisFuture&lt;Long&gt; count of the keys.
     */
    RedisFuture<Long> hgetall(KeyValueStreamingChannel<K, V> channel, K key);

    /**
     * Get all the fields in a hash.
     *
     * @param key the key
     * @return RedisFuture&lt;List&lt;K&gt;&gt; array-reply list of fields in the hash, or an empty list when {@code key} does
     *         not exist.
     */
    RedisFuture<List<K>> hkeys(K key);

    /**
     * Get all the fields in a hash.
     *
     * @param channel the channel
     * @param key the key
     *
     * @return RedisFuture&lt;Long&gt; count of the keys.
     */
    RedisFuture<Long> hkeys(KeyStreamingChannel<K> channel, K key);

    /**
     * Get the number of fields in a hash.
     *
     * @param key the key
     * @return RedisFuture&lt;Long&gt; integer-reply number of fields in the hash, or {@literal false} when {@code key} does not
     *         exist.
     */
    RedisFuture<Long> hlen(K key);

    /**
     * Get the values of all the given hash fields.
     *
     * @param key the key
     * @param fields the field type: key
     * @return RedisFuture&lt;List&lt;V&gt;&gt; array-reply list of values associated with the given fields, in the same
     */
    RedisFuture<List<V>> hmget(K key, K... fields);

    /**
     * Stream over the values of all the given hash fields.
     *
     * @param channel the channel
     * @param key the key
     * @param fields the fields
     *
     * @return RedisFuture&lt;Long&gt; count of the keys
     */
    RedisFuture<Long> hmget(ValueStreamingChannel<V> channel, K key, K... fields);

    /**
     * Set multiple hash fields to multiple values.
     *
     * @param key the key
     * @param map the null
     * @return RedisFuture&lt;String&gt; simple-string-reply
     */
    RedisFuture<String> hmset(K key, Map<K, V> map);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key the key
     * @return RedisFuture&lt;MapScanCursor&lt;K, V&gt;&gt; scan cursor.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key the key
     * @param scanArgs scan arguments
     * @return RedisFuture&lt;MapScanCursor&lt;K, V&gt;&gt; scan cursor.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @param scanArgs scan arguments
     * @return RedisFuture&lt;MapScanCursor&lt;K, V&gt;&gt; scan cursor.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key, ScanCursor scanCursor, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @return RedisFuture&lt;MapScanCursor&lt;K, V&gt;&gt; scan cursor.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key, ScanCursor scanCursor);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel streaming channel that receives a call for every key-value pair
     * @param key the key
     * @return RedisFuture&lt;StreamScanCursor&gt; scan cursor.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel streaming channel that receives a call for every key-value pair
     * @param key the key
     * @param scanArgs scan arguments
     * @return RedisFuture&lt;StreamScanCursor&gt; scan cursor.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel streaming channel that receives a call for every key-value pair
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @param scanArgs scan arguments
     * @return RedisFuture&lt;StreamScanCursor&gt; scan cursor.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key, ScanCursor scanCursor, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel streaming channel that receives a call for every key-value pair
     * @param key the key
     * @param scanCursor cursor to resume from a previous scan, must not be {@literal null}
     * @return RedisFuture&lt;StreamScanCursor&gt; scan cursor.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key, ScanCursor scanCursor);

    /**
     * Set the string value of a hash field.
     *
     * @param key the key
     * @param field the field type: key
     * @param value the value
     * @return RedisFuture&lt;Boolean&gt; integer-reply specifically:
     *
     *         {@literal true} if {@code field} is a new field in the hash and {@code value} was set. {@literal false} if
     *         {@code field} already exists in the hash and the value was updated.
     */
    RedisFuture<Boolean> hset(K key, K field, V value);

    /**
     * Set the value of a hash field, only if the field does not exist.
     *
     * @param key the key
     * @param field the field type: key
     * @param value the value
     * @return RedisFuture&lt;Boolean&gt; integer-reply specifically:
     *
     *         {@code 1} if {@code field} is a new field in the hash and {@code value} was set. {@code 0} if {@code field}
     *         already exists in the hash and no operation was performed.
     */
    RedisFuture<Boolean> hsetnx(K key, K field, V value);

    /**
     * Get the string length of the field value in a hash.
     *
     * @param key the key
     * @param field the field type: key
     * @return RedisFuture&lt;Long&gt; integer-reply the string length of the {@code field} value, or {@code 0} when
     *         {@code field} is not present in the hash or {@code key} does not exist at all.
     */
    RedisFuture<Long> hstrlen(K key, K field);

    /**
     * Get all the values in a hash.
     *
     * @param key the key
     * @return RedisFuture&lt;List&lt;V&gt;&gt; array-reply list of values in the hash, or an empty list when {@code key} does
     *         not exist.
     */
    RedisFuture<List<V>> hvals(K key);

    /**
     * Stream over all the values in a hash.
     *
     * @param channel streaming channel that receives a call for every value
     * @param key the key
     *
     * @return RedisFuture&lt;Long&gt; count of the keys.
     */
    RedisFuture<Long> hvals(ValueStreamingChannel<V> channel, K key);
}
