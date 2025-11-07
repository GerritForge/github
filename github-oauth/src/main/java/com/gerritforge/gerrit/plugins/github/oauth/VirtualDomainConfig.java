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

package com.gerritforge.gerrit.plugins.github.oauth;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;

@Singleton
public class VirtualDomainConfig {
  private final GitHubOAuthConfig oauthConfig;

  @Inject
  VirtualDomainConfig(GitHubOAuthConfig oauthConfig) {
    this.oauthConfig = oauthConfig;
  }

  public SortedMap<ScopeKey, List<OAuthProtocol.Scope>> getScopes(HttpServletRequest req) {
    String serverName = req.getServerName();
    return Optional.ofNullable(oauthConfig.virtualScopes.get(serverName))
        .orElse(oauthConfig.scopes);
  }
}
