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

import com.google.common.base.Strings;
import com.google.gerrit.entities.Project;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.project.ProjectCache;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gerritforge.gerrit.plugins.github.GitHubConfig;
import com.gerritforge.gerrit.plugins.github.oauth.GitHubLogin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.kohsuke.github.GHMyself.RepositoryListFilter;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RepositoriesListController implements VelocityController {
  private static final Logger log = LoggerFactory.getLogger(RepositoriesListController.class);
  private final ProjectCache projects;
  private final GitHubConfig config;

  @Inject
  public RepositoriesListController(final ProjectCache projects, final GitHubConfig config) {
    this.projects = projects;
    this.config = config;
  }

  @Override
  public void doAction(
      IdentifiedUser user,
      GitHubLogin hubLogin,
      HttpServletRequest req,
      HttpServletResponse resp,
      ControllerErrors errors)
      throws ServletException, IOException {
    String organisation = req.getParameter("organisation");

    JsonArray jsonRepos = new JsonArray();
    int numRepos = 0;
    PagedIterator<GHRepository> repoIter = getRepositories(hubLogin, organisation).iterator();

    while (repoIter.hasNext() && numRepos < config.repositoryListLimit) {
      GHRepository ghRepository = repoIter.next();
      if (ghRepository.hasPushAccess() && ghRepository.hasPullAccess()) {
        JsonObject repository = new JsonObject();
        String projectName = organisation + "/" + ghRepository.getName();
        if (!projects.get(Project.NameKey.parse(projectName)).isPresent()) {
          repository.add("name", new JsonPrimitive(ghRepository.getName()));
          repository.add("organisation", new JsonPrimitive(organisation));
          repository.add(
              "description", new JsonPrimitive(Strings.nullToEmpty(ghRepository.getDescription())));
          repository.add("private", new JsonPrimitive(Boolean.valueOf(ghRepository.isPrivate())));
          jsonRepos.add(repository);
          numRepos++;
        }
      } else {
        log.warn(
            "Skipping repository {} because user {} has no push/pull access to it",
            ghRepository.getName(),
            user.getUserName());
      }
    }

    resp.getWriter().println(jsonRepos.toString());
  }

  private PagedIterable<GHRepository> getRepositories(GitHubLogin hubLogin, String organisation)
      throws IOException {
    if (organisation.equals(hubLogin.getMyself().getLogin())) {
      return hubLogin
          .getMyself()
          .listRepositories(config.repositoryListPageSize, RepositoryListFilter.OWNER);
    }
    GHOrganization ghOrganisation =
        hubLogin.getMyself().getAllOrganizations().byLogin(organisation);
    return ghOrganisation.listRepositories(config.repositoryListPageSize);
  }
}
