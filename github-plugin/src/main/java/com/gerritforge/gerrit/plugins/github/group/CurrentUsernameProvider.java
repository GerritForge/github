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

package com.gerritforge.gerrit.plugins.github.group;

import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.IdentifiedUser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.Optional;

public class CurrentUsernameProvider implements Provider<String> {
  public static final String CURRENT_USERNAME = "CurrentUsername";

  private final Provider<CurrentUser> userProvider;

  @Inject
  CurrentUsernameProvider(Provider<CurrentUser> userProvider) {
    this.userProvider = userProvider;
  }

  @Override
  public String get() {
    return Optional.ofNullable(userProvider.get())
        .filter(CurrentUser::isIdentifiedUser)
        .map(CurrentUser::asIdentifiedUser)
        .flatMap(IdentifiedUser::getUserName)
        .orElse(null);
  }
}
