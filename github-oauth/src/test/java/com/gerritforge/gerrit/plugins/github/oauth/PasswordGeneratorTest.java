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

import static com.gerritforge.gerrit.plugins.github.oauth.GitHubOAuthConfig.KeyConfig.PASSWORD_LENGTH_DEFAULT;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PasswordGeneratorTest {
  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private Path passwordPath;
  private final PasswordGenerator objectUnderTest = new PasswordGenerator();

  @Before
  public void setup() throws IOException {
    passwordPath =
        temporaryFolder.newFolder().toPath().resolve(PasswordGenerator.DEFAULT_PASSWORD_FILE);
  }

  @Test
  public void shouldGenerateKeyFileWithPasswordDefaultLength() throws IOException {
    assertTrue(objectUnderTest.generate(passwordPath));
    assertTrue(Files.isRegularFile(passwordPath));

    byte[] token = Files.readAllBytes(passwordPath);
    assertEquals(
        String.format(
            "Generated password length doesn't equal to expected %d", PASSWORD_LENGTH_DEFAULT),
        token.length,
        PASSWORD_LENGTH_DEFAULT);
  }

  @Test
  public void shouldNotGenerateNewDefaultKeyIfOneAlreadyExistAndIsNotEmpty() throws IOException {
    assertTrue(objectUnderTest.generate(passwordPath));
    byte[] expected = Files.readAllBytes(passwordPath);
    assertFalse(objectUnderTest.generate(passwordPath));
    byte[] token = Files.readAllBytes(passwordPath);
    assertArrayEquals("Existing password file was overwritten", expected, token);
  }

  @Test
  public void shouldGenerateDifferentContentForDifferentSites() throws IOException {
    assertTrue(objectUnderTest.generate(passwordPath));
    byte[] siteA = Files.readAllBytes(passwordPath);

    assertTrue(passwordPath.toFile().delete());
    assertTrue(objectUnderTest.generate(passwordPath));
    byte[] siteB = Files.readAllBytes(passwordPath);
    assertFalse(
        "The same password was generated for two different sites", Arrays.equals(siteA, siteB));
  }

  @Test
  public void shouldThrowIllegalStateExceptionWhenDefaultKeyIsDirectory() throws IOException {
    // create dir from passwordPath
    assertTrue(passwordPath.toFile().mkdir());

    IllegalStateException illegalStateException =
        assertThrows(IllegalStateException.class, () -> objectUnderTest.generate(passwordPath));

    assertTrue(
        illegalStateException
            .getMessage()
            .endsWith("is directory whilst a regular file was expected."));
  }
}
