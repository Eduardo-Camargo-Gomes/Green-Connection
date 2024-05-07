
package com.example.green_connect

import com.example.green_connect.Tip
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class Tela_Introdutoria1 : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var dotsLayout: LinearLayout
    private lateinit var upButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_introdutoria1)

        viewPager = findViewById(R.id.view_pager)
        dotsLayout = findViewById(R.id.dots)
        upButton = findViewById(R.id.up)

        val tips = arrayOf(
            Tip(
                "Cultive a Vida, Cultive o Futuro.",
                "Transforme seu jardim em um oásis verde e sustentável com a ajuda da tecnologia.",
                R.drawable.vector_7,
                R.color.Verde
            ),

            Tip(
                "Jardinagem Automatizada.",
                "Irrigação Automática e Eficiente.\n" +
                        "Monitoramento Avançado do Solo.\n" +
                        "Energia Solar Limpa e Renovável.",
                R.drawable.group_4,
                R.color.Verde
            ),

            Tip(
                "Vamos Começar!",
                "",
                R.drawable.group_6,
                R.color.Verde
            )
        )

        addDots(tips.size)

        viewPager.adapter = OnboardingAdapter(tips)

        viewPager.setPageTransformer(true) { page, position ->
            page.alpha = 1 - abs(position)
            page.translationX = -position * page.width
        }

        viewPager.addOnPageChangeListener(onPageChangeListener)

        upButton.setOnClickListener {
            val intent = Intent(this, Tela_inicial::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addDots(size: Int) {
        dotsLayout.removeAllViews()

        val whiteDrawable = ContextCompat.getDrawable(baseContext, R.drawable.background_bolinhabranca)
        val grayDrawable = ContextCompat.getDrawable(baseContext, R.drawable.background_bolinhacinza)

        Array(size) {
            val imageView = ImageView(baseContext).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    // Margens das bolinhas
                    setMargins(5, 0, 5, 0)
                }
                // Ajuste o drawable com base na posição
                setImageDrawable(grayDrawable)
            }
            dotsLayout.addView(imageView)
        }
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            for (i in 0 until dotsLayout.childCount) {
                val dot = dotsLayout.getChildAt(i) as ImageView
                dot.setImageDrawable(
                    if (i == position) {
                        ContextCompat.getDrawable(baseContext, R.drawable.background_bolinhabranca)
                    } else {
                        ContextCompat.getDrawable(baseContext, R.drawable.background_bolinhacinza)
                    }
                )
            }
            upButton.visibility = if (position == viewPager.adapter?.count?.minus(1)) View.GONE else View.VISIBLE
        }
    }

    private inner class OnboardingAdapter(val tips: Array<Tip>) : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.tip_content, container, false)
            val tipTitleTextView = view.findViewById<TextView>(R.id.tip_title)
            val tipSubTitleTextView = view.findViewById<TextView>(R.id.tip_subtitle)
            val tipLogoImageView = view.findViewById<ImageView>(R.id.tip_logo)
            val returnToHomeButton = view.findViewById<Button>(R.id.returnToHomeButton)

            with(tips[position]) {
                tipTitleTextView.text = title
                tipSubTitleTextView.text = subtitle
                tipLogoImageView.setImageResource(logo)
                view.background = ContextCompat.getDrawable(this@Tela_Introdutoria1, background)
            }

            returnToHomeButton.visibility =
                if (position == tips.size - 1) View.VISIBLE else View.GONE

            returnToHomeButton.setOnClickListener {
                val intent = Intent(container.context, Tela_inicial::class.java)
                container.context.startActivity(intent)
            }

            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int = tips.size
    }
}

