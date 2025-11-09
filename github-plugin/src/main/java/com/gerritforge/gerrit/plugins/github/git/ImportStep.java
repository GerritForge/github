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

import com.gerritforge.gerrit.plugins.github.GitHubURL;
import org.eclipse.jgit.lib.ProgressMonitor;

public abstract class ImportStep {
  private final GitHubRepository gitHubRepository;

  public ImportStep(
      @GitHubURL String gitHubUrl,
      String organisation,
      String repository,
      GitHubRepository.Factory ghRepoFactory) {
    this.gitHubRepository = ghRepoFactory.create(organisation, repository);
  }

  protected String getSourceUri() {
    return gitHubRepository.getCloneUrl();
  }

  public String getOrganisation() {
    return gitHubRepository.getOrganisation();
  }

  public String getRepositoryName() {
    return gitHubRepository.getRepository();
  }

  public GitHubRepository getRepository() {
    return gitHubRepository;
  }

  public abstract void doImport(ProgressMonitor progress) throws Exception;

  public abstract boolean rollback();
}
