package demo.utils;

/**
 * @author xp
 */
public class RedisSerializerUtils {

    public static final byte[] EMPTY_ARRAY = new byte[0];

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

}
