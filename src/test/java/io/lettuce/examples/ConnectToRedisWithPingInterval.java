package io.lettuce.examples;

import java.time.Duration;
import java.util.List;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs.Builder;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.api.StatefulRedisConnection;

/**
 * @author Yang Bodong
 * @date 2022/11/11
 */
public class ConnectToRedisWithPingInterval {
    public static void main(String[] args) throws Exception {
        RedisClient client = RedisClient.create(RedisURI.Builder.redis("localhost", 6379).withTimeout(
            Duration.ofSeconds(1000)).build());
        client.setOptions(ClientOptions.builder().pingConnectionInterval(3000).build());
        StatefulRedisConnection<String, String> connection = client.connect();

        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1000);
                System.out.printf("%d:%s\n", i, connection.sync().set("" + i, "" + i));

                List<StreamMessage<String, String>> stream = connection.sync().xread(Builder.block(0),
                    StreamOffset.latest("stream"));
                for (StreamMessage sm : stream) {
                    System.out.println(sm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        connection.close();
        client.shutdown();
    }
}
