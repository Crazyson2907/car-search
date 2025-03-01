### Details & Description

- Design Approach: Implements the MVVM (Model-View-ViewModel) architecture
  with traits of MVI (Model-View-Intent), focusing on unidirectional data flow
  for robust state management.

- State Preservation: Utilizes Android ViewModel to maintain UI state
  consistency through configuration changes such as screen rotations.

- Dependency Injection: Framework Used: Dagger-Hilt, a lightweight
  dependency injection framework, is used to simplify the setup and management
  of dependencies, improving the modularity and testability of the application.

- UI Binding: Android view binding
- Guidelines: DRY, KISS, SOLID
- Pattern: Repository, Clean Architecture, Singleton
- Searching Algorithm: Kotlin Collection
- Screen Rotation: Supported

- Test pyramid:
  - Small tests : 26
  - Medium tests: 3

#### Running the app:

Please add the api URL and key in the local.properties gradle file with BASE_URL,API_KEY before
building the project.

- BASE_URL="https://api-aws-eu-qa-1.auto1-test.com/v1/" //This was a one time purpose url, so no confidential data leaked
- API_KEY = coding-puzzle-client-449cc9d