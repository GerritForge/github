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

import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.account.GroupMembership;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RemoteSiteUser extends CurrentUser {
  public interface Factory {
    RemoteSiteUser create(@Assisted GroupMembership authGroups);
  }

  private final GroupMembership effectiveGroups;

  @Inject
  RemoteSiteUser(@Assisted GroupMembership authGroups) {
    effectiveGroups = authGroups;
  }

  @Override
  public GroupMembership getEffectiveGroups() {
    return effectiveGroups;
  }

  @Override
  public Object getCacheKey() {
    return effectiveGroups.getKnownGroups();
  }
}
