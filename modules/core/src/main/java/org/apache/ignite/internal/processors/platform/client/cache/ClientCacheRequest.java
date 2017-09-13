/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.platform.client.cache;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryRawReader;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.processors.platform.client.ClientRequest;

/**
 * Cache get request.
 */
class ClientCacheRequest extends ClientRequest {
    /** Cache ID. */
    private final int cacheId;

    /**
     * Constructor.
     *
     * @param reader Reader.
     */
    ClientCacheRequest(BinaryRawReader reader) {
        super(reader);

        cacheId = reader.readInt();

        reader.readByte();  // Flags (skipStore, etc);
    }

    /**
     * Gets the cache for current cache id.
     *
     * @param ctx Kernal context.
     * @return Cache.
     */
    protected IgniteCache cache(GridKernalContext ctx) {
        String cacheName = ctx.cache().context().cacheContext(cacheId).cache().name();

        return ctx.grid().cache(cacheName).withKeepBinary();
    }
}
