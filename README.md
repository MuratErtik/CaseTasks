# Haratres F.I.T Internship Assessment

Two console programs written in plain Java, without frameworks.

| Task | Description | Status |
|---|---|---|
| 1 | Character counter (`FindMyChar`) | Implemented (`com.murat.Main`) |
| 2 | Product management, sorting and cart | _To be completed_ |



## Task 1: Character counter

Counts how many times a chosen character appears in a sentence, with or without case sensitivity.

**Flow:** maximum length → sentence (re-asked while it exceeds the limit) → case sensitivity → character to analyze → result → optionally analyze another character in the same sentence.

### Requirement coverage

| Requirement | Behavior |
|---|---|
| Ask for the maximum number of characters | Positive integer required, anything else is rejected and asked again |
| Sentence within the limit, otherwise warn and re-ask | Over-limit and blank sentences are rejected and asked again |
| Ask for the case-sensitivity preference before analysis, reject invalid answers | Accepts `y`, `yes`, `n`, `no` in any case, everything else shows an error and asks again |
| Ask for a character, error if none is given | Exactly one character is required; empty or longer input shows an error and asks again |
| Count occurrences according to the preference | Explicit character-by-character loop, no counting library |
| _Extra_ | The user can analyze several characters in the same sentence without restarting |

### Error handling

- Every validation rule has its own checked exception, named after the rule: `LimitMustBeGreaterThanZeroException`, `InputMustNotBeBlankException`, `InputLengthMustBeLessThanOrEqualToLimitException`, `InputNotEqualYesOrNoException`, `ForAnalyzeNeedOneCharException`.
- All of them extend a common `InvalidInputException` and carry the user-facing message. Errors are displayed through one method (`printError`), so the way errors are presented can be changed in a single place. This is the same idea as a centralized exception handler, done without a framework.
- Non-numeric limits are handled through `NumberFormatException` from `Integer.parseInt`.

### Design notes

- Small single-purpose methods, one per prompt and one per validation rule.
- Case-insensitive comparison uses `Character.toLowerCase(char)`, which does not depend on the system locale, so the program behaves the same on a Turkish or English machine.

### Assumptions and limitations

- Input is trimmed. A single space therefore cannot be analyzed.
- Characters outside the Basic Multilingual Plane (for example emoji) are not supported.
- Prompts and messages are in English. Accepted yes/no answers are `y`, `yes`, `n`, `no`.

---

## Task 2: Product management and cart

