package wx.cyan.testnavigation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation



class N3Fragment : Fragment() {
    lateinit var mView:View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView= inflater.inflate(R.layout.fragment_n3, container, false)
        mView.findViewById<Button>(R.id.btnToN2In3).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_page2_in3)
        }
        mView.findViewById<Button>(R.id.btnBack).setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        return mView;
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment N1Fragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            N1Fragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
