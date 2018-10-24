package chat.sh.orz.cyan.junit

import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class CalculatorTest {
    var cal: Calculator? = null
    private val a = 8
    private val b = 2
    @Before
    fun _init() {
        cal = Calculator()
    }

    @After
    fun _end() {
        cal = null;
    }

    @Test
    fun add() {
        val result = cal!!.add(a, b);
        // 第一个参数："sum(a, b)" 打印的tag信息 （可省略）
        // 第二个参数： 4 期望得到的结果
        // 第三个参数  result：实际返回的结果
        // 第四个参数  0 误差范围（可省略）
        assertEquals("sum(a, b)", 10, result);
    }

    @Test
    fun minus() {
        val result = cal!!.minus(a, b);
        assertEquals("minus(a, b)", 6, result);
    }

    @Test
    fun times() {
        val result = cal!!.times(a, b);
        assertEquals("times(a, b)", 16, result);
    }

    @Test
    fun divide() {
        val result = cal!!.divide(a, b);
        assertEquals("divide(a, b)", 4, result);
    }
}