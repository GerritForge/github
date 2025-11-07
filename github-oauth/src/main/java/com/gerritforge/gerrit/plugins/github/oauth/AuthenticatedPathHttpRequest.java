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
package com.gerritforge.gerrit.plugins.github.oauth;

import javax.servlet.http.HttpServletRequest;

public class AuthenticatedPathHttpRequest extends AuthenticatedHttpRequest {

  private final String contextPath;
  private final StringBuffer requestURL;
  private String requestURI;
  private String requestPath;

  public AuthenticatedPathHttpRequest(
      HttpServletRequest request, String requestPath, String userHeader, String username) {
    super(request, userHeader, username);

    this.requestPath = requestPath;
    this.contextPath = super.getContextPath();
    this.requestURL = super.getRequestURL();
    this.requestURI = super.getRequestURI();
  }

  @Override
  public String getRequestURI() {
    return contextPath + requestPath;
  }

  @Override
  public StringBuffer getRequestURL() {
    return new StringBuffer(
        requestURL.substring(0, requestURL.indexOf(requestURI)) + getRequestURI());
  }

  @Override
  public String getServletPath() {
    return requestPath;
  }
}
