# Contributing

## Before contributing code

Contributions require that you sign the [Contributor Copyright Assignment Agreement](https://www.gerritforge.com/20260305.GerritForge.BSL.Contributors.Agreement.pdf) electronically and send it to [GerritForge's CLA Office](cla@gerritforge.com).

You can either send a handwritten signature of the agreement or
[request a digital signature](mailto:cla@gerritforge.com?subject=I%20need%20to20sign20the20Contributor20Copyright20Assignment20Agreement20digitally).

### Open an issue for any new problem

Excluding very trivial changes, all contributions should be connected to an
existing issue on the [Gerrit Code Review issue tracker](https://issues.gerritcodereview.com).
Feel free to open one and discuss your plans.  This process
gives everyone a chance to validate the design, helps prevent duplication of
effort, and ensures that the idea fits inside the goals for the language and
tools.  It also checks that the design is sound before code is written; the code
review tool is not the place for high-level discussions.

Sensitive security-related issues should follow the [Gerrit Code Review Security Issues protocol](https://gerrit-documentation.storage.googleapis.com/Documentation/3.13.3/dev-processes.html#security-issues).

## Becoming a code contributor

The code contribution process is a little different from
that used by other open source projects.  We assume you have a basic
understanding of [`git`](https://git-scm.com/) and [Gerrit](https://gerritcodereview.com).

The first thing to decide is whether you want to contribute a code change via
[GitHub](https://github.com) or [GerritHub](https://review.gerrithub.io).
Both workflows are fully supported, and whilst GerritHub is
used by the core project maintainers as the "source of truth", the GitHub Pull
Request workflow is 100% supported - contributors should feel entirely
comfortable contributing this way if they prefer.

Contributions via either workflow must be accompanied by a Developer Certificate
of Origin.

### Asserting a Developer Certificate of Origin

All commit messages must contain the `Signed-off-by` line with an email address
that matches the commit author and the [Contributor Copyright Assignment Agreement](https://www.gerritforge.com/20260305.GerritForge.BSL.Contributors.Agreement.pdf). This line asserts the Developer Certificate of Origin.

When committing, use the `--signoff` (or `-s`) flag:

```console
$ git commit -s
```

The explanations of the GitHub and GerritHub contribution workflows that follow
assume all commits you create are signed-off in this way.

## Contributing using GerritHub's [CL](https://google.github.io/eng-practices/#terminology) Contributions

GerritForge maintainers use GerritHub for code review. It has a powerful review
interface with comments that are attributed to patchsets (versions of a change).
Orienting changes around a single commit allows for "stacked" changes, and also
encourages unrelated changes to be broken into separate CLs because the process
of creating and linking CLs is so easy.

For those more comfortable with contributing via GitHub PRs, please continue to
do so: this project supports both workflows so that people have a choice.

For those who would like to contribute via GerritHub, read on!

### Overview

The first step in the GerritHub flow is registering as a contributor and
configuring your environment. Here is a checklist of the required steps to
follow:

- **Step 0**: Review the guidelines on [Good Commit Messages](#good-commit-messages), [The Review Process](#the-review-process) and [Miscellaneous Topics](#miscellaneous-topics)
- **Step 1**: Set the email address that you have entered in the [Contributor Copyright Assignment Agreement](https://www.gerritforge.com/20260305.GerritForge.BSL.Contributors.Agreement.pdf) for your contributions.
- **Step 2**: Set up a [GerritHub](http://review.gerrithub.io/) account.
- **Step 3**: Clone this repository locally.

We cover steps 1-3 in more detail below.

### Step 1: Decide which email address you want to use for contributions

A contribution is made through a specific e-mail address.  Make sure to
use the same account throughout the process and for all your subsequent
contributions.  You may need to decide whether to use a personal address or a
corporate address.  The choice will depend on who will own the copyright for the
code that you will be writing and submitting.  You might want to discuss this
topic with your employer before deciding which account to use.

> **NOTE**: Ensure that the e-mail address chosen for the contribution matches
> the one entered in the
> [Contributor Copyright Assignment Agreement](https://www.gerritforge.com/20260305.GerritForge.BSL.Contributors.Agreement.pdf)
> signed with GerritForge.

You also need to make sure that your `git` tool is configured to create commits
using your chosen e-mail address.  You can either configure Git globally (as a
default for all projects), or locally (for a single specific project).  You can
check the current configuration with this command:

```console
$ git config --global user.email  # check current global config
$ git config user.email           # check current local config
```

To change the configured address:

```console
$ git config --global user.email name@example.com   # change global config
$ git config user.email name@example.com            # change local config
```

### Step 2: Setup a GerritHub account

If you have not used GerritHub before, setting up an account is a simple
process:

- Visit [GerritHub](http://gerrithub.io/).
- Click "First Time Sign In".
- Click the green "Sign In" button, to sign in using your GitHub credentials.
- When prompted "Which level of GitHub access do you need?",
  choose "Default" and then click "Login".
- Click "Authorize gerritforge-ltd" on the GitHub auth page.
- Confirm account profile details and click "Next".

It preferable to use SSH for authentication *to GerritHub*. Your existing GitHub
SSH keys can be imported using the
[GerritHub's account import page](https://review.gerrithub.io/plugins/github-plugin/static/account.html).

SSH keys can be also be [configured in your GerritHub user profile](https://review.gerrithub.io/settings/#SSHKeys).

### Step 3: Clone this repository locally

Visit `https://review.gerrithub.io/admin/repos/GerritForge/<<REPOSITORY>>`,
where `<<REPOSITORY>>` is the repository name of this project, click on "SSH"
and copy the sample command under the _Clone with commit-msg hook_ title.

Paste the command on your local workspace and the repository will be cloned
with the necessary `commit-msg` hook that Gerrit Code Review needs for generating
a `Change-Id` for all CLs.

## Sending a change via GerritHub

Sending a change via GerritHub is quite different to the GitHub PR flow. At
first the differences might be jarring, but with practice the workflow is
incredibly intuitive and far more powerful when it comes to chains of "stacked"
changes.

### Step 1: Prepare changes in a new branch

Each change must be made in a branch, created from the `master` branch.  You
can use the normal `git` commands to create a branch and stage changes:

```console
$ git checkout -b mybranch
$ [edit files...]
$ git add [files...]
```

To commit changes, use `git commit -s`:

```console
$ git commit -s
(opens $EDITOR)
```

You can edit the commit description in your favorite editor as usual. The Change-Id
line near the bottom is automatically added by the `commit-msg` hook.
That line is used by Gerrit to match successive uploads
of the same change.  Do not edit or delete it.  A Change-Id looks like this:

```
Change-Id: I2fbdbffb3aab626c4b6f56348861b7909e3e8990
```

If you need to edit the files again, you can stage the new changes and re-run
`git commit --amend --no-edit`: each subsequent run will amend the existing commit
while preserving the Change-Id.

Make sure that you always keep a single commit in each branch.  If you add more
commits by mistake, you can use `git rebase` to [squash them
together](https://medium.com/@slamflipstrom/a-beginners-guide-to-squashing-commits-with-git-rebase-8185cf6e62ec)
into a single one.

### Step 2: Test your changes

You've written and tested your code, but before sending code out for review, run
all the tests for the whole tree to ensure the changes don't break other
packages or programs.

### Step 3: Send changes for review

Once the change is ready and tested over the whole tree, send it for review.
This is done with the following command:

```console
$ git push origin HEAD:refs/for/master
```

Gerrit assigns your change a number and URL, like:

```
remote: New Changes:
remote:   https://review.gerrithub.io/99999 math: improved Sin, Cos and Tan precision for very large arguments
```

### Step 4: Revise changes after a review

GerritForge's maintainers will review your code on Gerrit, and you will get notifications
via e-mail.  You can see the review on Gerrit and comment on them there.  You
can also reply [using e-mail](https://gerrit-review.googlesource.com/Documentation/intro-user.html#reply-by-email)
if you prefer.

If you need to revise your change after the review, edit the files in the same
branch you previously created, add them to the Git staging area, and then amend
the commit with `git commit --amend`:

```console
$ git commit --amend  # amend current commit (without -s because we already signed-off, above)
(open $EDITOR)
$ git push origin HEAD:refs/for/master    # send new changes to Gerrit
```

If you don't need to change the commit description, just save and exit from the
editor.  Remember not to touch the special `Change-Id` line.

Again, make sure that you always keep a single commit in each branch.  If you
add more commits by mistake, you can use `git rebase` to [squash them
together](https://medium.com/@slamflipstrom/a-beginners-guide-to-squashing-commits-with-git-rebase-8185cf6e62ec)
into a single one.

### CL approved!

## Preparing for GitHub Pull Request (PR) Contributions

First-time contributors that are already familiar with the <a
href="https://docs.github.com/get-started/quickstart/github-flow">GitHub flow</a> are
encouraged to use the same process.  Even though GerritForge's
maintainers use GerritHub for code review, the GitHub PR workflow is 100%
supported.

Here is a checklist of the steps to follow when contributing via GitHub PR
workflow:

- **Step 0**: Review the guidelines on [Good Commit Messages](#good-commit-messages),
  [The Review Process](#the-review-process) and [Miscellaneous Topics](#miscellaneous-topics)
- **Step 1**: Create a GitHub account if you do not have one.
- **Step 2**: [Fork](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/fork-a-repo)
  the Gerrit project, and clone your fork locally

That's it! You are now ready to send a change via GitHub, the subject of the
next section.

## Sending a change via GitHub

The GitHub documentation around [working with
forks](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/getting-started/about-collaborative-development-models)
is extensive so we will not cover that ground here.

Before making any changes it's a good idea to verify that you have a stable
baseline by running the tests:

```console
$ go test ./...
```

Then make your planned changes and create a commit from the staged changes:

```console
# Edit files
$ git add file1 file2
$ git commit -s
```

Notice as we explained above, the `-s` flag asserts the Developer Certificate of
Origin by adding a `Signed-off-by` line to a commit. When writing a commit
message, remember the guidelines on [good commit messages](#good-commit-messages).

You've written and tested your code, but before sending code out for review, run
all the tests from the root of the repository to ensure the changes don't break
other packages or programs.

Your change is now ready!
[Submit a PR](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request).

> **NOTE**: Once the initial PR is ready, the GerritForge's Team will import the
> PR into GerritHub and the review process will continue in Gerrit.


## Good commit messages

Commit messages follow a specific set of conventions and rationale, described
in the [Gerrit Code Review contributor's guide](https://gerrit-documentation.storage.googleapis.com/Documentation/3.13.3/dev-crafting-changes.html#commit-message).

Here is an example of a good one:

```
DeleteTask: Do not return 500 via Response.withStatusCode

REST endpoints should throw an exception to indicate an internal server
error. RestApiServlet takes care to catch the exception and to return a
'500 Internal Server Error' response for it.

Returning 500 via Response.withStatusCode also works but messes up the
error metric. If no exception is thrown, then we can't log an exception
as the cause in the error metric and we fall back to using "_none" as
the cause (hard-coded in RestApiServlet). "_none" is not helpful, hence
fix the metric by throwing an exception, rather than returning a 500
response code with Response.withStatusCode.

Change-Id: Ib3919bb9927232720c3d1c753b8938f45ffdc9df
Signed-off-by: Edwin Kempin <ekempin@google.com>
```

## The review process

This section explains the review process in detail and how to approach reviews
after a change has been sent to either GerritHub or GitHub.

### Common mistakes

When a change is sent to Gerrit, it is usually triaged within a few days.  A
maintainer will have a look and provide some initial review that for first-time
contributors usually focuses on basic cosmetics and common mistakes.  These
include things like:

- Commit message not following the suggested format.
- The lack of a linked Gerrit Code Review issue.  The vast majority of changes require a
  linked issue that describes the bug or the feature that the change fixes or
  implements, and consensus should have been reached on the tracker before
  proceeding with it.  Gerrit reviews do not discuss the merit of the change, just
  its implementation.  Only trivial or cosmetic changes will be accepted without
  an associated issue.

### Continuous Integration (CI) checks

Your change may trigger CI checks, that run a full test suite with the target
version of Gerrit Code Review.

Most CI tests complete in a few minutes, at which point a link will be
posted in Gerrit where you can see the results.

If any of the CI checks fail, follow the link and check the full logs.  Try to
understand what broke, update your change to fix it, and upload again.

### Reviews

The Gerrit community values very thorough reviews.  Think of each review comment
like a ticket: you are expected to somehow "close" it by acting on it, either by
implementing the suggestion or convincing the reviewer otherwise.

After you update the change, go through the review comments and make sure to
reply to every one.  In GerritHub you can click the "Done" button to reply
indicating that you've implemented the reviewer's suggestion and in GitHub you
can mark a comment as resolved; otherwise, click on "Reply" and explain why you
have not, or what you have done instead.

It is perfectly normal for changes to go through several round of reviews, with
one or more reviewers making new comments every time and then waiting for an
updated change before reviewing again.  This cycle happens even for experienced
contributors, so don't be discouraged by it.

### Voting conventions in GerritHub

As they near a decision, reviewers will make a "vote" on your change.
The Gerrit voting system involves an integer in the range -2 to +2:

- **+2** The change is approved for being merged.  Only GerritForge's maintainers can cast
  a +2 vote.
- **+1** The change looks good, but either the reviewer is requesting minor
  changes before approving it, or they are not a maintainer and cannot approve
it, but would like to encourage an approval.
- **-1** The change is not good the way it is but might be fixable.  A -1 vote
  will always have a comment explaining why the change is unacceptable.
- **-2** The change is blocked by a maintainer and cannot be approved.  Again,
  there will be a comment explaining the decision.

### Reviewing PRs in GitHub

GitHub PRs are used solely as _source_ for creating a Gerrit change.
There is no review or approval happening on GitHub. All the review activity
happens on GerritHub.

### Submitting an approved change

After the code has been `+2`'ed in GerritHub, an
approver will apply it to the target branch using the Gerrit user interface.
This is called "submitting the change".

The two steps (approving and submitting) are separate because in some cases
maintainers may want to approve it but not to submit it right away (for
instance, the tree could be temporarily frozen).

Submitting a change checks it into the repository.  The change description will
include a link to the code review, which will be updated with a link to the
change in the repository.  Since the method used to integrate the changes is
Git's "Cherry Pick", the commit hashes in the repository will be changed by the
submit operation.

If your change has been approved for a few days without being submitted, feel
free to write a comment in GerritHub requesting submission.

## Miscellaneous topics

This section collects a number of other comments that are outside the
issue/edit/code review/submit process itself.

### AI-assisted development

Contributors can optionally use [LLMs](https://en.wikipedia.org/wiki/Large_language_model)
for AI-assisted development using popular AI coding assistants like
Claude Code, GitHub Copilot, Gemini, and others.

> **NOTE**: The use of AI must be compliant with the Section 7 of the
> [Contributor Copyright Assignment Agreement](https://www.gerritforge.com/20260305.GerritForge.BSL.Contributors.Agreement.pdf).

### Copyright headers

Files in this repository don't list author names, both to avoid clutter and
to avoid having to keep the lists up to date.

New files that you contribute should use the standard copyright header
with the current year reflecting when they were added.
Do not update the copyright year for existing files that you change.

```
// Copyright (C) 2026 GerritForge, Inc.
//
// Licensed under the BSL 1.1 (the "License");
// you may not use this file except in compliance with the License.
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
```

## Code of Conduct

Guidelines for participating in Gerrit community spaces and a reporting process for
handling issues can be found in the [Gerrit Code Review Code of Conduct](https://www.gerritcodereview.com/codeofconduct.html).