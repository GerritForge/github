load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load(
    "@com_googlesource_gerrit_bazlets//:gerrit_plugin.bzl",
    "gerrit_plugin_tests",
)

java_binary(
    name = "github-oauth",
    main_class = "Dummy",
    visibility = ["//visibility:public"],
    runtime_deps = [":github-oauth-lib"],
)

java_library(
    name = "github-oauth-lib",
    srcs = glob(["src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//plugins:plugin-lib-neverlink",
        "//lib:servlet-api",
        "@github_plugin_deps//:com_fasterxml_jackson_core_jackson_annotations",
        "@github_plugin_deps//:com_fasterxml_jackson_core_jackson_core",
        "@github_plugin_deps//:com_fasterxml_jackson_core_jackson_databind",
        "@github_plugin_deps//:com_infradna_tool_bridge_method_annotation",
        "@github_plugin_deps//:com_infradna_tool_bridge_method_injector",
        "@github_plugin_deps//:org_kohsuke_github_api",
    ],
)

gerrit_plugin_tests(
    name = "github-oauth_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    tags = ["github"],
    deps = [
        ":github-oauth-lib",
    ],
)
