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
        CommandNode tracker = commandHead;
        int commandCount = 0;
        while(tracker != null){
            commandCount++;
            tracker = tracker.next;
        }
        int currCommand = 0;
        CommandNode currentCommand = commandHead;
        
        while(currentCommand != null){
            String[] commComponents = currentCommand.command.split(" ", 6);
            String comm = commComponents[0];
            try {
                switch (comm){
                    case "add":
                        if(commComponents.length > 3){
                            currentCommand = currentCommand.next;
                            break;
                        }
                        int commIndex = Integer.parseInt(commComponents[1]);
                        String commValue = commComponents[2];

                        if(checkIndex(a,commIndex)){
                            currentCommand = currentCommand.next;
                            break;
                        }
                        if(aSize == a.length){
                            a = resizeArray(a);
                        }
                        if(a[commIndex] == null){
                            a[commIndex] = commValue;
                        } else{
                            shiftElementsRight(a,commIndex);
                            a[commIndex] = commValue;
                        }
                        aSize++;
                        currentCommand = currentCommand.next;
                        currCommand++;
                        break;
                    case "remove":
                        commIndex = Integer.parseInt(commComponents[1]);
                        if(checkIndex(a, commIndex)){
                            currentCommand = currentCommand.next;
                            break;
                        }
                        a[commIndex] = null;
                        shiftElementsLeft(a, commIndex);
                        aSize--;
                        currentCommand = currentCommand.next;
                        currCommand++;
                        break;
                    case "swap":
                        int commIndex1 = Integer.parseInt(commComponents[1]);
                        int commIndex2 = Integer.parseInt(commComponents[2]);
                        if(checkIndex(a, commIndex1) || checkIndex(a, commIndex2)){
                            currentCommand = currentCommand.next;
                            break;
                        }
                        String storeValue1 = a[commIndex1];
                        String storeValue2 = a[commIndex2];
                        a[commIndex1] = storeValue2;
                        a[commIndex2] = storeValue1;
                        currentCommand = currentCommand.next;
                        currCommand++;
                        break;
                    case "skip":
                        int skipper = Integer.parseInt(commComponents[1]);
                        CommandNode temp = currentCommand;
                        if(currCommand + skipper > commandCount || currCommand + skipper < 0){
                            currCommand++;
                            break;
                        }
                        if(skipper > 0){
                            for (int i = 0; i < skipper; i++) {
                                temp = temp.next;
                            }
                            if (temp != null) {
                                currentCommand = temp;
                                currCommand += skipper;
                            } 
                            break;
                        }else if(skipper < 0){
                            int newPosition = currCommand + skipper;
                            if (newPosition >= 0) {
                                CommandNode newCommand = commandHead;
                                for (int i = 0; i < newPosition; i++) {
                                    newCommand = newCommand.next;
                                }
                                if (newCommand != null) {
                                    currentCommand = newCommand.next.next;
                                    currCommand = newPosition;
                                }
                            }
                        }
                        break;
                        
                    case "ifvalue":
                        commIndex = Integer.parseInt(commComponents[1]);
                        commValue = commComponents[2];
                        if(a[commIndex].equals(commValue)){
                            String commCommand = commComponents[3];
                            try {
                                switch(commCommand){
                                    case "add":
                                        
                                        commIndex = Integer.parseInt(commComponents[4]);
                                        commValue = commComponents[5];
                
                                        if(checkIndex(a,commIndex)){
                                            currentCommand = currentCommand.next;
                                            break;
                                        }
                                        if(aSize == a.length){
                                            a = resizeArray(a);
                                        }
                                        if(a[commIndex] == null){
                                            a[commIndex] = commValue;
                                        } else{
                                            shiftElementsRight(a,commIndex);
                                            a[commIndex] = commValue;
                                        }
                                        aSize++;
                                        currentCommand = currentCommand.next;
                                        currCommand++;
                                        break;
                                    case "remove":
                                        commIndex = Integer.parseInt(commComponents[4]);
                                        if(checkIndex(a, commIndex)){
                                            currentCommand = currentCommand.next;
                                            break;
                                        }
                                        a[commIndex] = null;
                                        shiftElementsLeft(a, commIndex);
                                        aSize--;
                                        currentCommand = currentCommand.next;
                                        currCommand++;
                                        break;
                                    case "swap":
                                        commIndex1 = Integer.parseInt(commComponents[4]);
                                        commIndex2 = Integer.parseInt(commComponents[5]);
                                        if(checkIndex(a, commIndex1) || checkIndex(a, commIndex2)){
                                            currentCommand = currentCommand.next;
                                            break;
                                        }
                                        storeValue1 = a[commIndex1];
                                        storeValue2 = a[commIndex2];
                                        a[commIndex1] = storeValue2;
                                        a[commIndex2] = storeValue1;
                                        currentCommand = currentCommand.next;
                                        currCommand++;
                                        break;
                                    case "skip":
                                        skipper = Integer.parseInt(commComponents[4]);
                                        temp = currentCommand;
                                        if(currCommand + skipper > commandCount){
                                            currCommand++;
                                            break;
                                        }
                                        if(skipper > 0){
                                            for (int i = 0; i < skipper; i++) {
                                                temp = temp.next;
                                            }
                                            if (temp != null) {
                                                currentCommand = temp;
                                                currCommand += skipper - 1;
                                            } 
                                            break;
                                        }else if(skipper < 0){
                                            int newPosition = currCommand + skipper;
                                            if (newPosition >= 0) {
                                                CommandNode newCommand = commandHead;
                                                for (int i = 0; i < newPosition && newCommand != null; i++) {
                                                    newCommand = newCommand.next;
                                                }
                                                if (newCommand != null) {
                                                    currentCommand = newCommand.next.next;
                                                    currCommand = newPosition + 1;
                                                }
                                            }
                                        }
                                        break;  
                                }
                            } catch (Exception e) {
                            }
                        }
                        currentCommand = currentCommand.next;
                        currCommand++;
                        break;
                }
            } catch (Exception e) {
            }
            
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
        if(index < 0 || index > arr.length){
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
        while(currCommand < commands.length){
            String[] commComponents = commands[currCommand].split(" ", 6);
            String comm = commComponents[0];
            try {
                switch(comm){
                    case "insert":
                        int commIndex = Integer.parseInt(commComponents[1]);
                        String value = commComponents[2];
                        Node newNode = new Node(value);
                        if(commIndex < 0){
                            currCommand++;
                            break;
                        } else if(commIndex == 0){
                            newNode.next = getNode(head, 0);
                            head = newNode;
                        } else {
                            newNode.next = getNode(head, commIndex);
                            getNode(head, commIndex - 1).next = newNode;
                        }
                        currSize++;
                        currCommand++;
                        break;
                    case "delete":
                        commIndex = Integer.parseInt(commComponents[1]);
                        if(currSize == 0 || commIndex < 0 || commIndex > currSize){
                            currCommand++;
                            break;
                        } else if(commIndex == 0){
                            head = head.next;
                        }else{
                            getNode(head, commIndex - 1).next = getNode(head, commIndex).next;
                        }
                        currSize--;
                        currCommand++;
                        break;
                    case "move":
                        int fromIndex = Integer.parseInt(commComponents[1]);
                        int toIndex = Integer.parseInt(commComponents[2]);
                        if(fromIndex < 0 || fromIndex >= currSize || toIndex < 0 || toIndex >= currSize){
                            currCommand++;
                            break;
                        } else if(fromIndex == toIndex){
                            currCommand++;
                            break;
                        }

                        Node from = getNode(head, fromIndex);
                        Node next = from.next;
                        getNode(head, fromIndex - 1).next = next;
                        from.next = getNode(head, toIndex);
                        head = from;
                        currCommand++;
                        break;
                    case "reverse":
                        if(currSize == 0){
                            currCommand++;
                            break;
                        }
                        String[] tempData = new String[currSize];
                        for(int i = 0; i < currSize; i++){
                            tempData[i] = getNode(head, i).data;
                        }
                        int j = 0;
                        for(int i = currSize - 1; i >= 0; i--){
                            getNode(head, j).data = tempData[i];
                            j++;
                        }
                        head = getNode(head, 0);
                        currCommand++;
                        break;
                    case "skip":
                        int skipper = Integer.parseInt(commComponents[1]);
                        if (skipper < 0 || (currCommand + skipper) > commands.length - 1) {
                            currCommand = commands.length;
                            break;
                        }
                        currCommand += skipper;
                        break;
                    case "ifvalue":
                        commIndex = Integer.parseInt(commComponents[1]);
                        value = commComponents[2];
                        String commCommand = commComponents[3];
                        if(getNode(head, commIndex).data.equals(value)){

                            try {
                                switch(commCommand){
                                    case "insert":
                                        commIndex = Integer.parseInt(commComponents[4]);
                                        value = commComponents[5];
                                        newNode = new Node(value);
                                        if(commIndex < 0){
                                            currCommand++;
                                            break;
                                        } else if(commIndex == 0){
                                            newNode.next = getNode(head, 0);
                                            head = newNode;
                                        } else {
                                            newNode.next = getNode(head, commIndex);
                                            getNode(head, commIndex - 1).next = newNode;
                                        }
                                        currSize++;
                                        currCommand++;
                                        break;
                                        case "delete":
                                            commIndex = Integer.parseInt(commComponents[1]);
                                            if(currSize == 0 || commIndex < 0 || commIndex > commands.length - 1){
                                                currCommand++;
                                                break;
                                            } else if(commIndex == 0){
                                                head = head.next;
                                            }else{
                                                getNode(head, commIndex - 1).next = getNode(head, commIndex).next;
                                            }
                                            currSize--;
                                            currCommand++;
                                            break;
                                        case "move":
                                            fromIndex = Integer.parseInt(commComponents[1]);
                                            toIndex = Integer.parseInt(commComponents[2]);
                                            if(fromIndex < 0 || fromIndex >= currSize || toIndex < 0 || toIndex >= currSize){
                                                currCommand++;
                                                break;
                                            } else if(fromIndex == toIndex){
                                                currCommand++;
                                                break;
                                            }
                                            from = getNode(head, fromIndex);
                                            next = from.next;
                                            getNode(head, fromIndex - 1).next = next;
                                            from.next = getNode(head, toIndex);
                                            head = getNode(head, 0);
                                            currCommand++;
                                            break;
                                        case "reverse":
                                            if(currSize == 0){
                                                currCommand++;
                                                break;
                                            }
                                            tempData = new String[currSize];
                                            for(int i = 0; i < currSize; i++){
                                                tempData[i] = getNode(head, i).data;
                                            }
                                            j = 0;
                                            for(int i = currSize - 1; i >= 0; i--){
                                                getNode(head, j).data = tempData[i];
                                                j++;
                                            }
                                            head = getNode(head, 0);
                                            currCommand++;
                                            break;
                                        case "skip":
                                            skipper = Integer.parseInt(commComponents[1]);
                                            if (skipper < 0) {
                                                break;
                                            }
                                            if (currCommand + skipper > commands.length){
                                                currCommand = commands.length;
                                                break; 
                                            }

                                            currCommand += skipper;
                                            break;
                                }
                            } catch (Exception e) {
                            }
                            break;
                        }
                }
            } catch (Exception e) {
            }
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
