# Coding Conventions

- Always use modern Java switches with arrow-style `case ... ->` labels. Use switch expressions when producing a value; do not introduce traditional colon-style switch statements.
- Prefer a single return at the end of a method rather than scattered early returns throughout the method. Follow established patterns where they demonstrate otherwise.
- Use full, self-describing variable and parameter names. Preserve entity names, such as `periodKind` and `periodDescription`, rather than abbreviating them to `kind`, `description`, unless existing patterns abbreviate using a consistent name such as `entity`.
