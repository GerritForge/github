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
package com.gerritforge.gerrit.plugins.github;

import com.google.gerrit.extensions.annotations.Listen;
import com.google.gerrit.extensions.annotations.PluginName;
import com.google.gerrit.extensions.client.AuthType;
import com.google.gerrit.extensions.client.MenuItem;
import com.google.gerrit.extensions.webui.TopMenu;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.config.AuthConfig;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Listen
@Singleton
public class GitHubTopMenu implements TopMenu {
  private final List<MenuEntry> menuEntries;
  private final Provider<CurrentUser> userProvider;
  private final AuthConfig authConfig;

  @Inject
  public GitHubTopMenu(
      @PluginName String pluginName,
      Provider<CurrentUser> userProvider,
      AuthConfig authConfig,
      GitHubConfig ghConfig) {
    String baseUrl = "/plugins/" + pluginName;
    this.menuEntries =
        Arrays.asList(
            new MenuEntry(
                "GitHub",
                Arrays.asList(
                    getItem("Scope", baseUrl + "/static/scope.html"),
                    getItem("Profile", baseUrl + "/static/account.html"),
                    getItem("Repositories", baseUrl + "/static/repositories.html"),
                    getItem("Pull Requests", baseUrl + "/static/pullrequests.html"))));
    this.userProvider = userProvider;
    this.authConfig = authConfig;
  }

  private MenuItem getItem(String anchorName, String urlPath) {
    return new MenuItem(anchorName, urlPath, "");
  }

  @Override
  public List<MenuEntry> getEntries() {
    if (userProvider.get() instanceof IdentifiedUser
        &&
        // Only with HTTP authentication we can transparently trigger OAuth if needed
        authConfig.getAuthType().equals(AuthType.HTTP)) {
      return menuEntries;
    }
    return Collections.emptyList();
  }
}
