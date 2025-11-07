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

package com.gerritforge.gerrit.plugins.github.groups;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrganizationStructure implements Serializable {
  private static final long serialVersionUID = 1L;

  private HashMap<String, HashSet<String>> teams = new HashMap<>();

  public Set<String> put(String organisation, String team) {
    HashSet<String> userTeams =
        MoreObjects.firstNonNull(teams.get(organisation), new HashSet<String>());
    userTeams.add(team);
    return teams.put(organisation, userTeams);
  }

  public Set<String> keySet() {
    return teams.keySet();
  }

  public Iterable<String> get(String organization) {
    return teams.get(organization);
  }

  @Override
  public String toString() {
    return teams.entrySet().stream()
        .map(org -> "Organization " + org.getKey() + " Teams: " + org.getValue())
        .collect(Collectors.joining(" : "));
  }
}
