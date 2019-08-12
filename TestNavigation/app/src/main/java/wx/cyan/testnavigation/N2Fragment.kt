package wx.cyan.testnavigation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [N2Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [N2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class N2Fragment : Fragment() {
    lateinit var mView: View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_n2, container, false)
        arguments?.let {
            Log.i("日志", it.getString("name"))
            Log.i("日志", "${it.getInt("age", 0)}" )
        }
        mView.findViewById<Button>(R.id.btnToN1).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_page1)
        }
        mView.findViewById<Button>(R.id.btnToN3).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_page3)
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
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
