package wc.c.viewmodelivedatalifecycle.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import wc.c.viewmodelivedatalifecycle.R
import wc.c.viewmodelivedatalifecycle.lefecycles.MyLifeObserver
import wc.c.viewmodelivedatalifecycle.viewmodels.Datas


class TopFragment : Fragment() {
    lateinit var contentView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentView = inflater.inflate(R.layout.fragment_top, container, false)
        lifecycle.addObserver(lifeObserver)
        return contentView
    }

    val lifeObserver = object : MyLifeObserver() {
        override fun whenCreate() {
        }

        override fun whenStart() {
        }

        override fun whenResume() {
            activity?.also {
                //获取ViewModel，然后监听LiveData的修改，来修改UI
                val datas = ViewModelProvider(it).get(Datas::class.java)
                datas.requestTs.observe(this@TopFragment, Observer { ts ->
                    contentView?.also { cv ->
                        cv.findViewById<TextView>(R.id.topTxt).text = "$ts"
                    }
                })
            }
        }

        override fun whenPause() {
        }

        override fun whenStop() {
        }

        override fun whenDestroy() {
        }
    }
}
