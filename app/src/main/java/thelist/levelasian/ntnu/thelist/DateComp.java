package thelist.levelasian.ntnu.thelist;

import java.util.Comparator;

/**
 * Created by hk on 06.03.15.
 */
public class DateComp implements Comparator<Party> {
    @Override
    public int compare(Party o1, Party o2) {
        return o1.getDateTime().compareTo(o2.getDateTime());
    }
}


