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

package com.gerritforge.gerrit.plugins.github.group;

import com.google.gerrit.entities.AccountGroup;
import com.google.gerrit.entities.AccountGroup.UUID;
import com.google.gerrit.entities.GroupDescription.Basic;

public abstract class GitHubGroup implements Basic {
  public static final String UUID_PREFIX = "github:";
  public static final String NAME_PREFIX = "github/";

  protected final UUID groupUUID;

  protected final String url;

  GitHubGroup(UUID groupUUID, String url) {
    this.groupUUID = groupUUID;
    this.url = url;
  }

  @Override
  public String getEmailAddress() {
    return "";
  }

  @Override
  public AccountGroup.UUID getGroupUUID() {
    return groupUUID;
  }

  @Override
  public String getUrl() {
    return url;
  }
}
