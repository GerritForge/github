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

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class HttpSessionProvider<T> implements ScopedProvider<T> {
  private final Supplier<String> singletonKey = Suppliers.memoize(() -> getClass().getName());

  @Inject private Provider<T> provider;

  @Inject private Provider<HttpServletRequest> httpRequestProvider;

  @Override
  public T get() {
    return get(httpRequestProvider.get());
  }

  @Override
  public T get(final HttpServletRequest req) {
    HttpSession session = req.getSession();

    synchronized (this) {
      @SuppressWarnings("unchecked")
      T instance = (T) session.getAttribute(singletonKey.get());
      if (instance == null) {
        instance = provider.get();
        session.setAttribute(singletonKey.get(), instance);
      }
      return instance;
    }
  }

  public void clear(final HttpServletRequest req) {
    Optional.ofNullable(req.getSession(false))
        .ifPresent((s) -> s.removeAttribute(singletonKey.get()));
  }

  @Override
  public HttpServletRequest getScopedRequest() {
    return httpRequestProvider.get();
  }
}
