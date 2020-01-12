package com.jacobs.basic.zookeeper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.InputArchive;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.StatPersisted;
import org.apache.zookeeper.server.DataNode;
import org.apache.zookeeper.server.DataTree;
import org.apache.zookeeper.server.persistence.FileSnap;

/**
 * A tool for calculate data size by znode path, including all children
 */
public class SnapshotAnalyze {
    private static final Logger LOG = Logger.getLogger(SnapshotAnalyze.class);
    // data size constant
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = 1048576L;
    public static final long ONE_GB = 1073741824L;

    // specify number of level to analysis
    private static int analyzeLevel = 1;
    // specify snapshot file name of zookeeper
    private static String snapshotFileName = "";
    private static HashMap<String, Long> levelDataMap = new HashMap<>();

    public static void main(String[] args) {
        analyzeLevel = Integer.valueOf(args[0]);
        snapshotFileName = args[1];
        try {
            parse();
        } catch (IOException e) {
            LOG.error("SnapshotAnalyze get error. ", e);
        }
    }

    public static void parse() throws IOException {
        InputStream is =
                new CheckedInputStream(
                        new BufferedInputStream(new FileInputStream(snapshotFileName)), new Adler32());
        InputArchive ia = BinaryInputArchive.getArchive(is);
        FileSnap fileSnap = new FileSnap(null);
        DataTree dataTree = new DataTree();
        Map<Long, Integer> sessions = new HashMap();
        fileSnap.deserialize(dataTree, sessions, ia);
        printDetails(dataTree, sessions);
    }

    private static void printDetails(DataTree dataTree, Map<Long, Integer> sessions) {
        printZnodeDetails(dataTree);
        printSessionDetails(dataTree, sessions);
    }

    private static void printZnodeDetails(DataTree dataTree) {
        LOG.info(String.format("ZNode Details (count=%d):", dataTree.getNodeCount()));
        getZnodesTotalSize(dataTree, "/", 0);
        for (Entry<String, Long> entry : levelDataMap.entrySet()) {
            LOG.info(
                    String.format(
                            "node: %s, data size: %s", entry.getKey(), humanReadableUnits(entry.getValue())));
        }
        LOG.info("----");
    }

    private static void getZnodesTotalSize(DataTree dataTree, String name, int level) {
        DataNode n = dataTree.getNode(name);
        byte[] data = getDataByReflect(n);
        if (level == analyzeLevel) {
            levelDataMap.put(name, getDataLen(data));
        } else {
            for (Entry<String, Long> entry : levelDataMap.entrySet()) {
                if (name.startsWith(entry.getKey())) {
                    levelDataMap.put(entry.getKey(), entry.getValue() + getDataLen(data));
                }
            }
            if (levelDataMap.containsKey(name)) {
                levelDataMap.put(name, levelDataMap.get(name) + getDataLen(data));
            }
        }

        Set children = n.getChildren();
        if (children != null) {
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                String child = (String) iterator.next();
                getZnodesTotalSize(dataTree, name + (name.equals("/") ? "" : "/") + child, level + 1);
            }
        }
    }

    private static long getDataLen(byte[] data) {
        return data == null ? 0 : data.length;
    }

    private static void printSessionDetails(DataTree dataTree, Map<Long, Integer> sessions) {
        LOG.info("Session Details (sid, timeout, ephemeralCount):");
        Iterator i$ = sessions.entrySet().iterator();

        while (i$.hasNext()) {
            Entry<Long, Integer> e = (Entry) i$.next();
            long sid = (Long) e.getKey();
            LOG.info(
                    String.format("%#016x, %d, %d", sid, e.getValue(), dataTree.getEphemerals(sid).size()));
        }
    }

    private static byte[] getDataByReflect(Object object) {
        try {
            Field field = object.getClass().getDeclaredField("data");
            field.setAccessible(true);
            return (byte[]) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    private static void printStat(StatPersisted stat) {
        printHex("cZxid", stat.getCzxid());
        System.out.println("  ctime = " + (new Date(stat.getCtime())).toString());
        printHex("mZxid", stat.getMzxid());
        System.out.println("  mtime = " + (new Date(stat.getMtime())).toString());
        printHex("pZxid", stat.getPzxid());
        System.out.println("  cversion = " + stat.getCversion());
        System.out.println("  dataVersion = " + stat.getVersion());
        System.out.println("  aclVersion = " + stat.getAversion());
        printHex("ephemeralOwner", stat.getEphemeralOwner());
    }

    private static void printHex(String prefix, long value) {
        LOG.info(String.format("  %s = %#016x", prefix, value));
    }

    public static String humanReadableUnits(long bytes) {
        return humanReadableUnits(
                bytes, new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.ROOT)));
    }

    /** Returns <code>size</code> in human-readable units (GB, MB, KB or bytes). */
    public static String humanReadableUnits(long bytes, DecimalFormat df) {
        if (bytes / ONE_GB > 0) {
            return df.format((float) bytes / ONE_GB) + " GB";
        } else if (bytes / ONE_MB > 0) {
            return df.format((float) bytes / ONE_MB) + " MB";
        } else if (bytes / ONE_KB > 0) {
            return df.format((float) bytes / ONE_KB) + " KB";
        } else {
            return bytes + " bytes";
        }
    }
}
