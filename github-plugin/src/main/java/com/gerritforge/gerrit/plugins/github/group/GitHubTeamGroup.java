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

import com.google.gerrit.common.Nullable;
import com.google.gerrit.entities.AccountGroup;
import com.google.gerrit.entities.AccountGroup.UUID;
import com.google.gerrit.entities.GroupReference;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class GitHubTeamGroup extends GitHubGroup {
  public interface Factory {
    GitHubTeamGroup get(
        @Assisted GitHubOrganisationGroup orgGroup,
        @Assisted String teamName,
        @Nullable String teamUrl);
  }

  private final GitHubOrganisationGroup orgGroup;
  private final String teamName;

  @Inject
  GitHubTeamGroup(
      @Assisted GitHubOrganisationGroup orgGroup,
      @Assisted String teamName,
      @Nullable String teamUrl) {
    super(uuid(orgGroup.getGroupUUID(), teamName), teamUrl);
    this.orgGroup = orgGroup;
    this.teamName = teamName;
  }

  @Override
  public String getName() {
    return orgGroup.getName() + "/" + teamName;
  }

  public static UUID uuid(UUID orgUUID, String teamName) {
    return AccountGroup.uuid(orgUUID.get() + "/" + teamName);
  }

  public static GroupReference groupReference(GroupReference orgReference, String teamName) {
    return GroupReference.create(
        uuid(orgReference.getUUID(), teamName), orgReference.getName() + "/" + teamName);
  }
}
