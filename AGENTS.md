# Repository Guidelines

## Project Structure & Module Organization

This is a Maven-based Java SDK for QingStor. Main source lives in `src/main/java/com/qingstor/sdk`, with resources in `src/main/resources`. Unit tests live under `src/test/java/com/qingstor/sdk`; integration and Cucumber tests live under `src/test/java/integration/cucumber` and use feature files from `tests/features`. API specs are in `specs/qingstor`, code-generation templates are in `template`, and user-facing documentation is in `docs`. Architecture/release decisions belong in `docs/adr`, with implementation specs in `docs/specs`.

## Build, Test, and Development Commands

- `mvn test`: run unit tests, excluding integration tests.
- `mvn verify -DskipITs -Dgpg.skip`: run the CI-style Maven verify phase without signing.
- `mvn spotless:check`: verify formatting of Java and Markdown files.
- `mvn spotless:apply`: format Java and Markdown files according to repo style.
- `mvn integration-test`: run service/integration tests; configure local credentials from `tests/config.yaml.example` or `tests/test_config.yaml.example`.
- `mvn package -Prelease`: build normal jar, sources, and javadocs.
- `mvn clean -Pshade-all -DskipTests package`: build the shaded jar at `target/qingstor.sdk.java-<version>-shaded.jar`.
- `make generate`: regenerate service code from Swagger specs using `snips`, then apply formatting.

## Coding Style & Naming Conventions

Java targets source/bytecode level 8. Formatting is managed by Spotless with google-java-format `1.7` in AOSP style and the header in `spotless.license.java`; run `mvn spotless:apply` before committing generated or manually edited Java. Use package names under `com.qingstor.sdk`. Keep test classes named `*Test` and integration/Cucumber classes grouped under the existing integration package.

## Testing Guidelines

Use JUnit 4 for unit tests and Cucumber for behavior/integration coverage. Prefer focused unit tests for utility and model behavior; reserve integration tests for QingStor service workflows that require credentials. Do not commit local secrets or generated test config files.

## Commit & Pull Request Guidelines

Recent commits use concise, imperative subjects with optional prefixes, for example `fix(delete-obj): ...`, `deps: ...`, `chore: ...`, and `Bump version to ...`. Keep commits scoped to one concern. Pull requests should describe the behavior change, list verification commands run, link related issues when available, and call out release-impacting changes such as dependency updates, Central publishing, or shaded jar behavior.

## Release Guidelines

- **Release Guide & SOP**: See [`docs/release.md`](docs/release.md) for the complete, step-by-step maintainer release guide.
- **Version Bump Checklist**: Update version across `pom.xml`, `src/main/resources/version.properties`, `docs/install.md`, `docs/install_zh-CN.md`, and document changes in `CHANGELOG.md`.
- **GitHub Releases**: Automated via `.github/workflows/release.yml`. Pushing a tag `v<version>` automatically verifies, builds the standard JAR + shaded JAR, extracts notes from `CHANGELOG.md`, and creates the GitHub Release with assets attached.
- **Maven Central Publishing**: Use `mvn clean deploy -Prelease` (do NOT activate `shade-all`) to upload signed standard artifacts to Sonatype Central Portal, then confirm deployment in the portal.
