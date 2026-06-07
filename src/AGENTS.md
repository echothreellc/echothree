### Coding Conventions

- Maintain required alphabetical and type-based ordering for injected classes:
    - Control classes first in alphabetical order.
    - Logic classes second in alphabetical order.
    - All injections follow the static initializer block and be before the constructor
- Use Logic classes when available (e.g., `ChainKindLogic`, `ChainTypeLogic`).
- For Command classes that Get specific types of entities:
    - For Command classes getting multiple entities extend BasePaginatedMultipleEntitiesCommand
    - For Command classes getting a single entity extend BasePaginatedSingleEntityCommand
- For Command classes that Edit specific types of entities:
    - Extend BaseAbstractEditCommand
- Use dependency injection when possible using `@Inject` annotation
    - If the class being modified is annotated with @ApplicationScope, @RequestScope, @CommandScope, or @Dependent
        - Control and Logic classes should be injected using `@Inject` annotation
- Strings that span multiple lines should use Java text blocks
     - Opening quotes must be on the line of code before the start of the block
     - Closing quotes must be on the line following the last line of the block, followed immediately by any parameters or the closing parenthesis