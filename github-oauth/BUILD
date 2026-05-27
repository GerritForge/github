load(
    "@com_googlesource_gerrit_bazlets//:gerrit_plugin.bzl",
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
        ":bridge-method-injector-neverlink",
        artifact(
            "org.kohsuke:github-api",
            repository_name = EXT_REPO,
        ),
        "//lib:servlet-api",
        "//plugins:plugin-lib-neverlink",
    ],
)

gerrit_plugin_tests(
    name = "github-oauth_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    tags = ["github"],
    deps = [":github-oauth-lib"],
)

# bridge-method-injector is compile-only: javac needs BridgeMethodsAdded to
# resolve inherited-method signatures on github-api types (it has CLASS
# retention, so the JVM never loads it at runtime).
ava_library(
    name = "bridge-method-injector-neverlink",
    neverlink = 1,
    exports = [artifact(
        "com.infradna.tool:bridge-method-injector",
        repository_name = EXT_REPO,
    )],
)
