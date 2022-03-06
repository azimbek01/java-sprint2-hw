package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<UUID, Node<Task>> nodeMap = new HashMap<>();

    Node<Task> first;
    Node<Task> last;
    int size = 0;

    @Override
    public void add(Task task) {
        if (nodeMap.get(task.getId()) != null) {
            removeNode(task.getId());
        }
        linkLast(task);
    }

    public void linkLast(Task task) {
        Node<Task> l = last;
        Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.setNext(newNode);
        }
        size++;
        nodeMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(UUID id) {
        if (nodeMap.get(id) != null) {
            removeNode(id);
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public List<Task>getTasks() {
        List<Task> history = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            history.add((Task) currentNode.getItem());
            currentNode = currentNode.getNext();
        }
        return history;
    }

    public void removeNode(UUID id) {
        Node node = nodeMap.get(id);
        Node prevNode = node.getPrev();
        Node nextNode = node.getNext();
        if (prevNode == null && nextNode == null) {
            nodeMap.remove(id);
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
            nodeMap.remove(id);
            size--;
            return;
        }
        if (prevNode == null) {
            nextNode.setPrev(null);
            node.setNext(null);
            first = nextNode;
            nodeMap.remove(id);
            size--;
        } else if (nextNode == null) {
            prevNode.setNext(null);
            node.setPrev(null);
            nodeMap.remove(id);
            last = prevNode;
            size--;
        }

    }

    @Override
    public void clear() {
        Node currentNode = first;
        while (currentNode != null) {
            Node nextNode = currentNode.getNext();
            currentNode.setItem(null);
            currentNode.setPrev(null);
            currentNode.setNext(null);
            currentNode = nextNode;

        }
        first = null;
        last = null;
        size = 0;
    }
}
