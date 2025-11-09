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

import com.google.inject.ProvisionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractCloneJob {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractCloneJob.class);

  public AbstractCloneJob() {
    super();
  }

  protected String getErrorDescription(Throwable exception) {
    LOG.error("Job " + this + " FAILED", exception);
    if (exception instanceof ProtectedBranchFoundException) {
      return exception.getMessage();
    }
    if (GitException.class.isAssignableFrom(exception.getClass())) {
      return ((GitException) exception).getErrorDescription();
    } else if (ProvisionException.class.isAssignableFrom(exception.getClass())) {
      Throwable cause = exception.getCause();
      if (cause != null) {
        return getErrorDescription(cause);
      }
      return "Import startup failed";
    } else {
      return "Internal error";
    }
  }
}
