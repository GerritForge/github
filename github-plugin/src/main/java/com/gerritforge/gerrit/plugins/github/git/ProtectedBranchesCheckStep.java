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

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gerritforge.gerrit.plugins.github.GitHubConfig;
import java.util.Collection;
import java.util.List;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.kohsuke.github.GHBranch;

public class ProtectedBranchesCheckStep extends ImportStep {

  public interface Factory {
    ProtectedBranchesCheckStep create(
        @Assisted("organisation") String organisation, @Assisted("name") String repository);
  }

  @Inject
  public ProtectedBranchesCheckStep(
      GitHubConfig config,
      GitHubRepository.Factory gitHubRepoFactory,
      @Assisted("organisation") String organisation,
      @Assisted("name") String repository) {
    super(config.gitHubUrl, organisation, repository, gitHubRepoFactory);
  }

  @Override
  public void doImport(ProgressMonitor progress) throws Exception {
    Collection<GHBranch> branches = getRepository().getBranches().values();
    progress.beginTask("Checking branch protection", branches.size());
    List<String> protectedBranchNames = Lists.newLinkedList();
    for (GHBranch branch : branches) {
      if (branch.isProtected()) {
        protectedBranchNames.add(branch.getName());
      }
      progress.update(1);
    }
    progress.endTask();
    if (!protectedBranchNames.isEmpty()) {
      throw new ProtectedBranchFoundException(
          String.format(
              "Cannot import project with protected branches, you should remove protection from:%s",
              Joiner.on(",").join(protectedBranchNames)));
    }
  }

  @Override
  public boolean rollback() {
    return true;
  }
}
