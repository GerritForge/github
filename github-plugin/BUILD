load(
    "@com_googlesource_gerrit_bazlets//:gerrit_plugin.bzl",
    "gerrit_plugin",
    "gerrit_plugin_tests",
)
load("@rules_java//java:defs.bzl", "java_library")

PLUGIN = "github-plugin"

EXT_DEPS = [
    "com.sun.mail:javax.mail",
    "commons-discovery:commons-discovery",
    "javax.activation:activation",
    "org.apache.axis:axis",
    "org.apache.axis:axis-jaxrpc",
    "org.apache.velocity:velocity-engine-core",
    "org.eclipse.mylyn.github:org.eclipse.egit.github.core",
    "org.kohsuke:github-api",
]

gerrit_plugin(
    name = PLUGIN,
    srcs = glob(["src/main/java/**/*.java"]),
    dir_name = "github",
    ext_deps = EXT_DEPS,
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
    ],
)

gerrit_plugin_tests(
    srcs = glob(["src/test/java/**/*.java"]),
    ext_deps = ["org.kohsuke:github-api"],
    plugin = PLUGIN,
    deps = [
        "//javatests/com/google/gerrit/util/http/testutil",
        "//plugins/github/github-oauth:github-oauth-lib",
        "//plugins/replication:replication-api",
    ],
)

java_library(
    name = "replication-api",
    neverlink = True,
    exports = ["//plugins/replication:replication-api"],
)
