/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.networking.eth2.rpc.core;

import java.util.function.Consumer;
import tech.pegasys.teku.infrastructure.async.SafeFuture;

@FunctionalInterface
public interface ResponseStreamListener<O> {
  static <T> ResponseStreamListener<T> from(Consumer<T> listener) {
    return (T response) -> SafeFuture.fromRunnable(() -> listener.accept(response));
  }

  SafeFuture<?> onResponse(O response);
}