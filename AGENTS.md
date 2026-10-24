# Coding Conventions

- Always use modern Java switches with arrow-style `case ... ->` labels. Use switch expressions when producing a value; do not introduce traditional colon-style switch statements.
- Prefer a single return at the end of a method rather than scattered early returns throughout the method. Follow established patterns where they demonstrate otherwise.
- Use full, self-describing variable and parameter names. Preserve entity names, such as `periodKind` and `periodDescription`, rather than abbreviating them to `kind`, `description`, unless existing patterns abbreviate using a consistent name such as `entity`.
- In Logic classes, accumulator parameters are an exception to the full-name rule: use `eea` for `ExecutionErrorAccumulator`, `ewa` for `ExecutionWarningAccumulator`, and `sma` for `SecurityMessageAccumulator`.
- In Logic classes, report execution errors through `handleExecutionError` with the appropriate exception class rather than adding errors directly to the accumulator. This must support both accumulating errors when `eea` is supplied and throwing the matching exception when it is absent.
