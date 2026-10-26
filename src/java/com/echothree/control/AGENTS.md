### Command Conventions

- For Command classes that Get specific types of entities:
  - For Command classes getting multiple entities extend BasePaginatedMultipleEntitiesCommand
  - For Command classes getting a single entity extend BaseSingleEntityCommand
  - getUserVisit should only be called once and then reused
- For Command classes that Edit specific types of entities:
  - Extend BaseAbstractEditCommand
  - Use `editModeToEntityPermission(editMode)` with a permission-aware Logic or Control lookup instead of branching between read-only and for-update lookup methods.
  - For description edits, use the description as the edited entity and its owning entity as the lock entity.
- Prefer Logic-class `ByName` methods when available. Group independent `ByName` lookups together, with one `!hasExecutionErrors()` check before dependent work. After a direct Control lookup, test the returned entity for `null`.
- Create-result population is an exception to the execution-error guard: results may be populated whenever the returned entity is non-null, even if there are execution errors. Guard result population with an entity null check rather than `!hasExecutionErrors()`.
- Unknown-entity execution errors must include all names in the entity's complete unique key, ordered from parent names to the entity name. Unknown-description errors must additionally include `LanguageIsoName` last.
- Format all static `CommandSecurityDefinition` and `List<FieldDefinition>` assignments consistently:
  - Indent nested list entries one additional level.
  - Align closing parentheses with their assignment expression.
- Paginated multiple-entity Commands should support each indexed foreign-key retrieval path exposed by the Control
- A single-entity Command must accept the names required to resolve the complete unique key
- Do not request redundant names. For example, an `Item` supplies its `UnitOfMeasureKind`, so a single `PartyBucket` lookup requires only `UnitOfMeasureTypeName`.
- When a Command permits alternative identifiers, such as `PartyName`, `CompanyName`, or `WarehouseName`, expose all alternatives in GraphQL and leave them nullable so Command validation can enforce valid combinations

### Forms, Results, Specs, and Edits Generation

- Forms, Results, Specs, and Edits are defined as interfaces in `...common.form`, `...common.result`, `...common.spec`, and `...common.edit` packages
- Concrete implementations and factories are automatically generated during the build process (`ant compile`)
- Generated code is located in the `generated/` directory
- Use the generated factories via Beans or Services to instantiate these objects (e.g., `ContactBean.getGetContactMechanismsForm()`, `ContactService.getGetContactMechanismsResult()`)
- Always run `ant compile` after adding or modifying these interfaces to ensure the factories are up to date

### GraphQL Conventions

- GraphQL query parameters must match the corresponding Command's complete parameter set except `EntityRef`, which must never be exposed as a GraphQL query argument or mutation input field. Map `Uuid` to `id` with `@GraphQLID`.
- Apply `@GraphQLNonNull` when the Command field definition marks the parameter as required.
- Exception for alternative identifiers: when a Command requires an identifier but permits alternatives, and GraphQL exposes only one of those alternatives, mark the remaining GraphQL identifier `@GraphQLNonNull` even if its individual Command field is optional. For example, `sourceId` and `targetId` are required for `link`, `createLink`, and `deleteLink` because GraphQL omits their EntityRef alternatives. Identifiers used as optional list filters remain nullable.
