package com.jacobs.basic.algorithm;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lichao
 * @date 2019/01/28
 */
public class ConsistentHash {

    private Integer machineCount;
    private final int replicas;
    private volatile List<Node> nodes;
    Set<String> curIds;

    public static void main(String[] args) {
        try {
            final ConsistentHash consistentHash = new ConsistentHash(10000);
            consistentHash.addMachine("1");
            consistentHash.addMachine("2");
            consistentHash.addMachine("3");
            consistentHash.addMachine("4");
            consistentHash.addMachine("5");

            Map<String, Integer> counter = new HashMap<String, Integer>();
            counter.put("1", 0);
            counter.put("2", 0);
            counter.put("3", 0);
            counter.put("4", 0);
            counter.put("5", 0);
            for (int i = 0; i < 1000000; i++) {
                String machine = consistentHash.getMachineForKey(String.valueOf(i));
                counter.put(machine, counter.get(machine) + 1);
            }

            for (Map.Entry<String, Integer> entry : counter.entrySet()) {
                System.out.println("machine: " + entry.getKey() + ", count: " + entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ConsistentHash(int replicas) {
        machineCount = 0;
        this.replicas = replicas;
        nodes = new ArrayList<Node>();
        curIds = new HashSet<String>();
    }

    public boolean addMachine(String id) {
        synchronized (machineCount) {
            if (curIds.contains(id)) {
                return false;
            }
            curIds.add(id);

            List<Node> newNodes = new ArrayList<Node>();
            newNodes.addAll(nodes);
            for (int i = 0; i < replicas; i++) {
                String virtualId = id + "-" + i;
                newNodes.add(new Node(id, virtualId, genHash(virtualId)));
            }
            Collections.sort(newNodes);
            nodes = newNodes;
            machineCount++;
            return true;
        }
    }

    public void removeMachine(String id) {
        synchronized (machineCount) {
            if (!curIds.contains(id)) {
                return;
            }
            curIds.remove(id);

            List<Node> newNodes = new ArrayList<Node>();
            for (Node node : nodes) {
                if (!node.getId().equals(id)) {
                    newNodes.add(node);
                }
            }
            Collections.sort(newNodes);
            nodes = newNodes;
            machineCount--;
        }
    }

    public String getMachineForKey(String key) throws Exception {
        List<Node> curNodes = nodes;
        if (curNodes.isEmpty()) {
            throw new Exception("没有候选机器!");
        }

        int size = curNodes.size();
        int keyHash = genHash(key);
        if (keyHash > curNodes.get(size - 1).getHash() || keyHash < curNodes.get(0).getHash()) {
            return curNodes.get(0).getId();
        } else {
            int index = binarySearch(curNodes, 0, curNodes.size() - 1, keyHash);
            return curNodes.get(index).getId();
        }
    }

    private int binarySearch(List<Node> nodes, int left, int right, int hash) {
        if (right - left <= 1) {
            if (nodes.get(left).getHash() == hash) {
                return left;
            } else {
                return right;
            }
        }

        int middle = (left + right) / 2;
        if (nodes.get(middle).getHash() == hash) {
            return middle;
        } else if (nodes.get(middle).getHash() > hash) {
            return binarySearch(nodes, left, middle - 1, hash);
        } else {
            return binarySearch(nodes, middle + 1, right, hash);
        }
    }

    private int genHash(String key) {
        try {
            int hash = 0xFFFFFFFF;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(key.getBytes("UTF-8"));
            int q = bytes.length / 4;
            int r = bytes.length % 4;
            for (int i = 0; i < q; i++) {
                hash ^= ((bytes[i * 4] << 24) | (bytes[i * 4 + 1] << 16) | (bytes[i * 4 + 2] << 8) | (bytes[i * 4 + 3]));
            }
            int j = 3;
            for (int i = 0; i < r; i++) {
                hash ^= (bytes[q * 4 + i] << (8 * (j--)));
            }
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private class Node implements Comparable<Node> {

        private String id;
        private String virtualId;
        private int hash;

        public Node(String id, String virtualId, int hash) {
            this.id = id;
            this.virtualId = virtualId;
            this.hash = hash;
        }

        public int compareTo(Node anotherNode) {
            if (hash == anotherNode.hash) {
                return 0;
            } else if (hash < anotherNode.hash) {
                return -1;
            } else {
                return 1;
            }
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (other instanceof Node) {
                Node anotherNode = (Node) other;
                return hash == anotherNode.hash && id.equals(anotherNode.id) && virtualId.equals(anotherNode.virtualId);
            }

            return false;
        }

        @Override
        public String toString() {
            return "Node{" + "id='" + id + '\'' + ", virtualId='" + virtualId + '\'' + ", hash=" + hash + '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVirtualId() {
            return virtualId;
        }

        public void setVirtualId(String virtualId) {
            this.virtualId = virtualId;
        }

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }
    }

}
