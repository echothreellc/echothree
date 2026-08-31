### Command Conventions

- For Command classes that Get specific types of entities:
  - For Command classes getting multiple entities extend BasePaginatedMultipleEntitiesCommand
  - For Command classes getting a single entity extend BaseSingleEntityCommand
  - getUserVisit should only be called once and then reused
- For Command classes that Edit specific types of entities:
  - Extend BaseAbstractEditCommand
  - Use `editModeToEntityPermission(editMode)` with a permission-aware Logic or Control lookup instead of branching between read-only and for-update lookup methods.
  - For description edits, use the description as the edited entity and its owning entity as the lock entity.
- Prefer Logic-class `ByName` methods when available. After a Logic lookup, guard dependent work with `!hasExecutionErrors()`; after a direct Control lookup, test the returned entity for `null`.
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
