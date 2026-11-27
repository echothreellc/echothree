# Coding Conventions

- Always use modern Java switches with arrow-style `case ... ->` labels. Use switch expressions when producing a value; do not introduce traditional colon-style switch statements.
- Prefer a single return at the end of a method rather than scattered early returns throughout the method. Follow established patterns where they demonstrate otherwise.
- Use full, self-describing variable and parameter names. Preserve entity names, such as `periodKind` and `periodDescription`, rather than abbreviating them to `kind`, `description`, unless existing patterns abbreviate using a consistent name such as `entity`.

# GitLab Workflow

- Complete the requested steps without redundant confirmation; required tool approvals still apply. Do not merge or close work items unless requested.
- Leave changes uncommitted until the user explicitly requests a commit. Push only when the user explicitly requests a push.
- Prefix shell commands with `rtk` and prefer `glab` over browser automation. For API calls, specify `--hostname gitlab.echothree.com`; this project is `echothree/echothree`.
- Reuse known context and existing work items, branches, and merge requests. Match similar past items for titles, classification labels, and commit style (`#<issue>: <description>`).
- Use title case for GitLab work item titles. Preserve standard acronyms such as `XML`.
- The work item workflow is Open → To Do → Doing → Done.
- Record each workflow transition separately using `Workflow::To Do`, `Workflow::Doing`, and `Workflow::Done`. These scoped labels replace one another.
- Create work items in Open, unassigned, and without an iteration. Set an iteration only when requested.
- When picking up an Open work item, move it to To Do first, then to Doing. Create a related draft merge request when the item reaches Doing. Use the default issue branch name and target branch, and let GitLab generate the issue reference to avoid a duplicate closing reference. Assign the item to the authenticated user when requested.
- Preserve unrelated changes. Pull with `--ff-only` before switching to the issue branch, run appropriate checks (`rtk ant compile` for Java/dependency changes and `rtk git diff --check`), and stage only intended files.
- Keep remote calls focused and outputs small. Batch independent reads, use mutation responses for verification, and avoid repeated lookups. After a timeout or interruption, check whether a write succeeded before retrying it.
- CLI details: set assignees with `-F 'assignee_ids=[<user-id>]'`; assign iterations with GraphQL `issueSetIteration`.
