package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.ahmadullahpk.alldocumentreader.R
import com.ahmadullahpk.alldocumentreader.util.ImageBitmaps
import com.ahmadullahpk.alldocumentreader.util.ViewUtils

class CustomFrameLayout : FrameLayout {
    private var customFontTextViewTitle: CustomFontTextView? = null
    lateinit var customFontTextViewDescription: CustomFontTextView
    lateinit var btnBackNav: FrameLayout
    lateinit var optionalNav: FrameLayout
    lateinit var optionalNav2: FrameLayout
    lateinit var optionalNav3: FrameLayout
    private var roundNavButton: CustomButton? = null
    private var roundedOptionalNav: CustomButton? = null
    private var roundedOptionalNav2: CustomButton? = null
    private var roundedOptionalNav3: CustomButton? = null
    private var viewToolbar: View? = null
    private var customView: LinearLayout? = null
    private val isActive = true

    class Builder(private val customFrameLayout: CustomFrameLayout, private val context: Context) {
        private val toolBarNavHeight: Int =
            context.resources.getDimensionPixelSize(R.dimen.toolbar_nav_height)

        fun setToolbarTitle(i: Int, i2: Int): Builder {
            customFrameLayout.customFontTextViewTitle!!.setTextColor(ContextCompat.getColor(context, i2))
            customFrameLayout.customFontTextViewTitle!!.text = context.getString(i)
            return this
        }

        fun setToolbarTitle(str: String?, i: Int): Builder {
            if (customFrameLayout.customFontTextViewTitle!!.visibility == GONE) {
                customFrameLayout.customFontTextViewTitle!!.visibility = VISIBLE
            }
            customFrameLayout.customFontTextViewTitle!!.setTextColor(ContextCompat.getColor(context, i))
            customFrameLayout.customFontTextViewTitle!!.text = str
            return this
        }

        fun setToolbarDescription(str: String?, i: Int): Builder {
            customFrameLayout.customFontTextViewDescription.setTextColor(ContextCompat.getColor(context, i))
            customFrameLayout.customFontTextViewDescription.text = str
            customFrameLayout.customFontTextViewDescription.visibility = VISIBLE
            return this
        }

        fun setUpToolbarButtons(i: Int, i2: Int, onClickListener: OnClickListener?): Builder {
            customFrameLayout.roundNavButton!!.setDrawable(m17543c(i, i2), toolBarNavHeight)
            customFrameLayout.btnBackNav.setOnClickListener(onClickListener)
            customFrameLayout.btnBackNav.visibility = VISIBLE
            return this
        }

        fun setBackArrow(i: Int, i2: Int, i3: Int, onClickListener: OnClickListener?): Builder {
            customFrameLayout.roundNavButton!!.setDrawable(
                BitmapDrawable(
                    context.resources, ImageBitmaps.setBitmapGradient(
                        ViewUtils.createDrawableFromDrawable(
                            AppCompatResources.getDrawable(
                                context, i
                            )
                        ), i2, i3
                    )
                ), toolBarNavHeight
            )
            customFrameLayout.btnBackNav!!.visibility = VISIBLE
            customFrameLayout.btnBackNav!!.setOnClickListener(onClickListener)
            return this
        }

        private fun m17543c(i: Int, i2: Int): Drawable? {
            val b = AppCompatResources.getDrawable(context, i)
            if (i2 != -1) {
                b!!.setColorFilter(i2, PorterDuff.Mode.SRC_IN)
            }
            return b
        }

        fun setUpToolbarButtons(
            i: Int,
            i2: Int,
            i3: Int,
            onClickListener: OnClickListener?
        ): Builder {
            customFrameLayout.roundedOptionalNav!!.setDrawable(
                BitmapDrawable(
                    context.resources, ImageBitmaps.setBitmapGradient(
                        ViewUtils.createDrawableFromDrawable(
                            AppCompatResources.getDrawable(
                                context, i
                            )
                        ), i2, i3
                    )
                ), toolBarNavHeight
            )
            val layoutParams = customFrameLayout.optionalNav.layoutParams as LayoutParams
            layoutParams.width = context.resources.getDimensionPixelSize(R.dimen.toolbar_nav_height)
            customFrameLayout.optionalNav.layoutParams = layoutParams
            customFrameLayout.optionalNav.visibility = VISIBLE
            customFrameLayout.optionalNav.setOnClickListener(onClickListener)
            return this
        }

