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

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.gerritforge.gerrit.plugins.github.git.BatchImporter;
import com.gerritforge.gerrit.plugins.github.git.GitJob;
import com.gerritforge.gerrit.plugins.github.git.GitJobStatus;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class JobStatusController {

  public JobStatusController() {
    super();
  }

  protected void respondWithJobStatusJson(HttpServletResponse resp, BatchImporter cloner)
      throws IOException {
    Collection<GitJob> jobs = cloner.getJobs();
    List<GitJobStatus> jobListStatus = Lists.newArrayList();
    for (GitJob job : jobs) {
      jobListStatus.add(job.getStatus());
    }
    try (JsonWriter writer = new JsonWriter(resp.getWriter())) {
      new Gson().toJson(jobListStatus, jobListStatus.getClass(), writer);
    }
  }
}
