package com.example.capstone_navi_tab.ui.calendar

import android.annotation.SuppressLint
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.capstone_navi_tab.R
import com.example.capstone_navi_tab.databinding.FragmentCalendarBinding
import com.example.capstone_navi_tab.databinding.FragmentCalendarDiaryBinding
import com.example.capstone_navi_tab.databinding.FragmentSettingsBinding
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar_diary.*

class CalendarFragment : Fragment() {

    private var fname: String = ""
    private var str: String = ""

    lateinit var binding: FragmentCalendarDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarDiaryBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView2.setOnDateChangeListener { view, year, month, dayOfMonth ->

            binding.date.visibility = View.VISIBLE
            binding.btnSave.visibility = View.VISIBLE
            binding.editTodo.visibility = View.VISIBLE
            binding.details.visibility = View.INVISIBLE
            binding.btnUpdate.visibility = View.INVISIBLE
            binding.btnDel.visibility = View.INVISIBLE

            binding.date.text=String.format("%d / %d / %d",year,month+1,dayOfMonth)

            binding.editTodo.setText("")

            checkedDay(year,month,dayOfMonth)

        }

        binding.btnSave.setOnClickListener {
            saveDiary(fname)
            //toast(fname + "???????????? ??????????????????.")
            Toast.makeText(context,"OK",Toast.LENGTH_SHORT).show()
            str = binding.editTodo.text.toString()

            binding.details.text="${str}"
            binding.btnSave.visibility = View.INVISIBLE
            binding.btnUpdate.visibility = View.VISIBLE
            binding.btnDel.visibility = View.VISIBLE
            binding.editTodo.visibility = View.INVISIBLE
            binding.details.visibility = View.VISIBLE
        }
    }

    private fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
        fname = ""+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt"

        var fis: FileInputStream? = null

        try{
            fis = activity?.openFileInput(fname)

            val fileData = fis?.available()?.let { ByteArray(it) } // fileData??? ????????? ??????
//?????? ??????
            fis?.read(fileData) // fileData??? ??????
            fis?.close()

            str = fileData?.let { String(it) }.toString() // str ????????? fileData??? ??????

            binding.editTodo.visibility = View.INVISIBLE
            binding.details.visibility = View.VISIBLE
            binding.details.text = "${str}" // textView??? str ??????

            binding.btnSave.visibility = View.INVISIBLE
            binding.btnUpdate.visibility = View.VISIBLE
            binding.btnDel.visibility = View.VISIBLE

            binding.btnUpdate.setOnClickListener { // ?????? ????????? ?????? ???
                binding.editTodo.visibility = View.VISIBLE
                binding.details.visibility = View.INVISIBLE
                binding.editTodo.setText(str) // editText??? textView??? ?????????
// ????????? ??????
                binding.btnSave.visibility = View.VISIBLE
                binding.btnUpdate.visibility = View.INVISIBLE
                binding.btnDel.visibility = View.INVISIBLE
                binding.details.text = "${binding.editTodo.text}"
            }

            binding.btnDel.setOnClickListener {
                binding.details.visibility = View.INVISIBLE
                binding.editTodo.setText("")
                binding.editTodo.visibility = View.VISIBLE
                binding.btnSave.visibility = View.VISIBLE
                binding.btnUpdate.visibility = View.INVISIBLE
                binding.btnDel.visibility = View.INVISIBLE
                removeDiary(fname)
                Toast.makeText(context,"OK",Toast.LENGTH_SHORT).show()
                // toast(fname + "???????????? ??????????????????.")
            }

            if(binding.details.text == ""){
                binding.details.visibility = View.INVISIBLE
                binding.date.visibility = View.VISIBLE
                binding.btnSave.visibility = View.VISIBLE
                binding.btnUpdate.visibility = View.INVISIBLE
                binding.btnDel.visibility = View.INVISIBLE
                binding.editTodo.visibility = View.VISIBLE
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun saveDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = activity?.openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            if(binding.editTodo.text!=null) {
                var content: String = binding.editTodo.text.toString()
                fos?.write(content.toByteArray())
                fos?.close()
            }
            else{
                Toast.makeText(context,"????????? ???????????????",Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun removeDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = activity?.openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos?.write(content.toByteArray())
            fos?.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}