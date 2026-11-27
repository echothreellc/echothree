# Local WildFly rollback verification — November 26, 2026

Successful probe: `RBMWYDBKJ0`, 21:34:33 EST.

| Case | Result | Fresh-transaction observations |
| --- | --- | --- |
| Two nested creates, then outer RuntimeException | PASS | Parties 14701–14702 and their details absent from SQL; Party values absent from shared cache; factory returns null. |
| Two nested creates, then outer Error | PASS | Parties 14703–14704 and their details absent from SQL; Party values absent from shared cache; factory returns null. |
| GraphQL captures failing child after two flushed writes | PASS | Exactly one GraphQL error; enclosing transaction marked rollback-only; Parties 14705–14706 and details absent from SQL/cache/factory afterward. |
| Nested edit of committed, cached Party, then outer failure | PASS | Party 14707 retains active/last detail 14707 and original name in SQL/cache/factory; tentative detail 14708 absent; original detail still open-ended. |

Each child was checked against SQL and cache while its transaction was active,
before the intentional failure. Verification used new container-managed transactions
without prior cache eviction. Baseline verification also ran before the edit to
confirm it was committed and present in the shared cache.

Normal domain cleanup of the committed baseline passed at 21:34:33.983. The six
aborted Parties required no cleanup. Domain deletion can retain historical rows.

Full probe log and original EAR backup:
`/private/tmp/echothree-rollback-roy8vq_k/`.

The original deployment was restored byte-for-byte with SHA-256:
`36b0929340560da7925540d86f12eaee01ad44e17c28d56494f0ac9d83035958`.

An earlier probe attempt reached the first rollback and confirmed SQL absence, then
stopped because its cache verifier accessed a command-scoped bean outside a command.
The probe was corrected to use a direct JNDI cache reference and rerun successfully.
No production persistence changes were necessary.

Scope: real deployed EJB/JTA, Party persistence, datasource, and cache; GraphQL uses
a small fault-injection schema in the real GraphQL Java engine. This is not a test
of the public endpoint, authentication, or the complete production GraphQL schema.
See [README.md](README.md) for execution and restoration details.

Repository validation: `rtk ant compile` passed; probe sources compiled and deployed
successfully; `rtk git diff --check` passed.
