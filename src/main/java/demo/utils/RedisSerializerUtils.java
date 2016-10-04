package demo.utils;

/**
 * @author xp
 */
public class RedisSerializerUtils {

    static final byte[] EMPTY_ARRAY = new byte[0];

    static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

}
