# AGENTS: Guide for AI coding agents working on MindCheck

Checklist for an agent before changing code
- Read `src/main/java/.../controller`, `service`, `repository`, `mapper`, `command`, `dto` folders
- Confirm which `Repository` implementation is active (currently `MockMoodEntryRepository` is annotated `@Repository`)
- Run build and quick smoke (see run commands) and run unit tests

High-level architecture (big picture)
- Spring Boot Web MVC application (main: `MindCheckApplication.java`). Uses Thymeleaf for templates but also exposes REST endpoints under `/api/*`.
- Controller -> Service -> Repository -> Model pattern. Mappers convert between Command/Model/DTO.
  - Example: `MoodEntryController` accepts `MoodEntryCommand` -> `MoodEntryServiceImpl` uses `MoodEntryMapper` to create `MoodEntry` -> repository (e.g. `MockMoodEntryRepository`) persists and service maps result to `MoodEntryDTO`.

Key directories and their roles
- `controller/` — REST endpoints. Examples: `MoodEntryController` (`/api/moodEntries`), `UserController` (`/api/users`). Controllers return ResponseEntity with appropriate HTTP codes.
- `service/` — business logic interfaces & implementations. Example: `MoodEntryService` + `MoodEntryServiceImpl` delegating to `MoodEntryRepository` and `MoodEntryMapper`.
- `repository/` — data access layer. Two styles present:
  - in-memory/mock implementation: `MockMoodEntryRepository` (annotated `@Repository`, used by DI)
  - placeholder JDBC implementation: `JdbcMoodEntryRepository` (empty class — not implemented)
- `mapper/` — converts entity <-> DTO <-> command. Example: `MoodEntryMapper.toEntity()` and `toDTO()`.
- `command/` and `dto/` — request payloads that include Jakarta Bean Validation annotations (e.g. `@NotNull`, `@Min`, `@Max`, `@NotBlank`) — controllers rely on these for automatic validation.

Project-specific conventions and patterns
- DTO/Command/Mapper pattern is used consistently: controller accepts *Command*, service returns *DTO*, mappers live under `mapper/`.
- Repositories are plain interfaces (no Spring Data JPA). The mock repo returns pre-populated data (see `MockMoodEntryRepository` constructor with many `MoodEntry` entries).
- When multiple repository implementations exist, DI is driven by `@Repository` annotations (currently only the mock is annotated).
- Controllers use ResponseEntity and return 201 (CREATED) on successful saves, 409 (CONFLICT) when save/update returns null — follow this convention when adding endpoints.

Build, run and test (developer workflows)
- Build: use the included wrapper to ensure consistent Maven version:
  - Windows PowerShell: `./mvnw.cmd -DskipTests=true package`
- Run (development):
  - `./mvnw.cmd spring-boot:run` (runs the app; mock repository will provide data)
  - Or run the fat jar after package: `java -jar target/mindcheckapp-0.0.1-SNAPSHOT.jar`
- Tests: `./mvnw.cmd test` (there is a basic Spring context load test in `src/test`)
- Database: `schema.sql` and `data.sql` exist for H2, but the app currently uses the in-memory mock repository; switching to `JdbcMoodEntryRepository` will require wiring and verifying SQL schema alignment.

Integration points & external dependencies
- Spring Boot starters (Thymeleaf, WebMVC, Validation, JDBC) declared in `pom.xml`.
- H2 runtime dependency + `schema.sql`/`data.sql` are present — intended for JDBC-based storage if implemented.
- Lombok is used across models/dtos (e.g. `@Data`, `@AllArgsConstructor`) — ensure IDE annotation processing is enabled.

Actionable tips for agents editing code
- Before implementing persistence: prefer adding a Spring `@Configuration` test or new integration test proving `JdbcMoodEntryRepository` wired and using H2 with `schema.sql`/`data.sql`.
- Respect existing HTTP behavior: use ResponseEntity with the same status codes as related controllers (see `MoodEntryController` for examples: 200, 201, 404, 409).
- When adding new endpoints, follow Command -> Service -> Mapper -> Repository flow and add tests under `src/test`.
- Be conservative with Java version: `pom.xml` lists `java.version` = 25. For CI or local runs, confirm JDK availability; if unavailable, adjust `pom.xml` or use a compatible JDK.

Files to inspect first (quick tour)
- `MindCheckApplication.java` (app entry)
- `controller/MoodEntryController.java`, `controller/UserController.java`
- `service/MoodEntryServiceImpl.java`, `service/UserService.java`
- `mapper/MoodEntryMapper.java`, `mapper/UserMapper.java`
- `repository/MockMoodEntryRepository.java`, `repository/JdbcMoodEntryRepository.java`
- `command/*.java` and `dto/*.java` for validation and payload shapes
- `src/main/resources/schema.sql` and `data.sql` if enabling JDBC

If you change persistence wiring
- Add `@Primary` or `@Profile` annotations if introducing multiple repository beans to avoid accidental bean ambiguity.
- Add integration test that starts the context and verifies repository bean type and a simple query.

Why these decisions matter
- The current mock-first approach speeds front-end and controller development without requiring DB setup; migrating to JDBC requires deliberate wiring and alignment with SQL scripts.
- Mappers isolate conversion logic — modify them rather than controllers when changing payload shapes.

End of AGENTS.md

