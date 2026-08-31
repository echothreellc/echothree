### Coding Conventions

- Every newly added source file must include the repository's full copyright and Apache License 2.0 header. Do not abbreviate, condense, or omit any portion of the standard header; copy it verbatim from an existing neighboring source file.
- Maintain required alphabetical and type-based ordering for top-level injected dependencies:
    - Generated Factory classes first in alphabetical order.
    - Control classes second in alphabetical order.
    - Logic classes third in alphabetical order.
    - Place the injection block after the static initializer and before any constructor Javadoc.
    - Keep constructor Javadocs immediately above their constructors.
    - Leave a blank line before the first `@Inject`, between injected fields, and after the final injected field.
- Reuse dependencies injected by base classes; do not redeclare fields that hide inherited injections.
- Do not inject a class into itself.
- Use Logic classes when available (e.g., `ChainKindLogic`, `ChainTypeLogic`).
- When reporting an unknown or duplicate entity, relationship, or description, pass every name that identifies it to `addExecutionError`, ordered from the outermost parent to the entity itself
- Use dependency injection when possible using `@Inject` annotation
    - If the class being modified is annotated with @ApplicationScoped, @RequestScoped, @CommandScope, @Dependent, or @SentEventSubscriber
        - Control and Logic classes should be injected using `@Inject` annotation
- Classes annotated with `@CommandScope` and `@SentEventSubscriber` are CDI-managed.
- Strings that span multiple lines should use Java text blocks
     - Opening quotes must be on the line of code before the start of the block
     - Closing quotes must be on the line following the last line of the block, followed immediately by any parameters or the closing parenthesis

### Build Instructions

- `ant clean`: Clean the build environment
- `ant compile`: Compile the source code
- `ant deploy`: Deploy the application
- `ant javadoc`: Generate the Javadoc documentation
