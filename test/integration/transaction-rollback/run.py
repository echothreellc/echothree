#!/usr/bin/env python3
"""Run an isolated startup probe, restoring the exact deployed EAR in finally."""
import hashlib
import io
import pathlib
import subprocess
import tempfile
import time
import zipfile


ROOT = pathlib.Path(__file__).resolve().parents[3]
SERVER = pathlib.Path("/Users/rich/Java/wildfly-26.1.3.Final")
DEPLOYMENT = SERVER / "standalone/deployments/echothree.ear"
LOG = SERVER / "standalone/log/server.log"


def patched_archive(original, additions):
    output = io.BytesIO()
    with zipfile.ZipFile(io.BytesIO(original)) as source:
        with zipfile.ZipFile(output, "w") as target:
            for entry in source.infolist():
                target.writestr(entry, additions.pop(entry.filename, source.read(entry)))
            for name, data in additions.items():
                target.writestr(name, data, compress_type=zipfile.ZIP_DEFLATED)
    return output.getvalue()


def deploy(content):
    temporary = DEPLOYMENT.with_suffix(".ear.rollback-probe-tmp")
    temporary.write_bytes(content)
    temporary.replace(DEPLOYMENT)
    DEPLOYMENT.with_suffix(".ear.dodeploy").touch()


def wait_deployed(offset, require_probe):
    deadline = time.monotonic() + 180
    while time.monotonic() < deadline:
        with LOG.open() as stream:
            stream.seek(offset)
            text = stream.read()
        failure = DEPLOYMENT.with_suffix(".ear.failed")
        if failure.exists():
            raise RuntimeError(failure.read_text())
        ready = DEPLOYMENT.with_suffix(".ear.deployed").exists()
        pending = any(DEPLOYMENT.with_suffix(".ear." + marker).exists()
                      for marker in ("dodeploy", "isdeploying", "isundeploying", "pending"))
        if ready and not pending and (not require_probe or "ROLLBACK_PROBE FINISHED" in text):
            return text
        time.sleep(1)
    raise RuntimeError("Timed out waiting for deployment/probe completion")


def main():
    workspace = pathlib.Path(tempfile.mkdtemp(prefix="echothree-rollback-", dir="/private/tmp"))
    classes = workspace / "classes"
    classes.mkdir()
    sources = sorted((pathlib.Path(__file__).parent / "src").rglob("*.java"))
    classpath = ":".join(str(ROOT / path) for path in
                         ("build/jars/echothree-server.jar", "build/jars/echothree-client.jar", "build/lib/*"))
    subprocess.run(["rtk", "proxy", "javac", "-proc:none", "-cp", classpath,
                    "-d", str(classes), *map(str, sources)], check=True)
    original = DEPLOYMENT.read_bytes()
    original_hash = hashlib.sha256(original).hexdigest()
    backup = workspace / "original.ear"
    backup.write_bytes(original)
    with zipfile.ZipFile(io.BytesIO(original)) as archive:
        server_jar = archive.read("echothree-server.jar")
    additions = {path.relative_to(classes).as_posix(): path.read_bytes()
                 for path in classes.rglob("*.class")}
    instrumented = patched_archive(original, {"echothree-server.jar": patched_archive(server_jar, additions)})
    (workspace / "probe.ear").write_bytes(instrumented)
    print(f"Backup/report directory: {workspace}", flush=True)
    offset = LOG.stat().st_size
    try:
        deploy(instrumented)
        output = wait_deployed(offset, True)
        (workspace / "probe.log").write_text(output)
        for line in output.splitlines():
            if "ROLLBACK_PROBE" in line:
                print(line, flush=True)
        if "ROLLBACK_PROBE ALL PASS" not in output or "ROLLBACK_PROBE CLEANUP PASS" not in output:
            raise RuntimeError("Probe did not pass every check and cleanup; inspect probe.log")
    finally:
        restore_offset = LOG.stat().st_size
        deploy(original)
        wait_deployed(restore_offset, False)
        if hashlib.sha256(DEPLOYMENT.read_bytes()).hexdigest() != original_hash:
            raise RuntimeError(f"Restoration hash mismatch; original backup: {backup}")
        print(f"Original EAR restored and deployed; SHA256={original_hash}", flush=True)


if __name__ == "__main__":
    main()
