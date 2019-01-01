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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Utility to {@link #awaitAll(long, TimeUnit, Future[])} futures until they are done and to synchronize future execution using
 * {@link #awaitOrCancel(RedisFuture, long, TimeUnit)}.
 *
 * @author Mark Paluch
 * @since 3.0
 */
public class LettuceFutures {

    private LettuceFutures() {
    }

    /**
     * Wait until futures are complete or the supplied timeout is reached. Commands are not canceled (in contrast to
     * {@link #awaitOrCancel(RedisFuture, long, TimeUnit)}) when the timeout expires.
     *
     * @param timeout Maximum time to wait for futures to complete.
     * @param unit Unit of time for the timeout.
     * @param futures Futures to wait for.
     * @return {@literal true} if all futures complete in time, otherwise {@literal false}
     */
    public static boolean awaitAll(long timeout, TimeUnit unit, Future<?>... futures) {

        try {
            long nanos = unit.toNanos(timeout);
            long time = System.nanoTime();

            for (Future<?> f : futures) {

                if (nanos < 0) {
                    return false;
                }

                f.get(nanos, TimeUnit.NANOSECONDS);

                long now = System.nanoTime();
                nanos -= now - time;
                time = now;
            }

            return true;
        } catch (RuntimeException e) {
            throw e;
        } catch (TimeoutException e) {
            return false;
        } catch (ExecutionException e) {

            if (e.getCause() instanceof RedisCommandExecutionException) {
                throw new RedisCommandExecutionException(e.getCause().getMessage(), e.getCause());
            }

            throw new RedisException(e.getCause());
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            throw new RedisCommandInterruptedException(e);
        } catch (Exception e) {
            throw new RedisCommandExecutionException(e);
        }
    }

    /**
     * Wait until futures are complete or the supplied timeout is reached. Commands are canceled if the timeout is reached but
     * the command is not finished.
     *
     * @param cmd Command to wait for
     * @param timeout Maximum time to wait for futures to complete
     * @param unit Unit of time for the timeout
     * @param <T> Result type
     *
     * @return Result of the command.
     */
    public static <T> T awaitOrCancel(RedisFuture<T> cmd, long timeout, TimeUnit unit) {
        return await(timeout, unit, cmd);
    }

    /**
     * Wait until futures are complete or the supplied timeout is reached. Commands are canceled if the timeout is reached but
     * the command is not finished.
     *
     * @param cmd Command to wait for
     * @param timeout Maximum time to wait for futures to complete
     * @param unit Unit of time for the timeout
     * @param <T> Result type
     * @deprecated The method name does not reflect what the method is doing, therefore it is deprecated. Use
     *             {@link #awaitOrCancel(RedisFuture, long, TimeUnit)} instead. The semantics did not change and
     *             {@link #awaitOrCancel(RedisFuture, long, TimeUnit)} simply calls this method.
     * @return True if all futures complete in time.
     */
    @Deprecated
    public static <T> T await(long timeout, TimeUnit unit, RedisFuture<T> cmd) {

        try {
            if (!cmd.await(timeout, unit)) {
                cmd.cancel(true);
                throw new RedisCommandTimeoutException();
            }

            return cmd.get();
        } catch (RuntimeException e) {
            throw e;
        } catch (ExecutionException e) {

            if (e.getCause() instanceof RedisCommandExecutionException) {
                throw new RedisCommandExecutionException(e.getCause().getMessage(), e.getCause());
            }

            throw new RedisException(e.getCause());
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            throw new RedisCommandInterruptedException(e);
        } catch (Exception e) {
            throw new RedisCommandExecutionException(e);
        }
    }
}
