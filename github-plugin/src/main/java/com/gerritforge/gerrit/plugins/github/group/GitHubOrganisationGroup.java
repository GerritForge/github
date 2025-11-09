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

import static com.google.common.base.Preconditions.checkArgument;

import com.google.gerrit.common.Nullable;
import com.google.gerrit.entities.AccountGroup;
import com.google.gerrit.entities.AccountGroup.UUID;
import com.google.gerrit.entities.GroupDescription.Basic;
import com.google.gerrit.entities.GroupReference;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class GitHubOrganisationGroup extends GitHubGroup implements Basic {
  public interface Factory {
    GitHubOrganisationGroup get(
        @Assisted("orgName") String orgName, @Assisted("orgUrl") @Nullable String orgUrl);
  }

  private final String orgName;

  @Inject
  GitHubOrganisationGroup(
      @Assisted("orgName") String orgName, @Assisted("orgUrl") @Nullable String orgUrl) {
    super(uuid(orgName), orgUrl);
    this.orgName = orgName;
  }

  @Override
  public String getName() {
    return NAME_PREFIX + orgName;
  }

  public static GitHubOrganisationGroup fromUUID(UUID uuid) {
    checkArgument(uuid.get().startsWith(UUID_PREFIX), "Invalid GitHub UUID '" + uuid + "'");
    return new GitHubOrganisationGroup(uuid.get().substring(UUID_PREFIX.length()), null);
  }

  public static UUID uuid(String orgName) {
    return AccountGroup.uuid(UUID_PREFIX + orgName);
  }

  public static GroupReference groupReference(String orgName) {
    return GroupReference.create(uuid(orgName), NAME_PREFIX + orgName);
  }
}
