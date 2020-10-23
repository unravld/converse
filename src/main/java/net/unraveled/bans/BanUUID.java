package net.unraveled.bans;

import java.util.SplittableRandom;

/**
 * A custom ID generator using SplittableRandom and a charset.
 * This class has one static method which will return an 8 character ban id.
 * Formatting is as follows: A prefix of either P- or T- to signify either Permanent or Temporary ban.
 * Following the prefix is a 6 character 'identifier' which represents the actual Ban ID.
 */
public class BanUUID {
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMSET = "0123456789";

    /**
     * @param type The ban type, either temporary or permanent.
     * @return A new Ban ID in the form of an 8 character string.
     */
    public static String newBanID(BanType type) {
        StringBuilder sb = new StringBuilder();
        SplittableRandom random = new SplittableRandom();
        if (type == BanType.PERMANENT) {
            sb.append("P-");
        }
        if (type == BanType.TEMPORARY) {
            sb.append("T-");
        }

        for (int x = 0; x <= 3; x++) {
            sb.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
            sb.append(NUMSET.charAt(random.nextInt(NUMSET.length())));
        }
        return sb.toString();
    }
}
