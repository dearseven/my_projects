package chat.sh.orz.cyan.drawsome

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * save方法的google官方解释
 * Saves the current matrix and clip onto a private stack. Subsequent
 * calls to translate,scale,rotate,skew,concat or clipRect,clipPath
 * will all operate as usual, but when the balancing call to restore()
 * is made, those calls will be forgotten, and the settings that existed
 * before the save() will be reinstated.
 *
 * @return The value to pass to restoreToCount() to balance this save()
 */

/**
 * restore方法的google官方解释
 * This call balances a previous call to save(), and is used to remove all
 * modifications to the matrix/clip state since the last save call. It is
 * an error to call restore() more times than save() was called.
 */

/**
 * 我认为save一个是保存坐标系，而一个是保存好现在画的东西(貌似不是，其实画好了画布再一动也不会变)，不管画布怎么变，上一步的已经save了
 * 1，save和resotre只是控制坐标系特征的，restore只会把坐标系特征还原，不会清除save之后绘制的元素。
 * 2，对canvas做旋转缩放之类的操作，不会对坐标系本身产生影响，坐标原点还是在view的左上角。
 * 3,我发现要先处理画布的坐标大小旋转角再画【然后save】，如果先画在调整画布，貌似是不对的
 */
/**
 * 用canvas画东西最大的难度是往往是要调整画布的位置，角度再画，
 * 现实中其实我们可以先画好，再画布调整位置和角度，处理的先后顺序不一样，所以往往一下子转不过来
 * 如果有点晕，可以先自己在现实中画一个，比如我要画一个向右的箭头，其实我先可以在纸上画一个向上的，然后转90度就是了
 * 那么，我就可以知道纸上各个线段的起点和终点的位置（画的时候当然知道位置），然后我在安卓上做，就是先旋转画布
 * 然后画线段的时候，就用我在纸上作画的时候的线段的起点终点位置，就可以了，只是和现实处理的顺序反了一下而已
 */
class DrawSomeView : View {
    var paint = Paint();
    var width = 0f
    var height = 0f

    companion object {
        val TAG = "DrawSomeView"
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context?) : super(context) {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //保存初始状态
        val save1 = canvas!!.save()
        Log.i(TAG, "save1=${save1}")
        //
        val radius = 50f
        val centerY = height - radius
        //画左下角的圆
        paint.setStyle(Paint.Style.FILL)
        paint.setColor(Color.BLUE)
        paint.setAntiAlias(true)
        Log.i(TAG, "centerY=${centerY}")
        canvas!!.drawCircle(radius, centerY, radius, paint);
        //画右下方的
        paint.setColor(Color.YELLOW)
        //先把画布向右移动width - radius
        canvas!!.translate(width - radius, 0f)
        //因为画布移动了 虽然我x还是0，但是实际上要考虑画布平移了width - radius，所以出现再右下角
        canvas!!.drawCircle(0f, centerY, radius, paint);
        //保存画布,这个就是保存我 平移了width-radius的画布,
        val save2 = canvas!!.save()
        Log.i(TAG, "save2=${save2}")
        //然后我返回没有平移width-radius的save1状态,在左上加一个红的圆形
        canvas!!.restoreToCount(save1)
        paint.setColor(Color.RED)
        canvas!!.drawCircle(radius, radius, radius, paint)//正好cx cy(圆心)都是radius为坐标,所以这里就出现了3个radius
        //因为我返回了save1(初始状态,坐标从0 0开始),所以没有save2了,这里我已经是返回了没有平移的状态
        //那么既然这里我就试试向上平移y以后,再在右上角画黑圆形
        //canvas!!.scale(1.0f, 2.0f)
        //向上平移哦！！！
        canvas!!.translate(0f, -(height - 2 * radius))//哈哈哈 因为要画一个圆嘛 所以我这里留2个半径，跟平移x有点差别
        paint.setColor(Color.BLACK)
        //cx没什么问题本身就是width-radius（因为画在右边），cy因为一开始我向上平移了(height - 2*radius)
        //所以其实右上角的黑色圆形画在的是canvas的底部，所以cy的坐标变成了高度-半径，如果不平移cy是0(top)+radius
        canvas!!.drawCircle(width - radius, height - radius, radius, paint)
        val save3 = canvas!!.save()//这里因为我前面restoreToCount（save1），所以savecount=1哦，所以没有save2啦
        Log.i(TAG, "save3=${save3}")
        //所以我们理解，所谓平移什么的只是针对画布自己的位置，但是画在画布的内容的位置其实不影响的
        //但是由于画布平移了所以看起来画中的东西的为位置也变了
        //其实很好理解 ，比如那个黑圆 如果不平移画布，这个点其实是画在了右下角
        //但是由于我把画布向上平移了height - 2*radius，所以看起来就到了右上角
        //------------------------------------------------------------------------------------
        //-----------旋转同理，因为只有save3了，那么我们的把画布移动回去，到x，y=0，0，角度也是0
        canvas!!.translate(0f, (height - 2 * radius))
        paint.setColor(Color.CYAN)
        canvas!!.drawCircle(width / 2, height / 2, radius, paint)//画布移动回去了在中心画一个点
        val save4 = canvas!!.save()//ok画布保存 xy=0，0 角度0
        //###----画一个向右箭头---，其实这里画了一个向上的箭头，不过画布按照中心转了90度###
        canvas.rotate(90f, width / 2, height / 2);   //以画布中心为旋转点
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        //左线、右线、中线， 把旋转后的当做新画布，但x, y 的坐标仍是原来方向的，但画出的图是旋转后的
        //可以想象为先在原始画布上画，画好后旋转
        paint.color = Color.RED
        canvas.drawLine(width / 2, 0f, 0f, height / 2, paint)
        paint.color = Color.GREEN
        canvas.drawLine(width / 2, 0f, width, height / 2, paint)
        paint.color = Color.BLUE
        canvas.drawLine(width / 2, 0f, width / 2, height, paint)
        canvas.restoreToCount(save4)//返回4 因为4其实和初始状态一样，所以前面旋转的九十度也没有了
        canvas!!.save()//保存
        //在中间画一个竖线
        paint.strokeWidth = 10f
        paint.color = Color.DKGRAY
        canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 + radius, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(TAG, "w=$w,h=$h")
        width = w.toFloat()
        height = h.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(TAG, "mw=${MeasureSpec.getSize(widthMeasureSpec)},mh=${MeasureSpec.getSize(heightMeasureSpec)}")
    }
}