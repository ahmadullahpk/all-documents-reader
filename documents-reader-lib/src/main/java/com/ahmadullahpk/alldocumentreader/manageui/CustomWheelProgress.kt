package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.os.SystemClock
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.ahmadullahpk.alldocumentreader.R
import kotlin.math.abs
import kotlin.math.min

class CustomWheelProgress(context: Context, attributeSet: AttributeSet?) :
    View(context, attributeSet) {
    private var circleRadius = 80
    private var fillRadius = false
    private var timeStartGrowing = java.lang.Double.longBitsToDouble(1)
    private var barSpinCycleTime = 1000.0
    private var barExtraLength = java.lang.Float.intBitsToFloat(1)
    private var barGrowingFromFront = true
    private var pausedTimeWithoutGrowing: Long = 0
    private var barWidth = 5
    private var rimWidth = 5

    //Colors (with defaults)
    private var pBarColor = -0x56000000
    private var rimColor = 0x00FFFFFF

    //Paints
    private val barPaint = Paint()
    private val rimPaint = Paint()
    private var circleBounds = RectF()
    private var spinSpeed = 270.0f
    private var current: Long = 0
    private var startAngle = 270.0f
    private var f12959v = java.lang.Float.intBitsToFloat(1)
    private var isSpinning = false

    internal class WheelSavedState : BaseSavedState {
        var mProgress = 0f
        var mTargetProgress = 0f
        var isSpinning = false
        var spinSpeed = 0f
        var barWidth = 0
        var barColor = 0
        var rimWidth = 0
        var rimColor = 0
        var circleRadius = 0

        constructor(parcelable: Parcelable?) : super(parcelable) {}
        private constructor(parcel: Parcel) : super(parcel) {
            mProgress = parcel.readFloat()
            mTargetProgress = parcel.readFloat()
            isSpinning = parcel.readByte().toInt() != 0
            spinSpeed = parcel.readFloat()
            barWidth = parcel.readInt()
            barColor = parcel.readInt()
            rimWidth = parcel.readInt()
            rimColor = parcel.readInt()
            circleRadius = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, i: Int) {
            super.writeToParcel(parcel, i)
            parcel.writeFloat(mProgress)
            parcel.writeFloat(mTargetProgress)
            parcel.writeByte(if (isSpinning) 1.toByte() else 0)
            parcel.writeFloat(spinSpeed)
            parcel.writeInt(barWidth)
            parcel.writeInt(barColor)
            parcel.writeInt(rimWidth)
            parcel.writeInt(rimColor)
            parcel.writeInt(circleRadius)
        }

        companion object {
            @JvmField
            val CREATOR: Creator<WheelSavedState?> = object : Creator<WheelSavedState?> {
                override fun createFromParcel(parcel: Parcel): WheelSavedState? {
                    return WheelSavedState(parcel)
                }

                override fun newArray(i: Int): Array<WheelSavedState?> {
                    return arrayOfNulls(i)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var paddingLeft = circleRadius + paddingLeft + paddingRight
        var paddingTop = circleRadius + paddingTop + paddingBottom
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode2 = MeasureSpec.getMode(heightMeasureSpec)
        val size2 = MeasureSpec.getSize(heightMeasureSpec)
        if (mode == 1073741824) {
            paddingLeft = size
        } else if (mode == Int.MIN_VALUE) {
            paddingLeft = min(paddingLeft, size)
        }
        if (mode2 == 1073741824 || mode == 1073741824) {
            paddingTop = size2
        } else if (mode2 == Int.MIN_VALUE) {
            paddingTop = min(paddingTop, size2)
        }
        setMeasuredDimension(paddingLeft, paddingTop)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setPadding(w, h)
        setUpPaints()
        invalidate()
    }

    private fun setUpPaints() {
        barPaint.color = pBarColor
        barPaint.isAntiAlias = true
        barPaint.style = Paint.Style.STROKE
        barPaint.strokeWidth = barWidth.toFloat()
        rimPaint.color = rimColor
        rimPaint.isAntiAlias = true
        rimPaint.style = Paint.Style.STROKE
        rimPaint.strokeWidth = rimWidth.toFloat()
    }

    private fun setPadding(i: Int, i2: Int) {
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        if (!fillRadius) {
            val i3 = i - paddingLeft - paddingRight
            val min = Math.min(
                Math.min(i3, i2 - paddingBottom - paddingTop),
                circleRadius * 2 - barWidth * 2
            )
            val i4 = (i3 - min) / 2 + paddingLeft
            val i5 = (i2 - paddingTop - paddingBottom - min) / 2 + paddingTop
            val i6 = barWidth
            circleBounds = RectF(
                (i4 + i6).toFloat(),
                (i5 + i6).toFloat(),
                (i4 + min - i6).toFloat(),
                (i5 + min - i6).toFloat()
            )
            return
        }
        val i7 = barWidth
        circleBounds = RectF(
            (paddingLeft + i7).toFloat(),
            (paddingTop + i7).toFloat(),
            (i - paddingRight - i7).toFloat(),
            (i2 - paddingBottom - i7).toFloat()
        )
    }

    private fun initAppProgressWheel(typedArray: TypedArray) {
        val displayMetrics = context.resources.displayMetrics
        barWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            barWidth.toFloat(),
            displayMetrics
        )
            .toInt()
        rimWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            rimWidth.toFloat(),
            displayMetrics
        )
            .toInt()
        circleRadius =
            typedArray.getDimension(R.styleable.ProgressWheel_circleRadius, circleRadius.toFloat())
                .toInt()
        fillRadius = typedArray.getBoolean(R.styleable.ProgressWheel_fillRadius, false)
        barWidth = typedArray.getDimension(R.styleable.ProgressWheel_barWidth, barWidth.toFloat())
            .toInt()
        rimWidth = typedArray.getDimension(R.styleable.ProgressWheel_rimWidth, rimWidth.toFloat())
            .toInt()
        spinSpeed =
            typedArray.getFloat(R.styleable.ProgressWheel_spinSpeed, spinSpeed / 360.0f) * 360.0f
        barSpinCycleTime =
            typedArray.getInt(R.styleable.ProgressWheel_barSpinCycleTime, barSpinCycleTime.toInt())
                .toDouble()
        pBarColor = typedArray.getColor(R.styleable.ProgressWheel_pBarColor, pBarColor)
        rimColor = typedArray.getColor(R.styleable.ProgressWheel_rimColor, rimColor)
        if (typedArray.getBoolean(R.styleable.ProgressWheel_progressIndeterminate, false)) {
            upDateProgress()
        }
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(circleBounds, 360.0f, 360.0f, false, rimPaint)
        var z = true
        if (isSpinning) {
            val uptimeMillis = SystemClock.uptimeMillis() - current
            val f = uptimeMillis.toFloat() * spinSpeed / 1000.0f
            updateBarLength(uptimeMillis)
            startAngle += f
            val f2 = startAngle
            if (f2 > 360.0f) {
                startAngle = f2 - 360.0f
            }
            current = SystemClock.uptimeMillis()
            canvas.drawArc(
                circleBounds,
                startAngle - 90.0f,
                barExtraLength + 40.0f,
                false,
                barPaint
            )
        } else {
            if (startAngle != f12959v) {
                startAngle = Math.min(
                    startAngle + (SystemClock.uptimeMillis() - current).toFloat() / 1000.0f * spinSpeed,
                    f12959v
                )
                current = SystemClock.uptimeMillis()
            } else {
                z = false
            }
            canvas.drawArc(circleBounds, -90.0f, startAngle, false, barPaint)
        }
        if (z) {
            invalidate()
        }
    }

    private fun updateBarLength(deltaTimeInMilliSeconds: Long) {
        val j2 = pausedTimeWithoutGrowing
        if (j2 >= 300) {
            timeStartGrowing += deltaTimeInMilliSeconds.toDouble()
            if (timeStartGrowing > barSpinCycleTime) {
                timeStartGrowing = java.lang.Double.longBitsToDouble(1)
                if (!barGrowingFromFront) {
                    pausedTimeWithoutGrowing = 0
                }
                barGrowingFromFront = !barGrowingFromFront
            }
            val cos = Math.cos((timeStartGrowing / barSpinCycleTime + 1.0) * 3.141592653589793)
                .toFloat() / 2.0f + 0.5f
            if (barGrowingFromFront) {
                barExtraLength = cos * 230.0f
                return
            }
            val f = (1.0f - cos) * 230.0f
            startAngle += barExtraLength - f
            barExtraLength = f
            return
        }
        pausedTimeWithoutGrowing = j2 + deltaTimeInMilliSeconds
    }

    fun upDateProgress() {
        current = SystemClock.uptimeMillis()
        isSpinning = true
        invalidate()
    }

    fun setInstantProgress(f: Float) {
        var f = f
        if (isSpinning) {
            startAngle = java.lang.Float.intBitsToFloat(1)
            isSpinning = false
        }
        if (f > 1.0f) {
            f -= 1.0f
        } else if (f < java.lang.Float.intBitsToFloat(1)) {
            f = java.lang.Float.intBitsToFloat(1)
        }
        if (abs(f - f12959v).toDouble() >= 1.0E-4) {
            f12959v = min(f * 360.0f, 360.0f)
            startAngle = f12959v
            current = SystemClock.uptimeMillis()
            invalidate()
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val wheelSavedState = WheelSavedState(super.onSaveInstanceState())
        wheelSavedState.mProgress = startAngle
        wheelSavedState.mTargetProgress = f12959v
        wheelSavedState.isSpinning = isSpinning
        wheelSavedState.spinSpeed = spinSpeed
        wheelSavedState.barWidth = barWidth
        wheelSavedState.barColor = pBarColor
        wheelSavedState.rimWidth = rimWidth
        wheelSavedState.rimColor = rimColor
        wheelSavedState.circleRadius = circleRadius
        return wheelSavedState
    }

    public override fun onRestoreInstanceState(parcelable: Parcelable) {
        if (parcelable !is WheelSavedState) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        super.onRestoreInstanceState(parcelable.superState)
        startAngle = parcelable.mProgress
        f12959v = parcelable.mTargetProgress
        isSpinning = parcelable.isSpinning
        spinSpeed = parcelable.spinSpeed
        barWidth = parcelable.barWidth
        pBarColor = parcelable.barColor
        rimWidth = parcelable.rimWidth
        rimColor = parcelable.rimColor
        circleRadius = parcelable.circleRadius
    }

    var progress: Float
        get() = if (isSpinning) {
            -1.0f
        } else startAngle / 360.0f
        set(f) {
            var f = f
            if (isSpinning) {
                startAngle = java.lang.Float.intBitsToFloat(1)
                isSpinning = false
            }
            if (f > 1.0f) {
                f -= 1.0f
            } else if (f < java.lang.Float.intBitsToFloat(1)) {
                f = java.lang.Float.intBitsToFloat(1)
            }
            if (Math.abs(f - f12959v) >= 1.0E-4f) {
                if (Math.abs(startAngle - f12959v) < 1.0E-4f) {
                    current = SystemClock.uptimeMillis()
                }
                f12959v = Math.min(f * 360.0f, 360.0f)
                invalidate()
            }
        }

    fun getCircleRadius(): Int {
        return circleRadius
    }

    fun setCircleRadius(i: Int) {
        circleRadius = i
        if (!isSpinning) {
            invalidate()
        }
    }

    fun getBarWidth(): Int {
        return barWidth
    }

    fun setBarWidth(i: Int) {
        barWidth = i
        if (!isSpinning) {
            invalidate()
        }
    }

    var barColor: Int
        get() = pBarColor
        set(i) {
            pBarColor = i
            setUpPaints()
            if (!isSpinning) {
                invalidate()
            }
        }

    fun getRimColor(): Int {
        return rimColor
    }

    fun setRimColor(i: Int) {
        rimColor = i
        setUpPaints()
        if (!isSpinning) {
            invalidate()
        }
    }

    fun getSpinSpeed(): Float {
        return spinSpeed / 360.0f
    }

    fun setSpinSpeed(f: Float) {
        spinSpeed = f * 360.0f
    }

    fun getRimWidth(): Int {
        return rimWidth
    }

    fun setRimWidth(i: Int) {
        rimWidth = i
        if (!isSpinning) {
            invalidate()
        }
    }

    companion object {
        private val TAG = CustomWheelProgress::class.java.simpleName
    }

    init {
        initAppProgressWheel(
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.ProgressWheel
            )
        )
    }
}