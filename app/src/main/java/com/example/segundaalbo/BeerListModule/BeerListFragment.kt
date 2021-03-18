package com.example.segundaalbo.BeerListModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.segundaalbo.common.db.entities.BeerEntity
import com.example.segundaalbo.databinding.BeerListFragmentBinding
import kotlinx.android.synthetic.main.beer_list_fragment.*


class BeerListFragment : Fragment(), BeerListAdapter.BeerItemListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter: BeerListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mBinding: BeerListFragmentBinding
    private var isOnRefresh = false
    private var isLoading = false
    private var page  = 1
    private var per_page = 50
    private var totalPage = 20 //Estimando 1000 cervezas



    private fun setUpRecyclerView() {
        beerListAdapter = BeerListAdapter(this)
        linearLayoutManager = LinearLayoutManager(requireContext())

        mBinding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = beerListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    hasFixedSize()
                    val visibleItemCount = linearLayoutManager.childCount
                    val pastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    val total = beerListAdapter.itemCount

                    swipeRefresh.isEnabled = linearLayoutManager.findFirstCompletelyVisibleItemPosition()==0

                    if(!isLoading && page < totalPage){
                        if(visibleItemCount+pastVisibleItem >= total){
                            progressBar.visibility = View.VISIBLE
                            page++
                            isLoading = true
                            viewModel.loadBeers("${page}","${per_page}")
                        }
                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }

        swipeRefresh.setOnRefreshListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = BeerListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)
        isLoading = true
        if(!isOnRefresh) progressBar.visibility = View.VISIBLE

        viewModel.getBeers().observe(viewLifecycleOwner, Observer{ response ->
            beerListAdapter.addItems(response)
            if(response.size>0){
                if(page==totalPage){
                    progressBar.visibility = View.GONE
                }
                else {
                    progressBar.visibility = View.INVISIBLE
                }
                isLoading = false
                swipeRefresh.isRefreshing = false
            }
        })
        viewModel.status.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.loadBeers("${page}","${per_page}")

    }

    override fun onClickedBeerItem(view: View, beer: BeerEntity) {
    //    Navigation.findNavController(view).navigate(R.id.action_beerListFragment_to_beerPageFragment)
        //Toast.makeText(requireContext(), beer.name, Toast.LENGTH_SHORT).show()

        val action :NavDirections  = BeerListFragmentDirections.actionBeerListFragmentToBeerPageFragment(beer)
        findNavController().navigate(action)


    }

    override fun onRefresh() {
        beerListAdapter.clear()
        page = 1
        isLoading = true
        viewModel.loadBeers("${page}","${per_page}")
    }


}