package id.kodesumsi.netwatch.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BasePagingAdapter<VB: ViewBinding, T: Any>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val setUpViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val bindItemIntoLayout: (item: T, binding: VB) -> Unit
) : PagingDataAdapter<T, BasePagingAdapter.BasePagingViewHoler<VB, T>>(diffCallback) {

    class BasePagingViewHoler<VB: ViewBinding, T>(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: T, bindItemIntoLayout: (item: T, binding: VB) -> Unit) {
            @Suppress("UNCHECKED_CAST")
            bindItemIntoLayout(item, binding as VB)
        }
    }

    override fun onBindViewHolder(holder: BasePagingViewHoler<VB, T>, position: Int) {
        val item = getItem(position)
        holder.bindItem(item!!, bindItemIntoLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasePagingViewHoler<VB, T> {
        return BasePagingViewHoler(
            setUpViewBinding.invoke(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}