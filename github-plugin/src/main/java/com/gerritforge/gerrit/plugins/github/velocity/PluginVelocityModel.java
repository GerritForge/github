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
package com.gerritforge.gerrit.plugins.github.velocity;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import org.apache.velocity.VelocityContext;

@RequestScoped
public class PluginVelocityModel {

  private final VelocityContext context;

  public VelocityContext getContext() {
    return context;
  }

  @Inject
  public PluginVelocityModel(VelocityContext context) {
    this.context = context;
  }

  public Object get(String key) {
    return context.get(key);
  }

  public Object[] getKeys() {
    return context.getKeys();
  }

  public Object put(String key, Object value) {
    return context.put(key, value);
  }

  public Object remove(String key) {
    return context.remove(key);
  }
}
