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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.kohsuke.github.HttpConnector;

public class GitHubHttpConnector implements HttpConnector {

  private final GitHubOAuthConfig config;

  @Inject
  public GitHubHttpConnector(final GitHubOAuthConfig config) {
    this.config = config;
  }

  @Override
  public HttpURLConnection connect(URL url) throws IOException {
    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
    HttpURLConnection.setFollowRedirects(true);
    huc.setConnectTimeout((int) config.httpConnectionTimeout);
    huc.setReadTimeout((int) config.httpReadTimeout);
    return huc;
  }
}
