load(
    "@com_googlesource_gerrit_bazlets//:gerrit_plugin.bzl",
    "gerrit_api_neverlink",
    "gerrit_plugin_tests",
)
load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@rules_jvm_external//:defs.bzl", "artifact")

EXT_REPO = "github-plugin_plugin_deps"

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
        artifact(
            c,
            repository_name = EXT_REPO,
        )
        for c in [
            "com.infradna.tool:bridge-method-injector",
            "org.kohsuke:github-api",
        ]
    ] + [
        "//lib:servlet-api",
    ] + gerrit_api_neverlink(""),
)

gerrit_plugin_tests(
    name = "github-oauth_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    tags = ["github"],
    deps = [":github-oauth-lib"],
)
