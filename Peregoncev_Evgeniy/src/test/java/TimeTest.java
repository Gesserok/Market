import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
/**
 * Created by ENIAC on 16.11.2017.
 */
public class TimeTest {

    @Test
    public void printTime() throws Exception {

        Time testTime = new Time();

        Date testTimeNow = new Date();
        SimpleDateFormat time = new SimpleDateFormat("'Time: 'H:mm:ss'\nDate: 'yyyy.MM.dd");

        Assert.assertEquals(time.format(testTimeNow), testTime.printTime());

    }

}