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

import java.io.IOException;

/**
 * Signals that a cipher exception has occurred. This class can be used to represent exception for
 * both encryption and decryption failures
 */
public class CipherException extends IOException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a {@code CipherException} with the specified detail message and cause
   *
   * @param message The detail message of the failure
   * @param cause The cause of the failure
   */
  public CipherException(String message, Exception cause) {
    super(message, cause);
  }
}
