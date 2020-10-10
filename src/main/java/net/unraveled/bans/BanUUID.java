package net.unraveled.bans;

import java.util.SplittableRandom;
import java.util.UUID;

public class BanUUID {
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMSET = "0123456789";

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
