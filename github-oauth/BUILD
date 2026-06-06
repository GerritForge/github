load(
    "@com_googlesource_gerrit_bazlets//:gerrit_plugin.bzl",
    "gerrit_api_neverlink",
    "gerrit_plugin_dependency_tests",
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
<<<<<<< PATCH SET (6114127e1f8af849ba8a43e60aa3a27dbdf06053 Wire dependency_tests for the github-oauth library)

# bridge-method-injector is compile-only: javac needs BridgeMethodsAdded
# to resolve inherited-method signatures on github-api types.
# It has CLASS retention, so the JVM never loads it at runtime.
java_library(
    name = "bridge-method-injector-neverlink",
    neverlink = 1,
    exports = [artifact(
        "com.infradna.tool:bridge-method-injector",
        repository_name = EXT_REPO,
    )],
)

# github-oauth-lib is a java_library (not a gerrit_plugin); pass an explicit
# `target` so the same bazlets macro can wire allowlist + overlap tests.
gerrit_plugin_dependency_tests(
    plugin = "github-oauth",
    target = ":github-oauth-lib",
)
=======
>>>>>>> BASE      (9dd3d43a27a183de00deb2f7058695dad1e372c1 Align tsconfig with ts_project outputs)
