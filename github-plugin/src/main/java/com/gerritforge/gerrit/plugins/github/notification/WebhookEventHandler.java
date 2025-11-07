// Copyright (C) 2025 GerritForge, Inc.
//
// Licensed under the BSL 1.1 (the "License");
// you may not use this file except in compliance with the License.
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.gerritforge.gerrit.plugins.github.notification;

import java.io.IOException;
import org.kohsuke.github.GHEvent;

/**
 * Abstract interface to handler which is responsible for a specific github webhook event type.
 *
 * <p>Implementation classes must be named by the convention which {@link
 * WebhookServlet#getWebhookClassName(GHEvent)} defines.
 *
 * @param <T> Type of payload. Must be consistent to the event type.
 * @return true if the event has been successfully processed
 */
interface WebhookEventHandler<T> {
  Class<T> getPayloadType();

  boolean doAction(T payload) throws IOException;
}
