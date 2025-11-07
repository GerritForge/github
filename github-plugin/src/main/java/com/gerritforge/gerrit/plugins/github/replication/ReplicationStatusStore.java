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

import com.google.gerrit.entities.Project;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;

public interface ReplicationStatusStore {

  public void set(Project.NameKey projectKey, String refKey, JsonObject statusEvent)
      throws IOException;

  public List<JsonObject> list(Project.NameKey projectKey) throws IOException;
}
