# Contributing to Echo Three Projects

We're glad you want to contribute to Echo Three! This document will help answer common questions you may have
during your first contribution.

## Submitting Issues

Not every contribution comes in the form of code. Submitting, confirming, and triaging issues is an important
task for any project. At Echo Three we use GitLab to track all public issues.

If you are familiar with Echo Three and know the component that is causing you a problem, you can file an
issue in the corresponding GitLab project. All of our Open Source Software can be found in our
[Echo Three GitLab organization](https://gitlab.com/echothree/).

We ask you not to submit security concerns via GitLab. For details on submitting potential security issues
please see <https://www.echothree.com/security/>

## Supported IDEs

Using an IDE is not required, but sometimes they can make life easier. The Echo Three project is set up
with project files for two major IDEs:

* [NetBeans](https://netbeans.apache.org)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)

If you're adding or removing libraries from the project, it's important to make sure that references are
added and removed from the IDE projects as appropriate.

## Contribution Process

We have a 3 step process for contributions:

1. Commit changes to a git branch, making sure to sign-off those changes for the
[Developer Certificate of Origin](#developer-certification-of-origin-dco).
2. Create a GitLab merge request for your change, following the instructions in the merge request template.
3. Perform a [Code Review](#code-review-process) with the project maintainers on the pull request.

### Pull Request Requirements

We strive to ensure high quality throughout the experience. In order to ensure this, we require that all pull
requests to Echo Three meet these requirements:

1. **Tests:** To ensure high quality code and protect against future regressions, we require all the code in
Echo Three to have at least unit test coverage.
2. **CI Tests:** We use Jenkins as a CI system to test all pull requests. We require these test runs to succeed
on every pull request before being merged.

### Code Review Process

Code review takes place in GitLab merge requests. See
[this article](https://docs.gitlab.com/ee/gitlab-basics/add-merge-request.html)
if you're not familiar with GitLab merge requests.

Once you open a merge request, project maintainers will review your code and respond to your merge request with any
feedback they might have. The process at this point is as follows:

1. One thumbs-up (:+1:) is required from project maintainer, see the master maintainers document for Echo Three
projects at <MAINTAINERS.md>.
2. Your change will be merged into the project's `master` branch

### Developer Certification of Origin (DCO)

Licensing is very important to open source projects. It helps ensure the software continues to be available
under the terms that the author desired.

Echo Three uses [the Apache 2.0 license](LICENSE) to strike a balance
between open contribution and allowing you to use the software however you would like to.

The license tells you what rights you have that are provided by the copyright holder. It is important that the
contributor fully understands what rights they are licensing and agrees to them. Sometimes the copyright holder
isn't the contributor, such as when the contributor is doing work on behalf of a company.

To make a good faith effort to ensure these criteria are met, Echo Three requires the Developer Certificate of
Origin (DCO) process to be followed.

The DCO is an attestation attached to every contribution made by every developer. In the commit message of the
contribution, the developer simply adds a Signed-off-by statement and thereby agrees to the DCO, which you can
find below or at <http://developercertificate.org/>.

```
Developer's Certificate of Origin 1.1

By making a contribution to this project, I certify that:

(a) The contribution was created in whole or in part by me and I
    have the right to submit it under the open source license
    indicated in the file; or

(b) The contribution is based upon previous work that, to the
    best of my knowledge, is covered under an appropriate open
    source license and I have the right under that license to   
    submit that work with modifications, whether created in whole
    or in part by me, under the same open source license (unless
    I am permitted to submit under a different license), as
    Indicated in the file; or

(c) The contribution was provided directly to me by some other
    person who certified (a), (b) or (c) and I have not modified
    it.

(d) I understand and agree that this project and the contribution
    are public and that a record of the contribution (including
    all personal information I submit with it, including my
    sign-off) is maintained indefinitely and may be redistributed
    consistent with this project or the open source license(s)
    involved.
```

#### DCO Sign-Off Methods

The DCO requires a sign-off message in the following format appear on each commit in the pull request:

```
Signed-off-by: Luke Skywalker <lukeskywalker@echothree.com>
```

The DCO text can either be manually added to your commit body, or you can add either **-s** or **--signoff** to your
usual git commit commands. If you forget to add the sign-off you can also amend a previous commit with the sign-off by
running **git commit --amend -s**. If you've pushed your changes to GitLab already you'll need to force push your
branch after this with **git push -f**.

### Obvious Fix Policy

Small contributions, such as fixing spelling errors, where the content is small enough to not be considered
intellectual property, can be submitted without signing the contribution for the DCO.

As a rule of thumb, changes are obvious fixes if they do not introduce any new functionality or creative thinking.
Assuming the change does not affect functionality, some common obvious fix examples include the following:

- Spelling / grammar fixes
- Typo correction, white space and formatting changes
- Comment clean up
- Bug fixes that change default return values or error codes stored in constants
- Adding logging messages or debugging output
- Changes to 'metadata' files like .gitignore, build scripts, etc.
- Moving source files from one directory or package to another

**Whenever you invoke the "obvious fix" rule, please say so in your commit message:**

```
------------------------------------------------------------------------
commit 370adb3f82d55d912b0cf9c1d1e99b132a8ed3b5
Author: Luke Skywalker <lukeskywalker@echothree.com>
Date:   Sat Oct 20 10:42:07 2018 -0500

  Fix typo in the README.

  Obvious fix.

------------------------------------------------------------------------
```

## Official Releases

There are no official releases at this time other than tagged milestones available through git.

## Echo Three Community

If you have any questions or if you would like to get involved in the Echo Three community you can check out:

- [reddit](https://www.reddit.com/r/echothree/)
- [GitLab](https://gitlab.com/echothree/echothree/)

Source Code Mirrors:

- [GitHub Mirror](https://github.com/echothreellc/echothree/)

## Acknowledgements

Thank you to Chef Software, Inc. for putting together a well thought out contribution guide that this document
is based on.
