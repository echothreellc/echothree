### Coding Conventions

- Maintain required alphabetical and type-based ordering for top-level injected dependencies:
    - Generated Factory classes first in alphabetical order.
    - Control classes second in alphabetical order.
    - Logic classes third in alphabetical order.
    - Place the injection block after the static initializer and before any constructor Javadoc.
    - Keep constructor Javadocs immediately above their constructors.
    - Leave a blank line before the first `@Inject`, between injected fields, and after the final injected field.
- In Control classes, place generated Factory injections immediately after the comment header for the entity type where they are used.
  - Keep Factory injections alphabetically ordered within each entity section.
- Reuse dependencies injected by base classes; do not redeclare fields that hide inherited injections.
- Do not inject a class into itself.
- Use Logic classes when available (e.g., `ChainKindLogic`, `ChainTypeLogic`).
- For Command classes that Get specific types of entities:
    - For Command classes getting multiple entities extend BasePaginatedMultipleEntitiesCommand
    - For Command classes getting a single entity extend BasePaginatedSingleEntityCommand
    - getUserVisit should only be called once and then reused
- For Command classes that Edit specific types of entities:
    - Extend BaseAbstractEditCommand
- Use dependency injection when possible using `@Inject` annotation
    - If the class being modified is annotated with @ApplicationScoped, @RequestScoped, @CommandScope, @Dependent, or @SentEventSubscriber
        - Control and Logic classes should be injected using `@Inject` annotation
- Classes annotated with `@CommandScope` and `@SentEventSubscriber` are CDI-managed.
- Format all static `CommandSecurityDefinition` and `List<FieldDefinition>` assignments consistently:
    - Indent nested list entries one additional level.
    - Align closing parentheses with their assignment expression.
- Strings that span multiple lines should use Java text blocks
     - Opening quotes must be on the line of code before the start of the block
     - Closing quotes must be on the line following the last line of the block, followed immediately by any parameters or the closing parenthesis

### jOOQ Query Conventions

- Use generated jOOQ tables, fields, and foreign keys for SQL queries in Control classes
- Format query construction as a vertical chain, with each query clause on its own line, including `.select(...)`, `.from(...)`, `.where(...)`, `.orderBy(...)`, and `.forUpdate()`
- Keep `.onKey(...)` and `.on(...)` on the same line as its corresponding `.join(...)`
- Separate `baseQuery`, `query` or `sql`, and factory `return` statements with a blank line
- Use the `EntityPermission` branch to add `.forUpdate()` for `READ_WRITE` queries
- Apply client pagination to read-only entity-list queries using `session.applyLimit(query, EntityFactory.class)`
- Pass jOOQ queries directly through the generated entity factory using `getEntityFromQuery(entityPermission, query)` or `getEntitiesFromQuery(entityPermission, query)`
- Use `selectCount()`, `fetchOptional(0, Long.class)`, and `orElse(0L)` for count queries

### Forms, Results, Specs, and Edits Generation

- Forms, Results, Specs, and Edits are defined as interfaces in `...common.form`, `...common.result`, `...common.spec`, and `...common.edit` packages
- Concrete implementations and factories are automatically generated during the build process (`ant compile`)
- Generated code is located in the `generated/` directory
- Use the generated factories via Beans or Services to instantiate these objects (e.g., `ContactBean.getGetContactMechanismsForm()`, `ContactService.getGetContactMechanismsResult()`)
- Always run `ant compile` after adding or modifying these interfaces to ensure the factories are up to date

### Build Instructions

- `ant clean`: Clean the build environment
- `ant compile`: Compile the source code
- `ant deploy`: Deploy the application
- `ant javadoc`: Generate the Javadoc documentation
