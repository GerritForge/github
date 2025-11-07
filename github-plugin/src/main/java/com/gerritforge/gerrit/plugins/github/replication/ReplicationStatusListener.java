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

package com.gerritforge.gerrit.plugins.github.replication;

import com.google.gerrit.entities.Project.NameKey;
import com.google.gerrit.server.events.Event;
import com.google.gerrit.server.events.EventListener;
import com.google.gerrit.server.events.RefEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ReplicationStatusListener implements EventListener {
  private static final String REF_REPLICATED_EVENT = "ref-replicated";
  private static Logger log = LoggerFactory.getLogger(ReplicationStatusListener.class);

  private final ReplicationStatusStore statusStore;
  private final Gson gson;

  @Inject
  public ReplicationStatusListener(
      ReplicationStatusStore statusStore, Provider<Gson> gsonProvider) {
    this.statusStore = statusStore;
    this.gson = gsonProvider.get();
  }

  @Override
  public void onEvent(Event event) {
    if (RefEvent.class.isAssignableFrom(event.getClass())
        && event.getType().equals(REF_REPLICATED_EVENT)) {
      RefEvent refEvent = (RefEvent) event;
      NameKey projectNameKey = refEvent.getProjectNameKey();
      JsonObject eventJson = (JsonObject) gson.toJsonTree(event);
      String refKey = eventJson.get("ref").getAsString();

      try {
        statusStore.set(projectNameKey, refKey, eventJson);
      } catch (IOException e) {
        log.error(
            "Unable to update replication status for event "
                + eventJson
                + " on project "
                + projectNameKey,
            e);
      }
    }
  }
}
