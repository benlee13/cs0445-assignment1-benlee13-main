# CS 0445 Assignment 1: Command-Driven Data Structure Manipulation (20 Points)

## Overview

You will implement two methods that interpret and apply a sequence of commands on an array and a singly-linked list. This exercise practices custom data structures, iteration logic, and interpreting command strings.

## Commands for `processArrayCommands`

- `add <index> <value>`: Insert value at index
- `remove <index>`: Remove value at index
- `swap <index1> <index2>`: Swap elements at the two indices
- `skip +N` or `skip -N`: Skip forward or backward N commands
- `ifvalue <index> <value> <command>`: Execute the command only if value at `array[index].equals(value)`

If the array does not have enough capacity to accommodate an add operation,
you must resize the array to ensure there is enough space.

Resizing should follow the standard growth strategy of doubling the size:
- Allocate a new array with double the size of the original
- Copy all current elements into the new array
- Replace the original array reference with the new array

Example:
- Original array: ["a", "b", "c"] (capacity 3)
- Add at index 1: "x"
- Resize to capacity 6, then shift and insert: ["a", "x", "b", "c", null, null]

The input array may have `null` entries at the end.

## Commands for `processLinkedListCommands`

- `insert <index> <value>`: Insert value at index
- `delete <index>`: Remove element at index
- `move <from> <to>`: Move node from index to another
- `reverse`: Reverse the list
- `skip +N` or `skip -N`: Skip forward or backward N commands
- `ifvalue <index> <value> <command>`: Execute the command only if value at node `index` in the linked list `.equals(value)`

**In all the commands, index values start from 0.**

## Skip Command Behavior

The `skip ±N` command modifies the instruction pointer in the command list:

### Syntax:
```
skip +N   → skip forward N commands
skip -N   → skip backward N commands
```

### Semantics:
- After executing a skip, the next command to process will be the current position + N.
- Commands between the skip command and the jump target are not executed.
- `skip +1` is the default behavior of the processor (i.e., move to the next command).
- If the jump results in an invalid command index (out of bounds), execution terminates.

### Example:
```
Commands:
    0: add 0 A
    1: skip +2
    2: add 1 B   (skipped)
    3: add 2 C
```
Execution:
- Command 0 adds "A"
- Command 1 skips to index 3
- Command 2 is not executed
- Command 3 adds "C"

## Edge Case Specifications: 
 - Indexes must be validated before operations: ignore any command with invalid indices.
 - Skip commands may direct the pointer beyond the command list — in such cases, terminate processing gracefully.

## Project Structure
This project follows the standard Maven project layout:
```
├── pom.xml
├── src/
│   ├── main/java/
│   │       ├── A1.java            # You must implement this file ONLY
│   │       ├── A1Interface.java   # The interface file
│   │       ├── CommandNode.java   # The node structure for the command linked list
│   │       └── Node.java          # The node structure for the data linked list 
│   └── test/java/
│       └── edu/pitt/cs/A1Test.java # The JUnit test file
```
## Restrictions
- Do not use Java's built-in `ArrayList`, `LinkedList`, or `List` classes
- Use only custom data structures and arrays

## Deliverables
- Only modify `A1.java`. Your file must exist in the src/main/java folder of your repository.
- Ensure your solution compiles and passes the provided JUnit 4 tests in `A1Test.java`

## How to Compile, Run, and Debug

This project uses Maven for building and testing.

### To compile the project:
```
mvn compile
```

### To run the JUnit tests:
```
mvn test
```

Make sure you are in the root directory where `pom.xml` is located.

### Debugging Test Cases in VS Code with Test Runner

To debug JUnit test cases in VS Code, follow these steps:

### Prerequisites:
- Install the **Java Extension Pack** in VS Code.
- You may need to install version **0.40.0** of the **Test Runner for Java** extension if debugging options do not appear.

#### Steps:
1. Open the test file (e.g., `A1Test.java`) in the editor.
2. Set breakpoints by clicking on the gutter next to the line numbers.
3. Right-click on the gutter next to the line number of the test method name and select **Debug Test**.
4. Use the debug toolbar to step through code, inspect variables, and view call stacks.

This allows you to easily verify internal state, control flow, and ensure correctness of your implementation.

## Additional Resources and Submission Instructions

### Maven
Maven is used to build and manage the project. You can download it from: https://maven.apache.org/

### JUnit
JUnit 4 is used for testing. It is automatically included via Maven (see `pom.xml`). You do not need to install it separately.

### Gradescope Autograder
This assignment must be submitted on **Gradescope**. There will be an autograder that runs the same tests as those provided in `A1Test.java`, but **additional tests will also be used for grading**.

Please ensure that your code passes all visible tests and adheres to the constraints, especially the rule about modifying only `A1.java` and not using built-in Java container classes.
