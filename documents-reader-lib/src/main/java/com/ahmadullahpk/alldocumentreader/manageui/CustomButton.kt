package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader.TileMode
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.ahmadullahpk.alldocumentreader.R
import com.ahmadullahpk.alldocumentreader.manageui.backgroundshadow.ShadowOpacity
import com.ahmadullahpk.alldocumentreader.manageui.backgroundshadow.ShadowSetup
import com.ahmadullahpk.alldocumentreader.util.FontCache

open class CustomButton : AppCompatButton {
    var textStartColor = -1
    var textEndColor = -1
    protected var startColor = -1
    private var boarderColor = -1
    protected var endColor = -1
    private var cornerRadius = 0
    protected var gradientType = 0
    private var icon = 0
    var buttonStrokeDrawable: GradientStrokeDrawable? = null
        protected set
    protected var roundedCorner = false
    protected var enableShadow = false
    private var buttonShadowColor = -1
    var shadowOffsetX = 0
    var shadowOffsetY = 0
    var buttonElevation = 0
    private var adjustDrawableLeft = false
    var iEnabled = false
        private set

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        if (!isInEditMode) {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.appButtonView, 0, 0)
            try {
                enableShadow =
                    obtainStyledAttributes.getBoolean(R.styleable.appButtonView_enableShadow, false)
                enableShadow()
                setFontStyle(
                    context,
                    obtainStyledAttributes.getInt(R.styleable.appButtonView_textStyle, 0)
                )
                startColor =
                    obtainStyledAttributes.getResourceId(R.styleable.appButtonView_startColor, -1)
                textStartColor = obtainStyledAttributes.getResourceId(
                    R.styleable.appButtonView_textStartColor,
                    -1
                )
                textEndColor =
                    obtainStyledAttributes.getResourceId(R.styleable.appButtonView_textEndColor, -1)
                endColor =
                    obtainStyledAttributes.getResourceId(R.styleable.appButtonView_endColor, -1)
                icon = obtainStyledAttributes.getResourceId(R.styleable.appButtonView_icon, -1)
                gradientType =
                    obtainStyledAttributes.getInt(R.styleable.appButtonView_gradientType, 0)
                adjustDrawableLeft = obtainStyledAttributes.getBoolean(
                    R.styleable.appButtonView_adjustDrawableLeft,
                    false
                )
                roundedCorner = obtainStyledAttributes.getBoolean(
                    R.styleable.appButtonView_roundedCorner,
                    false
                )
                val resources = getContext().resources
                if (roundedCorner) {
                    cornerRadius =
                        resources.getDimension(R.dimen.round_button_corner_radius).toInt()
                } else {
                    cornerRadius = obtainStyledAttributes.getDimension(
                        R.styleable.appButtonView_cornerRadius,
                        resources.getDimension(R.dimen.button_corner_radius)
                    ).toInt()
                }
                if (!(startColor == -1 || endColor == -1)) {
                    setGradientStyle(getColor(startColor), getColor(endColor), enableShadow)
                }
                buttonShadowColor =
                    obtainStyledAttributes.getResourceId(R.styleable.appButtonView_shadowColor, -1)
                if (buttonShadowColor != -1) {
                    buttonShadowColor = getColor(buttonShadowColor)
                }
                boarderColor =
                    obtainStyledAttributes.getResourceId(R.styleable.appButtonView_borderColor, -1)
                if (boarderColor != -1) {
                    boarderColor = getColor(boarderColor)
                }
                if (enableShadow) {
                    setPadding()
                    initShadow()
                } else if (!(startColor == -1 || endColor == -1)) {
                    setBackgroundGradient()
                }
                if (adjustDrawableLeft) {
                    gravity = Gravity.CENTER_VERTICAL or Gravity.START
                }
                if (icon != 0 && !TextUtils.isEmpty(text)) {
                    setIconLeft(icon)
                } else if (icon != 0 && TextUtils.isEmpty(text)) {
                    setIcon(icon)
                }
                if (!(textStartColor == -1 || textEndColor == -1)) {
                    gradient
                }
            } finally {
                obtainStyledAttributes.recycle()
            }
        }
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context!!, attributeSet, i
    ) {
        enableShadow()
    }

    override fun setText(charSequence: CharSequence, bufferType: BufferType) {
        super.setText(charSequence, bufferType)
        if (!TextUtils.isEmpty(charSequence)) {
            tag = charSequence
        }
    }

    private fun enableShadow() {
        if (enableShadow) {
            enableShadowOffset()
        }
    }

    private fun enableShadowOffset() {
        shadowOffsetX = context.resources.getDimension(R.dimen.button_shadow_offset_x)
            .toInt()
        shadowOffsetY = context.resources.getDimension(R.dimen.button_shadow_offset_y)
            .toInt()
        buttonElevation = context.resources.getDimension(R.dimen.button_elevation).toInt()
    }

    protected fun setPadding() {
        setPadding(
            paddingLeft + buttonElevation - shadowOffsetX,
            paddingTop + buttonElevation - shadowOffsetY,
            paddingRight + shadowOffsetX + buttonElevation,
            paddingBottom + shadowOffsetY + buttonElevation
        )
    }

    private fun initShadow() {
        var iArr = IntArray(0)
        val i = startColor
        if (i != -1) {
            val i2 = endColor
            if (i2 != -1) {
                iArr = intArrayOf(i, i2)
            }
        }
        if (buttonShadowColor == -1) {
            val i3 = startColor
            if (i3 != -1) {
                buttonShadowColor = ColorUtils.setAlphaComponent(i3, 120)
            }
        }
        if (boarderColor == -1) {
            val i4 = startColor
            if (i4 != -1) {
                boarderColor = ColorUtils.setAlphaComponent(i4, 80)
            }
        }
        val background = background
        val b = ShadowOpacity.Builder().setGradientColorArray(iArr)
            .setColor(if (background is ColorDrawable) background.color else -1)
        var i5 = buttonShadowColor
        if (i5 == -1) {
            i5 = getColor(R.color.card_default_shadow_color)
        }
        val c = b.setOShadowColor(i5)
        var i6 = boarderColor
        if (i6 == -1) {
            i6 = getColor(R.color.card_default_border_color)
        }
        ShadowSetup.setUpShadow(
            this, c.setShadowColor(i6).setRadius(cornerRadius).setShadowRadius(buttonElevation).setOffsetX(
                shadowOffsetX
            ).setOffsetY(shadowOffsetY)
        )
    }

    fun setFontStyle(context: Context, i: Int) {
        val typeface: Typeface = when (i) {
            0 -> FontCache.setFontStyle(context, "fonts/gothamssm_bold.otf")
            1 -> FontCache.setFontStyle(context, "fonts/gothamssm_light.otf")
            2 -> FontCache.setFontStyle(context, "fonts/gothamssm_medium.otf")
            4 -> FontCache.setFontStyle(context, "fonts/gothamssm_black.otf")
            5 -> Typeface.create(
                FontCache.setFontStyle(context, "fonts/gothamssm_book.otf"),
                Typeface.ITALIC
            )
            3 -> FontCache.setFontStyle(context, "fonts/gothamssm_book.otf")
            else -> FontCache.setFontStyle(context, "fonts/gothamssm_book.otf")
        }
        setTypeface(typeface)
    }

    private fun setBackgroundCompat(drawable: Drawable) {
        background = drawable
    }

    private fun setBackgroundGradient() {
        setBackgroundResource(0)
        buttonStrokeDrawable = setStrokeGradient(startColor, endColor, cornerRadius, gradientType)
        setBackgroundCompat(buttonStrokeDrawable!!.gradientDrawable)
    }

    private fun getColor(i: Int): Int {
        return resources.getColor(i)
    }

    protected fun setIcon(i: Int) {
        if (i != -1) {
            post {
                val b = AppCompatResources.getDrawable(this@CustomButton.context, i)
                val measuredWidth = (this@CustomButton.measuredWidth - b!!.intrinsicWidth) / 2
                this@CustomButton.setCompoundDrawablesWithIntrinsicBounds(b, null, null, null)
                val appButton = this@CustomButton
                appButton.setPadding(
                    measuredWidth,
                    -(appButton.buttonElevation + shadowOffsetY) / 2,
                    0,
                    0
                )
            }
        }
    }

    fun setIcon(drawable: Drawable?) {
        if (drawable != null) {
            post {
                val measuredWidth = (this@CustomButton.measuredWidth - drawable.intrinsicWidth) / 2
                this@CustomButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                val appButton = this@CustomButton
                appButton.setPadding(
                    measuredWidth,
                    -(appButton.buttonElevation + shadowOffsetY) / 2,
                    0,
                    0
                )
            }
        }
    }

    fun setDrawable(drawable: Drawable?, i: Int) {
        if (drawable != null) {
            post {
                val intrinsicWidth = (i - drawable.intrinsicWidth) / 2
                this@CustomButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                val appButton = this@CustomButton
                appButton.setPadding(
                    intrinsicWidth,
                    -(appButton.buttonElevation + shadowOffsetY) / 2,
                    0,
                    0
                )
            }
        }
    }

    fun setText(drawable: Drawable?, i: Int, str: String?) {
        if (TextUtils.isEmpty(str)) {
            setDrawable(drawable, i)
        } else if (drawable != null) {
            post {
                val dimensionPixelSize =
                    this@CustomButton.resources.getDimensionPixelSize(R.dimen.margin_normal)
                this@CustomButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                this@CustomButton.text = str
                val appButton = this@CustomButton
                appButton.setTextColor(
                    ContextCompat.getColor(
                        appButton.context,
                        R.color.title_info_color
                    )
                )
                val appButton2 = this@CustomButton
                appButton2.setPadding(
                    dimensionPixelSize,
                    -(appButton2.buttonElevation + shadowOffsetY) / 2,
                    dimensionPixelSize,
                    0
                )
                this@CustomButton.compoundDrawablePadding = dimensionPixelSize
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (adjustDrawableLeft) {
            val width = (width - paddingLeft - paddingRight).toFloat()
            val transformationMethod = transformationMethod
            val measureText = paint.measureText(
                (if (transformationMethod != null) transformationMethod.getTransformation(
                    text, this
                ) else text).toString()
            )
            var i = 0
            val drawable = compoundDrawables[0]
            val intrinsicWidth = drawable?.intrinsicWidth ?: 0
            if (measureText > java.lang.Float.intBitsToFloat(1) && drawable != null) {
                i = compoundDrawablePadding
            }
            canvas.translate(
                (width - (measureText + intrinsicWidth.toFloat() + i.toFloat())) / 2.0f,
                java.lang.Float.intBitsToFloat(1)
            )
        }
        super.onDraw(canvas)
    }

    protected fun setStrokeGradient(i: Int, i2: Int, i3: Int, i4: Int): GradientStrokeDrawable {
        val strokeGradientDrawable = GradientStrokeDrawable(GradientDrawable())
        strokeGradientDrawable.gradientDrawable.shape = GradientDrawable.RECTANGLE
        strokeGradientDrawable.setGradient(i, i2, i4)
        strokeGradientDrawable.setGradientCornerRadius(i3.toFloat())
        return strokeGradientDrawable
    }

    protected fun setIconLeft(i: Int) {
        if (i != -1) {
            setCompoundDrawablesWithIntrinsicBounds(i, 0, 0, 0)
        }
    }

    fun setGradientColors(i: Int, i2: Int) {
        setGradientStyle(i, i2, true)
    }

    fun setGradientStyle(startColor: Int, endColor: Int, enableShadow: Boolean) {
        this.startColor = ColorUtils.blendARGB(startColor, Color.parseColor(textColor), 0.1f)
        this.endColor = ColorUtils.blendARGB(endColor, Color.parseColor(textColor), 0.1f)
        boarderColor = -1
        if (this.enableShadow != enableShadow) {
            enableShadowOffset()
            setPadding()
        }
        this.enableShadow = enableShadow
        if (this.enableShadow) {
            initShadow()
        } else {
            setBackgroundGradient()
        }
        invalidate()
    }

    fun setBackgroundBorderColor(i: Int) {
        boarderColor = i
        if (!enableShadow) {
            enableShadowOffset()
            setPadding()
        }
        initShadow()
        invalidate()
    }

    fun setUpShadow() {
        enableShadow = true
        initShadow()
        invalidate()
    }

    fun disableShadow() {
        setBackgroundDrawable(null)
        enableShadow = false
    }

    fun setCornerRadius(i: Int) {
        cornerRadius = i
        invalidate()
    }

    override fun onSizeChanged(i: Int, i2: Int, i3: Int, i4: Int) {
        super.onSizeChanged(i, i2, i3, i4)
        if (enableShadow) {
            initShadow()
        }
    }

    private val gradient: Unit
        get() {
            val f: Float
            val f2: Float
            val measureText = paint.measureText(text.toString())
            if (gradientType == 0) {
                f2 = measureText
                f = java.lang.Float.intBitsToFloat(1)
            } else {
                f = height.toFloat()
                f2 = java.lang.Float.intBitsToFloat(1)
            }
            setTextColor(ContextCompat.getColor(context, R.color.black))
            val paint = paint
            val linearGradient = LinearGradient(
                java.lang.Float.intBitsToFloat(1),
                java.lang.Float.intBitsToFloat(1),
                f2,
                f,
                textStartColor,
                textEndColor,
                TileMode.CLAMP
            )
            paint.shader = linearGradient
        }

    fun setTextGradient(i: Int, i2: Int) {
        textStartColor = i
        textEndColor = i2
        gradient
        invalidate()
    }

    fun setCollapsible(z: Boolean) {
        iEnabled = z
    }

    companion object {
        var textColor = "#ffffff"
    }
}