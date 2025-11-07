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

import com.google.gerrit.common.Nullable;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.account.AccountCache;
import com.google.gerrit.server.account.AccountState;
import com.google.gerrit.server.account.externalids.ExternalId;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gerritforge.gerrit.plugins.github.oauth.OAuthProtocol.AccessToken;
import java.io.IOException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class IdentifiedUserGitHubLoginProvider implements UserScopedProvider<GitHubLogin> {
  private static final Logger log =
      LoggerFactory.getLogger(IdentifiedUserGitHubLoginProvider.class);
  public static final String EXTERNAL_ID_PREFIX =
      ExternalId.SCHEME_EXTERNAL + ":" + OAuthWebFilter.GITHUB_EXT_ID;

  private final Provider<IdentifiedUser> userProvider;
  private final AccountCache accountCache;
  private final OAuthTokenCipher oAuthTokenCipher;
  private final Provider<GitHubLogin> gitHubLoginProvider;

  @Inject
  public IdentifiedUserGitHubLoginProvider(
      Provider<GitHubLogin> gitHubLoginaprovider,
      Provider<IdentifiedUser> identifiedUserProvider,
      AccountCache accountCache,
      OAuthTokenCipher oAuthTokenCipher) {
    this.userProvider = identifiedUserProvider;
    this.accountCache = accountCache;
    this.oAuthTokenCipher = oAuthTokenCipher;
    this.gitHubLoginProvider = gitHubLoginaprovider;
  }

  @Override
  public GitHubLogin get() {
    IdentifiedUser currentUser = userProvider.get();
    return get(currentUser.getUserName().get());
  }

  @Override
  @Nullable
  public GitHubLogin get(String username) {
    try {
      AccessToken accessToken = newAccessTokenFromUser(username);
      if (accessToken != null) {
        GitHubLogin login = gitHubLoginProvider.get();
        login.login(accessToken.accessToken);
        return login;
      }
      return null;
    } catch (IOException e) {
      log.error("Cannot login to GitHub as '" + username + "'", e);
      return null;
    }
  }

  private AccessToken newAccessTokenFromUser(String username) throws IOException {
    AccountState account = accountCache.getByUsername(username).get();
    Collection<ExternalId> externalIds = account.externalIds();
    for (ExternalId accountExternalId : externalIds) {
      String key = accountExternalId.key().get();
      if (key.startsWith(EXTERNAL_ID_PREFIX)) {
        String encryptedOauthToken = key.substring(EXTERNAL_ID_PREFIX.length());
        String decryptedOauthToken = oAuthTokenCipher.decrypt(encryptedOauthToken);
        return new AccessToken(decryptedOauthToken);
      }
    }
    return null;
  }
}
