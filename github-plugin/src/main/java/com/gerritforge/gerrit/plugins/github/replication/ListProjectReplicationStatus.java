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

package com.gerritforge.gerrit.plugins.github.replication;

import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.extensions.restapi.BadRequestException;
import com.google.gerrit.extensions.restapi.ResourceConflictException;
import com.google.gerrit.extensions.restapi.Response;
import com.google.gerrit.extensions.restapi.RestReadView;
import com.google.gerrit.server.project.ProjectResource;
import com.google.inject.Inject;

public class ListProjectReplicationStatus implements RestReadView<ProjectResource> {
  private final ReplicationStatusStore statusStore;

  @Inject
  public ListProjectReplicationStatus(ReplicationStatusStore statusStore) {
    this.statusStore = statusStore;
  }

  @Override
  public Response<Object> apply(ProjectResource resource)
      throws AuthException, BadRequestException, ResourceConflictException, Exception {
    return Response.ok(statusStore.list(resource.getNameKey()));
  }
}
