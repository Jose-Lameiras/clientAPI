# Agents Configuration

## Workflow Rules
- **Documentation**: Every time a new component, entity, or feature is created, the agent must document the action in `AI_DEVELOPMENT.md`.
- **Transparency**: Before creating any files, the agent must explicitly show:
    1. The destination path where the files will be created.
    2. The exact code that will be placed inside those files.
- **Verification**: Always follow existing project conventions and patterns for new implementations.

## Suggested Agent Workflow
Use the AI agent iteratively rather than asking it to generate the entire project in a single prompt.
A suggested workflow is:
1. Ask the agent to analyze the requirements.
2. Ask it to propose an implementation plan and architecture before writing code.
3. Break the implementation into small, verifiable tasks.
4. Let the agent implement tasks incrementally.
5. Ask the agent to build and run the project.
6. Ask it to create and execute the required automated tests.
7. Let the agent investigate and fix compilation or test failures.
8. Ask the agent to perform a code review of its own implementation.
9. Review the proposed changes yourself and challenge questionable decisions.
10. Iterate until the requirements are satisfied and all tests pass.

AI-generated code should not be accepted blindly. You remain responsible for understanding, validating, and explaining the submitted solution.
