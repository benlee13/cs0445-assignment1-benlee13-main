/**
 * An interface defining two methods to process a sequence of commands:
 * one applied to an array and the other to a singly linked list.
 */
public interface A1Interface {
    /**
     * Applies a sequence of commands, stored as a linked list of strings,
     * to modify the given array and return the modified array. Supported commands include:
     * - add <index> <value>: Insert the specified value at the given index.
     * - remove <index>: Remove the value at the specified index.
     * - swap <index1> <index2>: Swap the values at the two indices.
     * - skip +/-N: Skip the next or previous N commands.
     * - ifvalue <index> <value> <command>: Conditionally execute a command if the value at the given index matches.
     *
     * @param arr the array to be modified
     * @param commandHead the head node of the command linked list
     * @return a reference to the array after modifications
     */
    public String[] processArrayCommands(String[] arr, CommandNode commandHead);

    /**
     * Applies a sequence of command strings to the given singly linked list
     * and returns the modified head node. Supported commands include:
     * - insert <index> <value>: Insert the specified value at the given index.
     * - delete <index>: Remove the node at the specified index.
     * - move <from> <to>: Move a node from one index to another.
     * - reverse: Reverse the entire list.
     * - skip +/-N: Skip forward or backward N commands.
     * - ifvalue <index> <value> <command>: Conditionally execute a command if the value at the given index matches.
     *
     * @param head the head of the linked list
     * @param commands the array of commands to apply
     * @return the new head of the linked list after applying all commands
     */
    public Node processLinkedListCommands(Node head, String[] commands);
}