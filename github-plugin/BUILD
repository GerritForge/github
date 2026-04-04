load("//tools/bzl:junit.bzl", "junit_tests")
load("//tools/bzl:plugin.bzl", "PLUGIN_DEPS", "PLUGIN_DEPS_NEVERLINK", "PLUGIN_TEST_DEPS", "gerrit_plugin")

gerrit_plugin(
    name = "github-plugin",
    srcs = glob(["src/main/java/**/*.java"]),
    dir_name = "github",
    manifest_entries = [
        "Gerrit-PluginName: github-plugin",
        "Gerrit-Module: com.gerritforge.gerrit.plugins.github.GuiceModule",
        "Gerrit-HttpModule: com.gerritforge.gerrit.plugins.github.GuiceHttpModule",
        "Gerrit-InitStep: com.gerritforge.gerrit.plugins.github.InitGitHub",
        "Implementation-Title: GitHub plugin",
        "Implementation-Vendor: GerritForge",
        "Implementation-URL: http://www.gerritforge.com",
    ],
    resource_jars = ["//plugins/github/github-plugin/web:github-plugin"],
    resources = glob(["src/main/resources/**/*"]),
    deps = [
        ":replication-api",
        "//plugins/github/github-oauth:github-oauth-lib",
        "@axis-jaxrpc//jar",
        "@axis//jar",
        "@com-sun-mail//jar",
        "@commons-discovery//jar",
        "@eclipse-mylyn-github//jar",
        "@github-api//jar",
        "@javax-activation//jar",
        "@velocity//jar",
    ],
)

junit_tests(
    name = "github-plugin_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    tags = ["github"],
    deps = PLUGIN_DEPS + PLUGIN_TEST_DEPS + [
        ":github-plugin__plugin",
        "//javatests/com/google/gerrit/util/http/testutil",
        "//plugins/github/github-oauth:github-oauth-lib",
        "//plugins/replication:replication-api",
        "@github-api//jar",
    ],
)

java_library(
    name = "replication-api",
    neverlink = True,
    exports = ["//plugins/replication:replication-api"],
)
