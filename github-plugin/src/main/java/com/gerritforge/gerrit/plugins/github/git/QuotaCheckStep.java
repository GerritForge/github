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

import static com.google.gerrit.server.quota.QuotaGroupDefinitions.REPOSITORY_SIZE_GROUP;

import com.google.gerrit.entities.Project;
import com.google.gerrit.server.quota.QuotaBackend;
import com.google.gerrit.server.quota.QuotaResponse;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gerritforge.gerrit.plugins.github.GitHubConfig;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuotaCheckStep extends ImportStep {
  private static final Logger LOG = LoggerFactory.getLogger(QuotaCheckStep.class);
  private final QuotaBackend quotaBackend;

  private static final long BYTES_PER_KB = 1024L;

  public interface Factory {
    QuotaCheckStep create(
        @Assisted("organisation") String organisation, @Assisted("repository") String repository);
  }

  @Inject
  public QuotaCheckStep(
      QuotaBackend quotaBackend,
      GitHubConfig config,
      GitHubRepository.Factory gitHubRepoFactory,
      @Assisted("organisation") String organisation,
      @Assisted("repository") String repository) {
    super(config.gitHubUrl, organisation, repository, gitHubRepoFactory);
    this.quotaBackend = quotaBackend;
  }

  @Override
  public void doImport(ProgressMonitor progress) throws GitException {
    Project.NameKey fullProjectName =
        Project.nameKey(getOrganisation() + "/" + getRepositoryName());
    try {
      progress.beginTask("Getting repository size", 1);
      LOG.debug("{}|Getting repository size", fullProjectName);
      long size = getRepository().getSize() * BYTES_PER_KB;
      LOG.debug("{}|Repository size: {} Kb", fullProjectName, size);

      LOG.debug("{}|Checking repository size is allowed by quota", fullProjectName);
      if (size > 0) {
        QuotaResponse.Aggregated aggregated =
            quotaBackend.currentUser().project(fullProjectName).dryRun(REPOSITORY_SIZE_GROUP, size);
        aggregated.throwOnError();
      }
      progress.update(1);
    } catch (Exception e) {
      LOG.error("{}|Quota does not allow importing repo", fullProjectName, e);
      throw new QuotaEnforcedException(e.getMessage(), e);
    } finally {
      progress.endTask();
    }
    LOG.debug("{}|SUCCESS repository size is allowed", fullProjectName);
  }

  @Override
  public boolean rollback() {
    return true;
  }
}
