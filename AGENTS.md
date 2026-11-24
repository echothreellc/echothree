# Coding Conventions

- Always use modern Java switches with arrow-style `case ... ->` labels. Use switch expressions when producing a value; do not introduce traditional colon-style switch statements.
- Prefer a single return at the end of a method rather than scattered early returns throughout the method. Follow established patterns where they demonstrate otherwise.
- Use full, self-describing variable and parameter names. Preserve entity names, such as `periodKind` and `periodDescription`, rather than abbreviating them to `kind`, `description`, unless existing patterns abbreviate using a consistent name such as `entity`.

# GitLab Workflow

- Complete the requested steps without redundant confirmation; required tool approvals still apply. Do not merge or close work items unless requested.
- Prefix shell commands with `rtk` and prefer `glab` over browser automation. For API calls, specify `--hostname gitlab.echothree.com`; this project is `echothree/echothree`.
- Reuse known context and existing work items, branches, and merge requests. Match similar past items for titles, classification labels, and commit style (`#<issue>: <description>`).
- When requested, use the current iteration, assign to the authenticated user, and create a related draft merge request with the default issue branch name and target branch. Let GitLab generate the issue reference; do not add a duplicate closing reference.
- Record requested workflow transitions separately using `Workflow::To Do`, `Workflow::Doing`, and `Workflow::Done`. These scoped labels replace one another; do not copy a completed status onto a new item.
- Preserve unrelated changes. Pull with `--ff-only` before switching to the issue branch, run appropriate checks (`rtk ant compile` for Java/dependency changes and `rtk git diff --check`), and stage only intended files.
- Keep remote calls focused and outputs small. Batch independent reads, use mutation responses for verification, and avoid repeated lookups. After a timeout or interruption, check whether a write succeeded before retrying it.
- CLI details: set assignees with `-F 'assignee_ids=[<user-id>]'`; assign iterations with GraphQL `issueSetIteration`.
