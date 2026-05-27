load(
    "@com_googlesource_gerrit_bazlets//:gerrit_plugin.bzl",
    "gerrit_plugin",
    "gerrit_plugin_dependency_tests",
    "gerrit_plugin_tests",
)

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
        "@github_plugin_deps//:com_sun_mail_javax_mail",
        "@github_plugin_deps//:commons_discovery_commons_discovery",
        "@github_plugin_deps//:javax_activation_activation",
        "@github_plugin_deps//:org_apache_axis_axis",
        "@github_plugin_deps//:org_apache_axis_axis_jaxrpc",
        "@github_plugin_deps//:org_apache_velocity_velocity_engine_core",
        "@github_plugin_deps//:org_eclipse_mylyn_github_org_eclipse_egit_github_core",
        "@github_plugin_deps//:org_kohsuke_github_api",
    ],
)

gerrit_plugin_tests(
    name = "github-plugin_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    tags = ["github"],
    deps = [
        ":github-plugin__plugin",
        "//javatests/com/google/gerrit/util/http/testutil",
        "//plugins/github/github-oauth:github-oauth-lib",
        "//plugins/replication:replication-api",
        "@github_plugin_deps//:org_kohsuke_github_api",
    ],
)

java_library(
    name = "replication-api",
    neverlink = True,
    exports = ["//plugins/replication:replication-api"],
)

gerrit_plugin_dependency_tests(plugin = "github-plugin")
