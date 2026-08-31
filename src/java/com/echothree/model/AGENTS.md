### jOOQ Query Conventions

- Use generated jOOQ tables, fields, and foreign keys for SQL queries in Control classes
- Prefer jOOQ for Control-layer queries
- For read-only joins to versioned entities, join through `LAST_DETAIL`, not `ACTIVE_DETAIL`
- Keep read-write queries minimal: select only the entity being locked with `FOR UPDATE`
- A list query filtered by one foreign key should join and order by the remaining foreign keys that uniquely identify the entity. Use each related entity's semantic sort order and name rather than defaulting to the entity's primary key.
- Format query construction as a vertical chain, with each query clause on its own line, including `.select(...)`, `.from(...)`, `.where(...)`, `.orderBy(...)`, and `.forUpdate()`
- Keep `.onKey(...)` and `.on(...)` on the same line as its corresponding `.join(...)`
- Separate `baseQuery`, `query` or `sql`, and factory `return` statements with a blank line
- Use the `EntityPermission` branch to add `.forUpdate()` for `READ_WRITE` queries
- Apply client pagination to read-only entity-list queries using `session.applyLimit(query, EntityFactory.class)`
- Pass jOOQ queries directly through the generated entity factory using `getEntityFromQuery(entityPermission, query)` or `getEntitiesFromQuery(entityPermission, query)`
- Use `selectCount()`, `fetchOptional(0, Long.class)`, and `orElse(0L)` for count queries

### Control and Logic Conventions

- In Control classes, place generated Factory injections immediately after the comment header for the entity type where they are used.
  - Keep Factory injections alphabetically ordered within each entity section.
- Controls perform persistence operations; Logic classes enforce business rules
- Expose Control lookup overloads that accept `EntityPermission` when Commands need to select read-only or read-write behavior via `editModeToEntityPermission(editMode)`; keep convenience read-only and for-update methods delegating to that overload
- Follow entity-specific event ownership requirements
  - For `PartyBucket`, send `TOUCH` events against the associated `Item`; do not make `PartyBucket` the primary event entity

### GraphQL Conventions

- GraphQL query parameters must match the corresponding Command's complete parameter set
- Apply `@GraphQLNonNull` exactly when the Command field definition marks the parameter as required
