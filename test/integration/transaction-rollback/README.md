# Deployed transaction rollback probe

This opt-in probe runs inside the existing local WildFly application. It uses real
EJB container-managed transactions, `BaseCommand.run()`, `PartyControl`, the
`java:/EchoThreeDS` datasource, and the configured shared Infinispan data cache.
It is excluded from the production build and exposes no HTTP endpoint.

Run from the repository root, with WildFly already running and the build jars
matching the deployed application:

```sh
rtk proxy python3 test/integration/transaction-rollback/run.py
```

The runner compiles the probe into a temporary directory, backs up the **currently
deployed EAR**, adds the probe classes to a copy of its server jar, and temporarily
redeploys it through the deployment scanner. The startup singleton runs these cases:

1. Two successful nested commands flush new Parties, followed by an outer
   `RuntimeException`. Repeat with an outer Java `Error` and an extra nesting level.
2. A GraphQL mutation executes two child writes. The second child throws after
   flushing; GraphQL captures the exception. The enclosing command must leave the
   transaction marked rollback-only even though the GraphQL engine returns a result.
3. Commit an isolated baseline Party, verify its cached values, then edit it through
   a nested command and abort the outer command after the edit flushes.

Each case checks flushed SQL/cache state *before* triggering failure. Verification
then runs in a fresh `REQUIRES_NEW` transaction, reading SQL and the raw shared cache
before using a factory. New Parties/details must be absent. For the existing Party,
the original active/last pointers, detail name, and open-ended validity must survive,
and the additional detail must be absent. No cache eviction precedes verification.

The probe's nested commands intentionally bypass authentication and auditing to
isolate persistence behavior. Case 2 uses the real GraphQL Java engine with a small
fault-injection schema; it does not exercise the public GraphQL endpoint or its
authentication/schema wiring. The existing endpoint integration suite remains
separate coverage.

The baseline is deleted through the normal Party control after verification. As
with normal domain deletion, its history can remain. Rolled-back inserts may consume
IDs. Expected exception stack traces are labeled `ROLLBACK_PROBE_EXPECTED`.

The runner restores the original EAR in `finally`, waits for deployment completion,
and checks its SHA-256. Backups and a complete probe log remain in the printed
`/private/tmp/echothree-rollback-*` directory. Successful output includes all four
`PASS` cases, `ALL PASS`, `CLEANUP PASS`, and the restoration hash. If the process is
forcibly killed, restore `original.ear` from that directory and trigger redeployment.
Do not run concurrently with another deployment or application build/deploy task.