        fun setUpToolbarButtons(
            i: Int,
            i2: Int,
            i3: Int,
            onClickListener: OnClickListener?,
            str: String?,
            z: Boolean
        ): Builder {
            customFrameLayout.roundedOptionalNav!!.setText(
                BitmapDrawable(
                    context.resources, ImageBitmaps.setBitmapGradient(
                        ViewUtils.createDrawableFromDrawable(
                            AppCompatResources.getDrawable(
                                context, i
                            )
                        ), i2, i3
                    )
                ), toolBarNavHeight, str
            )
            val layoutParams = customFrameLayout.optionalNav.layoutParams
            val i4: Int = if (str != null) {
                -2
            } else {
                context.resources.getDimensionPixelSize(R.dimen.toolbar_nav_height)
            }
            layoutParams.width = i4
            customFrameLayout.optionalNav.layoutParams = layoutParams
            customFrameLayout.optionalNav.visibility = VISIBLE
            customFrameLayout.optionalNav.setOnClickListener(onClickListener)
            customFrameLayout.roundedOptionalNav!!.setCollapsible(z)
            return this
        }

        fun setRightNavButton(i: Int, i2: Int, onClickListener: OnClickListener?): Builder {
            customFrameLayout.roundedOptionalNav!!.setDrawable(m17543c(i, i2), toolBarNavHeight)
            customFrameLayout.optionalNav.visibility = VISIBLE
            customFrameLayout.optionalNav.setOnClickListener(onClickListener)
            return this
        }

        fun setNextToRightNavButton(i: Int, i2: Int, onClickListener: OnClickListener?): Builder {
            customFrameLayout.roundedOptionalNav2!!.setDrawable(m17543c(i, i2), toolBarNavHeight)
            customFrameLayout.optionalNav2.visibility = VISIBLE
            customFrameLayout.optionalNav2.setOnClickListener(onClickListener)
            return this
        }

        fun setNextToRightNavButton3(i: Int, i2: Int, onClickListener: OnClickListener?): Builder {
            customFrameLayout.roundedOptionalNav3!!.setDrawable(m17543c(i, i2), toolBarNavHeight)
            customFrameLayout.optionalNav3.visibility = VISIBLE
            customFrameLayout.optionalNav3.setOnClickListener(onClickListener)
            return this
        }

        fun setOptionalNavButtons(i: Int, i2: Int): Builder {
            customFrameLayout.roundedOptionalNav!!.setDrawable(m17543c(i, i2), toolBarNavHeight)
            customFrameLayout.optionalNav.visibility = VISIBLE
            return this
        }

        fun hideOptionalNav(): Builder {
            if (customFrameLayout.optionalNav2.visibility == VISIBLE) {
                customFrameLayout.optionalNav.visibility = GONE
            } else {
                customFrameLayout.optionalNav.visibility = INVISIBLE
            }
            return this
        }

        fun showOptionalNav(): Builder {
            customFrameLayout.optionalNav.visibility = VISIBLE

            return this
        }

        fun setRoundedOptionalNav2(
            drawable: Int,
            startColor: Int,
            endColor: Int,
            onClickListener: OnClickListener?,
            height: String?,
            isCollapsable: Boolean
        ): Builder {
            customFrameLayout.roundedOptionalNav2!!.setText(
                BitmapDrawable(
                    context.resources, ImageBitmaps.setBitmapGradient(
                        ViewUtils.createDrawableFromDrawable(
                            AppCompatResources.getDrawable(
                                context, drawable
                            )
                        ), startColor, endColor
                    )
                ), toolBarNavHeight, height
            )
            val layoutParams = customFrameLayout.optionalNav2!!.layoutParams as LayoutParams
            val i4: Int = if (height != null) {
                -2
            } else {
                context.resources.getDimensionPixelSize(R.dimen.toolbar_nav_height)
            }
            layoutParams.width = i4
            customFrameLayout.optionalNav2.layoutParams = layoutParams
            customFrameLayout.optionalNav2.visibility = VISIBLE
            customFrameLayout.optionalNav2.setOnClickListener(onClickListener)
            customFrameLayout.roundedOptionalNav2!!.setCollapsible(isCollapsable)
            return this
        }

        fun hideOptionalNav2(): Builder {
            customFrameLayout.optionalNav2.visibility = GONE
            return this
        }

        fun hideBackNav(): Builder {
            customFrameLayout.btnBackNav.visibility = GONE
            return this
        }

    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(
        context!!, attributeSet
    ) {
        initViews()
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context!!, attributeSet, i
    ) {
        initViews()
    }

