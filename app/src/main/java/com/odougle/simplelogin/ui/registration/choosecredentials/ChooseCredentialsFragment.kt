package com.odougle.simplelogin.ui.registration.choosecredentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.odougle.simplelogin.R
import kotlinx.android.synthetic.main.fragment_choose_credentials.*

class ChooseCredentialsFragment : Fragment() {

    val args: ChooseCredentialsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_credentials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setando o nome recebido do fragment anterior
        textChooseCredentialsName.text = getString(R.string.choose_credentials_text_name, args)
    }
}