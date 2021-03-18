package com.example.segundaalbo.BeerPageModule

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.segundaalbo.R
import com.example.segundaalbo.databinding.BeerPageFragmentBinding

class BeerPageFragment : Fragment() {

    private lateinit var viewModel: BeerPageViewModel
    private val args by navArgs<BeerPageFragmentArgs>()
    lateinit var mBinding: BeerPageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = BeerPageFragmentBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.selectedBeer.name

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeerPageViewModel::class.java)

        fillDataFiels()

    }

    private fun fillDataFiels() {

        var foods:String =""
        var index: Int = 0

        Glide.with(requireActivity())
            .load(args.selectedBeer.image_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .into(mBinding.imageBeer)

        mBinding.tvTagline.text = args.selectedBeer.tagline
        mBinding.tvFirstBrewed.text = args.selectedBeer.first_brewed
        mBinding.tvDescription.text = args.selectedBeer.description

//        for (food in args.selectedBeer.food_pairing){
//            foods += "${index}. ${food}\n"
//            index++
//        }
        mBinding.tvFoods.text = foods
        Log.d("HOLA", ""+foods)

    }

}