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
package com.gerritforge.gerrit.plugins.github.git;

import com.google.common.base.Strings;
import com.google.gerrit.extensions.registration.DynamicItem;
import com.google.inject.Inject;
import com.gerritforge.gerrit.plugins.github.GitHubURL;
import com.gerritforge.gerrit.plugins.github.oauth.GitHubLogin;
import com.gerritforge.gerrit.plugins.github.oauth.ScopedProvider;
import com.googlesource.gerrit.plugins.replication.api.ReplicationRemotesApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.lib.Config;

class ReplicationRemoteConfigBuilder {
  private final String gitHubUrl;
  private final String username;
  private final String authToken;
  private final DynamicItem<ReplicationRemotesApi> replicationConfigItem;

  @Inject
  ReplicationRemoteConfigBuilder(
      DynamicItem<ReplicationRemotesApi> replicationRemotesItem,
      ScopedProvider<GitHubLogin> ghLoginProvider,
      @GitHubURL String gitHubUrl)
      throws IOException {
    this.gitHubUrl = gitHubUrl;
    this.replicationConfigItem = replicationRemotesItem;
    GitHubLogin ghLogin = ghLoginProvider.get();
    this.username = ghLogin.getMyself().getLogin();
    this.authToken = ghLogin.getAccessToken();
  }

  Config build(String repositoryName) {
    Config remoteConfig = replicationConfigItem.get().get(username);

    remoteConfig.setString("remote", username, "username", username);
    remoteConfig.setString("remote", username, "password", authToken);

    setRemoteConfigIfNotSet(remoteConfig, "url", gitHubUrl + "/${name}.git");

    String[] existingProjects = getProjects();
    List<String> projects = new ArrayList<>(List.of(existingProjects));
    projects.add(repositoryName);

    remoteConfig.setStringList("remote", username, "projects", projects);
    setRemoteConfigIfNotSet(remoteConfig, "push", "refs/*:refs/*");

    return remoteConfig;
  }

  private void setRemoteConfigIfNotSet(Config remoteConfig, String key, String value) {
    String existingValue = remoteConfig.getString("remote", username, key);
    if (Strings.isNullOrEmpty(existingValue)) {
      remoteConfig.setString("remote", username, key, value);
    }
  }

  private String[] getProjects() {
    ReplicationRemotesApi config = replicationConfigItem.get();
    if (config != null) {
      return config.get(username).getStringList("remote", username, "projects");
    }

    return new String[0];
  }
}
