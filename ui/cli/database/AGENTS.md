# Database CLI Development Guide

This guidance applies to the `ui/cli/database` project and all directories below it.

## Purpose

This application parses the Echo Three database definition, creates or updates the physical MySQL schema, and generates
the legacy persistence model and jOOQ model. Treat the parsed definition model as the common source of truth for every
output. Do not introduce a second, hard-coded schema representation for a generator.

## Important directories

- `src/java/com/echothree/ui/cli/database/util/definition`: parsed database model, parser, enums, and centralized physical-name policy
- `src/java/com/echothree/ui/cli/database/util/current`: representation of schema metadata read from a live database
- `src/java/com/echothree/ui/cli/database/util/javagen`: generators for legacy Java persistence classes
- `src/java/com/echothree/ui/cli/database/util/jooqgen`: custom jOOQ generator and naming strategy
- `src/metadata/jooq-config.xml`: jOOQ code-generation configuration
- `src/xml`: database-definition XML resources consumed by `DatabaseDefinitionParser`
- `test/java`: JUnit Jupiter regression tests
- `test/resources`: compact database definitions used by tests
- `../../../generated/data`: generated XML, Java sources, and JUnit reports. Generated output is disposable and should not be edited by hand
- `../../../build/ui/cli/database`: compiled classes and the database utility JAR

## Definition model

The primary model types are `Database`, `Component`, `Table`, `Column`, `ColumnType`, and `Index`. Type-safe definition values live in separate enum files:

- `ColumnDataType`
- `IndexType`
- `ParentDeleteAction`

Model collection getters return unmodifiable live views. Mutate the model only through its controlled methods, such as
`Database.addTable`, `Component.addTable`, `Table.addColumn`, `Table.addIndex`, and `Index.addIndexColumn`. Direct
collection mutation would bypass lookup maps and derived collections such as foreign-key lists.

`DatabaseDefinitionParser` is responsible for rejecting malformed definitions, unknown attributes and elements, invalid
index shapes, missing references, and other inconsistent input. When adding syntax, add both positive and negative parser tests.

## Physical database names

`DatabasePhysicalNames` is the single policy for physical table, column, index, and foreign-key names. MySQL creation,
live-schema comparison, and jOOQ XML export must all use it. Do not duplicate prefix or suffix construction in a generator.

Physical names must match `DatabaseUtilitiesForMySQL` exactly:

- Table names are normalized database table names.
- Column names include the physical table/foreign-key prefixes used by MySQL.
- The MySQL primary-key name is `PRIMARY`.
- Secondary indexes use the physical index naming convention.
- Foreign keys use the physical foreign-key naming convention.
- Case normalization used for identifiers must use `Locale.ROOT`, not the host default locale.

Foreign-key column types inherit their physical SQL type from the referenced column. Preserve this behavior in every schema exporter.

## jOOQ generation

`GenerateJooq` has two stages:

1. `DatabaseUtilitiesForJooq` writes `generated/data/xml/XMLDatabase.xml` from the parsed `Database` model.
2. jOOQ reads that XML using `src/metadata/jooq-config.xml` and writes Java sources beneath `generated/data/java`.

The information-schema XML must use physical MySQL names in `table_name`, `column_name`, constraint names, key-column
usages, and referential constraints. Constraint catalogs disambiguate MySQL's table-local key names; keep table
constraints, key usages, and referential constraints aligned.

The custom classes in `util.jooqgen` provide the Java-facing naming and layout:

- Table class names use the mixed-case `Table.namePlural` value.
- Column methods and members use the friendly `Column.name`, while XML column names remain physical names.
- A trailing `Id` is omitted from friendly jOOQ column names where configured by `JooqGeneratorStrategy`.
- Generated table and record classes are grouped into lowercase component packages.
- Generated component key interfaces are under the tables-level `keys.<component>` package, with lowercase component names.
- Key identifiers use readable table/column names with `_PK`, `_UK`, or `_FK` suffixes.
- `KeyRegistry` connects the component key interfaces; there is no compatibility requirement for jOOQ's former monolithic `Keys` class.
- Large key initializers are distributed by `JooqJavaGenerator` to avoid Java class-file and method-size limits.

Keep `implicitJoinPathsToMany` disabled unless key-name collisions for self-references and multiple foreign keys to the
same table have been addressed another way.

## Legacy Java generation

`DatabaseUtilitiesForJava` is a stable orchestration facade. Generation logic belongs in `util.javagen`:

- `JavaGenerator`: shared directory and source-header helpers.
- `PrimaryKeyJavaGenerator`: primary-key classes.
- `ValueJavaGenerator`: value classes.
- `EntityJavaGenerator`: entity classes.
- `FactoryJavaGenerator`: persistence factories.
- `CommonJavaGenerator`: constants and the entity-types enum.

Add output-specific behavior to the corresponding generator. Put only genuinely shared behavior in `JavaGenerator`; do
not grow the facade back into a monolithic generator. Preserve the facade's generation order unless an output dependency
requires a deliberate change.

## Tests and verification

Run commands from `ui/cli/database` unless noted otherwise.

For definition, parser, or naming changes:

```sh
ant init clean-build test
```

JUnit XML and HTML reports are written together beneath `generated/data/junit`. Test fixtures should remain small and
should exercise physical naming, parser rejection, jOOQ XML structure, and collection invariants directly.

For legacy Java generator changes:

```sh
ant GenerateJava
```

For definition, physical naming, XML, jOOQ strategy, or jOOQ generator changes:

```sh
ant GenerateJooq
```

Before completing a cross-cutting change, run both generators and then run this from the repository root:

```sh
ant compile
```

Also run `git diff --check` and search for stale package names after moves. Use `git mv` for tracked files so history
follows package refactors.

## Change discipline

- Preserve unrelated working-tree changes; this project is often modified as part of a larger generation update.
- Do not edit generated Java or `XMLDatabase.xml` as the source of a fix. Change the definition model, naming policy, exporter, strategy, or generator and regenerate.
- Keep Ant output balanced: retain concise activity messages while suppressing jOOQ banners, tips, and routine informational noise.
- When changing naming, compare against actual SQL emitted by `DatabaseUtilitiesForMySQL`, not an assumed convention.
- When changing public model return types or generator layout, compile the entire repository because generated and server-side consumers may not be covered by the database module tests.
