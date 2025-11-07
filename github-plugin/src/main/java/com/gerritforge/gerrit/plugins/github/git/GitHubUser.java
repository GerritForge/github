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

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import java.io.IOException;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitUser;

public class GitHubUser {
  public final String login;
  public final String name;
  public final String email;

  private GitHubUser(GHUser gitHubUser, GitUser author) throws IOException {
    this.login = initLogin(gitHubUser).or(generateLogin(author.getName()));
    this.name = initFullName(gitHubUser).or(author.getName());
    this.email = initEmail(gitHubUser).or(author.getEmail());
  }

  private static String generateLogin(String fullName) {
    return fullName.toLowerCase().replaceAll("^[a-z0-9]", "_");
  }

  private static Optional<String> initLogin(GHUser gitHubUser) {
    return Optional.fromNullable(gitHubUser != null ? gitHubUser.getLogin() : null);
  }

  private static Optional<String> initEmail(GHUser gitHubUser) throws IOException {
    return Optional.fromNullable(
        gitHubUser != null ? Strings.emptyToNull(gitHubUser.getEmail()) : null);
  }

  private static Optional<String> initFullName(GHUser gitHubUser) throws IOException {
    return Optional.fromNullable(
        gitHubUser != null ? Strings.emptyToNull(gitHubUser.getName()) : null);
  }

  public static GitHubUser from(GHUser gitHubUser, GitUser author) throws IOException {
    return new GitHubUser(gitHubUser, author);
  }
}
