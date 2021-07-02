package com.theagilemonkeys.musictam.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseBindingFragment : Fragment() {

    private var binding: ViewDataBinding? = null
    private var layout: Int? = null
    private var props: Map<Int, Any?>? = null
    private var lifecycleOwner: LifecycleOwner? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setUpBinding()

        layout?.let {
            val viewRoot = inflater.inflate(it, container, false)
            binding = DataBindingUtil.bind(viewRoot)
        }

        props?.let {
            for (key in it.keys) {
                binding?.setVariable(key, it.get(key))
            }
        }

        if (lifecycleOwner != null) {
            binding?.lifecycleOwner = lifecycleOwner
        } else {
            binding?.lifecycleOwner = viewLifecycleOwner
        }

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        executeViewModels()
    }

    fun setBinding(
        layout: Int,
        props: Map<Int, Any>?,
        lifecycleOwner: LifecycleOwner? = viewLifecycleOwner
    ) {
        this.layout = layout
        this.props = props
        this.lifecycleOwner = lifecycleOwner
    }

    fun getBinding(): ViewDataBinding? {
        return binding
    }

    abstract fun setUpBinding()

    abstract fun setUpUI()

    abstract fun executeViewModels()

}