package com.connexal.raveldatapack.api.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class PDCUtil {
    public static final PersistentDataType<byte[], double[]> DOUBLE_ARRAY = new DoubleArray();
    public static final PersistentDataType<byte[], String[]> STRING_ARRAY = new StringArray(StandardCharsets.UTF_8);
    public static final PersistentDataType<byte[], java.util.UUID> UUID = new UUIDDataType();

    public static <Z> Z getData(PersistentDataHolder holder, PersistentDataType<?, Z> type, NamespacedKey key) {
        PersistentDataContainer container = holder.getPersistentDataContainer();
        if (container.has(key, type)) {
            return container.get(key, type);
        }
        return null;
    }

    public static void setData(PersistentDataHolder holder, NamespacedKey key, Object value) {
        PersistentDataContainer container = holder.getPersistentDataContainer();

        if (value instanceof Boolean i) {
            container.set(key, PersistentDataType.INTEGER, i ? 1 : 0);
        } else if (value instanceof Double i) {
            container.set(key, PersistentDataType.DOUBLE, i);
        } else if (value instanceof Integer i) {
            container.set(key, PersistentDataType.INTEGER, i);
        } else if (value instanceof String[] i) {
            container.set(key, STRING_ARRAY, i);
        } else if (value instanceof double[] i) {
            container.set(key, DOUBLE_ARRAY, i);
        } else if (value instanceof UUID i) {
            container.set(key, UUID, i);
        } else {
            String i = value.toString();
            container.set(key, PersistentDataType.STRING, i);
        }
    }

    public static void setData(ItemStack item, NamespacedKey key, Object value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        setData(meta, key, value);
        item.setItemMeta(meta);
    }

    public static String getStringData(PersistentDataHolder holder, NamespacedKey key) {
        return getData(holder, PersistentDataType.STRING, key);
    }

    public static void removeData(PersistentDataHolder holder, NamespacedKey key) {
        PersistentDataContainer container = holder.getPersistentDataContainer();
        container.remove(key);
    }

    public static void removeData(ItemStack item, NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        removeData(meta, key);
        item.setItemMeta(meta);
    }

    public static String getStringData(ItemStack item, NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        return meta == null ? null : getStringData(meta, key);
    }

    public static class DoubleArray implements PersistentDataType<byte[], double[]> {
        @Override
        public Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @Override
        public Class<double[]> getComplexType() {
            return double[].class;
        }

        @Override
        public byte[] toPrimitive(double[] complex, PersistentDataAdapterContext context) {
            ByteBuffer bb = ByteBuffer.allocate(complex.length * 8);
            for (double d : complex) {
                bb.putDouble(d);
            }
            return bb.array();
        }

        @Override
        public double[] fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
            ByteBuffer bb = ByteBuffer.wrap(primitive);
            DoubleBuffer dbuf = bb.asDoubleBuffer(); // Make DoubleBuffer
            double[] a = new double[dbuf.remaining()]; // Make an array of the correct size
            dbuf.get(a);

            return a;
        }
    }

    public static class StringArray implements PersistentDataType<byte[], String[]> {
        private final Charset charset;

        public StringArray(Charset charset) {
            this.charset = charset;
        }

        @Override
        public Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @Override
        public Class<String[]> getComplexType() {
            return String[].class;
        }

        @Override
        public byte[] toPrimitive(String[] strings, PersistentDataAdapterContext itemTagAdapterContext) {
            byte[][] allStringBytes = new byte[strings.length][];
            int total = 0;
            for (int i = 0; i < allStringBytes.length; i++) {
                byte[] bytes = strings[i].getBytes(charset);
                allStringBytes[i] = bytes;
                total += bytes.length;
            }

            ByteBuffer buffer = ByteBuffer.allocate(total + allStringBytes.length * 4); // stores integers
            for (byte[] bytes : allStringBytes) {
                buffer.putInt(bytes.length);
                buffer.put(bytes);
            }

            return buffer.array();
        }

        @Override
        public String[] fromPrimitive(byte[] bytes, PersistentDataAdapterContext itemTagAdapterContext) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            ArrayList<String> list = new ArrayList<>();

            while (buffer.remaining() > 0) {
                if (buffer.remaining() < 4)
                    break;
                int stringLength = buffer.getInt();
                if (buffer.remaining() < stringLength)
                    break;

                byte[] stringBytes = new byte[stringLength];
                buffer.get(stringBytes);

                list.add(new String(stringBytes, charset));
            }

            return list.toArray(new String[0]);
        }
    }

    public static class UUIDDataType implements PersistentDataType<byte[], UUID> {
        @Override
        public Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @Override
        public Class<UUID> getComplexType() {
            return UUID.class;
        }

        @Override
        public byte[] toPrimitive(UUID complex, PersistentDataAdapterContext context) {
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(complex.getMostSignificantBits());
            bb.putLong(complex.getLeastSignificantBits());
            return bb.array();
        }

        @Override
        public UUID fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
            ByteBuffer bb = ByteBuffer.wrap(primitive);
            long firstLong = bb.getLong();
            long secondLong = bb.getLong();
            return new UUID(firstLong, secondLong);
        }
    }
}
