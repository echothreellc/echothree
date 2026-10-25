# Cucumber Conventions

- Use Cucumber Expressions instead of regular expressions for step definitions. Do not add regex anchors such as `^` or `$`.
- Use `{string}` for string parameters and quote the corresponding values in feature files, including identifiers, execution-error names, and fixed choices such as `"be"` and `"not be"`.
- Preserve fixed-choice restrictions with explicit checks in the step body when converting alternatives to `{string}`.
