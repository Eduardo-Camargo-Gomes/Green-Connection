package com.example.green_connect

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adaptador para gerenciar a exibição de fragmentos no ViewPager2.
 * @param fragmentActivity A atividade que contém o ViewPager2.
 */
class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    // Lista para armazenar os fragmentos
    private val fragmentList: MutableList<Fragment> = ArrayList()

    // Lista para armazenar os títulos dos fragmentos
    private val titleList: MutableList<String> = ArrayList()

    /**
     * Retorna o título do fragmento na posição especificada.
     * @param position A posição do título a ser retornado.
     * @return O título do fragmento na posição especificada.
     */
    fun getTitle(position: Int): String {
        return titleList[position]
    }

    /**
     * Adiciona um fragmento e seu título ao adaptador.
     * @param fragment O fragmento a ser adicionado.
     * @param title O título do fragmento a ser adicionado.
     */
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    /**
     * Cria o fragmento para a posição especificada.
     *
     * @param position A posição do fragmento a ser criado.
     * @return O fragmento para a posição especificada.
     */
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    /**
     * Retorna o número total de fragmentos.
     *
     * @return O número total de fragmentos.
     */
    override fun getItemCount(): Int {
        return fragmentList.size
    }
}
