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

import com.google.inject.Singleton;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles ping event in github webhook.
 *
 * @see <a href="https://developer.github.com/webhooks/#ping-event">Ping Event</a>
 */
@Singleton
class PingHandler implements WebhookEventHandler<PingHandler.Ping> {
  private static final Logger logger = LoggerFactory.getLogger(PingHandler.class);

  static class Ping {
    String zen;
    int hookId;

    @Override
    public String toString() {
      return "Ping [zen=" + zen + ", hookId=" + hookId + "]";
    }
  }

  @Override
  public boolean doAction(Ping payload) throws IOException {
    logger.info(payload.toString());
    return true;
  }

  @Override
  public Class<Ping> getPayloadType() {
    return Ping.class;
  }
}
