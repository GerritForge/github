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

package com.gerritforge.gerrit.plugins.github;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import org.junit.Ignore;

@Ignore
public class FakeHttpSession implements HttpSession {
  private final HashMap<String, Object> attributes;

  public FakeHttpSession() {
    this.attributes = new HashMap<>();
  }

  @Override
  public long getCreationTime() {
    return 0;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }

  @Override
  public void setMaxInactiveInterval(int i) {}

  @Override
  public int getMaxInactiveInterval() {
    return 0;
  }

  @Override
  public HttpSessionContext getSessionContext() {
    return null;
  }

  @Override
  public Object getAttribute(String s) {
    return attributes.get(s);
  }

  @Override
  public Object getValue(String s) {
    return getAttribute(s);
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return java.util.Collections.enumeration(attributes.keySet());
  }

  @Override
  public String[] getValueNames() {
    return attributes.keySet().toArray(new String[0]);
  }

  @Override
  public void setAttribute(String s, Object o) {
    attributes.put(s, o);
  }

  @Override
  public void putValue(String s, Object o) {
    setAttribute(s, o);
  }

  @Override
  public void removeAttribute(String s) {
    attributes.remove(s);
  }

  @Override
  public void removeValue(String s) {
    removeAttribute(s);
  }

  @Override
  public void invalidate() {
    attributes.clear();
  }

  @Override
  public boolean isNew() {
    return false;
  }
}
