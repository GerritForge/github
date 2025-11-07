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

public class GitDestinationAlreadyExistsException extends GitException {
  private static final long serialVersionUID = -6202681486717426148L;

  public GitDestinationAlreadyExistsException(String projectName) {
    super("Git project " + projectName + " already exists");
  }

  @Override
  public String getErrorDescription() {
    return "A repository with the same name already exists";
  }
}
