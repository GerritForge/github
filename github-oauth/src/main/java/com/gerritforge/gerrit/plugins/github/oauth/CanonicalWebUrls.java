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

import static com.gerritforge.gerrit.plugins.github.oauth.GitHubOAuthConfig.GERRIT_OAUTH_FINAL;
import static com.gerritforge.gerrit.plugins.github.oauth.GitHubOAuthConfig.GITHUB_PLUGIN_OAUTH_SCOPE;

import com.google.common.base.CharMatcher;
import com.google.common.base.MoreObjects;
import com.google.gerrit.httpd.HttpCanonicalWebUrlProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CanonicalWebUrls {
  private final GitHubOAuthConfig oauthConf;
  private final HttpCanonicalWebUrlProvider canonicalWebUrlProvider;

  static String trimTrailingSlash(String url) {
    return CharMatcher.is('/').trimTrailingFrom(url);
  }

  @Inject
  CanonicalWebUrls(
      GitHubOAuthConfig oauthConf, HttpCanonicalWebUrlProvider canonicalWebUrlProvider) {
    this.oauthConf = oauthConf;
    this.canonicalWebUrlProvider = canonicalWebUrlProvider;
  }

  public String getScopeSelectionUrl() {
    return getCannonicalWebUrl()
        + MoreObjects.firstNonNull(oauthConf.scopeSelectionUrl, GITHUB_PLUGIN_OAUTH_SCOPE);
  }

  String getOAuthFinalRedirectUrl() {
    return getCannonicalWebUrl() + GERRIT_OAUTH_FINAL;
  }

  private String getCannonicalWebUrl() {
    return trimTrailingSlash(canonicalWebUrlProvider.get());
  }
}
