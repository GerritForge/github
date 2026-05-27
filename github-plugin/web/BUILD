load("@aspect_rules_ts//ts:defs.bzl", "ts_config", "ts_project")
load("@com_googlesource_gerrit_bazlets//js:defs.bzl", "gerrit_js_bundle")
load("//tools/js:eslint.bzl", "plugin_eslint")

package_group(
    name = "visibility",
    packages = ["//plugins/github/github-plugin/..."],
)

package(default_visibility = [":visibility"])

ts_config(
    name = "tsconfig",
    src = "tsconfig.json",
    deps = [
        "//plugins:tsconfig-plugins-base",
    ],
)

ts_project(
    name = "github-plugin-ts",
    srcs = glob(
        ["**/*.ts"],
    ),
    incremental = True,
    out_dir = "_bazel_ts_out",
    tsconfig = ":tsconfig",
    deps = [
        "//plugins:node_modules",
    ],
)

gerrit_js_bundle(
    name = "github-plugin",
    srcs = [":github-plugin-ts"],
    entry_point = "_bazel_ts_out/main.js",
)

plugin_eslint()
