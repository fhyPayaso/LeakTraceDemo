package com.bytedance.fanhongyu.scaffold;

import com.bytedance.fanhongyu.scaffold.test.DB;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        new DB().db();
    }
}