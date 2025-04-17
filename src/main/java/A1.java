public class A1 implements A1Interface{
    //comment
    @Override
    public String[] processArrayCommands(String[] arr, CommandNode commandHead){
        String[] a = arr;
        int aSize = 0;
        for (int idx = 0; idx < a.length; idx++) {
            if(a[idx] != null){
                aSize ++;
            }
        }
        int currCommand = 0;
        CommandNode currentCommand = commandHead;
        
        while(currentCommand != null){
            String[] commComponents = a[currCommand].split(" ", 4);
            String comm = commComponents[0];
            try {
                switch (comm){
                    case "add":
                        if(commComponents.length > 3){
                            break;
                        }
                        int commIndex = Integer.parseInt(commComponents[1]);
                        String commValue = commComponents[2];
                        if(checkIndex(a,commIndex)){
                            break;
                        }
                        if(a[commIndex] == null){
                            a[commIndex] = commValue;
                        } 
                        if(aSize == a.length){
                            a = resizeArray(a);
                        }
                        shiftElementsRight(a,commIndex);
                        a[commIndex] = commValue;
                        aSize++;
                        break;
                    case "remove":
                        commIndex = Integer.parseInt(commComponents[1]);
                        if(checkIndex(a, commIndex)){
                            break;
                        }
                        a[commIndex] = null;
                        shiftElementsLeft(a, commIndex);
                        aSize--;
                        break;
                    case "swap":
                        int commIndex1 = Integer.parseInt(commComponents[1]);
                        int commIndex2 = Integer.parseInt(commComponents[2]);
                        if(checkIndex(a, commIndex1) || checkIndex(a, commIndex2)){
                            break;
                        }
                        String storeValue1 = a[commIndex1];
                        String storeValue2 = a[commIndex2];
                        a[commIndex1] = storeValue2;
                        a[commIndex2] = storeValue1;
                        break;
                    case "skip":
                        int skipper = Integer.parseInt(commComponents[1]);
                        CommandNode temp = currentCommand;
                        for (int i = 0; i < skipper && temp != null; i++) {
                            temp = temp.next;
                        }
                        if (temp != null) {
                            currentCommand = temp;
                        } 
                        break;
                        
                    case "ifValue":
                        if (commComponents.length < 4) break;
                        commIndex = Integer.parseInt(commComponents[1]);
                        commValue = commComponents[2];
                        String commCommand = commComponents[3];
                        if(a[commIndex].equals(commValue)){

                            try {
                                switch(commCommand){
                                    case "add":
                                        if(commComponents.length > 3){
                                            break;
                                        }
                                        commIndex = Integer.parseInt(commComponents[1]);
                                        commValue = commComponents[2];
                                        if(checkIndex(a,commIndex)){
                                            break;
                                        }
                                        if(a[commIndex] == null){
                                            a[commIndex] = commValue;
                                        } 
                                        if(aSize == a.length){
                                            a = resizeArray(a);
                                        }
                                        shiftElementsRight(a,commIndex);
                                        a[commIndex] = commValue;
                                        aSize++;
                                        break;
                                    case "remove":
                                        commIndex = Integer.parseInt(commComponents[1]);
                                        if(checkIndex(a, commIndex)){
                                            break;
                                        }
                                        a[commIndex] = null;
                                        shiftElementsLeft(a, commIndex);
                                        aSize--;
                                        break;
                                    case "swap":
                                        commIndex1 = Integer.parseInt(commComponents[1]);
                                        commIndex2 = Integer.parseInt(commComponents[2]);
                                        if(checkIndex(a, commIndex1) || checkIndex(a, commIndex2)){
                                            break;
                                        }
                                        storeValue1 = a[commIndex1];
                                        storeValue2 = a[commIndex2];
                                        a[commIndex1] = storeValue2;
                                        a[commIndex2] = storeValue1;
                                        break;
                                    case "skip":
                                        skipper = Integer.parseInt(commComponents[1]);
                                        temp = currentCommand;
                                        for (int i = 0; i < skipper && temp != null; i++) {
                                            temp = temp.next;
                                        }
                                        if (temp != null) {
                                            currentCommand = temp;
                                        } 
                                        break;
                                    
                                }
                            } catch (Exception e) {
                            }
                        }
                        break;
                }
            } catch (Exception e) {
            }
            currCommand++;
            currentCommand = currentCommand.next;
        }
        return a;
    }
    private String[] resizeArray(String[] arr){
        String[] newA = new String[2*arr.length];
        System.arraycopy(arr, 0, newA, 0, arr.length);
        return newA;
    }
    private void shiftElementsRight(String[] arr, int index){
        for(int i = arr.length - 1; i > index; i--){
            arr[i] = arr[i-1];
        }
    }
    private void shiftElementsLeft(String[] arr, int index){
        for(int i = index; i < arr.length -1; i++){
            arr[i] = arr[i+1];
        }
    }
    private boolean checkIndex(String[] arr, int index){
        boolean check = false;
        if(index < 0 || index > arr.length - 1){
            check = true;
        }
        return check;
    }
    @Override
    public Node processLinkedListCommands(Node head, String[] commands){
        if(commands == null){
            return head;
        }
        int currCommand = 0;
        int currSize = 0;
        Node current = head;
        while(current != null){
            current = current.next;
            currSize++;
        }
        current = head;
        while(currCommand < currSize){
            String[] commComponents = commands[currCommand].split(" ");
            String comm = commComponents[0];
            try {
                switch(comm){
                    case "insert":
                        int commIndex = Integer.parseInt(commComponents[1]);
                        String value = commComponents[2];
                        Node newNode = new Node(value);
                        if(commIndex < 0){
                            break;
                        }else if(commIndex == 0){
                            newNode.next = getNode(head, 0);
                        } else{
                            newNode.next = getNode(head, commIndex).next;
                            getNode(head, commIndex - 1).next = newNode;
                        }
                        currSize++;
                        break;
                    case "delete":
                        commIndex = Integer.parseInt(commComponents[1]);
                        if(currSize ==0 || commIndex < 0){
                            break;
                        } else if(commIndex == 0){
                            head = head.next;
                        }else{
                            getNode(head, commIndex - 1).next = getNode(head, commIndex).next;
                        }
                        currSize--;
                    case "move":
                        int fromIndex = Integer.parseInt(commComponents[1]);
                        int toIndex = Integer.parseInt(commComponents[2]);
                        if(fromIndex < 0 || fromIndex > currSize || toIndex < 0 || toIndex > currSize + 1){
                            break;
                        } else if(fromIndex == toIndex){
                            break;
                        }
                        Node from = getNode(head, fromIndex);
                        Node to = getNode(head, toIndex);
                        Node toPrevious = getNode(head, toIndex - 1);

                        if(fromIndex == 0){
                            head = head.next;
                            from.next = to;
                            getNode(head, toIndex - 1).next = from;
                        } else{
                            getNode(head, fromIndex - 1).next = getNode(head, fromIndex + 1);
                            from.next = to;
                            getNode(head, toIndex - 1).next = from;
                        }
                        break;
                    case "reverse":
                        if(currSize == 0){
                            break;
                        }
                        Node next;
                        Node previous = null;
                        current = head;
                        while (current != null) {
                            next = current.next;
                            current.next = previous;
                            previous = current;
                            current = next;
                        }
                        head = previous;
                        break;
                    case "skip":
                        int skipper = Integer.parseInt(commComponents[1]);
                        if (skipper > 0 && currCommand + skipper < commands.length) {
                            currCommand += skipper;
                        }
                        break;
                    case "ifValue":
                        if (commComponents.length < 4) break;
                        commIndex = Integer.parseInt(commComponents[1]);
                        value = commComponents[2];
                        String commCommand = commComponents[3];
                        if(getNode(head, commIndex).data.equals(value)){
                            try {
                                switch(commCommand){
                                    case "insert":
                                        commIndex = Integer.parseInt(commComponents[1]);
                                        value = commComponents[2];
                                        newNode = new Node(value);
                                        if(commIndex < 0){
                                            break;
                                        }else if(commIndex == 0){
                                            newNode.next = getNode(head, 0);
                                        } else{
                                            newNode.next = getNode(head, commIndex).next;
                                            getNode(head, commIndex - 1).next = newNode;
                                        }
                                        currSize++;
                                        break;
                                    case "delete":
                                        commIndex = Integer.parseInt(commComponents[1]);
                                        if(currSize ==0 || commIndex < 0){
                                            break;
                                        } else if(commIndex == 0){
                                            head = head.next;
                                        }else{
                                            getNode(head, commIndex - 1).next = getNode(head, commIndex).next;
                                        }
                                        currSize--;
                                    case "move":
                                        fromIndex = Integer.parseInt(commComponents[1]);
                                        toIndex = Integer.parseInt(commComponents[2]);
                                        if(fromIndex < 0 || fromIndex > currSize || toIndex < 0 || toIndex > currSize + 1){
                                            break;
                                        } else if(fromIndex == toIndex){
                                            break;
                                        }
                                        from = getNode(head, fromIndex);
                                        to = getNode(head, toIndex);
                                        if(fromIndex == 0){
                                            head = head.next;
                                            from.next = to;
                                            getNode(head, toIndex - 1).next = from;
                                        } else{
                                            getNode(head, fromIndex - 1).next = getNode(head, fromIndex + 1);
                                            from.next = to;
                                            getNode(head, toIndex - 1).next = from;
                                        }
                                        break;
                                    case "reverse":
                                        if(currSize == 0){
                                            break;
                                        }
                                        previous = null;
                                        current = head;
                                        while (current != null) {
                                            next = current.next;
                                            current.next = previous;
                                            previous = current;
                                            current = next;
                                        }
                                        head = previous;
                                        break;
                                    case "skip":
                                        skipper = Integer.parseInt(commComponents[1]);
                                        if (skipper > 0 && currCommand + skipper < commands.length) {
                                            currCommand += skipper;
                                        }
                                        break;
                                }
                            } catch (Exception e) {
                            }
                            break;
                        }
                }
            } catch (Exception e) {
            }
            currCommand++;
        }
        return head;
    }
    private Node getNode(Node head, int index){
        Node result = head;
        int count = 0;
        while(result != null){
            if(count == index){
                return result;
            }
            result = result.next;
            count++;
        }
        return null;
    }

}