    private fun initViews() {
        inflate(context, R.layout.custom_toolbar, this)
        customFontTextViewTitle = findViewById(R.id.title)
        customFontTextViewDescription = findViewById(R.id.desc)
        btnBackNav = findViewById(R.id.backNav)
        viewToolbar = findViewById(R.id.toolbarView)
        optionalNav = findViewById(R.id.optionalNav)
        optionalNav.visibility = INVISIBLE
        optionalNav2 = findViewById(R.id.optionalNav2)
        optionalNav2.visibility = GONE
        optionalNav3 = findViewById(R.id.optionalNav3)
        optionalNav3.visibility = GONE
        customFontTextViewDescription.visibility = GONE
        customView = findViewById(R.id.customView)
        roundNavButton = btnBackNav.findViewById(R.id.roundedNavButton)
        roundedOptionalNav = optionalNav.findViewById(R.id.roundedNavButton)
        roundedOptionalNav2 = optionalNav2.findViewById(R.id.roundedNavButton)
        roundedOptionalNav3 = optionalNav3.findViewById(R.id.rectangleNavButton)
        setPadding()
        setAppToolBarAlpha(0)
    }

    private fun setPadding() {
        if (Build.VERSION.SDK_INT >= 23) {
            viewToolbar!!.setPadding(0, ViewUtils.getDimensions(resources), 0, 0)
        }
    }

    fun setToolbarPadding() {
        if (Build.VERSION.SDK_INT >= 23) {
            viewToolbar!!.setPadding(0, 0, 0, 0)
        }
    }

    private fun setHomeAsUpButtonIcon(drawable: Drawable) {
        roundNavButton!!.setIcon(drawable)
    }

    fun setAppToolBarAlpha(i: Int) {
        if (isActive) {
            setTitleAlpha(i.toFloat() / 255.0f)
        }
        val res = resources
        val nightMode = res.getBoolean(R.bool.night_mode)
        if (nightMode) {
            viewToolbar!!.setBackgroundColor(Color.argb((i.toDouble() * 0.92).toInt(), 61, 61, 61))
        } else {
            viewToolbar!!.setBackgroundColor(
                Color.argb(
                    (i.toDouble() * 0.92).toInt(),
                    255,
                    255,
                    255
                )
            )
        }
        if (i >= 255) {
            setToolbarButtonText(roundedOptionalNav)
            setToolbarButtonText(roundNavButton)
            setToolbarButtonText(roundedOptionalNav2)
            setToolbarButtonText(roundedOptionalNav3)
        }
        if (i <= 0) {
            setAppBarButtonText(roundedOptionalNav)
            setAppBarButtonText(roundNavButton)
            setAppBarButtonText(roundedOptionalNav2)
            setAppBarButtonText(roundedOptionalNav3)
        }
    }

    fun setOptionalMenuDrawable(drawable: Drawable?) {
        roundedOptionalNav!!.setIcon(drawable)
    }

    fun setOptionalMenu2Drawable(drawable: Drawable?) {
        roundedOptionalNav2!!.setIcon(drawable)
    }

    fun setToolbarTitle(charSequence: CharSequence?) {
        if (customFontTextViewTitle!!.visibility == GONE) {
            customFontTextViewTitle!!.visibility = VISIBLE
        }
        customFontTextViewTitle!!.text = charSequence
    }

    fun setToolbarSubtitle(charSequence: CharSequence?) {
        customFontTextViewDescription.text = charSequence
    }

    val backNavView: View
        get() = btnBackNav
    val optionalNavView: View
        get() = optionalNav
    val optionalNav2View: View
        get() = optionalNav2

    fun setTitleAlpha(f: Float) {
        customFontTextViewTitle!!.alpha = f
        customFontTextViewDescription.alpha = f
    }

    fun setRoundNavText() {
        setToolbarButtonText(roundNavButton)
    }

    fun setRoundedOptionalNav3Text() {
        setToolbarButtonText(roundedOptionalNav3)
    }

    fun setRoundedOptionalNavText() {
        setToolbarButtonText(roundedOptionalNav)
    }

    fun setCustomView(view: View?) {
        customView!!.addView(view)
        customView!!.visibility = VISIBLE
    }

    fun hideAppToolBar() {
        customView!!.visibility = GONE
        if (customView!!.childCount > 0) {
            customView!!.removeAllViews()
        }
    }

    private fun setToolbarButtonText(customButton: CustomButton?) {
        if (customButton!!.iEnabled) {
            customButton.text = ""
            setTitleAlpha(1.0f)
        }
        customButton.disableShadow()
    }

    private fun setAppBarButtonText(customButton: CustomButton?) {
        if (customButton!!.iEnabled) {
            customButton.text = if (customButton.tag != null) customButton.tag.toString() else ""
            setTitleAlpha(java.lang.Float.intBitsToFloat(1))
        }
        customButton.setUpShadow()
    }
}