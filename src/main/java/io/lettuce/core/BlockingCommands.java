/*
 * Copyright 2020-2022 the original author or authors.
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
package io.lettuce.core;

import java.util.HashMap;
import java.util.Map;

import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;

/**
 * Includes all blocking commands, some commands must have a blocking parameter.
 *
 * @author Yang Bodong
 */
class BlockingCommands {

    private static final Map<ProtocolKeyword, String> BLOCKING_COMMANDS = new HashMap<>();

    static {

        BLOCKING_COMMANDS.put(CommandType.BLMPOP, "");
        BLOCKING_COMMANDS.put(CommandType.BLMOVE, "");
        BLOCKING_COMMANDS.put(CommandType.BLPOP, "");
        BLOCKING_COMMANDS.put(CommandType.BRPOP, "");
        BLOCKING_COMMANDS.put(CommandType.BRPOPLPUSH, "");
        BLOCKING_COMMANDS.put(CommandType.BZPOPMAX, "");
        BLOCKING_COMMANDS.put(CommandType.BZPOPMIN, "");
        BLOCKING_COMMANDS.put(CommandType.XREAD, "block");
        BLOCKING_COMMANDS.put(CommandType.XREADGROUP, "block");
    }

    /**
     * Users can customize and add Module Blocking commands
     * @param protocolKeyword the command name
     * @param blockingArg the blocking arg.
     */
    public static void addBlockingCommand(ProtocolKeyword protocolKeyword, String blockingArg) {
        BLOCKING_COMMANDS.put(protocolKeyword, blockingArg);
    }

    /**
     * Determine whether a command is a block command, Some commands are block commands only when specific parameters
     * are added, for example: xread block xxx.
     * @param protocolKeyword the command name
     * @param commandArgs the command args
     * @return true, blocking; false, not blocking.
     */
    public static boolean isBlockingCommand(ProtocolKeyword protocolKeyword, CommandArgs<?, ?> commandArgs) {
        String blockingArg = BLOCKING_COMMANDS.get(protocolKeyword);
        if (blockingArg == null) {
            return false;
        }
        if (blockingArg.isEmpty()) {
            return true;
        }
        return commandArgs.containArg(blockingArg);
    }
}
