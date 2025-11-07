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

import com.gerritforge.gerrit.plugins.github.git.GitJobStatus.Code;

public class ErrorJob extends AbstractCloneJob implements GitJob {

  private int idx;
  private String organisation;
  private String repository;
  private Throwable exception;
  private GitJobStatus status;

  public ErrorJob(int idx, String organisation, String repository, Throwable e) {
    this.idx = idx;
    this.organisation = organisation;
    this.repository = repository;
    this.exception = e;
    status = new GitJobStatus(idx);
    status.update(Code.FAILED, "Failed", getErrorDescription(exception));
  }

  @Override
  public GitJobStatus getStatus() {
    return status;
  }

  @Override
  public int getIndex() {
    return idx;
  }

  @Override
  public String getOrganisation() {
    return organisation;
  }

  @Override
  public String getRepository() {
    return repository;
  }

  @Override
  public void cancel() {}

  @Override
  public void run() {}
}
