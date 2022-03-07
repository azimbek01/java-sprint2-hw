package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<UUID, Node<Task>> nodeMap = new HashMap<>();

    private Node<Task> first;
    private Node<Task> last;
    private int size = 0;

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            removeNode(task.getId());
        }
        nodeMap.put(task.getId(), linkLast(task));
    }

    @Override
    public void remove(UUID id) {
        if (nodeMap.containsKey(id)) {
            removeNode(id);
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            history.add((Task) currentNode.getItem());
            currentNode = currentNode.getNext();
        }
        return history;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
        nodeMap.clear();
    }

    private Node<Task> linkLast(Task task) {
        Node<Task> l = last;
        Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.setNext(newNode);
        }
        size++;
        return newNode;
    }

    private void removeNode(UUID id) {
        Node node = nodeMap.get(id);
        Node prevNode = node.getPrev();
        Node nextNode = node.getNext();
        if (prevNode == null && nextNode == null) {
            first = null;
            last = null;
            size--;
            return;
        }
        if (prevNode != null && nextNode != null) {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
            node.setPrev(null);
            node.setNext(null);
            size--;
            return;
        }
        if (prevNode == null) {
            nextNode.setPrev(null);
            node.setNext(null);
            first = nextNode;
            size--;
        } else if (nextNode == null) {
            prevNode.setNext(null);
            node.setPrev(null);
            last = prevNode;
            size--;
        }

    }
}
