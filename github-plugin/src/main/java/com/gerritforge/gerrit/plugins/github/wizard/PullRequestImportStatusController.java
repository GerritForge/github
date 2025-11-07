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
package com.gerritforge.gerrit.plugins.github.wizard;

import com.google.gerrit.server.IdentifiedUser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gerritforge.gerrit.plugins.github.git.PullRequestImporter;
import com.gerritforge.gerrit.plugins.github.oauth.GitHubLogin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PullRequestImportStatusController extends JobStatusController
    implements VelocityController {

  private Provider<PullRequestImporter> pullRequestsImporter;

  @Inject
  public PullRequestImportStatusController(
      final Provider<PullRequestImporter> pullRequestsImporter) {
    this.pullRequestsImporter = pullRequestsImporter;
  }

  @Override
  public void doAction(
      IdentifiedUser user,
      GitHubLogin hubLogin,
      HttpServletRequest req,
      HttpServletResponse resp,
      ControllerErrors errors)
      throws ServletException, IOException {
    respondWithJobStatusJson(resp, pullRequestsImporter.get());
  }
}
