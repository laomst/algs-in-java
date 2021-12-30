package site.laomst.learn.dsag;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.adt.list.Array;

public class Tester {

    @Test
    public void arrayTest() {
        Array<String> stringArray = new Array<>();
        System.out.println(stringArray);
    }
}
