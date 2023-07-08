package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.graphics.*
import android.graphics.Matrix.ScaleToFit
import android.graphics.Shader.TileMode
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.ahmadullahpk.alldocumentreader.R

class imgRoundCorner : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}

    private var res = 0
    private var mScaleType: ScaleType? = null
    var cornerRadius = 0f
        private set
    var borderWidth = 0f
        private set
    var borderColors: ColorStateList? = null
        private set
    private var en = false
    private var image: Drawable? = null
    private var dimens: FloatArray? = null

    internal class SelectableRoundedCornerDrawable(
        private val bitmap: Bitmap?,
        resources: Resources
    ) : Drawable() {
        private val rectF2 = RectF()
        private var scaleWidth = 0
        private var scaleHeight = 0
        private val paint: Paint
        private val paint1: Paint
        private val rectF = RectF()
        private val rectF1 = RectF()
        private val shader: BitmapShader = BitmapShader(bitmap!!, TileMode.CLAMP, TileMode.CLAMP)
        private val radius = floatArrayOf(
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat
        )
        private val radius1 = floatArrayOf(
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat
        )
        private var rounded = false
        private var conversion = intBitsToFloat
        private var colorStateList = ColorStateList.valueOf(-16777216)
        private var scaleType = ScaleType.FIT_CENTER
        private val path = Path()
        private var state = false
        override fun isStateful(): Boolean {
            return colorStateList.isStateful
        }

        override fun onStateChange(iArr: IntArray): Boolean {
            val colorForState = colorStateList.getColorForState(iArr, 0)
            if (paint1.color == colorForState) {
                return super.onStateChange(iArr)
            }
            paint1.color = colorForState
            return true
        }

        private fun setMatrixScale(canvas: Canvas) {
            val clipBounds = canvas.clipBounds
            val matrix = canvas.matrix
            if (ScaleType.CENTER == scaleType) {
                rectF.set(clipBounds)
            } else if (ScaleType.CENTER_CROP == scaleType) {
                setMatrixScale(matrix)
                rectF.set(clipBounds)
            } else if (ScaleType.FIT_XY == scaleType) {
                val matrix2 = Matrix()
                matrix2.setRectToRect(rectF2, RectF(clipBounds), ScaleToFit.FILL)
                shader.setLocalMatrix(matrix2)
                rectF.set(clipBounds)
            } else if (ScaleType.FIT_START == scaleType || ScaleType.FIT_END == scaleType || ScaleType.FIT_CENTER == scaleType || ScaleType.CENTER_INSIDE == scaleType) {
                setMatrixScale(matrix)
                rectF.set(rectF2)
            } else if (ScaleType.MATRIX == scaleType) {
                setMatrixScale(matrix)
                rectF.set(rectF2)
            }
        }

        private fun setMatrixScale(matrix: Matrix) {
            val fArr = FloatArray(9)
            matrix.getValues(fArr)
            var i = 0
            while (true) {
                val fArr2 = radius
                if (i < fArr2.size) {
                    fArr2[i] = fArr2[i] / fArr[0]
                    i++
                } else {
                    return
                }
            }
        }

        private fun drawCanvas(canvas: Canvas) {
            val fArr = FloatArray(9)
            canvas.matrix.getValues(fArr)
            val f = fArr[0]
            val f2 = fArr[4]
            val f3 = fArr[2]
            val f4 = fArr[5]
            val width = rectF.width()
            val width2 = rectF.width()
            val f5 = conversion
            val f6 = width / (width2 + f5 + f5)
            val height = rectF.height()
            val height2 = rectF.height()
            val f7 = conversion
            val f8 = height / (height2 + f7 + f7)
            canvas.scale(f6, f8)
            if (ScaleType.FIT_START == scaleType || ScaleType.FIT_END == scaleType || ScaleType.FIT_XY == scaleType || ScaleType.FIT_CENTER == scaleType || ScaleType.CENTER_INSIDE == scaleType || ScaleType.MATRIX == scaleType) {
                val f9 = conversion
                canvas.translate(f9, f9)
            } else if (ScaleType.CENTER == scaleType || ScaleType.CENTER_CROP == scaleType) {
                canvas.translate(-f3 / (f6 * f), -f4 / (f8 * f2))
                canvas.translate(-(rectF.left - conversion), -(rectF.top - conversion))
            }
        }

        private fun drawCanvasFloat(canvas: Canvas) {
            val fArr = FloatArray(9)
            canvas.matrix.getValues(fArr)
            conversion = conversion * rectF.width() / (rectF.width() * fArr[0] - conversion * 2.0f)
            paint1.strokeWidth = conversion
            rectF1.set(rectF)
            val rectF = rectF1
            val f = conversion
            rectF.inset(-f / 2.0f, -f / 2.0f)
        }

        private fun convertTofloat() {
            var i = 0
            while (true) {
                val fArr = radius
                if (i < fArr.size) {
                    if (fArr[i] > intBitsToFloat) {
                        radius1[i] = fArr[i]
                        fArr[i] = fArr[i] - conversion
                    }
                    i++
                } else {
                    return
                }
            }
        }

        override fun draw(canvas: Canvas) {
            canvas.save()
            if (!state) {
                setMatrixScale(canvas)
                if (conversion > intBitsToFloat) {
                    drawCanvasFloat(canvas)
                    convertTofloat()
                }
                state = true
            }
            if (rounded) {
                if (conversion > intBitsToFloat) {
                    drawCanvas(canvas)
                    path.addOval(rectF, Path.Direction.CW)
                    canvas.drawPath(path, paint)
                    path.reset()
                    path.addOval(rectF1, Path.Direction.CW)
                    canvas.drawPath(path, paint1)
                } else {
                    path.addOval(rectF, Path.Direction.CW)
                    canvas.drawPath(path, paint)
                }
            } else if (conversion > intBitsToFloat) {
                drawCanvas(canvas)
                path.addRoundRect(rectF, radius, Path.Direction.CW)
                canvas.drawPath(path, paint)
                path.reset()
                path.addRoundRect(rectF1, radius1, Path.Direction.CW)
                canvas.drawPath(path, paint1)
            } else {
                path.addRoundRect(rectF, radius, Path.Direction.CW)
                canvas.drawPath(path, paint)
            }
            canvas.restore()
        }

        fun castRadious(fArr: FloatArray?) {
            if (fArr != null) {
                if (fArr.size == 8) {
                    for (i in fArr.indices) {
                        radius[i] = fArr[i]
                    }
                    return
                }
                throw ArrayIndexOutOfBoundsException("radii[] needs 8 values")
            }
        }

        override fun getOpacity(): Int {
            val bitmap = bitmap
            return if (bitmap == null || bitmap.hasAlpha() || paint.alpha < 255) PixelFormat.TRANSLUCENT else PixelFormat.OPAQUE
        }

        override fun setAlpha(i: Int) {
            paint.alpha = i
            invalidateSelf()
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
            invalidateSelf()
        }

        override fun setDither(z: Boolean) {
            paint.isDither = z
            invalidateSelf()
        }

        override fun setFilterBitmap(z: Boolean) {
            paint.isFilterBitmap = z
            invalidateSelf()
        }

        override fun getIntrinsicWidth(): Int {
            return scaleWidth
        }

        override fun getIntrinsicHeight(): Int {
            return scaleHeight
        }

        fun mo12177a(f: Float) {
            conversion = f
            paint1.strokeWidth = f
        }

        fun setColorState(colorStateList: ColorStateList?) {
            if (colorStateList == null) {
                conversion = intBitsToFloat
                this.colorStateList = ColorStateList.valueOf(0)
                paint1.color = 0
                return
            }
            this.colorStateList = colorStateList
            paint1.color = this.colorStateList.getColorForState(getState(), -16777216)
        }

        fun mo12180a(z: Boolean) {
            rounded = z
        }

        fun setScaleType(scaleType: ScaleType?) {
            if (scaleType != null) {
                this.scaleType = scaleType
            }
        }

        companion object {
            fun m16121a(bitmap: Bitmap?, resources: Resources): SelectableRoundedCornerDrawable? {
                return bitmap?.let { SelectableRoundedCornerDrawable(it, resources) }
            }

            fun setDrawable(drawable: Drawable?, resources: Resources): Drawable? {
                if (drawable == null || drawable is SelectableRoundedCornerDrawable) {
                    return drawable
                }
                if (drawable is LayerDrawable) {
                    val layerDrawable = drawable
                    val numberOfLayers = layerDrawable.numberOfLayers
                    for (i in 0 until numberOfLayers) {
                        layerDrawable.setDrawableByLayerId(
                            layerDrawable.getId(i),
                            setDrawable(layerDrawable.getDrawable(i), resources)
                        )
                    }
                    return layerDrawable
                }
                val a = setDrawableBitmap(drawable)
                return a?.let { SelectableRoundedCornerDrawable(it, resources) } ?: drawable
            }

            fun setDrawableBitmap(drawable: Drawable?): Bitmap? {
                var bitmap: Bitmap? = null
                if (drawable == null) {
                    return null
                }
                if (drawable is BitmapDrawable) {
                    return drawable.bitmap
                }
                try {
                    val createBitmap = Bitmap.createBitmap(
                        Math.max(drawable.intrinsicWidth, 2),
                        Math.max(drawable.intrinsicHeight, 2),
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(createBitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bitmap = createBitmap
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }
                return bitmap
            }
        }

        init {
            if (bitmap != null) {
                scaleWidth = bitmap.getScaledWidth(resources.displayMetrics)
                scaleHeight = bitmap.getScaledHeight(resources.displayMetrics)
            } else {
                scaleHeight = -1
                scaleWidth = -1
            }
            rectF2[intBitsToFloat, intBitsToFloat, scaleWidth.toFloat()] = scaleHeight.toFloat()
            paint = Paint(1)
            paint.style = Paint.Style.FILL
            paint.shader = shader
            paint1 = Paint(1)
            paint1.style = Paint.Style.STROKE
            paint1.color = colorStateList.getColorForState(getState(), -16777216)
            paint1.strokeWidth = conversion
        }
    }

    @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet?, i: Int = 0) : super(
        context,
        attributeSet,
        i
    ) {
        res = 0
        mScaleType = ScaleType.FIT_CENTER
        cornerRadius = intBitsToFloat
        borderWidth = intBitsToFloat
        borderColors = ColorStateList.valueOf(-16777216)
        en = false
        dimens = floatArrayOf(
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat,
            intBitsToFloat
        )
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.RoundedCornerImageView, i, 0)
        val i2 = obtainStyledAttributes.getInt(0, -1)
        if (i2 >= 0) {
            scaleType = SCALE_TYPES[i2]
        }
        cornerRadius = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.RoundedCornerImageView_left_top_corner_radius,
            2
        ).toFloat()
        var dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.RoundedCornerImageView_right_top_corner_radius,
            2
        ).toFloat()
        var dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.RoundedCornerImageView_left_bottom_corner_radius,
            2
        ).toFloat()
        var dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.RoundedCornerImageView_right_bottom_corner_radius,
            2
        ).toFloat()
        val dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.RoundedCornerImageView_corner_radius,
            2
        ).toFloat()
        require(!(cornerRadius < intBitsToFloat || dimensionPixelSize < intBitsToFloat || dimensionPixelSize2 < intBitsToFloat || dimensionPixelSize3 < intBitsToFloat)) { "radius values cannot be negative." }
        if (dimensionPixelSize4.toDouble() >= longBitsToDouble) {
            if (dimensionPixelSize4 > intBitsToFloat) {
                cornerRadius = obtainStyledAttributes.getDimensionPixelSize(
                    R.styleable.RoundedCornerImageView_corner_radius,
                    0
                ).toFloat()
                dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(
                    R.styleable.RoundedCornerImageView_corner_radius,
                    0
                ).toFloat()
                dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(
                    R.styleable.RoundedCornerImageView_corner_radius,
                    0
                ).toFloat()
                dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(
                    R.styleable.RoundedCornerImageView_corner_radius,
                    0
                ).toFloat()
            }
            val f = cornerRadius
            dimens = floatArrayOf(
                f,
                f,
                dimensionPixelSize,
                dimensionPixelSize,
                dimensionPixelSize3,
                dimensionPixelSize3,
                dimensionPixelSize2,
                dimensionPixelSize2
            )
            borderWidth = obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.RoundedCornerImageView_border_width,
                1
            )
                .toFloat()
            if (borderWidth >= intBitsToFloat) {
                borderColors =
                    obtainStyledAttributes.getColorStateList(R.styleable.RoundedCornerImageView_border_color)
                if (borderColors == null) {
                    borderColors = ColorStateList.valueOf(-16777216)
                }
                en = obtainStyledAttributes.getBoolean(
                    R.styleable.RoundedCornerImageView_oval,
                    false
                )
                obtainStyledAttributes.recycle()
                getImage()
                return
            }
            throw IllegalArgumentException("border width cannot be negative.")
        } else {
            throw IllegalArgumentException("radius values cannot be negative.")
        }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun getScaleType(): ScaleType {
        return mScaleType!!
    }

    override fun setScaleType(scaleType: ScaleType) {
        super.setScaleType(scaleType)
        mScaleType = scaleType
        getImage()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        res = 0
        image = SelectableRoundedCornerDrawable.setDrawable(drawable, resources)
        super.setImageDrawable(image)
        getImage()
    }

    override fun setImageBitmap(bitmap: Bitmap) {
        res = 0
        image = SelectableRoundedCornerDrawable.m16121a(bitmap, resources)
        super.setImageDrawable(image)
        getImage()
    }

    override fun setImageResource(i: Int) {
        if (res != i) {
            res = i
            image = findDrawable()
            super.setImageDrawable(image)
            getImage()
        }
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        setImageDrawable(drawable)
    }

    private fun findDrawable(): Drawable? {
        val resources = resources
        var drawable: Drawable? = null
        if (resources == null) {
            return null
        }
        val i = res
        if (i != 0) {
            try {
                drawable = resources.getDrawable(i)
            } catch (e: NotFoundException) {
                val sb = StringBuilder()
                sb.append("Unable to find resource: ")
                sb.append(res)
                res = 0
            }
        }
        return SelectableRoundedCornerDrawable.setDrawable(drawable, getResources())
    }

    private fun getImage() {
        val drawable = image
        if (drawable != null) {
            (drawable as SelectableRoundedCornerDrawable).setScaleType(mScaleType)
            (image as SelectableRoundedCornerDrawable?)!!.castRadious(dimens)
            (image as SelectableRoundedCornerDrawable?)!!.mo12177a(borderWidth)
            (image as SelectableRoundedCornerDrawable?)!!.setColorState(borderColors)
            (image as SelectableRoundedCornerDrawable?)!!.mo12180a(en)
        }
    }

    fun setCornerRadiiDP(f: Float) {
        val f2 = f * resources.displayMetrics.density
        dimens = floatArrayOf(f2, f2, f2, f2, f2, f2, f2, f2)
        getImage()
    }

    fun setBorderWidthDP(f: Float) {
        val f2 = resources.displayMetrics.density * f
        if (Math.abs(borderWidth - f2).toDouble() >= 1.0E-4) {
            borderWidth = f2
            getImage()
            invalidate()
        }
    }

    var borderColor: Int
        get() = borderColors!!.defaultColor
        set(i) {
            setBorderColor(ColorStateList.valueOf(i))
        }

    fun setBorderColor(colorStateList: ColorStateList?) {
        var colorStateList = colorStateList
        if (borderColors != colorStateList) {
            if (colorStateList == null) {
                colorStateList = ColorStateList.valueOf(-16777216)
            }
            borderColors = colorStateList
            getImage()
            if (borderWidth > intBitsToFloat) {
                invalidate()
            }
        }
    }

    fun setOval(z: Boolean) {
        en = z
        getImage()
        invalidate()
    }

    companion object {
        val intBitsToFloat = java.lang.Float.intBitsToFloat(1)
        val longBitsToDouble = java.lang.Double.longBitsToDouble(1)
        private val SCALE_TYPES = arrayOf(
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
        )
    }
}