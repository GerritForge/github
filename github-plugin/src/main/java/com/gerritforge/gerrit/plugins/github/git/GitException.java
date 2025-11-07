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

import java.io.IOException;

public class GitException extends IOException {
  private static final long serialVersionUID = -1180349547385523064L;

  public GitException() {
    super();
  }

  public GitException(String message) {
    super(message);
  }

  public GitException(Throwable cause) {
    super(cause);
  }

  public GitException(String message, Throwable cause) {
    super(message, cause);
  }

  public String getErrorDescription() {
    return getMessage();
  }
}
